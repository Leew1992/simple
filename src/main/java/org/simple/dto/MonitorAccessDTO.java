package org.simple.dto;

import org.simple.entity.MonitorAccessDO;

/**
 * 监控访问DTO
 */
public class MonitorAccessDTO extends MonitorAccessDO {

	private static final long serialVersionUID = -1542807107069162216L;
	private String startDate;
	private String endDate;
	private String sortName;
	private String sortOrder;

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
