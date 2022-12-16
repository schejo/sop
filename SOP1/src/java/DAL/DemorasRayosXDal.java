/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAL;

import Conexion.Conexion;
import MD.DemorasRayosXMd;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.zk.ui.util.Clients;

/**
 *
 * @author Informatica
 */
public class DemorasRayosXDal {

    private Connection conexion = null;
    private Conexion cnn = new Conexion();
    PreparedStatement ps = null;
    //Statement st = null;
    ResultSet rs = null;

    public List<DemorasRayosXMd> Demoras(String anio, String arribo) throws SQLException {
        List<DemorasRayosXMd> alldata = new ArrayList<DemorasRayosXMd>();

        String query = "select  b.identifica_contene, e.nom_buque,\n"
                + "        --f.nom_naviera,\n"
                + "        to_char(b.fecha_inicial2,'DD/MM/YYYY HH24:MI:SS')as inicio,\n"
                + "        to_char(b.fecha_final2,'DD/MM/YYYY HH24:MI:SS')as fin,\n"
                + "        nvl(trim(b.obse_activ2),' '),\n"
                + "        TO_CHAR(TO_DATE(ROUND(((fecha_final2-fecha_inicial2)*24*60*60),2),'sssss'),'hh24:mi:ss') as Diferencia "
                + "        --ROUND(((TO_DATE(b.fecha_final2,'DD/MM/YYYY HH24:MI:SS') - TO_DATE(b.fecha_inicial2,'DD/MM/YYYY HH24:MI:SS'))*24*60*60),2) as Diferencia \n"
                + "                --24 * (to_date(to_char(b.fecha_final2,'DD/MM/YYYY hh24:mi:ss'),'DD/MM/YYYY hh24:mi:ss') - to_date(b.fecha_inicial2,'DD/MM/YYYY hh24:mi:ss') ) as Diferencia \n"
                + "        --TO_CHAR((TO_DATE('00:00:00','HH24:MI:SS')) + (TO_DATE(b.fecha_final2, 'DD/MM/YYYY HH24:MI:SS') - TO_DATE(b.fecha_inicial2, 'DD/MM/YYYY HH24:MI:SS')), 'HH24:MI:SS') Diferencia\n"
                + "                    from epqop.if_ca_contenedores a,\n"
                + "                         epqop.if_ca_activi_conte b, --// fech_final2 -fecha inicial2\n"
                + "                         epqop.if_bq_arribos d,\n"
                + "                         epqop.if_bq_buques e,\n"
                + "                         epqop.if_bq_naviera f\n"
                + "                where  a.identifica_contene = b.identifica_contene\n"
                + "                and    b.ano_arribo2 = d.ano_arribo\n"
                + "                and    b.num_arribo2 = d.num_arribo\n"
                + "                and    d.buque =  e.buque\n"
                + "                and    a.naviera =f.naviera1\n"
                + "                and    d.ano_arribo = ? \n"
                + "                and    d.num_arribo = ? \n"
                + "                --and    a.naviera    = '\"+naviera+\"'\n"
                + "                --and    b.obse_activ2    = '\"+act+\"'\n"
                + "                and    b.codigo_actividad = 45\n"
                + "                union all\n"
                + "                select b.identifica_contene, e.nom_buque,\n"
                + "                --f.nom_naviera,\n"
                + "                to_char(b.fecha_inicial2,'DD/MM/YYYY HH24:MI:SS')as inicio,\n"
                + "                to_char(b.fecha_final2,'DD/MM/YYYY HH24:MI:SS')as fin,\n"
                + "                nvl(trim(b.obse_activ2),' '),\n"
                + "        TO_CHAR(TO_DATE(ROUND(((fecha_final2-fecha_inicial2)*24*60*60),2),'sssss'),'hh24:mi:ss') as Diferencia "
                + "        --ROUND(((TO_DATE(b.fecha_final2,'DD/MM/YYYY HH24:MI:SS') - TO_DATE(b.fecha_inicial2,'DD/MM/YYYY HH24:MI:SS'))*24*60*60),2) as Diferencia \n"
                + "                --24 * (to_date(to_char(b.fecha_final2,'DD/MM/YYYY hh24:mi:ss'),'DD/MM/YYYY hh24:mi:ss') - to_date(b.fecha_inicial2,'DD/MM/YYYY hh24:mi:ss') ) as Diferencia \n"
                + "                --TO_CHAR((TO_DATE('00:00:00','HH24:MI:SS')) + (TO_DATE(b.fecha_final2, 'DD/MM/YYYY HH24:MI:SS') - TO_DATE(b.fecha_inicial2, 'DD/MM/YYYY HH24:MI:SS')), 'HH24:MI:SS') Diferencia\n"
                + "                  from  epqop.ih_ca_contenedores a,\n"
                + "                         epqop.ih_ca_activi_conte b,\n"
                + "                         epqop.if_bq_arribos d,\n"
                + "                         epqop.if_bq_buques e,\n"
                + "                         epqop.if_bq_naviera f\n"
                + "                where a.identifica_contene = b.identifica_contene\n"
                + "                and    a.correla_contene    = b.correla_contene\n"
                + "                and    b.ano_arribo2 = d.ano_arribo\n"
                + "                and    b.num_arribo2 = d.num_arribo\n"
                + "                and    d.buque =  e.buque\n"
                + "                and    a.naviera =f.naviera1\n"
                + "                and    d.ano_arribo = ? \n"
                + "                and    d.num_arribo = ? \n"
                + "                --and    a.naviera    = '\"+naviera+\"'\n"
                + "                --and    b.obse_activ2    = '\"+act+\"'\n"
                + "                and    b.codigo_actividad = 45 order by Diferencia desc";
        try {
            conexion = cnn.Conexion();
            ps = conexion.prepareStatement(query);
            ps.setString(1, anio);
            ps.setString(2, arribo);
            ps.setString(3, anio);
            ps.setString(4, arribo);
            rs = ps.executeQuery();
            DemorasRayosXMd rg;
            int x = 0;
            //System.out.println("ACTIVIDAD...: "+actividad);
            while (rs.next()) {
                rg = new DemorasRayosXMd();
                x++;
                rg.setContenedor(rs.getString(1));
                rg.setBuque(rs.getString(2));
                rg.setInicio(rs.getString(3));
                rg.setFin(rs.getString(4));
                rg.setActividad(rs.getString(5));
                rg.setDiferencia(rs.getString(6));

                alldata.add(rg);
            }

            ps.close();
            conexion.close();
            conexion = null;
        } catch (SQLException e) {
            conexion.close();
            conexion = null;
            Clients.showNotification("ERROR AL CONSULTAR <br/> <br/> REGISTROS! <br/> " + e.getMessage().toString(),
                    "warning", null, "middle_center", 0);
        }
        return alldata;
    }

}
