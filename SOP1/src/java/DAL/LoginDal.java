/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAL;

import Conexion.Conexion;
import MD.LoginMd;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import oracle.jdbc.OracleTypes;
import org.zkoss.zhtml.Messagebox;

/**
 *
 * @author alfonsoc7905
 */
public class LoginDal {
    Conexion obtener = new Conexion();
    Connection conn;
    LoginMd us = new LoginMd();
    public LoginMd UsuarioExiste(String usuario, String pass) throws SQLException {
        PreparedStatement smt = null;
        ResultSet result = null;
        int resultado = 0;
        
        System.out.println("Credenciales.: " + usuario + " -> " + pass);
        String sql = " select trim(usuario)as usuario, trim(correo_naviera) as nav, trim(PSWD) as pass from SYSAUDTEMP.usuarios_sat where trim(usuario) = '"+usuario+"' AND trim(pswd) = '"+pass+"' ";
        
        try {
            conn = obtener.Conexion();
            smt = conn.prepareStatement(sql);
            result = smt.executeQuery();
            int rsp = 0;
            while (result.next()) {
                us.setUseario(result.getString("usuario"));
                us.setEmpresa(result.getString("nav"));
                us.setPass(result.getString("pass"));
                us.setRespuesta("1");
                System.out.println("USUARIO.: "+us.getUseario());
                System.out.println("EMPRESA.: "+us.getEmpresa());
                rsp=1;
            }
            result.close();
            smt.close();
            conn.close();
            obtener.desconectar();
            if(rsp == 0){us.setRespuesta("0");}
        } catch (SQLException e) {
            System.out.println("ERROR CATCH.: "+e.getMessage().toLowerCase());
            Messagebox.show("Credenciales Incorrectas", "Informacion", Messagebox.OK, Messagebox.INFORMATION);
        } 
        return us;
    }
    
    public LoginMd UsuarioExiste1(String usuario) throws SQLException {
        CallableStatement cStmt = null;
        ResultSet result = null;
        int resultado = 0;
        
        try {
            conn = obtener.Conexion();
            cStmt = conn.prepareCall("{call epqop.LOGINCONTE(?,?)}");
            cStmt.setString(1, usuario);
            cStmt.registerOutParameter(2, OracleTypes.CURSOR);
            cStmt.execute();
            result = (ResultSet) cStmt.getObject(2);
            
            int rsp = 0;
            while (result.next()) {
                us.setUseario(result.getString(1));
                us.setEmpresa(result.getString(2));
                us.setPass(result.getString(3));
                us.setRespuesta("1");
                System.out.println("USUARIO.: "+us.getUseario());
                System.out.println("EMPRESA.: "+us.getEmpresa());
                rsp=1;
            }
            result.close();
            cStmt.close();
            if(rsp == 0){us.setRespuesta("0");} 
            else{
            System.out.println("PROCEDIMIENTO BITACORA..");
            cStmt = conn.prepareCall("{call SYSAUDTEMP.BitacoraWeb(?,?,?,?,?)}");
            cStmt.setString(1, usuario);//USUARIO
            cStmt.setString(2, "WEB");//MAQUINA
            cStmt.setString(3, "23");//ID de SISTEMA
            cStmt.setString(4, "LOGIN SOP web.: "+usuario);//OBSERVACIONES
            cStmt.setString(5, "WEB"); //IP DIRECCION
            cStmt.execute();
            cStmt.close();
            }
            conn.close();
            obtener.desconectar();
            
        } catch (SQLException e) {
            System.out.println("ERROR CATCH.: "+e.getMessage().toLowerCase());
            Messagebox.show("Credenciales Incorrectas", "Informacion", Messagebox.OK, Messagebox.INFORMATION);
        } 
        return us;
    }
    
    public LoginMd BuscaUsuario(String usuario) throws SQLException {
        PreparedStatement smt = null;
        ResultSet result = null;
        int resultado = 0;
        
        System.out.println("Credenciales.: " + usuario);
        String sql = " select trim(usuario)as usuario, trim(correo_naviera) as nav, trim(PSWD) as pass from SYSAUDTEMP.usuarios_sat where trim(usuario) = '"+usuario+"' ";
        
        try {
            conn = obtener.Conexion();
            smt = conn.prepareStatement(sql);
            result = smt.executeQuery();
            int rsp = 0;
            while (result.next()) {
                us.setUseario(result.getString("usuario"));
                us.setEmpresa(result.getString("nav"));
                us.setPass(result.getString("pass"));
                us.setRespuesta("1");
                System.out.println("USUARIO.: "+us.getUseario());
                System.out.println("EMPRESA.: "+us.getEmpresa());
                rsp=1;
            }
            result.close();
            smt.close();
            
//            System.out.println("PROCEDIMIENTO BITACORA..");
//            CallableStatement cStmt = conn.prepareCall("{call SYSAUDTEMP.BitacoraWeb(?,?,?,?,?)}");
//            cStmt.setString(1, usuario);//USUARIO
//            cStmt.setString(2, "WEB");//MAQUINA
//            cStmt.setString(3, "20");//ID de SISTEMA
//            cStmt.setString(4, "LOGIN CitasEPQ.: "+usuario);//OBSERVACIONES
//            cStmt.setString(5, "WEB"); //IP DIRECCION
//            cStmt.execute();
//            cStmt.close();
            
            conn.close();
            obtener.desconectar();
            if(rsp == 0){us.setRespuesta("0");}
        } catch (SQLException e) {
            System.out.println("ERROR CATCH.: "+e.getMessage().toLowerCase());
            Messagebox.show("Credenciales Incorrectas", "Informacion", Messagebox.OK, Messagebox.INFORMATION);
        } 
//        resultado = ValidarUser(usuario, pass, us.getUsuario(), us.getClave());
//        codigo = us.getId();

        //all_licencias = findLicencias(conn);
        return us;
    }
    
}
