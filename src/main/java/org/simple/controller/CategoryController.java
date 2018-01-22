package org.simple.controller;

import java.util.List;

import org.simple.dto.ResultDTO;
import org.simple.dto.TreeNode;
import org.simple.entity.CategoryDO;
import org.simple.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/category")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	/**
	 * 获取类别信息
	 */
	@RequestMapping("/getCategoryById.do")
	@ResponseBody
	public CategoryDO getCategoryById(String idCategory) {
		return categoryService.getCategoryById(idCategory);
	}
	
	/**
	 * 获取类别树
	 */
	@RequestMapping("/listAllCategories.do")
	@ResponseBody
	public List<CategoryDO> listAllCategories() {
		return categoryService.listAllCategories();
	}
	
	/**
	 * 获取类别树
	 */
	@RequestMapping("/getCategoryTree.do")
	@ResponseBody
	public List<TreeNode> getCategoryTree() {
		return categoryService.getCategoryTree();
	}
	
	/**
	 * 保存类别信息
	 */
	@RequestMapping("/saveCategory.do")
	@ResponseBody
	public ResultDTO saveCategory(CategoryDO categoryDO) {
		return categoryService.saveCategory(categoryDO);
	}
	
	/**
	 * 更新类别信息
	 */
	@RequestMapping("/updateCategory.do")
	@ResponseBody
	public ResultDTO updateCategory(CategoryDO categoryDO) {
		return categoryService.updateCategory(categoryDO);
	}
	
	/**	
	 * 删除分组信息
	 */
	@RequestMapping("/deleteCategory.do")
	@ResponseBody
	public ResultDTO deleteCategory(String idCategory) {
		return categoryService.deleteCategory(idCategory);
	}
	
	/**	
	 * 批量删除类别信息
	 */
	@RequestMapping("/batchDeleteCategories.do")
	@ResponseBody
	public ResultDTO batchDeleteCategories(String idCategorys) {
		return categoryService.batchDeleteCategories(idCategorys.split(","));
	}
	
}
