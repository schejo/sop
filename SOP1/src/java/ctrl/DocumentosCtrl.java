package ctrl;

import DAL.DocumentosDal;
import MD.DocumentosMd;
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

public class DocumentosCtrl extends GenericForwardComposer {

    private Textbox codDoc;
    private Textbox nomDoc;
    private Combobox claDoc;

    List<DocumentosMd> allDocumentos = new ArrayList<DocumentosMd>();

    private Listbox lb2;
    DocumentosDal rg = new DocumentosDal();
    private Include rootPagina;
    String codigo;
    String nombre;
    String clase;

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        allDocumentos = rg.RSelect();
        lb2.setModel(new ListModelList(allDocumentos));
        codDoc.focus();
    }

    //Selecionar nombres de una lista a campos de un combobox
    public void onSelect$lb2() throws SQLException {
        ArrayList<String> arraytemp = new ArrayList<String>();
        for (Object obj : lb2.getSelectedItem().getChildren()) {
            Listcell celda = (Listcell) obj;
            arraytemp.add(celda.getLabel());
        }
        codigo = arraytemp.get(0);
        nombre = arraytemp.get(1);
        clase = arraytemp.get(2);

        codDoc.setText(codigo);
        nomDoc.setText(nombre);
        claDoc.setText(clase);

        codDoc.setDisabled(true);
        nomDoc.setDisabled(true);
        claDoc.setDisabled(true);

    }//fin

    public void onChange$codDoc(Event e) throws SQLException {

        if (codDoc.getText().isEmpty()) {

            codDoc.setText("");
            nomDoc.setText("");
            claDoc.setText("");
        } else {
            for (DocumentosMd dt : allDocumentos) {
                if (dt.getCodigo().equals(codDoc.getText().toUpperCase())) {

                    codDoc.setText(dt.getCodigo());
                    nomDoc.setText(dt.getNombre());
                    claDoc.setText(dt.getClase());
                }
            }
        }
    }

    public void onClick$btnGuardar(Event e) throws SQLException {

        if (((codDoc.getText().equals("")) && (nomDoc.getText().equals("")) && ((claDoc.getText().equals(""))))) {

            Clients.showNotification("NO SE GUARDO EL REGISTRO <br/> DEBE LLENAR LOS CAMPOS VACIOS! <br/>",
                    "warning", null, "middle_center", 5000);
        } else {

            int op = 0;

            for (DocumentosMd dt : allDocumentos) {
                if (dt.getCodigo().equals(codDoc.getText().toUpperCase())) {
                    op++;
                }
            }

            if (op == 0) {
                rg.REGinsert(codDoc.getText().toUpperCase(), nomDoc.getText(), claDoc.getSelectedItem().getValue().toString());

                codDoc.setText("");
                nomDoc.setText("");
                claDoc.setText("");

                allDocumentos = rg.RSelect();
                lb2.setModel(new ListModelList(allDocumentos));
            } else {
                rg.REGupdate(codDoc.getText(), nomDoc.getText(), claDoc.getSelectedItem().getValue().toString());
                codDoc.setDisabled(false);
                codDoc.setText("");
                nomDoc.setText("");
                claDoc.setText("");
                codDoc.focus();

                allDocumentos = rg.RSelect();
                lb2.setModel(new ListModelList(allDocumentos));
            }
        }
    }

    public void onClick$btnNuevo(Event e) throws SQLException {
        codDoc.setText("");
        nomDoc.setText("");
        claDoc.setText("");
        nomDoc.focus();

        codDoc.setDisabled(false);
        nomDoc.setDisabled(false);
        claDoc.setDisabled(false);

    }

    public void onClick$btnSalir() {
        rootPagina.setSrc("/Views/Principal.zul");
    }
}
