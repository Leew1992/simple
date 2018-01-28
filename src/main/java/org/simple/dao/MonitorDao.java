package org.simple.dao;

import java.util.List;
import java.util.Map;

import org.simple.dto.MonitorAccessDTO;
import org.simple.entity.MonitorAccessDO;
import org.springframework.stereotype.Repository;

@Repository
public interface MonitorDao {
	
	/**
	 * 查询监控访问分页列表
	 */
	List<MonitorAccessDTO> listUnstatedMonitorAccesses();
	
	/**
	 * 查询监控访问分页列表
	 */
	List<MonitorAccessDTO> pagingMonitorAccesses(Map<String, Object> paramMap);
	
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
	void batchDeleteMonitorAccesses(List<String> idMonitorAccesses);
}
