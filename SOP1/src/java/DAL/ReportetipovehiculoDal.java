package DAL;

import Conexion.Conexion;
import MD.ReportetipovehiculoMd;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.zkoss.zk.ui.util.Clients;

public class ReportetipovehiculoDal {

    private Connection conexion = null;
    private Conexion cnn = new Conexion();
    PreparedStatement ps = null;
    Statement st = null;
    ResultSet rs = null;

    public List<ReportetipovehiculoMd> REGselect(String fechaInicio, String fechaFin) throws SQLException, ClassNotFoundException {
        List<ReportetipovehiculoMd> alldata = new ArrayList<ReportetipovehiculoMd>();

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String dateInString = "";

        try {
            Date date = formatter.parse(fechaInicio);
            formatter.applyPattern("yyyy/MM/dd");
            dateInString = formatter.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        SimpleDateFormat formatter2 = new SimpleDateFormat("dd/MM/yyyy");
        String dateInString2 = "";

        try {
            Date date2 = formatter2.parse(fechaFin);
            formatter2.applyPattern("yyyy/MM/dd");
            dateInString2 = formatter2.format(date2);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String query = "SELECT  ANO,\n"
              //  + "        MES,\n"
                + "        NOMMES,\n"
                + "        SUM(NUEVO) NUEVO,\n"
                + "        SUM(USADO) USADO\n"
                + "FROM\n"
                + "(\n"
                + "SELECT  TO_CHAR(FECHA_INGRESO,'RRRR') ANO,\n"
                + "        TO_CHAR(FECHA_INGRESO,'MM') MES,\n"
                + "        TO_CHAR(FECHA_INGRESO,'DD') DIA,\n"
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
                + "        DECODE(TRASIEGO,    'S',    NVL(BULTOS_RECIBIDOS,0),\n"
                + "                            'N',    0) NUEVO,\n"
                + "        DECODE(TRASIEGO,    'S',    0,\n"
                + "                            'N',    NVL(BULTOS_RECIBIDOS,0)) USADO\n"
                + "FROM    EPQOP.IF_CA_CARGA_IMPOR\n"
                + "WHERE   TRASIEGO        IN ( 'S', 'N' )\n"
                + "AND     TRUNC(FECHA_INGRESO)   >=   TO_DATE('" + fechaInicio + "','DD/MM/YYYY')\n"
                + "AND     TRUNC(FECHA_INGRESO)   <=   TO_DATE('" + fechaFin + "','DD/MM/YYYY')\n"
                + "\n"
                + "UNION ALL\n"
                + "SELECT  TO_CHAR(FECHA_INGRESO,'RRRR') ANO,\n"
                + "        TO_CHAR(FECHA_INGRESO,'MM') MES,\n"
                + "        TO_CHAR(FECHA_INGRESO,'DD') DIA,\n"
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
                + "        DECODE(TRASIEGO,    'S',    NVL(BULTOS_RECIBIDOS,0),\n"
                + "                            'N',    0) NUEVO,\n"
                + "        DECODE(TRASIEGO,    'S',    0,\n"
                + "                            'N',    NVL(BULTOS_RECIBIDOS,0)) USADO\n"
                + "FROM    EPQOP.IH_CA_CARGA_IMPOR\n"
                + "WHERE   TRASIEGO        IN ( 'S', 'N' )\n"
                + "AND     TRUNC(FECHA_INGRESO)   >=   TO_DATE('" + fechaInicio + "','DD/MM/YYYY')\n"
                + "AND     TRUNC(FECHA_INGRESO)   <=   TO_DATE('" + fechaFin + "','DD/MM/YYYY')\n"
                + ")\n"
                + "GROUP   BY\n"
                + "        ANO,\n"
                + "        MES,\n"
                + "        NOMMES\n"
                + "ORDER   BY\n"
                + "        ANO,\n"
                + "        MES";

        try {
            conexion = cnn.Conexion();
            st = conexion.createStatement();
            rs = st.executeQuery(query);
            ReportetipovehiculoMd rg;
            int x = 0;

            while (rs.next()) {
                rg = new ReportetipovehiculoMd();
                x++;
                rg.setCorrelativo(String.valueOf(x));
                rg.setAnio(rs.getString(1));
                rg.setMes2(rs.getString(2));
                rg.setUsados(rs.getString(3));
                rg.setNuevos(rs.getString(4));

                alldata.add(rg);
            }

            st.close();
            rs.close();
            conexion.close();
            conexion = null;
        } catch (SQLException e) {
            st.close();
            rs.close();
            conexion.close();
            conexion = null;
            Clients.showNotification("ERROR AL CONSULTAR <br/> <br/> REGISTROS! <br/> " + e.getMessage().toString(),
                    "warning", null, "middle_center", 0);
        }
        return alldata;
    }

}
