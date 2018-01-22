package org.simple.util;

import java.util.ArrayList;
import java.util.List;

import org.simple.entity.MenuDO;

public class MenuUtils {
	
	private List<MenuDO> orderList = new ArrayList<MenuDO>();
	private List<MenuDO> waitOrderList = new ArrayList<MenuDO>();
	
	public MenuUtils(List<MenuDO> nodeList) {
		this.waitOrderList = nodeList;
	}

	public List<MenuDO> recursive(String pId) {
		for(MenuDO menu : waitOrderList) {
			if(menu.getIdParent().equals(pId)) {
				orderList.add(menu);
				recursive(menu.getIdMenu());
			}
		}
		return orderList;
	}
}
