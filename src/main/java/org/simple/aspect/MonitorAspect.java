package org.simple.aspect;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.simple.annotation.MonitorAccess;
import org.simple.entity.MonitorAccessDO;
import org.simple.entity.UserDO;
import org.simple.service.MonitorService;
import org.simple.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSON;

/**
 * 用户监控拦截器
 */
public class MonitorAspect extends HandlerInterceptorAdapter {
	
	private final Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private MonitorService monitorService;

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		if (!(handler instanceof HandlerMethod)) {
			return;
		}

		HandlerMethod handlerMethod = (HandlerMethod) handler;
		Method method = handlerMethod.getMethod();

		// 记录访问监控信息
		final MonitorAccess monitorAccess = method.getAnnotation(MonitorAccess.class);
		if(monitorAccess != null) {
			String requestURI = StringUtils.delete(request.getRequestURI(), request.getContextPath());
			String accessModule = requestURI.split("/")[1];
			UserDO userDO = getCurrentUser();
			MonitorAccessDO monitorAccessDO = new MonitorAccessDO();
			monitorAccessDO.setIdUser(userDO.getIdUser());
			monitorAccessDO.setAccessModule(accessModule);
			monitorAccessDO.setRequestURI(requestURI);
			monitorAccessDO.setRemoteAddress(request.getRemoteAddr());
			logger.info("开始记录监控访问日志...");
			logger.info("日志参数信息：" + JSON.toJSONString(monitorAccessDO));
			monitorService.saveMonitorAccess(monitorAccessDO);
			logger.info("完成记录监控访问日志...");
		}
	}
	
	/**
	 * 获取用户信息
	 */
	private UserDO getCurrentUser() {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return userService.getUserByName(userDetails.getUsername());
	}
}