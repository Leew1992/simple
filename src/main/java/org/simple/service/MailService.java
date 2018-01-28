package org.simple.service;

import java.util.List;

import org.simple.entity.MailSendRecordDO;

public interface MailService {

	/**
	 * 获取最新的邮件发送记录
	 */
	MailSendRecordDO getNewestMailSendRecord(String from);
	
	/**
	 * 获取邮件发送列表
	 */
    List<MailSendRecordDO> listMailSendRecordsByFrom(String from);

    /**
     * 获取所有邮件发送记录
     */
    List<MailSendRecordDO> listAllMailSendRecords();
    
    /**
     * 保存邮件发送记录
     */
    void saveMailSendRecord(MailSendRecordDO mailSendRecordDO);
    
    /**
     * 删除邮件发送记录
     */
    void deleteMailSendRecord(String idMailSendRecord);
    
    /**
     * 批量删除邮件发送记录
     */
    void batchDeleteMailSendRecords(List<String> idMailSendRecords);
    
}
