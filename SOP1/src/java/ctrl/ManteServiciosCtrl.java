/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ctrl;

import DAL.CatalogoDal;
import DAL.ManteServiciosDal;
import MD.CatalogosMd;
import MD.ManteServiciosMd;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventQueues;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.GenericForwardComposer;
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
    ManteServiciosMd serviciosModelo = new ManteServiciosMd();
    ManteServiciosDal ProductoDal = new ManteServiciosDal();

    List<CatalogosMd> lista = new ArrayList<CatalogosMd>();
    CatalogoDal ctd = new CatalogoDal();

    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
       

    }

    public void onClick$btnBusca1(Event e) {
         lista = ctd.consulta(txtAnioArribo.getText(), txtNumArribo.getText());

        EventQueues.lookup("myEventQueue", EventQueues.DESKTOP, true)
                .publish(new Event("onChangeNickname", null, lista));

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
