package org.simple.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

public class RequestHeaderProcessingFilter extends AbstractAuthenticationProcessingFilter {
	private String usernameHeader = "username";
	private String passwordHeader = "password";
	private String signatureHeader = "signature";

	protected RequestHeaderProcessingFilter() {
		super("/sso_login");
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		String username = request.getHeader(usernameHeader);
		String password = request.getHeader(passwordHeader);
		String signature = request.getHeader(signatureHeader);
		SignedUsernamePasswordAuthenticationToken authRequest = new SignedUsernamePasswordAuthenticationToken(username,
				password, signature);
		return this.getAuthenticationManager().authenticate(authRequest);
	}
	// getters and setters omitted below
}