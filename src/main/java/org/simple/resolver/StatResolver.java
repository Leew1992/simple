package org.simple.resolver;

import java.util.ArrayList;
import java.util.List;

import org.simple.dto.StatDayAccessDTO;
import org.simple.entity.StatDayAccessDO;

public class StatResolver {

	private StatResolver() {}
	
	/**
	 * 从按日访问统计信息中提取Id
	 */
	public static List<String> extractIdsFromStatDayAccessDTOs(List<StatDayAccessDTO> statDayAccessList) {
		List<String> idStatDayAccessList = new ArrayList<>();
		for(StatDayAccessDO StatDayAccess : statDayAccessList) {
			idStatDayAccessList.add(StatDayAccess.getIdStatDayAccess());
		}
		return idStatDayAccessList;
	}
}
