package com.common.core.util;

import java.util.Date;

import org.apache.commons.lang.time.DateUtils;

public class DateUtil {
	public static Date getDateAfterDays(Date date,int days) {
		return DateUtils.addDays(date, days);
	}
	
	public static Date getDateAfterMonths(Date date,int months) {
		return DateUtils.addMonths(date, months);
	}
}
