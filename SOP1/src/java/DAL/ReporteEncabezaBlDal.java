package DAL;

import Conexion.Conexion;
import MD.ReporteEncabezaBlMd;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.zk.ui.util.Clients;

public class ReporteEncabezaBlDal {

    public List<ReporteEncabezaBlMd> GetByFecha(String fechaInicio, String fechaFin) throws SQLException {
        List<ReporteEncabezaBlMd> respuesta = new ArrayList<ReporteEncabezaBlMd>();
        PreparedStatement smt = null;
        Connection conn = null;
        Conexion conx = new Conexion();
        ResultSet rs = null;
        conn = conx.Conexion();
        try {

                String sql ="SELECT EPQOP.IF_MA_ENCABEZA_BL.ANO_MANIFIESTO,"+ 
                            "EPQOP.IF_MA_ENCABEZA_BL.MANIFIESTO,"+  
                            "EPQOP.IF_MA_ENCABEZA_BL.CALIFICADOR_MANIFI,"+  
                            "EPQOP.IF_MA_ENCABEZA_BL.CORRELATIVO_BL,"+
                            "EPQOP.IF_MA_ENCABEZA_BL.BL_NUMERO,"+  
                            "EPQOP.IF_MA_ENCABEZA_BL.PUERTO,"+  
                            "EPQOP.IF_MA_ENCABEZA_BL.PUERTO_TRANSBOR,"+  
                            "EPQOP.IF_MA_ENCABEZA_BL.LOCALIDAD_DEST_FIN,"+  
                            "EPQOP.IF_MA_ENCABEZA_BL.PESO1,"+  
                            "EPQOP.IF_MA_ENCABEZA_BL.PAIS_PROCEDENCIA,"+  
                            "EPQOP.IF_MA_ENCABEZA_BL.PAIS_DESTINO_FINAL,"+  
                            "EPQOP.IF_MA_ENCABEZA_BL.EMBARCADOR,"+  
                            "EPQOP.IF_MA_ENCABEZA_BL.CONSIGNATARIO,"+  
                            "EPQOP.IF_MA_ENCABEZA_BL.NOTIFICAR,"+  
                            "EPQOP.IF_MA_ENCABEZA_BL.OBSERVACIONES_BL,"+  
                            "EPQOP.IF_BQ_PUERTOS.NOMBRE_PUERTO,"+
                            "EPQOP.IF_MA_ENCABEZA_BL.CODIGO_LOCALIDAD,"+  
                            "EPQOP.IF_BQ_PAISES.NOMBRE_PAIS"+
                       "FROM EPQOP.IF_MA_ENCABEZA_BL,"+  
                            "EPQOP.IF_BQ_PUERTOS,"+  
                            "EPQOP.IF_BQ_PAISES"+  
                      "WHERE EPQOP.IF_MA_ENCABEZA_BL.PUERTO = EPQOP.IF_BQ_PUERTOS.PUERTO"+
                      "AND   EPQOP.IF_MA_ENCABEZA_BL.PAIS_DESTINO_FINAL = EPQOP.IF_BQ_PAISES.PAIS"+
                     " AND   EPQOP.IF_MA_ENCABEZA_BL.FECHA_ALTA >= TO_DATE('"+fechaInicio+"','DD/MM/YYYY')"+
                     "AND    EPQOP.IF_MA_ENCABEZA_BL.FECHA_ALTA <= TO_DATE('"+fechaFin+"','DD/MM/YYYY')";


                    
                    
            smt = conn.prepareStatement(sql);
            rs = smt.executeQuery();
            while (rs.next()) {
                ReporteEncabezaBlMd rp = new ReporteEncabezaBlMd();
                rp.setAno_manifiesto(rs.getString(1));
                rp.setManifiesto(rs.getString(2));
                rp.setCalificador_manifi(rs.getString(3));
                rp.setCorrelativo_bl(rs.getString(4));
                rp.setBl_numero(rs.getString(5));
                rp.setPuerto(rs.getString(6));
                rp.setPuerto_transbor(rs.getString(7));
                rp.setLocalidad_dest_fin(rs.getString(8));
                rp.setPeso1(rs.getString(9));
                rp.setPais_procedencia(rs.getString(10));
                rp.setPais_destino_final(rs.getString(11));
                rp.setEmbarcador(rs.getString(12));
                rp.setConsignatario(rs.getString(13));
                rp.setNotificar(rs.getString(14));
                rp.setObservaciones_bl(rs.getString(15));
                rp.setNombre_puerto(rs.getString(16));
                rp.setCodigo_localidad(rs.getString(17));
                rp.setNombre_pais(rs.getString(18));
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


