package org.simple.controller;

import org.simple.entity.UserDO;
import org.simple.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;

@Controller
public class BaseController {
	
	@Autowired
	private UserService userService;
	
	/**
	 * 获取当前用户
	 */
	protected UserDO getCurrentUser() {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return userService.getUserByName(userDetails.getUsername());
	}
}
