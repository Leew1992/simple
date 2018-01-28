package org.simple.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.simple.annotation.MonitorAccess;
import org.simple.cache.UserCache;
import org.simple.context.UserContext;
import org.simple.dto.MenuItem;
import org.simple.dto.UserDTO;
import org.simple.entity.MenuDO;
import org.simple.handler.MenuHandler;
import org.simple.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/portle")
public class PortleController {
	
	@Autowired
	private UserService userService;
	
	/**
	 * 注册用户
	 */
	@MonitorAccess
	@RequestMapping("/register.do")
	public String register(UserDTO userDTO) {
		userService.register(userDTO);
		return "redirect:/login.html";
	}
	
	/**
	 * 生成菜单
	 */
	@RequestMapping("/generateMenus.do")
	@ResponseBody
	public Map<String, List<MenuItem>> generateMenus() {
		Map<String, List<MenuItem>> moduleMap = new HashMap<String, List<MenuItem>>();
		String currentUserName = UserContext.getCurrentUserName();
		Map<String, List<MenuDO>> menuMap = UserCache.getMenuCache();
		List<MenuDO> menuList = menuMap.get(currentUserName);
		if(menuList != null && !menuList.isEmpty()) {
			List<MenuDO> orderedMenuList = MenuHandler.orderMenus("0", menuList);
			moduleMap = MenuHandler.formatOrderedMenus(orderedMenuList);			
		}
		return moduleMap;
	}
}
