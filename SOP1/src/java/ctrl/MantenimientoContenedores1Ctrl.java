/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ctrl;

import DAL.MantenimientoContenedores1Dal;
import MD.MantenimientoContenedores1Md;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Textbox;

/**
 *
 * @author alfonsoc7905
 */
public class MantenimientoContenedores1Ctrl extends GenericForwardComposer {

    private Textbox contenedor;
    private Textbox tipo;
    private Textbox linea;
    private Textbox naviera;
    private Textbox horaIn;
    private Textbox ubicacion1;
    private Textbox pesobrutor;
    private Textbox pesotara;
    private Textbox pesonetob;
    private Textbox vacio;
    private Textbox estado;
    private Textbox facentra;
    private Textbox manifiesto;
    private Textbox anomanif;
    private Textbox areau;
    private Textbox transito;

    private Datebox fechaIn;

    MantenimientoContenedores1Dal mcdal = new MantenimientoContenedores1Dal();
    List<MantenimientoContenedores1Md> alldata = new ArrayList<MantenimientoContenedores1Md>();

    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        System.out.println("Mantenimiento Contenedores #1");
    }

    public void onClick$btnBusca1(Event e) {
        if (contenedor.getText().trim().equals("")) {
            Clients.showNotification("Debe Ingresar un <br/> Numero de Contenedor valido <br/>Intentelo de nuevo. <br/> ",
                    //Clients.NOTIFICATION_TYPE_ERROR, null, "middle_center", 0);
                    Clients.NOTIFICATION_TYPE_WARNING, null, "top_center", 3000);
        } else {
            clear();
            alldata.clear();
            alldata = mcdal.search(contenedor.getText().trim().toUpperCase());
            //MantenimientoContenedores1Md item = new MantenimientoContenedores1Md();
            for (MantenimientoContenedores1Md item : alldata) {
                tipo.setText(item.getTipo());
                linea.setText(item.getLinea());
                naviera.setText(item.getNaviera());
                fechaIn.setText(item.getFecha2());
                horaIn.setText(item.getHora2());
                ubicacion1.setText(item.getUbicacion1());
                pesobrutor.setText(item.getPesobrutor());
                pesotara.setText(item.getPesotara());
                pesonetob.setText(item.getPesonetob());
                vacio.setText(item.getVacio());
                estado.setText(item.getEstado());
                facentra.setText(item.getFactura());
                manifiesto.setText(item.getManifiesto());
                anomanif.setText(item.getAniomanif());
                areau.setText(item.getAreaubica());
                transito.setText(item.getTransito());
            }

        }

    }

    public void onClick$btnActualizar(Event e) {
        MantenimientoContenedores1Md item = new MantenimientoContenedores1Md();
        String resp = "";

        if (tipo.getText().trim().equals("") || linea.getText().trim().equals("")
                || naviera.getText().trim().equals("") || fechaIn.getText().trim().equals("")
                || horaIn.getText().trim().equals("") || ubicacion1.getText().trim().equals("")
                || pesobrutor.getText().trim().equals("") || pesotara.getText().trim().equals("")
                || pesonetob.getText().trim().equals("") || vacio.getText().trim().equals("")
                || estado.getText().trim().equals("") || facentra.getText().trim().equals("")
                || manifiesto.getText().trim().equals("") || anomanif.getText().trim().equals("")
                || areau.getText().trim().equals("") || transito.getText().trim().equals("")) {
            Clients.showNotification("No puede dejar <br/> campos vacios <br/>Intentelo de nuevo. <br/> ",
                    //Clients.NOTIFICATION_TYPE_ERROR, null, "middle_center", 0);
                    Clients.NOTIFICATION_TYPE_WARNING, null, "top_center", 3000);
        }

        item.setTipo(tipo.getText());
        item.setLinea(linea.getText());
        item.setNaviera(naviera.getText());
        item.setFecha2(fechaIn.getText());
        item.setHora2(horaIn.getText());
        item.setUbicacion1(ubicacion1.getText());
        item.setPesobrutor(pesobrutor.getText());
        item.setPesotara(pesotara.getText());
        item.setPesonetob(pesonetob.getText());
        item.setVacio(vacio.getText());
        item.setEstado(estado.getText());
        item.setFactura(facentra.getText());
        item.setManifiesto(manifiesto.getText());
        item.setAniomanif(anomanif.getText());
        item.setAreaubica(areau.getText());
        item.setTransito(transito.getText());
        resp = mcdal.Updata(contenedor.getText().trim().toUpperCase(), item);

        if (resp.equals("1")) {
            Clients.showNotification("Actualizacion <br/> Exitosa <br/> ",
                    //Clients.NOTIFICATION_TYPE_ERROR, null, "middle_center", 0);
                    Clients.NOTIFICATION_TYPE_INFO, null, "top_center", 3000);
            clear();
        } else {
            Clients.showNotification("Actualizacion <br/> con errores <br/> " + resp + "<br/>",
                    //Clients.NOTIFICATION_TYPE_ERROR, null, "middle_center", 0);
                    Clients.NOTIFICATION_TYPE_ERROR, null, "top_center", 3000);
        }

    }

    public void onClick$btnEliminar(Event e) {
        String resp = "";
        if (contenedor.getText().trim().equals("")) {
            Clients.showNotification("Debe Ingresar un <br/> Numero de Contenedor valido <br/>Intentelo de nuevo. <br/> ",
                    //Clients.NOTIFICATION_TYPE_ERROR, null, "middle_center", 0);
                    Clients.NOTIFICATION_TYPE_WARNING, null, "top_center", 3000);
        } else {
            resp = mcdal.Deldata(contenedor.getText().trim());
            if (resp.equals("1")) {
            Clients.showNotification("Los Datos han sido <br/> Eliminados con Exito <br/> ",
                    //Clients.NOTIFICATION_TYPE_ERROR, null, "middle_center", 0);
                    Clients.NOTIFICATION_TYPE_INFO, null, "top_center", 3000);
            clear();
        } else {
            Clients.showNotification("No se han podido Eliminar <br/> los Datos <br/> " + resp + "<br/>",
                    //Clients.NOTIFICATION_TYPE_ERROR, null, "middle_center", 0);
                    Clients.NOTIFICATION_TYPE_ERROR, null, "top_center", 3000);
        }
        }
        
        
    }

    public void clear() {
        tipo.setText("");
        linea.setText("");
        naviera.setText("");
        fechaIn.setText("");
        horaIn.setText("");
        ubicacion1.setText("");
        pesobrutor.setText("");
        pesotara.setText("");
        pesonetob.setText("");
        vacio.setText("");
        estado.setText("");
        facentra.setText("");
        manifiesto.setText("");
        anomanif.setText("");
        areau.setText("");
        transito.setText("");
    }

}
