package ctrl;

import DAL.LineasDal;
import MD.LineasMd;
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
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

public class LineasCtrl extends GenericForwardComposer {

    private Textbox numLin;
    private Textbox nomLin;
    String lb;
    List<LineasMd> allLineas = new ArrayList<LineasMd>();

    private Listbox lb2;
    LineasDal rg = new LineasDal();
    private Include rootPagina;

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        allLineas = rg.RSelect();
        lb2.setModel(new ListModelList(allLineas));
        numLin.focus();
    }

    public void onClick$btnNuevo(Event e) throws SQLException {
        numLin.setText("");
        nomLin.setText("");
        numLin.focus();

        numLin.setDisabled(false);
        nomLin.setDisabled(false);
    }

    //inicio
    public void onSelect$lb2() throws SQLException {
        lb = lb2.getSelectedItem().getLabel();
        System.out.println("alias: " + lb);
        allLineas = rg.REGselect(lb);
        if (allLineas.isEmpty()) {
            Messagebox.show("Esta vacio", "Warning", Messagebox.OK, Messagebox.EXCLAMATION);
        } else {
            for (LineasMd dt : allLineas) {
                numLin.setText(dt.getLinea().toUpperCase());
                nomLin.setText(dt.getNombre().toUpperCase());

                numLin.setDisabled(true);
                nomLin.setDisabled(true);

            }
        }

    }//fin 

    public void onChange$numLin(Event e) throws SQLException {
        if (numLin.getText().isEmpty()) {

            numLin.setText("");
            nomLin.setText("");
        } else {
            for (LineasMd dt : allLineas) {
                if (dt.getLinea().equals(numLin.getText().toUpperCase())) {

                    numLin.setText(dt.getLinea().toUpperCase());
                    nomLin.setText(dt.getNombre().toUpperCase());
                }
            }
        }
    }

    public void onClick$btnGuardar(Event e) throws SQLException {

        if (((numLin.getText().equals("")) && (nomLin.getText().equals("")))) {

            Clients.showNotification("NO SE GUARDO EL REGISTRO <br/> DEBE LLENAR LOS CAMPOS VACIOS! <br/>",
                    "warning", null, "middle_center", 5000);
        } else {

            int op = 0;

            for (LineasMd dt : allLineas) {
                if (dt.getLinea().equals(numLin.getText().toUpperCase())) {
                    op++;
                }
            }

            if (op == 0) {
                rg.REGinsert(numLin.getText().toUpperCase(), nomLin.getText().toUpperCase());

                numLin.setText("");
                nomLin.setText("");

                allLineas = rg.RSelect();
                lb2.setModel(new ListModelList(allLineas));

            } else {
                rg.REGupdate(numLin.getText().toUpperCase(), nomLin.getText().toUpperCase());
                numLin.setDisabled(false);
                numLin.setText("");
                nomLin.setText("");
                numLin.focus();

                allLineas = rg.RSelect();
                lb2.setModel(new ListModelList(allLineas));
            }
        }
    }

    public void onClick$btnSalir() {
        rootPagina.setSrc("/Views/Principal.zul");
    }
}
