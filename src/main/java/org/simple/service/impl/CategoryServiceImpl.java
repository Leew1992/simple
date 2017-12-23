package org.simple.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.simple.constant.MessageConsts;
import org.simple.constant.SysCodeConsts;
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
	public List<TreeNode> getCategoryTreeForCommentList() {
		return categoryDao.getCategoryTreeForCommentList();
	}
	
	@Override
	public List<TreeNode> getCategoryTreeForCategoryConfig() {
		return categoryDao.getCategoryTreeForCategoryConfig();
	}
	
	@Override
	public TreeNode getCategoryNodeById(String idCategory) {
		CategoryDO categoryDO = categoryDao.getCategoryById(idCategory);
		TreeNode treeNode = new TreeNode();
		treeNode.setId(categoryDO.getIdCategory());
		treeNode.setpId(categoryDO.getIdParent());
		treeNode.setName(categoryDO.getCategoryName());
		treeNode.setDesc(categoryDO.getCategoryDesc());
		treeNode.setOpen("true");
		return treeNode;
	}

	@Override
	public ResultDTO saveCategory(CategoryDO categoryDO) {
		categoryDO.setCreatedBy(SysCodeConsts.OPERATOR);
		categoryDO.setCreatedDate(new Date());
		categoryDO.setUpdatedBy(SysCodeConsts.OPERATOR);
		categoryDO.setUpdatedDate(new Date());
		String idCategory = categoryDao.saveCategory(categoryDO);
		return new ResultDTO(true, idCategory, MessageConsts.SAVE_SUCCESS);
	}

	@Override
	public ResultDTO updateCategory(CategoryDO categoryDO) {
		categoryDO.setUpdatedBy(SysCodeConsts.OPERATOR);
		categoryDO.setUpdatedDate(new Date());
		categoryDao.updateCategory(categoryDO);
		return new ResultDTO(true, MessageConsts.UPDATE_SUCCESS);
	}

	@Override
	public ResultDTO batchDeleteCategories(String[] idCategories) {
		categoryDao.batchDeleteCategories(Arrays.asList(idCategories));
		return new ResultDTO(true, MessageConsts.DELETE_SUCCESS);
	}
	
	@Override
	public ResultDTO deleteCategory(String idCategory) {
		List<CategoryDO> categoryList = categoryDao.listCategoriesByIdParent(idCategory);
		if(categoryList != null && categoryList.size() == 0) {
			categoryDao.deleteCategory(idCategory);
		} else {
			return new ResultDTO(false, MessageConsts.HAS_SUB_GROUP_IN_GROUP);
		}
		return new ResultDTO(true, MessageConsts.DELETE_SUCCESS);
	}

}
