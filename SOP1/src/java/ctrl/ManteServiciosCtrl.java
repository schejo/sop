package ctrl;

import DAL.CatalogoDal;
import DAL.ManteServiciosDal;
import MD.CatalogosMd;
import MD.ManteServiciosMd;
import java.sql.SQLException;
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
    private Datebox fechaFin;
    private Button btnBusca1;
    private Doublebox boleta;
    private Textbox observaciones;
    

    ManteServiciosMd serviciosModelo = new ManteServiciosMd();
    ManteServiciosDal ProductoDal = new ManteServiciosDal();
    CatalogoDal ctd = new CatalogoDal();
    List<CatalogosMd> lista = new ArrayList<CatalogosMd>();

    List<ManteServiciosMd> allservicios = new ArrayList<ManteServiciosMd>();
    List<ManteServiciosMd> allparticulares = new ArrayList<ManteServiciosMd>();
    List<ManteServiciosMd> allclientes = new ArrayList<ManteServiciosMd>();

    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        nombreServicio.setVisible(false);
        btnBusca1.setVisible(false);
        nombreParticular.setVisible(false);
        nombreCliente.setVisible(false);

//        allservicios = ProductoDal.Servicios();
//        nombreServicio.setModel(new ListModelList(allservicios));
//        allparticulares = ProductoDal.Particulares();
//        nombreParticular.setModel(new ListModelList(allparticulares));
//        allclientes = ProductoDal.Clientes();
//        nombreCliente.setModel(new ListModelList(allclientes));

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

                                }
                            }
                        }
                    }
                });

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
                btnBusca1.setVisible(true);

            } else {

                Clients.evalJavaScript("msj('ERROR " + serviciosModelo.getMsg() + "','error')");
            }
        }

    }

}
