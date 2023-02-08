/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ctrl;

import MD.CataloAcBuqueMd;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.EventQueues;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

/**
 *
 * @author Usuario
 */
public class BuscaBuqueCtrl extends GenericForwardComposer {

    Window modalDialog;
    private Textbox bemp1;
    private Textbox anoAc;
    private Textbox numeroAc;
    private Listbox lb2;
    List<CataloAcBuqueMd> lista = new ArrayList<CataloAcBuqueMd>();
    List<CataloAcBuqueMd> data = new ArrayList<CataloAcBuqueMd>();

    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        bemp1.setFocus(true);
        EventQueues.lookup("myEventQueue", EventQueues.DESKTOP, true)
                .subscribe(new EventListener() {
                    public void onEvent(Event event) throws Exception {

                        lista = (List<CataloAcBuqueMd>) event.getData();

                    }
                });

    }

    public void onChanging$bemp1(InputEvent event) {
        data.clear();
        String byEvent = event.getValue(); //This will give you the new value
        System.out.println("TECLEADO.: " + byEvent);
        for (CataloAcBuqueMd item : lista) {
            if (!byEvent.equals("") && item.getBuque().contains(byEvent.toUpperCase())) {
                CataloAcBuqueMd da = new CataloAcBuqueMd();
                da.setAnoAc(item.getAnoAc());
                da.setNumAc(item.getNumAc());
                da.setFecha_atraque(item.getFecha_atraque());
                da.setBuque(item.getBuque());
                da.setEstado(item.getEstado());
                da.setTipoBu(item.getTipoBu());

                data.add(da);
            }
        }
        lb2.setModel(new ListModelList(data));
    }

       public void onClick$btnAgregar(Event e) {
        List<CataloAcBuqueMd> items = new ArrayList<CataloAcBuqueMd>();
        CataloAcBuqueMd data = new CataloAcBuqueMd();
        data.setAnoAc(anoAc.getText());
        data.setNumAc(numeroAc.getText());
        
        items.add(data);
        EventQueues.lookup("myEventSelecBuque", EventQueues.DESKTOP, true)
                .publish(new Event("onChangeNickname", null, items));
        modalDialog.detach();
    }
    public void onClick$btnSalir(Event e) {

        modalDialog.detach();
    }

}
