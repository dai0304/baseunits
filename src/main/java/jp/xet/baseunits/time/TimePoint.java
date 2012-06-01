/*
 * Copyright 2011-2012 Daisuke Miyamoto. (http://d.hatena.ne.jp/daisuke-m)
 * Copyright 2010-2011 TRICREO, Inc. (http://tricreo.jp/)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 * ----
 * Copyright (c) 2004 Domain Language, Inc. (http://domainlanguage.com) This
 * free software is distributed under the "MIT" licence.
 * For more information, see http://timeandmoney.sourceforge.net.
 */
package jp.xet.baseunits.time;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import jp.xet.baseunits.time.HourOfDay.Meridian;

import org.apache.commons.lang.Validate;

/**
 * ミリ秒精度で、ある時間の一点をあらわすクラス。
 * 
 * <p>タイムゾーン情報を含んでいる。つまり、時差のある複数の場所で、同時に「現在」を取得した場合、相互を{@link #equals(Object)}で
 * 評価した時、結果は{@code true}となる。</p>
 * 
 * @author daisuke
 * @since 1.0
 */
@SuppressWarnings("serial")
public class TimePoint implements Comparable<TimePoint>, Serializable {
	
	/** エポック */
	public static final TimePoint EPOCH = TimePoint.from(0);
	
	static final TimeZone UTC = TimeZone.getTimeZone("Universal");
	
	
	/**
	 * 指定したタイムゾーンにおける、指定した日時を表すインスタンスを取得する。
	 * 
	 * @param yearMonth 年月
	 * @param date 日
	 * @param hour 時
	 * @param minute 分
	 * @param second 秒
	 * @param millisecond ミリ秒
	 * @param zone タイムゾーン
	 * @return {@link TimePoint}
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	public static TimePoint at(CalendarMonth yearMonth, DayOfMonth date, int hour,
			int minute, int second, int millisecond, TimeZone zone) {
		Validate.notNull(yearMonth);
		Validate.notNull(date);
		Validate.notNull(zone);
		return at(yearMonth.getYear(), yearMonth.getMonthOfYear().value,
				date.value, hour, minute, second, millisecond, zone);
	}
	
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
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	public static TimePoint at(int year, int month, int date, int hour, int minute, int second, int millisecond, // CHECKSTYLE IGNORE THIS LINE
			TimeZone zone) {
		Validate.notNull(zone);
		Calendar calendar = CalendarUtil.newCalendar(zone);
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month - 1);
		calendar.set(Calendar.DATE, date);
		calendar.set(Calendar.HOUR_OF_DAY, hour);
		calendar.set(Calendar.MINUTE, minute);
		calendar.set(Calendar.SECOND, second);
		calendar.set(Calendar.MILLISECOND, millisecond);
		return valueOf(calendar);
	}
	
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
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	public static TimePoint at(int year, int month, int date, int hour, int minute, int second, TimeZone zone) {
		Validate.notNull(zone);
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
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	public static TimePoint at(int year, int month, int date, int hour, int minute, TimeZone zone) {
		Validate.notNull(zone);
		return at(year, month, date, hour, minute, 0, 0, zone);
	}
	
	/**
	 * 指定したタイムゾーンにおける、指定した日時を表すインスタンスを取得する。
	 * 
	 * @param year 年
	 * @param month 月
	 * @param date 日
	 * @param hour 時
	 * @param minute 分
	 * @param second 秒
	 * @param millisecond ミリ秒
	 * @param zone タイムゾーン
	 * @return {@link TimePoint}
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	public static TimePoint at(int year, MonthOfYear month, DayOfMonth date, int hour, int minute, int second, // CHECKSTYLE IGNORE THIS LINE
			int millisecond, TimeZone zone) {
		Validate.notNull(month);
		Validate.notNull(date);
		Validate.notNull(zone);
		return at(year, month.value, date.value, hour, minute, second, millisecond, zone);
	}
	
	/**
	 * 協定世界時における、指定した日時を表すインスタンスを取得する。
	 * 
	 * @param year 年
	 * @param month 月（1〜12）
	 * @param date 日
	 * @param hour 時
	 * @param meridian 午前/午後
	 * @param minute 分
	 * @param second 秒
	 * @param millisecond ミリ秒
	 * @return {@link TimePoint}
	 * @throws IllegalArgumentException 引数{@code hour}の値が0〜11の範囲ではない場合
	 * @throws IllegalArgumentException 引数{@code meridian}の値が {@code "AM"} または {@code "PM"} ではない場合
	 * @since 2.0
	 */
	public static TimePoint at12hr(int year, int month, int date, int hour, Meridian meridian, int minute, int second, // CHECKSTYLE IGNORE THIS LINE
			int millisecond) {
		return at(year, month, date, HourOfDay.convertTo24hour(hour, meridian), minute, second, millisecond, UTC);
	}
	
