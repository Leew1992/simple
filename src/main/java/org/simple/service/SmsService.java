package org.simple.service;

import java.util.List;

import org.simple.entity.SmsSendRecordDO;

public interface SmsService {
	
	/**
	 * 获取短信发送记录
	 */
    SmsSendRecordDO getSmsSendRecordByMobilephone(String mobilephone);

    /**
     * 获取所有短信发送记录
     */
    List<SmsSendRecordDO> listAllSmsSendRecords();
    
    /**
     * 保存短信发送记录
     */
    void saveSmsSendRecord(SmsSendRecordDO smsSendRecordDO);
    
    /**
     * 删除短信发送记录
     */
    void deleteSmsSendRecord(String idSmsSendRecord);
    
    /**
     * 批量删除短信发送记录
     */
    void batchDeleteSmsSendRecords(List<String> idSmsSendRecords);
    
}
