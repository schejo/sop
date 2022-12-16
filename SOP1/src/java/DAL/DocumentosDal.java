package DAL;

import Conexion.Conexion;
import MD.DocumentosMd;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.zk.ui.util.Clients;

public class DocumentosDal {

    private Connection conexion = null;
    private Conexion cnn = new Conexion();
    PreparedStatement ps = null;
    Statement st = null;
    ResultSet rs = null;

    public List<DocumentosMd> REGselect(String num) throws SQLException {
        List<DocumentosMd> allDocumentos = new ArrayList<DocumentosMd>();

        String query = "SELECT "
                + " TRIM(codigo_docto),"
                + " TRIM(nombre_docto),"
                + " TRIM(clase_docto)"
                + " FROM epqop.if_ca_tipo_docto "
                + " WHERE codigo_docto = '" + num + "' ";
        try {
            conexion = cnn.Conexion();
            st = conexion.createStatement();
            rs = st.executeQuery(query);
            DocumentosMd rg = new DocumentosMd();
            while (rs.next()) {
                rg.setCodigo(rs.getString(1));
                rg.setNombre(rs.getString(2));
                rg.setClase(rs.getString(3));

                allDocumentos.add(rg);
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
        return allDocumentos;
    }

    public List<DocumentosMd> RSelect() throws SQLException {
        List<DocumentosMd> allDocumentos = new ArrayList<DocumentosMd>();

        String query = "SELECT "
                + " TRIM(codigo_docto), "
                + " TRIM(nombre_docto),"
                + " TRIM (decode(clase_docto,'P','PAGADO','G','GENERADO'))"
                + " FROM epqop.if_ca_tipo_docto"
                + " ORDER BY codigo_docto ASC ";
        try {
            conexion = cnn.Conexion();
            st = conexion.createStatement();
            rs = st.executeQuery(query);
            DocumentosMd rg;
            while (rs.next()) {
                rg = new DocumentosMd();
                rg.setCodigo(rs.getString(1));
                rg.setNombre(rs.getString(2));
                rg.setClase(rs.getString(3));

                allDocumentos.add(rg);
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
        return allDocumentos;
    }

    public void REGinsert(String codigo, String nombre, String clase) throws SQLException {

        String sql = "INSERT INTO "
                + " epqop.if_ca_tipo_docto"
                + " (codigo_docto,"
                + " nombre_docto,"
                + " clase_docto)"
                + " VALUES(?,?,?)";


         String sql0 = "SELECT MAX(TO_NUMBER(codigo_docto))+1 AS CODIGO FROM epqop.if_ca_tipo_docto";
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
            ps = conexion.prepareStatement(sql);

            ps.setString(1, codigo1);
            ps.setString(2, nombre);
            ps.setString(3, clase);
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

    public void REGupdate(String codigo, String nombre, String clase) throws SQLException {
        try {
            conexion = cnn.Conexion();
            conexion.setAutoCommit(false);
            System.out.println("ACTUALIZAR DATOS..!");
            System.out.println("Actualizar " + codigo);
            st = conexion.createStatement();

            st.executeUpdate("UPDATE epqop.if_ca_tipo_docto "
                    + "       SET    nombre_docto = '" + nombre + "'"
                    + ",             clase_docto = '" + clase + "'"
                    + "      WHERE   codigo_docto = '" + codigo + "' ");

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
