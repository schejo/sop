package ctrl;

import DAL.ConfirmaMuelleDal;
import MD.ConfirmaMuelleMd;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;

public class ConfirmaMuelleCtrl extends GenericForwardComposer {

    private Textbox contene;
    private Textbox anoArribo;
    private Textbox numArribo;
    private Textbox tipo;
    private Textbox estado;
    private Textbox ubicacion;
    private Textbox tara;
    private Textbox msat;
    private Textbox morigen;
    private Combobox actividad;
    private Combobox patio;
    private Combobox via;
    private Combobox equipo;
    private Label rojo;
    private Label verde;
    private Button btnGuardar;
    private String tipoV, estadoV, correlaBl, anioManif, puerto;
    private String manif, nombreB, vacioLleno, numContene;

    private String fech, hor;
    //private Include rootPagina;

    ConfirmaMuelleDal cp = new ConfirmaMuelleDal();
    List<ConfirmaMuelleMd> alldata = new ArrayList<ConfirmaMuelleMd>();

    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
    }

    public void onChange$numArribo(Event e) {
        anoArribo.setDisabled(true);
        numArribo.setDisabled(true);
    }

    public void onClick$btnNuevo(Event e) {
        contene.setText("");
        tipo.setText("");
        ubicacion.setText("");
        estado.setText("");
        tara.setText("");
        msat.setText("");
        morigen.setText("");
        actividad.setSelectedIndex(-1);
        patio.setSelectedIndex(-1);
        via.setSelectedIndex(-1);
        equipo.setSelectedIndex(-1);
        tipo.setDisabled(false);
        estado.setDisabled(false);
        //rojo.setVisible(false);
        //verde.setVisible(false);
        //btnGuardar.setDisabled(false);
    }

    public void onChange$contene(Event e) {
        Date date = new Date();
        //Caso 1: obtener la hora y salida por pantalla con formato:
        DateFormat hourFormat = new SimpleDateFormat("HH:mm:ss");
        hor = hourFormat.format(date);
        System.out.println("Hora: " + hor);
        //Caso 2: obtener la fecha y salida por pantalla con formato:
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        fech = dateFormat.format(date);
        System.out.println("Fecha: " + fech);

        tipoV = "";
        estadoV = "";
        correlaBl = "";
        anioManif = "";
        puerto = "";
        nombreB = "";
        vacioLleno = "";
        numContene = "";
        manif = "";
        System.out.println("CONTENEDOR..: " + contene.getText());
        //Clients.showNotification("NO TIENE DATOS..!"+contene.getText());
        //Clients.showNotification("NO TIENE DATOS..!"+contene.getText());
        alldata = cp.VacioLleno(anoArribo.getText(), numArribo.getText(), contene.getText().toUpperCase());
        if (alldata.isEmpty()) {
            
            Clients.showNotification("NO TIENE DATOS <br/>",
                    "warning", null, "middle_center", 5000);
        } else {
            for (ConfirmaMuelleMd item : alldata) {
                anioManif = item.getAnio();
                tipoV = item.getTipo();
                tipo.setText(tipoV);
                estadoV = item.getEstado();
                estado.setText(estadoV);
                correlaBl = item.getCorrelaBl();
                puerto = item.getPuerto();
                nombreB = item.getNombre();
                vacioLleno = item.getEstado();
                numContene = item.getContenedor();
                manif = item.getManif();
                contene.setText(numContene);
                tipo.setDisabled(true);
                estado.setDisabled(true);
                actividad.setFocus(true);
            }
        }
    }

    public void onClick$btnGuardar(Event e) {
        String calificaManif = "N";
        String fecha1 = fech + " " + hor;
        String user = desktop.getSession().getAttribute("USER").toString();
        //TEXTBOX
        System.out.println("AÑO.: " + anoArribo.getText() + " NUMERO.: " + numArribo.getText());
        System.out.println("CONTENEDOR.: " + contene.getText() + " TIPO.: " + tipo.getText());
        System.out.println("UBICACION.: " + ubicacion.getText() + " ESTADO.: " + estado.getText());
        System.out.println("TARA.: " + tara.getText() + " MARCAHMO SAT.: " + msat.getText());
        System.out.println("MARCHAMO ORIGEN.: " + morigen.getText());

        //MENU SELECTIVO
//        System.out.println("ACIVIDAD.: "+actividad.getSelectedItem().getValue().toString());
//        System.out.println("PATIO.: "+patio.getSelectedItem().getValue().toString());
//        System.out.println("VIA.: "+via.getSelectedItem().getValue().toString());
//        System.out.println("EQUIPO.: "+equipo.getSelectedItem().getValue().toString());
        if (anoArribo.getText().equals("") || numArribo.getText().equals("") || contene.getText().equals("")
                || tipo.getText().equals("") || ubicacion.getText().equals("") || estado.getText().equals("")
                || tara.getText().isEmpty() || msat.getText().isEmpty() || morigen.getText().equals("")
                || actividad.getSelectedItem().getValue().toString().isEmpty() || patio.getSelectedItem().getValue().toString().isEmpty()
                || via.getSelectedItem().getValue().toString().isEmpty() || equipo.getSelectedItem().getValue().toString().isEmpty()) {
            
            Clients.showNotification("NO SE GUARDO EL REGISTRO <br/> DEBE LLENAR LOS CAMPOS VACIOS! <br/>",
                    "warning", null, "middle_center", 5000);
        } else {
            cp.Guardar(contene.getText(), fecha1, actividad.getSelectedItem().getValue().toString(), tara.getText(), msat.getText(),
                    morigen.getText(), anoArribo.getText(), numArribo.getText(), anioManif, correlaBl,
                    vacioLleno, equipo.getSelectedItem().getValue().toString(), nombreB, calificaManif, puerto,
                    ubicacion.getText(), via.getSelectedItem().getValue().toString(), user, manif);
        }

    }

}
