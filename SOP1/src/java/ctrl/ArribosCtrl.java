package ctrl;

import DAL.ArribosDal;
import MD.ArribosMd;
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
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Textbox;

public class ArribosCtrl extends GenericForwardComposer {
    
    private Textbox numArr;
    private Textbox nomArr;
    
    List<ArribosMd> allArribos = new ArrayList<ArribosMd>();
    
    private Listbox lb2;
    ArribosDal rg = new ArribosDal();
    private Include rootPagina;
    
    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        allArribos = rg.RSelect();
        lb2.setModel(new ListModelList(allArribos));
        numArr.focus();
    }

    //Selecionar nombres de una lista a campos de un combobox
    public void onSelect$lb2() throws SQLException {
        ArrayList<String> arraytemp = new ArrayList<String>();
        for (Object obj : lb2.getSelectedItem().getChildren()) {
            Listcell celda = (Listcell) obj;
            arraytemp.add(celda.getLabel());
        }
        String arribo = arraytemp.get(0);
        String nombre = arraytemp.get(1);
        
        numArr.setText(arribo);
        nomArr.setText(nombre);
        
        numArr.setDisabled(true);
        nomArr.setDisabled(true);
        
    }
    
    public void onChange$numArr(Event e) throws SQLException {
        if (numArr.getText().isEmpty()) {
            
            numArr.setText("");
            nomArr.setText("");
        } else {
            for (ArribosMd dt : allArribos) {
                if (dt.getTipo().equals(numArr.getText().toUpperCase())) {
                    
                    numArr.setText(dt.getTipo());
                    nomArr.setText(dt.getNombre());
                }
            }
        }
    }
    
    public void onClick$btnGuardar(Event e) throws SQLException {
        
        if (((numArr.getText().equals("")) && (nomArr.getText().equals("")))) {
            
            Clients.showNotification("NO SE GUARDO EL REGISTRO <br/> DEBE LLENAR LOS CAMPOS VACIOS! <br/>",
                    "warning", null, "middle_center", 5000);
        } else {
            
            int op = 0;
            
            for (ArribosMd dt : allArribos) {
                if (dt.getTipo().equals(numArr.getText().toUpperCase())) {
                    op++;
                }
            }
            
            if (op == 0) {
                rg.REGinsert(numArr.getText().toUpperCase(), nomArr.getText());
                
                numArr.setText("");
                nomArr.setText("");
                
                allArribos = rg.RSelect();
                lb2.setModel(new ListModelList(allArribos));
            } else {
                rg.REGupdate(numArr.getText(), nomArr.getText());
                numArr.setDisabled(false);
                numArr.setText("");
                nomArr.setText("");
                numArr.focus();
                allArribos = rg.RSelect();
                lb2.setModel(new ListModelList(allArribos));
            }
        }
    }
    
    public void onClick$btnNuevo(Event e) throws SQLException {
        numArr.setText("");
        nomArr.setText("");
        nomArr.focus();
        
        numArr.setDisabled(false);
        nomArr.setDisabled(false);
    }
    
    public void onClick$btnSalir() {
        rootPagina.setSrc("/Views/Principal.zul");
    }
}
