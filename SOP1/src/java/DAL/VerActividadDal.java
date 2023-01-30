package DAL;

import Conexion.Conexion;
import MD.VerActividadMd;
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
public class VerActividadDal {

    Conexion obtener = new Conexion();
    Connection conn;
    PreparedStatement pst;
    ResultSet rs;

    public List<VerActividadMd> consulta(String ano, String arribo) {
        List<VerActividadMd> lista = new ArrayList<VerActividadMd>();

        VerActividadMd data;

        String sql = "SELECT TRIM(a.NUM_ACTIVIDAD1) || ' '|| TRIM(b.NOM_ACTIVIDAD) actividad,\n"
                + "            a.FECHA_ACT,to_char(a.HORA_ACT,'hh24:mi') hora,\n"
                + "            b.NOMBRE_DURACION,a.DURACION,a.num_actividad1 \n"
                + "FROM epqop.if_bq_reg_activida a,\n"
                + "      epqop.if_bq_activ_buque b \n"
                + "WHERE a.NUM_ACTIVIDAD1 = b.NUM_ACTIVIDAD1 \n"
                + "AND ano_arribo = '" + ano + "' \n"
                + "AND num_arribo = '" + arribo + "'";
//                
        try {
            conn = obtener.Conexion();
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {
                data = new VerActividadMd();

                data.setActividad(rs.getString(1));
                data.setFecha_act(rs.getString(2));
                data.setHora_act(rs.getString(3));
                data.setNom_duracion(rs.getString(4));
                data.setDuracion(rs.getString(5));
                data.setNumActiv(rs.getString(6));

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
