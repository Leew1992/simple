package org.simple.resolver;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;

public class UserResolver {
	
	private static final String ROLE_PREFIX = "ROLE_";
	private UserResolver() {}
	
	/**
	 * 从权限中提取角色信息
	 */
	public static List<String> extractRolesFromAuthorities(Collection<? extends GrantedAuthority> authorities) {
		List<String> roleNames = new ArrayList<>();
		for(GrantedAuthority grantedAuthority : authorities) {
			String authority = grantedAuthority.getAuthority();
			// 转换权限为角色
			roleNames.add(authority.replace(ROLE_PREFIX, ""));
		}
		return roleNames;
	}
}
