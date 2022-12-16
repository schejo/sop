package DAL;

import Conexion.Conexion;
import MD.ClientesMd;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.zk.ui.util.Clients;

public class ClientesDal {

    private Connection conexion = null;
    private Conexion cnn = new Conexion();
    PreparedStatement ps = null;
    Statement st = null;
    ResultSet rs = null;

    public List<ClientesMd> REGselect(String num) throws SQLException {
        List<ClientesMd> allClientes = new ArrayList<ClientesMd>();
      
        String query
                = "SELECT"
                + "TRIM(codigo_cliente), "
                + "TRIM(codigo_agrupacion), "
                + "TRIM (nit), "
                + "TRIM(nombre_comercial), "
                + "TRIM(razon),"
                + "TRIM (direccion), "
                + "TRIM (direccion_cobro), "
                + "TRIM (direccion_puerto), "
                + "TRIM (telefonos), "
                + "TRIM (telefonos_puerto) "
                + "TRIM (numero_fax), "
                + "TRIM (numero_fax_puer), "
                + "TRIM (direccion_email), "
                + "TRIM (comentario_1), "
                + "TRIM (comentario_2), "
                + "TRIM (comentario_3), "
                + "TRIM (tipo_1), "
                + "TRIM (tipo_2), "
                + "TRIM (tipo_3), "
                + "TRIM (tipo_4), "
                + "TRIM (extento_iva) "
                + "FROM epqop.if_clientes WHERE codigo_cliente = '" + num + "' ";
        try {
            conexion = cnn.Conexion();
            st = conexion.createStatement();
            rs = st.executeQuery(query);
            ClientesMd rg = new ClientesMd();
            while (rs.next()) {
                rg.setCodigo_cliente(rs.getString(1));
                rg.setCodigo_agrupacion(rs.getString(2));
                rg.setNit(rs.getString(3));
                rg.setNombre_comercial(rs.getString(4));
                rg.setRazon(rs.getString(5));
                rg.setDireccion(rs.getString(6));
                rg.setDireccion_cobro(rs.getString(7));
                rg.setDireccion_puerto(rs.getString(8));
                rg.setTelefonos(rs.getString(9));
                rg.setTelefono_puerto(rs.getString(10));
                rg.setFax(rs.getString(11));
                rg.setFax_puerto(rs.getString(12));
                rg.setEmail(rs.getString(13));
                rg.setComentario_1(rs.getString(14));
                rg.setComentario_2(rs.getString(15));
                rg.setComentario_3(rs.getString(16));
                rg.setTipo_1(rs.getString(17));
                rg.setTipo_2(rs.getString(18));
                rg.setTipo_3(rs.getString(19));
                rg.setTipo_4(rs.getString(20));
                rg.setIva(rs.getString(21));

                allClientes.add(rg);
            }

            st.close();
            rs.close();
            conexion.close();
            conexion = null;
        } catch (SQLException e) {
            st.close();
            rs.close();
            conexion.close();
            conexion = null;
            Clients.showNotification("ERROR AL CONSULTAR <br/> <br/> REGISTROS! <br/> " + e.getMessage().toString(),
                    "warning", null, "middle_center", 0);
        }
        return allClientes;
    }

    /*El nvl (  , '') me sirve para que si un campo en la BD esta vacio o nulo*/
    public List<ClientesMd> RSelect() throws SQLException {
        List<ClientesMd> allClientes = new ArrayList<ClientesMd>();
        String query = "SELECT "
                + "TRIM(codigo_cliente), "
                + "TRIM(codigo_agrupacion), "
                + "TRIM (nit), "
                + "TRIM(nombre_comercial), "
                + "TRIM(razon), "
                + "NVL(TRIM (direccion),''), "
                + "NVL(TRIM (direccion_cobro),''), "
                + "NVL(TRIM (direccion_puerto),''), "
                + "NVL(TRIM (telefonos),''), "
                + "NVL(TRIM (telefonos_puerto),''), "
                + "NVL(TRIM (numero_fax),''), "
                + "NVL (TRIM (numero_fax_puer),''), "
                + "NVL (TRIM (direccion_email),''), "
                + "NVL (TRIM (comentario_1),''), "
                + "NVL (TRIM (comentario_2),''), "
                + "NVL (TRIM (comentario_3),''), "
                + "NVL (TRIM (tipo_1),''), "
                + "NVL (TRIM (tipo_2),''), "
                + "NVL (TRIM (tipo_3),''), "
                + "NVL (TRIM (tipo_4),''), "
                + "NVL (TRIM (REPLACE(REPLACE(exento_iva,'S','SI'),'N','NO')),'') "
                + "FROM epqop.if_clientes ORDER BY codigo_cliente ASC ";
        try {
            conexion = cnn.Conexion();
            st = conexion.createStatement();
            rs = st.executeQuery(query);
            ClientesMd rg;
            while (rs.next()) {
                rg = new ClientesMd();
                rg.setCodigo_cliente(rs.getString(1));
                rg.setCodigo_agrupacion(rs.getString(2));
                rg.setNit(rs.getString(3));
                rg.setNombre_comercial(rs.getString(4));
                rg.setRazon(rs.getString(5));
                rg.setDireccion(rs.getString(6));
                rg.setDireccion_cobro(rs.getString(7));
                rg.setDireccion_puerto(rs.getString(8));
                rg.setTelefonos(rs.getString(9));
                rg.setTelefono_puerto(rs.getString(10));
                rg.setFax(rs.getString(11));
                rg.setFax_puerto(rs.getString(12));
                rg.setEmail(rs.getString(13));
                rg.setComentario_1(rs.getString(14));
                rg.setComentario_2(rs.getString(15));
                rg.setComentario_3(rs.getString(16));
                rg.setTipo_1(rs.getString(17));
                rg.setTipo_2(rs.getString(18));
                rg.setTipo_3(rs.getString(19));
                rg.setTipo_4(rs.getString(20));
                rg.setIva(rs.getString(21));

                allClientes.add(rg);
            }

            rs.close();
            st.close();
            conexion.close();
            conexion = null;
        } catch (SQLException e) {
            st.close();
            conexion.close();
            conexion = null;
            Clients.showNotification("ERROR AL CONSULTAR <br/> <br/> REGISTROS! <br/> " + e.getMessage().toString(),
                    "warning", null, "middle_center", 0);
        }
        return allClientes;
    }

