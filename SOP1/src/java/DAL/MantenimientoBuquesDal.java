package DAL;

import Conexion.Conexion;
import MD.MantenimientoBuquesMd;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MantenimientoBuquesDal {

    private Connection conn = null;
    private Conexion obtener = new Conexion();
    PreparedStatement ps = null;
    Statement st = null;
    ResultSet rs = null;
    MantenimientoBuquesMd cl = new MantenimientoBuquesMd();

    public MantenimientoBuquesMd MostrarProducto(String buque) {
        Statement st = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String id = "";
        int resp = 0;
        cl = new MantenimientoBuquesMd();

        String query0 = " SELECT a.buque,a.imo,a.nom_buque,\n"
                + "       b.tipo_buque,b.nom_tipo_buque,\n"
                + "       c.pais,nombre_pais,\n"
                + "       a.ano_construccion,TO_CHAR(a.ultimo_arribo,'DD/MM/YYYY'),\n"
                + "       a.buq_trb,a.trn,a.peso_muerto,buq_eslora,\n"
                + "       a.manga,a.calado_maximo,a.cant_bodegas,\n"
                + "       a.cant_gruas,a.cant_plumas,a.capacidad_gruas,\n"
                + "       a.obse_buques,a.lr_buque,a.call_sign,a.usuario\n"
                + "FROM  epqop.if_bq_buques      a,\n"
                + "      epqop.if_bq_tipo_arribo b,\n"
                + "      epqop.if_bq_paises      c \n"
                + "WHERE a.tipo_buque = b.tipo_buque\n"
                + "AND   a.bandera   = c.pais\n"
                + "AND   a.buque = " + buque + " ";

        try {
            conn = obtener.Conexion();

            st = conn.createStatement();
            rs = st.executeQuery(query0);
            while (rs.next()) {
                resp = 1;
                cl.setImo_buque(rs.getString(2));
                cl.setNom_buque(rs.getString(3));
                cl.setCod_buque(rs.getString(4));
                cl.setTipo_buque(rs.getString(5));
                cl.setCod_bandera(rs.getString(6));
                cl.setNom_bandera(rs.getString(7));
                cl.setAnio_construccion(rs.getString(8));
                cl.setUltimo_arribo(rs.getString(9));
                cl.setTrb_buque(rs.getString(10));
                cl.setTrn_buque(rs.getString(11));
                cl.setPeso_muerto_buque(rs.getString(12));
                cl.setEslora_buque(rs.getString(13));
                cl.setManga_buque(rs.getString(14));
                cl.setCalado_maximo(rs.getString(15));
                cl.setCant_bodegas(rs.getString(16));
                cl.setCant_gruas(rs.getString(17));
                cl.setCant_plumas(rs.getString(18));
                cl.setCapacidad_gruas(rs.getString(19));
                cl.setObservaciones_buque(rs.getString(20));
                cl.setLr_buque(rs.getString(21));
                cl.setCall_sign_buque(rs.getString(22));
                cl.setUltima_actualizacion(rs.getString(23));
                cl.setResp("1");

                cl.setMsg("Actualizar Datos de Buque Activado.!");
            }
            st.close();
            rs.close();

            if (resp == 0) {

                cl.setResp("0");
                cl.setMsg("NUMERO DE BUQUE <br/>  <br/> NO EXISTE ");

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

    public MantenimientoBuquesMd updateBuque(MantenimientoBuquesMd data) throws SQLException {

        PreparedStatement ps = null;
        ResultSet rs = null;
        String id = "";
        int resp = 0;
        cl = new MantenimientoBuquesMd();
        try {
            conn = obtener.Conexion();
            conn.setAutoCommit(false);
            int vl = 0;
            st = conn.createStatement();

            vl = st.executeUpdate("UPDATE EPQOP.IF_BQ_BUQUES SET\n"
                    + "                      NOM_BUQUE = '" + data.getNom_buque() + "'\n"
                    + "                     ,ANO_CONSTRUCCION = '" + data.getAnio_construccion() + "'\n"
                    + "                     ,CANT_BODEGAS = '" + data.getCant_bodegas() + "'\n"
                    + "                     ,CANT_GRUAS = '" + data.getCant_gruas() + "'\n"
                    + "                     ,CANT_PLUMAS ='" + data.getCant_plumas() + "'\n"
                    + "                     ,CAPACIDAD_GRUAS = '" + data.getCapacidad_gruas() + "'\n"
                    + "                     ,OBSE_BUQUES = '" + data.getObservaciones_buque() + "'\n"
                    + "                     ,USUARIO = '" + data.getUltima_actualizacion() + "'\n"
                    + "                      WHERE BUQUE = '" + data.getBuque() + "'");

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
