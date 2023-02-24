package ctrl;

import DAL.CitasPlanificacionDal;
import MD.CitasPlanificacionMd;
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
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Include;
import org.zkoss.zul.Textbox;

public class CitasPlanificacionCtrl extends GenericForwardComposer {

    private Doublebox anioarribo;
    private Doublebox numarribo;
    private Combobox lugar;
    private Datebox fecha;
    private Textbox nomNaviera;
    private Datebox eta;
    private Datebox etaP;
    private Textbox nomBuque;
    private Textbox obs;

    CitasPlanificacionMd manteniMD = new CitasPlanificacionMd();
    CitasPlanificacionMd manteniMD2 = new CitasPlanificacionMd();
    CitasPlanificacionDal ManbuDal = new CitasPlanificacionDal();
    //creamos un array lis para guardar todos los arribos
    List<CitasPlanificacionMd> allProductos1 = new ArrayList<CitasPlanificacionMd>();
    private Include rootPagina;

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        desktop.getSession().getAttribute("USER").toString();
        // obs.setText(desktop.getSession().getAttribute("USER").toString());
        allProductos1 = ManbuDal.RSelect();

    }

    public void onClick$btnGuardar(Event e) throws SQLException, ClassNotFoundException, ParseException {
        int op1 = 0;
        allProductos1 = ManbuDal.RSelect();
        manteniMD = new CitasPlanificacionMd();

        int op = 0;
        if (fecha.getText().trim().equals("")) {
            op = 1;
        }
        if (lugar.getSelectedIndex() == -1) {
            op = 1;
        }
        if (op == 0) {
            //para identifiacar si el arribo existe
            for (CitasPlanificacionMd dt : allProductos1) {

                if (dt.getNum_arribo().equals(numarribo.getText()) && dt.getAnio_arribo().equals(anioarribo.getText())) {
                    op1++;

                }
            }
            // si el arribo no existe que haga in insert
            if (op1 == 0) {

                //aqui se pone lo que se va a guardar
                manteniMD.setAnio_arribo(anioarribo.getText());
                manteniMD.setNum_arribo(numarribo.getText());
                manteniMD.setFecha_hora(fecha.getText());
                manteniMD.setLugar(lugar.getSelectedItem().getValue().toString().toUpperCase());
                manteniMD.setObser(obs.getText().toUpperCase());
                manteniMD.setEta(eta.getText());
                manteniMD.setNomBuque(nomBuque.getText());
                manteniMD.setUsuario_alta(desktop.getSession().getAttribute("USER").toString().toUpperCase());
                manteniMD.setEtap(etaP.getText());
//                manteniMD.setNomNaviera(nomNaviera.getText().toUpperCase());
//                manteniMD.setEta(eta.getText().toUpperCase());

                manteniMD = ManbuDal.savePro(manteniMD);

            } else {
                manteniMD.setAnio_arribo(anioarribo.getText());
                manteniMD.setNum_arribo(numarribo.getText());
                manteniMD.setFecha_hora(fecha.getText());
                manteniMD.setLugar(lugar.getSelectedItem().getValue().toString().toUpperCase());
                manteniMD.setObser(obs.getText());
                manteniMD.setEtap(etaP.getText());
                manteniMD = ManbuDal.updatePro(manteniMD);

            }
            if (manteniMD.getResp().equals("1")) {
                clear();
                Clients.showNotification(manteniMD.getMsg() + "<br/>",
                        Clients.NOTIFICATION_TYPE_INFO, null, "middle_center", 10000);
            } else {
                Clients.showNotification(manteniMD.getMsg() + "<br/>",
                        Clients.NOTIFICATION_TYPE_WARNING, null, "middle_center", 10000);
            }

        } else {
            Clients.showNotification("NO PUEDE DEJAR <br/>  <br/>  CAMPOS VACIOS <br/> <br/>INTENTELO DE NUEVO",
                    Clients.NOTIFICATION_TYPE_WARNING, null, "middle_center", 0);
        }
    }

    public void onChange$numarribo(Event e) throws SQLException, ClassNotFoundException {
        manteniMD = new CitasPlanificacionMd();
        manteniMD2 = new CitasPlanificacionMd();

        anioarribo.setDisabled(true);
        manteniMD = ManbuDal.MostrarProducto(anioarribo.getText(), numarribo.getText());
        manteniMD2 = ManbuDal.MostrarNa_Eta(anioarribo.getText(), numarribo.getText());
        allProductos1 = ManbuDal.RSelect();
        nomNaviera.setText(manteniMD2.getNomNaviera());
        eta.setText(manteniMD2.getEta());
        nomBuque.setText(manteniMD2.getNomBuque());
        etaP.setText(manteniMD.getEtap());

        if (manteniMD.getResp().equals("1")) {
            fecha.setText(manteniMD.getFecha_hora());
            lugar.setText(manteniMD.getLugar());
            obs.setText(manteniMD.getObser());

//            estado.setText(manteniMD.getEstado());
//            atracadero.setText(manteniMD.getAtracadero());
            // planificador.setText(desktop.getSession().getAttribute("USER").toString());
//            fechaalta.setText(manteniMD.getFecha_alta());
//            usuarioalta.setText(manteniMD.getUsuario_alta());
        } else {

            anioarribo.setDisabled(false);
            fecha.setText("");
            lugar.setText("");
            obs.setText("");

        }
    }

    public void onClick$btnNuevo() {
        clear();
    }

    public void clear() {

        anioarribo.setText("");
        anioarribo.setDisabled(false);
        numarribo.setText("");
        lugar.setText("");
        fecha.setText("");
        nomNaviera.setText("");
        eta.setText("");
        nomBuque.setText("");
        etaP.setText("");
        obs.setText("");
        anioarribo.focus();

    }

    public void onClick$btnSalir() {
        rootPagina.setSrc("/Views/Principal.zul");
    }

    public void onClick$btnhistori() {
        rootPagina.setSrc("/Views/ReporteCitasPlani.zul");
    }
}
