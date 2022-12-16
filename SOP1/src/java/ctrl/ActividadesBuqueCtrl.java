package ctrl;

import DAL.ActividadesBuqueDal;
import MD.ActividadesBuqueMd;
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

public class ActividadesBuqueCtrl extends GenericForwardComposer {

    private Textbox numAct;
    private Textbox nomAct;
    private Textbox durAct;
    private Textbox nucAct;
    private Combobox lleAct;
    private Combobox praAct;
    private Combobox remAct;
    private Combobox calAct;

    String lb;
    String nombre, duracion, calculo, atracadero, practico, remolcador, calado;

    List<ActividadesBuqueMd> allActividadesBuque = new ArrayList<ActividadesBuqueMd>();

    private Listbox lb2;
    ActividadesBuqueDal rg = new ActividadesBuqueDal();
    private Include rootPagina;

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        numAct.setDisabled(false);
        allActividadesBuque = rg.RSelect();
        lb2.setModel(new ListModelList(allActividadesBuque));
        numAct.focus();
    }

    //Selecionar nombres de una lista a campos de un combobox
    public void onSelect$lb2() throws SQLException {
        ArrayList<String> arraytemp = new ArrayList<String>();
        for (Object obj : lb2.getSelectedItem().getChildren()) {
            Listcell celda = (Listcell) obj;
            arraytemp.add(celda.getLabel());
        }
        lb = arraytemp.get(0);
        nombre = arraytemp.get(1);
        duracion = arraytemp.get(2);
        calculo = arraytemp.get(3);
        atracadero = arraytemp.get(4);
        practico = arraytemp.get(5);
        remolcador = arraytemp.get(6);
        calado = arraytemp.get(7);

        numAct.setText(lb);
        nomAct.setText(nombre);
        durAct.setText(duracion);
        nucAct.setText(calculo);
        lleAct.setText(atracadero);
        praAct.setText(practico);
        remAct.setText(remolcador);
        calAct.setText(calado);

        numAct.setDisabled(true);
        nomAct.setDisabled(true);
        durAct.setDisabled(true);
        nucAct.setDisabled(true);
        lleAct.setDisabled(true);
        praAct.setDisabled(true);
        remAct.setDisabled(true);
        calAct.setDisabled(true);

    }

    public void onClick$btnNuevo(Event e) throws SQLException {
        numAct.setDisabled(true);
        numAct.setText("");
        nomAct.setText("");
        durAct.setText("");
        nucAct.setText("");
        lleAct.setText("");
        praAct.setText("");
        remAct.setText("");
        calAct.setText("");

        numAct.setDisabled(false);
        nomAct.setDisabled(false);
        durAct.setDisabled(false);
        nucAct.setDisabled(false);
        lleAct.setDisabled(false);
        praAct.setDisabled(false);
        remAct.setDisabled(false);
        calAct.setDisabled(false);
        nomAct.focus();
    }

    public void onChange$numAct(Event e) throws SQLException {

        if (numAct.getText().isEmpty()) {

            // numAct.setText("");
            nomAct.setText("");
            durAct.setText("");
            nucAct.setText("");
            lleAct.setText("");
            praAct.setText("");
            remAct.setText("");
            calAct.setText("");

        } else {
            for (ActividadesBuqueMd dt : allActividadesBuque) {
                if (dt.getNum_actividad1().equals(numAct.getText().toUpperCase())) {
                    
                    numAct.setText(dt.getNum_actividad1());
                    nomAct.setText(dt.getNom_actividad());
                    durAct.setText(dt.getNombre_duracion());
                    nucAct.setText(dt.getNum_activ_calculo());
                    lleAct.setText(dt.getLleva_atracadero());
                    praAct.setText(dt.getLleva_practico());
                    remAct.setText(dt.getLleva_remolcador());
                    calAct.setText(dt.getLleva_calado());
                }
            }
        }
    }

    public void onClick$btnGuardar(Event e) throws SQLException {

        if (((numAct.getText().equals("")) && (nomAct.getText().equals("")) && (durAct.getText().equals("")) && (nucAct.getText().equals(""))
                && (lleAct.getText().equals("")) && (praAct.getText().equals("")) && (remAct.getText().equals("")) && (calAct.getText().equals("")))) {

            Clients.showNotification("NO SE GUARDO EL REGISTRO <br/> DEBE LLENAR LOS CAMPOS VACIOS! <br/>",
                    "warning", null, "middle_center", 5000);
        } else {

            int op = 0;

            for (ActividadesBuqueMd dt : allActividadesBuque) {
                if (dt.getNum_actividad1().equals(numAct.getText().toUpperCase())) {
                    op++;
                }
            }

            if (op == 0) {
                rg.REGinsert(numAct.getText().toUpperCase(), nomAct.getText(), durAct.getText(), nucAct.getText(),
                        lleAct.getSelectedItem().getValue().toString(), praAct.getSelectedItem().getValue().toString(),
                        remAct.getSelectedItem().getValue().toString(), calAct.getSelectedItem().getValue().toString());

                numAct.setText("");
                nomAct.setText("");
                durAct.setText("");
                nucAct.setText("");
                lleAct.setText("");
                praAct.setText("");
                remAct.setText("");
                calAct.setText("");

                allActividadesBuque = rg.RSelect();
                lb2.setModel(new ListModelList(allActividadesBuque));
            } else {
                rg.REGupdate(numAct.getText().toUpperCase(), nomAct.getText(), durAct.getText(), nucAct.getText(),
                        lleAct.getSelectedItem().getValue().toString(), praAct.getSelectedItem().getValue().toString(),
                        remAct.getSelectedItem().getValue().toString(), calAct.getSelectedItem().getValue().toString());

                numAct.setText("");
                nomAct.setText("");
                durAct.setText("");
                nucAct.setText("");
                lleAct.setText("");
                praAct.setText("");
                remAct.setText("");
                calAct.setText("");
                numAct.focus();

                allActividadesBuque = rg.RSelect();
                lb2.setModel(new ListModelList(allActividadesBuque));
            }

        }
    }

    public void onClick$btnSalir() {
        rootPagina.setSrc("/Views/Principal.zul");
    }
}
