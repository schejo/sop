
package org.tempuri;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.11-b150120.1832
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "Consultar_Camiones", targetNamespace = "http://tempuri.org/", wsdlLocation = "http://10.75.1.37:8070/Consultar_Camiones.asmx?wsdl")
public class ConsultarCamiones
    extends Service
{

    private final static URL CONSULTARCAMIONES_WSDL_LOCATION;
    private final static WebServiceException CONSULTARCAMIONES_EXCEPTION;
    private final static QName CONSULTARCAMIONES_QNAME = new QName("http://tempuri.org/", "Consultar_Camiones");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://10.75.1.37:8070/Consultar_Camiones.asmx?wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        CONSULTARCAMIONES_WSDL_LOCATION = url;
        CONSULTARCAMIONES_EXCEPTION = e;
    }

    public ConsultarCamiones() {
        super(__getWsdlLocation(), CONSULTARCAMIONES_QNAME);
    }

    public ConsultarCamiones(WebServiceFeature... features) {
        super(__getWsdlLocation(), CONSULTARCAMIONES_QNAME, features);
    }

    public ConsultarCamiones(URL wsdlLocation) {
        super(wsdlLocation, CONSULTARCAMIONES_QNAME);
    }

    public ConsultarCamiones(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, CONSULTARCAMIONES_QNAME, features);
    }

    public ConsultarCamiones(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public ConsultarCamiones(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns ConsultarCamionesSoap
     */
    @WebEndpoint(name = "Consultar_CamionesSoap")
    public ConsultarCamionesSoap getConsultarCamionesSoap() {
        return super.getPort(new QName("http://tempuri.org/", "Consultar_CamionesSoap"), ConsultarCamionesSoap.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns ConsultarCamionesSoap
     */
    @WebEndpoint(name = "Consultar_CamionesSoap")
    public ConsultarCamionesSoap getConsultarCamionesSoap(WebServiceFeature... features) {
        return super.getPort(new QName("http://tempuri.org/", "Consultar_CamionesSoap"), ConsultarCamionesSoap.class, features);
    }

    /**
     * 
     * @return
     *     returns ConsultarCamionesSoap
     */
    @WebEndpoint(name = "Consultar_CamionesSoap12")
    public ConsultarCamionesSoap getConsultarCamionesSoap12() {
        return super.getPort(new QName("http://tempuri.org/", "Consultar_CamionesSoap12"), ConsultarCamionesSoap.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns ConsultarCamionesSoap
     */
    @WebEndpoint(name = "Consultar_CamionesSoap12")
    public ConsultarCamionesSoap getConsultarCamionesSoap12(WebServiceFeature... features) {
        return super.getPort(new QName("http://tempuri.org/", "Consultar_CamionesSoap12"), ConsultarCamionesSoap.class, features);
    }

    /**
     * 
     * @return
     *     returns ConsultarCamionesHttpGet
     */
    @WebEndpoint(name = "Consultar_CamionesHttpGet")
    public ConsultarCamionesHttpGet getConsultarCamionesHttpGet() {
        return super.getPort(new QName("http://tempuri.org/", "Consultar_CamionesHttpGet"), ConsultarCamionesHttpGet.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns ConsultarCamionesHttpGet
     */
    @WebEndpoint(name = "Consultar_CamionesHttpGet")
    public ConsultarCamionesHttpGet getConsultarCamionesHttpGet(WebServiceFeature... features) {
        return super.getPort(new QName("http://tempuri.org/", "Consultar_CamionesHttpGet"), ConsultarCamionesHttpGet.class, features);
    }

    /**
     * 
     * @return
     *     returns ConsultarCamionesHttpPost
     */
    @WebEndpoint(name = "Consultar_CamionesHttpPost")
    public ConsultarCamionesHttpPost getConsultarCamionesHttpPost() {
        return super.getPort(new QName("http://tempuri.org/", "Consultar_CamionesHttpPost"), ConsultarCamionesHttpPost.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns ConsultarCamionesHttpPost
     */
    @WebEndpoint(name = "Consultar_CamionesHttpPost")
    public ConsultarCamionesHttpPost getConsultarCamionesHttpPost(WebServiceFeature... features) {
        return super.getPort(new QName("http://tempuri.org/", "Consultar_CamionesHttpPost"), ConsultarCamionesHttpPost.class, features);
    }

    private static URL __getWsdlLocation() {
        if (CONSULTARCAMIONES_EXCEPTION!= null) {
            throw CONSULTARCAMIONES_EXCEPTION;
        }
        return CONSULTARCAMIONES_WSDL_LOCATION;
    }

}
