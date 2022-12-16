package DAL;

import Conexion.Conexion;
import MD.GraficavehiculoMd;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.zul.SimplePieModel;

public class GraficavehiculoDal {

    public List<GraficavehiculoMd> Grafica1(String sFecha_inicial, String sFecha_final) throws SQLException {
        List<GraficavehiculoMd> lstDatos = new ArrayList<GraficavehiculoMd>();

        PreparedStatement smt = null;
        Connection conn;
        Conexion conex = new Conexion();
        conn = conex.getConnection();
        String sql = null;
        ResultSet rs = null;

        try {

            sql = " SELECT  ANO,\n"
               // + "        MES,\n"
                + "        NOMMES,\n"
                + "        SUM(NUEVO) NUEVO,\n"
                + "        SUM(USADO) USADO\n"
                + "FROM\n"
                + "(\n"
                + "SELECT  TO_CHAR(FECHA_INGRESO,'RRRR') ANO,\n"
                + "        TO_CHAR(FECHA_INGRESO,'MM') MES,\n"
                + "        DECODE(TO_CHAR(FECHA_INGRESO,'MM'), '01',   'ENERO',\n"
                + "                                            '02',   'FEBRERO',\n"
                + "                                            '03',   'MARZO',\n"
                + "                                            '04',   'ABRIL',\n"
                + "                                            '05',   'MAYO',\n"
                + "                                            '06',   'JUNIO',\n"
                + "                                            '07',   'JULIO',\n"
                + "                                            '08',   'AGOSTO',\n"
                + "                                            '09',   'SEPTIEMBRE',\n"
                + "                                            '10',   'OCTUBRE',\n"
                + "                                            '11',   'NOVIEMBRE',\n"
                + "                                            '12',   'DICIEMBRE') NOMMES,\n"
                + "        \n"
                + "        DECODE(TRASIEGO,    'S',    NVL(BULTOS_RECIBIDOS,0),\n"
                + "                            'N',    0) NUEVO,\n"
                + "        DECODE(TRASIEGO,    'S',    0,\n"
                + "                            'N',    NVL(BULTOS_RECIBIDOS,0)) USADO\n"
                + "FROM    EPQOP.IF_CA_CARGA_IMPOR\n"
                + "WHERE   TRASIEGO        IN ( 'S', 'N' )\n"
                + "AND     TRUNC(FECHA_INGRESO)   >=   TO_DATE('01/01/2015','DD/MM/RRRR')\n"
                + "UNION ALL\n"
                + "SELECT  TO_CHAR(FECHA_INGRESO,'RRRR') ANO,\n"
                + "        TO_CHAR(FECHA_INGRESO,'MM') MES,\n"
                + "        DECODE(TO_CHAR(FECHA_INGRESO,'MM'), '01',   'ENERO',\n"
                + "                                            '02',   'FEBRERO',\n"
                + "                                            '03',   'MARZO',\n"
                + "                                            '04',   'ABRIL',\n"
                + "                                            '05',   'MAYO',\n"
                + "                                            '06',   'JUNIO',\n"
                + "                                            '07',   'JULIO',\n"
                + "                                            '08',   'AGOSTO',\n"
                + "                                            '09',   'SEPTIEMBRE',\n"
                + "                                            '10',   'OCTUBRE',\n"
                + "                                            '11',   'NOVIEMBRE',\n"
                + "                                            '12',   'DICIEMBRE') NOMMES,\n"
                + "        \n"
                + "        DECODE(TRASIEGO,    'S',    NVL(BULTOS_RECIBIDOS,0),\n"
                + "                            'N',    0) NUEVO,\n"
                + "        DECODE(TRASIEGO,    'S',    0,\n"
                + "                            'N',    NVL(BULTOS_RECIBIDOS,0)) USADO\n"
                + "FROM    EPQOP.IH_CA_CARGA_IMPOR\n"
                + "WHERE   TRASIEGO        IN ( 'S', 'N' )\n"
                + "AND     TRUNC(FECHA_INGRESO)   >=   TO_DATE('01/01/2015','DD/MM/RRRR')\n"
                + "\n"
                + ")\n"
                + "GROUP   BY\n"
                + "        ANO,\n"
                + "        MES,\n"
                + "        NOMMES\n"
                + "ORDER   BY\n"
                + "        ANO,\n"
                + "        MES";

            smt = conn.prepareStatement(sql);
            rs = smt.executeQuery();

            while (rs.next()) {
                GraficavehiculoMd temp = new GraficavehiculoMd();

                temp.setSumavehiculo(rs.getString(1));
                temp.setVehiculo(rs.getString(2));

                lstDatos.add(temp);
            }

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
                conn = conex.desconectar();
            }
        }
        return lstDatos;
    }

    public SimplePieModel Grafica2(String sFecha_inicial, String sFecha_final) throws SQLException {
        List<GraficavehiculoMd> lstDatos = new ArrayList<GraficavehiculoMd>();

        SimplePieModel model = new SimplePieModel();
        PreparedStatement smt = null;
        Connection conn;
        Conexion conex = new Conexion();
        conn = conex.getConnection();
        String sql = null;
        ResultSet result2 = null;
        try {

            sql = " SELECT  ANO,\n"
               // + "        MES,\n"
                + "        NOMMES,\n"
                + "        SUM(NUEVO) NUEVO,\n"
                + "        SUM(USADO) USADO\n"
                + "FROM\n"
                + "(\n"
                + "SELECT  TO_CHAR(FECHA_INGRESO,'RRRR') ANO,\n"
                + "        TO_CHAR(FECHA_INGRESO,'MM') MES,\n"
                + "        DECODE(TO_CHAR(FECHA_INGRESO,'MM'), '01',   'ENERO',\n"
                + "                                            '02',   'FEBRERO',\n"
                + "                                            '03',   'MARZO',\n"
                + "                                            '04',   'ABRIL',\n"
                + "                                            '05',   'MAYO',\n"
                + "                                            '06',   'JUNIO',\n"
                + "                                            '07',   'JULIO',\n"
                + "                                            '08',   'AGOSTO',\n"
                + "                                            '09',   'SEPTIEMBRE',\n"
                + "                                            '10',   'OCTUBRE',\n"
                + "                                            '11',   'NOVIEMBRE',\n"
                + "                                            '12',   'DICIEMBRE') NOMMES,\n"
                + "        \n"
                + "        DECODE(TRASIEGO,    'S',    NVL(BULTOS_RECIBIDOS,0),\n"
                + "                            'N',    0) NUEVO,\n"
                + "        DECODE(TRASIEGO,    'S',    0,\n"
                + "                            'N',    NVL(BULTOS_RECIBIDOS,0)) USADO\n"
                + "FROM    EPQOP.IF_CA_CARGA_IMPOR\n"
                + "WHERE   TRASIEGO        IN ( 'S', 'N' )\n"
                + "AND     TRUNC(FECHA_INGRESO)   >=   TO_DATE('01/01/2015','DD/MM/RRRR')\n"
                + "UNION ALL\n"
                + "SELECT  TO_CHAR(FECHA_INGRESO,'RRRR') ANO,\n"
                + "        TO_CHAR(FECHA_INGRESO,'MM') MES,\n"
                + "        DECODE(TO_CHAR(FECHA_INGRESO,'MM'), '01',   'ENERO',\n"
                + "                                            '02',   'FEBRERO',\n"
                + "                                            '03',   'MARZO',\n"
                + "                                            '04',   'ABRIL',\n"
                + "                                            '05',   'MAYO',\n"
                + "                                            '06',   'JUNIO',\n"
                + "                                            '07',   'JULIO',\n"
                + "                                            '08',   'AGOSTO',\n"
                + "                                            '09',   'SEPTIEMBRE',\n"
                + "                                            '10',   'OCTUBRE',\n"
                + "                                            '11',   'NOVIEMBRE',\n"
                + "                                            '12',   'DICIEMBRE') NOMMES,\n"
                + "        \n"
                + "        DECODE(TRASIEGO,    'S',    NVL(BULTOS_RECIBIDOS,0),\n"
                + "                            'N',    0) NUEVO,\n"
                + "        DECODE(TRASIEGO,    'S',    0,\n"
                + "                            'N',    NVL(BULTOS_RECIBIDOS,0)) USADO\n"
                + "FROM    EPQOP.IH_CA_CARGA_IMPOR\n"
                + "WHERE   TRASIEGO        IN ( 'S', 'N' )\n"
                + "AND     TRUNC(FECHA_INGRESO)   >=   TO_DATE('01/01/2015','DD/MM/RRRR')\n"
                + "\n"
                + ")\n"
                + "GROUP   BY\n"
                + "        ANO,\n"
                + "        MES,\n"
                + "        NOMMES\n"
                + "ORDER   BY\n"
                + "        ANO,\n"
                + "        MES";
                   

            smt = conn.prepareStatement(sql);
            result2 = smt.executeQuery();

            while (result2.next()) {

                GraficavehiculoMd temp = new GraficavehiculoMd();

                temp.setSumavehiculo(result2.getString(1));
                temp.setVehiculo(result2.getString(2));

                lstDatos.add(temp);
            }

            for (int i = 0; i < lstDatos.size(); i++) {
                model.setValue(lstDatos.get(i).getVehiculo(), Integer.parseInt(lstDatos.get(i).getSumavehiculo()));
            }

        } finally {
            if (smt != null) {
                smt.close();
                smt = null;
            }
            if (result2 != null) {
                result2.close();
                result2 = null;
            }
            if (conn != null) {
                conn = conex.desconectar();
            }
        }
        return model;
    }

}
