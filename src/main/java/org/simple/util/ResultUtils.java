package org.simple.util;

import org.simple.dto.Page;
import org.simple.dto.PageDTO;

import com.alibaba.fastjson.JSON;

/**
 * 响应消息工具类
 */
public class ResultUtils {

	@SuppressWarnings("rawtypes")
	public static String pageToJson(Page page) {
		return "{\"total\":\"" + page.getTotalCount() + "\", \"result\":" + JSON.toJSONString(page.getResult())  + 
			   ", \"pageNumber\":\"" + page.getPageNumber() + "\"}";
	}
	
	@SuppressWarnings("rawtypes")
	public static PageDTO pageToPageDto(Page page) {
		PageDTO dto = new PageDTO();
		dto.setTotal(10);
		dto.setRows(page.getResult());
		return dto;
	}
}
