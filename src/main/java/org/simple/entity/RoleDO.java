package org.simple.entity;


/**
 * 角色信息
 */
public class RoleDO extends BaseDO {
	private static final long serialVersionUID = -8707024553830027902L;
	private String idRole;
	private String idParent;
	private String roleName;
	private String roleDesc;

	public String getIdRole() {
		return idRole;
	}

	public void setIdRole(String idRole) {
		this.idRole = idRole;
	}
	
	public String getIdParent() {
		return idParent;
	}

	public void setIdParent(String idParent) {
		this.idParent = idParent;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleDesc() {
		return roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

}
