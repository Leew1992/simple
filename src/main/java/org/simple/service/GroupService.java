package org.simple.service;

import java.util.List;

import org.simple.dto.ResultDTO;
import org.simple.dto.TreeNode;
import org.simple.entity.GroupDO;

public interface GroupService {
	
	/**
	 * 获取分组信息
	 */
	GroupDO getGroupById(String idGroup);
	
	/**
	 * 获取所有分组信息
	 */
	List<GroupDO> listAllGroups();
	
	/**
	 * 获取分组信息
	 */
	TreeNode getGroupNodeById(String idGroup);
	
	/**
	 * 获取分组树
	 */
	List<TreeNode> getGroupTree();
	
	/**
	 * 获取分组树
	 */
	List<TreeNode> getHasCheckedGroupTreeByIdUser(String idUser);
	
	/**
	 * 获取多条分组信息
	 */
	List<TreeNode> getHasCheckedGroupTreeByIdRole(String idRole);
	
	/**
	 * 保存分组信息
	 */
	ResultDTO saveGroup(GroupDO groupDO);
	
	/**
	 * 更新分组信息
	 */
	ResultDTO updateGroup(GroupDO groupDO);
	
	/**
	 * 删除分组信息
	 */
	ResultDTO deleteGroup(String idGroup);
}
