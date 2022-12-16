
package ctrl;

import DAL.CiudadesDal;
import MD.CiudadesMd;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Include;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;

public class CiudadesCtrl extends GenericForwardComposer {
    
    private Textbox numCiu;
    private Textbox nomCiu;
    private Listbox lb2;
    CiudadesDal rg = new CiudadesDal();
    private Include rootPagina;
    
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        List<CiudadesMd> allCiudades = new ArrayList<CiudadesMd>();
        allCiudades = rg.RSelect();
        lb2.setModel(new ListModelList(allCiudades));
        System.out.println("CONEXION FORMA 1.: ");
        numCiu.setDisabled(true);
        nomCiu.focus();
    }
    
    public void onClick$btnGuardar(Event e) throws SQLException {
        System.out.println("Nombre Ciudad.: "+nomCiu.getText());
        if(numCiu.getText().isEmpty() || numCiu.getText().equals("") && !nomCiu.getText().equals("")){
        rg.REGinsert(nomCiu.getText());
        System.out.println("SALIENDO DE METODO INSERT");
        numCiu.setDisabled(true);
        nomCiu.setText("");
        List<CiudadesMd> allCiudades=new ArrayList<CiudadesMd>();
        allCiudades =rg.RSelect();
        lb2.setModel(new ListModelList(allCiudades));
        } 
        if(!numCiu.getText().equals("") && !nomCiu.getText().equals("")){
            rg.REGupdate(numCiu.getText(),nomCiu.getText());
            numCiu.setDisabled(true);
            numCiu.setText(""); nomCiu.setText("");
        }
        
    }
    
    public void onChange$numReg (Event e) throws SQLException {
        List<CiudadesMd> allCiudades = new ArrayList<CiudadesMd>();
        allCiudades = rg.REGselect(numCiu.getText());
        
        if(allCiudades.isEmpty()){
            numCiu.setText(""); nomCiu.setText("");
            numCiu.focus();
        } else {
            for(CiudadesMd dt:allCiudades){
                numCiu.setText(dt.getNumero()); nomCiu.setText(dt.getNombre());
            }
            //numReg.setDisabled(true);
        }
    }
    
    public void onClick$btnActualiza(Event e) throws SQLException {
        System.out.println("Numero Ciudad.: "+numCiu.getText());
        System.out.println("Nombre Ciudad.: "+nomCiu.getText());
        numCiu.setDisabled(false);
        numCiu.setText("");     nomCiu.setText("");
        numCiu.focus();
    }
    
    public void onClick$btnDelete(Event e) throws SQLException {
        System.out.println("Numero Ciudad.: "+numCiu.getText());
        System.out.println("Nombre Ciudad.: "+nomCiu.getText());
        if(!numCiu.getText().equals("")&& !numCiu.getText().equals("")){
            rg.REGdelete(numCiu.getText());
            numCiu.setDisabled(true);
            numCiu.setText(""); nomCiu.setText("");
            List<CiudadesMd> allCiudades=new ArrayList<CiudadesMd>();
            allCiudades=rg.RSelect();
            lb2.setModel(new ListModelList(allCiudades));   
        }else{
            Clients.showNotification("DEBE SELECCIONAR <br/> UN REGISTRO! <br/>",
                    "warning",null,"middle_center",50);
        }
                }
    public void onClick$btnSalir(){
        rootPagina.setSrc("/Views/Principal.zul");
    }
}

