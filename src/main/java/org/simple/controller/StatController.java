package org.simple.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.utils.DateUtils;
import org.simple.dto.ChartDTO;
import org.simple.entity.BaseStatDataDO;
import org.simple.entity.StatDayDataDO;
import org.simple.entity.StatMonthDataDO;
import org.simple.entity.StatWeekDataDO;
import org.simple.entity.StatYearDataDO;
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
	public BaseStatDataDO getStatData(String dateDimension) {
		BaseStatDataDO statData = new BaseStatDataDO();
		if("day".equals(dateDimension)) {
			String today = DateUtils.formatDate(new Date(), "yyyyMMdd");
			List<StatDayDataDO> dayDataList = statDataService.listStatDayDatas(today);
			if(dayDataList != null && dayDataList.size() != 0) {
				statData = dayDataList.get(0);				
			}
		}
		if("week".equals(dateDimension)) {
			Calendar cal = Calendar.getInstance();
			String thisWeek = DateUtils.formatDate(new Date(), "yyyy." + cal.get(Calendar.WEEK_OF_YEAR));
			List<StatWeekDataDO> weekDataList = statDataService.listStatWeekDatas(thisWeek);
			if(weekDataList != null && weekDataList.size() != 0) {
				statData = weekDataList.get(0);				
			}
		}
		if("month".equals(dateDimension)) {
			String thisMonth = DateUtils.formatDate(new Date(), "yyyyMM");
			List<StatMonthDataDO> monthDataList = statDataService.listStatMonthDatas(thisMonth);
			if(monthDataList != null && monthDataList.size() != 0) {
				statData = monthDataList.get(0);				
			}
		}
		if("year".equals(dateDimension)) {
			String thisYear = DateUtils.formatDate(new Date(), "yyyy");
			List<StatYearDataDO> yearDataList = statDataService.listStatYearDatas(thisYear);
			if(yearDataList != null && yearDataList.size() != 0) {
				statData = yearDataList.get(0);
			}
		}
		return statData;
	}
	
	/**
	 * 获取访问数据
	 */
	@RequestMapping("/getStatAccess.do")
	@ResponseBody
	public Map<String, Object> getStatAccess(String dateDimension) {
		Map<String, Object> statAccessMap = new HashMap<String, Object>();
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
