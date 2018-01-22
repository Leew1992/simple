package org.simple.entity;


/**
 * 分组用户信息
 */
public class GroupUserDO extends BaseDO {
	private static final long serialVersionUID = -4382073032084140709L;
	private String idGroupUser;
	private String idGroup;
	private String idUser;

	public String getIdGroupUser() {
		return idGroupUser;
	}

	public void setIdGroupUser(String idGroupUser) {
		this.idGroupUser = idGroupUser;
	}

	public String getIdGroup() {
		return idGroup;
	}

	public void setIdGroup(String idGroup) {
		this.idGroup = idGroup;
	}

	public String getIdUser() {
		return idUser;
	}

	public void setIdUser(String idUser) {
		this.idUser = idUser;
	}
	
}
