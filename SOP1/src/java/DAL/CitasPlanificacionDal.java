package DAL;

import Conexion.Conexion;
import MD.CitasPlanificacionMd;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.zk.ui.util.Clients;

public class CitasPlanificacionDal {

    private Connection conn = null;
    private Conexion obtener = new Conexion();
    PreparedStatement ps = null;
    Statement st = null;
    ResultSet rs = null;
    CitasPlanificacionMd cl = new CitasPlanificacionMd();

    public List<CitasPlanificacionMd> RSelect() throws SQLException, ClassNotFoundException {
        Statement st = null;
        ResultSet rs = null;//copie de usuarios
        List<CitasPlanificacionMd> allProductos = new ArrayList<CitasPlanificacionMd>();

        String query = "SELECT  num_arribo FROM"
                + " epqop.if_bq_citas_plani ORDER BY  num_arribo ASC ";

        try {
            conn = obtener.Conexion();
            st = conn.createStatement();
            rs = st.executeQuery(query);
            CitasPlanificacionMd rg;
            while (rs.next()) {
                rg = new CitasPlanificacionMd();
                rg.setNum_arribo(rs.getString(1));

                allProductos.add(rg);
            }

            st.close();
            rs.close();
            conn.close();
            conn = null;
        } catch (SQLException e) {
            conn.close();
            conn = null;
            Clients.showNotification("ERROR AL CONSULTAR (Rselect) <br/> <br/> REGISTROS! <br/> " + e.getMessage().toString(),
                    "warning", null, "middle_center", 0);
        }
        return allProductos;
    }

    public CitasPlanificacionMd savePro(CitasPlanificacionMd data) throws ClassNotFoundException, SQLException {
        Statement st = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String id = "";
        int resp = 0;
        cl = new CitasPlanificacionMd();
//        String query1 = " SELECT max(pro_id)+1 as id FROM productos ";
        String sql = "INSERT INTO epqop.if_bq_citas_plani \n"
                + "         VALUES  (?,?, TO_date(? , 'dd/mm/yyyy hh24:mi'),?,?,TO_date(? ,'dd/mm/yyyy hh24:mi'),null,SYSDATE,?,TO_date(? ,'dd/mm/yyyy hh24:mi'))";

        try {
            conn = obtener.Conexion();
            conn.setAutoCommit(false);

            st = conn.createStatement();
            ps = conn.prepareStatement(sql);

            ps.setString(1, data.getAnio_arribo());
            ps.setString(2, data.getNum_arribo());
            ps.setString(3, data.getFecha_hora());
            ps.setString(4, data.getLugar());
            ps.setString(5, data.getObser());
            ps.setString(6, data.getEta());
            //  ps.setString(6, data.getEstado());
            ps.setString(7, data.getUsuario_alta());
            ps.setString(8, data.getEtap());

            ps.executeUpdate();
            ps.close();
            conn.commit();
//            cl.setCodigo(id);
            cl.setResp("1");
            cl.setMsg("REGISTRO GUARDADO CORRECTAMENTE");

            conn.close();
            obtener.desconectar();

        } catch (SQLException e) {
            System.out.println("EXCEPTION..: " + e.getMessage());
            cl.setResp("0");
            cl.setMsg(e.getMessage());
        }

        return cl;
    }

