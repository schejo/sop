package DAL;

import Conexion.Conexion;
import MD.ParticularesMd;
import MD.TipoparticularesMd;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.zk.ui.util.Clients;

public class TipoparticularesDal {

    private Connection conexion = null;
    private Conexion cnn = new Conexion();
    PreparedStatement ps = null;
    //PreparedStatement st = null;
    Statement st = null;
    ResultSet rs = null;

    public List<TipoparticularesMd> REGselect(String num) throws SQLException {

        List<TipoparticularesMd> allTipoparticulares = new ArrayList<TipoparticularesMd>();

        String query = "SELECT"
                + " TRIM(a.codigo_particular),"
                + " TRIM(a.nombre_particular),"
                + " TRIM(b.descrip_tipo_parti)"
                + " FROM epqop.particulares a,"
                + "      epqop.if_ca_tipo_particu b "
                + "WHERE a.tipo_particular = b.tipo_particular"
                + "AND a.codigo_particular = '" + num + "' ";
        try {
            conexion = cnn.Conexion();
            st = conexion.createStatement();
            rs = st.executeQuery(query);
            TipoparticularesMd rg = new TipoparticularesMd();
            while (rs.next()) {
                rg.setParticular(rs.getString(1));
                rg.setNombre(rs.getString(2));
                rg.setTipo(rs.getString(3));

                allTipoparticulares.add(rg);
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
        return allTipoparticulares;
    }

    public List<TipoparticularesMd> RSelect() throws SQLException {
        List<TipoparticularesMd> allTipoparticulares = new ArrayList<TipoparticularesMd>();

        String query = "SELECT"
                + " TRIM(a.codigo_particular), "
                + " TRIM(a.nombre_particular), "
                + " TRIM(b.descrip_tipo_parti) "
                + " FROM epqop.particulares a, "
                + "      epqop.if_ca_tipo_particu b "
                + "WHERE a.tipo_particular = b.tipo_particular ";

        try {
            conexion = cnn.Conexion();
            st = conexion.createStatement();
            rs = st.executeQuery(query);
            TipoparticularesMd rg;
            while (rs.next()) {
                rg = new TipoparticularesMd();
                rg.setParticular(rs.getString(1));
                rg.setNombre(rs.getString(2));
                rg.setTipo(rs.getString(3));

                allTipoparticulares.add(rg);
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
        return allTipoparticulares;
    }

    public void REGinsert(String codigo, String nombre, String tipo) throws SQLException {

        String sql = "INSERT INTO "
                + " epqop.particulares"
                + " (codigo_particular,"
                + " nombre_particular,"
                + " tipo_particular)"
                + " VALUES(?,?,?)";
        try {
            conexion = cnn.Conexion();
            conexion.setAutoCommit(false);
            st = conexion.createStatement();

            ps = conexion.prepareStatement(sql);

            ps.setString(1, codigo);
            ps.setString(2, nombre);
            ps.setString(3, tipo);
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

    public void REGupdate(String codigo, String nombre, String tipo) throws SQLException {
        try {
            conexion = cnn.Conexion();
            conexion.setAutoCommit(false);
            System.out.println("ACTUALIZAR DATOS..!");
            System.out.println("Actualizar " + codigo);
            st = conexion.createStatement();

            st.executeUpdate("UPDATE "
                    + " epqop.particulares "
                    + " SET nombre_particular = '" + nombre + "'"
                    + ",tipo_particular = '" + tipo + "'"
                    + " WHERE codigo_particular = '" + codigo + "' ");

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

    public List<ParticularesMd> ParticularesSelect() throws SQLException {
        List<ParticularesMd> allParticulares = new ArrayList<ParticularesMd>();
       
        String query = "SELECT"
                + "  TRIM(tipo_particular), "
                + "  TRIM(descrip_tipo_parti) "
                + "  FROM epqop.if_ca_tipo_particu "
                + "  ORDER BY tipo_particular ASC ";
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
        return allParticulares;
    }

}
