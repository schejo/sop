package DAL;


import Conexion.Conexion;
import MD.GraficaConteneRefrimesMd;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.zul.SimplePieModel;

public class GraficaConteneRefrimesDal {
      
 public List<GraficaConteneRefrimesMd> Grafica1(String sFecha_inicial, String sFecha_final) throws SQLException {
        List<GraficaConteneRefrimesMd> lstDatos = new ArrayList<GraficaConteneRefrimesMd>();

        PreparedStatement smt = null;
        Connection conn;
        Conexion conex = new Conexion();
        conn = conex.getConnection();
        String sql = null;
        ResultSet rs = null;
        
        try {
            
            sql = " select MES, SUM(CONTADOR)\n" +
"                FROM (\n" +
"                select To_char(a.fecha_inicial2,'MM') AS MES,  count(1) AS CONTADOR\n" +
"                from epqop.if_ca_activi_conte a\n" +
"                where a.codigo_actividad in  (12)\n" +
"                and a.fecha_inicial2 >= TO_DATE('"+sFecha_inicial+"','DD/MM/YYYY')\n" +
"                and a.fecha_inicial2 <= TO_DATE('"+sFecha_final+"','DD/MM/YYYY')\n" +
"                Group by To_char(a.fecha_inicial2,'MM'), a.codigo_actividad\n" +
"                union all\n" +
"                select To_char(a.fecha_inicial2,'MM') AS MES,  count(1) AS CONTADOR\n" +
"                from epqop.ih_ca_activi_conte a\n" +
"                where a.codigo_actividad in  (12)\n" +
"                and a.fecha_inicial2 >= TO_DATE('"+sFecha_inicial+"','DD/MM/YYYY')\n" +
"                and a.fecha_inicial2 <= TO_DATE('"+sFecha_final+"','DD/MM/YYYY')\n" +
"                Group by To_char(a.fecha_inicial2,'MM'), a.codigo_actividad\n" +
"                )\n" +
"                group by MES\n" +
"                order by 1 " ;
                    
            smt = conn.prepareStatement(sql);
            rs  = smt.executeQuery();

            while (rs.next()) {
                GraficaConteneRefrimesMd temp = new GraficaConteneRefrimesMd();
                
                temp.setMes(rs.getString(1));
                temp.setCantidad(rs.getString(2));
                
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
        List<GraficaConteneRefrimesMd> lstDatos = new ArrayList<GraficaConteneRefrimesMd>();
        
        SimplePieModel model = new SimplePieModel();
        PreparedStatement smt = null;
        Connection conn;
        Conexion conex = new Conexion();
        conn = conex.getConnection();
        String sql = null;
        ResultSet result2 = null;
        try {
            
               sql = " select MES, SUM(CONTADOR)\n" +
"                FROM (\n" +
"                select To_char(a.fecha_inicial2,'MM') AS MES,  count(1) AS CONTADOR\n" +
"                from epqop.if_ca_activi_conte a\n" +
"                where a.codigo_actividad in  (12)\n" +
"                and a.fecha_inicial2 >= TO_DATE('"+sFecha_inicial+"','DD/MM/YYYY')\n" +
"                and a.fecha_inicial2 <= TO_DATE('"+sFecha_final+"','DD/MM/YYYY')\n" +
"                Group by To_char(a.fecha_inicial2,'MM'), a.codigo_actividad\n" +
"                union all\n" +
"                select To_char(a.fecha_inicial2,'MM') AS MES,  count(1) AS CONTADOR\n" +
"                from epqop.ih_ca_activi_conte a\n" +
"                where a.codigo_actividad in  (12)\n" +
"                and a.fecha_inicial2 >= TO_DATE('"+sFecha_inicial+"','DD/MM/YYYY')\n" +
"                and a.fecha_inicial2 <= TO_DATE('"+sFecha_final+"','DD/MM/YYYY')\n" +
"                Group by To_char(a.fecha_inicial2,'MM'), a.codigo_actividad\n" +
"                )\n" +
"                group by MES\n" +
"                order by 1 " ;
                      
            smt = conn.prepareStatement(sql);
            result2 = smt.executeQuery();

            while (result2.next()) {
        
                GraficaConteneRefrimesMd temp = new GraficaConteneRefrimesMd();
                
                temp.setMes(result2.getString(1));
                temp.setCantidad(result2.getString(2));
                
                lstDatos.add(temp);
            }

            for (int i = 0; i < lstDatos.size(); i++) {
               // model.setValue(lstDatos.get(i).getAnio(), Integer.parseInt(lstDatos.get(i).getPeso()));
                model.setValue(lstDatos.get(i).getMes(), Integer.parseInt(lstDatos.get(i).getCantidad()));
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

