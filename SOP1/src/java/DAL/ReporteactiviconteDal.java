package DAL;

import Conexion.Conexion;
import MD.ReporteactiviconteMd;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.zk.ui.util.Clients;

public class ReporteactiviconteDal {

    public List<ReporteactiviconteMd> GetByFecha(String fechaInicio) throws SQLException {
        List<ReporteactiviconteMd> respuesta = new ArrayList<ReporteactiviconteMd>();
        PreparedStatement smt = null;
        Connection conn = null;
        Conexion conx = new Conexion();
        ResultSet rs = null;
        conn = conx.Conexion();
        try {

            String sql = "SELECT  ANO,\n"
                    + "           MES,\n"
                    + "        DECODE(MES,     '01',   'ENERO',\n"
                    + "                        '02',   'FEBRERO',\n"
                    + "                        '03',   'MARZO',\n"
                    + "                        '04',   'ABRIL',\n"
                    + "                        '05',   'MAYO',\n"
                    + "                        '06',   'JUNIO',\n"
                    + "                        '07',   'JULIO',\n"
                    + "                        '08',   'AGOSTO',\n"
                    + "                        '09',   'SEPTIEMBRE',\n"
                    + "                        '10',   'OCTUBRE',\n"
                    + "                        '11',   'NOVIEMBRE',\n"
                    + "                        '12',   'DICIEMBRE') NOMMES,        \n"
                    + "        SUM(IMPORTACION) IMPORTACION,\n"
                    + "        SUM(EXPORTACION) EXPORTACION,\n"
                    + "        SUM(RAYOSX)      RAYOSX\n"
                    + "FROM        \n"
                    + "(\n"
                    + "SELECT  TO_CHAR(FECHA_ALTA,'RRRR') ANO,\n"
                    + "        TO_CHAR(FECHA_ALTA,'MM') MES,\n"
                    + "        1 IMPORTACION,\n"
                    + "        0 EXPORTACION,\n"
                    + "        0 RAYOSX\n"
                    + "FROM    EPQOP.IF_CA_ACTIVI_CONTE\n"
                    + "WHERE   CODIGO_ACTIVIDAD    IN ( 1,13 )\n"
                    + "UNION ALL\n"
                    + "SELECT  TO_CHAR(FECHA_ALTA,'RRRR') ANO,\n"
                    + "        TO_CHAR(FECHA_ALTA,'MM') MES,\n"
                    + "        0,\n"
                    + "        1,\n"
                    + "        0\n"
                    + "FROM    EPQOP.IF_CA_ACTIVI_CONTE\n"
                    + "WHERE   CODIGO_ACTIVIDAD    IN ( 6, 8 )\n"
                    + "UNION ALL\n"
                    + "SELECT  TO_CHAR(FECHA_ALTA,'RRRR') ANO,\n"
                    + "        TO_CHAR(FECHA_ALTA,'MM') MES,\n"
                    + "        0,\n"
                    + "        0,\n"
                    + "        1\n"
                    + "FROM    EPQOP.IF_CA_ACTIVI_CONTE\n"
                    + "WHERE   CODIGO_ACTIVIDAD    IN ( 45 )\n"
                    + "UNION ALL\n"
                    + "SELECT  TO_CHAR(FECHA_ALTA,'RRRR') ANO,\n"
                    + "        TO_CHAR(FECHA_ALTA,'MM') MES,\n"
                    + "        1,\n"
                    + "        0,\n"
                    + "        0 RAYOSX\n"
                    + "FROM    EPQOP.IH_CA_ACTIVI_CONTE\n"
                    + "WHERE   CODIGO_ACTIVIDAD    IN ( 1,13 )\n"
                    + "UNION ALL\n"
                    + "SELECT  TO_CHAR(FECHA_ALTA,'RRRR') ANO,\n"
                    + "        TO_CHAR(FECHA_ALTA,'MM') MES,\n"
                    + "        0,\n"
                    + "        1,\n"
                    + "        0\n"
                    + "FROM    EPQOP.IH_CA_ACTIVI_CONTE\n"
                    + "WHERE   CODIGO_ACTIVIDAD    IN ( 6, 8 )\n"
                    + "UNION ALL\n"
                    + "SELECT  TO_CHAR(FECHA_ALTA,'RRRR') ANO,\n"
                    + "        TO_CHAR(FECHA_ALTA,'MM') MES,\n"
                    + "        0,\n"
                    + "        0,\n"
                    + "        1\n"
                    + "FROM    EPQOP.IH_CA_ACTIVI_CONTE\n"
                    + "WHERE   CODIGO_ACTIVIDAD    IN ( 45 )\n"
                    + ")\n"
                    + "WHERE   ANO     >   '2014'\n"
                    + "GROUP   BY\n"
                    + "        ANO,\n"
                    + "        MES\n"
                    + "ORDER   BY\n"
                    + "        ANO,\n"
                    + "        MES";

            smt = conn.prepareStatement(sql);
            rs = smt.executeQuery();
            while (rs.next()) {
                ReporteactiviconteMd rp = new ReporteactiviconteMd();
                rp.setAnio(rs.getString(1));
                rp.setMes(rs.getString(2));
               // rp.setNombre_mes(rs.getString(3));
                rp.setImportacion(rs.getString(3));
                rp.setExportacion(rs.getString(4));
                rp.setRayosx(rs.getString(5));
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
