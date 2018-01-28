package org.simple.controller;

import org.simple.dto.PageDTO;
import org.simple.dto.PostDTO;
import org.simple.dto.QueryDTO;
import org.simple.dto.ResultDTO;
import org.simple.entity.PostDO;
import org.simple.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 贴子控制器
 */
@Controller
@RequestMapping("/post")
public class PostController extends BaseController {
	
	@Autowired
	private PostService postService;
	
	/**
	 * 获取贴子信息
	 */
	@RequestMapping("/getPostById.do")
	@ResponseBody
	public PostDO getPostById(String idPost) {
		return postService.getPostById(idPost);
	}
	
	/**
	 * 获取贴子分页列表
	 */
	@RequestMapping("/pagingPosts.do")
	@ResponseBody
	public PageDTO pagingPosts(PostDTO postDTO, QueryDTO queryDTO) {
		return postService.pagingPosts(postDTO, queryDTO);
	}
	
	/**
	 * 保存贴子信息
	 */
	@RequestMapping("/savePost.do")
	@ResponseBody
	public ResultDTO savePost(PostDTO postDTO) {
		return postService.savePost(postDTO);
	}
	
	/**
	 * 更新贴子信息
	 */
	@RequestMapping("/updatePost.do")
	@ResponseBody
	public ResultDTO updatePost(PostDO post, String idColumns) {
		return postService.updatePost(post, idColumns);
	}
	
	/**
	 * 删除贴子信息
	 */
	@RequestMapping("/batchDeletePosts.do")
	@ResponseBody
	public ResultDTO batchDeletePosts(String idPosts) {
		return postService.batchDeletePosts(idPosts.split(","));
	}
}
