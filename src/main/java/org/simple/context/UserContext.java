package org.simple.context;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 用户上下文
 */
public class UserContext {
	private static final String UNKNOWN = "unknown";
	private static final String ROLE_PREFIX = "ROLE_";
	private static final String EMPTY_STRING = "";
	
	private UserContext(){}

	/**
	 * 获取当前用户
	 */
	public static String getCurrentUserName() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication != null) {
			Object principal = authentication.getPrincipal();
			if(principal instanceof UserDetails) {
				UserDetails userDetails = (UserDetails)principal;
				return userDetails.getUsername();
			} else if(principal instanceof String) {
				return (String)principal;
			} else {
				return UNKNOWN;
			}
		} else {
			return UNKNOWN;
		}
	}
	
	/**
	 * 获取当前用户的角色
	 */
	@SuppressWarnings("unchecked")
	public static List<String> listCurrentRoleNames() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		List<String> authorityList = new ArrayList<>();
		if(authentication != null) {
			List<GrantedAuthority> grantedAuthorityList = (List<GrantedAuthority>) authentication.getAuthorities();
			for(GrantedAuthority grantedAuthority : grantedAuthorityList) {
				String authority = grantedAuthority.getAuthority();
				// 转换权限为角色
				authorityList.add(authority.replace(ROLE_PREFIX, EMPTY_STRING));
			}
		}
		return authorityList;
	}
}
