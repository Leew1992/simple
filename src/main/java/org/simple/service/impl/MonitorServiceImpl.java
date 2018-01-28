package org.simple.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.simple.context.UserContext;
import org.simple.dao.MonitorDao;
import org.simple.dto.MonitorAccessDTO;
import org.simple.dto.QueryDTO;
import org.simple.entity.MonitorAccessDO;
import org.simple.service.MonitorService;
import org.simple.util.BeanUtil;
import org.springframework.stereotype.Service;

@Service
public class MonitorServiceImpl implements MonitorService {
	
	@Resource
	private MonitorDao monitorDao;
	
	@Override
	public List<MonitorAccessDTO> listUnstatedMonitorAccesses() {
		return monitorDao.listUnstatedMonitorAccesses();
	}

	@Override
	public List<MonitorAccessDTO> pagingMonitorAccesses(MonitorAccessDTO monitorAccessDTO, QueryDTO queryDTO) {
		Map<String, Object> paramMap = BeanUtil.convertBeansToMap(monitorAccessDTO, queryDTO);
		return monitorDao.pagingMonitorAccesses(paramMap);
	}
	
	@Override
	public void saveMonitorAccess(MonitorAccessDO monitorAccessDO) {
		monitorAccessDO.setCreatedBy(UserContext.getCurrentUserName());
		monitorAccessDO.setUpdatedBy(UserContext.getCurrentUserName());
		monitorDao.saveMonitorAccess(monitorAccessDO);
	}
	
	@Override
	public void batchCloseMonitorAccesses(List<String> idMonitorAccesses) {
		monitorDao.batchCloseMonitorAccesses(idMonitorAccesses);
	}

	@Override
	public void deleteMonitorAccess(String idMonitorAccess) {
		monitorDao.deleteMonitorAccess(idMonitorAccess);
	}
	
	@Override
	public void batchDeleteMonitorAccesses(String idMonitorAccesses) {
		String[] idMonitorAccessArr = idMonitorAccesses.split(",");
		monitorDao.batchDeleteMonitorAccesses(Arrays.asList(idMonitorAccessArr));
	}

}
