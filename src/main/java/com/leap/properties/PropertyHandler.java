package com.leap.properties;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.properties.EncryptableProperties;

public class PropertyHandler {

	private static final Logger logger = Logger.getLogger(PropertyHandler.class.getName());
	private static Properties props;
	private static String filename;
	private static String encriptKey = "pwkey"; // <== this is the encryption key

	private PropertyHandler() {
	}

	public static void loadProperties(String propFile) {
		
		// this is for description password with 'http://www.jasypt.org' 
		StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();     
		encryptor.setPassword(encriptKey);     

		props = new EncryptableProperties(encryptor);
		filename = propFile;
		try {
			if (propFile.contains(".xml")) {
				props.loadFromXML(new FileInputStream(filename));
			} else {
				props.load(new FileInputStream(filename));
			}
			
		} catch (IOException ioe) {
			logger.log(Level.SEVERE, ioe.toString(), ioe);
		}
	}

	public static void saveProperties() {
		try {
			props.store(new FileOutputStream(filename), null);
		} catch (IOException ioe) {
			logger.log(Level.SEVERE, ioe.toString(), ioe);
		}
	}

	public static String getProperty(String key) {
		return props.getProperty(key);
	}

	public static String getProperty(String key, String defaultValue) {
		if (key == null) return defaultValue;
		return props.getProperty(key);
	}
	
	public static int getIntProperty(String key) {
		int pVal = 0;
		String val = PropertyHandler.getProperty(key);
		if (val == null) return pVal;
		pVal = java.lang.Integer.parseInt(val);

		return pVal;
	}

	public static boolean getBooleanProperty(String key ) {
		boolean pVal = false;
		String val = PropertyHandler.getProperty(key);
		if (val == null) return false;
		
		pVal = "true".equals(val) ? true : false ;

		return pVal;
	} 
	
	public static void setProperty(String key, String value) {
		props.setProperty(key, value);
	}
}
