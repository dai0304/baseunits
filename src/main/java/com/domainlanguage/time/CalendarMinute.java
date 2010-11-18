/**
 * Copyright (c) 2005 Domain Language, Inc. (http://domainlanguage.com) This
 * free software is distributed under the "MIT" licence. See file licence.txt.
 * For more information, see http://timeandmoney.sourceforge.net.
 */
package com.domainlanguage.time;

import java.util.Date;
import java.util.TimeZone;

import org.apache.commons.lang.Validate;

/**
 * 特定のカレンダー上の「日時」を表すクラス。
 * 
 * <p>{@link Date}と異なり、分未満（秒以下）の概念を持っていない。また、{@link TimePoint}と異なり、1分間全ての範囲を表し、
 * 特定の瞬間をモデリングしたものではない。</p>
 * 
 * @author daisuke
 */
public class CalendarMinute {
	
	/**
	 * @param aDate 年月日
	 * @param aTime 時分
	 * @return
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 */
	public static CalendarMinute dateAndTimeOfDay(CalendarDate aDate, TimeOfDay aTime) {
		Validate.notNull(aDate);
		Validate.notNull(aTime);
		return new CalendarMinute(aDate, aTime);
	}
	
	public static CalendarMinute dateHourAndMinute(int year, int month, int day, int hour, int minute) {
		return new CalendarMinute(CalendarDate.from(year, month, day), TimeOfDay.hourAndMinute(hour, minute));
	}
	

	private CalendarDate date;
	
	private TimeOfDay time;
	

	/**
	 * Only for use by persistence mapping frameworks
	 * <rant>These methods break encapsulation and we put them in here begrudgingly</rant>
	 */
	CalendarMinute() {
	}
	
	private CalendarMinute(CalendarDate date, TimeOfDay time) {
		Validate.notNull(date);
		Validate.notNull(time);
		this.date = date;
		this.time = time;
	}
	
	public TimePoint asTimePoint(TimeZone timeZone) {
		return TimePoint.at(date.breachEncapsulationOf_year(), date.breachEncapsulationOf_month(),
				date.breachEncapsulationOf_day(), time.getHour(), time.getMinute(), 0, 0, timeZone);
	}
	
	public boolean equals(CalendarMinute another) {
		if (another == null) {
			return false;
		}
		return date.equals(another.date) && time.equals(another.time);
	}
	
	@Override
	public boolean equals(Object anotherObject) {
		if (anotherObject instanceof CalendarMinute == false) {
			return false;
		}
		return equals((CalendarMinute) anotherObject);
	}
	
	@Override
	public int hashCode() {
		return date.hashCode() ^ time.hashCode();
	}
	
	@Override
	public String toString() {
		return date.toString() + " at " + time.toString();
	}
	
	/**
	 * Only for use by persistence mapping frameworks
	 * <rant>These methods break encapsulation and we put them in here begrudgingly</rant>
	 * @return {@link #date}
	 */
	@SuppressWarnings("unused")
	private CalendarDate getForPersistentMapping_Date() { // CHECKSTYLE IGNORE THIS LINE
		return date;
	}
	
	/**
	 * Only for use by persistence mapping frameworks
	 * <rant>These methods break encapsulation and we put them in here begrudgingly</rant>
	 * @return {@link #time}
	 */
	@SuppressWarnings("unused")
	private TimeOfDay getForPersistentMapping_Time() { // CHECKSTYLE IGNORE THIS LINE
		return time;
	}
	
	/**
	 * Only for use by persistence mapping frameworks
	 * <rant>These methods break encapsulation and we put them in here begrudgingly</rant>
	 * @param date {@link #date}
	 */
	@SuppressWarnings("unused")
	private void setForPersistentMapping_Date(CalendarDate date) { // CHECKSTYLE IGNORE THIS LINE
		this.date = date;
	}
	
	/**
	 * Only for use by persistence mapping frameworks
	 * <rant>These methods break encapsulation and we put them in here begrudgingly</rant>
	 * @param time {@link #time}
	 */
	@SuppressWarnings("unused")
	private void setForPersistentMapping_Time(TimeOfDay time) { // CHECKSTYLE IGNORE THIS LINE
		this.time = time;
	}
}
