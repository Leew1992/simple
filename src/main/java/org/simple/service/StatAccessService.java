package org.simple.service;

import java.util.List;
import java.util.Map;

import org.simple.dto.ChartDTO;
import org.simple.dto.ResultDTO;
import org.simple.entity.StatDayAccessDO;
import org.simple.entity.StatMonthAccessDO;
import org.simple.entity.StatWeekAccessDO;
import org.simple.entity.StatYearAccessDO;

public interface StatAccessService {

	/**
	 * 列出日访问数据
	 */
	List<StatDayAccessDO> listStatDayAccesses(Map<String, Object> paramMap);

	/**
	 * 列出周访问数据
	 */
	List<StatWeekAccessDO> listStatWeekAccesses(Map<String, Object> paramMap);

	/**
	 * 列出月访问数据
	 */
	List<StatMonthAccessDO> listStatMonthAccesses(Map<String, Object> paramMap);

	/**
	 * 列出年访问数据
	 */
	List<StatYearAccessDO> listStatYearAccesses(Map<String, Object> paramMap);

	/**
	 * 统计年访问数据
	 */
	Integer countStatYearAccess(Map<String, Object> paramMap);

	/**
	 * 列出日访问统计数据 
	 */
	Map<String, Object> getStatDayAccessCount(String accessDate);

	/**
	 * 列出周访问统计数据
	 */
	Map<String, Object> getStatWeekAccessCount(String accessDate);

	/**
	 * 列出月访问统计数据
	 */
	Map<String, Object> getStatMonthAccessCount(String accessDate);

	/**
	 * 列出年访问统计数据
	 */
	Map<String, Object> getStatYearAccessCount(String accessDate);
	
	/**
	 * 保存日访问数据
	 */
	ResultDTO saveStatDayAccess(StatDayAccessDO statDayAccessDO);

	/**
	 * 保存周访问数据
	 */
	ResultDTO saveStatWeekAccess(StatWeekAccessDO statWeekAccessDO);

	/**
	 * 保存月访问数据
	 */
	ResultDTO saveStatMonthAccess(StatMonthAccessDO statMonthAccessDO);

	/**
	 * 保存年访问数据
	 */
	ResultDTO saveStatYearAccess(StatYearAccessDO statYearAccessDO);

	/**
	 * 增加日访问数量
	 */
	ResultDTO increaseStatDayAccess(StatDayAccessDO statDayAccess);

	/**
	 * 增加周访问数量
	 */
	ResultDTO increaseStatWeekAccess(StatDayAccessDO statDayAccess);

	/**
	 * 增加月访问数量
	 */
	ResultDTO increaseStatMonthAccess(StatDayAccessDO statDayAccess);

	/**
	 * 增加年访问数量
	 */
	ResultDTO increaseStatYearAccess(StatDayAccessDO statDayAccess);

	/**
	 * 清除日访问增量数据
	 */
	ResultDTO clearStatDayAccessIncreament(List<String> idStatDayAccesses);

	/**
	 * 删除日访问数据
	 */
	ResultDTO deleteStatDayAccess(String idStatDayAccess);

	/**
	 * 删除周访问数据
	 */
	ResultDTO deleteStatWeekAccess(String idStatWeekAccess);

	/**
	 * 删除月访问数据
	 */
	ResultDTO deleteStatMonthAccess(String idStatMonthAccess);

	/**
	 * 删除年访问数据
	 */
	ResultDTO deleteStatYearAccess(String idStatYearAccess);

	/**
	 * 批量删除日访问数据
	 */
	ResultDTO batchDeleteStatDayAccesses(List<String> idStatDayAccesses);

	/**
	 * 批量删除周访问数据
	 */
	ResultDTO batchDeleteStatWeekAccesses(List<String> idStatWeekAccesses);

	/**
	 * 批量删除月访问数据
	 */
	ResultDTO batchDeleteStatMonthAccesses(List<String> idStatMonthAccesses);

	/**
	 * 批量删除年访问数据
	 */
	ResultDTO batchDeleteStatYearAccesses(List<String> idStatYearAccesses);

	/**
	 * 获取日登录访问数据
	 */
	ChartDTO getStatDayAccessForLogin(String dateDimension);

	/**
	 * 获取访问数据图表
	 */
	ChartDTO getStatAccessChart(String dateDimension, String moduleType);
}
