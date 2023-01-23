package ctrl;

import DAL.CatalogoDal;
import DAL.ManteServiciosDal;
import MD.CatalogosMd;
import MD.ManteServiciosMd;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.EventQueues;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Include;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

/**
 *
 * @author HP 15
 */
public class ManteServiciosCtrl extends GenericForwardComposer {

    private Textbox txtAnioArribo;
    private Textbox txtNumArribo;
    private Textbox txtCodigoBuque;
    private Textbox txtNombreBuque;
    private Textbox txtTrbBuque;
    private Textbox cod_serv;
    private Combobox nombreServicio;
    private Textbox cod_part;
    private Combobox nombreParticular;
    private Textbox cod_cli;
    private Combobox nombreCliente;
    private Datebox fechaInicio;
    private Datebox fechaFin;
    private Button btnBusca1;
    private Button btninsert;
    private Button btnBusAct;
    private Button btnUpdate;
    private Doublebox boleta;
    private Textbox observaciones;

    ManteServiciosMd serviciosModelo = new ManteServiciosMd();
    ManteServiciosMd InsertModelo = new ManteServiciosMd();
    ManteServiciosMd UpdatetModelo = new ManteServiciosMd();
    ManteServiciosMd borrajeModelo = new ManteServiciosMd();
    ManteServiciosMd correModelo = new ManteServiciosMd();
    ManteServiciosDal ProductoDal = new ManteServiciosDal();
    CatalogoDal ctd = new CatalogoDal();
    List<CatalogosMd> lista = new ArrayList<CatalogosMd>();

