package DAL;

import Conexion.Conexion;
import MD.ManteServiciosMd;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.zk.ui.util.Clients;

/**
 *
 * @author HP 15
 */
public class ManteServiciosDal {

    Conexion obtener = new Conexion();
    Connection conn;
    ManteServiciosMd cl = new ManteServiciosMd();

    PreparedStatement ps = null;
    Statement st = null;
    ResultSet rs = null;

    public ManteServiciosMd MostrarProducto(String ano, String arribo) {
        Statement st = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String id = "";
        int resp = 0;
        cl = new ManteServiciosMd();
        String query0
                = "SELECT B.BUQUE CODIGO,UPPER(TRIM(B.NOM_BUQUE)) NOMBRE \n"
                + "FROM EPQOP.IF_BQ_BUQUES B,"
                + "     EPQOP.IF_BQ_ARRIBOS A\n"
                + "WHERE B.BUQUE = A.BUQUE \n"
                + "AND A.ANO_ARRIBO ='" + ano + "'\n"
                + "AND A.NUM_ARRIBO ='" + arribo + "'";
        try {
            conn = obtener.Conexion();

            st = conn.createStatement();
            rs = st.executeQuery(query0);
            while (rs.next()) {
                resp = 1;
                cl.setNumBuque(rs.getString(1));
                cl.setNomBuque(rs.getString(2));

                cl.setResp("1");
                cl.setMsg("ACTUALIZAR!");
            }
            st.close();
            rs.close();

            if (resp == 0) {

                cl.setResp("0");
                cl.setMsg(" NO EXISTE <br/> EL ARRIBO <br/> VALIDE INFORMACION");

            }
            conn.close();
            obtener.desconectar();

        } catch (Exception e) {
            System.out.println("ERROR CATCH.: " + e.getMessage());
            cl.setResp("0");
            cl.setMsg(e.getMessage());

        }

        return cl;
    }

    public List<ManteServiciosMd> Servicios() throws SQLException {
        List<ManteServiciosMd> allservicios = new ArrayList<ManteServiciosMd>();

        String query = "SELECT TRIM(codigo_servicio), TRIM(descripcion_servic)\n"
                + "FROM   epqop.if_ca_tarifas\n"
                + "WHERE  tipo_particular_2 = '1'\n"
                + "ORDER BY codigo_servicio ASC";

        try {
            conn = obtener.Conexion();
            st = conn.createStatement();
            rs = st.executeQuery(query);
            ManteServiciosMd rg;
            while (rs.next()) {
                rg = new ManteServiciosMd();

                rg.setCod_servicio(rs.getString(1));
                rg.setNom_servicio(rs.getString(2));

                allservicios.add(rg);
            }

            st.close();
            rs.close();
            conn.close();
            conn = null;
        } catch (SQLException e) {
            conn.close();
            conn = null;
            Clients.showNotification("ERROR AL CONSULTAR (Servicios) <br/> <br/> REGISTROS! <br/> " + e.getMessage().toString(),
                    "warning", null, "middle_center", 0);
        }
        return allservicios;
    }

    public List<ManteServiciosMd> Particulares() throws SQLException {
        List<ManteServiciosMd> allparticulares = new ArrayList<ManteServiciosMd>();

        String query = "SELECT codigo_particular, TRIM(nombre_particular)\n"
                + "FROM epqop.particulares \n"
                + "WHERE estatus = 1\n"
                + "ORDER BY codigo_particular ASC";

        try {
            conn = obtener.Conexion();
            st = conn.createStatement();
            rs = st.executeQuery(query);
            ManteServiciosMd rg;
            while (rs.next()) {
                rg = new ManteServiciosMd();

                rg.setCod_particular(rs.getString(1));
                rg.setNom_particular(rs.getString(2));

                allparticulares.add(rg);
            }

            st.close();
            rs.close();
            conn.close();
            conn = null;
        } catch (SQLException e) {
            conn.close();
            conn = null;
            Clients.showNotification("ERROR AL CONSULTAR (Particulares) <br/> <br/> REGISTROS! <br/> " + e.getMessage().toString(),
                    "warning", null, "middle_center", 0);
        }
        return allparticulares;
    }

    public List<ManteServiciosMd> Clientes() throws SQLException {
        List<ManteServiciosMd> allclientes = new ArrayList<ManteServiciosMd>();

        String query = "SELECT codigo_cliente, TRIM(nombre_comercial)\n"
                + "FROM   epqop.if_clientes \n"
                + "ORDER BY codigo_cliente ASC";
        try {
            conn = obtener.Conexion();
            st = conn.createStatement();
            rs = st.executeQuery(query);
            ManteServiciosMd rg;
            while (rs.next()) {
                rg = new ManteServiciosMd();

                rg.setCod_cliente(rs.getString(1));
                rg.setNom_cliente(rs.getString(2));

                allclientes.add(rg);
            }

            st.close();
            rs.close();
            conn.close();
            conn = null;
        } catch (SQLException e) {
            conn.close();
            conn = null;
            Clients.showNotification("ERROR AL CONSULTAR (Particulares) <br/> <br/> REGISTROS! <br/> " + e.getMessage().toString(),
                    "warning", null, "middle_center", 0);
        }
        return allclientes;
    }
}
