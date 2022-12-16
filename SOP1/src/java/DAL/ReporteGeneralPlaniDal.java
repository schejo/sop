package DAL;

import Conexion.Conexion;
import MD.ReporteGeneralPlaniMd;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.zk.ui.util.Clients;

/**
 *
 * @author INFORMATICA
 */
public class ReporteGeneralPlaniDal {

    public List<ReporteGeneralPlaniMd> GetByFecha(String fechaIn, String fechaFin) throws SQLException {
        List<ReporteGeneralPlaniMd> respuesta = new ArrayList<ReporteGeneralPlaniMd>();
        PreparedStatement smt = null;
        Connection conn = null;
        Conexion conex = new Conexion();
        ResultSet result = null;
        conn = conex.Conexion();
        try {

            String sql = "SELECT                      ANO_ARRIBO,    \n"
                    + "                               NUM_ARRIBO,\n"
                    + "                               NOM_BUQUE,\n"
                    + "                               PAIS_BANDERA,\n"
                    + "                               NOM_TIPO_BUQUE,    \n"
                    + "                               TIPO_TERMINAL,    \n"
                    + "                               BUQ_TRB,    \n"
                    + "                               TRN,    \n"
                    + "                               PESO_MUERTO,    \n"
                    + "                               BUQ_ESLORA,\n"
                    + "                               MANGA,    \n"
                    + "                               CALADO_MAXIMO,    \n"
                    + "                               NUMERO_VIAJE,    \n"
                    + "                               FECHA_ATRAQUES,    \n"
                    + "                               FECHA_ZARPE,    \n"
                    + "                               DATOS_IMPORTACION,    \n"
                    + "                               DATOS_EXPORTACION,    \n"
                    + "                               VIA_DIRECTA,    \n"
                    + "                               PUERTO_PROCEDENCIA,    \n"
                    + "                               PUERTO_DESTINO,    \n"
                    + "                               TIPO_OPERACION,    \n"
                    + "                               CLASE_ARRIBO,    \n"
                    + "                               OPERA_INICIO,    \n"
                    + "                               OPERA_FIN,    \n"
                    + "                               PK_INICIAL,    \n"
                    + "                               PK_FINAL,    \n"
                    + "                               REPRESENTA_NAVIERA,    \n"
                    + "                               NOMBRE_PARTICULAR,    \n"
                    + "                               FECHA_HORA_VISITA,    \n"
                    + "                               DESC_ESTATUS,    \n"
                    + "                               NOM_NAVIERA,    \n"
                    + "                               NOM_LINEA,    \n"
                    + "                               PUERTO,    \n"
                    + "                               SUM(PESO_IMP_GENERAL),    \n"
                    + "                               SUM(PESO_EXP_GENERAL),    \n"
                    + "                               SUM(TOTAL_CONTE_TONELAJE_IMP),    \n"
                    + "                               SUM(TOTAL_CONTE_CANTIDAD_IMP),    \n"
                    + "                               SUM(TOTAL_CONTE_TONELAJE_EXP),    \n"
                    + "                               SUM(TOTAL_CONTE_CANTIDAD_EXP)    \n"
                    + "                    \n"
                    + "                       FROM    \n"
                    + "                       (    \n"
                    + "                          SELECT    \n"
                    + "                               A.BUQUE,    \n"
                    + "                               A.NOM_BUQUE,    \n"
                    + "                               UPPER(J.NOMBRE_PAIS) PAIS_BANDERA,    \n"
                    + "                               A.BUQ_TRB,    \n"
                    + "                               A.TRN,    \n"
                    + "                               A.PESO_MUERTO,    \n"
                    + "                               A.BUQ_ESLORA,    \n"
                    + "                               A.MANGA,    \n"
                    + "                               A.CALADO_MAXIMO,    \n"
                    + "                               B.ANO_ARRIBO,    \n"
                    + "                               B.NUM_ARRIBO,    \n"
                    + "                               NVL(UPPER(C.NOM_TIPO_BUQUE),'N/A') NOM_TIPO_BUQUE,    \n"
                    + "                               UPPER(H.TIPO_TERMINAL) TIPO_TERMINAL,    \n"
                    + "                               B.NUMERO_VIAJE,    \n"
                    + "                               TO_CHAR(B.FECHA_ATRAQUE,'DD/MM/YYYY')|| ' ' ||TO_CHAR(B.HORA_ATRAQUE,'HH24:MI:SS')  FECHA_ATRAQUES,    \n"
                    + "                               TO_CHAR(B.FECHA_ZARPE,'DD/MM/YYYY')|| ' ' ||TO_CHAR(B.HORA_ZARPE,'HH24:MI:SS') FECHA_ZARPE,    \n"
                    + "                               B.DATOS_IMPORTACION,    \n"
                    + "                               B.DATOS_EXPORTACION,    \n"
                    + "                               B.VIA_DIRECTA,    \n"
                    + "                               B.PUERTO_PROCEDENCIA,    \n"
                    + "                               B.PUERTO_DESTINO,    \n"
                    + "                               DECODE(B.TIPO_OPERACION2,'E','EXPORTACION', 'I','IMPORTACION', 'A','AMBOS', 'N/A') TIPO_OPERACION,    \n"
                    + "                               DECODE(B.CLASE_ARRIBO,'C','CHARTER','L','LINEA','P','PASAJEROS','O','OTROS') CLASE_ARRIBO,    \n"
                    + "                               B.OPERA_INICIO,    \n"
                    + "                               B.OPERA_FIN,    \n"
                    + "                               B.PK_INICIAL,    \n"
                    + "                               B.PK_FINAL,    \n"
                    + "                               B.REPRESENTA_NAVIERA,    \n"
                    + "                               UPPER(K.NOMBRE_PARTICULAR) NOMBRE_PARTICULAR,    \n"
                    + "                               B.FECHA_HORA_VISITA,    \n"
                    + "                               DECODE(B.STATUS3,'A','ATRACADO','V','AVISADO','F','FONDEADO','Z','ZARPADO') DESC_ESTATUS,    \n"
                    + "                               UPPER(F.NOM_NAVIERA) NOM_NAVIERA,    \n"
                    + "                               UPPER(G.NOM_LINEA) NOM_LINEA,    \n"
                    + "                               M.PUERTO,    \n"
                    + "                               (    \n"
                    + "                                   SELECT  NVL(SUM(L.PESO_BRUTO_RECIBID),0)    \n"
                    + "                                   FROM    EPQOP.IF_CA_CARGA_IMPOR L    \n"
                    + "                                   WHERE   B.ANO_ARRIBO    =   L.ANO_ARRIBO    \n"
                    + "                                   AND     B.NUM_ARRIBO    =   L.NUM_ARRIBO    \n"
                    + "                                   AND     E.NAVIERA1      =   L.NAVIERA    \n"
                    + "                                   AND     E.LINEA1        =   L.LINEA    \n"
                    + "                               ) PESO_IMP_GENERAL,    \n"
                    + "                               (    \n"
                    + "                                   SELECT  NVL(SUM(L.PESO_BRUTO_RECIBID),0)    \n"
                    + "                                   FROM    EPQOP.IF_CA_CARGA_EXPOR L    \n"
                    + "                                   WHERE   B.ANO_ARRIBO    =   L.ANO_ARRIBO    \n"
                    + "                                   AND     B.NUM_ARRIBO    =   L.NUM_ARRIBO    \n"
                    + "                                   AND     E.NAVIERA1      =   L.CODIGO_NAVIERA    \n"
                    + "                                   AND     E.LINEA1        =   L.CODIGO_LINEA    \n"
                    + "                               ) PESO_EXP_GENERAL,    \n"
                    + "                               (    \n"
                    + "                                   SELECT  NVL(SUM(M.PESO_BRUTO_RECIBID),0)    \n"
                    + "                                   FROM    EPQOP.IF_CA_ACTIVI_CONTE    M    \n"
                    + "                                   WHERE   B.ANO_ARRIBO        = M.ANO_ARRIBO2    \n"
                    + "                                   AND     B.NUM_ARRIBO        = M.NUM_ARRIBO2    \n"
                    + "                                   AND     M.CODIGO_ACTIVIDAD  IN (1,13)    \n"
                    + "                               ) TOTAL_CONTE_TONELAJE_IMP,    \n"
                    + "                               (    \n"
                    + "                                   SELECT  NVL(COUNT(1),0)    \n"
                    + "                                   FROM    EPQOP.IF_CA_ACTIVI_CONTE    M    \n"
                    + "                                   WHERE   B.ANO_ARRIBO        = M.ANO_ARRIBO2    \n"
                    + "                                   AND     B.NUM_ARRIBO        = M.NUM_ARRIBO2    \n"
                    + "                                   AND     M.CODIGO_ACTIVIDAD  IN (1,13)    \n"
                    + "                               ) TOTAL_CONTE_CANTIDAD_IMP,\n"
                    + "                               (    \n"
                    + "                                   SELECT  NVL(SUM(M.PESO_BRUTO_RECIBID),0)    \n"
                    + "                                   FROM    EPQOP.IF_CA_ACTIVI_CONTE    M    \n"
                    + "                                   WHERE   B.ANO_ARRIBO        = M.ANO_ARRIBO2    \n"
                    + "                                   AND     B.NUM_ARRIBO        = M.NUM_ARRIBO2    \n"
                    + "                                   AND     M.CODIGO_ACTIVIDAD  IN (6,8)     \n"
                    + "                               ) TOTAL_CONTE_TONELAJE_EXP,    \n"
                    + "                               (    \n"
                    + "                                   SELECT  NVL(COUNT(1),0)    \n"
                    + "                                   FROM    EPQOP.IF_CA_ACTIVI_CONTE    M    \n"
                    + "                                   WHERE   B.ANO_ARRIBO        = M.ANO_ARRIBO2    \n"
                    + "                                   AND     B.NUM_ARRIBO        = M.NUM_ARRIBO2    \n"
                    + "                                   AND     M.CODIGO_ACTIVIDAD  IN (6,8)    \n"
                    + "                               ) TOTAL_CONTE_CANTIDAD_EXP    \n"
                    + "                       FROM    EPQOP.IF_BQ_ARRIBOS         B,    \n"
                    + "                               EPQOP.IF_BQ_BUQUES          A,    \n"
                    + "                               EPQOP.IF_BQ_TIPO_ARRIBO     C,    \n"
                    + "                               EPQOP.IF_BQ_LINEAS_ARRIB    E,    \n"
                    + "                               EPQOP.IF_BQ_NAVIERA         F,    \n"
                    + "                               EPQOP.IF_BQ_LINEAS          G,    \n"
                    + "                               EPQOP.IF_BQ_ATRACADEROS     H,    \n"
                    + "                               EPQOP.IF_BQ_PAISES          J,    \n"
                    + "                               EPQOP.PARTICULARES          K,    \n"
                    + "                               EPQOP.IF_MA_MANIFIESTOS     L,    \n"
                    + "                               EPQOP.IF_MA_ENCABEZA_BL     M    \n"
                    + "                       WHERE   B.BUQUE             =   A.BUQUE             (+)\n"
                    + "                       AND     B.TIPO_BUQUE        =   C.TIPO_BUQUE        (+)    \n"
                    + "                       AND     B.ANO_ARRIBO        =   E.ANO_ARRIBO        (+)    \n"
                    + "                       AND     B.NUM_ARRIBO        =   E.NUM_ARRIBO        (+)    \n"
                    + "                       AND     E.NAVIERA1          =   F.NAVIERA1          (+)    \n"
                    + "                       AND     E.LINEA1            =   G.LINEA1            (+)    \n"
                    + "                       AND     B.NUM_ATRACADERO1   =   H.NUM_ATRACADERO1   (+)    \n"
                    + "                       AND     B.ESTIBADORA        =   K.CODIGO_PARTICULAR (+)    \n"
                    + "                       AND     A.BANDERA           =   J.PAIS              (+)    \n"
                    + "                       AND     B.FECHA_ZARPE       IS NOT NULL    \n"
                    + "                       AND     B.ANO_ARRIBO        =   L.ANO_ARRIBO        (+)\n"
                    + "                       AND     B.NUM_ARRIBO        =   L.NUM_ARRIBO        (+)\n"
                    + "                       AND     L.ANO_MANIFIESTO    =   M.ANO_MANIFIESTO    (+)\n"
                    + "                       AND     L.MANIFIESTO        =   M.MANIFIESTO        (+)\n"
                    + "                       AND     B.FECHA_ATRAQUE     >=   TO_DATE('" + fechaIn + "','DD/MM/RRRR') "
                    + "                       AND     B.FECHA_ATRAQUE     <=   TO_DATE('" + fechaFin + "','DD/MM/RRRR') "
                    + "                       UNION ALL\n"
                    + "                         SELECT     \n"
                    + "                               A.BUQUE,\n"
                    + "                               A.NOM_BUQUE,\n"
                    + "                               UPPER(J.NOMBRE_PAIS) PAIS_BANDERA,    \n"
                    + "                               A.BUQ_TRB,    \n"
                    + "                               A.TRN,    \n"
                    + "                               A.PESO_MUERTO,    \n"
                    + "                               A.BUQ_ESLORA,    \n"
                    + "                               A.MANGA,    \n"
                    + "                               A.CALADO_MAXIMO,    \n"
                    + "                               B.ANO_ARRIBO,    \n"
                    + "                               B.NUM_ARRIBO,    \n"
                    + "                               NVL(UPPER(C.NOM_TIPO_BUQUE),'N/A') NOM_TIPO_BUQUE,    \n"
                    + "                               UPPER(H.TIPO_TERMINAL) TIPO_TERMINAL,    \n"
                    + "                               B.NUMERO_VIAJE,    \n"
                    + "                               TO_CHAR(B.FECHA_ATRAQUE,'DD/MM/YYYY')|| ' ' ||TO_CHAR(B.HORA_ATRAQUE,'HH24:MI:SS')  FECHA_ATRAQUES,    \n"
                    + "                               TO_CHAR(B.FECHA_ZARPE,'DD/MM/YYYY')|| ' ' ||TO_CHAR(B.HORA_ZARPE,'HH24:MI:SS') FECHA_ZARPE,    \n"
                    + "                               B.DATOS_IMPORTACION,    \n"
                    + "                               B.DATOS_EXPORTACION,    \n"
                    + "                               B.VIA_DIRECTA,    \n"
                    + "                               B.PUERTO_PROCEDENCIA,    \n"
                    + "                               B.PUERTO_DESTINO,    \n"
                    + "                               DECODE(B.TIPO_OPERACION2,'E','EXPORTACION', 'I','IMPORTACION', 'A','AMBOS', 'N/A') TIPO_OPERACION,    \n"
                    + "                               DECODE(B.CLASE_ARRIBO,'C','CHARTER','L','LINEA','P','PASAJEROS','O','OTROS') CLASE_ARRIBO,    \n"
                    + "                               B.OPERA_INICIO,    \n"
                    + "                               B.OPERA_FIN,    \n"
                    + "                               B.PK_INICIAL,    \n"
                    + "                               B.PK_FINAL,    \n"
                    + "                               B.REPRESENTA_NAVIERA,    \n"
                    + "                               UPPER(K.NOMBRE_PARTICULAR) NOMBRE_PARTICULAR,    \n"
                    + "                               B.FECHA_HORA_VISITA,    \n"
                    + "                               DECODE(B.STATUS3,'A','ATRACADO','V','AVISADO','F','FONDEADO','Z','ZARPADO') DESC_ESTATUS,    \n"
                    + "                               UPPER(F.NOM_NAVIERA) NOM_NAVIERA,    \n"
                    + "                               UPPER(G.NOM_LINEA) NOM_LINEA,    \n"
                    + "                               M.PUERTO,    \n"
                    + "                               (    \n"
                    + "                                   SELECT  NVL(SUM(L.PESO_BRUTO_RECIBID),0)    \n"
                    + "                                   FROM    EPQOP.IH_CA_CARGA_IMPOR L    \n"
                    + "                                   WHERE   B.ANO_ARRIBO    =   L.ANO_ARRIBO    \n"
                    + "                                   AND     B.NUM_ARRIBO    =   L.NUM_ARRIBO    \n"
                    + "                                   AND     E.NAVIERA1      =   L.NAVIERA    \n"
                    + "                                   AND     E.LINEA1        =   L.LINEA    \n"
                    + "                               ) PESO_IMP_GENERAL,    \n"
                    + "                               (    \n"
                    + "                                   SELECT  NVL(SUM(L.PESO_BRUTO_RECIBID),0)    \n"
                    + "                                   FROM    EPQOP.IH_CA_CARGA_EXPOR L    \n"
                    + "                                   WHERE   B.ANO_ARRIBO    =   L.ANO_ARRIBO    \n"
                    + "                                   AND     B.NUM_ARRIBO    =   L.NUM_ARRIBO    \n"
                    + "                                   AND     E.NAVIERA1      =   L.CODIGO_NAVIERA    \n"
                    + "                                   AND     E.LINEA1        =   L.CODIGO_LINEA    \n"
                    + "                               ) PESO_EXP_GENERAL,     \n"
                    + "                               (    \n"
                    + "                                   SELECT  NVL(SUM(M.PESO_BRUTO_RECIBID),0)    \n"
                    + "                                   FROM    EPQOP.IH_CA_ACTIVI_CONTE    M    \n"
                    + "                                   WHERE   B.ANO_ARRIBO        = M.ANO_ARRIBO2    \n"
                    + "                                   AND     B.NUM_ARRIBO        = M.NUM_ARRIBO2    \n"
                    + "                                   AND     M.CODIGO_ACTIVIDAD  IN (1,13)    \n"
                    + "                               ) TOTAL_CONTE_TONELAJE_IMP,    \n"
                    + "                               (    \n"
                    + "                                   SELECT  NVL(COUNT(1),0)    \n"
                    + "                                   FROM    EPQOP.IH_CA_ACTIVI_CONTE    M    \n"
                    + "                                   WHERE   B.ANO_ARRIBO        = M.ANO_ARRIBO2    \n"
                    + "                                   AND     B.NUM_ARRIBO        = M.NUM_ARRIBO2    \n"
                    + "                                   AND     M.CODIGO_ACTIVIDAD  IN (1,13)    \n"
                    + "                               ) TOTAL_CONTE_CANTIDAD_IMP,    \n"
                    + "                               (    \n"
                    + "                                   SELECT  NVL(SUM(M.PESO_BRUTO_RECIBID),0)    \n"
                    + "                                   FROM    EPQOP.IH_CA_ACTIVI_CONTE    M    \n"
                    + "                                   WHERE   B.ANO_ARRIBO        = M.ANO_ARRIBO2    \n"
                    + "                                   AND     B.NUM_ARRIBO        = M.NUM_ARRIBO2    \n"
                    + "                                   AND     M.CODIGO_ACTIVIDAD  IN (6,8)    \n"
                    + "                               ) TOTAL_CONTE_TONELAJE_EXP,    \n"
                    + "                               (    \n"
                    + "                                   SELECT  NVL(COUNT(1),0)    \n"
                    + "                                   FROM    EPQOP.IH_CA_ACTIVI_CONTE    M    \n"
                    + "                                   WHERE   B.ANO_ARRIBO        = M.ANO_ARRIBO2    \n"
                    + "                                   AND     B.NUM_ARRIBO        = M.NUM_ARRIBO2    \n"
                    + "                                   AND     M.CODIGO_ACTIVIDAD  IN (6,8)    \n"
                    + "                               ) TOTAL_CONTE_CANTIDAD_EXP    \n"
                    + "                       FROM    EPQOP.IF_BQ_ARRIBOS         B,    \n"
                    + "                               EPQOP.IF_BQ_BUQUES          A,    \n"
                    + "                               EPQOP.IF_BQ_TIPO_ARRIBO     C,    \n"
                    + "                               EPQOP.IF_BQ_LINEAS_ARRIB    E,    \n"
                    + "                               EPQOP.IF_BQ_NAVIERA         F,    \n"
                    + "                               EPQOP.IF_BQ_LINEAS          G,    \n"
                    + "                               EPQOP.IF_BQ_ATRACADEROS     H,    \n"
                    + "                               EPQOP.IF_BQ_PAISES          J,    \n"
                    + "                               EPQOP.PARTICULARES          K,    \n"
                    + "                               EPQOP.IF_MA_MANIFIESTOS          L,    \n"
                    + "                               EPQOP.IF_MA_ENCABEZA_BL          M    \n"
                    + "                       WHERE   B.BUQUE             =   A.BUQUE             (+)\n"
                    + "                       AND     B.TIPO_BUQUE        =   C.TIPO_BUQUE        (+)    \n"
                    + "                       AND     B.ANO_ARRIBO        =   E.ANO_ARRIBO        (+)    \n"
                    + "                       AND     B.NUM_ARRIBO        =   E.NUM_ARRIBO        (+)    \n"
                    + "                       AND     E.NAVIERA1          =   F.NAVIERA1          (+)    \n"
                    + "                       AND     E.LINEA1            =   G.LINEA1            (+)    \n"
                    + "                       AND     B.NUM_ATRACADERO1   =   H.NUM_ATRACADERO1   (+)    \n"
                    + "                       AND     B.ESTIBADORA        =   K.CODIGO_PARTICULAR (+)    \n"
                    + "                       AND     A.BANDERA           =   J.PAIS              (+)    \n"
                    + "                       AND     B.FECHA_ZARPE       IS NOT NULL    \n"
                    + "                       AND     B.ANO_ARRIBO        =   L.ANO_ARRIBO        (+)\n"
                    + "                       AND     B.NUM_ARRIBO        =   L.NUM_ARRIBO        (+)\n"
                    + "                       AND     L.ANO_MANIFIESTO    =   M.ANO_MANIFIESTO    (+)\n"
                    + "                       AND     L.MANIFIESTO        =   M.MANIFIESTO        (+)\n"
                    + "                       AND     B.FECHA_ATRAQUE     >=   TO_DATE('" + fechaIn + "','DD/MM/RRRR') "
                    + "                       AND     B.FECHA_ATRAQUE     <=   TO_DATE('" + fechaFin + "','DD/MM/RRRR') "
                    + "                       )    \n"
                    + "                       GROUP   BY \n"
                    + "                               ANO_ARRIBO,    \n"
                    + "                               NUM_ARRIBO,   \n"
                    + "                               NOM_BUQUE,    \n"
                    + "                               PAIS_BANDERA,\n"
                    + "                               NOM_TIPO_BUQUE,    \n"
                    + "                               TIPO_TERMINAL,\n"
                    + "                               BUQ_TRB,    \n"
                    + "                               TRN,    \n"
                    + "                               PESO_MUERTO,    \n"
                    + "                               BUQ_ESLORA,    \n"
                    + "                               MANGA,    \n"
                    + "                               CALADO_MAXIMO,    \n"
                    + "                               NUMERO_VIAJE,    \n"
                    + "                               FECHA_ATRAQUES,    \n"
                    + "                               FECHA_ZARPE,    \n"
                    + "                               DATOS_IMPORTACION,    \n"
                    + "                               DATOS_EXPORTACION,    \n"
                    + "                               VIA_DIRECTA,    \n"
                    + "                               PUERTO_PROCEDENCIA,    \n"
                    + "                               PUERTO_DESTINO,    \n"
                    + "                               TIPO_OPERACION,    \n"
                    + "                               CLASE_ARRIBO,    \n"
                    + "                               OPERA_INICIO,    \n"
                    + "                               OPERA_FIN,    \n"
                    + "                               PK_INICIAL,    \n"
                    + "                               PK_FINAL,    \n"
                    + "                               REPRESENTA_NAVIERA,    \n"
                    + "                               NOMBRE_PARTICULAR,    \n"
                    + "                               FECHA_HORA_VISITA,    \n"
                    + "                               DESC_ESTATUS,    \n"
                    + "                               NOM_NAVIERA,    \n"
                    + "                               NOM_LINEA,    \n"
                    + "                               PUERTO    \n"
                    + "                               ORDER   BY 11, 12 ";

            smt = conn.prepareStatement(sql);
            result = smt.executeQuery();
            while (result.next()) {
                ReporteGeneralPlaniMd temporal = new ReporteGeneralPlaniMd();
                temporal.setAnoArribo(result.getInt(1));
                temporal.setNumArribo(result.getString(2));
                temporal.setNomBuque(result.getString(3));
                temporal.setPaisBandera(result.getString(4));
                temporal.setNomTipoBuque(result.getString(5));
                temporal.setTipoTerminal(result.getString(6));
                temporal.setBuqTrb(result.getInt(7));
                temporal.setTrn(result.getInt(8));
                temporal.setPesoMuerto(result.getFloat(9));
                temporal.setBuqueEslora(result.getFloat(10));
                temporal.setManga(result.getFloat(11));
                temporal.setCaladoMaximo(result.getFloat(12));
                temporal.setNumViaje(result.getString(13));
                temporal.setFechaAtraque(result.getString(14));
                temporal.setFechaZarpe(result.getString(15));
                temporal.setDatoImportacion(result.getString(16));
                temporal.setDatosExportacion(result.getString(17));
                temporal.setViaDirecta(result.getString(18));
                temporal.setPuertoProcedencia(result.getString(19));
                temporal.setPuertoDestino(result.getString(20));
                temporal.setNomTipoOperacion(result.getString(21));
                temporal.setClaseArribo(result.getString(22));
                temporal.setFechInicioOperacion(result.getString(23));
                temporal.setFechFinOperacion(result.getString(24));
                temporal.setPkInicial(result.getInt(25));
                temporal.setPkFinal(result.getInt(26));
                temporal.setRepresentaNaviera(result.getString(27));
                temporal.setNomParticular(result.getString(28));
                temporal.setFechaVisita(result.getString(29));
                temporal.setDescStatus(result.getString(30));
                temporal.setNomNaviera(result.getString(31));
                temporal.setNomLinea(result.getString(32));
                temporal.setPuerto(result.getString(33));
                temporal.setSumPesoImpGeneral(result.getFloat(34));
                temporal.setSumPesoExpGeneral(result.getFloat(35));
                temporal.setSumTotalConTonelajeImp(result.getFloat(36));
                temporal.setSumTotalConteCantidadImp(result.getInt(37));
                temporal.setSumTotalConteTonelajeExp(result.getInt(38));
                temporal.setSumCantContetTotalExp(result.getInt(39));

                respuesta.add(temporal);

            }
        } catch (Exception e) {
            Clients.showNotification(e.getMessage());
        } finally {
            if (smt != null) {
                smt.close();
                smt = null;
            }
            if (result != null) {
                result.close();
                result = null;
            }
            if (conn != null) {
                conn = conex.desconectar();
            }
        }

        return respuesta;
    }

}
