package org.simple.dao;

import java.util.List;

import org.simple.dto.TreeNode;
import org.simple.entity.GroupDO;
import org.simple.entity.GroupRoleDO;
import org.simple.entity.GroupUserDO;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupDao {
	
	/**
	 * 获取单条分组信息
	 */
	GroupDO getGroupById(String idGroup);
	
	/**
	 * 获取子分组列表
	 */
	List<GroupDO> listGroupsByIdParent(String idParent);
	
	/**
	 * 获取所有分组信息
	 */
	List<GroupDO> listAllGroups();
	
	/**
	 * 获取分组树
	 */
	List<TreeNode> getGroupTree();
	
	/**
	 * 获取分组树
	 */
	List<TreeNode> getHasCheckedGroupTreeByIdUser(String idUser);
	
	/**
	 * 获取分组树
	 */
	List<TreeNode> getHasCheckedGroupTreeByIdRole(String idRole);
	
	/**
	 * 保存分组信息
	 */
	void saveGroup(GroupDO groupDO);
	
	/**
	 * 更新分组信息
	 */
	void updateGroup(GroupDO groupDO);
	
	/**
	 * 删除分组信息
	 */
	void deleteGroup(String idGroup);
	
	/**
	 * 批量删除分组信息
	 */
	void batchDeleteGroups(List<String> idGroups);

	/**
	 * 获取分组用户列表
	 */
	List<GroupUserDO> listGroupUsersByIdUser(String idUser);
	
	/**
	 * 保存分组用户信息
	 */
	void saveGroupUser(GroupUserDO groupUserDO);
	
	/**
	 * 删除分组用户信息
	 */
	void deleteGroupUserByIdUser(String idUser);
	
	/**
	 * 批量删除分组用户
	 */
	void batchDeleteGroupsUserByIdUser(List<String> idUsers);
	
	/**
	 * 获取分组角色列表
	 */
	List<GroupRoleDO> listGroupRolesByIdRole(String idRole);
	
	/**
	 * 保存分组角色信息
	 */
	void saveGroupRole(GroupRoleDO groupRoleDO);
	
	/**
	 * 删除分组角色信息
	 */
	void deleteGroupRoleByIdRole(String idRole);
	
	/**
	 * 批量删除分组角色
	 */
	void batchDeleteGroupRolesByIdRole(List<String> idRoles);

}
