package org.simple.quartz;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.utils.DateUtils;
import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.simple.converter.StatConverter;
import org.simple.dto.MonitorAccessDTO;
import org.simple.dto.StatDayAccessDTO;
import org.simple.entity.StatDayAccessDO;
import org.simple.entity.StatMonthAccessDO;
import org.simple.entity.StatWeekAccessDO;
import org.simple.entity.StatYearAccessDO;
import org.simple.handler.StatHandler;
import org.simple.resolver.MonitorResolver;
import org.simple.resolver.StatResolver;
import org.simple.service.MonitorService;
import org.simple.service.StatAccessService;
import org.simple.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.transaction.annotation.Transactional;

/**
 * 统计访问周期JOB
 */
public class StatAccessJob extends QuartzJobBean {
	
	private static final String ID_USER = "idUser";
	private static final String ACCESS_MODULE = "accessModule";
	private static final String ACCESS_DATE = "accessDate";	
	private final Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private StatAccessService statAccessService;
	
	@Autowired
	private MonitorService monitorService;
	
	@Transactional
	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		
		// 生成按日访问统计信息
		List<StatDayAccessDTO> handledStatDayAccessList = genStatDayAccess();
		
		// 生成按周访问统计信息
		genStatWeekAccess(handledStatDayAccessList);
		
		// 生成按月访问统计信息
		genStatMonthAccess(handledStatDayAccessList);
		
		// 生成按年访问统计信息
		genStatYearAccess(handledStatDayAccessList);
		
