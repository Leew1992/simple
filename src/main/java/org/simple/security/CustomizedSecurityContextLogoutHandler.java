package org.simple.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.simple.context.UserContext;
import org.simple.entity.MonitorAccessDO;
import org.simple.entity.UserDO;
import org.simple.service.MonitorService;
import org.simple.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;

public class CustomizedSecurityContextLogoutHandler extends SecurityContextLogoutHandler {

	private static final String LOGOUT = "logout";
	
	private final Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	private MonitorService monitorService;
	@Autowired
	private UserService userService;
	
	public void logout(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) {
		recordLogoutLog(request);
		super.logout(request, response, authentication);
	}
	
	/**
	 * 记录退出日志
	 */
	private void recordLogoutLog(HttpServletRequest request) {
		String userName = UserContext.getCurrentUserName();
		UserDO userDO = userService.getUserByName(userName);
		String requestURI = StringUtils.delete(request.getRequestURI(), request.getContextPath());
		
		MonitorAccessDO monitorAccessDO = new MonitorAccessDO();
		monitorAccessDO.setIdUser(userDO.getIdUser());
		monitorAccessDO.setAccessModule(LOGOUT);
		monitorAccessDO.setRequestURI(requestURI);
		monitorAccessDO.setRemoteAddress(request.getRemoteAddr());
		
		logger.debug("开始记录退出日志...");
		logger.debug("日志参数信息：" + JSON.toJSONString(monitorAccessDO));
		monitorService.saveMonitorAccess(monitorAccessDO);
		logger.debug("完成记录退出日志...");
	}
}
