package DAL;

import Conexion.Conexion;
import MD.PersonalMd;
import MD.PuestosMd;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.zk.ui.util.Clients;

public class PersonalDal {

    private Connection conexion = null;
    private Conexion cnn = new Conexion();
    PreparedStatement ps = null;
    //PreparedStatement st = null;
    Statement st = null;
    ResultSet rs = null;

    public List<PersonalMd> REGselect(String num) throws SQLException {
        List<PersonalMd> allPersonal = new ArrayList<PersonalMd>();

        String query = "SELECT "
                + " TRIM(a.puesto_clase),"
                + " TRIM(a.nombre_empleado),"
                + " TRIM(b.descrip_puesto)"
                + " FROM epqop.catalogo_de_person a,"
                + "      epqop.if_catalogo_puesto b "
                + " WHERE a.puesto_clase = b.puesto_clase"
                + " AND a.puesto_clase = '" + num + "' ";
        try {
            conexion = cnn.Conexion();
            st = conexion.createStatement();
            rs = st.executeQuery(query);
            PersonalMd rg = new PersonalMd();
            while (rs.next()) {
                rg.setCodigo(rs.getString(1));
                rg.setNombre(rs.getString(2));
                rg.setPuesto(rs.getString(3));

                allPersonal.add(rg);
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
        return allPersonal;
    }

    public List<PersonalMd> RSelect() throws SQLException {
        List<PersonalMd> allPersonal = new ArrayList<PersonalMd>();

        String query = "SELECT\n"
                + "      TRIM(a.codigo_empleado), \n"
                + "      TRIM(a.nombre_empleado), \n"
                + "      TRIM(b.descrip_puesto) \n"
                + "FROM epqop.catalogo_de_person a, \n"
                + "     epqop.if_catalogo_puesto b \n"
                + "WHERE a.puesto_clase = b.puesto_clase\n"
                + "ORDER BY a.nombre_empleado ASC";

        try {
            conexion = cnn.Conexion();
            st = conexion.createStatement();
            rs = st.executeQuery(query);
            PersonalMd rg;
            while (rs.next()) {
                rg = new PersonalMd();
                rg.setCodigo(rs.getString(1));
                rg.setNombre(rs.getString(2));
                rg.setPuesto(rs.getString(3));

                allPersonal.add(rg);
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
        return allPersonal;
    }

    public void REGinsert(String codigo, String nombre, String puesto) throws SQLException {

        String sql = "INSERT INTO "
                + " epqop.catalogo_de_person"
                + " (codigo_empleado,"
                + " nombre_empleado,"
                + " puesto_clase)"
                + " VALUES(?,?,?)";
        try {
            conexion = cnn.Conexion();
            conexion.setAutoCommit(false);
            st = conexion.createStatement();

            ps = conexion.prepareStatement(sql);

            ps.setString(1, codigo);
            ps.setString(2, nombre);
            ps.setString(3, puesto);
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

    public void REGupdate(String codigo, String nombre, String puesto) throws SQLException {
        try {
            conexion = cnn.Conexion();
            conexion.setAutoCommit(false);
            System.out.println("ACTUALIZAR DATOS..!");
            System.out.println("Actualizar " + codigo);
            st = conexion.createStatement();

            st.executeUpdate("UPDATE "
                    + " epqop.catalogo_de_person"
                    + " SET nombre_empleado = '" + nombre + "'"
                    + ",puesto_clase = '" + puesto + "'"
                    + " WHERE puesto_clase = '" + puesto + "' ");

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

    public List<PuestosMd> PuestosSelect() throws SQLException {
        List<PuestosMd> allPuestos = new ArrayList<PuestosMd>();

        String query = "SELECT"
                + " TRIM(puesto_clase), "
                + " TRIM(descrip_puesto) "
                + " FROM epqop.if_catalogo_puesto "
                + " ORDER BY puesto_clase ASC ";
        try {
            conexion = cnn.Conexion();
            st = conexion.createStatement();
            rs = st.executeQuery(query);
            PuestosMd rg;
            while (rs.next()) {
                rg = new PuestosMd();
                rg.setCargo(rs.getString(1));
                rg.setDescripcion(rs.getString(2));

                allPuestos.add(rg);
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
        return allPuestos;
    }

}
