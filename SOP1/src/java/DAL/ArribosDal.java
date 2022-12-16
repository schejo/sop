package DAL;

import Conexion.Conexion;
import MD.ArribosMd;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.zk.ui.util.Clients;

public class ArribosDal {

    private Connection conexion = null;
    private Conexion cnn = new Conexion();
    PreparedStatement ps = null;
    //PreparedStatement st = null;
    Statement st = null;
    ResultSet rs = null;

    public List<ArribosMd> REGselect(String num) throws SQLException {
        List<ArribosMd> allArribos = new ArrayList<ArribosMd>();

        String query = "SELECT "
                + " TRIM(tipo_buque),"
                + " TRIM(nom_tipo_buque)"
                + " FROM epqop.if_bq_tipo_arribo"
                + " WHERE tipo_buque = '" + num + "' ";
        try {
            conexion = cnn.Conexion();
            st = conexion.createStatement();
            rs = st.executeQuery(query);
            ArribosMd rg = new ArribosMd();
            while (rs.next()) {
                rg.setTipo(rs.getString(1));
                rg.setNombre(rs.getString(2));

                allArribos.add(rg);
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
        return allArribos;
    }

    public List<ArribosMd> RSelect() throws SQLException {
        List<ArribosMd> allArribos = new ArrayList<ArribosMd>();
      
        String query = "SELECT"
                + " TRIM(tipo_buque),"
                + " TRIM(nom_tipo_buque)"
                + " FROM epqop.if_bq_tipo_arribo "
                + " ORDER BY tipo_buque ASC ";
        try {
            conexion = cnn.Conexion();
            st = conexion.createStatement();
            rs = st.executeQuery(query);
            ArribosMd rg;
            while (rs.next()) {
                rg = new ArribosMd();
                rg.setTipo(rs.getString(1));
                rg.setNombre(rs.getString(2));

                allArribos.add(rg);
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
        return allArribos;
    }

    public void REGinsert(String tipo, String nombre) throws SQLException {
       
        String sql = "INSERT INTO"
                + " epqop.if_bq_tipo_arribo"
                + " (tipo_buque,"
                + "  nom_tipo_buque)"
                + " VALUES(?,?)";

        String sql0 = "SELECT MAX(TO_NUMBER(tipo_buque))+1 AS CODIGO FROM epqop.if_bq_tipo_arribo";
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

    public void REGupdate(String tipo, String nombre) throws SQLException {
        try {
            conexion = cnn.Conexion();
            conexion.setAutoCommit(false);
            System.out.println("ACTUALIZAR DATOS..!");
            System.out.println("Actualizar " + tipo);
            st = conexion.createStatement();

            st.executeUpdate("UPDATE "
                    + " epqop.if_bq_tipo_arribo"
                    + " SET nom_tipo_buque = '" + nombre + "'"
                    + " WHERE tipo_buque = '" + tipo + "' ");

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
