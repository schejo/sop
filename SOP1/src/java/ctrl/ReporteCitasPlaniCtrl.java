package ctrl;

import DAL.ReporteCitasPlaniDal;
import MD.ReporteCitasPlaniMd;
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

public class ReporteCitasPlaniCtrl extends GenericForwardComposer {

    private Datebox fecha_inicio;
    private Datebox fechaFin;

    public void doAfterComposer(Component comp) throws Exception {
        super.doAfterCompose(comp);

    }

    public void onClick$btnDescargar(Event evn) throws SQLException, IOException {
        if (!fecha_inicio.getText().isEmpty()) {
            ReporteExcel(fecha_inicio.getText(), fechaFin.getText());
        } else {
            Clients.showNotification("Ingrese una fecha por favor");
        }
    }

    public void ReporteExcel(String fechainicio, String fechaFin) throws SQLException, IOException {
        ReporteCitasPlaniDal dataBase = new ReporteCitasPlaniDal();
        List<ReporteCitasPlaniMd> lista = dataBase.GetByFecha(fechainicio, fechaFin);
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
        c2.setCellValue("REPORTE DE HISTORIAL DE CONVOCATORIAS");
        c2.setCellStyle(style);

        Cell c3 = tutulo3.createCell(1);
        c3.setCellValue("Del.: " + fechainicio + " Al.: " + fechaFin);
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
        cE4.setCellValue("FECHA CITA");
        cE4.setCellStyle(style2);

        Cell cE5 = encabezado.createCell(4);
        cE5.setCellValue("FECHA CONVOCATORIA");
        cE5.setCellStyle(style2);
        
        Cell cE6 = encabezado.createCell(5);
        cE6.setCellValue("OBSERVACIONES");
        cE6.setCellStyle(style2);
        
        Cell cE7 = encabezado.createCell(6);
        cE7.setCellValue("HRS OPERACION");
        cE7.setCellStyle(style2);
        
        for (ReporteCitasPlaniMd item : lista) {
            Row contenido = listSheet.createRow(index++);

            Cell cC1 = contenido.createCell(0);
            cC1.setCellValue(item.getAnio_arribo());
            cC1.setCellStyle(styleEntero);

            Cell cC2 = contenido.createCell(1);
            cC2.setCellValue(item.getNum_arribo());
            cC2.setCellStyle(style2);

            Cell cC3 = contenido.createCell(2);
            cC3.setCellValue(item.getNombre_buque());
            cC3.setCellStyle(style2);

            Cell cC4 = contenido.createCell(3);
            cC4.setCellValue(item.getEta_cita());
            cC4.setCellStyle(style2);

            Cell cC5 = contenido.createCell(4);
            cC5.setCellValue(item.getFecha_hora());
            cC5.setCellStyle(styleEntero);

            Cell cC6 = contenido.createCell(5);
            cC6.setCellValue(item.getObservaciones());
            cC6.setCellStyle(styleEntero);
            
            Cell cC7 = contenido.createCell(6);
            cC7.setCellValue(item.getHras_plani());
            cC7.setCellStyle(styleEntero);
            
        }

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            estilo.autoSizeColumns(workbook, 7);
            workbook.write(baos);
            AMedia amedia = new AMedia("ReporteDeConvocatorias.xls", "xls", "application/file", baos.toByteArray());
            Filedownload.save(amedia);
            baos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
