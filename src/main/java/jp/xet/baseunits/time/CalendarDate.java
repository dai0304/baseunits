/*
 * Copyright 2011-2013 Daisuke Miyamoto. (http://d.hatena.ne.jp/daisuke-m)
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

import com.google.common.base.Preconditions;

/**
 * 「暦日」を表すクラス。
 * 
 * <p>暦日とは、カレンダー上の特定の「年月日」のことで、
 * 暦月における日によって指定される。例えば「2012年11月27日」や「1978年3月4日」等のことである。</p>
 * 
 * <p>{@link Date}と異なり、時間の概念を持っておらず、タイムゾーンの概念もない。
 * また、{@link TimePoint}と異なり、その日1日間全ての範囲を表すクラスであり、特定の瞬間のモデルではない。</p>
 * 
 * @author daisuke
 * @since 1.0
 * @see DayOfMonth
 */
@SuppressWarnings("serial")
public class CalendarDate implements Comparable<CalendarDate>, Serializable {
	
	/**
	 * エポック(1970年1月1日)の暦日
	 * 
	 * @since 2.0
	 */
	public static final CalendarDate EPOCH_DATE = CalendarDate.from(1970, 1, 1);
	
	private static final String DEFAULT_PATTERN = "yyyy-MM-dd";
	
	
	/**
	 * 指定した暦月及び日で表される暦週を返す。
	 * 
	 * @param calendarMonth 暦月
	 * @param day 日
	 * @return {@link CalendarDate}
	 * @throws IllegalArgumentException 引数{@code day}が{@code yearMonth}の月に存在しない場合
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	public static CalendarDate from(CalendarMonth calendarMonth, DayOfMonth day) {
		return new CalendarDate(calendarMonth, day);
	}
	
	/**
	 * {@link Date}とタイムゾーンから、暦日を返す。
	 * 
	 * @param date {@link Date}
	 * @param zone タイムゾーン
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @return {@link CalendarDate}
	 * @since 1.0
	 */
	public static CalendarDate from(Date date, TimeZone zone) {
		Preconditions.checkNotNull(date);
		Preconditions.checkNotNull(zone);
		Calendar calendar = TimePoint.from(date).asJavaCalendar(zone);
		return from(calendar);
	}
	
	/**
	 * 指定した年月日の各値で表される暦週を返す。
	 * 
	 * @param year 西暦年をあらわす数
	 * @param month 月をあらわす正数（1〜12）
	 * @param day 日をあらわす正数（1〜31）
	 * @return {@link CalendarDate}
	 * @throws IllegalArgumentException 引数{@code month}が1〜12の範囲ではない場合
	 * @throws IllegalArgumentException 引数{@code day}が1〜31の範囲ではない場合
	 * @throws IllegalArgumentException 引数{@code day}が{@code yearMonth}の月に存在しない場合
	 * @since 1.0
	 */
	public static CalendarDate from(int year, int month, int day) {
		return new CalendarDate(CalendarMonth.from(year, month), DayOfMonth.valueOf(day));
	}
	
	/**
	 * 指定した年月日で表される暦週を返す。
	 * 
	 * @param year 年
	 * @param month 月
	 * @param day 日
	 * @return {@link CalendarDate}
	 * @throws IllegalArgumentException 引数{@code day}が{@code year}年の{@code month}の月に存在しない場合
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	public static CalendarDate from(int year, MonthOfYear month, DayOfMonth day) {
		return from(CalendarMonth.from(year, month), day);
	}
	
	/**
	 * 指定したタイムゾーンにおいて、指定した{@link TimePoint}が属する暦日を返す。
	 * 
	 * @param timePoint 瞬間
	 * @param zone タイムゾーン
	 * @return {@link CalendarDate}
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	public static CalendarDate from(TimePoint timePoint, TimeZone zone) {
		Preconditions.checkNotNull(timePoint);
		Preconditions.checkNotNull(zone);
		Calendar calendar = timePoint.asJavaCalendar();
		calendar.setTimeZone(zone);
		return from(calendar);
	}
	
	/**
	 * Returns the greater of two {@link CalendarDate} values.
	 * If the arguments have the same value, the result is that same value.
	 *
	 * @param a an argument.
	 * @param b another argument.
	 * @return the larger of {@code a} and {@code b}.
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @since 2.6
	 */
	public static CalendarDate max(CalendarDate a, CalendarDate b) {
		Preconditions.checkNotNull(a);
		Preconditions.checkNotNull(b);
		if (a.isAfter(b)) {
			return a;
		}
		return b;
	}
	
