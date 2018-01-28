package org.simple.security;

import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.simple.cache.UserCache;
import org.simple.constant.SysCodeConst;
import org.simple.context.UserContext;
import org.simple.entity.MenuDO;
import org.simple.resolver.UserResolver;
import org.simple.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetails;

import com.alibaba.fastjson.JSON;

public class CustomizedSessionRegistryImpl extends SessionRegistryImpl {

	private final Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	private RoleService roleService;
	
	@Override
	public void registerNewSession(String sessionId, Object principal) {
		super.registerNewSession(sessionId, principal);
		UserDetails userDetails = (UserDetails)principal;
		String userName = userDetails.getUsername();
		Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
		List<String> roleNames = UserResolver.extractRolesFromAuthorities(authorities);
		logger.debug("当前用户：" + userName);
		logger.debug("当前权限：" + roleNames);
		logger.debug("开始添加菜单信息到缓存...");
		if(roleService != null) {
			List<MenuDO> menuList = roleService.listHasPermitedMenus(roleNames, SysCodeConst.CURRENT_SYSTEM_CODE);
			UserCache.storeMenusToCache(userName, menuList);
			logger.debug("当前菜单：" + JSON.toJSONString(menuList));
		}
		logger.debug("完成添加菜单信息到缓存...");
	}
	
	@Override
	public void removeSessionInformation(String sessionId) {
		super.removeSessionInformation(sessionId);
		String userName = UserContext.getCurrentUserName();
		logger.debug("当前用户：" + userName);
		logger.debug("开始清除缓存中的菜单信息...");
		UserCache.clearMenusFromCache(userName);
		logger.debug("完成清除缓存中的菜单信息...");
	}
}
