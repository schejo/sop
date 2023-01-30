package ctrl;

import DAL.ActividadesDal;
import MD.ActividadesMd;
import MD.CatalogosMd;
import MD.VerActividadMd;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.zk.ui.Component;
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
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

public class ActividadesCtrl extends GenericForwardComposer {

    private Textbox anoarriboAct;
    private Textbox numarriboAct;
    private Textbox actividadAct;
    private Combobox nombreAct;

    private Textbox practicoAct;
    private Combobox nompracticoAct;
    private Textbox boletasAct;
    private Datebox inicioAct;
    private Datebox finAct;

    private Textbox remolcadorAct;
    private Combobox nomremolcador1Act;
    private Textbox boletas1Act;
    private Datebox inicio1Act;
    private Datebox fin1Act;

    private Textbox remolcador2Act;
    private Combobox nomremolcador2Act;
    private Textbox boletas2Act;
    private Datebox inicio2Act;
    private Datebox fin2Act;

    private Textbox remolcador3Act;
    private Combobox nomremolcador3Act;
    private Textbox boletas3Act;
    private Datebox inicio3Act;
    private Datebox fin3Act;

    private Textbox lanchaAct;
    private Combobox nomlanchaAct;
    private Textbox boletas4Act;
    private Datebox inicio4Act;
    private Datebox fin4Act;

    private Button VerDatos;


    private Textbox lalmiranteAct;
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

    ActividadesDal rg = new ActividadesDal();
    private Include rootPagina;

