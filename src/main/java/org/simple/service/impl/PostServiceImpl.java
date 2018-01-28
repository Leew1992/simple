package org.simple.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Resource;

import org.simple.constant.ExceptionEnum;
import org.simple.constant.MessageConst;
import org.simple.constant.SysCodeConst;
import org.simple.context.UserContext;
import org.simple.dao.ColumnDao;
import org.simple.dao.PostDao;
import org.simple.dao.UserDao;
import org.simple.dto.PageDTO;
import org.simple.dto.PostDTO;
import org.simple.dto.QueryDTO;
import org.simple.dto.ResultDTO;
import org.simple.entity.ColumnPostDO;
import org.simple.entity.PostAttachmentDO;
import org.simple.entity.PostDO;
import org.simple.entity.UserDO;
import org.simple.exception.ServiceException;
import org.simple.service.PostService;
import org.simple.util.BeanUtil;
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
	
	@Resource
	private UserDao userDao;
	
	@Override
	public PostDO getPostById(String idPost) {
		return postDao.getPostById(idPost);
	}

	@Override
	public List<PostDO> listPostsByIdColumn(String idColumn) {
		return postDao.listPostsByIdColumn(idColumn);
	}

	@Override
	public PageDTO pagingPosts(PostDTO postDTO, QueryDTO queryDTO) {
		Map<String, Object> paramMap = BeanUtil.convertBeansToMap(postDTO, queryDTO);
		List<PostDO> postList = postDao.pagingPosts(paramMap);
		PageDTO page = new PageDTO();
		page.setRows(postList);
		page.setTotal(postList.size());
		return page;
	}
	
	@Override
	public Integer countPostsBasedFuzzy(String createdDate) {
		return postDao.countPostsBasedFuzzy(createdDate);
	}
	
	@Override
	public Integer countPostsBasedAccurate(List<String> createdDates) {
		return postDao.countPostsBasedAccurate(createdDates);
	}

	@Override
	public ResultDTO savePost(PostDTO postDTO) {
		
		String userName = UserContext.getCurrentUserName();
		if(Objects.equals(userName, SysCodeConst.UNKNOWN)) {
			throw new ServiceException(ExceptionEnum.USER_NOT_EXIST.getCode(), ExceptionEnum.USER_NOT_EXIST.getName());
		}
		
		UserDO userDO = userDao.getUserByName(userName);
		if(Objects.isNull(userDO)) {
			throw new ServiceException(ExceptionEnum.USER_NOT_EXIST.getCode(), ExceptionEnum.USER_NOT_EXIST.getName());
		}
		
		// 用户信息
		postDTO.setIdUser(userDO.getIdUser());
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
		return new ResultDTO(true, MessageConst.SAVE_SUCCESS);
	}

	@Override
	public ResultDTO updatePost(PostDO postDO, String idColumns) {
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
		return new ResultDTO(true, MessageConst.SAVE_SUCCESS);
	}

	@Override
	public ResultDTO batchDeletePosts(String[] idPosts) {
		postDao.batchDeletePosts(Arrays.asList(idPosts));
		columnDao.batchDeleteColumnPosts(Arrays.asList(idPosts));
		return new ResultDTO(true, MessageConst.DELETE_SUCCESS);
	}

	@Override
	public ResultDTO savePostAttachment(PostAttachmentDO postAttachmentDO) {
		postAttachmentDO.setCreatedBy(UserContext.getCurrentUserName());
		postAttachmentDO.setUpdatedBy(UserContext.getCurrentUserName());
		postDao.savePostAttachment(postAttachmentDO);
		return new ResultDTO(true, MessageConst.SAVE_SUCCESS);
	}
}
