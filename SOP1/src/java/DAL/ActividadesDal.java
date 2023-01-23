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

    public List<ActividadesMd> REGselect(String ano, String arribo, String actividad) throws SQLException {
        List<ActividadesMd> allActividades = new ArrayList<ActividadesMd>();
        
        String query = "SELECT     a.ano_arribo, a.num_arribo,\n"
                + "           h.nom_actividad,\n"
                + "           TO_CHAR (a.fecha_act, 'dd/mm/yyyy'),\n"
                + "           TO_CHAR (a.hora_act,'hh24:mm:ss'),\n"
                + "           TO_CHAR (a.fecha_fin_actividad, 'dd/mm/yyyy'),\n"
                + "           a.calado_proa, a.calado_medio, a.calado_popa,i.tipo_terminal, \n"
                + "           b.nombre_particular AS PRACTICO,\n"
                + "           a.boleta_prac,TO_CHAR ( a.fecha_piloi, 'dd/mm/yyyy '), TO_CHAR(a.fecha_pilof, 'dd/mm/yyyy '), TO_CHAR(a.fecha_pilodura, 'dd/mm/yyyy '),\n"
                + "           c.nombre_particular AS REMOLCADOR,\n"
                + "           a.boleta_rem1, TO_CHAR ( a.fecha_rem1i, 'dd/mm/yyyy '), TO_CHAR (a.fecha_rem1f, 'dd/mm/yyyy '), TO_CHAR(a.fecha_rem1dura, 'dd/mm/yyyy '),\n"
                + "           d.nombre_particular AS REMOLCADOR2,\n"
                + "           a.boleta_rem2, a.fecha_rem2i, a.fecha_rem2f, a.fecha_rem2dura,\n"
                + "           a.remolcador3, e.nombre_particular AS REMOLCADOR3,\n"
                + "           a.boleta_rem3, a.fecha_rem3i, a.fecha_rem3f, a.fecha_rem3dura,\n"
                + "           a.lancha_piloto, f.nombre_particular  AS LANCHA_PILOTO,\n"
                + "           a.boleta_lancha, a.fecha_lan1i, a.fecha_lan1f, a.fecha_landura, \n"
                + "           a.lancha_almirante, g.nombre_particular,\n"
                + "           a.boleta_lanad,\n"
                + "           a.obse_actividad1\n"
                + "FROM     epqop.if_bq_reg_activida a,\n"
                + "         epqop.particulares b,\n"
                + "         epqop.particulares c,\n"
                + "         epqop.particulares d,\n"
                + "         epqop.particulares e,\n"
                + "         epqop.particulares f,\n"
                + "         epqop.particulares g,\n"
                + "         epqop.if_bq_activ_buque h,\n"
                + "         epqop.if_bq_atracaderos i\n"
                + "WHERE   a.practico         = b.codigo_particular (+)\n"
                + "AND     a.remolcador       = c.codigo_particular (+)\n"
                + "AND     a.remolcador2      = d.codigo_particular (+)\n"
                + "AND     a.remolcador3      = e.codigo_particular(+)\n"
                + "AND     a.lancha_piloto    = f.codigo_particular(+)\n"
                + "AND     a.lancha_almirante = g.codigo_particular (+)\n"
                + "AND     a.num_actividad1   = h.num_actividad1  (+)\n"
                + "AND     a.num_atracadero3  = i.num_atracadero1   (+)\n"
                + "AND     a.ano_arribo = '" + ano + "' \n"
                + "AND     a.num_arribo = '" + arribo + "'\n"
                + "AND     a.num_actividad1 = '" + actividad + "' \n";

        try {
            conexion = cnn.Conexion();
            st = conexion.createStatement();
            rs = st.executeQuery(query);
            ActividadesMd rg = new ActividadesMd();
            while (rs.next()) {
                rg.setAno_arribo(rs.getString(1));
                rg.setNum_arribo(rs.getString(2));
                rg.setNom_actividad(rs.getString(3));
                rg.setFecha(rs.getString(4));
                rg.setHora(rs.getString(5));
                rg.setFecha_fin_act(rs.getString(6));
                rg.setCalado_proa(rs.getString(7));
                rg.setCalado_medio(rs.getString(8));
                rg.setCalado_popa(rs.getString(9));
                rg.setNom_atracadero(rs.getString(10));
                rg.setNombre_practico(rs.getString(11));
                rg.setBoleta(rs.getString(12));
                rg.setFecha_inicio1(rs.getString(13));
                rg.setFecha_fin1(rs.getString(14));
                rg.setDuracion1(rs.getString(15));
                rg.setNom_remolcador1(rs.getString(16));
                rg.setBoleta2(rs.getString(18));
                rg.setFecha_inicio2(rs.getString(19));
                rg.setNom_remolcador2(rs.getString(20));
                                
                
//                rg.setBoleta1(rs.getString(18));
//                rg.setFecha_inicio2(rs.getString(19));
//                
//                rg.setDuracion2(rs.getString(21));
//                //  rg.setCod_remolcador3(rs.getString(25));
//               
//                
//                rg.setFecha_inicio3(rs.getString(24));
//                rg.setFecha_fin3(rs.getString(25));
//                rg.setDuracion3(rs.getString(26));
//                //  rg.setCod_remolcador4(rs.getString(31));
//                rg.setNom_remolcador4(rs.getString(27));
//                rg.setBoleta3(rs.getString(28));
//                rg.setFecha_inicio4(rs.getString(29));
//                rg.setFecha_fin4(rs.getString(30));
//                rg.setDuracion4(rs.getString(31));
//                // rg.setCod_lancha_piloto(rs.getString(37));
//                rg.setNom_lancha_piloto(rs.getString(32));
//                rg.setBoleta4(rs.getString(33));
//                rg.setFecha_inicio5(rs.getString(34));
//                rg.setFecha_fin5(rs.getString(35));
//                rg.setDuracion5(rs.getString(36));
//                //  rg.setCod_lancha_almirante(rs.getString(43));
//                rg.setNombre_lancha(rs.getString(37));
//                rg.setBoleta5(rs.getString(38));
//                rg.setObservaciones(rs.getString(39));
//                //   rg.setCod_fondeo(rs.getString(47));
//                rg.setFondeo(rs.getString(40));
//                rg.setEstatus_cobro(rs.getString(41));

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

    public void REGinsert(String ano_arribo, String num_arribo, /* String correlativo1,*/ String actividad, String fecha,
            String hora, String fecha_fin_act, String calado_proa, String calado_medio, String calado_popa,
            String atracadero, String practico, String boleta, String fecha_inicio1, String fecha_fin1,
            String duracion1, String remolcador2, String boleta1, String fecha_inicio2, String fecha_fin2,
            String duracion2, String remolcador3, String boleta2, String fecha_inicio3, String fecha_fin3,
            String duracion3, String remolcador4, String boleta3, String fecha_inicio4, String fecha_fin4,
            String duracion4, String lancha_piloto, String boleta4, String fecha_inicio5, String fecha_fin5,
            String duracion5, String lancha_almirante, String boleta5, String observaciones, String cod_fondeo,
            String estatus_cobro) throws SQLException {

        String sql = "INSERT INTO epqop.if_bq_reg_activida "
                + "      (ano_arribo,      num_arribo,          correlativo2,  num_actividad1,  fecha_act, "
                + "       hora_act,        fecha_fin_actividad, calado_proa,   calado_medio,    calado_popa,\n"
                + "       num_atracadero3, practico,            boleta_prac,   fecha_piloI,     fecha_piloF,\n"
                + "       fecha_pilod,     remolcador,          boleta_rem1,   fecha_rem1I,     fecha_rem1F ,\n"
                + "       fecha_rem1dura,  remolcador2,         boleta_rem2,   fecha_rem2I,     fecha_rem2F,\n"
                + "       fecha_rem2dura,  remolcador3,         boleta_rem3,   fecha_rem3I,     fecha_rem3f,\n"
                + "       fecha_rem3dura,  lancha_piloto,       boleta_lancha, fecha_lan1I,     fecha_lan1F,\n"
                + "       fecha_landura,   lancha_almirante,    boleta_lanad,  obse_actividad1, codigo_fondeos,\n"
                + "       estatus_cobro)\n"
                + " VALUES("
                + "" + (ano_arribo.equals("") ? "null" : ano_arribo) + ",)"
                + "" + (num_arribo.equals("") ? "null" : num_arribo) + ",)"
                + "?"
                //   + "" + (correlativo1.equals("") ? "null" : correlativo1) + ",)"
                + "" + (actividad.equals("") ? "null" : actividad) + ",)"
                + "" + (fecha.equals("") ? "null" : fecha) + ",)"
                + "" + (hora.equals("") ? "null" : hora) + ",)"
                + "" + (fecha_fin_act.equals("") ? "null" : fecha_fin_act) + ",)"
                + "" + (calado_proa.equals("") ? "null" : calado_proa) + ",)"
                + "" + (calado_medio.equals("") ? "null" : calado_medio) + ",)"
                + "" + (calado_popa.equals("") ? "null" : calado_medio) + ",)"
                + "" + (atracadero.equals("") ? "null" : atracadero) + ",)"
                + "" + (practico.equals("") ? "null" : practico) + ",)"
                + "" + (boleta.equals("") ? "null" : boleta) + ",)"
                + "" + (fecha_inicio1.equals("") ? "null" : fecha_inicio1) + ",)"
                + "" + (fecha_fin1.equals("") ? "null" : fecha_fin1) + ",)"
                + "" + (duracion1.equals("") ? "null" : duracion1) + ",)"
                + "" + (remolcador2.equals("") ? "null" : remolcador2) + ",)"
                + "" + (boleta1.equals("") ? "null" : boleta1) + ",)"
                + "" + (fecha_inicio2.equals("") ? "null" : fecha_inicio2) + ",)"
                + "" + (fecha_fin2.equals("") ? "null" : fecha_fin2) + ",)"
                + "" + (duracion2.equals("") ? "null" : duracion2) + ",)"
                + "" + (remolcador3.equals("") ? "null" : remolcador3) + ",)"
                + "" + (boleta2.equals("") ? "null" : boleta2) + ",)"
                + "" + (fecha_inicio3.equals("") ? "null" : fecha_inicio3) + ",)"
                + "" + (fecha_fin3.equals("") ? "null" : fecha_fin3) + ",)"
                + " " + (duracion3.equals("") ? "null" : duracion3) + ",?)"
                + " " + (remolcador4.equals("") ? "null" : remolcador4) + ",?)"
                + " " + (boleta3.equals("") ? "null" : boleta3) + ",?)"
                + " " + (fecha_inicio4.equals("") ? "null" : fecha_inicio4) + ",?)"
                + " " + (fecha_fin4.equals("") ? "null" : fecha_fin4) + ",?)"
                + " " + (duracion4.equals("") ? "null" : duracion4) + ",?)"
                + " " + (lancha_piloto.equals("") ? "null" : lancha_piloto) + ",?)"
                + " " + (boleta4.equals("") ? "null" : boleta4) + ",?)"
                + " " + (fecha_inicio5.equals("") ? "null" : fecha_inicio5) + ",?)"
                + " " + (fecha_fin5.equals("") ? "null" : fecha_fin5) + ",?)"
                + " " + (duracion5.equals("") ? "null" : duracion5) + ",?)"
                + " " + (lancha_almirante.equals("") ? "null" : lancha_almirante) + ",?)"
                + " " + (boleta5.equals("") ? "null" : boleta5) + ",?)"
                + " " + (observaciones.equals("") ? "null" : observaciones) + ",?)"
                + " " + (cod_fondeo.equals("") ? "null" : cod_fondeo) + ",?)"
                + " " + (estatus_cobro.equals("") ? "null" : estatus_cobro) + ",?)";

        String sql0 = "SELECT MAX(correlativo2)+1 AS correlativo FROM epqop.if_bq_reg_activida";

        try {
            conexion = cnn.Conexion();
            conexion.setAutoCommit(false);
            st = conexion.createStatement();

            rs = st.executeQuery(sql0);
            String correlativo = "";
            while (rs.next()) {
                correlativo = rs.getString("codigo");
            }

            ps = conexion.prepareStatement(sql);

            ps.setString(1, ano_arribo);
            ps.setString(2, num_arribo);
            ps.setString(3, actividad);
            ps.setString(4, correlativo);
            ps.setString(5, fecha);
            ps.setString(6, hora);
            ps.setString(7, fecha_fin_act);
            ps.setString(8, calado_proa);
            ps.setString(9, calado_medio);
            ps.setString(10, calado_popa);
            ps.setString(11, atracadero);
            ps.setString(12, practico);
            ps.setString(13, boleta);
            ps.setString(14, fecha_inicio1);
            ps.setString(15, fecha_fin1);
            ps.setString(16, duracion1);
            ps.setString(17, remolcador2);
            ps.setString(18, boleta1);
            ps.setString(19, fecha_inicio2);
            ps.setString(20, fecha_fin2);
            ps.setString(21, duracion2);
            ps.setString(22, remolcador3);
            ps.setString(23, boleta2);
            ps.setString(24, fecha_inicio3);
            ps.setString(25, fecha_fin3);
            ps.setString(26, duracion3);
            ps.setString(27, remolcador4);
            ps.setString(28, boleta3);
            ps.setString(29, fecha_inicio4);
            ps.setString(30, fecha_fin4);
            ps.setString(31, duracion4);
            ps.setString(32, lancha_piloto);
            ps.setString(33, boleta4);
            ps.setString(34, fecha_inicio5);
            ps.setString(35, fecha_fin5);
            ps.setString(36, duracion5);
            ps.setString(37, lancha_almirante);
            ps.setString(38, boleta5);
            ps.setString(39, observaciones);
            ps.setString(40, cod_fondeo);
            ps.setString(41, estatus_cobro);

            ps.executeUpdate();
            ps.close();
            conexion.commit();
            Clients.showNotification("REGISTRO CREADO <br/> CON EXITO  <br/>");
            conexion.close();
            conexion = null;
            System.out.println("Conexion Cerrada" + conexion);

        } catch (SQLException e) {
            conexion.rollback();
            conexion.close();
            conexion = null;
            Clients.showNotification("ERROR AL INSERTAR <br/> <br/> REGISTROS! <br/> " + e.getMessage().toString(),
                    "warning", null, "middle_center", 0);
        }
    }

    public void REGupdate(String ano_arribo, String num_arribo,/* String correlativo, String cod_actividad,*/ String nom_actividad, String fecha, String hora,
            String fecha_fin_act, String calado_proa, String calado_medio, String calado_popa, String nom_atracadero,
            String nom_practico, String boleta, String fecha_inicio1, String fecha_fin1, String duracion1,
            String nom_remolcador2, String boleta1, String fecha_inicio2, String fecha_fin2, String duracion2,
            String nom_remolcador3, String boleta2, String fecha_inicio3, String fecha_fin3, String duracion3,
            String nom_remolcador4, String boleta3, String fecha_inicio4, String fecha_fin4, String duracion4,
            String nom_lancha_piloto, String boleta4, String fecha_inicio5, String fecha_fin5, String duracion5,
            String nom_lancha_almirante, String boleta5, String observaciones, String fondeo, String estatus_cobro) throws SQLException {
        try {
            conexion = cnn.Conexion();
            conexion.setAutoCommit(false);
            st = conexion.createStatement();

            //los nombres de las tablas van siempre con a. b. c. d.?
            st.executeUpdate("UPDATE epqop.if_bq_activ_buque SET"
                    //  + "num_actividad1 = '" + cod_actividad + "',"
                    + "nom_actividad = '" + nom_actividad + "',"
                    //  + "correlativo2 = '" + correlativo + "',"
                    + "fecha_act = '" + fecha + "',"
                    + "hora_act = '" + hora + "',"
                    + "fecha_fin_actividad = '" + fecha_fin_act + "',"
                    + "calado_proa = '" + calado_proa + "',"
                    + "calado_medio = '" + calado_medio + "',"
                    + "calado_popa = '" + calado_popa + "',"
                    //  + "num_atracadero1 = '" + cod_atracadero + "' "
                    + "tipo_terminal = '" + nom_atracadero + "' "
                    //  + "codigo_particular = '" + cod_practico + "' "
                    + "nombre_particular = '" + nom_practico + "' "
                    + "boleta_prac = '" + boleta + "' "
                    + "fecha_piloi = '" + fecha_inicio1 + "' "
                    + "fecha_pilof = '" + fecha_fin1 + "' "
                    + "fecha_pilodura = '" + duracion1 + "' "
                    //   + "remolcador = '" + cod_remolcador2 + "' "//arreglar
                    + "nom_remolcador = '" + nom_remolcador2 + "' "//arreglar
                    + "boleta_rem1 = '" + boleta1 + "' "
                    + "fecha_rem1i = '" + fecha_inicio2 + "' "
                    + "fecha_rem1f = '" + fecha_fin2 + "' "
                    + "fecha_rem1dura = '" + duracion2 + "' "
                    //  + "remolcador2 = '" + cod_remolcador3 + "' "//arreglar
                    + "nom_remolcador2 = '" + nom_remolcador3 + "' "//arreglar
                    + "boleta_ram2 = '" + boleta2 + "' "
                    + "fecha_rem2i = '" + fecha_inicio3 + "' "
                    + "fecha_rem2f = '" + fecha_fin3 + "' "
                    + "fecha_rem2dura = '" + duracion3 + "' "
                    //  + "remolcador3 = '" + cod_remolcador4 + "' "//arreglar
                    + "nom_remolcador3 = '" + nom_remolcador4 + "' "//arreglar
                    + "boletas3Act = '" + boleta3 + "' "
                    + "inicio3Act = '" + fecha_inicio4 + "' "
                    + "fin3Act = '" + fecha_fin4 + "' "
                    + "duracion3Act = '" + duracion4 + "' "
                    //  + "lanchaAct = '" + cod_lancha_piloto + "' "
                    + "nomlanchaAct = '" + nom_lancha_piloto + "' "
                    + "boletas4Act = '" + boleta4 + "' "
                    + "inicio4Act = '" + fecha_inicio5 + "' "
                    + "fin4Act = '" + fecha_fin5 + "' "
                    + "duracion4Act = '" + duracion5 + "' "
                    //   + "lalmiranteAct = '" + cod_lancha_almirante + "' "
                    + "nomlalmiranteAct = '" + nom_lancha_almirante + "' "
                    + "boletas5Act = '" + boleta5 + "' "
                    + "observacionesAct = '" + observaciones + "' "
                    //  + "codigofonAct = '" + cod_fondeo + "' "
                    + "nomfondeoAct = '" + fondeo + "' "
                    + "estatus2Act = '" + estatus_cobro + "' "
                    + "WHERE ano_arribo = '" + ano_arribo + "' "
                    + "AND   num_arribo = '" + num_arribo + "' ");
            //CODIGO DE LA ACTIVIDAD QUE SE QUIERE ACTUALIZAR

            Clients.showNotification("REGISTRO ACTUALIZADO <br/> CON EXITO  <br/>");
            System.out.println("Actualizacion Exitosa.! ");
            st.close();
            conexion.commit();
            conexion.close();
        } catch (SQLException e) {
            conexion.rollback();
            conexion.close();
            String mensaje = e.getMessage();
            Clients.showNotification("ERROR AL ACTUALIZAR <br/>'" + mensaje + "' <br/> REGISTROS! <br/> ",
                    "warning", null, "middle_center", 0);
            System.out.println("Actualizacion EXCEPTION.: " + mensaje);
        }

    }

    public void REGdelete(String anio, String num) throws SQLException {
        try {
            conexion = cnn.Conexion();
            conexion.setAutoCommit(false);
            System.out.println("ELIMINAR DATOS..!");
            System.out.println("Eliminar " + anio);
            System.out.println("Eliminar " + num);
            st = conexion.createStatement();

            st.executeUpdate("DELETE epqop.if_activ_buque"
                    + "       WHERE  ano_arribo = '" + anio + "' "
                    + "       AND num_arribo = '" + num);
            //CODIGO DE LA ACTIVIDAD
            
            Clients.showNotification("REGISTRO ELIMINADO <br/> CON EXITO  <br/>");
            System.out.println("Eliminacion Exitosa.! ");
            st.close();
            conexion.commit();
            conexion.close();
        } catch (SQLException e) {
            conexion.rollback();
            conexion.close();
            String mensaje = e.getMessage();
            Clients.showNotification("ERROR AL ELIMINAR <br/>'" + mensaje + "' <br/> REGISTROS! <br/> ",
                    "warning", null, "middle_center", 0);
            System.out.println("Eliminacion EXCEPTION.: " + mensaje);
        }

    }

    //ACTIVIDADES
    public List<ActividadesMd> tipoactRSelect() throws SQLException {
        List<ActividadesMd> alltipoact = new ArrayList<ActividadesMd>();
        
        String query = "SELECT TRIM(num_actividad1),"
                + "            TRIM(nom_actividad) "
                + " FROM epqop.if_bq_activ_buque ORDER BY num_actividad1 ASC ";
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
                + "WHERE tipo_particular = 'L'"
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

}
