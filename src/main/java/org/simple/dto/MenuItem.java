package org.simple.dto;

import java.io.Serializable;

public class MenuItem implements Serializable {
	private static final long serialVersionUID = -162787710450325909L;
	private String menuUrl;
	private String menuName;

	public String getMenuUrl() {
		return menuUrl;
	}

	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

}
