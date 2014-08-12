package com.common.core.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;

public class DateUtil {
	private static final DateFormat DATEFORMAT = new SimpleDateFormat("yyyy/MM/dd");
	private static final DateFormat DATEFORMAT1 = new SimpleDateFormat("yyyy-MM-dd");
	
	public static Date getDateAfterDays(Date date,int days) {
		return DateUtils.addDays(date, days);
	}
	
	public static Date getDateAfterMonths(Date date,int months) {
		return DateUtils.addMonths(date, months);
	}
	
	public static Date getDateByStr(String dateStr) {
		if(org.apache.commons.lang3.StringUtils.isBlank(dateStr))
			return null;
		try {
			return DATEFORMAT1.parse(dateStr);
		} catch (ParseException e) {
			return null;
		}
	}
	
	public static String getFormatDate(Date date) {
		return DATEFORMAT1.format(date);
	}
}
