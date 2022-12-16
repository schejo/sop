package ctrl;

import DAL.ReporteGeneralPlaniDal;
import MD.ReporteGeneralPlaniMd;
import Util.EstilosReporte;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.zkoss.util.media.AMedia;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Filedownload;

public class ReporteGeneralPlaniCtrl extends GenericForwardComposer {

    private Datebox fecha;
    private Datebox fechaFin;

    public void doAfterComposer(Component comp) throws Exception {
        super.doAfterCompose(comp);

    }

    public void onClick$btnDescargar(Event evn) throws SQLException, IOException {
        if (!fecha.getText().isEmpty()) {
            ReporteExcel(fecha.getText(), fechaFin.getText());
        } else {
            Clients.showNotification("Ingrese una fecha por favor");
        }
    }

    public void ReporteExcel(String fecha, String fechaFin) throws SQLException, IOException {
        ReporteGeneralPlaniDal dataBase = new ReporteGeneralPlaniDal();
        List<ReporteGeneralPlaniMd> lista = dataBase.GetByFecha(fecha, fechaFin);
        Workbook workbook = new HSSFWorkbook();

        //estilo de celdas
        EstilosReporte estilo = new EstilosReporte();
        CellStyle style = estilo.Estilo1(workbook);
        CellStyle style2 = estilo.Estilo2(workbook);
        CellStyle styleEntero = estilo.EstiloEnteros2(workbook);
        //hoja
        String dirImagen = desktop.getWebApp().getRealPath("bootstrap") + "/epq.png";
        Sheet listSheet = workbook.createSheet("Reporte");
        estilo.insertImage(workbook, listSheet, dirImagen);
        //filas
        Row titulo1 = listSheet.createRow(0);
        Row titulo2 = listSheet.createRow(1);
        Row tutulo3 = listSheet.createRow(2);
        Row encabezado = listSheet.createRow(5);
        int index = 6;

        Cell c1 = titulo1.createCell(1);
        c1.setCellValue("EMPRESA PORTUARIA QUETZAL");
        c1.setCellStyle(style);

        Cell c2 = titulo2.createCell(1);
        c2.setCellValue("REPORTE DE BUQUE Y SUS CARGAS");
        c2.setCellStyle(style);

        Cell c3 = tutulo3.createCell(1);
        c3.setCellValue("Del.: " + fecha + " Al.: " + fechaFin);
        c3.setCellStyle(style);

        Cell cE1 = encabezado.createCell(0);
        cE1.setCellValue("AÑO ARRIBO");
        cE1.setCellStyle(style2);

        Cell cE2 = encabezado.createCell(1);
        cE2.setCellValue("NUMERO ARRIBO");
        cE2.setCellStyle(style2);

        Cell cE3 = encabezado.createCell(2);
        cE3.setCellValue("NOMBRE BUQUE");
        cE3.setCellStyle(style2);

        Cell cE4 = encabezado.createCell(3);
        cE4.setCellValue("BANDERA");
        cE4.setCellStyle(style2);

        Cell cE5 = encabezado.createCell(4);
        cE5.setCellValue("TIPO BUQUE");
        cE5.setCellStyle(style2);

        Cell cE6 = encabezado.createCell(5);
        cE6.setCellValue("TERMINAL");
        cE6.setCellStyle(style2);

        Cell cE7 = encabezado.createCell(6);
        cE7.setCellValue("TRB");
        cE7.setCellStyle(style2);

        Cell cE8 = encabezado.createCell(7);
        cE8.setCellValue("TRN");
        cE8.setCellStyle(style2);

        Cell cE9 = encabezado.createCell(8);
        cE9.setCellValue("PESO MUERTO");
        cE9.setCellStyle(style2);

        Cell cE10 = encabezado.createCell(9);
        cE10.setCellValue("ESLORA");
        cE10.setCellStyle(style2);

        Cell cE11 = encabezado.createCell(10);
        cE11.setCellValue("MANGA");
        cE11.setCellStyle(style2);

        Cell cE12 = encabezado.createCell(11);
        cE12.setCellValue("CALADO");
        cE12.setCellStyle(style2);

        Cell cE13 = encabezado.createCell(12);
        cE13.setCellValue("VIAJE");
        cE13.setCellStyle(style2);

        Cell cE14 = encabezado.createCell(13);
        cE14.setCellValue("FECHA ATRAQUE");
        cE14.setCellStyle(style2);

        Cell cE15 = encabezado.createCell(14);
        cE15.setCellValue("FECHA ZARPE");
        cE15.setCellStyle(style2);

        Cell cE16 = encabezado.createCell(15);
        cE16.setCellValue("DATOS IMPORTACION");
        cE16.setCellStyle(style2);

        Cell cE17 = encabezado.createCell(16);
        cE17.setCellValue("DATOS EXPORTACION");
        cE17.setCellStyle(style2);

        Cell cE18 = encabezado.createCell(17);
        cE18.setCellValue("VIA");
        cE18.setCellStyle(style2);

        Cell cE19 = encabezado.createCell(18);
        cE19.setCellValue("PUERTO PROCEDENCIA");
        cE19.setCellStyle(style2);

        Cell cE20 = encabezado.createCell(19);
        cE20.setCellValue("PUERTO DESTINO");
        cE20.setCellStyle(style2);

        /* 
        REQUERIMIENTO 18/03/2022
         */
        Cell cE21 = encabezado.createCell(20);
        cE21.setCellValue("TIPO OPERACION");
        cE21.setCellStyle(style2);

        /*
        FIN
         */
        Cell cE22 = encabezado.createCell(21);
        cE22.setCellValue("CLASE ARRIBO");
        cE22.setCellStyle(style2);

        Cell cE23 = encabezado.createCell(22);
        cE23.setCellValue("FECHA INICIO");
        cE23.setCellStyle(style2);

        Cell cE24 = encabezado.createCell(23);
        cE24.setCellValue("FECHA FIN");
        cE24.setCellStyle(style2);

        Cell cE25 = encabezado.createCell(24);
        cE25.setCellValue("PK INICIO");
        cE25.setCellStyle(style2);

        Cell cE26 = encabezado.createCell(25);
        cE26.setCellValue("PK FINAL");
        cE26.setCellStyle(style2);

        Cell cE27 = encabezado.createCell(26);
        cE27.setCellValue("REPRESENTA NAVIERA");
        cE27.setCellStyle(style2);

        Cell cE28 = encabezado.createCell(27);
        cE28.setCellValue("ESTIBADORA");
        cE28.setCellStyle(style2);

        Cell cE29 = encabezado.createCell(28);
        cE29.setCellValue("FECHA VISITA");
        cE29.setCellStyle(style2);

        Cell cE30 = encabezado.createCell(29);
        cE30.setCellValue("ESTATUS");
        cE30.setCellStyle(style2);

        Cell cE31 = encabezado.createCell(30);
        cE31.setCellValue("NAVIERA");
        cE31.setCellStyle(style2);

        Cell cE32 = encabezado.createCell(31);
        cE32.setCellValue("LINEA");
        cE32.setCellStyle(style2);

        Cell cE33 = encabezado.createCell(32);
        cE33.setCellValue("PUERTO");
        cE33.setCellStyle(style2);

        Cell cE34 = encabezado.createCell(33);
        cE34.setCellValue("SUMA PESO IMP. GENERAL");
        cE34.setCellStyle(style2);

        Cell cE35 = encabezado.createCell(34);
        cE35.setCellValue("SUMA PESO EXP. GENERAL");
        cE35.setCellStyle(style2);

        Cell cE36 = encabezado.createCell(35);
        cE36.setCellValue("SUMA TOTAL CON. TONELAJE IMPORTACION");
        cE36.setCellStyle(style2);

        Cell cE37 = encabezado.createCell(36);
        cE37.setCellValue("SUMA TOTAL CON. CANTIDAD IMPORTACION");
        cE37.setCellStyle(style2);

        Cell cE38 = encabezado.createCell(37);
        cE38.setCellValue("SUMA TOTAL CONTE. TONELAJE EXPORTACION");
        cE38.setCellStyle(style2);

        Cell cE39 = encabezado.createCell(38);
        cE39.setCellValue("SUMA TOTAL CONT. CANTIDAD EXPORTACION");
        cE39.setCellStyle(style2);

        for (ReporteGeneralPlaniMd item : lista) {
            Row contenido = listSheet.createRow(index++);
            
            Cell cC1 = contenido.createCell(0);
            cC1.setCellValue(item.getAnoArribo());
            cC1.setCellStyle(styleEntero);

            Cell cC2 = contenido.createCell(1);
            cC2.setCellValue(item.getNumArribo());
            cC2.setCellStyle(style2);

            Cell cC3 = contenido.createCell(2);
            cC3.setCellValue(item.getNomBuque());
            cC3.setCellStyle(style2);

            Cell cC4 = contenido.createCell(3);
            cC4.setCellValue(item.getPaisBandera());
            cC4.setCellStyle(style2);

            Cell cC5 = contenido.createCell(4);
            cC5.setCellValue(item.getNomTipoBuque());
            cC5.setCellStyle(styleEntero);

            Cell cC6 = contenido.createCell(5);
            cC6.setCellValue(item.getTipoTerminal());
            cC6.setCellStyle(styleEntero);

            Cell cC7 = contenido.createCell(6);
            cC7.setCellValue(item.getBuqTrb());
            cC7.setCellStyle(style2);

            Cell cC8 = contenido.createCell(7);
            cC8.setCellValue(item.getTrn());
            cC8.setCellStyle(style2);

            Cell cC9 = contenido.createCell(8);
            cC9.setCellValue(item.getPesoMuerto());
            cC9.setCellStyle(style2);

            Cell cC10 = contenido.createCell(9);
            cC10.setCellValue(item.getBuqueEslora());
            cC10.setCellStyle(style2);

            Cell cC11 = contenido.createCell(10);
            cC11.setCellValue(item.getManga());
            cC11.setCellStyle(styleEntero);

            Cell cC12 = contenido.createCell(11);
            cC12.setCellValue(item.getCaladoMaximo());
            cC12.setCellStyle(styleEntero);

            Cell cC13 = contenido.createCell(12);
            cC13.setCellValue(item.getNumViaje());
            cC13.setCellStyle(styleEntero);

            Cell cC14 = contenido.createCell(13);
            cC14.setCellValue(item.getFechaAtraque());
            cC14.setCellStyle(style2);

            Cell cC15 = contenido.createCell(14);
            cC15.setCellValue(item.getFechaZarpe());
            cC15.setCellStyle(styleEntero);

            Cell cC16 = contenido.createCell(15);
            cC16.setCellValue(item.getDatoImportacion());
            cC16.setCellStyle(style2);

            Cell cC17 = contenido.createCell(16);
            cC17.setCellValue(item.getDatosExportacion());
            cC17.setCellStyle(style2);

            Cell cC18 = contenido.createCell(17);
            cC18.setCellValue(item.getViaDirecta());
            cC18.setCellStyle(style2);

            Cell cC19 = contenido.createCell(18);
            cC19.setCellValue(item.getPuertoProcedencia());
            cC19.setCellStyle(style2);

            Cell cC20 = contenido.createCell(19);
            cC20.setCellValue(item.getPuertoDestino());
            cC20.setCellStyle(style2);

            /*
            REQUERIMIENTO 18/03/2022
             */
            Cell cC21 = contenido.createCell(20);
            cC21.setCellValue(item.getNomTipoOperacion());
            cC21.setCellStyle(style2);
            /*
            FIN 
             */

            Cell cC22 = contenido.createCell(21);
            cC22.setCellValue(item.getClaseArribo());
            cC22.setCellStyle(style2);

            Cell cC23 = contenido.createCell(22);
            cC23.setCellValue(item.getFechInicioOperacion());
            cC23.setCellStyle(style2);

            Cell cC24 = contenido.createCell(23);
            cC24.setCellValue(item.getFechFinOperacion());
            cC24.setCellStyle(style2);

            Cell cC25 = contenido.createCell(24);
            cC25.setCellValue(item.getPkInicial());
            cC25.setCellStyle(style2);

            Cell cC26 = contenido.createCell(25);
            cC26.setCellValue(item.getPkFinal());
            cC26.setCellStyle(style2);

            Cell cC27 = contenido.createCell(26);
            cC27.setCellValue(item.getRepresentaNaviera());
            cC27.setCellStyle(style2);

            Cell cC28 = contenido.createCell(27);
            cC28.setCellValue(item.getNomParticular());
            cC28.setCellStyle(style2);

            Cell cC29 = contenido.createCell(28);
            cC29.setCellValue(item.getFechaVisita());
            cC29.setCellStyle(style2);

            Cell cC30 = contenido.createCell(29);
            cC30.setCellValue(item.getDescStatus());
            cC30.setCellStyle(style2);

            Cell cC31 = contenido.createCell(30);
            cC31.setCellValue(item.getNomNaviera());
            cC31.setCellStyle(styleEntero);

            Cell cC32 = contenido.createCell(31);
            cC32.setCellValue(item.getNomLinea());
            cC32.setCellStyle(styleEntero);

            Cell cC33 = contenido.createCell(32);
            cC33.setCellValue(item.getPuerto());
            cC33.setCellStyle(style2);

            Cell cC34 = contenido.createCell(33);
            cC34.setCellValue(item.getSumPesoImpGeneral());
            cC34.setCellStyle(style2);

            Cell cC35 = contenido.createCell(34);
            cC35.setCellValue(item.getSumPesoExpGeneral());
            cC35.setCellStyle(style2);

            Cell cC36 = contenido.createCell(35);
            cC36.setCellValue(item.getSumTotalConTonelajeImp());
            cC36.setCellStyle(style2);

            Cell cC37 = contenido.createCell(36);
            cC37.setCellValue(item.getSumTotalConteCantidadImp());
            cC37.setCellStyle(styleEntero);

            Cell cC38 = contenido.createCell(37);
            cC38.setCellValue(item.getSumTotalConteTonelajeExp());
            cC38.setCellStyle(style2);

            Cell cC39 = contenido.createCell(38);
            cC39.setCellValue(item.getSumCantContetTotalExp());
            cC39.setCellStyle(styleEntero);

        }

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            estilo.autoSizeColumns(workbook, 7);
            workbook.write(baos);
            AMedia amedia = new AMedia("ReporteDeCargaBuque.xls", "xls", "application/file", baos.toByteArray());
            Filedownload.save(amedia);
            baos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
