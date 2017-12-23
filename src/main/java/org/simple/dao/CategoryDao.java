package org.simple.dao;

import java.util.List;

import org.simple.dto.TreeNode;
import org.simple.entity.CategoryDO;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryDao {
    
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
	 * 获取所有类别信息
	 */
	List<TreeNode> getCategoryTreeForCommentList();

    /**
     * 获取类别树
     */
    List<TreeNode> getCategoryTreeForCommentForm(String idComment);
    
    /**
     * 获取类别树
     */
    List<TreeNode> getCategoryTreeForCategoryConfig();

    /**
     * 保存类别信息
     */
    String saveCategory(CategoryDO categoryDO);

    /**
     * 更新类别信息
     */
    void updateCategory(CategoryDO categoryDO);

    /**
     * 删除类别
     */
    void deleteCategory(String idCategory);

    /**
     * 批量删除类别
     */
    void batchDeleteCategories(List<String> idCategories);

}