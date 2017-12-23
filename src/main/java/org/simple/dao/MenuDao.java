package org.simple.dao;

import java.util.List;
import java.util.Map;

import org.simple.dto.TreeNode;
import org.simple.entity.MenuDO;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuDao {
	
	/**
	 * 获取菜单信息
	 */
	MenuDO getMenuById(String idMenu);
	
	/**
	 * 获取菜单列表
	 */
	List<MenuDO> listMenusByIdParent(String idParent);
	
	/**
	 * 获取菜单列表
	 */
	List<MenuDO> listMenusByIdSystem(String idSystem);
	
	/**
	 * 获取菜单列表
	 */
	List<MenuDO> listMenusByIdRole(String idRole);
	
	/**
	 * 获取所有菜单
	 */
	List<MenuDO> listAllMenus();
	
	/**
	 * 获取菜单列表
	 */
	List<TreeNode> getMenuTreeForMenuAssign(Map<String, Object> paramMap);

	/**
	 * 获取菜单列表
	 */
	List<TreeNode> getMenuTreeForMenuForm(Map<String, Object> paramMap);
	
	/**
	 * 获取菜单列表
	 */
	void saveMenu(MenuDO menuDO);
	
	/**
	 * 获取菜单列表
	 */
	void updateMenu(MenuDO menuDO);
	
	/**
	 * 获取菜单列表
	 */
	void updateMenuStatus(Map<String, Object> paramMap);
	
	/**
	 * 批量获取菜单列表s
	 */
	void batchUpdateMenuStatuses(Map<String, Object> paramMap);
	
	/**
	 * 获取菜单列表
	 */
	void deleteMenu(String idMenu);

	/**
	 * 获取菜单列表
	 */
	void batchDeleteMenus(List<String> idMenus);
}
