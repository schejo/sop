/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ctrl;

import DAL.DemorasRayosXDal;
import MD.DemorasRayosXMd;
import com.lowagie.text.Document;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import java.io.ByteArrayOutputStream;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.zkoss.util.media.AMedia;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Textbox;

/**
 *
 * @author Informatica
 */
public class DemorasRayosX extends GenericForwardComposer {

    private Combobox anio;
    private Textbox arribo;

    public void doAfterComposer(Component comp) throws Exception {
        super.doAfterCompose(comp);
    }

    public void onClick$btnDescargar(Event e) throws SQLException {

        if (((anio.getText().equals("")) && (arribo.getText().equals("")))
                || ((!anio.getText().equals("")) || (arribo.getText().equals("")))
                || ((anio.getText().equals("")) || (!arribo.getText().equals("")))) {

            Clients.showNotification("NO SE PUEDE GENERAR REPORTE LLENAR LOS CAMPOS VACIOS!  <br/> INGRESE LOS DATOS POR FAVOR PARA GENERAR EL PDF....!!!!<br/>",
                    "warning", null, "middle_center", 5000);

        } else {
            GeneraPDF(anio.getText(), arribo.getText());

        }

    }

    public void GeneraPDF(String anio, String arribo) throws SQLException {
        Document document;
        String actividad = "";
        String nom_buque = "";
        Paragraph ParrafoHoja = new Paragraph();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        List<DemorasRayosXMd> alldata = new ArrayList<DemorasRayosXMd>();
        DemorasRayosXDal rd = new DemorasRayosXDal();
        alldata = rd.Demoras(anio, arribo);

        DateFormat df = new SimpleDateFormat("HH:mm:ss");
        Date hora;

        if (alldata.isEmpty()) {
            Clients.showNotification("NO TIENE DATOS..!");
        }
        try {
            document = new Document();
            document.setPageSize(PageSize.LETTER);
            PdfWriter.getInstance(document, baos);

            String dirImagen = desktop.getWebApp().getRealPath("bootstrap") + "/epq.png";
            Image im = Image.getInstance(dirImagen);
            im.setAlignment(Image.ALIGN_RIGHT | Image.TEXTWRAP);
            im.setAbsolutePosition(25, 710);
            im.scalePercent(100);
            ParrafoHoja.add(im);

            /* String activi = actividad.equals("I")?"IMPORTACION":"EXPORTACION";*/
            ParrafoHoja.add(new Paragraph("               EMPRESA PORTUARIA QUETZAL"));
            ParrafoHoja.add(new Paragraph("               Sistema de Operaciones Portuarias. SOP"));
            ParrafoHoja.add(new Paragraph("               Reporte Demoras de Rayos X (IMP/EXP) en Operacion"));
            ParrafoHoja.add(new Paragraph("               Año Arribo.: " + anio + " Numero de Arribo.: " + arribo));

            System.out.println("CREAR TABLA");
            float anchosFilas[] = {0.5f, 0.5f, 0.5f, 0.5f, 0.5f};//Anchos de las filas
            PdfPTable tabla = new PdfPTable(anchosFilas);
            String rotulosColumnas[] = {"CONTENEDOR", "ACTIVIDAD", "INICIO RAYOS X", "FIN RAYOS X", "DEMORA"};//Rotulos de las columnas 
            System.out.println("SE CREARON ENCABZADOS");
            // Porcentaje que ocupa a lo ancho de la pagina del PDF
            tabla.setWidthPercentage(100);
            PdfPCell cell = new PdfPCell();

            System.out.println("CONSTRUIR TABLA");
            for (int i = 0; i < rotulosColumnas.length; i++) {
                cell = new PdfPCell(new Paragraph(rotulosColumnas[i]));
                tabla.addCell(cell);
            }

            int i = 0;
            String fechaD = "";
            for (DemorasRayosXMd a : alldata) {
                i++;

                if (a.getActividad() == null) {
                    actividad = "";
                }
                if (a.getActividad().equals("E")) {
                    actividad = "EXPORTACION";
                }
                if (a.getActividad().equals("I")) {
                    actividad = "IMPORTACION";
                }

                cell = new PdfPCell(new Paragraph(a.getContenedor()));
                tabla.addCell(cell);
                cell = new PdfPCell(new Paragraph(actividad));
                tabla.addCell(cell);
                cell = new PdfPCell(new Paragraph(a.getInicio()));
                tabla.addCell(cell);
                cell = new PdfPCell(new Paragraph(a.getFin()));
                tabla.addCell(cell);
                cell = new PdfPCell(new Paragraph(a.getDiferencia()));
                tabla.addCell(cell);

                //hora = df.parse(a.getDiferencia());
                System.out.println("ACTIVIDAD..: " + a.getDiferencia());
//                int dias = (int) TimeUnit.SECONDS.toDays(Integer.parseInt(a.getDiferencia()));
//                long horas = TimeUnit.SECONDS.toHours(Integer.parseInt(a.getDiferencia()))-(dias*24);
//                long minutos = TimeUnit.SECONDS.toMinutes(Integer.parseInt(a.getDiferencia())) - (TimeUnit.SECONDS.toHours(Integer.parseInt(a.getDiferencia()))*60);
//                long segundos = TimeUnit.SECONDS.toSeconds(Integer.parseInt(a.getDiferencia())) - (TimeUnit.SECONDS.toMinutes(Integer.parseInt(a.getDiferencia()))*60);
//                fechaD = horas+":"+minutos+":"+segundos;
//                System.out.println("Dias..: "+dias+" Horas.: "+horas+" Minutos.: "+minutos+" Segundos.: "+segundos);

//                cell = new PdfPCell(new Paragraph(fechaD));
//                tabla.addCell(cell);
                if (i == 1) {
                    nom_buque = a.getBuque();
                }
            }
            ParrafoHoja.add(new Paragraph("               NOMBRE BUQUE: " + nom_buque));

            ParrafoHoja.add(tabla);
            document.open();
            document.add(ParrafoHoja);
            document.close();

            AMedia amedia = new AMedia("ReporteDemorasRayosX.PDF", "PDF", "application/file", baos.toByteArray());
            Filedownload.save(amedia);
            baos.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
