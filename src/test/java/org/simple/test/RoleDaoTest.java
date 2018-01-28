package org.simple.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.simple.dao.RoleDao;
import org.simple.dto.MenuItem;
import org.simple.dto.QueryDTO;
import org.simple.dto.RoleDTO;
import org.simple.entity.MenuDO;
import org.simple.entity.RoleDO;
import org.simple.entity.RoleMenuDO;
import org.simple.handler.MenuHandler;
import org.simple.service.RoleService;
import org.simple.util.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;

public class RoleDaoTest extends BaseTest {
	
	private final Logger logger = Logger.getLogger(getClass());
	
	@Autowired
    private RoleDao roleDao;
	
	@Autowired
	private RoleService roleService;

	/**
     * 获取角色信息
     */
	@Test
    public void testGetRoleById() {
    	String idRole = "0";
    	RoleDO roleDO = roleDao.getRoleById(idRole);
    	logger.debug(JSON.toJSONString(roleDO));
    }
    
    /**
     * 获取角色信息
     */
	@Test
    public void testGetRoleByName() {
    	String roleName = "aa";
    	RoleDO roleDO = roleDao.getRoleByName(roleName);
    	logger.debug(JSON.toJSONString(roleDO));
    }
    
    /**
     * 获取角色列表
     */
	@Test
    public void testListRolesByIdGroup() { 
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("idGroup", "0");
    	List<RoleDO> roleList = roleDao.listRolesByIdGroup(paramMap);
    	logger.debug(JSON.toJSON(roleList));
    }
    
    /**
     * 获取角色列表
     */
	@Test
    public void testListRolesByIdUser() {
    	String idUser = "0";
    	List<RoleDTO> roleDTOList = roleDao.listRolesByIdUser(idUser);
    	logger.debug(JSON.toJSONString(roleDTOList));
    }
    
    /**
     * 获取角色列表
     */
	@Test
    public void testPagingRoles() {
    	RoleDTO roleDTO = new RoleDTO();
    	roleDTO.setIdGroup("0");
    	QueryDTO queryDTO = new QueryDTO();
    	Map<String, Object> paramMap = BeanUtil.convertBeansToMap(roleDTO, queryDTO);
    	List<RoleDO> roleList = roleDao.pagingRoles(paramMap);
    	logger.debug(JSON.toJSONString(roleList));
    }
    
    /**
     * 保存角色信息
     */
	@Test
    public void testSaveRole() {
    	RoleDO roleDO = new RoleDO();
    	roleDO.setIdParent("0");
    	roleDO.setRoleName("aa");
    	roleDO.setRoleDesc("aa");
    	roleDO.setCreatedBy("aa");
    	roleDO.setCreatedDate(new Date());
    	roleDO.setUpdatedBy("aa");
    	roleDO.setUpdatedDate(new Date());
    	roleDao.saveRole(roleDO);
    	logger.debug("保存角色信息成功");
    }
    
    /**
     * 更新角色信息
     */
	@Test
    public void testUpdateRole() {
    	RoleDO roleDO = new RoleDO();
    	roleDO.setIdParent("0");
    	roleDO.setIdRole("0");
    	roleDO.setRoleName("aa");
    	roleDO.setRoleDesc("aa");
    	roleDO.setCreatedBy("aa");
    	roleDO.setCreatedDate(new Date());
    	roleDO.setUpdatedBy("aa");
    	roleDO.setUpdatedDate(new Date());
    	roleDao.updateRole(roleDO);
    	logger.debug("更新角色信息成功");
    }
    
    /**
     * 删除角色信息
     */
	@Test
    public void testDeleteRole() {
    	String idRole = "0";
    	roleDao.deleteRole(idRole);
    	logger.debug("删除角色信息成功");
    }
    
    /**
     * 批量删除角色信息
     */
	@Test
    public void testBatchDeleteRole() {
    	List<String> idRoles = new ArrayList<>();
    	idRoles.add("0");
    	idRoles.add("1");
    	roleDao.batchDeleteRoles(idRoles);
    	logger.debug("批量删除角色信息成功");
    }
    
    /**
     * 保存角色菜单
     */
	@Test
    public void testSaveRoleMenu() {
    	RoleMenuDO roleMenuDO = new RoleMenuDO();
    	roleMenuDO.setIdRole("0");
    	roleMenuDO.setIdMenu("0");
    	roleMenuDO.setCreatedBy("aa");
    	roleMenuDO.setCreatedDate(new Date());
    	roleMenuDO.setUpdatedBy("aa");
    	roleMenuDO.setUpdatedDate(new Date());
    	roleDao.saveRoleMenu(roleMenuDO);
    	logger.debug("保存角色菜单信息成功");
    }
    
    /**
     * 删除角色菜单
     */
	@Test
    public void testDeleteRoleMenu() {
    	String idRole = "0";
    	roleDao.deleteRoleMenusByIdRole(idRole);
    	logger.debug("删除角色菜单信息成功");
    }
	
	/**
	 * 删除角色菜单
	 */
	@Test
	public void deleteCanceledRoleMenu() {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("idRole", "284661a1efd211e78716047d7b0f457f");
		List<String> idSystemList = new ArrayList<>();
		idSystemList.add("538cc0bfa2c011e780ce047d7b0f457f");
		paramMap.put("idSystems", idSystemList);
		roleDao.deleteCanceledRoleMenu(paramMap);
	}
    
    /**
     * 批量删除角色菜单
     */
	@Test
    public void testBatchDeleteRoleMenus() {
    	List<String> idRoles = new ArrayList<>();
    	idRoles.add("0");
    	idRoles.add("1");
    	roleDao.batchDeleteRoleMenus(idRoles);
    	logger.debug("批量删除角色菜单信息成功");
    }
	
	@Test
	public void testListHasPermitedMenus() {
		List<String> roleNames = new ArrayList<>();
		roleNames.add("ADMIN");
		List<MenuDO> menuList = roleService.listHasPermitedMenus(roleNames, "simple");
		List<MenuDO> orderedMenuList = MenuHandler.orderMenus("0", menuList);
		Map<String, List<MenuItem>> moduleMap = MenuHandler.formatOrderedMenus(orderedMenuList);
		logger.debug(JSON.toJSONString(moduleMap));
	}
}