	/**
	 * Returns the smaller of two {@link CalendarDate} values.
	 * If the arguments have the same value, the result is that same value.
	 *
	 * @param a an argument.
	 * @param b another argument.
	 * @return the smaller of {@code a} and {@code b}.
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @since 2.6
	 */
	public static CalendarDate min(CalendarDate a, CalendarDate b) {
		Preconditions.checkNotNull(a);
		Preconditions.checkNotNull(b);
		if (a.isBefore(b)) {
			return a;
		}
		return b;
	}
	
	/**
	 * 指定した暦日を表す文字列を {@value #DEFAULT_PATTERN} として解析し、暦日を返す。
	 * 
	 * @param dateString 暦日を表す文字列 
	 * @return {@link CalendarDate}
	 * @throws ParseException 文字列の解析に失敗した場合 
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @since 2.10
	 */
	public static CalendarDate parse(String dateString) throws ParseException {
		return parse(dateString, DEFAULT_PATTERN);
	}
	
	/**
	 * 指定した暦日を表す文字列を解析し、暦日を返す。
	 * 
	 * @param dateString 暦日を表す文字列 
	 * @param pattern 解析パターン文字列（{@link SimpleDateFormat}参照）
	 * @return {@link CalendarDate}
	 * @throws ParseException 文字列の解析に失敗した場合 
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	public static CalendarDate parse(String dateString, String pattern) throws ParseException {
		Preconditions.checkNotNull(dateString);
		Preconditions.checkNotNull(pattern);
		TimeZone arbitraryZone = TimeZone.getTimeZone("Universal");
		// Any timezone works, as long as the same one is used throughout.
		TimePoint point = TimePoint.parse(dateString, pattern, arbitraryZone);
		return CalendarDate.from(point, arbitraryZone);
	}
	
	// calendarがTimeZone情報を含むことが分かりづらいので、現状は敢えて非公開にしている。
	static CalendarDate from(Calendar calendar) {
		// Use timezone already set in calendar.
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1; // baseunits counts January as 1
		int date = calendar.get(Calendar.DATE);
		return from(year, month, date);
	}
	
	
	// TODO rename to calendarMonth on v3.0
	private final CalendarMonth yearMonth;
	
	private final DayOfMonth day;
	
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param calendarMonth 暦月
	 * @param day 日
	 * @throws IllegalArgumentException 引数{@code day}が{@code yearMonth}の月に存在しない場合
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 */
	CalendarDate(CalendarMonth calendarMonth, DayOfMonth day) {
		Preconditions.checkNotNull(calendarMonth);
		Preconditions.checkNotNull(day);
		Preconditions.checkArgument(day.isApplyable(calendarMonth));
		yearMonth = calendarMonth;
		this.day = day;
	}
	
	/**
	 * この暦日を含む暦月を返す。
	 * 
	 * @return この暦日を含む暦月
	 * @since 1.0
	 */
	public CalendarMonth asCalendarMonth() {
		return yearMonth;
	}
	
	// comment-out by daisuke
//	public CalendarDate start() {
//		return this;
//	}
//
//	public CalendarDate end() {
//		return this;
//	}
	
	/**
	 * この暦日を含む暦週を返す。
	 * 
	 * @return 暦週
	 * @since 2.0
	 */
	public CalendarWeek asCalendarWeek() {
		return CalendarWeek.from(this);
	}
	
	/**
	 * この暦日の午前0時を表す{@link Date}を返す。
	 * 
	 * @param timeZone タイムゾーン
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @return この暦日の午前0時を表す{@link Date}
	 */
	public Date asJavaUtilDate(TimeZone timeZone) {
		Preconditions.checkNotNull(timeZone);
		TimePoint tp = at(TimeOfDay.MIDNIGHT, timeZone);
		return tp.asJavaUtilDate();
	}
	
