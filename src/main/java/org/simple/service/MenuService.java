package org.simple.service;

import java.util.List;
import java.util.Map;

import org.simple.dto.MenuDTO;
import org.simple.dto.ResultDTO;
import org.simple.dto.TreeNode;
import org.simple.entity.MenuDO;

/**
 * 菜单Service接口
 */
public interface MenuService {
	
	/**
	 * 获取菜单信息
	 */
	MenuDO getMenuById(String idMenu);
	
	/**
	 * 获取菜单明细信息
	 */
	MenuDTO getMenuDetailById(String idMenu);
	
	/**
	 * 获取菜单列表
	 */
	List<MenuDO> listMenusByIdParent(String idParent);
	
	/**
	 * 获取菜单列表
	 */
	List<MenuDO> listMenusByIdSystem(String idSystem);
	
	/**
	 * 获取所有菜单
	 */
	List<MenuDO> listAllMenus();
	
	/**
	 * 获取菜单列表
	 */
	Map<String, List<TreeNode>> getHasCheckedMenuTreeByIdRole(String idRole);
	
	/**
	 * 获取菜单列表
	 */
	List<TreeNode> getHasCheckedMenuTreeByIdRoleAndIdSystem(String idRole, String idSystem);

	/**
	 * 获取菜单列表
	 */
	List<TreeNode> getHasCheckedMenuTreeByIdSystemAndIdMenu(String idSystem, String idMenu);
	
	/**
	 * 保存菜单信息
	 */
	ResultDTO saveMenu(MenuDTO menuDTO);
	
	/**
	 * 保存子菜单信息
	 */
	ResultDTO saveSubMenu(MenuDO menuDO);
	
	/**
	 * 更新菜单信息
	 */
	ResultDTO updateMenu(MenuDO menuDO);
	
	/**
	 * 批量启用菜单
	 */
	ResultDTO batchEnableMenus(String idMenus);
	
	/**
	 * 批量禁用菜单
	 */
	ResultDTO batchDisableMenus(String idMenus);
	
	/**
	 * 获取菜单列表
	 */
	ResultDTO deleteMenu(String idMenu);

	/**
	 * 获取菜单列表
	 */
	ResultDTO batchDeleteMenus(String idMenus);
	
}
