
package org.tempuri;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para anonymous complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Obtener_Detalle_ContenedorResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "obtenerDetalleContenedorResult"
})
@XmlRootElement(name = "Obtener_Detalle_ContenedorResponse")
public class ObtenerDetalleContenedorResponse {

    @XmlElement(name = "Obtener_Detalle_ContenedorResult")
    protected String obtenerDetalleContenedorResult;

    /**
     * Obtiene el valor de la propiedad obtenerDetalleContenedorResult.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getObtenerDetalleContenedorResult() {
        return obtenerDetalleContenedorResult;
    }

    /**
     * Define el valor de la propiedad obtenerDetalleContenedorResult.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setObtenerDetalleContenedorResult(String value) {
        this.obtenerDetalleContenedorResult = value;
    }

}
