package org.simple.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.simple.constant.MessageConst;
import org.simple.context.UserContext;
import org.simple.dao.RoleDao;
import org.simple.dao.SystemDao;
import org.simple.dto.ResultDTO;
import org.simple.dto.TreeNode;
import org.simple.entity.SystemDO;
import org.simple.service.SystemService;
import org.springframework.stereotype.Service;

@Service
public class SystemServiceImpl implements SystemService {
	
	@Resource
	private SystemDao systemDao;
	
	@Resource
	private RoleDao roleDao;
	
	@Override
	public SystemDO getSystemById(String idSystem) {
		return systemDao.getSystemById(idSystem);
	}
	
	@Override
	public TreeNode getSystemNodeById(String idSystem) {
		SystemDO systemDO = systemDao.getSystemById(idSystem);
		TreeNode treeNode = new TreeNode();
		treeNode.setId(systemDO.getIdSystem());
		treeNode.setpId(systemDO.getIdParent());
		treeNode.setName(systemDO.getSystemName());
		treeNode.setDesc(systemDO.getSystemDesc());
		treeNode.setOpen(true);
		return treeNode;
	}
	
	@Override
	public List<TreeNode> getSystemTree() {
		return systemDao.getSystemTree();
	}
	
	@Override
	public List<TreeNode> getSystemTreeByIdRole(String idRole) {
		return systemDao.getSystemTreeByIdRole(idRole);
	}

	@Override
	public List<TreeNode> getHasCheckedSystemTreeByIdMenu(String idMenu) {
		return systemDao.getHasCheckedSystemTreeByIdMenu(idMenu);
	}
	
	@Override
	public List<TreeNode> getHasCheckedSystemTreeByIdRole(String idRole) {
		return systemDao.getHasCheckedSystemTreeByIdRole(idRole);
	}
	
	@Override
	public ResultDTO saveSystem(SystemDO systemDO){
		systemDO.setCreatedBy(UserContext.getCurrentUserName());
		systemDO.setUpdatedBy(UserContext.getCurrentUserName());
		systemDao.saveSystem(systemDO);
		return new ResultDTO(true, systemDO.getIdSystem(), MessageConst.SAVE_SUCCESS);
	}
	
	@Override
	public ResultDTO updateSystem(SystemDO systemDO){
		systemDO.setUpdatedBy(UserContext.getCurrentUserName());
		systemDao.updateSystem(systemDO);
		return new ResultDTO(true, MessageConst.UPDATE_SUCCESS);
	}
	
	@Override
	public ResultDTO deleteSystem(String idSystem) {
		List<SystemDO> systemList = systemDao.getSystemByIdParent(idSystem);
		if(systemList != null && !systemList.isEmpty()) {
			systemDao.deleteSystem(idSystem);				
		} else {
			return new ResultDTO(false, MessageConst.HAS_SUB_GROUP_IN_GROUP);
		}
		return new ResultDTO(true, MessageConst.DELETE_SUCCESS);
	}

}
