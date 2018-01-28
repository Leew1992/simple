package org.simple.util;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.simple.exception.ServiceException;

public class BeanUtil {
	
	/**
	 * 将Beans转换成Map
	 */
	public static Map<String, Object> convertBeansToMap(Object bean1, Object bean2) {
		Map<String, Object> paramMap = new HashMap<>();
		try {
			BeanUtils.populate(bean1, paramMap);
			BeanUtils.populate(bean2, paramMap);
		} catch (IllegalAccessException e) {
			throw new ServiceException(e);
		} catch (InvocationTargetException e) {
			throw new ServiceException(e);
		}
		return paramMap;
	}
}
