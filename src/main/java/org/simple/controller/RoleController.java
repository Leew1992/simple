package org.simple.controller;

import java.util.List;

import org.simple.dto.PageDTO;
import org.simple.dto.QueryDTO;
import org.simple.dto.ResultDTO;
import org.simple.dto.RoleDTO;
import org.simple.dto.TreeNode;
import org.simple.entity.RoleDO;
import org.simple.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 角色Controller
 */
@Controller
@RequestMapping("/role")
public class RoleController {
	
	@Autowired
	private RoleService roleService;
	
	/**
	 * 获取角色信息
	 */
	@RequestMapping("/getRoleById.do")
	@ResponseBody
	public RoleDO getRoleById(String idRole) {
		return roleService.getRoleById(idRole);
	}
	
	/**
	 * 获取角色列表
	 */
	@RequestMapping("/listRolesByIdUser.do")
	@ResponseBody
	public List<TreeNode> listRolesByIdUser(String idUser) {
		return roleService.listRolesByIdUser(idUser);
	}
	
	/**
	 * 获取角色分页列表
	 */
	@RequestMapping("/pagingRoles.do")
	@ResponseBody
	public PageDTO pagingRoles(RoleDTO roleDTO, QueryDTO queryDTO) {
		return roleService.pagingRoles(roleDTO, queryDTO);
	}
	
	/**
	 * 获取角色树
	 */
	@RequestMapping("/getRoleTreeForUserList.do")
	@ResponseBody
	public List<TreeNode> getRoleTreeForUserList(String idUser) {
		return roleService.getRoleTreeForUserList(idUser);
	}
	
	/**
	 * 保存角色信息
	 */
	@RequestMapping("/saveRole.do")
	@ResponseBody
	public ResultDTO saveRole(RoleDTO roleDTO) {
		return roleService.saveRole(roleDTO);
	}
	
	/**
	 * 更新角色信息
	 */
	@RequestMapping("/updateRole.do")
	@ResponseBody
	public ResultDTO updateRole(RoleDTO roleDTO) {
		return roleService.updateRole(roleDTO);
	}
	
	/**
	 * 分配菜单信息
	 */
	@RequestMapping("/assignMenusForRole.do")
	@ResponseBody
	public ResultDTO assignMenusForRole(String idRole, String idMenus) {
		return roleService.assignMenusForRole(idRole, idMenus);
	}
	
	/**
	 * 分配菜单信息
	 */
	@RequestMapping("/assignSystemsForRole.do")
	@ResponseBody
	public ResultDTO assignSystemsForRole(String idRole, String idSystems) {
		return roleService.assignSystemsForRole(idRole, idSystems);
	}
	
	/**
	 * 删除角色信息
	 */
	@RequestMapping("/batchDeleteRoles.do")
	@ResponseBody
	public ResultDTO batchDeleteRoles(String idRoles) {
		return roleService.batchDeleteRoles(idRoles);
	}
}
