package ctrl;

import DAL.LoginDAL2;
import MD.UsuariosSOPMD;
import Util.Cripto;
import java.sql.SQLException;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Include;
import org.zkoss.zul.Textbox;

public class CambioClave extends GenericForwardComposer {

    private Textbox txtClave;
    private Textbox txtClave2;
    private Textbox txtUsuario;
    private Textbox txtPalabra;
    private Include rootPagina;
    String usuario = "";
    Session Session = Sessions.getCurrent();
    Cripto cp = new Cripto();
    UsuariosSOPMD usumodelo = new UsuariosSOPMD();

    LoginDAL2 clavedall = new LoginDAL2();

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        usuario = Session.getAttribute("USUARIO").toString();
        txtUsuario.setText(usuario);

    }

    public void onClick$btnGuardar(Event evt) throws SQLException {
        String pass1 = "";
        usumodelo = new UsuariosSOPMD();

        if (txtClave.getText().equals(txtClave2.getText())) {
            usumodelo = clavedall.palabraClave(txtUsuario.getText(), txtPalabra.getText());

            if (usumodelo.getUsrPalabra() != null) {
                pass1 = cp.encrypt(txtClave.getText());
                clavedall.insertclaVe(pass1, txtUsuario.getText());
                clear();

            } else {
                org.zkoss.zul.Messagebox.show("Palabra Incorrecta, Solicite Cambio de Palabra Clave", "Informacion", org.zkoss.zul.Messagebox.OK, org.zkoss.zul.Messagebox.INFORMATION);
            }

        } else {
            Messagebox.show("LAS CLAVES NO COINCIDEN, INTENTE DE NUEVO", "Informacion", Messagebox.OK, Messagebox.EXCLAMATION);

        }
    }

    public void clear() {

        txtClave.setText("");
        txtClave2.setText("");
        txtUsuario.setText("");
        txtPalabra.setText("");

    }

    public void onClick$btnCancelar() {
         execution.sendRedirect("login.zul");
    }
      public void onClick$itemSalir() {
        execution.sendRedirect("login.zul");
    }

}
