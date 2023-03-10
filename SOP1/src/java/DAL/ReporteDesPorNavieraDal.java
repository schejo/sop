/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;

import Conexion.Conexion;
import MD.ReporteDesPorNavieraMd;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Usuario
 */
public class ReporteDesPorNavieraDal {

    private Connection conexion = null;
    private Conexion cnn = new Conexion();
    // PreparedStatement ps = null;
    Statement st = null;
    ResultSet rs = null;

    public List<ReporteDesPorNavieraMd> REGselect(String inicio, String fin, String naviera) throws SQLException, ClassNotFoundException {
        List<ReporteDesPorNavieraMd> allReporteCli = new ArrayList<ReporteDesPorNavieraMd>();

        String query = " SELECT ROW_NUMBER() OVER (ORDER BY a.IDENTIFICA_CONTENE) AS correlativo, \n"
                + "         a.IDENTIFICA_CONTENE,  \n"
                + "         a.TIPO_CONTENEDOR,  \n"
                + "         c.nom_naviera,  \n"
                + "         b.CODIGO_ACTIVIDAD,  \n"
                + "         TO_char(a.FECHA_INGRESO2,'dd/mm/yyyy'),     a.HORA_INGRESO2,     --//CONCATENAR....\n"
                + "         TO_char(a.FECHA_SALIDA,'dd/mm/yyyy'),\n"
                + "        round( a.FECHA_SALIDA -a.FECHA_INGRESO2  ,0) as dias\n"
                + "         \n"
                + "    FROM IH_CA_CONTENEDORES a,  \n"
                + "         IH_CA_ACTIVI_CONTE b,  \n"
                + "         EPQOP.IF_BQ_NAVIERA c\n"
                + "   WHERE (a.naviera = c.naviera1) and\n"
                + "         ( b.IDENTIFICA_CONTENE = a.IDENTIFICA_CONTENE ) and  \n"
                + "         ( b.CORRELA_CONTENE = a.CORRELA_CONTENE ) and  \n"
                + "         ( b.NUM_CICLO1 IS NOT NULL) AND  \n"
                + "         ( b.CODIGO_ACTIVIDAD = (3 ))AND  \n"
                + "          ( a.FECHA_SALIDA BETWEEN '" + inicio + "' and '" + fin + "') and (round( a.FECHA_SALIDA -a.FECHA_INGRESO2  ,0) >5) and (trim(a.naviera)='" + naviera + "')";
//                + "SELECT ROW_NUMBER() OVER (ORDER BY a.\"IDENTIFICA_CONTENE\") AS correlativo, \n"
//                + "         a.\"IDENTIFICA_CONTENE\",  \n"
//                + "         a.\"TIPO_CONTENEDOR\",  \n"
//                + "         c.nom_naviera,  \n"
//                + "         b.\"CODIGO_ACTIVIDAD\",  \n"
//                + "         TO_char(a.\"FECHA_INGRESO2\",'dd/mm/yyyy'),     a.\"HORA_INGRESO2\",    \n"
//                + "         TO_char(a.\"FECHA_SALIDA\",'dd/mm/yyyy'),\n"
//                + "        round( a.FECHA_SALIDA -a.FECHA_INGRESO2  ,0) as dias\n"
//                + "         \n"
//                + "    FROM IH_CA_CONTENEDORES a,  \n"
//                + "         IH_CA_ACTIVI_CONTE b,  \n"
//                + "         EPQOP.IF_BQ_NAVIERA c\n"
//                + "   WHERE (a.naviera = c.naviera1) and\n"
//                + "         ( b.\"IDENTIFICA_CONTENE\" = a.\"IDENTIFICA_CONTENE\" ) and  \n"
//                + "         ( b.\"CORRELA_CONTENE\" = a.\"CORRELA_CONTENE\" ) and  \n"
//                + "         ( b.\"NUM_CICLO1\" IS NOT NULL) AND  \n"
//                + "         ( b.CODIGO_ACTIVIDAD = (3 ))AND  \n"
//                + "          ( a.FECHA_SALIDA BETWEEN '" + inicio + "' and '" + fin + "') and round( a.FECHA_SALIDA -a.FECHA_INGRESO2  ,0) >5";

        System.out.println("este es el año de arribo " + inicio + "este es el numero de arribo " + fin);

        try {
            conexion = cnn.Conexion();
            st = conexion.createStatement();
            rs = st.executeQuery(query);
            ReporteDesPorNavieraMd rg;
            while (rs.next()) {
                rg = new ReporteDesPorNavieraMd();

                rg.setCorre(rs.getString(1));
                rg.setIdentif(rs.getString(2));
                rg.setNombreNavi(rs.getString(4));
                rg.setFechaDese(rs.getString(6));
                rg.setFechaSalida(rs.getString(8));
                rg.setDias(rs.getString(9));

                allReporteCli.add(rg);
            }
            st.close();
            rs.close();
            conexion.close();
            conexion = null;

        } catch (SQLException e) {
            System.out.println("este es el erro " + e);
            st.close();
            rs.close();
            conexion.close();
            conexion = null;

        }
        return allReporteCli;

    }

}
