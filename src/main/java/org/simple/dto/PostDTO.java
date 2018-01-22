package org.simple.dto;

import org.simple.entity.PostDO;

/**
 * 贴子信息DTO
 */
public class PostDTO extends PostDO {
	private static final long serialVersionUID = -5858136724020464827L;
	private String idColumn;
	private String idColumns;
	private String keyWord;
	private String startDate;
	private String endDate;
	private String sortName;
	private String orderBy;

	public String getIdColumn() {
		return idColumn;
	}

	public void setIdColumn(String idColumn) {
		this.idColumn = idColumn;
	}

	public String getIdColumns() {
		return idColumns;
	}

	public void setIdColumns(String idColumns) {
		this.idColumns = idColumns;
	}

	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getSortName() {
		return sortName;
	}

	public void setSortName(String sortName) {
		this.sortName = sortName;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

}
