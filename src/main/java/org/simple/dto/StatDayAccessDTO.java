package org.simple.dto;

import org.simple.entity.StatDayAccessDO;

/**
 * 按日访问统计
 */
public class StatDayAccessDTO extends StatDayAccessDO {
	private static final long serialVersionUID = 4088497146075734260L;
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
	
	/**
	 * idUser,accessModule,accessDate
	 * 三个值相等的对象，默认是相等的
	 */
	@Override
	public int hashCode() {
		String identify = super.getIdUser()+super.getAccessModule()+super.getAccessDate();
		return identify.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return this.hashCode() == obj.hashCode();
	}

}
