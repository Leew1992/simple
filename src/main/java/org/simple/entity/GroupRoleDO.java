package org.simple.entity;


/**
 * 分组角色信息
 */
public class GroupRoleDO extends BaseDO {
	private static final long serialVersionUID = 6517714010880579795L;
	private String idGroupRole;
	private String idGroup;
	private String idRole;

	public String getIdGroupRole() {
		return idGroupRole;
	}

	public void setIdGroupRole(String idGroupRole) {
		this.idGroupRole = idGroupRole;
	}

	public String getIdGroup() {
		return idGroup;
	}

	public void setIdGroup(String idGroup) {
		this.idGroup = idGroup;
	}

	public String getIdRole() {
		return idRole;
	}

	public void setIdRole(String idRole) {
		this.idRole = idRole;
	}

}
