package ctrl;

import DAL.MantenimientoarribosDal;
import MD.BuquesMd;
import MD.EstibadorasMd;
import MD.MantenimientoarribosMd;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.util.media.AMedia;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Include;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;

public class MantenimientoarribosCtrl extends GenericForwardComposer {

    private Textbox anoArr;
    private Textbox numArr;
    private Combobox estArr;
    private Combobox esdArr;
    private Textbox cod_buque;
    private Combobox nomBuq;
    private Textbox cod_tipo_buque;
    private Combobox nom_tipo_buque;
    private Textbox numAtr;
    private Textbox numVia;
    private Combobox antPag;
    private Datebox fecAtr;
    private Datebox fecZar;
    private Combobox tipOpe;
    private Combobox tipArr;
    private Textbox canBod;
    private Textbox horOpe;
    private Textbox pasBuq;
    private Datebox iniOpe;
    private Textbox pkiNic;
    private Combobox posArr;
    private Datebox finOpe;
    private Textbox pkfNal;
    private Combobox radOpe;
    private Textbox datImp;
    private Textbox datExp;
    private Doublebox tmiMpo;
    private Doublebox tmeXpo;
    private Textbox numEst;
    private Combobox nomEst;
    private Combobox viaArr;
    private Textbox ultPue;
    private Textbox desPue;
    private Textbox repNav;
    private Datebox fecVis;
    private Textbox obsArr;

    List<MantenimientoarribosMd> allMantenimiento = new ArrayList<MantenimientoarribosMd>();
    MantenimientoarribosMd allMantenimiento2 = new MantenimientoarribosMd();
    MantenimientoarribosMd allmostrararribos = new MantenimientoarribosMd();
    List<MantenimientoarribosMd> allMantenifecha = new ArrayList<MantenimientoarribosMd>();

