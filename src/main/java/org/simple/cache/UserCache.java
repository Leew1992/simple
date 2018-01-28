package org.simple.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.simple.entity.MenuDO;

/**
 * 用户缓存
 */
public class UserCache {
	
	private static Map<String, List<MenuDO>> menuCache = new HashMap<>();
	
	private UserCache(){}
	
	/**
	 * 保存菜单信息到缓存中
	 */
	public static void storeMenusToCache(String userName, List<MenuDO> menuList) {
		menuCache.put(userName, menuList);
	}
	
	/**
	 * 清除缓存中的菜单信息
	 */
	public static void clearMenusFromCache(String userName) {
		menuCache.remove(userName);
	}

	public static Map<String, List<MenuDO>> getMenuCache() {
		return menuCache;
	}
	
}
