package org.simple.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.simple.dao.SmsDao;
import org.simple.entity.SmsSendRecordDO;
import org.simple.entity.SmsSendStatDO;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;

public class SmsDaoTest extends BaseTest {
	
	private final Logger logger = Logger.getLogger(getClass());
	
	@Autowired
    private SmsDao smsDao;

	@Test
	public void testGetSmsSendRecordByMobilephone() {
		String mobilephone = "aa";
		SmsSendRecordDO smsSendRecordDO = smsDao.getSmsSendRecordByMobilephone(mobilephone);
		logger.debug(JSON.toJSONString(smsSendRecordDO));
	}
	
	@Test
	public void testGetSmsSendStatByMobilephone() {
		String mobilephone = "aa";
		SmsSendStatDO smsSendStatDO = smsDao.getSmsSendStatByMobilephone(mobilephone);
		logger.debug(JSON.toJSONString(smsSendStatDO));
	}
    
	@Test
    public void testListSmsSendRecords() {
    	List<SmsSendRecordDO> smsSendRecordList = smsDao.listSmsSendRecords();
    	logger.debug(JSON.toJSONString(smsSendRecordList));
    }
	
	@Test
    public void testListSmsSendStats() {
    	List<SmsSendStatDO> smsSendStatList = smsDao.listSmsSendStats();
    	logger.debug(JSON.toJSONString(smsSendStatList));
    }
    
	@Test
    public void testSaveSmsSendRecord() {
		SmsSendRecordDO smsSendRecordDO = new SmsSendRecordDO();
		smsSendRecordDO.setMobilephone("aa");
		smsSendRecordDO.setSmsContent("aa");
		smsSendRecordDO.setCreatedBy("aa");
		smsSendRecordDO.setCreatedDate(new Date());
		smsSendRecordDO.setUpdatedBy("aa");
		smsSendRecordDO.setUpdatedDate(new Date());
		smsDao.saveSmsSendRecord(smsSendRecordDO);
		logger.debug("短信发送记录保存成功");
	}
    
	@Test
    public void testSaveSmsSendStat() {
		SmsSendStatDO smsSendStatDO = new SmsSendStatDO();
		smsSendStatDO.setMobilephone("aa");
		smsSendStatDO.setSendCount(1);
		smsSendStatDO.setCreatedBy("aa");
		smsSendStatDO.setCreatedDate(new Date());
		smsSendStatDO.setUpdatedBy("aa");
		smsSendStatDO.setUpdatedDate(new Date());
		smsDao.saveSmsSendStat(smsSendStatDO);
		logger.debug("短信发送统计保存成功");
	}
    
	@Test
    public void testDeleteSmsSendRecord() {
		String idSmsSendRecord = "0";
		smsDao.deleteSmsSendRecord(idSmsSendRecord);
		logger.debug("短信发送记录删除成功，ID[" + idSmsSendRecord + "]");
	}
    
	@Test
    public void testDeleteSmsSendStat() {
		String idSmsSendStat = "0";
		smsDao.deleteSmsSendStat(idSmsSendStat);
		logger.debug("短信发送统计删除成功，ID[" + idSmsSendStat + "]");
	}
    
	@Test
    public void testBatchDeleteSmsSendRecords() {
		List<String> idSmsSendRecords = new ArrayList<String>();
		idSmsSendRecords.add("0");
		idSmsSendRecords.add("1");
		smsDao.batchDeleteSmsSendRecords(idSmsSendRecords);
		logger.debug("短信发送记录删除成功，IDs[" + JSON.toJSONString(idSmsSendRecords) + "]");
		
	}
    
	@Test
    public void testBatchDeleteSmsSendStats() {
		List<String> idSmsSendStats = new ArrayList<String>();
		idSmsSendStats.add("0");
		idSmsSendStats.add("1");
		smsDao.batchDeleteSmsSendStats(idSmsSendStats);
		logger.debug("短信发送统计删除成功，IDs[" + JSON.toJSONString(idSmsSendStats) + "]");
	}
}