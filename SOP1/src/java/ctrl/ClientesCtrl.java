package ctrl;

import DAL.ClientesDal;
import MD.ClientesMd;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Include;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Textbox;

public class ClientesCtrl extends GenericForwardComposer {

    private Textbox codCli;
    private Textbox agrCli;
    private Textbox nitCli;
    private Textbox nomCli;
    private Textbox razCli;
    private Textbox dirCli;
    private Textbox dicCli;
    private Textbox dipCli;
    private Textbox dieCli;
    private Textbox telCli;
    private Textbox tepCli;
    private Textbox faxCli;
    private Textbox fapCli;
    private Textbox colCli;
    private Textbox co2Cli;
    private Textbox co3Cli;
    private Textbox ti1Cli;
    private Textbox ti2Cli;
    private Textbox ti3Cli;
    private Textbox ti4Cli;
    private Combobox ivaCli;
    String lb;
    String agrupacion, nit, comercial, razon, direccion, cobro, puerto, email,
            telefono, tel_puerto, fax, fax_puerto, comentario1, comentario2, comentario3,
            tipo1, tipo2, tipo3, tipo4, iva;

    List<ClientesMd> allClientes = new ArrayList<ClientesMd>();

    private Listbox lb2;
    ClientesDal rg = new ClientesDal();
    private Include rootPagina;

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        allClientes = rg.RSelect();
        lb2.setModel(new ListModelList(allClientes));
        codCli.focus();
    }

    //Selecionar nombres de una lista a campos de un combobox
    public void onSelect$lb2() throws SQLException {
        ArrayList<String> arraytemp = new ArrayList<String>();
        for (Object obj : lb2.getSelectedItem().getChildren()) {
            Listcell celda = (Listcell) obj;
            arraytemp.add(celda.getLabel());
        }
        lb = arraytemp.get(0);
        agrupacion = arraytemp.get(1);
        nit = arraytemp.get(2);
        comercial = arraytemp.get(3);
        razon = arraytemp.get(4);
        direccion = arraytemp.get(5);
        cobro = arraytemp.get(6);
        puerto = arraytemp.get(7);
        telefono = arraytemp.get(8);
        tel_puerto = arraytemp.get(9);
        fax = arraytemp.get(10);
        fax_puerto = arraytemp.get(11);
        email = arraytemp.get(12);
        comentario1 = arraytemp.get(13);
        comentario2 = arraytemp.get(14);
        comentario3 = arraytemp.get(15);
        tipo1 = arraytemp.get(16);
        tipo2 = arraytemp.get(17);
        tipo3 = arraytemp.get(18);
        tipo4 = arraytemp.get(19);
        iva = arraytemp.get(20);

        codCli.setText(lb);
        agrCli.setText(agrupacion);
        nitCli.setText(nit);
        nomCli.setText(comercial);
        razCli.setText(razon);
        dirCli.setText(direccion);
        dicCli.setText(cobro);
        dipCli.setText(puerto);
        telCli.setText(telefono);
        tepCli.setText(tel_puerto);
        faxCli.setText(fax);
        fapCli.setText(fax_puerto);
        dieCli.setText(email);
        colCli.setText(comentario1);
        co2Cli.setText(comentario2);
        co3Cli.setText(comentario3);
        ti1Cli.setText(tipo1);
        ti2Cli.setText(tipo2);
        ti3Cli.setText(tipo3);
        ti4Cli.setText(tipo4);
        ivaCli.setText(iva);
        
        codCli.setDisabled(true);
        agrCli.setDisabled(true);
        nitCli.setDisabled(true);
        nomCli.setDisabled(true);
        razCli.setDisabled(true);
        dirCli.setDisabled(true);
        dicCli.setDisabled(true);
        dipCli.setDisabled(true);
        dieCli.setDisabled(true);
        telCli.setDisabled(true);
        tepCli.setDisabled(true);
        faxCli.setDisabled(true);
        fapCli.setDisabled(true);
        colCli.setDisabled(true);
        co2Cli.setDisabled(true);
        co3Cli.setDisabled(true);
        ti1Cli.setDisabled(true);
        ti2Cli.setDisabled(true);
        ti3Cli.setDisabled(true);
        ti4Cli.setDisabled(true);
        ivaCli.setDisabled(true);

    }

    public void onChange$codCli(Event e) throws SQLException {

        if (codCli.getText().isEmpty()) {

            codCli.setText("");
            agrCli.setText("");
            nitCli.setText("");
            nomCli.setText("");
            razCli.setText("");
            dirCli.setText("");
            dicCli.setText("");
            dipCli.setText("");
            dieCli.setText("");
            telCli.setText("");
            tepCli.setText("");
            faxCli.setText("");
            fapCli.setText("");
            colCli.setText("");
            co2Cli.setText("");
            co3Cli.setText("");
            ti1Cli.setText("");
            ti2Cli.setText("");
            ti3Cli.setText("");
            ti4Cli.setText("");
            ivaCli.setText("");

        } else {
            for (ClientesMd dt : allClientes) {
                if (dt.getCodigo_cliente().equals(codCli.getText())) {

                    codCli.setText(dt.getCodigo_cliente());
                    agrCli.setText(dt.getCodigo_agrupacion());
                    nitCli.setText(dt.getNit());
                    nomCli.setText(dt.getNombre_comercial());
                    razCli.setText(dt.getRazon());
                    dirCli.setText(dt.getDireccion());
                    dicCli.setText(dt.getDireccion_cobro());
                    dipCli.setText(dt.getDireccion_puerto());
                    dieCli.setText(dt.getEmail());
                    telCli.setText(dt.getTelefonos());
                    tepCli.setText(dt.getTelefono_puerto());
                    faxCli.setText(dt.getFax());
                    fapCli.setText(dt.getFax_puerto());
                    colCli.setText(dt.getComentario_1());
                    co2Cli.setText(dt.getComentario_2());
                    co3Cli.setText(dt.getComentario_3());
                    ti1Cli.setText(dt.getTipo_1());
                    ti2Cli.setText(dt.getTipo_2());
                    ti3Cli.setText(dt.getTipo_3());
                    ti4Cli.setText(dt.getTipo_4());
                    ivaCli.setText(dt.getIva());

                }
            }
        }
    }

    public void onClick$btnGuardar(Event e) throws SQLException {

        if (codCli.getText().isEmpty() || agrCli.getText().isEmpty() || nitCli.getText().isEmpty()
                || nomCli.getText().isEmpty() || razCli.getText().isEmpty() || dirCli.getText().isEmpty()
                || dicCli.getText().isEmpty() || dipCli.getText().isEmpty() || dieCli.getText().isEmpty()
                || telCli.getText().isEmpty() || tepCli.getText().isEmpty() || faxCli.getText().isEmpty()
                || fapCli.getText().isEmpty() || colCli.getText().isEmpty() || co2Cli.getText().isEmpty()
                || co3Cli.getText().isEmpty() || ti1Cli.getText().isEmpty() || ti2Cli.getText().isEmpty()
                || ti3Cli.getText().isEmpty() || ti4Cli.getText().isEmpty() || ivaCli.getText().isEmpty()) {

            Clients.showNotification("NO SE GUARDO EL REGISTRO <br/> DEBE LLENAR LOS CAMPOS VACIOS! <br/>",
                    "warning", null, "middle_center", 5000);
        } else {

            int op = 0;

            for (ClientesMd dt : allClientes) {
                if (dt.getCodigo_cliente().equals(codCli.getText())) {
                    op++;
                }
            }

            if (op == 0) {
                rg.REGinsert(codCli.getText(), agrCli.getText(), nitCli.getText(),
                        nomCli.getText(), razCli.getText(), dirCli.getText(), dicCli.getText(),
                        dipCli.getText(), dieCli.getText(), telCli.getText(), tepCli.getText(),
                        faxCli.getText(), fapCli.getText(), colCli.getText(), co2Cli.getText(),
                        co3Cli.getText(), ti1Cli.getText(), ti2Cli.getText(), ti3Cli.getText(),
                        ti4Cli.getText(), ivaCli.getText());

                codCli.setText("");
                agrCli.setText("");
                nitCli.setText("");
                nomCli.setText("");
                razCli.setText("");
                dirCli.setText("");
                dicCli.setText("");
                dipCli.setText("");
                dieCli.setText("");
                telCli.setText("");
                tepCli.setText("");
                faxCli.setText("");
                fapCli.setText("");
                colCli.setText("");
                co2Cli.setText("");
                co3Cli.setText("");
                ti1Cli.setText("");
                ti2Cli.setText("");
                ti3Cli.setText("");
                ti4Cli.setText("");
                ivaCli.setText("");

                allClientes = rg.RSelect();
                lb2.setModel(new ListModelList(allClientes));
            } else {

                rg.REGupdate(codCli.getText(), agrCli.getText(), nitCli.getText(),
                        nomCli.getText(), razCli.getText(), dirCli.getText(), dicCli.getText(),
                        dipCli.getText(), dieCli.getText(), telCli.getText(), tepCli.getText(),
                        faxCli.getText(), fapCli.getText(), colCli.getText(), co2Cli.getText(),
                        co3Cli.getText(), ti1Cli.getText(), ti2Cli.getText(), ti3Cli.getText(),
                        ti4Cli.getText(), ivaCli.getText());
                
                agrCli.setText("");
                nitCli.setText("");
                nomCli.setText("");
                razCli.setText("");
                dirCli.setText("");
                dicCli.setText("");
                dipCli.setText("");
                dieCli.setText("");
                telCli.setText("");
                tepCli.setText("");
                faxCli.setText("");
                fapCli.setText("");
                colCli.setText("");
                co2Cli.setText("");
                co3Cli.setText("");
                ti1Cli.setText("");
                ti2Cli.setText("");
                ti3Cli.setText("");
                ti4Cli.setText("");
                ivaCli.setText("");
                codCli.focus();

                allClientes = rg.RSelect();
                lb2.setModel(new ListModelList(allClientes));
            }

        }
    }

    public void onClick$btnNuevo(Event e) throws SQLException {

        codCli.setText("");
        agrCli.setText("");
        nitCli.setText("");
        nomCli.setText("");
        razCli.setText("");
        dirCli.setText("");
        dicCli.setText("");
        dipCli.setText("");
        dieCli.setText("");
        telCli.setText("");
        tepCli.setText("");
        faxCli.setText("");
        fapCli.setText("");
        colCli.setText("");
        co2Cli.setText("");
        co3Cli.setText("");
        ti1Cli.setText("");
        ti2Cli.setText("");
        ti3Cli.setText("");
        ti4Cli.setText("");
        ivaCli.setText("");
        
        codCli.setDisabled(false);
        agrCli.setDisabled(false);
        nitCli.setDisabled(false);
        nomCli.setDisabled(false);
        razCli.setDisabled(false);
        dirCli.setDisabled(false);
        dicCli.setDisabled(false);
        dipCli.setDisabled(false);
        dieCli.setDisabled(false);
        telCli.setDisabled(false);
        tepCli.setDisabled(false);
        faxCli.setDisabled(false);
        fapCli.setDisabled(false);
        colCli.setDisabled(false);
        co2Cli.setDisabled(false);
        co3Cli.setDisabled(false);
        ti1Cli.setDisabled(false);
        ti2Cli.setDisabled(false);
        ti3Cli.setDisabled(false);
        ti4Cli.setDisabled(false);
        ivaCli.setDisabled(false);
             
        codCli.focus();
    }

    public void onClick$btnSalir() {
        rootPagina.setSrc("/Views/Principal.zul");
    }
}
