
package ru.itsc.backend;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the ru.gazprom_neft.metamap.bean package. 
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

    private final static QName _RetrieveGraphMlPiResponse_QNAME = new QName("http://gazprom_neft.ru/metamap/bean/", "retrieveGraphMlPiResponse");
    private final static QName _RetrieveGraphMl_QNAME = new QName("http://gazprom_neft.ru/metamap/bean/", "retrieveGraphMl");
    private final static QName _RetrieveGraphMlPi_QNAME = new QName("http://gazprom_neft.ru/metamap/bean/", "retrieveGraphMlPi");
    private final static QName _RetrieveGraphMlResponse_QNAME = new QName("http://gazprom_neft.ru/metamap/bean/", "retrieveGraphMlResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: ru.gazprom_neft.metamap.bean
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link RetrieveGraphMlResponse }
     * 
     */
    public RetrieveGraphMlResponse createRetrieveGraphMlResponse() {
        return new RetrieveGraphMlResponse();
    }

    /**
     * Create an instance of {@link RetrieveGraphMl }
     * 
     */
    public RetrieveGraphMl createRetrieveGraphMl() {
        return new RetrieveGraphMl();
    }

    /**
     * Create an instance of {@link RetrieveGraphMlPiResponse }
     * 
     */
    public RetrieveGraphMlPiResponse createRetrieveGraphMlPiResponse() {
        return new RetrieveGraphMlPiResponse();
    }

    /**
     * Create an instance of {@link RetrieveGraphMlPi }
     * 
     */
    public RetrieveGraphMlPi createRetrieveGraphMlPi() {
        return new RetrieveGraphMlPi();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RetrieveGraphMlPiResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://gazprom_neft.ru/metamap/bean/", name = "retrieveGraphMlPiResponse")
    public JAXBElement<RetrieveGraphMlPiResponse> createRetrieveGraphMlPiResponse(RetrieveGraphMlPiResponse value) {
        return new JAXBElement<RetrieveGraphMlPiResponse>(_RetrieveGraphMlPiResponse_QNAME, RetrieveGraphMlPiResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RetrieveGraphMl }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://gazprom_neft.ru/metamap/bean/", name = "retrieveGraphMl")
    public JAXBElement<RetrieveGraphMl> createRetrieveGraphMl(RetrieveGraphMl value) {
        return new JAXBElement<RetrieveGraphMl>(_RetrieveGraphMl_QNAME, RetrieveGraphMl.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RetrieveGraphMlPi }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://gazprom_neft.ru/metamap/bean/", name = "retrieveGraphMlPi")
    public JAXBElement<RetrieveGraphMlPi> createRetrieveGraphMlPi(RetrieveGraphMlPi value) {
        return new JAXBElement<RetrieveGraphMlPi>(_RetrieveGraphMlPi_QNAME, RetrieveGraphMlPi.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RetrieveGraphMlResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://gazprom_neft.ru/metamap/bean/", name = "retrieveGraphMlResponse")
    public JAXBElement<RetrieveGraphMlResponse> createRetrieveGraphMlResponse(RetrieveGraphMlResponse value) {
        return new JAXBElement<RetrieveGraphMlResponse>(_RetrieveGraphMlResponse_QNAME, RetrieveGraphMlResponse.class, null, value);
    }

}
