package org.simple.security;

import org.springframework.security.authentication.encoding.ShaPasswordEncoder;

public class PasswordGenerator {
	public static void main(String[] args) {
		ShaPasswordEncoder shaPasswordEncoder = new ShaPasswordEncoder();
		String encPass = shaPasswordEncoder.encodePassword("123", "123");
		System.out.println(encPass);
	}
}
