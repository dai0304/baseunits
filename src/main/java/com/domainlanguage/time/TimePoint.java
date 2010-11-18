/**
 * Copyright (c) 2004 Domain Language, Inc. (http://domainlanguage.com) This
 * free software is distributed under the "MIT" licence. See file licence.txt.
 * For more information, see http://timeandmoney.sourceforge.net.
 */

package com.domainlanguage.time;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.apache.commons.lang.Validate;

/**
 * ミリ秒精度で、ある時間の一点をあらわすクラス。
 * 
 * @author daisuke
 */
@SuppressWarnings("serial")
public class TimePoint implements Comparable<TimePoint>, Serializable {
	
	private static final TimeZone GMT = TimeZone.getTimeZone("Universal");
	

	/**
	 * 指定したタイムゾーンにおける、指定した日時を表すインスタンスを取得する。
	 * 
	 * @param year 年
	 * @param month 月（1〜12）
	 * @param date 日
	 * @param hour 時
	 * @param minute 分
	 * @param second 秒
	 * @param millisecond ミリ秒
	 * @param zone タイムゾーン
	 * @return {@link TimePoint}
	 */
	public static TimePoint at(int year, int month, int date, int hour, int minute, int second, int millisecond,
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
	
// CREATION METHODS
	
	/**
	 * 指定したタイムゾーンにおける、指定した日時を表すインスタンスを取得する。
	 * 
	 * @param year 年
	 * @param month 月（1〜12）
	 * @param date 日
	 * @param hour 時
	 * @param minute 分
	 * @param second 秒
	 * @param zone タイムゾーン
	 * @return {@link TimePoint}
	 */
	public static TimePoint at(int year, int month, int date, int hour, int minute, int second, TimeZone zone) {
		return at(year, month, date, hour, minute, second, 0, zone);
	}
	
	/**
	 * 指定したタイムゾーンにおける、指定した日時を表すインスタンスを取得する。
	 * 
	 * @param year 年
	 * @param month 月（1〜12）
	 * @param date 日
	 * @param hour 時
	 * @param minute 分
	 * @param zone タイムゾーン
	 * @return {@link TimePoint}
	 */
	public static TimePoint at(int year, int month, int date, int hour, int minute, TimeZone zone) {
		return at(year, month, date, hour, minute, 0, 0, zone);
	}
	
	/**
	 * 世界標準時における、指定した日時を表すインスタンスを取得する。
	 * 
	 * @param year 年
	 * @param month 月（1〜12）
	 * @param date 日
	 * @param hour 時
	 * @param am_pm 午前午後を表す文字列("AM", "PM"など)
	 * @param minute 分
	 * @param second 秒
	 * @param millisecond ミリ秒
	 * @return {@link TimePoint}
	 */
	public static TimePoint at12hr(int year, int month, int date, int hour, String am_pm, int minute, int second,
			int millisecond) {
		return at(year, month, date, convertedTo24hour(hour, am_pm), minute, second, millisecond, GMT);
	}
	
	/**
	 * 指定したタイムゾーンにおける、指定した日時を表すインスタンスを取得する。
	 * 
	 * @param year 年
	 * @param month 月（1〜12）
	 * @param date 日
	 * @param hour 時
	 * @param am_pm 午前午後を表す文字列("AM", "PM"など)
	 * @param minute 分
	 * @param second 秒
	 * @param millisecond ミリ秒
	 * @param zone タイムゾーン
	 * @return {@link TimePoint}
	 */
	public static TimePoint at12hr(int year, int month, int date, int hour, String am_pm, int minute, int second,
			int millisecond, TimeZone zone) {
		return at(year, month, date, convertedTo24hour(hour, am_pm), minute, second, millisecond, zone);
	}
	
	/**
	 * 世界標準時における、指定した日時を表すインスタンスを取得する。
	 * 
	 * @param year 年
	 * @param month 月（1〜12）
	 * @param date 日
	 * @param hour 時
	 * @param minute 分
	 * @return {@link TimePoint}
	 */
	public static TimePoint atGMT(int year, int month, int date, int hour, int minute) {
		return atGMT(year, month, date, hour, minute, 0, 0);
	}
	
	/**
	 * 世界標準時における、指定した日時を表すインスタンスを取得する。
	 * 
	 * @param year 年
	 * @param month 月（1〜12）
	 * @param date 日
	 * @param hour 時
	 * @param minute 分
	 * @param second 秒
	 * @return {@link TimePoint}
	 */
	public static TimePoint atGMT(int year, int month, int date, int hour, int minute, int second) {
		return atGMT(year, month, date, hour, minute, second, 0);
	}
	
	/**
	 * 世界標準時における、指定した日時を表すインスタンスを取得する。
	 * 
	 * @param year 年
	 * @param month 月（1〜12）
	 * @param date 日
	 * @param hour 時
	 * @param minute 分
	 * @param second 秒
	 * @param millisecond ミリ秒
	 * @return {@link TimePoint}
	 */
	public static TimePoint atGMT(int year, int month, int date, int hour, int minute, int second, int millisecond) {
		return at(year, month, date, hour, minute, second, millisecond, GMT);
	}
	
	/**
	 * 指定したタイムゾーンにおける、指定した日付の午前0時（深夜）を表すインスタンスを取得する。
	 * 
	 * @param year 年
	 * @param month 月（1〜12）
	 * @param date 日
	 * @param zone タイムゾーン
	 * @return {@link TimePoint}
	 */
	public static TimePoint atMidnight(int year, int month, int date, TimeZone zone) {
		return at(year, month, date, 0, 0, 0, 0, zone);
	}
	
	/**
	 * 世界標準時における、指定した日付の午前0時（深夜）を表すインスタンスを取得する。
	 * 
	 * @param year 年
	 * @param month 月（1〜12）
	 * @param date 日
	 * @return {@link TimePoint}
	 */
	public static TimePoint atMidnightGMT(int year, int month, int date) {
		return atMidnight(year, month, date, GMT);
	}
	
	/**
	 * {@link Calendar}を{@link TimePoint}に変換する。
	 * 
	 * @param calendar 元となる日時情報を表す {@link Calendar}インスタンス
	 * @return {@link TimePoint}
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 */
	public static TimePoint from(Calendar calendar) {
		Validate.notNull(calendar);
		return from(calendar.getTime());
	}
	
	/**
	 * {@link Date}を{@link TimePoint}に変換する。
	 * 
	 * @param javaDate 元となる日時情報を表す {@link Date}インスタンス
	 * @return {@link TimePoint}
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 */
	public static TimePoint from(Date javaDate) {
		Validate.notNull(javaDate);
		return from(javaDate.getTime());
	}
	
	/**
	 * エポックからの経過ミリ秒を {@link TimePoint} に変換する。
	 * 
	 * @param milliseconds エポックからの経過ミリ秒
	 * @return {@link TimePoint}
	 */
	public static TimePoint from(long milliseconds) {
		TimePoint result = new TimePoint(milliseconds);
		//assert FAR_FUTURE == null || result.isBefore(FAR_FUTURE);
		//assert FAR_PAST == null || result.isAfter(FAR_PAST);
		return result;
	}
	
	/**
	 * 日時を表す文字列を、指定したパターンで指定したタイムゾーンとして解析し、その日時を表す {@link TimePoint}を返す。
	 * 
	 * @param dateString 日時を表す文字列
	 * @param pattern 解析パターン
	 * @param zone タイムゾーン
	 * @return {@link TimePoint}
	 * @throws ParseException 文字列の解析に失敗した場合
	 */
	public static TimePoint parseFrom(String dateString, String pattern, TimeZone zone) throws ParseException {
		DateFormat format = new SimpleDateFormat(pattern);
		format.setTimeZone(zone);
		Date date = format.parse(dateString);
		return from(date);
	}
	
	/**
	 * 日時を表す文字列を、指定したパターンで世界標準時として解析し、その日時を表す {@link TimePoint}を返す。
	 * 
	 * @param dateString 日時を表す文字列
	 * @param pattern 解析パターン
	 * @return {@link TimePoint}
	 * @throws ParseException 文字列の解析に失敗した場合
	 */
	public static TimePoint parseGMTFrom(String dateString, String pattern) throws ParseException {
		return parseFrom(dateString, pattern, GMT);
	}
	
	private static int convertedTo24hour(int hour, String am_pm) {
		int translatedAmPm = "AM".equalsIgnoreCase(am_pm) ? 0 : 12;
		translatedAmPm -= (hour == 12) ? 12 : 0;
		return hour + translatedAmPm;
	}
	

	/** エポックからの経過ミリ秒 */
	long millisecondsFromEpoc;
	

	/**
	 * Only for use by persistence mapping frameworks
	 * <rant>These methods break encapsulation and we put them in here begrudgingly</rant>
	 */
	TimePoint() {
	}
	
// BEHAVIORAL METHODS
	
	private TimePoint(long milliseconds) {
		millisecondsFromEpoc = milliseconds;
	}
	
	/**
	 * このオブジェクトが表現する瞬間をGMTとして扱い、{@link java.util.Calendar}型として取得する。
	 * 
	 * @return {@link java.util.Calendar}
	 */
	public Calendar asJavaCalendar() {
		return asJavaCalendar(GMT);
	}
	
	/**
	 * このオブジェクトが表現する瞬間を指定したタイムゾーンとして扱い、{@link java.util.Calendar}型として取得する。
	 * 
	 * @param zone タイムゾーン
	 * @return {@link java.util.Calendar}
	 */
	public Calendar asJavaCalendar(TimeZone zone) {
		Calendar result = Calendar.getInstance(zone);
		result.setTime(asJavaUtilDate());
		return result;
	}
	
	/**
	 * このオブジェクトが表現する瞬間を、{@link java.util.Date}型として取得する。
	 * 
	 * @return {@link java.util.Date}
	 */
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
	
	/**
	 * このオブジェクトと、与えたオブジェクト {@code other}の同一性を検証する。
	 * 
	 * <p>与えたオブジェクトが {@code null} ではなく、かつ {@link TimePoint}型であった場合、
	 * 同じ日時を指している場合は{@code true}、そうでない場合は{@code false}を返す。</p>
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object other) {
		//revisit: maybe use: Reflection.equalsOverClassAndNull(this, other)
		return (other instanceof TimePoint) && ((TimePoint) other).millisecondsFromEpoc == millisecondsFromEpoc;
	}
	
	@Override
	public int hashCode() {
		return (int) (millisecondsFromEpoc ^ (millisecondsFromEpoc >>> 32));
	}
	
	public boolean isAfter(TimeInterval interval) {
		return interval.isBefore(this);
	}
	
	/**
	 * 指定した日時 {@code other} が、このオブジェクトが表現する日時よりも未来であるかどうかを検証する。
	 * 
	 * <p>同一日時である場合は {@code false} を返す。</p>
	 * 
	 * @param other 対象日時
	 * @return 未来である場合は{@code true}、そうでない場合は{@code false}
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 */
	public boolean isAfter(TimePoint other) {
		Validate.notNull(other);
		return millisecondsFromEpoc > other.millisecondsFromEpoc;
	}
	
	public boolean isBefore(TimeInterval interval) {
		return interval.isAfter(this);
	}
	
	/**
	 * 指定した日時 {@code other} が、このオブジェクトが表現する日時よりも過去であるかどうかを検証する。
	 * 
	 * <p>同一日時である場合は {@code false} を返す。</p>
	 * 
	 * @param other 対象日時
	 * @return 過去である場合は{@code true}、そうでない場合は{@code false}
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 */
	public boolean isBefore(TimePoint other) {
		Validate.notNull(other);
		return millisecondsFromEpoc < other.millisecondsFromEpoc;
	}
	
	public boolean isSameDayAs(TimePoint other, TimeZone zone) {
		return calendarDate(zone).equals(other.calendarDate(zone));
	}
	
// CONVENIENCE METHODS
// (Responsibility lies elsewhere, but language is more fluid with a method here.)
	
	/**
	 * この日時の、指定した時間の長さ分過去の日時を取得する。
	 * 
	 * @param duration 時間の長さ
	 * @return 過去の日時
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 */
	public TimePoint minus(Duration duration) {
		Validate.notNull(duration);
		return duration.subtractedFrom(this);
	}
	
	/**
	 * このオブジェクトが表現する瞬間の、ちょうど1日後を取得する。
	 * 
	 * <p>日内の時間は変化しない。</p>
	 * 
	 * @return 1日後
	 */
	public TimePoint nextDay() {
		return plus(Duration.days(1));
	}
	
	/**
	 * この日時から、指定した時間の長さ分未来の日時を取得する。
	 * 
	 * @param duration 時間の長さ
	 * @return 未来の日時
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 */
	public TimePoint plus(Duration duration) {
		Validate.notNull(duration);
		return duration.addedTo(this);
	}
	
	/**
	 * この瞬間の文字列表現を取得する。
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return asJavaUtilDate().toString();
	}
	
	/**
	 * この瞬間を、指定したパターンで整形し、その文字列表現を取得する。
	 * 
	 * @param pattern パターン
	 * @param zone タイムゾーン
	 * @return 文字列表現
	 */
	public String toString(String pattern, TimeZone zone) {
		DateFormat format = new SimpleDateFormat(pattern);
		format.setTimeZone(zone);
		return format.format(asJavaUtilDate());
	}
	
	public TimeInterval until(TimePoint end) {
		return TimeInterval.over(this, end);
	}
	
	/**
	 * Only for use by persistence mapping frameworks
	 * <rant>These methods break encapsulation and we put them in here begrudgingly</rant>
	 * @return {@link #millisecondsFromEpoc}
	 */
	@SuppressWarnings("unused")
	private long getForPersistentMapping_MillisecondsFromEpoc() { // CHECKSTYLE IGNORE THIS LINE
		return millisecondsFromEpoc;
	}
	
	/**
	 * Only for use by persistence mapping frameworks
	 * <rant>These methods break encapsulation and we put them in here begrudgingly</rant>
	 * @param millisecondsFromEpoc {@link #millisecondsFromEpoc}
	 */
	@SuppressWarnings("unused")
	private void setForPersistentMapping_MillisecondsFromEpoc(long millisecondsFromEpoc) { // CHECKSTYLE IGNORE THIS LINE
		this.millisecondsFromEpoc = millisecondsFromEpoc;
	}
	
}
