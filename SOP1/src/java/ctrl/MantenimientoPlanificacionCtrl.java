/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ctrl;

import DAL.MantenimientoPlanificacionDal;
import MD.MantenimientoPlanificacionMd;
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
public class MantenimientoPlanificacionCtrl extends GenericForwardComposer {

    private Textbox dua;
    private Textbox planificacion;
    private Textbox anio;
    private Textbox tipo;
    private Textbox estado;
    private Textbox mes;
    private Textbox retiene;
    private Textbox empresa;

    private String resp;

    MantenimientoPlanificacionMd item = new MantenimientoPlanificacionMd();
    MantenimientoPlanificacionDal mpdal = new MantenimientoPlanificacionDal();
    List<MantenimientoPlanificacionMd> alldata = new ArrayList<MantenimientoPlanificacionMd>();

    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        System.out.println("Mantenimiento de Planificacion");
    }

    public void onClick$btnBusca1(Event e) {
        if (dua.getText().trim().equals("")) {
            Clients.showNotification("Debe Ingresar un <br/> Numero de Contenedor valido <br/>Intentelo de nuevo. <br/> ",
                    //Clients.NOTIFICATION_TYPE_ERROR, null, "middle_center", 0);
                    Clients.NOTIFICATION_TYPE_WARNING, null, "top_center", 3000);
        } else {
            clear();
            alldata.clear();
            alldata = mpdal.search(dua.getText().trim().toUpperCase());

            if (alldata.isEmpty()) {
                Clients.showNotification("No se Encontraron <br/> datos para el numero de DUA <br/>Intentelo de nuevo. <br/> ",
                        //Clients.NOTIFICATION_TYPE_ERROR, null, "middle_center", 0);
                        Clients.NOTIFICATION_TYPE_WARNING, null, "top_center", 3000);
            } else {
                dua.setDisabled(true);
                //MantenimientoContenedores1Md item = new MantenimientoContenedores1Md();
                for (MantenimientoPlanificacionMd item : alldata) {
                    planificacion.setText(item.getIdPlnificacion());
                    anio.setText(item.getAnio());
                    tipo.setText(item.getTipo());
                    estado.setText(item.getEstado());
                    mes.setText(item.getMes());
                    retiene.setText(item.getRetiene());
                    empresa.setText(item.getEmpresa());
                }
            }
        }

    }
    
    public void onClick$btnActualizar(Event e) {
        item = new MantenimientoPlanificacionMd();
        resp = "";

        if (dua.getText().trim().equals("") ) {
            Clients.showNotification("Debe Ingresar <br/> Numero de DUA <br/>Intentelo de nuevo. <br/> ",
                    //Clients.NOTIFICATION_TYPE_ERROR, null, "middle_center", 0);
                    Clients.NOTIFICATION_TYPE_WARNING, null, "top_center", 3000);
        } else {
            item.setIdPlnificacion(planificacion.getText());
            item.setAnio(anio.getText());
            item.setTipo(tipo.getText());
            item.setEmpresa(empresa.getText());
            item.setMes(mes.getText());
            item.setRetiene(retiene.getText());
            item.setEstado(estado.getText());
            
            //Messagebox.show("Are you sure to save?", "Confirm Dialog", Messagebox.OK | Messagebox.IGNORE | Messagebox.CANCEL, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
            Messagebox.show("Estas SEGURO que deseas ACTUALIZAR los datos?", "Confirmar Actualizacion", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
                public void onEvent(Event evt) throws InterruptedException {
                    if (evt.getName().equals("onOK")) {
                        resp = mpdal.Updata(dua.getText().trim().toUpperCase(), item);
                        //alert("Datos Actualizados !");

                        if (resp.equals("1")) {
                            Clients.showNotification("Actualizacion <br/> Exitosa <br/> ",
                                    //Clients.NOTIFICATION_TYPE_ERROR, null, "middle_center", 0);
                                    Clients.NOTIFICATION_TYPE_INFO, null, "top_center", 3000);
                            clear();
                            dua.setDisabled(false);
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

    }
    
    
    public void onClick$btnEliminar(Event e) {
        resp = "";
        if (dua.getText().trim().equals("") ) {
            Clients.showNotification("Debe Ingresar <br/> Numero de DUA <br/>Intentelo de nuevo. <br/> ",
                    //Clients.NOTIFICATION_TYPE_ERROR, null, "middle_center", 0);
                    Clients.NOTIFICATION_TYPE_WARNING, null, "top_center", 3000);
        } else {

            Messagebox.show("Estas SEGURO que deseas ELIMINAR los datos?", "Confirmar ELIMINACION", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
                public void onEvent(Event evt) throws InterruptedException {
                    if (evt.getName().equals("onOK")) {
                        resp = mpdal.Deldata(dua.getText().trim().toUpperCase());
                        if (resp.equals("1")) {
                            Clients.showNotification("Los Datos han sido <br/> Eliminados con Exito <br/> ",
                                    //Clients.NOTIFICATION_TYPE_ERROR, null, "middle_center", 0);
                                    Clients.NOTIFICATION_TYPE_INFO, null, "top_center", 3000);
                            clear();
                            dua.setDisabled(false);
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
        dua.setText("");
        clear();
    }

    public void clear() {
        dua.setDisabled(false);
        //dua.setText("");
        planificacion.setText("");
        anio.setText("");
        tipo.setText("");
        estado.setText("");
        mes.setText("");
        retiene.setText("");
        empresa.setText("");
    }

}
