/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ctrl;

import DAL.manCicloCamionDal;
import MD.manCicloCamionMd;
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
public class manCicloCamionCtrl extends GenericForwardComposer {
    
    private Doublebox ciclo1;
    private Datebox fecha;
    private Textbox contenedor;
    private Textbox pesoTaCont;
    private Textbox pesaj1;
    private Textbox pesaj2;
    private Textbox pesoB;
    private Textbox pesoT;
    private Datebox fePes1;
    private Datebox fePes2;
    private Textbox tick1;
    private Textbox tick2;
    private Doublebox bascu1;
    private Doublebox bascu2;
    private Doublebox emBascu1;
    private Doublebox emBascu2;
    private Textbox idcuadri;
    private Textbox indiTa;
    private Doublebox corre9;
    private Textbox tipoAct;
    
    manCicloCamionMd pesajeModelo = new manCicloCamionMd();
    manCicloCamionMd borrajeModelo = new manCicloCamionMd();
    manCicloCamionDal ProductoDal = new manCicloCamionDal();
    
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
        
        pesajeModelo.setFechaCiclo(fecha.getText().toUpperCase());
        pesajeModelo.setCiclom(ciclo1.getText().toUpperCase());
        pesajeModelo.setIdeContenedor(contenedor.getText().toUpperCase());
        pesajeModelo.setPesoTaraCont(pesoTaCont.getText().toUpperCase());
        pesajeModelo.setPesaje1(pesaj1.getText().toUpperCase());
        pesajeModelo.setPesaje2(pesaj2.getText().toUpperCase());
        pesajeModelo.setPesoBruto(pesoB.getText().toUpperCase());
        pesajeModelo.setPesoTara(pesoT.getText().toUpperCase());
        pesajeModelo.setFechaPesa1(fePes1.getText().toUpperCase());
        pesajeModelo.setFechaPesa2(fePes2.getText().toUpperCase());
        pesajeModelo.setTicket1(tick1.getText().toUpperCase());
        pesajeModelo.setTicket2(tick2.getText().toUpperCase());
        pesajeModelo.setBascula1(bascu1.getText().toUpperCase());
        pesajeModelo.setBascula2(bascu2.getText().toUpperCase());
        pesajeModelo.setEmpreBas1(emBascu1.getText().toUpperCase());
        pesajeModelo.setEmpreBas2(emBascu2.getText().toUpperCase());
        pesajeModelo.setIndTabla(indiTa.getText().toUpperCase());
        pesajeModelo.setIndCua(idcuadri.getText().toUpperCase());
        pesajeModelo.setCorre9(corre9.getText().toUpperCase());
        pesajeModelo.setTipoAct(tipoAct.getText().toUpperCase());
        
        pesajeModelo = ProductoDal.updatePro(pesajeModelo);
        if (pesajeModelo.getResp().equals("1")) {

//            
            Clients.evalJavaScript("msj('CICLO "+ciclo1.getText().toUpperCase()+" MODIFICADO CORRECTAMENTE','success')");
            clear();
        } else {
            Clients.evalJavaScript("msj('" + pesajeModelo.getMsg() + "','error')");
        }
        
    }
    
    public void clear() {
        ciclo1.setDisabled(false);
        fecha.setDisabled(false);
        ciclo1.setText("");
        fecha.setText("");
        contenedor.setText("");
        pesoTaCont.setText("");
        pesaj1.setText("");
        pesaj2.setText("");
        pesoB.setText("");
        pesoT.setText("");
        fePes1.setText("");
        fePes2.setText("");
        bascu1.setText("");
        tick2.setText("");
        tick1.setText("");
        bascu2.setText("");
        emBascu1.setText("");
        emBascu2.setText("");
        idcuadri.setText("");
        indiTa.setText("");
        corre9.setText("");
        tipoAct.setText("");
        ciclo1.setText("");
        
    }
    
    public void onClick$btnNuevo(Event e) throws SQLException {
        
        clear();
        
    }
    
    public void onOK$ciclo1(Event e) {
        onChange$ciclo1(e);
    }
    
    public void onChange$ciclo1(Event e) {
        ciclo1.setDisabled(true);
        fecha.setDisabled(true);
        pesajeModelo = new manCicloCamionMd();
        pesajeModelo = ProductoDal.MostrarProducto(fecha.getText(), ciclo1.getText());
        if (pesajeModelo.getResp().equals("1")) {
            contenedor.setText(pesajeModelo.getIdeContenedor().trim());
            pesoTaCont.setText(pesajeModelo.getPesoTaraCont());
            pesaj1.setText(pesajeModelo.getPesaje1());
            pesaj2.setText(pesajeModelo.getPesaje2());
            pesoB.setText(pesajeModelo.getPesoBruto());
            pesoT.setText(pesajeModelo.getPesoTara());
            fePes1.setText(pesajeModelo.getFechaPesa1());
            fePes2.setText(pesajeModelo.getFechaPesa2());
            tick1.setText(pesajeModelo.getTicket1());
            tick2.setText(pesajeModelo.getTicket2());
            bascu1.setText(pesajeModelo.getBascula1());
            bascu2.setText(pesajeModelo.getBascula2());
            emBascu1.setText(pesajeModelo.getEmpreBas1());
            emBascu2.setText(pesajeModelo.getEmpreBas2());
            indiTa.setText(pesajeModelo.getIndTabla());
            idcuadri.setText(pesajeModelo.getIndCua());
            corre9.setText(pesajeModelo.getCorre9());
            tipoAct.setText(pesajeModelo.getTipoAct());
            
        }else {
           ciclo1.setDisabled(false);
        fecha.setDisabled(false);
            Clients.showNotification(pesajeModelo.getMsg() + "<br/>",
                    Clients.NOTIFICATION_TYPE_WARNING, null, "middle_center", 3000);
        }
        
    }
    
}
