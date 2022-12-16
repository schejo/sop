package DAL;

import Conexion.Conexion;
import MD.ReporteServiciosMd;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.zk.ui.util.Clients;

public class ReporteServiciosDal {

    public List<ReporteServiciosMd> GetByFecha(String fechaInicio, String fechaFin) throws SQLException {
        List<ReporteServiciosMd> respuesta = new ArrayList<ReporteServiciosMd>();
        PreparedStatement smt = null;
        Connection conn = null;
        Conexion conx = new Conexion();
        ResultSet rs = null;
        conn = conx.Conexion();
        try {

                String sql ="SELECT ANO_ARRIBO,  \n" +
                        "  NUM_ARRIBO,  \n" +
                        "  CORRELATIVO,  \n" +
                        "  TIPO_TARIFA,  \n" +
                        "  FECHA_VIGENCIA,  \n" +
                        "  CODIGO_PARTICULAR,  \n" +
                        "  CANTIDAD1,  \n" +
                        "  CANTIDAD2,  \n" +
                        "to_char (fecha_inicio1,'DD/MM/RRRR') || to_char(hora_inicio1,'HH24:MI:SS') as FECHA_INICIO,"+
                        "to_char (fecha_fin1,'DD/MM/RRRR') || to_char(hora_fin2,'HH24:MI:SS')as FECHA_FINAL,"+
                        "  OBSE_SERVICIO,  \n" +
                        "  CODIGO_SERVICIO,  \n" +
                        "  NUMERO_FACTURA\n" +
                        "FROM  EPQOP.IF_BQ_SERVICIOS\n" +
                        "WHERE EPQOP.IF_BQ_SERVICIOS.FECHA_INICIO1 >= TO_DATE('"+fechaInicio+"','DD/MM/YYYY')\n"+
                        "AND   EPQOP.IF_BQ_SERVICIOS.FECHA_INICIO1 <= TO_DATE('"+fechaFin+"','DD/MM/YYYY')\n"+
                        "ORDER BY  ANO_ARRIBO,NUM_ARRIBO ";
                    
                    
            smt = conn.prepareStatement(sql);
            rs = smt.executeQuery();
            while (rs.next()) {
                ReporteServiciosMd rp = new ReporteServiciosMd();
                rp.setAno_arribo(rs.getString(1));
                rp.setNum_arribo(rs.getString(2));
                rp.setCorrelativo(rs.getString(3));
                rp.setTipo_tarifa(rs.getString(4));
                rp.setFecha_vigencia(rs.getString(5));
                rp.setCodigo_particular(rs.getString(6));
                rp.setCantidad1(rs.getString(7));
                rp.setCantidad2(rs.getString(8));
                rp.setFecha_inicio(rs.getString(9));
                rp.setFecha_final(rs.getString(10));
                rp.setObse_servicio(rs.getString(11));
                rp.setCodigo_servicio(rs.getString(12));
                rp.setNumero_factura(rs.getString(13));
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
