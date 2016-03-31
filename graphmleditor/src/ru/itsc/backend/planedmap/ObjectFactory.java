
package ru.itsc.backend.planedmap;

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

    private final static QName _GetLastPlanedMetamap_QNAME = new QName("http://gazprom_neft.ru/metamap/bean/", "getLastPlanedMetamap");
    private final static QName _GetPlanedMetamapResponse_QNAME = new QName("http://gazprom_neft.ru/metamap/bean/", "getPlanedMetamapResponse");
    private final static QName _GetLastPlanedMetamapResponse_QNAME = new QName("http://gazprom_neft.ru/metamap/bean/", "getLastPlanedMetamapResponse");
    private final static QName _GetPlanedMetamaps_QNAME = new QName("http://gazprom_neft.ru/metamap/bean/", "getPlanedMetamaps");
    private final static QName _GetPlanedMetamapsByLimitResponse_QNAME = new QName("http://gazprom_neft.ru/metamap/bean/", "getPlanedMetamapsByLimitResponse");
    private final static QName _GetPlanedMetamap_QNAME = new QName("http://gazprom_neft.ru/metamap/bean/", "getPlanedMetamap");
    private final static QName _GetPlanedMetamapsByLimit_QNAME = new QName("http://gazprom_neft.ru/metamap/bean/", "getPlanedMetamapsByLimit");
    private final static QName _GetPlanedMetamapsResponse_QNAME = new QName("http://gazprom_neft.ru/metamap/bean/", "getPlanedMetamapsResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: ru.gazprom_neft.metamap.bean
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetPlanedMetamap }
     * 
     */
    public GetPlanedMetamap createGetPlanedMetamap() {
        return new GetPlanedMetamap();
    }

    /**
     * Create an instance of {@link GetPlanedMetamapsByLimit }
     * 
     */
    public GetPlanedMetamapsByLimit createGetPlanedMetamapsByLimit() {
        return new GetPlanedMetamapsByLimit();
    }

    /**
     * Create an instance of {@link GetPlanedMetamapsResponse }
     * 
     */
    public GetPlanedMetamapsResponse createGetPlanedMetamapsResponse() {
        return new GetPlanedMetamapsResponse();
    }

    /**
     * Create an instance of {@link GetPlanedMetamapResponse }
     * 
     */
    public GetPlanedMetamapResponse createGetPlanedMetamapResponse() {
        return new GetPlanedMetamapResponse();
    }

    /**
     * Create an instance of {@link GetLastPlanedMetamapResponse }
     * 
     */
    public GetLastPlanedMetamapResponse createGetLastPlanedMetamapResponse() {
        return new GetLastPlanedMetamapResponse();
    }

    /**
     * Create an instance of {@link GetPlanedMetamaps }
     * 
     */
    public GetPlanedMetamaps createGetPlanedMetamaps() {
        return new GetPlanedMetamaps();
    }

    /**
     * Create an instance of {@link GetPlanedMetamapsByLimitResponse }
     * 
     */
    public GetPlanedMetamapsByLimitResponse createGetPlanedMetamapsByLimitResponse() {
        return new GetPlanedMetamapsByLimitResponse();
    }

    /**
     * Create an instance of {@link GetLastPlanedMetamap }
     * 
     */
    public GetLastPlanedMetamap createGetLastPlanedMetamap() {
        return new GetLastPlanedMetamap();
    }

    /**
     * Create an instance of {@link Date }
     * 
     */
    public Date createDate() {
        return new Date();
    }

    /**
     * Create an instance of {@link PlanedMetamap }
     * 
     */
    public PlanedMetamap createPlanedMetamap() {
        return new PlanedMetamap();
    }

    /**
     * Create an instance of {@link Timestamp }
     * 
     */
    public Timestamp createTimestamp() {
        return new Timestamp();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetLastPlanedMetamap }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://gazprom_neft.ru/metamap/bean/", name = "getLastPlanedMetamap")
    public JAXBElement<GetLastPlanedMetamap> createGetLastPlanedMetamap(GetLastPlanedMetamap value) {
        return new JAXBElement<GetLastPlanedMetamap>(_GetLastPlanedMetamap_QNAME, GetLastPlanedMetamap.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetPlanedMetamapResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://gazprom_neft.ru/metamap/bean/", name = "getPlanedMetamapResponse")
    public JAXBElement<GetPlanedMetamapResponse> createGetPlanedMetamapResponse(GetPlanedMetamapResponse value) {
        return new JAXBElement<GetPlanedMetamapResponse>(_GetPlanedMetamapResponse_QNAME, GetPlanedMetamapResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetLastPlanedMetamapResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://gazprom_neft.ru/metamap/bean/", name = "getLastPlanedMetamapResponse")
    public JAXBElement<GetLastPlanedMetamapResponse> createGetLastPlanedMetamapResponse(GetLastPlanedMetamapResponse value) {
        return new JAXBElement<GetLastPlanedMetamapResponse>(_GetLastPlanedMetamapResponse_QNAME, GetLastPlanedMetamapResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetPlanedMetamaps }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://gazprom_neft.ru/metamap/bean/", name = "getPlanedMetamaps")
    public JAXBElement<GetPlanedMetamaps> createGetPlanedMetamaps(GetPlanedMetamaps value) {
        return new JAXBElement<GetPlanedMetamaps>(_GetPlanedMetamaps_QNAME, GetPlanedMetamaps.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetPlanedMetamapsByLimitResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://gazprom_neft.ru/metamap/bean/", name = "getPlanedMetamapsByLimitResponse")
    public JAXBElement<GetPlanedMetamapsByLimitResponse> createGetPlanedMetamapsByLimitResponse(GetPlanedMetamapsByLimitResponse value) {
        return new JAXBElement<GetPlanedMetamapsByLimitResponse>(_GetPlanedMetamapsByLimitResponse_QNAME, GetPlanedMetamapsByLimitResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetPlanedMetamap }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://gazprom_neft.ru/metamap/bean/", name = "getPlanedMetamap")
    public JAXBElement<GetPlanedMetamap> createGetPlanedMetamap(GetPlanedMetamap value) {
        return new JAXBElement<GetPlanedMetamap>(_GetPlanedMetamap_QNAME, GetPlanedMetamap.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetPlanedMetamapsByLimit }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://gazprom_neft.ru/metamap/bean/", name = "getPlanedMetamapsByLimit")
    public JAXBElement<GetPlanedMetamapsByLimit> createGetPlanedMetamapsByLimit(GetPlanedMetamapsByLimit value) {
        return new JAXBElement<GetPlanedMetamapsByLimit>(_GetPlanedMetamapsByLimit_QNAME, GetPlanedMetamapsByLimit.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetPlanedMetamapsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://gazprom_neft.ru/metamap/bean/", name = "getPlanedMetamapsResponse")
    public JAXBElement<GetPlanedMetamapsResponse> createGetPlanedMetamapsResponse(GetPlanedMetamapsResponse value) {
        return new JAXBElement<GetPlanedMetamapsResponse>(_GetPlanedMetamapsResponse_QNAME, GetPlanedMetamapsResponse.class, null, value);
    }

}
