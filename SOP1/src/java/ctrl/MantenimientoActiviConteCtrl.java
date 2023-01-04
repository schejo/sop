/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ctrl;

import DAL.MantenimientoActiviConteDal;
import MD.MantenimientoActiviConteMd;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

/**
 *
 * @author alfonsoc7905
 */
public class MantenimientoActiviConteCtrl extends GenericForwardComposer {

    private Textbox contenedor;
    private Textbox actividad;
    private Textbox correla;
    private Textbox horaIn4;
    private Textbox codactivi;
    private Textbox horaFin4;
    private Textbox ubicacion1;
    private Textbox marchamo3;
    private Textbox anioarribo;
    private Textbox numarribo;
    private Textbox estadoconte;
    private Textbox danos;
    private Textbox manifiesto;
    private Textbox anomanif;
    private Textbox corrBL;
    private Textbox detaBL;
    private Textbox pesobrutob;
    private Textbox puerto;
    private Textbox areaubica;

    private Datebox fechaIn2;
    private Datebox fechaFin2;
    private Datebox fechaciclo;

    private String resp;

    MantenimientoActiviConteMd item = new MantenimientoActiviConteMd();
    MantenimientoActiviConteDal macdal = new MantenimientoActiviConteDal();
    List<MantenimientoActiviConteMd> alldata = new ArrayList<MantenimientoActiviConteMd>();

    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        System.out.println("Mantenimiento Activi Conte");
    }

    public void onClick$btnBusca1(Event e) {
        if (contenedor.getText().trim().equals("")
                || actividad.getText().trim().equals("")
                || correla.getText().trim().equals("")) {
            Clients.showNotification("Debe Ingresar <br/> Numero de Contenedor, <br/> Numero de Actividad, <br/> y el Correlativo <br/>Intentelo de nuevo. <br/> ",
                    //Clients.NOTIFICATION_TYPE_ERROR, null, "middle_center", 0);
                    Clients.NOTIFICATION_TYPE_WARNING, null, "top_center", 3000);
        } else {
            alldata.clear();
            alldata = macdal.search(contenedor.getText().trim().toUpperCase(), actividad.getText().trim(), correla.getText().trim());

            if (alldata.isEmpty()) {
                Clients.showNotification("No se Encontraron <br/> datos para el numero de contenedore con ese <br/> Numero de Actividad <br/> y el Correlativo <br/>Intentelo de nuevo. <br/> ",
                        //Clients.NOTIFICATION_TYPE_ERROR, null, "middle_center", 0);
                        Clients.NOTIFICATION_TYPE_WARNING, null, "top_center", 3000);
            } else {
                contenedor.setDisabled(true);
                actividad.setDisabled(true);
                correla.setDisabled(true);
                for (MantenimientoActiviConteMd item : alldata) {
                    fechaIn2.setText(item.getFechaInicial2());
                    codactivi.setText(item.getCodigoActividad());
                    horaIn4.setText(item.getHoraInicial4());
                    fechaFin2.setText(item.getFechaFinal2());
                    horaFin4.setText(item.getHoraFinal4());
                    ubicacion1.setText(item.getUbicacion1());
                    marchamo3.setText(item.getMarchamo3());
                    anioarribo.setText(item.getAnioArribo());
                    numarribo.setText(item.getNumArribo());
                    fechaciclo.setText(item.getFechaCiclo());
                    estadoconte.setText(item.getEstadoConte());
                    danos.setText(item.getDanos());
                    manifiesto.setText(item.getManifiesto());
                    anomanif.setText(item.getAnioManif());
                    corrBL.setText(item.getCorrelaBL());
                    detaBL.setText(item.getDetalleBL());
                    pesobrutob.setText(item.getPesobrutob());
                    puerto.setText(item.getPuerto());
                    areaubica.setText(item.getAreaU());
                }
            }
        }
    }

    public void onClick$btnActualizar(Event e) {
        item = new MantenimientoActiviConteMd();
        resp = "";

        if (contenedor.getText().trim().equals("")
                || actividad.getText().trim().equals("")
                || correla.getText().trim().equals("")) {
            Clients.showNotification("Debe Ingresar <br/> Numero de Contenedor, <br/> Numero de Actividad, <br/> y el Correlativo <br/>Intentelo de nuevo. <br/> ",
                    //Clients.NOTIFICATION_TYPE_ERROR, null, "middle_center", 0);
                    Clients.NOTIFICATION_TYPE_WARNING, null, "top_center", 3000);
        } else {
            item.setFechaInicial2(fechaIn2.getText());
            item.setCodigoActividad(codactivi.getText());
            item.setHoraInicial4(horaIn4.getText());
            item.setFechaFinal2(fechaFin2.getText());
            item.setHoraFinal4(horaFin4.getText());
            item.setUbicacion1(ubicacion1.getText());
            item.setMarchamo3(marchamo3.getText());
            item.setAnioArribo(anioarribo.getText());
            item.setNumArribo(numarribo.getText());
            item.setFechaCiclo(fechaciclo.getText());
            item.setEstadoConte(estadoconte.getText());
            item.setDanos(danos.getText());
            item.setManifiesto(manifiesto.getText());
            item.setAnioManif(anomanif.getText());
            item.setCorrelaBL(corrBL.getText());
            item.setDetalleBL(detaBL.getText());
            item.setPesobrutob(pesobrutob.getText());
            item.setPuerto(puerto.getText());
            item.setAreaU(areaubica.getText());

            //Messagebox.show("Are you sure to save?", "Confirm Dialog", Messagebox.OK | Messagebox.IGNORE | Messagebox.CANCEL, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
            Messagebox.show("Estas SEGURO que deseas ACTUALIZAR los datos?", "Confirmar Actualizacion", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
                public void onEvent(Event evt) throws InterruptedException {
                    if (evt.getName().equals("onOK")) {
                        resp = macdal.updata(contenedor.getText().trim().toUpperCase(), actividad.getText().trim(), correla.getText().trim(), item);
                        //alert("Datos Actualizados !");

                        if (resp.equals("1")) {
                            Clients.showNotification("Actualizacion <br/> Exitosa <br/> ",
                                    //Clients.NOTIFICATION_TYPE_ERROR, null, "middle_center", 0);
                                    Clients.NOTIFICATION_TYPE_INFO, null, "top_center", 3000);
                            clear();
                            contenedor.setDisabled(false);
                            actividad.setDisabled(false);
                            correla.setDisabled(false);
                        } else {
                            Clients.showNotification("Actualizacion <br/> con errores <br/> " + resp + "<br/>",
                                    //Clients.NOTIFICATION_TYPE_ERROR, null, "middle_center", 0);
                                    Clients.NOTIFICATION_TYPE_ERROR, null, "top_center", 3000);

                        }

                    } /*else if (evt.getName().equals("onIgnore")) {
                    Messagebox.show("Ignore Save", "Warning", Messagebox.OK, Messagebox.EXCLAMATION);
                } */ else {
                        //alert("Actaulizacion Cancelada !");
                        Clients.showNotification("Actualizacion <br/> Cancelada <br/> ",
                                //Clients.NOTIFICATION_TYPE_ERROR, null, "middle_center", 0);
                                Clients.NOTIFICATION_TYPE_WARNING, null, "top_center", 3000);
                    }
                }
            });

        }

        /*
        //ORIGINAL
        //resp = macdal.updata(contenedor.getText().trim().toUpperCase(), actividad.getText().trim(), correla.getText().trim(), item);
        if (resp.equals("1")) {
            Clients.showNotification("Actualizacion <br/> Exitosa <br/> ",
                    //Clients.NOTIFICATION_TYPE_ERROR, null, "middle_center", 0);
                    Clients.NOTIFICATION_TYPE_INFO, null, "top_center", 3000);
            clear();
        } else {
            Clients.showNotification("Actualizacion <br/> con errores <br/> " + resp + "<br/>",
                    //Clients.NOTIFICATION_TYPE_ERROR, null, "middle_center", 0);
                    Clients.NOTIFICATION_TYPE_ERROR, null, "top_center", 3000);
        }*/
    }

    public void onClick$btnEliminar(Event e) {
        resp = "";
        if (contenedor.getText().trim().equals("")
                || actividad.getText().trim().equals("")
                || correla.getText().trim().equals("")) {
            Clients.showNotification("Debe Ingresar <br/> Numero de Contenedor, <br/> Numero de Actividad, <br/> y el Correlativo <br/>Intentelo de nuevo. <br/> ",
                    //Clients.NOTIFICATION_TYPE_ERROR, null, "middle_center", 0);
                    Clients.NOTIFICATION_TYPE_WARNING, null, "top_center", 3000);
        } else {

            Messagebox.show("Estas SEGURO que deseas ELIMINAR los datos?", "Confirmar ELIMINACION", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
                public void onEvent(Event evt) throws InterruptedException {
                    if (evt.getName().equals("onOK")) {
                        resp = macdal.Deldata(contenedor.getText().trim().toUpperCase(), actividad.getText().trim(), correla.getText().trim());
                        if (resp.equals("1")) {
                            Clients.showNotification("Los Datos han sido <br/> Eliminados con Exito <br/> ",
                                    //Clients.NOTIFICATION_TYPE_ERROR, null, "middle_center", 0);
                                    Clients.NOTIFICATION_TYPE_INFO, null, "top_center", 3000);
                            clear();
                            contenedor.setDisabled(false);
                            actividad.setDisabled(false);
                            correla.setDisabled(false);
                        } else {
                            Clients.showNotification("No se han podido Eliminar <br/> los Datos <br/> " + resp + "<br/>",
                                    //Clients.NOTIFICATION_TYPE_ERROR, null, "middle_center", 0);
                                    Clients.NOTIFICATION_TYPE_ERROR, null, "top_center", 3000);
                        }

                    } /*else if (evt.getName().equals("onIgnore")) {
                    Messagebox.show("Ignore Save", "Warning", Messagebox.OK, Messagebox.EXCLAMATION);
                } */ else {
                        //alert("Actaulizacion Cancelada !");
                        Clients.showNotification("Eliminacion <br/> Cancelada <br/> ",
                                //Clients.NOTIFICATION_TYPE_ERROR, null, "middle_center", 0);
                                Clients.NOTIFICATION_TYPE_WARNING, null, "top_center", 3000);
                    }
                }
            });

        }
    }

    public void onClick$btnNuevo(Event e) {
        contenedor.setText("");
        actividad.setText("");
        correla.setText("");
        contenedor.setDisabled(false);
        actividad.setDisabled(false);
        correla.setDisabled(false);
        clear();
    }

    public void clear() {
        fechaIn2.setText("");
        codactivi.setText("");
        horaIn4.setText("");
        fechaFin2.setText("");
        horaFin4.setText("");
        ubicacion1.setText("");
        marchamo3.setText("");
        anioarribo.setText("");
        numarribo.setText("");
        fechaciclo.setText("");
        estadoconte.setText("");
        danos.setText("");
        manifiesto.setText("");
        anomanif.setText("");
        corrBL.setText("");
        detaBL.setText("");
        pesobrutob.setText("");
        puerto.setText("");
        areaubica.setText("");
    }

}
