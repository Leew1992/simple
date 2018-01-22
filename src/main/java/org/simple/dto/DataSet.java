package org.simple.dto;

import java.util.List;

public class DataSet {
	// 标签
	private String label;
	// 数据
	private List<Integer> data;

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public List<Integer> getData() {
		return data;
	}

	public void setData(List<Integer> data) {
		this.data = data;
	}

}
