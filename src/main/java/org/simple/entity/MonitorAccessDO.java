package org.simple.entity;

/**
 * 访问监控信息
 */
public class MonitorAccessDO extends BaseDO {

	private static final long serialVersionUID = 8399007856895266190L;
	private String idMonitorAccess;
	private String idUser;
	private String accessModule;
	private String requestURI;
	private String remoteAddress;
	private Boolean stated;

	public String getIdMonitorAccess() {
		return idMonitorAccess;
	}

	public void setIdMonitorAccess(String idMonitorAccess) {
		this.idMonitorAccess = idMonitorAccess;
	}

	public String getAccessModule() {
		return accessModule;
	}

	public void setAccessModule(String accessModule) {
		this.accessModule = accessModule;
	}

	public String getIdUser() {
		return idUser;
	}

	public void setIdUser(String idUser) {
		this.idUser = idUser;
	}

	public String getRequestURI() {
		return requestURI;
	}

	public void setRequestURI(String requestURI) {
		this.requestURI = requestURI;
	}

	public String getRemoteAddress() {
		return remoteAddress;
	}

	public void setRemoteAddress(String remoteAddress) {
		this.remoteAddress = remoteAddress;
	}

	public Boolean getStated() {
		return stated;
	}

	public void setStated(Boolean stated) {
		this.stated = stated;
	}

}
