/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.itsc.graphmleditor.config;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Koryakov.RV
 */
public class PropertiesManager {
    // properties
    public static final String COMMENT = "GraphML Editor settings";
    public static final String PROXY_ADDRESS = "ProxyAddress";
    public static final String PROXY_PORT = "ProxyPort";
    public static final String METAMAP_ID = "MetamapId";
    public static final String METAMAP_NAME = "MetamapName";
    public static final String SERVER_HOST = "ServerHost";
    public static final String USER_NAME = "UserName";
    public static final String USER_PASSWORD = "UserPassword";
    
    // default property values
    public static final String DEFAULT_METAMAP_ID = "-";
    public static final String DEFAULT_METAMAP_NAME = "HMI*";
    public static final String DEFAULT_SERVER_HOST = "http://sapdp7.gazprom-neft.local";
    public static final String DEFAULT_USER_NAME = "GUEST";
    public static final String DEFAULT_USER_PASSWORD = "";
     
    private static final Logger logger = Logger.getLogger(PropertiesManager.class.getName());
    
    private static final File appSettingsFile;
    static {
        appSettingsFile = new File(System.getProperty("user.home"), "gmleditor.cfg");
        if (!appSettingsFile.exists()) {
            try {
                appSettingsFile.createNewFile();
            } catch (Exception e) {
                logger.log(Level.SEVERE, e.getMessage(), e);
            }
        }
    }
    
    private static Properties properties;
    static {
        try {
            properties = loadProperties();
        } catch (IOException ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }
        
    public static void saveProperties(Properties props) throws FileNotFoundException, IOException {
        
        final Properties oldProps = loadProperties();
        
        props.forEach((k, v) -> {
            oldProps.put(k, v);
        });
        
        FileOutputStream fos = new FileOutputStream(appSettingsFile);
        oldProps.store(fos, COMMENT);
        properties = oldProps;
    }
    
    private static Properties loadProperties() throws FileNotFoundException, IOException {
        // check if we have saved setings
        final Properties result = new Properties();
        if (appSettingsFile.exists() && appSettingsFile.isFile()) {
            result.load(new FileInputStream(appSettingsFile));
        }
        return result;
    }
    
    private static Properties getProperties() {
        return properties;
    }
    
    public static String getPropertyValue(String key) {
        String result = null;

        switch (key) {
            case SERVER_HOST:
                result = getPropertyValue(SERVER_HOST, DEFAULT_SERVER_HOST);
                break;
            case METAMAP_ID:
                result = getPropertyValue(METAMAP_ID, DEFAULT_METAMAP_ID);
                break;
            case METAMAP_NAME:
                result = getPropertyValue(METAMAP_NAME, DEFAULT_METAMAP_NAME);
                break;
            case USER_NAME:
                result = getPropertyValue(USER_NAME, DEFAULT_USER_NAME);
                break;
            case USER_PASSWORD:
                result = getPropertyValue(USER_PASSWORD, DEFAULT_USER_PASSWORD);
                break;
        }

        return result;
    }
    
    private static String getPropertyValue(String key, String defaultValue) {
        String propertyValue = properties.getProperty(key);
        
        if (propertyValue == null || propertyValue.isEmpty()) {
            propertyValue = defaultValue;
        }
        return propertyValue;
    }
}
