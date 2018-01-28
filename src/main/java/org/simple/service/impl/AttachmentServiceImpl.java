package org.simple.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.simple.constant.MessageConst;
import org.simple.context.UserContext;
import org.simple.dao.AttachmentDao;
import org.simple.dao.PostDao;
import org.simple.dto.AttachmentDTO;
import org.simple.dto.PageDTO;
import org.simple.dto.QueryDTO;
import org.simple.dto.ResultDTO;
import org.simple.entity.AttachmentDO;
import org.simple.service.AttachmentService;
import org.simple.util.BeanUtil;
import org.springframework.stereotype.Service;

/**
 * 附件信息Service
 */
@Service
public class AttachmentServiceImpl implements AttachmentService {
	
	@Resource
	private AttachmentDao attachmentDao;
	
	@Resource
	private PostDao postDao;

	@Override
	public AttachmentDO getAttachmentById(String idAttachment) {
		return attachmentDao.getAttachmentById(idAttachment);
	}
	
	@Override
	public List<AttachmentDO> listAllAttachments() {
		return attachmentDao.listAllAttachments();
	}

	@Override
	public PageDTO pagingAttachments(AttachmentDTO attachmentDTO, QueryDTO queryDTO) {
		Map<String, Object> paramMap = BeanUtil.convertBeansToMap(attachmentDTO, queryDTO);
		List<AttachmentDO> attachmentList = attachmentDao.pagingAttachments(paramMap);
		PageDTO page = new PageDTO();
		page.setRows(attachmentList);
		page.setTotal(attachmentList.size());
		return page;
	}
	
	@Override
	public Integer countAttachmentsBasedFuzzy(Map<String, Object> paramMap) {
		return attachmentDao.countAttachmentsBasedFuzzy(paramMap);
	}
	
	@Override
	public Integer countAttachmentsBasedAccurate(Map<String, Object> paramMap) {
		return attachmentDao.countAttachmentsBasedAccurate(paramMap);
	}

	@Override
	public Integer countAttachmentsByCreatedDateForVideo(String accessDate) {
		return attachmentDao.countAttachmentsByCreatedDateForVideo(accessDate);
	}
	
	@Override
	public Integer countAttachmentsInCreatedDatesForVideo(
			List<String> accessDates) {
		return attachmentDao.countAttachmentsInCreatedDatesForVideo(accessDates);
	}

	@Override
	public ResultDTO saveAttachment(AttachmentDO attachmentDO) {
		attachmentDO.setCreatedBy(UserContext.getCurrentUserName());
		attachmentDO.setUpdatedBy(UserContext.getCurrentUserName());
		attachmentDao.saveAttachment(attachmentDO);
		return new ResultDTO(true, MessageConst.SAVE_SUCCESS);
	}
	
	@Override
	public ResultDTO updateAttachment(AttachmentDO attachmentDO) {
		attachmentDao.updateAttachment(attachmentDO);
		attachmentDO.setUpdatedBy(UserContext.getCurrentUserName());
		return new ResultDTO(true, MessageConst.UPDATE_SUCCESS);
	}

	@Override
	public ResultDTO deleteAttachment(String idAttachment) {
		postDao.deletePostAttachmentByAttachment(idAttachment);
		attachmentDao.deleteAttachment(idAttachment);
		return new ResultDTO(true, MessageConst.DELETE_SUCCESS);
	}

}
