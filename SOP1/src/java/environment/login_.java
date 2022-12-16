
package environment;

import DAL.UsuariosDal;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Textbox;


public class login_ extends GenericForwardComposer {
    private static final long serialVersionUID = 1L;
    public static String USER = "";
    public static final String PATH_INICIAL = "menu.zul";
    private Textbox txtUser;
    private Textbox txtPass;
    UsuariosDal us = new UsuariosDal();
    
    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
    }
    
    public void onClick$btnLogin(Event evt) {
        
        if (txtUser.getValue() == null || txtUser.getValue().toString().trim().equals("")) {
            return;
        }

        if (txtPass.getValue() == null || txtPass.getValue().toString().trim().equals("")) {
            return;
        }

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
    }
}
