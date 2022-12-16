package ctrl;

import DAL.RegionDal;
import MD.RegionMd;
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

public class RegionesCtrl extends GenericForwardComposer {

    private Textbox numReg;
    private Textbox nomReg;
    String lb;

    List<RegionMd> allRegion = new ArrayList<RegionMd>();

    private Listbox lb2;
    RegionDal rg = new RegionDal();
    private Include rootPagina;

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        numReg.setDisabled(false);
        allRegion = rg.RSelect();
        lb2.setModel(new ListModelList(allRegion));
        numReg.focus();
    }

    public void onClick$btnNuevo(Event e) throws SQLException {

        numReg.setText("");
        nomReg.setText("");

        numReg.setDisabled(true);
        nomReg.setDisabled(false);

        nomReg.focus();
    }

    //inicio
    public void onSelect$lb2() throws SQLException {
        lb = lb2.getSelectedItem().getLabel();
        System.out.println("alias: " + lb);
        allRegion = rg.REGselect(lb);
        if (allRegion.isEmpty()) {
            Messagebox.show("Esta vacio", "Warning", Messagebox.OK, Messagebox.EXCLAMATION);
        } else {
            for (RegionMd dt : allRegion) {
                numReg.setText(dt.getNumero());
                nomReg.setText(dt.getNombre().toUpperCase());

                numReg.setDisabled(true);
                nomReg.setDisabled(true);
            }
        }

    }//fin 

    public void onChange$numReg(Event e) throws SQLException {

        if (numReg.getText().isEmpty()) {

            numReg.setText("");
            nomReg.setText("");
        } else {
            for (RegionMd dt : allRegion) {
                if (dt.getNumero().equals(numReg.getText())) {

                    numReg.setText(dt.getNumero());
                    nomReg.setText(dt.getNombre());

                }
            }
        }
    }

    public void onClick$btnGuardar(Event e) throws SQLException {

        if (((numReg.getText().equals("")) && (nomReg.getText().equals("")))) {

            Clients.showNotification("NO PUEDE DEJAR <br/>  <br/>  CAMPOS VACIOS <br/> <br/>INTENTELO DE NUEVO",
                    Clients.NOTIFICATION_TYPE_WARNING, null, "middle_center", 0);
        } else {

            int op = 0;

            for (RegionMd dt : allRegion) {
                if (dt.getNumero().equals(numReg.getText())) {
                    op++;
                }
            }

            if (op == 0) {
                rg.REGinsert(numReg.getText(), nomReg.getText().toUpperCase());

                numReg.setDisabled(false);
                numReg.setText("");
                nomReg.setText("");

                allRegion = rg.RSelect();
                lb2.setModel(new ListModelList(allRegion));

            } else {
                rg.REGupdate(numReg.getText(), nomReg.getText().toUpperCase());

                numReg.setDisabled(false);
                nomReg.setText("");
                numReg.focus();
            }
        }
        allRegion = rg.RSelect();
        lb2.setModel(new ListModelList(allRegion));

    }

    public void onClick$btnSalir() {
        rootPagina.setSrc("/Views/Principal.zul");
    }

}
