package DAL;

import Conexion.Conexion;
import MD.BultosMd;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.zk.ui.util.Clients;

public class BultosDal {
    
    private Connection conexion = null;
    private Conexion cnn = new Conexion();
    PreparedStatement ps = null;
    //PreparedStatement st = null;
    Statement st = null;
    ResultSet rs = null;
    
    
    public List<BultosMd> REGselect(String num) throws SQLException{
        List<BultosMd> allBultos = new ArrayList<BultosMd>();
        
        String query = "SELECT"
                + " TRIM(codigo_tipo_bultos),"
                + " TRIM(nom_tipo_bulto_esp),"
                + " TRIM(nom_tipo_bulto_ing)"
                + " FROM epqop.if_ca_tipos_bulto"
                + " WHERE codigo_tipo_bultos = '"+num+"' ";
        try {
            conexion = cnn.Conexion();
            st = conexion.createStatement();
            rs = st.executeQuery(query);
            BultosMd rg = new BultosMd();
            while (rs.next()) {
                rg.setCodigo(rs.getString(1));
                rg.setNombre1(rs.getString(2));
                rg.setNombre2(rs.getString(3));
                
                allBultos.add(rg);
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
        return allBultos;
    }
    
    public List<BultosMd> RSelect() throws SQLException{
        List<BultosMd> allBultos = new ArrayList<BultosMd>();
        
        String query = "SELECT "
                + " TRIM(codigo_tipo_bultos), "
                + " TRIM(nom_tipo_bulto_esp),"
                + " TRIM(nom_tipo_bulto_ing)"
                + " FROM epqop.if_ca_tipos_bulto "
                + " ORDER BY codigo_tipo_bultos ASC ";
        try {
            conexion = cnn.Conexion();
            st = conexion.createStatement();
            rs = st.executeQuery(query);
            BultosMd rg;
            while (rs.next()) {
                rg = new BultosMd();
                rg.setCodigo(rs.getString(1));
                rg.setNombre1(rs.getString(2));
                rg.setNombre2(rs.getString(3));
                
                allBultos.add(rg);
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
        return allBultos;
    }
    
    public void REGinsert(String codigo,String nombre1, String nombre2) throws SQLException{
        
        String sql = "INSERT INTO "
                + " epqop.if_ca_tipos_bulto"
                + " (codigo_tipo_bultos,"
                + " nom_tipo_bulto_esp,"
                + " nom_tipo_bulto_ing)"
                + " VALUES(?,?,?)";
        try {
            conexion = cnn.Conexion();
            conexion.setAutoCommit(false);
            st = conexion.createStatement();

            ps = conexion.prepareStatement(sql);
            
            ps.setString(1, codigo);
            ps.setString(2, nombre1);
            ps.setString(3, nombre2);
            
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
    
    public void REGupdate (String codigo, String nombre1, String nombre2) throws SQLException{
        try {
            conexion = cnn.Conexion();
            conexion.setAutoCommit(false);
            System.out.println("ACTUALIZAR DATOS..!");
            System.out.println("Actualizar "+codigo);
            st = conexion.createStatement();

            st.executeUpdate("UPDATE epqop.if_ca_tipos_bulto "
                    + " SET nom_tipo_bulto_esp = '"+nombre1+"'"
                    + ",nom_tipo_bulto_ing = '"+nombre2+"'"
                    + " WHERE codigo_tipo_bultos= '"+codigo+"' ");
            
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


