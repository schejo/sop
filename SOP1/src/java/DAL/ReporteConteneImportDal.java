package DAL;

import Conexion.Conexion;
import MD.ReporteConteneImportMd;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.zk.ui.util.Clients;

public class ReporteConteneImportDal {

    public List<ReporteConteneImportMd> GetByFecha(String anio, String numero) throws SQLException {
        List<ReporteConteneImportMd> respuesta = new ArrayList<ReporteConteneImportMd>();
        PreparedStatement smt = null;
        Connection conn = null;
        Conexion conx = new Conexion();
        ResultSet rs = null;
        conn = conx.Conexion();
        try {

            String sql = "SELECT a.buque,a.nom_buque AS NOMBRE_BUQUE,b.ano_arribo AS AÑO_ARRIBO,\n"
                    + "       b.num_arribo AS NUMERO_ARRIBO,b.datos_importacion,b.datos_exportacion,\n"
                    + "       b.fecha_atraque,b.opera_inicio,b.opera_fin,\n"
                    + "       COUNT(c.identifica_contene) as CONTENEDORES,\n"
                    + "       DECODE(c.vacio_lleno,4,'VACIO',5,'LLENO') VACIO_LLENO,g.nombre_actividad,\n"
                    + " COUNT(DECODE(c.via_directa,'S','DIRECTA')) AS VIA_DIRECTA,\n"
                    + " COUNT(DECODE(c.via_directa,'N','INDIRECTA')) AS VIA_INDIRECTA,\n"
                    + " COUNT(DECODE(c.via_directa,'P','INTERMEDIA')) AS VIA_INTERMEDIA,\n"
                    + " COUNT(DECODE(c.via_directa,'M','INTERMEDIA MAERSK')) AS VIA_INTERMEDIA_MAERSK,\n"
                    + "              e.tipo_contenedor,DECODE(d.transito, 'S','SI','N','NO') AS TRANSITO,\n"
                    + "              f.nom_linea,h.nom_naviera\n"
                    + "FROM epqop.if_bq_buques a,\n"
                    + "     epqop.if_bq_arribos b,\n"
                    + "     epqop.if_ca_activi_conte c,\n"
                    + "     epqop.if_ca_contenedores d,\n"
                    + "     epqop.if_ca_tipo_contene e,\n"
                    + "     epqop.if_bq_lineas       f,\n"
                    + "     epqop.if_ca_tipo_activid g,\n"
                    + "     epqop.if_bq_naviera      h\n"
                    + "WHERE  a.buque              = b.buque\n"
                    + "AND    b.ano_arribo         = c.ano_arribo2\n"
                    + "AND    b.num_arribo         = c.num_arribo2\n"
                    + "AND    c.identifica_contene = d.identifica_contene\n"
                    + "AND    d.tipo_contenedor    = e.tipo_contenedor\n"
                    + "AND    d.linea              = f.linea1\n"
                    + "AND    c.codigo_actividad   = g.codigo_actividad\n"
                    + "AND    d.naviera            = h.naviera1\n"
                    + "AND b.ano_arribo =  " + anio + "\n"
                    + "AND b.num_arribo = " + numero + "\n"
                    + "GROUP BY a.buque, a.nom_buque, b.datos_importacion, b.datos_exportacion,\n"
                    + "         b.ano_arribo, b.num_arribo,c.via_directa, d.transito, c.vacio_lleno,d.linea,\n"
                    + "         e.tipo_contenedor,f.nom_linea,b.fecha_atraque,b.opera_inicio,b.opera_fin,\n"
                    + "         g.nombre_actividad,h.nom_naviera";

            smt = conn.prepareStatement(sql);
            rs = smt.executeQuery();
            while (rs.next()) {
                ReporteConteneImportMd rp = new ReporteConteneImportMd();
                rp.setBuque(rs.getString(1));
                rp.setNom_buque(rs.getString(2));
                rp.setAnio_arribo(rs.getString(3));
                rp.setNum_arribo(rs.getString(4));
                rp.setDatos_import(rs.getString(5));
                rp.setDatos_export(rs.getString(6));
                rp.setFecha_atraque(rs.getString(7));
                rp.setF_opera_inicio(rs.getString(8));
                rp.setF_opera_fin(rs.getString(9));
                rp.setContenedores(rs.getString(10));
                rp.setVacio_lleno(rs.getString(11));
                rp.setActividad(rs.getString(12));
                rp.setVia_directa(rs.getString(13));
                rp.setVia_indirecta(rs.getString(14));
                rp.setVia_intermedia(rs.getString(15));
                rp.setVia_maersk(rs.getString(16));
                rp.setTipo_contene(rs.getString(17));
                rp.setTransito(rs.getString(18));
                rp.setLinea(rs.getString(19));
                rp.setNaviera(rs.getString(20));
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
