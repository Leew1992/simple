package org.simple.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.http.client.utils.DateUtils;
import org.simple.constant.MessageConst;
import org.simple.dao.StatAccessDao;
import org.simple.dto.ChartDTO;
import org.simple.dto.DataSet;
import org.simple.dto.ResultDTO;
import org.simple.entity.StatDayAccessDO;
import org.simple.entity.StatMonthAccessDO;
import org.simple.entity.StatWeekAccessDO;
import org.simple.entity.StatYearAccessDO;
import org.simple.handler.StatHandler;
import org.simple.service.StatAccessService;
import org.springframework.stereotype.Service;

@Service
public class StatAccessServiceImpl implements StatAccessService {

	private static final String LOGIN = "login";
	private static final String LOGOUT = "logout";
	private static final String DATES = "dates";
	private static final String ZH_ACCESS_COUNT = "访问量";
	private static final String EN_ACCESS_COUNT = "accessCount";
	private static final String ACCESS_MODULE = "accessModule";
	private static final String STAT_ACCESS_JOB = "StatAccessJob";
	private static final List<String> LOGIN_MODULES_NAME = Arrays.asList("登录","登出");
	private static final List<String> LOGIN_MODULES_CODE = Arrays.asList(LOGIN,LOGOUT);
	private static final List<String> COLUMN_MODULES_NAME = Arrays.asList("分组","系统","栏目","类别","用户","角色","贴子","评论");
	private static final List<String> COLUMN_MODULES_CODE = Arrays.asList("group","system","column","category","user","role","post","comment");
	
	@Resource
	private StatAccessDao statAccessDao;
	
	@Override
	public List<StatDayAccessDO> listStatDayAccesses(Map<String, Object> paramMap) {
		return statAccessDao.listStatDayAccesses(paramMap);
	}
	
	@Override
	public List<StatWeekAccessDO> listStatWeekAccesses(Map<String, Object> paramMap) {
		return statAccessDao.listStatWeekAccesses(paramMap);
	}
	
	@Override
	public List<StatMonthAccessDO> listStatMonthAccesses(Map<String, Object> paramMap) {
		return statAccessDao.listStatMonthAccesses(paramMap);
	}

	@Override
	public List<StatYearAccessDO> listStatYearAccesses(Map<String, Object> paramMap) {
		return statAccessDao.listStatYearAccesses(paramMap);
	}

	@Override
	public Integer countStatYearAccess(Map<String, Object> paramMap) {
		return statAccessDao.countStatYearAccess(paramMap);
	}
	
	@Override
	public Map<String, Object> getStatDayAccessCount(String accessDate) {
		List<Map<String, Object>> statDayAccessList = statAccessDao.listStatDayAccessCounts(accessDate);
		Map<String, Object> dayAccessMap = new HashMap<>();
		for(Map<String, Object> statDayAccess : statDayAccessList) {
			dayAccessMap.put((String)statDayAccess.get(ACCESS_MODULE), statDayAccess.get(EN_ACCESS_COUNT));
		}
		return dayAccessMap;
	}
	
	@Override
	public Map<String, Object> getStatWeekAccessCount(String accessDate) {
		List<Map<String, Object>> statWeekAccessList = statAccessDao.listStatWeekAccessCounts(accessDate);
		Map<String, Object> weekAccessMap = new HashMap<>();
		for(Map<String, Object> statWeekAccess : statWeekAccessList) {
			weekAccessMap.put((String)statWeekAccess.get(ACCESS_MODULE), statWeekAccess.get(EN_ACCESS_COUNT));
		}
		return weekAccessMap;
	}

	@Override
	public Map<String, Object> getStatMonthAccessCount(String accessDate) {
		List<Map<String, Object>> statMonthAccessList = statAccessDao.listStatMonthAccessCounts(accessDate);
		Map<String, Object> monthAccessMap = new HashMap<>();
		for(Map<String, Object> statMonthAccess : statMonthAccessList) {
			monthAccessMap.put((String)statMonthAccess.get(ACCESS_MODULE), statMonthAccess.get(EN_ACCESS_COUNT));
		}
		return monthAccessMap;
	}

