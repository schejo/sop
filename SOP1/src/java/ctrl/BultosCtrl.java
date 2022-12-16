package ctrl;

import DAL.BultosDal;
import MD.BultosMd;
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

public class BultosCtrl extends GenericForwardComposer {

    private Textbox codBul;
    private Textbox nomBul;
    private Textbox nomBu2;

    List<BultosMd> allBultos = new ArrayList<BultosMd>();

    private Listbox lb2;
    BultosDal rg = new BultosDal();
    private Include rootPagina;

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        allBultos = rg.RSelect();
        lb2.setModel(new ListModelList(allBultos));
        codBul.focus();
        
    }

    //Selecionar nombres de una lista a campos de un combobox
    public void onSelect$lb2() throws SQLException {
        ArrayList<String> arraytemp = new ArrayList<String>();
        for (Object obj : lb2.getSelectedItem().getChildren()) {
            Listcell celda = (Listcell) obj;
            arraytemp.add(celda.getLabel());
        }
        String codigo = arraytemp.get(0);
        String bulto1 = arraytemp.get(1);
        String bulto2 = arraytemp.get(2);

        codBul.setText(codigo);
        nomBul.setText(bulto1);
        nomBu2.setText(bulto2);

        codBul.setDisabled(true);
        nomBul.setDisabled(true);
        nomBu2.setDisabled(true);
        
    }//fin

    public void onChange$codBul(Event e) throws SQLException {
        
        codBul.setDisabled(true);
        if (codBul.getText().isEmpty()) {

            codBul.setText("");
            nomBul.setText("");
            nomBu2.setText("");
        } else {
            for (BultosMd dt : allBultos) {
                if (dt.getCodigo().equals(codBul.getText().toUpperCase())) {

                    codBul.setText(dt.getCodigo());
                    nomBul.setText(dt.getNombre1());
                    nomBu2.setText(dt.getNombre2());
                }
            }
        }
    }

    public void onClick$btnGuardar(Event e) throws SQLException {

        if (((codBul.getText().equals("")) && (nomBul.getText().equals("")) && ((nomBu2.getText().equals(""))))) {

            Clients.showNotification("NO SE GUARDO EL REGISTRO <br/> DEBE LLENAR LOS CAMPOS VACIOS! <br/>",
                    "warning", null, "middle_center", 5000);
        } else {

            int op = 0;

            for (BultosMd dt : allBultos) {
                if (dt.getCodigo().equals(codBul.getText().toUpperCase())) {
                    op++;
                }
            }

            if (op == 0) {
                rg.REGinsert(codBul.getText().toUpperCase(), nomBul.getText().toUpperCase(), nomBu2.getText().toUpperCase());

                codBul.setText("");
                nomBul.setText("");
                nomBu2.setText("");

                allBultos = rg.RSelect();
                lb2.setModel(new ListModelList(allBultos));
            } else {
                rg.REGupdate(codBul.getText().toUpperCase(), nomBul.getText().toUpperCase(), nomBu2.getText().toUpperCase());
                codBul.setDisabled(false);
                codBul.setText("");
                nomBul.setText("");
                nomBu2.setText("");
                codBul.focus();

                allBultos = rg.RSelect();
                lb2.setModel(new ListModelList(allBultos));
            }
        }
    }

    public void onClick$btnNuevo(Event e) throws SQLException {
        codBul.setText("");
        nomBul.setText("");
        nomBu2.setText("");
        codBul.focus();
        
        codBul.setDisabled(false);
        nomBul.setDisabled(false);
        nomBu2.setDisabled(false);

    }

    public void onClick$btnSalir() {
        rootPagina.setSrc("/Views/Principal.zul");
    }
}
