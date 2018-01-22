package org.simple.entity;


/**
 * 用户角色信息
 */
public class UserRoleDO extends BaseDO {
	private static final long serialVersionUID = 4554396301589613052L;
	private String idUserRole;
	private String idUser;
	private String idRole;

	public String getIdUserRole() {
		return idUserRole;
	}

	public void setIdUserRole(String idUserRole) {
		this.idUserRole = idUserRole;
	}

	public String getIdUser() {
		return idUser;
	}

	public void setIdUser(String idUser) {
		this.idUser = idUser;
	}

	public String getIdRole() {
		return idRole;
	}

	public void setIdRole(String idRole) {
		this.idRole = idRole;
	}

}