	/**
	 * 指定したタイムゾーンにおける、指定した日時を表すインスタンスを取得する。
	 * 
	 * @param year 年
	 * @param month 月（1〜12）
	 * @param date 日
	 * @param hour 時
	 * @param meridian 午前/午後
	 * @param minute 分
	 * @param second 秒
	 * @param millisecond ミリ秒
	 * @param zone タイムゾーン
	 * @return {@link TimePoint}
	 * @throws IllegalArgumentException 引数{@code hour}の値が0〜11の範囲ではない場合
	 * @throws IllegalArgumentException 引数{@code zone}または{@code meridian}に{@code null}を与えた場合
	 * @since 1.0
	 */
	public static TimePoint at12hr(int year, int month, int date, int hour, Meridian meridian, int minute, int second, // CHECKSTYLE IGNORE THIS LINE
			int millisecond, TimeZone zone) {
		Validate.notNull(zone);
		return at(year, month, date, HourOfDay.convertTo24hour(hour, meridian), minute, second, millisecond, zone);
	}
	
	/**
	 * 指定したタイムゾーンにおける、指定した日時の午前0時（深夜）を表すインスタンスを取得する。
	 * 
	 * @param calendarDate 日付
	 * @param zone タイムゾーン
	 * @return {@link TimePoint}
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	public static TimePoint atMidnight(CalendarDate calendarDate, TimeZone zone) {
		Validate.notNull(calendarDate);
		Validate.notNull(zone);
		return at(calendarDate.asCalendarMonth(), calendarDate.getDayOfMonth(), 0, 0, 0, 0, zone);
	}
	
	/**
	 * 指定したタイムゾーンにおける、指定した日付の午前0時（深夜）を表すインスタンスを取得する。
	 * 
	 * @param year 年
	 * @param month 月（1〜12）
	 * @param date 日
	 * @param zone タイムゾーン
	 * @return {@link TimePoint}
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	public static TimePoint atMidnight(int year, int month, int date, TimeZone zone) {
		Validate.notNull(zone);
		return at(year, month, date, 0, 0, 0, 0, zone);
	}
	
	/**
	 * 協定世界時における、指定した日付の午前0時（深夜）を表すインスタンスを取得する。
	 * 
	 * @param year 年
	 * @param month 月（1〜12）
	 * @param date 日
	 * @return {@link TimePoint}
	 * @since 1.0
	 */
	public static TimePoint atMidnightUTC(int year, int month, int date) {
		return atMidnight(year, month, date, UTC);
	}
	
	/**
	 * 協定世界時における、指定した日時を表すインスタンスを取得する。
	 * 
	 * @param year 年
	 * @param month 月（1〜12）
	 * @param date 日
	 * @param hour 時
	 * @param minute 分
	 * @return {@link TimePoint}
	 * @since 1.0
	 */
	public static TimePoint atUTC(int year, int month, int date, int hour, int minute) {
		return atUTC(year, month, date, hour, minute, 0, 0);
	}
	
	/**
	 * 協定世界時における、指定した日時を表すインスタンスを取得する。
	 * 
	 * @param year 年
	 * @param month 月（1〜12）
	 * @param date 日
	 * @param hour 時
	 * @param minute 分
	 * @param second 秒
	 * @return {@link TimePoint}
	 * @since 1.0
	 */
	public static TimePoint atUTC(int year, int month, int date, int hour, int minute, int second) {
		return atUTC(year, month, date, hour, minute, second, 0);
	}
	
	/**
	 * 協定世界時における、指定した日時を表すインスタンスを取得する。
	 * 
	 * @param year 年
	 * @param month 月（1〜12）
	 * @param date 日
	 * @param hour 時
	 * @param minute 分
	 * @param second 秒
	 * @param millisecond ミリ秒
	 * @return {@link TimePoint}
	 * @since 1.0
	 */
	public static TimePoint atUTC(int year, int month, int date, int hour, int minute, int second, int millisecond) {
		return at(year, month, date, hour, minute, second, millisecond, UTC);
	}
	
