package DAL;

import Conexion.Conexion;
import MD.ActividadesBuqueMd;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.zk.ui.util.Clients;

public class ActividadesBuqueDal {

    private Connection conexion = null;
    private Conexion cnn = new Conexion();
    PreparedStatement ps = null;
    Statement st = null;
    ResultSet rs = null;

    public List<ActividadesBuqueMd> REGselect(String num) throws SQLException {
        List<ActividadesBuqueMd> allActividadesBuque = new ArrayList<ActividadesBuqueMd>();

        String query = "SELECT"
                + "        TRIM(num_actividad1),"
                + "        TRIM(nom_actividad), "
                + "        TRIM (nombre_duracion), "
                + "        TRIM (num_activ_calculo), "
                + "        TRIM (lleva_atracadero), "
                + "        TRIM (lleva_practico), "
                + "        TRIM (lleva_remolcador), "
                + "        TRIM (lleva_calado) "
                + "        FROM epqop.if_bq_activ_buque "
                + "        WHERE num_actividad1 = '" + num + "' ";
        try {
            conexion = cnn.Conexion();
            st = conexion.createStatement();
            rs = st.executeQuery(query);
            ActividadesBuqueMd rg = new ActividadesBuqueMd();
            while (rs.next()) {
                
                rg.setNum_actividad1(rs.getString(1));
                rg.setNom_actividad(rs.getString(2));
                rg.setNombre_duracion(rs.getString(3));
                rg.setNum_activ_calculo(rs.getString(4));
                rg.setLleva_atracadero(rs.getString(5));
                rg.setLleva_practico(rs.getString(6));
                rg.setLleva_remolcador(rs.getString(7));
                rg.setLleva_calado(rs.getString(8));
                allActividadesBuque.add(rg);
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
            Clients.showNotification("ERROR AL CONSULTAR  <br/> <br/> REGISTROS! <br/> " + e.getMessage().toString(),
                    "warning", null, "middle_center", 0);
        }
        return allActividadesBuque;
    }

    public List<ActividadesBuqueMd> RSelect() throws SQLException {
        List<ActividadesBuqueMd> allActividadesBuque = new ArrayList<ActividadesBuqueMd>();

        String query = "SELECT"
                + "         TRIM (num_actividad1),"
                + "         TRIM (nom_actividad), "
                + "         TRIM (nombre_duracion), "
                + "         TRIM (num_activ_calculo), "
                + "         TRIM (DECODE(lleva_atracadero,'S','SI','N','NO')), "
                + "         TRIM (DECODE(lleva_practico,'S','SI','N','NO')), "
                + "         TRIM (DECODE(lleva_remolcador,'S','SI','N','NO')), "
                + "         TRIM (DECODE(lleva_calado,'S','SI','N','NO')) "
                + "         FROM epqop.if_bq_activ_buque"
                + "         ORDER BY num_actividad1 ASC";

        try {
            conexion = cnn.Conexion();
            st = conexion.createStatement();
            rs = st.executeQuery(query);
            ActividadesBuqueMd rg;
            while (rs.next()) {
                
                rg = new ActividadesBuqueMd();
                rg.setNum_actividad1(rs.getString(1));
                rg.setNom_actividad(rs.getString(2));
                rg.setNombre_duracion(rs.getString(3));
                rg.setNum_activ_calculo(rs.getString(4));
                rg.setLleva_atracadero(rs.getString(5));
                rg.setLleva_practico(rs.getString(6));
                rg.setLleva_remolcador(rs.getString(7));
                rg.setLleva_calado(rs.getString(8));
                allActividadesBuque.add(rg);
            }

            st.close();
            rs.close();
            conexion.close();
            conexion = null;
        } catch (SQLException e) {
            conexion.close();
            conexion = null;
            Clients.showNotification("ERROR AL CONSULTAR aqui <br/> <br/> REGISTROS! <br/> " + e.getMessage().toString(),
                    "warning", null, "middle_center", 0);
        }
        return allActividadesBuque;
    }

    public void REGinsert(String numero, String nombre, String duracion, String calculo, String atracadero, String practico, String remolcador, String calado) throws SQLException {

        String sql = "INSERT INTO "
                + " epqop.if_bq_activ_buque "
                + " (num_actividad1,"
                + " nom_actividad,"
                + " nombre_duracion,"
                + " num_activ_calculo,"
                + " lleva_atracadero,"
                + " lleva_practico,"
                + " lleva_remolcador,"
                + " lleva_calado)"
                + " VALUES(?,?,?,?,?,?,?,?)";

        String sql0 = "SELECT MAX(TO_NUMBER(num_actividad1))+1 AS CODIGO FROM epqop.if_bq_activ_buque";
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
            ps.setString(3, duracion);
            ps.setString(4, calculo);
            ps.setString(5, atracadero);
            ps.setString(6, practico);
            ps.setString(7, remolcador);
            ps.setString(8, calado);

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

    public void REGupdate(String numero, String nombre, String duracion, String calculo, String atracadero, String practico, String remolcador, String calado) throws SQLException {
        try {
            conexion = cnn.Conexion();
            conexion.setAutoCommit(false);
            st = conexion.createStatement();

            st.executeUpdate("UPDATE "
                    + "epqop.if_bq_activ_buque"
                    + " SET "
                    + " nom_actividad = '" + nombre + "',"
                    + " nombre_duracion = '" + duracion + "',"
                    + " num_activ_calculo = '" + calculo + "',"
                    + " lleva_atracadero = '" + atracadero + "',"
                    + " lleva_practico = '" + practico + "',"
                    + " lleva_remolcador = '" + remolcador + "',"
                    + " lleva_calado = '" + calado + "',"
                    + "WHERE num_actividad1 = '" + numero + "' ");

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
