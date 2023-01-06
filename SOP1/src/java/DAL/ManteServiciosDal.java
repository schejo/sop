/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAL;

import Conexion.Conexion;
import MD.ManteServiciosMd;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

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
                + "FROM EPQOP.IF_BQ_BUQUES B, EPQOP.IF_BQ_ARRIBOS A\n"
                + "WHERE B.BUQUE = A.BUQUE \n"
                + "AND A.ANO_ARRIBO ='" + ano + "'\n"
                + "AND A.NUM_ARRIBO ='" + arribo + "'";
//                + "select identif_contenedor,peso_tara_contened, nvl(pesaje_1,0), nvl(pesaje_2,0),peso_bruto,peso_tara,"
//                + "to_char(fecha_pesaje_1,'dd/mm/yyyy hh24:mi'), \n"
//                + "to_char(fecha_pesaje_2,'dd/mm/yyyy hh24:mi'),ticket1,ticket2,num_bascula_1,num_bascula_2,nvl(empresa_bascula_1,0),"
//                + "nvl(empresa_bascula_2,0),indicador_tabla, nvl(ident_cuadrilla,0),correlativo9,nvl(hist_act,0)\n"
//                + "from epqop.if_cm_pesaje_camio where fecha_ciclo= to_date('" + ano + "','dd/mm/yyyy') and num_ciclo='" + arribo + "'";

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

}
