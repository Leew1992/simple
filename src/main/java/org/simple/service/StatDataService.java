package org.simple.service;

import java.util.List;

import org.simple.dto.ResultDTO;
import org.simple.entity.StatDayDataDO;
import org.simple.entity.StatMonthDataDO;
import org.simple.entity.StatWeekDataDO;
import org.simple.entity.StatYearDataDO;

public interface StatDataService {
	
	/**
	 * 列出日数据统计
	 */
	List<StatDayDataDO> listStatDayDatas(String accessDate);
	
	/**
	 * 列出周数据统计
	 */
	List<StatWeekDataDO> listStatWeekDatas(String accessDate);
	
	/**
	 * 列出月数据统计
	 */
	List<StatMonthDataDO> listStatMonthDatas(String accessDate);
	
	/**
	 * 列出年数据统计
	 */
	List<StatYearDataDO> listStatYearDatas(String accessDate);
	
	/**
	 * 保存日数据统计
	 */
	ResultDTO saveStatDayData(StatDayDataDO statDayDataDO);
	
	/**
	 * 保存周数据统计
	 */
	ResultDTO saveStatWeekData(StatWeekDataDO statWeekDataDO);
	
	/**
	 * 保存月数据统计
	 */
	ResultDTO saveStatMonthData(StatMonthDataDO statMonthDataDO);
	
	/**
	 * 保存年数据统计
	 */
	ResultDTO saveStatYearData(StatYearDataDO statYearDataDO);
	
	/**
	 * 更新日数据统计
	 */
	ResultDTO updateDayDataForCurrentDay(StatDayDataDO statDayDataDO);
	
	/**
	 * 更新周数据统计
	 */
	ResultDTO updateWeekDataForCurrentWeek(StatWeekDataDO statWeekDataDO);
	
	/**
	 * 更新月数据统计
	 */
	ResultDTO updateMonthDataForCurrentMonth(StatMonthDataDO statMonthDataDO);
	
	/**
	 * 更新年数据统计
	 */
	ResultDTO updateYearDataForCurrentYear(StatYearDataDO statYearDataDO);
}
