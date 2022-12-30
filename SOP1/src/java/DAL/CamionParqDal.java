package DAL;

import Conexion.Conexion;
import MD.CamionParqMd;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author Informatica
 */
public class CamionParqDal {

    private Connection conn = null;
    private Conexion obtener = new Conexion();
    PreparedStatement ps = null;
    Statement st = null;
    ResultSet rs = null;
    CamionParqMd cl = new CamionParqMd();

    public CamionParqMd MostrarProducto(String placa) {
        Statement st = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int resp = 0;
        cl = new CamionParqMd();

        String query0 = "SELECT codigo_pais1,num_placa_camion,cod_pais_piloto,\n"
                + "       num_licen_piloto,tipo_operacion2,fch_hora_ing_parq,\n"
                + "       naviera3,obse_camio1,codigo_destino,\n"
                + "       fecha_inicial,fecha_final,ubica_camion,numero_contenedor\n"
                + "FROM epqop.if_cm_camion_parq\n"
                + "WHERE num_placa_camion = ' " + placa + "' ";
        try {
            conn = obtener.Conexion();

            st = conn.createStatement();
            rs = st.executeQuery(query0);
            while (rs.next()) {
                resp = 1;
//                cl.setPlaca(rs.getString(2));
                cl.setCod_pais(rs.getString(2));
                cl.setPais_piloto(rs.getString(3));
                cl.setLicencia(rs.getString(4));
                cl.setTipo_opera(rs.getString(5));
                cl.setFecha_ing_parqueo(rs.getString(6));
                cl.setNaviera(rs.getString(7));
                cl.setObservaciones(rs.getString(8));
                cl.setCod_destino(rs.getString(9));
                cl.setFecha_inicio(rs.getString(10));
                cl.setFecha_fin(rs.getString(11));
                cl.setUbic_camion(rs.getString(12));
                cl.setNum_contene(rs.getString(13));

                cl.setResp("1");

                cl.setMsg("Actualizar Datos de el camion con la placa.!" + cl.getPlaca() + "");
            }
            st.close();
            rs.close();

            if (resp == 0) {

                cl.setResp("0");
                cl.setMsg("NUMERO DE PLACA <br/>  <br/> NO EXISTE ");

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
