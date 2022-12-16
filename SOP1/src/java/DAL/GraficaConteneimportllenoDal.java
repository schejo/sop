package DAL;

import Conexion.Conexion;
import MD.GraficaConteneimportllenoMd;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.zul.SimplePieModel;

public class GraficaConteneimportllenoDal {

    public List<GraficaConteneimportllenoMd> Grafica1(String sFecha_inicial, String sFecha_final) throws SQLException {
        List<GraficaConteneimportllenoMd> lstDatos = new ArrayList<GraficaConteneimportllenoMd>();

        PreparedStatement smt = null;
        Connection conn;
        Conexion conex = new Conexion();
        conn = conex.getConnection();
        String sql = null;
        ResultSet rs = null;

        try {

            sql = " select sum(TOTAL), linea\n" +
                   "from(\n" +
                   "select count(a.vacio_lleno)as TOTAL, b.linea\n" +
                   "from  epqop.if_ca_activi_conte a,\n" +
                   "      epqop.if_ca_contenedores b\n" +
                   "where  a.identifica_contene = b.identifica_contene\n" +
                   "and    a.codigo_actividad in (1,13)\n" +
                   "and    a.fecha_inicial2 >= TO_DATE('"+sFecha_inicial+"','DD/MM/YYYY') "+
                   "and    a.fecha_inicial2 <= TO_DATE('"+sFecha_final+"','DD/MM/YYYY') "+
                   "and    a.vacio_lleno = 5\n" +
                   "group by b.linea\n" +
                   "union all\n" +
                   "select count(a.vacio_lleno)as TOTAL, b.linea\n" +
                   "from  epqop.ih_ca_activi_conte a,\n" +
                   "      epqop.ih_ca_contenedores b\n" +
                   "where  a.identifica_contene = b.identifica_contene\n" +
                   "and    a.correla_contene = b.correla_contene\n" +
                   "and    a.codigo_actividad in (1,13)\n" +
                   "and    a.fecha_inicial2 >= TO_DATE('"+sFecha_inicial+"','DD/MM/YYYY') "+
                   "and    a.fecha_inicial2 <= TO_DATE('"+sFecha_final+"','DD/MM/YYYY') "+ "and     a.vacio_lleno = 5\n" +
                   "group by b.linea\n" +
                   ")\n" +
                   "group by linea\n" +
                   "order by sum(TOTAL)";
                    
            smt = conn.prepareStatement(sql);
            rs = smt.executeQuery();

            while (rs.next()) {
                GraficaConteneimportllenoMd temp = new GraficaConteneimportllenoMd();

                temp.setSumcontenelleno(rs.getString(1));
                temp.setContenelleno(rs.getString(2));

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
        List<GraficaConteneimportllenoMd> lstDatos = new ArrayList<GraficaConteneimportllenoMd>();

        SimplePieModel model = new SimplePieModel();
        PreparedStatement smt = null;
        Connection conn;
        Conexion conex = new Conexion();
        conn = conex.getConnection();
        String sql = null;
        ResultSet result2 = null;
        try {

            sql = " select sum(TOTAL), linea\n" +
                   "from(\n" +
                   "select count(a.vacio_lleno)as TOTAL, b.linea\n" +
                   "from  epqop.if_ca_activi_conte a,\n" +
                   "      epqop.if_ca_contenedores b\n" +
                   "where  a.identifica_contene = b.identifica_contene\n" +
                   "and    a.codigo_actividad in (1,13)\n" +
                   "and    a.fecha_inicial2 >= TO_DATE('"+sFecha_inicial+"','DD/MM/YYYY') "+
                   "and    a.fecha_inicial2 <= TO_DATE('"+sFecha_final+"','DD/MM/YYYY') "+
                   "and    a.vacio_lleno = 5\n" +
                   "group by b.linea\n" +
                   "union all\n" +
                   "select count(a.vacio_lleno)as TOTAL, b.linea\n" +
                   "from  epqop.ih_ca_activi_conte a,\n" +
                   "      epqop.ih_ca_contenedores b\n" +
                   "where  a.identifica_contene = b.identifica_contene\n" +
                   "and    a.correla_contene = b.correla_contene\n" +
                   "and    a.codigo_actividad in (1,13)\n" +
                   "and    a.fecha_inicial2 >= TO_DATE('"+sFecha_inicial+"','DD/MM/YYYY') "+
                   "and    a.fecha_inicial2 <= TO_DATE('"+sFecha_final+"','DD/MM/YYYY') "+
                   "and    a.vacio_lleno = 5\n" +
                   "group by b.linea\n" +
                   ")\n" +
                   "group by linea\n" +
                   "order by sum(TOTAL)";
            
            smt = conn.prepareStatement(sql);
            result2 = smt.executeQuery();

            while (result2.next()) {

                GraficaConteneimportllenoMd temp = new GraficaConteneimportllenoMd();

                temp.setSumcontenelleno(result2.getString(1));
                temp.setContenelleno(result2.getString(2));

                lstDatos.add(temp);
            }

            for (int i = 0; i < lstDatos.size(); i++) {
                model.setValue(lstDatos.get(i).getContenelleno(), Integer.parseInt(lstDatos.get(i).getSumcontenelleno()));
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


