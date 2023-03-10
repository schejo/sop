
package org.tempuri;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.11-b150120.1832
 * Generated source version: 2.2
 * 
 */
@WebService(name = "Consultar_ContenedoresSoap", targetNamespace = "http://tempuri.org/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface ConsultarContenedoresSoap {


    /**
     * 
     * @param xml
     * @return
     *     returns java.lang.String
     */
    @WebMethod(operationName = "Obtener_Informacion", action = "http://tempuri.org/Obtener_Informacion")
    @WebResult(name = "Obtener_InformacionResult", targetNamespace = "http://tempuri.org/")
    @RequestWrapper(localName = "Obtener_Informacion", targetNamespace = "http://tempuri.org/", className = "org.tempuri.ObtenerInformacion")
    @ResponseWrapper(localName = "Obtener_InformacionResponse", targetNamespace = "http://tempuri.org/", className = "org.tempuri.ObtenerInformacionResponse")
    public String obtenerInformacion(
        @WebParam(name = "xml", targetNamespace = "http://tempuri.org/")
        String xml);

    /**
     * 
     * @param xml
     * @return
     *     returns java.lang.String
     */
    @WebMethod(operationName = "Obtener_Retencion_Pesaje", action = "http://tempuri.org/Obtener_Retencion_Pesaje")
    @WebResult(name = "Obtener_Retencion_PesajeResult", targetNamespace = "http://tempuri.org/")
    @RequestWrapper(localName = "Obtener_Retencion_Pesaje", targetNamespace = "http://tempuri.org/", className = "org.tempuri.ObtenerRetencionPesaje")
    @ResponseWrapper(localName = "Obtener_Retencion_PesajeResponse", targetNamespace = "http://tempuri.org/", className = "org.tempuri.ObtenerRetencionPesajeResponse")
    public String obtenerRetencionPesaje(
        @WebParam(name = "xml", targetNamespace = "http://tempuri.org/")
        String xml);

    /**
     * 
     * @param xml
     * @return
     *     returns java.lang.String
     */
    @WebMethod(operationName = "Obtener_Detalle_Contenedor", action = "http://tempuri.org/Obtener_Detalle_Contenedor")
    @WebResult(name = "Obtener_Detalle_ContenedorResult", targetNamespace = "http://tempuri.org/")
    @RequestWrapper(localName = "Obtener_Detalle_Contenedor", targetNamespace = "http://tempuri.org/", className = "org.tempuri.ObtenerDetalleContenedor")
    @ResponseWrapper(localName = "Obtener_Detalle_ContenedorResponse", targetNamespace = "http://tempuri.org/", className = "org.tempuri.ObtenerDetalleContenedorResponse")
    public String obtenerDetalleContenedor(
        @WebParam(name = "xml", targetNamespace = "http://tempuri.org/")
        String xml);

    /**
     * 
     * @param xml
     * @return
     *     returns java.lang.String
     */
    @WebMethod(operationName = "Obtener_Documentos_Contenedor", action = "http://tempuri.org/Obtener_Documentos_Contenedor")
    @WebResult(name = "Obtener_Documentos_ContenedorResult", targetNamespace = "http://tempuri.org/")
    @RequestWrapper(localName = "Obtener_Documentos_Contenedor", targetNamespace = "http://tempuri.org/", className = "org.tempuri.ObtenerDocumentosContenedor")
    @ResponseWrapper(localName = "Obtener_Documentos_ContenedorResponse", targetNamespace = "http://tempuri.org/", className = "org.tempuri.ObtenerDocumentosContenedorResponse")
    public String obtenerDocumentosContenedor(
        @WebParam(name = "xml", targetNamespace = "http://tempuri.org/")
        String xml);

    /**
     * 
     * @param xml
     * @return
     *     returns java.lang.String
     */
    @WebMethod(operationName = "Obtener_Operacion_Ciclo", action = "http://tempuri.org/Obtener_Operacion_Ciclo")
    @WebResult(name = "Obtener_Operacion_CicloResult", targetNamespace = "http://tempuri.org/")
    @RequestWrapper(localName = "Obtener_Operacion_Ciclo", targetNamespace = "http://tempuri.org/", className = "org.tempuri.ObtenerOperacionCiclo")
    @ResponseWrapper(localName = "Obtener_Operacion_CicloResponse", targetNamespace = "http://tempuri.org/", className = "org.tempuri.ObtenerOperacionCicloResponse")
    public String obtenerOperacionCiclo(
        @WebParam(name = "xml", targetNamespace = "http://tempuri.org/")
        String xml);

    /**
     * 
     * @param xml
     * @return
     *     returns java.lang.String
     */
    @WebMethod(operationName = "Obtener_Cabeceras_Contenedor", action = "http://tempuri.org/Obtener_Cabeceras_Contenedor")
    @WebResult(name = "Obtener_Cabeceras_ContenedorResult", targetNamespace = "http://tempuri.org/")
    @RequestWrapper(localName = "Obtener_Cabeceras_Contenedor", targetNamespace = "http://tempuri.org/", className = "org.tempuri.ObtenerCabecerasContenedor")
    @ResponseWrapper(localName = "Obtener_Cabeceras_ContenedorResponse", targetNamespace = "http://tempuri.org/", className = "org.tempuri.ObtenerCabecerasContenedorResponse")
    public String obtenerCabecerasContenedor(
        @WebParam(name = "xml", targetNamespace = "http://tempuri.org/")
        String xml);

}
