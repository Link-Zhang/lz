package com.shch.lz.ssm.util;

import java.util.Date;
import java.util.HashMap;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Created by Link at 22:20 on 3/28/18.
 */
public class PropertiesFileUtil {
    private static HashMap<String, PropertiesFileUtil> configMap = new HashMap<String, PropertiesFileUtil>();
    private Date loadTime = null;

    private ResourceBundle resourceBundle = null;

    private static final String NAME = "config";

    private static final Integer TIME_OUT = 60 * 1000;

    // Singleton
    private PropertiesFileUtil(String name) {
        this.loadTime = new Date();
        this.resourceBundle = ResourceBundle.getBundle(name);
    }

    public static synchronized PropertiesFileUtil getInstance(String name) {
        PropertiesFileUtil config = configMap.get(name);
        if (null == config) {
            config = new PropertiesFileUtil(name);
            configMap.put(name, config);
        }
        if ((System.currentTimeMillis() - config.getLoadTime().getTime() > TIME_OUT)) {
            // time out
            config = new PropertiesFileUtil(name);
            configMap.put(name, config);
        }
        return config;
    }

    public static synchronized PropertiesFileUtil getInstance() {
        return getInstance(NAME);
    }

    public String get(String key) {
        try {
            String value = resourceBundle.getString(key);
            return value;
        } catch (MissingResourceException exception) {
            return "";
        }
    }

    public Integer getInteger(String key) {
        try {
            String value = resourceBundle.getString(key);
            return Integer.parseInt(value);
        } catch (MissingResourceException e) {
            return null;
        }
    }

    public boolean getBool(String key) {
        try {
            String value = resourceBundle.getString(key);
            if ("true".equals(value)) {
                return true;
            }
            return false;
        } catch (MissingResourceException e) {
            return false;
        }
    }

    public Date getLoadTime() {
        return this.loadTime;
    }
}
