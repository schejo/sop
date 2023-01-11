package ctrl;

import DAL.CatalogoDal;
import DAL.ManteServiciosDal;
import MD.CatalogosMd;
import MD.ManteServiciosMd;
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
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
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
    private Textbox cod_serv;
    private Combobox nombreServicio;
    private Textbox cod_part;
    private Combobox nombreParticular;
    private Textbox cod_cli;
    private Combobox nombreCliente;
    private Datebox fechaInicio;

    ManteServiciosMd serviciosModelo = new ManteServiciosMd();
    ManteServiciosDal ProductoDal = new ManteServiciosDal();
    CatalogoDal ctd = new CatalogoDal();
    List<CatalogosMd> lista = new ArrayList<CatalogosMd>();

    List<ManteServiciosMd> allservicios = new ArrayList<ManteServiciosMd>();
    List<ManteServiciosMd> allparticulares = new ArrayList<ManteServiciosMd>();
    List<ManteServiciosMd> allclientes = new ArrayList<ManteServiciosMd>();

    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);

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
                            cod_part.setText("");
                            cod_cli.setText("");
                            fechaInicio.setText("");
                            nombreServicio.setText("");

                        } else {
                            for (CatalogosMd item : data) {
                                if (data.size() == 1) {
                                    cod_serv.setText(item.getCodigo());
                                    nombreServicio.setText(item.getNom_servicio());
                                    cod_part.setText(item.getCodparti());
                                    cod_cli.setText(item.getCodcliFac());
                                    fechaInicio.setText(item.getFecha_inicio());
//                                 txtProductoId.setFocus(true);
                                }
                            }
                        }
                    }
                });

    }
    Session session = Sessions.getCurrent();

    public void onClick$btnBusca1(Event e) {
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

    public void onChange$txtNumArribo(Event evt) {

        if (!txtAnioArribo.getText().equals("") && !txtNumArribo.getText().equals("")) {
            serviciosModelo = new ManteServiciosMd();
            serviciosModelo = ProductoDal.MostrarProducto(txtAnioArribo.getText(), txtNumArribo.getText());
            if (serviciosModelo.getResp().equals("1")) {
                txtCodigoBuque.setText(serviciosModelo.getNumBuque());
                txtNombreBuque.setText(serviciosModelo.getNomBuque());

            } else {

                Clients.evalJavaScript("msj('ERROR " + serviciosModelo.getMsg() + "','error')");
            }
        }

    }

}
