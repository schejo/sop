package ctrl;

import DAL.RendimientosGranelesDal;
import MD.RendimientosGranelesMd;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Include;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Timebox;

public class RendimientosGranelesCtrl extends GenericForwardComposer {
    
    private Textbox anio_arribo;
    private Textbox num_arribo;
    private Textbox nomBuque;
    private Textbox naviera;
    private Textbox estibadora;
    private Textbox muelle;
    private Textbox fech_atraque;
    private Textbox hrs_plani;
    private Textbox fech_zarpe;
    private Doublebox terpac;
    private Doublebox tmplanificadas;
    private Combobox producto;
    private Doublebox directa;
    private Doublebox tmdespachadas;
    private Doublebox gruas;
    private Doublebox otros;
    private Timebox hrs_operacion;
    private Textbox gruasolg;
    private Doublebox rendibuque;
    private Textbox iniopera;
    private Textbox finopera;
    private Textbox totalHoras;
    
    String lb;
    
    List<RendimientosGranelesMd> tipoprod = new ArrayList<RendimientosGranelesMd>();
    RendimientosGranelesMd manteniMD1 = new RendimientosGranelesMd();
    RendimientosGranelesDal ManbuDal = new RendimientosGranelesDal();
    RendimientosGranelesDal rg = new RendimientosGranelesDal();

    // private Listbox lb2;
    private Include rootPagina;
    
    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        tipoprod = rg.tipoactRSelect();
        producto.setModel(new ListModelList(tipoprod));
        terpac.setFormat("###0.###");
        terpac.setLocale(Locale.US);
        tmplanificadas.setFormat("###0.###");
        tmplanificadas.setLocale(Locale.US);
        directa.setFormat("###0.###");
        directa.setLocale(Locale.US);
        tmdespachadas.setFormat("###0.###");
        tmdespachadas.setLocale(Locale.US);
        
