package org.simple.service;

import java.util.List;

import org.simple.dto.ResultDTO;
import org.simple.dto.TreeNode;
import org.simple.entity.SystemDO;

public interface SystemService {
	
	/**
	 * 获取系统信息
	 */
	SystemDO getSystemById(String idSystem);

	/**
	 * 获取系统信息
	 */
	TreeNode getSystemNodeById(String idSystem);
	
	/**
     * 获取系统树
     */
    List<TreeNode> getSystemTree();
    
    /**
     * 获取系统树
     */
    List<TreeNode> getSystemTreeByIdRole(String idRole);

    /**
     * 获取系统树
     */
    List<TreeNode> getHasCheckedSystemTreeByIdMenu(String idMenu);
    
    /**
     * 获取系统树
     */
    List<TreeNode> getHasCheckedSystemTreeByIdRole(String idRole);

	/**
	 * 保存系统信息
	 */
	ResultDTO saveSystem(SystemDO systemDO);

	/**
	 * 更新系统信息
	 */
	ResultDTO updateSystem(SystemDO systemDO);

	/**
	 * 删除系统信息
	 */
	ResultDTO deleteSystem(String idSystem);

}
