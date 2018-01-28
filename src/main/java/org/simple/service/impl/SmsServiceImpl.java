package org.simple.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.simple.context.UserContext;
import org.simple.dao.SmsDao;
import org.simple.entity.SmsSendRecordDO;
import org.simple.service.SmsService;
import org.springframework.stereotype.Service;

@Service
public class SmsServiceImpl implements SmsService {
	
	@Resource
	private SmsDao smsDao;

	@Override
	public SmsSendRecordDO getSmsSendRecordByMobilephone(String mobilephone) {
		return smsDao.getSmsSendRecordByMobilephone(mobilephone);
	}

	@Override
	public List<SmsSendRecordDO> listAllSmsSendRecords() {
		return smsDao.listAllSmsSendRecords();
	}

	@Override
	public void saveSmsSendRecord(SmsSendRecordDO smsSendRecordDO) {
		smsSendRecordDO.setCreatedBy(UserContext.getCurrentUserName());
		smsSendRecordDO.setUpdatedBy(UserContext.getCurrentUserName());
		smsDao.saveSmsSendRecord(smsSendRecordDO);
	}

	@Override
	public void deleteSmsSendRecord(String idSmsSendRecord) {
		smsDao.deleteSmsSendRecord(idSmsSendRecord);
	}

	@Override
	public void batchDeleteSmsSendRecords(List<String> idSmsSendRecords) {
		smsDao.batchDeleteSmsSendRecords(idSmsSendRecords);
	}
}
