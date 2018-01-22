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
	@RequestMapping("/getGroupTree.do")
	@ResponseBody
	public List<TreeNode> getGroupTree() {
		return groupService.getGroupTree();
	}
	
	/**
	 * 获取分组树
	 */
	@RequestMapping("/getHasCheckedGroupTreeByIdUser.do")
	@ResponseBody
	public List<TreeNode> getHasCheckedGroupTreeByIdUser(String idUser) {
		return groupService.getHasCheckedGroupTreeByIdUser(idUser);
	}
	
	/**
	 * 获取分组树
	 */
	@RequestMapping("/getHasCheckedGroupTreeByIdRole.do")
	@ResponseBody
	public List<TreeNode> getHasCheckedGroupTreeByIdRole(String idRole) {
		return groupService.getHasCheckedGroupTreeByIdRole(idRole);
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
