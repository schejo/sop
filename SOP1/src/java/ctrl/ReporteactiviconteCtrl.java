package ctrl;

import DAL.ReporteactiviconteDal;
import MD.ReporteactiviconteMd;
import Util.EstilosReporte;
import java.util.List;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.SQLException;
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

public class ReporteactiviconteCtrl extends GenericForwardComposer {

    private Datebox fechaInicio;

    public void doAfterComposer(Component comp) throws Exception {
        super.doAfterCompose(comp);

    }

    public void onClick$btnDescargar(Event evn) throws SQLException, IOException {
        if ((fechaInicio.getText().equals("")) || (fechaInicio.getText().isEmpty())) {
            
            Clients.showNotification("NO SE PUEDE GENERAR REPORTE LLENAR LOS CAMPOS VACIOS!  <br/> INGRESE LOS DATOS POR FAVOR PARA GENERAR EL PDF....!!!!<br/>",
                    "warning", null, "middle_center", 5000);
        } else {

            ReporteExcel(fechaInicio.getText());
        }
    }

    public void ReporteExcel(String fechaInicio) throws SQLException, IOException {
        ReporteactiviconteDal dataBase = new ReporteactiviconteDal();
        List<ReporteactiviconteMd> lista = dataBase.GetByFecha(fechaInicio);
        Workbook workbook = new HSSFWorkbook();

        // Bitacora bt = new Bitacora();
        //String rps = bt.login(Login.usuario, "Estadistica-Web", "Reporte", "null", 0, 0, "Reporte Demoras de Buques");  
        //Bitacora Ingreso
        // Bitacora cc = new Bitacora();
        //String rps2 = cc.CCopias("15", "Reporte Demoras de Buque", "", Login.usuario);
        //estilo de celdas
        EstilosReporte estilo = new EstilosReporte();
        CellStyle style = estilo.Estilo1(workbook);
        CellStyle style2 = estilo.Estilo2(workbook);
        CellStyle styleEntero = estilo.EstiloEnteros2(workbook);
        //hoja
        String dirImagen = desktop.getWebApp().getRealPath("bootstrap") + "/epq.png";
        Sheet listSheet = workbook.createSheet("Reporte Actividad Contenedores");
        estilo.insertImage(workbook, listSheet, dirImagen);
        //filas
        Row titulo1 = listSheet.createRow(0);
        Row titulo2 = listSheet.createRow(1);
        Row titulo3 = listSheet.createRow(2);
        Row encabezado = listSheet.createRow(5);
        int index = 7;

        Cell c1 = titulo1.createCell(1);
        c1.setCellValue("EMPRESA PORTUARIA QUETZAL");
        c1.setCellStyle(style);

        Cell c2 = titulo2.createCell(1);
        c2.setCellValue("REPORTE DE ARRIBO DE BUQUES");
        c2.setCellStyle(style);

        Cell c3 = titulo3.createCell(1);
        c3.setCellValue("DEL.: " + fechaInicio);
        c3.setCellStyle(style);

        Cell cE1 = encabezado.createCell(0);
        cE1.setCellValue("AÑO");
        cE1.setCellStyle(style2);

        Cell cE2 = encabezado.createCell(1);
        cE2.setCellValue("MES");
        cE2.setCellStyle(style2);

//        Cell cE3 = encabezado.createCell(2);
//        cE3.setCellValue("NOMBRE MES");
//        cE3.setCellStyle(style2);
        Cell cE3 = encabezado.createCell(2);
        cE3.setCellValue("IMPORTACION");
        cE3.setCellStyle(style2);

        Cell cE4 = encabezado.createCell(3);
        cE4.setCellValue("EXPORTACION");
        cE4.setCellStyle(style2);

        Cell cE5 = encabezado.createCell(4);
        cE5.setCellValue("RAYOS X");
        cE5.setCellStyle(style2);

        for (ReporteactiviconteMd item : lista) {
            Row contenido = listSheet.createRow(index++);
            Cell cC1 = contenido.createCell(0);
            cC1.setCellValue(item.getAnio());
            cC1.setCellStyle(styleEntero);

            Cell cC2 = contenido.createCell(1);
            cC2.setCellValue(item.getMes());
            cC2.setCellStyle(style2);

//            Cell cC3 = contenido.createCell(2);
//            cC3.setCellValue(item.getNombre_mes());
//            cC3.setCellStyle(style2);
            Cell cC3 = contenido.createCell(2);
            cC3.setCellValue(item.getImportacion());
            cC3.setCellStyle(style2);

            Cell cC4 = contenido.createCell(3);
            cC4.setCellValue(item.getExportacion());
            cC4.setCellStyle(style2);

            Cell cC5 = contenido.createCell(4);
            cC5.setCellValue(item.getRayosx());
            cC5.setCellStyle(styleEntero);
        }

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            estilo.autoSizeColumns(workbook, 6);
            workbook.write(baos);
            AMedia amedia = new AMedia("Reporte De Actividades de Contenedores.xls", "xls", "application/file", baos.toByteArray());
            Filedownload.save(amedia);
            baos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
