package org.simple.service.impl;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.simple.constant.MessageConsts;
import org.simple.context.UserContext;
import org.simple.dao.CategoryDao;
import org.simple.dao.CommentDao;
import org.simple.dao.PostDao;
import org.simple.dto.CommentDTO;
import org.simple.dto.PageDTO;
import org.simple.dto.ResultDTO;
import org.simple.entity.CommentDO;
import org.simple.entity.PostCommentDO;
import org.simple.service.CommentService;
import org.springframework.stereotype.Service;

/**
 * 评论Service实现
 */
@Service
public class CommentServiceImpl implements CommentService {
	
	@Resource
	private CategoryDao categoryDao;
	
	@Resource
	private PostDao postDao;
	
	@Resource
	private CommentDao commentDao;
	
	@Override
	public CommentDO getCommentById(String id) {
		return commentDao.getCommentById(id);
	}
	
	@Override
	public ResultDTO listCommentsByIds(String idComments) {
		String[] idCommentArr = idComments.split(",");
		commentDao.listCommentsByIds(Arrays.asList(idCommentArr));
		return new ResultDTO(true, MessageConsts.SHOW_SUCCESS);
	}

	@Override
	public List<CommentDO> listAllComments() {
		return commentDao.listAllComments();
	}

	@Override
	public PageDTO pagingComments(CommentDTO commentDTO) {
		List<CommentDTO> commentList = commentDao.pagingComments(commentDTO);
		PageDTO pageDTO = new PageDTO();
		pageDTO.setRows(commentList);
		pageDTO.setTotal(commentList.size());
		return pageDTO;
	}
	
	@Override
	public Integer countCommentsByCreatedDate(String accessDate) {
		return commentDao.countCommentsByCreatedDate(accessDate);
	}
	
	@Override
	public Integer countCommentsInCreatedDates(List<String> accessDates) {
		return commentDao.countCommentsInCreatedDates(accessDates);
	}
	
	@Override
	public ResultDTO saveComment(CommentDTO commentDTO) {
		commentDTO.setCreatedBy(UserContext.getCurrentUserName());
		commentDTO.setUpdatedBy(UserContext.getCurrentUserName());
		commentDao.saveComment(commentDTO);
		
		PostCommentDO postCommentDO = new PostCommentDO();
		postCommentDO.setIdPost(commentDTO.getIdPost());
		postCommentDO.setCreatedBy(UserContext.getCurrentUserName());
		postCommentDO.setUpdatedBy(UserContext.getCurrentUserName());
		postDao.savePostComment(postCommentDO);
		
		return new ResultDTO(true, MessageConsts.SAVE_SUCCESS);
	}
	
	@Override
	public ResultDTO updateComment(CommentDTO commentDTO) {
		commentDTO.setUpdatedBy(UserContext.getCurrentUserName());
		commentDao.updateComment(commentDTO);
		return new ResultDTO(true, MessageConsts.UPDATE_SUCCESS);
	}
	
	@Override
	public ResultDTO stickCommentById(String idComment) {
		commentDao.stickCommentById(idComment);
		return new ResultDTO(true, MessageConsts.STICK_SUCCESS);
	}

	@Override
	public ResultDTO batchDeleteComments(String idComments) {
		String[] idCommentArr = idComments.split(",");
		commentDao.batchDeleteComments(Arrays.asList(idCommentArr));
		return new ResultDTO(true, MessageConsts.DELETE_SUCCESS);
	}

}
