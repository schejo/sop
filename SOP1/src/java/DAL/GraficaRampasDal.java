package DAL;

import Conexion.Conexion;
import MD.GraficaRampasMd;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.zul.SimplePieModel;

public class GraficaRampasDal {

    public List<GraficaRampasMd> Grafica1(String sFecha_inicial, String sFecha_final) throws SQLException {
        List<GraficaRampasMd> lstDatos = new ArrayList<GraficaRampasMd>();

        PreparedStatement smt = null;
        Connection conn;
        Conexion conex = new Conexion();
        conn = conex.getConnection();
        String sql = null;
        ResultSet rs = null;

        try {

            sql = "select sum(TOTAL), naviera\n" +
                    "from(\n" +
                    "select count(a.codigo_actividad)as TOTAL, c.naviera\n" +
                    "from  epqop.if_ca_activi_conte a,\n" +
                    "      epqop.if_ca_tipo_activid b,\n" +
                    "      epqop.if_ca_contenedores c\n" +
                    "where  a.codigo_actividad = b.codigo_actividad\n" +
                    "and    a.identifica_contene = c.identifica_contene\n" +
                    "and    a.codigo_actividad = 38\n" +
                    " and   c.fecha_ingreso2 >= TO_DATE('"+sFecha_inicial+"','DD/MM/YYYY') "+
                    " and   c.fecha_ingreso2 <= TO_DATE('"+sFecha_final+"','DD/MM/YYYY') "+
                    "group by c.naviera\n" +
                    "union all\n" +
                    "select count(a.codigo_actividad)as TOTAL,c.naviera\n" +
                    "from  epqop.ih_ca_activi_conte a,\n" +
                    "      epqop.if_ca_tipo_activid b,\n" +
                    "      epqop.ih_ca_contenedores c\n" +
                    "where  a.codigo_actividad = b.codigo_actividad\n" +
                    "and    a.identifica_contene = c.identifica_contene\n" +
                    "and    a.correla_contene = c.correla_contene\n" +
                    "and    a.codigo_actividad = 38\n" +
                    "and    c.fecha_ingreso2 >= TO_DATE('"+sFecha_inicial+"','DD/MM/YYYY') "+
                    "and    c.fecha_ingreso2 <= TO_DATE('"+sFecha_final+"','DD/MM/YYYY') "+
                    "group by c.naviera\n" +
                    ")\n" +
                    "group by naviera\n" +
                    " order by sum(TOTAL)";
            
            smt = conn.prepareStatement(sql);
            rs = smt.executeQuery();

            while (rs.next()) {
                GraficaRampasMd temp = new GraficaRampasMd();

                temp.setSumarampas(rs.getString(1));
                temp.setRampas(rs.getString(2));

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
        List<GraficaRampasMd> lstDatos = new ArrayList<GraficaRampasMd>();

        SimplePieModel model = new SimplePieModel();
        PreparedStatement smt = null;
        Connection conn;
        Conexion conex = new Conexion();
        conn = conex.getConnection();
        String sql = null;
        ResultSet result2 = null;
        try {

            sql = "select sum(TOTAL), naviera\n" +
                    "from(\n" +
                    "select count(a.codigo_actividad)as TOTAL, c.naviera\n" +
                    "from  epqop.if_ca_activi_conte a,\n" +
                    "      epqop.if_ca_tipo_activid b,\n" +
                    "      epqop.if_ca_contenedores c\n" +
                    "where  a.codigo_actividad = b.codigo_actividad\n" +
                    "and    a.identifica_contene = c.identifica_contene\n" +
                    "and    a.codigo_actividad = 38\n" +
                    " and   c.fecha_ingreso2 >= TO_DATE('"+sFecha_inicial+"','DD/MM/YYYY') "+
                    " and   c.fecha_ingreso2 <= TO_DATE('"+sFecha_final+"','DD/MM/YYYY') "+
                    "group by c.naviera\n" +
                    "union all\n" +
                    "select count(a.codigo_actividad)as TOTAL,c.naviera\n" +
                    "from  epqop.ih_ca_activi_conte a,\n" +
                    "      epqop.if_ca_tipo_activid b,\n" +
                    "      epqop.ih_ca_contenedores c\n" +
                    "where  a.codigo_actividad = b.codigo_actividad\n" +
                    "and    a.identifica_contene = c.identifica_contene\n" +
                    "and    a.correla_contene = c.correla_contene\n" +
                    "and    a.codigo_actividad = 38\n" +
                    " and   c.fecha_ingreso2 >= TO_DATE('"+sFecha_inicial+"','DD/MM/YYYY') "+
                    " and   c.fecha_ingreso2 <= TO_DATE('"+sFecha_final+"','DD/MM/YYYY') "+
                    "group by c.naviera\n" +
                    ")\n" +
                    "group by naviera\n" +
                    " order by sum(TOTAL)";

            smt = conn.prepareStatement(sql);
            result2 = smt.executeQuery();

            while (result2.next()) {

                GraficaRampasMd temp = new GraficaRampasMd();

                temp.setSumarampas(result2.getString(1));
                temp.setRampas(result2.getString(2));

                lstDatos.add(temp);
            }

            for (int i = 0; i < lstDatos.size(); i++) {
                model.setValue(lstDatos.get(i).getRampas(), Integer.parseInt(lstDatos.get(i).getSumarampas()));
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

