package org.simple.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.simple.constant.MessageConsts;
import org.simple.constant.SysCodeConsts;
import org.simple.dao.ColumnDao;
import org.simple.dto.ResultDTO;
import org.simple.dto.TreeNode;
import org.simple.entity.ColumnDO;
import org.simple.service.ColumnService;
import org.springframework.stereotype.Service;

@Service
public class ColumnServiceImpl implements ColumnService {
	
	@Resource
	private ColumnDao columnDao;

	@Override
	public ColumnDO getColumnById(String idColumn) {
		return columnDao.getColumnById(idColumn);
	}
	
	@Override
	public List<ColumnDO> listAllColumns() {
		return columnDao.listAllColumns();
	}

	@Override
	public List<TreeNode> getColumnTreeForPostList() {
		return columnDao.getColumnTreeForPostList();
	}
	
	@Override
	public List<TreeNode> getColumnTreeForPostForm(String idPost) {
		return columnDao.getColumnTreeForPostForm(idPost);
	}
	
	@Override
	public List<TreeNode> getColumnTreeForColumnConfig() {
		return columnDao.getColumnTreeForColumnConfig();
	}

	@Override
	public ResultDTO saveColumn(ColumnDO columnDO) {
		columnDO.setCreatedBy(SysCodeConsts.OPERATOR);
		columnDO.setCreatedDate(new Date());
		columnDO.setUpdatedBy(SysCodeConsts.OPERATOR);
		columnDO.setUpdatedDate(new Date());
		String idColumn = columnDao.saveColumn(columnDO);
		return new ResultDTO(true, idColumn, MessageConsts.SAVE_SUCCESS);
	}

	@Override
	public ResultDTO updateColumn(ColumnDO columnDO) {
		columnDO.setUpdatedBy(SysCodeConsts.OPERATOR);
		columnDO.setUpdatedDate(new Date());
		columnDao.updateColumn(columnDO);
		return new ResultDTO(true, MessageConsts.UPDATE_SUCCESS);
	}
	
	@Override
	public ResultDTO deleteColumn(String idColumn) {
		List<ColumnDO> columnList = columnDao.listColumnsByIdParent(idColumn);
		if(columnList != null && columnList.size() == 0) {
			columnDao.deleteColumn(idColumn);
		} else {
			return new ResultDTO(false, MessageConsts.HAS_SUB_COLUMN_IN_COLUMN);
		}
		return new ResultDTO(true, MessageConsts.DELETE_SUCCESS);
	}

	@Override
	public ResultDTO batchDeleteColumns(String idColumns) {
		String[] idColumnArr = idColumns.split(",");
		columnDao.batchDeleteColumns(Arrays.asList(idColumnArr));
		return new ResultDTO(true, MessageConsts.DELETE_SUCCESS);
	}
	
}
