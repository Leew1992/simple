package org.simple.controller;

import java.util.List;

import org.simple.dto.MenuDTO;
import org.simple.dto.ResultDTO;
import org.simple.dto.TreeNode;
import org.simple.entity.MenuDO;
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
	@RequestMapping("/getMenuTreeForMenuForm.do")
	@ResponseBody
	public List<TreeNode> getMenuTreeForMenuForm(String idRole) {
		return menuService.getMenuTreeForMenuForm(idRole);
	}
	
	/**
	 * 获取菜单树
	 */
	@RequestMapping("/getMenuTreeForMenuAssign.do")
	@ResponseBody
	public List<TreeNode> getMenuTreeForMenuAssign(String idSystem, String idMenu) {
		return menuService.getMenuTreeForMenuAssign(idSystem, idMenu);
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
	@RequestMapping("/enableMenus.do")
	@ResponseBody
	public ResultDTO enableMenus(String idMenus) {
		return menuService.enableMenus(idMenus);
	}
	
	/**
	 * 禁用菜单
	 */
	@RequestMapping("/disableMenus.do")
	@ResponseBody
	public ResultDTO disableMenus(String idMenus) {
		return menuService.disableMenus(idMenus);
	}
	
	/**
	 * 删除菜单
	 */
	@RequestMapping("/batchDeleteMenus.do")
	@ResponseBody
	public ResultDTO batchDeleteMenus(String idMenus) {
		return menuService.batchDeleteMenus(idMenus);
	}
}
