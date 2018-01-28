package org.simple.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.simple.dao.MenuDao;
import org.simple.dto.MenuItem;
import org.simple.dto.TreeNode;
import org.simple.entity.MenuDO;
import org.simple.handler.MenuHandler;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;

public class MenuDaoTest extends BaseTest {
	
	private final Logger logger = Logger.getLogger(getClass());
	
	@Autowired
    private MenuDao menuDao;

	/**
	 * 获取菜单信息
	 */
	@Test
	public void testGetMenuById() {
		String idMenu = "0";
		MenuDO menuDO =	menuDao.getMenuById(idMenu);
		logger.debug(JSON.toJSONString(menuDO));
	}
	
	/**
	 * 获取菜单信息
	 */
	@Test
	public void testGetMenuDetailById() {
		String idMenu = "af32cf38f16111e78716047d7b0f457f";
		MenuDO menuDO =	menuDao.getMenuDetailById(idMenu);
		logger.debug(JSON.toJSONString(menuDO));
	}
	
	/**
	 * 获取菜单列表
	 */
	@Test
	public void testListMenusByIdParent() {
		String idParent = "0";
		List<MenuDO> menuList = menuDao.listMenusByIdParent(idParent);
		logger.debug(JSON.toJSONString(menuList));
	}
	
	/**
	 * 获取菜单列表
	 */
	@Test
	public void testListMenusByIdSystem() {
		String idSystem = "0";
		List<MenuDO> menuList = menuDao.listMenusByIdSystem(idSystem);
		logger.debug(JSON.toJSONString(menuList));
	}
	
	/**
	 * 获取菜单列表
	 */
	@Test
	public void testListMenusByIdRole() {
		String idRole = "0";
		List<MenuDO> menuList = menuDao.listMenusByIdRole(idRole);
		logger.debug(JSON.toJSONString(menuList));
	}
	
	/**
	 * 获取菜单树
	 */
	@Test
	public void testGetMenuTreeForMenuAssign() {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("idRole", "0");
		paramMap.put("idSystem", "0");
		List<TreeNode>  treeNodeList = menuDao.getMenuTreeForMenuAssign(paramMap);
		logger.debug(JSON.toJSONString(treeNodeList));
	}

	/**
	 * 获取菜单树
	 */
	@Test
	public void testGetMenuTreeForMenuForm() {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("idSystem", "0");
		paramMap.put("idMenu", "0");
		List<TreeNode>  treeNodeList = menuDao.getHasCheckedMenuTreeByIdSystemAndIdMenu(paramMap);
		logger.debug(JSON.toJSONString(treeNodeList));
	}
	
	@Test
	public void testListAllMenu() {
		List<MenuDO> allMenus =  menuDao.listAllMenus();
		List<MenuDO> orderedMenus = MenuHandler.orderMenus("0", allMenus);
		Map<String, List<MenuItem>> moduleMap = MenuHandler.formatOrderedMenus(orderedMenus);
		logger.debug(JSON.toJSONString(moduleMap));
	}
	
	/**
	 * 保存菜单信息
	 */
	@Test
	public void testSaveMenu() {
		MenuDO menuDO = new MenuDO();
		menuDO.setIdParent("0");
		menuDO.setMenuName("aa");
		menuDO.setMenuDesc("aa");
		menuDO.setMenuUrl("aa");
		menuDO.setIsEnabled(true);
		menuDO.setCreatedBy("aa");
		menuDO.setCreatedDate(new Date());
		menuDO.setUpdatedBy("aa");
		menuDO.setUpdatedDate(new Date());
		menuDao.saveMenu(menuDO);
		logger.debug("菜单信息保存成功");
	}
	
	/**
	 * 更新菜单信息
	 */
	@Test
	public void testUpdateMenu() {
		MenuDO menuDO = new MenuDO();
		menuDO.setIdMenu("0");
		menuDO.setIdParent("0");
		menuDO.setMenuName("bb");
		menuDO.setMenuDesc("bb");
		menuDO.setMenuUrl("bb");
		menuDO.setIsEnabled(true);
		menuDO.setCreatedBy("bb");
		menuDO.setCreatedDate(new Date());
		menuDO.setUpdatedBy("bb");
		menuDO.setUpdatedDate(new Date());
		menuDao.updateMenu(menuDO);
		logger.debug("菜单信息更新成功");
	}
	
	/**
	 * 更新菜单状态
	 */
	@Test
	public void testUpdateMenuStatus() {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("idMenu", "0");
		paramMap.put("enabled", "0");
		menuDao.updateMenuEnabled(paramMap);
		logger.debug("菜单状态更新成功");
	}
	
	/**
	 * 删除菜单列表
	 */
	@Test
	public void testDeleteMenu() {
		String idMenu = "0";
		menuDao.deleteMenu(idMenu);
		logger.debug("菜单信息删除成功");
	}

	/**
	 * 批量删除菜单信息
	 */
	@Test
	public void testBatchDeleteMenus() {
		List<String> idMenus = new ArrayList<>();	
		idMenus.add("0");
		idMenus.add("1");
		menuDao.batchDeleteMenus(idMenus);
		logger.debug("菜单信息批量删除成功");
	}
}