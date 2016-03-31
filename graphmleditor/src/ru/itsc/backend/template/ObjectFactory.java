
package ru.itsc.backend.template;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the ru.gazprom_neft.metamap.entities.beans package. 
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

    private final static QName _AddTemplateResponse_QNAME = new QName("http://gazprom_neft.ru/metamap/entities/beans/", "addTemplateResponse");
    private final static QName _GetTemplate_QNAME = new QName("http://gazprom_neft.ru/metamap/entities/beans/", "getTemplate");
    private final static QName _GetLastTemplateResponse_QNAME = new QName("http://gazprom_neft.ru/metamap/entities/beans/", "getLastTemplateResponse");
    private final static QName _GetTemplateResponse_QNAME = new QName("http://gazprom_neft.ru/metamap/entities/beans/", "getTemplateResponse");
    private final static QName _GetLastTemplate_QNAME = new QName("http://gazprom_neft.ru/metamap/entities/beans/", "getLastTemplate");
    private final static QName _GetTemplatesByLimitResponse_QNAME = new QName("http://gazprom_neft.ru/metamap/entities/beans/", "getTemplatesByLimitResponse");
    private final static QName _GetTemplatesByLimit_QNAME = new QName("http://gazprom_neft.ru/metamap/entities/beans/", "getTemplatesByLimit");
    private final static QName _AddTemplate_QNAME = new QName("http://gazprom_neft.ru/metamap/entities/beans/", "addTemplate");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: ru.gazprom_neft.metamap.entities.beans
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetTemplatesByLimit }
     * 
     */
    public GetTemplatesByLimit createGetTemplatesByLimit() {
        return new GetTemplatesByLimit();
    }

    /**
     * Create an instance of {@link AddTemplate }
     * 
     */
    public AddTemplate createAddTemplate() {
        return new AddTemplate();
    }

    /**
     * Create an instance of {@link GetLastTemplateResponse }
     * 
     */
    public GetLastTemplateResponse createGetLastTemplateResponse() {
        return new GetLastTemplateResponse();
    }

    /**
     * Create an instance of {@link GetTemplateResponse }
     * 
     */
    public GetTemplateResponse createGetTemplateResponse() {
        return new GetTemplateResponse();
    }

    /**
     * Create an instance of {@link GetTemplate }
     * 
     */
    public GetTemplate createGetTemplate() {
        return new GetTemplate();
    }

    /**
     * Create an instance of {@link GetLastTemplate }
     * 
     */
    public GetLastTemplate createGetLastTemplate() {
        return new GetLastTemplate();
    }

    /**
     * Create an instance of {@link GetTemplatesByLimitResponse }
     * 
     */
    public GetTemplatesByLimitResponse createGetTemplatesByLimitResponse() {
        return new GetTemplatesByLimitResponse();
    }

    /**
     * Create an instance of {@link AddTemplateResponse }
     * 
     */
    public AddTemplateResponse createAddTemplateResponse() {
        return new AddTemplateResponse();
    }

    /**
     * Create an instance of {@link Template }
     * 
     */
    public Template createTemplate() {
        return new Template();
    }

    /**
     * Create an instance of {@link Timestamp }
     * 
     */
    public Timestamp createTimestamp() {
        return new Timestamp();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddTemplateResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://gazprom_neft.ru/metamap/entities/beans/", name = "addTemplateResponse")
    public JAXBElement<AddTemplateResponse> createAddTemplateResponse(AddTemplateResponse value) {
        return new JAXBElement<AddTemplateResponse>(_AddTemplateResponse_QNAME, AddTemplateResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTemplate }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://gazprom_neft.ru/metamap/entities/beans/", name = "getTemplate")
    public JAXBElement<GetTemplate> createGetTemplate(GetTemplate value) {
        return new JAXBElement<GetTemplate>(_GetTemplate_QNAME, GetTemplate.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetLastTemplateResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://gazprom_neft.ru/metamap/entities/beans/", name = "getLastTemplateResponse")
    public JAXBElement<GetLastTemplateResponse> createGetLastTemplateResponse(GetLastTemplateResponse value) {
        return new JAXBElement<GetLastTemplateResponse>(_GetLastTemplateResponse_QNAME, GetLastTemplateResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTemplateResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://gazprom_neft.ru/metamap/entities/beans/", name = "getTemplateResponse")
    public JAXBElement<GetTemplateResponse> createGetTemplateResponse(GetTemplateResponse value) {
        return new JAXBElement<GetTemplateResponse>(_GetTemplateResponse_QNAME, GetTemplateResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetLastTemplate }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://gazprom_neft.ru/metamap/entities/beans/", name = "getLastTemplate")
    public JAXBElement<GetLastTemplate> createGetLastTemplate(GetLastTemplate value) {
        return new JAXBElement<GetLastTemplate>(_GetLastTemplate_QNAME, GetLastTemplate.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTemplatesByLimitResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://gazprom_neft.ru/metamap/entities/beans/", name = "getTemplatesByLimitResponse")
    public JAXBElement<GetTemplatesByLimitResponse> createGetTemplatesByLimitResponse(GetTemplatesByLimitResponse value) {
        return new JAXBElement<GetTemplatesByLimitResponse>(_GetTemplatesByLimitResponse_QNAME, GetTemplatesByLimitResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTemplatesByLimit }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://gazprom_neft.ru/metamap/entities/beans/", name = "getTemplatesByLimit")
    public JAXBElement<GetTemplatesByLimit> createGetTemplatesByLimit(GetTemplatesByLimit value) {
        return new JAXBElement<GetTemplatesByLimit>(_GetTemplatesByLimit_QNAME, GetTemplatesByLimit.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddTemplate }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://gazprom_neft.ru/metamap/entities/beans/", name = "addTemplate")
    public JAXBElement<AddTemplate> createAddTemplate(AddTemplate value) {
        return new JAXBElement<AddTemplate>(_AddTemplate_QNAME, AddTemplate.class, null, value);
    }

}