    String actividadGlo = "";


    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);

        allatracadero = rg.atracaderoRSelect();

        alltipoParticular = rg.tipoParticularRSelect();
        alltipoPractico = rg.tipoPracticoRSelect();
        allLancha1Piloto = rg.LanchaPilotoRSelect();
        allLanchaalmirante = rg.LanchaalmirnateRSelect();

        nompracticoAct.setModel(new ListModelList(alltipoPractico));


        alltipoParticular = rg.tipoParticularRSelect();
        alltipoPractico = rg.tipoPracticoRSelect();
        allLancha1Piloto = rg.LanchaPilotoRSelect();

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

    public void onChange$numarriboAct(Event e) throws SQLException {

        alltipoact = rg.tipoactRSelect(anoarriboAct.getText(), numarriboAct.getText());
        nombreAct.setModel(new ListModelList(alltipoact));

    }



    

    

    public void onClick$btnNuevo(Event e) throws SQLException {

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
        estatus2Act.setText("");
        
        anoarriboAct.focus();
        VerDatos.setVisible(false);
    }

    //metodo para llamar un combobox con la informacion que se desea mostrar en pantalla
    public void BuscaItem(String letra, Combobox cb) {
        if (letra != null) {
            for (int i = 0; i < cb.getItemCount(); i++) {
                if (letra.trim().equals(cb.getItemAtIndex(i).getLabel())) {
                    cb.setSelectedIndex(i);
                    break;
                }
            }
        }

    }

    //fin metodo
    public void onChange$nombreAct(Event e) throws SQLException {

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

    public void onClick$btnGuardar(Event e) throws SQLException {



                    VerDatos.setVisible(false);

        int op = 0;

        for (ActividadesMd dt : allatracadero) {
            if (dt.getAno_arribo().equals(anoarriboAct.getText())) {
                op++;
            }
        }

        if (op == 0) {
            rg.REGinsert(anoarriboAct.getText(), numarriboAct.getText(), actividadAct.getText(),
                    practicoAct.getText(), boletasAct.getText(), inicioAct.getText(), finAct.getText(),
                    remolcadorAct.getText(), boletas1Act.getText(), inicio1Act.getText(), fin1Act.getText(),
                    remolcador2Act.getText(), boletas2Act.getText(), inicio2Act.getText(), fin2Act.getText(),
                    remolcador3Act.getText(), boletas3Act.getText(), inicio3Act.getText(), fin3Act.getText(),
                    lanchaAct.getText(), boletas4Act.getText(), inicio4Act.getText(), fin4Act.getText(),
                    lalmiranteAct.getText(), boletas5Act.getText(), observacionesAct.getText(), nomfondeoAct.getText(),
                    estatus2Act.getText());

            anoarriboAct.setText("");
            numarriboAct.setText("");
            actividadAct.setText("");
            practicoAct.setText("");
            boletasAct.setText("");
            inicioAct.setText("");
            finAct.setText("");
            remolcadorAct.setText("");
            boletas1Act.setText("");
            inicio1Act.setText("");
            fin1Act.setText("");
            remolcador2Act.setText("");
            boletas2Act.setText("");
            inicio2Act.setText("");
            fin2Act.setText("");
            remolcador3Act.setText("");
            boletas3Act.setText("");
            inicio3Act.setText("");
            fin3Act.setText("");
            lanchaAct.setText("");
            boletas4Act.setText("");
            inicio4Act.setText("");
            fin4Act.setText("");
            lalmiranteAct.setText("");
            boletas5Act.setText("");
            observacionesAct.setText("");
            nomfondeoAct.setText("");
            estatus2Act.setText("");

        } else {

            rg.REGupdate(anoarriboAct.getText(), numarriboAct.getText(), nombreAct.getText(),
                    nompracticoAct.getText(), boletasAct.getText(), inicioAct.getText(), finAct.getText(),
                    nomremolcador1Act.getText(), boletas1Act.getText(), inicio1Act.getText(), fin1Act.getText(),
                    nomremolcador2Act.getText(), boletas2Act.getText(), inicio2Act.getText(), fin2Act.getText(),
                    nomremolcador3Act.getText(), boletas3Act.getText(), inicio3Act.getText(), fin3Act.getText(),
                    nomlanchaAct.getText(), boletas4Act.getText(), inicio4Act.getText(), fin4Act.getText(),
                    nomlalmiranteAct.getText(), boletas5Act.getText(), observacionesAct.getText(), nomfondeoAct.getText(), estatus2Act.getText());

            anoarriboAct.setDisabled(false);
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
            estatus2Act.setText("");
            anoarriboAct.focus();

            // allatracadero = rg.RSelect();
            //  lb2.setModel(new ListModelList(allatracadero));
        }

    }


    public void onClick$btnActualiza(Event e) throws SQLException {

        anoarriboAct.setText("");
        numarriboAct.setText("");
        actividadAct.setText("");
        nombreAct.setText("");
        practicoAct.setText("");
        nompracticoAct.setText("");
        boletasAct.setText("");
        inicioAct.setText("");
        finAct.setText("");
        remolcadorAct.setText("");
        nomremolcador1Act.setText("");
        boletas1Act.setText("");
        inicio1Act.setText("");
        fin1Act.setText("");
        remolcador2Act.setText("");
        nomremolcador2Act.setText("");
        boletas2Act.setText("");
        inicio2Act.setText("");
        fin2Act.setText("");
        remolcador3Act.setText("");
        nomremolcador3Act.setText("");
        boletas3Act.setText("");
        inicio3Act.setText("");
        fin3Act.setText("");
        lanchaAct.setText("");
        nomlanchaAct.setText("");
        boletas4Act.setText("");
        inicio4Act.setText("");
        fin4Act.setText("");
        lalmiranteAct.setText("");
        nomlalmiranteAct.setText("");
        boletas5Act.setText("");
        observacionesAct.setText("");
        codigofonAct.setText("");
        nomfondeoAct.setText("");
        estatus2Act.setText("");
        anoarriboAct.focus();
    }


    public void onClick$btnDelete(Event e) throws SQLException {
        if (!anoarriboAct.getText().equals("") && !anoarriboAct.getText().equals("")) {
            Messagebox.show("Estas seguro que Deseas Borrar este Registro?",
                    "Question", Messagebox.OK | Messagebox.CANCEL,
                    Messagebox.QUESTION,
                    new org.zkoss.zk.ui.event.EventListener() {
                public void onEvent(Event e) throws SQLException {
                    if (Messagebox.ON_OK.equals(e.getName())) {

                        rg.REGdelete(anoarriboAct.getText(), numarriboAct.getText());
                        anoarriboAct.setDisabled(false);
                        numarriboAct.setText("");
                        actividadAct.setText("");
                        nombreAct.setText("");
                        practicoAct.setText("");
                        nompracticoAct.setText("");
                        boletasAct.setText("");
                        inicioAct.setText("");
                        finAct.setText("");
                        remolcadorAct.setText("");
                        nomremolcador1Act.setText("");
                        boletas1Act.setText("");
                        inicio1Act.setText("");
                        fin1Act.setText("");
                        remolcador2Act.setText("");
                        nomremolcador2Act.setText("");
                        boletas2Act.setText("");
                        inicio2Act.setText("");
                        fin2Act.setText("");
                        remolcador3Act.setText("");
                        nomremolcador3Act.setText("");
                        boletas3Act.setText("");
                        inicio3Act.setText("");
                        fin3Act.setText("");
                        lanchaAct.setText("");
                        nomlanchaAct.setText("");
                        boletas4Act.setText("");
                        inicio4Act.setText("");
                        fin4Act.setText("");
                        lalmiranteAct.setText("");
                        nomlalmiranteAct.setText("");
                        boletas5Act.setText("");
                        observacionesAct.setText("");
                        codigofonAct.setText("");
                        nomfondeoAct.setText("");
                        estatus2Act.setText("");

                        //   allatracadero = rg.RSelect();
                        //  lb2.setModel(new ListModelList(allatracadero));
                    } else if (Messagebox.ON_CANCEL.equals(e.getName())) {
                        Clients.showNotification("REGISTRO NO SE HA  <br/> BORRADO  <br/>");
                    }
                }
            }
            );

        } else {
            Clients.showNotification("DEBE SELECCIONAR <br/> UN REGISTRO! <br/>",
                    "warning", null, "middle_center", 50);
        }
    }

    public void onClick$btnSalir() {
        rootPagina.setSrc("/Views/Principal.zul");
    }
}
