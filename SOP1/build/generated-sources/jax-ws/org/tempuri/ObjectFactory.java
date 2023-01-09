
package org.tempuri;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.tempuri package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _String_QNAME = new QName("http://tempuri.org/", "string");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.tempuri
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ObtenerInformacion }
     * 
     */
    public ObtenerInformacion createObtenerInformacion() {
        return new ObtenerInformacion();
    }

    /**
     * Create an instance of {@link ObtenerInformacionResponse }
     * 
     */
    public ObtenerInformacionResponse createObtenerInformacionResponse() {
        return new ObtenerInformacionResponse();
    }

    /**
     * Create an instance of {@link ObtenerRetencionPesaje }
     * 
     */
    public ObtenerRetencionPesaje createObtenerRetencionPesaje() {
        return new ObtenerRetencionPesaje();
    }

    /**
     * Create an instance of {@link ObtenerRetencionPesajeResponse }
     * 
     */
    public ObtenerRetencionPesajeResponse createObtenerRetencionPesajeResponse() {
        return new ObtenerRetencionPesajeResponse();
    }

    /**
     * Create an instance of {@link ObtenerDetalleContenedor }
     * 
     */
    public ObtenerDetalleContenedor createObtenerDetalleContenedor() {
        return new ObtenerDetalleContenedor();
    }

    /**
     * Create an instance of {@link ObtenerDetalleContenedorResponse }
     * 
     */
    public ObtenerDetalleContenedorResponse createObtenerDetalleContenedorResponse() {
        return new ObtenerDetalleContenedorResponse();
    }

    /**
     * Create an instance of {@link ObtenerDocumentosContenedor }
     * 
     */
    public ObtenerDocumentosContenedor createObtenerDocumentosContenedor() {
        return new ObtenerDocumentosContenedor();
    }

    /**
     * Create an instance of {@link ObtenerDocumentosContenedorResponse }
     * 
     */
    public ObtenerDocumentosContenedorResponse createObtenerDocumentosContenedorResponse() {
        return new ObtenerDocumentosContenedorResponse();
    }

    /**
     * Create an instance of {@link ObtenerOperacionCiclo }
     * 
     */
    public ObtenerOperacionCiclo createObtenerOperacionCiclo() {
        return new ObtenerOperacionCiclo();
    }

    /**
     * Create an instance of {@link ObtenerOperacionCicloResponse }
     * 
     */
    public ObtenerOperacionCicloResponse createObtenerOperacionCicloResponse() {
        return new ObtenerOperacionCicloResponse();
    }

    /**
     * Create an instance of {@link ObtenerCabecerasContenedor }
     * 
     */
    public ObtenerCabecerasContenedor createObtenerCabecerasContenedor() {
        return new ObtenerCabecerasContenedor();
    }

    /**
     * Create an instance of {@link ObtenerCabecerasContenedorResponse }
     * 
     */
    public ObtenerCabecerasContenedorResponse createObtenerCabecerasContenedorResponse() {
        return new ObtenerCabecerasContenedorResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "string")
    public JAXBElement<String> createString(String value) {
        return new JAXBElement<String>(_String_QNAME, String.class, null, value);
    }

}
