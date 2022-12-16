/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ctrl;

import DAL.ManteActiviDal;
import MD.ManteActiviMd;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Include;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Textbox;

/**
 *
 * @author Informatica
 */
public class ManteActiviCtrl extends GenericForwardComposer {

    private Textbox anoarriboAct;
    private Textbox numarriboAct;
    private Textbox nom_buque;
    private Textbox cod_buque;
    private Combobox nombreAct;
    private Combobox atracadero;
    private Datebox fecha;
    //private Datebox hora;

    private Textbox caPopa;
    private Textbox caMedio;
    private Textbox caPro;
    private Include rootPagina;

    ManteActiviMd allMantenimiento2 = new ManteActiviMd();
    ManteActiviDal rg = new ManteActiviDal();
    List<ManteActiviMd> alltipoact = new ArrayList<ManteActiviMd>();
    List<ManteActiviMd> allatracadero = new ArrayList<ManteActiviMd>();

    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        alltipoact = rg.tipoactRSelect();
        nombreAct.setModel(new ListModelList(alltipoact));
        allatracadero = rg.atracaderoRSelect();
        atracadero.setModel(new ListModelList(allatracadero));

        anoarriboAct.focus();

    }

    public void onClick$btnGuardar(Event e) throws SQLException, ClassNotFoundException, ParseException {
        int op1 = 0;

        allMantenimiento2 = new ManteActiviMd();

        int op = 0;
//        if (nombreAct.getText().trim().equals("")) {
//            op = 1;
//        }
        if (nombreAct.getSelectedIndex() == -1) {
            op = 1;
        }

        // si el arribo no existe que haga in insert
        if (op1 == 0) {

            //aqui se pone lo que se va a guardar
            allMantenimiento2.setNumAct(nombreAct.getSelectedItem().getValue().toString().toUpperCase());
            allMantenimiento2.setAno_arribo(anoarriboAct.getText().toUpperCase());
            allMantenimiento2.setNum_arribo(numarriboAct.getText().toUpperCase());
            allMantenimiento2.setFecha(fecha.getText().toUpperCase());
            // allMantenimiento2.setHora(hora.getText().toUpperCase());
            allMantenimiento2.setCaPro(caPro.getText().toUpperCase());
            allMantenimiento2.setCaMedio(caMedio.getText().toUpperCase());
            allMantenimiento2.setCaPopa(caPopa.getText().toUpperCase());
            allMantenimiento2.setNumAtra(atracadero.getSelectedItem().getValue().toString().toUpperCase());
            allMantenimiento2.setUsuario(desktop.getSession().getAttribute("USER").toString());

            allMantenimiento2 = rg.savePro(allMantenimiento2);

        }
        if (allMantenimiento2.getResp().equals("1")) {
            clear();
            Clients.showNotification(allMantenimiento2.getMsg() + "<br/>" + " mensaje",
                    Clients.NOTIFICATION_TYPE_INFO, null, "middle_center", 0);
        } else {
            Clients.showNotification(allMantenimiento2.getMsg() + "<br/>",
                    Clients.NOTIFICATION_TYPE_WARNING, null, "middle_center", 0);
        }

    }

    public void clear() {

        anoarriboAct.setDisabled(false);
        anoarriboAct.setText("");
        numarriboAct.setText("");
        nom_buque.setText("");
        cod_buque.setText("");
        nombreAct.setText("");
        atracadero.setText("");
        fecha.setText("");
        caPopa.setText("");
        caMedio.setText("");
        caPro.setText("");

    }

    public void onChange$numarriboAct(Event e) throws SQLException {

        allMantenimiento2 = new ManteActiviMd();
        anoarriboAct.setDisabled(true);
        allMantenimiento2 = rg.Mostrardatos(anoarriboAct.getText(), numarriboAct.getText());

        if (allMantenimiento2.getResp().equals("1")) {

            cod_buque.setText(allMantenimiento2.getNumero_buque());
            nom_buque.setText(allMantenimiento2.getNombre_buque());

        }

    }

    public void onClick$btnSalir() {
        rootPagina.setSrc("/Views/Principal.zul");
    }

    public void onClick$btnNuevo() {
        clear();
    }

}
