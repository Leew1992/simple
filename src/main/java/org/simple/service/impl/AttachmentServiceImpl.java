package org.simple.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.simple.constant.MessageConsts;
import org.simple.context.UserContext;
import org.simple.dao.AttachmentDao;
import org.simple.dao.PostDao;
import org.simple.dto.AttachmentDTO;
import org.simple.dto.PageDTO;
import org.simple.dto.ResultDTO;
import org.simple.entity.AttachmentDO;
import org.simple.service.AttachmentService;
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
	public PageDTO pagingAttachments(AttachmentDTO attachmentDTO) {
		List<AttachmentDO> attachmentList = attachmentDao.pagingAttachments(attachmentDTO);
		PageDTO page = new PageDTO();
		page.setRows(attachmentList);
		page.setTotal(attachmentList.size());
		return page;
	}
	
	@Override
	public Integer countAttachmentsByCreatedDateForPicture(String accessDate) {
		return attachmentDao.countAttachmentsByCreatedDateForPicture(accessDate);
	}
	
	@Override
	public Integer countAttachmentsInCreatedDatesForPicture(
			List<String> accessDates) {
		return attachmentDao.countAttachmentsInCreatedDatesForPicture(accessDates);
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
		return new ResultDTO(true, MessageConsts.SAVE_SUCCESS);
	}
	
	@Override
	public ResultDTO updateAttachment(AttachmentDO attachmentDO) {
		attachmentDao.updateAttachment(attachmentDO);
		attachmentDO.setUpdatedBy(UserContext.getCurrentUserName());
		return new ResultDTO(true, MessageConsts.UPDATE_SUCCESS);
	}

	@Override
	public ResultDTO deleteAttachment(String idAttachment) {
		postDao.deletePostAttachmentByAttachment(idAttachment);
		attachmentDao.deleteAttachment(idAttachment);
		return new ResultDTO(true, MessageConsts.DELETE_SUCCESS);
	}

}
