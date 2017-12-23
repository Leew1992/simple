package org.simple.service;

import java.util.List;

import org.simple.dto.MenuDTO;
import org.simple.dto.ResultDTO;
import org.simple.dto.TreeNode;
import org.simple.entity.MenuDO;

/**
 * 菜单Service接口
 */
public interface MenuService {
	
	MenuDO getMenuById(String idMenu);
	
	List<MenuDO> listMenusByIdParent(String idParent);
	
	List<MenuDO> listMenusByIdSystem(String idSystem);
	
	List<MenuDO> listAllMenus();
	
	List<TreeNode> getMenuTreeForMenuForm(String idRole);
	
	List<TreeNode> getMenuTreeForMenuAssign(String idSystem, String idMenu);

	ResultDTO saveMenu(MenuDTO menuDTO);
	
	ResultDTO saveSubMenu(MenuDO menuDO);

	ResultDTO updateMenu(MenuDO menuDO);
	
	ResultDTO batchDeleteMenus(String idMenus);

	ResultDTO enableMenus(String idMenus);
	
	ResultDTO disableMenus(String idMenus);
	
}
