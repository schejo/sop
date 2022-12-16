package DAL;

import Conexion.Conexion;
import MD.AtracaderosMd;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.zk.ui.util.Clients;

public class AtracaderosDal {

    private Connection conexion = null;
    private Conexion cnn = new Conexion();
    PreparedStatement ps = null;
    Statement st = null;
    ResultSet rs = null;
    int global =3163;

    public List<AtracaderosMd> REGselect(String num) throws SQLException {
        List<AtracaderosMd> allAtracaderos = new ArrayList<AtracaderosMd>();
        
        String query = "SELECT "
                + "TRIM(num_atracadero1),"
                + "TRIM(tipo_terminal), "
                + "TRIM(pk_inicial), "
                + "TRIM(pk_final),"
                + "TRIM (profundidad), "
                + "TRIM (intervalo), "
                + "TRIM (muelle), "
                + "TRIM (estatus), "
                + "TRIM (fuera_zona_abrigo) "
                + "FROM epqop.if_bq_atracaderos WHERE num_atracadero1 = '" + num + "' ";
        try {
            conexion = cnn.Conexion();
            st = conexion.createStatement();
            rs = st.executeQuery(query);
            AtracaderosMd rg = new AtracaderosMd();
            while (rs.next()) {
                rg.setAtracadero(rs.getString(1));
                rg.setTerminal(rs.getString(2));
                rg.setPk_inicial(rs.getString(3));
                rg.setPk_final(rs.getString(4));
                rg.setProfundidad(rs.getString(5));
                rg.setIntervalo(rs.getString(6));
                rg.setMuelle(rs.getString(7));
                rg.setEstatus(rs.getString(8));
                rg.setFuera_abrigo(rs.getString(9));
                allAtracaderos.add(rg);
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
            Clients.showNotification("ERROR AL CONSULTAR ATRACADERO <br/> <br/> REGISTROS! <br/> " + e.getMessage().toString(),
                    "warning", null, "middle_center", 0);
        }
        return allAtracaderos;
    }

    public List<AtracaderosMd> RSelect() throws SQLException {
        List<AtracaderosMd> allAtracaderos = new ArrayList<AtracaderosMd>();
        
        String query = "SELECT TRIM(num_atracadero1), "
                + "TRIM(tipo_terminal), "
                + "TRIM(pk_inicial), "
                + "TRIM(pk_final),"
                + "TRIM (profundidad), "
                + "TRIM (intervalo), "
                + "TRIM (muelle), "
                + "TRIM (decode(estatus,'A','ACTIVO','B','INACTIVO')),"
                + "TRIM (decode(fuera_zona_abrigo,'S','SI','N','NO')) "
                + "FROM epqop.if_bq_atracaderos ORDER BY num_atracadero1 ASC";

        try {
            conexion = cnn.Conexion();
            st = conexion.createStatement();
            rs = st.executeQuery(query);
            AtracaderosMd rg;
            while (rs.next()) {
                rg = new AtracaderosMd();
                rg.setAtracadero(rs.getString(1));
                rg.setTerminal(rs.getString(2));
                rg.setPk_inicial(rs.getString(3));
                rg.setPk_final(rs.getString(4));
                rg.setProfundidad(rs.getString(5));
                rg.setIntervalo(rs.getString(6));
                rg.setMuelle(rs.getString(7));
                rg.setEstatus(rs.getString(8));
                rg.setFuera_abrigo(rs.getString(9));

                allAtracaderos.add(rg);
            }

            st.close();
            rs.close();
            conexion.close();
            conexion = null;
        } catch (SQLException e) {
            conexion.close();
            conexion = null;
            Clients.showNotification("ERROR AL CONSULTAR <br/> <br/> REGISTROS! <br/> " + e.getMessage().toString(),
                    "warning", null, "middle_center", 0);
        }
        return allAtracaderos;
    }

    public void REGinsert(String terminal, String pk_inicial, String pk_final, String profundidad, 
                          String intervalo, String muelle, String estatus, String fuera_abrigo) throws SQLException {
        
         String id = "";
        
        String query1 = " SELECT (max(num_atracadero1)-'"+global+"') as id FROM epqop.if_bq_atracaderos ";
        global--;
        String sql = "INSERT INTO epqop.if_bq_atracaderos "
                + "(num_atracadero1,tipo_terminal,"
                + "pk_inicial,pk_final,"
                + "profundidad,intervalo,"
                + "muelle,estatus,"
                + "fuera_zona_abrigo)"
                
                + " VALUES(?,?,?,?,?,?,?,?,?)";
        try {
            conexion = cnn.Conexion();
            conexion.setAutoCommit(false);
            st = conexion.createStatement();
             rs = st.executeQuery(query1);
            while (rs.next()) {
                id = rs.getString("id");
            }

            ps = conexion.prepareStatement(sql);

            ps.setString(1, id);
            ps.setString(2, terminal);
            ps.setString(3, pk_inicial);
            ps.setString(4, pk_final);
            ps.setString(5, profundidad);
            ps.setString(6, intervalo);
            ps.setString(7, muelle);
            ps.setString(8, estatus);
            ps.setString(9, fuera_abrigo);

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

    public void REGupdate(String atracadero, String terminal, String pk_inicial, String pk_final, String profundidad, String intervalo,
                          String muelle, String estatus, String fuera_abrigo) throws SQLException {
        try {
            conexion = cnn.Conexion();
            conexion.setAutoCommit(false);
            st = conexion.createStatement();

            st.executeUpdate("UPDATE epqop.if_bq_atracaderos SET "
                    + "tipo_terminal = '" + terminal + "',"
                    + "pk_inicial = '" + pk_inicial + "',"
                    + "pk_final = '" + pk_final + "',"
                    + "profundidad = '" + profundidad + "',"
                    + "intervalo = '" + intervalo + "',"
                    + "muelle = '" + muelle + "',"
                    + "estatus = '" + estatus + "',"
                    + "fuera_zona_abrigo = '" + fuera_abrigo + "'"
                    + "where num_atracadero1 = '" + atracadero + "' ");
            
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

    public void REGdelete(String num) throws SQLException {
        try {
            conexion = cnn.Conexion();
            conexion.setAutoCommit(false);
            System.out.println("ELIMINAR DATOS..!");
            System.out.println("Eliminar " + num);
            st = conexion.createStatement();

            st.executeUpdate("DELETE epqop.if_bq_atracaderos WHERE num_atracadero1= '" + num + "' ");
            Clients.showNotification("REGISTRO ELIMINADO <br/> CON EXITO  <br/>");
            System.out.println("Eliminacion Exitosa.! ");
            st.close();
            conexion.commit();
            conexion.close();
        } catch (SQLException e) {
            conexion.rollback();
            conexion.close();
            String mensaje = e.getMessage();
            Clients.showNotification("ERROR AL ELIMINAR <br/>'" + mensaje + "' <br/> REGISTROS! <br/> ",
                    "warning", null, "middle_center", 0);
            System.out.println("Eliminacion EXCEPTION.: " + mensaje);
        }

    }
}
