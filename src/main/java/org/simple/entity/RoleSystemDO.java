package org.simple.entity;

/**
 * 角色系统信息
 */
public class RoleSystemDO extends BaseDO {
	private static final long serialVersionUID = -2878914647628127172L;
	private String idRoleSystem;
	private String idRole;
	private String idSystem;

	public String getIdRoleSystem() {
		return idRoleSystem;
	}

	public void setIdRoleSystem(String idRoleSystem) {
		this.idRoleSystem = idRoleSystem;
	}

	public String getIdRole() {
		return idRole;
	}

	public void setIdRole(String idRole) {
		this.idRole = idRole;
	}

	public String getIdSystem() {
		return idSystem;
	}

	public void setIdSystem(String idSystem) {
		this.idSystem = idSystem;
	}

}
