package ctrl;

import DAL.ActividadesDal;
import DAL.CatalogoDal;
import MD.ActividadesMd;
import MD.CataloAcBuqueMd;
import MD.CatalogosMd;
import MD.VerActividadMd;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.EventQueues;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Include;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class ActividadesCtrl extends GenericForwardComposer {

    private Textbox anoarriboAct;
    private Textbox numarriboAct;
    private Combobox nombreAct;

    private Combobox nompracticoAct;
    private Textbox boletasAct;
    private Datebox inicioAct;
    private Datebox finAct;

    private Combobox nomremolcador1Act;
    private Textbox boletas1Act;
    private Datebox inicio1Act;
    private Datebox fin1Act;

    private Combobox nomremolcador2Act;
    private Textbox boletas2Act;
    private Datebox inicio2Act;
    private Datebox fin2Act;

    private Combobox nomremolcador3Act;
    private Textbox boletas3Act;
    private Datebox inicio3Act;
    private Datebox fin3Act;

    private Combobox nomlanchaAct;
    private Textbox boletas4Act;
    private Datebox inicio4Act;
    private Datebox fin4Act;

    private Button VerDatos;

    private Combobox nomlalmiranteAct;
    private Textbox boletas5Act;

    private Textbox observacionesAct;
    private Textbox codigofonAct;
    private Combobox nomfondeoAct;
    private Combobox estatus2Act;

    List<ActividadesMd> allatracadero = new ArrayList<ActividadesMd>();
    List<ActividadesMd> alltipoact = new ArrayList<ActividadesMd>();
    List<ActividadesMd> alltipoParticular = new ArrayList<ActividadesMd>();
    List<ActividadesMd> alltipoPractico = new ArrayList<ActividadesMd>();
    List<ActividadesMd> allLancha1Piloto = new ArrayList<ActividadesMd>();
    List<ActividadesMd> allLanchaalmirante = new ArrayList<ActividadesMd>();

    ActividadesMd manteniMD = new ActividadesMd();

    ActividadesDal rg = new ActividadesDal();

    private Include rootPagina;

    String actividadGlo = "";
    List<CataloAcBuqueMd> lista = new ArrayList<CataloAcBuqueMd>();
    CatalogoDal ctd = new CatalogoDal();

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
         lista = ctd.consulta();

        allatracadero = rg.atracaderoRSelect();

        alltipoParticular = rg.tipoParticularRSelect();
        alltipoPractico = rg.tipoPracticoRSelect();
        allLancha1Piloto = rg.LanchaPilotoRSelect();
        allLanchaalmirante = rg.LanchaalmirnateRSelect();

        nompracticoAct.setModel(new ListModelList(alltipoPractico));
        nomremolcador1Act.setModel(new ListModelList(alltipoParticular));
        nomremolcador2Act.setModel(new ListModelList(alltipoParticular));
        nomremolcador3Act.setModel(new ListModelList(alltipoParticular));
        nomlanchaAct.setModel(new ListModelList(allLancha1Piloto));
        nomlalmiranteAct.setModel(new ListModelList(allLanchaalmirante));

        anoarriboAct.focus();

        anoarriboAct.focus();
        VerDatos.setVisible(false);

        EventQueues.lookup("myEventQueue2", EventQueues.DESKTOP, true)
                .subscribe(new EventListener() {
                    public void onEvent(Event event) throws Exception {
                        List<VerActividadMd> data = new ArrayList<VerActividadMd>();
                        data.clear();
                        data = (List<VerActividadMd>) event.getData();
                        if (data.isEmpty()) {
                            anoarriboAct.setText("");

                        } else {
                            for (VerActividadMd item : data) {
                                if (data.size() == 1) {
                                    anoarriboAct.setText(item.getAnoArriBo());
                                    numarriboAct.setText(item.getNumArrib());
                                    Events.postEvent("onChange", numarriboAct, null);
                                    actividadGlo = item.getNumActiv();
                                    VerDatos.setVisible(true);

                                }
                            }
                        }
                    }
                });
        
         EventQueues.lookup("myEventSelecBuque", EventQueues.DESKTOP, true)
                .subscribe(new EventListener() {
                    public void onEvent(Event event) throws Exception {
                        List<CataloAcBuqueMd> data = new ArrayList<CataloAcBuqueMd>();
                        data.clear();
                        data = (List<CataloAcBuqueMd>) event.getData();
                        if (data.isEmpty()) {
                            anoarriboAct.setText("");
                            numarriboAct.setText("");

                        } else {
                            for (CataloAcBuqueMd item : data) {
                                if(data.size()==1){
                                anoarriboAct.setText(item.getAnoAc());
                                numarriboAct.setText(item.getNumAc());
                                 numarriboAct.setFocus(true);
                                }
                            }
                        }
                    }
                });

    }
    
      public void onClick$btnBusca1(Event e) {
        
        EventQueues.lookup("myEventQueue", EventQueues.DESKTOP, true)
                .publish(new Event("onChangeNickname", null, lista));

        //INVOCAR MODAL
        Window window = (Window) Executions.createComponents(
                "/Views/BuscaBuque.zul", null, null);
        window.doModal();
    }

    public void BuscaItemAct(String letra, Combobox cb) {
        if (letra != null) {
            for (int i = 0; i < cb.getItemCount(); i++) {
                if (letra.trim().equals(cb.getItemAtIndex(i).getValue())) {
                    cb.setSelectedIndex(i);
                    break;
                }
            }
        }

    }

    public void onClick$VerDatos(Event e) {
        BuscaItemAct(actividadGlo, this.nombreAct);
        Events.postEvent("onChange", nombreAct, null);

    }

    public void onOK$numarriboAct(Event e) throws SQLException {
        onChange$numarriboAct(e);
    }

    public void onChange$numarriboAct(Event e) throws SQLException {

        if (numarriboAct.getText().equals("")) {
            Clients.showNotification("No hay Arribo <br/>  <br/> Ingresado",
                    Clients.NOTIFICATION_TYPE_WARNING, null, "middle_center", 0);
            nombreAct.getChildren().clear();
            nombreAct.setText("");
            clear();
        } else {
            alltipoact = rg.tipoactRSelect(anoarriboAct.getText(), numarriboAct.getText());
            nombreAct.setModel(new ListModelList(alltipoact));
            clear();

        }

    }

    public void onClick$btnNuevo(Event e) throws SQLException {

        clear();

    }

    public void clear() {

//        nombreAct.setSelectedIndex(-1);
//        nombreAct.setText("");
        nompracticoAct.setSelectedIndex(-1);
        boletasAct.setText("");
        inicioAct.setText("");
        finAct.setText("");

        nomremolcador1Act.setSelectedIndex(-1);
        boletas1Act.setText("");
        inicio1Act.setText("");
        fin1Act.setText("");

        nomremolcador2Act.setSelectedIndex(-1);
        boletas2Act.setText("");
        inicio2Act.setText("");
        fin2Act.setText("");

        nomremolcador3Act.setSelectedIndex(-1);
        boletas3Act.setText("");
        inicio3Act.setText("");
        fin3Act.setText("");

        nomlanchaAct.setSelectedIndex(-1);
        boletas4Act.setText("");
        inicio4Act.setText("");
        fin4Act.setText("");

        nomlalmiranteAct.setSelectedIndex(-1);
        boletas5Act.setText("");
        observacionesAct.setText("");
        nomfondeoAct.setSelectedIndex(-1);
        estatus2Act.setText("");

        nombreAct.focus();
        VerDatos.setVisible(false);
    }

    //metodo para llamar un combobox con la informacion que se desea mostrar en pantalla
    public void BuscaItem(String letra, Combobox cb) {
        if (letra == null) {
            cb.setSelectedIndex(-1);
        } else {
//        if (letra != null) {
            for (int i = 0; i < cb.getItemCount(); i++) {
                if (letra.trim().equals(cb.getItemAtIndex(i).getLabel())) {
                    cb.setSelectedIndex(i);
                    break;
                }
            }
//        }

        }
    }

    //fin metodo
    public void onClick$nombreAct(Event e) throws SQLException {
        nombreAct.open();
    }

    public void onChange$nombreAct(Event e) throws SQLException {
        if (nombreAct.getSelectedIndex() == -1) {
            Clients.showNotification("Seleccione  <br/>  <br/> Actividad",
                    Clients.NOTIFICATION_TYPE_WARNING, null, "middle_center", 0);
            clear();

        } else {

            allatracadero = rg.REGselect(anoarriboAct.getText(), numarriboAct.getText(), nombreAct.getSelectedItem().getValue().toString());

            if (anoarriboAct.getText().isEmpty()) {

                anoarriboAct.setText("");
                numarriboAct.setText("");
                nombreAct.setText("");
                nompracticoAct.setText("");
                boletasAct.setText("");
                inicioAct.setText("");
                finAct.setText("");
                nomremolcador1Act.setText("");
                boletas1Act.setText("");
                inicio1Act.setText("");
                fin1Act.setText("");
                nomremolcador2Act.setText("");
                boletas2Act.setText("");
                inicio2Act.setText("");
                fin2Act.setText("");
                nomremolcador3Act.setText("");
                boletas3Act.setText("");
                inicio3Act.setText("");
                fin3Act.setText("");
                nomlanchaAct.setText("");
                boletas4Act.setText("");
                inicio4Act.setText("");
                fin4Act.setText("");
                nomlalmiranteAct.setText("");
                boletas5Act.setText("");
                observacionesAct.setText("");
                nomfondeoAct.setText("");

                observacionesAct.setText("");
                codigofonAct.setText("");

                estatus2Act.setText("");

            } else {
                VerDatos.setVisible(false);
                for (ActividadesMd dt : allatracadero) {

                    BuscaItem(dt.getNombre_practico(), this.nompracticoAct);
                    boletasAct.setText(dt.getBoleta());
                    inicioAct.setText(dt.getFecha_inicio1());
                    finAct.setText(dt.getFecha_fin1());

                    BuscaItem(dt.getNombre_remolcador(), this.nomremolcador1Act);
                    boletas1Act.setText(dt.getBoleta1());
                    inicio1Act.setText(dt.getFecha_inicio2());
                    fin1Act.setText(dt.getFecha_fin2());

                    BuscaItem(dt.getNom_remolcador1(), this.nomremolcador2Act);
                    boletas2Act.setText(dt.getBoleta2());
                    inicio2Act.setText(dt.getFecha_inicio3());
                    fin2Act.setText(dt.getFecha_fin3());

                    BuscaItem(dt.getNom_remolcador2(), this.nomremolcador3Act);
                    boletas3Act.setText(dt.getBoleta3());
                    inicio3Act.setText(dt.getFecha_inicio4());
                    fin3Act.setText(dt.getFecha_fin4());

                    BuscaItem(dt.getNom_lancha_piloto(), this.nomlanchaAct);
                    boletas4Act.setText(dt.getBoleta4());
                    inicio4Act.setText(dt.getFecha_inicio5());
                    fin4Act.setText(dt.getFecha_fin5());

                    BuscaItem(dt.getNombre_lancha(), this.nomlalmiranteAct);
                    boletas5Act.setText(dt.getBoleta5());

                    observacionesAct.setText(dt.getObservaciones());
                    BuscaItem(dt.getFondeo(), this.nomfondeoAct);
//                BuscaItem(dt.getEstatus_cobro(), this.estatus2Act);

                }
            }
        }
    }

    public void onClick$btnGuardar(Event e) throws SQLException {
        int op = 0;
        if (anoarriboAct.getText().trim().equals("")) {
            op = 1;
        }
        if (numarriboAct.getText().trim().equals("")) {
            op = 1;
        }
        if (nombreAct.getSelectedIndex() == -1) {
            op = 1;
        }
        if (op == 0) {
            manteniMD = new ActividadesMd();

            manteniMD.setAno_arribo(anoarriboAct.getText());
            manteniMD.setNum_arribo(numarriboAct.getText());
            manteniMD.setActividad(nombreAct.getSelectedItem().getValue());
            if (nompracticoAct.getSelectedIndex() == -1) {
                manteniMD.setCodigo_practico("");
            } else {
                manteniMD.setCodigo_practico(nompracticoAct.getSelectedItem().getValue());
            }

            manteniMD.setBoleta(boletasAct.getText().toUpperCase());
            manteniMD.setFecha_inicio1(inicioAct.getText());
            manteniMD.setFecha_fin1(finAct.getText());
            if (nomremolcador1Act.getSelectedIndex() == -1) {
                manteniMD.setCodigo_remolcador("");
            } else {
                manteniMD.setCodigo_remolcador(nomremolcador1Act.getSelectedItem().getValue());
            }

            manteniMD.setBoleta1(boletas1Act.getText());
            manteniMD.setFecha_inicio2(inicio1Act.getText());
            manteniMD.setFecha_fin2(fin1Act.getText());
            if (nomremolcador2Act.getSelectedIndex() == -1) {
                manteniMD.setCod_remolcador1("");
            } else {
                manteniMD.setCod_remolcador1(nomremolcador2Act.getSelectedItem().getValue());
            }

            manteniMD.setBoleta2(boletas2Act.getText());
            manteniMD.setFecha_inicio3(inicio2Act.getText());
            manteniMD.setFecha_fin3(fin2Act.getText());
            if (nomremolcador3Act.getSelectedIndex() == -1) {
                manteniMD.setCod_remolcador2("");
            } else {
                manteniMD.setCod_remolcador2(nomremolcador3Act.getSelectedItem().getValue());
            }

            manteniMD.setBoleta3(boletas3Act.getText());
            manteniMD.setFecha_inicio4(inicio3Act.getText());
            manteniMD.setFecha_fin4(fin3Act.getText());
            if (nomlanchaAct.getSelectedIndex() == -1) {
                manteniMD.setCod_lancha_piloto("");
            } else {
                manteniMD.setCod_lancha_piloto(nomlanchaAct.getSelectedItem().getValue());
            }

            manteniMD.setBoleta4(boletas4Act.getText());
            manteniMD.setFecha_inicio5(inicio4Act.getText());
            manteniMD.setFecha_fin5(fin4Act.getText());
            if (nomlalmiranteAct.getSelectedIndex() == -1) {
                manteniMD.setCodigo_lancha("");
            } else {
                manteniMD.setCodigo_lancha(nomlalmiranteAct.getSelectedItem().getValue());
            }

            manteniMD.setBoleta5(boletas5Act.getText());
            manteniMD.setObservaciones(observacionesAct.getText());
            if (nomfondeoAct.getSelectedIndex() == -1) {
                manteniMD.setCod_fondeo("");
            } else {
                manteniMD.setCod_fondeo(nomfondeoAct.getSelectedItem().getValue());
            }

            manteniMD = rg.updateActividad(manteniMD);

            if (manteniMD.getResp().equals("1")) {

                Clients.showNotification(manteniMD.getMsg() + "<brRegistros Guardados con Exito/>",
                        Clients.NOTIFICATION_TYPE_INFO, null, "middle_center", 3000);
                clear();
            } else {
                Clients.showNotification(manteniMD.getMsg() + "<brRegistro no Guardado revise los datos/>",
                        Clients.NOTIFICATION_TYPE_WARNING, null, "middle_center", 3000);
            }
        } else {
            Clients.showNotification("Seleccione  <br/>  <br/> Actividad <br/> <br/>Intentelo de Nuevo",
                    Clients.NOTIFICATION_TYPE_WARNING, null, "middle_center", 0);
        }
    }

    public void onClick$btnSalir() {
        rootPagina.setSrc("/Views/Principal.zul");
    }

    public void onCancel() {
        rootPagina.setSrc("/Views/Principal.zul");
    }
}
