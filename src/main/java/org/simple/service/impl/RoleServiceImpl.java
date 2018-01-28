package org.simple.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.simple.constant.MessageConst;
import org.simple.constant.SysCodeConst;
import org.simple.context.UserContext;
import org.simple.dao.GroupDao;
import org.simple.dao.RoleDao;
import org.simple.dto.PageDTO;
import org.simple.dto.QueryDTO;
import org.simple.dto.ResultDTO;
import org.simple.dto.RoleDTO;
import org.simple.dto.TreeNode;
import org.simple.entity.GroupRoleDO;
import org.simple.entity.MenuDO;
import org.simple.entity.RoleDO;
import org.simple.entity.RoleMenuDO;
import org.simple.entity.RoleSystemDO;
import org.simple.service.RoleService;
import org.simple.util.BeanUtil;
import org.springframework.stereotype.Service;

/**
 * 角色信息ServiceImpl
 */
@Service
public class RoleServiceImpl implements RoleService {
	
	@Resource
	private RoleDao roleDao;
	
	@Resource
	private GroupDao groupDao;
	
	@Override
	public RoleDO getRoleById(String idGroup) {
		return roleDao.getRoleById(idGroup);
	}
	
	@Override
	public RoleDO getRoleByName(String roleName) {
		return roleDao.getRoleByName(roleName);
	}
	
	@Override
	public List<TreeNode> listRolesByIdUser(String idUser) {
		List<RoleDTO> roleList = roleDao.listRolesByIdUser(idUser);
		List<TreeNode> treeNodeList = new ArrayList<>();
		for(RoleDTO roleDTO : roleList) {
			TreeNode treeNode = new TreeNode();
			treeNode.setId(roleDTO.getIdRole());
			treeNode.setpId(roleDTO.getIdParent());
			treeNode.setName(roleDTO.getRoleName());
			treeNode.setDesc(roleDTO.getRoleDesc());
			treeNode.setOpen(true);
			treeNode.setChecked(roleDTO.getChecked());
			treeNodeList.add(treeNode);
		}
		return treeNodeList;
	}
	
	@Override
	public PageDTO pagingRoles(RoleDTO roleDTO, QueryDTO queryDTO) {
		Map<String, Object> paramMap = BeanUtil.convertBeansToMap(roleDTO, queryDTO);
		List<RoleDO> roleList = roleDao.pagingRoles(paramMap);
		PageDTO page = new PageDTO();
		page.setRows(roleList);
		page.setTotal(roleList.size());
		return page;
	}
	
	/**
     * 获取角色树
     */
	@Override
    public List<TreeNode> getRoleTreeForUserList(String idUser) {
    	return roleDao.getRoleTreeForUserList(idUser);
    }
	
	/**
	 * 获取有权限的菜单列表
	 */
	@Override
	public List<MenuDO> listHasPermitedMenus(List<String> roleNames, String systemCode) {
		List<MenuDO> menuList = new ArrayList<>();
		if(roleNames != null && !roleNames.isEmpty()) {
			Map<String, Object> paramMap = new HashMap<>();
			paramMap.put("roleNames", roleNames);
			paramMap.put("systemCode", SysCodeConst.CURRENT_SYSTEM_CODE);
			RoleSystemDO roleSystem = roleDao.getHasPermitedRoleSystem(paramMap);
		
			Map<String, Object> menuMap = new HashMap<>();
			menuMap.put("idRole", roleSystem.getIdRole());
			menuMap.put("idSystem", roleSystem.getIdSystem());
			menuList = roleDao.listHasPermitedMenus(menuMap);			
		}
		return menuList;
	}
	
	@Override
	public ResultDTO assignMenusForRole(String idRole, String idMenus) {
		// 删除用户角色信息
		roleDao.deleteRoleMenusByIdRole(idRole);
		
		String[] idMenuArr = idMenus.split(",");
		for(String idMenu : idMenuArr) {
			RoleMenuDO roleMenuDO = new RoleMenuDO();
			roleMenuDO.setIdRole(idRole);
			roleMenuDO.setIdMenu(idMenu);
			roleMenuDO.setCreatedBy(UserContext.getCurrentUserName());
			roleMenuDO.setUpdatedBy(UserContext.getCurrentUserName());
			roleDao.saveRoleMenu(roleMenuDO);
		}
		return new ResultDTO(true, MessageConst.ASSIGN_SUCCESS);
	}
	
	@Override
	public ResultDTO assignSystemsForRole(String idRole, String idSystems) {
		// 删除用户角色信息
		roleDao.deleteRoleSystemsByIdRole(idRole);
		// 删除被取消的角色菜单
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("idRole", idRole);
		paramMap.put("idSystems", Arrays.asList(idSystems.split(",")));
		roleDao.deleteCanceledRoleMenu(paramMap);
		
		String[] idSystemArr = idSystems.split(",");
		for(String idSystem : idSystemArr) {
			RoleSystemDO roleSystemDO = new RoleSystemDO();
			roleSystemDO.setIdRole(idRole);
			roleSystemDO.setIdSystem(idSystem);
			roleSystemDO.setCreatedBy(UserContext.getCurrentUserName());
			roleSystemDO.setUpdatedBy(UserContext.getCurrentUserName());
			roleDao.saveRoleSystem(roleSystemDO);
		}
		return new ResultDTO(true, MessageConst.ASSIGN_SUCCESS);
	}
	
	@Override
	public ResultDTO saveRole(RoleDTO roleDTO) {
		// 用户信息
		roleDTO.setCreatedBy(UserContext.getCurrentUserName());
		roleDTO.setUpdatedBy(UserContext.getCurrentUserName());
		roleDao.saveRole(roleDTO);
		
		// 字典-用户
		GroupRoleDO groupRoleDO = new GroupRoleDO();
		groupRoleDO.setIdGroup(roleDTO.getIdGroup());
		groupRoleDO.setIdRole(roleDTO.getIdRole());
		groupRoleDO.setCreatedBy(UserContext.getCurrentUserName());
		groupRoleDO.setUpdatedBy(UserContext.getCurrentUserName());
		groupDao.saveGroupRole(groupRoleDO);
		return new ResultDTO(true, MessageConst.SAVE_SUCCESS);
	}
	
	@Override
	public ResultDTO updateRole(RoleDTO roleDTO) {
		// 用户信息
		roleDTO.setCreatedBy(UserContext.getCurrentUserName());
		roleDTO.setUpdatedBy(UserContext.getCurrentUserName());
		roleDao.updateRole(roleDTO);
		groupDao.deleteGroupRoleByIdRole(roleDTO.getIdRole());
		
		// 字典-用户
		GroupRoleDO groupRoleDO = new GroupRoleDO();
		groupRoleDO.setIdGroup(roleDTO.getIdGroup());
		groupRoleDO.setIdRole(roleDTO.getIdRole());
		groupRoleDO.setCreatedBy(UserContext.getCurrentUserName());
		groupRoleDO.setUpdatedBy(UserContext.getCurrentUserName());
		groupDao.saveGroupRole(groupRoleDO);
		return new ResultDTO(true, MessageConst.UPDATE_SUCCESS);
	}

	@Override
	public ResultDTO batchDeleteRoles(String idRoles) {
		String[] idRoleArr = idRoles.split(",");
		roleDao.batchDeleteRoles(Arrays.asList(idRoleArr));
		roleDao.batchDeleteRoleMenus(Arrays.asList(idRoleArr));
		groupDao.batchDeleteGroups(Arrays.asList(idRoleArr));
		return new ResultDTO(true, MessageConst.DELETE_SUCCESS);
	}

}
