/*
 * Copyright 2010-2015 Miyamoto Daisuke.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package jp.xet.baseunits.time;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import jp.xet.baseunits.util.TimeZones;

import com.google.common.base.Preconditions;

/**
 * 「暦月」を表すクラス。
 * 
 * <p>暦月とは、カレンダー上の特定の「年月」のことで、
 * 暦年における月によって指定される。例えば「2012年11月」や「1978年3月」等のことである。</p>
 * 
 * <p>{@link java.util.Date}と異なり、月未満（日以下）の概念を持っていない。また、{@link TimePoint}と異なり、
 * その月1ヶ月間全ての範囲を表すクラスであり、特定の瞬間のモデルではない。</p>
 * 
 * @author daisuke
 * @since 1.0
 * @see MonthOfYear
 */
@SuppressWarnings("serial")
public class CalendarMonth implements Comparable<CalendarMonth>, Serializable {
	
	/**
	 * 指定した年月から暦月を返す。
	 * 
	 * @param year 西暦年をあらわす数
	 * @param month 月をあらわす正数（1〜12）
	 * @return {@link CalendarDate}
	 * @throws IllegalArgumentException 引数{@code month}が1〜12の範囲ではない場合
	 * @since 1.0
	 */
	public static CalendarMonth from(int year, int month) {
		return new CalendarMonth(year, MonthOfYear.valueOf(month));
	}
	
	/**
	 * 指定した年月の値で表される暦月を返す。
	 * 
	 * @param year 年
	 * @param month 月
	 * @return {@link CalendarMonth}
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	public static CalendarMonth from(int year, MonthOfYear month) {
		return new CalendarMonth(year, month);
	}
	
	/**
	 * 指定したタイムゾーンにおける、{@link TimePoint}が属する暦月を返す。
	 * 
	 * @param timePoint 瞬間
	 * @param zone タイムゾーン
	 * @return {@link CalendarDate}
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	public static CalendarMonth from(TimePoint timePoint, TimeZone zone) {
		Preconditions.checkNotNull(timePoint);
		Preconditions.checkNotNull(zone);
		Calendar calendar = timePoint.asJavaCalendar();
		calendar.setTimeZone(zone);
		return CalendarMonth.from(calendar);
	}
	
	/**
	 * 指定した暦月を表す文字列を解析し、暦月を返す。
	 * 
	 * @param dateString 暦月を表す文字列
	 * @param pattern 解析パターン文字列（{@link SimpleDateFormat}参照）
	 * @return {@link CalendarMonth}
	 * @throws ParseException 文字列の解析に失敗した場合
	 * @since 1.0
	 */
	public static CalendarMonth parse(String dateString, String pattern) throws ParseException {
		//Any timezone works, as long as the same one is used throughout.
		TimeZone arbitraryZone = TimeZones.UNIVERSAL;
		TimePoint point = TimePoint.parse(dateString, pattern, arbitraryZone);
		return CalendarMonth.from(point, arbitraryZone);
	}
	
	static CalendarMonth from(Calendar calendar) { // CHECKSTYLE IGNORE THIS LINE
		// Use timezone already set in calendar.
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1; // baseunits counts January as 1
		return CalendarMonth.from(year, month);
	}
	
	
	final int year;
	
	final MonthOfYear month;
	
	
	CalendarMonth(int year, MonthOfYear month) {
		Preconditions.checkNotNull(month);
		this.year = year;
		this.month = month;
	}
	
	/**
	 * この暦月の初日から月末日までの、期間を生成する。
	 * 
	 * <p>生成する期間の開始暦日と終了暦日は期間に含む（閉じている）開区間を生成する。</p>
	 * 
	 * @return この暦月の初日からその月末日までを表現する期間
	 * @since 1.0
	 */
	public CalendarInterval asCalendarInterval() {
		return CalendarInterval.month(year, month);
	}
	
	/**
	 * 指定したタイムゾーンにおける、この暦月の初日深夜0時の {@link TimePoint} を返す。
	 * 
	 * @param timeZone タイムゾーン
	 * @return {@link TimePoint}
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	public TimePoint asTimePoint(TimeZone timeZone) {
		Preconditions.checkNotNull(timeZone);
		return TimePoint.at(year, month, DayOfMonth.valueOf(1), 0, 0, 0, 0, timeZone);
	}
	
	/**
	 * この暦月を含む暦年の、元旦からその大晦日までの期間を生成する。
	 * 
	 * <p>生成する期間の開始暦日と終了暦日は期間に含む（閉じている）開区間を生成する。</p>
	 * 
	 * @return この暦月を含む暦年の、元旦からその大晦日までの期間
	 * @since 1.0
	 */
	public CalendarInterval asYearInterval() {
		return CalendarInterval.year(year);
	}
	
	/**
	 * この暦月における、引数{@code day}で表す暦日を返す。
	 * 
	 * @param day 日（1〜31）
	 * @return この暦月における、引数{@code day}で表す暦日
	 * @throws IllegalArgumentException 引数{@code day}がこの暦月内に存在しない場合
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	public CalendarDate at(DayOfMonth day) {
		return CalendarDate.from(year, month, day);
	}
	
	/**
	 * 暦月同士の比較を行う。
	 * 
	 * <p>相対的に過去である方を「小さい」と判断する。</p>
	 * 
	 * @param other 比較対象
	 * @return {@link Comparable#compareTo(Object)}に準じる
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	@Override
	public int compareTo(CalendarMonth other) {
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
		CalendarMonth other = (CalendarMonth) obj;
		if (month.equals(other.month) == false) {
			return false;
		}
		if (year != other.year) {
			return false;
		}
		return true;
	}
	
	/**
	 * 月初日の暦日を取得する。
	 * 
	 * @return {@link DayOfMonth}
	 * @since 2.4
	 * @see #getLastDay()
	 */
	public CalendarDate getFirstDay() {
		return at(DayOfMonth.MIN);
	}
	
