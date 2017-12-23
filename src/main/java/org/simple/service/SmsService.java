package org.simple.service;

import org.simple.entity.SmsSendRecordDO;
import org.simple.entity.SmsSendStatDO;

public interface SmsService {
	
	/**
	 * 保存短信发送记录
	 */
	SmsSendStatDO getSmsSendStatByMobilephone(String mobilephone);
	
	/**
	 * 保存短信发送记录
	 */
	void saveSmsSendRecord(SmsSendRecordDO smsSendRecordDO);
	
	/**
	 * 保存短信发送统计
	 */
	void saveSmsSendStat(SmsSendStatDO smsSendStatDO);
	
	/**
	 * 新增发送次数
	 */
	void increaseSendCount(String mobilephone);

	/**
	 * 记录短信发送统计
	 */
	void recordSmsSend(String mobilephone, String smsContent, String requestIp);
}
