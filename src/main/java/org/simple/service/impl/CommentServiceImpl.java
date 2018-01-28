package org.simple.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.simple.constant.MessageConst;
import org.simple.context.UserContext;
import org.simple.dao.CategoryDao;
import org.simple.dao.CommentDao;
import org.simple.dao.PostDao;
import org.simple.dto.CommentDTO;
import org.simple.dto.PageDTO;
import org.simple.dto.QueryDTO;
import org.simple.dto.ResultDTO;
import org.simple.entity.CommentDO;
import org.simple.entity.PostCommentDO;
import org.simple.service.CommentService;
import org.simple.util.BeanUtil;
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
		return new ResultDTO(true, MessageConst.SHOW_SUCCESS);
	}

	@Override
	public List<CommentDO> listAllComments() {
		return commentDao.listAllComments();
	}

	@Override
	public PageDTO pagingComments(CommentDTO commentDTO, QueryDTO queryDTO) {
		Map<String, Object> paramMap = BeanUtil.convertBeansToMap(commentDTO, queryDTO);
		List<CommentDTO> commentList = commentDao.pagingComments(paramMap);
		PageDTO pageDTO = new PageDTO();
		pageDTO.setRows(commentList);
		pageDTO.setTotal(commentList.size());
		return pageDTO;
	}

	@Override
	public Integer countCommentsBasedFuzzy(String createdDate) {
		return commentDao.countCommentsBasedFuzzy(createdDate);
	}

	@Override
	public Integer countCommentsBasedAccurate(List<String> createdDates) {
		return commentDao.countCommentsBasedAccurate(createdDates);
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

		return new ResultDTO(true, MessageConst.SAVE_SUCCESS);
	}

	@Override
	public ResultDTO updateComment(CommentDTO commentDTO) {
		commentDTO.setUpdatedBy(UserContext.getCurrentUserName());
		commentDao.updateComment(commentDTO);
		return new ResultDTO(true, MessageConst.UPDATE_SUCCESS);
	}

	@Override
	public ResultDTO stickCommentById(String idComment) {
		commentDao.stickCommentById(idComment);
		return new ResultDTO(true, MessageConst.STICK_SUCCESS);
	}

	@Override
	public ResultDTO batchDeleteComments(String idComments) {
		String[] idCommentArr = idComments.split(",");
		commentDao.batchDeleteComments(Arrays.asList(idCommentArr));
		return new ResultDTO(true, MessageConst.DELETE_SUCCESS);
	}

}