    List<BuquesMd> allBuques = new ArrayList<BuquesMd>();
    List<EstibadorasMd> allEstibadoras = new ArrayList<EstibadorasMd>();
    MantenimientoarribosDal rg = new MantenimientoarribosDal();
    private Include rootPagina;
    private Listbox lb2;
    private Document doc;

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        allBuques = rg.BuquesSelect();
        nomBuq.setModel(new ListModelList(allBuques));
        allMantenimiento = rg.RSelect();
        nomEst.setModel(new ListModelList(allMantenimiento));
        anoArr.focus();

    }

    public void onClick$btnNuevo(Event e) throws SQLException {
        anoArr.setText("");
        numArr.setText("");
        estArr.setText("");
        esdArr.setText("");
        cod_buque.setText("");
        nomBuq.setText("");
        cod_tipo_buque.setText("");
        nom_tipo_buque.setText("");
        numAtr.setText("");
        numVia.setText("");
        antPag.setText("");
        fecAtr.setText("");
        fecZar.setText("");
        tipOpe.setText("");
        tipArr.setText("");
        canBod.setText("");
        horOpe.setText("");
        pasBuq.setText("");
        iniOpe.setText("");
        pkiNic.setText("");
        posArr.setText("");
        finOpe.setText("");
        pkfNal.setText("");
        radOpe.setText("");
        datImp.setText("");
        datExp.setText("");
        tmiMpo.setText("");
        tmeXpo.setText("");
        numEst.setText("");
        nomEst.setText("");
        viaArr.setText("");
        ultPue.setText("");
        desPue.setText("");
        repNav.setText("");
        fecVis.setText("");
        obsArr.setText("");
        anoArr.focus();
        anoArr.setDisabled(false);
    }

    //metodo para llamar un combobox con la informacion que se desea mostrar en pantalla
    public void BuscaItem(String letra, Combobox cb) {
        if (cb.getText().isEmpty()) {

        } else {
            for (int i = 0; i < cb.getItemCount(); i++) {
                if (letra.equals(cb.getItemAtIndex(i).getValue())) {
                    cb.setSelectedIndex(i);
                    break;
                }
            }
        }

    }
    //fin metodo

    public void onChange$numArr(Event e) throws SQLException {

        allMantenimiento2 = new MantenimientoarribosMd();
        allMantenimiento2 = rg.Mostrardatos(anoArr.getText(), numArr.getText());

        if (allMantenimiento2.getResp().equals("1")) {

            estArr.setText(allMantenimiento2.getStatus());
            BuscaItem(allMantenimiento2.getStatus(), this.estArr);
            esdArr.setText(allMantenimiento2.getEstado());
            BuscaItem(allMantenimiento2.getEstado(), this.esdArr);
            cod_buque.setText(allMantenimiento2.getCod_buque());
            nomBuq.setText(allMantenimiento2.getNom_buque());
            cod_tipo_buque.setText(allMantenimiento2.getCod_tipo_buque());
            nom_tipo_buque.setText(allMantenimiento2.getNom_tipo_buque());
            numAtr.setText(allMantenimiento2.getNum_atracadero());
            numVia.setText(allMantenimiento2.getNum_viaje());
            antPag.setText(allMantenimiento2.getAnt_pagados());
            BuscaItem(allMantenimiento2.getAnt_pagados(), this.antPag);
            fecAtr.setText(allMantenimiento2.getFecha_Atraque());
            fecZar.setText(allMantenimiento2.getFecha_zarpe());
            tipOpe.setText(allMantenimiento2.getTipo_operacion());
            BuscaItem(allMantenimiento2.getTipo_operacion(), this.tipOpe);
            tipArr.setText(allMantenimiento2.getTipo_arribo());
            BuscaItem(allMantenimiento2.getTipo_arribo(), this.tipArr);
            canBod.setText(allMantenimiento2.getCantidad_bodegas());
            horOpe.setText(allMantenimiento2.getHora_operacion());
            pasBuq.setText(allMantenimiento2.getPasajeros());
            iniOpe.setText(allMantenimiento2.getInicio_operacion());
            pkiNic.setText(allMantenimiento2.getPk_inicial());
            posArr.setText(allMantenimiento2.getPosicion_buque());
            BuscaItem(allMantenimiento2.getPosicion_buque(), this.posArr);
            finOpe.setText(allMantenimiento2.getFin_operacion());
            pkfNal.setText(allMantenimiento2.getPk_final());
            radOpe.setText(allMantenimiento2.getR_operador());
            datImp.setText(allMantenimiento2.getDatos_import());
            datExp.setText(allMantenimiento2.getDatos_export());
            tmiMpo.setText(allMantenimiento2.getTm_import());
            tmeXpo.setText(allMantenimiento2.getTm_export());
            numEst.setText(allMantenimiento2.getNum_estibadora());
            nomEst.setText(allMantenimiento2.getNom_estibadora());
            viaArr.setText(allMantenimiento2.getVia_directa());
            BuscaItem(allMantenimiento2.getVia_directa(), this.viaArr);
            ultPue.setText(allMantenimiento2.getPto_procedencia());
            desPue.setText(allMantenimiento2.getPto_destino());
            repNav.setText(allMantenimiento2.getRepresenta_naviera());
            fecVis.setText(allMantenimiento2.getFecha_visita());
            obsArr.setText(allMantenimiento2.getObservaciones());

//            anoArr.setDisabled(true);
//            numArr.setDisabled(true);
//            estArr.setDisabled(true);
//            esdArr.setDisabled(true);
//            cod_buque.setDisabled(true);
//            nomBuq.setDisabled(true);
//            cod_tipo_buque.setDisabled(true);
//            nom_tipo_buque.setDisabled(true);
//            numAtr.setDisabled(true);
//            numVia.setDisabled(true);
//            antPag.setDisabled(true);
//            fecAtr.setDisabled(true);
//            fecZar.setDisabled(true);
//            tipOpe.setDisabled(true);
//            tipArr.setDisabled(true);
//            canBod.setDisabled(true);
//            horOpe.setDisabled(true);
//            pasBuq.setDisabled(true);
//            iniOpe.setDisabled(true);
//            pkiNic.setDisabled(true);
//            posArr.setDisabled(true);
//            finOpe.setDisabled(true);
//            pkfNal.setDisabled(true);
//            radOpe.setDisabled(true);
//            datImp.setDisabled(true);
//            datExp.setDisabled(true);
//            tmiMpo.setDisabled(true);
//            tmeXpo.setDisabled(true);
//            numEst.setDisabled(true);
//            nomEst.setDisabled(true);
//            viaArr.setDisabled(true);
//            ultPue.setDisabled(true);
//            desPue.setDisabled(true);
//            repNav.setDisabled(true);
//            fecVis.setDisabled(true);
//            obsArr.setDisabled(true);
        }

    }

    public void onClick$btnDescargar1(Event e) throws SQLException, ClassNotFoundException, IOException {
        generarPDF();
    }

    public void generarPDF() throws IOException, SQLException, ClassNotFoundException {
        allMantenifecha = rg.ObtenerFecha();
        allMantenimiento2 = rg.Mostrardatos(anoArr.getText(), numArr.getText());
        allmostrararribos = rg.Mostrardatos2(anoArr.getText(), numArr.getText());
        String user = desktop.getSession().getAttribute("USUARIO").toString();

        Paragraph ParrafoHoja = new Paragraph();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ByteArrayOutputStream outputS = new ByteArrayOutputStream();

        try {
            doc = new Document();
            doc.setPageSize(PageSize.LETTER);
            PdfWriter.getInstance(doc, baos);

            String dimg = desktop.getWebApp().getRealPath("bootstrap") + "/epq.png";
            Image im = Image.getInstance(dimg);
            im.setAlignment(Image.ALIGN_RIGHT | Image.TEXTWRAP);
            im.setAbsolutePosition(25, 715);
            im.scalePercent(100);

            //Linea #1
            Chunk linea1 = new Chunk("                  Empresa Portuaria Quetzal                                                       \n"
                    + "                  Gerencia de Operaciones\n"
                    + "                  BQ 32 - BOLETA DE VISITAS AL BUQUE\n"
                    + "                                                                                              FECHA: " + allMantenifecha.get(0).getObtefechaHora() + "\n"
                    + "                                                                                              USUARIO:  " + user + "\n"
                    + "   FECHA Y HORA DE VISITA:" + allMantenimiento2.getFecha_visita() + "                                      ");

            Chunk linea2 = new Chunk("________________________________________________________________________________\n"
                    + "   BUQUE:" + allMantenimiento2.getAno_arribo() + "     " + allMantenimiento2.getNum_arribo() + "                  " + allMantenimiento2.getNom_buque() + "                   VIAJE:" + allMantenimiento2.getNum_viaje() + "  \n"
                    + "   ESLORA: " + allmostrararribos.getEslora() + "                                        IMO: " + allmostrararribos.getImo() + "                             CALL SIGN: " + allmostrararribos.getCall_sign() + " \n"
                    + "   PROCEDENCIA:" + allMantenimiento2.getPto_procedencia() + "                           PLUMAS: " + allmostrararribos.getPlumas() + "                             MANGA: " + allmostrararribos.getManga() + " \n");
            Chunk linea3 = new Chunk("________________________________________________________________________________\n"
                    + "    DESTINO:" + allMantenimiento2.getPto_destino() + "                                                      AÑO CONSTRUCCION: " + allmostrararribos.getAnoCostruccion() + "\n"
                    + "    BANDERA: " + allmostrararribos.getNom_pais() + "                                 GRUAS: " + allmostrararribos.getCant_Gruas() + "\n"
                    + "    ATRACADERO:" + allMantenimiento2.getNum_atracadero() + "                                                TON. REGISTRO BRUTO: " + allmostrararribos.getTn_bruto() + "\n"
                    + "    NO. BODEGAS:" + allMantenimiento2.getCantidad_bodegas() + "                                             TON. REGISTRO NETO: " + allmostrararribos.getTn_neto() + "\n"
                    + "    CALADO MAX: " + allmostrararribos.getCalado() + "                                 TON.REGISTRO MUERTO: " + allmostrararribos.getPeso_muerto() + "\n");
            Chunk linea4 = new Chunk("________________________________________________________________________________\n"
                    + "    NAVIARA: " + allmostrararribos.getNom_naviera() + "                                                                                                    LINEA:" + allmostrararribos.getNom_linea() + "\n"
                    + "    AGENTE NAVIERA:" + allmostrararribos.getAgente_naviera() + "                                                                      TELEFONO:" + allmostrararribos.getTelefono() + "\n"
                    + "    EMAIL: " + allmostrararribos.getEmail() + "\n");
            Chunk linea5 = new Chunk("________________________________________________________________________________\n"
                    + "    DATOS IMPORTACION:" + allMantenimiento2.getDatos_import() + "                                               TM:" + allMantenimiento2.getTm_import() + "\n"
                    + "    DATOS EXPORTACION:" + allMantenimiento2.getDatos_export() + "                                               TM:" + allMantenimiento2.getTm_export() + "\n");
            Chunk linea6 = new Chunk("________________________________________________________________________________\n"
                    + "    ATRAQUE:" + allMantenimiento2.getFecha_Atraque() + "                                                               FONDEO: \n"//buscar fecha de fondeo
                    + "    INICIO OPERACION:" + allMantenimiento2.getInicio_operacion() + "                FIN OPERACION:" + allMantenimiento2.getFin_operacion() + "\n"
                    + "\n"
                    + "    ESTIBADORA:" + allMantenimiento2.getNom_estibadora() + " \n"
                    + "    OBSERVACIONES:" + allMantenimiento2.getObservaciones() + " \n");
            Chunk linea7 = new Chunk("________________________________________________________________________________\n");
            Chunk linea8 = new Chunk("      "
                    + ""
                    + "                            ________________________________________________\n"
                    + "                                  REPRESENTANTE EMPRESA PORTUARIA QUETZAL\n");

            Paragraph pr1 = new Paragraph();
            pr1.add(linea1);
            ParrafoHoja.add(pr1);

            Paragraph pr2 = new Paragraph();
            pr2.add(linea2);
            ParrafoHoja.add(pr2);

            Paragraph pr3 = new Paragraph();
            pr3.add(linea3);
            ParrafoHoja.add(pr3);

            Paragraph pr4 = new Paragraph();
            pr4.add(linea4);
            ParrafoHoja.add(pr4);

            Paragraph pr5 = new Paragraph();
            pr5.add(linea5);
            ParrafoHoja.add(pr5);

            Paragraph pr6 = new Paragraph();
            pr6.add(linea6);
            ParrafoHoja.add(pr6);

            Paragraph pr7 = new Paragraph();
            pr7.add(linea7);
            ParrafoHoja.add(pr7);

            Paragraph pr8 = new Paragraph();
            pr8.add(linea8);
            ParrafoHoja.add(pr8);

            Phrase ph1 = new Phrase();
            ph1.add(ParrafoHoja);

//            PdfReader read = new PdfReader(baos.toByteArray());
//            int page = read.getNumberOfPages();
//            DataOutputStream outp = new DataOutputStream(outputS);
//            PdfStamper stamp = new PdfStamper(read, outp);
//
            HeaderFooter head = new HeaderFooter(ph1, false);
            head.setBorder(0);

            doc.setHeader(head);
            doc.open();
            doc.add(im);
            doc.close();
//            PdfContentByte over = null;
//            for (int i = 1; i <= page; i++) {
//                over = stamp.getOverContent(i);
//                over.add(im);
//                over.endText();
//                stamp.close();
//            }

            AMedia media = new AMedia("Reporte Visistas Buques.PDF", "pdf", "application/pdf", baos.toByteArray());
            Filedownload.save(media);
            baos.close();

        } catch (Exception e) {
            System.out.println("EXCEPTION..: " + e.getMessage());
        }

    }

    public void onClick$btnActualiza(Event e) throws SQLException {
        anoArr.setText("");
        numArr.setText("");
        estArr.setText("");
        esdArr.setText("");
        cod_buque.setText("");
        nomBuq.setText("");
        cod_tipo_buque.setText("");
        numAtr.setText("");
        numVia.setText("");
        antPag.setText("");
        fecAtr.setText("");
        fecZar.setText("");
        tipOpe.setText("");
        tipArr.setText("");
        canBod.setText("");
        horOpe.setText("");
        pkiNic.setText("");
        pasBuq.setText("");
        iniOpe.setText("");
        pkfNal.setText("");
        radOpe.setText("");
        datImp.setText("");
        datExp.setText("");
        tmiMpo.setText("");
        tmeXpo.setText("");
        numEst.setText("");
        nomEst.setText("");
        viaArr.setText("");
        ultPue.setText("");
        desPue.setText("");
        repNav.setText("");
        fecVis.setText("");
        obsArr.setText("");
        anoArr.focus();
    }

    public void onClick$btnSalir() {
        rootPagina.setSrc("/Views/Principal.zul");
    }
}
