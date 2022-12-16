package ctrl;

import DAL.PersonalDal;
import MD.PersonalMd;
import MD.PuestosMd;
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

public class PersonalCtrl extends GenericForwardComposer {

    private Textbox empPer;
    private Textbox nomPer;
    private Combobox pucPer;
    String codigo;
    String nombre;
    String puesto;

    List<PersonalMd> allPersonal = new ArrayList<PersonalMd>();
    List<PuestosMd> allPuestos = new ArrayList<PuestosMd>();

    private Listbox lb2;
    PersonalDal rg = new PersonalDal();
    private Include rootPagina;

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);

        allPersonal = rg.RSelect();
        allPuestos = rg.PuestosSelect();
        lb2.setModel(new ListModelList(allPersonal));
        pucPer.setModel(new ListModelList(allPuestos));
        empPer.focus();
    }

    //Selecionar nombres de una lista a campos de un combobox
    public void onSelect$lb2() throws SQLException {
        ArrayList<String> arraytemp = new ArrayList<String>();
        for (Object obj : lb2.getSelectedItem().getChildren()) {
            Listcell celda = (Listcell) obj;
            arraytemp.add(celda.getLabel());
        }
        codigo = arraytemp.get(0);
        nombre = arraytemp.get(1);
        puesto = arraytemp.get(2);

        empPer.setText(codigo);
        nomPer.setText(nombre);
        pucPer.setText(puesto);
        
        empPer.setDisabled(true);
        nomPer.setDisabled(true);
        pucPer.setDisabled(true);

    }//fin

    public void onChange$empPer(Event e) throws SQLException {

        if (empPer.getText().isEmpty()) {

            empPer.setText("");
            nomPer.setText("");
            pucPer.setText("");
        } else {
            for (PersonalMd dt : allPersonal) {
                if (dt.getCodigo().equals(empPer.getText())) {

                    empPer.setText(dt.getCodigo());
                    nomPer.setText(dt.getNombre());
                    pucPer.setText(dt.getPuesto());
                }
            }
        }
    }

    public void onClick$btnGuardar(Event e) throws SQLException {

        if (((empPer.getText().equals("")) && (nomPer.getText().equals("")) && ((pucPer.getText().equals(""))))) {

            Clients.showNotification("NO SE GUARDO EL REGISTRO <br/> DEBE LLENAR LOS CAMPOS VACIOS! <br/>",
                    "warning", null, "middle_center", 5000);
        } else {

            int op = 0;

            for (PersonalMd dt : allPersonal) {
                if (dt.getCodigo().equals(empPer.getText())) {
                    op++;
                }
            }

            if (op == 0) {
                rg.REGinsert(empPer.getText(), nomPer.getText().toUpperCase(), pucPer.getSelectedItem().getValue().toString());

                empPer.setText("");
                nomPer.setText("");
                pucPer.setText("");

                allPersonal = rg.RSelect();
                lb2.setModel(new ListModelList(allPersonal));

            } else {
                rg.REGupdate(empPer.getText(), nomPer.getText().toUpperCase(), pucPer.getSelectedItem().getValue().toString());

                empPer.setDisabled(false);
                empPer.setText("");
                pucPer.setText("");
                empPer.focus();

                allPersonal = rg.RSelect();
                lb2.setModel(new ListModelList(allPersonal));
            }
        }
    }

     public void onClick$btnNuevo(Event e) throws SQLException {
        empPer.setText("");
        nomPer.setText("");
        pucPer.setText("");
        
        empPer.setDisabled(false);
        nomPer.setDisabled(false);
        pucPer.setDisabled(false);
        
        empPer.focus();
    }
    
    public void onClick$btnSalir() {
        rootPagina.setSrc("/Views/Principal.zul");
    }
}
