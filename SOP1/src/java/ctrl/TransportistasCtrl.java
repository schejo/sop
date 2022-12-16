package ctrl;

import DAL.TransportistasDal;
import MD.TransportistasMd;
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

public class TransportistasCtrl extends GenericForwardComposer {

    private Textbox numTra;
    private Textbox nomTra;
    private Textbox telTra;

    List<TransportistasMd> allTransportistas = new ArrayList<TransportistasMd>();

    private Listbox lb2;
    TransportistasDal rg = new TransportistasDal();
    private Include rootPagina;

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        allTransportistas = rg.RSelect();
        lb2.setModel(new ListModelList(allTransportistas));
        numTra.focus();
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
        String telefono = arraytemp.get(2);

        numTra.setText(codigo);
        nomTra.setText(nombre);
        telTra.setText(telefono);

        numTra.setDisabled(true);
        nomTra.setDisabled(true);
        telTra.setDisabled(true);

    }//fin

    public void onChange$numTra(Event e) throws SQLException {
        
        numTra.setDisabled(true);
        if (numTra.getText().isEmpty()) {

            numTra.setText("");
            nomTra.setText("");
            telTra.setText("");
            
        } else {
            for (TransportistasMd dt : allTransportistas) {
                if (dt.getNumero().equals(numTra.getText().toUpperCase())) {

                    numTra.setText(dt.getNumero());
                    nomTra.setText(dt.getNombre());
                    telTra.setText(dt.getTelefono());
                }
            }
        }
    }

    public void onClick$btnGuardar(Event e) throws SQLException {

        if (((numTra.getText().equals("")) && (nomTra.getText().equals("")) && ((telTra.getText().equals("")))
                || ((!numTra.getText().equals("")) || (nomTra.getText().equals("")) || (telTra.getText().equals("")))
                || ((numTra.getText().equals("")) || (nomTra.getText().equals("")) || (!telTra.getText().equals("")))
                || ((!numTra.getText().equals("")) || (!nomTra.getText().equals("")) || (telTra.getText().equals(""))))) {

            Clients.showNotification("NO SE GUARDO EL REGISTRO <br/> DEBE LLENAR LOS CAMPOS VACIOS! <br/>",
                    "warning", null, "middle_center", 5000);
        } else {

            int op = 0;

            for (TransportistasMd dt : allTransportistas) {
                if (dt.getNumero().equals(numTra.getText())) {
                    op++;
                }
            }

            if (op == 0) {
                rg.REGinsert(numTra.getText(), nomTra.getText(), telTra.getText());

                numTra.setText("");
                nomTra.setText("");
                telTra.setText("");

                allTransportistas = rg.RSelect();
                lb2.setModel(new ListModelList(allTransportistas));
            } else {
                rg.REGupdate(numTra.getText(), nomTra.getText(), telTra.getText());

                numTra.setText("");
                nomTra.setText("");
                telTra.setText("");
                numTra.focus();

            }

        }
    }

    public void onClick$btnNuevo(Event e) throws SQLException {
        numTra.setText("");
        nomTra.setText("");
        telTra.setText("");

        numTra.setDisabled(false);
        nomTra.setDisabled(false);
        telTra.setDisabled(false);

        numTra.focus();

    }

    public void onClick$btnSalir() {
        rootPagina.setSrc("/Views/Principal.zul");
    }
}