	/**
	 * 指定したタイムゾーンにおける、指定した日時を表すインスタンスを取得する。
	 * 
	 * @param date 日付
	 * @param time 時間
	 * @param zone タイムゾーン
	 * @return {@link TimePoint}
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	public static TimePoint from(CalendarDate date, TimeOfDay time, TimeZone zone) {
		Validate.notNull(date);
		Validate.notNull(time);
		Validate.notNull(zone);
		TimePoint tp = date.startAsTimePoint(zone);
		tp = tp.plus(time.toDuration());
		return tp;
	}
	
	/**
	 * {@link Date}を{@link TimePoint}に変換する。
	 * 
	 * @param javaDate 元となる日時情報を表す {@link Date}インスタンス
	 * @return {@link TimePoint}
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @since 1.0
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
	 * @since 1.0
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
	 * @param dateTimeString 日時を表す文字列
	 * @param pattern 解析パターン（{@link SimpleDateFormat}参照）
	 * @param zone タイムゾーン
	 * @return {@link TimePoint}
	 * @throws ParseException 文字列の解析に失敗した場合
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	public static TimePoint parse(String dateTimeString, String pattern, TimeZone zone) throws ParseException {
		Validate.notNull(dateTimeString);
		Validate.notNull(pattern);
		Validate.notNull(zone);
		SimpleDateFormat format = CalendarUtil.newSimpleDateFormat(pattern, zone);
		Date date = format.parse(dateTimeString);
		return from(date);
	}
	
	/**
	 * 日時を表す文字列を、指定したパターンで協定世界時として解析し、その日時を表す {@link TimePoint}を返す。
	 * 
	 * @param dateString 日時を表す文字列
	 * @param pattern 解析パターン（{@link SimpleDateFormat}参照）
	 * @return {@link TimePoint}
	 * @throws ParseException 文字列の解析に失敗した場合
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	public static TimePoint parseUTCFrom(String dateString, String pattern) throws ParseException {
		Validate.notNull(dateString);
		Validate.notNull(pattern);
		return parse(dateString, pattern, UTC);
	}
	
	/**
	 * {@link Calendar}を{@link TimePoint}に変換する。
	 * 
	 * @param calendar 元となる日時情報を表す {@link Calendar}インスタンス
	 * @return {@link TimePoint}
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	public static TimePoint valueOf(Calendar calendar) {
		Validate.notNull(calendar);
		return from(calendar.getTime());
	}
	
	
	/** エポックからの経過ミリ秒 */
	final long millisecondsFromEpoch;
	
	
	private TimePoint(long millisecondsFromEpoc) {
		millisecondsFromEpoch = millisecondsFromEpoc;
	}
	
	/**
	 * このオブジェクトが表現する瞬間をUTCとして扱い、{@link Calendar}型として取得する。
	 * 
	 * @return {@link Calendar}
	 * @since 1.0
	 */
	public Calendar asJavaCalendar() {
		return asJavaCalendar(UTC);
	}
	
	/**
	 * このオブジェクトが表現する瞬間を指定したタイムゾーンとして扱い、{@link Calendar}型として取得する。
	 * 
	 * @param zone タイムゾーン
	 * @return {@link Calendar}
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	public Calendar asJavaCalendar(TimeZone zone) {
		Validate.notNull(zone);
		Calendar calendar = CalendarUtil.newCalendar(zone);
		calendar.setTimeInMillis(millisecondsFromEpoch);
		return calendar;
	}
	
	/**
	 * このオブジェクトが表現する瞬間を、{@link Date}型として取得する。
	 * 
	 * @return {@link Date}
	 * @since 1.0
	 */
	public Date asJavaUtilDate() {
		return new Date(millisecondsFromEpoch);
	}
	
