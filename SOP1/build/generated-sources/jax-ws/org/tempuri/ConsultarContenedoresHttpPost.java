
package org.tempuri;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.11-b150120.1832
 * Generated source version: 2.2
 * 
 */
@WebService(name = "Consultar_ContenedoresHttpPost", targetNamespace = "http://tempuri.org/")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@XmlSeeAlso({
    ObjectFactory.class
})
public interface ConsultarContenedoresHttpPost {


    /**
     * 
     * @param xml
     * @return
     *     returns java.lang.String
     */
    @WebMethod(operationName = "Obtener_Informacion")
    @WebResult(name = "string", targetNamespace = "http://tempuri.org/", partName = "Body")
    public String obtenerInformacion(
        @WebParam(name = "string", targetNamespace = "http://www.w3.org/2001/XMLSchema", partName = "xml")
        String xml);

    /**
     * 
     * @param xml
     * @return
     *     returns java.lang.String
     */
    @WebMethod(operationName = "Obtener_Retencion_Pesaje")
    @WebResult(name = "string", targetNamespace = "http://tempuri.org/", partName = "Body")
    public String obtenerRetencionPesaje(
        @WebParam(name = "string", targetNamespace = "http://www.w3.org/2001/XMLSchema", partName = "xml")
        String xml);

    /**
     * 
     * @param xml
     * @return
     *     returns java.lang.String
     */
    @WebMethod(operationName = "Obtener_Detalle_Contenedor")
    @WebResult(name = "string", targetNamespace = "http://tempuri.org/", partName = "Body")
    public String obtenerDetalleContenedor(
        @WebParam(name = "string", targetNamespace = "http://www.w3.org/2001/XMLSchema", partName = "xml")
        String xml);

    /**
     * 
     * @param xml
     * @return
     *     returns java.lang.String
     */
    @WebMethod(operationName = "Obtener_Documentos_Contenedor")
    @WebResult(name = "string", targetNamespace = "http://tempuri.org/", partName = "Body")
    public String obtenerDocumentosContenedor(
        @WebParam(name = "string", targetNamespace = "http://www.w3.org/2001/XMLSchema", partName = "xml")
        String xml);

    /**
     * 
     * @param xml
     * @return
     *     returns java.lang.String
     */
    @WebMethod(operationName = "Obtener_Operacion_Ciclo")
    @WebResult(name = "string", targetNamespace = "http://tempuri.org/", partName = "Body")
    public String obtenerOperacionCiclo(
        @WebParam(name = "string", targetNamespace = "http://www.w3.org/2001/XMLSchema", partName = "xml")
        String xml);

    /**
     * 
     * @param xml
     * @return
     *     returns java.lang.String
     */
    @WebMethod(operationName = "Obtener_Cabeceras_Contenedor")
    @WebResult(name = "string", targetNamespace = "http://tempuri.org/", partName = "Body")
    public String obtenerCabecerasContenedor(
        @WebParam(name = "string", targetNamespace = "http://www.w3.org/2001/XMLSchema", partName = "xml")
        String xml);

}
