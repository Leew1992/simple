package org.simple.constant;

public enum ExceptionEnum {
	USER_NOT_EXIST("用户不存在", "601");
	// 成员变量
	private String name;
	private String code;

	// 构造方法
	private ExceptionEnum(String name, String code) {
		this.name = name;
		this.code = code;
	}

	// get set 方法
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
