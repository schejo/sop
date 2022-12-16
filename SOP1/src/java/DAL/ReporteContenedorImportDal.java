package DAL;

import Conexion.Conexion;
import MD.ReporteContenedorImportMd;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.zk.ui.util.Clients;

public class ReporteContenedorImportDal {

    public List<ReporteContenedorImportMd> GetByFecha(String fechaInicio, String fechaFin) throws SQLException {
        List<ReporteContenedorImportMd> respuesta = new ArrayList<ReporteContenedorImportMd>();
        PreparedStatement smt = null;
        Connection conn = null;
        Conexion conx = new Conexion();
        ResultSet rs = null;
        conn = conx.Conexion();
        try {

            String sql = "SELECT a.identifica_contene AS CONTENEDOR,\n"
                    + " nvl(decode(a.codigo_actividad,'1','IMPORTACION','13','FUERA MANIFIESTO'), 0) as ACTIVIDAD,\n"
                    + " nvl(a.puerto,' ')   AS PUERTO_ORIGEN,\n"
                    + " nvl(b.nombre_puerto,' ')    AS NOMBRE_ORIGEN,\n"
                    + " nvl(c.nombre_pais,' ')  AS PAIS_ORIGEN,\n"
                    + " to_char(a.fecha_inicial2,'DD/MM/YYYY')   AS FECHA_OPERACION,\n"
                    + " nvl(d.tipo_contenedor,' ') AS TIPO_CONTENEDOR,\n"
                    + " e.cantidad_teu AS CANTIDAD_TEUS,\n"
                    + " a.nom_buque as NOMBRE_BUQUE,\n"
                    + " to_char(f.fecha_atraque,'DD/MM/YYYY') FECHA_ATRAQUE\n"
                    + " FROM epqop.If_ca_activi_conte a,\n"
                    + " epqop.If_bq_puertos      b,\n"
                    + " epqop.If_Bq_Paises       c,\n"
                    + " epqop.if_ca_contenedores d,\n"
                    + " epqop.if_ca_tipo_contene e,\n"
                    + " epqop.if_bq_arribos f\n"
                    + " WHERE a.identifica_contene = d.identifica_contene\n"
                    + " AND  b.puerto(+)           = a.puerto\n"
                    + " AND  c.pais(+)             = b.pais\n"
                    + " AND  trim(d.tipo_contenedor) = trim(e.tipo_contenedor)\n"
                    + " AND a.ano_arribo2 = f.ano_arribo\n"
                    + " AND a.num_arribo2 = f.num_arribo\n"
                    + " AND  a.codigo_actividad in (1,13)\n"
                    + " AND   f.fecha_atraque >= TO_DATE('" + fechaInicio + "','DD/MM/YYYY')\n"
                    + " AND   f.fecha_atraque < TO_DATE('" + fechaFin + "','DD/MM/YYYY')\n"
                    + " ORDER BY a.fecha_inicial2";

            smt = conn.prepareStatement(sql);
            rs = smt.executeQuery();
            while (rs.next()) {
                ReporteContenedorImportMd rp = new ReporteContenedorImportMd();
                rp.setContenedor(rs.getString(1));
                rp.setActividad(rs.getString(2));
                rp.setPuerto_Origen(rs.getString(3));
                rp.setNombre_Origen(rs.getString(4));
                rp.setPais_Origen(rs.getString(5));
                rp.setFecha_Operacion(rs.getString(6));
                rp.setTipo_Contenedor(rs.getString(7));
                rp.setCantidad_Teus(rs.getString(8));
                rp.setNombre_Buque(rs.getString(9));
                rp.setFecha_Atracadero(rs.getString(10));
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
