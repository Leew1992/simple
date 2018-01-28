package org.simple.service;

import java.util.List;

import org.simple.dto.PageDTO;
import org.simple.dto.QueryDTO;
import org.simple.dto.ResultDTO;
import org.simple.dto.RoleDTO;
import org.simple.dto.TreeNode;
import org.simple.entity.MenuDO;
import org.simple.entity.RoleDO;

/**
 * 角色服务接口
 *
 */
public interface RoleService {
	
	/**
	 * 获取角色信息
	 */
	RoleDO getRoleById(String idRole);
	
	/**
	 * 获取角色信息
	 */
	RoleDO getRoleByName(String roleName);
	
	/**
	 * 获取角色列表
	 */
	PageDTO pagingRoles(RoleDTO roleDTO, QueryDTO queryDTO);
	
	/**
     * 获取角色树
     */
    List<TreeNode> getRoleTreeForUserList(String idUser);
	
	/**
	 * 获取角色列表
	 */
	List<TreeNode> listRolesByIdUser(String idUser);
	
	/**
     * 获取有权限的菜单列表
     */
    List<MenuDO> listHasPermitedMenus(List<String> roleNames, String systemCode);
	
	/**
	 * 保存角色信息
	 */
	ResultDTO saveRole(RoleDTO roleDTO);
	
	/**
	 * 更新角色信息
	 */
	ResultDTO updateRole(RoleDTO roleDTO);
	
	/**
	 * 给角色分配菜单
	 */
	ResultDTO assignMenusForRole(String idRole, String idMenus);
	
	/**
	 * 给角色分配系统
	 */
	ResultDTO assignSystemsForRole(String idRole, String idSystems);
	
	/**
	 * 删除角色信息
	 */
	ResultDTO batchDeleteRoles(String idRoles);
	
}
