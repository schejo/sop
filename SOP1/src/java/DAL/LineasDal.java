package DAL;

import Conexion.Conexion;
import MD.LineasMd;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.zk.ui.util.Clients;

public class LineasDal {
    
    private Connection conexion = null;
    private Conexion cnn = new Conexion();
    PreparedStatement ps = null;
    //PreparedStatement st = null;
    Statement st = null;
    ResultSet rs = null;
    
    
    public List<LineasMd> REGselect(String num) throws SQLException{
        List<LineasMd> allLineas = new ArrayList<LineasMd>();
        
        String query = "SELECT"
                + " TRIM(linea1),"
                + " TRIM(nom_linea)"
                + " FROM epqop.if_bq_lineas WHERE linea1 = '"+num+"' ";
        try {
            conexion = cnn.Conexion();
            st = conexion.createStatement();
            rs = st.executeQuery(query);
            LineasMd rg = new LineasMd();
            while (rs.next()) {
                rg.setLinea(rs.getString(1));
                rg.setNombre(rs.getString(2));
                
                allLineas.add(rg);
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
        return allLineas;
    }
    
    public List<LineasMd> RSelect() throws SQLException{
        List<LineasMd> allLineas = new ArrayList<LineasMd>();
        String query = "SELECT"
                + " TRIM(linea1),"
                + " TRIM(nom_linea)"
                + " FROM epqop.if_bq_Lineas ORDER BY linea1 ASC ";
        try {
            conexion = cnn.Conexion();
            st = conexion.createStatement();
            rs = st.executeQuery(query);
            LineasMd rg;
            while (rs.next()) {
                rg = new LineasMd();
                rg.setLinea(rs.getString(1));
                rg.setNombre(rs.getString(2));
                
                allLineas.add(rg);
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
        return allLineas;
    }
    
    public void REGinsert(String linea,String nombre) throws SQLException{
        String sql = "INSERT INTO "
                + " epqop.if_bq_Lineas"
                + " (linea1,"
                + " nom_linea)"
                + " VALUES(?,?)";
        try {
            conexion = cnn.Conexion();
            conexion.setAutoCommit(false);
            st = conexion.createStatement();
        
            
            ps = conexion.prepareStatement(sql);
            
            ps.setString(1, linea);
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
    
    public void REGupdate (String linea, String nombre) throws SQLException{
        try {
            conexion = cnn.Conexion();
            conexion.setAutoCommit(false);
            System.out.println("ACTUALIZAR DATOS..!");
            System.out.println("Actualizar "+linea);
            st = conexion.createStatement();

            st.executeUpdate("UPDATE epqop.if_bq_Lineas "
                    + " SET nom_linea = '"+nombre+"'"
                    + " WHERE linea1= '"+linea+"' ");
            
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

