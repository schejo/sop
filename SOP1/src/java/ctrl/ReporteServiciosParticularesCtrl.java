package ctrl;

import DAL.ReporteServiciosParticularesDal;
import MD.ReporteServiciosParticularesMd;
import Util.EstilosReporte;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
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
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.zkoss.zul.Combobox;

public class ReporteServiciosParticularesCtrl extends GenericForwardComposer {

    private Datebox fechaInicio;
    private Datebox fechaFin;
    private Combobox codPar;

    public void doAfterComposer(Component comp) throws Exception {
        super.doAfterCompose(comp);

    }

    //BOTON PARA DESCARGAR EL REPORTE EN EXCEL
    public void onClick$btnDescargar(Event e) throws SQLException, IOException, ClassNotFoundException {
        ReporteExcel(fechaInicio.getText(), fechaFin.getText(), codPar.getSelectedItem().getValue().toString());

    }

    public void ReporteExcel(String finicio, String fFin, String codPar) throws SQLException, IOException, ClassNotFoundException {

        List<ReporteServiciosParticularesMd> allReporteCli = new ArrayList<ReporteServiciosParticularesMd>();
        // List<ReporteViajesMd> lista = dataBase.GetByFecha(cambio_fecha(fechaInicial), cambio_fecha(fechaFinal));
        ReporteServiciosParticularesDal rd = new ReporteServiciosParticularesDal();
        allReporteCli = rd.REGselect(finicio, fFin, codPar);
        Workbook workbook = new HSSFWorkbook();

        EstilosReporte estilo = new EstilosReporte();
        CellStyle style = estilo.Estilo1(workbook);
        CellStyle style2 = estilo.Estilo2(workbook);
        CellStyle styleEntero = estilo.EstiloEnteros2(workbook);

        // String dirImagen ="http://"+InetAddress.getLocalHost().getHostName().toString()+":8080/bootstrap/epq.png";
        Sheet listSheet = workbook.createSheet("Reporte Servicios Particulares");
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
        c2.setCellValue("   REPORTE DE SERVICIOS DE PARTICULARES");
        c2.setCellStyle(style);

        Cell c3 = titulo3.createCell(1);
        c3.setCellValue("   DEL.: " + finicio + "       AL.:" + fFin);
        c3.setCellStyle(style);

        Cell c4 = titulo4.createCell(1);
        c4.setCellValue("PARTICULAR.:" + allReporteCli.get(0).getParticular()); // + allReporteCli.get(0).getParticular());
        c4.setCellStyle(style);

        Cell cE2 = encabezado.createCell(1);
        cE2.setCellValue("AÑO ARRIBO");
        cE2.setCellStyle(style2);

        Cell cE3 = encabezado.createCell(2);
        cE3.setCellValue("NO. ARRIBO");
        cE3.setCellStyle(style2);

        Cell cE4 = encabezado.createCell(3);
        cE4.setCellValue("NOMBRE BUQUE");
        cE4.setCellStyle(style2);

        Cell cE5 = encabezado.createCell(4);
        cE5.setCellValue("ATRAQUE");
        cE5.setCellStyle(style2);

        Cell cE6 = encabezado.createCell(5);
        cE6.setCellValue("CODIGO SERVICIO");
        cE6.setCellStyle(style2);

        Cell cE7 = encabezado.createCell(6);
        cE7.setCellValue("DESCRIPCION SERVICIO");
        cE7.setCellStyle(style2);

        Cell cE8 = encabezado.createCell(7);
        cE8.setCellValue("BOLETA");
        cE8.setCellStyle(style2);

        Cell cE9 = encabezado.createCell(8);
        cE9.setCellValue("FECHA INICIO");
        cE9.setCellStyle(style2);

        Cell cE10 = encabezado.createCell(9);
        cE10.setCellValue("FECHA FIN");
        cE10.setCellStyle(style2);

        Cell cE11 = encabezado.createCell(10);
        cE11.setCellValue("OBSERVACIONES");
        cE11.setCellStyle(style2);

        for (ReporteServiciosParticularesMd item : allReporteCli) {
            Row contenido = listSheet.createRow(index++);

            Cell cC2 = contenido.createCell(1);
            cC2.setCellValue(item.getAnio_arribo());
            cC2.setCellStyle(styleEntero);

            Cell cC3 = contenido.createCell(2);
            cC3.setCellValue(item.getNumero_arribo());
            cC3.setCellStyle(style2);

            Cell cC4 = contenido.createCell(3);
            cC4.setCellValue(item.getNom_buque());
            cC4.setCellStyle(style2);

            Cell cC5 = contenido.createCell(4);
            cC5.setCellValue(item.getFecha_atraque());
            cC5.setCellStyle(style2);

            Cell cC6 = contenido.createCell(5);
            cC6.setCellValue(item.getCod_servicio());
            cC6.setCellStyle(style2);

            Cell cC7 = contenido.createCell(6);
            cC7.setCellValue(item.getDesc_servicio());
            cC7.setCellStyle(style2);

            Cell cC8 = contenido.createCell(7);
            cC8.setCellValue(item.getFactura());
            cC8.setCellStyle(style2);

            Cell cC9 = contenido.createCell(8);
            cC9.setCellValue(item.getFecha_inicio());
            cC9.setCellStyle(style2);

            Cell cC10 = contenido.createCell(9);
            cC10.setCellValue(item.getFecha_fin());
            cC10.setCellStyle(style2);

            Cell cC11 = contenido.createCell(10);
            cC11.setCellValue(item.getObservacion());
            cC11.setCellStyle(style2);

        }

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            estilo.autoSizeColumns(workbook, 5);
            workbook.write(baos);
            AMedia amedia = new AMedia("REPORTE DE SERVICIOS PARTICULARES.xls", "xls", "application/file", baos.toByteArray());
            Filedownload.save(amedia);
            baos.close();
        } catch (Exception e) {
            e.printStackTrace();

            System.out.println("EXCEPTION..: " + e.getMessage());
        }

    }

    //BOTON PARA DESCARGAR EL FORMATO EN PDF
    
    public void onClick$btnDescargar1(Event e) throws SQLException, ClassNotFoundException, IOException {
        GeneraPDF(fechaInicio.getText(), fechaFin.getText(), codPar.getSelectedItem().getValue().toString());
    }

    public void GeneraPDF(String finicio, String fFin, String codPar) throws SQLException, ClassNotFoundException {

        Document document;
        Paragraph ParrafoHoja = new Paragraph();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        List<ReporteServiciosParticularesMd> allReporteCli = new ArrayList<ReporteServiciosParticularesMd>();
        ReporteServiciosParticularesDal rd = new ReporteServiciosParticularesDal();
        allReporteCli = rd.REGselect(finicio, fFin, codPar);
        
        if (allReporteCli.isEmpty()) {
            Clients.showNotification("NO TIENE DATOS..!");
        }
        try {
            document = new Document();
            document.setPageSize(PageSize.LETTER);
            PdfWriter.getInstance(document, baos);
            //String dirImagen = desktop.getWebApp().getRealPath("bootstrap") + "/epq.png";
//            Image im = Image.getInstance(dirImagen);
//            im.setAlignment(Image.ALIGN_RIGHT | Image.TEXTWRAP);
//            im.setAbsolutePosition(10, 680);
//            im.scalePercent(10);
//            ParrafoHoja.add(im);
            ParrafoHoja.add(new Paragraph("              EMPRESA PORTUARIA QUETZAL"));
            ParrafoHoja.add(new Paragraph("              REPORTE DE SERVICIOS DE PARTICULARES"));
            ParrafoHoja.add(new Paragraph("              DEL.: " + finicio + "       AL.:" + fFin));
            ParrafoHoja.add(new Paragraph("              PARTICULAR.:" + allReporteCli.get(0).getParticular()));

            float anchosFilas[] = {0.2f, 0.15f, 0.15f, 0.3f, 0.3f, 0.2f, 0.85f, 0.2f, 0.3f, 0.3f, 0.35f};//Anchos de las filas
            PdfPTable tabla = new PdfPTable(anchosFilas);
            String rotulosColumnas[] = {"No", "AÑO", "No.", "NOMBRE", "ATRAQUE", "COD SER",
                "DESCRIPCION SERVICIO", "BOLETA", "FECHA INICIO", "FECHA FIN", "OBSERVACIONES"};

            tabla.setWidthPercentage(100);
            PdfPCell cell = new PdfPCell();

            //System.out.println("CONSTRUIR TABLA");
            for (int i = 0; i < rotulosColumnas.length; i++) {
                cell = new PdfPCell(new Paragraph(rotulosColumnas[i], FontFactory.getFont(FontFactory.TIMES_ROMAN, 9, Font.BOLD)));
                tabla.addCell(cell);
            }

            int i = 0, contador = 0;
            for (ReporteServiciosParticularesMd a : allReporteCli) {
                i++;
                contador++;
                String numCadena = contador + "";

                cell = new PdfPCell(new Paragraph(numCadena, FontFactory.getFont("Arial", 7, Font.NORMAL)));
                tabla.addCell(cell);

                cell = new PdfPCell(new Paragraph(a.getAnio_arribo(), FontFactory.getFont("Arial", 7, Font.NORMAL)));
                tabla.addCell(cell);
                cell = new PdfPCell(new Paragraph(a.getNumero_arribo(), FontFactory.getFont("Arial", 7, Font.NORMAL)));
                tabla.addCell(cell);
                cell = new PdfPCell(new Paragraph(a.getNom_buque(), FontFactory.getFont("Arial", 7, Font.NORMAL)));
                tabla.addCell(cell);
                cell = new PdfPCell(new Paragraph(a.getFecha_atraque(), FontFactory.getFont("Arial", 7, Font.NORMAL)));
                tabla.addCell(cell);
                cell = new PdfPCell(new Paragraph(a.getCod_servicio(), FontFactory.getFont("Arial", 7, Font.NORMAL)));
                tabla.addCell(cell);
                cell = new PdfPCell(new Paragraph(a.getDesc_servicio(), FontFactory.getFont("Arial", 7, Font.NORMAL)));
                tabla.addCell(cell);
                cell = new PdfPCell(new Paragraph(a.getFactura(), FontFactory.getFont("Arial", 7, Font.NORMAL)));
                tabla.addCell(cell);
                cell = new PdfPCell(new Paragraph(a.getFecha_inicio(), FontFactory.getFont("Arial", 7, Font.NORMAL)));
                tabla.addCell(cell);
                cell = new PdfPCell(new Paragraph(a.getFecha_fin(), FontFactory.getFont("Arial", 7, Font.NORMAL)));
                tabla.addCell(cell);
                cell = new PdfPCell(new Paragraph(a.getObservacion(), FontFactory.getFont("Arial", 7, Font.NORMAL)));
                tabla.addCell(cell);

            }
            //  ParrafoHoja.add(new Paragraph("              Reporte: "));
            ParrafoHoja.add(new Paragraph("                  \n"));
            ParrafoHoja.add(tabla);
            document.open();
            document.add(ParrafoHoja);
            document.close();

            AMedia amedia = new AMedia("Reporte.PDF", "PDF", "application/file", baos.toByteArray());
            Filedownload.save(amedia);
            baos.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
