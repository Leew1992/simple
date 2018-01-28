package org.simple.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.simple.dao.ColumnDao;
import org.simple.dto.TreeNode;
import org.simple.entity.ColumnDO;
import org.simple.entity.ColumnPostDO;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;

public class ColumnDaoTest extends BaseTest {
	
	private final Logger logger = Logger.getLogger(getClass());
	
	@Autowired
    private ColumnDao columnDao;

	/**
	 * 获取栏目信息
	 */
	@Test
    public void testGetColumnById() {
    	String idColumn = "0";
    	ColumnDO columnDO = columnDao.getColumnById(idColumn);
    	logger.debug(JSON.toJSONString(columnDO));
    }
    
    /**
     * 获取栏目列表
     */
    @Test
    public void testListColumnsByIdParent() {
    	String idParent = "0";
    	List<ColumnDO> columnList = columnDao.listColumnsByIdParent(idParent);
    	logger.debug(JSON.toJSONString(columnList));
    }

    /**
     * 获取栏目树
     */
    @Test
    public void testGetColumnTreeForPostForm() {
    	String idPost = "0";
    	List<TreeNode> treeNodeList = columnDao.getHasCheckedColumnTreeByIdPost(idPost);
    	logger.debug(JSON.toJSONString(treeNodeList));
    }
    
    /**
     * 获取栏目列表
     */
    @Test
    public void testListAllColumns() {
    	List<ColumnDO> columnList = columnDao.listAllColumns();
    	logger.debug(JSON.toJSONString(columnList));
    }

    /**
     * 保存栏目信息
     */
    @Test
    public void testSaveColumn() {
    	ColumnDO columnDO = new ColumnDO();
    	columnDO.setIdParent("11");
    	columnDO.setColumnName("aa");
    	columnDO.setColumnDesc("aa");
    	columnDO.setCreatedBy("aa");
    	columnDO.setCreatedDate(new Date());
    	columnDO.setUpdatedBy("aa");
    	columnDO.setUpdatedDate(new Date());
    	columnDao.saveColumn(columnDO);
    	logger.debug("栏目信息保存成功");
    }
    
    /**
     * 更新栏目信息
     */
    @Test
    public void testUpdateColumn() {
    	ColumnDO columnDO = new ColumnDO();
    	columnDO.setIdColumn("0");
    	columnDO.setColumnName("aa");
    	columnDO.setColumnDesc("aa");
    	columnDO.setCreatedBy("aa");
    	columnDO.setCreatedDate(new Date());
    	columnDO.setUpdatedBy("aa");
    	columnDO.setUpdatedDate(new Date());
    	columnDao.updateColumn(columnDO);
    	logger.debug("栏目信息更新成功");
    	
    }

    /**
     * 删除栏目信息
     */
    @Test
    public void testDeleteColumn() {
    	String idColumn = "0";
    	columnDao.deleteColumn(idColumn);
    	logger.debug("栏目信息删除成功");
    }
    
    /**
     * 批量批除栏目信息
     */
    @Test
    public void testBatchDeleteColumns() {
    	List<String> idColumns = new ArrayList<>();
    	idColumns.add("0");
    	idColumns.add("1");
    	columnDao.batchDeleteColumns(idColumns);
    	logger.debug("栏目信息批量删除成功");
    }
    
    /**
     * 获取栏目贴子
     */
    @Test
    public void testGetColumnPostByIdPost() {
    	String idPost = "0";
    	ColumnPostDO columnPostDO = columnDao.getColumnPostByIdPost(idPost);
    	logger.debug(JSON.toJSONString(columnPostDO));
    }
    
    /**
     * 保存栏目贴子信息
     */
    @Test
    public void testSaveColumnPost() {
    	ColumnPostDO columnPostDO = new ColumnPostDO();
    	columnPostDO.setIdColumn("0");
    	columnPostDO.setIdPost("0");
    	columnPostDO.setCreatedBy("aa");
    	columnPostDO.setCreatedDate(new Date());
    	columnPostDO.setUpdatedBy("aa");
    	columnPostDO.setUpdatedDate(new Date());
    	columnDao.saveColumnPost(columnPostDO);
    }

    /**
     * 删除栏目贴子信息
     */
    @Test
    public void testDeleteColumnPost() {
    	String idPost = "0";
    	columnDao.deleteColumn(idPost);
    	logger.debug("栏目贴子信息删除成功");
    }
    
    /**
     * 批量删除栏目贴子信息
     */
    @Test
    public void testBatchDeleteColumnPost() {
    	List<String> idPosts = new ArrayList<>();
    	idPosts.add("0");
    	idPosts.add("1");
    	columnDao.batchDeleteColumnPosts(idPosts);
    	logger.debug("栏目贴子信息批量删除成功");
    }
}