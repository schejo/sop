package DAL;

import Conexion.Conexion;
import MD.AreasTrabajoMd;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.zk.ui.util.Clients;

public class AreasTrabajoDal {

    private Connection conexion = null;
    private Conexion cnn = new Conexion();
    PreparedStatement ps = null;
    //PreparedStatement st = null;
    Statement st = null;
    ResultSet rs = null;

    public List<AreasTrabajoMd> REGselect(String num) throws SQLException {
        List<AreasTrabajoMd> allAreasTrabajo = new ArrayList<AreasTrabajoMd>();

        String query = "SELECT"
                + " TRIM(codigo_area_trab),"
                + " TRIM(descripcion_area),"
                + " TRIM(tiempo_estadia)"
                + " FROM epqop.if_eq_area_trabajo"
                + " WHERE codigo_area_trab = '" + num + "' ";
        try {
            conexion = cnn.Conexion();
            st = conexion.createStatement();
            rs = st.executeQuery(query);
            AreasTrabajoMd rg = new AreasTrabajoMd();
            while (rs.next()) {
                rg.setCodigo(rs.getString(1));
                rg.setDescripcion(rs.getString(2));
                rg.setTiempo(rs.getString(3));

                allAreasTrabajo.add(rg);
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
        return allAreasTrabajo;
    }

    public List<AreasTrabajoMd> RSelect() throws SQLException {
        List<AreasTrabajoMd> allAreasTrabajo = new ArrayList<AreasTrabajoMd>();

        String query = "SELECT"
                + " TRIM(codigo_area_trab), "
                + " TRIM(descripcion_area),"
                + " TRIM(tiempo_estadia)"
                + " FROM epqop.if_eq_area_trabajo"
                + " ORDER BY codigo_area_trab ASC ";
        try {
            conexion = cnn.Conexion();
            st = conexion.createStatement();
            rs = st.executeQuery(query);
            AreasTrabajoMd rg;
            while (rs.next()) {
                rg = new AreasTrabajoMd();
                rg.setCodigo(rs.getString(1));
                rg.setDescripcion(rs.getString(2));
                rg.setTiempo(rs.getString(3));

                allAreasTrabajo.add(rg);
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
        return allAreasTrabajo;
    }

    public void REGinsert(String codigo, String descripcion, String tiempo) throws SQLException {
        String sql = "INSERT INTO "
                + "epqop.if_eq_area_trabajo"
                + " (codigo_area_trab,"
                + " descripcion_area,"
                + " tiempo_estadia)"
                + " VALUES(?,?,?)";
        try {
            conexion = cnn.Conexion();
            conexion.setAutoCommit(false);
            st = conexion.createStatement();

            ps = conexion.prepareStatement(sql);

            ps.setString(1, codigo);
            ps.setString(2, descripcion);
            ps.setString(3, tiempo);
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

    public void REGupdate(String codigo, String descripcion, String tiempo) throws SQLException {
        try {
            conexion = cnn.Conexion();
            conexion.setAutoCommit(false);
            System.out.println("ACTUALIZAR DATOS..!");
            System.out.println("Actualizar " + codigo);
            st = conexion.createStatement();

            st.executeUpdate("UPDATE "
                    + " epqop.if_eq_area_trabajo "
                    + "SET descripcion_area = '" + descripcion + "'"
                    + ",   tiempo_estadia = '" + tiempo + "'"
                    + " WHERE codigo_area_trab = '" + codigo + "' ");

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
