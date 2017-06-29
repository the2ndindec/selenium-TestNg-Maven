package com.test.plugins;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

import org.apache.log4j.Logger;

public class ConfigReader {

	private static Logger logger = Logger.getLogger(ConfigReader.class);
    private static ConfigReader cr;
    private int retryCount = 0;
    private String sourceCodeDir = "src";
    private String sourceCodeEncoding = "UTF-8";
    private static final String RETRYCOUNT = "retrycount";
    private static final String SOURCEDIR = "sourcecodedir";
    private static final String SOURCEENCODING = "sourcecodeencoding";
    private static final String CONFIGFILE = "./config/config.properties";
    
    private ConfigReader() {
        readConfig(CONFIGFILE);
    }
    
    public static ConfigReader getInstance() {
        if (cr == null) {
            cr = new ConfigReader();
        }
        return cr;
    }

    private void readConfig(String fileName) {
		// TODO Auto-generated method stub
    	Properties properties = getConfig(fileName);
    	if (properties != null) {
			String sRetryCount = null;
			Enumeration<?> en = properties.propertyNames();
			while (en.hasMoreElements()) {
				String keyString = (String) en.nextElement();
				if (keyString.toLowerCase().equals(RETRYCOUNT)) {
					sRetryCount = properties.getProperty(keyString);
				}
				if (keyString.toLowerCase().equals(SOURCEDIR)) {
					sourceCodeDir = properties.getProperty(keyString);
				}
				if (keyString.toLowerCase().equals(SOURCEENCODING)) {
					sourceCodeEncoding = properties.getProperty(keyString);
				}
			}
			if (sRetryCount != null) {
				sRetryCount = sRetryCount.trim();
				try {
					retryCount = Integer.parseInt(sRetryCount);
				} catch (final NumberFormatException e) {
					// TODO: handle exception
					throw new NumberFormatException("Parse " + RETRYCOUNT + " [" + sRetryCount + "] from String to Int Exception");
				}
			}
		}
	}

    public int getRetryCount() {
		return this.retryCount;
	}
    
    public String getSourceCodeDir() {
		return this.sourceCodeDir;
	}
    
    public String getSourceCodeEncoding() {
		return this.sourceCodeEncoding;
	}
    
	private Properties getConfig(String fileName) {
		// TODO Auto-generated method stub
		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream(fileName));
		} catch (FileNotFoundException e) {
			// TODO: handle exception
			properties = null;
			logger.warn("FileNotFoundException:" + fileName);
		}catch (IOException e) {
			// TODO: handle exception
			properties = null;
            logger.warn("IOException:" + fileName);
		}
		return properties;
	}
}
