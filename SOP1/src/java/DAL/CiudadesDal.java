package DAL;

import Conexion.Conexion;
import MD.CiudadesMd;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.zk.ui.util.Clients;

public class CiudadesDal {
    
    private Connection conexion = null;
    private Conexion cnn = new Conexion();
    PreparedStatement ps = null;
    //PreparedStatement st = null;
    Statement st = null;
    ResultSet rs = null;
    
    
    public List<CiudadesMd> REGselect(String num) throws SQLException{
        List<CiudadesMd> allCiudades = new ArrayList<CiudadesMd>();
        String query = "select trim(codigo_pueblo), trim(nombre_pueblo) from federicoy1672.pueblos_2020 where codigo_pueblo = '"+num+"' ";
        try {
            conexion = cnn.Conexion();
            st = conexion.createStatement();
            rs = st.executeQuery(query);
            CiudadesMd rg = new CiudadesMd();
            while (rs.next()) {
                rg.setNumero(rs.getString(1));
                rg.setNombre(rs.getString(2));
                allCiudades.add(rg);
            }
            
            st.close();
            rs.close();
            conexion.close(); conexion=null;
        } catch(SQLException e){
            st.close();
            rs.close();
            conexion.close(); conexion=null;
            Clients.showNotification("ERROR AL CONSULTAR <br/> <br/> REGISTROS! <br/> "+e.getMessage().toString(),
                    "warning", null, "middle_center", 0);
        }
        return allCiudades;
    }
    
    public List<CiudadesMd> RSelect() throws SQLException{
        List<CiudadesMd> allCiudades = new ArrayList<CiudadesMd>();
        String query = "select trim(codigo_pueblo), trim(nombre_pueblo) from federicoy1672.pueblos_2020 order by codigo_pueblo asc ";
        try {
            conexion = cnn.Conexion();
            st = conexion.createStatement();
            rs = st.executeQuery(query);
            CiudadesMd rg;
            while (rs.next()) {
                rg = new CiudadesMd();
                rg.setNumero(rs.getString(1));
                rg.setNombre(rs.getString(2));
                allCiudades.add(rg);
            }
            
            st.close();
            rs.close();
            conexion.close(); conexion=null;
        } catch(SQLException e){
            st.close();
            rs.close();
            conexion.close(); conexion=null;
            Clients.showNotification("ERROR AL CONSULTAR <br/> <br/> REGISTROS! <br/> "+e.getMessage().toString(),
                    "warning", null, "middle_center", 0);
        }
        return allCiudades;
    }
    
    public void REGinsert(String nom) throws SQLException{
        String id="";
        String query="select nvl(MAX(codigo_pueblo),0)+1 as codigo from federicoy1672.pueblos_2020";
        String sql = "INSERT INTO federicoy1672.pueblos_2020 (codigo_pueblo, nombre_pueblo) VALUES(?,?)";
        try {
            conexion = cnn.Conexion();
            conexion.setAutoCommit(false);
            
            st = conexion.createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
                id = rs.getString("codigo");
            }
            System.out.println("ID CIUDAD.: "+id);
            st.close();
            rs.close();
            
            ps = conexion.prepareStatement(sql);
            
            ps.setString(1, id);
            ps.setString(2, nom);
            ps.executeUpdate();
            ps.close();
            conexion.commit();
            Clients.showNotification("REGISTRO CREADO <br/> CON EXITO  <br/>");
            conexion.close(); conexion=null;
            System.out.println("Conexion Cerrada"+conexion);
            
        } catch(SQLException e){
            conexion.rollback();
            conexion.close(); conexion=null;
            Clients.showNotification("ERROR AL INSERTAR <br/> <br/> REGISTROS! <br/> "+e.getMessage().toString(),
                    "warning", null, "middle_center", 0);
        }
    }
    
    public void REGupdate (String num, String nom) throws SQLException{
        try {
            conexion = cnn.Conexion();
            conexion.setAutoCommit(false);
            System.out.println("ACTUALIZAR DATOS..!");
            System.out.println("Actualizar "+num);
            st = conexion.createStatement();

            st.executeUpdate("UPDATE federicoy1672.pueblos_2020 set nombre_pueblo = '"+nom+"' where codigo_pueblo= '"+num+"' ");
            Clients.showNotification("REGISTRO ACTUALIZADO <br/> CON EXITO  <br/>");
            System.out.println("Actualizacion Exitosa.! ");
            st.close();
            conexion.commit();
            conexion.close();
        } catch(SQLException e){
            conexion.rollback();
            conexion.close();
            String mensaje = e.getMessage();
            Clients.showNotification("ERROR AL ACTUALIZAR <br/>'"+mensaje+"' <br/> REGISTROS! <br/> ",
                    "warning", null, "middle_center", 0);
            System.out.println("Actualizacion EXCEPTION.: " + mensaje);
        }
        
    }
    
    public void REGdelete (String num) throws SQLException{
        try {
            conexion = cnn.Conexion();
            conexion.setAutoCommit(false);
            System.out.println("ELIMINAR DATOS..!");
            System.out.println("Eliminar "+num);
            st = conexion.createStatement();

            st.executeUpdate("DELETE federicoy1672.pueblos_2020 where codigo_pueblo= '"+num+"' ");
            Clients.showNotification("REGISTRO ELIMINADO <br/> CON EXITO  <br/>");
            System.out.println("Eliminacion Exitosa.! ");
            st.close();
            conexion.commit();
            conexion.close();
        } catch(SQLException e){
            conexion.rollback();
            conexion.close();
            String mensaje = e.getMessage();
            Clients.showNotification("ERROR AL ELIMINAR <br/>'"+mensaje+"' <br/> REGISTROS! <br/> ",
                    "warning", null, "middle_center", 0);
            System.out.println("Eliminacion EXCEPTION.: " + mensaje);
        }
        
    }
}
