package com.leap.properties;

import java.util.logging.Logger;

import org.jasypt.intf.service.JasyptStatelessService;

public class TestEncryptorProperties {
	private static final Logger logger = Logger.getLogger(TestEncryptorProperties.class.getName());
	
	
	public static void main(String[] args) {
	   	try {
	   		testPasswordValue();
	   		testGenerateEncriptValue();
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}

	public static void testPasswordValue() {
   		PropertyHandler.loadProperties("cfg/integrity.xml");
   		logger.info("password="+PropertyHandler.getProperty("password"));
   		System.out.println("password="+PropertyHandler.getProperty("password"));
	}

	
	public static void testGenerateEncriptValue() {
		String input = "ptc"; // <<== this is the value
		String password = "pwkey";
		String algorithm = "PBEWITHMD5ANDDES";
   		
   		JasyptStatelessService service = new JasyptStatelessService();
   		String encriptedValue =  service.encrypt(input, password, null, null, algorithm, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
   		logger.info("encriptedValue="+encriptedValue);
   		System.out.println("encriptedValue="+encriptedValue);
	}
}
