package org.simple.controller;

import java.util.List;
import java.util.Map;

import org.simple.dto.MenuDTO;
import org.simple.dto.ResultDTO;
import org.simple.dto.TreeNode;
import org.simple.entity.MenuDO;
import org.simple.handler.MenuHandler;
import org.simple.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 菜单控制器
 */
@Controller
@RequestMapping("/menu")
public class MenuController {

	@Autowired
	private MenuService menuService;
	
	/**
	 * 获取菜单信息
	 */
	@RequestMapping("/getMenuById.do")
	@ResponseBody
	public MenuDO getMenuById(String idMenu) {
		return menuService.getMenuById(idMenu);
	}
	
	/**
	 * 获取菜单明细信息
	 */
	@RequestMapping("/getMenuDetailById.do")
	@ResponseBody
	public MenuDTO getMenuDetailById(String idMenu) {
		return menuService.getMenuDetailById(idMenu);
	}
	
	/**
	 * 获取菜单列表
	 * @return 
	 */
	@RequestMapping("/listMenusByIdSystem.do")
	@ResponseBody
	public List<MenuDO> listMenusByIdSystem(String idSystem) {
		return menuService.listMenusByIdSystem(idSystem);
	}
	
	/**
	 * 获取所有菜单
	 */
	@RequestMapping("/listAllMenus.do")
	@ResponseBody
	public List<MenuDO> listAllMenus() {
		return menuService.listAllMenus();
	}
	
	/**
	 * 获取菜单树
	 */
	@RequestMapping("/getHasCheckedMenuTreeByIdSystemAndIdMenu.do")
	@ResponseBody
	public List<TreeNode> getHasCheckedMenuTreeByIdSystemAndIdMenu(String idSystem, String idMenu) {
		return menuService.getHasCheckedMenuTreeByIdSystemAndIdMenu(idSystem, idMenu);
	}
	
	/**
	 * 获取菜单树
	 */
	@RequestMapping("/getHasCheckedMenuTreeByIdRoleAndIdSystem.do")
	@ResponseBody
	public List<TreeNode> getHasCheckedMenuTreeByIdRoleAndIdSystem(String idRole, String idSystem) {
		return menuService.getHasCheckedMenuTreeByIdRoleAndIdSystem(idRole, idSystem);
	}
	
	/**
	 * 获取菜单树
	 */
	@RequestMapping("/getHasCheckedMenuTreeByIdRole.do")
	@ResponseBody
	public Map<String, List<TreeNode>> getHasCheckedMenuTreeByIdRole(String idRole) {
		return menuService.getHasCheckedMenuTreeByIdRole(idRole);
	}
	
	/**
	 * 生成月份树
	 */
	@RequestMapping("/getMonthMenuTree.do")
	@ResponseBody
	public List<TreeNode> getMonthMenuTree(String startMonth) {
		return MenuHandler.generateMonthMenuTree(startMonth);
	}
	
	/**
	 * 保存菜单
	 */
	@RequestMapping("/saveMenu.do")
	@ResponseBody
	public ResultDTO saveMenu(MenuDTO menuDTO) {
		return menuService.saveMenu(menuDTO);
	}
	
	/**
	 * 更新菜单
	 */
	@RequestMapping("/updateMenu.do")
	@ResponseBody
	public ResultDTO updateMenu(MenuDTO menuDTO) {
		return menuService.updateMenu(menuDTO);
	}
	
	/**
	 * 保存子菜单
	 */
	@RequestMapping("/saveSubMenu.do")
	@ResponseBody
	public ResultDTO saveSubMenu(MenuDO menuDO) {
		return menuService.saveSubMenu(menuDO);
	}
	
	/**
	 * 启用菜单
	 */
	@RequestMapping("/batchEnableMenus.do")
	@ResponseBody
	public ResultDTO batchEnableMenus(String idMenus) {
		return menuService.batchEnableMenus(idMenus);
	}
	
	/**
	 * 禁用菜单
	 */
	@RequestMapping("/batchDisableMenus.do")
	@ResponseBody
	public ResultDTO batchDisableMenus(String idMenus) {
		return menuService.batchDisableMenus(idMenus);
	}
	
	/**
	 * 删除菜单
	 */
	@RequestMapping("/deleteMenuById.do")
	@ResponseBody
	public ResultDTO deleteMenuById(String idMenus) {
		return menuService.batchDeleteMenus(idMenus);
	}
	
	/**
	 * 批量删除菜单
	 */
	@RequestMapping("/batchDeleteMenus.do")
	@ResponseBody
	public ResultDTO batchDeleteMenus(String idMenus) {
		return menuService.batchDeleteMenus(idMenus);
	}
}
