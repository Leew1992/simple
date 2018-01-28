package org.simple.handler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.utils.DateUtils;
import org.simple.dto.StatDayAccessDTO;
import org.simple.entity.StatDayAccessDO;

public class StatHandler {
	
	private static final String YYYYMMDD = "yyyyMMdd";
	
	private StatHandler(){}

	/**
	 * 合并日访问统计数据
	 */
	public static List<StatDayAccessDTO> mergeStatDayAccess(List<StatDayAccessDTO> statDayAccessList) {
		Map<String, StatDayAccessDTO> statDayAccessMap = new HashMap<> ();
		for(StatDayAccessDTO statDayAccess : statDayAccessList) {
			String identify = statDayAccess.getIdUser() + statDayAccess.getAccessModule() + statDayAccess.getAccessDate();
			if(statDayAccessMap.get(identify) == null) {
				statDayAccessMap.put(identify, statDayAccess);
			} else {
				StatDayAccessDTO storedStatAccess = statDayAccessMap.get(identify);
				Integer accessCount = storedStatAccess.getAccessCount() + 1;
				Integer lastIncrease = storedStatAccess.getLastIncrease() + 1;
				storedStatAccess.setAccessCount(accessCount);
				storedStatAccess.setLastIncrease(lastIncrease);
				statDayAccessMap.put(identify, storedStatAccess);
			}
		}
		StatDayAccessDTO[] statDayAccessArr = new StatDayAccessDTO[statDayAccessMap.values().size()];
		statDayAccessMap.values().toArray(statDayAccessArr);
		return Arrays.asList(statDayAccessArr);
	}
	
	/**
	 * 增量数据清零
	 */
	public static List<StatDayAccessDO> clearNoLastIncrease(List<StatDayAccessDO> statDayAccessList) {
		List<StatDayAccessDO> clearedStatDayAccessList = new ArrayList<>();
		for(StatDayAccessDO statDayAccess : statDayAccessList) {
			// 上次增加访问量不为空
			if(statDayAccess.getLastIncrease() != 0) {
				clearedStatDayAccessList.add(statDayAccess);
			}
		}
		return clearedStatDayAccessList;
	}
	
	/**
	 * 获取本周已过去的天
	 */
	public static List<String> getPastDayOfThisWeek() {
		List<String> weekDayList = new ArrayList<>();	 
		Calendar cal = Calendar.getInstance();
		int dayOfWeek = cal.get(Calendar.WEEK_OF_YEAR);
		String today = DateUtils.formatDate(new Date(), YYYYMMDD);
		weekDayList.add(today);
		for(int i = dayOfWeek-1; i >= 1; i --) {
			cal.add(Calendar.DATE, -1);
			weekDayList.add(DateUtils.formatDate(cal.getTime(), YYYYMMDD));
		}
		return weekDayList;
	}
	
	/**
	 * 获取最近多少天
	 */
	public static List<String> getRecentDays(int days) {
		Date today = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(today);
		SimpleDateFormat sdf = new SimpleDateFormat(YYYYMMDD);
		List<String> recentDays = new ArrayList<>();
		for(int i = 0; i < days; i ++) {
			recentDays.add(sdf.format(cal.getTime()));
			cal.add(Calendar.DATE, -1);
		}
		Collections.sort(recentDays);
		return recentDays;
	}
	
	/**
	 * 获取最近多少周
	 */
	public static List<String> getRecentWeeks(int weeks) {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat yearSdf = new SimpleDateFormat("yyyy");
		List<String> weekList = new ArrayList<>();
		for(int i = 1; i <= weeks; i ++) {
			cal.add(Calendar.DATE, -7);
			String week = yearSdf.format(cal.getTime()) + "." + cal.get(Calendar.WEEK_OF_YEAR);
			weekList.add(week);
		}
		return weekList;
	}
	
	/**
	 * 获取最近多少月
	 */
	public static List<String> getRecentMonths(int months){
		Date today = new Date();
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		List<String> recentMonths = new ArrayList<>();
		for(int i = 0; i < months; i ++) {
			cal.setTime(today);
			cal.add(Calendar.MONTH, -i);
			recentMonths.add(sdf.format(cal.getTime()));
		}
		return recentMonths;
	}
	
	/**
	 * 获取最近多少年
	 */
	public static List<String> getRecentYears(int years) {
		Date today = new Date();
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		List<String> recentYears = new ArrayList<>();
		for(int i = 0; i < years; i ++) {
			cal.setTime(today);
			cal.add(Calendar.YEAR, -i);
			recentYears.add(sdf.format(cal.getTime()));
		}
		return recentYears;
	}
	
}
