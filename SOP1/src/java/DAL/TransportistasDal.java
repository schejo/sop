package DAL;

import Conexion.Conexion;
import MD.TransportistasMd;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.zk.ui.util.Clients;

public class TransportistasDal {

    private Connection conexion = null;
    private Conexion cnn = new Conexion();
    PreparedStatement ps = null;
    //PreparedStatement st = null;
    Statement st = null;
    ResultSet rs = null;

    public List<TransportistasMd> REGselect(String num) throws SQLException {
        List<TransportistasMd> allTransportistas = new ArrayList<TransportistasMd>();

        String query = " SELECT   \n"
                + "       num_transpor,   \n"
                + "       nom_transpor,   \n"
                + "       observacion       \n"
                + "FROM epqop.if_cm_transportis    \n"
                + "WHERE num_transpor = '" + num + "' ";

        try {
            conexion = cnn.Conexion();
            st = conexion.createStatement();
            rs = st.executeQuery(query);
            TransportistasMd rg = new TransportistasMd();
            while (rs.next()) {
                rg.setNumero(rs.getString(1));
                rg.setNombre(rs.getString(2));
                rg.setTelefono(rs.getString(3));
                
                allTransportistas.add(rg);
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
        return allTransportistas;
    }

    public List<TransportistasMd> RSelect() throws SQLException {
        List<TransportistasMd> allTransportistas = new ArrayList<TransportistasMd>();

        String query = " SELECT   \n"
                + "       num_transpor,   \n"
                + "       nom_transpor,   \n"
                + "       observacion       \n"
                + "FROM epqop.if_cm_transportis    \n"
                + "ORDER BY num_transpor ASC";

        try {
            conexion = cnn.Conexion();
            st = conexion.createStatement();
            rs = st.executeQuery(query);
            TransportistasMd rg;
            while (rs.next()) {
                rg = new TransportistasMd();
                rg.setNumero(rs.getString(1));
                rg.setNombre(rs.getString(2));
                rg.setTelefono(rs.getString(3));
                
                allTransportistas.add(rg);
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
            Clients.showNotification("ERROR AL CONSULTAR <br/> <br/> REGISTROS! <br/> " + e.getMessage().toString(),
                    "warning", null, "middle_center", 0);
        }
        return allTransportistas;
    }

    public void REGinsert(String numero, String nombre, String telefono) throws SQLException {

        String sql = "INSERT INTO"
                + " epqop.if_cm_transportis"
                + " (num_transpor,"
                + "  nom_transpor,"
                + "  observacion"
                + " VALUES(?,?,?)";
        try {
            conexion = cnn.Conexion();
            conexion.setAutoCommit(false);
            st = conexion.createStatement();

            ps = conexion.prepareStatement(sql);

            ps.setString(1, numero);
            ps.setString(2, nombre);
            ps.setString(3, telefono);
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

    public void REGupdate(String numero, String nombre, String telefono) throws SQLException { //String nombre,
        try {
            conexion = cnn.Conexion();
            conexion.setAutoCommit(false);
            System.out.println("ACTUALIZAR DATOS..!");
            System.out.println("Actualizar " + numero);
            st = conexion.createStatement();

            st.executeUpdate("UPDATE"
                    + "  epqop.if_cm_transportis "
                    + "  SET "
                    + "        observacion = '" + telefono + "'"
                    + "  WHERE num_transpor = '" + numero + "' ");

            System.out.println("observacion ..:" + telefono);
            System.out.println("nom_transpor ..:" + nombre);
            System.out.println("num_transpor..:" + numero);

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