	@Override
	public Map<String, Object> getStatYearAccessCount(String accessDate) {
		List<Map<String, Object>> statYearAccessList = statAccessDao.listStatYearAccessCounts(accessDate);
		Map<String, Object> yearAccessMap = new HashMap<>();
		for(Map<String, Object> statYearAccess : statYearAccessList) {
			yearAccessMap.put((String)statYearAccess.get(ACCESS_MODULE), statYearAccess.get(EN_ACCESS_COUNT));
		}
		return yearAccessMap;
	}

	@Override
	public ResultDTO saveStatDayAccess(StatDayAccessDO statDayAccessDO) {
		statDayAccessDO.setCreatedBy(STAT_ACCESS_JOB);
		statDayAccessDO.setUpdatedBy(STAT_ACCESS_JOB);
		statAccessDao.saveStatDayAccess(statDayAccessDO);
		return new ResultDTO(true, MessageConst.SAVE_SUCCESS);
	}

	@Override
	public ResultDTO saveStatWeekAccess(StatWeekAccessDO statWeekAccessDO) {
		statWeekAccessDO.setCreatedBy(STAT_ACCESS_JOB);
		statWeekAccessDO.setUpdatedBy(STAT_ACCESS_JOB);
		statAccessDao.saveStatWeekAccess(statWeekAccessDO);
		return new ResultDTO(true, MessageConst.SAVE_SUCCESS);
	}

	@Override
	public ResultDTO saveStatMonthAccess(StatMonthAccessDO statMonthAccessDO) {
		statMonthAccessDO.setCreatedBy(STAT_ACCESS_JOB);
		statMonthAccessDO.setUpdatedBy(STAT_ACCESS_JOB);
		statAccessDao.saveStatMonthAccess(statMonthAccessDO);
		return new ResultDTO(true, MessageConst.SAVE_SUCCESS);
	}

	@Override
	public ResultDTO saveStatYearAccess(StatYearAccessDO statYearAccessDO) {
		statYearAccessDO.setCreatedBy(STAT_ACCESS_JOB);
		statYearAccessDO.setUpdatedBy(STAT_ACCESS_JOB);
		statAccessDao.saveStatYearAccess(statYearAccessDO);
		return new ResultDTO(true, MessageConst.SAVE_SUCCESS);
	}
	
	@Override
	public ResultDTO increaseStatDayAccess(StatDayAccessDO statDayAccess) {
		statAccessDao.increaseStatDayAccess(statDayAccess);
		return new ResultDTO(true, MessageConst.LOAD_SUCCESS);
	}
	
	@Override
	public ResultDTO increaseStatWeekAccess(StatDayAccessDO statDayAccess) {
		statAccessDao.increaseStatWeekAccess(statDayAccess);
		return new ResultDTO(true, MessageConst.LOAD_SUCCESS);
	}

	@Override
	public ResultDTO increaseStatMonthAccess(StatDayAccessDO statDayAccess) {
		statAccessDao.increaseStatMonthAccess(statDayAccess);
		return new ResultDTO(true, MessageConst.LOAD_SUCCESS);
	}

	@Override
	public ResultDTO increaseStatYearAccess(StatDayAccessDO statDayAccess) {
		statAccessDao.increaseStatYearAccess(statDayAccess);
		return new ResultDTO(true, MessageConst.LOAD_SUCCESS);
	}
	
	@Override
	public ResultDTO clearStatDayAccessIncreament(List<String> idStatDayAccesses) {
		statAccessDao.clearStatDayAccessIncreament(idStatDayAccesses);
		return new ResultDTO(true, MessageConst.DELETE_SUCCESS);
		
	}

	@Override
	public ResultDTO deleteStatDayAccess(String idStatDayAccess) {
		statAccessDao.deleteStatDayAccess(idStatDayAccess);
		return new ResultDTO(true, MessageConst.DELETE_SUCCESS);
	}

	@Override
	public ResultDTO deleteStatWeekAccess(String idStatWeekAccess) {
		statAccessDao.deleteStatWeekAccess(idStatWeekAccess);
		return new ResultDTO(true, MessageConst.DELETE_SUCCESS);
	}

	@Override
	public ResultDTO deleteStatMonthAccess(String idStatMonthAccess) {
		statAccessDao.deleteStatMonthAccess(idStatMonthAccess);
		return new ResultDTO(true, MessageConst.DELETE_SUCCESS);
	}

