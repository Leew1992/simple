package org.simple.listener;

import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSessionEvent;

import org.apache.log4j.Logger;
import org.simple.cache.UserCache;
import org.simple.constant.SysCodeConst;
import org.simple.context.UserContext;
import org.simple.entity.MenuDO;
import org.simple.service.RoleService;
import org.simple.service.impl.RoleServiceImpl;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.alibaba.fastjson.JSON;

/**
 * 用户权限加载监听器
 */
public class CustomizedHttpSessionEventPublisher extends HttpSessionEventPublisher implements ServletContextListener  {
	
	private final Logger logger = Logger.getLogger(getClass());
	private RoleService roleService;
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		roleService =  WebApplicationContextUtils.getWebApplicationContext(sce.getServletContext()).getBean(RoleServiceImpl.class);
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		logger.info("上下文被废弃");
	}

	@Override
	public void sessionCreated(HttpSessionEvent event) {
		super.sessionCreated(event);
		String userName = UserContext.getCurrentUserName();
		List<String> roleNames = UserContext.listCurrentRoleNames();
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
	public void sessionDestroyed(HttpSessionEvent event) {
		super.sessionDestroyed(event);
		String userName = UserContext.getCurrentUserName();
		logger.debug("当前用户：" + userName);
		logger.debug("开始清除缓存中的菜单信息...");
		UserCache.clearMenusFromCache(userName);
		logger.debug("完成清除缓存中的菜单信息...");
	}

}
