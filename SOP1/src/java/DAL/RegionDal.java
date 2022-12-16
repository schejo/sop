package DAL;

import Conexion.Conexion;
import MD.RegionMd;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.zk.ui.util.Clients;

public class RegionDal {

    private Connection conexion = null;
    private Conexion cnn = new Conexion();
    PreparedStatement ps = null;
    //PreparedStatement st = null;
    Statement st = null;
    ResultSet rs = null;

    public List<RegionMd> REGselect(String region) throws SQLException {
        List<RegionMd> allRegion = new ArrayList<RegionMd>();

        String query = "SELECT "
                + "  TRIM(region),"
                + "  TRIM(nom_region)"
                + "  FROM epqop.if_bq_regiones  "
                + "  WHERE region = '" + region + "'";
        try {
            conexion = cnn.Conexion();
            st = conexion.createStatement();
            rs = st.executeQuery(query);
            RegionMd rg = new RegionMd();
            while (rs.next()) {
                rg.setNumero(rs.getString(1));
                rg.setNombre(rs.getString(2));

                allRegion.add(rg);
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
        return allRegion;
    }

    public List<RegionMd> RSelect() throws SQLException {
        List<RegionMd> allRegion = new ArrayList<RegionMd>();

        String query = "SELECT "
                + " TRIM(region),"
                + " TRIM(nom_region)"
                + " FROM epqop.if_bq_regiones"
                + " ORDER BY region ASC ";

        try {
            conexion = cnn.Conexion();
            st = conexion.createStatement();
            rs = st.executeQuery(query);
            RegionMd rg;
            while (rs.next()) {
                rg = new RegionMd();
                rg.setNumero(rs.getString(1));
                rg.setNombre(rs.getString(2));

                allRegion.add(rg);
            }

            st.close();
            rs.close();
            conexion.close();
            conexion = null;
        } catch (SQLException e) {
            st.close();
//            rs.close();
            conexion.close();
            conexion = null;
            Clients.showNotification("ERROR AL CONSULTAR (Rselect) <br/> <br/> REGISTROS! <br/> " + e.getMessage().toString(),
                    "warning", null, "middle_center", 0);
        }
        return allRegion;
    }

    public void REGinsert(String region, String nombre) throws SQLException {
        Statement st = null;
        ResultSet rs = null;

        String sql = "INSERT INTO "
                + "epqop.if_bq_regiones "
                + "(region, "
                + "nom_region) "
                + "VALUES(?,?)";

        String sql0 = "SELECT MAX(TO_NUMBER(region))+1 AS CODIGO FROM epqop.if_bq_regiones";

        System.out.println("que lleva el correlativo  " + sql0);

        try {
            conexion = cnn.Conexion();
            conexion.setAutoCommit(false);
            st = conexion.createStatement();

            rs = st.executeQuery(sql0);
            System.out.println("que lleva el rs  " + rs);

            String codigo1 = "";
            while (rs.next()) {
                codigo1 = rs.getString("codigo");
            }
            System.out.println("que lleva el correlativo sql " + sql);
            ps = conexion.prepareStatement(sql);

            System.out.println("que lleva el correlativo ps  " + ps);

            ps.setString(1, codigo1);
            ps.setString(2, nombre);
            System.out.println("que lleva el correlativo ps ANTES EXECUTE  " + ps);
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

            System.out.println("que lleva el correlativo 2 sql " + sql);
            System.out.println("que lleva el correlativo 2 ps  " + ps);

            Clients.showNotification("ERROR AL INSERTAR <br/> <br/> REGISTROS! <br/> " + e.getMessage().toString(),
                    "warning", null, "middle_center", 0);
        }
    }

    public void REGupdate(String region, String nombre) throws SQLException {
        try {
            conexion = cnn.Conexion();
            conexion.setAutoCommit(false);
            System.out.println("ACTUALIZAR DATOS..!");
            System.out.println("Actualizar " + region);
            st = conexion.createStatement();

            st.executeUpdate("UPDATE "
                    + "epqop.if_bq_regiones "
                    + "SET nom_region = '" + nombre + "'"
                    + "WHERE region= '" + region + "' ");

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
