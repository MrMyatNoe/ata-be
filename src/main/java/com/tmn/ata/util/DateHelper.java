package com.tmn.ata.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DateHelper {

	public static Date parseDate(String dateStr) {
		try {
			return new SimpleDateFormat("M/d/yyyy HH:mm:ss").parse(dateStr);
		} catch (ParseException e) {
			log.error("Error parsing date: " + dateStr, e);
			return null;
		}
	}
	
	public static String DateToFormat(Date timestamp) {
		SimpleDateFormat outputFormat = new SimpleDateFormat("M/d/yyyy HH:mm:ss");
		return outputFormat.format(timestamp);
	}
}