	/**
	 * この暦日を含む暦月を表す期間を取得する。
	 * 
	 * @return この暦日を含む暦月を表す期間
	 * @since 1.0
	 */
	public CalendarInterval asMonthInterval() {
		return CalendarInterval.month(yearMonth);
	}
	
	/**
	 * この暦日の午前0時から丸一日の期間を返す。
	 * 
	 * <p>生成する期間の開始{@link TimePoint}は区間に含み（閉じている）、終了{@link TimePoint}は区間に含まない（開いている）半開区間を生成する。</p>
	 * 
	 * @param zone タイムゾーン
	 * @return この暦日の午前0時から丸一日の期間
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	public TimePointInterval asTimePointInterval(TimeZone zone) {
		Preconditions.checkNotNull(zone);
		return TimePointInterval.startingFrom(startAsTimePoint(zone), true, Duration.days(1), false);
	}
	
	/**
	 * この暦日を含む暦年を表す期間を返す。
	 * 
	 * @return この暦日を含む暦年を表す期間
	 * @since 1.0
	 */
	public CalendarInterval asYearInterval() {
		return CalendarInterval.year(yearMonth.getYear());
	}
	
	/**
	 * この暦日で、引数{@code timeOfDay}で表す時を表す{@link TimePoint}を返す。
	 * 
	 * @param timeOfDay 時
	 * @param timeZone タイムゾーン
	 * @return {@link TimePoint}
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @since 2.0
	 */
	public TimePoint at(TimeOfDay timeOfDay, TimeZone timeZone) {
		Preconditions.checkNotNull(timeOfDay);
		Preconditions.checkNotNull(timeZone);
		return TimePoint.from(this, timeOfDay, timeZone);
	}
	
	/**
	 * この暦日の、指定したタイムゾーンにおける深夜0時の{@link TimePoint}を返す。
	 * 
	 * @param timeZone タイムゾーン
	 * @return {@link TimePoint}
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @since 2.3
	 */
	public TimePoint atMidnight(TimeZone timeZone) {
		return TimePoint.atMidnight(this, timeZone);
	}
	
	/**
	 * この暦日の、UTCにおける深夜0時の{@link TimePoint}を返す。
	 * 
	 * @return {@link TimePoint}
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @since 2.3
	 */
	public TimePoint atMidnightUTC() {
		return atMidnight(TimePoint.UTC);
	}
	
