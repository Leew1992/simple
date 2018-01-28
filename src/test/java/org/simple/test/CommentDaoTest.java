package org.simple.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.simple.dao.CommentDao;
import org.simple.dto.CommentDTO;
import org.simple.dto.QueryDTO;
import org.simple.entity.CommentDO;
import org.simple.util.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;

public class CommentDaoTest extends BaseTest {
	
	private final Logger logger = Logger.getLogger(getClass());
	
	@Autowired
    private CommentDao commentDao;

	/**
     * 获取评论信息
     */
	@Test
    public void testGetCommentById() {
    	String idComment = "0";
    	CommentDO commentDO = commentDao.getCommentById(idComment);
    	logger.debug(JSON.toJSONString(commentDO));
    }
    
    /**
     * 获取评论分页信息
     */
	@Test
    public void pagingComments() {
    	CommentDTO commentDTO = new CommentDTO();
    	QueryDTO queryDTO = new QueryDTO();
    	Map<String, Object> paramMap = BeanUtil.convertBeansToMap(commentDTO, queryDTO);
    	List<CommentDTO> commentList = commentDao.pagingComments(paramMap);
    	logger.debug(JSON.toJSONString(commentList));
    }
    
    /**
     * 保存评论信息
     */
	@Test
    public void testSaveComment() {
    	CommentDO commentDO = new CommentDO();
    	commentDO.setIdPost("0");
    	commentDO.setIdUser("0");
    	commentDO.setContent("aa");
    	commentDO.setFavorNum(1);
    	commentDO.setStatus("1");
    	commentDO.setCreatedBy("aa");
    	commentDO.setCreatedDate(new Date());
    	commentDO.setUpdatedBy("aa");
    	commentDO.setUpdatedDate(new Date());
    	commentDao.saveComment(commentDO);
    	logger.debug("评论信息保存成功");
    }
    
    /**
     * 更新评论信息
     */
	@Test
    public void testUpdateComment() {
		CommentDO commentDO = new CommentDO();
		commentDO.setIdComment("1");
    	commentDO.setIdPost("0");
    	commentDO.setIdUser("0");
    	commentDO.setContent("aa");
    	commentDO.setFavorNum(1);
    	commentDO.setStatus("1");
    	commentDO.setCreatedBy("aa");
    	commentDO.setCreatedDate(new Date());
    	commentDO.setUpdatedBy("aa");
    	commentDO.setUpdatedDate(new Date());
    	commentDao.updateComment(commentDO);
    	logger.debug("评论信息更新成功");
	}
    
    /**
     * 删除评论信息
     */
	@Test
    public void testDeleteComment() {
    	String idComment = "0";
    	commentDao.deleteComment(idComment);
    	logger.debug("评论信息删除成功");
    }
    
    /**
     * 批量删除评论信息
     */
	@Test
    public void testbatchDeleteComments() {
    	List<String> idComments = new ArrayList<>();
    	idComments.add("0");
    	idComments.add("1");
    	commentDao.batchDeleteComments(idComments);
    	logger.debug("评论信息批量删除成功");
    }
}