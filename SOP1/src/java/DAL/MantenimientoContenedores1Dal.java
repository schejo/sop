/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAL;

import Conexion.Conexion;
import MD.MantenimientoContenedores1Md;
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
public class MantenimientoContenedores1Dal {
    
    private Connection conexion = null;
    private Conexion cnn = new Conexion();
    PreparedStatement ps = null;
    Statement st = null;
    ResultSet rs = null;
    
    public List<MantenimientoContenedores1Md> search(String contene){
        List<MantenimientoContenedores1Md> alldata = new ArrayList<MantenimientoContenedores1Md>();
        
        String query = "select TIPO_CONTENEDOR, "
                + " LINEA, NAVIERA, "
                + " TO_CHAR(FECHA_INGRESO2,'DD/MM/YYYY')as FECHA, "
                + " TO_CHAR(HORA_INGRESO2,'HH24:MI:SS') as HORA, "
                + " UBICACION_1, "
                + " PESO_BRUTO_RECIBID, "
                + " PESO_TARA, "
                + " PESO_NETO_BASCULA, "
                + " VACIO_LLENO, "
                + " STATUS1, "
                + " NVL(FACTURA_ENTRADA,0) as FACTURA, "
                + " MANIFIESTO, "
                + " ANO_MANIFIESTO, "
                + " AREA_UBICACION, "
                + " NVL(TRANSITO,'0') as TRANSITO "
                + " FROM EPQOP.if_ca_contenedores where identifica_contene = '"+contene+"' ";
        
        try {
            conexion = cnn.Conexion();
            st = conexion.createStatement();
            rs = st.executeQuery(query);
            MantenimientoContenedores1Md item = new MantenimientoContenedores1Md();
            while (rs.next()) {
                item.setTipo(rs.getString("TIPO_CONTENEDOR"));
                item.setLinea(rs.getString("LINEA"));
                item.setNaviera(rs.getString("NAVIERA"));
                item.setFecha2(rs.getString("FECHA"));
                item.setHora2(rs.getString("HORA"));
                item.setUbicacion1(rs.getString("UBICACION_1"));
                item.setPesobrutor(rs.getString("PESO_BRUTO_RECIBID"));
                item.setPesotara(rs.getString("PESO_TARA"));
                item.setPesonetob(rs.getString("PESO_NETO_BASCULA"));
                item.setVacio(rs.getString("VACIO_LLENO"));
                item.setEstado(rs.getString("STATUS1"));
                item.setFactura(rs.getString("FACTURA"));
                item.setManifiesto(rs.getString("MANIFIESTO"));
                item.setAniomanif(rs.getString("ANO_MANIFIESTO"));
                item.setAreaubica(rs.getString("AREA_UBICACION"));
                item.setTransito(rs.getString("TRANSITO"));
                alldata.add(item);
            }
            
            st.close();
            conexion.close();
            cnn.desconectar();
            
        } catch(SQLException e){System.out.println("Exception..: "+e.getMessage());}
        
        return alldata;
    }
    
    public String Updata (String contene, MantenimientoContenedores1Md data) {
        int v1 = 0;
        String resp = "0";
        String query = "UPDATE EPQOP.if_ca_contenedores SET "
                + " TIPO_CONTENEDOR = '"+data.getTipo()+"', "
                + " LINEA = '"+data.getLinea()+"', "
                + " NAVIERA = '"+data.getNaviera()+"', "
                + " FECHA_INGRESO2 = TO_DATE('"+data.getFecha2()+"','DD/MM/YYYY'), "
                + " HORA_INGRESO2 = TO_DATE('"+data.getHora2()+"','HH24:MI:SS'), "
                + " UBICACION_1 = '"+data.getUbicacion1()+"', "
                + " PESO_BRUTO_RECIBID = '"+data.getPesobrutor()+"', "
                + " PESO_TARA = '"+data.getPesotara()+"', "
                + " PESO_NETO_BASCULA = '"+data.getPesonetob()+"', "
                + " VACIO_LLENO = '"+data.getVacio()+"', "
                + " STATUS1 = '"+data.getEstado()+"', "
                + " FACTURA_ENTRADA = '"+data.getFactura()+"', "
                + " MANIFIESTO = '"+data.getManifiesto()+"', "
                + " ANO_MANIFIESTO = '"+data.getAniomanif()+"', "
                + " AREA_UBICACION = '"+data.getAreaubica()+"', "
                + " TRANSITO = '"+data.getTransito()+"' "
                + " WHERE identifica_contene = '"+contene+"' ";
        
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
            System.out.println("Exception Formulario Edit..: "+e.getMessage());
        }
        
        return resp;
    }
    
    public String Deldata (String contene) {
        String resp = "0";
        String query = " DELETE FROM EPQOP.if_ca_contenedores where trim(identifica_contene) = ? ";
        try {
            conexion = cnn.Conexion();
            ps = conexion.prepareStatement(query);
            ps.setString(1, contene);
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
