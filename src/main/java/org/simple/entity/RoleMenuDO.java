package org.simple.entity;


/**
 * 角色菜单信息
 */
public class RoleMenuDO extends BaseDO {
	private static final long serialVersionUID = -1279632405547580924L;
	private String idRoleMenu;
	private String idRole;
	private String idMenu;

	public String getIdRoleMenu() {
		return idRoleMenu;
	}

	public void setIdRoleMenu(String idRoleMenu) {
		this.idRoleMenu = idRoleMenu;
	}

	public String getIdRole() {
		return idRole;
	}

	public void setIdRole(String idRole) {
		this.idRole = idRole;
	}

	public String getIdMenu() {
		return idMenu;
	}

	public void setIdMenu(String idMenu) {
		this.idMenu = idMenu;
	}

}
