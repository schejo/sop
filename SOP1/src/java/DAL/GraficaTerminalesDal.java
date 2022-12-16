package DAL;


import Conexion.Conexion;
import MD.GraficaTerminalesMd;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.zul.SimplePieModel;

public class GraficaTerminalesDal {
      
 public List<GraficaTerminalesMd> Grafica1(String sFecha_inicial, String sFecha_final) throws SQLException {
        List<GraficaTerminalesMd> lstDatos = new ArrayList<GraficaTerminalesMd>();

        PreparedStatement smt = null;
        Connection conn;
        Conexion conex = new Conexion();
        conn = conex.getConnection();
        String sql = null;
        ResultSet rs = null;
        
        try {
            
            sql = " select count(b.tipo_terminal) as TOTAL_TERMINAL," 
                 + " b.tipo_terminal as TERMINAL" 
                 + " from epqop.if_bq_arribos a," 
                 + " epqop.if_bq_atracaderos b" 
                 + " where a.num_atracadero1= b.num_atracadero1"
                 + " and   a.fecha_atraque >= TO_DATE('"+sFecha_inicial+"','DD/MM/YYYY') " 
                 + " and   a.fecha_atraque <= TO_DATE('"+sFecha_final+"','DD/MM/YYYY') "
                 + " and   a.status3  <> 'V'"
                 + " and   b.num_atracadero1 not in (19,20,23,24,26,27,32,33,34)"
                 + " group by b.tipo_terminal"
                 +"  order by total_terminal";
            
            smt = conn.prepareStatement(sql);
            rs  = smt.executeQuery();

            while (rs.next()) {
                GraficaTerminalesMd temp = new GraficaTerminalesMd();
                
                temp.setSumabuques(rs.getString(1));
                temp.setBuques(rs.getString(2));
                
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
        List<GraficaTerminalesMd> lstDatos = new ArrayList<GraficaTerminalesMd>();
        
        SimplePieModel model = new SimplePieModel();
        PreparedStatement smt = null;
        Connection conn;
        Conexion conex = new Conexion();
        conn = conex.getConnection();
        String sql = null;
        ResultSet result2 = null;
        try {
            
              sql = " select count(b.tipo_terminal) as TOTAL_TERMINAL," 
                 + " b.tipo_terminal as TERMINAL" 
                 + " from epqop.if_bq_arribos a," 
                 + " epqop.if_bq_atracaderos b" 
                 + " where a.num_atracadero1= b.num_atracadero1"
                 + " and   a.fecha_atraque >= TO_DATE('"+sFecha_inicial+"','DD/MM/YYYY') " 
                 + " and   a.fecha_atraque <= TO_DATE('"+sFecha_final+"','DD/MM/YYYY') "
                 + " and   a.status3  <> 'V'"
                 + " and   b.num_atracadero1 not in (19,20,23,24,26,27,32,33,34)"
                 + " group by b.tipo_terminal"
                 + " order by TOTAL_TERMINAL";
             
            smt = conn.prepareStatement(sql);
            result2 = smt.executeQuery();

            while (result2.next()) {
        
                GraficaTerminalesMd temp = new GraficaTerminalesMd();
                
                temp.setSumabuques(result2.getString(1));
                temp.setBuques(result2.getString(2));
                
                lstDatos.add(temp);
            }

            for (int i = 0; i < lstDatos.size(); i++) {
                model.setValue(lstDatos.get(i).getBuques(), Integer.parseInt(lstDatos.get(i).getSumabuques()));
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
