package org.simple.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.simple.dao.GroupDao;
import org.simple.dto.TreeNode;
import org.simple.entity.GroupDO;
import org.simple.entity.GroupRoleDO;
import org.simple.entity.GroupUserDO;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;

public class GroupDaoTest extends BaseTest {
	
	private final Logger logger = Logger.getLogger(getClass());
	
	@Autowired
    private GroupDao groupDao;

	/**
	 * 获取单条分组信息
	 */
	@Test
	public void testGetGroupById() {
		String idGroup = "0";
		GroupDO groupDO = groupDao.getGroupById(idGroup);
		logger.debug(JSON.toJSONString(groupDO));
	}
	
	/**
	 * 获取子分组列表
	 */
	@Test
	public void testGetGroupByIdParent() {
		String idParent = "0";
		List<GroupDO> groupList = groupDao.listGroupsByIdParent(idParent);
		logger.debug(JSON.toJSONString(groupList));
	}
	
	/**
	 * 获取分组树
	 */
	@Test
	public void testGetGroupTreeForUserForm() {
		String idUser = "0";
		List<TreeNode> treeNodeList = groupDao.getHasCheckedGroupTreeByIdUser(idUser);
		logger.debug(JSON.toJSONString(treeNodeList));
	}
	
	/**
	 * 获取分组树
	 */
	@Test
	public void testGetGroupTreeForRoleForm() {
		String idRole = "0";
		List<TreeNode> treeNodeList = groupDao.getHasCheckedGroupTreeByIdRole(idRole);
		logger.debug(JSON.toJSONString(treeNodeList));
	}
	
	/**
	 * 获取所有分组信息
	 */
	@Test
	public void testListAllGroups() {
		List<GroupDO> groupList = groupDao.listAllGroups();
		logger.debug(JSON.toJSONString(groupList));
	}
	
	/**
	 * 保存分组信息
	 */
	@Test
	public void testSaveGroup() {
		GroupDO groupDO = new GroupDO();
		groupDO.setGroupName("aa");
		groupDO.setGroupDesc("aa");
		groupDO.setCreatedBy("aa");
		groupDO.setCreatedDate(new Date());
		groupDO.setUpdatedBy("aa");
		groupDO.setUpdatedDate(new Date());
		groupDao.saveGroup(groupDO);
		logger.debug("分组信息保存成功！idGroup: " + groupDO.getIdGroup());
	}
	
	/**
	 * 更新分组信息
	 */
	@Test
	public void testUpdateGroup() {
		GroupDO groupDO = new GroupDO();
		groupDO.setIdGroup("0");
		groupDO.setGroupName("bb");
		groupDO.setGroupDesc("bb");
		groupDO.setCreatedBy("bb");
		groupDO.setCreatedDate(new Date());
		groupDO.setUpdatedBy("bb");
		groupDO.setUpdatedDate(new Date());
		groupDao.updateGroup(groupDO);
		logger.debug("分组信息更新成功");
	}
	
	/**
	 * 删除分组信息
	 */
	@Test
	public void testDeleteGroup() {
		String idGroup = "0";
		groupDao.deleteGroup(idGroup);
		logger.debug("分组信息删除成功");
	}
	
	/**
	 * 批量删除分组信息
	 */
	@Test
	public void testBatchDeleteGroups() {
		List<String> idGroups = new ArrayList<>();
		idGroups.add("0");
		idGroups.add("1");
		groupDao.batchDeleteGroups(idGroups);
		logger.debug("用户信息批量删除成功");
	}

	/**
	 * 获取分组用户列表
	 */
	@Test
	public void testGetGroupUserByIdUser() {
		String idUser = "0";
		List<GroupUserDO> groupUserList = groupDao.listGroupUsersByIdUser(idUser);
		logger.debug(JSON.toJSONString(groupUserList));
	}
	
	/**
	 * 保存分组用户信息
	 */
	@Test
	public void testSaveGroupUser() {
		GroupUserDO groupUserDO = new GroupUserDO();
		groupUserDO.setIdGroup("0");
		groupUserDO.setIdUser("0");
		groupUserDO.setCreatedBy("aa");
		groupUserDO.setCreatedDate(new Date());
		groupUserDO.setUpdatedBy("aa");
		groupUserDO.setUpdatedDate(new Date());
		groupDao.saveGroupUser(groupUserDO);
		logger.debug("分组用户信息保存成功");
	}
	
	/**
	 * 删除分组用户信息
	 */
	@Test
	public void testDeleteGroupUserByIdUser() {
		String idUser = "0";
		groupDao.deleteGroupUserByIdUser(idUser);
		logger.debug("分组用户信息删除成功");
	}
	
	/**
	 * 批量删除分组用户
	 */
	@Test
	public void testBatchDeleteGroupsUserByIdUser() {
		List<String> idUsers = new ArrayList<>();
		idUsers.add("0");
		idUsers.add("1");
		groupDao.batchDeleteGroupsUserByIdUser(idUsers);
		logger.debug("分组用户信息批量删除成功");
	}
	
	/**
	 * 获取分组角色列表
	 */
	@Test
	public void testListGroupRoleByIdRole() {
		String idRole = "0";
		List<GroupRoleDO> groupRoleList = groupDao.listGroupRolesByIdRole(idRole);
		logger.debug(JSON.toJSONString(groupRoleList));
	}
	
	/**
	 * 保存分组角色信息
	 */
	@Test
	public void testSaveGroupRole() {
		GroupRoleDO groupRoleDO  = new GroupRoleDO();
		groupRoleDO.setIdGroup("0");
		groupRoleDO.setIdRole("0");
		groupRoleDO.setCreatedBy("aa");
		groupRoleDO.setCreatedDate(new Date());
		groupRoleDO.setUpdatedBy("bb");
		groupRoleDO.setUpdatedDate(new Date());
		groupDao.saveGroupRole(groupRoleDO);
		logger.debug("分组角色信息保存成功");
	}
	
	/**
	 * 删除分组角色信息
	 */
	@Test
	public void testDeleteGroupRoleByIdRole() {
		String idRole = "0";
		groupDao.deleteGroupRoleByIdRole(idRole);
		logger.debug("分组角色信息删除成功");
	}
	
	/**
	 * 批量删除分组角色
	 */
	@Test
	public void testBatchDeleteGroupRolesByIdRole() {
		List<String> idRoles = new ArrayList<>();
		idRoles.add("0");
		idRoles.add("1");
		groupDao.batchDeleteGroupRolesByIdRole(idRoles);
		logger.debug("分组角色信息批量删除成功");
	}
}