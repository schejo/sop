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
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.GenericForwardComposer;
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
    private Datebox fechaAct;
    private Textbox horaAct;
    private Datebox fechafinAct;
    private Textbox proaAct;
    private Textbox medioAct;
    private Textbox popaAct;
    private Textbox atracaderoAct;
    private Combobox nomterminalAct;
    private Textbox practicoAct;
    private Combobox nompracticoAct;
    private Textbox boletasAct;
    private Datebox inicioAct;
    private Datebox finAct;
    private Textbox duracionaAct;
    private Textbox remolcadorAct;
    private Combobox nomremolcador1Act;
    private Textbox boletas1Act;
    private Datebox inicio1Act;
    private Datebox fin1Act;
    private Textbox duracionbAct;
    private Textbox remolcador2Act;
    private Combobox nomremolcador2Act;
    private Textbox boletas2Act;
    private Datebox inicio2Act;
    private Datebox fin2Act;
    private Textbox duracion2Act;
    private Textbox remolcador3Act;
    private Combobox nomremolcador3Act;
    private Textbox boletas3Act;
    private Datebox inicio3Act;
    private Datebox fin3Act;
    private Textbox duracion3Act;
    private Textbox lanchaAct;
    private Combobox nomlanchaAct;
    private Textbox boletas4Act;
    private Datebox inicio4Act;
    private Datebox fin4Act;
    private Textbox duracion4Act;
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

    ActividadesDal rg = new ActividadesDal();
    private Include rootPagina;

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);

        allatracadero = rg.atracaderoRSelect();
        alltipoact = rg.tipoactRSelect();
        alltipoParticular = rg.tipoParticularRSelect();
        alltipoPractico = rg.tipoPracticoRSelect();
        allLancha1Piloto = rg.LanchaPilotoRSelect();

        nomterminalAct.setModel(new ListModelList(allatracadero));
        nombreAct.setModel(new ListModelList(alltipoact));
        nomremolcador1Act.setModel(new ListModelList(alltipoParticular));
        nomremolcador2Act.setModel(new ListModelList(alltipoParticular));
        nomremolcador3Act.setModel(new ListModelList(alltipoParticular));
        nompracticoAct.setModel(new ListModelList(alltipoPractico));
        nomlanchaAct.setModel(new ListModelList(allLancha1Piloto));

        anoarriboAct.focus();
        
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
                                    

                                }
                            }
                        }
                    }
                });

    }

    public void onClick$btnNuevo(Event e) throws SQLException {

        anoarriboAct.setText("");
        numarriboAct.setText("");
        actividadAct.setText("");
        nombreAct.setText("");
        fechaAct.setText("");
        horaAct.setText("");
        fechafinAct.setText("");
        proaAct.setText("");
        medioAct.setText("");
        popaAct.setText("");
        atracaderoAct.setText("");
        nomterminalAct.setText("");
        practicoAct.setText("");
        nompracticoAct.setText("");
        boletasAct.setText("");
        inicioAct.setText("");
        finAct.setText("");
        duracionaAct.setText("");
        remolcadorAct.setText("");
        nomremolcador1Act.setText("");
        boletas1Act.setText("");
        inicio1Act.setText("");
        fin1Act.setText("");
        duracionbAct.setText("");
        remolcador2Act.setText("");
        nomremolcador2Act.setText("");
        boletas2Act.setText("");
        inicio2Act.setText("");
        fin2Act.setText("");
        duracion2Act.setText("");
        remolcador3Act.setText("");
        nomremolcador3Act.setText("");
        boletas3Act.setText("");
        inicio3Act.setText("");
        fin3Act.setText("");
        duracion3Act.setText("");
        lanchaAct.setText("");
        nomlanchaAct.setText("");
        boletas4Act.setText("");
        inicio4Act.setText("");
        fin4Act.setText("");
        duracion4Act.setText("");
        lalmiranteAct.setText("");
        nomlalmiranteAct.setText("");
        boletas5Act.setText("");
        observacionesAct.setText("");
        codigofonAct.setText("");
        nomfondeoAct.setText("");
        estatus2Act.setText("");
        anoarriboAct.focus();
    }

    //metodo para llamar un combobox con la informacion que se desea mostrar en pantalla
    public void BuscaItem(String letra, Combobox cb) {
        for (int i = 0; i < cb.getItemCount(); i++) {
            if (letra.trim().equals(cb.getItemAtIndex(i).getLabel())) {
                cb.setSelectedIndex(i);
                break;
            }
        }
    }

    //fin metodo
    public void onChange$nombreAct(Event e) throws SQLException {

        allatracadero = rg.REGselect(anoarriboAct.getText(), numarriboAct.getText(), nombreAct.getSelectedItem().getValue().toString());

        if (anoarriboAct.getText().isEmpty()) {
            anoarriboAct.setText("");
            numarriboAct.setText("");
            actividadAct.setText("");

            nombreAct.setText("");
            fechaAct.setText("");
            horaAct.setText("");
            fechafinAct.setText("");
            proaAct.setText("");
            medioAct.setText("");
            popaAct.setText("");
            atracaderoAct.setText("");
            nomterminalAct.setText("");
            practicoAct.setText("");
            nompracticoAct.setText("");
            boletasAct.setText("");
            inicioAct.setText("");
            finAct.setText("");
            duracionaAct.setText("");
            remolcadorAct.setText("");
            nomremolcador1Act.setText("");
            boletas1Act.setText("");
            inicio1Act.setText("");
            fin1Act.setText("");
            duracionbAct.setText("");
            remolcador2Act.setText("");
            nomremolcador2Act.setText("");
            boletas2Act.setText("");
            inicio2Act.setText("");
            fin2Act.setText("");
            duracion2Act.setText("");
            remolcador3Act.setText("");
            nomremolcador3Act.setText("");
            boletas3Act.setText("");
            inicio3Act.setText("");
            fin3Act.setText("");
            duracion3Act.setText("");
            lanchaAct.setText("");
            nomlanchaAct.setText("");
            boletas4Act.setText("");
            inicio4Act.setText("");
            fin4Act.setText("");
            duracion4Act.setText("");
            lalmiranteAct.setText("");
            nomlalmiranteAct.setText("");
            boletas5Act.setText("");
            observacionesAct.setText("");
            codigofonAct.setText("");
            estatus2Act.setText("");

        } else {
            for (ActividadesMd dt : allatracadero) {
                
                fechaAct.setText(dt.getFecha());
                horaAct.setText(dt.getHora());
                fechafinAct.setText(dt.getFecha_fin_act());
                proaAct.setText(dt.getCalado_proa());
                medioAct.setText(dt.getCalado_medio());
                popaAct.setText(dt.getCalado_popa());
                BuscaItem(dt.getNom_atracadero(), this.nomterminalAct);
                
                BuscaItem(dt.getNombre_practico(), this.nompracticoAct);
                boletasAct.setText(dt.getBoleta());
                inicioAct.setText(dt.getFecha_inicio1());
                finAct.setText(dt.getFecha_fin1());
                duracionbAct.setText(dt.getDuracion1());
                
                BuscaItem(dt.getNom_remolcador1(), this.nomremolcador1Act);
                boletas1Act.setText(dt.getBoleta2());
                inicio1Act.setText(dt.getFecha_inicio2());
                
                BuscaItem(dt.getNom_remolcador2(), this.nomremolcador2Act);

//                    duracionaAct.setText(dt.getDuracion2());
//                    fin1Act.setText(dt.getFecha_fin2());
//                    
//                    boletas2Act.setText(dt.getBoleta3());
//                    inicio2Act.setText(dt.getDuracion3());
//                    fin2Act.setText(dt.getFecha_fin3());
//                    duracion2Act.setText(dt.getDuracion3());
//                    BuscaItem(dt.getCod_remolcador4(), this.nomremolcador3Act);
//                    boletas3Act.setText(dt.getBoleta4());
//                    inicio3Act.setText(dt.getFecha_inicio4());
//                    fin3Act.setText(dt.getFecha_fin4());
//                    duracion3Act.setText(dt.getDuracion4());
//                    BuscaItem(dt.getNom_lancha_piloto(), this.nomlanchaAct);
//                    boletas4Act.setText(dt.getBoleta5());
//                    inicio4Act.setText(dt.getFecha_inicio5());
//                    fin4Act.setText(dt.getFecha_fin5());
//                    duracion4Act.setText(dt.getDuracion5());
//                    BuscaItem(dt.getCod_lancha_almirante(), this.nomterminalAct);
//                    observacionesAct.setText(dt.getObservaciones());
//                    BuscaItem(dt.getCod_fondeo(), this.nomfondeoAct);
//                    BuscaItem(dt.getEstatus_cobro(), this.estatus2Act);
                //}
            }
        }
    }

    public void onClick$btnGuardar(Event e) throws SQLException {

        int op = 0;

        for (ActividadesMd dt : allatracadero) {
            if (dt.getAno_arribo().equals(anoarriboAct.getText())) {
                op++;
            }
        }

        if (op == 0) {
            rg.REGinsert(anoarriboAct.getText(), numarriboAct.getText(), actividadAct.getText(), fechaAct.getText(), horaAct.getText(),
                    fechafinAct.getText(), proaAct.getText(), medioAct.getText(), popaAct.getText(), atracaderoAct.getText(),
                    practicoAct.getText(), boletasAct.getText(), inicioAct.getText(), finAct.getText(), duracionaAct.getText(),
                    remolcadorAct.getText(), boletas1Act.getText(), inicio1Act.getText(), fin1Act.getText(), duracionbAct.getText(),
                    remolcador2Act.getText(), boletas2Act.getText(), inicio2Act.getText(), fin2Act.getText(), duracion2Act.getText(),
                    remolcador3Act.getText(), boletas3Act.getText(), inicio3Act.getText(), fin3Act.getText(), duracion3Act.getText(),
                    lanchaAct.getText(), boletas4Act.getText(), inicio4Act.getText(), fin4Act.getText(), duracion4Act.getText(),
                    lalmiranteAct.getText(), boletas5Act.getText(), observacionesAct.getText(), nomfondeoAct.getText(),
                    estatus2Act.getText());

            anoarriboAct.setText("");
            numarriboAct.setText("");
            actividadAct.setText("");
            fechaAct.setText("");
            horaAct.setText("");
            fechafinAct.setText("");
            proaAct.setText("");
            medioAct.setText("");
            popaAct.setText("");
            atracaderoAct.setText("");
            practicoAct.setText("");
            boletasAct.setText("");
            inicioAct.setText("");
            finAct.setText("");
            duracionaAct.setText("");
            remolcadorAct.setText("");
            boletas1Act.setText("");
            inicio1Act.setText("");
            fin1Act.setText("");
            duracionbAct.setText("");
            remolcador2Act.setText("");
            boletas2Act.setText("");
            inicio2Act.setText("");
            fin2Act.setText("");
            duracion2Act.setText("");
            remolcador3Act.setText("");
            boletas3Act.setText("");
            inicio3Act.setText("");
            fin3Act.setText("");
            duracion3Act.setText("");
            lanchaAct.setText("");
            boletas4Act.setText("");
            inicio4Act.setText("");
            fin4Act.setText("");
            duracion4Act.setText("");
            lalmiranteAct.setText("");
            boletas5Act.setText("");
            observacionesAct.setText("");
            nomfondeoAct.setText("");
            estatus2Act.setText("");

            //   allatracadero = rg.RSelect();
            //  lb2.setModel(new ListModelList(allatracadero));
        } else {

            rg.REGupdate(anoarriboAct.getText(), numarriboAct.getText(), nombreAct.getText(), fechaAct.getText(), horaAct.getText(),
                    fechafinAct.getText(), proaAct.getText(), medioAct.getText(), popaAct.getText(), nomterminalAct.getText(),
                    nompracticoAct.getText(), boletasAct.getText(), inicioAct.getText(), finAct.getText(), duracionaAct.getText(),
                    nomremolcador1Act.getText(), boletas1Act.getText(), inicio1Act.getText(), fin1Act.getText(), duracionbAct.getText(),
                    nomremolcador2Act.getText(), boletas2Act.getText(), inicio2Act.getText(), fin2Act.getText(), duracion2Act.getText(),
                    nomremolcador3Act.getText(), boletas3Act.getText(), inicio3Act.getText(), fin3Act.getText(), duracion3Act.getText(),
                    nomlanchaAct.getText(), boletas4Act.getText(), inicio4Act.getText(), fin4Act.getText(), duracion4Act.getText(),
                    nomlalmiranteAct.getText(), boletas5Act.getText(), observacionesAct.getText(), nomfondeoAct.getText(), estatus2Act.getText());

            anoarriboAct.setDisabled(false);
            anoarriboAct.setText("");
            numarriboAct.setText("");
            nombreAct.setText("");
            fechaAct.setText("");
            horaAct.setText("");
            fechafinAct.setText("");
            proaAct.setText("");
            medioAct.setText("");
            popaAct.setText("");
            nomterminalAct.setText("");
            nompracticoAct.setText("");
            boletasAct.setText("");
            inicioAct.setText("");
            finAct.setText("");
            duracionaAct.setText("");
            nomremolcador1Act.setText("");
            boletas1Act.setText("");
            inicio1Act.setText("");
            fin1Act.setText("");
            duracionbAct.setText("");
            nomremolcador2Act.setText("");
            boletas2Act.setText("");
            inicio2Act.setText("");
            fin2Act.setText("");
            duracion2Act.setText("");
            nomremolcador3Act.setText("");
            boletas3Act.setText("");
            inicio3Act.setText("");
            fin3Act.setText("");
            duracion3Act.setText("");
            nomlanchaAct.setText("");
            boletas4Act.setText("");
            inicio4Act.setText("");
            fin4Act.setText("");
            duracion4Act.setText("");
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
        fechaAct.setText("");
        horaAct.setText("");
        fechafinAct.setText("");
        proaAct.setText("");
        medioAct.setText("");
        popaAct.setText("");
        atracaderoAct.setText("");
        nomterminalAct.setText("");
        practicoAct.setText("");
        nompracticoAct.setText("");
        boletasAct.setText("");
        inicioAct.setText("");
        finAct.setText("");
        duracionaAct.setText("");
        remolcadorAct.setText("");
        nomremolcador1Act.setText("");
        boletas1Act.setText("");
        inicio1Act.setText("");
        fin1Act.setText("");
        duracionbAct.setText("");
        remolcador2Act.setText("");
        nomremolcador2Act.setText("");
        boletas2Act.setText("");
        inicio2Act.setText("");
        fin2Act.setText("");
        duracion2Act.setText("");
        remolcador3Act.setText("");
        nomremolcador3Act.setText("");
        boletas3Act.setText("");
        inicio3Act.setText("");
        fin3Act.setText("");
        duracion3Act.setText("");
        lanchaAct.setText("");
        nomlanchaAct.setText("");
        boletas4Act.setText("");
        inicio4Act.setText("");
        fin4Act.setText("");
        duracion4Act.setText("");
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
                        fechaAct.setText("");
                        horaAct.setText("");
                        fechafinAct.setText("");
                        proaAct.setText("");
                        medioAct.setText("");
                        popaAct.setText("");
                        atracaderoAct.setText("");
                        nomterminalAct.setText("");
                        practicoAct.setText("");
                        nompracticoAct.setText("");
                        boletasAct.setText("");
                        inicioAct.setText("");
                        finAct.setText("");
                        duracionaAct.setText("");
                        remolcadorAct.setText("");
                        nomremolcador1Act.setText("");
                        boletas1Act.setText("");
                        inicio1Act.setText("");
                        fin1Act.setText("");
                        duracionbAct.setText("");
                        remolcador2Act.setText("");
                        nomremolcador2Act.setText("");
                        boletas2Act.setText("");
                        inicio2Act.setText("");
                        fin2Act.setText("");
                        duracion2Act.setText("");
                        remolcador3Act.setText("");
                        nomremolcador3Act.setText("");
                        boletas3Act.setText("");
                        inicio3Act.setText("");
                        fin3Act.setText("");
                        duracion3Act.setText("");
                        lanchaAct.setText("");
                        nomlanchaAct.setText("");
                        boletas4Act.setText("");
                        inicio4Act.setText("");
                        fin4Act.setText("");
                        duracion4Act.setText("");
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
