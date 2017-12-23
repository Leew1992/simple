package org.simple.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.simple.dao.SmsDao;
import org.simple.entity.SmsSendRecordDO;
import org.simple.entity.SmsSendStatDO;
import org.simple.service.SmsService;
import org.springframework.stereotype.Service;

@Service
public class SmsServiceImpl implements SmsService {
	
	@Resource
	private SmsDao smsDao;
	
	@Override
	public SmsSendStatDO getSmsSendStatByMobilephone(String mobilephone) {
		return smsDao.getSmsSendStatByMobilephone(mobilephone);
	}

	@Override
	public void saveSmsSendRecord(SmsSendRecordDO smsSendRecordDO) {
		smsDao.saveSmsSendRecord(smsSendRecordDO);
	}

	@Override
	public void saveSmsSendStat(SmsSendStatDO smsSendStatDO) {
		smsDao.saveSmsSendStat(smsSendStatDO);
	}

	@Override
	public void increaseSendCount(String mobilephone) {
		smsDao.addOneForSmsSendStat(mobilephone);
	}

	@Override
	public void recordSmsSend(String mobilephone, String smsContent, String requestIp) {
		// 保存短信发送记录
		SmsSendRecordDO smsSendRecordDO = new SmsSendRecordDO();
		smsSendRecordDO.setMobilephone(mobilephone);
		smsSendRecordDO.setSmsContent(smsContent);
		smsSendRecordDO.setRequestIp(requestIp);
		smsSendRecordDO.setCreatedBy("system");
		smsSendRecordDO.setCreatedDate(new Date());
		smsSendRecordDO.setUpdatedBy("system");
		smsSendRecordDO.setUpdatedDate(new Date());
		smsDao.saveSmsSendRecord(smsSendRecordDO);
		
		// 保存短信发送统计
		SmsSendStatDO smsSendStatDO = smsDao.getSmsSendStatByMobilephone(mobilephone);
		if(smsSendStatDO != null) {
			smsDao.addOneForSmsSendStat(mobilephone);
		} else {
			smsSendStatDO = new SmsSendStatDO();
			smsSendStatDO.setMobilephone(mobilephone);
			smsSendStatDO.setSendCount(1);
			smsSendStatDO.setCreatedBy("system");
			smsSendStatDO.setCreatedDate(new Date());
			smsSendStatDO.setUpdatedBy("system");
			smsSendStatDO.setUpdatedDate(new Date());
			smsDao.saveSmsSendStat(smsSendStatDO);
		}
	}
}
