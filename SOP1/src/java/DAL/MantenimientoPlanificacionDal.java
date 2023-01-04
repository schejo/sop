/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAL;

import Conexion.Conexion;
import MD.MantenimientoPlanificacionMd;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author alfonsoc7905
 */
public class MantenimientoPlanificacionDal {
    
    private Connection conexion = null;
    private Conexion cnn = new Conexion();
    PreparedStatement ps = null;
    Statement st = null;
    ResultSet rs = null;
    
    public List<MantenimientoPlanificacionMd> search (String dua){
        List<MantenimientoPlanificacionMd> alldata = new ArrayList<MantenimientoPlanificacionMd>();
        String query = " select IDPLANIFICACION, "
                + " ANO,"
                + " TIPO_DUA,"
                + " IDEMPRESA,"
                + " MES,"
                + " ENT_RETIENE,"
                + " ESTADO "
                + " FROM EPQ.planificacion "
                + " WHERE TRIM(DUA) = '"+dua+"' ";
        try {
            conexion = cnn.Conexion();
            st = conexion.createStatement();
            rs = st.executeQuery(query);
            MantenimientoPlanificacionMd item = new MantenimientoPlanificacionMd();
            while (rs.next()) {
                item.setIdPlnificacion(rs.getString("IDPLANIFICACION"));
                item.setAnio(rs.getString("ANO"));
                item.setTipo(rs.getString("TIPO_DUA"));
                item.setEmpresa(rs.getString("IDEMPRESA"));
                item.setMes(rs.getString("MES"));
                item.setRetiene(rs.getString("ENT_RETIENE"));
                item.setEstado(rs.getString("ESTADO"));
                alldata.add(item);
            }
            st.close();
            conexion.close();
            cnn.desconectar();
        } catch(SQLException e){ System.out.println("Exception..: "+e.getMessage());}
        return alldata;
    }
    
    public String Updata (String dua, MantenimientoPlanificacionMd data) {
        int v1 = 0;
        String resp = "0";
        String query = "UPDATE EPQ.planificacion SET "
                + " ANO = '"+data.getAnio()+"' ,"
                + " TIPO_DUA = '"+data.getTipo()+"' ,"
                + " IDEMPRESA = '"+data.getMes()+"' ,"
                + " MES = '"+data.getMes()+"' ,"
                + " ENT_RETIENE = '"+data.getRetiene()+"' ,"
                + " ESTADO = '"+data.getEstado()+"' "
                + " WHERE TRIM(DUA) = '"+dua+"' ";
        
        try {
            conexion = cnn.Conexion();
            st = conexion.createStatement();
            v1 = st.executeUpdate(query);
            if (v1 > 0) {
                resp = "1";
            }
            
            st.close();
            conexion.close();
            cnn.desconectar();
            
        } catch(SQLException e){
            resp = e.getMessage();
            System.out.println("Exception Planifica UPDATE..: "+e.getMessage());
        }
        
        return resp;
    }
    
    
    public String Deldata (String dua) {
        String resp = "0";
        String query = " DELETE EPQ.planificacion "
                + " WHERE TRIM(DUA) = ? ";
        
        try {
            conexion = cnn.Conexion();
            ps = conexion.prepareStatement(query);
            ps.setString(1, dua);
            ps.executeUpdate();
            ps.close();
            
            resp = "1";
            conexion.close();
            cnn.desconectar();
            
        } catch(SQLException e){
            resp = e.getMessage();
        }
        
        return resp;
    }
    
}
