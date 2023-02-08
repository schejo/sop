package DAL;

import Conexion.Conexion;
import MD.CataloAcBuqueMd;
import MD.CatalogosMd;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author HP 15
 */
public class CatalogoDal {

    Conexion obtener = new Conexion();
    Connection conn;
    PreparedStatement pst;
    ResultSet rs;

    public List<CatalogosMd> consulta(String ano, String arribo) {
        List<CatalogosMd> lista = new ArrayList<CatalogosMd>();

        CatalogosMd data;
        String sql = "select TRIM(a.CODIGO_SERVICIO) codi,TRIM(b.DESCRIPCION_SERVIC),TRIM(c.NOMBRE_PARTICULAR)"
                + ",TRIM(a.CODIGO_PARTICULAR),TRIM(a.COD_CLI_FACT)"
                + ",to_char(a.FECHA_INICIO1,'dd/mm/yyyy')||' '||to_char(a.HORA_INICIO1,'hh24:mi') fecha"
                + ",to_char(a.FECHA_FIN1,'dd/mm/yyyy')||' '||to_char(a.HORA_FIN2,'hh24:mi') fecha_fin"
                + ",nvl(a.NUMERO_FACTURA,0),TRIM(nvl(a.OBSE_SERVICIO,'SIN DATO')),a.CORRELATIVO\n"
                + "from epqop.if_bq_servicios a,\n"
                + "     epqop.if_ca_tarifas b,\n"
                + "     epqop.particulares c\n"
                + "     WHERE a.CODIGO_PARTICULAR=c.CODIGO_PARTICULAR and "
                + "a.CODIGO_SERVICIO=b.CODIGO_SERVICIO and ano_arribo='" + ano + "' AND num_arribo='" + arribo + "' order by a.CORRELATIVO";
//                + "select pro_id, CONCAT(pro_descripcion,' MARCA: ',pro_marca,' PRECIO Q',pro_precio_venta) from productos;";

//                
        try {
            conn = obtener.Conexion();
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {
                data = new CatalogosMd();

                data.setCodigo(rs.getString(1));
                data.setServi(rs.getString(2));
                data.setParti(rs.getString(3));
                data.setCodparti(rs.getString(4));
                data.setCodcliFac(rs.getString(5));
                data.setFecha_inicio(rs.getString(6));
                data.setFecha_fin(rs.getString(7));
                data.setBoleta(rs.getString(8));
                data.setObs(rs.getString(9));
                data.setCorrela(rs.getString(10));

                lista.add(data);
            }
            rs.close();
            pst.close();

            obtener.desconectar();
            conn.close();

        } catch (SQLException e) {
            System.out.println("Exception..: " + e.getMessage());
        }

        return lista;
    }

    public List<CataloAcBuqueMd> consulta() {
        List<CataloAcBuqueMd> lista = new ArrayList<CataloAcBuqueMd>();

        CataloAcBuqueMd data;
        String sql = " select a.ANO_ARRIBO,a.NUM_ARRIBO,to_char(a.FECHA_ATRAQUE,'dd/mm/yyyy'),TRIM(b.NOM_BUQUE),DECODE(a.status3,'V','AVISADO','A','ATRACADO','F','FONDEADO','Z','ZARPADO') AS ESTATUS_BUQUE,TRIM(c.NOM_TIPO_BUQUE) \n"
                + " from EPQOP.IF_BQ_ARRIBOS a,\n"
                + "      EPQOP.IF_BQ_BUQUES b,\n"
                + "      EPQOP.IF_BQ_TIPO_ARRIBO c\n"
                + " where \n"
                + " c.TIPO_BUQUE=a.TIPO_BUQUE and\n"
                + " a.buque=b.BUQUE and  a.ANO_ARRIBO >2019 ORDER BY  a.ANO_ARRIBO DESC,a.NUM_ARRIBO DESC";
        //                + "select TRIM(a.CODIGO_SERVICIO) codi,TRIM(b.DESCRIPCION_SERVIC),TRIM(c.NOMBRE_PARTICULAR)"
                //                + ",TRIM(a.CODIGO_PARTICULAR),TRIM(a.COD_CLI_FACT)"
                //                + ",to_char(a.FECHA_INICIO1,'dd/mm/yyyy')||' '||to_char(a.HORA_INICIO1,'hh24:mi') fecha"
                //                + ",to_char(a.FECHA_FIN1,'dd/mm/yyyy')||' '||to_char(a.HORA_FIN2,'hh24:mi') fecha_fin"
                //                + ",nvl(a.NUMERO_FACTURA,0),TRIM(nvl(a.OBSE_SERVICIO,'SIN DATO')),a.CORRELATIVO\n"
                //                + "from epqop.if_bq_servicios a,\n"
                //                + "     epqop.if_ca_tarifas b,\n"
                //                + "     epqop.particulares c\n"
                //                + "     WHERE a.CODIGO_PARTICULAR=c.CODIGO_PARTICULAR and "
                //                + "a.CODIGO_SERVICIO=b.CODIGO_SERVICIO and ano_arribo='"+ano+"' AND num_arribo='"+arribo+"' order by a.CORRELATIVO";
                ////                + "select pro_id, CONCAT(pro_descripcion,' MARCA: ',pro_marca,' PRECIO Q',pro_precio_venta) from productos;";
                //                
        try {
            conn = obtener.Conexion();
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {
                data = new CataloAcBuqueMd();

                data.setAnoAc(rs.getString(1));
                data.setNumAc(rs.getString(2));
                data.setFecha_atraque(rs.getString(3));
                data.setBuque(rs.getString(4));
                data.setEstado(rs.getString(5));
                data.setTipoBu(rs.getString(6));
              
//                data.setFecha_fin(rs.getString(7));
//                data.setBoleta(rs.getString(8));
//                data.setObs(rs.getString(9));
//                data.setCorrela(rs.getString(10));

                lista.add(data);
            }
            rs.close();
            pst.close();

            obtener.desconectar();
            conn.close();

        } catch (SQLException e) {
            System.out.println("Exception..: " + e.getMessage());
        }

        return lista;
    }

}
