package org.simple.dto;

import org.simple.entity.AttachmentDO;

/**
 * 附件信息DTO
 */
public class AttachmentDTO extends AttachmentDO {
	private static final long serialVersionUID = 348650033169184891L;
	private String keyWord;
	private String startDate;
	private String endDate;
	private String sortName;
	private String orderBy;

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
