/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAL;

import Conexion.Conexion;
import MD.cicloCamionMd;
import MD.manCicloCamionMd;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author HP 15
 */
public class cicloCamionDal {

    Conexion obtener = new Conexion();
    Connection conn;
    cicloCamionMd cl = new cicloCamionMd();

    PreparedStatement ps = null;
    Statement st = null;
    ResultSet rs = null;

    public cicloCamionMd REGdelete(String fecha, String ciclo) {
        Statement st = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String id = "";
        int resp = 0;
        cl = new cicloCamionMd();
        try {
            conn = obtener.Conexion();
            conn.setAutoCommit(false);
            int vl = 0;
            st = conn.createStatement();
            vl = st.executeUpdate("DELETE from epqop.if_cm_ciclo_camio WHERE FECHA_CICLO=to_date('" + fecha + "','dd/mm/yyyy')  and num_ciclo='" + ciclo + "'");
                   

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

    public cicloCamionMd MostrarProducto(String fecha, String ciclo) {
        Statement st = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String id = "";
        int resp = 0;
        cl = new cicloCamionMd();
        String query0
                = "select  COD_PAIS_PILOTO,NUM_LICEN_PILOTO,CODIGO_DESTINO,NUM_SECCION_DESTIN,\n"
                + "TIPO_OPERACION2,to_char(FCH_HORA_ING_RECIN,'dd/mm/yyyy hh24:mi'),ANO_ARRIBO3,NUM_ARRIBO3,\n"
                + "NUMERO_PLACA,to_char(FECHA_ALTA,'dd/mm/yyyy hh24:mi'),indicador,\n"
                + "TIPO_VIA,INGRESA_CONTENEDOR1,INGRESA_CONTENEDOR2,INGRESA_CONTENEDOR3,\n"
                + "to_char(FECHA_INICIO_DAT,'dd/mm/yyyy hh24:mi'),to_char(FECHA_FIN_DAT,'dd/mm/yyyy hh24:mi')\n"
                + "from epqop.if_cm_ciclo_camio where FECHA_CICLO=to_date('" + fecha + "','dd/mm/yyyy')  and num_ciclo='" + ciclo + "'";
//                + "select num_transpor, COD_PAIS_PILOTO,NUM_LICEN_PILOTO,CODIGO_DESTINO,\n"
//                + "TIPO_OPERACION2,to_char(FCH_HORA_ING_PARQ,'dd/mm/yyyy hh24:mi'),to_char(FCH_HORA_ING_RECIN,'dd/mm/yyyy hh24:mi'),ANO_ARRIBO3,NUM_ARRIBO3,\n"
//                + "TIENE_SOBREPESO,NUMERO_PLACA,to_char(FECHA_ALTA,'dd/mm/yyyy hh24:mi'),OBSE_CAMION,NUM_PARQUEO,BUQUE,PYD_CODIGO,\n"
//                + "TIPO_VIA,VERIFICA_CONTENEDOR3,INGRESA_CONTENEDOR1,INGRESA_CONTENEDOR2,INGRESA_CONTENEDOR3,\n"
//                + "to_char(FECHA_INICIO_PARQUEO,'dd/mm/yyyy hh24:mi'),to_char(FECHA_FIN_PARQUEO,'dd/mm/yyyy hh24:mi'),to_char(FECHA_INICIO_DAT,'dd/mm/yyyy hh24:mi'),to_char(FECHA_FIN_DAT,'dd/mm/yyyy hh24:mi')\n"
//                + "from epqop.if_cm_ciclo_camio where FECHA_CICLO=to_date('" + fecha + "','dd/mm/yyyy')  and num_ciclo='" + ciclo + "'";

        try {
            conn = obtener.Conexion();

            st = conn.createStatement();
            rs = st.executeQuery(query0);
            while (rs.next()) {
                resp = 1;
                cl.setCodPais(rs.getString(1));
                cl.setLicencia(rs.getString(2));
                cl.setCodDesti(rs.getString(3));
                cl.setSeccDesti(rs.getString(4));
                cl.setTipOpera(rs.getString(5));
                cl.setfIngRec(rs.getString(6));
                cl.setAnoArribo(rs.getString(7));
                cl.setNumArribo(rs.getString(8));
                cl.setNumPlaca(rs.getString(9));
                cl.setfAlta(rs.getString(10));
                cl.setIndicador(rs.getString(11));
                cl.setTipoVia(rs.getString(12));
                ///aqui van los 6
                cl.setIngCont1(rs.getString(13));
                cl.setIngCont2(rs.getString(14));
                cl.setIngCont3(rs.getString(15));
                cl.setfIniDat(rs.getString(16));
                cl.setfFinDat(rs.getString(17));

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

    public cicloCamionMd updatePro(cicloCamionMd data) {
        Statement st = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String id = "";
        int resp = 0;
        cl = new cicloCamionMd();
        try {
            conn = obtener.Conexion();
            conn.setAutoCommit(false);
            int vl = 0;
            st = conn.createStatement();
            vl = st.executeUpdate("update epqop.if_cm_ciclo_camio set COD_PAIS_PILOTO='" + data.getCodPais() + "',"
                    + "NUM_LICEN_PILOTO='" + data.getLicencia() + "',CODIGO_DESTINO='" + data.getCodDesti() + "'"
                    + ",NUM_SECCION_DESTIN='" + data.getSeccDesti() + "',\n"
                    + "TIPO_OPERACION2='" + data.getTipOpera() + "',FCH_HORA_ING_RECIN=to_date('" + data.getfIngRec() + "','dd/mm/yyyy hh24:mi' ),"
                    + "ANO_ARRIBO3='" + data.getAnoArribo() + "',NUM_ARRIBO3='" + data.getNumArribo() + "',\n"
                    + "NUMERO_PLACA='" + data.getNumPlaca() + "',FECHA_ALTA=to_date('" + data.getfAlta() + "','dd/mm/yyyy hh24:mi' ),"
                    + "indicador='" + data.getIndicador() + "',\n"
                    + "TIPO_VIA='" + data.getTipoVia() + "',INGRESA_CONTENEDOR1='" + data.getIngCont1() + "',INGRESA_CONTENEDOR2='" + data.getIngCont2() + "'"
                    + ",INGRESA_CONTENEDOR3='" + data.getIngCont3() + "',\n"
                    + "FECHA_INICIO_DAT=to_date('" + data.getfIniDat() + "','dd/mm/yyyy hh24:mi' ),FECHA_FIN_DAT=to_date('" + data.getfFinDat() + "','dd/mm/yyyy hh24:mi' )\n"
                    + "where FECHA_CICLO=to_date('" + data.getFechaCiclo() + "','dd/mm/yyyy')  and num_ciclo='" + data.getCiclom() + "'");

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
