/**
 * Copyright (c) 2004 Domain Language, Inc. (http://domainlanguage.com) This
 * free software is distributed under the "MIT" licence. See file licence.txt.
 * For more information, see http://timeandmoney.sourceforge.net.
 */

package com.domainlanguage.time;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * ある一瞬の時間点を表現するクラス。
 * 
 * @version $Id$
 * @author daisuke
 */
public class TimePoint implements Comparable<TimePoint>, Serializable {
	
	private static final long serialVersionUID = -6111696236461533464L;
	
	private static final TimeZone GMT = TimeZone.getTimeZone("Universal");
	

	/**
	 * 指定した年〜ミリ秒及びタイムゾーンによって表される瞬間を表す{@link TimePoint}インスタンスを生成する。
	 * 
	 * @param year 年
	 * @param month 月(1-12)
	 * @param date 日(1-31)
	 * @param hour 時(0-23)
	 * @param minute 分(0-59)
	 * @param second 秒(0-59)
	 * @param millisecond ミリ秒(0-999)
	 * @param zone タイムゾーン
	 * @return 生成した {@link TimePoint}
	 */
	public static TimePoint at(int year, int month, int date, int hour, int minute, int second, int millisecond, // CHECKSTYLE IGNORE THIS LINE
			TimeZone zone) {
		Calendar calendar = Calendar.getInstance(zone);
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month - 1);
		calendar.set(Calendar.DATE, date);
		calendar.set(Calendar.HOUR_OF_DAY, hour);
		calendar.set(Calendar.MINUTE, minute);
		calendar.set(Calendar.SECOND, second);
		calendar.set(Calendar.MILLISECOND, millisecond);
		return from(calendar);
	}
	
	/**
	 * 指定した年〜秒及びタイムゾーンによって表される瞬間を表す{@link TimePoint}インスタンスを生成する。
	 * 
	 * <p>ミリ秒は{@code 0}とする。</p>
	 * 
	 * @param year 年
	 * @param month 月(1-12)
	 * @param date 日(1-31)
	 * @param hour 時(0-23)
	 * @param minute 分(0-59)
	 * @param second 秒(0-59)
	 * @param zone タイムゾーン
	 * @return 生成した {@link TimePoint}
	 */
	public static TimePoint at(int year, int month, int date, int hour, int minute, int second, TimeZone zone) {
		return at(year, month, date, hour, minute, second, 0, zone);
	}
	
	public static TimePoint at12hr(int year, int month, int date, int hour, String am_pm, int minute, int second,
			int millisecond, TimeZone zone) {
		return at(year, month, date, convertedTo24hour(hour, am_pm), minute, second, millisecond, zone);
	}
	
	/**
	 * 指定した年〜分によって表される瞬間をGMTとして解釈し、{@link TimePoint}インスタンスを生成する。
	 * 
	 * <p>秒及びミリ秒は{@code 0}とする。</p>
	 * 
	 * @param year 年
	 * @param month 月(1-12)
	 * @param date 日(1-31)
	 * @param hour 時(0-23)
	 * @param minute 分(0-59)
	 * @return 生成した {@link TimePoint}
	 */
	public static TimePoint atGMT(int year, int month, int date, int hour, int minute) {
		return atGMT(year, month, date, hour, minute, 0, 0);
	}
	
	/**
	 * 指定した年〜秒によって表される瞬間をGMTとして解釈し、{@link TimePoint}インスタンスを生成する。
	 * 
	 * <p>ミリ秒は{@code 0}とする。</p>
	 * 
	 * @param year 年
	 * @param month 月(1-12)
	 * @param date 日(1-31)
	 * @param hour 時(0-23)
	 * @param minute 分(0-59)
	 * @return 生成した {@link TimePoint}
	 */
	public static TimePoint atGMT(int year, int month, int date, int hour, int minute, int second) {
		return atGMT(year, month, date, hour, minute, second, 0);
	}
	
	/**
	 * 指定した年〜ミリ秒及びタイムゾーンによって表される瞬間をGMTとして解釈し、{@link TimePoint}インスタンスを生成する。
	 * 
	 * @param year 年
	 * @param month 月(1-12)
	 * @param date 日(1-31)
	 * @param hour 時(0-23)
	 * @param minute 分(0-59)
	 * @param second 秒(0-59)
	 * @param millisecond ミリ秒(0-999)
	 * @return 生成した {@link TimePoint}
	 */
	public static TimePoint atGMT(int year, int month, int date, int hour, int minute, int second, int millisecond) {
		return at(year, month, date, hour, minute, second, millisecond, GMT);
	}
	
	/**
	 * 指定した年〜日及びタイムゾーンによって表される瞬間を表す{@link TimePoint}インスタンスを生成する。
	 * 
	 * <p>時〜ミリ秒は{@code 0}とする。</p>
	 * 
	 * @param year 年
	 * @param month 月(1-12)
	 * @param date 日(1-31)
	 * @param zone タイムゾーン
	 * @return 生成した {@link TimePoint}
	 */
	public static TimePoint atMidnight(int year, int month, int date, TimeZone zone) {
		return at(year, month, date, 0, 0, 0, 0, zone);
	}
	
	/**
	 * 指定した年〜日及びタイムゾーンによって表される瞬間をGMTとして解釈し、{@link TimePoint}インスタンスを生成する。
	 * 
	 * <p>時〜ミリ秒は{@code 0}とする。</p>
	 * 
	 * @param year 年
	 * @param month 月(1-12)
	 * @param date 日(1-31)
	 * @return 生成した {@link TimePoint}
	 */
	public static TimePoint atMidnightGMT(int year, int month, int date) {
		return atMidnight(year, month, date, GMT);
	}
	
	public static TimePoint from(Calendar calendar) {
		return from(calendar.getTime());
	}
	
	public static TimePoint from(Date javaDate) {
		return from(javaDate.getTime());
	}
	
	public static TimePoint from(long milliseconds) {
		TimePoint result = new TimePoint(milliseconds);
		//assert FAR_FUTURE == null || result.isBefore(FAR_FUTURE);
		//assert FAR_PAST == null || result.isAfter(FAR_PAST);
		return result;
		
	}
	
	public static TimePoint parseFrom(String dateString, String pattern, TimeZone zone) {
		DateFormat format = new SimpleDateFormat(pattern);
		format.setTimeZone(zone);
		Date date = format.parse(dateString, new ParsePosition(0));
		return from(date);
	}
	
	public static TimePoint parseGMTFrom(String dateString, String pattern) {
		return parseFrom(dateString, pattern, GMT);
	}
	
	private static int convertedTo24hour(int hour, String amPm) {
		int translatedAmPm = "AM".equalsIgnoreCase(amPm) ? 0 : 12;
		translatedAmPm -= (hour == 12) ? 12 : 0;
		return hour + translatedAmPm;
	}
	

	long millisecondsFromEpoc;
	

	/**
	 * Only for use by persistence mapping frameworks
	 * <rant>These methods break encapsulation and we put them in here begrudgingly</rant>
	 */
	TimePoint() {
	}
	
	private TimePoint(long milliseconds) {
		millisecondsFromEpoc = milliseconds;
	}
	
	public Calendar asJavaCalendar() {
		return asJavaCalendar(GMT);
	}
	
	public Calendar asJavaCalendar(TimeZone zone) {
		Calendar result = Calendar.getInstance(zone);
		result.setTime(asJavaUtilDate());
		return result;
	}
	
	public Date asJavaUtilDate() {
		return new Date(millisecondsFromEpoc);
	}
	
	public TimePoint backToMidnight(TimeZone zone) {
		return calendarDate(zone).asTimeInterval(zone).start();
	}
	
	public CalendarDate calendarDate(TimeZone zone) {
		return CalendarDate.from(this, zone);
	}
	
	@Override
	public int compareTo(TimePoint otherPoint) {
		if (this.isBefore(otherPoint)) {
			return -1;
		}
		if (this.isAfter(otherPoint)) {
			return 1;
		}
		return 0;
	}
	
	@Override
	public boolean equals(Object other) {
		return
		//revisit: maybe use: Reflection.equalsOverClassAndNull(this, other)
		(other instanceof TimePoint) && ((TimePoint) other).millisecondsFromEpoc == millisecondsFromEpoc;
	}
	
	@Override
	public int hashCode() {
		return (int) millisecondsFromEpoc;
	}
	
	public boolean isAfter(TimeInterval interval) {
		return interval.isBefore(this);
	}
	
	/**
	 * この時間点が、与えた時間点{@code other}よりも未来である場合{@code true}を返す。
	 * 
	 * <p>同時刻である場合は{@code false}とする。</p>
	 * 
	 * @param other 比較対象時間点
	 * @return この時間点が、与えた時間点{@code other}よりも未来である場合は{@code true}、そうでない場合は{@code false}
	 * @since TODO
	 */
	public boolean isAfter(TimePoint other) {
		return millisecondsFromEpoc > other.millisecondsFromEpoc;
	}
	
	public boolean isBefore(TimeInterval interval) {
		return interval.isAfter(this);
	}
	
	/**
	 * この時間点が、与えた時間点{@code other}よりも過去である場合{@code true}を返す。
	 * 
	 * <p>同時刻である場合は{@code false}とする。</p>
	 * 
	 * @param other 比較対象時間点
	 * @return この時間点が、与えた時間点{@code other}よりも過去である場合は{@code true}、そうでない場合は{@code false}
	 * @since TODO
	 */
	public boolean isBefore(TimePoint other) {
		return millisecondsFromEpoc < other.millisecondsFromEpoc;
	}
	
	public boolean isSameDayAs(TimePoint other, TimeZone zone) {
		return calendarDate(zone).equals(other.calendarDate(zone));
	}
	
	public TimePoint minus(Duration duration) {
		return duration.subtractedFrom(this);
	}
	
	public TimePoint nextDay() {
		return plus(Duration.days(1));
	}
	
	public TimePoint plus(Duration duration) {
		return duration.addedTo(this);
	}
	
	@Override
	public String toString() {
		return asJavaUtilDate().toString(); //for better readability
		//return String.valueOf(millisecondsFromEpoc);
	}
	
	public String toString(String pattern, TimeZone zone) {
		DateFormat format = new SimpleDateFormat(pattern);
		format.setTimeZone(zone);
		return format.format(asJavaUtilDate());
	}
	
	public TimeInterval until(TimePoint end) {
		return TimeInterval.over(this, end);
	}
	
	//Only for use by persistence mapping frameworks
	//<rant>These methods break encapsulation and we put them in here begrudgingly</rant>
	private long getForPersistentMapping_MillisecondsFromEpoc() {
		return millisecondsFromEpoc;
	}
	
	//Only for use by persistence mapping frameworks
	//<rant>These methods break encapsulation and we put them in here begrudgingly</rant>
	private void setForPersistentMapping_MillisecondsFromEpoc(long millisecondsFromEpoc) {
		this.millisecondsFromEpoc = millisecondsFromEpoc;
	}
	
}
