package org.simple.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 报表信息DTO
 */
public class ChartDTO implements Serializable {
	private static final long serialVersionUID = 6502375924439986030L;
	private List<String> names;
	private List<String> values;

	public List<String> getNames() {
		return names;
	}

	public void setNames(List<String> names) {
		this.names = names;
	}

	public List<String> getValues() {
		return values;
	}

	public void setValues(List<String> values) {
		this.values = values;
	}

}
