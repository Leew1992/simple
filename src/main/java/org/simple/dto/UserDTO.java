package org.simple.dto;

import org.simple.entity.UserDO;

/**
 * 用户信息DTO
 */
public class UserDTO extends UserDO {
	private static final long serialVersionUID = -8764860999367616106L;
	// 列表查询
	private String idGroup;
	// 分配角色
	private String idRoles;
	// 保存用户
	private String idGroups;
	// 注册用户
	private String verifyCode;
	
	public String getIdGroup() {
		return idGroup;
	}

	public void setIdGroup(String idGroup) {
		this.idGroup = idGroup;
	}

	public String getIdRoles() {
		return idRoles;
	}

	public void setIdRoles(String idRoles) {
		this.idRoles = idRoles;
	}

	public String getIdGroups() {
		return idGroups;
	}

	public void setIdGroups(String idGroups) {
		this.idGroups = idGroups;
	}

	public String getVerifyCode() {
		return verifyCode;
	}

	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}
}
