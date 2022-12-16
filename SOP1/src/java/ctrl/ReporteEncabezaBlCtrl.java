package ctrl;

import DAL.ReporteEncabezaBlDal;
import MD.ReporteEncabezaBlMd;
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


public class ReporteEncabezaBlCtrl extends GenericForwardComposer {

    private Datebox fechaInicio;
    private Datebox fechaFin;

    public void doAfterComposer(Component comp) throws Exception {
        super.doAfterCompose(comp);

    }

    public void onClick$btnDescargar(Event evn) throws SQLException, IOException {
        if (!fechaInicio.getText().isEmpty()) {
            ReporteExcel(fechaInicio.getText(), fechaFin.getText());
        } else {
            Clients.showNotification("INGRESE UNA FECHA POR FAVOR....!!!!");
        }
    }
    
    
    public void ReporteExcel(String fechaInicio, String fechaFin) throws SQLException, IOException {
        ReporteEncabezaBlDal dataBase = new ReporteEncabezaBlDal();
        List<ReporteEncabezaBlMd> lista = dataBase.GetByFecha(fechaInicio, fechaFin);
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
        Sheet listSheet = workbook.createSheet("ReporteEncabezaBl");
        estilo.insertImage(workbook, listSheet, dirImagen);
        //filas
        Row titulo1 = listSheet.createRow(0);
        Row titulo2 = listSheet.createRow(1);
        Row titulo3 = listSheet.createRow(2);
        Row encabezado = listSheet.createRow(5);
        int index = 6;

        Cell c1 = titulo1.createCell(1);
        c1.setCellValue("EMPRESA PORTUARIA QUETZAL");
        c1.setCellStyle(style);

        Cell c2 = titulo2.createCell(1);
        c2.setCellValue("REPORTE DE CONTENEDORES DE IMPORTACION");
        c2.setCellStyle(style);
        
        Cell c3 = titulo3.createCell(1);
        c3.setCellValue("Del.: "+fechaInicio+" Al.: "+fechaFin);
        c3.setCellStyle(style);
        

        Cell cE1 = encabezado.createCell(0);
        cE1.setCellValue("AÑO MANIFIESTO");
        cE1.setCellStyle(style2);

        Cell cE2 = encabezado.createCell(1);
        cE2.setCellValue("MANIFIESTO");
        cE2.setCellStyle(style2);

        Cell cE3 = encabezado.createCell(2);
        cE3.setCellValue("CALIFICADOR MANIFIESTO");
        cE3.setCellStyle(style2);

        Cell cE4 = encabezado.createCell(3);
        cE4.setCellValue("CORRELATIVO BL");
        cE4.setCellStyle(style2);

        Cell cE5 = encabezado.createCell(4);
        cE5.setCellValue("BUMERO BL");
        cE5.setCellStyle(style2);

        Cell cE6 = encabezado.createCell(5);
        cE6.setCellValue("PUERTO");
        cE6.setCellStyle(style2);

        Cell cE7 = encabezado.createCell(6);
        cE7.setCellValue("PUERTO TRANSBORDO");
        cE7.setCellStyle(style2);

        Cell cE8 = encabezado.createCell(7);
        cE8.setCellValue("DESTINO FINAL");
        cE8.setCellStyle(style2);
        
         Cell cE9 = encabezado.createCell(8);
        cE9.setCellValue("PESO");
        cE9.setCellStyle(style2);
        
         Cell cE10 = encabezado.createCell(9);
        cE10.setCellValue("PAIS DE PROCEDENCIA");
        cE10.setCellStyle(style2);
        
         Cell cE11 = encabezado.createCell(10);
        cE11.setCellValue("PAIS DESTINO FINAL");
        cE11.setCellStyle(style2);
        
         Cell cE12 = encabezado.createCell(11);
        cE12.setCellValue("EMBARCADOR");
        cE12.setCellStyle(style2);
        
         Cell cE13 = encabezado.createCell(12);
        cE13.setCellValue("CONSIGNATARIO");
        cE13.setCellStyle(style2);
        
         Cell cE14 = encabezado.createCell(13);
        cE14.setCellValue("NOTIFICADOR");
        cE14.setCellStyle(style2);
        
         Cell cE15 = encabezado.createCell(14);
        cE15.setCellValue("OBSERVACIONES BL");
        cE15.setCellStyle(style2);
        
         Cell cE16 = encabezado.createCell(15);
        cE16.setCellValue("NOMBRE PUERTO");
        cE16.setCellStyle(style2);
        
         Cell cE17 = encabezado.createCell(16);
        cE17.setCellValue("LOCALIDAD");
        cE17.setCellStyle(style2);
        
         Cell cE18 = encabezado.createCell(17);
        cE18.setCellValue("NOMBRE PAIS");
        cE18.setCellStyle(style2);

        for (ReporteEncabezaBlMd item : lista) {
            Row contenido = listSheet.createRow(index++);
            
            Cell cC1 = contenido.createCell(0);
            cC1.setCellValue(item.getAno_manifiesto());
            cC1.setCellStyle(styleEntero);

            Cell cC2 = contenido.createCell(1);
            cC2.setCellValue(item.getManifiesto());
            cC2.setCellStyle(style2);

            Cell cC3 = contenido.createCell(2);
            cC3.setCellValue(item.getCalificador_manifi());
            cC3.setCellStyle(style2);

            Cell cC4 = contenido.createCell(3);
            cC4.setCellValue(item.getCorrelativo_bl());
            cC4.setCellStyle(style2);

            Cell cC5 = contenido.createCell(4);
            cC5.setCellValue(item.getBl_numero());
            cC5.setCellStyle(styleEntero);

            Cell cC6 = contenido.createCell(5);
            cC6.setCellValue(item.getPuerto());
            cC6.setCellStyle(styleEntero);

            Cell cC7 = contenido.createCell(6);
            cC7.setCellValue(item.getPuerto_transbor());
            cC7.setCellStyle(style2);

            Cell cC8 = contenido.createCell(7);
            cC8.setCellValue(item.getLocalidad_dest_fin());
            cC8.setCellStyle(style2);
            
             Cell cC9 = contenido.createCell(8);
            cC9.setCellValue(item.getPeso1());
            cC9.setCellStyle(style2);
            
             Cell cC10 = contenido.createCell(9);
            cC10.setCellValue(item.getPais_procedencia());
            cC10.setCellStyle(style2);
            
             Cell cC11 = contenido.createCell(10);
            cC11.setCellValue(item.getPais_destino_final());
            cC11.setCellStyle(style2);
            
             Cell cC12 = contenido.createCell(11);
            cC12.setCellValue(item.getEmbarcador());
            cC12.setCellStyle(style2);
            
             Cell cC13 = contenido.createCell(12);
            cC13.setCellValue(item.getConsignatario());
            cC13.setCellStyle(style2);
            
             Cell cC14 = contenido.createCell(13);
            cC14.setCellValue(item.getNotificar());
            cC14.setCellStyle(style2);
            
             Cell cC15 = contenido.createCell(14);
            cC15.setCellValue(item.getObservaciones_bl());
            cC15.setCellStyle(style2);
            
             Cell cC16 = contenido.createCell(15);
            cC16.setCellValue(item.getNombre_puerto());
            cC16.setCellStyle(style2);
            
             Cell cC17 = contenido.createCell(16);
            cC17.setCellValue(item.getCodigo_localidad());
            cC17.setCellStyle(style2);
            
             Cell cC18 = contenido.createCell(17);
            cC18.setCellValue(item.getNombre_pais());
            cC18.setCellStyle(style2);
 
        }

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            estilo.autoSizeColumns(workbook, 10);
            workbook.write(baos);
            AMedia amedia = new AMedia("ReporteEncabezaBl.xls", "xls", "application/file", baos.toByteArray());
            Filedownload.save(amedia);
            baos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    

}
