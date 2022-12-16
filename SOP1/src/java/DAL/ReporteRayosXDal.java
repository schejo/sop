package DAL;

import Conexion.Conexion;
import MD.ReporteRayosXMd;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.zk.ui.util.Clients;


public class ReporteRayosXDal {

    private Connection conexion = null;
    private Conexion cnn = new Conexion();
    PreparedStatement ps = null;
    Statement st = null;
    ResultSet rs = null;

    public List<ReporteRayosXMd> REGselect(String anio, String arribo,String naviera, String act) throws SQLException {
        List<ReporteRayosXMd> alldata = new ArrayList<ReporteRayosXMd>();
        
        String query = " select b.identifica_contene, e.nom_buque, f.nom_naviera,nvl(trim(b.obse_activ2),' ')\n"      
                + "    from epqop.if_ca_contenedores a,\n"
                + "         epqop.if_ca_activi_conte b,\n" // fech_final2 -fecha inicial2
                + "         epqop.if_bq_arribos d,\n"
                + "         epqop.if_bq_buques e,\n"
                + "         epqop.if_bq_naviera f\n"
                + "where  a.identifica_contene = b.identifica_contene\n"
                + "and    b.ano_arribo2 = d.ano_arribo\n"
                + "and    b.num_arribo2 = d.num_arribo\n"
                + "and    d.buque =  e.buque\n"
                + "and    a.naviera =f.naviera1\n"
                + "and    d.ano_arribo = '"+anio+"' \n"
                + "and    d.num_arribo = '"+arribo+"' \n"
                + "and    a.naviera    = '"+naviera+"'\n"
                + "and    b.obse_activ2    = '"+act+"'\n"
                + "and    b.codigo_actividad = 45 \n"
                + "union all\n"
                + "select b.identifica_contene, e.nom_buque, f.nom_naviera,nvl(trim(b.obse_activ2),' ')\n"   
                + "   from  epqop.ih_ca_contenedores a,\n"
                + "         epqop.ih_ca_activi_conte b,\n"
                + "         epqop.if_bq_arribos d,\n"
                + "         epqop.if_bq_buques e,\n"
                + "         epqop.if_bq_naviera f\n"
                + "where a.identifica_contene = b.identifica_contene\n"
                + "and    a.correla_contene    = b.correla_contene\n"
                + "and    b.ano_arribo2 = d.ano_arribo\n"
                + "and    b.num_arribo2 = d.num_arribo\n"
                + "and    d.buque =  e.buque\n"
                + "and    a.naviera = f.naviera1\n"
                + "and    d.ano_arribo = '"+anio+"' \n"
                + "and    d.num_arribo = '"+arribo+"' \n"
                + "and    a.naviera    = '"+naviera+"'\n"
                + "and    b.obse_activ2    = '"+act+"'\n"
                + "and    b.codigo_actividad = 45";
           try {
            conexion = cnn.Conexion();
            st = conexion.createStatement();
            rs = st.executeQuery(query);
            ReporteRayosXMd rg;
            int x = 0;
            //System.out.println("ACTIVIDAD...: "+actividad);
            while (rs.next()) {
                rg = new ReporteRayosXMd();
                x++;
                rg.setCorrelativo(String.valueOf(x));
                rg.setNcontenedor(rs.getString(1));
                rg.setBuque(rs.getString(2));
                rg.setNaviera(rs.getString(3));
                rg.setActividad(rs.getString(4));

                alldata.add(rg);
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
            Clients.showNotification("ERROR AL CONSULTAR <br/> <br/> REGISTROS! <br/> " + e.getMessage().toString(),
                    "warning", null, "middle_center", 0);
        }
        return alldata;
    }

}