	/**
	 * この瞬間を、指定したタイムゾーンにおける「時分」として返す。
	 * 
	 * @param zone タイムゾーン
	 * @return 時分
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	public TimeOfDay asTimeOfDay(TimeZone zone) {
		Validate.notNull(zone);
		Calendar calendar = asJavaCalendar(zone);
		return TimeOfDay.from(calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE),
				calendar.get(Calendar.SECOND), calendar.get(Calendar.MILLISECOND));
	}
	
	/**
	 * この瞬間を、「時分」として返す。
	 * 
	 * @return 時分
	 * @since 2.0
	 */
	public TimePointOfDay asTimePointOfDay() {
		long t = millisecondsFromEpoch % TimeUnitConversionFactor.millisecondsPerDay.value;
		return TimePointOfDay.from(t);
	}
	
	/**
	 * このインスタンスが表現する瞬間の、指定したタイムゾーンにおける日付における午前0時（深夜）の瞬間を表す {@link TimePoint}を取得する。
	 * 
	 * @param zone タイムゾーン
	 * @return 午前0時（深夜）の瞬間を表す {@link TimePoint}
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	public TimePoint backToMidnight(TimeZone zone) {
		Validate.notNull(zone);
		return calendarDate(zone).asTimePointInterval(zone).start();
	}
	
	/**
	 * このインスタンスが表現する瞬間の、指定したタイムゾーンにおける日付を取得する。
	 * 
	 * @param zone タイムゾーン
	 * @return 日付
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	public CalendarDate calendarDate(TimeZone zone) {
		Validate.notNull(zone);
		return CalendarDate.from(this, zone);
	}
	
	/**
	 * 瞬間同士の比較を行う。
	 * 
	 * <p>相対的に過去である方を「小さい」と判断する。</p>
	 * 
	 * @param otherPoint 比較対象
	 * @return {@link Comparable#compareTo(Object)}に準じる
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	@Override
	public int compareTo(TimePoint otherPoint) {
		if (otherPoint == null) {
			throw new NullPointerException();
		}
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
	 * @since 1.0
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		TimePoint other = (TimePoint) obj;
		if (millisecondsFromEpoch != other.millisecondsFromEpoch) {
			return false;
		}
		return true;
	}
	
	@Override
	public int hashCode() {
		return (int) (millisecondsFromEpoch ^ (millisecondsFromEpoch >>> 32)); // CHECKSTYLE IGNORE THIS LINE;
	}
	
	/**
	 * 指定した瞬間 {@code other} が、このオブジェクトが表現する日時よりも未来であるかどうかを検証する。
	 * 
	 * <p>同一日時である場合は {@code false} を返す。</p>
	 * 
	 * @param other 対象日時
	 * @return 未来である場合は{@code true}、そうでない場合は{@code false}
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	public boolean isAfter(TimePoint other) {
		Validate.notNull(other);
		return millisecondsFromEpoch > other.millisecondsFromEpoch;
	}
	
	/**
	 * 指定した瞬間 {@code other} が、このオブジェクトが表現する日時よりも未来であるかどうかを検証する。
	 * 
	 * <p>同一日時である場合は {@code false} を返す。</p>
	 * 
	 * @param other 対象日時
	 * @return 未来である場合は{@code true}、そうでない場合は{@code false}
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @since 2.3
	 */
	public boolean isAfterOrEquals(TimePoint other) {
		Validate.notNull(other);
		return millisecondsFromEpoch >= other.millisecondsFromEpoch;
	}
	
	/**
	 * このインスタンスがあらわす瞬間が、指定した期間の終了後に位置するかどうか調べる。
	 * 
	 * @param interval 基準期間
	 * @return 期間の終了後に位置する場合は{@code true}、そうでない場合は{@code false}
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	public boolean isAfter(TimePointInterval interval) {
		Validate.notNull(interval);
		return interval.isBefore(this);
	}
	
	/**
	 * 指定した瞬間 {@code other} が、このオブジェクトが表現する日時よりも過去であるかどうかを検証する。
	 * 
	 * <p>同一日時である場合は {@code false} を返す。</p>
	 * 
	 * @param other 対象日時
	 * @return 過去である場合は{@code true}、そうでない場合は{@code false}
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	public boolean isBefore(TimePoint other) {
		Validate.notNull(other);
		return millisecondsFromEpoch < other.millisecondsFromEpoch;
	}
	
	/**
	 * 指定した瞬間 {@code other} が、このオブジェクトが表現する日時よりも過去であるかどうかを検証する。
	 * 
	 * <p>同一日時である場合は {@code true} を返す。</p>
	 * 
	 * @param other 対象日時
	 * @return 過去である場合は{@code true}、そうでない場合は{@code false}
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @since 2.3
	 */
	public boolean isBeforeOrEquals(TimePoint other) {
		Validate.notNull(other);
		return millisecondsFromEpoch <= other.millisecondsFromEpoch;
	}
	
