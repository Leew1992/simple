package org.simple.controller;

import java.util.List;

import org.simple.dto.ResultDTO;
import org.simple.dto.TreeNode;
import org.simple.entity.ColumnDO;
import org.simple.service.ColumnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 类别控制器
 */
@Controller
@RequestMapping("/column")
public class ColumnController {
	
	@Autowired
	private ColumnService columnService;
	
	/**
	 * 获取类别信息
	 */
	@RequestMapping("/getColumnById.do")
	@ResponseBody
	public ColumnDO getColumnById(String idColumn) {
		return columnService.getColumnById(idColumn);
	}
	
	/**
	 * 获取所有类别
	 */
	@RequestMapping("/listAllColumns.do")
	@ResponseBody
	public List<ColumnDO> listAllColumns() {
		return columnService.listAllColumns();
	}
	
	/**
	 * 获取类别树
	 */
	@RequestMapping("/getColumnTree.do")
	@ResponseBody
	public List<TreeNode> getColumnTree() {
		return columnService.getColumnTree();
	}
	
	/**
	 * 获取类别树
	 */
	@RequestMapping("/getHasCheckedColumnTreeByIdPost.do")
	@ResponseBody
	public List<TreeNode> getHasCheckedColumnTreeByIdPost(String idPost) {
		return columnService.getHasCheckedColumnTreeByIdPost(idPost);
	}
	
	/**
	 * 保存类别信息
	 */
	@RequestMapping("/saveColumn.do")
	@ResponseBody
	public ResultDTO saveColumn(ColumnDO columnDO) {
		return columnService.saveColumn(columnDO);
	}
	
	/**
	 * 更新类别信息
	 */
	@RequestMapping("/updateColumn.do")
	@ResponseBody
	public ResultDTO updateColumn(ColumnDO columnDO) {
		return columnService.updateColumn(columnDO);
	}
	
	/**	
	 * 删除分组信息
	 */
	@RequestMapping("/deleteColumn.do")
	@ResponseBody
	public ResultDTO deleteColumn(String idColumn) {
		return columnService.deleteColumn(idColumn);
	}
	
	/**	
	 * 删除类别信息
	 */
	@RequestMapping("/batchDeleteColumns.do")
	@ResponseBody
	public ResultDTO batchDeleteColumns(String idColumns) {
		return columnService.batchDeleteColumns(idColumns);
	}
}
