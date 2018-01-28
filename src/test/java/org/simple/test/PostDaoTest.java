package org.simple.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.simple.dao.PostDao;
import org.simple.dto.PostDTO;
import org.simple.dto.QueryDTO;
import org.simple.entity.PostDO;
import org.simple.util.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;

public class PostDaoTest extends BaseTest {
	
	private final Logger logger = Logger.getLogger(getClass());
	
	@Autowired
    private PostDao postDao;

	/**
     * 获取贴子信息
     */
	@Test
    public void getPostById() {
    	String idPost = "0";
    	PostDO postDO = postDao.getPostById(idPost);
    	logger.debug(JSON.toJSONString(postDO));
    }
	
	/**
	 * 获取贴子列表
	 */
	@Test
	public void listPostsByIdColumn() {
		String idColumn = "0";
		List<PostDO> postList = postDao.listPostsByIdColumn(idColumn);
		logger.debug(JSON.toJSONString(postList));
	}
    
    /**
     * 获取贴子分页信息
     */
	@Test
    public void pagingPosts() {
    	PostDTO postDTO = new PostDTO();
    	postDTO.setIdPost("0");
    	QueryDTO queryDTO = new QueryDTO();
    	Map<String, Object> paramMap = BeanUtil.convertBeansToMap(postDTO, queryDTO);
    	List<PostDO> postList = postDao.pagingPosts(paramMap);
    	logger.debug(JSON.toJSONString(postList));
    }
    
    /**
     * 保存贴子信息
     */
	@Test
    public void savePost() {
    	PostDO postDO = new PostDO();
    	postDO.setIdUser("0");
    	postDO.setTitle("aa");
    	postDO.setSummary("aa");
    	postDO.setTags("aa");
    	postDO.setContent("aa");
    	postDO.setCollectNum(1);
    	postDO.setFavorNum(1);
    	postDO.setCreatedBy("aa");
    	postDO.setCreatedDate(new Date());
    	postDO.setUpdatedBy("aa");
    	postDO.setUpdatedDate(new Date());
    	postDao.savePost(postDO);
    	logger.debug("贴子信息保存成功");
    }
    
    /**
     * 更新贴子信息
     */
	@Test
    public void updatePost() {
    	PostDO postDO = postDao.getPostById("1");
    	postDO.setTitle("bb");
    	postDO.setSummary("bb");
    	postDao.updatePost(postDO);
    	logger.debug("贴子信息更新成功");
    }
    
    /**
     * 删除贴子信息
     */
	@Test
    public void deletePost() {
    	String idPost = "0";
    	postDao.deletePost(idPost);
    	logger.debug("贴子信息删除成功");
    }
    
    /**
     * 批量删除贴子信息
     */
	@Test
    public void batchDeletePost() {
		List<String> idPosts = new ArrayList<>();
		idPosts.add("0");
		idPosts.add("1");
		postDao.batchDeletePosts(idPosts);
		logger.debug("贴子信息批量删除成功");
	}
}