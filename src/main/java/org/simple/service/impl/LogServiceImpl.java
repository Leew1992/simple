package org.simple.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.simple.dao.LogDao;
import org.simple.dto.LogOperateDTO;
import org.simple.entity.LogOperateDO;
import org.simple.service.LogService;
import org.springframework.stereotype.Service;

@Service
public class LogServiceImpl implements LogService {
	
	@Resource
	private LogDao logDao;

	@Override
	public List<LogOperateDTO> pagingLogOperates(LogOperateDTO logOperateDTO) {
		return logDao.pagingLogOperates(logOperateDTO);
	}

	@Override
	public void saveLogOperate(LogOperateDO logOperateDO) {
		logDao.saveLogOperate(logOperateDO);

	}

	@Override
	public void deleteLogOperate(String idLogOperate) {
		logDao.deleteLogOperate(idLogOperate);

	}

	@Override
	public void batchDeleteLogOperates(String idLogOperates) {
		logDao.batchDeleteLogOperates(idLogOperates);
	}

}
