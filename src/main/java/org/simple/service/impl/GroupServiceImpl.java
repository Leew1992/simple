package org.simple.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.simple.constant.MessageConst;
import org.simple.context.UserContext;
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
		treeNode.setOpen(true);
		return treeNode;
	}

	@Override
	public List<TreeNode> getGroupTree() {
		return groupDao.getGroupTree();
	}
	
	@Override
	public List<TreeNode> getHasCheckedGroupTreeByIdUser(String idUser) {
		return groupDao.getHasCheckedGroupTreeByIdUser(idUser);
	}
	
	@Override
	public List<TreeNode> getHasCheckedGroupTreeByIdRole(String idRole) {
		return groupDao.getHasCheckedGroupTreeByIdRole(idRole);
	}
	
	@Override
	public ResultDTO saveGroup(GroupDO groupDO) {
		groupDO.setCreatedBy(UserContext.getCurrentUserName());
		groupDO.setUpdatedBy(UserContext.getCurrentUserName());
		groupDao.saveGroup(groupDO);
		return new ResultDTO(true, groupDO.getIdGroup(), MessageConst.SAVE_SUCCESS);
	}
	
	@Override
	public ResultDTO updateGroup(GroupDO groupDO) {
		groupDO.setUpdatedBy(UserContext.getCurrentUserName());
		groupDao.updateGroup(groupDO);
		return new ResultDTO(true, MessageConst.UPDATE_SUCCESS);
	}
	
	@Override
	public ResultDTO deleteGroup(String idGroup) {
		List<GroupDO> groupList = groupDao.listGroupsByIdParent(idGroup);
		if(groupList != null && !groupList.isEmpty()) {
			groupDao.deleteGroup(idGroup);				
		} else {
			return new ResultDTO(false, MessageConst.HAS_SUB_GROUP_IN_GROUP);
		}
		return new ResultDTO(true, MessageConst.DELETE_SUCCESS);
	}

}
