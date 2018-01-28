package org.simple.service;

import java.util.List;

import org.simple.dto.MonitorAccessDTO;
import org.simple.dto.QueryDTO;
import org.simple.entity.MonitorAccessDO;

public interface MonitorService {
	
	/**
	 * 查询监控访问分页列表
	 */
	List<MonitorAccessDTO> listUnstatedMonitorAccesses();

	/**
	 * 查询监控访问分页列表
	 */
	List<MonitorAccessDTO> pagingMonitorAccesses(MonitorAccessDTO monitorAccessDTO, QueryDTO queryDTO);
	
	/**
	 * 保存监控登录访问信息
	 */
	void saveMonitorAccess(MonitorAccessDO monitorAccessDO);
	
	/**
	 * 批量关闭监控访问信息
	 */
	void batchCloseMonitorAccesses(List<String> idMonitorAccesses);

	/**
	 * 删除监控访问信息
	 */
	void deleteMonitorAccess(String idMonitorAccess);

	/**
	 * 批量删除监控访问信息
	 */
	void batchDeleteMonitorAccesses(String idMonitorAccesses);
}
