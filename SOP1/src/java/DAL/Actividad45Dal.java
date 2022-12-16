package DAL;

import Conexion.Conexion;
import MD.Actividad45Md;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.zk.ui.util.Clients;


public class Actividad45Dal {

    private Connection conexion = null;
    private Conexion cnn = new Conexion();
    PreparedStatement ps = null;
    Statement st = null;
    ResultSet rs = null;

    public List<Actividad45Md> REGselect(String anio) throws SQLException {
        List<Actividad45Md> alldata = new ArrayList<Actividad45Md>();
        
        String query =" select identifica_contene, \n"
                + " to_char(fecha_inicial2, 'dd/mm/yyyy hh24:mi:ss' ), \n"
                + " decode (trim(obse_activ2),'I','IMPORTACION','E','EXPORTACION','','NULO') as ACTIVIDAD, \n"
                + " to_char(fecha_alta, 'dd/mm/yyyy hh24:mi:ss' ) \n" 
                + " from epqop.if_ca_activi_conte \n "
                + " where codigo_actividad = 45 \n"
                + " and identifica_contene = '"+anio+"' ";
        
           try {
            conexion = cnn.Conexion();
            st = conexion.createStatement();
            rs = st.executeQuery(query);
            Actividad45Md rg;
            int x = 0;
            //System.out.println("ACTIVIDAD...: "+actividad);
            while (rs.next()) {
                rg = new Actividad45Md();
                x++;
                rg.setCorrelativo(String.valueOf(x));
                
                rg.setNcontenedor(rs.getString(1));
                rg.setFechas1(rs.getString(2));
                rg.setNaviera(rs.getString(3));
                rg.setFechas2(rs.getString(4));

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
            Clients.showNotification("ERROR AL CONSULTAR ACTIVIDAD NO INTRUSIVA ... <br/> <br/> REGISTROS! <br/> " + e.getMessage().toString(),
                    "warning", null, "middle_center", 0);
        }
        return alldata;
    }

}


