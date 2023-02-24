/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Conexion;

import DAL.LoginDAL2;
import MD.UsuariosSOPMD;
import Util.Cripto;
import Util.Util;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class authbd {

    Util Decryptar = new Util();
    Cripto cp = new Cripto();

    //Menu ValidarPassword = new Menu();
    public String[] autUser(String user2, String pass) throws Exception {

        String[] result = new String[2];
        result[0] = "fail";
        result[1] = "No se pudo validar el usuario O ocurrio un error al conectar con la base de datos";

        Properties prop = new Properties();
        InputStream is = authbd.class.getResourceAsStream("config.properties");

        String driver = "";
        String db = "";
        String port = "";
        String sid = "";
        String Suser = "";
        String Spwd = "";
        String sPwdDB = "";
        String sPwdDecryot = "";
        String FechVencimiento = "";
        String ValiPass = "";

        Connection conn = null;
        try {
            prop.load(is);

            //dejar las variables sin el #2

            driver = prop.getProperty("driver");
            db = prop.getProperty("ipdb");
            port = prop.getProperty("port");
            sid = prop.getProperty("sid");
            Suser = prop.getProperty("user");
            Spwd = prop.getProperty("pwd");
////                        
            Class.forName(driver);

            String url = "jdbc:oracle:thin:@";
            url += db;
            url += ":" + port;
            url += ":" + sid;

            conn = DriverManager.getConnection(url, Suser, Spwd);

            if (conn != null) {

                LoginDAL2 UserValidar = new LoginDAL2();
                UsuariosSOPMD Usuario = new UsuariosSOPMD();

                int PwdValidar = 0;

                PwdValidar = UserValidar.BuscarExiste(user2);

                if (PwdValidar == 0) {

                    result[0] = "fail";
                    result[1] = "Usuario no Existe";

                } else {
                    // pass = Decryptar.EncriptarClave(user2,pass);
                    pass = cp.encrypt(pass);
                    //String pass2 = Decryptar.EncriptarClave2(user2,pass);
                    String pass2 = Decryptar.EncriptarClave(user2, pass);
                    Usuario = UserValidar.validarPassword_Empleado(user2, pass, pass2);

                    if (Usuario.getEstado().equals("A")) {

                        String fecha1 = UserValidar.FechaIng(user2, "1");
                        String fecha2 = UserValidar.FechaSal(user2, "4");

                        int entrada = Decryptar.calculaTiempo(fecha1);
                        int salida = Decryptar.calculaTiempo(fecha2);

                        Usuario = UserValidar.UserEmpleado(user2);

                        //TecInfor = UserValidar.UserEmpleado(user2);
                        if (Usuario.getGroup_name().equals("informatica")) {
                            result[0] = "ok";
                            result[1] = "Usuario valido";

                        } else ////////////se encuentra todo la validacion de ingreso y egreso del biometrico
                        if (entrada > salida) {
                            result[0] = "ok";
                            result[1] = "Usuario valido";

                        } else {

                            result[0] = "fail";
                            result[1] = "No ha Marcado su ingreso en los Biometricos";
                        }

                    } else {
                        if (Usuario.getEstado().equals("D")) {
                            result[0] = "ok";
                            result[1] = "Usuario esta Suspendido, Retirado";

                        }

                        if (Usuario.getEstado().equals("V")) {

                            result[0] = "ok";
                            result[1] = "Usuario esta en Vacaciones";

                        }

                        if (Usuario.getEstado().equals("C")) {
                            result[0] = "ok";
                            result[1] = "Usuario debe cambiar su Clave";
                        }

                    }

                }
            }
        } catch (IOException e) {
            result[0] = "fail";
            result[1] = "error " + e.getMessage();
            return result;
        } catch (ClassNotFoundException e) {
            result[0] = "fail";
            result[1] = "error " + e.getMessage();
            return result;
        } catch (SQLException e) {
            if (e.getErrorCode() == 1017) {
                result[0] = "fail";
                result[1] = "Usuario o clave no validos";
            }
            return result;
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                    conn = null;
                }
            } catch (Exception e2) {
                e2.printStackTrace();
                result[0] = "fail";
                result[1] = "error " + e2.getMessage();
                return result;
            }
        }
        return result;
    }

    public String autUserStr(String user, String pass) throws Exception {
        String[] resp = autUser(user, pass);
        return resp[0] + "|" + resp[1];
    }

    public int Objeto() {
        return 0;
    }

}
