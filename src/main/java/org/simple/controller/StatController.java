package org.simple.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.utils.DateUtils;
import org.simple.dto.ChartDTO;
import org.simple.entity.BaseStatDataDO;
import org.simple.service.StatAccessService;
import org.simple.service.StatDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/stat")
public class StatController {
	
	@Autowired
	private StatDataService statDataService;
	
	@Autowired
	private StatAccessService statAccessService;
	
	/**
	 * 获取统计数据
	 */
	@RequestMapping("/getStatData.do")
	@ResponseBody
	@SuppressWarnings("rawtypes")
	public BaseStatDataDO getStatData(String dateDimension) {
		BaseStatDataDO statData = new BaseStatDataDO();
		List dataList = new ArrayList<>();
		if("day".equals(dateDimension)) {
			String today = DateUtils.formatDate(new Date(), "yyyyMMdd");
			dataList = statDataService.listStatDayDatas(today);
		}
		if("week".equals(dateDimension)) {
			Calendar cal = Calendar.getInstance();
			String thisWeek = DateUtils.formatDate(new Date(), "yyyy." + cal.get(Calendar.WEEK_OF_YEAR));
			dataList = statDataService.listStatWeekDatas(thisWeek);
		}
		if("month".equals(dateDimension)) {
			String thisMonth = DateUtils.formatDate(new Date(), "yyyyMM");
			dataList = statDataService.listStatMonthDatas(thisMonth);
		}
		if("year".equals(dateDimension)) {
			String thisYear = DateUtils.formatDate(new Date(), "yyyy");
			dataList = statDataService.listStatYearDatas(thisYear);
		}
		if(dataList != null && !dataList.isEmpty()) {
			statData = (BaseStatDataDO) dataList.get(0);
		}
		return statData;
	}
	
	/**
	 * 获取访问数据
	 */
	@RequestMapping("/getStatAccess.do")
	@ResponseBody
	public Map<String, Object> getStatAccess(String dateDimension) {
		Map<String, Object> statAccessMap = new HashMap<>();
		if("day".equals(dateDimension)) {
			String today = DateUtils.formatDate(new Date(), "yyyyMMdd");
			statAccessMap = statAccessService.getStatDayAccessCount(today);
		}
		if("week".equals(dateDimension)) {
			Calendar cal = Calendar.getInstance();
			String thisWeek = DateUtils.formatDate(new Date(), "yyyy." + cal.get(Calendar.WEEK_OF_YEAR));
			statAccessMap = statAccessService.getStatWeekAccessCount(thisWeek);
		}
		if("month".equals(dateDimension)) {
			String thisMonth = DateUtils.formatDate(new Date(), "yyyyMM");
			statAccessMap = statAccessService.getStatMonthAccessCount(thisMonth);
		}
		if("year".equals(dateDimension)) {
			String thisYear = DateUtils.formatDate(new Date(), "yyyy");
			statAccessMap = statAccessService.getStatYearAccessCount(thisYear);
		}
		return statAccessMap;
	}
	
	/**
	 * 获取最近的栏目访问量
	 */
	@RequestMapping("/getStatDayAccessForLogin.do")
	@ResponseBody
	public ChartDTO getStatDayAccessForLogin(String dateDimension) {
		return statAccessService.getStatDayAccessForLogin(dateDimension);
	}
	
	/**
	 * 获取访问数据图表
	 */
	@RequestMapping("/getStatAccessChart.do")
	@ResponseBody
	public ChartDTO getStatAccessChart(String dateDimension, String moduleType) {
		return statAccessService.getStatAccessChart(dateDimension, moduleType);
	}
	
}
