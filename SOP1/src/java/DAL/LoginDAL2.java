/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAL;

import Conexion.Conexion;
import MD.UsuariosSOPMD;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.zkoss.zk.ui.util.Clients;

import org.zkoss.zul.Messagebox;

/**
 *
 * @author Informatica
 */
public class LoginDAL2 {

    private String ll_fechaing;
    private String ll_codemp;

    private Connection conexion = null;
    private Conexion cnn = new Conexion();
    PreparedStatement ps = null;
    Statement st = null;
    ResultSet rs = null;

    public void insertclaVe(String clave, String usuario) throws SQLException {
        try {
            conexion = cnn.Conexion();
            conexion.setAutoCommit(false);
            st = conexion.createStatement();

            st.executeUpdate("update epqop.users_controlspwd  set clave_sop='" + clave + "' where name='" + usuario + "' ");

            Clients.showNotification("REGISTRO ACTUALIZADO <br/> CON EXITO  <br/>");
            System.out.println("Actualizacion Exitosa.! ");
            st.close();
            conexion.commit();
            conexion.close();
        } catch (SQLException e) {
            conexion.rollback();
            conexion.close();
            String mensaje = e.getMessage();
            Clients.showNotification("ERROR AL ACTUALIZAR <br/>'" + mensaje + "' <br/> REGISTROS! <br/> ",
                    "warning", null, "middle_center", 0);
            System.out.println("Actualizacion EXCEPTION.: " + mensaje);
        }

    }

    public UsuariosSOPMD busca(String pUsuario) throws SQLException {

        PreparedStatement smt = null;
        Connection conn;
        Conexion conex = new Conexion();
        conn = conex.getConnection();
        ResultSet result = null;

        UsuariosSOPMD Buscar = null;

        String sql = "select  name , palabrasecreta from epqop.users_controlspwd where name= ?";

        try {
            smt = conn.prepareStatement(sql);
            smt.setString(1, pUsuario);

            result = smt.executeQuery();

            while (result.next()) {
                Buscar = new UsuariosSOPMD();
                Buscar.setUsrUsuario(result.getString(1));
                Buscar.setUsrPalabra(result.getString(2));

            }
        } catch (Exception e) {
        } finally {
            if (smt != null) {
                smt.close();
            }
            if (result != null) {
                smt.close();
            }
            if (conn != null) {
                conex.desconectar();
                conn.close();
                conn = null;
            }
        }
        return Buscar;
    }

    public UsuariosSOPMD palabraClave(String usuario, String palabra) {

        //boolean existente = false;
        PreparedStatement smt = null;
        Connection conn;
        Conexion conex = new Conexion();
        conn = conex.getConnection();
        String diasVenci;

        UsuariosSOPMD Buscar = new UsuariosSOPMD();

        String sql = "select palabrasecreta from epqop.users_controlspwd where name='" + usuario + "' and palabrasecreta='" + palabra + "'";

        try {
//            smt = conn.prepareStatement(sql);
//            smt.setString(1, usuario);
//            smt.setString(2, palabra);
//
//            ResultSet result = smt.executeQuery();
            conn = conex.Conexion();

            st = conn.createStatement();
            rs = st.executeQuery(sql);

            while (rs.next()) {
                Buscar.setUsrPalabra(rs.getString(1));
            }
              st.close();
            rs.close();
             conn.close();
            conex.desconectar();
        } catch (Exception e) {
            System.out.println("ERROR CATCH.: " + e.getMessage());
        }
        return Buscar;
    }

    public UsuariosSOPMD UserVencimiento(String UserID) {

        //boolean existente = false;
        PreparedStatement smt = null;
        Connection conn;
        Conexion conex = new Conexion();
        conn = conex.getConnection();
        String diasVenci;

        UsuariosSOPMD Buscar = new UsuariosSOPMD();

        String sql = "select nvl(trunc(fecha_vencimiento-sysdate),0)fecha_vencimiento from epqop.users_controlspwd where name = ? and codigo_emp is not null";

        try {
            smt = conn.prepareStatement(sql);
            smt.setString(1, UserID);

            ResultSet result = smt.executeQuery();

            while (result.next()) {
                Buscar.setFechaVencimiento(result.getString(1));
            }
        } catch (Exception e) {
        }
        return Buscar;
    }

    public UsuariosSOPMD CodEmpleado(String UserID) {
        //boolean existente = false;
        PreparedStatement smt = null;
        Connection conn;
        Conexion conex = new Conexion();
        conn = conex.getConnection();
        String diasVenci;

        UsuariosSOPMD Buscar = new UsuariosSOPMD();

        String sql = "select codigo_emp  from epqop.users_controlspwd where name = ? and codigo_emp is not null";

        try {
            smt = conn.prepareStatement(sql);
            smt.setString(1, UserID);

            ResultSet result = smt.executeQuery();

            while (result.next()) {
                Buscar.setCodEmpleado(result.getString(1));
            }
        } catch (Exception e) {
        }
        return Buscar;
    }

