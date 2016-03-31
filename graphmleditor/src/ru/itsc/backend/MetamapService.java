
package ru.itsc.backend;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.6 in JDK 6
 * Generated source version: 2.1
 * 
 */
@WebServiceClient(name = "MetamapService", targetNamespace = "http://gazprom_neft.ru/metamap/bean/", wsdlLocation = "http://sapdp7.gazprom-neft.local:50000/MetamapService/MetamapBean?wsdl")
public class MetamapService extends Service {

    private final static URL METAMAPSERVICE_WSDL_LOCATION;
    private final static QName METAMAPSERVICE_QNAME = new QName("http://gazprom_neft.ru/metamap/bean/", "MetamapService");
    private final static Logger logger = Logger.getLogger(MetamapService.class.getName());
    private final static String URL_QUERY_PART = "/MetamapService/MetamapBean?wsdl";
    
    static {
        URL url = null;
        try {
            URL baseUrl;
            baseUrl = MetamapService.class.getResource(".");
            url = new URL(baseUrl, "http://sapdp7.gazprom-neft.local:50000/MetamapService/MetamapBean?wsdl");
        } catch (MalformedURLException e) {
            logger.warning("Failed to create URL for the wsdl Location: 'http://sapdp7.gazprom-neft.local:50000/MetamapService/MetamapBean?wsdl', retrying as a local file");
            logger.warning(e.getMessage());
        }
        METAMAPSERVICE_WSDL_LOCATION = url;
    }

    public MetamapService(String hostUrl) throws MalformedURLException {
        super(new URL(hostUrl + URL_QUERY_PART), METAMAPSERVICE_QNAME);
    }

    public MetamapService() {
        super(METAMAPSERVICE_WSDL_LOCATION, METAMAPSERVICE_QNAME);
    }

    /**
     * 
     * @return
     *     returns MetamapBeanRemote
     */
    @WebEndpoint(name = "MetamapBeanPort")
    public MetamapBeanRemote getMetamapBeanPort() {
        return super.getPort(new QName("http://gazprom_neft.ru/metamap/bean/", "MetamapBeanPort"), MetamapBeanRemote.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns MetamapBeanRemote
     */
    @WebEndpoint(name = "MetamapBeanPort")
    public MetamapBeanRemote getMetamapBeanPort(WebServiceFeature... features) {
        return super.getPort(METAMAPSERVICE_QNAME, MetamapBeanRemote.class, features);
    }

}
