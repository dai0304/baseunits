/**
 * Copyright (c) 2005 Domain Language, Inc. (http://domainlanguage.com) This
 * free software is distributed under the "MIT" licence. See file licence.txt.
 * For more information, see http://timeandmoney.sourceforge.net.
 */
package com.domainlanguage.time;

import java.util.Date;
import java.util.TimeZone;

/**
 * 特定の「時分」を表すクラス。
 * 
 * <p>{@link Date}と異なり、日付の概念を持っていない。</p>
 * 
 * @author daisuke
 */
public class TimeOfDay {
	
	private HourOfDay hour;
	
	private MinuteOfHour minute;
	

	public static TimeOfDay hourAndMinute(int hour, int minute) {
		return new TimeOfDay(hour, minute);
	}
	
	private TimeOfDay(int hour, int minute) {
		this.hour = HourOfDay.of(hour);
		this.minute = MinuteOfHour.of(minute);
	}
	
	public CalendarMinute on(CalendarDate date) {
		return CalendarMinute.dateAndTimeOfDay(date, this);
	}
	
	@Override
	public String toString() {
		return hour.toString() + ":" + minute.toString();
	}
	
	@Override
	public boolean equals(Object anotherObject) {
		if (anotherObject instanceof TimeOfDay == false) {
			return false;
		}
		return equals((TimeOfDay) anotherObject);
	}
	
	public boolean equals(TimeOfDay another) {
		if (another == null) {
			return false;
		}
		return hour.equals(another.hour) && minute.equals(another.minute);
	}
	
	@Override
	public int hashCode() {
		return hour.hashCode() ^ minute.hashCode();
	}
	
	public boolean isAfter(TimeOfDay another) {
		return hour.isAfter(another.hour)
				|| hour.equals(another) && minute.isAfter(another.minute);
	}
	
	public boolean isBefore(TimeOfDay another) {
		return hour.isBefore(another.hour)
				|| hour.equals(another) && minute.isBefore(another.minute);
	}
	
	int getHour() {
		return hour.value();
	}
	
	int getMinute() {
		return minute.value();
	}
	
	/**
	 * Only for use by persistence mapping frameworks
	 * <rant>These methods break encapsulation and we put them in here begrudgingly</rant>
	 */
	TimeOfDay() {
	}
	
	/**
	 * Only for use by persistence mapping frameworks
	 * <rant>These methods break encapsulation and we put them in here begrudgingly</rant>
	 * @return value of hour
	 */
	@SuppressWarnings("unused")
	private int getForPersistentMapping_Hour() { // CHECKSTYLE IGNORE THIS LINE
		return hour.value();
	}
	
	/**
	 * Only for use by persistence mapping frameworks
	 * <rant>These methods break encapsulation and we put them in here begrudgingly</rant>
	 * @param hour value of hour
	 */
	@SuppressWarnings("unused")
	private void setForPersistentMapping_Hour(int hour) { // CHECKSTYLE IGNORE THIS LINE
		this.hour = HourOfDay.of(hour);
	}
	
	/**
	 * Only for use by persistence mapping frameworks
	 * <rant>These methods break encapsulation and we put them in here begrudgingly</rant>
	 * @return value of minute
	 */
	@SuppressWarnings("unused")
	private int getForPersistentMapping_Minute() { // CHECKSTYLE IGNORE THIS LINE
		return minute.value();
	}
	
	/**
	 * Only for use by persistence mapping frameworks
	 * <rant>These methods break encapsulation and we put them in here begrudgingly</rant>
	 * @param minute value of minute
	 */
	@SuppressWarnings("unused")
	private void setForPersistentMapping_Minute(int minute) { // CHECKSTYLE IGNORE THIS LINE
		this.minute = MinuteOfHour.of(minute);
	}
	
	public TimePoint asTimePointGiven(CalendarDate date, TimeZone timeZone) {
		CalendarMinute timeOfDayOnDate = on(date);
		return timeOfDayOnDate.asTimePoint(timeZone);
	}
}
