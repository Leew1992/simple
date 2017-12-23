package org.simple.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.simple.constant.MessageConsts;
import org.simple.dao.MenuDao;
import org.simple.dao.SystemDao;
import org.simple.dto.MenuDTO;
import org.simple.dto.ResultDTO;
import org.simple.dto.TreeNode;
import org.simple.entity.MenuDO;
import org.simple.entity.SystemMenuDO;
import org.simple.service.MenuService;
import org.springframework.stereotype.Service;

@Service
public class MenuServiceImpl implements MenuService {
	
	@Resource
	private MenuDao menuDao;
	
	@Resource
	private SystemDao systemDao;
	
	@Override
	public MenuDO getMenuById(String idMenu) {
		return menuDao.getMenuById(idMenu);
	}
	
	@Override
	public List<MenuDO> listMenusByIdParent(String idParent) {
		return menuDao.listMenusByIdParent(idParent);
	}
	
	@Override
	public List<MenuDO> listMenusByIdSystem(String idSystem) {
		return menuDao.listMenusByIdSystem(idSystem);
	}
	@Override
	public List<MenuDO> listAllMenus() {
		return menuDao.listAllMenus();
	}
	
	@Override
	public List<TreeNode> getMenuTreeForMenuForm(String idRole) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("idSystem", "0");
		paramMap.put("idRole", idRole);
		return menuDao.getMenuTreeForMenuForm(paramMap);
	}
	
	@Override
	public List<TreeNode> getMenuTreeForMenuAssign(String idSystem, String idMenu) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("idSystem", idSystem);
		paramMap.put("idMenu", idMenu);
		return menuDao.getMenuTreeForMenuAssign(paramMap);
	}

	@Override
	public ResultDTO saveMenu(MenuDTO menuDTO) {
		menuDTO.setCreatedBy("system");
		menuDTO.setCreatedDate(new Date());
		menuDTO.setUpdatedBy("system");
		menuDTO.setUpdatedDate(new Date());
		menuDTO.setStatus("1");
		menuDao.saveMenu(menuDTO);
		
		SystemMenuDO systemMenuDO = new SystemMenuDO();
		systemMenuDO.setIdSystem(menuDTO.getIdSystem());
		systemMenuDO.setIdMenu(menuDTO.getIdMenu());
		systemMenuDO.setCreatedBy("system");
		systemMenuDO.setCreatedDate(new Date());
		systemMenuDO.setUpdatedBy("system");
		systemMenuDO.setUpdatedDate(new Date());
		systemDao.saveSystemMenu(systemMenuDO);
		
		return new ResultDTO(true,MessageConsts.SAVE_SUCCESS);
	}
	
	@Override
	public ResultDTO saveSubMenu(MenuDO menuDO) {
		menuDO.setCreatedBy("system");
		menuDO.setCreatedDate(new Date());
		menuDO.setUpdatedBy("system");
		menuDO.setUpdatedDate(new Date());
		menuDO.setStatus("1");
		menuDao.saveMenu(menuDO);

		SystemMenuDO systemMenuDO = systemDao.getSystemMenuByIdMenu(menuDO.getIdParent());
		SystemMenuDO newSystemMenu = new SystemMenuDO();
		newSystemMenu.setIdSystem(systemMenuDO.getIdSystem());
		newSystemMenu.setIdMenu(menuDO.getIdMenu());
		newSystemMenu.setCreatedBy("system");
		newSystemMenu.setCreatedDate(new Date());
		newSystemMenu.setUpdatedBy("system");
		newSystemMenu.setUpdatedDate(new Date());
		systemDao.saveSystemMenu(newSystemMenu);
		
		return new ResultDTO(true,MessageConsts.SAVE_SUCCESS);
	}
	
	@Override
	public ResultDTO updateMenu(MenuDO menuDO) {
		menuDO.setCreatedBy("system");
		menuDO.setCreatedDate(new Date());
		menuDO.setUpdatedBy("system");
		menuDO.setUpdatedDate(new Date());
		menuDao.updateMenu(menuDO);
		return new ResultDTO(true,MessageConsts.UPDATE_SUCCESS);
	}
	
	@Override
	public ResultDTO enableMenus(String idMenus) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("status", "1");
		paramMap.put("idMenus", Arrays.asList(idMenus));
		menuDao.batchUpdateMenuStatuses(paramMap);
		return new ResultDTO(true,MessageConsts.ENABLE_SUCCESS);
	}
	
	@Override
	public ResultDTO disableMenus(String idMenus) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("status", "0");
		paramMap.put("idMenus", Arrays.asList(idMenus));
		menuDao.batchUpdateMenuStatuses(paramMap);
		return new ResultDTO(true,MessageConsts.DISABLE_SUCCESS);
	}
	
	@Override
	public ResultDTO batchDeleteMenus(String idMenus) {
		String[] idMenuArr = idMenus.split(",");
		menuDao.batchDeleteMenus(Arrays.asList(idMenuArr));
		return new ResultDTO(true,MessageConsts.DELETE_SUCCESS);
	}
	
}
