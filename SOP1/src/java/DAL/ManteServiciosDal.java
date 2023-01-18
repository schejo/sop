package DAL;

import Conexion.Conexion;
import MD.ManteServiciosMd;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.zk.ui.util.Clients;

/**
 *
 * @author HP 15
 */
public class ManteServiciosDal {

    Conexion obtener = new Conexion();
    Connection conn;
    ManteServiciosMd cl = new ManteServiciosMd();

    PreparedStatement ps = null;
    Statement st = null;
    ResultSet rs = null;

    public ManteServiciosMd MostrarProducto(String ano, String arribo) {
        Statement st = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String id = "";
        int resp = 0;
        cl = new ManteServiciosMd();
        String query0
                = "SELECT B.BUQUE CODIGO,UPPER(TRIM(B.NOM_BUQUE)) NOMBRE \n"
                + "FROM EPQOP.IF_BQ_BUQUES B,"
                + "     EPQOP.IF_BQ_ARRIBOS A\n"
                + "WHERE B.BUQUE = A.BUQUE \n"
                + "AND A.ANO_ARRIBO ='" + ano + "'\n"
                + "AND A.NUM_ARRIBO ='" + arribo + "'";
        try {
            conn = obtener.Conexion();

            st = conn.createStatement();
            rs = st.executeQuery(query0);
            while (rs.next()) {
                resp = 1;
                cl.setNumBuque(rs.getString(1));
                cl.setNomBuque(rs.getString(2));

                cl.setResp("1");
                cl.setMsg("ACTUALIZAR!");
            }
            st.close();
            rs.close();

            if (resp == 0) {

                cl.setResp("0");
                cl.setMsg(" NO EXISTE <br/> EL ARRIBO <br/> VALIDE INFORMACION");

            }
            conn.close();
            obtener.desconectar();

        } catch (Exception e) {
            System.out.println("ERROR CATCH.: " + e.getMessage());
            cl.setResp("0");
            cl.setMsg(e.getMessage());

        }

        return cl;
    }

    public ManteServiciosMd updatePro(ManteServiciosMd data) {
        Statement st = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String id = "";
        int resp = 0;
        cl = new ManteServiciosMd();
        try {
            conn = obtener.Conexion();
            conn.setAutoCommit(false);
            int vl = 0;
            st = conn.createStatement();
            vl = st.executeUpdate(" update epqop.if_bq_servicios  set codigo_particular='" + data.getCod_particular()+ "',"
                    + " CODIGO_SERVICIO='" + data.getCod_servicio()+ "', FECHA_INICIO1=to_date('" + data.getFechaInicio()+ "','dd/mm/yyyy hh24:mi'),\n"
                    + " HORA_INICIO1=to_date('" + data.getFechaInicio()+ "','dd/mm/yyyy hh24:mi' ),FECHA_FIN1=to_date('" + data.getFechaFin()+ "','dd/mm/yyyy hh24:mi' ),\n"
                    + " HORA_FIN2=to_date('" + data.getFechaFin()+ "','dd/mm/yyyy hh24:mi' ),numero_factura='" + data.getBoleta()+ "',FECHA_ALTA=sysdate,\n"
                    + " USUARIO='" + data.getUsuario()+ "', OBSE_SERVICIO='" + data.getObs()+ "', COD_CLI_FACT='" + data.getCod_cliente()+ "'  where  ano_arribo='" + data.getAnoArri()+ "' "
                    + "AND num_arribo='" + data.getNumArri()+ "' and correlativo='" + data.getCorrelativo()+ "' ");
//                    + "UPDATE productos SET "
//                    + "pro_descripcion='" + data.getDescripcion() + "',"
//                    + "pro_tipo='" + data.getTipo_pro() + "',pro_tipo_servicio='" + data.getTipo_ser() + "',pro_marca='" + data.getMarca()
//                    + "',pro_presentacion='" + data.getPresentacion() + "',pro_precio_compra='" + data.getPre_compra() + "',pro_precio_venta='" + data.getPre_venta()
//                    + "',pro_descuento='" + data.getDescuento() + "',pro_stock_barrita='" + data.getPro_stock_Barrita()
//                    + "',pro_conversion='" + data.getPro_conver() + "',pro_medida='" + data.getMedi_pro()
//                    + "',pro_minimo='" + data.getMinimo() + "',pro_maximo='" + data.getMaximo() + "'"
//                    + ",pro_ubicacion='" + data.getUbicacion() + "'"
//                    + ",pro_ferreteria='1',pro_fecha_modifica=NOW(),pro_usuario_modifica='" + data.getUsuario() + "' "
//                    + "where pro_id='" + data.getCodigo() + "' ");
            if (vl > 0) {
                cl.setResp("1");
                cl.setMsg("DATOS ACTUALIZADOS CORRECTAMENTE");
                System.out.println("Actualizacion Exitosa");
            } else {
                cl.setResp("0");
                cl.setMsg("DATOS NO ACTUALIZADOS");
                System.out.println("Actualizacion Fallida");
            }
            st.close();

            conn.commit();
            conn.close();
            obtener.desconectar();

        } catch (Exception e) {
            System.out.println("ERROR CATCH.: " + e.getMessage());
            cl.setResp("0");
            cl.setMsg(e.getMessage());

        }

        return cl;
    }

