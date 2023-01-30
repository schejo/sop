
package ctrl;

import DAL.VerActividadDal;
import MD.CatalogosMd;
import MD.VerActividadMd;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventQueues;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Include;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class VerActividadCtrl extends GenericForwardComposer {
    private Include rootPagina;

    Window modalDialog;
    List<VerActividadMd> lista = new ArrayList<VerActividadMd>();
    List<VerActividadMd> data = new ArrayList<VerActividadMd>();
    private Listbox lb2;
    VerActividadDal ctd = new VerActividadDal();
    Session Session = Sessions.getCurrent();

    private Textbox nom_actividad;
    private Textbox fecha_actividad;
    private Textbox hora_actividad;
    private Textbox nombre_duracion;
    private Textbox duracion;
    private Textbox numActiv;

    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);

        lista = ctd.consulta(Session.getAttribute("anioArribo").toString(), Session.getAttribute("numeroArribo").toString());
        data.clear();

        for (VerActividadMd item : lista) {

            VerActividadMd da = new VerActividadMd();
            da.setActividad(item.getActividad());
            da.setFecha_act(item.getFecha_act());
            da.setHora_act(item.getHora_act());
            da.setNom_duracion(item.getNom_duracion());
            da.setDuracion(item.getDuracion());
            da.setNumArrib(item.getNumArrib());
            da.setNumActiv(item.getNumActiv());

            data.add(da);

        }
        lb2.setModel(new ListModelList(data));

    }

    public void onClick$btnAgregar(Event e) {
        List<VerActividadMd> items = new ArrayList<VerActividadMd>();
        VerActividadMd data = new VerActividadMd();
        data.setAnoArriBo(Session.getAttribute("anioArribo").toString());
        data.setNumArrib(Session.getAttribute("numeroArribo").toString());
        data.setNumActiv(numActiv.getText());
       

        items.add(data);
       EventQueues.lookup("myEventQueue1", EventQueues.DESKTOP, true)
               .publish(new Event("onChangeNickname", null, items));
      modalDialog.detach();
    

       

    }

    public void onClick$btnSalir(Event e) {
        List<VerActividadMd> items = new ArrayList<VerActividadMd>();
        EventQueues.lookup("myEventQueue", EventQueues.DESKTOP, true)
                .publish(new Event("onChangeNickname", null, items));

        modalDialog.detach();

    }


}
