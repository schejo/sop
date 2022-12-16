package DAL;


import Conexion.Conexion;
import MD.GraficaToneladasMuelleMd;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.zul.SimplePieModel;

public class GraficaToneladasMuelleDal {
      
 public List<GraficaToneladasMuelleMd> Grafica1(String sFecha_inicial, String sFecha_final) throws SQLException {
        List<GraficaToneladasMuelleMd> lstDatos = new ArrayList<GraficaToneladasMuelleMd>();

        PreparedStatement smt = null;
        Connection conn;
        Conexion conex = new Conexion();
        conn = conex.getConnection();
        String sql = null;
        ResultSet rs = null;
        
        try {
            
            sql = "select ANIO, SUM(PESO) "
            + " FROM ( "
            + " select To_char(a.fecha_ingreso,'RRRR') AS ANIO, Sum(a.peso_bruto_recibid) AS PESO "
            + " from epqop.if_ca_carga_impor a, epqop.if_bq_arribos b,  epqop.if_bq_tipo_arribo c "
            + " where a.ano_arribo = b.ano_arribo "
            + " and   a.num_arribo = b.num_arribo "
            + " and   b.tipo_buque = c.tipo_buque "
            + " and   c.tipo_buque in ( 2,3,4,5) "
            + " and   b.num_atracadero1 in (1,2,3,4) "
            + " and   a.peso_bruto_recibid > 0 "
            + " and   fecha_ingreso >= TO_DATE('"+sFecha_inicial+"','DD/MM/YYYY') "
            + " and   fecha_ingreso <= TO_DATE('"+sFecha_final+"','DD/MM/YYYY') "
            + " Group by To_char(a.fecha_ingreso,'RRRR') "
            + " union all "
            + " select To_char(a.fecha_ingreso,'RRRR') AS ANIO, Sum(a.peso_bruto_recibid) AS PESO "
            + " from epqop.ih_ca_carga_impor a, epqop.if_bq_arribos b,  epqop.if_bq_tipo_arribo c "
            + " where a.ano_arribo = b.ano_arribo "
            + " and   a.num_arribo = b.num_arribo "
            + " and   b.tipo_buque = c.tipo_buque "
            + " and   c.tipo_buque in ( 2,3,4,5) "
            + " and   b.num_atracadero1 in (1,2,3,4) "
            + " and   a.peso_bruto_recibid > 0 "
            + " and   fecha_ingreso >= TO_DATE('"+sFecha_inicial+"','DD/MM/YYYY') "
            + " and   fecha_ingreso <= TO_DATE('"+sFecha_final+"','DD/MM/YYYY') "
            + " Group by To_char(a.fecha_ingreso,'RRRR')"
            + " ) "
            + " group by ANIO"
            + " order by 1";

            smt = conn.prepareStatement(sql);
            rs  = smt.executeQuery();

            while (rs.next()) {
                GraficaToneladasMuelleMd temp = new GraficaToneladasMuelleMd();
                
                temp.setAnio(rs.getString(1));
                temp.setPeso(rs.getInt(2));
                
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
        List<GraficaToneladasMuelleMd> lstDatos = new ArrayList<GraficaToneladasMuelleMd>();
        
        SimplePieModel model = new SimplePieModel();
        PreparedStatement smt = null;
        Connection conn;
        Conexion conex = new Conexion();
        conn = conex.getConnection();
        String sql = null;
        ResultSet result2 = null;
        try {
            
              sql ="select ANIO, SUM(PESO) "
            + " FROM ( "
            + " select To_char(a.fecha_ingreso,'RRRR') AS ANIO, Sum(a.peso_bruto_recibid) AS PESO "
            + " from epqop.if_ca_carga_impor a, epqop.if_bq_arribos b,  epqop.if_bq_tipo_arribo c "
            + " where a.ano_arribo = b.ano_arribo "
            + " and   a.num_arribo = b.num_arribo "
            + " and   b.tipo_buque = c.tipo_buque "
            + " and   c.tipo_buque in ( 2,3,4,5) "
            + " and   b.num_atracadero1 in (1,2,3,4) "
            + " and   a.peso_bruto_recibid > 0 "
            + " and   fecha_ingreso >= TO_DATE('"+sFecha_inicial+"','DD/MM/YYYY') "
            + " and   fecha_ingreso <= TO_DATE('"+sFecha_final+"','DD/MM/YYYY') "
            + " Group by To_char(a.fecha_ingreso,'RRRR') "
            + " union all "
            + " select To_char(a.fecha_ingreso,'RRRR') AS ANIO, Sum(a.peso_bruto_recibid) AS PESO "
            + " from epqop.ih_ca_carga_impor a, epqop.if_bq_arribos b,  epqop.if_bq_tipo_arribo c "
            + " where a.ano_arribo = b.ano_arribo "
            + " and   a.num_arribo = b.num_arribo "
            + " and   b.tipo_buque = c.tipo_buque "
            + " and   c.tipo_buque in ( 2,3,4,5) "
            + " and   b.num_atracadero1 in (1,2,3,4) "
            + " and   a.peso_bruto_recibid > 0 "
            + " and   fecha_ingreso >= TO_DATE('"+sFecha_inicial+"','DD/MM/YYYY') "
            + " and   fecha_ingreso <= TO_DATE('"+sFecha_final+"','DD/MM/YYYY') "
            + " Group by To_char(a.fecha_ingreso,'RRRR')"
            + " ) "
            + " group by ANIO"
            + " order by 1";
             
            smt = conn.prepareStatement(sql);
            result2 = smt.executeQuery();

            while (result2.next()) {
        
                GraficaToneladasMuelleMd temp = new GraficaToneladasMuelleMd();
                
                temp.setAnio(result2.getString(1));
                temp.setPeso(result2.getInt(2));
                
                lstDatos.add(temp);
            }

            for (int i = 0; i < lstDatos.size(); i++) {
               // model.setValue(lstDatos.get(i).getAnio(), Integer.parseInt(lstDatos.get(i).getPeso()));
                model.setValue(lstDatos.get(i).getAnio(),lstDatos.get(i).getPeso());
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
