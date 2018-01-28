package org.simple.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.simple.dao.AttachmentDao;
import org.simple.entity.AttachmentDO;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;

public class AttachmentDaoTest extends BaseTest {
	
	private final Logger logger = Logger.getLogger(getClass());
	
	@Autowired
    private AttachmentDao attachmentDao;

	/**
	 * 获取附件信息
	 */
    @Test
    public void testGetAttachmentById() {
    	AttachmentDO attachmentDO = attachmentDao.getAttachmentById("0");
    	logger.debug(JSON.toJSONString(attachmentDO));
    }
    
    /**
	 * 获取附件列表
	 */
    @Test
    public void testListAttachments() {
    	List<AttachmentDO> attachmentList = attachmentDao.listAllAttachments();
    	logger.debug(JSON.toJSONString(attachmentList));
    }
    
    /**
     * 保存附件信息
     */
    @Test
    public void testSaveAttachment() {
    	AttachmentDO attachmentDO = new AttachmentDO();
    	attachmentDO.setAttachName("aa");
    	attachmentDO.setAttachPath("aa");
    	attachmentDO.setAttachSize(1L);
    	attachmentDO.setMediaType("aa");
    	attachmentDO.setAttachSource("aa");
    	attachmentDO.setCreatedBy("aa");
    	attachmentDO.setCreatedDate(new Date());
    	attachmentDO.setUpdatedBy("aa");
    	attachmentDO.setUpdatedDate(new Date());
    	attachmentDao.saveAttachment(attachmentDO);
    	logger.debug("附件信息保存成功");
    }
    
    /**
     * 更新附件信息
     */
    @Test
    public void testUpdateAttachment() {
    	AttachmentDO attachmentDO = new AttachmentDO();
    	attachmentDO.setIdAttachment("0");
    	attachmentDO.setAttachName("bb");
    	attachmentDO.setAttachPath("bb");
    	attachmentDO.setAttachSize(1L);
    	attachmentDO.setMediaType("bb");
    	attachmentDO.setAttachSource("bb");
    	attachmentDO.setCreatedBy("bb");
    	attachmentDO.setCreatedDate(new Date());
    	attachmentDO.setUpdatedBy("bb");
    	attachmentDO.setUpdatedDate(new Date());
    	attachmentDao.updateAttachment(attachmentDO);
    	logger.debug("附件信息更新成功");
    }
    
    /**
     * 删除附件信息
     */
    @Test
    public void testDeleteAttachment() {
    	String idAttachment = "1";
    	attachmentDao.deleteAttachment(idAttachment);
    	logger.debug("附件信息删除成功");
    }
    
    /**
     * 批量删除附件信息
     */
    @Test
    public void testBatchDeleteAttachments() {
    	List<String> idAttachments = new ArrayList<>();
    	idAttachments.add("0");
    	idAttachments.add("1");
    	attachmentDao.batchDeleteAttachments(idAttachments);
    	logger.debug("附件信息批量删除成功");
    }
}
