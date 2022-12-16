package ctrl;

import DAL.Actividad45Dal;
import MD.Actividad45Md;
import com.lowagie.text.Document;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import java.io.ByteArrayOutputStream;
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

public class Actividad45Ctrl extends GenericForwardComposer {

    private Textbox anio;
    //private Textbox arribo;
    //private Combobox actividad;
    private Textbox nom_buque;
    private Textbox naviera;
    private Textbox act;

    public void doAfterComposer(Component comp) throws Exception {
        super.doAfterCompose(comp);
    }

    public void onClick$btnDescargar(Event e) throws SQLException {
        if ((anio.getText().equals("")) || (anio.getText().isEmpty())) {
            
            Clients.showNotification("NO SE PUEDE GENERAR REPORTE LLENAR LOS CAMPOS VACIOS!  <br/> INGRESE LOS DATOS POR FAVOR PARA GENERAR EL PDF....!!!!<br/>",
                    "warning", null, "middle_center", 5000);
        } else {
            GeneraPDF(anio.getText());

        }
    }
    
    public void GeneraPDF(String anio) throws SQLException {
        Document document;
        String actividad = "";
        Paragraph ParrafoHoja = new Paragraph();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        List<Actividad45Md> alldata = new ArrayList<Actividad45Md>();
        Actividad45Dal rd = new Actividad45Dal();
        alldata = rd.REGselect(anio);

        if (alldata.isEmpty()) {
            Clients.showNotification("NO SE PUEDE GENERAR REPORTE  <br/> NO TIENE DATOS<br/>",
                    "warning", null, "middle_center", 5000);
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
            ParrafoHoja.add(new Paragraph("               Reporte de Contenedor con ACTIVIDAD NO INTRUSIVA "));
            /*  ParrafoHoja.add(new Paragraph("               Operacion.: "+activi));*/

            //System.out.println("CREAR TABLA");
            float anchosFilas[] = {0.5f, 0.5f, 0.5f, 0.5f};//Anchos de las filas
            PdfPTable tabla = new PdfPTable(anchosFilas);
            String rotulosColumnas[] = {"CONTENEDOR", "FECHA_INICIO", "ACTIVIDAD", "FECHA_ENVIO"};//Rotulos de las columnas 
            //System.out.println("SE CREARON ENCABZADOS");
            // Porcentaje que ocupa a lo ancho de la pagina del PDF
            tabla.setWidthPercentage(100);
            PdfPCell cell = new PdfPCell();

            System.out.println("CONSTRUIR TABLA");
            for (int i = 0; i < rotulosColumnas.length; i++) {
                cell = new PdfPCell(new Paragraph(rotulosColumnas[i]));
                tabla.addCell(cell);
            }

            int i = 0;
            for (Actividad45Md a : alldata) {
                i++;
                cell = new PdfPCell(new Paragraph(a.getNcontenedor()));
                tabla.addCell(cell);
                cell = new PdfPCell(new Paragraph(a.getFechas1()));
                tabla.addCell(cell);
                cell = new PdfPCell(new Paragraph(a.getNaviera()));
                tabla.addCell(cell);
                cell = new PdfPCell(new Paragraph(a.getFechas2()));
                tabla.addCell(cell);
                //System.out.println("ACTIVIDAD..: "+a.getActividad());
                //if(a.getActividad()== null){actividad = "";}
                //if(a.getActividad().equals("E")){actividad = "EXPORTACION";}
                //if (a.getActividad().equals("I")){actividad = "IMPORTACION";}
                //cell = new PdfPCell(new Paragraph(actividad));
                //tabla.addCell(cell);
                //if(i==1){
                //  nom_buque = a.getBuque();
                //}
            }
            //ParrafoHoja.add(new Paragraph("               NOMBRE BUQUE: " +nom_buque));

            ParrafoHoja.add(tabla);
            document.open();
            document.add(ParrafoHoja);
            document.close();

            AMedia amedia = new AMedia("Actividad45.PDF", "PDF", "application/file", baos.toByteArray());
            Filedownload.save(amedia);
            baos.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
