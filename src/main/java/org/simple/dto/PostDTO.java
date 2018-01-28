package org.simple.dto;

import org.simple.entity.PostDO;

/**
 * 贴子信息DTO
 */
public class PostDTO extends PostDO {
	private static final long serialVersionUID = -5858136724020464827L;
	private String idColumn;
	private String idColumns;

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

}
