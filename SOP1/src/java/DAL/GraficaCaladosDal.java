package DAL;


import Conexion.Conexion;
import DAL.GraficaCaladosDal;
import ctrl.GraficaCaladosCtrl;
import MD.GraficaCaladosMd;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.zul.SimplePieModel;

public class GraficaCaladosDal {
      
 public List<GraficaCaladosMd> Grafica1(String sFecha_inicial, String sFecha_final) throws SQLException {
        List<GraficaCaladosMd> lstDatos = new ArrayList<GraficaCaladosMd>();

        PreparedStatement smt = null;
        Connection conn;
        Conexion conex = new Conexion();
        conn = conex.getConnection();
        String sql = null;
        ResultSet rs = null;
        
        try {

            
            sql = " select count(c.tipo_terminal) as TOTAL_BUQUES,"
                + " c.tipo_terminal as TERMINAL"
                + " from epqop.if_bq_buques a, "
                + " epqop.if_bq_arribos b,"
                + " epqop.if_bq_atracaderos c"
                + " where a.buque = b.buque"
                + " and   b.num_atracadero1 = c.num_atracadero1"
                + " and   c.num_atracadero1 not in (26,23,24,19,20,27,32,33,34)"
                + " and   a.calado_maximo >11"
                + " and  b.fecha_atraque >= TO_DATE('"+sFecha_inicial+"','DD/MM/YYYY') " 
                + " and  b.fecha_atraque <= TO_DATE('"+sFecha_final+"','DD/MM/YYYY') "
                + " group by c.tipo_terminal"
                + " order by TOTAL_BUQUES";

            
            smt = conn.prepareStatement(sql);
            rs  = smt.executeQuery();

            while (rs.next()) {
                GraficaCaladosMd temp = new GraficaCaladosMd();
                
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
        List<GraficaCaladosMd> lstDatos = new ArrayList<GraficaCaladosMd>();
        
        SimplePieModel model = new SimplePieModel();
        PreparedStatement smt = null;
        Connection conn;
        Conexion conex = new Conexion();
        conn = conex.getConnection();
        String sql = null;
        ResultSet result2 = null;
        try {

            
             sql = " select count(c.tipo_terminal) as TOTAL_BUQUES,"
                + " c.tipo_terminal as TERMINAL"
                + " from epqop.if_bq_buques a, "
                + " epqop.if_bq_arribos b,"
                + " epqop.if_bq_atracaderos c"
                + " where a.buque = b.buque"
                + " and   b.num_atracadero1 = c.num_atracadero1"
                + " and   c.num_atracadero1 not in (26,23,24,19,20,27,32,33,34)"
                + " and   a.calado_maximo >11"
                + " and  b.fecha_atraque >= TO_DATE('"+sFecha_inicial+"','DD/MM/YYYY') " 
                + " and  b.fecha_atraque <= TO_DATE('"+sFecha_final+"','DD/MM/YYYY') "
                + " group by c.tipo_terminal"
                + " order by TOTAL_BUQUES";

             
            smt = conn.prepareStatement(sql);
            result2 = smt.executeQuery();

            while (result2.next()) {
        
                GraficaCaladosMd temp = new GraficaCaladosMd();
                
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
