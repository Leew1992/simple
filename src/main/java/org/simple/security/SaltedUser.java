package org.simple.security;

import org.simple.dto.SaltedUserDTO;
import org.springframework.security.core.userdetails.User;

@SuppressWarnings("serial")
public class SaltedUser extends User {
	private String salt;

	public SaltedUser(SaltedUserDTO saltedUserDTO) {
		super(saltedUserDTO.getUsername(), saltedUserDTO.getPassword(), saltedUserDTO.getEnabled(), saltedUserDTO.getAccountNonExpired(), saltedUserDTO.getCredentialsNonExpired(), saltedUserDTO.getAccountNonLocked(), saltedUserDTO.getAuthorities());
		this.salt = saltedUserDTO.getSalt();
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}
}