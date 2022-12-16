/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ctrl;
/*
import MD.WSContenedoresMD;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import org.tempuri.*;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;

/**
 *
 * @author Informatica
 */


/*
public class WSContenedoresCtrl extends GenericForwardComposer {

    private Textbox ciclo;
    private Textbox contenedor;
    private String parametro;
    private Listbox lb2;

    ConsultarContenedores var = new ConsultarContenedores();

    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);

    }

    public void onChange$contenedor(Event evn) throws SQLException, IOException {

        System.out.println("CICLO..: " + ciclo.getText());
        System.out.println("CONTENEDOR..: " + contenedor.getText());
        parametro = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                + "<MSG_EPQ_DEPO>\n"
                + "<DOC_CONT>\n"
                + "<FECHA_CICLO>20/01/2021</FECHA_CICLO>\n"
                + "</DOC_CONT>\n"
                + "<INFO_USUARIO>\n"
                + "<PARAMETRO1>COT</PARAMETRO1>\n"
                + "<PARAMETRO2>mirror</PARAMETRO2>\n"
                + "<PARAMETRO3>jelocos*jesus</PARAMETRO3>\n"
                + "<PARAMETRO4>1</PARAMETRO4>\n"
                + "</INFO_USUARIO>\n"
                + "</MSG_EPQ_DEPO>";
        String respuesta = var.getConsultarContenedoresSoap().obtenerOperacionCiclo(parametro);

        System.out.println("RESPUESTA : " + respuesta);

        JSONObject myJson0 = new JSONObject(respuesta);
        JSONObject myJson1 = new JSONObject(myJson0.get("MSG_RESPUESTA").toString());
        JSONObject data = new JSONObject(myJson1.get("PARAMETRO_SALIDA").toString());
        
        JSONArray array = new JSONArray(data.get("REGISTRO").toString());
        List<WSContenedoresMD> allCiclos = new ArrayList<WSContenedoresMD>();
        if (array != null) {
            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObj = array.getJSONObject(i);
                WSContenedoresMD dat = new WSContenedoresMD();
                dat.setOperacion(jsonObj.getString("OPERACION"));
                dat.setNumero(jsonObj.getString("NUMERO_CICLO"));
                dat.setContenedores(jsonObj.getString("CONTENEDOR"));
                dat.setId(jsonObj.getString("ID"));
                dat.setFecha(jsonObj.getString("FECHA_CICLO"));
                allCiclos.add(dat);
            }
        }
        lb2.setModel(new ListModelList(allCiclos));
    }

}
*/