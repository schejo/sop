package DAL;

import Conexion.Conexion;
import MD.GraficaConteneviaMd;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.zul.SimplePieModel;

public class GraficaConteneviaDal {

    public List<GraficaConteneviaMd> Grafica1(String sFecha_inicial, String sFecha_final) throws SQLException {
        List<GraficaConteneviaMd> lstDatos = new ArrayList<GraficaConteneviaMd>();

        PreparedStatement smt = null;
        Connection conn;
        Conexion conex = new Conexion();
        conn = conex.getConnection();
        String sql = null;
        ResultSet rs = null;

        try {

            sql = "select sum(TOTAL),linea, VIA_DIRECTA\n" +
                   "from (\n" +
                   "select count(a.via_directa) as TOTAL, b.linea, decode(a.via_directa,'S','DIRECTA','N','INDIRECTA', 'P','INTERMEDIA') as VIA_DIRECTA          \n" +
                   "from    epqop.if_ca_activi_conte a,\n" +
                   "        epqop.if_ca_contenedores b\n" +
                   "where   a.identifica_contene = b.identifica_contene\n" +
                   "and     a.codigo_actividad in (1,13)\n" +
                   "and     b.linea is not null\n" +
                   "and     a.via_directa = 'S' \n" +
                   "and     a.fecha_inicial2 >= TO_DATE('"+sFecha_inicial+"','DD/MM/YYYY') "+
                   "and     a.fecha_inicial2 <= TO_DATE('"+sFecha_final+"','DD/MM/YYYY') "+
                   "group by b.linea, a.via_directa\n" +
                   "union all\n" +
                   "select count(a.via_directa) as TOTAL, b.linea, decode(a.via_directa,'S','DIRECTA','N','INDIRECTA', 'P','INTERMEDIA') as VIA_DIRECTA          \n" +
                   "from    epqop.ih_ca_activi_conte a,\n" +
                   "        epqop.ih_ca_contenedores b\n" +
                   "where   a.identifica_contene = b.identifica_contene\n" +
                   "and     a.correla_contene = b.correla_contene\n" +
                   "and     a.codigo_actividad in (1,13)\n" +
                   "and     b.linea is not null\n" +
                   "and     a.via_directa = 'S' \n" +
                   "and     a.fecha_inicial2 >= TO_DATE('"+sFecha_inicial+"','DD/MM/YYYY') "+
                   "and     a.fecha_inicial2 <= TO_DATE('"+sFecha_final+"','DD/MM/YYYY') "+
                   "group by b.linea, a.via_directa\n" +
                    ")\n" +
                    "group by linea, via_directa\n" +
                    "order by sum(TOTAL)";
                   
                    
            
            smt = conn.prepareStatement(sql);
            rs = smt.executeQuery();

            while (rs.next()) {
                GraficaConteneviaMd temp = new GraficaConteneviaMd();

                temp.setSumacontenevia(rs.getString(1));
                temp.setContenevia(rs.getString(2));

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
        List<GraficaConteneviaMd> lstDatos = new ArrayList<GraficaConteneviaMd>();

        SimplePieModel model = new SimplePieModel();
        PreparedStatement smt = null;
        Connection conn;
        Conexion conex = new Conexion();
        conn = conex.getConnection();
        String sql = null;
        ResultSet result2 = null;
        try {

            sql = " select sum(TOTAL),linea, VIA_DIRECTA\n" +
                   "from (\n" +
                   "select count(a.via_directa) as TOTAL, b.linea, decode(a.via_directa,'S','DIRECTA','N','INDIRECTA', 'P','INTERMEDIA') as VIA_DIRECTA          \n" +
                   "from    epqop.if_ca_activi_conte a,\n" +
                   "        epqop.if_ca_contenedores b\n" +
                   "where   a.identifica_contene = b.identifica_contene\n" +
                   "and     a.codigo_actividad in (1,13)\n" +
                   "and     b.linea is not null\n" +
                   "and     a.via_directa = 'S' \n" +
                   "and     a.fecha_inicial2 >= TO_DATE('"+sFecha_inicial+"','DD/MM/YYYY') "+
                   "and     a.fecha_inicial2 <= TO_DATE('"+sFecha_final+"','DD/MM/YYYY') "+
                   "group by b.linea, a.via_directa\n" +
                   "union all\n" +
                   "select count(a.via_directa) as TOTAL, b.linea, decode(a.via_directa,'S','DIRECTA','N','INDIRECTA', 'P','INTERMEDIA') as VIA_DIRECTA          \n" +
                   "from    epqop.ih_ca_activi_conte a,\n" +
                   "        epqop.ih_ca_contenedores b\n" +
                   "where   a.identifica_contene = b.identifica_contene\n" +
                   "and     a.correla_contene = b.correla_contene\n" +
                   "and     a.codigo_actividad in (1,13)\n" +
                   "and     b.linea is not null\n" +
                   "and     a.via_directa = 'S' \n" +
                   "and     a.fecha_inicial2 >= TO_DATE('"+sFecha_inicial+"','DD/MM/YYYY') "+
                   "and     a.fecha_inicial2 <= TO_DATE('"+sFecha_final+"','DD/MM/YYYY') "+
                   "group by b.linea, a.via_directa\n" +
                   ")\n" +
                   "group by linea, via_directa\n" +
                   "order by sum(TOTAL)";
                  
            smt = conn.prepareStatement(sql);
            result2 = smt.executeQuery();

            while (result2.next()) {

                GraficaConteneviaMd temp = new GraficaConteneviaMd();

                temp.setSumacontenevia(result2.getString(1));
                temp.setContenevia(result2.getString(2));

                lstDatos.add(temp);
            }

            for (int i = 0; i < lstDatos.size(); i++) {
                model.setValue(lstDatos.get(i).getContenevia(), Integer.parseInt(lstDatos.get(i).getSumacontenevia()));
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
