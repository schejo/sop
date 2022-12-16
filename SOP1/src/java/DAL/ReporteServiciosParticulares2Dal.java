package DAL;

import Conexion.Conexion;
import MD.ReporteServiciosParticulares2Md;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ReporteServiciosParticulares2Dal {

    private Connection conexion = null;
    private Conexion cnn = new Conexion();
    // PreparedStatement ps = null;
    Statement st = null;
    ResultSet rs = null;

    public List<ReporteServiciosParticulares2Md> REGselect(String anio, String numero) throws SQLException, ClassNotFoundException {
        List<ReporteServiciosParticulares2Md> allReporteCli = new ArrayList<ReporteServiciosParticulares2Md>();

        String query = "select a.ano_arribo, a.num_arribo, b.nom_buque, c.tcq_identifica_contene,\n"
                + "           c.tcq_linea, c.tcq_naviera,\n"
                + "           decode( c.tcq_tipo_operacion,'I','IMP','E','EXP') operacion, c.tcq_fecha_ingreso, c.tcq_tipo_contenedor, c.tcq_tm_total,\n"
                + "                decode(c.tcq_estado,'4','VACIO','5','LLENO') ESTADO, c.tcq_cantidad_teu,\n"
                + "             decode(c.tcq_transito,'S','SI','N','NO') TRANSITO\n"
                + "from            epqop.if_bq_arribos a,\n"
                + "                epqop.if_bq_buques b,\n"
                + "                facturacion.fac_dan_detalle_tcq c\n"
                + "where a.buque = b.buque\n"
                + "and     a.ano_arribo = c.tcq_anio_arribo\n"
                + "and     a.num_arribo = c.tcq_num_arribo\n"
                + "and    a.ano_arribo = '" + anio + "'\n"
                + "and    a.num_arribo = '" + numero + "'\n";

        System.out.println("este es el año de arribo " + anio + "este es el numero de arribo " + numero);

        try {
            conexion = cnn.Conexion();
            st = conexion.createStatement();
            rs = st.executeQuery(query);
            ReporteServiciosParticulares2Md rg;
            while (rs.next()) {
                rg = new ReporteServiciosParticulares2Md();

                rg.setAnio(rs.getString(1));
                rg.setNumero(rs.getString(2));
                rg.setNom_buque(rs.getString(3));
                rg.setContenedor(rs.getString(4));
                rg.setLinea(rs.getString(5));
                rg.setNaviera(rs.getString(6));
                rg.setOperacion(rs.getString(7));
                rg.setFecha_ingreso1(rs.getString(8));
                rg.setTipo_contenedor(rs.getString(9));
                rg.setTm_total(rs.getString(10));
                rg.setEstado(rs.getString(11));
                rg.setCant_teu(rs.getString(12));
                rg.setTransito(rs.getString(13));

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

    public List<ReporteServiciosParticulares2Md> REGselectResum(String anio, String numero) throws SQLException, ClassNotFoundException {
        List<ReporteServiciosParticulares2Md> allReporteCli = new ArrayList<ReporteServiciosParticulares2Md>();

        String query2 = "SELECT  'TOTAL DE CONTENEDORES' CONCEPTO, COUNT(1) REGISTROS\n"
                + "FROM    FACTURACION.FAC_DAN_DETALLE_TCQ\n"
                + "WHERE   TCQ_ANIO_ARRIBO     =  '" + anio + "'\n"
                + "AND     TCQ_NUM_ARRIBO      =  '" + numero + "'\n"
                + "UNION ALL\n"
                + "SELECT  'IMPORTACION' CONCEPTO, COUNT(1)\n"
                + "FROM    FACTURACION.FAC_DAN_DETALLE_TCQ\n"
                + "WHERE   TCQ_ANIO_ARRIBO     =   '" + anio + "'\n"
                + "AND     TCQ_NUM_ARRIBO      =   '" + numero + "'\n"
                + "AND     TCQ_TIPO_OPERACION  =   'I'\n"
                + "UNION   ALL\n"
                + "SELECT  'EXPORTACION' CONCEPTO, COUNT(1)\n"
                + "FROM    FACTURACION.FAC_DAN_DETALLE_TCQ\n"
                + "WHERE   TCQ_ANIO_ARRIBO     =   '" + anio + "'\n"
                + "AND     TCQ_NUM_ARRIBO      =   '" + numero + "'\n"
                + "AND     TCQ_TIPO_OPERACION  =   'E'\n"
                + "UNION   ALL\n"
                + "SELECT  'VACIOS' CONCEPTO, COUNT(1)\n"
                + "FROM    FACTURACION.FAC_DAN_DETALLE_TCQ\n"
                + "WHERE   TCQ_ANIO_ARRIBO     =   '" + anio + "'\n"
                + "AND     TCQ_NUM_ARRIBO      =   '" + numero + "'\n"
                + "AND     TCQ_ESTADO          =   '4'\n"
                + "UNION   ALL\n"
                + "SELECT  'LLENOS' CONCEPTO, COUNT(1)\n"
                + "FROM    FACTURACION.FAC_DAN_DETALLE_TCQ\n"
                + "WHERE   TCQ_ANIO_ARRIBO     =   '" + anio + "'\n"
                + "AND     TCQ_NUM_ARRIBO      =  '" + numero + "'\n"
                + "AND     TCQ_ESTADO          =   '5'";

        System.out.println("este es el año de arribo " + anio + "este es el numero de arribo " + numero);

        try {
            conexion = cnn.Conexion();
            st = conexion.createStatement();
            rs = st.executeQuery(query2);
            ReporteServiciosParticulares2Md rg;
            while (rs.next()) {
                rg = new ReporteServiciosParticulares2Md();

                rg.setDescripcion(rs.getString(1));
                rg.setRegistros(rs.getString(2));

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

    public List<ReporteServiciosParticulares2Md> REGselectResumF(String F_inicio, String F_fin) throws SQLException, ClassNotFoundException {
        List<ReporteServiciosParticulares2Md> allReporteCli = new ArrayList<ReporteServiciosParticulares2Md>();

        String query2 = "SELECT  'CONTENEDORES' CONCEPTO, COUNT(1) REGISTROS\n"
                + "FROM    FACTURACION.FAC_DAN_DETALLE_TCQ\n"
                + "WHERE   TCQ_FECHA_INGRESO   >=    to_date('" + F_inicio + "','DD/MM/RRRR')\n"
                + "AND     TCQ_FECHA_INGRESO   <=   to_date('" + F_fin + "','DD/MM/RRRR')\n"
                + "UNION ALL\n"
                + "SELECT  'IMPORTACION' CONCEPTO, COUNT(1)\n"
                + "FROM    FACTURACION.FAC_DAN_DETALLE_TCQ\n"
                + "WHERE   TCQ_FECHA_INGRESO   >=     to_date('" + F_inicio + "','DD/MM/RRRR')\n"
                + "AND     TCQ_FECHA_INGRESO   <=    to_date('" + F_fin + "','DD/MM/RRRR')\n"
                + "AND     TCQ_TIPO_OPERACION  =   'I'\n"
                + "UNION   ALL\n"
                + "SELECT  'EXPORTACION' CONCEPTO, COUNT(1)\n"
                + "FROM    FACTURACION.FAC_DAN_DETALLE_TCQ\n"
                + "WHERE   TCQ_FECHA_INGRESO   >=     to_date('" + F_inicio + "','DD/MM/RRRR')\n"
                + "AND     TCQ_FECHA_INGRESO   <=    to_date('" + F_fin + "','DD/MM/RRRR')\n"
                + "AND     TCQ_TIPO_OPERACION  =   'E'\n"
                + "UNION   ALL\n"
                + "SELECT  'NO OPERACION' CONCEPTO, COUNT(1)\n"
                + "FROM    FACTURACION.FAC_DAN_DETALLE_TCQ\n"
                + "WHERE   TCQ_FECHA_INGRESO   >=     to_date('" + F_inicio + "','DD/MM/RRRR')\n"
                + "AND     TCQ_FECHA_INGRESO   <=    to_date('" + F_fin + "','DD/MM/RRRR')\n"
                + "AND     TCQ_TIPO_OPERACION  <> 'I' AND TCQ_TIPO_OPERACION  <> 'E' \n"
                + "UNION   ALL\n"
               
                + "SELECT  'VACIOS' CONCEPTO, COUNT(1)\n"
                + "FROM    FACTURACION.FAC_DAN_DETALLE_TCQ\n"
                + "WHERE   TCQ_FECHA_INGRESO   >=     to_date('" + F_inicio + "','DD/MM/RRRR')\n"
                + "AND     TCQ_FECHA_INGRESO   <=    to_date('" + F_fin + "','DD/MM/RRRR')\n"
                + "AND     TCQ_ESTADO          =   '4'\n"
                + "UNION   ALL\n"
                + "SELECT  'LLENOS' CONCEPTO, COUNT(1)\n"
                + "FROM    FACTURACION.FAC_DAN_DETALLE_TCQ\n"
                + "WHERE   TCQ_FECHA_INGRESO   >=    to_date('" + F_inicio + "','DD/MM/RRRR')\n"
                + "AND     TCQ_FECHA_INGRESO   <=    to_date('" + F_fin + "','DD/MM/RRRR')\n"
                + "AND     TCQ_ESTADO          =   '5'";

        System.out.println("este es el año de arribo " + F_inicio + "este es el numero de arribo " + F_fin);

        try {
            conexion = cnn.Conexion();
            st = conexion.createStatement();
            rs = st.executeQuery(query2);
            ReporteServiciosParticulares2Md rg;
            while (rs.next()) {
                rg = new ReporteServiciosParticulares2Md();

                rg.setDescripcion(rs.getString(1));
                rg.setRegistros(rs.getString(2));

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
