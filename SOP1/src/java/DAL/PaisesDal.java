package DAL;

import Conexion.Conexion;
import MD.PaisesMd;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.zk.ui.util.Clients;

public class PaisesDal {

    private Connection conexion = null;
    private Conexion cnn = new Conexion();
    PreparedStatement ps = null;
    //PreparedStatement st = null;
    Statement st = null;
    ResultSet rs = null;

    public List<PaisesMd> REGselect(String num) throws SQLException {
        List<PaisesMd> allPaises = new ArrayList<PaisesMd>();
        
        String query = "SELECT "
                + " TRIM(pais),"
                + " TRIM(nombre_pais),"
                + " TRIM(region)"
                + " FROM epqop.if_bq_paises"
                + " WHERE pais = '" + num + "' ";
        try {
            conexion = cnn.Conexion();
            st = conexion.createStatement();
            rs = st.executeQuery(query);
            PaisesMd rg = new PaisesMd();
            while (rs.next()) {
                rg.setPais(rs.getString(1));
                rg.setNombre(rs.getString(2));
                rg.setRegion(rs.getString(3));

                allPaises.add(rg);
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
            Clients.showNotification("ERROR AL CONSULTAR (REGselect) <br/> <br/> REGISTROS! <br/> " + e.getMessage().toString(),
                    "warning", null, "middle_center", 0);
        }
        return allPaises;
    }

    public List<PaisesMd> RSelect() throws SQLException {
        List<PaisesMd> allPaises = new ArrayList<PaisesMd>();
        
        String query = "SELECT"
                + " TRIM(a.pais), "
                + " TRIM(a.nombre_pais),"
                + " TRIM(b.nom_region)"
                + " FROM epqop.if_bq_paises a,"
                + " epqop.if_bq_regiones b "
                + " WHERE a.region = b.region"
                + " ORDER BY pais ASC ";

        try {
            conexion = cnn.Conexion();
            st = conexion.createStatement();
            rs = st.executeQuery(query);
            PaisesMd rg;
            while (rs.next()) {
                rg = new PaisesMd();
                rg.setPais(rs.getString(1));
                rg.setNombre(rs.getString(2));
                rg.setNomregion(rs.getString(3));

                allPaises.add(rg);
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
        return allPaises;
    }

    public void REGinsert(String pais, String nombre, String region) throws SQLException {
        
        String sql = "INSERT INTO "
                + "epqop.if_bq_paises"
                + " (pais,"
                + "  nombre_pais,"
                + "  region)"
                + " VALUES(?,?,?)";
        try {
            conexion = cnn.Conexion();
            conexion.setAutoCommit(false);
            st = conexion.createStatement();

            ps = conexion.prepareStatement(sql);

            ps.setString(1, pais);
            ps.setString(2, nombre);
            ps.setString(3, region);
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

    public void REGupdate(String pais, String nombre, String region) throws SQLException {
        try {
            conexion = cnn.Conexion();
            conexion.setAutoCommit(false);
            System.out.println("ACTUALIZAR DATOS..!");
            System.out.println("Actualizar " + pais);
            st = conexion.createStatement();

            st.executeUpdate("UPDATE "
                    + " epqop.if_bq_paises "
                    + " SET nombre_pais = '" + nombre + "'"
                    + ",region = '" + region + "'"
                    + " WHERE pais = '" + pais + "' ");

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

}
