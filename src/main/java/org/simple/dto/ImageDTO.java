package org.simple.dto;

import java.io.Serializable;

/**
 * 图片DTO
 */
public class ImageDTO implements Serializable {
	private static final long serialVersionUID = -304884270216032619L;
	private String result;
	private String message;

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
