package org.simple.entity;


/**
 * 系统菜单信息
 */
public class SystemMenuDO extends BaseDO {
	private static final long serialVersionUID = 7454631278652173715L;
	private String idSystemMenu;
	private String idSystem;
	private String idMenu;

	public String getIdSystemMenu() {
		return idSystemMenu;
	}

	public void setIdSystemMenu(String idSystemMenu) {
		this.idSystemMenu = idSystemMenu;
	}

	public String getIdSystem() {
		return idSystem;
	}

	public void setIdSystem(String idSystem) {
		this.idSystem = idSystem;
	}

	public String getIdMenu() {
		return idMenu;
	}

	public void setIdMenu(String idMenu) {
		this.idMenu = idMenu;
	}

}
