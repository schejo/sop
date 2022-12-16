package DAL;

import Conexion.Conexion;
import MD.RendimientosGranelesMd;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.zk.ui.util.Clients;

public class RendimientosGranelesDal {

    private Connection conexion = null;
    private Conexion cnn = new Conexion();
    PreparedStatement ps = null;
    Statement st = null;
    ResultSet rs = null;
    RendimientosGranelesMd cl = new RendimientosGranelesMd();
    
    
       public List<RendimientosGranelesMd> tipoactRSelect() throws SQLException {
        List<RendimientosGranelesMd> alltipoact = new ArrayList<RendimientosGranelesMd>();
        String query = "SELECT codigo_prod ,nvl(nom_prod,'s/d') FROM epqop.if_bq_plan_tprod";
//                + "select trim(num_actividad1),"
//                + "trim(nom_actividad) "
//                + " from epqop.if_bq_activ_buque order by num_actividad1 asc ";
        try {
            conexion = cnn.Conexion();
            st = conexion.createStatement();
            rs = st.executeQuery(query);
            RendimientosGranelesMd rg;
            while (rs.next()) {
                rg = new RendimientosGranelesMd();
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
            Clients.showNotification("ERROR AL CONSULTAR  (Rselect) <br/> <br/> REGISTROS! <br/> " + e.getMessage().toString(),
                    "warning", null, "middle_center", 0);
        }
        return alltipoact;
    }

    public RendimientosGranelesMd Rendimientos(String anio, String num) {
        Statement st = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String id = "";
        int resp = 0;
        cl = new RendimientosGranelesMd();

        String query0 = "SELECT  b.ano_arribo,b.num_arribo,a.nom_buque,   \n"
                + "        e.nom_naviera, d.nombre_particular AS ESTIBADORA,\n"
                + "        h.tipo_terminal,\n"
                + "        TO_CHAR(b.fecha_atraque,'DD/MM/YYYY')||' '||TO_CHAR(b.hora_atraque,'HH24:MI:SS')AS FECHA_ATRAQUE, \n"
                + "        TO_CHAR(b.fecha_zarpe,'DD/MM/YYYY')||' '||TO_CHAR(b.hora_zarpe,'HH24:MI:SS')AS FECHA_ZARPE, \n"
                + "        a.cant_gruas \n"
                + "FROM      epqop.if_bq_buques       a,    \n"
                + "          epqop.if_bq_arribos      b,    \n"
                + "          epqop.if_bq_paises       c,    \n"
                + "          epqop.particulares       d,    \n"
                + "          epqop.if_bq_naviera      e,    \n"
                + "          epqop.if_bq_lineas       f,    \n"
                + "          epqop.if_bq_lineas_arrib g,\n"
                + "          epqop.if_bq_atracaderos  h \n"
                + "WHERE     a.buque = b.buque     \n"
                + "AND       a.bandera = c.pais    \n"
                + "AND       b.estibadora = d.codigo_particular    \n"
                + "AND       e.naviera1 = g.naviera1    \n"
                + "AND       f.linea1 = g.linea1 \n"
                + "AND       b.num_atracadero1 = h.num_atracadero1  \n"
                + "AND       g.ano_arribo = " + anio + "\n"
                + "AND       g.num_arribo = " + num + "\n"
                + "AND       d.tipo_particular = 'A'\n"
                + "AND       b.ano_arribo = " + anio + "\n"
                + "AND       b.num_arribo = " + num + " ";

        try {
            conexion = cnn.Conexion();

            st = conexion.createStatement();
            rs = st.executeQuery(query0);
            while (rs.next()) {
                resp = 1;
                cl.setAnio(rs.getString(1));
                cl.setArribo(rs.getString(2));
                cl.setNombre_buque(rs.getString(3));
                cl.setAg_naviera(rs.getString(4));
                cl.setEstibadora(rs.getString(5));
                cl.setMuelle(rs.getString(6));
                cl.setFecha_atraque(rs.getString(7));
                cl.setFecha_zarpe(rs.getString(8));
                cl.setGruas_buque(rs.getString(9));
                
                
                cl.setResp("1");
                cl.setMsg("ACTUALIZAR DATOS.!");
            }
            st.close();
            rs.close();

            if (resp == 0) {

                cl.setResp("0");
                cl.setMsg("NUMERO DE ARRIBO O BUQUE <br/>  <br/> NO EXISTE ");

            }
            conexion.close();
            cnn.desconectar();

        } catch (Exception e) {
            System.out.println("ERROR CATCH.: " + e.getMessage());
            cl.setResp("0");
            cl.setMsg(e.getMessage());

        }

        return cl;
    }

}
