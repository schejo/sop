/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ctrl;

import DAL.cicloCamionDal;
import MD.cicloCamionMd;
import java.sql.SQLException;
import java.text.ParseException;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

/**
 *
 * @author HP 15
 */
public class cicloCamionCtrl extends GenericForwardComposer {

    private Doublebox ciclo1;
    private Datebox fecha;
    private Textbox licencia;
    private Textbox cdPais;
    private Doublebox seccDestino;
    private Doublebox cdDestino;
    private Datebox fingRe;
    private Textbox tOpera;
    private Doublebox aArribo;
    private Doublebox nArribo;
    private Textbox placa;
    private Datebox fAlta;
    private Textbox ingCont1;
    private Textbox tiVia;
    private Textbox indicador;
    private Textbox ingCont2;
    private Textbox ingCont3;
    private Datebox fFinDat;
    private Datebox fIniDat;

    cicloCamionMd cicloModelo = new cicloCamionMd();
    cicloCamionMd borrajeModelo = new cicloCamionMd();
    cicloCamionDal ProductoDal = new cicloCamionDal();

    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        ciclo1.setDisabled(false);
        fecha.setDisabled(false);

    }
    
          public void onClick$btnDelete(Event e) throws SQLException {
            Messagebox.show("Estas Seguro Que Deseas Borrar El Ciclo "+ciclo1.getText()+"?",
                    "Question", Messagebox.OK | Messagebox.CANCEL,
                    Messagebox.QUESTION,
                    new org.zkoss.zk.ui.event.EventListener() {
                public void onEvent(Event e) throws SQLException, ClassNotFoundException {
                    if (Messagebox.ON_OK.equals(e.getName())) {
                        borrajeModelo=ProductoDal.REGdelete(fecha.getText(),ciclo1.getText());
                        Clients.evalJavaScript("msj('" + borrajeModelo.getMsg() + "','success')");
                          clear();
                    } else if (Messagebox.ON_CANCEL.equals(e.getName())) {
                        Clients.showNotification("REGISTRO NO SE HA <br/> BORRADO <br/>",
                                "warning", null, "middle_center", 5000);
                    }
                }
            }
            );
      }

    public void onClick$btnGuardar(Event e) throws SQLException, ClassNotFoundException, ParseException {

        cicloModelo.setFechaCiclo(fecha.getText().toUpperCase());
        cicloModelo.setCiclom(ciclo1.getText().toUpperCase());
        cicloModelo.setCodPais(cdPais.getText().toUpperCase());
        cicloModelo.setLicencia(licencia.getText().toUpperCase());
        cicloModelo.setCodDesti(cdDestino.getText().toUpperCase());
        cicloModelo.setSeccDesti(seccDestino.getText().toUpperCase());
        cicloModelo.setTipOpera(tOpera.getText().toUpperCase());
        cicloModelo.setfIngRec(fingRe.getText().toUpperCase());
        cicloModelo.setAnoArribo(aArribo.getText().toUpperCase());
        cicloModelo.setNumArribo(nArribo.getText().toUpperCase());
        cicloModelo.setNumPlaca(placa.getText().toUpperCase());
        cicloModelo.setfAlta(fAlta.getText().toUpperCase());
        cicloModelo.setIndicador(indicador.getText().toUpperCase());
        cicloModelo.setTipoVia(tiVia.getText().toUpperCase());
        cicloModelo.setIngCont1(ingCont1.getText().toUpperCase());
        cicloModelo.setIngCont2(ingCont2.getText().toUpperCase());
        cicloModelo.setIngCont3(ingCont3.getText().toUpperCase());

        cicloModelo = ProductoDal.updatePro(cicloModelo);
        if (cicloModelo.getResp().equals("1")) {

           
            Clients.evalJavaScript("msj('CICLO " + ciclo1.getText().toUpperCase() + " MODIFICADO CORRECTAMENTE','success')");
            clear();
        } else {
          Clients.evalJavaScript("msj('" + cicloModelo.getMsg() + "','error')");         
            Clients.showNotification("ERROR MODIFICAR <br/> <br/> REGISTROS! <br/> " + cicloModelo.getMsg(),
                    "warning", null, "middle_center", 0);
        }

    }

    public void onOK$ciclo1(Event e) {
        onChange$ciclo1(e);
    }

    public void onChange$ciclo1(Event e) {
        ciclo1.setDisabled(true);
        fecha.setDisabled(true);
        cicloModelo = new cicloCamionMd();
        cicloModelo = ProductoDal.MostrarProducto(fecha.getText(), ciclo1.getText());
        if (cicloModelo.getResp().equals("1")) {
            cdPais.setText(cicloModelo.getCodPais().trim());
            licencia.setText(cicloModelo.getLicencia());
            cdDestino.setText(cicloModelo.getCodDesti());
            seccDestino.setText(cicloModelo.getSeccDesti());
            tOpera.setText(cicloModelo.getTipOpera());
            fingRe.setText(cicloModelo.getfIngRec());
            aArribo.setText(cicloModelo.getAnoArribo());
            nArribo.setText(cicloModelo.getNumArribo());
            placa.setText(cicloModelo.getNumPlaca());
            fAlta.setText(cicloModelo.getfAlta());
            indicador.setText(cicloModelo.getIndicador());
            tiVia.setText(cicloModelo.getTipoVia());
            ingCont1.setText(cicloModelo.getIngCont1());
            ingCont2.setText(cicloModelo.getIngCont2());
            ingCont3.setText(cicloModelo.getIngCont3());
            fIniDat.setText(cicloModelo.getfIniDat());
            fFinDat.setText(cicloModelo.getfFinDat());

        } else {
            ciclo1.setDisabled(false);
            fecha.setDisabled(false);
            Clients.showNotification(cicloModelo.getMsg() + "<br/>",
                    Clients.NOTIFICATION_TYPE_WARNING, null, "middle_center", 3000);
        }

    }

    public void onClick$btnNuevo(Event e) throws SQLException {

        clear();

    }

    public void clear() {
        ciclo1.setDisabled(false);
        fecha.setDisabled(false);
        ciclo1.setText("");
        fecha.setText("");
        licencia.setText("");

        cdPais.setText("");
        seccDestino.setText("");
        cdDestino.setText("");
        fingRe.setText("");
        tOpera.setText("");
        aArribo.setText("");
        nArribo.setText("");
        placa.setText("");
        fAlta.setText("");
        ingCont1.setText("");
        tiVia.setText("");
        ingCont2.setText("");
        ingCont3.setText("");
        fFinDat.setText("");
        fIniDat.setText("");
        fecha.setText("");
        indicador.setText("");

    }

}
