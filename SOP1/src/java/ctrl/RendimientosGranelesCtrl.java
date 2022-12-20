package ctrl;

import DAL.RendimientosGranelesDal;
import MD.RendimientosGranelesMd;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
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
    private Textbox rendibuque;

    String lb;

    List<RendimientosGranelesMd> alltipoact = new ArrayList<RendimientosGranelesMd>();
    RendimientosGranelesMd manteniMD1 = new RendimientosGranelesMd();
    RendimientosGranelesDal ManbuDal = new RendimientosGranelesDal();
    RendimientosGranelesDal rg = new RendimientosGranelesDal();

    // private Listbox lb2;
    private Include rootPagina;

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        alltipoact = rg.tipoactRSelect();
        producto.setModel(new ListModelList(alltipoact));

    }

    public void onChange$num_arribo(Event e) throws SQLException {
        manteniMD1 = new RendimientosGranelesMd();
        manteniMD1 = ManbuDal.Rendimientos(anio_arribo.getText(), num_arribo.getText());

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
            tmplanificadas.setText(manteniMD1.getTmplanificadas());
            producto.setText(manteniMD1.getTipo_producto());
            directa.setText(manteniMD1.getTipo_producto());
            tmdespachadas.setText(manteniMD1.getTm_despachadas());
            gruas.setText(manteniMD1.getGruas_buque());
            otros.setText(manteniMD1.getOtros());
            hrs_operacion.setText(manteniMD1.getTotal_hrs_operacion());
            gruasolg.setText(manteniMD1.getGruas_olg());
            rendibuque.setText(manteniMD1.getRendi_hr_buque());

        } else {
            clear();

            Clients.showNotification(manteniMD1.getMsg() + "<br/>",
                    Clients.NOTIFICATION_TYPE_WARNING, null, "middle_center", 3000);
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
        producto.setText("");
        directa.setText("");
        tmdespachadas.setText("");
        gruas.setText("");
        otros.setText("");
        hrs_operacion.setText("");
        gruasolg.setText("");
        rendibuque.setText("");
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
