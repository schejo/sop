package ctrl;

import DAL.TipoCargaDal;
import MD.TipoCargaMd;
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

public class TipoCargaCtrl extends GenericForwardComposer {

    private Textbox tipCar;
    private Textbox noeCar;
    private Textbox noiCar;
    private Textbox claCar;
    List<TipoCargaMd> allTipoCarga = new ArrayList<TipoCargaMd>();

    private Listbox lb2;
    TipoCargaDal rg = new TipoCargaDal();
    private Include rootPagina;

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        allTipoCarga = rg.RSelect();
        lb2.setModel(new ListModelList(allTipoCarga));
        tipCar.focus();
    }

    //Selecionar nombres de una lista a campos de un combobox
    public void onSelect$lb2() throws SQLException {
        ArrayList<String> arraytemp = new ArrayList<String>();
        for (Object obj : lb2.getSelectedItem().getChildren()) {
            Listcell celda = (Listcell) obj;
            arraytemp.add(celda.getLabel());
        }
        String tipo = arraytemp.get(0);
        String español = arraytemp.get(1);
        String ingles = arraytemp.get(2);
        String clasificacion = arraytemp.get(3);

        tipCar.setText(tipo);
        noeCar.setText(español);
        noiCar.setText(ingles);
        claCar.setText(clasificacion);
        
        tipCar.setDisabled(true);
        noeCar.setDisabled(true);
        noiCar.setDisabled(true);
        claCar.setDisabled(true);

    }//fin

    public void onChange$tipCar(Event e) throws SQLException {
        if (tipCar.getText().isEmpty()) {

            tipCar.setText("");
            noeCar.setText("");
            noiCar.setText("");
            claCar.setText("");

        } else {
            for (TipoCargaMd dt : allTipoCarga) {
                if (dt.getTipo().equals(tipCar.getText())) {

                    tipCar.setText(dt.getTipo());
                    noeCar.setText(dt.getEspanol());
                    noiCar.setText(dt.getIngles());
                    claCar.setText(dt.getClasificacion());

                }
            }
        }
    }

    public void onClick$btnGuardar(Event e) throws SQLException {

        if (((tipCar.getText().equals("")) && (noeCar.getText().equals("")) && ((noiCar.getText().equals("")) && (claCar.getText().equals("")))
                || ((!tipCar.getText().equals("")) || (noeCar.getText().equals("")) || (noiCar.getText().equals("")) || (claCar.getText().equals("")))
                || ((tipCar.getText().equals("")) || (!noeCar.getText().equals("")) || (noiCar.getText().equals("")) || (claCar.getText().equals("")))
                || ((tipCar.getText().equals("")) || (noeCar.getText().equals("")) || (!noiCar.getText().equals("")) || (claCar.getText().equals("")))
                || ((!tipCar.getText().equals("")) || (!noeCar.getText().equals("")) || (noiCar.getText().equals("")) || (!claCar.getText().equals(""))))) {

            Clients.showNotification("NO SE GUARDO EL REGISTRO <br/> DEBE LLENAR LOS CAMPOS VACIOS! <br/>",
                    "warning", null, "middle_center", 5000);
        } else {
            int op = 0;

            for (TipoCargaMd dt : allTipoCarga) {
                if (dt.getTipo().equals(tipCar.getText())) {
                    op++;
                }
            }

            if (op == 0) {
                rg.REGinsert(tipCar.getText(), noeCar.getText(), noiCar.getText(), claCar.getText());

                tipCar.setText("");
                noeCar.setText("");
                noiCar.setText("");
                claCar.setText("");

                allTipoCarga = rg.RSelect();
                lb2.setModel(new ListModelList(allTipoCarga));
            } else {
                rg.REGupdate(tipCar.getText(), noeCar.getText(), noiCar.getText(), claCar.getText());
                tipCar.setText("");
                noeCar.setText("");
                noiCar.setText("");
                claCar.setText("");
                tipCar.focus();
                allTipoCarga = rg.RSelect();
                lb2.setModel(new ListModelList(allTipoCarga));
            }
        }
    }

    public void onClick$btnNuevo(Event e) throws SQLException {
        
        tipCar.setText("");
        noeCar.setText("");
        noiCar.setText("");
        claCar.setText("");

        tipCar.setDisabled(false);
        noeCar.setDisabled(false);
        noiCar.setDisabled(false);
        claCar.setDisabled(false);
        
        tipCar.focus();

    }

    public void onClick$btnSalir() {
        rootPagina.setSrc("/Views/Principal.zul");
    }
}
