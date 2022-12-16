package DAL;

import Conexion.Conexion;
import MD.TipoCargaMd;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.zk.ui.util.Clients;

public class TipoCargaDal {
    
    private Connection conexion = null;
    private Conexion cnn = new Conexion();
    PreparedStatement ps = null;
    Statement st = null;
    ResultSet rs = null;
    
    
    public List<TipoCargaMd> REGselect(String cod) throws SQLException{
        List<TipoCargaMd> allTipoCarga = new ArrayList<TipoCargaMd>();
        
        String query = "SELECT"
                + " TRIM(cod_carga),"
                + " TRIM(nom_carga_esp),"
                + " TRIM(nom_carga_ing),"
                +"  TRIM(producto_quetzal)"
                + " FROM epqop.if_ca_tipo_carga "
                + " WHERE cod_carga = '"+cod+"' ";
        try {
            conexion = cnn.Conexion();
            st = conexion.createStatement();
            rs = st.executeQuery(query);
            TipoCargaMd rg = new TipoCargaMd();
            while (rs.next()) {
                rg.setTipo(rs.getString(1));
                rg.setEspanol(rs.getString(2));
                rg.setIngles(rs.getString(3));
                rg.setClasificacion(rs.getString(4));
                //System.out.println("Linea 43 en el WHILE ... "+query);
                allTipoCarga.add(rg);
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
        return allTipoCarga;
    }
    
    public List<TipoCargaMd> RSelect() throws SQLException{
        List<TipoCargaMd> allTipoCarga = new ArrayList<TipoCargaMd>();
        
        String query = "SELECT"
                + " TRIM(cod_carga), "
                + " TRIM(nom_carga_esp),"
                + " TRIM(nom_carga_ing),"
                + " TRIM(producto_quetzal)"
                + " FROM epqop.if_ca_tipo_carga"
                + " ORDER BY cod_carga ASC ";
        
        System.out.println("Linea 67 en el RSelect ... "+query);
        try {
            conexion = cnn.Conexion();
            st = conexion.createStatement();
            rs = st.executeQuery(query);
            TipoCargaMd rg;
            System.out.println("Linea 73 en el try ... "+query);
            while (rs.next()) {
                rg = new TipoCargaMd();
                rg.setTipo(rs.getString(1));
                rg.setEspanol(rs.getString(2));
                rg.setIngles(rs.getString(3));
                rg.setClasificacion(rs.getString(4));
                System.out.println("Linea 80 en el RSelect ... "+query);
                allTipoCarga.add(rg);
                System.out.println("Linea 82 en el RSelect ... "+rs.getString(1));
                System.out.println("Linea 82 en el RSelect ... "+rs.getString(2));
                System.out.println("Linea 82 en el RSelect ... "+rs.getString(3));
                System.out.println("Linea 82 en el RSelect ... "+rs.getString(4));
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
        return allTipoCarga;
    }
    
    public void REGinsert(String codigo,String español, String ingles, String clasificacion) throws SQLException{
        
        String sql = "INSERT INTO "
                + "  epqop.if_ca_tipo_carga"
                + " (cod_carga,"
                + " nom_carga_esp,"
                + " nom_carga_ing,"
                + " producto_quetzal)"
                + " VALUES(?,?,?,?)";
        try {
            conexion = cnn.Conexion();
            conexion.setAutoCommit(false);
            st = conexion.createStatement();

            ps = conexion.prepareStatement(sql);
            
            ps.setString(1, codigo);
            ps.setString(2, español);
            ps.setString(3, ingles);
            ps.setString(4, clasificacion);
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
    
    public void REGupdate (String codigo, String español, String ingles, String clasificacion) throws SQLException{
        try {
            conexion = cnn.Conexion();
            conexion.setAutoCommit(false);
            System.out.println("ACTUALIZAR DATOS..!");
            System.out.println("Actualizar "+codigo);
            st = conexion.createStatement();

            st.executeUpdate("UPDATE "
                    + " epqop.if_ca_tipo_carga "
                    + " SET nom_carga_esp = '"+español+"'"
                    + ",nom_carga_ing = '"+ingles+"'"
                    + ",producto_quetzal = '"+clasificacion+"'"
                    + " WHERE cod_carga = '"+codigo+"' ");
            
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


