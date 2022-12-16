package DAL;

import Conexion.Conexion;
import MD.PilotosMd;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.zk.ui.util.Clients;

public class PilotosDal {

    private Connection conexion = null;
    private Conexion cnn = new Conexion();
    PreparedStatement ps = null;
    Statement st = null;
    ResultSet rs = null;

    public List<PilotosMd> REGselect(String pais, String licencia) throws SQLException {
        List<PilotosMd> allPilotos = new ArrayList<PilotosMd>();

        String query = "SELECT"
                + " NVL(cod_pais_piloto, ''), "
                + " NVL(num_licen_piloto, ''), "
                + " NVL (nombre_piloto, ''), "
                + " TO_CHAR(fecha_licencia,'DD/MM/YYYY'),"
                + " NVL(dpi, ''), "
                + " NVL(pasaporte, ''), "
                + " TRIM (decode(antecedentes_poli,'S','SI','N','NO')), "
                + " TO_CHAR(vigencia_poli,'DD/MM/YYYY'), "
                + " TRIM (decode(antecedentes_pena,'S','SI','N','NO')), "
                + " TO_CHAR(vigencia_pena,'DD/MM/YYYY') "
                + " FROM epqop.if_cm_pilotos"
                + " WHERE trim(cod_pais_piloto) = '" + pais + "'\n"
                + " AND  trim(num_licen_piloto) = '" + licencia + "'\n";

        System.out.println("REGselect ... :" + query);

        try {
            conexion = cnn.Conexion();
            st = conexion.createStatement();
            rs = st.executeQuery(query);
            PilotosMd rg = new PilotosMd();
            System.out.println("entro al try ... :" + query);
            while (rs.next()) {
                rg.setPais(rs.getString(1));
                rg.setLicencia(rs.getString(2));
                rg.setNombre(rs.getString(3));
                rg.setVigencia(rs.getString(4));
                rg.setDpi(rs.getString(5));
                rg.setPasaporte(rs.getString(6));
                rg.setAntecedente_poli(rs.getString(7));
                rg.setVigencia_poli(rs.getString(8));
                rg.setAntecedente_pena(rs.getString(9));
                rg.setVigencia_pena(rs.getString(10));
                allPilotos.add(rg);
                System.out.println("entro al while  Pais ... :" + rs.getString(1));
                System.out.println("entro al while  Licencia ... :" + rs.getString(2));
                System.out.println("entro al while  Nombre ... :" + rs.getString(3));
            }
            rs.close();
            st.close();
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
        return allPilotos;
    }

    public List<PilotosMd> RSelect() throws SQLException {
        List<PilotosMd> allPilotos = new ArrayList<PilotosMd>();

        String query = "SELECT "
                + "TRIM(cod_pais_piloto), "
                + "TRIM(num_licen_piloto), "
                + "TRIM(nombre_piloto), "
                + " TO_DATE(fecha_licencia,'DD/MM/YYYY'),"
                + "TRIM (dpi), "
                + "TRIM (pasaporte), "
                + "TRIM (decode(antecedentes_poli,'S','SI','N','NO')), "
                + "TO_CHAR(vigencia_poli,'DD/MM/YYYY'),"
                + "TRIM (DECODE(antecedentes_pena,'S','SI','N','NO')), "
                + "TO_CHAR(vigencia_pena,'DD/MM/YYYY') "
                + "FROM epqop.if_cm_pilotos "
                + "ORDER BY cod_pais_piloto, num_licen_piloto asc ";
        try {
            conexion = cnn.Conexion();
            st = conexion.createStatement();
            rs = st.executeQuery(query);
            PilotosMd rg;
            while (rs.next()) {
                rg = new PilotosMd();

                rg.setPais(rs.getString(1));
                rg.setLicencia(rs.getString(2));
                rg.setNombre(rs.getString(3));
                rg.setVigencia(rs.getString(4));
                rg.setDpi(rs.getString(5));
                rg.setPasaporte(rs.getString(6));
                rg.setAntecedente_poli(rs.getString(5));
                rg.setVigencia_poli(rs.getString(6));
                rg.setAntecedente_pena(rs.getString(7));
                rg.setVigencia_pena(rs.getString(8));
                // rg.setFecha_inicio(rs.getString(11));
                // rg.setFecha_fin(rs.getString(12));

                allPilotos.add(rg);
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
        return allPilotos;
    }

    public void REGinsert(String pais, String licencia, String nombre, String vigencia,
            String antecedente_poli, String vigencia_poli, String antecedente_pena,
            String vigencia_pena) throws SQLException {

        String sql = "INSERT INTO "
                + "epqop.if_cm_pilotos "
                + "(cod_pais_piloto, "
                + "num_licen_piloto, "
                + "nombre_piloto, "
                + "fecha_licencia,"
                + "dpi, "
                + "pasaporte, "
                + "antecedentes_poli, "
                + "vigencia_poli, "
                + "antecedentes_pena, "
                + "vigencia_pena) "
                + "VALUES(?,?,?,TO_DATE(?,'DD/MM/YYYY'),?,?,?,TO_DATE(?,'DD/MM/YYYY'),?,TO_DATE(?,'DD/MM/YYYY'))";//,?,?
        try {
            conexion = cnn.Conexion();
            conexion.setAutoCommit(false);
            st = conexion.createStatement();

            ps = conexion.prepareStatement(sql);

            ps.setString(1, pais);
            ps.setString(2, licencia);
            ps.setString(3, nombre);
            ps.setString(4, vigencia);
            ps.setString(5, licencia);
            ps.setString(6, licencia);
            ps.setString(7, antecedente_poli);
            ps.setString(8, vigencia_poli);
            ps.setString(9, antecedente_pena);
            ps.setString(10, vigencia_pena);
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

    public void REGupdate(String pais, String licencia, String nombre, String vigencia,
            String antecedente_poli, String vigencia_poli, String antecedente_pena,
            String vigencia_pena) throws SQLException {
        try {
            conexion = cnn.Conexion();
            conexion.setAutoCommit(false);
            st = conexion.createStatement();

            st.executeUpdate("UPDATE epqop.if_cm_pilotos SET "
                    + " fecha_licencia = TO_DATE('" + vigencia + "','DD/MM/YYYY'),"
                    + " antecedentes_poli = '" + antecedente_poli + "',"
                    + " vigencia_poli = TO_DATE('" + vigencia_poli + "','DD/MM/YYYY'),"
                    + " antecedentes_pena = '" + antecedente_pena + "', "
                    + " vigencia_pena = TO_DATE('" + vigencia_pena + "','DD/MM/YYYY')"
                    + " WHERE cod_pais_piloto = '" + pais + "' "
                    + " AND   num_licen_piloto = '" + licencia + "' ");

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
            //System.out.println("Actualizacion EXCEPTION.: " + mensaje);
        }
    }

}
