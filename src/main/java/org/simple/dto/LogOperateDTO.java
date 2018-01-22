package org.simple.dto;

import org.simple.entity.LogOperateDO;

/**
 * 操作日志信息
 */
public class LogOperateDTO extends LogOperateDO {

	private static final long serialVersionUID = 3986701851918279406L;
	private String keyWord;
	private String startDate;
	private String endDate;
	private String sortName;
	private String sortOrder;

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

	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

}
