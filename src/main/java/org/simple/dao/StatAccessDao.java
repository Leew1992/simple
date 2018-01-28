package org.simple.dao;

import java.util.List;
import java.util.Map;

import org.simple.dto.StatDayAccessDTO;
import org.simple.dto.StatMonthAccessDTO;
import org.simple.dto.StatWeekAccessDTO;
import org.simple.dto.StatYearAccessDTO;
import org.simple.entity.StatDayAccessDO;
import org.simple.entity.StatMonthAccessDO;
import org.simple.entity.StatWeekAccessDO;
import org.simple.entity.StatYearAccessDO;
import org.springframework.stereotype.Repository;

@Repository
public interface StatAccessDao {
	
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
	 * 分页获取日访问数据
	 */
	List<StatDayAccessDTO> pagingStatDayAccesses(StatDayAccessDTO statDayAccessDTO);
	
	/**
	 * 分页获取周访问数据
	 */
	List<StatWeekAccessDTO> pagingStatWeekAccesses(StatWeekAccessDTO statWeekAccessDTO);
	
	/**
	 * 分页获取月访问数据
	 */
	List<StatMonthAccessDTO> pagingStatMonthAccesses(StatMonthAccessDTO statMonthAccessDTO);
	
	/**
	 * 分页获取年访问数据
	 */
	List<StatYearAccessDTO> pagingStatYearAccesses(StatYearAccessDTO statYearAccessDTO);
	
	/**
	 * 统计年访问数据
	 */
	Integer countStatYearAccess(Map<String, Object> paramMap);
	
	/**
	 * 列出日访问统计数据 
	 */
	List<Map<String, Object>> listStatDayAccessCounts(String accessDate);
	
	/**
	 * 列出周访问统计数据
	 */
	List<Map<String, Object>> listStatWeekAccessCounts(String accessDate);
	
	/**
	 * 列出月访问统计数据
	 */
	List<Map<String, Object>> listStatMonthAccessCounts(String accessDate);
	
	/**
	 * 列出年访问统计数据
	 */
	List<Map<String, Object>> listStatYearAccessCounts(String accessDate);
	
	/**
	 * 保存日访问数据
	 */
	void saveStatDayAccess(StatDayAccessDO statDayAccessDO);
	
	/**
	 * 保存周访问数据
	 */
	void saveStatWeekAccess(StatWeekAccessDO statWeekAccessDO);
	
	/**
	 * 保存月访问数据
	 */
	void saveStatMonthAccess(StatMonthAccessDO statMonthAccessDO);
	
	/**
	 * 保存年访问数据
	 */
	void saveStatYearAccess(StatYearAccessDO statYearAccessDO);
	
	/**
	 * 增加日访问数量
	 */
	void increaseStatDayAccess(StatDayAccessDO statDayAccess);
	
	/**
	 * 增加周访问数量
	 */
	void increaseStatWeekAccess(StatDayAccessDO statDayAccess);
	
	/**
	 * 增加月访问数量
	 */
	void increaseStatMonthAccess(StatDayAccessDO statDayAccess);
	
	/**
	 * 增加年访问数量
	 */
	void increaseStatYearAccess(StatDayAccessDO statDayAccess);
	
	/**
	 * 清除日访问增量数据
	 */
	void clearStatDayAccessIncreament(List<String> idStatDayAccesses);
	
	/**
	 * 删除日访问数据
	 */
	void deleteStatDayAccess(String idStatDayAccess);
	
	/**
	 * 删除周访问数据
	 */
	void deleteStatWeekAccess(String idStatWeekAccess);
	
	/**
	 * 删除月访问数据
	 */
	void deleteStatMonthAccess(String idStatMonthAccess);
	
	/**
	 * 删除年访问数据
	 */
	void deleteStatYearAccess(String idStatYearAccess);
	
	/**
	 * 批量删除日访问数据
	 */
	void batchDeleteStatDayAccesses(List<String> idStatDayAccesses);
	
	/**
	 * 批量删除周访问数据
	 */
	void batchDeleteStatWeekAccesses(List<String> idStatWeekAccesses);
	
	/**
	 * 批量删除月访问数据
	 */
	void batchDeleteStatMonthAccesses(List<String> idStatMonthAccesses);
	
	/**
	 * 批量删除年访问数据
	 */
	void batchDeleteStatYearAccesses(List<String> idStatYearAccesses);
	
	/**
	 * 分组统计日访问数量
	 */
	List<Integer> countStatDayAccessesDividedByAccessDate(Map<String, Object> paramMap);
	
	/**
	 * 分组统计日访问数量
	 */
	List<Integer> countStatDayAccessesDividedByAccessModule(Map<String, Object> paramMap);
	
	/**
	 * 分组统计周访问数量
	 */
	List<Integer> countStatWeekAccessesDividedByAccessModule(Map<String, Object> paramMap);
	
	/**
	 * 分组统计月访问数量
	 */
	List<Integer> countStatMonthAccessesDividedByAccessModule(Map<String, Object> paramMap);
	
	/**
	 * 分组统计年访问数量
	 */
	List<Integer> countStatYearAccessesDividedByAccessModule(Map<String, Object> paramMap);
	
}