    public ManteServiciosMd saveIngreso(ManteServiciosMd data) throws ClassNotFoundException, SQLException {
        Statement st = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String corre = "";
        int resp = 0;
        cl = new ManteServiciosMd();

        String query1 = "select correlativo+1 AS corre from epqop.if_bq_servicios where"
                + " ano_arribo='" + data.getAnoArri() + "' AND num_arribo='" + data.getNumArri() + "'"
                + " and rownum=1 ORDER BY correlativo desc";

        String sql = "insert into epqop.if_bq_servicios (ano_arribo,\n"
                + " num_arribo,correlativo,CODIGO_PARTICULAR,CODIGO_SERVICIO,\n"
                + " FECHA_INICIO1,HORA_INICIO1,FECHA_FIN1,HORA_FIN2,NUMERO_FACTURA,FECHA_ALTA,\n"
                + " usuario,obse_servicio,cod_cli_fact\n"
                + ")values (?,?,?\n"
                + "  ,?,?,to_date(?,'dd/mm/yyyy hh24:mi'),to_date(?,'dd/mm/yyyy hh24:mi' ),"
                + "to_date(?,'dd/mm/yyyy hh24:mi'),to_date(?,'dd/mm/yyyy hh24:mi'),\n"
                + "  ?,sysdate,?,?,?)";
//                + " INSERT INTO "
//                + "epqop.if_cm_camiones_ent_sal "
//                + "(fecha_ciclo,"
//                + " num_ciclo,\n"
//                + " num_licen_piloto,\n"
//                + " fch_hora_ing_recin,\n"
//                + " numero_contenedor_ent,\n"
//                + " numero_placa,\n"
//                + " fecha_alta,\n"
//                + " obse_camion,\n"
//                + " garita_entrada,\n"
//                + " usuario_ent,\n"
//                + " maquina,\n"
//                + " marchamo_entrada,\n"
//                + " numero_poliza_ent,"
//                + " estatus_entrada,"
//                + " cod_pais_piloto,estatus_ent_sal,revision_camion) \n"
//                + "  VALUES (TO_date(? , 'dd/mm/yyyy'),?,?,sysdate,?,?,sysdate,?,?,?,?,?,?,?,?,'1','0')";

        try {
            conn = obtener.Conexion();
            conn.setAutoCommit(false);

            st = conn.createStatement();
            rs = st.executeQuery(query1);
            while (rs.next()) {
                corre = rs.getString("corre");
            }
            st.close();
            rs.close();

            ps = conn.prepareStatement(sql);
            ps.setString(3, corre);

            ps.setString(1, data.getAnoArri());
            ps.setString(2, data.getNumArri());
            ps.setString(4, data.getCod_particular());

            ps.setString(5, data.getCod_servicio());
            ps.setString(6, data.getFechaInicio());
            ps.setString(7, data.getHoraInicio());
            ps.setString(8, data.getFechaFin());
            ps.setString(9, data.getHoraFin());
            ps.setString(10, data.getBoleta());
            ps.setString(11, data.getUsuario());
            ps.setString(12, data.getObs());
            ps.setString(13, data.getCod_cliente());

            ps.executeUpdate();
            ps.close();
            conn.commit();
//            cl.setCodigo(id);
            cl.setResp("1");
            cl.setMsg("REGISTRO GUARDADO CORRECTAMENTE");

            conn.close();
            obtener.desconectar();

        } catch (SQLException e) {
            System.out.println("EXCEPTION..: " + e.getMessage());
            cl.setResp("0");
            cl.setMsg(e.getMessage());
        }

        return cl;
    }

