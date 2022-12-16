/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAL;

import Conexion.Conexion;
import MD.ConfirmaPatiosMd;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.zk.ui.util.Clients;

/**
 *
 * @author Informatica
 */
public class ConfirmaPatiosDal {

    private Connection conexion = null;
    private Conexion cnn = new Conexion();
    PreparedStatement ps = null;
    Statement st = null;
    ResultSet rs = null;
    private String vacio;

    public List<ConfirmaPatiosMd> VacioLleno(String anio, String num, String contenedor) {
        //vacio="";
        List<ConfirmaPatiosMd> alldata = new ArrayList<ConfirmaPatiosMd>();
        String query = "select vacio_lleno from epqop.if_ca_activi_conte where codigo_actividad in (1,13) AND identifica_contene = '" + contenedor + "' AND ano_arribo2='" + anio + "' AND num_arribo2='" + num + "' ";
        String query2 = "select count(1) as actividad from epqop.if_ca_activi_conte where identifica_contene='" + contenedor + "' AND codigo_actividad='45' ";
        //String query3="select count(1) from epqop.if_ca_activi_conte where codigo_actividad='45' AND identifica_contene='"+contenedor+"' ";
        try {
            conexion = cnn.Conexion();
            st = conexion.createStatement();
            rs = st.executeQuery(query);
            ConfirmaPatiosMd cp = new ConfirmaPatiosMd();
            while (rs.next()) {
                cp.setVacio(rs.getString("vacio_lleno"));
                System.out.println("VACIO_LLENO.: " + rs.getString("vacio_lleno"));
                //vacio = rs.getString("vacio_lleno");
            }

            rs.close();
            st = conexion.createStatement();
            rs = st.executeQuery(query2);
            while (rs.next()) {
                cp.setActividad(rs.getString("actividad"));
                //vacio = rs.getString("actividad");
            }

            alldata.add(cp);
            st.close();
            conexion.close();
            cnn.desconectar();
        } catch (Exception e) {
            System.out.println("CONTENEDOR..: " + e.getMessage());
            Clients.showNotification("ERROR CATCH Vacio_Lleno..!" + e.getMessage());
        }

        return alldata;
    }

    
    public List<ConfirmaPatiosMd> Actividad(String contenedor) {
        //vacio="";
        List<ConfirmaPatiosMd> alldata = new ArrayList<ConfirmaPatiosMd>();
        String query2 = "select count(1) as actividad from epqop.if_ca_activi_conte where identifica_contene='" + contenedor + "' AND codigo_actividad='45' ";
        //String query3="select count(1) from epqop.if_ca_activi_conte where codigo_actividad='45' AND identifica_contene='"+contenedor+"' ";
        try {
            conexion = cnn.Conexion();
            st = conexion.createStatement();
            rs = st.executeQuery(query2);
            ConfirmaPatiosMd cp = new ConfirmaPatiosMd();
            while (rs.next()) {
                cp.setActividad(rs.getString("actividad"));
                //vacio = rs.getString("actividad");
            }

            alldata.add(cp);
            st.close();
            conexion.close();
            cnn.desconectar();
        } catch (Exception e) {
            System.out.println("CONTENEDOR..: " + e.getMessage());
            Clients.showNotification("ERROR CATCH Vacio_Lleno..!" + e.getMessage());
        }

        return alldata;
    }
    
    
    
    public void guardar(String contenedor, String fecha, String codigo, String hora,
            String ubica1, String vLleno, String equipo, String estatus, String ubica2, String usuario) {
        String fecha1 = fecha+" "+hora;
        System.out.println("FECHA CONCATENADA..: "+fecha1);
        //String fechaFin = "";
        String sql = "Select nvl(MAX(correlativo),0)+1 as correlativo \n"
                + " From EPQOP.if_ca_activi_conte\n"
                + " Where identifica_contene = '" + contenedor + "' ";
        String corr = "";

        String sql2 = "INSERT INTO EPQOP.if_ca_activi_conte  \n"
                + " (identifica_contene, fecha_inicial2,codigo_actividad,hora_inicial4,\n"
                + " ubicacion_1,vacio_lleno, codigo_equipo,status_actividad,correlativo, area_ubicacion,\n"
                + " fecha_alta, hora_alta,usuario,obse_activ2)\n"
                + " VALUES(?,to_date(?,'dd/mm/yyyy hh24:mi:ss'),?,to_date(?,'dd/mm/yyyy hh24:mi:ss'),?,?,?,?,?,?,TO_DATE(?,'DD/MM/YYYY hh24:mi:ss'),TO_DATE(?,'DD/MM/YYYY hh24:mi:ss'),?,?)";

        String sql3 = "Update EPQOP.if_ca_contenedores\n"
                + " set ubicacion_1    = '"+ubica1+"',\n"
                + " area_ubicacion = '"+ubica2+"' ,\n"
                + " fecha_alta     = to_date('"+fecha1+"','dd/mm/yyyy hh24:mi:ss') ,\n"
                + " hora_alta      = to_date('"+fecha1+"','dd/mm/yyyy hh24:mi:ss') ,\n"
                + " usuario        = '"+usuario+"' \n"
                + " Where identifica_contene = '"+contenedor+"' ";

        try {
            conexion = cnn.Conexion();
            st = conexion.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                corr = rs.getString("correlativo");
                System.out.println("CORRRELATIVO.: " + corr);
            }

            ps = conexion.prepareStatement(sql2);
            ps.setString(1, contenedor);
            ps.setString(2, fecha1);
            ps.setString(3, codigo);
            ps.setString(4, fecha1);
            ps.setString(5, ubica1);
            ps.setString(6, vLleno);
            ps.setString(7, equipo);
            ps.setString(8, estatus);
            ps.setString(9, corr);
            ps.setString(10, ubica2);
            ps.setString(11, fecha1);
            ps.setString(12, fecha1);
            ps.setString(13, usuario);
            ps.setString(14, "SOP - WEB");
            ps.executeUpdate();
            ps.close();
            System.out.println("INSERCION EXITOSA..!");
            
            int v1 = st.executeUpdate(sql3);
            if(v1>0){System.out.println("ACTUALIZACION EXITOSA..!");}else{System.out.println("FALLO ACTUALIZACION..!");}
            st.close();
            conexion.close();

        } catch (Exception e) {
            System.out.println("ERROR..!" + e.getMessage());
        }
    }

}
