package org.simple.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.simple.dao.CategoryDao;
import org.simple.dto.TreeNode;
import org.simple.entity.CategoryDO;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;

public class CategoryDaoTest extends BaseTest {
	
	private final Logger logger = Logger.getLogger(getClass());
	
	@Autowired
    private CategoryDao categoryDao;

	/**
	 * 获取类别信息
	 */
	@Test
    public void testGetCategoryById() {
    	String idCategory = "0";
    	CategoryDO categoryDO = categoryDao.getCategoryById(idCategory);
    	logger.debug(JSON.toJSONString(categoryDO));
    }

    /**
     * 获取类别列表
     */
	@Test
    public void testListCategoriesByIdParent() {
		String idParent = "0";
		List<CategoryDO> categoryList = categoryDao.listCategoriesByIdParent(idParent);
		logger.debug(JSON.toJSONString(categoryList));
	}

    /**
     * 获取类别列表
     */
	@Test
    public void testListAllCategories() {
    	List<CategoryDO> categoryList = categoryDao.listAllCategories();
    	logger.debug(JSON.toJSONString(categoryList));
    }

    /**
     * 获取类别树
     */
	@Test
    public void testGetCategoryTreeForCommentForm() {
		String idComment = "0";
		List<TreeNode> treeNodeList = categoryDao.getHasCheckedCategoryTreeByIdComment(idComment);
		logger.debug(treeNodeList);
	}

    /**
     * 保存类别信息
     */
	@Test
    public void testSaveCategory() {
    	CategoryDO categoryDO = new CategoryDO();
    	categoryDO.setIdParent("0");
    	categoryDO.setCategoryName("aa");
    	categoryDO.setCategoryDesc("aa");
    	categoryDO.setCreatedBy("aa");
    	categoryDO.setCreatedDate(new Date());
    	categoryDO.setUpdatedBy("aa");
    	categoryDO.setUpdatedDate(new Date());
    	categoryDao.saveCategory(categoryDO);
    	logger.debug("类别信息保存成功");
    }

    /**
     * 更新类别信息
     */
	@Test
    public void testUpdateCategory() {
    	CategoryDO categoryDO = new CategoryDO();
    	categoryDO.setIdCategory("0");
    	categoryDO.setIdParent("0");
    	categoryDO.setCategoryName("aa");
    	categoryDO.setCategoryDesc("aa");
    	categoryDO.setCreatedBy("aa");
    	categoryDO.setCreatedDate(new Date());
    	categoryDO.setUpdatedBy("aa");
    	categoryDO.setUpdatedDate(new Date());
    	categoryDao.updateCategory(categoryDO);
    	logger.debug("类别信息更新成功");
    }

    /**
     * 删除类别
     */
	@Test
    public void testDeleteCategory() {
    	String idCategory = "0";
    	categoryDao.deleteCategory(idCategory);
    	logger.debug("类别信息删除成功");
    }

    /**
     * 批量删除类别
     */
	@Test
    public void testBatchDeleteCategories() {
		List<String> idCategories = new ArrayList<>();
		idCategories.add("0");
		idCategories.add("1");
		categoryDao.batchDeleteCategories(idCategories);
		logger.debug("类别信息批量删除成功");
	}
}