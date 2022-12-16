/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ctrl;

import DAL.ConfirmaPatiosDal;
import MD.ConfirmaPatiosMd;
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
import org.zkoss.zul.Include;
import org.zkoss.zul.Textbox;


public class ConfirmaPatiosGCtrl extends GenericForwardComposer {

    private Textbox contene;
    private Textbox anoArribo;
    private Textbox numArribo;
    private Textbox ubica2;
    private Textbox fecha;
    private Textbox hora;
    private Combobox ubica;
    private Combobox equipo;
//    private Label rojo;
//    private Label verde;
    private Button btnGuardar;
    private String vLleno;
    private String fech, hor;
    private Include rootPagina;
    
    ConfirmaPatiosDal cp = new ConfirmaPatiosDal();
    List<ConfirmaPatiosMd> alldata = new ArrayList<ConfirmaPatiosMd>();

    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);

        fecha.setDisabled(true);
        hora.setDisabled(true);
//        rojo.setVisible(false);
//        verde.setVisible(false);
    }
    
    public void onClick$btnNuevo(Event e) {
//        anoArribo.setDisabled(false);
//        numArribo.setDisabled(false);
//        anoArribo.setText("");
//        numArribo.setText("");
        contene.setText("");
        ubica.setSelectedIndex(-1);
        equipo.setSelectedIndex(-1);
        ubica2.setText("");
        fecha.setText("");
        hora.setText("");
//        rojo.setVisible(false);
//        verde.setVisible(false);
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
        
        fecha.setText(fech);
        hora.setText(hor);
        
        vLleno = "";
        System.out.println("CONTENEDOR..: " + contene.getText());
        //Clients.showNotification("NO TIENE DATOS..!"+contene.getText());
//        alldata = cp.Actividad(contene.getText().toUpperCase());
//        for (ConfirmaPatiosMd item : alldata) {
//                    //Clients.showNotification("ACTIVIDAD..: " + item.getActividad() + " VACIO_LLENO.: " + item.getVacio());
//            if (item.getActividad().equals("1")) {
//                verde.setVisible(true);
//            } else {
//                rojo.setVisible(true);
//                btnGuardar.setDisabled(true);
//            }
//        }

    }
    
    public void onClick$btnGuardar(Event e){
        String user = desktop.getSession().getAttribute("USER").toString();
        cp.guardar( contene.getText().toUpperCase(),fecha.getText(),"9",hora.getText(),
                    ubica2.getText().toUpperCase(),vLleno,equipo.getText(),"P",ubica.getSelectedItem().getValue().toString(),
                    user);
        Clients.showNotification("DATOS GUARDADOS CON EXITO..: ");
        contene.setText("");
        ubica.setSelectedIndex(-1);
        equipo.setSelectedIndex(-1);
        ubica2.setText("");
        fecha.setText("");
        hora.setText("");
//        rojo.setVisible(false);
//        verde.setVisible(false);
    }
    
    public void onClick$btnSalir(Event e){
        rootPagina.setSrc("/Views/Principal.zul");
    }
    
}
