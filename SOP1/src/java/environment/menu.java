package environment;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Include;
import org.zkoss.zul.Label;
import org.zkoss.zul.Menu;

public class menu extends GenericForwardComposer {

    @Wire
    private Include rootPagina;
    private Label lblUser;
    private Menu menuInformatica;

    @SuppressWarnings("unchecked")
    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        String User = getUsuario();
        System.out.println("Session.: " + User);
        lblUser.setValue(User);
        rootPagina.setSrc("/Views/Principal.zul");

        Permisos(User);

    }

    public void Permisos(String permiso) {
        if (permiso.equals("federicoc1672")) {
            menuInformatica.setVisible(true);

        } else {
            menuInformatica.setVisible(false);

        }
    }

    //LINK MENUS
    public void onClick$menuHome(Event evt) {
        rootPagina.setSrc("/Views/Principal.zul");
    }

    //PROCESOS
    public void onClick$ItemYG(Event evt) {
        rootPagina.setSrc("/Views/CitasPlanificacion.zul");
    }

    public void onClick$ItemRH(Event evt) {
        rootPagina.setSrc("/Views/Actividades.zul");
    }

    //CATALOGOS
    public void onClick$ItemAA(Event evt) {
        rootPagina.setSrc("/Views/ManteActivi.zul");
    }

    public void onClick$ItemAAB(Event evt) {
        rootPagina.setSrc("/Views/Mantenimientoarribos.zul");
    }

    public void onClick$ItemRG(Event evt) {
        rootPagina.setSrc("/Views/RendimientosGraneles.zul");
    }
    public void onClick$ItemRDN(Event evt) {
        rootPagina.setSrc("/Views/ReporteDesPorNaviera.zul");
    }

    public void onClick$ItemAAD(Event evt) {
        rootPagina.setSrc("/Views/MantenimientoBuques.zul");
    }

    public void onClick$ItemServ(Event evt) {
        rootPagina.setSrc("/Views/ManteServicios.zul");
    }

    public void onClick$ItemB(Event evt) {
        rootPagina.setSrc("/Views/Regiones.zul");
    }

    public void onClick$ItemBA(Event evt) {
        rootPagina.setSrc("/Views/Paises.zul");
    }

    public void onClick$ItemBB(Event evt) {
        rootPagina.setSrc("/Views/Lineas.zul");
    }

    public void onClick$ItemBC(Event evt) {
        rootPagina.setSrc("/Views/Navieras.zul");
    }

    public void onClick$ItemBD(Event evt) {
        rootPagina.setSrc("/Views/Puertos.zul");
    }

    public void onClick$ItemBE(Event evt) {
        rootPagina.setSrc("/Views/Atracaderos.zul");
    }

    public void onClick$ItemBF(Event evt) {
        rootPagina.setSrc("/Views/Arribos.zul");
    }

    public void onClick$ItemBG(Event evt) {
        rootPagina.setSrc("/Views/Equipos.zul");
    }

    public void onClick$ItemBH(Event evt) {
        rootPagina.setSrc("/Views/Bultos.zul");
    }

    public void onClick$ItemBI(Event evt) {
        rootPagina.setSrc("/Views/Documentos.zul");
    }

    public void onClick$ItemBJ(Event evt) {
        rootPagina.setSrc("/Views/Contenedores.zul");
    }

    public void onClick$ItemBk(Event evt) {
        rootPagina.setSrc("/Views/ActividadesBuque.zul");
    }

    public void onClick$ItemBL(Event evt) {
        rootPagina.setSrc("/Views/AreasTrabajo.zul");
    }

    public void onClick$ItemBN(Event evt) {
        rootPagina.setSrc("/Views/Personal.zul");
    }

    public void onClick$ItemBO(Event evt) {
        rootPagina.setSrc("/Views/Particulares.zul");
    }

    public void onClick$ItemBP(Event evt) {
        rootPagina.setSrc("/Views/Tipoparticulares.zul");
    }

    public void onClick$ItemBQ(Event evt) {
        rootPagina.setSrc("/Views/Clientes.zul");
    }

    public void onClick$ItemBR(Event evt) {
        rootPagina.setSrc("/Views/Pilotos.zul");
    }

    public void onClick$ItemBS(Event evt) {
        rootPagina.setSrc("/Views/Transportistas.zul");
    }

    public void onClick$ItemBT(Event evt) {
        rootPagina.setSrc("/Views/TipoCarga.zul");
    }

    public void onClick$ItemBU(Event evt) {
        rootPagina.setSrc("/Views/ProductosImport.zul");
    }

    //REPORTES
    public void onClick$ItemC(Event evt) {
        rootPagina.setSrc("/Views/ReporteArribos.zul");
    }

    public void onClick$ItemCA(Event evt) {
        rootPagina.setSrc("/Views/ReporteContenedorImport.zul");
    }

    /*  public void onClick$ItemCB(Event evt) {
        rootPagina.setSrc("/Views/ReportePesajesBasculas.zul");
    }*/
    public void onClick$ItemCC(Event evt) {
        rootPagina.setSrc("/Views/ReporteServicios.zul");
    }

    public void onClick$ItemCD(Event evt) {
        rootPagina.setSrc("/Views/ReporteActividadesyBuques.zul");
    }

    public void onClick$ItemCE(Event evt) {
        rootPagina.setSrc("/Views/ReporteManifiestos.zul");
    }

    public void onClick$ItemCF(Event evt) {
        rootPagina.setSrc("/Views/ReporteEncabezaBl.zul");
    }

    public void onClick$ItemCG(Event evt) {
        rootPagina.setSrc("/Views/reportebuque.zul");
    }

    public void onClick$ItemCI(Event evt) {
        rootPagina.setSrc("/Views/Reportetipovehiculo.zul");
    }

    public void onClick$ItemCJ(Event evt) {
        rootPagina.setSrc("/Views/Reporteactiviconte.zul");
    }

    public void onClick$ItemCK(Event evt) {
        rootPagina.setSrc("/Views/ReporteGeneralPlani.zul");
    }

    //GRAFICAS
    public void onClick$ItemD(Event evt) {
        rootPagina.setSrc("/Views/GraficaBuques1.zul");
    }

    public void onClick$ItemDA(Event evt) {
        rootPagina.setSrc("/Views/GraficaTerminales.zul");
    }

    public void onClick$ItemDB(Event evt) {
        rootPagina.setSrc("/Views/GraficaCalados.zul");
    }

    public void onClick$ItemDC(Event evt) {
        rootPagina.setSrc("/Views/GraficaToneladasMuelle.zul");
    }

    public void onClick$ItemDD(Event evt) {
        rootPagina.setSrc("/Views/GraficaContenelinea.zul");
    }

    public void onClick$ItemDE(Event evt) {
        rootPagina.setSrc("/Views/GraficaContenelineaexp.zul");
    }

    public void onClick$ItemDF(Event evt) {
        rootPagina.setSrc("/Views/GraficaRampas.zul");
    }

    public void onClick$ItemDG(Event evt) {
        rootPagina.setSrc("/Views/GraficaContenerefri.zul");
    }

    public void onClick$ItemDH(Event evt) {
        rootPagina.setSrc("/Views/GraficaContenevia.zul");
    }

    public void onClick$ItemDI(Event evt) {
        rootPagina.setSrc("/Views/GraficaContenetransito.zul");
    }

    public void onClick$ItemDJ(Event evt) {
        rootPagina.setSrc("/Views/GraficaConteneimportvacio.zul");
    }

    public void onClick$ItemDK(Event evt) {
        rootPagina.setSrc("/Views/GraficaConteneimportlleno.zul");
    }

    public void onClick$ItemDL(Event evt) {
        rootPagina.setSrc("/Views/GraficaConteneimportbuque.zul");
    }

    public void onClick$ItemDM(Event evt) {
        rootPagina.setSrc("/Views/Graficavehiculo.zul");
    }

    public void onClick$ItemDN(Event evt) {
        rootPagina.setSrc("/Views/Graficaactivcontene.zul");
    }

    //CONTENEDORES
    public void onClick$ItemE(Event evt) {
        rootPagina.setSrc("/Views/ReporteRayosX.zul");
    }

    public void onClick$ItemEA(Event evt) {
        rootPagina.setSrc("/Views/Graficarayosx.zul");
    }

    public void onClick$ItemEB(Event evt) {
        rootPagina.setSrc("/Views/GraficaContenedoresactividades.zul");
    }

    public void onClick$ItemEC(Event evt) {
        rootPagina.setSrc("/Views/GraficaContenedoresExport.zul");
    }

    public void onClick$ItemED(Event evt) {
        rootPagina.setSrc("/Views/GraficaConteneRefrimes.zul");
    }

    public void onClick$ItemEE(Event evt) {
        rootPagina.setSrc("/Views/DemorasRayosX.zul");
    }

    public void onClick$ItemEF(Event evt) {
        rootPagina.setSrc("/Views/Actividad45.zul");
    }

    public void onClick$ItemEG(Event evt) {
        rootPagina.setSrc("/Views/ConfirmaPatios.zul");
    }

    public void onClick$ItemEH(Event evt) {
        rootPagina.setSrc("/Views/ConfirmaPatiosG.zul");
    }

    public void onClick$ItemEI(Event evt) {
        rootPagina.setSrc("/Views/ConfirmaMuelle.zul");
    }

    //ESTADISTICAS
    public void onClick$ItemZ(Event evt) {
        rootPagina.setSrc("/Views/ReporteConteneImport.zul");
    }

    public void onClick$ItemZ1(Event evt) {
        rootPagina.setSrc("/Views/ReporteBuquesFecha.zul");
    }

    //SERVICIOS 
    public void onClick$ItemAZ(Event evt) {
        rootPagina.setSrc("/Views/ReporteServiciosParticulares.zul");
    }

    public void onClick$ItemAZ2(Event evt) {
        rootPagina.setSrc("/Views/ReporteServiciosParticulares2.zul");
    }

    public void onClick$ItemAZ3(Event evt) {
        rootPagina.setSrc("/Views/ResumenReporteConte.zul");
    }

    public void onClick$ItemAZ4(Event evt) {
        rootPagina.setSrc("/Views/ResumenReporteConteneFecha.zul");
    }

    
    //INFORMATICA
    public void onClick$Itempesaje(Event evt) {
        rootPagina.setSrc("/Views/manCicloCamion.zul");
    }

    public void onClick$ItemCiclo(Event evt) {
        rootPagina.setSrc("/Views/cicloCamion.zul");
    }

    public void onClick$ItemCont(Event evt) {
        rootPagina.setSrc("/Views/MantenimientoContenedores1.zul");
    }

    public void onClick$ItemACont(Event evt) {
        rootPagina.setSrc("/Views/MantenimientoActiviConte.zul");
    }

    public void onClick$ItemPlani(Event evt) {
        rootPagina.setSrc("/Views/MantenimientoPlanificacion.zul");
    }

    public void onClick$ItemR1(Event evt) {
        rootPagina.setSrc("/Views/CamionParq.zul");
    }

    // CERRAR SESSION EN ZK FRAMEWORK
    public void onClick$ItemSalir(Event evt) {
        desktop.getSession().invalidate();
        execution.sendRedirect("/login.zul");
    }

    //VALIDAR SESSION EN ZK FRAMEWORK
    public void salir() {
        desktop.getSession().invalidate();
        execution.sendRedirect("login.zul");
    }

    public String getUsuario() {
        System.out.println("Session getUsuario.: " + desktop.getSession().getAttribute("USUARIO"));
        if (desktop.getSession().getAttribute("USUARIO") == null) {
            salir();
            return "";
        }
        Clients.showNotification("BIEVENIDO AL SISTEMA DE OPERACIONES PORTUARIAS WEB...!! <br/ >" + Login2.USER + " <br/>  <br/>");
        return desktop.getSession().getAttribute("USUARIO").toString();
    }

}
