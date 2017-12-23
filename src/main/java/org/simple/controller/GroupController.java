package org.simple.controller;

import java.util.List;

import org.simple.dto.ResultDTO;
import org.simple.dto.TreeNode;
import org.simple.entity.GroupDO;
import org.simple.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 分组控制器
 */
@Controller
@RequestMapping("/group")
public class GroupController {
	
	@Autowired
	private GroupService groupService;
	
	/**
	 * 获取分组信息
	 */
	@RequestMapping("/getGroupById.do")
	@ResponseBody
	public GroupDO getGroupById(String idGroup) {
		return groupService.getGroupById(idGroup);
	}
	
	/**
	 * 获取所有分组
	 */
	@RequestMapping("/getGroupTreeForUserList.do")
	@ResponseBody
	public List<TreeNode> getGroupTreeForUserList() {
		return groupService.getGroupTreeForUserList();
	}
	
	/**
	 * 获取分组树
	 */
	@RequestMapping("/getGroupTreeForUserForm.do")
	@ResponseBody
	public List<TreeNode> getGroupTreeForUserForm(String idUser) {
		return groupService.getGroupTreeForUserForm(idUser);
	}
	
	/**
	 * 获取分组树
	 */
	@RequestMapping("/getGroupTreeForRoleForm.do")
	@ResponseBody
	public List<TreeNode> getGroupTreeForRoleForm(String idRole) {
		return groupService.getGroupTreeForRoleForm(idRole);
	}
	
	/**
	 * 获取分组树
	 */
	@RequestMapping("/getGroupTreeForGroupConfig.do")
	@ResponseBody
	public List<TreeNode> getGroupTreeForGroupConfig() {
		return groupService.getGroupTreeForGroupConfig();
	}
	
	/**
	 * 保存分组信息
	 */
	@RequestMapping("/saveGroup.do")
	@ResponseBody
	public ResultDTO saveGroup(GroupDO groupDO) {
		return groupService.saveGroup(groupDO);
	}
	
	/**
	 * 更新分组信息
	 */
	@RequestMapping("/updateGroup.do")
	@ResponseBody
	public ResultDTO updateGroup(GroupDO groupDO) {
		return groupService.updateGroup(groupDO);
	}
	
	/**	
	 * 删除分组信息
	 */
	@RequestMapping("/deleteGroup.do")
	@ResponseBody
	public ResultDTO deleteGroup(String idGroup) {
		return groupService.deleteGroup(idGroup);
	}
}
