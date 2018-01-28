package org.simple.service.impl;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.simple.constant.MessageConst;
import org.simple.context.UserContext;
import org.simple.dao.CategoryDao;
import org.simple.dto.ResultDTO;
import org.simple.dto.TreeNode;
import org.simple.entity.CategoryDO;
import org.simple.service.CategoryService;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {
	
	@Resource
	private CategoryDao categoryDao;

	@Override
	public CategoryDO getCategoryById(String idCategory) {
		return categoryDao.getCategoryById(idCategory);
	}
	
	@Override
	public List<CategoryDO> listCategoriesByIdParent(String idParent) {
		return categoryDao.listCategoriesByIdParent(idParent);
	}
	
	@Override
	public List<CategoryDO> listAllCategories() {
		return categoryDao.listAllCategories();
	}

	@Override
	public List<TreeNode> getCategoryTree() {
		return categoryDao.getCategoryTree();
	}
	
	@Override
	public List<TreeNode> getHasCheckedCategoryTreeByIdComment(String idComment) {
		return categoryDao.getHasCheckedCategoryTreeByIdComment(idComment);
	}
	
	@Override
	public TreeNode getCategoryNodeById(String idCategory) {
		CategoryDO categoryDO = categoryDao.getCategoryById(idCategory);
		TreeNode treeNode = new TreeNode();
		treeNode.setId(categoryDO.getIdCategory());
		treeNode.setpId(categoryDO.getIdParent());
		treeNode.setName(categoryDO.getCategoryName());
		treeNode.setDesc(categoryDO.getCategoryDesc());
		treeNode.setOpen(true);
		return treeNode;
	}

	@Override
	public ResultDTO saveCategory(CategoryDO categoryDO) {
		categoryDO.setCreatedBy(UserContext.getCurrentUserName());
		categoryDO.setUpdatedBy(UserContext.getCurrentUserName());
		categoryDao.saveCategory(categoryDO);
		return new ResultDTO(true, categoryDO.getIdCategory(), MessageConst.SAVE_SUCCESS);
	}

	@Override
	public ResultDTO updateCategory(CategoryDO categoryDO) {
		categoryDO.setUpdatedBy(UserContext.getCurrentUserName());
		categoryDao.updateCategory(categoryDO);
		return new ResultDTO(true, MessageConst.UPDATE_SUCCESS);
	}

	@Override
	public ResultDTO batchDeleteCategories(String[] idCategories) {
		categoryDao.batchDeleteCategories(Arrays.asList(idCategories));
		return new ResultDTO(true, MessageConst.DELETE_SUCCESS);
	}
	
	@Override
	public ResultDTO deleteCategory(String idCategory) {
		List<CategoryDO> categoryList = categoryDao.listCategoriesByIdParent(idCategory);
		if(categoryList != null && !categoryList.isEmpty()) {
			categoryDao.deleteCategory(idCategory);
		} else {
			return new ResultDTO(false, MessageConst.HAS_SUB_CATEGORY_IN_CATEGORY);
		}
		return new ResultDTO(true, MessageConst.DELETE_SUCCESS);
	}

}
