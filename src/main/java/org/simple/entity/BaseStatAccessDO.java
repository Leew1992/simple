package org.simple.entity;

public class BaseStatAccessDO extends BaseDO {
	private static final long serialVersionUID = 2391535317412640151L;
	private String idUser;
	private String accessModule;
	private Integer accessCount;
	private String accessDate;

	public String getIdUser() {
		return idUser;
	}

	public void setIdUser(String idUser) {
		this.idUser = idUser;
	}

	public String getAccessModule() {
		return accessModule;
	}

	public void setAccessModule(String accessModule) {
		this.accessModule = accessModule;
	}

	public Integer getAccessCount() {
		return accessCount;
	}

	public void setAccessCount(Integer accessCount) {
		this.accessCount = accessCount;
	}

	public String getAccessDate() {
		return accessDate;
	}

	public void setAccessDate(String accessDate) {
		this.accessDate = accessDate;
	}

}
