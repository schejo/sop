package ctrl;

import DAL.CamionParqDal;
import MD.CamionParqMd;
import java.sql.SQLException;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Datebox;
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

    public void onClick$btnModificar(Event e) throws SQLException {

        Clients.showNotification(manteniMD.getMsg() + "<brRegistros Guardados con Exito/>",
                Clients.NOTIFICATION_TYPE_INFO, null, "middle_center", 3000);

        //SE HABILITAN LOS CAMPOS PARA UN UPDATE
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

    }

}
