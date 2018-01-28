package org.simple.dto;

import org.simple.entity.RoleDO;

/**
 * 角色信息DTO
 */
public class RoleDTO extends RoleDO {
	private static final long serialVersionUID = -5123013857135943431L;
	// 角色分配
	private Boolean checked;
	// 列表查询
	private String idGroup;

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	public String getIdGroup() {
		return idGroup;
	}

	public void setIdGroup(String idGroup) {
		this.idGroup = idGroup;
	}

}