	@Override
	public ResultDTO deleteStatYearAccess(String idStatYearAccess) {
		statAccessDao.deleteStatYearAccess(idStatYearAccess);
		return new ResultDTO(true, MessageConst.DELETE_SUCCESS);
	}

	@Override
	public ResultDTO batchDeleteStatDayAccesses(List<String> idStatDayAccesses) {
		statAccessDao.batchDeleteStatDayAccesses(idStatDayAccesses);
		return new ResultDTO(true, MessageConst.DELETE_SUCCESS);
	}

	@Override
	public ResultDTO batchDeleteStatWeekAccesses(List<String> idStatWeekAccesses) {
		statAccessDao.batchDeleteStatWeekAccesses(idStatWeekAccesses);
		return new ResultDTO(true, MessageConst.DELETE_SUCCESS);
	}

	@Override
	public ResultDTO batchDeleteStatMonthAccesses(List<String> idStatMonthAccesses) {
		statAccessDao.batchDeleteStatMonthAccesses(idStatMonthAccesses);
		return new ResultDTO(true, MessageConst.DELETE_SUCCESS);
	}

	@Override
	public ResultDTO batchDeleteStatYearAccesses(List<String> idStatYearAccesses) {
		statAccessDao.batchDeleteStatYearAccesses(idStatYearAccesses);
		return new ResultDTO(true, MessageConst.DELETE_SUCCESS);
	}
	
	@Override
	public ChartDTO getStatDayAccessForLogin(String dateDimension) {
		List<String> dates = StatHandler.getRecentDays(7);
		List<String> modules = Arrays.asList(LOGIN);
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put(DATES, dates);
		paramMap.put("modules", modules);
		List<Integer> counts = statAccessDao.countStatDayAccessesDividedByAccessDate(paramMap);
		List<DataSet> dataSetList = new ArrayList<>();
		DataSet dataSet = new DataSet();
		dataSet.setData(counts);
		dataSet.setLabel(ZH_ACCESS_COUNT);
		dataSetList.add(dataSet);
		
		ChartDTO chartDTO = new ChartDTO();
		chartDTO.setDatasets(dataSetList);
		chartDTO.setLabels(dates);
		return chartDTO;
	}

	@Override
	public ChartDTO getStatAccessChart(String dateDimension, String moduleType) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("modules", LOGIN.equals(moduleType) ? LOGIN_MODULES_CODE : COLUMN_MODULES_CODE);
		List<String> dates = new ArrayList<>();
		List<Integer> counts = new ArrayList<>();
		if("day".equals(dateDimension)) {
			String currentDay = DateUtils.formatDate(new Date(), "yyyyMMdd");
			dates.add(currentDay);
			paramMap.put(DATES, dates);
			counts = statAccessDao.countStatDayAccessesDividedByAccessModule(paramMap);
		}
		if("week".equals(dateDimension)) {
			Calendar cal = Calendar.getInstance();
			String currentWeek = DateUtils.formatDate(new Date(), "yyyy." + cal.get(Calendar.WEEK_OF_YEAR));
			dates.add(currentWeek);
			paramMap.put(DATES, dates);
			counts = statAccessDao.countStatWeekAccessesDividedByAccessModule(paramMap);
		}
		if("month".equals(dateDimension)) {
			String currentMonth = DateUtils.formatDate(new Date(), "yyyyMM");
			dates.add(currentMonth);
			paramMap.put(DATES, dates);
			counts = statAccessDao.countStatMonthAccessesDividedByAccessModule(paramMap);
		}
		if("year".equals(dateDimension)) {
			String currentYear = DateUtils.formatDate(new Date(), "yyyy");
			dates.add(currentYear);
			paramMap.put(DATES, dates);
			counts = statAccessDao.countStatYearAccessesDividedByAccessModule(paramMap);
		}
		
		List<DataSet> dataSetList = new ArrayList<>();
		DataSet dataSet = new DataSet();
		dataSet.setData(counts);
		dataSet.setLabel(ZH_ACCESS_COUNT);
		dataSetList.add(dataSet);
		
		ChartDTO chartDTO = new ChartDTO();
		chartDTO.setDatasets(dataSetList);
		chartDTO.setLabels(LOGIN.equals(moduleType) ? LOGIN_MODULES_NAME : COLUMN_MODULES_NAME);
		return chartDTO;
	}
}
