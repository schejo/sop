package DAL;

import Conexion.Conexion;
import MD.ReporteCitasPlaniMd;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.zk.ui.util.Clients;

public class ReporteCitasPlaniDal {

    public List<ReporteCitasPlaniMd> GetByFecha(String fechaInicio, String fechaFin) throws SQLException {
        List<ReporteCitasPlaniMd> respuesta = new ArrayList<ReporteCitasPlaniMd>();
        PreparedStatement smt = null;
        Connection conn = null;
        Conexion conex = new Conexion();
        ResultSet result = null;
        conn = conex.Conexion();
        try {

            String sql = "SELECT a.ano_arribo,b.num_arribo,c.nom_buque,a.eta_cita,\n"
                    + "       a.fecha_hora,a.observaciones\n"
                    + "FROM   epqop.ib_bq_citas_plani a,\n"
                    + "       epqop.if_bq_arribos b,\n"
                    + "       epqop.if_bq_buques c\n"
                    + "WHERE  a.ano_arribo = b.ano_arribo\n"
                    + "AND    a.num_arribo = b.num_arribo\n"
                    + "AND    b.buque = c.buque\n"
                    + "AND    a.eta_cita BETWEEN  TO_DATE('" + fechaInicio + "','DD/MM/RRRR hh24:mi') \n"
                    + "AND    TO_DATE('" + fechaFin + "','DD/MM/RRRR hh24:mi') \n"
                    + "ORDER BY fecha_hora ASC";

            smt = conn.prepareStatement(sql);
            result = smt.executeQuery();
            while (result.next()) {
                ReporteCitasPlaniMd temporal = new ReporteCitasPlaniMd();
                temporal.setAnio_arribo(result.getString(1));
                temporal.setNum_arribo(result.getString(2));
                temporal.setNombre_buque(result.getString(3));
                temporal.setEta_cita(result.getString(4));
                temporal.setFecha_hora(result.getString(5));
                temporal.setObservaciones(result.getString(6));

                respuesta.add(temporal);

            }
        } catch (Exception e) {
            Clients.showNotification(e.getMessage());
        } finally {
            if (smt != null) {
                smt.close();
                smt = null;
            }
            if (result != null) {
                result.close();
                result = null;
            }
            if (conn != null) {
                conn = conex.desconectar();
            }
        }

        return respuesta;
    }

}
