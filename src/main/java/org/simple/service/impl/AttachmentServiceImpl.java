package org.simple.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.simple.constant.MessageConsts;
import org.simple.dao.AttachmentDao;
import org.simple.dto.AttachmentDTO;
import org.simple.dto.Page;
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

	@Override
	public AttachmentDO getAttachmentById(String idAttachment) {
		return attachmentDao.getAttachmentById(idAttachment);
	}
	
	@Override
	public List<AttachmentDO> listAllAttachments() {
		return attachmentDao.listAllAttachments();
	}

	@Override
	public Page<AttachmentDO> pagingAttachments(AttachmentDTO attachmentDTO) {
		List<AttachmentDO> attachmentList = attachmentDao.pagingAttachments(attachmentDTO);
		Page<AttachmentDO> attachmentPage = new Page<AttachmentDO>();
		attachmentPage.setResult(attachmentList);
		return attachmentPage;
	}

	@Override
	public ResultDTO saveAttachment(AttachmentDO attachmentDO) {
		attachmentDao.saveAttachment(attachmentDO);
		return new ResultDTO(true, MessageConsts.SAVE_SUCCESS);
	}
	
	@Override
	public ResultDTO updateAttachment(AttachmentDO attachmentDO) {
		attachmentDao.updateAttachment(attachmentDO);
		return new ResultDTO(true, MessageConsts.UPDATE_SUCCESS);
	}

	@Override
	public ResultDTO deleteAttachment(String idAttachment) {
		attachmentDao.deleteAttachment(idAttachment);
		return new ResultDTO(true, MessageConsts.DELETE_SUCCESS);
	}

}
