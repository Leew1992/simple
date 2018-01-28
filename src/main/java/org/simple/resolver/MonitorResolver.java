package org.simple.resolver;

import java.util.ArrayList;
import java.util.List;

import org.simple.dto.MonitorAccessDTO;
import org.simple.entity.MonitorAccessDO;

public class MonitorResolver {
	
	private MonitorResolver() {}
	
	/**
	 * 从访问监控中提取Id
	 */
	public static List<String> extractIdsFromMonitorAccessDTOs(List<MonitorAccessDTO> monitorAccessList) {
		List<String> idMonitorAccessList = new ArrayList<>();
		for(MonitorAccessDO monitorAccess : monitorAccessList) {
			idMonitorAccessList.add(monitorAccess.getIdMonitorAccess());
		}
		return idMonitorAccessList;
	}
	
}
