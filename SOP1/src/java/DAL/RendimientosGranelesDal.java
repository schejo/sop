package DAL;
//package Dal;
import Conexion.Conexion;
import MD.RendimientosGranelesMd;
//import Modelo.RendimientosGranelesMd;
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

        try {
            conexion = cnn.Conexion();
            st = conexion.createStatement();
            rs = st.executeQuery(query);
            RendimientosGranelesMd rg;
            while (rs.next()) {
                rg = new RendimientosGranelesMd();
                rg.setNumAct(rs.getString(1));
                rg.setNombreAct(rs.getString(2).trim());
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

        String query0 = "SELECT  b.ano_arribo,b.num_arribo,a.nom_buque,   \n" +
"                        e.nom_naviera, d.nombre_particular AS ESTIBADORA,\n" +
"                        h.tipo_terminal,\n" +
"                        TO_CHAR(b.fecha_atraque,'DD/MM/YYYY')||' '||TO_CHAR(b.hora_atraque,'HH24:MI:SS')AS FECHA_ATRAQUE,\n" +
"                        i.tiempo_est_opera, \n" +
"                        TO_CHAR(b.fecha_zarpe,'DD/MM/YYYY')   || ' ' || TO_CHAR(b.hora_zarpe,'HH24:MI:SS') FECHA_ZARPE,\n" +
"                        nvl(j.rsilos,0),  k.nom_prod,--tm_planificadas,directa tm_despachadas, \n" +
"                        j.rcamion,nvl(a.cant_gruas,0), nvl(j.rotros,0), --otros total horas operacion\n" +
"                        i.doc_cant_gruas,TO_CHAR(b.opera_inicio,'DD/MM/YYYY HH24:MI:SS'),TO_CHAR(b.opera_fin,'DD/MM/YYYY HH24:MI:SS'),\n" +
"                        TRUNC(24*(b.opera_fin - opera_inicio)) ||':'||ROUND(60*(24*(b.opera_fin - opera_inicio)- TRUNC(24*(b.opera_fin - opera_inicio)))) as total_horas,round(24*(b.opera_fin - opera_inicio),2) as horas\n" +
"                FROM      epqop.if_bq_buques       a,    \n" +
"                          epqop.if_bq_arribos      b,    \n" +
"                          epqop.if_bq_paises       c,    \n" +
"                          epqop.particulares       d,    \n" +
"                          epqop.if_bq_naviera      e,    \n" +
"                          epqop.if_bq_lineas       f,    \n" +
"                          epqop.if_bq_lineas_arrib g,\n" +
"                          epqop.if_bq_atracaderos  h,\n" +
"                          epqop.if_bq_planifica_conte i,\n" +
"                          epqop.if_bq_plan_cargagen j,\n" +
"                          epqop.if_bq_plan_tprod    k \n" +
"                WHERE     a.buque = b.buque     \n" +
"                AND       a.bandera = c.pais    \n" +
"                AND       b.estibadora = d.codigo_particular    \n" +
"                AND       e.naviera1 = g.naviera1    \n" +
"                AND       f.linea1 = g.linea1 \n" +
"                AND       b.num_atracadero1 = h.num_atracadero1  \n" +
"                AND       b.ano_arribo =   g.ano_arribo     \n" +
"                AND       b.num_arribo =   g.num_arribo  \n" +
"                AND       b.ano_arribo =   i.ano_arrribo     \n" +
"                AND       b.num_arribo =   i.num_arribo \n" +
"                AND       b.ano_arribo =   j.ano_arrribo     \n" +
"                AND       b.num_arribo =   j.num_arribo \n" +
"                AND       j.producto1  =   k.codigo_prod \n" +
"                AND       d.tipo_particular = 'A'    \n" +
"                AND       b.ano_arribo =  " + anio + " \n" +
"                AND       b.num_arribo =  " + num + "";
//        +"SELECT  b.ano_arribo,b.num_arribo,a.nom_buque,   \n"
//                + "        e.nom_naviera, d.nombre_particular AS ESTIBADORA,\n"
//                + "        h.tipo_terminal,\n"
//                + "        TO_CHAR(b.fecha_atraque,'DD/MM/YYYY')||' '||TO_CHAR(b.hora_atraque,'HH24:MI:SS')AS FECHA_ATRAQUE,\n"
//                + "        i.tiempo_est_opera, \n"
//                + "        TO_CHAR(b.fecha_zarpe,'DD/MM/YYYY')   || ' ' || TO_CHAR(b.hora_zarpe,'HH24:MI:SS') FECHA_ZARPE,\n"
//                + "        j.rsilos,  k.nom_prod,--tm_planificadas,directa tm_despachadas, \n"
//                + "        j.rcamion,a.cant_gruas, j.rotros, --otros total horas operacion\n"
//                + "        i.doc_cant_gruas,b.opera_inicio,b.opera_fin--rendimiento hora buque\n"
//                + "FROM      epqop.if_bq_buques       a,    \n"
//                + "          epqop.if_bq_arribos      b,    \n"
//                + "          epqop.if_bq_paises       c,    \n"
//                + "          epqop.particulares       d,    \n"
//                + "          epqop.if_bq_naviera      e,    \n"
//                + "          epqop.if_bq_lineas       f,    \n"
//                + "          epqop.if_bq_lineas_arrib g,\n"
//                + "          epqop.if_bq_atracaderos  h,\n"
//                + "          epqop.if_bq_planifica_conte i,\n"
//                + "          epqop.if_bq_plan_cargagen j,\n"
//                + "          epqop.if_bq_plan_tprod    k \n"
//                + "WHERE     a.buque = b.buque     \n"
//                + "AND       a.bandera = c.pais    \n"
//                + "AND       b.estibadora = d.codigo_particular    \n"
//                + "AND       e.naviera1 = g.naviera1    \n"
//                + "AND       f.linea1 = g.linea1 \n"
//                + "AND       b.num_atracadero1 = h.num_atracadero1  \n"
//                + "AND       b.ano_arribo =   g.ano_arribo     \n"
//                + "AND       b.num_arribo =   g.num_arribo  \n"
//                + "AND       b.ano_arribo =   i.ano_arrribo     \n"
//                + "AND       b.num_arribo =   i.num_arribo \n"
//                + "AND       b.ano_arribo =   j.ano_arrribo     \n"
//                + "AND       b.num_arribo =   j.num_arribo \n"
//                + "AND       j.producto1  =   k.codigo_prod \n"
//                + "AND       d.tipo_particular = 'A'    \n"
//                + "AND       b.ano_arribo = " + anio + "\n"
//                + "AND       b.num_arribo = " + num + " ";

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
                cl.setHrs_plani(rs.getString(8));
                cl.setFecha_zarpe(rs.getString(9));
                cl.setTerpac(rs.getString(10));
                cl.setTipo_producto(rs.getString(11).trim());
                cl.setTm_despachadas(rs.getString(12));
                cl.setGruas_buque(rs.getString(15));
                cl.setOtros(rs.getString(14));
                cl.setGruas_olg(rs.getString(13));
                cl.setInicio_operacion(rs.getString(16));
                cl.setFin_operacion(rs.getString(17));
                cl.setTotal_hrs_operacion(rs.getString(18));
                cl.setHorasOperacion(rs.getString(19));

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
