package org.simple.dto;

import org.simple.entity.AttachmentDO;

/**
 * 附件信息DTO
 */
public class AttachmentDTO extends AttachmentDO {
	private static final long serialVersionUID = 348650033169184891L;
	private String dateValue;

	public String getDateValue() {
		return dateValue;
	}

	public void setDateValue(String dateValue) {
		this.dateValue = dateValue;
	}

}
