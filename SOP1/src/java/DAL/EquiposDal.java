package DAL;

import Conexion.Conexion;
import MD.EquiposMd;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.zk.ui.util.Clients;

public class EquiposDal {
    
    private Connection conexion = null;
    private Conexion cnn = new Conexion();
    PreparedStatement ps = null;
    //PreparedStatement st = null;
    Statement st = null;
    ResultSet rs = null;
    
    
    public List<EquiposMd> REGselect(String num) throws SQLException{
        List<EquiposMd> allEquipos = new ArrayList<EquiposMd>();
        
        String query = "SELECT"
                + " TRIM(codigo_tipo_equipo),"
                + " TRIM(nombre_tipo_equipo)"
                + " FROM epqop.if_eq_tipo_equipo "
                + " WHERE codigo_tipo_equipo = '"+num+"' ";
        try {
            conexion = cnn.Conexion();
            st = conexion.createStatement();
            rs = st.executeQuery(query);
            EquiposMd rg = new EquiposMd();
            while (rs.next()) {
                rg.setEquipo(rs.getString(1));
                rg.setNombre(rs.getString(2));
                
                allEquipos.add(rg);
            }
            
            st.close();
            rs.close();
            conexion.close(); conexion=null;
        } catch(SQLException e){
            st.close();
            rs.close();
            conexion.close(); conexion=null;
            Clients.showNotification("ERROR AL CONSULTAR (REGselect) <br/> <br/> REGISTROS! <br/> "+e.getMessage().toString(),
                    "warning", null, "middle_center", 0);
        }
        return allEquipos;
    }
    
    public List<EquiposMd> RSelect() throws SQLException{
        List<EquiposMd> allEquipos = new ArrayList<EquiposMd>();
        
        String query = "SELECT"
                + " TRIM(codigo_tipo_equipo),"
                + " TRIM(nombre_tipo_equipo)"
                + " FROM epqop.if_eq_tipo_equipo "
                + " ORDER BY codigo_tipo_equipo ASC ";
        try {
            conexion = cnn.Conexion();
            st = conexion.createStatement();
            rs = st.executeQuery(query);
            EquiposMd rg;
            while (rs.next()) {
                rg = new EquiposMd();
                rg.setEquipo(rs.getString(1));
                rg.setNombre(rs.getString(2));
                
                allEquipos.add(rg);
            }
            
            st.close();
            rs.close();
            conexion.close(); conexion=null;
        } catch(SQLException e){
            st.close();
            rs.close();
            conexion.close(); conexion=null;
            Clients.showNotification("ERROR AL CONSULTAR (Rselect) <br/> <br/> REGISTROS! <br/> "+e.getMessage().toString(),
                    "warning", null, "middle_center", 0);
        }
        return allEquipos;
    }
    
    public void REGinsert(String Equipo,String nombre) throws SQLException{
        
        String sql = "INSERT INTO epqop.if_eq_tipo_equipo"
                + " (codigo_tipo_equipo,"
                + "nombre_tipo_equipo)"
                + " VALUES(?,?)";
        try {
            conexion = cnn.Conexion();
            conexion.setAutoCommit(false);
            st = conexion.createStatement();
        
            
            ps = conexion.prepareStatement(sql);
            
            ps.setString(1, Equipo);
            ps.setString(2, nombre);
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
    
    public void REGupdate (String Equipo, String nombre) throws SQLException{
        try {
            conexion = cnn.Conexion();
            conexion.setAutoCommit(false);
            System.out.println("ACTUALIZAR DATOS..!");
            System.out.println("Actualizar "+Equipo);
            st = conexion.createStatement();

            st.executeUpdate("UPDATE epqop.if_eq_tipo_equipo set nombre_tipo_equipo = '"+nombre+"'"
                    + " where codigo_tipo_equipo = '"+Equipo+"' ");
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
    
    public void REGdelete (String tipo) throws SQLException{
        try {
            conexion = cnn.Conexion();
            conexion.setAutoCommit(false);
            System.out.println("ELIMINAR DATOS..!");
            System.out.println("Eliminar "+tipo);
            st = conexion.createStatement();

            st.executeUpdate("DELETE epqop.if_bq_lineas where linea1= '"+tipo+"' ");
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

