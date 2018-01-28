package org.simple.dao;

import java.util.List;
import java.util.Map;

import org.simple.entity.AttachmentDO;
import org.springframework.stereotype.Repository;

@Repository
public interface AttachmentDao {
	
	/**
	 * 获取附件信息
	 */
    AttachmentDO getAttachmentById(String idAttachment);
   
    /**
     * 获取附件列表
     */
    List<AttachmentDO> listAttachmentsByIdUser(String idUser);
    
    /**
     * 获取附件列表
     */
    List<AttachmentDO> listAllAttachments();
    
    /**
     * 获取附件分页
     */
    List<AttachmentDO> pagingAttachments(Map<String, Object> paramMap);
    
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
    void saveAttachment(AttachmentDO attachmentDO);

    /**
     * 更新附件信息
     */
    void updateAttachment(AttachmentDO attachmentDO);

    /**
     * 删除附件信息
     */
    void deleteAttachment(String idAttachment);
    
    /**
     * 批量删除附件信息
     */
    void batchDeleteAttachments(List<String> idAttachments);
}
