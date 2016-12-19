package com.onedirect.app.appproperties.util;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

public class DateHelper {
	
	public static final int CURRENT_YEAR = Calendar.getInstance().get(Calendar.YEAR);
	
	public static Date getCurrentDateTime(){
		Calendar cal = Calendar.getInstance();
		return cal.getTime();
	}
	
	// converts number of days to milli seconds
	public static long convertDaysToMillis(long days) {
		  return days * 24 * 60 * 60 * 1000;
	}
	
	public static Date getPastDateTimeHour(Date fromDate, int hours) throws Exception{
		Date futreDate= new Date(fromDate.getTime()-hours*60*60*1000 );
		return futreDate;
	}
	
	public static Date setDayStartTimeInDate(Calendar cal) {
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		return cal.getTime();
	}
	
	//convert IST into GMT by subtracting 5:30 hrs
	/*public static Date changeISTToGMT(Date date) throws ParseException{
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return new Date(c.getTime().getTime() - Constants.IST_TO_GMT_CONVERSION_CONSTANT );
	}*/
	
	
	public static Date getPreviousDate(Date date, int previousDays) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, -previousDays);
		return cal.getTime();
	}
	
	/*public static void main(String[] args ){
		Date dat = new Date();
		System.out.println(getPreviousDate(dat, 3));
	}*/
	
}
