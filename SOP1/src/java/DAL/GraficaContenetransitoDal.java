package DAL;

import Conexion.Conexion;
import MD.GraficaContenetransitoMd;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.zul.SimplePieModel;

public class GraficaContenetransitoDal {

    public List<GraficaContenetransitoMd> Grafica1(String sFecha_inicial, String sFecha_final) throws SQLException {
        List<GraficaContenetransitoMd> lstDatos = new ArrayList<GraficaContenetransitoMd>();

        PreparedStatement smt = null;
        Connection conn;
        Conexion conex = new Conexion();
        conn = conex.getConnection();
        String sql = null;
        ResultSet rs = null;

        try {

            sql = "select sum(TOTAL),linea\n" +
                   "from (\n" +
                   "select count(b.transito) as TOTAL, b.linea          \n" +
                   "from    epqop.ih_ca_activi_conte a,\n" +
                   "        epqop.ih_ca_contenedores b      \n" +
                   "where   a.identifica_contene = b.identifica_contene\n" +
                   "and     a.codigo_actividad in (1,13)\n" +
                   "and     b.transito = 'S'\n" +
                /* "and     a.fecha_inicial2 >= '01/01/2020'\n" +
                   "and     a.fecha_inicial2 <= '06/12/2020'\n" +*/
                   "and a.fecha_inicial2 >= TO_DATE('"+sFecha_inicial+"','DD/MM/YYYY') "+
                   "and  a.fecha_inicial2 <= TO_DATE('"+sFecha_final+"','DD/MM/YYYY') "+
                   "group by b.linea\n" +
                   "union all\n" +
                   "select count(b.transito) as TOTAL, b.linea          \n" +
                   "from    epqop.ih_ca_activi_conte a,\n" +
                   "        epqop.ih_ca_contenedores b      \n" +
                   "where   a.identifica_contene = b.identifica_contene\n" +
                   "and     a.correla_contene = b.correla_contene\n" +
                   "and     a.codigo_actividad in (1,13)\n" +
                   "and     b.transito = 'S'\n" +
                 /*"and     a.fecha_inicial2 >= '01/01/2020'\n" +
                   "and     a.fecha_inicial2 <= '06/12/2020'\n" +*/
                   "and a.fecha_inicial2 >= TO_DATE('"+sFecha_inicial+"','DD/MM/YYYY') "+
                   "and  a.fecha_inicial2 <= TO_DATE('"+sFecha_final+"','DD/MM/YYYY') "+
                   "group by b.linea)\n" +
                   "group by linea\n" +
                   "order by sum(TOTAL)\n";
                  
                    
            smt = conn.prepareStatement(sql);
            rs = smt.executeQuery();

            while (rs.next()) {
                GraficaContenetransitoMd temp = new GraficaContenetransitoMd();

                temp.setSumacontenetransito(rs.getString(1));
                temp.setContenetransito(rs.getString(2));

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
        List<GraficaContenetransitoMd> lstDatos = new ArrayList<GraficaContenetransitoMd>();

        SimplePieModel model = new SimplePieModel();
        PreparedStatement smt = null;
        Connection conn;
        Conexion conex = new Conexion();
        conn = conex.getConnection();
        String sql = null;
        ResultSet result2 = null;
        try {

            sql = "select sum(TOTAL),linea\n" +
                   "from (\n" +
                   "select count(b.transito) as TOTAL, b.linea          \n" +
                   "from    epqop.ih_ca_activi_conte a,\n" +
                   "        epqop.ih_ca_contenedores b      \n" +
                   "where   a.identifica_contene = b.identifica_contene\n" +
                   "and     a.codigo_actividad in (1,13)\n" +
                   "and     b.transito = 'S'\n" +
                   /*"and   a.fecha_inicial2 >= '01/01/2020'\n" +
                   "and     a.fecha_inicial2 <= '06/12/2020'\n" +*/
                   "and     a.fecha_inicial2 >= TO_DATE('"+sFecha_inicial+"','DD/MM/YYYY') "+
                   "and     a.fecha_inicial2 <= TO_DATE('"+sFecha_final+"','DD/MM/YYYY') "+
                   "group by b.linea\n" +
                   "union all\n" +
                   "select count(b.transito) as TOTAL, b.linea          \n" +
                   "from    epqop.ih_ca_activi_conte a,\n" +
                   "        epqop.ih_ca_contenedores b      \n" +
                   "where   a.identifica_contene = b.identifica_contene\n" +
                   "and     a.correla_contene = b.correla_contene\n" +
                   "and     a.codigo_actividad in (1,13)\n" +
                   "and     b.transito = 'S'\n" +
                 /*"and     a.fecha_inicial2 >= '01/01/2020'\n" +
                   "and     a.fecha_inicial2 <= '06/12/2020'\n" +*/
                   "and     a.fecha_inicial2 >= TO_DATE('"+sFecha_inicial+"','DD/MM/YYYY') "+
                   "and     a.fecha_inicial2 <= TO_DATE('"+sFecha_final+"','DD/MM/YYYY') "+
                   "group by b.linea)\n" +
                   "group by linea\n" +
                   "order by sum(TOTAL)\n";

            smt = conn.prepareStatement(sql);
            result2 = smt.executeQuery();

            while (result2.next()) {

                GraficaContenetransitoMd temp = new GraficaContenetransitoMd();

                temp.setSumacontenetransito(result2.getString(1));
                temp.setContenetransito(result2.getString(2));

                lstDatos.add(temp);
            }

            for (int i = 0; i < lstDatos.size(); i++) {
                model.setValue(lstDatos.get(i).getContenetransito(), Integer.parseInt(lstDatos.get(i).getSumacontenetransito()));
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


