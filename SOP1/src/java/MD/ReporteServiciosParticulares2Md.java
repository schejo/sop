package MD;

public class ReporteServiciosParticulares2Md {

    private String anio;
    private String numero;
    private String nom_buque;
    private String contenedor;
    private String linea;
    private String naviera;
    private String operacion;
    private String fecha_ingreso1;
    private String tipo_contenedor;
    private String tm_total;
    private String estado;
    private String cant_teu;
    private String transito;
    
    //VARIABLES PARA EL RESUMEN DE AÑO Y ARRIBO
    private String descripcion;
    private String registros;
    
    //VARIABLES PARA EL RESUMEN F_INICIO Y F_FIN
    private String f_inicio;
    private String f_fin;

    public String getAnio() {
        return anio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    

    public void setAnio(String anio) {
        this.anio = anio;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getNom_buque() {
        return nom_buque;
    }

    public void setNom_buque(String nom_buque) {
        this.nom_buque = nom_buque;
    }

    public String getContenedor() {
        return contenedor;
    }

    public void setContenedor(String contenedor) {
        this.contenedor = contenedor;
    }

    public String getLinea() {
        return linea;
    }

    public void setLinea(String linea) {
        this.linea = linea;
    }

    public String getNaviera() {
        return naviera;
    }

    public void setNaviera(String naviera) {
        this.naviera = naviera;
    }

    public String getOperacion() {
        return operacion;
    }

    public void setOperacion(String operacion) {
        this.operacion = operacion;
    }

    public String getFecha_ingreso1() {
        return fecha_ingreso1;
    }

    public void setFecha_ingreso1(String fecha_ingreso1) {
        this.fecha_ingreso1 = fecha_ingreso1;
    }

    public String getTipo_contenedor() {
        return tipo_contenedor;
    }

    public void setTipo_contenedor(String tipo_contenedor) {
        this.tipo_contenedor = tipo_contenedor;
    }

    public String getTm_total() {
        return tm_total;
    }

    public void setTm_total(String tm_total) {
        this.tm_total = tm_total;
    }

    public String getCant_teu() {
        return cant_teu;
    }

    public void setCant_teu(String cant_teu) {
        this.cant_teu = cant_teu;
    }

    public String getTransito() {
        return transito;
    }

    public void setTransito(String transito) {
        this.transito = transito;
    }
    
    //VARIABLES PARA EL RESUMEN ANIO Y ARRIBO

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getRegistros() {
        return registros;
    }

    public void setRegistros(String registros) {
        this.registros = registros;
    }
    
    //VARIABLES PARA EL RESUMEN F_INICIO Y F_FIN

    public String getF_inicio() {
        return f_inicio;
    }

    public void setF_inicio(String f_inicio) {
        this.f_inicio = f_inicio;
    }

    public String getF_fin() {
        return f_fin;
    }

    public void setF_fin(String f_fin) {
        this.f_fin = f_fin;
    }
    
    

}
