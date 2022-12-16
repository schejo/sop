package ctrl;

import DAL.PuertosDal;
import MD.PaisesMd;
import MD.PuertosMd;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Include;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Textbox;

public class PuertosCtrl extends GenericForwardComposer {

    private Textbox puePue;
    private Textbox nomPue;
    private Combobox paiPue;

    List<PuertosMd> allPuertos = new ArrayList<PuertosMd>();
    List<PaisesMd> allPais = new ArrayList<PaisesMd>();

    private Listbox lb2;
    PuertosDal rg = new PuertosDal();
    private Include rootPagina;

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);

        paiPue.setDisabled(true);
        
        allPuertos = rg.RSelect();
        allPais = rg.PaisSelect();
        lb2.setModel(new ListModelList(allPuertos));
        paiPue.setModel(new ListModelList(allPais));
        puePue.focus();
    }

    //Selecionar nombres de una lista a campos de un combobox
    public void onSelect$lb2() throws SQLException {
        ArrayList<String> arraytemp = new ArrayList<String>();
        for (Object obj : lb2.getSelectedItem().getChildren()) {
            Listcell celda = (Listcell) obj;
            arraytemp.add(celda.getLabel());
        }
        String puerto = arraytemp.get(0);
        String nombre = arraytemp.get(1);
        String pais = arraytemp.get(2);

        puePue.setText(puerto);
        nomPue.setText(nombre);
        paiPue.setText(pais);
        
        puePue.setDisabled(true);
        nomPue.setDisabled(true);
        paiPue.setDisabled(true);

    }

    public void onChange$puePue(Event e) throws SQLException {

        paiPue.setDisabled(false);

        if (puePue.getText().isEmpty()) {

            puePue.setText("");
            nomPue.setText("");
            paiPue.setText("");
        } else {
            for (PuertosMd dt : allPuertos) {
                if (dt.getPuerto().equals(puePue.getText().toUpperCase())) {

                    puePue.setText(dt.getPuerto());
                    nomPue.setText(dt.getNombre());
                    paiPue.setText(dt.getPais());
                }
            }
        }
    }

    public void onClick$btnGuardar(Event e) throws SQLException {

        if (((puePue.getText().equals("")) && (nomPue.getText().equals("")) && ((paiPue.getText().equals(""))))) {

            Clients.showNotification("NO SE GUARDO EL REGISTRO <br/> DEBE LLENAR LOS CAMPOS VACIOS! <br/>",
                    "warning", null, "middle_center", 5000);
        } else {

            int op = 0;

            for (PuertosMd dt : allPuertos) {
                if (dt.getPuerto().equals(puePue.getText().toUpperCase())) {
                    op++;
                }
            }

            if (op == 0) {
                rg.REGinsert(puePue.getText().toUpperCase(), nomPue.getText().toUpperCase(), paiPue.getSelectedItem().getValue().toString());
               
                puePue.setText("");
                nomPue.setText("");
                paiPue.setSelectedIndex(-1);

                allPuertos = rg.RSelect();
                lb2.setModel(new ListModelList(allPuertos));
                
            } else {
                rg.REGupdate(puePue.getText().toUpperCase(), nomPue.getText().toUpperCase(), paiPue.getSelectedItem().getValue().toString());

                puePue.setDisabled(false);
                puePue.setText("");
                paiPue.setText("");
                puePue.focus();

                allPuertos = rg.RSelect();
                lb2.setModel(new ListModelList(allPuertos));
            }
        }
    }

    public void onClick$btnNuevo(Event e) throws SQLException {
        puePue.setText("");
        nomPue.setText("");
        paiPue.setText("");
        puePue.focus();
        
        puePue.setDisabled(false);
        nomPue.setDisabled(false);
        paiPue.setDisabled(false);

    }

    public void onClick$btnSalir() {
        rootPagina.setSrc("/Views/Principal.zul");
    }
}
