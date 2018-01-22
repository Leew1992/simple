package org.simple.service.impl;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.simple.constant.MessageConsts;
import org.simple.context.UserContext;
import org.simple.dao.ColumnDao;
import org.simple.dao.PostDao;
import org.simple.dto.PageDTO;
import org.simple.dto.PostDTO;
import org.simple.dto.ResultDTO;
import org.simple.entity.ColumnPostDO;
import org.simple.entity.PostAttachmentDO;
import org.simple.entity.PostDO;
import org.simple.service.PostService;
import org.springframework.stereotype.Service;

/**
 * 贴子服务接口实现
 */
@Service
public class PostServiceImpl implements PostService {
	
	@Resource
	private PostDao postDao;
	
	@Resource
	private ColumnDao columnDao;
	
	@Override
	public PostDO getPostById(String idPost) {
		return postDao.getPostById(idPost);
	}

	@Override
	public List<PostDO> listPostsByIdColumn(String idColumn) {
		return postDao.listPostsByIdColumn(idColumn);
	}

	@Override
	public PageDTO pagingPosts(PostDTO postDTO) {
		List<PostDO> postList = postDao.pagingPosts(postDTO);
		PageDTO page = new PageDTO();
		page.setRows(postList);
		page.setTotal(postList.size());
		return page;
	}
	
	@Override
	public Integer countPostsByCreatedDate(String accessDate) {
		return postDao.countPostsByCreatedDate(accessDate);
	}
	
	@Override
	public Integer countPostsInCreatedDates(List<String> accessDates) {
		return postDao.countPostsInCreatedDates(accessDates);
	}

	@Override
	public ResultDTO savePost(PostDTO postDTO) throws Exception {
		try {
			// 用户信息
			postDTO.setCreatedBy(UserContext.getCurrentUserName());
			postDTO.setUpdatedBy(UserContext.getCurrentUserName());
			postDao.savePost(postDTO);
			
			// 字典-用户
			String[] idColumnArr = postDTO.getIdColumns().split(",");
			for(String idColumn : idColumnArr) {
				ColumnPostDO columnPostDO = new ColumnPostDO();
				columnPostDO.setIdColumn(idColumn);
				columnPostDO.setIdPost(postDTO.getIdPost());
				columnPostDO.setCreatedBy(UserContext.getCurrentUserName());
				columnPostDO.setUpdatedBy(UserContext.getCurrentUserName());
				columnDao.saveColumnPost(columnPostDO);
			}
			return new ResultDTO(true, MessageConsts.SAVE_SUCCESS);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	@Override
	public ResultDTO updatePost(PostDO postDO, String idColumns) throws Exception {
		try {
			// 用户信息
			postDO.setUpdatedBy(UserContext.getCurrentUserName());
			postDao.updatePost(postDO);
			columnDao.deleteColumnPost(postDO.getIdPost());
			
			// 字典-用户
			String[] idColumnArr = idColumns.split(",");
			for(String idColumn : idColumnArr) {
				ColumnPostDO columnPostDO = new ColumnPostDO();
				columnPostDO.setIdColumn(idColumn);
				columnPostDO.setIdPost(postDO.getIdPost());
				columnPostDO.setCreatedBy(UserContext.getCurrentUserName());
				columnPostDO.setUpdatedBy(UserContext.getCurrentUserName());
				columnDao.saveColumnPost(columnPostDO);
			}
			return new ResultDTO(true, MessageConsts.SAVE_SUCCESS);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	@Override
	public ResultDTO batchDeletePosts(String[] idPosts) throws Exception {
		try {
			postDao.batchDeletePosts(Arrays.asList(idPosts));
			columnDao.batchDeleteColumnPosts(Arrays.asList(idPosts));
			return new ResultDTO(true, MessageConsts.DELETE_SUCCESS);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	@Override
	public ResultDTO savePostAttachment(PostAttachmentDO postAttachmentDO) {
		postAttachmentDO.setCreatedBy(UserContext.getCurrentUserName());
		postAttachmentDO.setUpdatedBy(UserContext.getCurrentUserName());
		postDao.savePostAttachment(postAttachmentDO);
		return new ResultDTO(true, MessageConsts.SAVE_SUCCESS);
	}
}
