package org.simple.service.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.simple.constant.MessageConst;
import org.simple.context.UserContext;
import org.simple.dao.MenuDao;
import org.simple.dao.RoleDao;
import org.simple.dao.SystemDao;
import org.simple.dto.MenuDTO;
import org.simple.dto.ResultDTO;
import org.simple.dto.TreeNode;
import org.simple.entity.MenuDO;
import org.simple.entity.RoleSystemDO;
import org.simple.entity.SystemMenuDO;
import org.simple.handler.MenuHandler;
import org.simple.service.MenuService;
import org.springframework.stereotype.Service;

@Service
public class MenuServiceImpl implements MenuService {
	
	private static final String ID_SYSTEM = "idSystem";
	
	@Resource
	private MenuDao menuDao;

	@Resource
	private SystemDao systemDao;

	@Resource
	private RoleDao roleDao;
	
	@Override
	public MenuDO getMenuById(String idMenu) {
		return menuDao.getMenuById(idMenu);
	}
	
	@Override
	public MenuDTO getMenuDetailById(String idMenu) {
		return menuDao.getMenuDetailById(idMenu);
	}
	
	@Override
	public List<MenuDO> listMenusByIdParent(String idParent) {
		return menuDao.listMenusByIdParent(idParent);
	}
	
	@Override
	public List<MenuDO> listMenusByIdSystem(String idSystem) {
		List<MenuDO> menuList = menuDao.listMenusByIdSystem(idSystem);
		return MenuHandler.orderMenus("0", menuList);
	}
	@Override
	public List<MenuDO> listAllMenus() {
		List<MenuDO> menuList = menuDao.listAllMenus();
		return MenuHandler.orderMenus("0", menuList);
	}
	
	@Override
	public Map<String, List<TreeNode>> getHasCheckedMenuTreeByIdRole(String idRole) {
		List<RoleSystemDO> roleSystemList = roleDao.listRoleSystemsByIdRole(idRole);
		Map<String, List<TreeNode>> resultMap = new HashMap<>();
		for(RoleSystemDO roleSystemDO : roleSystemList) {
			Map<String, Object> paramMap = new HashMap<>();
			String idSystem = roleSystemDO.getIdSystem();
			paramMap.put("idRole", idRole);
			paramMap.put(ID_SYSTEM, idSystem);
			List<TreeNode> treeList = menuDao.getHasCheckedMenuTreeByIdRoleAndIdSystem(paramMap);
			resultMap.put(idSystem, treeList);
		}
		return resultMap;
	}
	
	@Override
	public List<TreeNode> getHasCheckedMenuTreeByIdSystemAndIdMenu(String idSystem, String idMenu) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put(ID_SYSTEM, idSystem);
		paramMap.put("idMenu", idMenu);
		return menuDao.getHasCheckedMenuTreeByIdSystemAndIdMenu(paramMap);
	}
	
	@Override
	public List<TreeNode> getHasCheckedMenuTreeByIdRoleAndIdSystem(String idRole, String idSystem) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("idRole", idRole);
		paramMap.put(ID_SYSTEM, idSystem);
		return menuDao.getHasCheckedMenuTreeByIdRoleAndIdSystem(paramMap);
	}
	
	@Override
	public ResultDTO saveMenu(MenuDTO menuDTO) {
		menuDTO.setCreatedBy(UserContext.getCurrentUserName());
		menuDTO.setUpdatedBy(UserContext.getCurrentUserName());
		menuDTO.setIsEnabled(true);
		menuDao.saveMenu(menuDTO);
		
		SystemMenuDO systemMenuDO = new SystemMenuDO();
		systemMenuDO.setIdSystem(menuDTO.getIdSystem());
		systemMenuDO.setIdMenu(menuDTO.getIdMenu());
		systemMenuDO.setCreatedBy(UserContext.getCurrentUserName());
		systemMenuDO.setUpdatedBy(UserContext.getCurrentUserName());
		systemDao.saveSystemMenu(systemMenuDO);
		
		return new ResultDTO(true,MessageConst.SAVE_SUCCESS);
	}
	
	@Override
	public ResultDTO saveSubMenu(MenuDO menuDO) {
		menuDO.setCreatedBy(UserContext.getCurrentUserName());
		menuDO.setUpdatedBy(UserContext.getCurrentUserName());
		menuDO.setIsEnabled(true);
		menuDao.saveMenu(menuDO);

		SystemMenuDO systemMenuDO = systemDao.getSystemMenuByIdMenu(menuDO.getIdParent());
		SystemMenuDO newSystemMenu = new SystemMenuDO();
		newSystemMenu.setIdSystem(systemMenuDO.getIdSystem());
		newSystemMenu.setIdMenu(menuDO.getIdMenu());
		newSystemMenu.setCreatedBy(UserContext.getCurrentUserName());
		newSystemMenu.setUpdatedBy(UserContext.getCurrentUserName());
		systemDao.saveSystemMenu(newSystemMenu);
		
		return new ResultDTO(true,MessageConst.SAVE_SUCCESS);
	}
	
	@Override
	public ResultDTO updateMenu(MenuDO menuDO) {
		menuDO.setUpdatedBy(UserContext.getCurrentUserName());
		menuDao.updateMenu(menuDO);
		return new ResultDTO(true,MessageConst.UPDATE_SUCCESS);
	}
	
	@Override
	public ResultDTO batchEnableMenus(String idMenus) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("enabled", "1");
		paramMap.put("idMenus", Arrays.asList(idMenus));
		menuDao.batchUpdateMenuEnableds(paramMap);
		return new ResultDTO(true,MessageConst.ENABLE_SUCCESS);
	}
	
	@Override
	public ResultDTO batchDisableMenus(String idMenus) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("enabled", false);
		paramMap.put("idMenus", Arrays.asList(idMenus));
		menuDao.batchUpdateMenuEnableds(paramMap);
		return new ResultDTO(true,MessageConst.DISABLE_SUCCESS);
	}
	
	@Override
	public ResultDTO deleteMenu(String idMenu) {
		systemDao.deleteSystemMenuByIdMenu(idMenu);
		roleDao.deleteRoleMenusByIdMenu(idMenu);
		menuDao.deleteMenu(idMenu);
		return new ResultDTO(true,MessageConst.DELETE_SUCCESS);
	}
	
	@Override
	public ResultDTO batchDeleteMenus(String idMenus) {
		String[] idMenuArr = idMenus.split(",");
		for(String idMenu : idMenuArr) {
			systemDao.deleteSystemMenuByIdMenu(idMenu);
			roleDao.deleteRoleMenusByIdMenu(idMenu);
			menuDao.deleteMenu(idMenu);
		}
		return new ResultDTO(true,MessageConst.DELETE_SUCCESS);
	}

}