    public UsuariosSOPMD PasswordEmpleado(String usuario, String pass) {
        UsuariosSOPMD usuar = new UsuariosSOPMD();
        PreparedStatement smt = null;
        Connection conn;
        Conexion conex = new Conexion();
        conn = conex.getConnection();
        ResultSet result = null;
        String sql = " select distinct name,status, to_char(fecha_vencimiento,'yyyy-MM-dd hh24:mi:ss') as fecha from epqop.users_controlspwd where name = '" + usuario + "' and password2 = '" + pass + "' and fecha_vencimiento > sysdate ";
        try {
            smt = conn.prepareStatement(sql);
            result = smt.executeQuery();
            while (result.next()) {
                usuar.setNombre(result.getString(1));
                usuar.setEstado(result.getString(2));
                usuar.setFechaVencimiento(result.getString(3));
            }
        } catch (Exception e) {
        }
        return usuar;
    }

    public UsuariosSOPMD validarPassword_Empleado(String usuario, String pass, String pass2) throws SQLException {
        UsuariosSOPMD usuar = new UsuariosSOPMD();
        PreparedStatement smt = null;
        Connection conn;
        Conexion conex = new Conexion();
        conn = conex.getConnection();
        ResultSet result = null;
        String user = "V";
        String estado = "I";
        String fecha = "I";
        String sql = " select distinct name,status from epqop.users_controlspwd where name = '" + usuario + "' and clave_sop = '" + pass + "' or password2 = '" + pass2 + "' ";
        String sql2 = " select to_char(fecha_vencimiento,'yyyy-MM-dd hh24:mi:ss') as fecha from epqop.users_controlspwd where name = '" + usuario + "' and fecha_vencimiento > sysdate ";
        try {
            smt = conn.prepareStatement(sql);
            result = smt.executeQuery();
            while (result.next()) {
                user = result.getString(1);
                estado = result.getString(2);
                //usuar.setFechaVenc(result.getString(3));
            }
            if (user.equals("V")) {
                usuar.setNombre("Error");
                usuar.setFechaVencimiento("Error");
                usuar.setEstado("Error");
            } else {
                smt = conn.prepareStatement(sql2);
                result = smt.executeQuery();
                while (result.next()) {
                    fecha = result.getString(1);
                }
                if (fecha.equals("I")) {
                    usuar.setNombre("Error");
                    usuar.setFechaVencimiento("Vencida");
                    usuar.setEstado("Error");
                } else {
                    usuar.setNombre(usuario);
                    usuar.setEstado(estado);
                    usuar.setFechaVencimiento(fecha);
                }
            }
        } catch (Exception e) {
        } finally {
            if (smt != null) {
                smt.close();
                smt = null;
            }
            if (result != null) {
                result.close();
                result = null;
            }
            if (conn != null) {
                conn = null;
            }
        }
        return usuar;
    }

    public UsuariosSOPMD UserEmpleado(String CodEmpleado)///////Validar el rol solo para el ingreso de personal de Garitas
    {
        // String resultado = "";
        UsuariosSOPMD existente = new UsuariosSOPMD();
        PreparedStatement smt = null;
        Connection conn;
        Conexion conex = new Conexion();
        conn = conex.getConnection();

        String sql = "Select group_name from epqop.security_groupings where user_name = ? ";

        try {
            smt = conn.prepareStatement(sql);
            smt.setString(1, CodEmpleado);

            ResultSet result = smt.executeQuery();

            while (result.next()) {
                //  resultado = result.getString(1);
                existente.setGroup_name(result.getString(1));
                // existente.setNombre(result.getString(2));

            }
        } catch (Exception e) {
        }
        return existente;
    }

    public String FechaIng(String CodEmpleado, String TipMarca) //////Valida biometrico marca de Entrada
    {
        String resultado = "";
        PreparedStatement smt = null;
        Connection conn;
        Conexion conex = new Conexion();
        conn = conex.getConnection();
        ResultSet result = null;
        String cadena = CodEmpleado;
        String delimitadores = "[a-z, A-Z]+";
        String[] tokens = cadena.split(delimitadores);
        String user = tokens[1];
        String sql = "select max(to_char(bio_fecha,'yyyy-MM-dd hh24:mi:ss')) from rhepq.accesos where bio_id = " + user + " and bio_tipo_marca = " + TipMarca + "";
        try {

            smt = conn.prepareStatement(sql);
            result = smt.executeQuery();
            while (result.next()) {
                resultado = result.getString(1);
            }
        } catch (Exception e) {
        }
        return resultado;
    }