    public CitasPlanificacionMd MostrarNa_Eta(String anio, String numero) {
        Statement st = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String id = "";
        int resp = 0;
        cl = new CitasPlanificacionMd();

        String query0 = "SELECT "
                + "      TO_CHAR(a.fecha_atraque,'DD/MM/RRRR')||' '||TO_CHAR(a.hora_atraque,'HH24:MI:SS') ETA,\n"
                + "            b.nom_buque, d.nom_naviera\n"
                + "     FROM     epqop.if_bq_arribos       a,\n"
                + "              epqop.if_bq_buques        b,\n"
                + "              epqop.if_bq_lineas_arrib  c,\n"
                + "              epqop.if_bq_naviera       d\n"
                + "WHERE a.buque = b.buque\n"
                + "AND    a.ano_arribo = c.ano_arribo\n"
                + "AND    a.num_arribo = c.num_arribo\n"
                + "AND    c.naviera1     =  d.naviera1\n"
                + "AND    a.ano_arribo = " + anio + "\n"
                + "AND    a.num_arribo = " + numero + " ";

        try {
            conn = obtener.Conexion();

            st = conn.createStatement();
            rs = st.executeQuery(query0);
            while (rs.next()) {
                resp = 1;
                cl.setNomNaviera(rs.getString(3));
                cl.setEta(rs.getString(1));
                cl.setNomBuque(rs.getString(2));

                // cl.setMsg("ACTUALIZAR DATOS DE CATEDRATICO.!");
            }
            st.close();
            rs.close();

            if (resp == 0) {

                cl.setResp("0");
                cl.setMsg("AÑO ARRIBO O NUMERO DE ARRIBO  <br/>  <br/> NO EXISTE ");

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

    public CitasPlanificacionMd MostrarProducto(String anio, String numero) {
        Statement st = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String id = "";
        int resp = 0;
        cl = new CitasPlanificacionMd();

        String query0 = "SELECT"
                + "         ano_arribo,num_arribo,TO_CHAR(fecha_hora,'dd/mm/yyyy hh24:mi'),"
                + "        (DECODE(lugar,'V','VIRTUAL','P','PRESENCIAL')),observaciones,eta_cita,\n"
                + "        TO_CHAR(eta_plani,'DD/MM/RRRR')||' '||TO_CHAR(eta_plani,'HH24:MI:SS') ETA_PLANI,\n"
                + "        estado,to_char(fecha_alta,'dd/mm/yyyy hh24:mi'),usuario_alta\n"
                + "FROM    epqop.if_bq_citas_plani\n"
                + "WHERE   ano_arribo =  " + anio + "\n"
                + "AND     num_arribo =  " + numero + " ";

        try {
            conn = obtener.Conexion();

            st = conn.createStatement();
            rs = st.executeQuery(query0);
            while (rs.next()) {
                resp = 1;
                cl.setAnio_arribo(rs.getString(1));
                cl.setNum_arribo(rs.getString(2));
                cl.setFecha_hora(rs.getString(3));
                cl.setLugar(rs.getString(4));
                cl.setObser(rs.getString(5));
                cl.setEtaCita(rs.getString(6));
                cl.setEtap(rs.getString(7));
                cl.setEstado(rs.getString(8));
                cl.setFecha_alta(rs.getString(9));
                cl.setUsuario_alta(rs.getString(10));
                cl.setResp("1");
                cl.setMsg("ACTUALIZAR DATOS DE CATEDRATICO.!");
            }
            st.close();
            rs.close();

            if (resp == 0) {

                cl.setResp("0");
                cl.setMsg("AÑO ARRIBO O NUMERO DE ARRIBO  <br/>  <br/> NO EXISTE ");

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

    public CitasPlanificacionMd updatePro(CitasPlanificacionMd data) {
        Statement st = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String id = "";
        int resp = 0;
        cl = new CitasPlanificacionMd();
        try {
            conn = obtener.Conexion();
            conn.setAutoCommit(false);
            int vl = 0;
            st = conn.createStatement();

            vl = st.executeUpdate("UPDATE  epqop.if_bq_citas_plani\n"
                    + "SET     fecha_hora = to_date('" + data.getFecha_hora() + "',"
                    + "'dd/mm/yyyy hh24:mi'),lugar = '" + data.getLugar() + "'\n"
                    + ",observaciones = '" + data.getObser() + "'\n"
                    + ",eta_plani = to_date('" + data.getEtap() + "','dd/mm/yyyy hh24:mi')\n"
                    + "WHERE   ano_arribo = " + data.getAnio_arribo() + ""
                    + "AND     num_arribo = " + data.getNum_arribo() + "");

            if (vl > 0) {
                cl.setResp("1");
                cl.setMsg("DATOS ACTUALIZADOS CORRECTAMENTE");
                System.out.println("Actualizacion Exitosa");
            } else {
                cl.setResp("0");
                cl.setMsg("DATOS NO ACTUALIZADOS");
                System.out.println("Actualizacion Fallida");
            }
            st.close();

            conn.commit();
            conn.close();
            obtener.desconectar();

        } catch (Exception e) {
            System.out.println("dato fecha " + data.getFecha_hora());

            System.out.println("ERROR CATCH.: " + e.getMessage());
            cl.setResp("0");
            cl.setMsg(e.getMessage());

        }

        return cl;
    }

}
