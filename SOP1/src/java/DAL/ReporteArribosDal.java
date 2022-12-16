package DAL;

import Conexion.Conexion;
import MD.ReporteArribosMd;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.zk.ui.util.Clients;

public class ReporteArribosDal {

    public List<ReporteArribosMd> GetByFecha(String fechaInicio, String fechaFin) throws SQLException {
        List<ReporteArribosMd> respuesta = new ArrayList<ReporteArribosMd>();
        PreparedStatement smt = null;
        Connection conn = null;
        Conexion conx = new Conexion();
        ResultSet rs = null;
        conn = conx.Conexion();
        try {

                String sql ="select a.ano_arribo,a.num_arribo,a.numero_viaje,b.nom_buque,"
                        + " to_char (a.fecha_atraque,'DD/MM/RRRR')|| to_char(a.hora_atraque,'HH24:MI:SS')as fecha_atraque,"
                        + " to_char (a.fecha_zarpe,'DD/MM/RRRR')|| to_char(a.hora_zarpe,'HH24:MI:SS')as fecha_zarpe,"
                        + " a.datos_importacion,a.datos_exportacion,"
                        + " b.calado_maximo,b.buq_trb,"
                        + " c.tipo_terminal,"
                        + " d.nom_tipo_buque"
                        + " from epqop.if_bq_arribos a,"
                        + " epqop.if_bq_buques b,"
                        + " epqop.if_bq_atracaderos c,"
                        + " epqop.if_bq_tipo_arribo d"
                        + " where   a.buque = b.buque"
                       + " and     a.num_atracadero1 = c.num_atracadero1"
                       + " and     a.tipo_buque = d.tipo_buque"
                       + " and   a.fecha_atraque >= TO_DATE('"+fechaInicio+"','DD/MM/YYYY')\n"
                       + " and   a.fecha_atraque <= TO_DATE('"+fechaFin+"','DD/MM/YYYY')\n"
                       + " order by a.ano_arribo, a.num_arribo"; 
                       
                    
                    
            smt = conn.prepareStatement(sql);
            rs = smt.executeQuery();
            while (rs.next()) {
                ReporteArribosMd rp = new ReporteArribosMd();
                rp.setAnoarribo(rs.getString(1));
                rp.setNumarribo(rs.getString(2));
                rp.setNumeroviaje(rs.getString(3));
                rp.setNombrebuque(rs.getString(4));
                rp.setFechaatraque(rs.getString(5));
                rp.setFechazarpe(rs.getString(6));
                rp.setDatosimportacion(rs.getString(7));
                rp.setDatosexportacion(rs.getString(8));
                rp.setCaladomaximo(rs.getString(9));
                rp.setTrbbuque(rs.getString(10));
                rp.setTipoterminal(rs.getString(11));
                rp.setNombretipobuque(rs.getString(12));
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
