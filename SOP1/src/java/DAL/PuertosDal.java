package DAL;

import Conexion.Conexion;
import MD.PaisesMd;
import MD.PuertosMd;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.zk.ui.util.Clients;

public class PuertosDal {

    private Connection conexion = null;
    private Conexion cnn = new Conexion();
    PreparedStatement ps = null;
    //PreparedStatement st = null;
    Statement st = null;
    ResultSet rs = null;

    public List<PuertosMd> REGselect(String num) throws SQLException {
        List<PuertosMd> allPuertos = new ArrayList<PuertosMd>();
       
        String query = "SELECT"
                + " TRIM(a.puerto),"
                + " TRIM(a.nombre_puerto),"
                + " TRIM(b.nombre_pais)"
                + " FROM epqop.if_bq_puertos a,"
                + "      epqop.if_bq_paises b "
                + " WHERE a.pais = b.pais"
                + " AND a.puerto = '" + num + "' ";
        try {
            conexion = cnn.Conexion();
            st = conexion.createStatement();
            rs = st.executeQuery(query);
            PuertosMd rg = new PuertosMd();
            while (rs.next()) {
                rg.setPuerto(rs.getString(1));
                rg.setNombre(rs.getString(2));
                rg.setPais(rs.getString(3));

                allPuertos.add(rg);
            }

            st.close();
            rs.close();
            conexion.close();
            conexion = null;
        } catch (SQLException e) {
            conexion.close();
            conexion = null;
            Clients.showNotification("ERROR AL CONSULTAR (REGselect) <br/> <br/> REGISTROS! <br/> " + e.getMessage().toString(),
                    "warning", null, "middle_center", 0);
        }
        return allPuertos;
    }

    public List<PuertosMd> RSelect() throws SQLException {
        List<PuertosMd> allPuertos = new ArrayList<PuertosMd>();
      
        String query = "SELECT"
                + " TRIM(a.puerto), "
                + " TRIM(a.nombre_puerto), "
                + " TRIM(b.nombre_pais) "
                + " FROM epqop.if_bq_puertos a, "
                + "      epqop.if_bq_paises b "
                + " WHERE a.pais = b.pais ";

        try {
            conexion = cnn.Conexion();
            st = conexion.createStatement();
            rs = st.executeQuery(query);
            PuertosMd rg;
            while (rs.next()) {
                rg = new PuertosMd();
                rg.setPuerto(rs.getString(1));
                rg.setNombre(rs.getString(2));
                rg.setPais(rs.getString(3));

                allPuertos.add(rg);
            }

            st.close();
            rs.close();
            conexion.close();
            conexion = null;
        } catch (SQLException e) {
            conexion.close();
            conexion = null;
            Clients.showNotification("ERROR AL CONSULTAR (Rselect) <br/> <br/> REGISTROS! <br/> " + e.getMessage().toString(),
                    "warning", null, "middle_center", 0);
        }
        return allPuertos;
    }

    public void REGinsert(String puerto, String nombre, String pais) throws SQLException {
        String sql = "INSERT INTO "
                + " epqop.if_bq_puertos"
                + " (puerto,"
                + " nombre_puerto,"
                + " pais)"
                + " VALUES(?,?,?)";
        try {
            conexion = cnn.Conexion();
            conexion.setAutoCommit(false);
            st = conexion.createStatement();

            ps = conexion.prepareStatement(sql);

            ps.setString(1, puerto);
            ps.setString(2, nombre);
            ps.setString(3, pais);
            ps.executeUpdate();
            ps.close();
            conexion.commit();
            Clients.showNotification("REGISTRO CREADO <br/> CON EXITO  <br/>");
            conexion.close();
            conexion = null;
            System.out.println("Conexion Cerrada" + conexion);

        } catch (SQLException e) {
            conexion.rollback();
            conexion.close();
            conexion = null;
            Clients.showNotification("ERROR AL INSERTAR <br/> <br/> REGISTROS! <br/> " + e.getMessage().toString(),
                    "warning", null, "middle_center", 0);
        }
    }

    public void REGupdate(String puerto, String nombre, String pais) throws SQLException {
        try {
            conexion = cnn.Conexion();
            conexion.setAutoCommit(false);
            System.out.println("ACTUALIZAR DATOS..!");
            System.out.println("Actualizar " + puerto);
            st = conexion.createStatement();

            st.executeUpdate("UPDATE"
                    + " epqop.if_bq_puertos"
                    + " SET nombre_puerto = '" + nombre + "'"
                    + ",pais = '" + pais + "'"
                    + " WHERE puerto = '" + puerto + "' ");
            
            Clients.showNotification("REGISTRO ACTUALIZADO <br/> CON EXITO  <br/>");
            System.out.println("Actualizacion Exitosa.! ");
            st.close();
            conexion.commit();
            conexion.close();
        } catch (SQLException e) {
            conexion.rollback();
            conexion.close();
            String mensaje = e.getMessage();
            Clients.showNotification("ERROR AL ACTUALIZAR <br/>'" + mensaje + "' <br/> REGISTROS! <br/> ",
                    "warning", null, "middle_center", 0);
            System.out.println("Actualizacion EXCEPTION.: " + mensaje);
        }

    }

    public List<PaisesMd> PaisSelect() throws SQLException {
        List<PaisesMd> allPais = new ArrayList<PaisesMd>();
        
        String query = "SELECT"
                + " TRIM(pais), "
                + " TRIM(nombre_pais) "
                + " FROM epqop.if_bq_paises "
                + "ORDER BY pais ASC ";
        try {
            conexion = cnn.Conexion();
            st = conexion.createStatement();
            rs = st.executeQuery(query);
            PaisesMd rg;
            while (rs.next()) {
                rg = new PaisesMd();
                rg.setPais(rs.getString(1));
                rg.setNombre(rs.getString(2));

                allPais.add(rg);
            }

            st.close();
            rs.close();
            conexion.close();
            conexion = null;
        } catch (SQLException e) {
            st.close();
            rs.close();
            conexion.close();
            conexion = null;
            Clients.showNotification("ERROR AL CONSULTAR (Rselect) <br/> <br/> REGISTROS! <br/> " + e.getMessage().toString(),
                    "warning", null, "middle_center", 0);
        }
        return allPais;
    }

}
