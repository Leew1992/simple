package org.simple.security;

import java.io.IOException;

import javax.servlet.ServletException;
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
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;

public class CustomizedForwardAuthenticationSuccessHandler implements AuthenticationSuccessHandler  {
	
	private static final String LOGIN = "login";
	
	private final String forwardUrl;
	private final Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	private MonitorService monitorService;
	@Autowired
	private UserService userService;

	public CustomizedForwardAuthenticationSuccessHandler(String forwardUrl) {
		Assert.isTrue(UrlUtils.isValidRedirectUrl(forwardUrl), "'"
				+ forwardUrl + "' is not a valid forward URL");
		this.forwardUrl = forwardUrl;
	}
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		request.getRequestDispatcher(forwardUrl).forward(request, response);
		recordLoginLog(request);
	}
	
	/**
	 * 记录登录日志
	 */
	private void recordLoginLog(HttpServletRequest request) {
		String userName = UserContext.getCurrentUserName();
		UserDO userDO = userService.getUserByName(userName);
		String requestURI = StringUtils.delete(request.getRequestURI(), request.getContextPath());
		
		MonitorAccessDO monitorAccessDO = new MonitorAccessDO();
		monitorAccessDO.setIdUser(userDO.getIdUser());
		monitorAccessDO.setAccessModule(LOGIN);
		monitorAccessDO.setRequestURI(requestURI);
		monitorAccessDO.setRemoteAddress(request.getRemoteAddr());
		
		logger.debug("开始记录登陆日志...");
		logger.debug("日志参数信息：" + JSON.toJSONString(monitorAccessDO));
		monitorService.saveMonitorAccess(monitorAccessDO);
		logger.debug("完成记录登陆日志...");
	}

}
