package org.simple.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 报表信息DTO
 */
public class ChartDTO implements Serializable {
	private static final long serialVersionUID = 6502375924439986030L;
	private List<String> labels;
	private List<DataSet> datasets;

	public List<String> getLabels() {
		return labels;
	}

	public void setLabels(List<String> labels) {
		this.labels = labels;
	}

	public List<DataSet> getDatasets() {
		return datasets;
	}

	public void setDatasets(List<DataSet> datasets) {
		this.datasets = datasets;
	}

}
