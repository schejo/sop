package DAL;

import Conexion.Conexion;
import MD.ManteActiviMd;
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
 * @author Informatica
 */
public class ManteActiviDal {

    private Connection conn = null;
    private Conexion obtener = new Conexion();

    ManteActiviMd cl = new ManteActiviMd();
    private Connection conexion = null;
    private Conexion cnn = new Conexion();
    PreparedStatement ps = null;
    Statement st = null;
    ResultSet rs = null;

    public List<ManteActiviMd> atracaderoRSelect() throws SQLException {
        List<ManteActiviMd> allatracadero = new ArrayList<ManteActiviMd>();

        String query = "SELECT TRIM(num_atracadero1),"
                + "            TRIM(tipo_terminal) "
                + " FROM epqop.if_bq_atracaderos "
                + " ORDER BY num_atracadero1 ASC ";
        try {
            conexion = cnn.Conexion();
            st = conexion.createStatement();
            rs = st.executeQuery(query);
            ManteActiviMd rg;
            while (rs.next()) {
                rg = new ManteActiviMd();
                rg.setCod_atracadero(rs.getString(1));
                rg.setNom_atracadero(rs.getString(2));
                allatracadero.add(rg);
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
            Clients.showNotification("ERROR AL CONSULTAR ATRACADERO (Rselect) <br/> <br/> REGISTROS! <br/> " + e.getMessage().toString(),
                    "warning", null, "middle_center", 0);
        }
        return allatracadero;
    }

    //ACTIVIDADES
    public ManteActiviMd savePro(ManteActiviMd data) throws ClassNotFoundException, SQLException {
        Statement st = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String id = "";
        int resp = 0;
        cl = new ManteActiviMd();
        String query1 = " SELECT MAX(correlativo2)+1 AS id FROM epqop.if_bq_reg_activida"
                + " WHERE ano_arribo = " + data.getAno_arribo() + " AND num_arribo = " + data.getNum_arribo() + " ";

        String sql = " INSERT INTO epqop.if_bq_reg_activida (ano_arribo,num_arribo,correlativo2,num_actividad1,fecha_act,hora_act,calado_proa,calado_medio,calado_popa,num_atracadero3,fecha_alta,usuario,hora_alta) \n"
                  + "  VALUES (?,?,?,?,TO_DATE(?,'dd/mm/yyyy hh24:mi' ),TO_DATE(?,'dd/mm/yyyy hh24:mi'),?,?,?,?,sysdate,?,sysdate)";

        try {
            conn = obtener.Conexion();
            conn.setAutoCommit(false);

            st = conn.createStatement();
            ps = conn.prepareStatement(sql);

            rs = st.executeQuery(query1);
            while (rs.next()) {
                id = rs.getString("id");
            }
            st.close();
            rs.close();

            ps.setString(1, data.getAno_arribo());
            ps.setString(2, data.getNum_arribo());
            ps.setString(3, id);
            ps.setString(4, data.getNumAct());
            ps.setString(5, data.getFecha());
            ps.setString(6, data.getFecha());
            ps.setString(7, data.getCaPro());
            ps.setString(8, data.getCaMedio());
            ps.setString(9, data.getCaPopa());
            ps.setString(10, data.getNumAtra());
            ps.setString(11, data.getUsuario());

            ps.executeUpdate();
            ps.close();
            conn.commit();

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

    public List<ManteActiviMd> tipoactRSelect() throws SQLException {
        List<ManteActiviMd> alltipoact = new ArrayList<ManteActiviMd>();
        
        String query = "SELECT TRIM(num_actividad1),"
                + "             TRIM(nom_actividad) "
                + " FROM epqop.if_bq_activ_buque ORDER BY num_actividad1 ASC ";
        try {
            conexion = cnn.Conexion();
            st = conexion.createStatement();
            rs = st.executeQuery(query);
            ManteActiviMd rg;
            while (rs.next()) {
                rg = new ManteActiviMd();
                rg.setNumAct(rs.getString(1));
                rg.setNombreAct(rs.getString(2));
                alltipoact.add(rg);
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
            Clients.showNotification("ERROR AL CONSULTAR TIPO ACTIVIDAD (Rselect) <br/> <br/> REGISTROS! <br/> " + e.getMessage().toString(),
                    "warning", null, "middle_center", 0);
        }
        return alltipoact;
    }

    public ManteActiviMd Mostrardatos(String anio, String numero) {
        Statement st = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String id = "";
        int resp = 0;
        cl = new ManteActiviMd();

        String query0 = "SELECT b.buque, b.nom_buque\n"
                + "FROM   epqop.if_bq_arribos a,\n"
                + "       epqop.if_bq_buques b\n"
                + "WHERE  b.buque = a.buque\n"
                + "AND    a.ano_arribo = " + anio + "\n"
                + "AND    a.num_arribo = " + numero + "";

        try {
            conn = obtener.Conexion();

            st = conn.createStatement();
            rs = st.executeQuery(query0);
            while (rs.next()) {
                resp = 1;
                cl.setNumero_buque(rs.getString(1));
                cl.setNombre_buque(rs.getString(2));

                cl.setResp("1");
                cl.setMsg("ACTUALIZAR DATOS DE CATEDRATICO.!");
            }
            st.close();
            rs.close();

            if (resp == 0) {

                cl.setResp("0");
                cl.setMsg("AÑO ARRIBO O NUMERO DE ARRIBO  <br/>  <br/> NO EXISTE ");

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
