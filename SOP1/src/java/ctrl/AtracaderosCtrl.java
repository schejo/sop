package ctrl;

import DAL.AtracaderosDal;
import MD.AtracaderosMd;
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

public class AtracaderosCtrl extends GenericForwardComposer {

    private Textbox numAtr;
    private Textbox nomAtr;
    private Textbox iniAtr;
    private Textbox finAtr;
    private Textbox proAtr;
    private Textbox intAtr;
    private Textbox mueAtr;
    private Combobox estAtr;
    private Combobox fueAtr;

    String lb;
    String tipo_muelle, pk_inicial, pk_final, profundidad, intervalo,
            muelle, estatus, zona_abrigo;

    List<AtracaderosMd> allAtracaderos = new ArrayList<AtracaderosMd>();

    private Listbox lb2;
    AtracaderosDal rg = new AtracaderosDal();
    private Include rootPagina;

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        allAtracaderos = rg.RSelect();
        lb2.setModel(new ListModelList(allAtracaderos));
        numAtr.focus();

        numAtr.setDisabled(true);
    }

    //Selecionar nombres de una lista a campos de un combobox
    public void onSelect$lb2() throws SQLException {
        ArrayList<String> arraytemp = new ArrayList<String>();
        for (Object obj : lb2.getSelectedItem().getChildren()) {
            Listcell celda = (Listcell) obj;
            arraytemp.add(celda.getLabel());
        }
        lb = arraytemp.get(0);
        tipo_muelle = arraytemp.get(1);
        pk_inicial = arraytemp.get(2);
        pk_final = arraytemp.get(3);
        profundidad = arraytemp.get(4);
        intervalo = arraytemp.get(5);
        muelle = arraytemp.get(6);
        estatus = arraytemp.get(7);
        zona_abrigo = arraytemp.get(8);

        numAtr.setText(lb);
        nomAtr.setText(tipo_muelle);
        iniAtr.setText(pk_inicial);
        finAtr.setText(pk_final);
        proAtr.setText(profundidad);
        intAtr.setText(intervalo);
        mueAtr.setText(muelle);
        estAtr.setText(estatus);
        fueAtr.setText(zona_abrigo);

        numAtr.setDisabled(true);
        nomAtr.setDisabled(true);
        iniAtr.setDisabled(true);
        finAtr.setDisabled(true);
        proAtr.setDisabled(true);
        intAtr.setDisabled(true);
        mueAtr.setDisabled(true);
        estAtr.setDisabled(true);
        fueAtr.setDisabled(true);

    }

    public void onChange$numAtr(Event e) throws SQLException {

        if (numAtr.getText().isEmpty()) {

            numAtr.setText("");
            nomAtr.setText("");
            iniAtr.setText("");
            finAtr.setText("");
            proAtr.setText("");
            intAtr.setText("");
            estAtr.setText("");
            mueAtr.setText("");
            fueAtr.setText("");
        } else {
            for (AtracaderosMd dt : allAtracaderos) {
                if (dt.getAtracadero().equals(numAtr.getText().toUpperCase())) {
                    numAtr.setText(dt.getAtracadero());
                    nomAtr.setText(dt.getTerminal());
                    iniAtr.setText(dt.getPk_inicial());
                    finAtr.setText(dt.getPk_final());
                    proAtr.setText(dt.getProfundidad());
                    intAtr.setText(dt.getIntervalo());
                    mueAtr.setText(dt.getMuelle());
                    estAtr.setText(dt.getEstatus());
                    fueAtr.setText(dt.getFuera_abrigo());
                }
            }
        }
    }

    public void onClick$btnGuardar(Event e) throws SQLException {
        int op1 = 0;
        if (op1 == 1) {
            Clients.showNotification("NO SE GUARDO EL REGISTRO <br/> DEBE LLENAR LOS CAMPOS VACIOS! <br/>",
                    "warning", null, "middle_center", 5000);
        } else {

            int op = 0;

            for (AtracaderosMd dt : allAtracaderos) {
                if (dt.getAtracadero().equals(numAtr.getText().toUpperCase())) {
                    op++;
                }
            }

            if (op == 0) {
                rg.REGinsert(nomAtr.getText(), iniAtr.getText(), finAtr.getText(), proAtr.getText(), intAtr.getText(), mueAtr.getText(), estAtr.getSelectedItem().getValue().toString(), fueAtr.getSelectedItem().getValue().toString());

                numAtr.setText("");
                nomAtr.setText("");
                iniAtr.setText("");
                finAtr.setText("");
                proAtr.setText("");
                intAtr.setText("");
                estAtr.setText("");
                mueAtr.setText("");
                fueAtr.setText("");

                allAtracaderos = rg.RSelect();
                lb2.setModel(new ListModelList(allAtracaderos));
            } else {
                Clients.showNotification("No se Puedo Modificar<br/> Comunicarse con Informatica",
                        Clients.NOTIFICATION_TYPE_WARNING, null, "middle_center", 3000);
                //  rg.REGupdate(numAtr.getText().toUpperCase(), nomAtr.getText(), iniAtr.getText(), finAtr.getText(), proAtr.getText(), intAtr.getText(), mueAtr.getText(), estAtr.getSelectedItem().getValue().toString(), fueAtr.getSelectedItem().getValue().toString());
                numAtr.setDisabled(false);
                numAtr.setText("");
                nomAtr.setText("");
                iniAtr.setText("");
                finAtr.setText("");
                proAtr.setText("");
                intAtr.setText("");
                estAtr.setText("");
                mueAtr.setText("");
                fueAtr.setText("");
                numAtr.focus();
                allAtracaderos = rg.RSelect();
                lb2.setModel(new ListModelList(allAtracaderos));
            }

        }
    }

    public void onClick$btnNuevo(Event e) throws SQLException {
        numAtr.setText("");
        nomAtr.setText("");
        iniAtr.setText("");
        finAtr.setText("");
        proAtr.setText("");
        intAtr.setText("");
        estAtr.setText("");
        mueAtr.setText("");
        fueAtr.setText("");
        nomAtr.focus();

        numAtr.setDisabled(false);
        nomAtr.setDisabled(false);
        iniAtr.setDisabled(false);
        finAtr.setDisabled(false);
        proAtr.setDisabled(false);
        intAtr.setDisabled(false);
        mueAtr.setDisabled(false);
        estAtr.setDisabled(false);
        fueAtr.setDisabled(false);
    }

    public void onClick$btnSalir() {
        rootPagina.setSrc("/Views/Principal.zul");
    }
}
