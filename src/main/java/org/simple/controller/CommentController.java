package org.simple.controller;

import java.util.List;

import org.simple.dto.CommentDTO;
import org.simple.dto.PageDTO;
import org.simple.dto.QueryDTO;
import org.simple.dto.ResultDTO;
import org.simple.entity.CommentDO;
import org.simple.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 评论控制器
 */
@Controller
@RequestMapping("/comment")
public class CommentController {

	@Autowired
	private CommentService commentService;
	
	/**
	 * 获取评论信息
	 */
	@RequestMapping("/getCommentById.do")
	@ResponseBody
	public CommentDO getCommentById(String idComment) {
		return commentService.getCommentById(idComment);
	}
	
	/**
	 * 获取所有评论
	 */
	@RequestMapping("/listAllComments.do")
	@ResponseBody
	public List<CommentDO> listAllComments() {
		return commentService.listAllComments();
	}
	
	/**
	 * 获取评论分页列表
	 */
	@RequestMapping("/pagingComments.do")
	@ResponseBody
	public PageDTO pagingComments(CommentDTO commentDTO, QueryDTO queryDTO) {
		return commentService.pagingComments(commentDTO, queryDTO);
	}
	
	/**
	 * 保存评论信息
	 */
	@RequestMapping("/saveComment.do")
	@ResponseBody
	public ResultDTO saveComment(CommentDTO commentDTO) {
		return commentService.saveComment(commentDTO);
	}
	
	/**
	 * 置顶评论信息
	 */
	@RequestMapping("/stickCommentById.do")
	@ResponseBody
	public ResultDTO stickCommentById(String idComment) {
		return commentService.stickCommentById(idComment);
	}
	
	
	/**
	 * 批量删除评论信息
	 */
	@RequestMapping("/batchDeleteComments.do")
	@ResponseBody
	public ResultDTO batchDeleteComments(String idComments) {
		return commentService.batchDeleteComments(idComments);
	}
}
