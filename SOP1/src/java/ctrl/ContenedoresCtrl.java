package ctrl;

import DAL.ContenedoresDal;
import MD.ContenedoresMd;
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

public class ContenedoresCtrl extends GenericForwardComposer {

    private Textbox tipCon;
    private Textbox desCon;
    private Textbox canCon;
    List<ContenedoresMd> allContenedores = new ArrayList<ContenedoresMd>();

    private Listbox lb2;
    ContenedoresDal rg = new ContenedoresDal();
    private Include rootPagina;

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        allContenedores = rg.RSelect();
        lb2.setModel(new ListModelList(allContenedores));
        tipCon.focus();
    }

    //Selecionar nombres de una lista a campos de un combobox
    public void onSelect$lb2() throws SQLException {
        ArrayList<String> arraytemp = new ArrayList<String>();
        for (Object obj : lb2.getSelectedItem().getChildren()) {
            Listcell celda = (Listcell) obj;
            arraytemp.add(celda.getLabel());
        }
        String tipo = arraytemp.get(0);
        String descripcion = arraytemp.get(1);
        String cantidad = arraytemp.get(2);

        tipCon.setText(tipo);
        desCon.setText(descripcion);
        canCon.setText(cantidad);

        tipCon.setDisabled(true);
        desCon.setDisabled(true);
        canCon.setDisabled(true);
    }//fin

    public void onChange$tipCon(Event e) throws SQLException {
        if (tipCon.getText().isEmpty()) {

            tipCon.setText("");
            desCon.setText("");
            canCon.setText("");
        } else {
            for (ContenedoresMd dt : allContenedores) {
                if (dt.getTipo().equals(tipCon.getText().toUpperCase())) {

                    tipCon.setText(dt.getTipo().toUpperCase());
                    desCon.setText(dt.getDescripcion().toUpperCase());
                    canCon.setText(dt.getCantidad());
                }
            }
        }
    }

    public void onClick$btnGuardar(Event e) throws SQLException {

        if (((tipCon.getText().equals("")) && (desCon.getText().equals("")) && ((canCon.getText().equals(""))))) {

            Clients.showNotification("NO SE GUARDO EL REGISTRO <br/> DEBE LLENAR LOS CAMPOS VACIOS! <br/>",
                    "warning", null, "middle_center", 5000);
        } else {

            int op = 0;

            for (ContenedoresMd dt : allContenedores) {
                if (dt.getTipo().equals(tipCon.getText().toUpperCase())) {
                    op++;
                }
            }

            if (op == 0) {
                rg.REGinsert(tipCon.getText().toUpperCase(), desCon.getText().toUpperCase(), canCon.getText());

                tipCon.setText("");
                desCon.setText("");
                canCon.setText("");

                allContenedores = rg.RSelect();
                lb2.setModel(new ListModelList(allContenedores));
            } else {
                rg.REGupdate(tipCon.getText().toUpperCase(), desCon.getText().toUpperCase(), canCon.getText());
                tipCon.setDisabled(false);
                tipCon.setText("");
                desCon.setText("");
                canCon.setText("");
                tipCon.focus();
                allContenedores = rg.RSelect();
                lb2.setModel(new ListModelList(allContenedores));
            }
        }
    }

    public void onClick$btnNuevo(Event e) throws SQLException {
        tipCon.setText("");
        desCon.setText("");
        canCon.setText("");
        tipCon.focus();
        
        tipCon.setDisabled(false);
        desCon.setDisabled(false);
        canCon.setDisabled(false);
    }

    public void onClick$btnSalir() {
        rootPagina.setSrc("/Views/Principal.zul");
    }
}
