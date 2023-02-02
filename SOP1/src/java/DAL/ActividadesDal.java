package DAL;

import Conexion.Conexion;
import MD.ActividadesMd;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.zk.ui.util.Clients;

public class ActividadesDal {

    private Connection conexion = null;
    private Conexion cnn = new Conexion();
    PreparedStatement ps = null;
    Statement st = null;
    ResultSet rs = null;

    ActividadesMd cl = new ActividadesMd();

    public List<ActividadesMd> REGselect(String ano, String arribo, String actividad) throws SQLException {
        List<ActividadesMd> allActividades = new ArrayList<ActividadesMd>();

        String query = "SELECT     a.ano_arribo, a.num_arribo,\n"
                + "                                           h.nom_actividad,\n"
                + "                                           b.nombre_particular AS PRACTICO,\n"
                + "                                           a.boleta_prac,TO_CHAR ( a.fecha_piloi, 'dd/mm/yyyy hh24:mi'), TO_CHAR(a.fecha_pilof, 'dd/mm/yyyy hh24:mi'), \n"
                + "                                           c.nombre_particular AS REMOLCADOR,\n"
                + "                                           a.boleta_rem1, TO_CHAR ( a.fecha_rem1i, 'dd/mm/yyyy hh24:mi'), TO_CHAR (a.fecha_rem1f, 'dd/mm/yyyy hh24:mi'),\n"
                + "                                           d.nombre_particular AS REMOLCADOR2,\n"
                + "                                           a.boleta_rem2, TO_CHAR(a.fecha_rem2i, 'dd/mm/yyyy hh24:mi'), TO_CHAR(a.fecha_rem2f,'dd/mm/yyyy hh24:mi'),\n"
                + "                                           e.nombre_particular AS REMOLCADOR3,\n"
                + "                                           a.boleta_rem3, TO_CHAR(a.fecha_rem3i,'dd/mm/yyyy hh24:mi'), TO_CHAR( a.fecha_rem3f,'dd/mm/yyyy hh24:mi'),\n"
                + "                                           f.nombre_particular  AS LANCHA_PILOTO,\n"
                + "                                           a.boleta_lancha, TO_CHAR(a.fecha_lan1i,'dd/mm/yyyy hh24:mi'), TO_CHAR( a.fecha_lan1f,'dd/mm/yyyy hh24:mi'),\n"
                + "                                           g.nombre_particular,\n"
                + "                                           a.boleta_lanad,\n"
                + "                                           a.obse_actividad1,"
                + "                                    DECODE(a.codigo_fondeos,'1','ATRAQUE SIN NOVEDAD','2','ZARPE SIN NOVEDAD','3','MOVIMIENTO INTERNO REMUNERADO','4',\n"
                + "                                           'ZARPE A FONDEO','5','FONDEO A SOLICITUD DE NAVIERO','6','FONDEO POR FALTA DE ESPACIO','7','FONDEO EN ESPERA DE TERMINAL ESPECIALIZADA',\n"
                + "                                           '8','FONDEO POR TIPO DE CARGA','9','MOVIMIENTO INTERNO NO REMUNERADO','10','MOVIMIENTO POR BAJO RENDIMIENTO','11','A CONVENIENCIA DE PUERTO QUETZAL') AS FONDEO\n"
                + "                                FROM     epqop.if_bq_reg_activida a,\n"
                + "                                         epqop.particulares b,\n"
                + "                                         epqop.particulares c,\n"
                + "                                         epqop.particulares d,\n"
                + "                                         epqop.particulares e,\n"
                + "                                         epqop.particulares f,\n"
                + "                                         epqop.particulares g,\n"
                + "                                         epqop.if_bq_activ_buque h,\n"
                + "                                         epqop.if_bq_atracaderos i\n"
                + "                                WHERE   a.practico         = b.codigo_particular (+)\n"
                + "                                AND     a.remolcador       = c.codigo_particular (+)\n"
                + "                                AND     a.remolcador2      = d.codigo_particular (+)\n"
                + "                                AND     a.remolcador3      = e.codigo_particular(+)\n"
                + "                                AND     a.lancha_piloto    = f.codigo_particular(+)\n"
                + "                                AND     a.lancha_almirante = g.codigo_particular (+)\n"
                + "                                AND     a.num_actividad1   = h.num_actividad1  (+)\n"
                + "                                AND     a.num_atracadero3  = i.num_atracadero1   (+)\n"
                + "                                AND     a.ano_arribo = '" + ano + "' \n"
                + "                                AND     a.num_arribo = '" + arribo + "'\n"
                + "                                AND     a.num_actividad1 = '" + actividad + "' \n";

        try {
            conexion = cnn.Conexion();
            st = conexion.createStatement();
            rs = st.executeQuery(query);
            ActividadesMd rg = new ActividadesMd();
            while (rs.next()) {
                rg.setAno_arribo(rs.getString(1));
                rg.setNum_arribo(rs.getString(2));
                rg.setNom_actividad(rs.getString(3));

                rg.setNombre_practico(rs.getString(4));
                rg.setBoleta(rs.getString(5));
                rg.setFecha_inicio1(rs.getString(6));
                rg.setFecha_fin1(rs.getString(7));

                rg.setNombre_remolcador(rs.getString(8));
                rg.setBoleta1(rs.getString(9));
                rg.setFecha_inicio2(rs.getString(10));
                rg.setFecha_fin2(rs.getString(11));

                rg.setNom_remolcador1(rs.getString(12));
                rg.setBoleta2(rs.getString(13));
                rg.setFecha_inicio3(rs.getString(14));
                rg.setFecha_fin3(rs.getString(15));

                rg.setNom_remolcador2(rs.getString(16));
                rg.setBoleta3(rs.getString(17));
                rg.setFecha_inicio4(rs.getString(18));
                rg.setFecha_fin4(rs.getString(19));

                rg.setNom_lancha_piloto(rs.getString(20));
                rg.setBoleta4(rs.getString(21));
                rg.setFecha_inicio5(rs.getString(22));
                rg.setFecha_fin5(rs.getString(23));

                rg.setNombre_lancha(rs.getString(24));
                rg.setBoleta5(rs.getString(25));

                rg.setObservaciones(rs.getString(26));
                rg.setFondeo(rs.getString(27));
//                rg.setEstatus_cobro(rs.getString(28));

                allActividades.add(rg);
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
            Clients.showNotification("ERROR AL CONSULTAR (REGselect)<br/> <br/> REGISTROS! <br/> " + e.getMessage().toString(),
                    "warning", null, "middle_center", 0);
        }
        return allActividades;
    }

    public ActividadesMd updateActividad(ActividadesMd data) throws SQLException {

        PreparedStatement ps = null;
        ResultSet rs = null;
        String id = "";
        int resp = 0;
        cl = new ActividadesMd();
        try {
            conexion = cnn.Conexion();
            conexion.setAutoCommit(false);
            int vl = 0;
            st = conexion.createStatement();

            vl = st.executeUpdate("UPDATE epqop.if_bq_reg_activida  SET\n"
                    + "                    practico =  '" + data.getCodigo_practico()+ "'\n"
                    + "                    ,boleta_prac =  '" + data.getBoleta()+ "'\n"
                    + "                    ,fecha_piloi = to_date('" + data.getFecha_inicio1()+ "','dd/mm/yyyy hh24:mi')\n"
                    + "                    ,fecha_pilof = to_date('" + data.getFecha_fin1()+ "','dd/mm/yyyy hh24:mi')\n"
                    + "                    ,remolcador ='" + data.getCodigo_remolcador()+ "'\n"
                    + "                    ,boleta_rem1 =    '" + data.getBoleta1()+ "'\n"
                    + "                    ,fecha_rem1i =  to_date('" + data.getFecha_inicio2()+ "','dd/mm/yyyy hh24:mi') \n"
                    + "                    ,fecha_rem1f = to_date('" + data.getFecha_fin2()+ "','dd/mm/yyyy hh24:mi') \n"
                    + "                    ,remolcador2 = '" + data.getCod_remolcador1()+ "'\n"
                    + "                    ,boleta_rem2 =    '" + data.getBoleta2()+ "'\n"
                    + "                    ,fecha_rem2i = to_date('" + data.getFecha_inicio3()+ "','dd/mm/yyyy hh24:mi')  \n"
                    + "                    ,fecha_rem2f = to_date('" + data.getFecha_fin3()+ "','dd/mm/yyyy hh24:mi')  \n"
                    + "                    ,remolcador3 ='" + data.getCod_remolcador2()+ "'\n"
                    + "                    ,boleta_rem3 =   '" + data.getBoleta3()+ "'\n"
                    + "                    ,fecha_rem3i = to_date('" + data.getFecha_inicio4()+ "','dd/mm/yyyy hh24:mi')  \n"
                    + "                    ,fecha_rem3f = to_date('" + data.getFecha_fin4()+ "','dd/mm/yyyy hh24:mi')  \n"
                    + "                    ,lancha_piloto =   '" + data.getCod_lancha_piloto()+ "'\n"
                    + "                    ,boleta_lancha =   '" + data.getBoleta4()+ "'\n"
                    + "                    ,fecha_lan1i = to_date('" + data.getFecha_inicio5()+ "','dd/mm/yyyy hh24:mi') \n"
                    + "                    ,fecha_lan1f =  to_date('" + data.getFecha_fin5()+ "','dd/mm/yyyy hh24:mi')  \n"
                    + "                    ,lancha_almirante = '" + data.getCodigo_lancha()+ "'\n"
                    + "                    ,boleta_lanad = '" + data.getBoleta5()+ "'\n"
                    + "                    ,obse_actividad1 = '" + data.getObservaciones()+ "'\n"
                    + "                    ,codigo_fondeos =   '" + data.getCod_fondeo()+ "'\n"
                    + "                    WHERE ano_arribo = '" + data.getAno_arribo()+ "'\n"
                    + "                    AND   num_arribo =    '" + data.getNum_arribo()+ "'\n"
                    + "                    AND   num_actividad1 = '" + data.getActividad()+ "'");
           
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

            conexion.commit();
            conexion.close();

        } catch (SQLException e) {
            System.out.println("ERROR CATCH.: " + e.getMessage());
            conexion.rollback();
            conexion.close();
            cl.setResp("0");
            cl.setMsg(e.getMessage());

        }

        return cl;
    }

    //ACTIVIDADES
    public List<ActividadesMd> tipoactRSelect(String ano, String numarribo) throws SQLException {
        List<ActividadesMd> alltipoact = new ArrayList<ActividadesMd>();

        String query = "SELECT TRIM(a.num_actividad1),\n"
                + "TRIM(a.nom_actividad) \n"
                + "FROM epqop.if_bq_activ_buque a,\n"
                + "     epqop.if_bq_reg_activida b\n"
                + " where  a.NUM_ACTIVIDAD1=b.NUM_ACTIVIDAD1 and b.ano_arribo = " + ano + " AND b.num_arribo = " + numarribo + " ORDER BY a.num_actividad1 ASC";

        try {
            conexion = cnn.Conexion();
            st = conexion.createStatement();
            rs = st.executeQuery(query);
            ActividadesMd rg;
            while (rs.next()) {
                rg = new ActividadesMd();
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
            Clients.showNotification("ERROR AL CONSULTAR TIPO ACTIVIDAD (tipoactRSelect) <br/> <br/> REGISTROS! <br/> " + e.getMessage().toString(),
                    "warning", null, "middle_center", 0);
        }
        return alltipoact;
    }

    //ATRACADEROS
    public List<ActividadesMd> atracaderoRSelect() throws SQLException {
        List<ActividadesMd> allatracadero = new ArrayList<ActividadesMd>();

        String query = "SELECT TRIM(num_atracadero1),"
                + "            TRIM(tipo_terminal) "
                + " FROM epqop.if_bq_atracaderos "
                + " ORDER BY num_atracadero1 ASC ";
        try {
            conexion = cnn.Conexion();
            st = conexion.createStatement();
            rs = st.executeQuery(query);
            ActividadesMd rg;
            while (rs.next()) {
                rg = new ActividadesMd();
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
            Clients.showNotification("ERROR AL CONSULTAR ATRACADERO (atracaderoRSelect) <br/> <br/> REGISTROS! <br/> " + e.getMessage().toString(),
                    "warning", null, "middle_center", 0);
        }
        return allatracadero;
    }

    //PRACTICOS
    public List<ActividadesMd> tipoPracticoRSelect() throws SQLException {
        List<ActividadesMd> alltipoPractico = new ArrayList<ActividadesMd>();

        String query = "SELECT TRIM(codigo_particular),"
                + "            TRIM(nombre_particular) "
                + " FROM epqop.particulares "
                + "WHERE tipo_particular = 'E'"
                + "ORDER BY codigo_particular ASC ";

        try {
            conexion = cnn.Conexion();
            st = conexion.createStatement();
            rs = st.executeQuery(query);
            ActividadesMd rg;
            while (rs.next()) {
                rg = new ActividadesMd();
                rg.setCodigo_practico(rs.getString(1));
                rg.setNombre_practico(rs.getString(2));

                alltipoPractico.add(rg);
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
            Clients.showNotification("ERROR AL CONSULTAR PARTICULAR REMOLCADOR (tipoPracticoRSelect) <br/> <br/> REGISTROS! <br/> " + e.getMessage().toString(),
                    "warning", null, "middle_center", 0);
        }
        return alltipoPractico;
    }

    //REMOLCADORES
    public List<ActividadesMd> tipoParticularRSelect() throws SQLException {
        List<ActividadesMd> alltipoParticular = new ArrayList<ActividadesMd>();

        String query = "SELECT TRIM(codigo_particular),"
                + "            TRIM(nombre_particular) "
                + " FROM epqop.particulares "
                + " WHERE tipo_particular = 'C'"
                + " ORDER BY codigo_particular ASC ";

        try {
            conexion = cnn.Conexion();
            st = conexion.createStatement();
            rs = st.executeQuery(query);
            ActividadesMd rg;
            while (rs.next()) {
                rg = new ActividadesMd();
                rg.setCodigo_remolcador(rs.getString(1));
                rg.setNombre_remolcador(rs.getString(2));

                alltipoParticular.add(rg);
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
            Clients.showNotification("ERROR AL CONSULTAR PARTICULAR REMOLCADOR (tipoParticularRSelect) <br/> <br/> REGISTROS! <br/> " + e.getMessage().toString(),
                    "warning", null, "middle_center", 0);
        }
        return alltipoParticular;
    }

    //LANCHAS PILOTO
    public List<ActividadesMd> LanchaPilotoRSelect() throws SQLException {
        List<ActividadesMd> allLanchaPiloto = new ArrayList<ActividadesMd>();

        String query = "SELECT TRIM(codigo_particular),"
                + "            TRIM(nombre_particular) "
                + " FROM epqop.particulares "
                + "WHERE tipo_particular IN ('L','H')"
                + "ORDER BY codigo_particular ASC ";

        try {
            conexion = cnn.Conexion();
            st = conexion.createStatement();
            rs = st.executeQuery(query);
            ActividadesMd rg;
            while (rs.next()) {
                rg = new ActividadesMd();
                rg.setCod_lancha_piloto(rs.getString(1));
                rg.setNom_lancha_piloto(rs.getString(2));

                allLanchaPiloto.add(rg);
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
            Clients.showNotification("ERROR AL CONSULTAR PARTICULAR LANCHA PILOTO (LanchaPilotoRSelect) <br/> <br/> REGISTROS! <br/> " + e.getMessage().toString(),
                    "warning", null, "middle_center", 0);
        }
        return allLanchaPiloto;
    }

    //LANCHAS ALMIRANTE
    public List<ActividadesMd> LanchaalmirnateRSelect() throws SQLException {
        List<ActividadesMd> allLanchaalmirante = new ArrayList<ActividadesMd>();

        String query = "SELECT TRIM(codigo_particular),"
                + "            TRIM(nombre_particular) "
                + " FROM epqop.particulares "
                + "WHERE tipo_particular IN ('L','H')"
                + "ORDER BY codigo_particular ASC ";

        try {
            conexion = cnn.Conexion();
            st = conexion.createStatement();
            rs = st.executeQuery(query);
            ActividadesMd rg;
            while (rs.next()) {
                rg = new ActividadesMd();
                rg.setCodigo_lancha(rs.getString(1));
                rg.setNombre_lancha(rs.getString(2));

                allLanchaalmirante.add(rg);
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
            Clients.showNotification("ERROR AL CONSULTAR PARTICULAR LANCHA PILOTO (LanchaPilotoRSelect) <br/> <br/> REGISTROS! <br/> " + e.getMessage().toString(),
                    "warning", null, "middle_center", 0);
        }
        return allLanchaalmirante;
    }
}
