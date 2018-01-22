package org.simple.quartz;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.utils.DateUtils;
import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.simple.entity.BaseStatDataDO;
import org.simple.entity.StatDayDataDO;
import org.simple.entity.StatMonthDataDO;
import org.simple.entity.StatWeekDataDO;
import org.simple.entity.StatYearDataDO;
import org.simple.handler.StatHandler;
import org.simple.service.AttachmentService;
import org.simple.service.CommentService;
import org.simple.service.MonitorService;
import org.simple.service.PostService;
import org.simple.service.StatAccessService;
import org.simple.service.StatDataService;
import org.simple.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class StatDataJob extends QuartzJobBean {
	
	private final Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	private UserService userService;
	@Autowired
	private PostService postService;
	@Autowired
	private CommentService commentService;
	@Autowired
	private AttachmentService attachmentService;
	@Autowired
	private MonitorService monitorService;
	@Autowired
	private StatAccessService statAccessService;
	@Autowired
	private StatDataService statDataService;

	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		
		//生成本日生成数据的数量
		genStatDayData();
		
		//生成本周生成数据的数量
		genStatWeekData();
		
		//生成本月生成数据的数量
		genStatMonthData();
		
		//生成本年生成数据的数量
		genStatYearData();
	}
	
	/**
	 * 生成本日生成数据的数量
	 */
	private void genStatDayData() {
		logger.debug("统计本日数量开始...");
		String currentDay = DateUtils.formatDate(new Date(), "yyyyMMdd");
		BaseStatDataDO baseStatDataDO = genBaseStatDataDOByCurrentDate(currentDay);
		StatDayDataDO statDayDataDO = new StatDayDataDO();
		BeanUtils.copyProperties(baseStatDataDO, statDayDataDO);
		statDayDataDO.setAccessDate(currentDay);
		List<StatDayDataDO> statDayDatas = statDataService.listStatDayDatas(currentDay);
		if(statDayDatas != null && statDayDatas.size() != 0) {
			statDataService.updateDayDataForCurrentDay(statDayDataDO);
		} else {
			statDataService.saveStatDayData(statDayDataDO);			
		}
		logger.debug("统计本日数量结束...");
	}
	
	/**
	 * 生成本周生成数据的数量
	 */
	private void genStatWeekData() {
		logger.debug("统计本周数量开始...");
		Calendar cal = Calendar.getInstance();
		String currentWeek = DateUtils.formatDate(new Date(), "yyyy") + "." + cal.get(Calendar.WEEK_OF_YEAR);
		
		List<String> weekDayList = StatHandler.getPastDayOfCurrentWeek();
		BaseStatDataDO baseStatDataDO = genBaseStatDataDOInCurrentDates(weekDayList);
		StatWeekDataDO statWeekDataDO = new StatWeekDataDO();
		BeanUtils.copyProperties(baseStatDataDO, statWeekDataDO);
		statWeekDataDO.setAccessDate(currentWeek);
		List<StatWeekDataDO> statWeekDatas = statDataService.listStatWeekDatas(currentWeek);
		if(statWeekDatas != null && statWeekDatas.size() != 0) {
			statDataService.updateWeekDataForCurrentWeek(statWeekDataDO);
		} else {
			statDataService.saveStatWeekData(statWeekDataDO);			
		}
		logger.debug("统计本周数量结束...");
	}
	
	/**
	 * 生成本月生成数据的数量
	 */
	private void genStatMonthData() {
		logger.debug("统计本月数量开始...");
		String currentMonth = DateUtils.formatDate(new Date(), "yyyyMM");
		BaseStatDataDO baseStatDataDO = genBaseStatDataDOByCurrentDate(currentMonth);
		StatMonthDataDO statMonthDataDO = new StatMonthDataDO();
		BeanUtils.copyProperties(baseStatDataDO, statMonthDataDO);
		statMonthDataDO.setAccessDate(currentMonth);
		List<StatMonthDataDO> statMonthDatas = statDataService.listStatMonthDatas(currentMonth);
		if(statMonthDatas != null && statMonthDatas.size() != 0) {
			statDataService.updateMonthDataForCurrentMonth(statMonthDataDO);
		} else {
			statDataService.saveStatMonthData(statMonthDataDO);			
		}
		logger.debug("统计本月数量结束...");
	}
	
	/**
	 * 生成本年生成数据的数量
	 */
	private void genStatYearData() {
		logger.debug("统计本年数量开始...");
		String currentYear = DateUtils.formatDate(new Date(), "yyyy");
		BaseStatDataDO baseStatDataDO = genBaseStatDataDOByCurrentDate(currentYear);
		StatYearDataDO statYearDataDO = new StatYearDataDO();
		BeanUtils.copyProperties(baseStatDataDO, statYearDataDO);
		statYearDataDO.setAccessDate(currentYear);
		List<StatYearDataDO> statYearDatas = statDataService.listStatYearDatas(currentYear);
		if(statYearDatas != null && statYearDatas.size() == 0) {
			statDataService.updateYearDataForCurrentYear(statYearDataDO);
		} else {
			statDataService.saveStatYearData(statYearDataDO);			
		}
		logger.debug("统计本年数量结束...");
	}
	
	/**
	 * 生成基础的统计数据
	 */
	private BaseStatDataDO genBaseStatDataDOByCurrentDate(String currentDate) {
		Integer userTotal = userService.countUsers(Arrays.asList(currentDate));
		Integer postTotal = postService.countPostsByCreatedDate(currentDate);
		Integer commentTotal = commentService.countCommentsByCreatedDate(currentDate);
		Integer pictureTotal = attachmentService.countAttachmentsByCreatedDateForPicture(currentDate);
		Integer videoTotal = attachmentService.countAttachmentsByCreatedDateForVideo(currentDate);
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dates", Arrays.asList("2018"));
		paramMap.put("accessModule", "login");
		Integer accessTotal = statAccessService.countStatYearAccess(paramMap);
		
		BaseStatDataDO baseStatDataDO = new BaseStatDataDO();
		baseStatDataDO.setUserTotal(userTotal);
		baseStatDataDO.setPostTotal(postTotal);
		baseStatDataDO.setCommentTotal(commentTotal);
		baseStatDataDO.setPictureTotal(pictureTotal);
		baseStatDataDO.setVideoTotal(videoTotal);
		baseStatDataDO.setAccessTotal(accessTotal);
		
		return baseStatDataDO;
	}
	
	/**
	 * 生成基础的统计数据
	 */
	private BaseStatDataDO genBaseStatDataDOInCurrentDates(List<String> currentDates) {
		Integer userTotal = userService.countUsers(currentDates);
		Integer postTotal = postService.countPostsInCreatedDates(currentDates);
		Integer commentTotal = commentService.countCommentsInCreatedDates(currentDates);
		Integer pictureTotal = attachmentService.countAttachmentsInCreatedDatesForPicture(currentDates);
		Integer videoTotal = attachmentService.countAttachmentsInCreatedDatesForVideo(currentDates);
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dates", Arrays.asList("2018"));
		paramMap.put("accessModule", "login");
		Integer accessTotal = statAccessService.countStatYearAccess(paramMap);
		
		BaseStatDataDO baseStatDataDO = new BaseStatDataDO();
		baseStatDataDO.setUserTotal(userTotal);
		baseStatDataDO.setPostTotal(postTotal);
		baseStatDataDO.setCommentTotal(commentTotal);
		baseStatDataDO.setPictureTotal(pictureTotal);
		baseStatDataDO.setVideoTotal(videoTotal);
		baseStatDataDO.setAccessTotal(accessTotal);
		
		return baseStatDataDO;
	}
	
}
