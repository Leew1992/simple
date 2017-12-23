package org.simple.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.commons.lang.StringUtils;

/**
 * JavaBean转换器
 */
public class BeanUtils {

	/**
	 * 将Bean中非空值的属性存入到Map中
	 */
	public static Map<String, Object> beanToMap(Object obj) {
		Map<String, Object> params = new HashMap<String, Object>(0);
		try {
			PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean();
			PropertyDescriptor[] descriptors = propertyUtilsBean
					.getPropertyDescriptors(obj);
			for (int i = 0; i < descriptors.length; i++) {
				String name = descriptors[i].getName();
				Object value = propertyUtilsBean.getNestedProperty(obj, name);
				if (!StringUtils.equals(name, "class") && value != null
						&& !"NULL".equals(value) && !"".equals(value)) {
					params.put(name, value);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return params;
	}

	/**
	 * 将destination中非空值的属性替换origin中的属性
	 */
	public static <T> void mergeObject(T origin, T destination) {
		if (origin == null || destination == null) {
			return;
		}

		if (!origin.getClass().equals(destination.getClass())) {
			return;
		}

		Field[] fields = origin.getClass().getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			try {
				fields[i].setAccessible(true);
				Object value = fields[i].get(origin);
				if (null != value) {
					fields[i].set(destination, value);
				}
				fields[i].setAccessible(false);
			} catch (Exception e) {

			}
		}
	}
}
