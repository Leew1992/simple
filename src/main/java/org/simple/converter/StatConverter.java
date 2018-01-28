package org.simple.converter;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.utils.DateUtils;
import org.simple.context.UserContext;
import org.simple.dto.MonitorAccessDTO;
import org.simple.dto.StatDayAccessDTO;

public class StatConverter {
	
	private StatConverter() {}
	
	/**
	 * 将MonitorAccessDTO转换成StatDayAccessDTO
	 */
	public static List<StatDayAccessDTO> convertStatDayAccess(List<MonitorAccessDTO> monitorAccessList) {
		List<StatDayAccessDTO> statDayAccessList = new ArrayList<>();
		for(MonitorAccessDTO monitorAccess : monitorAccessList) {
			String accessDate = DateUtils.formatDate(monitorAccess.getCreatedDate(), "yyyyMMdd");
			StatDayAccessDTO statDayAccess = new StatDayAccessDTO();
			statDayAccess.setIdUser(monitorAccess.getIdUser());
			statDayAccess.setAccessModule(monitorAccess.getAccessModule());
			statDayAccess.setAccessCount(1);
			statDayAccess.setLastIncrease(1);
			statDayAccess.setAccessDate(accessDate);
			statDayAccess.setCreatedBy(UserContext.getCurrentUserName());
			statDayAccess.setUpdatedBy(UserContext.getCurrentUserName());
			statDayAccessList.add(statDayAccess);
		}
		return statDayAccessList;
	}
	
}
