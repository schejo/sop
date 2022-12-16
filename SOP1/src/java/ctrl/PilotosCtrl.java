package ctrl;

import DAL.PilotosDal;
import MD.PilotosMd;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Include;
import org.zkoss.zul.Textbox;

public class PilotosCtrl extends GenericForwardComposer {

    private Textbox paiPil;
    private Textbox licPil;
    private Textbox nomPil;
    private Datebox vigPil;
    private Combobox antPil;
    private Datebox fecPil;
    private Combobox anpPil;
    private Datebox vipPil;
    int op = 0;

    List<PilotosMd> allPilotos = new ArrayList<PilotosMd>();

    // private Listbox lb2;
    PilotosDal rg = new PilotosDal();
    private Include rootPagina;

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        allPilotos = rg.RSelect();
        paiPil.focus();
    }

    public void onChange$licPil(Event e) throws SQLException {

        allPilotos = rg.REGselect(paiPil.getText().toUpperCase(), licPil.getText().toUpperCase());
        if (allPilotos.isEmpty()) {
            Clients.showNotification("NUMERO DE LICENCIA  <br/> NO ENCONTRADA<br/>",
                    "warning", null, "middle_center", 5000);

            op = 0;

        } else {
            for (PilotosMd dt : allPilotos) {
                System.out.println("<--------------  --------------->");
                System.out.println("Fecha..: " + dt.getVigencia());
                System.out.println("Vigencia Poli..: " + dt.getVigencia_poli());
                System.out.println("Vigencia Pena..: " + dt.getVigencia_pena());
                System.out.println("Fecha In..: " + dt.getFecha_inicio());
                System.out.println("Fecha Fin..: " + dt.getFecha_fin());

                paiPil.setText(dt.getPais());
                nomPil.setText(dt.getNombre());
                vigPil.setText(dt.getVigencia());
                licPil.setText(dt.getLicencia());
                antPil.setText(dt.getAntecedente_poli());
                fecPil.setText(dt.getVigencia_poli());
                anpPil.setText(dt.getAntecedente_pena());
                vipPil.setText(dt.getVigencia_pena());
                op = 1;

                paiPil.setDisabled(true);
                nomPil.setDisabled(true);
                licPil.setDisabled(true);
            }
        }
    }

    public void onClick$btnGuardar(Event e) throws SQLException {

        if (paiPil.getText().isEmpty() || nomPil.getText().isEmpty() || vigPil.getText().isEmpty()
                || licPil.getText().isEmpty() || antPil.getText().isEmpty() || fecPil.getText().isEmpty()
                || anpPil.getText().isEmpty() || vipPil.getText().isEmpty()) {
            Clients.showNotification("NO SE GUARDO EL REGISTRO <br/> DEBE LLENAR LOS CAMPOS VACIOS! <br/>",
                    "warning", null, "middle_center", 5000);
        } else if (op == 0) {
            rg.REGinsert(paiPil.getText().toUpperCase(), licPil.getText().toUpperCase(), nomPil.getText().toUpperCase().trim(),
                    vigPil.getText(), antPil.getSelectedItem().getValue().toString(),
                    fecPil.getText(), anpPil.getSelectedItem().getValue().toString(), vipPil.getText());
            // feiPil.getText(), finPil.getText()*/

            paiPil.setText("");
            licPil.setText("");
            nomPil.setText("");
            vigPil.setText("");
            licPil.setText("");
            antPil.setText("");
            fecPil.setText("");
            anpPil.setText("");
            vipPil.setText("");

        } else {
            rg.REGupdate(paiPil.getText().toUpperCase(), licPil.getText().toUpperCase(), nomPil.getText().toUpperCase(), vigPil.getText(),
                    antPil.getSelectedItem().getValue().toString(), fecPil.getText(),
                    anpPil.getSelectedItem().getValue().toString(), vipPil.getText());
            //  feiPil.getText(), finPil.getText());

            paiPil.setText("");
            licPil.setText("");
            nomPil.setText("");
            vigPil.setText("");
            licPil.setText("");
            antPil.setText("");
            fecPil.setText("");
            anpPil.setText("");
            vipPil.setText("");
            //  feiPil.setText("");
            // finPil.setText("");
            paiPil.focus();

        }
    }

    public void onClick$btnNuevo(Event e) throws SQLException {
        paiPil.setText("");
        licPil.setText("");
        nomPil.setText("");
        vigPil.setText("");
        licPil.setText("");
        antPil.setText("");
        fecPil.setText("");
        anpPil.setText("");
        vipPil.setText("");

        paiPil.setDisabled(false);
        licPil.setDisabled(false);
        nomPil.setDisabled(false);
        vigPil.setDisabled(false);
        licPil.setDisabled(false);
        antPil.setDisabled(false);
        fecPil.setDisabled(false);
        anpPil.setDisabled(false);
        vipPil.setDisabled(false);

        paiPil.focus();
    }

    public void onClick$btnSalir() {
        rootPagina.setSrc("/Views/Principal.zul");
    }
}
