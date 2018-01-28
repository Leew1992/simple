package org.simple.service;

import java.util.List;
import java.util.Map;

import org.simple.dto.AttachmentDTO;
import org.simple.dto.PageDTO;
import org.simple.dto.QueryDTO;
import org.simple.dto.ResultDTO;
import org.simple.entity.AttachmentDO;

/**
 * 附件服务接口
 */
public interface AttachmentService {
	
	/**
	 * 获取附件信息
	 */
	AttachmentDO getAttachmentById(String idAttachment);
	
	/**
	 * 获取所有附件信息
	 */
	List<AttachmentDO> listAllAttachments();
	
	/**
	 * 获取附件分页列表
	 */
	PageDTO pagingAttachments(AttachmentDTO attachmentDTO, QueryDTO queryDTO);
	
    /**
     * 统计图片附件数量
     */
    Integer countAttachmentsBasedFuzzy(Map<String, Object> paramMap);
    
    /**
     * 统计图片附件数量
     */
    Integer countAttachmentsBasedAccurate(Map<String, Object> paramMap);
    
    /**
     * 统计视频附件数量
     */
    Integer countAttachmentsByCreatedDateForVideo(String accessDate);
    
    /**
     * 统计视频附件数量
     */
    Integer countAttachmentsInCreatedDatesForVideo(List<String> accessDates);
	
	/**
	 * 保存附件信息
	 */
	ResultDTO saveAttachment(AttachmentDO attachmentDO);
	
	/**
	 * 更新附件信息
	 */
	ResultDTO updateAttachment(AttachmentDO attachmentDO);
	
	/**
	 * 删除附件信息
	 */
	ResultDTO deleteAttachment(String idAttachment);
}
