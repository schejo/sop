/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ctrl;

import DAL.CatalogoDal;
import MD.CatalogosMd;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.EventQueues;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

/**
 *
 * @author HP 15
 */
public class BuscarServiciosCtrl extends GenericForwardComposer {

    Window modalDialog;
    List<CatalogosMd> lista = new ArrayList<CatalogosMd>();
    List<CatalogosMd> data = new ArrayList<CatalogosMd>();
    private Listbox lb2;
    CatalogoDal ctd = new CatalogoDal();
    Session Session = Sessions.getCurrent();
    private Textbox cod1;
    private Textbox codparti;
    private Textbox codcliFac;
    private Textbox fecha_inicio;

    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
//           EventQueues.lookup("myEventQueue", EventQueues.DESKTOP, true)
//                .subscribe(new EventListener() {
//                    public void onEvent(Event event) throws Exception {
//                        lista = (List<CatalogosMd>) event.getData();
//                    }
//                });
        lista = ctd.consulta(Session.getAttribute("anioArribo").toString(), Session.getAttribute("numeroArribo").toString());
        data.clear();

        for (CatalogosMd item : lista) {

            CatalogosMd da = new CatalogosMd();
            da.setCodigo(item.getCodigo());
            da.setServi(item.getServi());
            da.setParti(item.getParti());
            da.setCodparti(item.getCodparti());
            da.setCodcliFac(item.getCodcliFac());
            da.setFecha_inicio(item.getFecha_inicio());

            data.add(da);

        }
        lb2.setModel(new ListModelList(data));

    }

    public void onClick$btnAgregar(Event e) {
        List<CatalogosMd> items = new ArrayList<CatalogosMd>();
        CatalogosMd data = new CatalogosMd();
        data.setCodigo(cod1.getText());
        data.setCodparti(codparti.getText());
        data.setCodcliFac(codcliFac.getText());
        data.setFecha_inicio(fecha_inicio.getText());

        items.add(data);
        EventQueues.lookup("myEventQueue", EventQueues.DESKTOP, true)
                .publish(new Event("onChangeNickname", null, items));
        modalDialog.detach();
    }

    public void onClick$btnSalir(Event e) {

        modalDialog.detach();
    }

}
