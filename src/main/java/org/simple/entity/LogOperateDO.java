package org.simple.entity;

/**
 * 操作日志信息
 */
public class LogOperateDO extends BaseDO {

	private static final long serialVersionUID = -1246253525477233843L;
	private String idLogOperate;
	private String idUser;
	private String belongClass;
	private String belongMethod;
	private String inputParam;
	private String messageHint;

	public String getIdLogOperate() {
		return idLogOperate;
	}

	public void setIdLogOperate(String idLogOperate) {
		this.idLogOperate = idLogOperate;
	}

	public String getIdUser() {
		return idUser;
	}

	public void setIdUser(String idUser) {
		this.idUser = idUser;
	}

	public String getBelongClass() {
		return belongClass;
	}

	public void setBelongClass(String belongClass) {
		this.belongClass = belongClass;
	}

	public String getBelongMethod() {
		return belongMethod;
	}

	public void setBelongMethod(String belongMethod) {
		this.belongMethod = belongMethod;
	}

	public String getInputParam() {
		return inputParam;
	}

	public void setInputParam(String inputParam) {
		this.inputParam = inputParam;
	}

	public String getMessageHint() {
		return messageHint;
	}

	public void setMessageHint(String messageHint) {
		this.messageHint = messageHint;
	}

}
