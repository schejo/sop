package ctrl;

import DAL.CamionParqDal;
import MD.CamionParqMd;
import java.sql.SQLException;
import java.text.ParseException;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Include;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

/**
 *
 * @author Informatica
 */
public class CamionParqCtrl extends GenericForwardComposer {

    private Textbox placa;
    private Textbox cod_pais;
    private Textbox paispil;
    private Textbox licencia;
    private Textbox tipo_opera;
    private Datebox fecha_ing_parq;
    private Textbox naviera;
    private Textbox observaciones;
    private Textbox cod_destino;
    private Datebox fecha_inicio;
    private Datebox fecha_fin;
    private Textbox ubic_camion;
    private Textbox num_contene;

    CamionParqMd manteniMD = new CamionParqMd();
    CamionParqDal ManbuDal = new CamionParqDal();
    private Include rootPagina;

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);

    }

    public void onChange$placa(Event e) throws SQLException {
        manteniMD = new CamionParqMd();
        manteniMD = ManbuDal.MostrarProducto(placa.getText().toUpperCase());

        if (manteniMD.getResp().equals("1")) {

            cod_pais.setText(manteniMD.getCod_pais());
            paispil.setText(manteniMD.getPais_piloto());
            licencia.setText(manteniMD.getLicencia());
            tipo_opera.setText(manteniMD.getTipo_opera());
            fecha_ing_parq.setText(manteniMD.getFecha_ing_parqueo());
            naviera.setText(manteniMD.getNaviera());
            observaciones.setText(manteniMD.getObservaciones());
            cod_destino.setText(manteniMD.getCod_destino());
            fecha_inicio.setText(manteniMD.getFecha_inicio());
            fecha_fin.setText(manteniMD.getFecha_fin());
            ubic_camion.setText(manteniMD.getUbic_camion());
            num_contene.setText(manteniMD.getNum_contene());

            //INABILITAR LOS CAMPOS
            placa.setDisabled(false);
            cod_pais.setDisabled(true);
            paispil.setDisabled(true);
            licencia.setDisabled(true);
            tipo_opera.setDisabled(true);
            fecha_ing_parq.setDisabled(true);
            naviera.setDisabled(true);
            observaciones.setDisabled(true);
            cod_destino.setDisabled(true);
            fecha_inicio.setDisabled(true);
            fecha_fin.setDisabled(true);
            ubic_camion.setDisabled(true);
            num_contene.setDisabled(true);

        } else {

            Clients.showNotification(manteniMD.getMsg() + "<br/>",
                    Clients.NOTIFICATION_TYPE_WARNING, null, "middle_center", 3000);
        }

    }

    public void clear() {
        placa.setDisabled(false);

        placa.setText("");
        cod_pais.setText("");
        paispil.setText("");
        licencia.setText("");
        tipo_opera.setText("");
        fecha_ing_parq.setText("");
        naviera.setText("");
        observaciones.setText("");
        cod_destino.setText("");
        fecha_inicio.setText("");
        fecha_fin.setText("");
        ubic_camion.setText("");
        num_contene.setText("");
    }

    public void onClick$btnNuevo() {
        clear();
    }

    public void onClick$btnModificar(Event e) throws SQLException {
        
        if(placa.getText().equals("")){

        Clients.showNotification("<br/> Favor Ingrese Placa",
                Clients.NOTIFICATION_TYPE_ERROR, null, "middle_center", 3000);
        }else{
             

        //SE HABILITAN LOS CAMPOS PARA UN UPDATE
        placa.setDisabled(false);
        cod_pais.setDisabled(false);
        paispil.setDisabled(false);
        licencia.setDisabled(false);
        tipo_opera.setDisabled(false);
        fecha_ing_parq.setDisabled(false);
        naviera.setDisabled(false);
        observaciones.setDisabled(false);
        cod_destino.setDisabled(false);
        fecha_inicio.setDisabled(false);
        fecha_fin.setDisabled(false);
        ubic_camion.setDisabled(false);
        num_contene.setDisabled(false);
        }

    }

    public void onClick$btnGuardar(Event e) throws SQLException, ClassNotFoundException, ParseException {

        int op1 = 0;
        //aqui se coloca lo que se va a guardar

        manteniMD = new CamionParqMd();

        manteniMD.setPlaca(placa.getText().toUpperCase());
        manteniMD.setCod_pais(cod_pais.getText().toUpperCase());
        manteniMD.setPais_piloto(paispil.getText().toUpperCase());
        manteniMD.setLicencia(licencia.getText());
        manteniMD.setTipo_opera(tipo_opera.getText().toUpperCase());
        manteniMD.setFecha_ing_parqueo(fecha_ing_parq.getText());
        manteniMD.setNaviera(naviera.getText().toUpperCase());
        manteniMD.setObservaciones(observaciones.getText().toUpperCase());
        manteniMD.setCod_destino(cod_destino.getText());
        manteniMD.setFecha_inicio(fecha_inicio.getText());
        manteniMD.setFecha_fin(fecha_fin.getText());
        manteniMD.setUbic_camion(ubic_camion.getText());
        manteniMD.setNum_contene(num_contene.getText().toUpperCase());

        manteniMD = ManbuDal.updatePlaca(manteniMD);

        if (manteniMD.getResp().equals("1")) {
            clear();
            Clients.showNotification(manteniMD.getMsg() + "<brRegistros Guardados con Exito/>",
                    Clients.NOTIFICATION_TYPE_INFO, null, "middle_center", 3000);
        } else {
            Clients.showNotification(manteniMD.getMsg() + "<brRegistro no Guardado revise los datos/>",
                    Clients.NOTIFICATION_TYPE_WARNING, null, "middle_center", 3000);
        }
    }

    public void onClick$btnDelete(Event e) throws SQLException {

        Messagebox.show("Estas Seguro Que Deseas Borrar este numero de placa " + placa.getText() + "?",
                "Question", Messagebox.OK | Messagebox.CANCEL,
                Messagebox.QUESTION,
                new org.zkoss.zk.ui.event.EventListener() {
                    
            public void onEvent(Event e) throws SQLException, ClassNotFoundException {
                if (Messagebox.ON_OK.equals(e.getName())) {
                    manteniMD = ManbuDal.REGdelete(placa.getText());
                    Clients.evalJavaScript("msj('" + manteniMD.getMsg() + "','success')");
                    clear();
                } else if (Messagebox.ON_CANCEL.equals(e.getName())) {
                    Clients.showNotification("REGISTRO NO SE HA <br/> BORRADO <br/>",
                            "warning", null, "middle_center", 5000);
                }
            }
        }
        );
    }

    public void onClick$btnSalir() {
        rootPagina.setSrc("/Views/Principal.zul");
    }

}
