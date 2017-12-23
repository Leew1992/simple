package org.simple.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.simple.constant.MessageConsts;
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
		treeNode.setOpen("true");
		return treeNode;
	}
	
	@Override
	public List<TreeNode> getSystemTreeForMenuList() {
		return systemDao.getSystemTreeForMenuList();
	}

	@Override
	public List<TreeNode> getSystemTreeForMenuForm(String idMenu) {
		return systemDao.getSystemTreeForMenuForm(idMenu);
	}
	
	@Override
	public List<TreeNode> getSystemTreeForSystemConfig() {
		return systemDao.getSystemTreeForSystemConfig();
	}
	
	@Override
	public ResultDTO saveSystem(SystemDO systemDO){
		systemDO.setCreatedBy("system");
		systemDO.setCreatedDate(new Date());
		systemDO.setUpdatedBy("system");
		systemDO.setUpdatedDate(new Date());
		systemDao.saveSystem(systemDO);
		return new ResultDTO(true, systemDO.getIdSystem(), MessageConsts.SAVE_SUCCESS);
	}
	
	@Override
	public ResultDTO updateSystem(SystemDO systemDO){
		systemDO.setUpdatedBy("system");
		systemDO.setUpdatedDate(new Date());
		systemDao.updateSystem(systemDO);
		return new ResultDTO(true, MessageConsts.UPDATE_SUCCESS);
	}
	
	@Override
	public ResultDTO deleteSystem(String idSystem) {
		List<SystemDO> systemList = systemDao.getSystemByIdParent(idSystem);
		if(systemList != null && systemList.size() == 0) {
			systemDao.deleteSystem(idSystem);				
		} else {
			return new ResultDTO(false, MessageConsts.HAS_SUB_GROUP_IN_GROUP);
		}
		return new ResultDTO(true, MessageConsts.DELETE_SUCCESS);
	}

}
