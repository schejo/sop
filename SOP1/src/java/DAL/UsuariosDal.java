
package DAL;

import Conexion.Conexion;
import MD.UsuariosMd;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.zkoss.zhtml.Messagebox;


public class UsuariosDal {
    
    private Connection conexion = null;
    private Conexion cnn = new Conexion();
    //PreparedStatement st = null;
    Statement st = null;
    ResultSet rs = null;
    
        public int UsuarioExiste(String usuario, String pass) throws SQLException {
        
        int resultado = 0;

        System.out.println("Credenciales.: " + usuario + " -> " + pass);
        String sql = " SELECT TRIM(nombre_usuario), TRIM(password_usuario) FROM federicoy1672.usuarios_web WHERE TRIM(nombre_usuario) = '"+usuario+"' AND TRIM(password_usuario) = '"+pass+"' ";
        UsuariosMd us = new UsuariosMd();
        try {
            
            conexion = cnn.Conexion();
            st = conexion.createStatement();
            rs = st.executeQuery(sql);
            
            while (rs.next()) {
                us.setUsuario(rs.getString(1));
                us.setClave(rs.getString(2));
                resultado = 1;
            }
            st.close();
            rs.close();
            conexion.close(); conexion=null;
            
        } catch (SQLException e) {
            st.close();
            rs.close();
            conexion.close(); conexion=null;
            Messagebox.show(e.getMessage().toString(), "Informacion", Messagebox.OK, Messagebox.INFORMATION);
        } catch (Exception ex){
            Messagebox.show(ex.getMessage().toString(), "Informacion", Messagebox.OK, Messagebox.INFORMATION);
        }
        System.out.println("RESULTADO DE BUSQUEDA..: "+resultado);
        
        return resultado;
    }
    
}
