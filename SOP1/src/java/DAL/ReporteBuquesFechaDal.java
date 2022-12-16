package DAL;

import Conexion.Conexion;
import MD.ReporteBuquesFechaMd;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.zk.ui.util.Clients;

public class ReporteBuquesFechaDal {

    public List<ReporteBuquesFechaMd> GetByFecha(String fechaInicio, String fechaFin) throws SQLException {
        List<ReporteBuquesFechaMd> respuesta = new ArrayList<ReporteBuquesFechaMd>();
        PreparedStatement smt = null;
        Connection conn = null;
        Conexion conx = new Conexion();
        ResultSet rs = null;
        conn = conx.Conexion();
        try {

            String sql = "SELECT c.nom_buque,b.num_arribo, b.ano_arribo, b.fecha_atraque,\n"
                    + "DECODE(b.tipo_buque,1,'PORTACOTENEDORES',2,'CARGA GENERAL',3,'GRANEL SOLIDO',4,'GRANEL LIQUIDO',\n"
                    + "             5,'VEHICULOS RO/RO',6,'CRUCERO',7,'FRAGATA',8,'PESQUERO',9,'REMOLCADOR',10,'OTRO',\n"
                    + "             11,'REFRIGERADO',20,'BUQUE DE GUERRA') AS TIPO_BUQUE, a.descripcion,\n"
                    + "SUM(a.peso_bruto_recibid) AS PESO_BRUTO_RECIBIDO,\n"
                    + "SUM(a.bultos_recibidos) AS BULTOS_RECIBIDOS\n"
                    + "FROM  epqop.if_ca_carga_impor a,\n"
                    + "      epqop.if_bq_arribos     b,\n"
                    + "      epqop.if_bq_buques      c\n"
                    + "WHERE a.ano_arribo = b.ano_arribo\n"
                    + "AND   a.num_arribo = b.num_arribo\n"
                    + "AND   b.buque      = c.buque\n"
                    + "AND   b.fecha_atraque >= TO_DATE('" + fechaInicio + "','DD/MM/YYYY')\n"
                    + "AND   b.fecha_atraque <= TO_DATE('" + fechaFin + "','DD/MM/YYYY')\n"
                    + "GROUP BY b.num_arribo,b.ano_arribo, b.tipo_buque, b.fecha_atraque, c.nom_buque, a.descripcion";
                   
            smt = conn.prepareStatement(sql);
            rs = smt.executeQuery();
            while (rs.next()) {
                ReporteBuquesFechaMd rp = new ReporteBuquesFechaMd();
                rp.setNom_buque(rs.getString(1));
                rp.setNum_arribo(rs.getString(2));
                rp.setAnio_arribo(rs.getString(3));
                rp.setF_atraque(rs.getString(4));
                rp.setTipo_buque(rs.getString(5));
                rp.setDescripcion(rs.getString(6));
                rp.setTonelaje(rs.getString(7));
                rp.setBultos(rs.getString(8));
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
        return respuesta;
    }

}
