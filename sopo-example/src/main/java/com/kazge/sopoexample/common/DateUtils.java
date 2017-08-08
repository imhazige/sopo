package com.kazge.sopoexample.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class DateUtils extends org.apache.commons.lang.time.DateUtils{
	public static Date parse2Date(String patter, String value)
			throws ParseException {
		return new SimpleDateFormat(patter).parse(value);
	}

	public static Date buildDate(int year, int month, int day) {
		Calendar calendar = new GregorianCalendar(year, month, day);

		return calendar.getTime();
	}

	public static String format(String patter, Date date) {
		return new SimpleDateFormat(patter).format(date);
	}

	public static String format(String patter, int year, int month, int day) {
		return format(patter, buildDate(year, month, day));
	}

	public static int getYear(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		return calendar.get(Calendar.YEAR);
	}

	public static int getMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		
		return calendar.get(Calendar.MONTH);
	}

	public static int getDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		
		return calendar.get(Calendar.DAY_OF_MONTH);
	}
}
