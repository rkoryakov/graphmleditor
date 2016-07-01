
package ru.itsc.backend.plannedmap;

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
     * Create an instance of {@link GetPlannedMetamap }
     * 
     */
    public GetPlannedMetamap createGetPlanedMetamap() {
        return new GetPlannedMetamap();
    }

    /**
     * Create an instance of {@link GetPlannedMetamapsByLimit }
     * 
     */
    public GetPlannedMetamapsByLimit createGetPlanedMetamapsByLimit() {
        return new GetPlannedMetamapsByLimit();
    }

    /**
     * Create an instance of {@link GetPlannedMetamapsResponse }
     * 
     */
    public GetPlannedMetamapsResponse createGetPlanedMetamapsResponse() {
        return new GetPlannedMetamapsResponse();
    }

    /**
     * Create an instance of {@link GetPlannedMetamapResponse }
     * 
     */
    public GetPlannedMetamapResponse createGetPlanedMetamapResponse() {
        return new GetPlannedMetamapResponse();
    }

    /**
     * Create an instance of {@link GetLastPlannedMetamapResponse }
     * 
     */
    public GetLastPlannedMetamapResponse createGetLastPlanedMetamapResponse() {
        return new GetLastPlannedMetamapResponse();
    }

    /**
     * Create an instance of {@link GetPlannedMetamaps }
     * 
     */
    public GetPlannedMetamaps createGetPlanedMetamaps() {
        return new GetPlannedMetamaps();
    }

    /**
     * Create an instance of {@link GetPlannedMetamapsByLimitResponse }
     * 
     */
    public GetPlannedMetamapsByLimitResponse createGetPlanedMetamapsByLimitResponse() {
        return new GetPlannedMetamapsByLimitResponse();
    }

    /**
     * Create an instance of {@link GetLastPlannedMetamap }
     * 
     */
    public GetLastPlannedMetamap createGetLastPlanedMetamap() {
        return new GetLastPlannedMetamap();
    }

    /**
     * Create an instance of {@link Date }
     * 
     */
    public Date createDate() {
        return new Date();
    }

    /**
     * Create an instance of {@link PlannedMetamap }
     * 
     */
    public PlannedMetamap createPlanedMetamap() {
        return new PlannedMetamap();
    }

    /**
     * Create an instance of {@link Timestamp }
     * 
     */
    public Timestamp createTimestamp() {
        return new Timestamp();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetLastPlannedMetamap }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://gazprom_neft.ru/metamap/bean/", name = "getLastPlanedMetamap")
    public JAXBElement<GetLastPlannedMetamap> createGetLastPlanedMetamap(GetLastPlannedMetamap value) {
        return new JAXBElement<GetLastPlannedMetamap>(_GetLastPlanedMetamap_QNAME, GetLastPlannedMetamap.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetPlannedMetamapResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://gazprom_neft.ru/metamap/bean/", name = "getPlanedMetamapResponse")
    public JAXBElement<GetPlannedMetamapResponse> createGetPlanedMetamapResponse(GetPlannedMetamapResponse value) {
        return new JAXBElement<GetPlannedMetamapResponse>(_GetPlanedMetamapResponse_QNAME, GetPlannedMetamapResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetLastPlannedMetamapResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://gazprom_neft.ru/metamap/bean/", name = "getLastPlanedMetamapResponse")
    public JAXBElement<GetLastPlannedMetamapResponse> createGetLastPlanedMetamapResponse(GetLastPlannedMetamapResponse value) {
        return new JAXBElement<GetLastPlannedMetamapResponse>(_GetLastPlanedMetamapResponse_QNAME, GetLastPlannedMetamapResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetPlannedMetamaps }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://gazprom_neft.ru/metamap/bean/", name = "getPlanedMetamaps")
    public JAXBElement<GetPlannedMetamaps> createGetPlanedMetamaps(GetPlannedMetamaps value) {
        return new JAXBElement<GetPlannedMetamaps>(_GetPlanedMetamaps_QNAME, GetPlannedMetamaps.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetPlannedMetamapsByLimitResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://gazprom_neft.ru/metamap/bean/", name = "getPlanedMetamapsByLimitResponse")
    public JAXBElement<GetPlannedMetamapsByLimitResponse> createGetPlanedMetamapsByLimitResponse(GetPlannedMetamapsByLimitResponse value) {
        return new JAXBElement<GetPlannedMetamapsByLimitResponse>(_GetPlanedMetamapsByLimitResponse_QNAME, GetPlannedMetamapsByLimitResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetPlannedMetamap }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://gazprom_neft.ru/metamap/bean/", name = "getPlanedMetamap")
    public JAXBElement<GetPlannedMetamap> createGetPlanedMetamap(GetPlannedMetamap value) {
        return new JAXBElement<GetPlannedMetamap>(_GetPlanedMetamap_QNAME, GetPlannedMetamap.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetPlannedMetamapsByLimit }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://gazprom_neft.ru/metamap/bean/", name = "getPlanedMetamapsByLimit")
    public JAXBElement<GetPlannedMetamapsByLimit> createGetPlanedMetamapsByLimit(GetPlannedMetamapsByLimit value) {
        return new JAXBElement<GetPlannedMetamapsByLimit>(_GetPlanedMetamapsByLimit_QNAME, GetPlannedMetamapsByLimit.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetPlannedMetamapsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://gazprom_neft.ru/metamap/bean/", name = "getPlanedMetamapsResponse")
    public JAXBElement<GetPlannedMetamapsResponse> createGetPlanedMetamapsResponse(GetPlannedMetamapsResponse value) {
        return new JAXBElement<GetPlannedMetamapsResponse>(_GetPlanedMetamapsResponse_QNAME, GetPlannedMetamapsResponse.class, null, value);
    }

}