        rendibuque.setFormat("###0.###");
        rendibuque.setLocale(Locale.US);
        
    }
    
    public void onChange$terpac(Event e) {
        if (terpac.getText().equals("")) {
            Clients.showNotification("<br/>" + "TERPAC NO PUEDE ESTAR VACIO",
                    Clients.NOTIFICATION_TYPE_WARNING, null, "middle_center", 3000);
            terpac.setText("0");
            double directad = Double.parseDouble(directa.getText());
            double terpacd = Double.parseDouble(terpac.getText());
            
            double total = directad + terpacd;
            tmdespachadas.setText(String.valueOf(total));
            
            terpac.focus();
        } else {
            double directad = Double.parseDouble(directa.getText());
            double terpacd = Double.parseDouble(terpac.getText());
            //para calcular el rendimiento
            double total = directad + terpacd;
            tmdespachadas.setText(String.valueOf(total));
            
            double rendiHora = total / horasOperacion;
            rendibuque.setText(String.valueOf(rendiHora));
            
        }
        
    }
    
    public void onChange$directa(Event e) {
        if (directa.getText().equals("")) {
            Clients.showNotification("<br/>" + "DIRECTA NO PUEDE ESTAR VACIO",
                    Clients.NOTIFICATION_TYPE_WARNING, null, "middle_center", 3000);
            directa.setText("0");
            double directad = Double.parseDouble(directa.getText());
            double terpacd = Double.parseDouble(terpac.getText());
            
            double total = directad + terpacd;
            tmdespachadas.setText(String.valueOf(total));
            
            terpac.focus();
        } else {
            double directad = Double.parseDouble(directa.getText());
            double terpacd = Double.parseDouble(terpac.getText());
            //para calcular el rendimiento
            double total = directad + terpacd;
            tmdespachadas.setText(String.valueOf(total));
            double rendiHora = total / horasOperacion;
            rendibuque.setText(String.valueOf(rendiHora));
            
        }
        
    }
    
    double horasOperacion = 0;
    
    public void onChange$num_arribo(Event e) throws SQLException {
        manteniMD1 = new RendimientosGranelesMd();
        manteniMD1 = ManbuDal.Rendimientos(anio_arribo.getText(), num_arribo.getText());
        String a, b;
        if (manteniMD1.getResp().equals("1")) {
            
            anio_arribo.setText(manteniMD1.getAnio());
            num_arribo.setText(manteniMD1.getArribo());
            nomBuque.setText(manteniMD1.getNombre_buque());
            naviera.setText(manteniMD1.getAg_naviera());
            estibadora.setText(manteniMD1.getEstibadora());
            muelle.setText(manteniMD1.getMuelle());
            fech_atraque.setText(manteniMD1.getFecha_atraque());
            hrs_plani.setText(manteniMD1.getHrs_plani());
            fech_zarpe.setText(manteniMD1.getFecha_zarpe());
            terpac.setText(manteniMD1.getTerpac());
            a = manteniMD1.getTerpac();
            // tmplanificadas.setText(manteniMD1.getTmplanificadas());
            BuscaItem(manteniMD1.getTipo_producto(), this.producto);
            //producto.setValue(manteniMD1.getTipo_producto());
            BuscaItem(manteniMD1.getTipo_producto(), this.producto);
            //  directa.setText(manteniMD1.getDir());
            directa.setText(manteniMD1.getTm_despachadas());
            b = manteniMD1.getTm_despachadas();
            gruas.setText(manteniMD1.getGruas_buque());
            otros.setText(manteniMD1.getOtros());
            // hrs_operacion.setText(manteniMD1.getTotal_hrs_operacion());
            gruasolg.setText(manteniMD1.getGruas_olg());
            rendibuque.setText(manteniMD1.getRendi_hr_buque());
            iniopera.setText(manteniMD1.getInicio_operacion());
            finopera.setText(manteniMD1.getFin_operacion());
            totalHoras.setText(manteniMD1.getTotal_hrs_operacion());
            double num = Double.parseDouble(a);
            double num2 = Double.parseDouble(b);
            double suma = num + num2;
            tmplanificadas.setText(Double.toString(suma));
            horasOperacion = Double.parseDouble(manteniMD1.getHorasOperacion());
            //  tmdespachadas.setText(Double.toString(suma));

        } else {
            clear();
            
            Clients.showNotification(manteniMD1.getMsg() + "<br/>",
                    Clients.NOTIFICATION_TYPE_WARNING, null, "middle_center", 3000);
        }
        
    }
    
    public void BuscaItem(String letra, Combobox cb) {
        for (int i = 0; i < cb.getItemCount(); i++) {
            if (letra.equals(cb.getItemAtIndex(i).getLabel())) {
                cb.setSelectedIndex(i);
                break;
            }
        }
    }
    
    public void clear() {
        
        anio_arribo.setText("");
        num_arribo.setText("");
        nomBuque.setText("");
        naviera.setText("");
        estibadora.setText("");
        muelle.setText("");
        fech_atraque.setText("");
        hrs_plani.setText("");
        fech_zarpe.setText("");
        terpac.setText("");
        tmplanificadas.setText("");
        producto.setSelectedIndex(-1);
        directa.setText("");
        tmdespachadas.setText("");
        gruas.setText("");
        otros.setText("");
        gruasolg.setText("");
        rendibuque.setText("");
        iniopera.setText("");
        finopera.setText("");
        totalHoras.setText("");
        anio_arribo.focus();
        
    }
    
    public void onClick$btnNuevo() {
        clear();
    }
    //boton para modificar

    public void onClick$btnGuardar(Event e) throws SQLException, ClassNotFoundException, ParseException {
        Clients.showNotification("No se Puede Modificar <br/>Comunicarse con informatica",
                Clients.NOTIFICATION_TYPE_WARNING, null, "middle_center", 3000);
        
    }
    
    public void onClick$btnSalir() {
        rootPagina.setSrc("/Views/Principal.zul");
    }
}
