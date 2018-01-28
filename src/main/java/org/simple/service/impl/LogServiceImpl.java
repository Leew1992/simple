package org.simple.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.simple.dao.LogDao;
import org.simple.dto.LogOperateDTO;
import org.simple.dto.QueryDTO;
import org.simple.entity.LogOperateDO;
import org.simple.service.LogService;
import org.simple.util.BeanUtil;
import org.springframework.stereotype.Service;

@Service
public class LogServiceImpl implements LogService {
	
	@Resource
	private LogDao logDao;

	@Override
	public List<LogOperateDTO> pagingLogOperates(LogOperateDTO logOperateDTO, QueryDTO queryDTO) {
		Map<String, Object> paramMap = BeanUtil.convertBeansToMap(logOperateDTO, queryDTO);
		return logDao.pagingLogOperates(paramMap);
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
