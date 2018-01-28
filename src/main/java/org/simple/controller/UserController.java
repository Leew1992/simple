package org.simple.controller;

import org.simple.annotation.MonitorAccess;
import org.simple.dto.PageDTO;
import org.simple.dto.QueryDTO;
import org.simple.dto.ResultDTO;
import org.simple.dto.UserDTO;
import org.simple.entity.UserDO;
import org.simple.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 用户Controller
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("/getCurrentUser.do")
	@ResponseBody
	public UserDO getCurrentUser() {
		return super.getCurrentUser();
	}
	
	/**
	 * 获取用户信息
	 */
	@RequestMapping("/getUserById.do")
	@ResponseBody
	public UserDO getUserById(String idUser) {
		return userService.getUserById(idUser);
	}
	
	/**
	 * 获取用户分页列表
	 */
	@MonitorAccess
	@RequestMapping("/pagingUsers.do")
	@ResponseBody
	public PageDTO pagingUsers(UserDTO userDTO, QueryDTO queryDTO) {
		return userService.pagingUsers(userDTO, queryDTO);
	}
	
	/**
	 * 保存用户信息
	 */
	@MonitorAccess
	@RequestMapping("/saveUser.do")
	@ResponseBody
	public ResultDTO saveUser(UserDTO userDTO) {
		return userService.saveUser(userDTO);
	}
	
	/**
	 * 更新用户信息
	 */
	@MonitorAccess
	@RequestMapping("/updateUser.do")
	@ResponseBody
	public ResultDTO updateUser(UserDTO userDTO) {
		return userService.updateUser(userDTO);
	}
	
	/**
	 * 分配角色
	 */
	@MonitorAccess
	@RequestMapping("/assignRolesForUserList.do")
	@ResponseBody
	public ResultDTO assignRolesForUserList(String idUser, String idRoles) {
		return userService.assignRolesForUserList(idUser, idRoles);
	}
	
	/**
	 * 删除用户信息
	 */
	@MonitorAccess
	@RequestMapping("/batchDeleteUsers.do")
	@ResponseBody
	public ResultDTO batchDeleteUsers(String idUsers) {
		return userService.batchDeleteUsers(idUsers);
	}
}
