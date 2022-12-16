package ctrl;

import DAL.ProductosImportDal;
import MD.ProductosImportMd;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Include;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

public class ProductosImportCtrl extends GenericForwardComposer {

    private Textbox codigoProd;
    private Textbox nombreProd;
    String lb;

    List<ProductosImportMd> allProductosImport = new ArrayList<ProductosImportMd>();

    private Listbox lb2;
    ProductosImportDal rg = new ProductosImportDal();
    private Include rootPagina;

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        allProductosImport = rg.RSelect();
        lb2.setModel(new ListModelList(allProductosImport));
        codigoProd.focus();
    }

    //inicio
    public void onSelect$lb2() throws SQLException {
        lb = lb2.getSelectedItem().getLabel();
        System.out.println("alias: " + lb);
        allProductosImport = rg.REGselect(lb);
        if (allProductosImport.isEmpty()) {
            Messagebox.show("Esta vacio", "Warning", Messagebox.OK, Messagebox.EXCLAMATION);
        } else {
            for (ProductosImportMd dt : allProductosImport) {

                codigoProd.setText(dt.getCodigo());
                nombreProd.setText(dt.getNombre());

                codigoProd.setDisabled(true);
                nombreProd.setDisabled(true);
            }
        }

    }//fin 

    public void onChange$codigoProd(Event e) throws SQLException {
        if (codigoProd.getText().isEmpty()) {

            codigoProd.setText("");
            nombreProd.setText("");
        } else {
            for (ProductosImportMd dt : allProductosImport) {
                if (dt.getCodigo().equals(codigoProd.getText())) {

                    codigoProd.setText(dt.getCodigo());
                    nombreProd.setText(dt.getNombre());

                }
            }
        }
    }

    public void onClick$btnGuardar(Event e) throws SQLException {

        if (((codigoProd.getText().equals("")) && (nombreProd.getText().equals("")))
                || ((!codigoProd.getText().equals("")) || (nombreProd.getText().equals("")))
                || ((codigoProd.getText().equals("")) || (!nombreProd.getText().equals("")))) {

            Clients.showNotification("NO SE GUARDO EL REGISTRO <br/> DEBE LLENAR LOS CAMPOS VACIOS! <br/>",
                    "warning", null, "middle_center", 5000);
        } else {

            int op = 0;

            for (ProductosImportMd dt : allProductosImport) {
                if (dt.getCodigo().equals(codigoProd.getText())) {
                    op++;
                }
            }

            if (op == 0) {
                rg.REGinsert(codigoProd.getText(), nombreProd.getText());

                codigoProd.setText("");
                nombreProd.setText("");

                allProductosImport = rg.RSelect();
                lb2.setModel(new ListModelList(allProductosImport));
            } else {
                rg.REGupdate(codigoProd.getText(), nombreProd.getText());

                nombreProd.setText("");
                codigoProd.focus();

                allProductosImport = rg.RSelect();
                lb2.setModel(new ListModelList(allProductosImport));
            }
        }
    }

    public void onClick$btnNuevo(Event e) throws SQLException {
        codigoProd.setText("");
        nombreProd.setText("");

        codigoProd.setDisabled(false);
        nombreProd.setDisabled(false);
        
        nombreProd.focus();
    }

    public void onClick$btnSalir() {
        rootPagina.setSrc("/Views/Principal.zul");
    }

}
