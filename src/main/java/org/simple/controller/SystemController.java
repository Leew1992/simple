package org.simple.controller;

import java.util.List;

import org.simple.dto.ResultDTO;
import org.simple.dto.TreeNode;
import org.simple.entity.SystemDO;
import org.simple.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 系统Controller
 */
@Controller
@RequestMapping("/system")
public class SystemController {
	
	@Autowired
	private SystemService systemService;
	
	@RequestMapping("/getSystemById.do")
	@ResponseBody
	public SystemDO getSystemById(String idSystem) {
		return systemService.getSystemById(idSystem);
	}
	
	@RequestMapping("/getSystemTree.do")
	@ResponseBody
	public List<TreeNode> getSystemTree() {
		return systemService.getSystemTree();
	}
	
	@RequestMapping("/getSystemTreeByIdRole.do")
	@ResponseBody
	public List<TreeNode> getSystemTreeByIdRole(String idRole) {
		return systemService.getSystemTreeByIdRole(idRole);
	}
	
	@RequestMapping("/getHasCheckedSystemTreeByIdMenu.do")
	@ResponseBody
	public List<TreeNode> getHasCheckedSystemTreeByIdMenu(String idMenu) {
		return systemService.getHasCheckedSystemTreeByIdMenu(idMenu);
	}
	
	@RequestMapping("/getHasCheckedSystemTreeByIdRole.do")
	@ResponseBody
	public List<TreeNode> getHasCheckedSystemTreeByIdRole(String idRole) {
		return systemService.getHasCheckedSystemTreeByIdRole(idRole);
	}
	
	@RequestMapping("/saveSystem.do")
	@ResponseBody
	public ResultDTO saveSystem(SystemDO systemDO) {
		return systemService.saveSystem(systemDO);
	}
	
	@RequestMapping("/updateSystem.do")
	@ResponseBody
	public ResultDTO updateSystem(SystemDO systemDO) {
		return systemService.updateSystem(systemDO);
	}
	
	@RequestMapping("/deleteSystem.do")
	@ResponseBody
	public ResultDTO deleteSystem(String idSystem) {
		return systemService.deleteSystem(idSystem);
	}
}
