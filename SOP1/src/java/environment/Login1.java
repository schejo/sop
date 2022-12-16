


package environment;

import Conexion.Conexion;
import Conexion.authbd;
import DAL.LoginDAL2;
import MD.UsuariosSOPMD;
import Util.Commons;
import static java.awt.SystemColor.desktop;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Button;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

/**
 *
 * @author Informatica
 */
public class Login1 extends Commons{
    
    private static final long serialVersionUID = 1L;
    
    
    private Textbox txtUsuario;
   // private Textbox txtCodigoEmp;
    private Textbox txtPassword;
   // private Label lblFechVencimiento;
    
 // private Button btnLogin;
    String[] Respuesta;
    
    LoginDAL2 Validaruser = new LoginDAL2();
    //LoginMD model = new LoginMD();
    
    public void doAfterCompose(Component comp) throws Exception {
       super.doAfterCompose(comp);
       if(validarSesion()){
           execution.sendRedirect("login.zul");
       
       }
       
    }
    
    authbd ValidarUsuario = new authbd();
    LoginDAL2 validaDal = new LoginDAL2();
    UsuariosSOPMD Usuario = new UsuariosSOPMD();
    
    
     public void onClick$btnRecuperar(Event evt) throws SQLException {
        if (txtUsuario.getValue() == null || txtUsuario.getValue().toString().trim().equals("")) {
            org.zkoss.zhtml.Messagebox.show("NO INGRESO USUARIO", "INFORMACION", org.zkoss.zhtml.Messagebox.OK, org.zkoss.zhtml.Messagebox.ERROR);
            return;
        }
        try {
            LoginDAL2 dal = new LoginDAL2();

            UsuariosSOPMD us = dal.busca(txtUsuario.getValue());
            

            if (us != null) {
               

                    session.setAttribute("USUARIO", us.getUsrUsuario());
                    session.setAttribute("OPCION", "2");
                    session.setAttribute("PALABRA", us.getUsrPalabra());

                    execution.sendRedirect("CambioClave.zul");

                

            } else {
                org.zkoss.zhtml.Messagebox.show("EL USUARIO NO ESTA REGISTRADO EN LA BASE DE DATOS", "Informacion", org.zkoss.zhtml.Messagebox.OK, org.zkoss.zhtml.Messagebox.ERROR);
            }

        } catch (WrongValueException e) {
            org.zkoss.zhtml.Messagebox.show("Ocurrió un error al intentar validar el usuario en Base de Datos, intente nuevamente.", "Información", org.zkoss.zhtml.Messagebox.OK, org.zkoss.zhtml.Messagebox.ERROR);
        }
    }

    
    
    public void onClick$btnLogin (Event evt){   /////Boton de Login
     PreparedStatement smt = null;
		Connection conn;
		Conexion conex = new Conexion();
		conn = conex.getConnection();
        
        if(txtUsuario.getValue() == null || txtUsuario.getValue().toString().trim().equals("")){
            
          return;
        
    }

    if(txtPassword.getValue() == null || txtPassword.getValue().toString().trim().equals("")){
        return ;
        
    }
        try{
                    String[] Respuesta = ValidarUsuario.autUser(txtUsuario.getValue(), txtPassword.getValue());
            
            
            if(Respuesta[0].equals("ok")){
               Session UserSesion = Sessions.getCurrent();
               UserSesion.setAttribute("USUARIO", txtUsuario.getValue());
               UserSesion.setAttribute("PASSWORD",txtPassword.getValue());
               
                int User_SW = -1;
                               UsuariosSOPMD AutenticarUser_SW = new UsuariosSOPMD();
                               LoginDAL2 Connex_UserSW = new  LoginDAL2();
                               AutenticarUser_SW.setNombre(txtUsuario.getValue());
              
              
                if (Validaruser.BuscarExiste(txtUsuario.getValue()) == 1){
            System.out.println("PROCEDIMIENTO BITACORA..");
            CallableStatement cStmt = conn.prepareCall("{call SYSAUDTEMP.BitacoraWeb(?,?,?,?,?)}");
            cStmt.setString(1, txtUsuario.getValue());//USUARIO
            cStmt.setString(2, "WEB");//MAQUINA
            cStmt.setString(3, "23");//ID de SISTEMA
            cStmt.setString(4, "LOGIN SOP WEB.: "+txtUsuario.getValue());//OBSERVACIONES
            cStmt.setString(5, "WEB"); //IP DIRECCION
            cStmt.execute();
            cStmt.close();
                    
                    if(Validaruser.Password(txtUsuario.getValue()) != false)
                    {
                        if(Validaruser.UserVencimiento(txtUsuario.getValue()) != null )
                    {
                        if(Validaruser.CodEmpleado(txtUsuario.getValue()) != null )
                    {
                        
                         ////////////////////////LLEVA CONTROL DE BITACORAS DEL USUARIO QUE INGRESA AL SISTEMA///////////////////////////////////////////////////////////                    
                               User_SW = Connex_UserSW.Crear_Usuarios_SW(AutenticarUser_SW);
                               
                               if(User_SW > 0){
                                       
				desktop.getSession().setAttribute(Commons.USER, txtUsuario.getValue());
                                execution.sendRedirect(Commons.PATH_INICIAL);
                                
                                }else{
                               
                               
                               org.zkoss.zhtml.Messagebox.show("Error en:"+"Autenticar el usuario");
                               }
                               
         //////////////////////////////////////FIN DE CONTROL DE BITACORAS DE USUARIO///////////////////////////////////////////////////////////////////////////    
                    
                
                    
                      }else {
                         Messagebox.show("El usuario no tiene bien sus datos le falta codigo de empleado.", "Informacion", Messagebox.OK, Messagebox.INFORMATION);
                        }
                    
                    }
                        else {
                         Messagebox.show("No existe usuario, no puede ingresar al sistema", "Informacion", Messagebox.OK, Messagebox.INFORMATION);
                        }
                
            }else{
                    Messagebox.show("Contraseña no valida");
                    
                    }      
                    
               }else{
                Messagebox.show("No puede acceder al sistema, No existe Usuario");
                }
           
            } else{
                Messagebox.show(Respuesta[1], "Informacion", Messagebox.OK, Messagebox.INFORMATION);                
            }
        }catch (Exception e){
            Messagebox.show("Ha ocurrido un error al momento de validar el Usuario y Contraseña, siga intentado.", "Informacion", Messagebox.OK, Messagebox.INFORMATION);
           
        }
    }
    
    public void onOK$txtPassword(Event evt){
       onClick$btnLogin (evt);
    }
   
}
