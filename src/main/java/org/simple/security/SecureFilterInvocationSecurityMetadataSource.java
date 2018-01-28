package org.simple.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class SecureFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
	protected final Log logger = LogFactory.getLog(getClass());
	private final Map<RequestMatcher, Collection<ConfigAttribute>> requestMap;

	public SecureFilterInvocationSecurityMetadataSource() {
		requestMap = new LinkedHashMap<>();
		
		AntPathRequestMatcher userMatcher = new AntPathRequestMatcher("/**");
		Collection<ConfigAttribute> userCas = new ArrayList<>();
		ConfigAttribute userCa = new SecurityConfig("REQUIRES_SECURE_CHANNEL");
		userCas.add(userCa);
		requestMap.put(userMatcher, userCas);
	}

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		Set allAttributes = new HashSet();

		for (Map.Entry entry : this.requestMap.entrySet()) {
			allAttributes.addAll((Collection) entry.getValue());
		}

		return allAttributes;
	}

	@Override
	public Collection<ConfigAttribute> getAttributes(Object object) {
		HttpServletRequest request = ((FilterInvocation) object).getRequest();
		for (Map.Entry entry : this.requestMap.entrySet()) {
			if (((RequestMatcher) entry.getKey()).matches(request)) {
				return (Collection) entry.getValue();
			}
		}
		return new ArrayList<>();
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return FilterInvocation.class.isAssignableFrom(clazz);
	}
}
