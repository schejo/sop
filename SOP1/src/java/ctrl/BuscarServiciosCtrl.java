/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ctrl;

import MD.CatalogosMd;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.EventQueues;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Window;

/**
 *
 * @author HP 15
 */
public class BuscarServiciosCtrl extends GenericForwardComposer{
     Window modalDialog;
     List<CatalogosMd> lista = new ArrayList<CatalogosMd>();
     List<CatalogosMd> data = new ArrayList<CatalogosMd>();
     private Listbox lb2;
     
     
         public void doAfterCompose (Component comp) throws Exception {
         super.doAfterCompose(comp);
           EventQueues.lookup("myEventQueue", EventQueues.DESKTOP, true)
                .subscribe(new EventListener() {
                    public void onEvent(Event event) throws Exception {
                        lista = (List<CatalogosMd>) event.getData();
                    }
                });
            
        
    }
    
    
    
    public void onClick$btnAgregar1(Event e) {
         data.clear();
         
        for(CatalogosMd item : lista){
           
                CatalogosMd da = new CatalogosMd();
                da.setCodigo(item.getCodigo());
                da.setServi(item.getServi());
                da.setParti(item.getParti());
                
                data.add(da);
            
        }
        lb2.setModel(new ListModelList(data));
        
    }
        
     public void onClick$btnSalir(Event e) {
        
        

     modalDialog.detach();
    }
    
}
