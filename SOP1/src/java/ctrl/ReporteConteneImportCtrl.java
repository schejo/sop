package ctrl;

import DAL.ReporteConteneImportDal;
import MD.ReporteConteneImportMd;
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
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Textbox;

public class ReporteConteneImportCtrl extends GenericForwardComposer {

    private Textbox anio;
    private Textbox numero;

    public void doAfterComposer(Component comp) throws Exception {
        super.doAfterCompose(comp);

    }

    public void onClick$btnDescargar(Event evn) throws SQLException, IOException {
        if (!anio.getText().isEmpty()) {
            ReporteExcel(anio.getText(), numero.getText());
        } else {
            Clients.showNotification("NO SE GUARDO EL REGISTRO DEBE LLENAR LOS CAMPOS VACIOS!  <br/> INGRESE AÑO Y NUMERO DE ARRIBO POR FAVOR PARA GENERAR EL EXCEL....!!!!<br/>",
                    "warning", null, "middle_center", 5000);
        }
    }

    public void ReporteExcel(String anio, String numero) throws SQLException, IOException {
        ReporteConteneImportDal dataBase = new ReporteConteneImportDal();
        List<ReporteConteneImportMd> lista = dataBase.GetByFecha(anio, numero);
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
        Sheet listSheet = workbook.createSheet("Reporte Contenedores de Importacion");
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
        c3.setCellValue("AÑO ARRIBO.: " + anio + " NUMERO ARRIBO.: " + numero);
        c3.setCellStyle(style);

        Cell cE1 = encabezado.createCell(0);
        cE1.setCellValue("CODIGO BUQUE");
        cE1.setCellStyle(style2);

        Cell cE2 = encabezado.createCell(1);
        cE2.setCellValue("NOMBRE BUQUE");
        cE2.setCellStyle(style2);

        Cell cE3 = encabezado.createCell(2);
        cE3.setCellValue("AÑO ARRIBO");
        cE3.setCellStyle(style2);

        Cell cE4 = encabezado.createCell(3);
        cE4.setCellValue("NUMERO ARRIBO ");
        cE4.setCellStyle(style2);

        Cell cE5 = encabezado.createCell(4);
        cE5.setCellValue("DATOS IMPORTACION");
        cE5.setCellStyle(style2);

        Cell cE6 = encabezado.createCell(5);
        cE6.setCellValue("DATOS EXPORTACION");
        cE6.setCellStyle(style2);

        Cell cE7 = encabezado.createCell(6);
        cE7.setCellValue("FECHA ATRAQUE");
        cE7.setCellStyle(style2);

        Cell cE8 = encabezado.createCell(7);
        cE8.setCellValue("INICIO OPERA");
        cE8.setCellStyle(style2);

        Cell cE9 = encabezado.createCell(8);
        cE9.setCellValue("FIN OPERA");
        cE9.setCellStyle(style2);

        Cell cE10 = encabezado.createCell(9);
        cE10.setCellValue("CONTENEDORES");
        cE10.setCellStyle(style2);

        Cell cE11 = encabezado.createCell(10);
        cE11.setCellValue("LLENO - VACIO");
        cE11.setCellStyle(style2);

        Cell cE12 = encabezado.createCell(11);
        cE12.setCellValue("ACTIVIDAD");
        cE12.setCellStyle(style2);

        Cell cE13 = encabezado.createCell(12);
        cE13.setCellValue("VIA DIRECTA");
        cE13.setCellStyle(style2);

        Cell cE14 = encabezado.createCell(13);
        cE14.setCellValue("VIA INDIRECTA");
        cE14.setCellStyle(style2);

        Cell cE15 = encabezado.createCell(14);
        cE15.setCellValue("VIA INTERMEDIA");
        cE15.setCellStyle(style2);

        Cell cE16 = encabezado.createCell(15);
        cE16.setCellValue("VIA MAERSK");
        cE16.setCellStyle(style2);

        Cell cE17 = encabezado.createCell(16);
        cE17.setCellValue("TIPO CONTENEDOR");
        cE17.setCellStyle(style2);

        Cell cE18 = encabezado.createCell(17);
        cE18.setCellValue("TRANSITO");
        cE18.setCellStyle(style2);

        Cell cE19 = encabezado.createCell(18);
        cE19.setCellValue("LINEANAVIERA");
        cE19.setCellStyle(style2);

        Cell cE20 = encabezado.createCell(19);
        cE20.setCellValue("NAVIERA");
        cE20.setCellStyle(style2);

        for (ReporteConteneImportMd item : lista) {
            Row contenido = listSheet.createRow(index++);

            Cell cC1 = contenido.createCell(0);
            cC1.setCellValue(item.getBuque());
            cC1.setCellStyle(styleEntero);

            Cell cC2 = contenido.createCell(1);
            cC2.setCellValue(item.getNom_buque());
            cC2.setCellStyle(style2);

            Cell cC3 = contenido.createCell(2);
            cC3.setCellValue(item.getAnio_arribo());
            cC3.setCellStyle(style2);

            Cell cC4 = contenido.createCell(3);
            cC4.setCellValue(item.getNum_arribo());
            cC4.setCellStyle(style2);

            Cell cC5 = contenido.createCell(4);
            cC5.setCellValue(item.getDatos_import());
            cC5.setCellStyle(styleEntero);

            Cell cC6 = contenido.createCell(5);
            cC6.setCellValue(item.getDatos_export());
            cC6.setCellStyle(style2);

            Cell cC7 = contenido.createCell(6);
            cC7.setCellValue(item.getFecha_atraque());
            cC7.setCellStyle(styleEntero);

            Cell cC8 = contenido.createCell(7);
            cC8.setCellValue(item.getF_opera_inicio());
            cC8.setCellStyle(styleEntero);

            Cell cC9 = contenido.createCell(8);
            cC9.setCellValue(item.getF_opera_fin());
            cC9.setCellStyle(styleEntero);

            Cell cC10 = contenido.createCell(9);
            cC10.setCellValue(item.getContenedores());
            cC10.setCellStyle(styleEntero);

            Cell cC11 = contenido.createCell(10);
            cC11.setCellValue(item.getVacio_lleno());
            cC11.setCellStyle(styleEntero);

            Cell cC12 = contenido.createCell(11);
            cC12.setCellValue(item.getActividad());
            cC12.setCellStyle(styleEntero);

            Cell cC13 = contenido.createCell(12);
            cC13.setCellValue(item.getVia_directa());
            cC13.setCellStyle(styleEntero);

            Cell cC14 = contenido.createCell(13);
            cC14.setCellValue(item.getVia_indirecta());
            cC14.setCellStyle(styleEntero);

            Cell cC15 = contenido.createCell(14);
            cC15.setCellValue(item.getVia_indirecta());
            cC15.setCellStyle(styleEntero);

            Cell cC16 = contenido.createCell(15);
            cC16.setCellValue(item.getVia_maersk());
            cC16.setCellStyle(styleEntero);

            Cell cC17 = contenido.createCell(16);
            cC17.setCellValue(item.getTipo_contene());
            cC17.setCellStyle(styleEntero);

            Cell cC18 = contenido.createCell(17);
            cC18.setCellValue(item.getTransito());
            cC18.setCellStyle(styleEntero);

            Cell cC19 = contenido.createCell(18);
            cC19.setCellValue(item.getLinea());
            cC19.setCellStyle(styleEntero);

            Cell cC20 = contenido.createCell(19);
            cC20.setCellValue(item.getNaviera());
            cC20.setCellStyle(styleEntero);

        }

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            estilo.autoSizeColumns(workbook, 5);
            workbook.write(baos);
            AMedia amedia = new AMedia("Reporte De Contenedores Importacion.xls", "xls", "application/file", baos.toByteArray());
            Filedownload.save(amedia);
            baos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
