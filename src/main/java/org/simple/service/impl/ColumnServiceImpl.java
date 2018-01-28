package org.simple.service.impl;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.simple.constant.MessageConst;
import org.simple.context.UserContext;
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
	public List<TreeNode> getColumnTree() {
		return columnDao.getColumnTree();
	}
	
	@Override
	public List<TreeNode> getHasCheckedColumnTreeByIdPost(String idPost) {
		return columnDao.getHasCheckedColumnTreeByIdPost(idPost);
	}

	@Override
	public ResultDTO saveColumn(ColumnDO columnDO) {
		columnDO.setCreatedBy(UserContext.getCurrentUserName());
		columnDO.setUpdatedBy(UserContext.getCurrentUserName());
		columnDao.saveColumn(columnDO);
		return new ResultDTO(true, columnDO.getIdColumn(), MessageConst.SAVE_SUCCESS);
	}

	@Override
	public ResultDTO updateColumn(ColumnDO columnDO) {
		columnDO.setUpdatedBy(UserContext.getCurrentUserName());
		columnDao.updateColumn(columnDO);
		return new ResultDTO(true, MessageConst.UPDATE_SUCCESS);
	}
	
	@Override
	public ResultDTO deleteColumn(String idColumn) {
		List<ColumnDO> columnList = columnDao.listColumnsByIdParent(idColumn);
		if(columnList != null && !columnList.isEmpty()) {
			columnDao.deleteColumn(idColumn);
		} else {
			return new ResultDTO(false, MessageConst.HAS_SUB_COLUMN_IN_COLUMN);
		}
		return new ResultDTO(true, MessageConst.DELETE_SUCCESS);
	}

	@Override
	public ResultDTO batchDeleteColumns(String idColumns) {
		String[] idColumnArr = idColumns.split(",");
		columnDao.batchDeleteColumns(Arrays.asList(idColumnArr));
		return new ResultDTO(true, MessageConst.DELETE_SUCCESS);
	}
	
}