	/**
	 * このインスタンスがあらわす瞬間が、指定した期間の開始前に位置するかどうか調べる。
	 * 
	 * @param interval 基準期間
	 * @return 期間の開始前に位置する場合は{@code true}、そうでない場合は{@code false}
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	public boolean isBefore(TimePointInterval interval) {
		Validate.notNull(interval);
		return interval.isAfter(this);
	}
	
	/**
	 * 指定したタイムゾーンにおいて、このインスタンスが表現する瞬間と指定した瞬間{@code other}の年月日が等価であるかを調べる。
	 * 
	 * @param other 対象瞬間
	 * @param zone タイムゾーン
	 * @return 等価である場合は{@code true}、そうでない場合は{@code false}
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	public boolean isSameDayAs(TimePoint other, TimeZone zone) {
		Validate.notNull(other);
		Validate.notNull(zone);
		return calendarDate(zone).equals(other.calendarDate(zone));
	}
	
	/**
	 * この日時の、指定した時間の長さ分過去の日時を取得する。
	 * 
	 * @param duration 時間の長さ
	 * @return 過去の日時
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @since 1.0
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
	 * @since 1.0
	 */
	public TimePoint nextDay() {
		return plus(Duration.days(1));
	}
	
	/**
	 * この瞬間から、指定した時間の長さ分未来の瞬間を取得する。
	 * 
	 * @param duration 時間の長さ
	 * @return 未来の日時
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	public TimePoint plus(Duration duration) {
		Validate.notNull(duration);
		return duration.addedTo(this);
	}
	
	/**
	 * この瞬間をエポックミリ秒に変換して返す。
	 * 
	 * @return エポックミリ秒
	 * @since 1.3
	 */
	public long toEpochMillisec() {
		return millisecondsFromEpoch;
	}
	
	/**
	 * この瞬間をエポック秒に変換して返す。ミリ秒以下は切り捨てる。
	 * 
	 * @return エポック秒
	 * @since 1.3
	 */
	public long toEpochSec() {
		return millisecondsFromEpoch / TimeUnitConversionFactor.millisecondsPerSecond.value;
	}
	
	/**
	 * この瞬間の文字列表現を取得する。
	 * 
	 * @see java.lang.Object#toString()
	 * @since 1.0
	 */
	@Override
	public String toString() {
		return asJavaUtilDate().toString();
	}
	
	/**
	 * この瞬間を、指定したパターンで整形し、その文字列表現を取得する。
	 * 
	 * @param pattern {@link SimpleDateFormat}に基づくパターン
	 * @param locale ロケール
	 * @param zone タイムゾーン
	 * @return 整形済み時間文字列
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @since 2.0
	 */
	public String toString(String pattern, Locale locale, TimeZone zone) {
		Validate.notNull(pattern);
		Validate.notNull(locale);
		Validate.notNull(zone);
		SimpleDateFormat df = CalendarUtil.newSimpleDateFormat(pattern, locale, zone);
		return df.format(asJavaUtilDate());
	}
	
	/**
	 * この瞬間を、指定したパターンで整形し、その文字列表現を取得する。
	 * 
	 * @param pattern {@link SimpleDateFormat}に基づくパターン
	 * @param zone タイムゾーン
	 * @return 整形済み時間文字列
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	public String toString(String pattern, TimeZone zone) {
		Validate.notNull(pattern);
		Validate.notNull(zone);
		return toString(pattern, Locale.getDefault(), zone);
	}
	
	/**
	 * このインスタンスがあらわす瞬間を開始瞬間、{@code end}を終了瞬間とする、期間を返す。
	 * 
	 * <p>生成する期間の開始日時は期間に含み（閉じている）、終了日時は期間に含まない（開いている）半開区間を生成する。</p>
	 * 
	 * @param end 終了日時（上側限界値）. {@code null}の場合は、限界がないことを表す
	 * @return {@link TimePointInterval}
	 * @since 1.0
	 */
	public TimePointInterval until(TimePoint end) {
		return TimePointInterval.over(this, end);
	}
}
