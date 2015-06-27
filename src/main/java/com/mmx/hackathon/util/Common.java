package com.mmx.hackathon.util;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

/**
 *
 * @author Manindar
 */
public class Common {

    public PropertiesConfiguration getConfig() {
        PropertiesConfiguration config = null;
        try {
            config = new PropertiesConfiguration("conf.properties");
        } catch (ConfigurationException ex) {
            Logger.getLogger(Common.class.getName()).log(Level.SEVERE, null, ex);
        }
        return config;
    }
}
