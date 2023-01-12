package DAL;

import Conexion.Conexion;
import MD.BuquesMd;
import MD.MantenimientoarribosMd;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.zk.ui.util.Clients;

public class MantenimientoarribosDal {

    private Connection conn = null;
    private Conexion obtener = new Conexion();

    private Connection conexion = null;
    private Conexion cnn = new Conexion();
    PreparedStatement ps = null;
    Statement st = null;
    ResultSet rs = null;
    MantenimientoarribosMd cl = new MantenimientoarribosMd();
    
    
    public List<MantenimientoarribosMd> ObtenerFecha() throws SQLException, ClassNotFoundException {
        Statement st = null;
        ResultSet rs = null;
        List<MantenimientoarribosMd> allBusca = new ArrayList<MantenimientoarribosMd>();
        
        String query = "SELECT TO_CHAR(SYSDATE,'dd/mm/yyyy hh24:mi:ss') FROM dual";

        try {
            conexion = cnn.Conexion();
            st = conexion.createStatement();
            rs = st.executeQuery(query);
            MantenimientoarribosMd rg;
            while (rs.next()) {
                rg = new MantenimientoarribosMd();
                rg.setObtefechaHora(rs.getString(1));

                allBusca.add(rg);
            }

            st.close();
            rs.close();
            conexion.close();
            conexion = null;
        } catch (SQLException e) {
            conexion.close();
            conexion = null;
            Clients.showNotification("ERROR AL CONSULTAR <br/> <br/> REGISTROS! <br/> " + e.getMessage().toString(),
                    "warning", null, "middle_center", 0);
        }
        return allBusca;
    }

