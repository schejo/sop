package ctrl;

import DAL.EquiposDal;
import MD.EquiposMd;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Include;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Textbox;

public class EquiposCtrl extends GenericForwardComposer {

    private Textbox numEqu;
    private Textbox nomEqu;
    
    List<EquiposMd> allEquipos = new ArrayList<EquiposMd>();

    private Listbox lb2;
    EquiposDal rg = new EquiposDal();
    private Include rootPagina;

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        allEquipos = rg.RSelect();
        lb2.setModel(new ListModelList(allEquipos));
        numEqu.focus();
    }

    //Selecionar nombres de una lista a campos de un combobox
    public void onSelect$lb2() throws SQLException {
        ArrayList<String> arraytemp = new ArrayList<String>();
        for (Object obj : lb2.getSelectedItem().getChildren()) {
            Listcell celda = (Listcell) obj;
            arraytemp.add(celda.getLabel());
        }
        String numero = arraytemp.get(0);
        String nombre = arraytemp.get(1);

        numEqu.setText(numero);
        nomEqu.setText(nombre);

        numEqu.setDisabled(true);
        nomEqu.setDisabled(true);

    }//fin

    public void onChange$numEqu(Event e) throws SQLException {
        if (numEqu.getText().isEmpty()) {

            numEqu.setText("");
            nomEqu.setText("");
        } else {
            for (EquiposMd dt : allEquipos) {
                if (dt.getEquipo().equals(numEqu.getText().toUpperCase())) {

                    numEqu.setText(dt.getEquipo().toUpperCase());
                    nomEqu.setText(dt.getNombre().toUpperCase());
                }
            }
        }
    }

    public void onClick$btnGuardar(Event e) throws SQLException {

        if (((numEqu.getText().equals("")) && (nomEqu.getText().equals("")))) {

            Clients.showNotification("NO SE GUARDO EL REGISTRO <br/> DEBE LLENAR LOS CAMPOS VACIOS! <br/>",
                    "warning", null, "middle_center", 5000);
        } else {

            int op = 0;

            for (EquiposMd dt : allEquipos) {
                if (dt.getEquipo().equals(numEqu.getText().toUpperCase())) {
                    op++;
                }
            }

            if (op == 0) {
                rg.REGinsert(numEqu.getText().toUpperCase(), nomEqu.getText().toUpperCase());

                numEqu.setText("");
                nomEqu.setText("");

                allEquipos = rg.RSelect();
                lb2.setModel(new ListModelList(allEquipos));
            } else {
                rg.REGupdate(numEqu.getText().toUpperCase(), nomEqu.getText().toUpperCase());
                numEqu.setDisabled(false);
                numEqu.setText("");
                nomEqu.setText("");
                numEqu.focus();
                allEquipos = rg.RSelect();
                lb2.setModel(new ListModelList(allEquipos));
            }
        }
    }

    public void onClick$btnNuevo(Event e) throws SQLException {
        numEqu.setText("");
        nomEqu.setText("");
        numEqu.focus();

        numEqu.setDisabled(false);
        nomEqu.setDisabled(false);

    }

    public void onClick$btnSalir() {
        rootPagina.setSrc("/Views/Principal.zul");
    }
}
