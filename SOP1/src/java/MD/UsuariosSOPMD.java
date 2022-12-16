/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package MD;

//import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.date;
import java.util.Date;

/**
 *
 * @author Informatica
 */
public class UsuariosSOPMD {
    private String codEmpleado;
    private String Nombre;
    private String Descripcion;
    private Double Prioridad;
    private String TipoUsuario;
    private String Password;
    private String FechaVencimiento;
    private Date EntBiometrico;
    private Date SalBiometrico;
    private String Estado;
    private String Group_name;
     private String UsrPalabra;
      private String UsrUsuario;

    public String getUsrPalabra() {
        return UsrPalabra;
    }

    public void setUsrPalabra(String UsrPalabra) {
        this.UsrPalabra = UsrPalabra;
    }

    public String getUsrUsuario() {
        return UsrUsuario;
    }

    public void setUsrUsuario(String UsrUsuario) {
        this.UsrUsuario = UsrUsuario;
    }
      
      

    public String getCodEmpleado() {
        return codEmpleado;
    }

    public void setCodEmpleado(String codEmpleado) {
        this.codEmpleado = codEmpleado;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String Descripcion) {
        this.Descripcion = Descripcion;
    }

    public Double getPrioridad() {
        return Prioridad;
    }

    public void setPrioridad(Double Prioridad) {
        this.Prioridad = Prioridad;
    }

    public String getTipoUsuario() {
        return TipoUsuario;
    }

    public void setTipoUsuario(String TipoUsuario) {
        this.TipoUsuario = TipoUsuario;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public String getFechaVencimiento() {
        return FechaVencimiento;
    }

    public void setFechaVencimiento(String FechaVencimiento) {
        this.FechaVencimiento = FechaVencimiento;
    }

    public Date getEntBiometrico() {
        return EntBiometrico;
    }

    public void setEntBiometrico(Date EntBiometrico) {
        this.EntBiometrico = EntBiometrico;
    }

    public Date getSalBiometrico() {
        return SalBiometrico;
    }

    public void setSalBiometrico(Date SalBiometrico) {
        this.SalBiometrico = SalBiometrico;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String Estado) {
        this.Estado = Estado;
    }

    public String getGroup_name() {
        return Group_name;
    }

    public void setGroup_name(String Group_name) {
        this.Group_name = Group_name;
    }

    

   
}
