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
	public void saveSmsSendRecord(SmsSendRecordDO SmsSendRecordDO) {
		SmsSendRecordDO.setCreatedBy(UserContext.getCurrentUserName());
		SmsSendRecordDO.setUpdatedBy(UserContext.getCurrentUserName());
		smsDao.saveSmsSendRecord(SmsSendRecordDO);
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
