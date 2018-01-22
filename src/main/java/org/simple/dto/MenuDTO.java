package org.simple.dto;

import org.simple.entity.MenuDO;

/**
 * 菜单信息DTO
 */
public class MenuDTO extends MenuDO {
	private static final long serialVersionUID = 1383937543548750863L;
	private String parentMenuName;
	private String idSystem;
	private String checked;

	public String getParentMenuName() {
		return parentMenuName;
	}

	public void setParentMenuName(String parentMenuName) {
		this.parentMenuName = parentMenuName;
	}

	public String getIdSystem() {
		return idSystem;
	}

	public void setIdSystem(String idSystem) {
		this.idSystem = idSystem;
	}

	public String getChecked() {
		return checked;
	}

	public void setChecked(String checked) {
		this.checked = checked;
	}

}
