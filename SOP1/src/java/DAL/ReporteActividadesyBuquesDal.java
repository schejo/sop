package DAL;

import Conexion.Conexion;
import MD.ReporteActividadesyBuquesMd;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.zk.ui.util.Clients;

public class ReporteActividadesyBuquesDal {

    public List<ReporteActividadesyBuquesMd> GetByFecha(String fechaInicio, String fechaFin) throws SQLException {
        List<ReporteActividadesyBuquesMd> respuesta = new ArrayList<ReporteActividadesyBuquesMd>();
        PreparedStatement smt = null;
        Connection conn = null;
        Conexion conx = new Conexion();
        ResultSet rs = null;
        conn = conx.Conexion();
        try {

                String sql ="SELECT EPQOP.IF_BQ_REG_ACTIVIDA.NUM_ACTIVIDAD1,  \n" +
                            "         EPQOP.IF_BQ_REG_ACTIVIDA.FECHA_ACT,  \n" +
                            "         EPQOP.IF_BQ_ACTIV_BUQUE.NOMBRE_DURACION,  \n" +
                            "         EPQOP.IF_BQ_REG_ACTIVIDA.DURACION,  \n" +
                            "         EPQOP.IF_BQ_REG_ACTIVIDA.CALADO_PROA,  \n" +
                            "         EPQOP.IF_BQ_REG_ACTIVIDA.CALADO_POPA,  \n" +
                            "         EPQOP.IF_BQ_REG_ACTIVIDA.CALADO_MEDIO,  \n" +
                            "         EPQOP.IF_BQ_REG_ACTIVIDA.NUM_ATRACADERO3,  \n" +
                            "         EPQOP.IF_BQ_REG_ACTIVIDA.PRACTICO,  \n" +
                            "         EPQOP.IF_BQ_REG_ACTIVIDA.REMOLCADOR,  \n" +
                            "         EPQOP.IF_BQ_REG_ACTIVIDA.REMOLCADOR2,  \n" +
                            "         EPQOP.IF_BQ_REG_ACTIVIDA.OBSE_ACTIVIDAD1,\n" +
                            "         EPQOP.IF_BQ_REG_ACTIVIDA.CODIGO_FONDEOS,\n" +
                            "         EPQOP.IF_BQ_REG_ACTIVIDA.REMOLCADOR3,\n" +
                            "         EPQOP.IF_BQ_REG_ACTIVIDA.LANCHA_PILOTO,\n" +
                            "         EPQOP.IF_BQ_REG_ACTIVIDA.LANCHA_ALMIRANTE,\n" +
                       " ( SELECT NOMBRE_PARTICULAR FROM EPQOP.PARTICULARES WHERE EPQOP.PARTICULARES.CODIGO_PARTICULAR = EPQOP.IF_BQ_REG_ACTIVIDA.PRACTICO ) NOMBRE_PRACTICO,\n" +
                       " ( SELECT NOMBRE_PARTICULAR FROM EPQOP.PARTICULARES WHERE EPQOP.PARTICULARES.CODIGO_PARTICULAR = EPQOP.IF_BQ_REG_ACTIVIDA.REMOLCADOR ) NOMBRE_REMOLCADOR,\n" +
                       " ( SELECT NOMBRE_PARTICULAR FROM EPQOP.PARTICULARES WHERE EPQOP.PARTICULARES.CODIGO_PARTICULAR = EPQOP.IF_BQ_REG_ACTIVIDA.REMOLCADOR2 ) NOMBRE_REMOLCADOR2,\n" +
                       " ( SELECT NOMBRE_PARTICULAR FROM EPQOP.PARTICULARES WHERE EPQOP.PARTICULARES.CODIGO_PARTICULAR = EPQOP.IF_BQ_REG_ACTIVIDA.REMOLCADOR3 ) NOMBRE_REMOLCADOR3,\n" +
                       " ( SELECT NOMBRE_PARTICULAR FROM EPQOP.PARTICULARES WHERE EPQOP.PARTICULARES.CODIGO_PARTICULAR = EPQOP.IF_BQ_REG_ACTIVIDA.LANCHA_PILOTO) LANCHA_PILOTO\n" +
                       "    FROM EPQOP.IF_BQ_REG_ACTIVIDA,  \n" +
                       "         EPQOP.IF_BQ_ACTIV_BUQUE  \n" +
                       "WHERE ( EPQOP.IF_BQ_REG_ACTIVIDA.NUM_ACTIVIDAD1 = EPQOP.IF_BQ_ACTIV_BUQUE.NUM_ACTIVIDAD1 )\n" +
                       "AND  EPQOP.IF_BQ_REG_ACTIVIDA.FECHA_ACT >= TO_DATE('"+fechaInicio+"','DD/MM/YYYY')\n"+
                       "AND  EPQOP.IF_BQ_REG_ACTIVIDA.FECHA_ACT <= TO_DATE('"+fechaFin+"','DD/MM/YYYY')\n"+
                       "ORDER BY EPQOP.IF_BQ_REG_ACTIVIDA.FECHA_ACT";

                    
                    
            smt = conn.prepareStatement(sql);
            rs = smt.executeQuery();
            while (rs.next()) {
                ReporteActividadesyBuquesMd rp = new ReporteActividadesyBuquesMd();
                rp.setNum_actividad1(rs.getString(1));
                rp.setFecha_act(rs.getString(2));
                rp.setNombre_duracion(rs.getString(3));
                rp.setDuracion(rs.getString(4));
                rp.setCalado_proa(rs.getString(5));
                rp.setCalado_popa(rs.getString(6));
                rp.setCalado_medio(rs.getString(7));
                rp.setNum_atracadero3(rs.getString(8));
                rp.setPractico(rs.getString(9));
                rp.setRemolcador(rs.getString(10));
                rp.setRemolcador2(rs.getString(11));
                rp.setObse_actividad1(rs.getString(12));
                rp.setCodigo_fondeados(rs.getString(13));
                rp.setRemolcador3(rs.getString(14));
                rp.setLancha_piloto(rs.getString(15));
                rp.setLancha_almirante(rs.getString(16));
                rp.setNombre_practico(rs.getString(17));
                rp.setNombre_remolcador(rs.getString(18));
                rp.setNombre_remolcador2(rs.getString(19));
                rp.setNombre_remolcador3(rs.getString(20));
                rp.setNombre_lancha_piloto(rs.getString(21));
                respuesta.add(rp);
            }

        } catch (Exception e) {
            Clients.showNotification(e.getMessage());
        } finally {
            if (smt != null) {
                smt.close();
                smt = null;
            }
            if (rs != null) {
                rs.close();
                rs = null;
            }
            if (conn != null) {
                conn = conx.desconectar();
            }
        }
        //System.out.println("Datos.: " + respuesta);
        return respuesta;
    }

}

