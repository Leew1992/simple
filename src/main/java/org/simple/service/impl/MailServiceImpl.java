package org.simple.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.simple.dao.MailDao;
import org.simple.entity.MailSendRecordDO;
import org.simple.service.MailService;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService {
	
	@Resource
	private MailDao mailDao;
	
	@Override
	public MailSendRecordDO getNewestMailSendRecord(String from) {
		return mailDao.getNewestMailSendRecord(from);
	}

	@Override
	public List<MailSendRecordDO> listMailSendRecordsByFrom(String from) {
		return mailDao.listMailSendRecordsByFrom(from);
	}

	@Override
	public List<MailSendRecordDO> listAllMailSendRecords() {
		return mailDao.listAllMailSendRecords();
	}

	@Override
	public void saveMailSendRecord(MailSendRecordDO mailSendRecordDO) {
		mailSendRecordDO.setCreatedBy("system");
		mailSendRecordDO.setUpdatedBy("system");
		mailDao.saveMailSendRecord(mailSendRecordDO);
	}

	@Override
	public void deleteMailSendRecord(String idMailSendRecord) {
		mailDao.deleteMailSendRecord(idMailSendRecord);
	}

	@Override
	public void batchDeleteMailSendRecords(List<String> idMailSendRecords) {
		mailDao.batchDeleteMailSendRecords(idMailSendRecords);
	}

}
