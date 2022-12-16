package ctrl;

import DAL.ParticularesDal;
import MD.ParticularesMd;
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

public class ParticularesCtrl extends GenericForwardComposer {

    private Textbox tipPar;
    private Textbox desPar;

    List<ParticularesMd> allParticulares = new ArrayList<ParticularesMd>();

    private Listbox lb2;
    ParticularesDal rg = new ParticularesDal();
    private Include rootPagina;

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);

        allParticulares = rg.RSelect();
        lb2.setModel(new ListModelList(allParticulares));
        tipPar.focus();
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

        tipPar.setText(codigo);
        desPar.setText(nombre);

        tipPar.setDisabled(true);
        desPar.setDisabled(true);

    }//fin

    public void onChange$tipPar(Event e) throws SQLException {
        if (tipPar.getText().isEmpty()) {

            tipPar.setText("");
            desPar.setText("");

        } else {
            for (ParticularesMd dt : allParticulares) {
                if (dt.getTipo().equals(tipPar.getText().toUpperCase())) {

                    tipPar.setText(dt.getTipo());
                    desPar.setText(dt.getDescripcion());
                }
            }
        }
    }

    public void onClick$btnGuardar(Event e) throws SQLException {

        if (((tipPar.getText().equals("")) && (desPar.getText().equals("")))) {

            Clients.showNotification("NO SE GUARDO EL REGISTRO <br/> DEBE LLENAR LOS CAMPOS VACIOS! <br/>",
                    "warning", null, "middle_center", 5000);
        } else {

            int op = 0;

            for (ParticularesMd dt : allParticulares) {
                if (dt.getTipo().equals(tipPar.getText().toUpperCase())) {
                    op++;
                }
            }

            if (op == 0) {
                rg.REGinsert(tipPar.getText().toUpperCase(), desPar.getText());

                tipPar.setText("");
                desPar.setText("");

                allParticulares = rg.RSelect();
                lb2.setModel(new ListModelList(allParticulares));
            } else {
                rg.REGupdate(tipPar.getText(), desPar.getText());
                tipPar.setText("");
                desPar.setText("");
                tipPar.focus();

                allParticulares = rg.RSelect();
                lb2.setModel(new ListModelList(allParticulares));
            }
        }
    }

    public void onClick$btnNuevo(Event e) throws SQLException {
        tipPar.setText("");
        desPar.setText("");

        tipPar.setDisabled(false);
        desPar.setDisabled(false);

        tipPar.focus();

    }

    public void onClick$btnSalir() {
        rootPagina.setSrc("/Views/Principal.zul");
    }
}
