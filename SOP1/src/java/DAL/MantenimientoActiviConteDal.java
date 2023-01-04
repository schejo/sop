/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAL;

import Conexion.Conexion;
import MD.MantenimientoActiviConteMd;
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
public class MantenimientoActiviConteDal {
    
    private Connection conexion = null;
    private Conexion cnn = new Conexion();
    PreparedStatement ps = null;
    Statement st = null;
    ResultSet rs = null;
    
    public List<MantenimientoActiviConteMd> search(String conte, String activi, String correla){
        List<MantenimientoActiviConteMd> alldata = new ArrayList<MantenimientoActiviConteMd>();
        String query = " select "
                + " TO_CHAR(FECHA_INICIAL2,'DD/MM/YYYY')as FECHAIN2,"
                + " CODIGO_ACTIVIDAD, "
                + " TO_CHAR(HORA_INICIAL4,'HH24:MI:SS')as HORA_INICIAL4, "
                + " TO_CHAR(FECHA_FINAL2,'DD/MM/YYYY') as FECHA_FINAL2, "
                + " TO_CHAR(HORA_FINAL4,'HH24:MI:SS') as HORA_FINAL4, "
                + " UBICACION_1, "
                + " MARCHAMO_3, "
                + " ANO_ARRIBO2, "
                + " NUM_ARRIBO2, "
                + " TO_CHAR(FECHA_CICLO1,'DD/MM/YYYY') as FECHA_CICLO1, "
                + " ESTADO_CONTENEDOR, "
                + " ANO_MANIFIESTO1, "
                + " MANIFIESTO1, "
                + " CORRELATIVO_BL1, "
                + " DETALLE_BL1, "
                + " PESO_BRUTO_BASCULA, "
                + " DANOS, "
                //+ " CORRELATIVO, "
                + " PUERTO,"
                + " AREA_UBICACION "
                + " FROM EPQOP.if_ca_activi_conte "
                + " WHERE TRIM(IDENTIFICA_CONTENE) = '"+conte+"' "
                + " AND TRIM(CODIGO_ACTIVIDAD) = '"+activi+"' "
                + " AND TRIM(CORRELATIVO) = '"+correla+"' ";
        
        try {
            conexion = cnn.Conexion();
            st = conexion.createStatement();
            rs = st.executeQuery(query);
            
            MantenimientoActiviConteMd item = new MantenimientoActiviConteMd();
            while(rs.next()){
                item.setFechaInicial2(rs.getString("FECHAIN2"));
                item.setCodigoActividad(rs.getString("CODIGO_ACTIVIDAD"));
                item.setHoraInicial4(rs.getString("HORA_INICIAL4"));
                item.setFechaFinal2(rs.getString("FECHA_FINAL2"));
                item.setHoraFinal4(rs.getString("HORA_FINAL4"));
                item.setUbicacion1(rs.getString("UBICACION_1"));
                item.setMarchamo3(rs.getString("MARCHAMO_3"));
                item.setAnioArribo(rs.getString("ANO_ARRIBO2"));
                item.setNumArribo(rs.getString("NUM_ARRIBO2"));
                item.setFechaCiclo(rs.getString("FECHA_CICLO1"));
                item.setEstadoConte(rs.getString("ESTADO_CONTENEDOR"));
                item.setAnioManif(rs.getString("ANO_MANIFIESTO1"));
                item.setManifiesto(rs.getString("MANIFIESTO1"));
                item.setCorrelaBL(rs.getString("CORRELATIVO_BL1"));
                item.setDetalleBL(rs.getString("DETALLE_BL1"));
                item.setPesobrutob(rs.getString("PESO_BRUTO_BASCULA"));
                item.setDanos(rs.getString("DANOS"));
                //item.setCorrelativo(rs.getString("CORRELATIVO"));
                item.setPuerto(rs.getString("PUERTO"));
                item.setAreaU(rs.getString("AREA_UBICACION"));
                alldata.add(item);
            }
            
            st.close();
            conexion.close();
            cnn.desconectar();
        } catch(SQLException e){System.out.println("Exception..: "+e.getMessage());}
        
        return alldata;
    }
    
    public String updata (String conte, String activi, String correla, MantenimientoActiviConteMd item ) {
        int v1 = 0;
        String resp = "";
        String query = " UPDATE EPQOP.if_ca_activi_conte SET"
                + " FECHA_INICIAL2 = TO_DATE('"+item.getFechaInicial2()+"','DD/MM/YYYY'), "
                + " CODIGO_ACTIVIDAD = '"+item.getCodigoActividad()+"', "
                + " HORA_INICIAL4 = TO_DATE('"+item.getHoraInicial4()+"','HH24:MI:SS'), "
                + " FECHA_FINAL2 = TO_DATE('"+item.getFechaFinal2()+"','DD/MM/YYYY'), "
                + " HORA_FINAL4 = TO_DATE('"+item.getHoraFinal4()+"','HH24:MI:SS'), "
                + " UBICACION_1 = '"+item.getUbicacion1()+"', "
                + " MARCHAMO_3 = '"+item.getMarchamo3()+"', "
                + " ANO_ARRIBO2 = '"+item.getAnioArribo()+"', "
                + " NUM_ARRIBO2 = '"+item.getNumArribo()+"', "
                + " FECHA_CICLO1 = TO_DATE('"+item.getFechaCiclo()+"','DD/MM/YYYY'), "
                + " ESTADO_CONTENEDOR = '"+item.getEstadoConte()+"', "
                + " ANO_MANIFIESTO1 = '"+item.getAnioManif()+"', "
                + " MANIFIESTO1 = '"+item.getManifiesto()+"', "
                + " CORRELATIVO_BL1 = '"+item.getCorrelaBL()+"', "
                + " DETALLE_BL1 = '"+item.getDetalleBL()+"', "
                + " PESO_BRUTO_BASCULA = '"+item.getPesobrutob()+"', "
                + " DANOS = '"+item.getDanos()+"', "
                //+ " CORRELATIVO = '"+item.getCorrelativo()+"', "
                + " PUERTO = '"+item.getPuerto()+"',"
                + " AREA_UBICACION = '"+item.getAreaU()+"' "
                + " WHERE TRIM(IDENTIFICA_CONTENE) = '"+conte+"' "
                + " AND TRIM(CODIGO_ACTIVIDAD) = '"+activi+"' "
                + " AND TRIM(CORRELATIVO) = '"+correla+"'  ";
        
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
        } catch (SQLException e) {
            resp = e.getMessage();
            System.out.println("Exception..: "+e.getMessage());}
        return resp;
    }
    
    public String Deldata (String conte, String activi, String correla) {
        String resp = "0";
        String query = " DELETE FROM EPQOP.if_ca_activi_conte "
                + " WHERE TRIM(IDENTIFICA_CONTENE) = ? "
                + " AND TRIM(CODIGO_ACTIVIDAD) = ? "
                + " AND TRIM(CORRELATIVO) = ? ";
        
        try {
            conexion = cnn.Conexion();
            ps = conexion.prepareStatement(query);
            ps.setString(1, conte);
            ps.setString(2, activi);
            ps.setString(3, correla);
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
