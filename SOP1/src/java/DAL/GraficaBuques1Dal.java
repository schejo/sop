package DAL;


import Conexion.Conexion;
import MD.GraficaBuques1Md;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.zul.SimplePieModel;

public class GraficaBuques1Dal {
      
 public List<GraficaBuques1Md> Grafica1(String sFecha_inicial, String sFecha_final) throws SQLException {
        List<GraficaBuques1Md> lstDatos = new ArrayList<GraficaBuques1Md>();
        System.out.println("Linea 21 Dal");

        PreparedStatement smt = null;
        Connection conn;
        Conexion conex = new Conexion();
        conn = conex.getConnection();
        String sql = null;
        ResultSet rs = null;
        
        try {
            
            sql = " select count(b.nom_tipo_buque) as Total_buques," 
                 + " b.nom_tipo_buque as Buques" 
                 + " from epqop.if_bq_arribos a," 
                 + " epqop.if_bq_tipo_arribo b" 
                 + " where a.tipo_buque = b.tipo_buque"
                 + " and  a.fecha_atraque >= TO_DATE('"+sFecha_inicial+"','DD/MM/YYYY') " 
                 + " and  a.fecha_atraque <= TO_DATE('"+sFecha_final+"','DD/MM/YYYY') "
                 + " and  a.status3  <> 'V'"
                 + " and  b.tipo_buque not in (7,8,9,10,20)"   
                 + " group by b.nom_tipo_buque";
            
            smt = conn.prepareStatement(sql);
            rs  = smt.executeQuery();

            while (rs.next()) {
                GraficaBuques1Md temp = new GraficaBuques1Md();
                
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
        List<GraficaBuques1Md> lstDatos = new ArrayList<GraficaBuques1Md>();
        
        SimplePieModel model = new SimplePieModel();
        PreparedStatement smt = null;
        Connection conn;
        Conexion conex = new Conexion();
        conn = conex.getConnection();
        String sql = null;
        ResultSet result2 = null;
        try {
            
            sql = " select count(b.nom_tipo_buque) as Total_buques," 
                 + " b.nom_tipo_buque as Buques" 
                 + " from epqop.if_bq_arribos a," 
                 + " epqop.if_bq_tipo_arribo b" 
                 + " where a.tipo_buque = b.tipo_buque"
                 + " and  a.fecha_atraque >= TO_DATE('"+sFecha_inicial+"','DD/MM/YYYY') " 
                 + " and  a.fecha_atraque <= TO_DATE('"+sFecha_final+"','DD/MM/YYYY') "
                 + " and  a.status3  <> 'V'"
                 + " and  b.tipo_buque not in (7,8,9,10,20)"   
                 + " group by b.nom_tipo_buque";
            
            smt = conn.prepareStatement(sql);
            result2 = smt.executeQuery();

            while (result2.next()) {
        
                GraficaBuques1Md temp = new GraficaBuques1Md();
                
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
