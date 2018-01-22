package org.simple.service;

import java.util.List;

import org.simple.dto.ResultDTO;
import org.simple.dto.TreeNode;
import org.simple.entity.ColumnDO;

public interface ColumnService {
	
	/**
	 * 获取单条类别信息
	 */
	ColumnDO getColumnById(String idColumn);
	
    /**
     * 获取栏目列表
     */
    List<ColumnDO> listAllColumns();
	
    /**
	 * 获取栏目树
	 */
	List<TreeNode> getColumnTree();

    /**
     * 获取栏目树
     */
    List<TreeNode> getHasCheckedColumnTreeByIdPost(String idPost);

	/**
	 * 保存类别信息
	 */
	ResultDTO saveColumn(ColumnDO columnDO);
	
	/**
	 * 更新类别信息
	 */
	ResultDTO updateColumn(ColumnDO columnDO);
	
	/**
	 * 删除类别信息
	 */
	ResultDTO deleteColumn(String idColumn);
	
	/**
	 * 批量删除类别信息
	 */
	ResultDTO batchDeleteColumns(String idColumns);

}
