/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAL;

import Conexion.Conexion;
import MD.manCicloCamionMd;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author HP 15
 */
public class manCicloCamionDal {

    Conexion obtener = new Conexion();
    Connection conn;
    manCicloCamionMd cl = new manCicloCamionMd();
  
    PreparedStatement ps = null;
    Statement st = null;
    ResultSet rs = null;

    public manCicloCamionMd MostrarProducto(String fecha, String ciclo) {
        Statement st = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String id = "";
        int resp = 0;
        cl = new manCicloCamionMd();
        String query0
                = "select identif_contenedor,peso_tara_contened, nvl(pesaje_1,0), nvl(pesaje_2,0),peso_bruto,peso_tara,"
                + "to_char(fecha_pesaje_1,'dd/mm/yyyy hh24:mi'), \n"
                + "to_char(fecha_pesaje_2,'dd/mm/yyyy hh24:mi'),ticket1,ticket2,num_bascula_1,num_bascula_2,nvl(empresa_bascula_1,0),"
                + "nvl(empresa_bascula_2,0),indicador_tabla, nvl(ident_cuadrilla,0),correlativo9,nvl(hist_act,0)\n"
                + "from epqop.if_cm_pesaje_camio where fecha_ciclo= to_date('" + fecha + "','dd/mm/yyyy') and num_ciclo='" + ciclo + "'";
//                + "SELECT pro_descripcion,pro_tipo,pro_tipo_servicio,pro_marca,pro_presentacion,pro_precio_compra,pro_precio_venta,\n"
//                + "pro_descuento,pro_stock_barrita,pro_stock_Carrizal,pro_stock_angeles,pro_conversion,pro_medida,pro_minimo,pro_maximo,pro_ubicacion,pro_ferreteria\n"
//                + "from  productos\n"
//                + " where pro_id='" + producto + "';";
        try {
            conn = obtener.Conexion();

            st = conn.createStatement();
            rs = st.executeQuery(query0);
            while (rs.next()) {
                resp = 1;
                cl.setIdeContenedor(rs.getString(1));
                cl.setPesoTaraCont(rs.getString(2));
                cl.setPesaje1(rs.getString(3));
                cl.setPesaje2(rs.getString(4));
                cl.setPesoBruto(rs.getString(5));
                cl.setPesoTara(rs.getString(6));
                cl.setFechaPesa1(rs.getString(7));
                cl.setFechaPesa2(rs.getString(8));
                cl.setTicket1(rs.getString(9));
                cl.setTicket2(rs.getString(10));
                cl.setBascula1(rs.getString(11));
                cl.setBascula2(rs.getString(12));
                ///aqui van los 6
                cl.setEmpreBas1(rs.getString(13));
                cl.setEmpreBas2(rs.getString(14));
                cl.setIndTabla(rs.getString(15));
                cl.setIndCua(rs.getString(16));
                cl.setCorre9(rs.getString(17));
                cl.setTipoAct(rs.getString(18));

                cl.setResp("1");
                cl.setMsg("ACTUALIZAR!");
            }
            st.close();
            rs.close();

            if (resp == 0) {

                cl.setResp("0");
                cl.setMsg(" NO EXISTE <br/> EL CICLO <br/> VALIDE INFORMACION");

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
      public manCicloCamionMd REGdelete(String fecha,String ciclo) {
        Statement st = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String id = "";
        int resp = 0;
        cl = new manCicloCamionMd();
        try {
            conn = obtener.Conexion();
            conn.setAutoCommit(false);
            int vl = 0;
            st = conn.createStatement();
            vl = st.executeUpdate("DELETE from epqop.if_cm_pesaje_camio where fecha_ciclo= to_date('"+fecha+"','dd/mm/yyyy') and num_ciclo='"+ciclo+"'");
//                    + "update epqop.if_cm_pesaje_camio set identif_contenedor='" + data.getIdeContenedor() + "',peso_tara_contened='" + data.getPesoTaraCont() + "'"
//                    + ",pesaje_1='" + data.getPesaje1() + "',pesaje_2='" + data.getPesaje2() + "',peso_bruto='" + data.getPesoBruto() + "'\n"
//                    + ",peso_tara='" + data.getPesoTara() + "',fecha_pesaje_1=to_date('" + data.getFechaPesa1() + "','dd/mm/yyyy hh24:mi' ),"
//                    + "fecha_pesaje_2=to_date('" + data.getFechaPesa2() + "','dd/mm/yyyy hh24:mi' )\n"
//                    + ",ticket1='" + data.getTicket1() + "',ticket2='" + data.getTicket2() + "',num_bascula_1='" + data.getBascula1() + "',"
//                    + "num_bascula_2='" + data.getBascula2() + "',empresa_bascula_1='" + data.getEmpreBas1() + "',empresa_bascula_2='" + data.getBascula2() + "',\n"
//                    + "indicador_tabla='" + data.getIndTabla() + "',ident_cuadrilla='" + data.getIndCua() + "',correlativo9='" + data.getCorre9() + "',"
//                    + "hist_act='"+data.getTipoAct()+"' where fecha_ciclo= to_date('"+data.getFechaCiclo()+"','dd/mm/yyyy') and num_ciclo='"+data.getCiclom()+"'");
                
            if (vl > 0) {
                cl.setResp("1");
                cl.setMsg("DATO BORRADO CORRECTAMENTE");
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

    public manCicloCamionMd updatePro(manCicloCamionMd data) {
        Statement st = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String id = "";
        int resp = 0;
        cl = new manCicloCamionMd();
        try {
            conn = obtener.Conexion();
            conn.setAutoCommit(false);
            int vl = 0;
            st = conn.createStatement();
            vl = st.executeUpdate("update epqop.if_cm_pesaje_camio set identif_contenedor='" + data.getIdeContenedor() + "',peso_tara_contened='" + data.getPesoTaraCont() + "'"
                    + ",pesaje_1='" + data.getPesaje1() + "',pesaje_2='" + data.getPesaje2() + "',peso_bruto='" + data.getPesoBruto() + "'\n"
                    + ",peso_tara='" + data.getPesoTara() + "',fecha_pesaje_1=to_date('" + data.getFechaPesa1() + "','dd/mm/yyyy hh24:mi' ),"
                    + "fecha_pesaje_2=to_date('" + data.getFechaPesa2() + "','dd/mm/yyyy hh24:mi' )\n"
                    + ",ticket1='" + data.getTicket1() + "',ticket2='" + data.getTicket2() + "',num_bascula_1='" + data.getBascula1() + "',"
                    + "num_bascula_2='" + data.getBascula2() + "',empresa_bascula_1='" + data.getEmpreBas1() + "',empresa_bascula_2='" + data.getBascula2() + "',\n"
                    + "indicador_tabla='" + data.getIndTabla() + "',ident_cuadrilla='" + data.getIndCua() + "',correlativo9='" + data.getCorre9() + "',"
                    + "hist_act='"+data.getTipoAct()+"' where fecha_ciclo= to_date('"+data.getFechaCiclo()+"','dd/mm/yyyy') and num_ciclo='"+data.getCiclom()+"'");
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

}
