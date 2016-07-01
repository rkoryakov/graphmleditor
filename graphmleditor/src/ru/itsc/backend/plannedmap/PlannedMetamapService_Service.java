
package ru.itsc.backend.plannedmap;

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
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "PlannedMetamapService", targetNamespace = "http://gazprom_neft.ru/metamap/bean/", wsdlLocation = "http://sapdp7.gazprom-neft.local:50000/PlanedMetamapService/PlanedMetamapBean?wsdl")
public class PlannedMetamapService_Service extends Service {

    private final static URL PLANEDMETAMAPSERVICE_WSDL_LOCATION;
    private final static WebServiceException PLANEDMETAMAPSERVICE_EXCEPTION;
    private final static QName PLANEDMETAMAPSERVICE_QNAME = new QName("http://gazprom_neft.ru/metamap/bean/", "PlanedMetamapService");
    private final static String URL_QUERY_PART = "/PlanedMetamapService/PlanedMetamapBean?wsdl";
    
    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://sapdp7.gazprom-neft.local:50000/PlanedMetamapService/PlanedMetamapBean?wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        PLANEDMETAMAPSERVICE_WSDL_LOCATION = url;
        PLANEDMETAMAPSERVICE_EXCEPTION = e;
    }

    public PlannedMetamapService_Service(String hostUrl) throws MalformedURLException {
        super(new URL(hostUrl + URL_QUERY_PART), PLANEDMETAMAPSERVICE_QNAME);
    }
    
    public PlannedMetamapService_Service() {
        super(__getWsdlLocation(), PLANEDMETAMAPSERVICE_QNAME);
    }

    public PlannedMetamapService_Service(WebServiceFeature... features) {
        super(__getWsdlLocation(), PLANEDMETAMAPSERVICE_QNAME, features);
    }

    public PlannedMetamapService_Service(URL wsdlLocation) {
        super(wsdlLocation, PLANEDMETAMAPSERVICE_QNAME);
    }

    public PlannedMetamapService_Service(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, PLANEDMETAMAPSERVICE_QNAME, features);
    }

    public PlannedMetamapService_Service(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public PlannedMetamapService_Service(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns PlannedMetamapService
     */
    @WebEndpoint(name = "PlanedMetamapPort")
    public PlannedMetamapService getPlanedMetamapPort() {
        return super.getPort(new QName("http://gazprom_neft.ru/metamap/bean/", "PlanedMetamapPort"), PlannedMetamapService.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns PlannedMetamapService
     */
    @WebEndpoint(name = "PlanedMetamapPort")
    public PlannedMetamapService getPlanedMetamapPort(WebServiceFeature... features) {
        return super.getPort(new QName("http://gazprom_neft.ru/metamap/bean/", "PlanedMetamapPort"), PlannedMetamapService.class, features);
    }

    private static URL __getWsdlLocation() {
        if (PLANEDMETAMAPSERVICE_EXCEPTION!= null) {
            throw PLANEDMETAMAPSERVICE_EXCEPTION;
        }
        return PLANEDMETAMAPSERVICE_WSDL_LOCATION;
    }

}