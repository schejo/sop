package DAL;

import Conexion.Conexion;
import MD.GraficaContenelineaexpMd;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.zul.SimplePieModel;

public class GraficaContenelineaexpDal {

    public List<GraficaContenelineaexpMd> Grafica1(String sFecha_inicial, String sFecha_final) throws SQLException {
        List<GraficaContenelineaexpMd> lstDatos = new ArrayList<GraficaContenelineaexpMd>();

        PreparedStatement smt = null;
        Connection conn;
        Conexion conex = new Conexion();
        conn = conex.getConnection();
        String sql = null;
        ResultSet rs = null;

        try {

            sql = " select sum(TOTAl_LINEA), linea\n"
                    + " from (\n"
                    + " select count(a.linea) as TOTAL_LINEA,a.linea\n"
                    + " from epqop.if_ca_contenedores a,\n"
                    + "     epqop.if_ca_activi_conte b\n"
                    + " where a.identifica_contene = b.identifica_contene\n"
                    + " and   b.codigo_actividad in (6,8)\n"
                    + " and   a.fecha_ingreso2 >= TO_DATE('"+sFecha_inicial+"','DD/MM/YYYY') "
                    + " and   a.fecha_ingreso2 <= TO_DATE('"+sFecha_final+"','DD/MM/YYYY') "
                    + " group by a.linea\n"
                    + " union all\n"
                    + " select count(a.linea) as TOTAL_LINEA,a.linea\n"
                    + " from epqop.ih_ca_contenedores a,\n"
                    + "      epqop.ih_ca_activi_conte b\n"
                    + " where a.identifica_contene = b.identifica_contene\n"
                    + " and   a.correla_contene = b.correla_contene\n"
                    + " and   b.codigo_actividad in (6,8)\n"
                    + " and   a.fecha_ingreso2 >= TO_DATE('"+sFecha_inicial+"','DD/MM/YYYY') "
                    + " and   a.fecha_ingreso2 <= TO_DATE('"+sFecha_final+"','DD/MM/YYYY') "
                    + " group by a.linea\n"
                    + " )\n" 
                    + " group by linea"
                    + " order by sum(TOTAL_LINEA)";
            
            smt = conn.prepareStatement(sql);
            rs = smt.executeQuery();

            while (rs.next()) {
                GraficaContenelineaexpMd temp = new GraficaContenelineaexpMd();

                temp.setSumalineas(rs.getString(1));
                temp.setLineas(rs.getString(2));

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
        List<GraficaContenelineaexpMd> lstDatos = new ArrayList<GraficaContenelineaexpMd>();

        SimplePieModel model = new SimplePieModel();
        PreparedStatement smt = null;
        Connection conn;
        Conexion conex = new Conexion();
        conn = conex.getConnection();
        String sql = null;
        ResultSet result2 = null;
        try {

            sql = " select sum(TOTAl_LINEA), linea\n"
                    + " from (\n"
                    + " select count(a.linea) as TOTAL_LINEA,a.linea\n"
                    + " from epqop.if_ca_contenedores a,\n"
                    + "     epqop.if_ca_activi_conte b\n"
                    + " where a.identifica_contene = b.identifica_contene\n"
                    + " and   b.codigo_actividad in (6,8)\n"
                    + " and   a.fecha_ingreso2 >= TO_DATE('"+sFecha_inicial+"','DD/MM/YYYY') "
                    + " and   a.fecha_ingreso2 <= TO_DATE('"+sFecha_final+"','DD/MM/YYYY') "
                    + " group by a.linea\n"
                    + " union all\n"
                    + " select count(a.linea) as TOTAL_LINEA,a.linea\n"
                    + " from epqop.ih_ca_contenedores a,\n"
                    + "      epqop.ih_ca_activi_conte b\n"
                    + " where a.identifica_contene = b.identifica_contene\n"
                    + " and   a.correla_contene = b.correla_contene\n"
                    + " and   b.codigo_actividad in (6,8)\n"
                    + " and   a.fecha_ingreso2 >= TO_DATE('"+sFecha_inicial+"','DD/MM/YYYY') "
                    + " and   a.fecha_ingreso2 <= TO_DATE('"+sFecha_final+"','DD/MM/YYYY') "
                    + " group by a.linea\n"
                    + " )\n" 
                    + " group by linea"
                    + " order by sum(TOTAL_LINEA)";

            smt = conn.prepareStatement(sql);
            result2 = smt.executeQuery();

            while (result2.next()) {

                GraficaContenelineaexpMd temp = new GraficaContenelineaexpMd();

                temp.setSumalineas(result2.getString(1));
                temp.setLineas(result2.getString(2));

                lstDatos.add(temp);
            }

            for (int i = 0; i < lstDatos.size(); i++) {
                model.setValue(lstDatos.get(i).getLineas(), Integer.parseInt(lstDatos.get(i).getSumalineas()));
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
