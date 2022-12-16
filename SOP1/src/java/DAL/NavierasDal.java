package DAL;

import Conexion.Conexion;
import MD.NavierasMd;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.zk.ui.util.Clients;

public class NavierasDal {

    private Connection conexion = null;
    private Conexion cnn = new Conexion();
    PreparedStatement ps = null;
    Statement st = null;
    ResultSet rs = null;

    public List<NavierasMd> REGselect(String num) throws SQLException {
        List<NavierasMd> allNavieras = new ArrayList<NavierasMd>();

        String query = "SELECT naviera1, nom_naviera,\n"
                + "       gln_nav, email,\n"
                + "       representa,contacto,\n"
                + "       agente,tel_agente, \n"
                + "       tel_contacto,"
                + "TO_CHAR(ingreso,'DD/MM/YYYY HH24:MI:SS') AS FECHA_INGRESO\n"
                + "FROM    epqop.if_bq_naviera\n"
                + "WHERE   naviera1 = '" + num + "' ";

        try {
            conexion = cnn.Conexion();
            st = conexion.createStatement();
            rs = st.executeQuery(query);
            NavierasMd rg = new NavierasMd();
            while (rs.next()) {
                rg.setNaviera1(rs.getString(1));
                rg.setNom_naviera(rs.getString(2));
                rg.setGln_nav(rs.getString(3));
                rg.setEmail(rs.getString(4));
                rg.setRepresenta(rs.getString(5));
                rg.setContacto(rs.getString(6));
                rg.setAgente(rs.getString(7));
                rg.setTel_agente(rs.getString(8));
                rg.setTel_contacto(rs.getString(9));
                rg.setIngreso(rs.getString(10));

                allNavieras.add(rg);
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
            Clients.showNotification("ERROR AL CONSULTAR <br/> <br/> REGISTROS! <br/> " + e.getMessage().toString(),
                    "warning", null, "middle_center", 0);
        }
        return allNavieras;
    }

    public List<NavierasMd> RSelect() throws SQLException {
        List<NavierasMd> allNavieras = new ArrayList<NavierasMd>();

        String query = "SELECT naviera1, nom_naviera,\n"
                + "       gln_nav, email,\n"
                + "       representa,contacto,\n"
                + "       agente,tel_agente, \n"
                + "       tel_contacto,"
                + "TO_CHAR(ingreso,'DD/MM/YYYY HH24:MI:SS') AS FECHA_INGRESO\n"
                + "FROM   epqop.if_bq_naviera\n"
                + "ORDER BY naviera1 ASC";
        try {
            conexion = cnn.Conexion();
            st = conexion.createStatement();
            rs = st.executeQuery(query);
            NavierasMd rg;
            while (rs.next()) {
                rg = new NavierasMd();
                rg.setNaviera1(rs.getString(1));
                rg.setNom_naviera(rs.getString(2));
                rg.setGln_nav(rs.getString(3));
                rg.setEmail(rs.getString(4));
                rg.setRepresenta(rs.getString(5));
                rg.setContacto(rs.getString(6));
                rg.setAgente(rs.getString(7));
                rg.setTel_agente(rs.getString(8));
                rg.setTel_contacto(rs.getString(9));
                rg.setIngreso(rs.getString(10));

                allNavieras.add(rg);
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
            Clients.showNotification("ERROR AL CONSULTAR <br/> <br/> REGISTROS! <br/> " + e.getMessage().toString(),
                    "warning", null, "middle_center", 0);
        }
        return allNavieras;
    }

    public void REGinsert(String naviera1, String nom_naviera, String gln_nav, String representa, String email, String contacto,
            String tel_contacto, String agente, String tel_agente, String ingreso) throws SQLException {

        String sql = "INSERT INTO "
                + " epqop.if_bq_naviera "
                + " (naviera1,nom_naviera,"
                + " gln_nav,representa,"
                + " email,contacto,"
                + " tel_contacto,agente,"
                + " tel_agente,ingreso)"
                + " VALUES(?,?,?,?,?,?,?,?,?,TO_DATE(?,'DD/MM/YYYY HH24:MI:SS'))";
        try {
            conexion = cnn.Conexion();
            conexion.setAutoCommit(false);
            st = conexion.createStatement();

            ps = conexion.prepareStatement(sql);

            ps.setString(1, naviera1);
            ps.setString(2, nom_naviera);
            ps.setString(3, gln_nav);
            ps.setString(4, representa);
            ps.setString(5, email);
            ps.setString(6, contacto);
            ps.setString(7, tel_contacto);
            ps.setString(8, agente);
            ps.setString(9, tel_agente);
            ps.setString(10, ingreso);
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

    public void REGupdate(String naviera1, String nom_naviera, String gln_nav, String representa, String email, String contacto,
            String tel_contacto, String agente, String tel_agente, String ingreso) throws SQLException {
        try {
            conexion = cnn.Conexion();
            conexion.setAutoCommit(false);
            System.out.println("ACTUALIZAR DATOS..!");
            System.out.println("Actualizar " + naviera1);
            st = conexion.createStatement();

            st.executeUpdate("UPDATE "
                    + " epqop.if_bq_naviera SET"
                    + " nom_naviera = '" + nom_naviera + "',"
                    + " gln_nav = '" + gln_nav + "',"
                    + " representa = '" + representa + "',"
                    + " email = '" + email + "',"
                    + " contacto = '" + contacto + "',"
                    + " tel_contacto = '" + tel_contacto + "',"
                    + " agente = '" + agente + "',"
                    + " tel_agente = '" + tel_agente + "',"
                    + " ingreso      = to_date('"+ingreso+"','DD/MM/YYYY HH24:MI:SS')\n"
                    + " WHERE naviera1 = '" + naviera1 + "' ");

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