    public ManteServiciosMd OpteCorre(String ano, String arribo) {
        Statement st = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String id = "";
        int resp = 0;
        cl = new ManteServiciosMd();
        String query0
                = "select correlativo+1 corre from epqop.if_bq_servicios where"
                + " ano_arribo='" + ano + "' AND num_arribo='" + arribo + "' and rownum=1 ORDER BY correlativo desc";
//                + "SELECT B.BUQUE CODIGO,UPPER(TRIM(B.NOM_BUQUE)) NOMBRE \n"
//                + "FROM EPQOP.IF_BQ_BUQUES B,"
//                + "     EPQOP.IF_BQ_ARRIBOS A\n"
//                + "WHERE B.BUQUE = A.BUQUE \n"
//                + "AND A.ANO_ARRIBO ='" + ano + "'\n"
//                + "AND A.NUM_ARRIBO ='" + arribo + "'";
        try {
            conn = obtener.Conexion();

            st = conn.createStatement();
            rs = st.executeQuery(query0);
            while (rs.next()) {
                resp = 1;
                cl.setCorrelativo(rs.getString(1));

                cl.setResp("1");
                cl.setMsg("ACTUALIZAR!");
            }
            st.close();
            rs.close();

            if (resp == 0) {

                cl.setResp("0");
                cl.setMsg(" NO EXISTE <br/> EL ARRIBO <br/> VALIDE INFORMACION");

            }
            conn.close();
            obtener.desconectar();

        } catch (Exception e) {
            System.out.println("ERROR CATCH.: " + e.getMessage());
            cl.setResp("0");
            cl.setMsg(e.getMessage());

        }

        return cl;
    }

    public List<ManteServiciosMd> Servicios() throws SQLException {
        List<ManteServiciosMd> allservicios = new ArrayList<ManteServiciosMd>();

        String query = "SELECT TRIM(codigo_servicio), TRIM(descripcion_servic)\n"
                + "FROM   epqop.if_ca_tarifas\n"
                + "WHERE  tipo_particular_2 = '1'\n"
                + "ORDER BY codigo_servicio ASC";

        try {
            conn = obtener.Conexion();
            st = conn.createStatement();
            rs = st.executeQuery(query);
            ManteServiciosMd rg;
            while (rs.next()) {
                rg = new ManteServiciosMd();

                rg.setCod_servicio(rs.getString(1));
                rg.setNom_servicio(rs.getString(2));

                allservicios.add(rg);
            }

            st.close();
            rs.close();
            conn.close();
            conn = null;
        } catch (SQLException e) {
            conn.close();
            conn = null;
            Clients.showNotification("ERROR AL CONSULTAR (Servicios) <br/> <br/> REGISTROS! <br/> " + e.getMessage().toString(),
                    "warning", null, "middle_center", 0);
        }
        return allservicios;
    }

    public List<ManteServiciosMd> Particulares() throws SQLException {
        List<ManteServiciosMd> allparticulares = new ArrayList<ManteServiciosMd>();

        String query = "SELECT codigo_particular, TRIM(nombre_particular)\n"
                + "FROM epqop.particulares \n"
                + "WHERE estatus = 1\n"
                + "ORDER BY codigo_particular ASC";

        try {
            conn = obtener.Conexion();
            st = conn.createStatement();
            rs = st.executeQuery(query);
            ManteServiciosMd rg;
            while (rs.next()) {
                rg = new ManteServiciosMd();

                rg.setCod_particular(rs.getString(1));
                rg.setNom_particular(rs.getString(2));

                allparticulares.add(rg);
            }

            st.close();
            rs.close();
            conn.close();
            conn = null;
        } catch (SQLException e) {
            conn.close();
            conn = null;
            Clients.showNotification("ERROR AL CONSULTAR (Particulares) <br/> <br/> REGISTROS! <br/> " + e.getMessage().toString(),
                    "warning", null, "middle_center", 0);
        }
        return allparticulares;
    }

    public List<ManteServiciosMd> Clientes() throws SQLException {
        List<ManteServiciosMd> allclientes = new ArrayList<ManteServiciosMd>();

        String query = "SELECT codigo_cliente, TRIM(nombre_comercial)\n"
                + "FROM   epqop.if_clientes \n"
                + "ORDER BY codigo_cliente ASC";
        try {
            conn = obtener.Conexion();
            st = conn.createStatement();
            rs = st.executeQuery(query);
            ManteServiciosMd rg;
            while (rs.next()) {
                rg = new ManteServiciosMd();

                rg.setCod_cliente(rs.getString(1));
                rg.setNom_cliente(rs.getString(2));

                allclientes.add(rg);
            }

            st.close();
            rs.close();
            conn.close();
            conn = null;
        } catch (SQLException e) {
            conn.close();
            conn = null;
            Clients.showNotification("ERROR AL CONSULTAR (Particulares) <br/> <br/> REGISTROS! <br/> " + e.getMessage().toString(),
                    "warning", null, "middle_center", 0);
        }
        return allclientes;
    }
}
