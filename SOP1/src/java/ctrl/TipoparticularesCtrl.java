package ctrl;

import DAL.TipoparticularesDal;
import MD.ParticularesMd;
import MD.TipoparticularesMd;
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

public class TipoparticularesCtrl extends GenericForwardComposer {

    private Textbox parPar;
    private Textbox nomPar;
    private Combobox tipPar;

    List<TipoparticularesMd> allTipoparticulares = new ArrayList<TipoparticularesMd>();
    List<ParticularesMd> allParticulares = new ArrayList<ParticularesMd>();

    private Listbox lb2;
    TipoparticularesDal rg = new TipoparticularesDal();
    private Include rootPagina;

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        allTipoparticulares = rg.RSelect();
        allParticulares = rg.ParticularesSelect();
        lb2.setModel(new ListModelList(allTipoparticulares));
        tipPar.setModel(new ListModelList(allParticulares));
        parPar.focus();
    }

    //Selecionar nombres de una lista a campos de un combobox
    public void onSelect$lb2() throws SQLException {
        ArrayList<String> arraytemp = new ArrayList<String>();
        for (Object obj : lb2.getSelectedItem().getChildren()) {
            Listcell celda = (Listcell) obj;
            arraytemp.add(celda.getLabel());
        }
        String codigo = arraytemp.get(0);
        String nombre = arraytemp.get(1);
        String tipo = arraytemp.get(2);

        parPar.setText(codigo);
        nomPar.setText(nombre);
        tipPar.setText(tipo);

        parPar.setDisabled(true);
        nomPar.setDisabled(true);
        tipPar.setDisabled(true);

    }//fin

    public void onChange$parPar(Event e) throws SQLException {

        if (parPar.getText().isEmpty()) {

            parPar.setText("");
            nomPar.setText("");
            tipPar.setText("");
        } else {
            for (TipoparticularesMd dt : allTipoparticulares) {
                if (dt.getParticular().equals(parPar.getText().toUpperCase())) {

                    parPar.setText(dt.getParticular());
                    nomPar.setText(dt.getNombre());
                    tipPar.setText(dt.getTipo());
                }
            }
        }
    }

    public void onClick$btnGuardar(Event e) throws SQLException {

        if (((parPar.getText().equals("")) && (nomPar.getText().equals("")) && ((tipPar.getText().equals(""))))) {

            Clients.showNotification("NO SE GUARDO EL REGISTRO <br/> DEBE LLENAR LOS CAMPOS VACIOS! <br/>",
                    "warning", null, "middle_center", 5000);
        } else {

            int op = 0;

            for (TipoparticularesMd dt : allTipoparticulares) {
                if (dt.getParticular().equals(parPar.getText())) {
                    op++;
                }
            }

            if (op == 0) {
                rg.REGinsert(parPar.getText(), nomPar.getText(), tipPar.getSelectedItem().getValue().toString());

                parPar.setText("");
                nomPar.setText("");
                tipPar.setText("");

                allTipoparticulares = rg.RSelect();
                lb2.setModel(new ListModelList(allTipoparticulares));
            } else {
                rg.REGupdate(parPar.getText(), nomPar.getText(), tipPar.getSelectedItem().getValue().toString());

                parPar.setText("");
                nomPar.setText("");
                tipPar.setText("");
                parPar.focus();

                allTipoparticulares = rg.RSelect();
                lb2.setModel(new ListModelList(allTipoparticulares));
            }
        }
    }

    public void onClick$btnANuevo(Event e) throws SQLException {
        parPar.setText("");
        nomPar.setText("");
        tipPar.setText("");

        parPar.setDisabled(false);
        nomPar.setDisabled(false);
        tipPar.setDisabled(false);

        parPar.focus();

    }

    public void onClick$btnSalir() {
        rootPagina.setSrc("/Views/Principal.zul");
    }
}
