package ctrl;

import DAL.ReporteServiciosParticulares2Dal;
import MD.ReporteServiciosParticulares2Md;
import Util.EstilosReporte;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.util.media.AMedia;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Textbox;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.text.DecimalFormat;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.zkoss.zul.Datebox;

public class ReporteServiciosParticulares2Ctrl extends GenericForwardComposer {

    private Textbox NumArribo;
    private Textbox anioArribo;
    private Datebox fechaInicio;
    private Datebox fechaFin;

    public void doAfterComposer(Component comp) throws Exception {
        super.doAfterCompose(comp);

    }

//    //BOTON PARA DESCARGAR EL REPORTE EN EXCEL
    public void onClick$btnDescargar(Event e) throws SQLException, ClassNotFoundException, IOException {

        ReporteExcel(anioArribo.getText(), NumArribo.getText());
    }

    public void ReporteExcel(String anio, String numero) throws SQLException, IOException, ClassNotFoundException {

        List<ReporteServiciosParticulares2Md> allReporteCli = new ArrayList<ReporteServiciosParticulares2Md>();
        // List<ReporteViajesMd> lista = dataBase.GetByFecha(cambio_fecha(fechaInicial), cambio_fecha(fechaFinal));
        ReporteServiciosParticulares2Dal rd = new ReporteServiciosParticulares2Dal();
        allReporteCli = rd.REGselect(anio, numero);
        Workbook workbook = new HSSFWorkbook();

        EstilosReporte estilo = new EstilosReporte();
        CellStyle style = estilo.Estilo1(workbook);
        CellStyle style2 = estilo.Estilo2(workbook);
        CellStyle styleEntero = estilo.EstiloEnteros2(workbook);

        // String dirImagen ="http://"+InetAddress.getLocalHost().getHostName().toString()+":8080/bootstrap/epq.png";
        Sheet listSheet = workbook.createSheet("Reporte Contenedores APM");
        // estilo.insertImage(workbook, listSheet, dirImagen);

        Row titulo1 = listSheet.createRow(0);
        Row titulo2 = listSheet.createRow(1);
        Row titulo3 = listSheet.createRow(2);
        Row titulo4 = listSheet.createRow(3);
        Row encabezado = listSheet.createRow(5);
        int index = 6;

        Cell c1 = titulo1.createCell(1);
        c1.setCellValue("   EMPRESA PORTUARIA QUETZAL");
        c1.setCellStyle(style);

        Cell c2 = titulo2.createCell(1);
        c2.setCellValue("   REPORTE DE CONTENEDORES DE APM");
        c2.setCellStyle(style);

        Cell c3 = titulo2.createCell(1);
        c3.setCellValue("   GERENCIA DE OPERACIONES PORTUARIAS");
        c3.setCellStyle(style);

        Cell c4 = titulo3.createCell(1);
        c4.setCellValue("   AÑO ARRIBO.: " + anio + "       NUMERO ARRIBO.:" + numero);
        c4.setCellStyle(style);

        Cell c5 = titulo4.createCell(1);
        c5.setCellValue("NOMBRE BUQUE.:" + allReporteCli.get(0).getNom_buque());
        c5.setCellStyle(style);

        Cell cE2 = encabezado.createCell(1);
        cE2.setCellValue("CONTENEDOR");
        cE2.setCellStyle(style2);

        Cell cE3 = encabezado.createCell(2);
        cE3.setCellValue("NAVIERA");
        cE3.setCellStyle(style2);

        Cell cE4 = encabezado.createCell(3);
        cE4.setCellValue("LINEA");
        cE4.setCellStyle(style2);

        Cell cE5 = encabezado.createCell(4);
        cE5.setCellValue("OPERACION");
        cE5.setCellStyle(style2);

        Cell cE6 = encabezado.createCell(5);
        cE6.setCellValue("FECHA INGRESO");
        cE6.setCellStyle(style2);

        Cell cE7 = encabezado.createCell(6);
        cE7.setCellValue("TIPO CONTENEDOR");
        cE7.setCellStyle(style2);

        Cell cE8 = encabezado.createCell(7);
        cE8.setCellValue("TM");
        cE8.setCellStyle(style2);

        Cell cE9 = encabezado.createCell(8);
        cE9.setCellValue("ESTADO");
        cE9.setCellStyle(style2);

        Cell cE10 = encabezado.createCell(9);
        cE10.setCellValue("TEUS");
        cE10.setCellStyle(style2);

        Cell cE11 = encabezado.createCell(10);
        cE11.setCellValue("TRANSITO");
        cE11.setCellStyle(style2);

        for (ReporteServiciosParticulares2Md item : allReporteCli) {
            Row contenido = listSheet.createRow(index++);

            Cell cC2 = contenido.createCell(1);
            cC2.setCellValue(item.getContenedor());
            cC2.setCellStyle(styleEntero);

            Cell cC3 = contenido.createCell(2);
            cC3.setCellValue(item.getNaviera());
            cC3.setCellStyle(style2);

            Cell cC4 = contenido.createCell(3);
            cC4.setCellValue(item.getLinea());
            cC4.setCellStyle(style2);

            Cell cC5 = contenido.createCell(4);
            cC5.setCellValue(item.getOperacion());
            cC5.setCellStyle(style2);

            Cell cC6 = contenido.createCell(5);
            cC6.setCellValue(item.getFecha_ingreso1());
            cC6.setCellStyle(style2);

            Cell cC7 = contenido.createCell(6);
            cC7.setCellValue(item.getTipo_contenedor());
            cC7.setCellStyle(style2);

            Cell cC8 = contenido.createCell(7);
            cC8.setCellValue(item.getTm_total());
            cC8.setCellStyle(style2);

            Cell cC9 = contenido.createCell(8);
            cC9.setCellValue(item.getEstado());
            cC9.setCellStyle(style2);

            Cell cC10 = contenido.createCell(9);
            cC10.setCellValue(item.getCant_teu());
            cC10.setCellStyle(style2);

            Cell cC11 = contenido.createCell(10);
            cC11.setCellValue(item.getTransito());
            cC11.setCellStyle(style2);

        }

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            estilo.autoSizeColumns(workbook, 5);
            workbook.write(baos);
            AMedia amedia = new AMedia("REPORTE DE CONTENEDORES DE APM.xls", "xls", "application/file", baos.toByteArray());
            Filedownload.save(amedia);
            baos.close();
        } catch (Exception e) {
            e.printStackTrace();

            System.out.println("EXCEPTION..: " + e.getMessage());
        }

    }

    //BOTON PARA DESCARGAR EL FORMATO EN PDF
    public void onClick$btnDescargar1(Event e) throws SQLException, ClassNotFoundException, IOException {
        GeneraPDF(anioArribo.getText(), NumArribo.getText());
    }

    public void GeneraPDF(String finicio, String codPar) throws SQLException, ClassNotFoundException {

        Document document;
        Paragraph ParrafoHoja = new Paragraph();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        List<ReporteServiciosParticulares2Md> allReporteCli = new ArrayList<ReporteServiciosParticulares2Md>();
        ReporteServiciosParticulares2Dal rd = new ReporteServiciosParticulares2Dal();
        allReporteCli = rd.REGselect(finicio, codPar);

        if (allReporteCli.isEmpty()) {
            Clients.showNotification("NO TIENE DATOS..!");
//            Clients.showNotification("NO TIENE DATOS..!<br/>",
//                    "warning", null, "middle_center", 5000);
        }
        try {
            document = new Document();
            document.setPageSize(PageSize.LETTER);
            PdfWriter.getInstance(document, baos);
//            String dirImagen = desktop.getWebApp().getRealPath("bootstrap") + "/epq.png";
//            Image im = Image.getInstance(dirImagen);
//            im.setAlignment(Image.ALIGN_RIGHT | Image.TEXTWRAP);
//            im.setAbsolutePosition(10, 680);
//            im.scalePercent(10);
//            ParrafoHoja.add(im);
            ParrafoHoja.add(new Paragraph("              EMPRESA PORTUARIA QUETZAL"));
            ParrafoHoja.add(new Paragraph("              GERENCIA DE OPERACIONES PORTUARIAS"));
            ParrafoHoja.add(new Paragraph("              REPORTE DE CONTENEDORES POR ARRIBO DE APM"));
            ParrafoHoja.add(new Paragraph("              AÑO DE ARRIBO: " + allReporteCli.get(0).getAnio() + "    " + "NUMERO DE ARRIBO:" + allReporteCli.get(0).getNumero() + "   " + "NOMBRE BUQUE:" + allReporteCli.get(0).getNom_buque()));         // ParrafoHoja.add(new Paragraph("              PARTICULAR.:" + allReporteCli.get(0).getParticular()));

            float anchosFilas[] = {0.2f, 0.35f, 0.25f, 0.3f, 0.25f, 0.3f, 0.3f, 0.3f, 0.3f, 0.2f, 0.35f};//Anchos de las filas
            PdfPTable tabla = new PdfPTable(anchosFilas);
            String rotulosColumnas[] = {"No", "CONT", "NAVIERA",
                "LINEA", "OPERA", "FECHA INGRESO", "TIPO CONT", "TM", "ESTADO", "TEUS", "TRANSITO"};
//                 private String fecha_ingreso1;
//    private String tipo_contenedor;
//    private String tm_total;
//    private String cant_teu;
//    private String transito;
            tabla.setWidthPercentage(100);
            PdfPCell cell = new PdfPCell();

            //System.out.println("CONSTRUIR TABLA");
            for (int i = 0; i < rotulosColumnas.length; i++) {
                cell = new PdfPCell(new Paragraph(rotulosColumnas[i], FontFactory.getFont(FontFactory.TIMES_ROMAN, 9, Font.BOLD)));
                tabla.addCell(cell);
            }
//            System.out.println("CONSTRUIR TABLA");
//            for (int i = 0; i < rotulosColumnas.length; i++) {
//                cell = new PdfPCell(new Paragraph(rotulosColumnas[i],FontFactory.getFont(FontFactory.TIMES_ROMAN, 10, Font.BOLD)));
//                tabla.addCell(cell);
//            }

            int i = 0, contador = 0;

            for (ReporteServiciosParticulares2Md a : allReporteCli) {
                i++;

                System.out.println("contador " + contador++);

                String numCadena = contador + "";

//                cell = new PdfPCell(new Paragraph(a.getAnio(),FontFactory.getFont("Arial", 7, Font.NORMAL)));
//                tabla.addCell(cell);
                cell = new PdfPCell(new Paragraph(numCadena, FontFactory.getFont("Arial", 7, Font.NORMAL)));
                tabla.addCell(cell);
                cell = new PdfPCell(new Paragraph(a.getContenedor(), FontFactory.getFont("Arial", 7, Font.NORMAL)));
                tabla.addCell(cell);
                cell = new PdfPCell(new Paragraph(a.getLinea(), FontFactory.getFont("Arial", 7, Font.NORMAL)));
                tabla.addCell(cell);
                cell = new PdfPCell(new Paragraph(a.getNaviera(), FontFactory.getFont("Arial", 7, Font.NORMAL)));
                tabla.addCell(cell);
                cell = new PdfPCell(new Paragraph(a.getOperacion(), FontFactory.getFont("Arial", 7, Font.NORMAL)));
                tabla.addCell(cell);
                cell = new PdfPCell(new Paragraph(a.getFecha_ingreso1(), FontFactory.getFont("Arial", 7, Font.NORMAL)));
                tabla.addCell(cell);
                cell = new PdfPCell(new Paragraph(a.getTipo_contenedor(), FontFactory.getFont("Arial", 7, Font.NORMAL)));
                tabla.addCell(cell);
                cell = new PdfPCell(new Paragraph(a.getTm_total(), FontFactory.getFont("Arial", 7, Font.NORMAL)));
                tabla.addCell(cell);
                cell = new PdfPCell(new Paragraph(a.getEstado(), FontFactory.getFont("Arial", 7, Font.NORMAL)));
                tabla.addCell(cell);
                cell = new PdfPCell(new Paragraph(a.getCant_teu(), FontFactory.getFont("Arial", 7, Font.NORMAL)));
                tabla.addCell(cell);
                cell = new PdfPCell(new Paragraph(a.getTransito(), FontFactory.getFont("Arial", 7, Font.NORMAL)));
                tabla.addCell(cell);

            }

            ParrafoHoja.add(tabla);
            ParrafoHoja.add(new Paragraph("\n\n Esta Informacion Es Transmitida via Servicio WEB\n Desde La Terminal APM Hacia "
                    + "Nuestros Sistemas De Operaciones", FontFactory.getFont("Arial", 10, Font.NORMAL)));
            document.open();
            document.add(ParrafoHoja);
            document.close();

            AMedia amedia = new AMedia("Reporte Contenedores APM.PDF", "PDF", "application/file", baos.toByteArray());
            Filedownload.save(amedia);
            baos.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //BOTON PARA DESCARGAR EL RESUMEN
    public void onClick$btnDescargar2(Event e) throws SQLException, ClassNotFoundException, IOException {
        GeneraPDF2(anioArribo.getText(), NumArribo.getText());
    }

    public void GeneraPDF2(String anio, String numero) throws SQLException, ClassNotFoundException {

        Document document;
        Paragraph ParrafoHoja = new Paragraph();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        List<ReporteServiciosParticulares2Md> allReporteResumen = new ArrayList<ReporteServiciosParticulares2Md>();
        ReporteServiciosParticulares2Dal rd = new ReporteServiciosParticulares2Dal();
        allReporteResumen = rd.REGselectResum(anio, numero);
        //para poder mostrar el los datos del encabezado
        List<ReporteServiciosParticulares2Md> allReporteCli = new ArrayList<ReporteServiciosParticulares2Md>();
        ReporteServiciosParticulares2Dal rd2 = new ReporteServiciosParticulares2Dal();
        allReporteCli = rd2.REGselect(anio, numero);

        if (allReporteCli.isEmpty()) {
            // Clients.showNotification("NO TIENE DATOS..!");
            Clients.showNotification("NO TIENE DATOS..!<br/>",
                    "warning", null, "middle_center", 5000);
        }
        try {
            document = new Document();
            document.setPageSize(PageSize.LETTER);
            PdfWriter.getInstance(document, baos);
//            String dirImagen = desktop.getWebApp().getRealPath("bootstrap") + "/epq.png";
//            Image im = Image.getInstance(dirImagen);
//            im.setAlignment(Image.ALIGN_RIGHT | Image.TEXTWRAP);
//            im.setAbsolutePosition(10, 680);
//            im.scalePercent(10);
//            ParrafoHoja.add(im);
            ParrafoHoja.add(new Paragraph("              EMPRESA PORTUARIA QUETZAL"));
            ParrafoHoja.add(new Paragraph("              GERENCIA DE OPERACIONES PORTUARIAS"));
            ParrafoHoja.add(new Paragraph("              REPORTE DE CONTENEDORES POR ARRIBO DE APM"));
            ParrafoHoja.add(new Paragraph("              AÑO DE ARRIBO: " + allReporteCli.get(0).getAnio() + "    " + "NUMERO DE ARRIBO:" + allReporteCli.get(0).getNumero() + "   " + "NOMBRE BUQUE:" + allReporteCli.get(0).getNom_buque()));         // ParrafoHoja.add(new Paragraph("              PARTICULAR.:" + allReporteCli.get(0).getParticular()));

            float anchosFilas[] = {0.35f, 0.35f};//Anchos de las filas
            PdfPTable tabla = new PdfPTable(anchosFilas);
            String rotulosColumnas[] = {"CONCEPTO", "REGISTROS"};
//                 private String fecha_ingreso1;
//    private String tipo_contenedor;
//    private String tm_total;
//    private String cant_teu;
//    private String transito;
            tabla.setWidthPercentage(100);
            PdfPCell cell = new PdfPCell();

            //System.out.println("CONSTRUIR TABLA");
            for (int i = 0; i < rotulosColumnas.length; i++) {
                cell = new PdfPCell(new Paragraph(rotulosColumnas[i], FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD)));
                tabla.addCell(cell);
            }
//            System.out.println("CONSTRUIR TABLA");
//            for (int i = 0; i < rotulosColumnas.length; i++) {
//                cell = new PdfPCell(new Paragraph(rotulosColumnas[i],FontFactory.getFont(FontFactory.TIMES_ROMAN, 10, Font.BOLD)));
//                tabla.addCell(cell);
//            }

            int i = 0;

            for (ReporteServiciosParticulares2Md a : allReporteResumen) {
                i++;

                cell = new PdfPCell(new Paragraph(a.getDescripcion(), FontFactory.getFont("Arial", 10, Font.NORMAL)));
                tabla.addCell(cell);
                int datos = Integer.parseInt(a.getRegistros());
                String patron = "#,###.##";
                DecimalFormat formato = new DecimalFormat(patron);
                cell = new PdfPCell(new Paragraph(formato.format(datos), FontFactory.getFont("Arial", 10, Font.NORMAL)));
                tabla.addCell(cell);
            }
            
            ParrafoHoja.add(new Paragraph("\n\n"));
            ParrafoHoja.add(tabla);
            ParrafoHoja.add(new Paragraph("\n\n Esta Informacion Es Transmitida via Servicio WEB\n Desde La Terminal APM Hacia "
                    + "Nuestros Sistemas De Operaciones", FontFactory.getFont("Arial", 10, Font.NORMAL)));

            document.open();
            document.add(ParrafoHoja);
            document.close();

            AMedia amedia = new AMedia("Reporte Contenedores APM.PDF", "PDF", "application/file", baos.toByteArray());
            Filedownload.save(amedia);
            baos.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //BOTON PARA DESCARGAR EL RESUMEN POR FECHA
    public void onClick$btnDescargar3(Event e) throws SQLException, ClassNotFoundException, IOException {
        GeneraPDF3(fechaInicio.getText(), fechaFin.getText());
    }

    public void GeneraPDF3(String F_inicio, String F_fin) throws SQLException, ClassNotFoundException {

        Document document;
        Paragraph ParrafoHoja = new Paragraph();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        List<ReporteServiciosParticulares2Md> allReporteResumen = new ArrayList<ReporteServiciosParticulares2Md>();
        ReporteServiciosParticulares2Dal rd = new ReporteServiciosParticulares2Dal();
        allReporteResumen = rd.REGselectResumF(F_inicio, F_fin);

        if (allReporteResumen.isEmpty()) {
            // Clients.showNotification("NO TIENE DATOS..!");
            Clients.showNotification("NO TIENE DATOS..!<br/>",
                    "warning", null, "middle_center", 5000);
        }

        try {
            document = new Document();
            document.setPageSize(PageSize.LETTER);
            PdfWriter.getInstance(document, baos);
//            String dirImagen = desktop.getWebApp().getRealPath("bootstrap") + "/epq.png";
//            Image im = Image.getInstance(dirImagen);
//            im.setAlignment(Image.ALIGN_RIGHT | Image.TEXTWRAP);
//            im.setAbsolutePosition(10, 680);
//            im.scalePercent(10);
//            ParrafoHoja.add(im);
            ParrafoHoja.add(new Paragraph("              EMPRESA PORTUARIA QUETZAL"));
            ParrafoHoja.add(new Paragraph("              GERENCIA DE OPERACIONES PORTUARIAS"));
            ParrafoHoja.add(new Paragraph("              REPORTE DE CONTENEDORES POR FECHA DE APM"));
            ParrafoHoja.add(new Paragraph("              FECHA INICIO: " + fechaInicio.getText() + "    " + "FECHA FIN:" + fechaFin.getText()));         // ParrafoHoja.add(new Paragraph("              PARTICULAR.:" + allReporteCli.get(0).getParticular()));

            float anchosFilas[] = {0.35f, 0.35f};//Anchos de las filas
            PdfPTable tabla = new PdfPTable(anchosFilas);
            String rotulosColumnas[] = {"CONCEPTO", "REGISTROS"};
//                 private String fecha_ingreso1;
//    private String tipo_contenedor;
//    private String tm_total;
//    private String cant_teu;
//    private String transito;
            tabla.setWidthPercentage(100);
            PdfPCell cell = new PdfPCell();

            //System.out.println("CONSTRUIR TABLA");
            for (int i = 0; i < rotulosColumnas.length; i++) {
                cell = new PdfPCell(new Paragraph(rotulosColumnas[i], FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD)));
                tabla.addCell(cell);
            }
//        

            int i = 0;

            for (ReporteServiciosParticulares2Md a : allReporteResumen) {
                i++;

                cell = new PdfPCell(new Paragraph(a.getDescripcion(), FontFactory.getFont("Arial", 10, Font.NORMAL)));
                tabla.addCell(cell);
                
                int datos = Integer.parseInt(a.getRegistros());
                String patron = "#,###.##";
                DecimalFormat formato = new DecimalFormat(patron);

                cell = new PdfPCell(new Paragraph(formato.format(datos), FontFactory.getFont("Arial", 10, Font.NORMAL)));
                tabla.addCell(cell);
            }
            
            ParrafoHoja.add(new Paragraph("\n\n"));

            ParrafoHoja.add(tabla);
            ParrafoHoja.add(new Paragraph("\n\n Esta Informacion Es Transmitida via Servicio WEB\n Desde La Terminal APM Hacia "
                    + "Nuestros Sistemas De Operaciones", FontFactory.getFont("Arial", 10, Font.NORMAL)));

            document.open();
            document.add(ParrafoHoja);
            document.close();

            AMedia amedia = new AMedia("Reporte Contenedores APM.PDF", "PDF", "application/file", baos.toByteArray());
            Filedownload.save(amedia);
            baos.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
