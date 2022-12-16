package DAL;

import Conexion.Conexion;
import MD.ReporteServiciosParticularesMd;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReporteServiciosParticularesDal {
    
    private Connection conexion = null;
    private Conexion cnn = new Conexion();
    // PreparedStatement ps = null;
    PreparedStatement st = null;
    ResultSet rs = null;
    
    public List<ReporteServiciosParticularesMd> REGselect(String fechaInicio, String fechaFin, String codigo) throws SQLException, ClassNotFoundException {
        List<ReporteServiciosParticularesMd> allReporteCli = new ArrayList<ReporteServiciosParticularesMd>();
        
        String query = "SELECT a.ano_arribo, a.num_arribo, d.nom_buque,  \n"
                + "       to_char (c.fecha_atraque,'DD/MM/RRRR') || to_char(c.hora_atraque,'HH24:MI:SS') AS ATRAQUE,\n"
                + "       b.nombre_particular, a.codigo_servicio, e.descripcion_servic, a.numero_factura,\n"
                + "       to_char (a.fecha_inicio1,'DD/MM/RRRR') || to_char(a.hora_inicio1,'HH24:MI:SS') AS FECHA_INICIO,\n"
                + "       to_char (a.fecha_fin1,'DD/MM/RRRR') || to_char(a.hora_fin2,'HH24:MI:SS') AS FECHA_FIN,\n"
                + "       a.obse_servicio\n"
                + "FROM   epqop.if_bq_servicios a,\n"
                + "       epqop.particulares b,\n"
                + "       epqop.if_bq_arribos c,\n"
                + "       epqop.if_bq_buques d,\n"
                + "       epqop.if_ca_tarifas e\n"
                + "WHERE  a.codigo_particular = b.codigo_particular\n"
                + "AND    a.ano_arribo = c.ano_arribo\n"
                + "and    a.num_arribo = c.num_arribo\n"
                + "and    c.buque      = d.buque\n"
                + "and    a.codigo_servicio = e.codigo_servicio\n"
                + " AND      a.fecha_inicio1 >= to_date('" + fechaInicio + "','DD/MM/RRRR')\n"
                + " AND      a.fecha_fin1 <= to_date('" + fechaFin + "','DD/MM/RRRR')+1\n"
                + " AND      b.codigo_particular = " + codigo + "";
        
        System.out.println("esta es la fecha inicio " + fechaInicio + "esta es la fecha fin " + fechaFin + "esta es el codigo " + codigo);
        
        try {
            conexion = cnn.Conexion();
            st = conexion.prepareStatement(query);
            rs = st.executeQuery();
            ReporteServiciosParticularesMd rg;
            while (rs.next()) {
                rg = new ReporteServiciosParticularesMd();
                
                rg.setAnio_arribo(rs.getString(1));
                rg.setNumero_arribo(rs.getString(2));
                rg.setNom_buque(rs.getString(3));
                rg.setFecha_atraque(rs.getString(4));
                rg.setParticular(rs.getString(5));
                rg.setCod_servicio(rs.getString(6));
                rg.setDesc_servicio(rs.getString(7));
                rg.setFactura(rs.getString(8));
                rg.setFecha_inicio(rs.getString(9));
                rg.setFecha_fin(rs.getString(10));
                rg.setObservacion(rs.getString(11));
                
                allReporteCli.add(rg);
            }
            st.close();
            rs.close();
            conexion.close();
            conexion = null;
            
        } catch (SQLException e) {
            System.out.println("este es el erro " + e);
            st.close();
            rs.close();
            conexion.close();
            conexion = null;
            
        }
        return allReporteCli;
        
    }
    
}
