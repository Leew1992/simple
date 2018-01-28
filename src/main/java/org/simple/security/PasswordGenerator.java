package org.simple.security;

import org.apache.log4j.Logger;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;

public class PasswordGenerator {
	
	private static final Logger logger = Logger.getLogger(PasswordGenerator.class);
	
	private PasswordGenerator(){}
	
	public static void main(String[] args) {
		ShaPasswordEncoder shaPasswordEncoder = new ShaPasswordEncoder();
		String encPass = shaPasswordEncoder.encodePassword("123", "123");
		logger.info(encPass);
	}
}
