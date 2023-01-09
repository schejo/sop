/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ctrl;

import DAL.ManteServiciosDal;
import MD.ManteServiciosMd;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Textbox;

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

    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);

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
