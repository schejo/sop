/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAL;

import Conexion.Conexion;
import MD.ConfirmaMuelleExportIHMd;
import MD.ConfirmaMuelleExportMd;
import MD.ConfirmaMuelleMd;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.zk.ui.util.Clients;

/**
 *
 * @author Informatica
 */
public class ConfirmaMuelleDal {

    private Connection conexion = null;
    private Conexion cnn = new Conexion();
    PreparedStatement ps = null;
    Statement st = null;
    ResultSet rs = null;
    ResultSet rs2 = null;
    ResultSet rs3 = null;
    ResultSet rs4 = null;

    public List<ConfirmaMuelleMd> VacioLleno(String anio, String num, String contenedor) {
        //vacio="";
        List<ConfirmaMuelleMd> alldata = new ArrayList<ConfirmaMuelleMd>();
        String query = " select anio, manif, tipo, status, bl, puerto, nombre, equipo from( "
                + "SELECT a.ano_manifiesto as anio, a.manifiesto as manif, b.COD_TIPO_CONTENED as tipo, "
                + " trim(b.ESTADO_CONTENEDOR) as status, c.correlativo_bl as bl, c.puerto as puerto, a.nombre_buque as nombre, "
                + " trim(b.num_contenedor) as equipo \n"
                + "    FROM    EPQOP.if_ma_manifiestos a, \n"
                + "            EPQOP.if_ma_contene_bl b,"
                + "            EPQOP.if_ma_encabeza_bl c \n"
                + "    where   a.ANO_MANIFIESTO = b.ano_manifiesto \n"
                + "    AND     a.manifiesto     = b.manifiesto \n"
                + "    AND     a.ano_manifiesto = c.ano_manifiesto "
                + "    AND     a.manifiesto    = c.manifiesto"
                + "    AND     trim(a.ano_arribo)    =   '" + anio + "' \n"
                + "    AND     trim(a.num_arribo)    =   '" + num + "'\n"
                + "    AND     trim(b.num_contenedor) LIKE '%" + contenedor + "' order by a.manifiesto desc )"
                + "    where rownum <= 1 ";
        System.out.println("ANIO.: " + anio);
        System.out.println("NUMERO.: " + num);
        System.out.println("CONTENEDOR.: " + contenedor);
        try {
            conexion = cnn.Conexion();
            st = conexion.createStatement();
            rs = st.executeQuery(query);
            ConfirmaMuelleMd cp = new ConfirmaMuelleMd();
            while (rs.next()) {
                cp.setAnio(rs.getString("anio"));
                cp.setManif(rs.getString("manif"));
                cp.setTipo(rs.getString("tipo"));
                cp.setEstado(rs.getString("status"));
                cp.setCorrelaBl(rs.getString("bl"));
                cp.setPuerto(rs.getString("puerto"));
                cp.setNombre(rs.getString("nombre"));
                cp.setContenedor(rs.getString("equipo"));
                //System.out.println("VACIO_LLENO.: " + rs.getString("vacio_lleno"));
                //vacio = rs.getString("vacio_lleno");
            }

            alldata.add(cp);
            st.close();
            conexion.close();
            cnn.desconectar();
        } catch (Exception e) {
            System.out.println("CONTENEDOR..: " + e.getMessage());
            Clients.showNotification("ERROR CATCH Vacio_Lleno..!" + e.getMessage());
        }

        return alldata;
    }

