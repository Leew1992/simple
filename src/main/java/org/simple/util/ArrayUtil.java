package org.simple.util;

/**
 * 数组操作工具类
 */
public class ArrayUtil {
	
	private ArrayUtil() {}

	/**
	 * 转换String[]为Long[]
	 */
	public static Long[] toLongArr(String[] arr) {
		if (arr == null || arr.length == 0) {
			return null;
		}
		Long[] lrr = new Long[arr.length];
		for (int i = 0; i < arr.length; i++) {
			lrr[i] = Long.parseLong(arr[i]);
		}
		return lrr;
	}

	/**
	 * ｛转换String[]为Long[]｝
	 */
	public static Short[] toShortArr(String[] arr) {
		if (arr == null || arr.length == 0) {
			return null;
		}
		Short[] lrr = new Short[arr.length];
		for (int i = 0; i < arr.length; i++) {
			lrr[i] = Short.parseShort(arr[i]);
		}
		return lrr;
	}

}
