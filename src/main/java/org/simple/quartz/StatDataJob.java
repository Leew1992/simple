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
	private static final String ACCESS_MODULE = "accessModule";
	private static final String ATTACH_SOURCE = "attachSource";
	private static final String CREATED_DATE = "createdDate";
	private static final String CREATED_DATES = "createdDates";

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

		// 生成本日生成数据的数量
		generateStatDayData();

		// 生成本周生成数据的数量
		generateStatWeekData();

		// 生成本月生成数据的数量
		generateStatMonthData();

		// 生成本年生成数据的数量
		generateStatYearData();
	}

	/**
	 * 生成本日生成数据的数量
	 */
	private void generateStatDayData() {
		logger.debug("统计本日数量开始...");
		String today = DateUtils.formatDate(new Date(), "yyyyMMdd");
		BaseStatDataDO baseStatDataDO = buildBaseStatDataDOs(Arrays.asList(today));
		StatDayDataDO statDayDataDO = new StatDayDataDO();
		BeanUtils.copyProperties(baseStatDataDO, statDayDataDO);
		statDayDataDO.setAccessDate(today);
		List<StatDayDataDO> statDayDatas = statDataService.listStatDayDatas(today);
		if (statDayDatas != null && !statDayDatas.isEmpty()) {
			statDataService.updateDayDataForCurrentDay(statDayDataDO);
		} else {
			statDataService.saveStatDayData(statDayDataDO);
		}
		logger.debug("统计本日数量结束...");
	}

	/**
	 * 生成本周生成数据的数量
	 */
	private void generateStatWeekData() {
		logger.debug("统计本周数量开始...");
		Calendar cal = Calendar.getInstance();
		String thisWeek = DateUtils.formatDate(new Date(), "yyyy") + "." + cal.get(Calendar.WEEK_OF_YEAR);

		List<String> weekDayList = StatHandler.getPastDayOfThisWeek();
		BaseStatDataDO baseStatDataDO = buildBaseStatDataDOs(weekDayList);
		StatWeekDataDO statWeekDataDO = new StatWeekDataDO();
		BeanUtils.copyProperties(baseStatDataDO, statWeekDataDO);
		statWeekDataDO.setAccessDate(thisWeek);
		List<StatWeekDataDO> statWeekDatas = statDataService.listStatWeekDatas(thisWeek);
		if (statWeekDatas != null && !statWeekDatas.isEmpty()) {
			statDataService.updateWeekDataForCurrentWeek(statWeekDataDO);
		} else {
			statDataService.saveStatWeekData(statWeekDataDO);
		}
		logger.debug("统计本周数量结束...");
	}

	/**
	 * 生成本月生成数据的数量
	 */
	private void generateStatMonthData() {
		logger.debug("统计本月数量开始...");
		String thisMonth = DateUtils.formatDate(new Date(), "yyyyMM");
		BaseStatDataDO baseStatDataDO = buildBaseStatDataDO(thisMonth);
		StatMonthDataDO statMonthDataDO = new StatMonthDataDO();
		BeanUtils.copyProperties(baseStatDataDO, statMonthDataDO);
		statMonthDataDO.setAccessDate(thisMonth);
		List<StatMonthDataDO> statMonthDatas = statDataService.listStatMonthDatas(thisMonth);
		if (statMonthDatas != null && !statMonthDatas.isEmpty()) {
			statDataService.updateMonthDataForCurrentMonth(statMonthDataDO);
		} else {
			statDataService.saveStatMonthData(statMonthDataDO);
		}
		logger.debug("统计本月数量结束...");
	}

	/**
	 * 生成本年生成数据的数量
	 */
	private void generateStatYearData() {
		logger.debug("统计本年数量开始...");
		String thisYear = DateUtils.formatDate(new Date(), "yyyy");
		BaseStatDataDO baseStatDataDO = buildBaseStatDataDOs(Arrays.asList(thisYear));
		StatYearDataDO statYearDataDO = new StatYearDataDO();
		BeanUtils.copyProperties(baseStatDataDO, statYearDataDO);
		statYearDataDO.setAccessDate(thisYear);
		List<StatYearDataDO> statYearDatas = statDataService.listStatYearDatas(thisYear);
		if (statYearDatas != null && !statYearDatas.isEmpty()) {
			statDataService.updateYearDataForCurrentYear(statYearDataDO);
		} else {
			statDataService.saveStatYearData(statYearDataDO);
		}
		logger.debug("统计本年数量结束...");
	}

	/**
	 * 生成基础的统计数据
	 */
	private BaseStatDataDO buildBaseStatDataDOs(List<String> days) {
		Integer userTotal = userService.countUsersBasedAccurate(days);
		Integer postTotal = postService.countPostsBasedAccurate(days);
		Integer commentTotal = commentService.countCommentsBasedAccurate(days);
		
		Map<String, Object> attachMap = new HashMap<>();
		attachMap.put(CREATED_DATES, days);
		attachMap.put(ATTACH_SOURCE, "picture");
		Integer pictureTotal = attachmentService.countAttachmentsBasedAccurate(attachMap);
		attachMap.put(ATTACH_SOURCE, "video");
		Integer videoTotal = attachmentService.countAttachmentsBasedAccurate(attachMap);

		Map<String, Object> statMap = new HashMap<>();
		statMap.put("dates", Arrays.asList("2018"));
		statMap.put(ACCESS_MODULE, "login");
		Integer accessTotal = statAccessService.countStatYearAccess(statMap);

		return assembleBaseStatDataDO(userTotal, postTotal, commentTotal,
				pictureTotal, videoTotal, accessTotal);
	}

	/**
	 * 生成基础的统计数据
	 */
	private BaseStatDataDO buildBaseStatDataDO(String today) {
		Integer userTotal = userService.countUsersBasedFuzzy(today);
		Integer postTotal = postService.countPostsBasedFuzzy(today);
		Integer commentTotal = commentService.countCommentsBasedFuzzy(today);
		
		Map<String, Object> attachMap = new HashMap<>();
		attachMap.put(CREATED_DATE, today);
		attachMap.put(ATTACH_SOURCE, "picture");
		Integer pictureTotal = attachmentService.countAttachmentsBasedFuzzy(attachMap);
		attachMap.put(ATTACH_SOURCE, "video");
		Integer videoTotal = attachmentService.countAttachmentsBasedFuzzy(attachMap);

		Map<String, Object> statMap = new HashMap<>();
		statMap.put("dates", Arrays.asList("2018"));
		statMap.put(ACCESS_MODULE, "login");
		Integer accessTotal = statAccessService.countStatYearAccess(statMap);

		return assembleBaseStatDataDO(userTotal, postTotal, commentTotal,
				pictureTotal, videoTotal, accessTotal);
	}

	/**
	 * 组装成BaseStatDataDO
	 */
	private BaseStatDataDO assembleBaseStatDataDO(Integer userTotal,
			Integer postTotal, Integer commentTotal, Integer pictureTotal,
			Integer videoTotal, Integer accessTotal) {
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
