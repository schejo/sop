package ctrl;

import DAL.NavierasDal;
import MD.NavierasMd;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Include;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Textbox;

public class NavierasCtrl extends GenericForwardComposer {

    private Textbox numNav;
    private Textbox nomNav;
    private Textbox glnNav;
    private Textbox repNav;
    private Textbox emaNav;
    private Textbox conNav;
    private Textbox telNav;
    private Textbox ageNav;
    private Textbox te2Nav;
    private Datebox ingNav;
    
    String lb;
    String nom_naviera, gln_nav, representa, email, contacto, tel_contacto,
            agente, tel_agente, ingreso;

    List<NavierasMd> allNavieras = new ArrayList<NavierasMd>();

    private Listbox lb2;
    NavierasDal rg = new NavierasDal();
    private Include rootPagina;

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        allNavieras = rg.RSelect();
        lb2.setModel(new ListModelList(allNavieras));
        numNav.focus();
    }

    //Selecionar nombres de una lista a campos de un combobox
    public void onSelect$lb2() throws SQLException {
        ArrayList<String> arraytemp = new ArrayList<String>();
        for (Object obj : lb2.getSelectedItem().getChildren()) {
            Listcell celda = (Listcell) obj;
            arraytemp.add(celda.getLabel());
        }
        lb = arraytemp.get(0);
        nom_naviera = arraytemp.get(1);
        gln_nav = arraytemp.get(2);
        representa = arraytemp.get(3);
        email = arraytemp.get(4);
        contacto = arraytemp.get(5);
        tel_contacto = arraytemp.get(6);
        agente = arraytemp.get(7);
        tel_agente = arraytemp.get(8);
        ingreso = arraytemp.get(9);

        numNav.setText(lb);
        nomNav.setText(nom_naviera);
        glnNav.setText(gln_nav);
        repNav.setText(representa);
        emaNav.setText(email);
        conNav.setText(contacto);
        telNav.setText(tel_contacto);
        ageNav.setText(agente);
        te2Nav.setText(tel_agente);
        ingNav.setText(ingreso);

        //deshabilitar campos 
        numNav.setDisabled(true);
        nomNav.setDisabled(true);
        glnNav.setDisabled(true);
        repNav.setDisabled(true);
        emaNav.setDisabled(true);
        conNav.setDisabled(true);
        telNav.setDisabled(true);
        ageNav.setDisabled(true);
        te2Nav.setDisabled(true);
        ingNav.setDisabled(true);

    }

    public void onClick$btnNuevo(Event e) throws SQLException {
        numNav.setText("");
        nomNav.setText("");
        glnNav.setText("");
        repNav.setText("");
        emaNav.setText("");
        conNav.setText("");
        telNav.setText("");
        ageNav.setText("");
        te2Nav.setText("");
        ingNav.setText("");
        numNav.focus();

        //habilitar campos
        numNav.setDisabled(false);
        nomNav.setDisabled(false);
        glnNav.setDisabled(false);
        repNav.setDisabled(false);
        emaNav.setDisabled(false);
        conNav.setDisabled(false);
        telNav.setDisabled(false);
        ageNav.setDisabled(false);
        te2Nav.setDisabled(false);
        ingNav.setDisabled(false);
    }

    public void onChange$numNav(Event e) throws SQLException {
        numNav.setDisabled(true);

        if (numNav.getText().isEmpty()) {

            numNav.setText("");
            nomNav.setText("");
            glnNav.setText("");
            repNav.setText("");
            emaNav.setText("");
            conNav.setText("");
            telNav.setText("");
            ageNav.setText("");
            te2Nav.setText("");
            ingNav.setText("");
            
        } else {
            for (NavierasMd dt : allNavieras) {
                if (dt.getNaviera1().equals(numNav.getText().toUpperCase())) {

                    numNav.setText(dt.getNaviera1());
                    nomNav.setText(dt.getNom_naviera());
                    glnNav.setText(dt.getGln_nav());
                    repNav.setText(dt.getRepresenta());
                    emaNav.setText(dt.getEmail());
                    conNav.setText(dt.getContacto());
                    telNav.setText(dt.getTel_agente());
                    ageNav.setText(dt.getAgente());
                    te2Nav.setText(dt.getTel_contacto());
                    ingNav.setText(dt.getIngreso());
                }
            }
        }
    }

    public void onClick$btnGuardar(Event e) throws SQLException {

        if (numNav.getText().isEmpty() || nomNav.getText().isEmpty() || glnNav.getText().isEmpty()
                || repNav.getText().isEmpty() || emaNav.getText().isEmpty() || conNav.getText().isEmpty()
                || telNav.getText().isEmpty() || ageNav.getText().isEmpty() || te2Nav.getText().isEmpty()
                || ingNav.getText().isEmpty()) {
            
            Clients.showNotification("NO SE GUARDO EL REGISTRO <br/> DEBE LLENAR LOS CAMPOS VACIOS! <br/>",
                    "warning", null, "middle_center", 5000);
        } else {

            int op = 0;

            for (NavierasMd dt : allNavieras) {
                if (dt.getNaviera1().equals(numNav.getText().toUpperCase())) {
                    op++;
                }
            }

            if (op == 0) {
                rg.REGinsert(numNav.getText().toUpperCase(), nomNav.getText(), glnNav.getText(), repNav.getText(), emaNav.getText(),
                        conNav.getText(), telNav.getText(), ageNav.getText(), te2Nav.getText(), ingNav.getText());

                numNav.setText("");
                nomNav.setText("");
                glnNav.setText("");
                repNav.setText("");
                emaNav.setText("");
                conNav.setText("");
                telNav.setText("");
                ageNav.setText("");
                te2Nav.setText("");
                ingNav.setText("");

                allNavieras = rg.RSelect();
                lb2.setModel(new ListModelList(allNavieras));
            } else {
                rg.REGupdate(numNav.getText().toUpperCase(), nomNav.getText(), glnNav.getText(), repNav.getText(), emaNav.getText(),
                        conNav.getText(), telNav.getText(), ageNav.getText(), te2Nav.getText(), ingNav.getText());
                numNav.setDisabled(false);
                numNav.setText("");
                nomNav.setText("");
                glnNav.setText("");
                repNav.setText("");
                emaNav.setText("");
                conNav.setText("");
                telNav.setText("");
                ageNav.setText("");
                te2Nav.setText("");
                ingNav.setText("");
                numNav.focus();
                allNavieras = rg.RSelect();
                lb2.setModel(new ListModelList(allNavieras));
            }

        }
    }

    public void onClick$btnSalir() {
        rootPagina.setSrc("/Views/Principal.zul");
    }
}
