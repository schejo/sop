package DAL;

import Conexion.Conexion;
import MD.GraficaContenedoresactividadesMd;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.zul.SimplePieModel;

public class GraficaContenedoresactividadesDal {

    public List<GraficaContenedoresactividadesMd> Grafica1(String sFecha_inicial, String sFecha_final) throws SQLException {
        List<GraficaContenedoresactividadesMd> lstDatos = new ArrayList<GraficaContenedoresactividadesMd>();

        PreparedStatement smt = null;
        Connection conn;
        Conexion conex = new Conexion();
        conn = conex.getConnection();
        String sql = null;
        ResultSet rs = null;

        try {

            sql = "select sum(TOTALIMP),linea\n"
                    + "from(\n"
                    + "select count(a.codigo_actividad)as TOTALIMP,  b.linea\n"
                    + "from  epqop.if_ca_activi_conte a, \n"
                    + "      epqop.if_ca_contenedores b\n"
                    + "where  a.identifica_contene = b.identifica_contene \n"
                    + "and  a.codigo_actividad in (1,13)\n"
                    + "and  b.fecha_ingreso2 >'01/07/2020'\n"
                    + "and  b.fecha_ingreso2 <'30/12/2020'\n"
                    + "group by b.linea\n"
                    + "union all\n"
                    + "select count(a.codigo_actividad)as TOTALIMP, b.linea\n"
                    + "from  epqop.ih_ca_activi_conte a, \n"
                    + "      epqop.ih_ca_contenedores b\n"
                    + "where  a.identifica_contene = b.identifica_contene \n"
                    + "and    a.correla_contene = b.correla_contene\n"
                    + "and  a.codigo_actividad in (1,13)\n"
                    + "and  b.fecha_ingreso2 >'01/07/2020'\n"
                    + "and  b.fecha_ingreso2 <'30/12/2020'\n"
                    + "group by b.linea\n"
                    + ")\n"
                    + "group by linea\n" 
                    + "order by linea" ;
                                      
            smt = conn.prepareStatement(sql);
            rs = smt.executeQuery();

            while (rs.next()) {
                GraficaContenedoresactividadesMd temp = new GraficaContenedoresactividadesMd();

                temp.setSumacnteneactividades(rs.getString(1));
                temp.setConteneactividad(rs.getString(2));

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
        List<GraficaContenedoresactividadesMd> lstDatos = new ArrayList<GraficaContenedoresactividadesMd>();

        SimplePieModel model = new SimplePieModel();
        PreparedStatement smt = null;
        Connection conn;
        Conexion conex = new Conexion();
        conn = conex.getConnection();
        String sql = null;
        ResultSet result2 = null;
        try {

            sql = "select sum(TOTALIMP),linea\n"
                    + "from(\n"
                    + "select count(a.codigo_actividad)as TOTALIMP,  b.linea\n"
                    + "from  epqop.if_ca_activi_conte a, \n"
                    + "      epqop.if_ca_contenedores b\n"
                    + "where  a.identifica_contene = b.identifica_contene \n"
                    + "and  a.codigo_actividad in (1,13)\n"
                    + "and  b.fecha_ingreso2 >'01/07/2020'\n"
                    + "and  b.fecha_ingreso2 <'30/12/2020'\n"
                    + "group by b.linea\n"
                    + "union all\n"
                    + "select count(a.codigo_actividad)as TOTALIMP, b.linea\n"
                    + "from  epqop.ih_ca_activi_conte a, \n"
                    + "      epqop.ih_ca_contenedores b\n"
                    + "where  a.identifica_contene = b.identifica_contene \n"
                    + "and    a.correla_contene = b.correla_contene\n"
                    + "and  a.codigo_actividad in (1,13)\n"
                    + "and  b.fecha_ingreso2 >'01/07/2020'\n"
                    + "and  b.fecha_ingreso2 <'30/12/2020'\n"
                    + "group by b.linea\n"
                    + ")\n"
                    + "group by linea\n" 
                    + "order by linea" ;
            
            smt = conn.prepareStatement(sql);
            result2 = smt.executeQuery();

            while (result2.next()) {

                GraficaContenedoresactividadesMd temp = new GraficaContenedoresactividadesMd();

                temp.setSumacnteneactividades(result2.getString(1));
                temp.setConteneactividad(result2.getString(2));

                lstDatos.add(temp);
            }

            for (int i = 0; i < lstDatos.size(); i++) {
                model.setValue(lstDatos.get(i).getConteneactividad(), Integer.parseInt(lstDatos.get(i).getSumacnteneactividades()));
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
