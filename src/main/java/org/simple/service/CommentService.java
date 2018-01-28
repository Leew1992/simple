package org.simple.service;

import java.util.List;

import org.simple.dto.CommentDTO;
import org.simple.dto.PageDTO;
import org.simple.dto.QueryDTO;
import org.simple.dto.ResultDTO;
import org.simple.entity.CommentDO;

/**
 * 评论服务接口
 */
public interface CommentService {
	
	/**
	 * 获取评论信息
	 */
	CommentDO getCommentById(String id);
	
	/**
	 * 获取评论列表
	 */
	ResultDTO listCommentsByIds(String idComments);
	
	/**
	 * 获取所有评论
	 */
	List<CommentDO> listAllComments();
	
	/**
	 * 获取评论分页
	 */
	PageDTO pagingComments(CommentDTO commentDTO, QueryDTO queryDTO);
	
    /**
     * 统计评论数量
     */
    Integer countCommentsBasedFuzzy(String createdDate);
    
    /**
     * 统计评论数量
     */
    Integer countCommentsBasedAccurate(List<String> createdDates);
	
	/**
	 * 保存评论信息
	 */
	ResultDTO saveComment(CommentDTO commentDTO);
	
	/**
	 * 更新评论信息
	 */
	ResultDTO updateComment(CommentDTO commentDTO);
	
	/**
	 * 置顶评论信息
	 */
	ResultDTO stickCommentById(String idComment);
	
	/**
	 * 批量删除评论信息
	 */
	ResultDTO batchDeleteComments(String idComments);
	
}
