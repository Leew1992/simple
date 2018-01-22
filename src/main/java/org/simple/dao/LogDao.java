package org.simple.dao;

import java.util.List;

import org.simple.dto.LogOperateDTO;
import org.simple.entity.LogOperateDO;
import org.springframework.stereotype.Repository;

@Repository
public interface LogDao {

	/**
	 * 操作日志分页列表
	 */
	List<LogOperateDTO> pagingLogOperates(LogOperateDTO logOperateDTO);
	
	/**
	 * 保存操作日志信息
	 */
	void saveLogOperate(LogOperateDO logOperateDO);
	
	/**
	 * 删除操作日志
	 */
	void deleteLogOperate(String idLogOperate);
	
	/**
	 * 批量删除操作日志
	 */
	void batchDeleteLogOperates(String idLogOperates);
}
