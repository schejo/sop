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
