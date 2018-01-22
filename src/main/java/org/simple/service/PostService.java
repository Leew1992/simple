package org.simple.service;

import java.util.List;

import org.simple.dto.PageDTO;
import org.simple.dto.PostDTO;
import org.simple.dto.ResultDTO;
import org.simple.entity.PostAttachmentDO;
import org.simple.entity.PostDO;

/**
 * 贴子服务接口
 *
 */
public interface PostService {
	
	/**
	 * 获取贴子信息
	 */
	PostDO getPostById(String idPost);
	
	/**
	 * 获取贴子列表
	 */
	List<PostDO> listPostsByIdColumn(String idColumn);
	
	/**
	 * 获取贴子列表
	 */
	PageDTO pagingPosts(PostDTO postDTO);
	
	/**
     * 统计贴子数量
     */
    Integer countPostsByCreatedDate(String accessDate);
    
    /**
     * 统计所有贴子数量
     */
    Integer countPostsInCreatedDates(List<String> accessDates);
	
	/**
	 * 保存贴子信息
	 */
	ResultDTO savePost(PostDTO postDTO) throws Exception;
	
	/**
	 * 更新贴子信息
	 */
	ResultDTO updatePost(PostDO postDO, String idPosts) throws Exception;
	
	/**
	 * 删除贴子信息
	 */
	ResultDTO batchDeletePosts(String[] idPosts) throws Exception;
	
    /**
     * 保存贴子附件信息
     */
	ResultDTO savePostAttachment(PostAttachmentDO postAttachmentDO);
	
}
