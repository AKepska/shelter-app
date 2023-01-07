package com.javafee.shelter.web.service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Utils {
	public Calendar toCalendar(LocalDate localDate) {
		Instant instant = localDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
		Date date = Date.from(instant);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar;
	}

	public Calendar toCalendar(LocalDateTime localDateTime) {
		Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
		Date date = Date.from(instant);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar;
	}

	public Date toDate(LocalDateTime localDateTime) {
		Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
		return Date.from(instant);
	}

	public LocalDateTime toLocalDateTime(Calendar calendar) {
		TimeZone tz = calendar.getTimeZone();
		ZoneId zoneId = tz.toZoneId();
		return LocalDateTime.ofInstant(calendar.toInstant(), zoneId);
	}
}
