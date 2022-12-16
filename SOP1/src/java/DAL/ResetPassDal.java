/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAL;

import Conexion.Conexion;
import MD.LoginMd;
import MD.ResetPassMd;
import MD.VisadoMd;
import Util.Bitacora;
import Util.Cripto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Messagebox;

/**
 *
 * @author alfonsoc7905
 */
public class ResetPassDal {

    private static String key = "epqCitas*8965*";
    private Connection conexion = null;
    private Conexion cnn = new Conexion();
    PreparedStatement st = null;
    ResultSet rs = null;

    public ResetPassMd findData() throws SQLException {
        ResetPassMd allData = new ResetPassMd();
        List<VisadoMd> allEmpresas = new ArrayList<VisadoMd>();
        List<LoginMd> allUsers = new ArrayList<LoginMd>();
        String sql = "select UNIQUE(correo_naviera) from SYSAUDTEMP.usuarios_sat where correo_naviera is not null";
        //String sql2 = " select trim(usuario)as usuario, trim(correo_naviera) as nav, trim(PSWD) as pass from SYSAUDTEMP.usuarios_sat where correo_naviera is not null ";
        String sql2 = " select trim(usuario) as usuario, trim(correo_naviera) as nav, trim(PSWD) as pass, NVL(correo_electronico,'jvargas16222603@gmail.com') as EMAIL from SYSAUDTEMP.usuarios_sat where correo_naviera is not null ";
        try {
            conexion = cnn.Conexion();
            st = conexion.prepareStatement(sql);
            rs = st.executeQuery();
            System.out.println("OBTENER DATOS DE EMPRESAS..!");
            VisadoMd ac;
            while (rs.next()) {
                ac = new VisadoMd();
                ac.setEmpresa(rs.getString(1));
                allEmpresas.add(ac);
            }
            rs.close();
            st.close();
            allData.setAllEmpresas(allEmpresas);

            st = conexion.prepareStatement(sql2);
            rs = st.executeQuery();
            System.out.println("OBTENER DATOS DE USUARIOS..!");
            LoginMd us;
            while (rs.next()) {
                us = new LoginMd();
                us.setUseario(rs.getString(1));
                us.setEmpresa(rs.getString(2));
                us.setPass(rs.getString(3));
                us.setEmail(rs.getString(4));
                allUsers.add(us);
            }
            rs.close();
            st.close();
            allData.setAllUsers(allUsers);
            conexion.close();
            conexion = null;

        } catch (Exception e) {
            System.out.println("Error All CITAS.: " + e.getMessage());
            conexion.close();
            conexion = null;
            Messagebox.show("SOLICITUD NO PUEDE SER PROCESADA", "Informacion", Messagebox.OK, Messagebox.INFORMATION);
        }
        return allData;
    }

    public int resetPass(String usuario) {
        Statement st = null;
        int resp = 0;
        String sql = "";
        Cripto cp = new Cripto();
        String newpass = cp.reset();
        System.out.println("RESET PASS.: "+newpass);
        try {
            conexion = cnn.Conexion();
            st = conexion.createStatement();
            resp = st.executeUpdate("UPDATE SYSAUDTEMP.usuarios_sat SET pswd = '"+newpass+"', fecha_mov = sysdate WHERE trim(usuario) ='"+usuario+"' ");
            if (resp > 0) {
                System.out.println("Actualizacion Exitosa.: " + resp);
                Bitacora bt = new Bitacora();
                String rps = bt.login(usuario, "CitasEPQ", "RESET PASS", "null", 0, 0, "PROCESO EXITOSO ");
//                Clients.showNotification("PROCESO <br/>  <br/> EXITOSO <br/>",
//                        Clients.NOTIFICATION_TYPE_INFO, null, "middle_center", 0);
            } else {
                System.out.println("Actualizacion Fallida.: " + resp);
            }
            st.close();
            conexion.close();
        } catch (SQLException e) {
            System.out.println("ERROR.: " + e.getMessage());
            Bitacora bt = new Bitacora();
            String rps = bt.login(usuario, "CitasEPQ", "RESET PASS", "null", 0, 0, "Error.: "+e.getMessage().toString());
            Clients.showNotification("ERROR <br/>  <br/> No se puede Cambiar la Clave <br/>"+e.getMessage().toString(),
                    Clients.NOTIFICATION_TYPE_ERROR, null, "top_center", 0);
        }
        return resp;
    }
    
    public int cambioPass(String usuario, String pass) {
        Statement st = null;
        int resp = 0;
        String sql = "";
        Cripto cp = new Cripto();
        String newpass = cp.encrypt(pass);
        System.out.println("NEW PASS.: "+newpass);
        try {
            conexion = cnn.Conexion();
            st = conexion.createStatement();
            resp = st.executeUpdate("UPDATE SYSAUDTEMP.usuarios_sat SET pswd = '"+newpass+"', fecha_mov = sysdate WHERE trim(usuario) ='"+usuario+"' ");
            if (resp > 0) {
                System.out.println("Actualizacion Exitosa.: " + resp);
                Bitacora bt = new Bitacora();
                String rps = bt.login(usuario, "CitasEPQ", "NEW PASS", "null", 0, 0, "PROCESO EXITOSO ");
                Clients.showNotification("PROCESO <br/>  <br/> EXITOSO <br/>",
                        Clients.NOTIFICATION_TYPE_INFO, null, "middle_center", 0);
            } else {
                System.out.println("Actualizacion Fallida.: " + resp);
            }
            st.close();
            conexion.close();
        } catch (SQLException e) {
            System.out.println("ERROR.: " + e.getMessage());
            Bitacora bt = new Bitacora();
            String rps = bt.login(usuario, "CitasEPQ", "NEW PASS", "null", 0, 0, "Error.: "+e.getMessage().toString());
            Clients.showNotification("ERROR <br/>  <br/> No se puede Cambiar la Clave <br/>"+e.getMessage().toString(),
                    Clients.NOTIFICATION_TYPE_ERROR, null, "top_center", 0);
        }
        return resp;
    }

}
