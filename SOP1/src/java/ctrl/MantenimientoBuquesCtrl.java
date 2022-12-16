package ctrl;

import DAL.MantenimientoBuquesDal;
import MD.MantenimientoBuquesMd;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Include;
import org.zkoss.zul.Textbox;

public class MantenimientoBuquesCtrl extends GenericForwardComposer {

    private Doublebox buque;
    private Textbox imo;
    private Textbox nombre;
    private Textbox tipobuque;
    private Textbox nomBuque;
    private Textbox bandera;
    private Textbox nomBandera;
    private Textbox anoConstruc;
    private Datebox ultimoArrib;
    private Textbox trb;
    private Textbox trn;
    private Textbox pesoMuerto;
    private Textbox eslora;
    private Textbox manga;
    private Textbox caladoMax;
    private Textbox cantBodega;
    private Textbox cantGrua;
    private Textbox cantPluma;
    private Textbox capacidadGru;
    private Textbox observaciones;
    private Textbox lr;
    private Textbox callsign;
    private Textbox ultimaActu;

    String lb;

    List<MantenimientoBuquesMd> allMantbuques = new ArrayList<MantenimientoBuquesMd>();
    MantenimientoBuquesMd manteniMD = new MantenimientoBuquesMd();
    // private Listbox lb2;
    MantenimientoBuquesDal ManbuDal = new MantenimientoBuquesDal();
    private Include rootPagina;

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);

    }

    public void onChange$buque(Event e) throws SQLException {
        manteniMD = new MantenimientoBuquesMd();
        manteniMD = ManbuDal.MostrarProducto(buque.getText());

        if (manteniMD.getResp().equals("1")) {

            imo.setText(manteniMD.getImo_buque());
            nombre.setText(manteniMD.getNom_buque());
            tipobuque.setText(manteniMD.getCod_buque());
            nomBuque.setText(manteniMD.getTipo_buque());
            bandera.setText(manteniMD.getCod_bandera());
            nomBandera.setText(manteniMD.getNom_bandera());
            anoConstruc.setText(manteniMD.getAnio_construccion());
            ultimoArrib.setText(manteniMD.getUltimo_arribo());
            trb.setText(manteniMD.getTrb_buque());
            trn.setText(manteniMD.getTrn_buque());
            pesoMuerto.setText(manteniMD.getPeso_muerto_buque());
            eslora.setText(manteniMD.getEslora_buque());
            manga.setText(manteniMD.getManga_buque());
            caladoMax.setText(manteniMD.getCalado_maximo());
            cantBodega.setText(manteniMD.getCant_bodegas());
            cantGrua.setText(manteniMD.getCant_gruas());
            cantPluma.setText(manteniMD.getCant_plumas());
            capacidadGru.setText(manteniMD.getCapacidad_gruas());
            observaciones.setText(manteniMD.getObservaciones_buque());
            lr.setText(manteniMD.getLr_buque());
            callsign.setText(manteniMD.getCall_sign_buque());
            ultimaActu.setText(manteniMD.getUltima_actualizacion());

            //INABILITAR LOS CAMPOS
            buque.setDisabled(false);
            imo.setDisabled(true);
            nombre.setDisabled(true);
            tipobuque.setDisabled(true);
            nomBuque.setDisabled(true);
            bandera.setDisabled(true);
            nomBandera.setDisabled(true);
            anoConstruc.setDisabled(true);
            ultimoArrib.setDisabled(true);
            trb.setDisabled(true);
            trn.setDisabled(true);
            pesoMuerto.setDisabled(true);
            eslora.setDisabled(true);
            manga.setDisabled(true);
            caladoMax.setDisabled(true);
            cantBodega.setDisabled(true);
            cantGrua.setDisabled(true);
            cantPluma.setDisabled(true);
            capacidadGru.setDisabled(true);
            observaciones.setDisabled(true);
            lr.setDisabled(true);
            callsign.setDisabled(true);
            ultimaActu.setDisabled(true);

        } else {
            clear();

            Clients.showNotification(manteniMD.getMsg() + "<br/>",
                    Clients.NOTIFICATION_TYPE_WARNING, null, "middle_center", 3000);
        }

    }

    public void onClick$btnModificar(Event e) throws SQLException {

        Clients.showNotification(manteniMD.getMsg() + "<brRegistros Guardados con Exito/>",
                Clients.NOTIFICATION_TYPE_INFO, null, "middle_center", 3000);

        //SE HABILITAN LOS CAMPOS PARA UN UPDATE
        buque.setDisabled(true);
        imo.setDisabled(true);
        nombre.setDisabled(false);
        tipobuque.setDisabled(true);
        nomBuque.setDisabled(true);
        bandera.setDisabled(true);
        nomBandera.setDisabled(true);
        anoConstruc.setDisabled(false);
        ultimoArrib.setDisabled(true);
        trb.setDisabled(true);
        trn.setDisabled(true);
        pesoMuerto.setDisabled(true);
        eslora.setDisabled(true);
        manga.setDisabled(true);
        caladoMax.setDisabled(true);
        cantBodega.setDisabled(false);
        cantGrua.setDisabled(false);
        cantPluma.setDisabled(false);
        capacidadGru.setDisabled(false);
        observaciones.setDisabled(false);
        lr.setDisabled(true);
        callsign.setDisabled(true);
        ultimaActu.setDisabled(true);

    }

    public void clear() {

        buque.setDisabled(false);

        buque.setText("");
        imo.setText("");
        nombre.setText("");
        tipobuque.setText("");
        nomBuque.setText("");
        bandera.setText("");
        nomBandera.setText("");
        anoConstruc.setText("");
        ultimoArrib.setText("");
        trb.setText("");
        trn.setText("");
        pesoMuerto.setText("");
        eslora.setText("");
        manga.setText("");
        caladoMax.setText("");
        cantBodega.setText("");
        cantGrua.setText("");
        cantPluma.setText("");
        capacidadGru.setText("");
        observaciones.setText("");
        lr.setText("");
        callsign.setText("");
        ultimaActu.setText("");
        buque.focus();

    }

    public void onClick$btnNuevo() {
        clear();
    }

    public void onClick$btnGuardar(Event e) throws SQLException, ClassNotFoundException, ParseException {

        int op1 = 0;
        //aqui se coloca lo que se va a guardar

        manteniMD = new MantenimientoBuquesMd();

        manteniMD.setBuque(buque.getText().toUpperCase());
        manteniMD.setImo_buque(imo.getText().toUpperCase());
        manteniMD.setNom_buque(nombre.getText().toUpperCase());
        manteniMD.setAnio_construccion(anoConstruc.getText().toUpperCase());
        manteniMD.setTrb_buque(trb.getText().toUpperCase());

        manteniMD.setTrn_buque(trn.getText().toUpperCase());
        manteniMD.setPeso_muerto_buque(pesoMuerto.getText().toUpperCase());
        manteniMD.setEslora_buque(eslora.getText().toUpperCase());
        manteniMD.setManga_buque(manga.getText().toUpperCase());
        manteniMD.setCalado_maximo(caladoMax.getText().toUpperCase());

        manteniMD.setCant_bodegas(cantBodega.getText().toUpperCase());
        manteniMD.setCant_gruas(cantGrua.getText().toUpperCase());
        manteniMD.setCant_plumas(cantPluma.getText().toUpperCase());
        manteniMD.setCapacidad_gruas(capacidadGru.getText().toUpperCase());
        manteniMD.setObservaciones_buque(observaciones.getText().toUpperCase());

        manteniMD.setLr_buque(lr.getText().toUpperCase());
        manteniMD.setCall_sign_buque(callsign.getText().toUpperCase());
        manteniMD.setUltima_actualizacion(desktop.getSession().getAttribute("USUARIO").toString());
        
        manteniMD = ManbuDal.updateBuque(manteniMD);

        if (manteniMD.getResp().equals("1")) {
            clear();
            Clients.showNotification(manteniMD.getMsg() + "<brRegistros Guardados con Exito/>",
                    Clients.NOTIFICATION_TYPE_INFO, null, "middle_center", 3000);
        } else {
            Clients.showNotification(manteniMD.getMsg() + "<brRegistro no Guardado revise los datos/>",
                    Clients.NOTIFICATION_TYPE_WARNING, null, "middle_center", 3000);
        }
    }

    public void onClick$btnSalir() {
        rootPagina.setSrc("/Views/Principal.zul");
    }
}
