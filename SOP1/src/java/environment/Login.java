


package environment;


import java.sql.SQLException;
import javax.swing.JOptionPane;
//import org.tempuri.WsContenedor;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

/**
 *
 * @author Informatica
 */
public class Login extends GenericForwardComposer{
    
    private static final long serialVersionUID = 1L;
    public static final String PATH_INICIAL = "menu.zul";
    
    private Textbox txtUsuario;
   // private Textbox txtCodigoEmp;
    private Textbox txtPassword;
    //private Label lblUSER;
    
 // private Button btnLogin;
    String[] Respuesta;
    
//    LoginDAL Validaruser = new LoginDAL();
//    LoginMD model = new LoginMD();
    
  // WsContenedor webs = new WsContenedor();
    
    public void doAfterCompose(Component comp) throws Exception {
       super.doAfterCompose(comp);
       if(validarSesion()){
           execution.sendRedirect("login.zul");
       
       }
       
    }
    
//    authbd ValidarUsuario = new authbd();
//    LoginDAL validaDal = new LoginDAL();
//    UsuariosMD Usuario = new UsuariosMD();
    
    
    public void onClick$btnLogin (Event evt){   /////Boton de Login
     
    ValidarLogin();
   
}
    
    public boolean validarSesion(){
		if(desktop.getSession().getAttribute("USER") != null){
			return true;
		}
		return false;
	}
    
    public void ValidarLogin(){
        
        if(txtUsuario.getValue() == null || txtUsuario.getValue().toString().trim().equals("")){
          return;
        }

        if(txtPassword.getValue() == null || txtPassword.getValue().toString().trim().equals("")){
            return ;
        }
        try{
              //      String[] Respuesta = ValidarUsuario.autUser(txtUsuario.getValue(), txtPassword.getValue());
                    
    ////////////////////////// CONSUME WS DE VALIDAR USUARIO DE OPERACIONES////////////////////////////////////////////////////////////////////////////
    
            String RESP = "";//webs.getWsContenedorSoap().validarUsuario(txtPassword.getText(), txtUsuario.getText(), txtUsuario.getText(), 1);
            //System.out.println("CORRER DE WS VALIDAR USUARIO: "+RESP);
            
            String CapturaError= "";
            String sp = "" + (char) 124;
                String data = RESP.replace(sp, "-");

                String vector[] = data.split("-");
                //System.out.println("Posicion [0].. " + vector[0] + "  Posicion [1].." + vector[1]);

                String val = vector[0];
                String va2 = vector[1];
                CapturaError = txtUsuario.getText().concat(" | " + va2 + "");
               // lblUSER.setValue(va2);
                
                if(val.equals("MSG")){
                    
                //Messagebox.show(va2, "Informacion:", Messagebox.OK, Messagebox.INFORMATION);
                 
                
                
                 desktop.getSession().setAttribute("USER", txtUsuario.getText());
                 execution.sendRedirect(PATH_INICIAL);
                 Clients.showNotification("Tiempo de Sesión: "+va2, Clients.NOTIFICATION_TYPE_INFO, null, "middle_center", 0);
               
               
                }else{
                
                Messagebox.show(CapturaError, "ERROR:", Messagebox.OK, Messagebox.ERROR);
                }

     //////////////////////////////////////////////FIN VALIDA WS USUARIOS DE OPERACIONES///////////////////////////////////////////////////////////////////////////////////
     
     
//               if(Respuesta[0].equals("ok")){
//               Session UserSesion = Sessions.getCurrent();
//               UserSesion.setAttribute("USUARIO", txtUsuario.getValue());
//               UserSesion.setAttribute("PASSWORD",txtPassword.getValue());
//              
//              
//                if (Validaruser.BuscarExiste(txtUsuario.getValue()) == 1){
//                    
//                    if(Validaruser.Password(txtUsuario.getValue()) != false)
//                    {
//                        if(Validaruser.UserVencimiento(txtUsuario.getValue()) != null )
//                    {
//                        if(Validaruser.CodEmpleado(txtUsuario.getValue()) != null )
//                    {
//                        
//                    
//                desktop.getSession().setAttribute(Commons.USER, txtUsuario.getValue());
//                execution.sendRedirect(Commons.PATH_INICIAL);
//                    
//                      }else {
//                         Messagebox.show("El usuario no tiene bien sus datos le falta codigo de empleado.", "Informacion", Messagebox.OK, Messagebox.INFORMATION);
//                        }
//                    
//                    }else {
//                         Messagebox.show("No existe usuario, no puede ingresar al sistema", "Informacion", Messagebox.OK, Messagebox.INFORMATION);
//                        }
//                
//            }else{
//                    Messagebox.show("Contraseña no valida");
//                    
//                    }      
//                    
//               }else{
//                Messagebox.show("No puede acceder al sistema, No existe Usuario");
//                }
//           
//            } else{
//                Messagebox.show(Respuesta[1], "Informacion", Messagebox.OK, Messagebox.INFORMATION);                
//            }
        }catch (Exception e){
            Messagebox.show("Ha ocurrido un error al momento de validar el Usuario y Contraseña, siga intentado.", "Informacion", Messagebox.OK, Messagebox.INFORMATION);
           
        }
    }
    
//    public void onOK$txtPassword(Event evt){
//       onClick$btnLogin (evt);
//    } 
        
        
    


}