package DAL;

import Conexion.Conexion;
import MD.CamionParqMd;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Informatica
 */
public class CamionParqDal {

    private Connection conn = null;
    private Conexion obtener = new Conexion();
    PreparedStatement ps = null;
    Statement st = null;
    ResultSet rs = null;
    CamionParqMd cl = new CamionParqMd();

    public CamionParqMd MostrarProducto(String placa) {
        Statement st = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int resp = 0;
        cl = new CamionParqMd();

        String query0 = "SELECT codigo_pais1,cod_pais_piloto,\n"
                + "       num_licen_piloto,tipo_operacion2,TO_CHAR(fch_hora_ing_parq,'DD/MM/YYYY HH24:mi'),\n"
                + "       naviera3,obse_camio1,codigo_destino,\n"
                + "       TO_CHAR(fecha_inicial, 'DD/MM/YYYY HH24:mi'),TO_CHAR(fecha_final,'DD/MM/YYYY HH24:mi'),"
                + "       ubica_camion,numero_contenedor\n"
                + "FROM epqop.if_cm_camion_parq\n"
                + "WHERE num_placa_camion ='" + placa + "'";
        try {
            conn = obtener.Conexion();

            st = conn.createStatement();
            rs = st.executeQuery(query0);
            while (rs.next()) {
                resp = 1;
                cl.setCod_pais(rs.getString(1));
                cl.setPais_piloto(rs.getString(2));
                cl.setLicencia(rs.getString(3));
                cl.setTipo_opera(rs.getString(4));
                cl.setFecha_ing_parqueo(rs.getString(5));
                cl.setNaviera(rs.getString(6));
                cl.setObservaciones(rs.getString(7));
                cl.setCod_destino(rs.getString(8));
                cl.setFecha_inicio(rs.getString(9));
                cl.setFecha_fin(rs.getString(10));
                cl.setUbic_camion(rs.getString(11));
                cl.setNum_contene(rs.getString(12));

                cl.setResp("1");

                cl.setMsg("Actualizar Datos de el camion con la placa.!");
            }
            st.close();
            rs.close();

            if (resp == 0) {

                cl.setResp("0");
                cl.setMsg("NUMERO DE PLACA <br/>  <br/> NO EXISTE ");

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

    public CamionParqMd updatePlaca(CamionParqMd data) throws SQLException {

        PreparedStatement ps = null;
        ResultSet rs = null;
        String id = "";
        int resp = 0;
        cl = new CamionParqMd();
        try {
            conn = obtener.Conexion();
            conn.setAutoCommit(false);
            int vl = 0;
            st = conn.createStatement();

            vl = st.executeUpdate("UPDATE epqop.if_cm_camion_parq SET\n"
                    + "                      codigo_pais1 = '" + data.getCod_pais() + "'\n"
                    + "                     ,cod_pais_piloto = '" + data.getCod_pais() + "'\n"
                    + "                     ,num_licen_piloto = '" + data.getLicencia() + "'\n"
                    + "                     ,tipo_operacion2 = '" + data.getTipo_opera() + "'\n"
                    + "                     ,fch_hora_ing_parq = TO_CHAR('" + data.getFecha_ing_parqueo() + "', 'DD/MM/YYYY, HH24:MI')\n"
                    + "                     ,naviera3 = '" + data.getNaviera() + "'\n"
                    + "                     ,obse_camio1 = '" + data.getObservaciones() + "'\n"
                    + "                     ,codigo_destino = '" + data.getCod_destino() + "'\n"
                    + "                     ,fecha_inicial = TO_CHAR('" + data.getFecha_inicio() + "', 'DD/MM/YYYY, HH24:MI')\n"
                    + "                     ,fecha_final = TO_CHAR('" + data.getFecha_fin() + "', 'DD/MM/YYYY, HH24:MI')\n"
                    + "                     ,ubica_camion = '" + data.getUbic_camion() + "'\n"
                    + "                     ,numero_contenedor = '" + data.getNum_contene() + "'\n"
                    + "                WHERE num_placa_camion = '" + data.getPlaca() + "'");

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
            //obtener.desconectar();

        } catch (SQLException e) {
            System.out.println("ERROR CATCH.: " + e.getMessage());
            conn.rollback();
            conn.close();
            cl.setResp("0");
            cl.setMsg(e.getMessage());

        }

        return cl;
    }

}
