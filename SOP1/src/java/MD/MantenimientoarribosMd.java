package MD;

public class MantenimientoarribosMd {

    //fecha y hora
    private String ObtefechaHora;

    private String ano_arribo;
    private String num_arribo;
    private String status;
    private String estado;
    private String cod_buque;
    private String nom_buque;
    private String cod_tipo_buque;
    private String nom_tipo_buque;
    private String num_atracadero;
    private String num_viaje;
    private String ant_pagados;
    private String fecha_atraque;
    private String fecha_zarpe;
    private String tipo_operacion;
    private String tipo_arribo;
    private String cantidad_bodegas;
    private String hora_operacion;
    private String pasajeros;
    private String inicio_operacion;
    private String pk_inicial;
    private String posicion_buque;
    private String fin_operacion;
    private String Pk_final;
    private String r_operador;
    private String datos_import;
    private String datos_export;
    private String tm_import;
    private String tm_export;
    private String num_estibadora;
    private String nom_estibadora;
    private String via_directa;
    private String pto_procedencia;
    private String pto_destino;
    private String representa_naviera;
    private String fecha_visita;
    private String Observaciones;

    //para el segundo query
    private String eslora;
    private String imo;
    private String call_sign;
    private String plumas;
    private String manga;
    private String anoCostruccion;
    private String calado;
    private String tn_bruto;
    private String tn_neto;
    private String peso_muerto;
    private String nom_pais;
    private String cant_Gruas;
    private String nom_naviera;
    private String nom_linea;
    private String agente_naviera;
    private String telefono;
    private String email;

    //mensajes 
    private String resp;
    private String msg;
    private String dir;

    public String getObtefechaHora() {
        return ObtefechaHora;
    }

    public void setObtefechaHora(String ObtefechaHora) {
        this.ObtefechaHora = ObtefechaHora;
    }

    public String getFecha_atraque() {
        return fecha_atraque;
    }

    public void setFecha_atraque(String fecha_atraque) {
        this.fecha_atraque = fecha_atraque;
    }

    public String getEslora() {
        return eslora;
    }

    public void setEslora(String eslora) {
        this.eslora = eslora;
    }

    public String getImo() {
        return imo;
    }

    public void setImo(String imo) {
        this.imo = imo;
    }

    public String getCall_sign() {
        return call_sign;
    }

    public void setCall_sign(String call_sign) {
        this.call_sign = call_sign;
    }

    public String getPlumas() {
        return plumas;
    }

    public void setPlumas(String plumas) {
        this.plumas = plumas;
    }

    public String getManga() {
        return manga;
    }

    public void setManga(String manga) {
        this.manga = manga;
    }

    public String getAnoCostruccion() {
        return anoCostruccion;
    }

    public void setAnoCostruccion(String anoCostruccion) {
        this.anoCostruccion = anoCostruccion;
    }

    public String getCalado() {
        return calado;
    }

    public void setCalado(String calado) {
        this.calado = calado;
    }

    public String getTn_bruto() {
        return tn_bruto;
    }

    public void setTn_bruto(String tn_bruto) {
        this.tn_bruto = tn_bruto;
    }

    public String getTn_neto() {
        return tn_neto;
    }

    public void setTn_neto(String tn_neto) {
        this.tn_neto = tn_neto;
    }

    public String getPeso_muerto() {
        return peso_muerto;
    }

    public void setPeso_muerto(String peso_muerto) {
        this.peso_muerto = peso_muerto;
    }

    public String getNom_pais() {
        return nom_pais;
    }

    public void setNom_pais(String nom_pais) {
        this.nom_pais = nom_pais;
    }

    public String getCant_Gruas() {
        return cant_Gruas;
    }

    public void setCant_Gruas(String cant_Gruas) {
        this.cant_Gruas = cant_Gruas;
    }

    public String getNom_naviera() {
        return nom_naviera;
    }

    public void setNom_naviera(String nom_naviera) {
        this.nom_naviera = nom_naviera;
    }

    public String getNom_linea() {
        return nom_linea;
    }

    public void setNom_linea(String nom_linea) {
        this.nom_linea = nom_linea;
    }

    public String getAgente_naviera() {
        return agente_naviera;
    }

