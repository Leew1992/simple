package org.simple.service;

import java.util.List;

import org.simple.dto.ResultDTO;
import org.simple.dto.TreeNode;
import org.simple.entity.CategoryDO;

public interface CategoryService {
	
	/**
	 * 获取类别信息
	 */
	CategoryDO getCategoryById(String idCategory);
	
	/**
	 * 获取类别列表
	 */
	List<CategoryDO> listCategoriesByIdParent(String idParent);
	
	/**
     * 获取所有类别
     */
    List<CategoryDO> listAllCategories();
	
	/**
	 * 获取类别节点
	 */
	TreeNode getCategoryNodeById(String idCategory);
	
	/**
	 * 获取所有类别信息
	 */
	List<TreeNode> getCategoryTree();

    /**
     * 获取类别树
     */
    List<TreeNode> getHasCheckedCategoryTreeByIdComment(String idComment);
	
	/**
	 * 保存类别信息
	 */
	ResultDTO saveCategory(CategoryDO categoryDO);
	
	/**
	 * 更新类别信息
	 */
	ResultDTO updateCategory(CategoryDO categoryDO);
	
	/**
	 * 删除类别信息
	 */
	ResultDTO deleteCategory(String idCategory);
	
	/**
	 * 批量删除类别信息
	 */
	ResultDTO batchDeleteCategories(String[] idCategories);
	
}
