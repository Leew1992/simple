package org.simple.test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Test {
	public static void main(String[] args) throws Exception {
		String dateStr = "20180119";
		SimpleDateFormat dateSdf = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat monthSdf = new SimpleDateFormat("yyyyMM");
		Date date = dateSdf.parse(dateStr);
		System.out.println(monthSdf.format(date));
		System.out.println(dateStr.substring(0, dateStr.length()-2));
		
		String dateStr1 = "20171230";
		Calendar cal = Calendar.getInstance();
		Date date1 = dateSdf.parse(dateStr1);
		cal.setTime(date1);
		System.out.println(cal.get(Calendar.WEEK_OF_YEAR));
	}
}
