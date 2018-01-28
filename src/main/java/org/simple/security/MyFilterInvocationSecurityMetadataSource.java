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
public class MyFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
	protected final Log logger = LogFactory.getLog(getClass());
	private final Map<RequestMatcher, Collection<ConfigAttribute>> requestMap;

	public MyFilterInvocationSecurityMetadataSource() {
		requestMap = new LinkedHashMap<>();
		
		AntPathRequestMatcher loginMatcher = new AntPathRequestMatcher("/login*");
		AntPathRequestMatcher registerMatcher = new AntPathRequestMatcher("/user/register*");
		Collection<ConfigAttribute> anonyCas = new ArrayList<>();
		ConfigAttribute anonyCa = new SecurityConfig("ROLE_ANONYMOUS");
		anonyCas.add(anonyCa);
		requestMap.put(loginMatcher, anonyCas);
		requestMap.put(registerMatcher, anonyCas);
		
		AntPathRequestMatcher indexMatcher = new AntPathRequestMatcher("/index.do");
		AntPathRequestMatcher groupMatcher = new AntPathRequestMatcher("/group/*");
		AntPathRequestMatcher userMatcher = new AntPathRequestMatcher("/user/*");
		AntPathRequestMatcher roleMatcher = new AntPathRequestMatcher("/role/*");
		AntPathRequestMatcher systemMatcher = new AntPathRequestMatcher("/system/*");
		AntPathRequestMatcher menuMatcher = new AntPathRequestMatcher("/menu/*");
		AntPathRequestMatcher columnMatcher = new AntPathRequestMatcher("/column/*");
		AntPathRequestMatcher postMatcher = new AntPathRequestMatcher("/post/*");
		AntPathRequestMatcher categoryMatcher = new AntPathRequestMatcher("/category/*");
		AntPathRequestMatcher commentMatcher = new AntPathRequestMatcher("/comment/*");
		Collection<ConfigAttribute> userCas = new ArrayList<>();
		ConfigAttribute userCa = new SecurityConfig("ROLE_USER");
		userCas.add(userCa);
		requestMap.put(indexMatcher, userCas);
		requestMap.put(groupMatcher, userCas);
		requestMap.put(userMatcher, userCas);
		requestMap.put(roleMatcher, userCas);
		requestMap.put(systemMatcher, userCas);
		requestMap.put(menuMatcher, userCas);
		requestMap.put(columnMatcher, userCas);
		requestMap.put(postMatcher, userCas);
		requestMap.put(categoryMatcher, userCas);
		requestMap.put(commentMatcher, userCas);
		
		AntPathRequestMatcher adminMatcher = new AntPathRequestMatcher("/config/*");
		Collection<ConfigAttribute> adminCas = new ArrayList<>();
		ConfigAttribute adminCa = new SecurityConfig("ROLE_ADMIN");
		adminCas.add(adminCa);
		requestMap.put(adminMatcher, adminCas);
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
