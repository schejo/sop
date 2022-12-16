package DAL;

import Conexion.Conexion;
import MD.ReporteManifiestosMd;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.zk.ui.util.Clients;

public class ReporteManifiestosDal {

    public List<ReporteManifiestosMd> GetByFecha(String fechaInicio, String fechaFin) throws SQLException {
        List<ReporteManifiestosMd> respuesta = new ArrayList<ReporteManifiestosMd>();
        PreparedStatement smt = null;
        Connection conn = null;
        Conexion conx = new Conexion();
        ResultSet rs = null;
        conn = conx.Conexion();
        try {

                String sql =" SELECT ANO_MANIFIESTO,"+ 
                             " MANIFIESTO," + 
                             " LINEA1," +
                             " PAIS,"+ 
                             " ANO_ARRIBO,"+  
                             " NUM_ARRIBO,"+ 
                             " NAVIERA1,"+
                             " FECHA_MANIFIESTO,"+  
                             " DESCRIPCION_MANIF,"+  
                             " FECHA_TRANSMISION,"+ 
                             " NUM_REF_TRANSMI,"+
                             " NOMBRE_BUQUE,"+
                             " REF_INTER_NAVIERA,"+  
                             " DECODE(MODO_RECEPCION,'M','MANUAL','E','ELECTRONICO') as RECEPCION,"+
                             " NUMERO_VIAJE,"+
                             " ENTIEMPO"+
                             " FROM EPQOP.IF_MA_MANIFIESTOS" + 
                             " WHERE FECHA_MANIFIESTO >= TO_DATE('"+fechaInicio+"','DD/MM/YYYY')" +
                             " AND   FECHA_MANIFIESTO <= TO_DATE('"+fechaFin+"','DD/MM/YYYY')";

                    
                    
            smt = conn.prepareStatement(sql);
            rs = smt.executeQuery();
            while (rs.next()) {
                ReporteManifiestosMd rp = new ReporteManifiestosMd();
                rp.setAno_manifiesto(rs.getString(1));
                rp.setManifiesto(rs.getString(2));
                rp.setLinea1(rs.getString(3));
                rp.setPais(rs.getString(4));
                rp.setAno_arribo(rs.getString(5));
                rp.setNum_arribo(rs.getString(6));
                rp.setNaviera1(rs.getString(7));
                rp.setFecha_manifiesto(rs.getString(8));
                rp.setDescripcion_manif(rs.getString(9));
                rp.setFecha_transmision(rs.getString(10));
                rp.setNum_ref_transmi(rs.getString(11));
                rp.setNombre_buque(rs.getString(12));
                rp.setRef_inter_naviera(rs.getString(13));
                rp.setModo_recepcion(rs.getString(14));
                rp.setNumero_viaje(rs.getString(15));
                rp.setEntiempo(rs.getString(16));
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


