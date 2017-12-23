package org.simple.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.simple.constant.MessageConsts;
import org.simple.dao.GroupDao;
import org.simple.dao.RoleDao;
import org.simple.dto.PageDTO;
import org.simple.dto.ResultDTO;
import org.simple.dto.RoleDTO;
import org.simple.dto.TreeNode;
import org.simple.entity.GroupRoleDO;
import org.simple.entity.RoleDO;
import org.simple.entity.RoleMenuDO;
import org.simple.service.RoleService;
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
		List<TreeNode> treeNodeList = new ArrayList<TreeNode>();
		for(RoleDTO roleDTO : roleList) {
			TreeNode treeNode = new TreeNode();
			treeNode.setId(roleDTO.getIdRole());
			treeNode.setpId(roleDTO.getIdParent());
			treeNode.setName(roleDTO.getRoleName());
			treeNode.setDesc(roleDTO.getRoleDesc());
			treeNode.setOpen("true");
			treeNode.setChecked(roleDTO.getChecked());
			treeNodeList.add(treeNode);
		}
		return treeNodeList;
	}
	
	@Override
	public PageDTO pagingRoles(RoleDTO roleDTO) {
		List<RoleDO> roleList = roleDao.pagingRoles(roleDTO);
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
	
	@Override
	public ResultDTO assignMenusForRoleList(String idRole, String idMenus) {
		// 删除用户角色信息
		roleDao.deleteRoleMenu(idRole);
		
		String[] idMenuArr = idMenus.split(",");
		for(String idMenu : idMenuArr) {
			RoleMenuDO roleMenuDO = new RoleMenuDO();
			roleMenuDO.setIdRole(idRole);
			roleMenuDO.setIdMenu(idMenu);
			roleMenuDO.setCreatedBy("system");
			roleMenuDO.setCreatedDate(new Date());
			roleMenuDO.setUpdatedBy("system");
			roleMenuDO.setUpdatedDate(new Date());
			roleDao.saveRoleMenu(roleMenuDO);
		}
		return new ResultDTO(true, MessageConsts.ASSIGN_SUCCESS);
	}
	
	@Override
	public ResultDTO saveRole(RoleDTO roleDTO) {
		// 用户信息
		roleDTO.setCreatedBy("system");
		roleDTO.setCreatedDate(new Date());
		roleDTO.setUpdatedBy("system");
		roleDTO.setUpdatedDate(new Date());
		roleDao.saveRole(roleDTO);
		
		// 字典-用户
		GroupRoleDO groupRoleDO = new GroupRoleDO();
		groupRoleDO.setIdGroup(roleDTO.getIdGroup());
		groupRoleDO.setIdRole(roleDTO.getIdRole());
		groupRoleDO.setCreatedBy("system");
		groupRoleDO.setCreatedDate(new Date());
		groupRoleDO.setUpdatedBy("system");
		groupRoleDO.setUpdatedDate(new Date());
		groupDao.saveGroupRole(groupRoleDO);
		return new ResultDTO(true, MessageConsts.SAVE_SUCCESS);
	}
	
	@Override
	public ResultDTO updateRole(RoleDTO roleDTO) {
		// 用户信息
		roleDTO.setCreatedBy("system");
		roleDTO.setCreatedDate(new Date());
		roleDTO.setUpdatedBy("system");
		roleDTO.setUpdatedDate(new Date());
		roleDao.updateRole(roleDTO);
		groupDao.deleteGroupRoleByIdRole(roleDTO.getIdRole());
		
		// 字典-用户
		GroupRoleDO groupRoleDO = new GroupRoleDO();
		groupRoleDO.setIdGroup(roleDTO.getIdGroup());
		groupRoleDO.setIdRole(roleDTO.getIdRole());
		groupRoleDO.setCreatedBy("system");
		groupRoleDO.setCreatedDate(new Date());
		groupRoleDO.setUpdatedBy("system");
		groupRoleDO.setUpdatedDate(new Date());
		groupDao.saveGroupRole(groupRoleDO);
		return new ResultDTO(true, MessageConsts.UPDATE_SUCCESS);
	}

	@Override
	public ResultDTO batchDeleteRoles(String idRoles) {
		String[] idRoleArr = idRoles.split(",");
		roleDao.batchDeleteRoles(Arrays.asList(idRoleArr));
		groupDao.batchDeleteGroups(Arrays.asList(idRoleArr));
		return new ResultDTO(true, MessageConsts.DELETE_SUCCESS);
	}
}
