/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ctrl;

import DAL.ReporteDesPorNavieraDal;
import MD.ReporteDesPorNavieraMd;
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
import org.zkoss.zul.Textbox;

/**
 *
 * @author Usuario
 */
public class ReporteDesPorNavieraCtrl extends GenericForwardComposer{
    
    private Datebox fechaInicio;
    private Datebox fechaFin;
    private Textbox Naviera;
    
      public void doAfterComposer(Component comp) throws Exception {
        super.doAfterCompose(comp);

    }
       public void onClick$btnDescargar3(Event e) throws SQLException, ClassNotFoundException, IOException {
           if(fechaInicio.getText().equals("") || fechaFin.getText().equals("") || Naviera.getText().equals("")){
                Clients.showNotification("NO SE A GENERADO EL REPORTE <br/> DEBE LLENAR LOS CAMPOS VACIOS! <br/>",
                    "warning", null, "middle_center", 5000);
               
           }else{
               ReporteExcel(fechaInicio.getText(), fechaFin.getText(),Naviera.getText().trim().toUpperCase());
               Clients.showNotification("EMTRO AL REPORTE EXCEL! <br/>",
                    "warning", null, "middle_center", 5000);
           }
           
        
    }
    
    
    
       public void ReporteExcel(String inicio, String fin,String naviera) throws SQLException, IOException, ClassNotFoundException {

        List<ReporteDesPorNavieraMd> allReporteCli = new ArrayList<ReporteDesPorNavieraMd>();
        // List<ReporteViajesMd> lista = dataBase.GetByFecha(cambio_fecha(fechaInicial), cambio_fecha(fechaFinal));
        ReporteDesPorNavieraDal rd = new ReporteDesPorNavieraDal();
        allReporteCli = rd.REGselect(inicio, fin,naviera);
        Workbook workbook = new HSSFWorkbook();

        EstilosReporte estilo = new EstilosReporte();
        CellStyle style = estilo.Estilo1(workbook);
        CellStyle style2 = estilo.Estilo2(workbook);
        CellStyle styleEntero = estilo.EstiloEnteros2(workbook);

        // String dirImagen ="http://"+InetAddress.getLocalHost().getHostName().toString()+":8080/bootstrap/epq.png";
        Sheet listSheet = workbook.createSheet("Reporte De Despacho ");
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
        c2.setCellValue("   REPORTE DE DESPACHO");
        c2.setCellStyle(style);

        Cell c3 = titulo3.createCell(1);
        c3.setCellValue("   DESPACHO DE CONTENEDORES");
        c3.setCellStyle(style);

        Cell c4 = titulo4.createCell(1);
        c4.setCellValue("   FECHA INICIO.: " + inicio + "       FECHA FIN.:" + fin);
        c4.setCellStyle(style);

//        Cell c5 = titulo4.createCell(1);
//        c5.setCellValue("NOMBRE BUQUE.:" + allReporteCli.get(0).getNom_buque());
//        c5.setCellStyle(style);

        Cell cE2 = encabezado.createCell(1);
        cE2.setCellValue("CORRELATIVO");
        cE2.setCellStyle(style2);

        Cell cE3 = encabezado.createCell(2);
        cE3.setCellValue("NO. DE CONTENEDOR");
        cE3.setCellStyle(style2);

        Cell cE4 = encabezado.createCell(3);
        cE4.setCellValue("NAVIERA");
        cE4.setCellStyle(style2);

        Cell cE5 = encabezado.createCell(4);
        cE5.setCellValue("FECHA DE DESEMBARQUE");
        cE5.setCellStyle(style2);

        Cell cE6 = encabezado.createCell(5);
        cE6.setCellValue("FECHA DE SALIDA");
        cE6.setCellStyle(style2);

        Cell cE7 = encabezado.createCell(6);
        cE7.setCellValue("DIAS TRANSCURIDOS");
        cE7.setCellStyle(style2);

//        Cell cE8 = encabezado.createCell(7);
//        cE8.setCellValue("TM");
//        cE8.setCellStyle(style2);
//
//        Cell cE9 = encabezado.createCell(8);
//        cE9.setCellValue("ESTADO");
//        cE9.setCellStyle(style2);
//
//        Cell cE10 = encabezado.createCell(9);
//        cE10.setCellValue("TEUS");
//        cE10.setCellStyle(style2);
//
//        Cell cE11 = encabezado.createCell(10);
//        cE11.setCellValue("TRANSITO");
//        cE11.setCellStyle(style2);

        for (ReporteDesPorNavieraMd item : allReporteCli) {
            Row contenido = listSheet.createRow(index++);

            Cell cC2 = contenido.createCell(1);
            cC2.setCellValue(item.getCorre());
            cC2.setCellStyle(styleEntero);

            Cell cC3 = contenido.createCell(2);
            cC3.setCellValue(item.getIdentif());
            cC3.setCellStyle(style2);

            Cell cC4 = contenido.createCell(3);
            cC4.setCellValue(item.getNombreNavi());
            cC4.setCellStyle(style2);

            Cell cC5 = contenido.createCell(4);
            cC5.setCellValue(item.getFechaDese());
            cC5.setCellStyle(style2);

            Cell cC6 = contenido.createCell(5);
            cC6.setCellValue(item.getFechaSalida());
            cC6.setCellStyle(style2);

            Cell cC7 = contenido.createCell(6);
            cC7.setCellValue(item.getDias());
            cC7.setCellStyle(style2);

//            Cell cC8 = contenido.createCell(7);
//            cC8.setCellValue(item.getTm_total());
//            cC8.setCellStyle(style2);
//
//            Cell cC9 = contenido.createCell(8);
//            cC9.setCellValue(item.getEstado());
//            cC9.setCellStyle(style2);
//
//            Cell cC10 = contenido.createCell(9);
//            cC10.setCellValue(item.getCant_teu());
//            cC10.setCellStyle(style2);
//
//            Cell cC11 = contenido.createCell(10);
//            cC11.setCellValue(item.getTransito());
//            cC11.setCellStyle(style2);

        }

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            estilo.autoSizeColumns(workbook, 5);
            workbook.write(baos);
            AMedia amedia = new AMedia("REPORTE DE DESPACHO.xls", "xls", "application/file", baos.toByteArray());
            Filedownload.save(amedia);
            baos.close();
        } catch (Exception e) {
            e.printStackTrace();

            System.out.println("EXCEPTION..: " + e.getMessage());
        }

    }
    
}