    public void Guardar(String contenedor, String fechain2, String actividad, String pesoB, String marchamoA,
            String marchamo1, String anio2, String num2, String anioMa1, String correlaB1, String Vacio, String cequipo,
            String nomBuque, String calificaman, String puerto, String areaUbica, String via, String usuario, String manif) {

        List<ConfirmaMuelleExportMd> alldata = new ArrayList<ConfirmaMuelleExportMd>();
        List<ConfirmaMuelleExportIHMd> alldataIH = new ArrayList<ConfirmaMuelleExportIHMd>();
        alldata.clear();

        String corr = "";
        String corrH = "";
        //EPQOP.if_ca_activi_conte
        //ACTUALES
        String query = "Select nvl(MAX(correlativo),0)+1 as correlativo \n"
                + " From EPQOP.if_ca_activi_conte\n"
                + " Where trim(identifica_contene) = '" + contenedor + "' ";

        String sql = "INSERT INTO EPQOP.if_ca_activi_conte (identifica_contene, fecha_inicial2, codigo_actividad, hora_inicial4, "
                + " peso_bruto_recibid, marchamo_aduana, marchamo_1, ano_arribo2, num_arribo2, ano_manifiesto1, correlativo_bl1, "
                + " vacio_lleno, codigo_equipo, nom_buque, calificador_manifi, correlativo, puerto, area_ubicacion, via_directa, "
                + " fecha_alta, hora_alta, usuario) "
                + " VALUES (?,TO_DATE(?,'DD/MM/YYYY hh24:mi:ss'),?,TO_DATE(?,'DD/MM/YYYY hh24:mi:ss'),?,?,?,?,?,?,?,?,?,?,?,?,?,"
                + " ?,?,TO_DATE(?,'DD/MM/YYYY hh24:mi:ss'),TO_DATE(?,'DD/MM/YYYY hh24:mi:ss'),?)";
        //FIN ACTUALES
        
        //HISTORICOS
        //Buscamos el Maximo en Encabezacdo IH_CA_CONTENEDORES
        String query2 = "select nvl(MAX(identifica_contene),0)+1 as correlativo from EPQOP.ih_ca_contenedores where trim(identifica_contene)='" + contenedor + "' ";

        //BUSCAMOS EL ENCABEZADO EN ACTUALES LA LINEA A INSERTAR EN HISTORICO
        String query3 = "select identifica_contene, tipo_contenedor, linea, naviera, fecha_ingreso2, hora_ingreso2, ubicacion_1, peso_bruto_recibid, peso_tara, peso_neto_bascula,"
                + " vacio_lleno, marchamo_aduana, marchamo_1, modo_entrada, status1, usuario, fecha_alta, manifiesto, ano_manifiesto, hora_alta, area_ubicacion, transito "
                + " FROM EPQOP.if_ca_contenedores "
                + " WHERE trim(identifica_contene) = '" + contenedor + "' ";
        
        //BUSCAMOS LAS ACTIVIDADES EN ACTUALES PARA INSERTAR EN HISTORICOS
        String query4 = "select identifica_contene, fecha_inicial2, codigo_actividad, hora_inicial4, "
                + " peso_bruto_recibid, marchamo_aduana, marchamo_1, ano_arribo2, num_arribo2, ano_manifiesto1, correlativo_bl1, "
                + " vacio_lleno, codigo_equipo, nom_buque, calificador_manifi, correlativo, puerto, area_ubicacion, via_directa, "
                + " fecha_alta, hora_alta, usuario"
                + " FROM EPQOP.if_ca_activi_conte "
                + " WHERE trim(identifica_contene) = '" + contenedor + "' ";

        String sql2 = "INSERT INTO EPQ.ih_ca_contenedores (identifica_contene, tipo_contenedor, linea, naviera, fecha_ingreso2, hora_ingreso2, ubicacion_1, peso_bruto_recibid, peso_tara, peso_neto_bascula,"
                + "vacio_lleno, marchamo_aduana, marchamo_1, modo_entrada, status1, usuario, fecha_alta, manifiesto, ano_manifiesto, hora_alta, area_ubicacion, transito, correla_contene) "
                + "VALUES(?,?,?,?,TO_DATE(?,'DD/MM/YYYY hh24:mi:ss'),TO_DATE(?,'DD/MM/YYYY hh24:mi:ss'),?,?,?,?,?,?,?,?,?,?,TO_DATE(?,'DD/MM/YYYY hh24:mi:ss'),?,?,TO_DATE(?,'DD/MM/YYYY hh24:mi:ss'),?,?,?)";

        String slq3 = "INSERT INTO EPQOP.ih_ca_activi_conte (identifica_contene, fecha_inicial2, codigo_actividad, hora_inicial4, "
                + " peso_bruto_recibid, marchamo_aduana, marchamo_1, ano_arribo2, num_arribo2, ano_manifiesto1, correlativo_bl1, "
                + " vacio_lleno, codigo_equipo, nom_buque, calificador_manifi, correlativo, puerto, area_ubicacion, via_directa, "
                + " fecha_alta, hora_alta, usuario) "
                + " VALUES (?,TO_DATE(?,'DD/MM/YYYY hh24:mi:ss'),?,TO_DATE(?,'DD/MM/YYYY hh24:mi:ss'),?,?,?,?,?,?,?,?,?,?,?,?,?,"
                + " ?,?,TO_DATE(?,'DD/MM/YYYY hh24:mi:ss'),TO_DATE(?,'DD/MM/YYYY hh24:mi:ss'),?)";

        try {
            conexion = cnn.Conexion();
            st = conexion.createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
                corr = rs.getString("correlativo");
                System.out.println("CORRRELATIVO.: " + corr);
            }

            ps = conexion.prepareStatement(sql);
            ps.setString(1, contenedor);
            ps.setString(2, fechain2);
            ps.setString(3, actividad);
            ps.setString(4, fechain2);
            ps.setString(5, pesoB);
            ps.setString(6, marchamoA);
            ps.setString(7, marchamo1);
            ps.setString(8, anio2);
            ps.setString(9, num2);
            ps.setString(10, anioMa1);
            ps.setString(11, correlaB1);
            ps.setString(12, Vacio);
            ps.setString(13, cequipo);
            ps.setString(14, nomBuque);
            ps.setString(15, calificaman);
            ps.setString(16, corr);
            ps.setString(17, puerto);
            ps.setString(18, areaUbica);
            ps.setString(19, via);
            ps.setString(20, fechain2);
            ps.setString(21, fechain2);
            ps.setString(22, usuario);
            ps.executeUpdate();
            ps.close();
            System.out.println("INSERCION EXITOSA..!");

            //BUSCA CORRELATIVO EN HISTORICOS
            rs2 = st.executeQuery(query2);
            while (rs2.next()) {
                corrH = rs2.getString("correlativo");
                System.out.println("CORRRELATIVO.: " + corrH);
            }

            ConfirmaMuelleExportMd cme;
            rs3 = st.executeQuery(query3);
            while (rs3.next()) {
                cme = new ConfirmaMuelleExportMd();
                cme.setIdentConte(rs3.getString("identifica_contene"));
                cme.setTipoConte(rs3.getString("tipo_contenedor"));
                cme.setLinea(rs3.getString("linea"));
                cme.setNaviera(rs3.getString("naviera"));
                cme.setFechaIn2(rs3.getString("fecha_ingreso2"));
                cme.setHoraIn2(rs3.getString("hora_ingreso2"));
                cme.setUbica1(rs3.getString("ubicacion_1"));
                cme.setPesoBR(rs3.getString("peso_bruto_recibid"));
                cme.setPesoT(rs3.getString("peso_tara"));
                cme.setPesoNB(rs3.getString("peso_neto_bascula"));
                cme.setVscioLleno(rs3.getString(" vacio_lleno"));
                cme.setMarchamoA(rs3.getString("marchamo_aduana"));
                cme.setMarchamo1(rs3.getString("marchamo_1"));
                cme.setModoE(rs3.getString("modo_entrada"));
                cme.setEstado(rs3.getString("status1"));
                cme.setUsuario(rs3.getString("usuario"));
                cme.setFechaAlta(rs3.getString("fecha_alta"));
                cme.setManif(rs3.getString("manifiesto"));
                cme.setAnoManif(rs3.getString("ano_manifiesto"));
                cme.setHoraAlta(rs3.getString("hora_alta"));
                cme.setAreaUbica(rs3.getString("area_ubicacion"));
                cme.setTransito(rs3.getString("transito"));
                alldata.add(cme);
            }
            
            ConfirmaMuelleExportIHMd cmeih;
            rs4 = st.executeQuery(query4);
            while (rs4.next()){
                cmeih = new ConfirmaMuelleExportIHMd();
                cmeih.setIdentifica_contene(rs4.getString("identifica_contene"));
                cmeih.setFecha_inicial2(rs4.getString("fecha_inicial2"));
                cmeih.setCodigo_actividad(rs4.getString("codigo_actividad"));
                cmeih.setHora_inicial4(rs4.getString("hora_inicial4"));
                cmeih.setPeso_bruto_recibid(rs4.getString("peso_bruto_recibido"));
                cmeih.setMarchamo_aduana(rs4.getString("marchamo_aduana"));
                cmeih.setMarchamo_1(rs4.getString("marchamo_1"));
                cmeih.setAno_arribo2(rs4.getString("ano_arribo"));
                cmeih.setNum_arribo2(rs4.getString("num_arribo2"));
                cmeih.setAno_manifiesto1(rs4.getString("ano_manifiesto1"));
                cmeih.setCorrelativo_bl1(rs4.getString("correlativo_bl1"));
                cmeih.setVacio_lleno(rs4.getString("vacio_lleno"));
                cmeih.setCodigo_equipo(rs4.getString("codigo_equipo"));
                cmeih.setNom_buque(rs4.getString("nom_buque"));
                cmeih.setCalificador_manifi(rs4.getString("calificador_manifi"));
                cmeih.setCorrelativo(rs4.getString("correlativo"));
                cmeih.setPuerto(rs4.getString("puerto"));
                cmeih.setArea_ubicacion(rs4.getString("area_ubicacion"));
                cmeih.setVia_directa(rs4.getString("via_directa"));
                cmeih.setFecha_alta(rs4.getString("fecha_alta"));
                cmeih.setHora_alta(rs4.getString("hora_alta"));
                cmeih.setUsuario(rs4.getString("usuario"));
                alldataIH.add(cmeih);
            }

            ps = conexion.prepareStatement(sql2);
            for (ConfirmaMuelleExportMd item : alldata) {
                ps.setString(1, item.getIdentConte());
                ps.setString(2, item.getTipoConte());
                ps.setString(3, item.getLinea());
                ps.setString(4, item.getNaviera());
                ps.setString(5, item.getFechaIn2());
                ps.setString(6, item.getHoraIn2());
                ps.setString(7, item.getUbica1());
                ps.setString(8, item.getPesoBR());
                ps.setString(9, item.getPesoT());
                ps.setString(10, item.getPesoNB());
                ps.setString(11, item.getVscioLleno());
                ps.setString(12, item.getMarchamoA());
                ps.setString(13, item.getMarchamo1());
                ps.setString(14, item.getModoE());
                ps.setString(15, item.getEstado());
                ps.setString(16, item.getUsuario());
                ps.setString(17, item.getFechaAlta());
                ps.setString(18, item.getManif());
                ps.setString(19, item.getAnoManif());
                ps.setString(20, item.getHoraAlta());
                ps.setString(21, item.getAreaUbica());
                ps.setString(22, item.getTransito());
                ps.setString(23, corrH);
                ps.executeUpdate();
            }
            ps.close();
            System.out.println("INSERCION EXITOSA..! HISTORICO CONTENEDORES");
            
            ps = conexion.prepareStatement(sql2);
            for (ConfirmaMuelleExportIHMd item : alldataIH) {
                ps.setString(1, item.getIdentifica_contene());
                ps.setString(2, item.getFecha_inicial2());
                ps.setString(3, item.getCodigo_actividad());
                ps.setString(4, item.getHora_inicial4());
                ps.setString(5, item.getPeso_bruto_recibid());
                ps.setString(6, item.getMarchamo_aduana());
                ps.setString(7, item.getMarchamo_1());
                ps.setString(8, item.getAno_arribo2());
                ps.setString(9, item.getNum_arribo2());
                ps.setString(10, item.getAno_manifiesto1());
                ps.setString(11, item.getCorrelativo_bl1());
                ps.setString(12, item.getVacio_lleno());
                ps.setString(13, item.getCodigo_equipo());
                ps.setString(14, item.getNom_buque());
                ps.setString(15, item.getCalificador_manifi());
                ps.setString(16, item.getCorrelativo());
                ps.setString(17, item.getPuerto());
                ps.setString(18, item.getArea_ubicacion());
                ps.setString(19, item.getVia_directa());
                ps.setString(20, item.getFecha_alta());
                ps.setString(21, item.getHora_alta());
                ps.setString(22, item.getUsuario());
                ps.setString(23, corrH);
                ps.executeUpdate();
            }
            ps.close();
            System.out.println("INSERCION EXITOSA..! HISTORICO ACTIVI CONTE");

            st.close();
            conexion.close();

        } catch (Exception e) {
            System.out.println("ERROR..!" + e.getMessage());
        }

    }

}
