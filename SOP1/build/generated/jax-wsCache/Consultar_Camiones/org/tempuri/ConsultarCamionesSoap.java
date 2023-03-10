
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
@WebService(name = "Consultar_CamionesSoap", targetNamespace = "http://tempuri.org/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface ConsultarCamionesSoap {


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
    @WebMethod(operationName = "Obtener_Pesaje", action = "http://tempuri.org/Obtener_Pesaje")
    @WebResult(name = "Obtener_PesajeResult", targetNamespace = "http://tempuri.org/")
    @RequestWrapper(localName = "Obtener_Pesaje", targetNamespace = "http://tempuri.org/", className = "org.tempuri.ObtenerPesaje")
    @ResponseWrapper(localName = "Obtener_PesajeResponse", targetNamespace = "http://tempuri.org/", className = "org.tempuri.ObtenerPesajeResponse")
    public String obtenerPesaje(
        @WebParam(name = "xml", targetNamespace = "http://tempuri.org/")
        String xml);

}
