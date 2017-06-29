package com.test.utils;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;

public class PropertiesDataProvider {

	public static String getTestData(String configFilePath, String string) {
		// TODO Auto-generated method stub
		Configuration config = null;
		try {
			config = new PropertiesConfiguration(configFilePath);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return String.valueOf(config.getProperty(string));
	}

}