		// 清除增量的日访问数据
		clearIncrementDayAccesses(handledStatDayAccessList);
	}
	
	/**
	 * 生成按日访问统计信息
	 */
	private List<StatDayAccessDTO> genStatDayAccess() {
		// 获取未处理的监控访问列表
		List<MonitorAccessDTO> monitorAccessList = monitorService.listUnstatedMonitorAccesses();
		// 将监控访问信息转成按日访问统计信息
		List<StatDayAccessDTO> statDayAccessList = StatConverter.convertStatDayAccess(monitorAccessList);
		// 将按日统计统计信息合并
		List<StatDayAccessDTO> handledStatDayAccessList = StatHandler.mergeStatDayAccess(statDayAccessList);
		// 如果查询监控访问列表为空，则返回
		if(handledStatDayAccessList == null || handledStatDayAccessList.isEmpty()) {
			return new ArrayList<>();
		}
		// 更新按日访问统计
		for(StatDayAccessDO statDayAccess : handledStatDayAccessList) {
			Map<String, Object> paramMap = new HashMap<>();
			paramMap.put(ID_USER, statDayAccess.getIdUser());
			paramMap.put(ACCESS_MODULE, statDayAccess.getAccessModule());
			paramMap.put(ACCESS_DATE, DateUtils.formatDate(new Date(), "yyyyMMdd"));
			// 查询当天的访问统计
			List<StatDayAccessDO> statDayAccessDOs = statAccessService.listStatDayAccesses(paramMap);
			if (statDayAccessDOs != null && !statDayAccessDOs.isEmpty()) {
				// 增加按日访问统计数据
				statAccessService.increaseStatDayAccess(statDayAccess);
			} else {
				// 保存按日访问统计信息
				statAccessService.saveStatDayAccess(statDayAccess);
			}
		}
		List<String> idMonitorAccessList = MonitorResolver.extractIdsFromMonitorAccessDTOs(monitorAccessList);
		// 批量关闭监控访问信息
		monitorService.batchCloseMonitorAccesses(idMonitorAccessList);
		logger.debug("按日访问统计JOB执行成功");
		
		return handledStatDayAccessList;
	}
	
	/**
	 * 生成按周访问统计信息
	 */
	private void genStatWeekAccess(List<StatDayAccessDTO> handledStatDayAccessList) {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat daySdf = new SimpleDateFormat("yyyyMMdd");
		// 如果无新增访问数据，则返回
		if(handledStatDayAccessList == null || handledStatDayAccessList.isEmpty()) {
			return;
		}
		for(StatDayAccessDO statDayAccess : handledStatDayAccessList) {
			try {
				cal.setTime(daySdf.parse(statDayAccess.getAccessDate()));
			} catch (ParseException e) {
				logger.error(e.getMessage());
			}
			String accessDate = statDayAccess.getAccessDate();
			String currentWeek = accessDate.substring(0, accessDate.length()-4) + "." + cal.get(Calendar.WEEK_OF_YEAR);
			Map<String, Object> paramMap = new HashMap<>();
			paramMap.put(ID_USER, statDayAccess.getIdUser());
			paramMap.put(ACCESS_MODULE, statDayAccess.getAccessModule());
			paramMap.put(ACCESS_DATE, currentWeek);
			List<StatWeekAccessDO> statWeekAccessDOs = statAccessService.listStatWeekAccesses(paramMap);
			// 判断该用户是否已存在本周访问统计信息
			if(statWeekAccessDOs != null && !statWeekAccessDOs.isEmpty()) {
				// 增加按周访问统计数据
				statAccessService.increaseStatWeekAccess(statDayAccess);				
			} else {
				StatWeekAccessDO statWeekAccess = new StatWeekAccessDO();
				statWeekAccess.setIdUser(statDayAccess.getIdUser());
				statWeekAccess.setAccessModule(statDayAccess.getAccessModule());
				statWeekAccess.setAccessCount(statDayAccess.getLastIncrease());
				statWeekAccess.setAccessDate(currentWeek);
				// 保存按周访问统计数据
				statAccessService.saveStatWeekAccess(statWeekAccess);
			}
		}
	}
	
	/**
	 * 生成按月访问统计信息
	 */
	private void genStatMonthAccess(List<StatDayAccessDTO> handledStatDayAccessList) {
		// 如果无新增访问数据，则返回
		if(handledStatDayAccessList == null || handledStatDayAccessList.isEmpty()) {
			return;
		}
		for(StatDayAccessDO statDayAccess : handledStatDayAccessList) {
			String accessDate = statDayAccess.getAccessDate();
			String currentMonth = accessDate.substring(0, accessDate.length()-2);
			Map<String, Object> paramMap = new HashMap<>();
			paramMap.put(ID_USER, statDayAccess.getIdUser());
			paramMap.put(ACCESS_MODULE, statDayAccess.getAccessModule());
			paramMap.put(ACCESS_DATE, currentMonth);
			List<StatMonthAccessDO> statMonthAccessDOs = statAccessService.listStatMonthAccesses(paramMap);
			// 判断该用户是否已存在当月访问统计信息
			if(statMonthAccessDOs != null && !statMonthAccessDOs.isEmpty()) {
				// 增加按月访问统计数据
				statAccessService.increaseStatMonthAccess(statDayAccess);				
			} else {
				StatMonthAccessDO statMonthAccess = new StatMonthAccessDO();
				statMonthAccess.setIdUser(statDayAccess.getIdUser());
				statMonthAccess.setAccessModule(statDayAccess.getAccessModule());
				statMonthAccess.setAccessCount(statDayAccess.getLastIncrease());
				statMonthAccess.setAccessDate(currentMonth);
				// 保存按月访问统计数据
				statAccessService.saveStatMonthAccess(statMonthAccess);
			}
		}
	}
	
	/**
	 * 生成按年访问统计信息
	 */
	private void genStatYearAccess(List<StatDayAccessDTO> handledStatDayAccessList) {
		// 如果无新增访问数据，则返回
		if(handledStatDayAccessList == null || handledStatDayAccessList.isEmpty()) {
			return;
		}
		for(StatDayAccessDO statDayAccess : handledStatDayAccessList) {
			String accessDate = statDayAccess.getAccessDate();
			String currentYear = accessDate.substring(0, accessDate.length()-4);
			Map<String, Object> paramMap = new HashMap<>();
			paramMap.put(ID_USER, statDayAccess.getIdUser());
			paramMap.put(ACCESS_MODULE, statDayAccess.getAccessModule());
			paramMap.put(ACCESS_DATE, currentYear);
			List<StatYearAccessDO> statYearAccessDOs = statAccessService.listStatYearAccesses(paramMap);
			if(statYearAccessDOs != null && !statYearAccessDOs.isEmpty()) {
				statAccessService.increaseStatYearAccess(statDayAccess);		
			} else {
				StatYearAccessDO statYearAccess = new StatYearAccessDO();
				statYearAccess.setIdUser(statDayAccess.getIdUser());
				statYearAccess.setAccessModule(statDayAccess.getAccessModule());
				statYearAccess.setAccessCount(statDayAccess.getLastIncrease());
				statYearAccess.setAccessDate(currentYear);
				statAccessService.saveStatYearAccess(statYearAccess);
			}
		}
	}
	
	/**
	 * 清除新增的按日访问统计数据
	 */
	private void clearIncrementDayAccesses(List<StatDayAccessDTO> handledStatDayAccessList) {
		// 如果无新增访问数据，则返回
		if(handledStatDayAccessList == null || handledStatDayAccessList.isEmpty()) {
			return;
		}
		List<String> idStatDayAccessList = StatResolver.extractIdsFromStatDayAccessDTOs(handledStatDayAccessList);
		statAccessService.clearStatDayAccessIncreament(idStatDayAccessList);
	}
}