    public String FechaSal(String CodEmpleado, String TipMarca) //////Valida biometrico marca de Salida
    {
        String resultado = "";
        PreparedStatement smt = null;
        Connection conn;
        Conexion conex = new Conexion();
        conn = conex.getConnection();
        ResultSet result = null;
        String cadena = CodEmpleado;
        String delimitadores = "[a-z, A-Z]+";
        String[] tokens = cadena.split(delimitadores);
        String user = tokens[1];
        String sql = "select max(to_char(bio_fecha,'yyyy-MM-dd hh24:mi:ss')) from rhepq.accesos where bio_id = " + user + " and bio_tipo_marca = " + TipMarca + "";
        try {

            smt = conn.prepareStatement(sql);
            result = smt.executeQuery();
            while (result.next()) {
                resultado = result.getString(1);
            }
        } catch (Exception e) {
        }
        return resultado;
    }

    public int BuscarExiste(String bUsuario) {
        int contador = 0;
        int existente = 0;
        PreparedStatement smt = null;
        Connection conn;
        Conexion conex = new Conexion();
        conn = conex.getConnection();

        String sql = "Select name from epqop.security_users where name = ? ";

        try {
            smt = conn.prepareStatement(sql);
            smt.setString(1, bUsuario);

            ResultSet result = smt.executeQuery();

            while (result.next()) {
                existente = 1;
            }

        } catch (Exception e) {
            System.out.println("error "+e);
        }
        return existente;
    }

    public UsuariosSOPMD UserSecurity(String sUsuario) {
        PreparedStatement smt = null;
        Connection conn;
        Conexion conex = new Conexion();
        conn = conex.getConnection();

        UsuariosSOPMD Buscar = new UsuariosSOPMD();

        String sql = "Select * from epqop.security_users where name = ? ";

        try {
            smt = conn.prepareStatement(sql);
            smt.setString(1, sUsuario);

            ResultSet result = smt.executeQuery();

            while (result.next()) {
                Buscar.setNombre(result.getString(1));
                Buscar.setDescripcion(result.getString(2));
                Buscar.setPrioridad(result.getDouble(3));
                Buscar.setTipoUsuario(result.getString(4));
                Buscar.setPassword(result.getString(5));
            }
        } catch (Exception e) {
        }
        return Buscar;
    }

    public boolean RolUser(String rolUsuario) {
        boolean existente = false;
        PreparedStatement smt = null;
        Connection conn;
        Conexion conex = new Conexion();
        conn = conex.getConnection();

        String sql = "Select group_name from epqop.security_groupings where user_name = ?";

        try {
            smt = conn.prepareStatement(sql);
            smt.setString(1, rolUsuario);

            ResultSet result = smt.executeQuery();

            while (result.next()) {
                existente = true;
            }
        } catch (Exception e) {
        }
        return existente;
    }

    public boolean Password(String Password) {
        boolean existente = false;
        PreparedStatement smt = null;
        Connection conn;
        Conexion conex = new Conexion();
        conn = conex.getConnection();

        String sql = "select codigo_emp from epqop.users_controlspwd where name = ?";

        try {
            smt = conn.prepareStatement(sql);
            smt.setString(1, Password);

            ResultSet result = smt.executeQuery();

            while (result.next()) {
                existente = true;
            }
        } catch (Exception e) {
        }
        return existente;
    }

    public int Crear_Usuarios_SW(UsuariosSOPMD UsuariosSW) throws SQLException {
        {
            PreparedStatement smt = null;
            Connection conn;
            Conexion conex = new Conexion();
            conn = conex.getConnection();
            int result = 0;
            int pcorrelativo = 0;

            String sql = "select Nvl(Max(correlativo)+1,1)CORR FROM epqop.usuarios_servicios_web";

            try {

                smt = conn.prepareStatement(sql);
                ResultSet pst = smt.executeQuery();
                while (pst.next()) {
                    pcorrelativo = pst.getInt("CORR");
                }

                sql = "insert into epqop.usuarios_servicios_web values ((" + pcorrelativo + "),?,'SISTEMA INGRESO CAMIONES AL PARQUEO','LOGIN',sysdate,null,0,0,'AUTENTICACION AL SISTEMA DE INGRESO DE CAMIONES AL PARQUEO EXITOSAMENTE')";

                smt = conn.prepareStatement(sql);
                smt.setString(1, UsuariosSW.getNombre());
//                smt.setString(2, UsuariosDAL.getPswd());
//                smt.setString(3, UsuariosDAL.getEmail());
//                smt.setInt(4, UsuariosDAL.getBloqueo());
//                smt.setInt(5, UsuariosDAL.getConfirmacion());
//                smt.setString(6, UsuariosDAL.getUltimo_Acceso());

                result = smt.executeUpdate();
                return result;

            } finally {
                if (smt != null) {
                    smt.close();
                    smt = null;
                }
                if (conn != null) {
                    conn = conex.desconectar();
                }
            }

        }
    }

}
