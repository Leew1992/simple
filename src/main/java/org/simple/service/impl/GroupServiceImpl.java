package org.simple.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.simple.constant.MessageConsts;
import org.simple.dao.GroupDao;
import org.simple.dto.ResultDTO;
import org.simple.dto.TreeNode;
import org.simple.entity.GroupDO;
import org.simple.service.GroupService;
import org.springframework.stereotype.Service;

@Service
public class GroupServiceImpl implements GroupService {
	
	@Resource
	private GroupDao groupDao;

	@Override
	public GroupDO getGroupById(String idGroup) {
		return groupDao.getGroupById(idGroup);
	}
	
	@Override
	public List<GroupDO> listAllGroups() {
		return groupDao.listAllGroups();
	}
	
	@Override
	public TreeNode getGroupNodeById(String idGroup) {
		GroupDO groupDO = groupDao.getGroupById(idGroup);
		TreeNode treeNode = new TreeNode();
		treeNode.setId(groupDO.getIdGroup());
		treeNode.setpId(groupDO.getIdParent());
		treeNode.setName(groupDO.getGroupName());
		treeNode.setDesc(groupDO.getGroupDesc());
		treeNode.setOpen("true");
		return treeNode;
	}

	@Override
	public List<TreeNode> getGroupTreeForUserList() {
		return groupDao.getGroupTreeForUserList();
	}
	
	@Override
	public List<TreeNode> getGroupTreeForUserForm(String idUser) {
		return groupDao.getGroupTreeForUserForm(idUser);
	}
	
	@Override
	public List<TreeNode> getGroupTreeForRoleForm(String idRole) {
		return groupDao.getGroupTreeForRoleForm(idRole);
	}
	
	@Override
	public List<TreeNode> getGroupTreeForGroupConfig() {
		return groupDao.getGroupTreeForGroupConfig();
	}
	
	@Override
	public ResultDTO saveGroup(GroupDO groupDO) {
		groupDO.setCreatedBy("system");
		groupDO.setCreatedDate(new Date());
		groupDO.setUpdatedBy("system");
		groupDO.setUpdatedDate(new Date());
		groupDao.saveGroup(groupDO);
		return new ResultDTO(true, groupDO.getIdGroup(), MessageConsts.SAVE_SUCCESS);
	}
	
	@Override
	public ResultDTO updateGroup(GroupDO groupDO) {
		groupDO.setUpdatedBy("system");
		groupDO.setUpdatedDate(new Date());
		groupDao.updateGroup(groupDO);
		return new ResultDTO(true, MessageConsts.UPDATE_SUCCESS);
	}
	
	@Override
	public ResultDTO deleteGroup(String idGroup) {
		List<GroupDO> groupList = groupDao.listGroupsByIdParent(idGroup);
		if(groupList != null && groupList.size() == 0) {
			groupDao.deleteGroup(idGroup);				
		} else {
			return new ResultDTO(false, MessageConsts.HAS_SUB_GROUP_IN_GROUP);
		}
		return new ResultDTO(true, MessageConsts.DELETE_SUCCESS);
	}

}