    List<ManteServiciosMd> allservicios = new ArrayList<ManteServiciosMd>();
    List<ManteServiciosMd> allparticulares = new ArrayList<ManteServiciosMd>();
    List<ManteServiciosMd> allclientes = new ArrayList<ManteServiciosMd>();
    private String correla = null;
    private Include rootPagina;

    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);

        btnBusca1.setVisible(false);
        btninsert.setVisible(false);
        btnUpdate.setVisible(false);
        btnBusAct.setVisible(false);
        nombreParticular.setVisible(false);
        nombreCliente.setVisible(false);
        nombreServicio.setVisible(false);

        allservicios = ProductoDal.Servicios();
        nombreServicio.setModel(new ListModelList(allservicios));
        allparticulares = ProductoDal.Particulares();
        nombreParticular.setModel(new ListModelList(allparticulares));
        allclientes = ProductoDal.Clientes();
        nombreCliente.setModel(new ListModelList(allclientes));

        EventQueues.lookup("myEventQueue", EventQueues.DESKTOP, true)
                .subscribe(new EventListener() {
                    public void onEvent(Event event) throws Exception {
                        List<CatalogosMd> data = new ArrayList<CatalogosMd>();
                        data.clear();
                        data = (List<CatalogosMd>) event.getData();
                        if (data.isEmpty()) {
                            cod_serv.setText("");
                            nombreServicio.setText("");
                            cod_part.setText("");
                            cod_cli.setText("");
                            fechaInicio.setText("");
                            fechaFin.setText("");
                            boleta.setText("");
                            observaciones.setText("");
                            btninsert.setVisible(true);

                        } else {
                            for (CatalogosMd item : data) {
                                if (data.size() == 1) {
                                    cod_serv.setText(item.getCodigo());
                                    nombreServicio.setText(item.getNom_servicio());
                                    cod_part.setText(item.getCodparti());
                                    cod_cli.setText(item.getCodcliFac());
                                    fechaInicio.setText(item.getFecha_inicio());
                                    fechaFin.setText(item.getFecha_fin());
                                    boleta.setText(item.getBoleta());
                                    observaciones.setText(item.getObs());
                                    correla = item.getCorrela();
                                    nombreCliente.setSelectedIndex(-1);
                                    nombreParticular.setText("");
                                    nombreServicio.setText("");
                                    nombreParticular.setVisible(false);
                                    nombreCliente.setVisible(false);
                                    nombreServicio.setVisible(false);

                                    //aqui va el boton update
                                    btnUpdate.setVisible(true);
                                    btninsert.setVisible(false);

                                }
                            }
                        }
                    }
                });

    }
    
        public void onClick$btnBusAct(Event e) {


        session.setAttribute("anioArribo", txtAnioArribo.getText());
        session.setAttribute("numeroArribo", txtNumArribo.getText());

        //INVOCAR MODAL
        Window window = (Window) Executions.createComponents(
                "/Views/VerActividad.zul", null, null);
        window.doModal();
    }

    public void onClick$btnDelete(Event e) throws SQLException {
        Messagebox.show("Estas Seguro Que Deseas Anular la Boleta " + boleta.getText() + "?",
                "Question", Messagebox.OK | Messagebox.CANCEL,
                Messagebox.QUESTION,
                new org.zkoss.zk.ui.event.EventListener() {
            public void onEvent(Event e) throws SQLException, ClassNotFoundException {
                if (Messagebox.ON_OK.equals(e.getName())) {
                    borrajeModelo = ProductoDal.REGdelete(txtAnioArribo.getText(), txtNumArribo.getText(), correla);
                    Clients.evalJavaScript("msj('" + borrajeModelo.getMsg() + "','success')");
                    clear();
                } else if (Messagebox.ON_CANCEL.equals(e.getName())) {
                    Clients.showNotification("BOLETA NO SE HA <br/> ANULADO <br/>",
                            "warning", null, "middle_center", 5000);
                }
            }
        }
        );
    }

    public void onClick$btnUpdate(Event e) throws SQLException, ClassNotFoundException, ParseException {
        UpdatetModelo = new ManteServiciosMd();
        UpdatetModelo.setAnoArri(txtAnioArribo.getText().toUpperCase());
        UpdatetModelo.setNumArri(txtNumArribo.getText().toUpperCase());
        UpdatetModelo.setCod_servicio(cod_serv.getText().toUpperCase());
        UpdatetModelo.setCod_particular(cod_part.getText().toUpperCase());
        UpdatetModelo.setCod_cliente(cod_cli.getText().toUpperCase());
        UpdatetModelo.setFechaInicio(fechaInicio.getText().toUpperCase());
        UpdatetModelo.setHoraInicio(fechaInicio.getText().toUpperCase());
        UpdatetModelo.setFechaFin(fechaFin.getText().toUpperCase());
        UpdatetModelo.setHoraFin(fechaFin.getText().toUpperCase());
        UpdatetModelo.setBoleta(boleta.getText().toUpperCase());
        UpdatetModelo.setUsuario(desktop.getSession().getAttribute("USER").toString());
        UpdatetModelo.setObs(observaciones.getText().toUpperCase());
        UpdatetModelo.setCorrelativo(correla);

        UpdatetModelo = ProductoDal.updatePro(UpdatetModelo);
        if (UpdatetModelo.getResp().equals("1")) {
            clear();
//                bloquear();
//                ubiFerrete.setSelectedIndex(-1);
            Clients.showNotification(UpdatetModelo.getMsg() + "<br/>",
                    Clients.NOTIFICATION_TYPE_INFO, null, "middle_center", 3000);
        } else {
            Clients.showNotification(UpdatetModelo.getMsg() + "<br/>",
                    Clients.NOTIFICATION_TYPE_WARNING, null, "middle_center", 3000);
        }

    }

    public void onClick$btninsert(Event e) throws ClassNotFoundException, SQLException {
//        correModelo = new ManteServiciosMd();
//        correModelo = ProductoDal.OpteCorre(txtAnioArribo.getText(), txtNumArribo.getText());

        InsertModelo.setAnoArri(txtAnioArribo.getText().toUpperCase());
        InsertModelo.setNumArri(txtNumArribo.getText().toUpperCase());
//        InsertModelo.setCorrelativo(correModelo.getCorrelativo());
        InsertModelo.setCod_particular(cod_part.getText().toUpperCase());
        InsertModelo.setCod_servicio(cod_serv.getText().toUpperCase());
        InsertModelo.setFechaInicio(fechaInicio.getText().toUpperCase());
        InsertModelo.setHoraInicio(fechaInicio.getText().toUpperCase());
        InsertModelo.setFechaFin(fechaFin.getText().toUpperCase());
        InsertModelo.setHoraFin(fechaFin.getText().toUpperCase());
        InsertModelo.setBoleta(boleta.getText().toUpperCase());
        InsertModelo.setUsuario(desktop.getSession().getAttribute("USER").toString());
        InsertModelo.setObs(observaciones.getText().toUpperCase());
        InsertModelo.setCod_cliente(cod_cli.getText().toUpperCase());

        InsertModelo = ProductoDal.saveIngreso(InsertModelo);
        if (InsertModelo.getResp().equals("1")) {
            clear();

            Clients.showNotification(InsertModelo.getMsg() + "<br/>",
                    Clients.NOTIFICATION_TYPE_INFO, null, "middle_center", 3000);
        } else {
            Clients.showNotification(InsertModelo.getMsg() + "<br/>",
                    Clients.NOTIFICATION_TYPE_WARNING, null, "middle_center", 3000);
        }

    }

    //aqui inicio las acciones del primer codigo
    public void onClick$cod_serv(Event e) {
        onChange$cod_serv(e);
    }

    public void onChange$cod_serv(Event e) {
        BuscaItem(cod_serv.getText(), this.nombreServicio);
        nombreServicio.setVisible(true);

    }

    public void onSelect$nombreServicio(Event evt) throws SQLException {
        cod_serv.setText(nombreServicio.getSelectedItem().getValue().toString());
    }
    //aqui inicio las acciones del segundo codigo

    public void onClick$cod_part(Event e) {
        onChange$cod_part(e);
    }

    public void onChange$cod_part(Event e) {
        BuscaItem(cod_part.getText(), this.nombreParticular);
        nombreParticular.setVisible(true);

    }

    public void onSelect$nombreParticular(Event evt) throws SQLException {
        cod_part.setText(nombreParticular.getSelectedItem().getValue().toString());
    }

    //aqui inicio las acciones del segundo codigo
    public void onClick$cod_cli(Event e) {
        onChange$cod_cli(e);
    }

    public void onChange$cod_cli(Event e) {
        BuscaItem(cod_cli.getText(), this.nombreCliente);
        nombreCliente.setVisible(true);

    }

    public void onSelect$nombreCliente(Event evt) throws SQLException {
        cod_cli.setText(nombreCliente.getSelectedItem().getValue().toString());
    }

    public void BuscaItem(String letra, Combobox cb) {
        for (int i = 0; i < cb.getItemCount(); i++) {
            if (letra.equals(cb.getItemAtIndex(i).getValue())) {
                cb.setSelectedIndex(i);
                break;
            }
        }
    }
    Session session = Sessions.getCurrent();

    public void onClick$btnBusca1(Event e) {
//        btninsert.setVisible(false);
//         lista = ctd.consulta(txtAnioArribo.getText(), txtNumArribo.getText());
//
//        EventQueues.lookup("myEventQueue", EventQueues.DESKTOP, true)
//                .publish(new Event("onChangeNickname", null, lista));
        session.setAttribute("anioArribo", txtAnioArribo.getText());
        session.setAttribute("numeroArribo", txtNumArribo.getText());

        //INVOCAR MODAL
        Window window = (Window) Executions.createComponents(
                "/Views/BuscarServicios.zul", null, null);
        window.doModal();
    }

    public void onOK$txtNumArribo(Event evt) {
        onChange$txtNumArribo(evt);
    }

    public void onChange$txtNumArribo(Event evt) {

        if (!txtAnioArribo.getText().equals("") && !txtNumArribo.getText().equals("")) {
            serviciosModelo = new ManteServiciosMd();
            serviciosModelo = ProductoDal.MostrarProducto(txtAnioArribo.getText(), txtNumArribo.getText());
            if (serviciosModelo.getResp().equals("1")) {
                txtCodigoBuque.setText(serviciosModelo.getNumBuque());
                txtNombreBuque.setText(serviciosModelo.getNomBuque());
                txtTrbBuque.setText(serviciosModelo.getTrb());
                btnBusca1.setVisible(true);
                btninsert.setVisible(true);
                btnBusAct.setVisible(true);

            } else {
                btninsert.setVisible(false);
                Clients.evalJavaScript("msj('ERROR " + serviciosModelo.getMsg() + "','error')");
            }
        }

    }

    public void clear() {

        txtCodigoBuque.setText("");
        txtNombreBuque.setText("");
        txtTrbBuque.setText("");
        cod_serv.setText("");
        nombreServicio.setSelectedIndex(-1);
        cod_part.setText("");
        nombreParticular.setSelectedIndex(-1);
        cod_cli.setText("");
        nombreCliente.setSelectedIndex(-1);
        fechaInicio.setText("");
        fechaFin.setText("");
        boleta.setText("");
        observaciones.setText("");
        btnUpdate.setVisible(false);
        btninsert.setVisible(false);
        btnBusca1.setVisible(false);

    }

    public void onClick$btnSalir() {
        rootPagina.setSrc("/Views/Principal.zul");
    }
}
