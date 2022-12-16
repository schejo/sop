package ctrl;

import DAL.AreasTrabajoDal;
import MD.AreasTrabajoMd;
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

public class AreasTrabajoCtrl extends GenericForwardComposer {

    private Textbox areTra;
    private Textbox desAre;
    private Textbox tieEst;

    List<AreasTrabajoMd> allAreasTrabajo = new ArrayList<AreasTrabajoMd>();

    private Listbox lb2;
    AreasTrabajoDal rg = new AreasTrabajoDal();
    private Include rootPagina;

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        allAreasTrabajo = rg.RSelect();
        lb2.setModel(new ListModelList(allAreasTrabajo));
        areTra.focus();
    }

    //Selecionar nombres de una lista a campos de un combobox
    public void onSelect$lb2() throws SQLException {
        ArrayList<String> arraytemp = new ArrayList<String>();
        for (Object obj : lb2.getSelectedItem().getChildren()) {
            Listcell celda = (Listcell) obj;
            arraytemp.add(celda.getLabel());
        }
        String area = arraytemp.get(0);
        String descripcion = arraytemp.get(1);
        String estadia = arraytemp.get(2);

        areTra.setText(area);
        desAre.setText(descripcion);
        tieEst.setText(estadia);

        areTra.setDisabled(true);
        desAre.setDisabled(true);
        tieEst.setDisabled(true);

    }//fin

    public void onChange$areTra(Event e) throws SQLException {
        if (areTra.getText().isEmpty()) {

            areTra.setText("");
            desAre.setText("");
            tieEst.setText("");
        } else {
            for (AreasTrabajoMd dt : allAreasTrabajo) {
                if (dt.getCodigo().equals(areTra.getText())) {

                    areTra.setText(dt.getCodigo());
                    desAre.setText(dt.getDescripcion());
                    tieEst.setText(dt.getTiempo());
                }
            }
        }
    }

    public void onClick$btnGuardar(Event e) throws SQLException {

        if (((areTra.getText().equals("")) && (desAre.getText().equals("")) && ((tieEst.getText().equals(""))))) {

            Clients.showNotification("NO SE GUARDO EL REGISTRO <br/> DEBE LLENAR LOS CAMPOS VACIOS! <br/>",
                    "warning", null, "middle_center", 5000);
        } else {

            int op = 0;

            for (AreasTrabajoMd dt : allAreasTrabajo) {
                if (dt.getCodigo().equals(areTra.getText())) {
                    op++;
                }
            }

            if (op == 0) {
                rg.REGinsert(areTra.getText(), desAre.getText(), tieEst.getText());

                areTra.setText("");
                desAre.setText("");
                tieEst.setText("");

                allAreasTrabajo = rg.RSelect();
                lb2.setModel(new ListModelList(allAreasTrabajo));
            } else {
                rg.REGupdate(areTra.getText(), desAre.getText(), tieEst.getText());
                areTra.setDisabled(false);
                areTra.setText("");
                desAre.setText("");
                tieEst.setText("");
                areTra.focus();
                allAreasTrabajo = rg.RSelect();
                lb2.setModel(new ListModelList(allAreasTrabajo));
            }
        }
    }

    public void onClick$btnNuevo(Event e) throws SQLException {
        areTra.setText("");
        desAre.setText("");
        tieEst.setText("");

        areTra.setDisabled(false);
        desAre.setDisabled(false);
        tieEst.setDisabled(false);

        areTra.focus();

    }

    public void onClick$btnSalir() {
        rootPagina.setSrc("/Views/Principal.zul");
    }
}