    public MantenimientoarribosMd Mostrardatos2(String anio, String numero) {
        Statement st = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String id = "";
        int resp = 0;
        cl = new MantenimientoarribosMd();

        String query0 = "SELECT  a.buq_eslora AS ESLORA,a.imo AS IMO,a.call_sign,\n"
                + "        a.cant_plumas AS PLUMAS,a.manga,a.ano_construccion,\n"
                + "        a.calado_maximo AS CALADO,a.buq_trb AS TN_BRUTO,a.trn AS TN_NETO,\n"
                + "        a.peso_muerto,c.nombre_pais,a.cant_gruas,e.nom_naviera,f.nom_linea,\n"
                + "        e.agente,e.tel_contacto,e.email\n"
                + "FROM      epqop.if_bq_buques       a,\n"
                + "          epqop.if_bq_arribos      b,\n"
                + "          epqop.if_bq_paises       c,\n"
                + "          epqop.particulares       d,\n"
                + "          epqop.if_bq_naviera      e,\n"
                + "          epqop.if_bq_lineas       f,\n"
                + "          epqop.if_bq_lineas_arrib g\n"
                + "WHERE     a.buque = b.buque \n"
                + "AND       a.bandera = c.pais\n"
                + "AND       b.estibadora = d.codigo_particular\n"
                + "AND       e.naviera1 = g.naviera1\n"
                + "AND       f.linea1 = g.linea1\n"
                + "AND       g.ano_arribo = " + anio + "\n"
                + "AND       g.num_arribo = " + numero + "\n"
                + "AND       d.tipo_particular = 'A'\n"
                + "AND       b.ano_arribo = " + anio + "\n"
                + "AND       b.num_arribo = " + numero + " ";

        try {
            conn = obtener.Conexion();

            st = conn.createStatement();
            rs = st.executeQuery(query0);
            while (rs.next()) {
                resp = 1;
                cl.setEslora(rs.getString(1));
                cl.setImo(rs.getString(2));
                cl.setCall_sign(rs.getString(3));
                cl.setPlumas(rs.getString(4));
                cl.setManga(rs.getString(5));
                cl.setAnoCostruccion(rs.getString(6));
                cl.setCalado(rs.getString(7));
                cl.setTn_bruto(rs.getString(8));
                cl.setTn_neto(rs.getString(9));
                cl.setPeso_muerto(rs.getString(10));
                cl.setNom_pais(rs.getString(11));
                cl.setCant_Gruas(rs.getString(12));
                cl.setNom_naviera(rs.getString(13));
                cl.setNom_linea(rs.getString(14));
                cl.setAgente_naviera(rs.getString(15));
                cl.setTelefono(rs.getString(16));
                cl.setEmail(rs.getString(17));//aqui ya no se setio

                cl.setResp("1");
                cl.setMsg("ACTUALIZAR DATOS DE ARRIBO.!");
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

    public MantenimientoarribosMd Mostrardatos(String anio, String numero) {
        Statement st = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String id = "";
        int resp = 0;
        cl = new MantenimientoarribosMd();

        String query0 = " SELECT       \n"
                + "                                    a.ano_arribo,     \n"
                + "                                    a.num_arribo,     \n"
                + "                             DECODE(a.status3,'V','AVISADO','A','ATRACADO','F','FONDEADO','Z','ZARPADO') AS ESTATUS_BUQUE,     \n"
                + "                             DECODE(a.estatus_arribo,'A','ANUNCIADO','E','ELIMINADO','P','PENDIENTE','F','FINALIZADO') AS ESTATADO,            \n"
                + "                                    a.buque,     \n"
                + "                                    b.nom_buque,     \n"
                + "                                    a.tipo_buque,     \n"
                + "                                    d.nom_tipo_buque,     \n"
                + "                                    a.num_atracadero1,     \n"
                + "                                    a.numero_viaje,     \n"
                + "                             DECODE(a.ant_pagados,'S','SI','N','NO') AS ANTICIPOS_PAGADOS,     \n"
                + "                             TO_CHAR(a.fecha_atraque,'DD/MM/YYYY')||' '||TO_CHAR(a.hora_atraque,'HH24:MI:SS')AS ATRAQUE_ETA,     \n"
                + "                             TO_CHAR(a.fecha_zarpe,'DD/MM/YYYY')||' '||TO_CHAR(a.hora_zarpe,'HH24:MI:SS')AS ZARPE,     \n"
                + "                             DECODE(a.tipo_operacion2,'I','IMPORTACION','E','EXPORTACION') AS TIPO_OPERACION,     \n"
                + "                             DECODE(a.clase_arribo,'C','CHARTER','L','LINER')TIPO_ARRIBO,     \n"
                + "                                    a.cant_bodegas,     \n"
                + "                                    a.horas_operacion,     \n"
                + "                                    a.pasajeros,     \n"
                + "                             TO_CHAR(a.opera_inicio,'DD/MM/YYYY HH24:MI:SS'),     \n"
                + "                                    a.pk_inicial,     \n"
                + "                             DECODE(a.ladomuelle,'E','ESTRIBOR','B','BABOR') AS POSICION,     \n"
                + "                             TO_CHAR( a.opera_fin,'DD/MM/YYYY HH24:MI:SS'),     \n"
                + "                                    a.pk_final,     \n"
                + "                                    a.estado_serv_buque AS R_OPERADOR,     \n"
                + "                                    a.datos_importacion,     \n"
                + "                                    a.datos_exportacion,     \n"
                + "                                    a.tm_importar1,     \n"
                + "                                    a.tm_exportar1,     \n"
                + "                                    a.estibadora,     \n"
                + "                                    c.nombre_particular,     \n"
                + "                             DECODE(a.via_directa,'S','DIRECTA','N','INDIRECTA','T','INTERMEDIA') AS VIA,     \n"
                + "                                    a.puerto_procedencia,     \n"
                + "                                    a.puerto_destino,     \n"
                + "                                    e.agente,    \n"
                + "                            TO_CHAR(a.fecha_hora_visita,'DD/MM/YYYY HH24:MI:SS'),     \n"
                + "                                    a.obse_actividad1 AS OBSERVACIONES     \n"
                + "                               FROM epqop.if_bq_arribos a,      \n"
                + "                                    epqop.if_bq_buques b,      \n"
                + "                                    epqop.particulares c,     \n"
                + "                                    epqop.if_bq_tipo_arribo d,\n"
                + "                                    epqop.if_bq_naviera e,\n"
                + "                                    epqop.if_bq_lineas_arrib f  \n"
                + "                              WHERE a.buque = b.buque      \n"
                + "                              AND   a.estibadora = c.codigo_particular      \n"
                + "                              AND   a.tipo_buque = d.tipo_buque \n"
                + "                              AND   e.naviera1 = f.naviera1    \n"
                + "                              AND   f.ano_arribo =   " + anio + "\n"
                + "                              AND   f.num_arribo =  " + numero + "\n"
                + "                              AND   c.tipo_particular = 'A'     \n"
                + "                              AND   a.ano_arribo = " + anio + "\n"
                + "                              AND   a.num_arribo = " + numero + " ";

        try {
            conn = obtener.Conexion();

            st = conn.createStatement();
            rs = st.executeQuery(query0);
            while (rs.next()) {
                resp = 1;
                cl.setAno_arribo(rs.getString(1));
                cl.setNum_arribo(rs.getString(2));
                cl.setStatus(rs.getString(3));
                cl.setEstado(rs.getString(4));
                cl.setCod_buque(rs.getString(5));
                cl.setNom_buque(rs.getString(6));
                cl.setCod_tipo_buque(rs.getString(7));
                cl.setNom_tipo_buque(rs.getString(8));
                cl.setNum_atracadero(rs.getString(9));
                cl.setNum_viaje(rs.getString(10));
                cl.setAnt_pagados(rs.getString(11));
                cl.setFecha_eta(rs.getString(12));
                cl.setFecha_zarpe(rs.getString(13));
                cl.setTipo_operacion(rs.getString(14));
                cl.setTipo_arribo(rs.getString(15));
                cl.setCantidad_bodegas(rs.getString(16));
                cl.setHora_operacion(rs.getString(17));
                cl.setPasajeros(rs.getString(18));
                cl.setInicio_operacion(rs.getString(19));
                cl.setPk_inicial(rs.getString(20));
                cl.setPosicion_buque(rs.getString(21));
                cl.setFin_operacion(rs.getString(22));
                cl.setPk_final(rs.getString(23));
                cl.setR_operador(rs.getString(24));
                cl.setDatos_import(rs.getString(25));
                cl.setDatos_export(rs.getString(26));
                cl.setTm_import(rs.getString(27));
                cl.setTm_export(rs.getString(28));
                cl.setNum_estibadora(rs.getString(29));
                cl.setNom_estibadora(rs.getString(30));
                cl.setVia_directa(rs.getString(31));
                cl.setPto_procedencia(rs.getString(32));
                cl.setPto_destino(rs.getString(33));
                cl.setRepresenta_naviera(rs.getString(34));
                cl.setFecha_visita(rs.getString(35));
                cl.setObservaciones(rs.getString(36));

                cl.setResp("1");
                cl.setMsg("ACTUALIZAR DATOS DE ARRIBO.!");
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

    public List<MantenimientoarribosMd> RSelect() throws SQLException {
        List<MantenimientoarribosMd> allMantenimiento = new ArrayList<MantenimientoarribosMd>();

        String query = "SELECT "
                + "a.ano_arribo,"
                + "a.num_arribo,"
                + "a.status3, "
                + "a.estatus_arribo,"
                + "a.buque,"
                + "b.nom_buque,"
                + "a.tipo_buque,"
                + "a.num_atracadero1,"
                + "a.numero_viaje,"
                + "a.ant_pagados,"
                + "a.fecha_atraque,"
                + "a.fecha_zarpe,"
                + "a.tipo_operacion2,"
                + "a.clase_arribo,"
                + "a.cant_bodegas,"
                + "a.horas_operacion,"
                + "a.pk_inicial,"
                + "a.pasajeros,"
                + "a.opera_inicio,"
                + "a.pk_final,"
                + "a.estado_serv_buque,"
                + "a.datos_importacion,"
                + "a.datos_exportacion,"
                + "a.tm_importar1,"
                + "a.tm_exportar1,"
                + "a.estibadora,"
                + "c.nombre_particular,"
                + "a.via_directa,"
                + "a.puerto_procedencia,"
                + "a.puerto_destino,"
                + "a.representa_naviera,"
                + "a.fecha_hora_visita,"
                + "a.obse_actividad1,"
                + "c.nombre_particular "
                + " FROM epqop.if_bq_arribos a, "
                + "      epqop.if_bq_buques b, "
                + "      epqop.particulares c "
                + "WHERE a.buque = b.buque "
                + "AND   a.estibadora = c.codigo_particular "
                + "AND   c.tipo_particular = 'A' ";

        try {
            conexion = cnn.Conexion();
            st = conexion.createStatement();
            rs = st.executeQuery(query);
            MantenimientoarribosMd rg;
            while (rs.next()) {
                rg = new MantenimientoarribosMd();

                rg.setAno_arribo(rs.getString(1));
                rg.setNum_arribo(rs.getString(2));
                rg.setStatus(rs.getString(3));
                rg.setEstado(rs.getString(4));
                rg.setCod_buque(rs.getString(5));
                rg.setNom_buque(rs.getString(6));
                rg.setCod_tipo_buque(rs.getString(7));
                rg.setNum_atracadero(rs.getString(8));
                rg.setNum_viaje(rs.getString(9));
                rg.setAnt_pagados(rs.getString(10));
                rg.setFecha_eta(rs.getString(11));
                rg.setFecha_zarpe(rs.getString(12));
                rg.setTipo_operacion(rs.getString(13));
                rg.setTipo_arribo(rs.getString(14));
                rg.setCantidad_bodegas(rs.getString(15));
                rg.setHora_operacion(rs.getString(16));
                rg.setPk_inicial(rs.getString(17));
                rg.setPasajeros(rs.getString(18));
                rg.setInicio_operacion(rs.getString(19));
                rg.setPk_final(rs.getString(20));
                rg.setR_operador(rs.getString(21));
                rg.setDatos_import(rs.getString(22));
                rg.setDatos_export(rs.getString(23));
                rg.setTm_import(rs.getString(24));
                rg.setTm_export(rs.getString(25));
                rg.setNum_estibadora(rs.getString(26));
                rg.setNom_estibadora(rs.getString(27));
                rg.setVia_directa(rs.getString(28));
                rg.setPto_procedencia(rs.getString(29));
                rg.setPto_destino(rs.getString(30));
                rg.setRepresenta_naviera(rs.getString(31));
                rg.setFecha_visita(rs.getString(32));
                rg.setObservaciones(rs.getString(33));

                allMantenimiento.add(rg);
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
            Clients.showNotification("ERROR AL CONSULTAR (Rselect) <br/> <br/> REGISTROS! <br/> " + e.getMessage().toString(),
                    "warning", null, "middle_center", 0);
        }
        return allMantenimiento;
    }

    public void REGinsert(String ano, String numero, String status, String estado, String buque, String nom_buque, String tipo_buque, String num_atracadero,
            String num_viaje, String ant_pagados, String fecha_atraque, String fecha_zarpe, String tipo_operacion, String tipo_arribo, String cant_bodegas,
            String horas_operacion, String pk_inicial, String pasajeros, String inicio_operacion, String pk_final, String r_operador, String datos_import,
            String datos_export, String tm_import, String tm_export, String num_estibadora, String via_directa, String pto_procedencia, String pto_destino,
            String representa_naviera, String fecha_visita, String observaciones) throws SQLException {

        String sql = "INSERT INTO epqop.if_bq_arribos"
                + "(ano_arribo,"
                + " num_arribo,"
                + " status3,"
                + " estatus_arribo,"
                + " buque,"
                + " nom_buque,"
                + " tipo_buque,"
                + " num_atracadero1,"
                + " numero_viaje"
                + " ant_pagados,"
                + " fecha_atraque,"
                + " fecha_zarpe,"
                + " tipo_operacion2,"
                + " clase_arribo,"
                + " cant_bodegas,"
                + " horas_operacion,"
                + " pk_inicial,"
                + " pasajeros,"
                + " inicio_operacion,"
                + " pk_final,"
                + " estado_serv_buque,"
                + " datos_importacion,"
                + " datos_exportacion,"
                + " tm_importar1,"
                + " tm_exportar1,"
                + " estibadora,"
                + " via_directa,"
                + " puerto_procedencia,"
                + " puerta_destino,"
                + " representa_naviera,"
                + " fecha_hora_visita,"
                + " obse_actividad1"
                + " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";//sysdate puede remplazar para que la hora sea automatica
        try {
            conexion = cnn.Conexion();
            conexion.setAutoCommit(false);
            st = conexion.createStatement();

            ps = conexion.prepareStatement(sql);

            ps.setString(1, ano);
            ps.setString(2, numero);
            ps.setString(2, status);
            ps.setString(4, estado);
            ps.setString(5, buque);
            ps.setString(6, nom_buque);
            ps.setString(7, tipo_buque);
            ps.setString(8, num_atracadero);
            ps.setString(9, num_viaje);
            ps.setString(10, ant_pagados);
            ps.setString(11, fecha_atraque);
            ps.setString(12, fecha_zarpe);
            ps.setString(13, tipo_operacion);
            ps.setString(14, tipo_arribo);
            ps.setString(15, cant_bodegas);
            ps.setString(16, horas_operacion);
            ps.setString(17, pk_inicial);
            ps.setString(18, pasajeros);
            ps.setString(19, inicio_operacion);
            ps.setString(20, pk_final);
            ps.setString(21, r_operador);
            ps.setString(22, datos_import);
            ps.setString(23, datos_export);
            ps.setString(24, tm_import.replace(",", ""));
            ps.setString(25, tm_export.replace(",", ""));
            ps.setString(26, num_estibadora);
            ps.setString(27, via_directa);
            ps.setString(28, pto_procedencia);
            ps.setString(29, pto_destino);
            ps.setString(30, representa_naviera);
            ps.setString(31, fecha_visita);
            ps.setString(32, observaciones);
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

    public void REGupdate(String ano, String numero, String status, String estado, String num_buque, String nom_buque, String tipo_buque, String num_atracadero,
            String num_viaje, String ant_pagados, String fecha_atraque, String fecha_zarpe, String tipo_operacion, String tipo_arribo, String cant_bodegas,
            String horas_operacion, String pk_inicial, String pasajeros, String inicio_operacion, String pk_final, String r_operador, String datos_import,
            String datos_export, String tm_import, String tm_export, String num_estibadora, String via_directa, String pto_procede, String pto_destino,
            String representa_naviera, String fecha_visita, String observaciones) throws SQLException {

        try {
            conexion = cnn.Conexion();
            conexion.setAutoCommit(false);
            st = conexion.createStatement();

            st.executeUpdate("UPDATE epqop.if_bq_arribos SET "
                    + "estatus_arribo = '" + estado + "'"
                    + ",status3 = '" + status + "'"
                    + ",buque = '" + num_buque + "'"
                    + ",nom_buque = '" + nom_buque + "'"
                    + ",tipo_buque = '" + tipo_buque + "'"
                    + ",num_atracadero1 = '" + num_atracadero + "'"
                    + ",numero_viaje = '" + num_viaje + "'"
                    + ",ant_pagados = '" + ant_pagados + "'"
                    + ",fecha_atraque = '" + fecha_atraque + "'"
                    + ",fecha_zarpe = '" + fecha_zarpe + "'"
                    + ",tipo_operacion2 = '" + tipo_operacion + "'"
                    + ",clase_arribo = '" + tipo_arribo + "'"
                    + ",ant_pagados = '" + ant_pagados + "'"
                    + ",horas_operacion = '" + horas_operacion + "'"
                    + ",pk_inicial = '" + pk_inicial + "'"
                    + ",pasajeros = '" + pasajeros + "'"
                    + ",inicio_operacion = '" + inicio_operacion + "'"
                    + ",pk_final = '" + pk_final + "'"
                    + ",r_operador = '" + r_operador + "'"
                    + ",datos_importacion = '" + datos_import + "'"
                    + ",datos_exportacion = '" + datos_export + "'"
                    + ",tm_importar1 = '" + tm_import + "'"
                    + ",tm_exportar1 = '" + tm_export + "'"
                    + ",estibadora = '" + num_estibadora + "'"
                    + ",via_directa = '" + via_directa + "'"
                    + ",puerto_procedencia = '" + pto_procede + "'"
                    + ",puerto_destino = '" + pto_destino + "'"
                    + ",representa_naviera = '" + representa_naviera + "'"
                    + ",fecha_hora_visita = '" + fecha_visita + "'"
                    + ",obse_actividad1 = '" + observaciones + "'"
                    + " WHERE ano_arribo = '" + ano + "' "
                    + " AND num_arribo = '" + numero + "' ");

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

    public String Ecancela(String ano_arribo, String num_arribo) throws SQLException {

        String estado = "";
        try {
            conexion = cnn.Conexion();
            st = conexion.createStatement();

            rs = st.executeQuery("SELECT estado_de_cancelacion FROM  epqop.if_bq_arribos "
                    + "WHERE ano_arribo = '" + ano_arribo + "' "
                    + "AND num_arribo = '" + num_arribo + "'");
            while (rs.next()) {
                estado = rs.getString("estado_de_cancelacion");
            }
            st.close();
            conexion.close();

        } catch (SQLException e) {
            String mensaje = e.getMessage();
            Clients.showNotification("ERROR AL SELECCIONAR EL REGISTRO <br/>'" + mensaje + "'",
                    "warning", null, "middle_center", 0);
        }
        return estado;
    }

    public List<BuquesMd> BuquesSelect() throws SQLException {
        List<BuquesMd> allBuques = new ArrayList<BuquesMd>();

        String query = "SELECT TRIM(buque), "
                + " TRIM(nom_buque) "
                + " FROM epqop.if_bq_buques"
                + " ORDER BY buque ASC ";
        try {
            conexion = cnn.Conexion();
            st = conexion.createStatement();
            rs = st.executeQuery(query);
            BuquesMd rg;
            while (rs.next()) {
                rg = new BuquesMd();
                rg.setNum_buque(rs.getString(1));
                rg.setNom_buque(rs.getString(2));

                allBuques.add(rg);
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
            Clients.showNotification("ERROR AL CONSULTAR (Rselect) <br/> <br/> REGISTROS! <br/> " + e.getMessage().toString(),
                    "warning", null, "middle_center", 0);
        }
        return allBuques;
    }

}
