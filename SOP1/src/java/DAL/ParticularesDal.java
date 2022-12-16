package DAL;

import Conexion.Conexion;
import MD.ParticularesMd;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.zk.ui.util.Clients;

public class ParticularesDal {
    
    private Connection conexion = null;
    private Conexion cnn = new Conexion();
    PreparedStatement ps = null;
    //PreparedStatement st = null;
    Statement st = null;
    ResultSet rs = null;
    
    
    public List<ParticularesMd> REGselect(String num) throws SQLException{
        List<ParticularesMd> allParticulares = new ArrayList<ParticularesMd>();
       
        String query = "SELECT"
                + " TRIM(tipo_particular),"
                + " TRIM(descrip_tipo_parti)"
                + " FROM epqop.if_ca_tipo_particu"
                + " WHERE tipo_particular = '"+num+"' ";
        try {
            conexion = cnn.Conexion();
            st = conexion.createStatement();
            rs = st.executeQuery(query);
            ParticularesMd rg = new ParticularesMd();
            while (rs.next()) {
                rg.setTipo(rs.getString(1));
                rg.setDescripcion(rs.getString(2));
                
                allParticulares.add(rg);
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
        return allParticulares;
    }
    
    public List<ParticularesMd> RSelect() throws SQLException{
        List<ParticularesMd> allParticulares = new ArrayList<ParticularesMd>();
       
        String query = "SELECT"
                + " TRIM(tipo_particular),"
                + " TRIM(descrip_tipo_parti)"
                + " FROM epqop.if_ca_tipo_particu"
                + " ORDER BY tipo_particular ASC ";
        try {
            conexion = cnn.Conexion();
            st = conexion.createStatement();
            rs = st.executeQuery(query);
            ParticularesMd rg;
            while (rs.next()) {
                rg = new ParticularesMd();
                rg.setTipo(rs.getString(1));
                rg.setDescripcion(rs.getString(2));
                
                allParticulares.add(rg);
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
        return allParticulares;
    }
    
    public void REGinsert(String tipo,String descripcion) throws SQLException{
        
        String sql = "INSERT INTO "
                + " epqop.if_ca_tipo_particu"
                + " (tipo_particular,"
                + "  descrip_tipo_parti)"
                + " VALUES(?,?)";
        try {
            conexion = cnn.Conexion();
            conexion.setAutoCommit(false);
            st = conexion.createStatement();
        
            
            ps = conexion.prepareStatement(sql);
            
            ps.setString(1, tipo);
            ps.setString(2, descripcion);
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
    
    public void REGupdate (String tipo, String descripcion) throws SQLException{
        try {
            conexion = cnn.Conexion();
            conexion.setAutoCommit(false);
            System.out.println("ACTUALIZAR DATOS..!");
            System.out.println("Actualizar "+tipo);
            st = conexion.createStatement();

            st.executeUpdate("UPDATE "
                    + " epqop.if_ca_tipo_particu "
                    + " SET descrip_tipo_parti = '"+descripcion+"'"
                    + " WHERE tipo_particular = '"+tipo+"' ");
           
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
    
}


