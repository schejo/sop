package DAL;

import Conexion.Conexion;
import MD.ReportePesajesBasculasMd;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.zk.ui.util.Clients;

public class ReportePesajesBasculasDal {

    public List<ReportePesajesBasculasMd> GetByFecha(String fechaInicio, String fechaFin) throws SQLException {
        List<ReportePesajesBasculasMd> respuesta = new ArrayList<ReportePesajesBasculasMd>();
        PreparedStatement smt = null;
        Connection conn = null;
        Conexion conx = new Conexion();
        ResultSet rs = null;
        conn = conx.Conexion();
        try {

                String sql =" SELECT  EPQOP.IH_CM_PESAJE_CAMIO.FECHA_PESAJE_1 AS FECHA_ENTRADA,"
                                    +"EPQOP.IH_CM_PESAJE_CAMIO.PESO_BRUTO AS PESO_ENTRADA,"
                             +"DECODE(EPQOP.IH_CM_PESAJE_CAMIO.EMPRESA_BASCULA_1, '25','NEPORSA','26','REPIMEX','149','BAGAGO') AS BASCULA_ENTRADA,"
                                    +"EPQOP.IH_CM_PESAJE_CAMIO.PESO_TARA AS PESO_SALIDA,"
                                    +"EPQOP.IH_CM_PESAJE_CAMIO.FECHA_PESAJE_2 AS FECHA_SALIDA,"
                             +"DECODE(EPQOP.IH_CM_PESAJE_CAMIO.EMPRESA_BASCULA_2,'25','NEPORSA','26','REPIMEX','149','BAGAGO' ) AS BASCULA_SALIDA"
                         +" FROM      EPQOP.IH_CM_CICLO_CAMIO,"
                                   +" EPQOP.IH_CM_PESAJE_CAMIO"
                           +" WHERE ( EPQOP.IH_CM_CICLO_CAMIO.FECHA_CICLO = EPQOP.IH_CM_PESAJE_CAMIO.FECHA_CICLO )"
                           +" AND   ( EPQOP.IH_CM_CICLO_CAMIO.NUM_CICLO = EPQOP.IH_CM_PESAJE_CAMIO.NUM_CICLO )"
                           +" AND   ( EPQOP.IH_CM_PESAJE_CAMIO.FECHA_PESAJE_1 >= TO_DATE('"+fechaInicio+"','DD/MM/YYYY')\n"
                           +" AND   ( EPQOP.IH_CM_PESAJE_CAMIO.FECHA_PESAJE_1 <= TO_DATE('"+fechaFin+"','DD/MM/YYYY')\n"
                           +"ORDER BY EPQOP.IH_CM_PESAJE_CAMIO.FECHA_PESAJE_1";

                    
            smt = conn.prepareStatement(sql);
            rs = smt.executeQuery();
            ReportePesajesBasculasMd rp;
            while (rs.next()) {
                rp = new ReportePesajesBasculasMd();
                rp.setFecha_entrada(rs.getString(1));
                rp.setPeso_entrada(rs.getString(2));
                rp.setEmpresa_bascula_1(rs.getString(3));
                rp.setPeso_tara(rs.getString(4));
                rp.setFecha_pesaje_2(rs.getString(5));
                rp.setEmpresa_bascula_2(rs.getString(6));
                respuesta.add(rp);
            }

        } catch (Exception e) {
            Clients.showNotification(e.getMessage());
        } finally {
            if (smt != null) {
                smt.close();
                smt = null;
            }
            if (rs != null) {
                rs.close();
                rs = null;
            }
            if (conn != null) {
                conn = conx.desconectar();
            }
        }
        //System.out.println("Datos.: " + respuesta);
        return respuesta;
    }

}
