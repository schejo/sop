package DAL;

import Conexion.Conexion;
import MD.reportebuqueMd;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.zk.ui.util.Clients;

public class reportebuqueDal {

    public List<reportebuqueMd> GetByFecha(String fechaInicio, String fechaFin) throws SQLException {
        List<reportebuqueMd> respuesta = new ArrayList<reportebuqueMd>();
        PreparedStatement smt = null;
        Connection conn = null;
        Conexion conx = new Conexion();
        ResultSet rs = null;
        conn = conx.Conexion();
        try {

            String sql = "SELECT  A.ANO_ARRIBO,\n"
                    + "        A.NUM_ARRIBO,\n"
                    + "        B.NOM_BUQUE,\n"
                    + "        UPPER(C.NOM_TIPO_BUQUE) TIPO_BUQUE,\n"
                    + "        UPPER(D.TIPO_TERMINAL) TIPO_TERMINAL,\n"
                    + "        A.DATOS_IMPORTACION,\n"
                    + "        A.DATOS_EXPORTACION,\n"
                    + "        TO_CHAR(A.FECHA_ATRAQUE,'DD/MM/YYYY') || ' ' || TO_CHAR(A.HORA_ATRAQUE,'HH24:MI:SS')  FECHA_ATRAQUE,              \n"
                    + "        A.OPERA_INICIO,\n"
                    + "        A.OPERA_FIN,    \n"
                    + "        TO_CHAR(A.FECHA_ZARPE,'DD/MM/YYYY') || ' ' || TO_CHAR(A.HORA_ZARPE,'HH24:MI:SS') FECHA_ZARPE,\n"
                    + "        (\n"
                    + "            SELECT  E.FECHA_PILOI\n"
                    + "            FROM    IF_BQ_REG_ACTIVIDA  E\n"
                    + "            WHERE   E.ANO_ARRIBO        =   A.ANO_ARRIBO\n"
                    + "            AND     E.NUM_ARRIBO        =   A.NUM_ARRIBO\n"
                    + "            AND     E.NUM_ACTIVIDAD1    =   3 AND ROWNUM = 1\n"
                    + "        ) ATRAQUE_PRACT_INICIO,\n"
                    + "        (\n"
                    + "            SELECT  E.FECHA_PILOF\n"
                    + "            FROM    IF_BQ_REG_ACTIVIDA  E\n"
                    + "            WHERE   E.ANO_ARRIBO        =   A.ANO_ARRIBO\n"
                    + "            AND     E.NUM_ARRIBO        =   A.NUM_ARRIBO\n"
                    + "            AND     E.NUM_ACTIVIDAD1    =   3 AND ROWNUM = 1\n"
                    + "        ) ATRAQUE_PRACT_FIN,\n"
                    + "        (\n"
                    + "            SELECT  E.FECHA_PILOI\n"
                    + "            FROM    IF_BQ_REG_ACTIVIDA  E\n"
                    + "            WHERE   E.ANO_ARRIBO        =   A.ANO_ARRIBO\n"
                    + "            AND     E.NUM_ARRIBO        =   A.NUM_ARRIBO\n"
                    + "            AND     E.NUM_ACTIVIDAD1    =   5 AND ROWNUM = 1\n"
                    + "        ) ZARPE_PRACT_INICIO,\n"
                    + "        (\n"
                    + "            SELECT  E.FECHA_PILOF\n"
                    + "            FROM    IF_BQ_REG_ACTIVIDA  E\n"
                    + "            WHERE   E.ANO_ARRIBO        =   A.ANO_ARRIBO\n"
                    + "            AND     E.NUM_ARRIBO        =   A.NUM_ARRIBO\n"
                    + "            AND     E.NUM_ACTIVIDAD1    =   5 AND ROWNUM = 1\n"
                    + "        ) ZARPE_PRACT_FIN,\n"
                    + "        (\n"
                    + "            SELECT  TO_CHAR(E.FECHA_ACT,'DD/MM/YYYY') || ' ' || TO_CHAR(E.HORA_ACT,'HH24:MI:SS')\n"
                    + "            FROM    IF_BQ_REG_ACTIVIDA  E\n"
                    + "            WHERE   E.ANO_ARRIBO        =   A.ANO_ARRIBO\n"
                    + "            AND     E.NUM_ARRIBO        =   A.NUM_ARRIBO\n"
                    + "            AND     E.NUM_ACTIVIDAD1    =   1 AND ROWNUM = 1\n"
                    + "        ) LLEGA_FONDEO,\n"
                    + "        (\n"
                    + "            SELECT  \n"
                    + "                    TO_CHAR(E.FECHA_ACT,'DD/MM/YYYY') || ' ' || TO_CHAR(E.HORA_ACT,'HH24:MI:SS')\n"
                    + "            FROM    IF_BQ_REG_ACTIVIDA  E\n"
                    + "            WHERE   E.ANO_ARRIBO        =   A.ANO_ARRIBO\n"
                    + "            AND     E.NUM_ARRIBO        =   A.NUM_ARRIBO\n"
                    + "            AND     E.NUM_ACTIVIDAD1    =   35 AND ROWNUM = 1\n"
                    + "        ) SALE_FONDEO                \n"
                    + "FROM    IF_BQ_ARRIBOS       A,\n"
                    + "        IF_BQ_BUQUES        B,\n"
                    + "        IF_BQ_TIPO_ARRIBO   C,\n"
                    + "        IF_BQ_ATRACADEROS   D\n"
                    + "WHERE   A.BUQUE             =   B.BUQUE\n"
                    + "AND     A.TIPO_BUQUE        =   C.TIPO_BUQUE\n"
                    + "AND     A.NUM_ATRACADERO1   =   D.NUM_ATRACADERO1\n"
                    + "AND     A.FECHA_ATRAQUE\n"
                    + "        BETWEEN         TO_DATE('" + fechaInicio + "','DD/MM/YYYY')\n"
                    + "        AND             TO_DATE('" + fechaFin + "','DD/MM/YYYY')\n"
                    + "ORDER   BY\n"
                    + "        A.ANO_ARRIBO,\n"
                    + "        A.NUM_ARRIBO";

            smt = conn.prepareStatement(sql);
            rs = smt.executeQuery();
            while (rs.next()) {
                reportebuqueMd rp = new reportebuqueMd();
                rp.setAnio_arribo(rs.getString(1));
                rp.setNum_arribo(rs.getString(2));
                rp.setNombre_buque(rs.getString(3));
                rp.setTipo_buque(rs.getString(4));
                rp.setTipo_terminal(rs.getString(5));
                rp.setDatos_import(rs.getString(6));
                rp.setDatos_export(rs.getString(7));
                rp.setFecha_atraque(rs.getString(8));
                rp.setInicio_operacion(rs.getString(9));
                rp.setFin_operacion(rs.getString(10));
                rp.setFecha_zarpe(rs.getString(11));
                rp.setAtraque_pract_inicio(rs.getString(12));
                rp.setAtraque_pract_fin(rs.getString(13));
                rp.setZarpe_pract_inicio(rs.getString(14));
                rp.setZarpe_pract_fin(rs.getString(15));
                rp.setLlega_fondeo(rs.getString(16));
                rp.setDeja_fondeo(rs.getString(17));

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