    public void REGinsert(String codigo_cliente, String codigo_agrupacion, String nit, String nombre_comercial, String razon, String direccion, String direccion_cobro, String direccion_puerto, String telefonos, String telefonos_puerto, String numero_fax, String numero_fax_puer, String direccion_email, String comentario_1, String comentario_2, String comentario_3, String tipo_1, String tipo_2, String tipo_3, String tipo_4, String exento_iva) throws SQLException {
        
        String sql = "INSERT INTO "
                + " epqop.if_clientes "
                + "(codigo_cliente,codigo_agrupacion,"
                + " nit,nombre_comercial,"
                + " razon,direccion,"
                + " direccion_cobro,direccion_puerto,"
                + " telefonos,telefonos_puerto,"
                + " numero_fax,numero_fax_puer"
                + " direccion_email,comentario_1"
                + " comenterio_2,comentario_3"
                + " tipo_1,tipo_2"
                + " tipo_3,tipo_4"
                + " exento_iva)"
                + " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            conexion = cnn.Conexion();
            conexion.setAutoCommit(false);
            st = conexion.createStatement();

            ps = conexion.prepareStatement(sql);

            ps.setString(1, codigo_cliente);
            ps.setString(2, codigo_agrupacion);
            ps.setString(3, nit);
            ps.setString(4, nombre_comercial);
            ps.setString(5, razon);
            ps.setString(6, direccion);
            ps.setString(7, direccion_cobro);
            ps.setString(8, direccion_puerto);
            ps.setString(9, telefonos);
            ps.setString(10, telefonos_puerto);
            ps.setString(11, numero_fax);
            ps.setString(12, numero_fax_puer);
            ps.setString(13, direccion_email);
            ps.setString(14, comentario_1);
            ps.setString(15, comentario_2);
            ps.setString(16, comentario_3);
            ps.setString(17, tipo_1);
            ps.setString(18, tipo_2);
            ps.setString(19, tipo_3);
            ps.setString(20, tipo_4);
            ps.setString(21, exento_iva);

            ps.executeUpdate();
            ps.close();
            conexion.commit();
            Clients.showNotification("REGISTRO CREADO <br/> CON EXITO  <br/>");
            conexion.close();
            conexion = null;
            System.out.println("Conexion Cerrada" + conexion);

        } catch (SQLException e) {
            conexion.rollback();
            conexion.close();
            conexion = null;
            Clients.showNotification("ERROR AL INSERTAR <br/> <br/> REGISTROS! <br/> " + e.getMessage().toString(),
                    "warning", null, "middle_center", 0);
        }
    }

    public void REGupdate(String codigo_cliente, String codigo_agrupacion, String nit, String nombre_comercial, String razon, String direccion, String direccion_cobro, String direccion_puerto, String telefonos, String telefonos_puerto, String numero_fax, String numero_fax_puer, String direccion_email, String comentario_1, String comentario_2, String comentario_3, String tipo_1, String tipo_2, String tipo_3, String tipo_4, String exento_iva) throws SQLException {
        try {
            conexion = cnn.Conexion();
            conexion.setAutoCommit(false);
            System.out.println("ACTUALIZAR DATOS..!");
            System.out.println("Actualizar " + codigo_cliente);
            st = conexion.createStatement();

            st.executeUpdate("UPDATE epqop.if_clientes"
                    + "SET codigo_cliente = '" + codigo_cliente + "',"
                    + "codigo_agrupacion'" +codigo_agrupacion+"',"
                    + "nit = '" + nit + "',"
                    + "nombre_comercial = '" + nombre_comercial + "',"
                    + "razon = '" + razon + "',"
                    + "direccion = '" + direccion + "',"
                    + "direccion_cobro = '" + direccion_cobro + "',"
                    + "direccion_puerto = '" + direccion_puerto + "',"
                    + "telefonos = '" + telefonos + "',"
                    + "telefonos_puerto = '" + telefonos_puerto + "' "
                    + "numero_fax = '" + numero_fax + "' "
                    + "numero_fax_puer = '" + numero_fax_puer + "' "
                    + "direccion_email = '" + direccion_email + "' "
                    + "comentario_1 = '" + comentario_1 + "' "
                    + "comentario_2 = '" + comentario_2 + "' "
                    + "comentario_3 = '" + comentario_3 + "' "
                    + "tipo1 = '" + tipo_1 + "' "
                    + "tipo_2 = '" + tipo_2 + "' "
                    + "tipo_3 = '" + tipo_3 + "' "
                    + "tipo = '" + tipo_4 + "' "
                    + "exento_iva = '" + exento_iva + "' "
                    + "WHERE codigo_cliente = '" + codigo_cliente + "' ");

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

}
