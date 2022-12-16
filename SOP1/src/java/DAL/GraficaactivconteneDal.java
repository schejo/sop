package DAL;

import Conexion.Conexion;
import MD.GraficaactivconteneMd;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.zul.SimplePieModel;

public class GraficaactivconteneDal {

    public List<GraficaactivconteneMd> Grafica1(String sFecha_final) throws SQLException {
        List<GraficaactivconteneMd> lstDatos = new ArrayList<GraficaactivconteneMd>();

        PreparedStatement smt = null;
        Connection conn;
        Conexion conex = new Conexion();
        conn = conex.getConnection();
        String sql = null;
        ResultSet rs = null;

        try {

            sql = "SELECT  ANO,\n"
                    + "       DECODE(MES,'01','ENERO','02','FEBRERO','03','MARZO','04','ABRIL','05','MAYO','06','JUNIO','07','JULIO','08','AGOSTO','09','SEPTIEMBRE','10','OCTUBRE','11','NOVIEMBRE','12','DICIEMBRE') as MES,\n"
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
                    + "WHERE   ANO     >   '2015'\n"
                    + "GROUP   BY\n"
                    + "        ANO,\n"
                    + "        MES\n"
                    + "ORDER   BY\n"
                    + "        ANO,\n"
                    + "        MES";

            smt = conn.prepareStatement(sql);
            rs = smt.executeQuery();

            while (rs.next()) {
                GraficaactivconteneMd temp = new GraficaactivconteneMd();

                temp.setSumaactividad(rs.getString(1));
                temp.setActividad(rs.getString(2));

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

    public SimplePieModel Grafica2(String sFecha_final) throws SQLException {
        List<GraficaactivconteneMd> lstDatos = new ArrayList<GraficaactivconteneMd>();

        SimplePieModel model = new SimplePieModel();
        PreparedStatement smt = null;
        Connection conn;
        Conexion conex = new Conexion();
        conn = conex.getConnection();
        String sql = null;
        ResultSet result2 = null;
        try {

            sql = "SELECT  ANO,\n"
                    + "       DECODE(MES,'01','ENERO','02','FEBRERO','03','MARZO','04','ABRIL','05','MAYO','06','JUNIO','07','JULIO','08','AGOSTO','09','SEPTIEMBRE','10','OCTUBRE','11','NOVIEMBRE','12','DICIEMBRE') as MES,\n"
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
                    + "WHERE   ANO     >   '2015'\n"
                    + "GROUP   BY\n"
                    + "        ANO,\n"
                    + "        MES\n"
                    + "ORDER   BY\n"
                    + "        ANO,\n"
                    + "        MES";

            smt = conn.prepareStatement(sql);
            result2 = smt.executeQuery();

            while (result2.next()) {

                GraficaactivconteneMd temp = new GraficaactivconteneMd();

                temp.setSumaactividad(result2.getString(1));
                temp.setActividad(result2.getString(2));

                lstDatos.add(temp);
            }

            for (int i = 0; i < lstDatos.size(); i++) {
                model.setValue(lstDatos.get(i).getActividad(), Integer.parseInt(lstDatos.get(i).getSumaactividad()));
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