	/**
	 * 暦日同士の比較を行う。
	 * 
	 * <p>相対的に過去である方を「小さい」と判断する。</p>
	 * 
	 * @param other 比較対象
	 * @return {@link Comparable#compareTo(Object)}に準じる
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	@Override
	public int compareTo(CalendarDate other) {
		if (other == null) {
			throw new NullPointerException();
		}
		if (isBefore(other)) {
			return -1;
		}
		if (isAfter(other)) {
			return 1;
		}
		return 0;
	}
	
	/**
	 * この暦日の曜日を返す。
	 * 
	 * @return 曜日
	 * @since 1.0
	 */
	public DayOfWeek dayOfWeek() {
		Calendar calendar = asJavaCalendarUniversalZoneMidnight();
		return DayOfWeek.valueOf(calendar.get(Calendar.DAY_OF_WEEK));
	}
	
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
		CalendarDate other = (CalendarDate) obj;
		if (day.equals(other.day) == false) {
			return false;
		}
		if (yearMonth.equals(other.yearMonth) == false) {
			return false;
		}
		return true;
	}
	
	/**
	 * {@code this}と引数{@code d}をそれぞれ誕生日と基準日とし、満年齢を返す。
	 * 
	 * @param d 基準日または誕生日
	 * @return 満年齢
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @since 2.6
	 */
	public long getAge(CalendarDate d) {
		Preconditions.checkNotNull(d);
		CalendarDate date = max(this, d);
		CalendarDate birth = min(this, d);
		long age = date.getYear() - birth.getYear();
		if (date.getMonthOfYear().isBefore(birth.getMonthOfYear())
				|| date.getDayOfMonth().isBefore(birth.getDayOfMonth())) {
			age--;
		}
		return age;
	}
	
	/**
	 * この暦日が属する日を返す。
	 * 
	 * @return 日
	 * @since 1.0
	 */
	public DayOfMonth getDayOfMonth() {
		return day;
	}
	
	/**
	 * この暦日が属する月を返す。
	 * 
	 * @return 月
	 * @since 1.0
	 */
	public MonthOfYear getMonthOfYear() {
		return yearMonth.getMonthOfYear();
	}
	
	/**
	 * この暦日が属する年を返す。
	 * 
	 * @return 年
	 * @since 1.0
	 */
	public int getYear() {
		return yearMonth.getYear();
	}
	
	/**
	 * この暦日が属する暦月を返す。
	 * 
	 * @return 暦月
	 * @since 1.0
	 */
	public CalendarMonth getYearMonth() {
		return yearMonth;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + day.hashCode();
		result = prime * result + yearMonth.hashCode();
		return result;
	}
	
	/**
	 * この暦日が {@code other} よりも未来であるかどうかを検証する。
	 * 
	 * <p>{@code other} が {@code null} である場合は {@code false} を返す。
	 * また、同一である場合は {@code false} を返す。</p>
	 * 
	 * @param other 比較対象暦日
	 * @return 未来である場合は{@code true}、そうでない場合は{@code false}
	 * @since 1.0
	 */
	public boolean isAfter(CalendarDate other) {
		if (other == null) {
			return false;
		}
		return isBefore(other) == false && equals(other) == false;
	}
	
	/**
	 * この暦日が {@code other} よりも未来であるかどうかを検証する。
	 * 
	 * <p>{@code other} が {@code null} である場合は {@code false} を返す。
	 * また、同一である場合は {@code true} を返す。</p>
	 * 
	 * @param other 比較対象暦日
	 * @return 未来または同一である場合は{@code true}、そうでない場合は{@code false}
	 * @since 2.6
	 */
	public boolean isAfterOrEquals(CalendarDate other) {
		if (other == null) {
			return false;
		}
		return isBefore(other) == false;
	}
	
	/**
	 * この暦日が {@code other} よりも過去であるかどうかを検証する。
	 * 
	 * <p>{@code other} が {@code null} である場合は {@code false} を返す。
	 * また、同一である場合は {@code false} を返す。</p>
	 * 
	 * @param other 比較対象暦日
	 * @return 過去である場合は{@code true}、そうでない場合は{@code false}
	 * @since 1.0
	 */
	public boolean isBefore(CalendarDate other) {
		if (other == null) {
			return false;
		}
		if (yearMonth.isBefore(other.yearMonth)) {
			return true;
		}
		if (yearMonth.isAfter(other.yearMonth)) {
			return false;
		}
		return day.isBefore(other.day);
	}
	
	/**
	 * この暦日が {@code other} よりも過去であるかどうかを検証する。
	 * 
	 * <p>{@code other} が {@code null} である場合は {@code false} を返す。
	 * また、同一である場合は {@code true} を返す。</p>
	 * 
	 * @param other 比較対象暦日
	 * @return 過去または同一である場合は{@code true}、そうでない場合は{@code false}
	 * @since 2.6
	 */
	public boolean isBeforeOrEquals(CalendarDate other) {
		if (other == null) {
			return false;
		}
		if (yearMonth.isBefore(other.yearMonth)) {
			return true;
		}
		if (yearMonth.isAfter(other.yearMonth)) {
			return false;
		}
		return day.isBeforeOrEquals(other.day);
	}
	
	/**
	 * この暦日から、指定した長さの時間を引いた、過去の暦日を取得する。
	 * 
	 * <p>引数の長さの単位が "日" 未満である場合は、元の暦日をそのまま返す。<p>
	 * 
	 * @param length 時間の長さ
	 * @return 未来の暦日
	 * @since 1.0
	 */
	public CalendarDate minus(Duration length) {
		return length.subtractedFrom(this);
	}
	
	/**
	 * この暦日の翌日に当たる暦日を返す。
	 * 
	 * @return 翌日にあたる暦日
	 * @see #previousDay()
	 * @see #plus(Duration)
	 * @since 1.0
	 */
	public CalendarDate nextDay() {
		return plusDays(1);
	}
	
	/**
	 * この暦日に、指定した長さの時間を加えた、未来の暦日を取得する。
	 * 
	 * <p>引数の長さの単位が "日" 未満である場合は、元の暦日をそのまま返す。<p>
	 * 
	 * @param length 時間の長さ
	 * @return 未来の暦日
	 * @since 1.0
	 */
	public CalendarDate plus(Duration length) {
		return length.addedTo(this);
	}
	
	/**
	 * この暦日の {@code increment} 日後に当たる暦日を返す。
	 * 
	 * <p> {@code increment}に負数を与えてもよい。</p>
	 * 
	 * @param increment 加える日数
	 * @return {@code increment} 日後に当たる暦日
	 * @since 1.0
	 */
	public CalendarDate plusDays(int increment) {
		Calendar calendar = asJavaCalendarUniversalZoneMidnight();
		calendar.add(Calendar.DATE, increment);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DATE); // CHECKSTYLE IGNORE THIS LINE
		return CalendarDate.from(year, month, day);
	}
	
	/**
	 * この暦日の {@code increment} ヶ月後に当たる暦日を返す。
	 * 
	 * <p> {@code increment}に負数を与えてもよい。</p>
	 * 
	 * @param increment 加える月数
	 * @return {@code increment} ヶ月後に当たる暦日
	 * @since 1.0
	 */
	public CalendarDate plusMonths(int increment) {
		Calendar calendar = asJavaCalendarUniversalZoneMidnight();
		calendar.add(Calendar.MONTH, increment);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DATE); // CHECKSTYLE IGNORE THIS LINE
		return CalendarDate.from(year, month, day);
	}
	
	/**
	 * この暦日の前日に当たる暦日を返す。
	 * 
	 * @return 前日に当たる暦日
	 * @see #nextDay()
	 * @see #minus(Duration)
	 * @since 1.0
	 */
	public CalendarDate previousDay() {
		return plusDays(-1);
	}
	
	/**
	 * この暦日の午前0時を表現する{@link TimePoint}を返す。
	 * 
	 * @param zone タイムゾーン
	 * @return この暦日の午前0時を表現する{@link TimePoint}
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	public TimePoint startAsTimePoint(TimeZone zone) {
		Preconditions.checkNotNull(zone);
		return TimePoint.atMidnight(this, zone);
	}
	
	/**
	 * この暦日を開始暦日とし、{@code otherDate} を終了暦日とする期間を返す。
	 * 
	 * @param otherDate 終了日
	 * @return 期間
	 * @since 1.0
	 */
	public CalendarInterval through(CalendarDate otherDate) {
		return CalendarInterval.inclusive(this, otherDate);
	}
	
	/**
	 * この暦日の文字列表現を取得する。
	 * 
	 * <p>{@link SimpleDateFormat}の仕様に基づく {@code "yyyy-MM-dd"}のパターンで整形する。</p>
	 * 
	 * @see java.lang.Object#toString()
	 * @since 1.0
	 */
	@Override
	public String toString() {
		return toString(DEFAULT_PATTERN); //default for console
	}
	
	/**
	 * この暦日を、指定したパターンで整形し、その文字列表現を取得する。
	 * 
	 * @param pattern {@link SimpleDateFormat}に基づくパターン
	 * @return 整形済み時間文字列
	 * @since 1.0
	 */
	public String toString(String pattern) {
		return toString(pattern, Locale.getDefault());
	}
	
	/**
	 * この暦日を、指定したパターンで整形し、その文字列表現を取得する。
	 * 
	 * @param pattern {@link SimpleDateFormat}に基づくパターン
	 * @param locale ロケール
	 * @return 整形済み時間文字列
	 * @since 2.0
	 */
	public String toString(String pattern, Locale locale) {
		// Any timezone works, as long as the same one is used throughout.
		TimeZone arbitraryZone = TimePoint.UTC;
		TimePoint point = startAsTimePoint(arbitraryZone);
		return point.toString(pattern, locale, arbitraryZone);
	}
	
	Calendar asJavaCalendarUniversalZoneMidnight() {
		Calendar calendar = CalendarUtil.newCalendar();
		calendar.set(Calendar.YEAR, yearMonth.getYear());
		calendar.set(Calendar.MONTH, yearMonth.getMonthOfYear().value - 1);
		calendar.set(Calendar.DATE, day.value);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar;
	}
}
