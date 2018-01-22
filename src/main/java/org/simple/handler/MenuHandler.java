package org.simple.handler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.simple.dto.MenuItem;
import org.simple.dto.TreeNode;
import org.simple.entity.MenuDO;

public class MenuHandler {

	/**
	 * 排序菜单列表
	 */
	public static List<MenuDO> orderMenus(String rootId, List<MenuDO> menuList) {
		List<MenuDO> orderList = new ArrayList<MenuDO>();
		return recursive(rootId, menuList, orderList);
	}

	/**
	 * 递归排序菜单
	 */
	private static List<MenuDO> recursive(String idParent, List<MenuDO> waitOrderList, List<MenuDO> orderList) {
		for(MenuDO menu : waitOrderList) {
			if(menu.getIdParent().equals(idParent)) {
				orderList.add(menu);
				recursive(menu.getIdMenu(), waitOrderList, orderList);
			}
		}
		return orderList;
	}
	
	/**
	 * 格式化菜单
	 */
	public static Map<String, List<MenuItem>> formatOrderedMenus(List<MenuDO> menuList) {
		Map<String, List<MenuItem>> moduleMap = new HashMap<String, List<MenuItem>>();
		String idModule = "";
		String moduleCode = "";
		List<MenuItem> menuItemList = null;
		for(int i = 0; i < menuList.size()-1; i ++) {
			MenuDO currentMenu = menuList.get(i);
			MenuDO nextMenu = menuList.get(i+1);
			// 一级菜单
			if("0".equals(currentMenu.getIdParent())) {
				idModule = currentMenu.getIdMenu();
				moduleCode = currentMenu.getMenuCode();
				menuItemList = new ArrayList<MenuItem>();
			}
			// 二级菜单
			if(idModule.equals(currentMenu.getIdParent())) {
				MenuItem item = new MenuItem();
				item.setMenuName(currentMenu.getMenuName());
				item.setMenuUrl(currentMenu.getMenuUrl());
				menuItemList.add(item);
			}
			// 下个菜单是否是一级菜单
			if("0".equals(nextMenu.getIdParent())) {
				moduleMap.put(moduleCode, menuItemList);
			}
			// 最后一个菜单并且是一级菜单
			if((i == menuList.size() - 2) && ("0".equals(nextMenu.getIdParent()))) {
				idModule = nextMenu.getIdMenu();
				moduleMap.put(moduleCode, menuItemList);
			}
			// 最后一个菜单并且是二级菜单
			if((i == menuList.size() - 2) && (idModule.equals(nextMenu.getIdParent()))) {
				MenuItem item = new MenuItem();
				item.setMenuName(nextMenu.getMenuName());
				item.setMenuUrl(nextMenu.getMenuUrl());
				menuItemList.add(item);
				moduleMap.put(moduleCode, menuItemList);
			}
		}
		return moduleMap;
	}
	
	/**
	 * 生成基于月份的菜单树
	 */
	public static List<TreeNode> generateMonthMenuTree(String startMonth) {
		if(StringUtils.isEmpty(startMonth)) {
			return new ArrayList<TreeNode>();
		}
		Map<String, List<String>> pastedMonthMap = new HashMap<String, List<String>>();
		try {
			pastedMonthMap = getHasPastedMonths(startMonth);
		} catch (Exception e) {
			e.printStackTrace();
		}
		SimpleDateFormat yearSdf = new SimpleDateFormat("yyyy");
		String nowYear = yearSdf.format(new Date());
		List<TreeNode> monthNodeList = new ArrayList<TreeNode>();
		for(String year : pastedMonthMap.keySet()) {
			List<String> monthList = pastedMonthMap.get(year);
			TreeNode yearNode = new TreeNode();
			if(Objects.equals(year, nowYear)) {
				yearNode = createDateNode("0", year, true);				
			} else {
				yearNode = createDateNode("0", year, false);
			}
			monthNodeList.add(yearNode);
			for(String month : monthList) {
				TreeNode monthNode = createDateNode(year, month, false);
				monthNodeList.add(monthNode);
			}
		}
		return monthNodeList;
	}
	
	/**
	 * 获取已经过去的月
	 */
	private static Map<String, List<String>> getHasPastedMonths(String startMonth) throws Exception {
		Map<String, List<String>> pastedMonthMap = new HashMap<String, List<String>>();
		SimpleDateFormat yearSdf = new SimpleDateFormat("yyyy");
		SimpleDateFormat monthSdf = new SimpleDateFormat("yyyyMM");
		String nowMonth = monthSdf.format(new Date());
		Date startDate = monthSdf.parse(startMonth);
		Calendar startCalendar = Calendar.getInstance();
		startCalendar.setTime(startDate);
		String startYearStr = yearSdf.format(startCalendar.getTime());
		String nowYearStr = yearSdf.format(new Date());
		Integer startYearInt = Integer.parseInt(startYearStr);
		Integer nowYearInt = Integer.parseInt(nowYearStr);
		// 小于当前年份
		while(startYearInt < nowYearInt) {
			List<String> monthList = new ArrayList<String>();
			for(int i = 1; i <= 12; i ++) {
				String monthStr = "";
				if(i < 10) {
					monthStr = startYearInt + "0" + i;
				} else {
					monthStr = String.valueOf(startYearInt) + i;
				}
				monthList.add(monthStr);
			}
			pastedMonthMap.put(String.valueOf(startYearInt), monthList);
			startYearInt ++;
		}
		// 等于当前年份
		if(Objects.equals(startYearInt, nowYearInt)) {
			List<String> monthList = new ArrayList<String>();
			for(int i = 1; i <= 12; i ++) {
				String monthStr = "";
				if(i < 10) {
					monthStr = startYearInt + "0" + i;
				} else {
					monthStr = String.valueOf(startYearInt) + i;
				}
				monthList.add(monthStr);
				
				if(Objects.equals(monthStr, nowMonth)) {
					break;
				}
			}
			pastedMonthMap.put(String.valueOf(startYearInt), monthList);
		}
		return pastedMonthMap;
	}
	
	/**
	 * 创建日期节点
	 */
	private static TreeNode createDateNode(String pId, String dateStr, boolean open) {
		TreeNode treeNode = new TreeNode();
		treeNode.setpId(pId);
		treeNode.setId(dateStr);
		treeNode.setName(dateStr);
		treeNode.setDesc(dateStr);
		treeNode.setOpen(open);
		return treeNode;
	}
}