    public void setAgente_naviera(String agente_naviera) {
        this.agente_naviera = agente_naviera;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAno_arribo() {
        return ano_arribo;
    }

    public void setAno_arribo(String ano_arribo) {
        this.ano_arribo = ano_arribo;
    }

    public String getNum_arribo() {
        return num_arribo;
    }

    public void setNum_arribo(String num_arribo) {
        this.num_arribo = num_arribo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCod_buque() {
        return cod_buque;
    }

    public void setCod_buque(String cod_buque) {
        this.cod_buque = cod_buque;
    }

    public String getNom_buque() {
        return nom_buque;
    }

    public void setNom_buque(String nom_buque) {
        this.nom_buque = nom_buque;
    }

    public String getCod_tipo_buque() {
        return cod_tipo_buque;
    }

    public void setCod_tipo_buque(String cod_tipo_buque) {
        this.cod_tipo_buque = cod_tipo_buque;
    }

    public String getNom_tipo_buque() {
        return nom_tipo_buque;
    }

    public void setNom_tipo_buque(String nom_tipo_buque) {
        this.nom_tipo_buque = nom_tipo_buque;
    }

    public String getNum_viaje() {
        return num_viaje;
    }

    public void setNum_viaje(String num_viaje) {
        this.num_viaje = num_viaje;
    }

    public String getNum_atracadero() {
        return num_atracadero;
    }

    public void setNum_atracadero(String num_atracadero) {
        this.num_atracadero = num_atracadero;
    }

    public String getAnt_pagados() {
        return ant_pagados;
    }

    public void setAnt_pagados(String ant_pagados) {
        this.ant_pagados = ant_pagados;
    }

    public String getFecha_Atraque() {
        return fecha_atraque;
    }

    public void setFecha_eta(String fecha_atraque) {
        this.fecha_atraque = fecha_atraque;
    }

    public String getFecha_zarpe() {
        return fecha_zarpe;
    }

    public void setFecha_zarpe(String fecha_zarpe) {
        this.fecha_zarpe = fecha_zarpe;
    }

    public String getTipo_operacion() {
        return tipo_operacion;
    }

    public void setTipo_operacion(String tipo_operacion) {
        this.tipo_operacion = tipo_operacion;
    }

    public String getTipo_arribo() {
        return tipo_arribo;
    }

    public void setTipo_arribo(String tipo_arribo) {
        this.tipo_arribo = tipo_arribo;
    }

    public String getCantidad_bodegas() {
        return cantidad_bodegas;
    }

    public void setCantidad_bodegas(String cantidad_bodegas) {
        this.cantidad_bodegas = cantidad_bodegas;
    }

    public String getHora_operacion() {
        return hora_operacion;
    }

    public void setHora_operacion(String hora_operacion) {
        this.hora_operacion = hora_operacion;
    }

    public String getPk_inicial() {
        return pk_inicial;
    }

    public void setPk_inicial(String pk_inicial) {
        this.pk_inicial = pk_inicial;
    }

    public String getPasajeros() {
        return pasajeros;
    }

    public void setPasajeros(String pasajeros) {
        this.pasajeros = pasajeros;
    }

    public String getInicio_operacion() {
        return inicio_operacion;
    }

    public void setInicio_operacion(String inicio_operacion) {
        this.inicio_operacion = inicio_operacion;
    }

    public String getPk_final() {
        return Pk_final;
    }

    public void setPk_final(String Pk_final) {
        this.Pk_final = Pk_final;
    }

    public String getR_operador() {
        return r_operador;
    }

    public void setR_operador(String r_operador) {
        this.r_operador = r_operador;
    }

    public String getDatos_import() {
        return datos_import;
    }

    public void setDatos_import(String datos_import) {
        this.datos_import = datos_import;
    }

    public String getDatos_export() {
        return datos_export;
    }

    public void setDatos_export(String datos_export) {
        this.datos_export = datos_export;
    }

    public String getTm_import() {
        return tm_import;
    }

    public void setTm_import(String tm_import) {
        this.tm_import = tm_import;
    }

    public String getTm_export() {
        return tm_export;
    }

    public void setTm_export(String tm_export) {
        this.tm_export = tm_export;
    }

    public String getNum_estibadora() {
        return num_estibadora;
    }

    public void setNum_estibadora(String num_estibadora) {
        this.num_estibadora = num_estibadora;
    }

    public String getNom_estibadora() {
        return nom_estibadora;
    }

    public void setNom_estibadora(String nom_estibadora) {
        this.nom_estibadora = nom_estibadora;
    }

    public String getVia_directa() {
        return via_directa;
    }

    public void setVia_directa(String via_directa) {
        this.via_directa = via_directa;
    }

    public String getPto_procedencia() {
        return pto_procedencia;
    }

    public void setPto_procedencia(String pto_procedencia) {
        this.pto_procedencia = pto_procedencia;
    }

    public String getPto_destino() {
        return pto_destino;
    }

    public void setPto_destino(String pto_destino) {
        this.pto_destino = pto_destino;
    }

    public String getRepresenta_naviera() {
        return representa_naviera;
    }

    public void setRepresenta_naviera(String representa_naviera) {
        this.representa_naviera = representa_naviera;
    }

    public String getFecha_visita() {
        return fecha_visita;
    }

    public void setFecha_visita(String fecha_visita) {
        this.fecha_visita = fecha_visita;
    }

    public String getObservaciones() {
        return Observaciones;
    }

    public void setObservaciones(String Observaciones) {
        this.Observaciones = Observaciones;
    }

    public String getPosicion_buque() {
        return posicion_buque;
    }

    public void setPosicion_buque(String posicion_buque) {
        this.posicion_buque = posicion_buque;
    }

    public String getFin_operacion() {
        return fin_operacion;
    }

    public void setFin_operacion(String fin_operacion) {
        this.fin_operacion = fin_operacion;
    }

    public String getResp() {
        return resp;
    }

    public void setResp(String resp) {
        this.resp = resp;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

}
