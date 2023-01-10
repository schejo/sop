/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MD;

/**
 *
 * @author HP 15
 */
public class CatalogosMd {

    private String codigo;
    private String servi;
    private String parti;
    private String codparti;
    private String codcliFac;
    private String fecha_inicio;

    public String getFecha_inicio() {
        return fecha_inicio;
    }

    public void setFecha_inicio(String fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }
    
    

    public String getCodcliFac() {
        return codcliFac;
    }

    public void setCodcliFac(String codcliFac) {
        this.codcliFac = codcliFac;
    }

    public String getCodparti() {
        return codparti;
    }

    public void setCodparti(String codparti) {
        this.codparti = codparti;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getServi() {
        return servi;
    }

    public void setServi(String servi) {
        this.servi = servi;
    }

    public String getParti() {
        return parti;
    }

    public void setParti(String parti) {
        this.parti = parti;
    }

}
