package org.simple.dto;

import java.io.Serializable;

/**
 * 响应消息DTO
 */
public class ResultDTO implements Serializable {
	private static final long serialVersionUID = 974781526702228119L;
	private Boolean flag;
	private Object data;
	private String msg;
	
	public ResultDTO(Boolean flag, String msg) {
		super();
		this.flag = flag;
		this.msg = msg;
	}

	public ResultDTO(Boolean flag, Object data, String msg) {
		super();
		this.flag = flag;
		this.data = data;
		this.msg = msg;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(Boolean flag) {
		this.flag = flag;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
