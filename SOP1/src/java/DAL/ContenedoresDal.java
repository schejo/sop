package DAL;

import Conexion.Conexion;
import MD.ContenedoresMd;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.zk.ui.util.Clients;

public class ContenedoresDal {

    private Connection conexion = null;
    private Conexion cnn = new Conexion();
    PreparedStatement ps = null;
    //PreparedStatement st = null;
    Statement st = null;
    ResultSet rs = null;

    public List<ContenedoresMd> REGselect(String num) throws SQLException {
        List<ContenedoresMd> allContenedores = new ArrayList<ContenedoresMd>();

        String query = "SELECT "
                + " TRIM(tipo_contenedor),"
                + " TRIM(descrip_tipo_cont),"
                + " TRIM(cantidad_teu)"
                + " FROM epqop.if_ca_tipo_contene"
                + " WHERE tipo_contenedor = '" + num + "' ";
        try {
            conexion = cnn.Conexion();
            st = conexion.createStatement();
            rs = st.executeQuery(query);
            ContenedoresMd rg = new ContenedoresMd();
            while (rs.next()) {
                rg.setTipo(rs.getString(1));
                rg.setDescripcion(rs.getString(2));
                rg.setCantidad(rs.getString(3));

                allContenedores.add(rg);
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
        return allContenedores;
    }

    public List<ContenedoresMd> RSelect() throws SQLException {
        List<ContenedoresMd> allContenedores = new ArrayList<ContenedoresMd>();

        String query = "SELECT "
                + " TRIM(tipo_contenedor), "
                + " TRIM(descrip_tipo_cont),"
                + " TRIM(cantidad_teu)"
                + " FROM epqop.if_ca_tipo_contene"
                + " ORDER BY  tipo_contenedor ASC ";
        try {
            conexion = cnn.Conexion();
            st = conexion.createStatement();
            rs = st.executeQuery(query);
            ContenedoresMd rg;
            while (rs.next()) {
                rg = new ContenedoresMd();
                rg.setTipo(rs.getString(1));
                rg.setDescripcion(rs.getString(2));
                rg.setCantidad(rs.getString(3));

                allContenedores.add(rg);
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
        return allContenedores;
    }

    public void REGinsert(String tipo, String descripcion, String cantidad) throws SQLException {

        String sql = "INSERT INTO"
                + " epqop.if_ca_tipo_contene"
                + " (tipo_contenedor,"
                + "  descrip_tipo_cont,"
                + "  cantidad_teu)"
                + " VALUES(?,?,?)";
        try {
            conexion = cnn.Conexion();
            conexion.setAutoCommit(false);
            st = conexion.createStatement();

            ps = conexion.prepareStatement(sql);

            ps.setString(1, tipo);
            ps.setString(2, descripcion);
            ps.setString(3, cantidad);
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

    public void REGupdate(String tipo, String descripcion, String cantidad) throws SQLException {
        try {
            conexion = cnn.Conexion();
            conexion.setAutoCommit(false);
            System.out.println("ACTUALIZAR DATOS..!");
            System.out.println("Actualizar " + tipo);
            st = conexion.createStatement();

            st.executeUpdate("UPDATE"
                    + " epqop.if_ca_tipo_contene "
                    + " SET descrip_tipo_cont = '" + descripcion + "'"
                    + ",    cantidad_teu = '" + cantidad + "'"
                    + " WHERE tipo_contenedor= '" + tipo + "' ");

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
