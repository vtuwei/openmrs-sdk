package org.openmrs.maven.plugins.utility;

import org.apache.commons.io.IOUtils;
import org.apache.maven.plugin.logging.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Class for reading/writing .properties files
 */
public class PropertyManager {
    private Properties properties;
    private String path;
    private Log log;

    private PropertyManager() { properties = new Properties(); }

    /**
     * Initialize
     * @param filePath - .properties file path
     */
    public PropertyManager(String filePath, Log log) {
        this();
        this.log = log;
        this.path = filePath;
        File config = new File(path);
        if (config.exists()) {
            FileInputStream in = null;
            try {
                in = new FileInputStream(path);
                properties.load(in);
                in.close();
            } catch (IOException e) {
                log.error("Error while reading properties");
            } finally {
                IOUtils.closeQuietly(in);
            }
        }
    }

    /**
     * Get param from properties
     * @param key - property key
     * @return - property value
     */
    public String getParam(String key) {
        return properties.getProperty(key);
    }

    /**
     * Set param to properties object (without applying)
     * @param key - property key
     * @param value - value to set
     */
    public void setParam(String key, String value) {
        properties.setProperty(key, value);
    }

    /**
     * Write properties to .property file
     */
    public void apply() {
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(path);
            properties.store(out, null);
            out.close();
        } catch (IOException e) {
            log.error(e.getMessage());
        } finally {
            IOUtils.closeQuietly(out);
        }
    }

    /**
     * Set property and apply it
     * @param key - property key
     * @param value - value to set
     */
    public void applyParam(String key, String value) {
        setParam(key, value);
        apply();
    }
}