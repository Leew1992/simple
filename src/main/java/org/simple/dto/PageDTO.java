package org.simple.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 分页信息DTO
 */
@SuppressWarnings("rawtypes")
public class PageDTO implements Serializable {
	private static final long serialVersionUID = 5970853286618622387L;
	private Integer total;
	private List rows;

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public List getRows() {
		return rows;
	}

	public void setRows(List rows) {
		this.rows = rows;
	}

}
