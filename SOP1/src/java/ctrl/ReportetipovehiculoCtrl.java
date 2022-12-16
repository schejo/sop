package ctrl;

import DAL.ReportetipovehiculoDal;
import MD.ReportetipovehiculoMd;
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
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Filedownload;

public class ReportetipovehiculoCtrl extends GenericForwardComposer {

    private Datebox fechaInicio;
    private Datebox fechaFin;

    public void doAfterComposer(Component comp) throws Exception {
        super.doAfterCompose(comp);
    }

    public void onClick$btnDescargar(Event e) throws SQLException, ClassNotFoundException {
        String usuario = (String) desktop.getSession().getAttribute("USER");
        
        if (!fechaInicio.getText().isEmpty())  {
            GeneraPDF(fechaInicio.getText(), fechaFin.getText());
        } else {
            Clients.showNotification("NO SE GUARDO EL REGISTRO DEBE LLENAR LOS CAMPOS VACIOS!  <br/> INGRESE UNA FECHA POR FAVOR PARA GENERAR EL PDF....!!!!<br/>",
                    "warning", null, "middle_center", 5000);
        }
    }

    public void GeneraPDF(String fechaInicio, String fechaFin) throws SQLException, ClassNotFoundException {
        System.out.println("Generar PDF");
        Document document;
        Paragraph ParrafoHoja = new Paragraph();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        List<ReportetipovehiculoMd> alldata = new ArrayList<ReportetipovehiculoMd>();
        ReportetipovehiculoDal rd = new ReportetipovehiculoDal();
        alldata = rd.REGselect(fechaInicio, fechaFin);

        Float totalprecio = Float.parseFloat("0.00");
        Float totalcantidad = Float.parseFloat("0.00");

        for (int i = 0; i < alldata.size(); i++) {
            totalprecio += Float.parseFloat(alldata.get(i).getUsados());
            totalcantidad += Float.parseFloat(alldata.get(i).getNuevos());

        }

        if (alldata.isEmpty()) {
        Clients.showNotification("NO TIENE <br/> DATOS..!!<br/>",
                    "warning", null, "middle_center", 5000);
        }
        try {

            document = new Document();
            document.setPageSize(PageSize.LETTER);
            PdfWriter.getInstance(document, baos);

            String dirImagen = desktop.getWebApp().getRealPath("bootstrap") + "/img/reportar.png";
            Image im = Image.getInstance(dirImagen);
            im.setAlignment(Image.ALIGN_RIGHT | Image.TEXTWRAP);
            im.setAbsolutePosition(25, 700);
            im.scalePercent(10);
            ParrafoHoja.add(im);

            ParrafoHoja.add(new Paragraph("              EMPRESA PORTUARIA QUETZAL"));
            ParrafoHoja.add(new Paragraph("              REPORTE DE VEHICULOS DE IMPORTACION"));
            ParrafoHoja.add(new Paragraph("              CARROS USADOS/NUEVOS"));

            System.out.println("CREAR TABLA");
            float anchosFilas[] = {0.4f, 0.4f, 0.4f, 0.4f,};//Largo de las filas
            PdfPTable tabla = new PdfPTable(anchosFilas);
            String rotulosColumnas[] = {"AÑO", "MES", "USADOS", "NUEVOS"};//Rotulos de las columnas
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
            for (ReportetipovehiculoMd a : alldata) {
                i++;
                cell = new PdfPCell(new Paragraph(a.getAnio()));
                tabla.addCell(cell);
                cell = new PdfPCell(new Paragraph(a.getMes2()));
                tabla.addCell(cell);
                cell = new PdfPCell(new Paragraph(a.getUsados()));
                tabla.addCell(cell);
                cell = new PdfPCell(new Paragraph(a.getNuevos()));
                tabla.addCell(cell);
            }
            //SUMATORIA DE COLUMNAS 
            cell = new PdfPCell(new Paragraph("TOTALES"));
            cell.setColspan(1);
            cell.setBorder(0);
            cell.setHorizontalAlignment(1);
            tabla.addCell(cell);

            cell = new PdfPCell(new Paragraph(""));
            cell.setColspan(1);
            cell.setBorder(0);
            tabla.addCell(cell);

            cell = new PdfPCell(new Paragraph(String.valueOf(totalprecio)));
            cell.setColspan(1);
            cell.setBorder(0);
            tabla.addCell(cell);

            cell = new PdfPCell(new Paragraph(String.valueOf(totalcantidad)));
            cell.setColspan(1);
            cell.setBorder(0);
            tabla.addCell(cell);

            cell = new PdfPCell(new Paragraph(""));
            cell.setColspan(1);
            cell.setBorder(0);
            tabla.addCell(cell);

            cell = new PdfPCell(new Paragraph(""));
            cell.setColspan(1);
            cell.setBorder(0);
            tabla.addCell(cell);

            //FIN DE SUMA DE COLUMNAS
            ParrafoHoja.add(tabla);
            document.open();
            document.add(ParrafoHoja);
            document.close();

            AMedia amedia = new AMedia("Tipo Vehiculo.PDF", "PDF", "application/file", baos.toByteArray());
            Filedownload.save(amedia);
            baos.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