	/**
	 * 月末日の暦日を取得する。
	 * 
	 * @return {@link DayOfMonth}
	 * @since 1.0
	 * @see #getFirstDay()
	 */
	public CalendarDate getLastDay() {
		return at(getLastDayOfMonth());
	}
	
	/**
	 * 月末の日を取得する。
	 * 
	 * @return {@link DayOfMonth}
	 * @since 1.0
	 */
	public DayOfMonth getLastDayOfMonth() {
		return month.getLastDayOfThisMonth(year);
	}
	
	/**
	 * この暦月の月を取得する。
	 * 
	 * @return 月
	 * @since 2.0
	 */
	public MonthOfYear getMonthOfYear() {
		return month;
	}
	
	/**
	 * この暦月の西暦年を返す。
	 * 
	 * @return 西暦年をあらわす数
	 * @since 2.0
	 */
	public int getYear() {
		return year;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + month.hashCode();
		result = prime * result + year;
		return result;
	}
	
	/**
	 * この暦月が、{@code other} よりも未来であるかどうかを検証する。
	 * 
	 * <p>{@code other} が {@code null} である場合は {@code false} を返す。
	 * また、同一である場合は {@code false} を返す。</p>
	 * 
	 * @param other 比較対象暦月
	 * @return 未来である場合は{@code true}、そうでない場合は{@code false}
	 * @since 1.0
	 */
	public boolean isAfter(CalendarMonth other) {
		if (other == null) {
			return false;
		}
		return isBefore(other) == false && equals(other) == false;
	}
	
	/**
	 * この暦月が、{@code other} よりも過去であるかどうかを検証する。
	 * 
	 * <p>{@code other} が {@code null} である場合は {@code false} を返す。
	 * また、同一である場合は {@code false} を返す。</p>
	 * 
	 * @param other 比較対象暦月
	 * @return 過去である場合は{@code true}、そうでない場合は{@code false}
	 * @since 1.0
	 */
	public boolean isBefore(CalendarMonth other) {
		if (other == null) {
			return false;
		}
		if (year < other.year) {
			return true;
		}
		if (year > other.year) {
			return false;
		}
		return month.isBefore(other.month);
	}
	
	/**
	 * この暦月の翌月を返す。
	 * 
	 * @return 翌月の暦月
	 * @since 1.0
	 */
	public CalendarMonth nextMonth() {
		return plusMonths(1);
	}
	
	/**
	 * この暦月に、指定した長さの時間を加えた、未来の暦月を取得する。
	 * 
	 * <p>引数の長さの単位が "月" 未満である場合は、元の暦月をそのまま返す。<p>
	 * 
	 * @param length 時間の長さ
	 * @return 未来の暦月
	 * @since 1.0
	 */
	public CalendarMonth plus(Duration length) {
		return length.addedTo(this);
	}
	
	/**
	 * この暦月の{@code increment}ヶ月後に当たる歴月を返す。
	 * 
	 * <p> {@code increment}に負数を与えてもよい。</p>
	 * 
	 * @param increment 加える月数
	 * @return {@code increment}ヶ月後に当たる歴月
	 * @since 1.0
	 */
	public CalendarMonth plusMonths(int increment) {
		Calendar calendar = asJavaCalendarUniversalZoneMidnight();
		calendar.add(Calendar.MONTH, increment);
		int yearValue = calendar.get(Calendar.YEAR);
		int monthValue = calendar.get(Calendar.MONTH) + 1;
		return CalendarMonth.from(yearValue, monthValue);
	}
	
	/**
	 * この暦月の前月に当たる暦月を返す。
	 * 
	 * @return 前月の暦月
	 * @since 1.0
	 */
	public CalendarMonth previousMonth() {
		return plusMonths(-1);
	}
	
	/**
	 * この暦月の文字列表現を取得する。
	 * 
	 * <p>{@link SimpleDateFormat}の仕様に基づく {@code "yyyy-MM"}のパターンで整形する。</p>
	 * 
	 * @see java.lang.Object#toString()
	 * @since 1.0
	 */
	@Override
	public String toString() {
		return toString("yyyy-MM"); //default for console
	}
	
	/**
	 * この暦月を、指定したパターンで整形し、その文字列表現を取得する。
	 * 
	 * @param pattern パターン
	 * @return 文字列表現
	 * @since 1.0
	 */
	public String toString(String pattern) {
		return toString(pattern, Locale.getDefault());
	}
	
	/**
	 * この暦月を、指定したパターンで整形し、その文字列表現を取得する。
	 * 
	 * @param pattern パターン
	 * @param locale ロケール
	 * @return 文字列表現
	 * @since 2.1
	 */
	public String toString(String pattern, Locale locale) {
		// Any timezone works, as long as the same one is used throughout.
		TimeZone arbitraryZone = TimeZones.UNIVERSAL;
		TimePoint point = asTimePoint(arbitraryZone);
		return point.toString(pattern, locale, arbitraryZone);
	}
	
	Calendar asJavaCalendarUniversalZoneMidnight() {
		Calendar calendar = CalendarUtil.newCalendar();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month.value - 1);
		calendar.set(Calendar.DATE, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar;
	}
}
