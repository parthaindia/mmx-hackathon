package com.mmx.hackathon.util;

import java.lang.reflect.Method;
import java.util.Map;
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

    public Object mapToDto(Map<String, String> inputMap, Class c) throws Exception {
        if (inputMap == null) {
            return null;
        }
        Object dtoObject = c.newInstance();
        Method methods[] = c.getDeclaredMethods();
        for (Method method : methods) {
            if (method.getName().startsWith("set")) {
                String fieldName = method.getName().replace("set", "");
                method.invoke(dtoObject, inputMap.get(fieldName.toLowerCase()));
            }
        }
        return dtoObject;
    }
}
