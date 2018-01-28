package org.simple.service.impl;

import java.util.List;

import org.simple.constant.MessageConst;
import org.simple.context.UserContext;
import org.simple.dao.StatDataDao;
import org.simple.dto.ResultDTO;
import org.simple.entity.StatDayDataDO;
import org.simple.entity.StatMonthDataDO;
import org.simple.entity.StatWeekDataDO;
import org.simple.entity.StatYearDataDO;
import org.simple.service.StatDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatDataServiceImpl implements StatDataService {
	
	@Autowired
	private StatDataDao statDataDao;

	@Override
	public List<StatDayDataDO> listStatDayDatas(String accessDate) {
		return statDataDao.listStatDayDatas(accessDate);
	}

	@Override
	public List<StatWeekDataDO> listStatWeekDatas(String accessDate) {
		return statDataDao.listStatWeekDatas(accessDate);
	}

	@Override
	public List<StatMonthDataDO> listStatMonthDatas(String accessDate) {
		return statDataDao.listStatMonthDatas(accessDate);
	}

	@Override
	public List<StatYearDataDO> listStatYearDatas(String accessDate) {
		return statDataDao.listStatYearDatas(accessDate);
	}

	@Override
	public ResultDTO saveStatDayData(StatDayDataDO statDayDataDO) {
		statDayDataDO.setCreatedBy(UserContext.getCurrentUserName());
		statDayDataDO.setUpdatedBy(UserContext.getCurrentUserName());
		statDataDao.saveStatDayData(statDayDataDO);
		return new ResultDTO(true, MessageConst.SAVE_SUCCESS);
	}

	@Override
	public ResultDTO saveStatWeekData(StatWeekDataDO statWeekDataDO) {
		statWeekDataDO.setCreatedBy(UserContext.getCurrentUserName());
		statWeekDataDO.setUpdatedBy(UserContext.getCurrentUserName());
		statDataDao.saveStatWeekData(statWeekDataDO);
		return new ResultDTO(true, MessageConst.SAVE_SUCCESS);
	}

	@Override
	public ResultDTO saveStatMonthData(StatMonthDataDO statMonthDataDO) {
		statMonthDataDO.setCreatedBy(UserContext.getCurrentUserName());
		statMonthDataDO.setUpdatedBy(UserContext.getCurrentUserName());
		statDataDao.saveStatMonthData(statMonthDataDO);
		return new ResultDTO(true, MessageConst.SAVE_SUCCESS);
	}

	@Override
	public ResultDTO saveStatYearData(StatYearDataDO statYearDataDO) {
		statYearDataDO.setCreatedBy(UserContext.getCurrentUserName());
		statYearDataDO.setUpdatedBy(UserContext.getCurrentUserName());
		statDataDao.saveStatYearData(statYearDataDO);
		return new ResultDTO(true, MessageConst.SAVE_SUCCESS);
	}

	@Override
	public ResultDTO updateDayDataForCurrentDay(StatDayDataDO statDayDataDO) {
		statDataDao.updateDayDataForCurrentDay(statDayDataDO);
		return new ResultDTO(true, MessageConst.UPDATE_SUCCESS);
	}

	@Override
	public ResultDTO updateWeekDataForCurrentWeek(StatWeekDataDO statWeekDataDO) {
		statDataDao.updateWeekDataForCurrentWeek(statWeekDataDO);
		return new ResultDTO(true, MessageConst.UPDATE_SUCCESS);
	}

	@Override
	public ResultDTO updateMonthDataForCurrentMonth(StatMonthDataDO statMonthDataDO) {
		statDataDao.updateMonthDataForCurrentMonth(statMonthDataDO);
		return new ResultDTO(true, MessageConst.UPDATE_SUCCESS);
	}

	@Override
	public ResultDTO updateYearDataForCurrentYear(StatYearDataDO statYearDataDO) {
		statDataDao.updateYearDataForCurrentYear(statYearDataDO);
		return new ResultDTO(true, MessageConst.UPDATE_SUCCESS);
	}

}
