package org.simple.dao;

import java.util.List;

import org.simple.entity.SmsSendRecordDO;
import org.simple.entity.SmsSendStatDO;
import org.springframework.stereotype.Repository;

@Repository
public interface SmsDao {
    
    SmsSendRecordDO getSmsSendRecordByMobilephone(String mobilephone);
    
    SmsSendStatDO getSmsSendStatByMobilephone(String mobilephone);
    
    List<SmsSendRecordDO> listSmsSendRecords();
    
    List<SmsSendStatDO> listSmsSendStats();
    
    void saveSmsSendRecord(SmsSendRecordDO smsSendRecordDO);
    
    void saveSmsSendStat(SmsSendStatDO smsSendStatDO);
    
    void addOneForSmsSendStat(String mobilephone);
    
    void deleteSmsSendRecord(String idSmsSendRecord);
    
    void deleteSmsSendStat(String idSmsSendStat);
    
    void batchDeleteSmsSendRecords(List<String> idSmsSendRecords);
    
    void batchDeleteSmsSendStats(List<String> idSmsSendStats);
    
}
