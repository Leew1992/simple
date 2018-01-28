package org.simple.util;

import java.lang.reflect.Method;
import java.util.Date;

public class DOUtil {
	
	private DOUtil() {}
	
	public static Object mappingValues(Object object, String createdBy, Date createdDate, String updatedBy, Date updatedDate) throws Exception {
	
		Class<? extends Object> clazz = object.getClass();
		
		// 设置创建人
		Method createByMethod = clazz.getMethod("setCreatedBy", String.class);
		createByMethod.invoke(object, createdBy);
		
		// 设置创建日期
		Method createdDateMethod = clazz.getMethod("setCreatedDate", Date.class);
		createdDateMethod.invoke(object, createdDate);
		
		// 设置更新人
		Method updatedByMethod = clazz.getMethod("setUpdatedBy", String.class);
		updatedByMethod.invoke(object, updatedBy);
		
		// 设置更新日期
		Method updatedDateMethod = clazz.getMethod("setUpdatedDate", Date.class);
		updatedDateMethod.invoke(object, updatedDate);
		
		return object;
	}
}	
