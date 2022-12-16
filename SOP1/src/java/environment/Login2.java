
package environment;

import DAL.LoginDal;
import DAL.UsuariosDal;
import MD.LoginMd;
import Util.Bitacora;
import Util.Cripto;
import java.sql.SQLException;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventQueues;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;


public class Login2 extends GenericForwardComposer {
    private static final long serialVersionUID = 1L;
    private static String key = "epqCitas*8965*";
    public static String USER = "";
    public static final String PATH_INICIAL = "menu.zul";
    private Textbox txtUser;
    private Textbox txtPass;
    UsuariosDal us = new UsuariosDal();
    Cripto cp = new Cripto();
    LoginDal at = new LoginDal();
    
    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
    }
    
    public void onClick$btnLogin(Event evt) throws SQLException {
        String usuario = "";
        String passR = "";
        String pass1 = "";
        LoginMd data = new LoginMd();
        if (txtUser.getValue() == null || txtUser.getValue().toString().trim().equals("")) {
            return;
        }

        if (txtPass.getValue() == null || txtPass.getValue().toString().trim().equals("")) {
            return;
        }
        
        System.out.println("CLAVE.: " + txtPass.getText());
        passR = cp.reset();
        pass1 = cp.encrypt(txtPass.getText());
        System.out.println("CLAVE CAJA TEXTO.: " + pass1);
        System.out.println("CLAVE RESET.: " + passR);
        
        if (pass1.equals(passR)) {
            //BUSCA SIN CLAVE
            //data = at.UsuarioExiste(txtUser.getValue(), txtPass.getValue());
            data = at.BuscaUsuario(txtUser.getValue());

            String pwd = data.getPass();

            if (pass1.equals(pwd)) {
                //if (txtPass.getText().equals(pwd)) {

                EventQueues.lookup("myEventQueue", EventQueues.DESKTOP, true)
                        .publish(new Event("onChangeNickname", null, txtUser.getText()));

                //INVOCAR MODAL
                Window window = (Window) Executions.createComponents(
                        "/Views/CambioPass.zul", null, null);
                window.doModal();
            } else {
                Clients.showNotification("Credenciales <br/>  <br/> Incorrectas <br/> <br/>Intentelo de Nuevo",
                        Clients.NOTIFICATION_TYPE_WARNING, null, "middle_center", 2000);
            }
        } else {
            try {
                //data = at.UsuarioExiste(txtUser.getValue(), pass1);
                //data = at.UsuarioExiste(txtUser.getValue(), txtPass.getText());
                //data = at.BuscaUsuario(txtUser.getValue());
                data = at.UsuarioExiste1(txtUser.getValue());
                if (data.getRespuesta().equals("1")) {
                    int x = 0;
                    if (txtPass.getText().equals(data.getPass())) {
                        x = 1;
                        System.out.println("PASSWORD SIN ENCRIPTAR.: " + data.getPass());
                    }
                    if (pass1.equals(data.getPass())) {
                        x = 1;
                        System.out.println("PASSWORD ENCRIPTADA.: " + data.getPass());
                    }
                    if (x == 1) {
                        System.out.println("VALOR DE USUARIO..: " + data.getUseario());
                        Session InventarioSession = Sessions.getCurrent();
                        InventarioSession.setAttribute("USUARIO", data.getUseario());
                        InventarioSession.setAttribute("EMPRESA", data.getEmpresa());
                        //InventarioSession.setAttribute("PASSWORD", txtPass.getValue());

                Bitacora bt = new Bitacora();
//                String rps = bt.login(data.getUseario(), "CitasEPQ", "Login2", "null", 0, 0, "Inicio de Session");
                        //lab0.setValue("1");
                        desktop.getSession().setAttribute("USUARIO", txtUser.getValue());
                        execution.sendRedirect(PATH_INICIAL);
                    } else {
                        Clients.showNotification("Credenciales <br/>  <br/> Incorrectas <br/> <br/>Intentelo de Nuevo",
                            Clients.NOTIFICATION_TYPE_ERROR, null, "top_center", 2000);
                    }
                } else {
                    //lab0.setValue("0");
                    Bitacora bt = new Bitacora();
//                    String rps = bt.login(usuario, "CitasEPQ", "Login2", "null", 0, 0, "Credenciales Incorrectas");
                    Clients.showNotification("Credenciales <br/>  <br/> Incorrectas <br/> <br/>Intentelo de Nuevo",
                            Clients.NOTIFICATION_TYPE_ERROR, null, "top_center", 2000);
                    //Messagebox.show("Credenciales Incorrectas", "Informacion", Messagebox.OK, Messagebox.INFORMATION);
                }
            } catch (Exception e) {
                Messagebox.show("Ocurrió un error al intentar validar el usuario, intente nuevamente." + e.getMessage().toString(),
                        "Informacion", Messagebox.OK, Messagebox.INFORMATION);
            }
        }
        
        /*
        try {
            int resp = us.UsuarioExiste(txtUser.getText(), txtPass.getText());
            System.out.println("Resultado de VALIDACION..: "+resp);
            if (resp==1) {
                //OBTENEMOS UNA SESSION PARA EL USUARIO
                //Clients.showNotification("BIENVENIDO AL SISTEMA <br/> <br/> mas <br/> ",
                  //  "warning", null, "middle_center", 0);
                desktop.getSession().setAttribute("USER", txtUser.getValue());
                USER = txtUser.getValue();
                execution.sendRedirect(PATH_INICIAL);
            } else {
                Clients.showNotification("Credenciales Incorrectas <br/> <br/> Intente nuevamente. <br/> ",
                    "warning", null, "middle_center", 0);
            }
        } catch (Exception e) {
            Clients.showNotification("Ocurrió un ERROR al intentar <br/> validar el usuario <br/> Intente nuevamente. <br/> ",
                    "warning", null, "middle_center", 0);
        }
        */
    }
}
