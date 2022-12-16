package ctrl;

import DAL.PaisesDal;
import MD.PaisesMd;
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

public class PaisesCtrl extends GenericForwardComposer {

    private Textbox numPai;
    private Textbox nomPai;
    private Combobox regPai;
    String lb;
    String nombre;
    String region;
    
    List<PaisesMd> allPaises = new ArrayList<PaisesMd>();

    private Listbox lb2;
    PaisesDal rg = new PaisesDal();
    private Include rootPagina;

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        allPaises = rg.RSelect();
        lb2.setModel(new ListModelList(allPaises));
        numPai.focus();
    }

    public void onClick$btnNuevo(Event e) throws SQLException {
        
        numPai.setText("");
        nomPai.setText("");
        regPai.setText("");
        numPai.focus();
        //se deshabilitan los campos bloqueados
        numPai.setDisabled(false);
        nomPai.setDisabled(false);
        regPai.setDisabled(false);
    }

    //Selecionar nombres de una lista a campos de un combobox
    public void onSelect$lb2() throws SQLException {
        
        ArrayList<String> arraytemp = new ArrayList<String>();
        for (Object obj : lb2.getSelectedItem().getChildren()) {
            Listcell celda = (Listcell) obj;
            arraytemp.add(celda.getLabel());
        }
        lb = arraytemp.get(0);
        nombre = arraytemp.get(1);
        region = arraytemp.get(2);

        numPai.setText(lb);
        nomPai.setText(nombre);
        regPai.setText(region);
        //sirven para que en este metodo y en cualquier otro
        //se habilite o desabilite el camposeleccionado
        regPai.setDisabled(true);
        numPai.setDisabled(true);
        nomPai.setDisabled(true);
        
    }//fin

    public void onChange$numPai(Event e) throws SQLException {
        
        if (numPai.getText().isEmpty()) {

            numPai.setText("");
            nomPai.setText("");
            regPai.setText("");
            
        } else {
            for (PaisesMd dt : allPaises) {
                if (dt.getPais().equals(numPai.getText().toUpperCase())) {

                    numPai.setText(dt.getPais());
                    nomPai.setText(dt.getNombre());
                    regPai.setText(dt.getNomregion());
                }
            }
        }
    }

    public void onClick$btnGuardar(Event e) throws SQLException {

        if (((numPai.getText().equals("")) && (nomPai.getText().equals("")) && ((regPai.getText().equals(""))))) {

            Clients.showNotification("NO SE GUARDO EL REGISTRO <br/> DEBE LLENAR LOS CAMPOS VACIOS! <br/>",
                    "warning", null, "middle_center", 5000);
        } else {

            int op = 0;

            for (PaisesMd dt : allPaises) {
                if (dt.getPais().equals(numPai.getText().toUpperCase())) {
                    op++;
                }
            }

            if (op == 0) {
                rg.REGinsert(numPai.getText().toUpperCase(), nomPai.getText().toUpperCase(), regPai.getSelectedItem().getValue().toString());

                numPai.setText("");
                nomPai.setText("");
                regPai.setText("");

                allPaises = rg.RSelect();
                lb2.setModel(new ListModelList(allPaises));
            } else {
                rg.REGupdate(numPai.getText().toUpperCase(), nomPai.getText().toUpperCase(), regPai.getSelectedItem().getValue().toString());
              
                numPai.setDisabled(false);
                numPai.setText("");
                nomPai.setText("");
                regPai.setText("");
                numPai.focus();

                allPaises = rg.RSelect();
                lb2.setModel(new ListModelList(allPaises));
            }
        }
    }

    public void onClick$btnSalir() {
        rootPagina.setSrc("/Views/Principal.zul");
    }
}
