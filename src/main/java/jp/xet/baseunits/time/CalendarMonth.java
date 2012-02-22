/*
 * Copyright 2011 Daisuke Miyamoto. (http://d.hatena.ne.jp/daisuke-m)
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
 * Copyright (c) 2005 Domain Language, Inc. (http://domainlanguage.com) This
 * free software is distributed under the "MIT" licence.
 * For more information, see http://timeandmoney.sourceforge.net.
 */
package jp.xet.baseunits.time;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

import org.apache.commons.lang.Validate;

/**
 * カレンダー上の特定の「年月」を表すクラス。
 * 
 * <p>{@link java.util.Date}と異なり、月未満（日以下）の概念を持っていない。また、{@link TimePoint}と異なり、
 * その月1ヶ月間全ての範囲を表すクラスであり、特定の瞬間をモデリングしたものではない。</p>
 * 
 * @since 1.0
 */
@SuppressWarnings("serial")
public class CalendarMonth implements Comparable<CalendarMonth>, Serializable {
	
	/**
	 * 指定した年月を表す、{@link CalendarMonth}のインスタンスを生成する。
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
	 * 指定した年月を表す、{@link CalendarMonth}のインスタンスを生成する。
	 * 
	 * @param year 年
	 * @param month 月
	 * @return {@link CalendarMonth}
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	public static CalendarMonth from(int year, MonthOfYear month) {
		return new CalendarMonth(year, month);
	}
	
	/**
	 * 指定したタイムゾーン上で指定した瞬間が属する日付を元に、{@link CalendarDate}のインスタンスを生成する。
	 * 
	 * @param timePoint 瞬間
	 * @param zone タイムゾーン
	 * @return {@link CalendarDate}
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	public static CalendarMonth from(TimePoint timePoint, TimeZone zone) {
		Validate.notNull(timePoint);
		Validate.notNull(zone);
		Calendar calendar = timePoint.asJavaCalendar();
		calendar.setTimeZone(zone);
		return CalendarMonth.from(calendar);
	}
	
	/**
	 * 指定した年月を表す、{@link CalendarMonth}のインスタンスを生成する。
	 * 
	 * @param dateString 年月を表す文字列 
	 * @param pattern 解析パターン文字列
	 * @return {@link CalendarMonth}
	 * @throws ParseException 文字列の解析に失敗した場合 
	 * @since 1.0
	 */
	public static CalendarMonth parse(String dateString, String pattern) throws ParseException {
		TimeZone arbitraryZone = TimeZone.getTimeZone("Universal");
		//Any timezone works, as long as the same one is used throughout.
		TimePoint point = TimePoint.parse(dateString, pattern, arbitraryZone);
		return CalendarMonth.from(point, arbitraryZone);
	}
	
	static CalendarMonth from(Calendar calendar) { // CHECKSTYLE IGNORE THIS LINE
		// Use timezone already set in calendar.
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1; // T&M Lib counts January as 1
		return CalendarMonth.from(year, month);
	}
	
	
	final int year;
	
	final MonthOfYear month;
	
	
	CalendarMonth(int year, MonthOfYear month) {
		Validate.notNull(month);
		this.year = year;
		this.month = month;
	}
	
	/**
	 * このインスタンスが表現する年月の1日からその月末までの、期間を生成する。
	 * 
	 * <p>生成する期間の開始日と終了日は期間に含む（閉じている）開区間を生成する。</p>
	 * 
	 * @return このインスタンスが表現する年月の1日からその月末までを表現する期間
	 * @since 1.0
	 */
	public CalendarInterval asCalendarInterval() {
		return CalendarInterval.month(year, month);
	}
	
	/**
	 * 指定したタイムゾーンにおける、このインスタンスが表す「年月」の1日0時0分0秒0ミリ秒の瞬間について {@link TimePoint} 型のインスタンスを返す。
	 * 
	 * @param timeZone タイムゾーン
	 * @return {@link TimePoint}
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	public TimePoint asTimePoint(TimeZone timeZone) {
		Validate.notNull(timeZone);
		return TimePoint.at(year, month, DayOfMonth.valueOf(1), 0, 0, 0, 0, timeZone);
	}
	
	/**
	 * このインスタンスが表現する年月を含む年の元旦からその大晦日までの、期間を生成する。
	 * 
	 * <p>生成する期間の開始日と終了日は期間に含む（閉じている）開区間を生成する。</p>
	 * 
	 * @return このインスタンスが表現する年月の1日からその月末までを表現する期間
	 * @since 1.0
	 */
	public CalendarInterval asYearInterval() {
		return CalendarInterval.year(year);
	}
	
	/**
	 * このインスタンスが表す年月で、引数{@code day}で表す日を表す年月日を返す。
	 * 
	 * @param day 日（1〜31）
	 * @return 日時
	 * @throws IllegalArgumentException 引数{@code day}がこの月に存在しない場合
	 * @since 1.0
	 */
	public CalendarDate at(DayOfMonth day) {
		return CalendarDate.from(year, month, day);
	}
	
	/**
	 * 年月日同士の比較を行う。
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
	 * 月末の日付を取得する。
	 * 
	 * @return {@link DayOfMonth}
	 * @since 1.0
	 */
	public CalendarDate getLastDay() {
		return CalendarDate.from(year, month, getLastDayOfMonth());
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
	 * この年月が属する月を取得する。
	 * 
	 * @return 月
	 * @since 2.0
	 */
	public MonthOfYear getMonthOfYear() {
		return month;
	}
	
	/**
	 * この年月が属する年を返す。
	 * 
	 * @return 西暦年をあらわす数
	 * @since 2.0
	 */
	public int getYear() {
		return year;
	}
	
	@Override
	public int hashCode() {
		assert month != null;
		final int prime = 31;
		int result = 1;
		result = prime * result + month.hashCode();
		result = prime * result + year;
		return result;
	}
	
	/**
	 * 指定した日 {@code other} が、このオブジェクトが表現する日よりも過去であるかどうかを検証する。
	 * 
	 * <p>{@code other} が {@code null} である場合は {@code false} を返す。
	 * また、お互いが同一日時である場合は {@code false} を返す。</p>
	 * 
	 * @param other 対象日時
	 * @return 過去である場合は{@code true}、そうでない場合は{@code false}
	 * @since 1.0
	 */
	public boolean isAfter(CalendarMonth other) {
		if (other == null) {
			return false;
		}
		return isBefore(other) == false && equals(other) == false;
	}
	
	/**
	 * 指定した年月 {@code other} が、このオブジェクトが表現する年月よりも未来であるかどうかを検証する。
	 * 
	 * <p>{@code other} が {@code null} である場合は {@code false} を返す。
	 * また、お互いが同一日時である場合は {@code false} を返す。</p>
	 * 
	 * @param other 対象年月
	 * @return 未来である場合は{@code true}、そうでない場合は{@code false}
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
	 * このインスタンスが表現する年月の翌月を返す。
	 * 
	 * @return 翌月
	 * @since 1.0
	 */
	public CalendarMonth nextMonth() {
		return plusMonths(1);
	}
	
	/**
	 * このオブジェクトが表現する日付に、指定した長さの時間を加えた、未来の日付を取得する。
	 * 
	 * <p>引数の長さの単位が "月" 未満である場合は、元の年月をそのまま返す。<p>
	 * 
	 * @param length 時間の長さ
	 * @return 未来の年月
	 * @since 1.0
	 */
	public CalendarMonth plus(Duration length) {
		return length.addedTo(this);
	}
	
	/**
	 * このインスタンスが表現する年月の {@code increment} ヶ月後を返す。
	 * 
	 * <p> {@code increment}に負数を与えてもよい。</p>
	 * 
	 * @param increment 加える月数
	 * @return 計算結果
	 * @since 1.0
	 */
	public CalendarMonth plusMonths(int increment) {
		Calendar calendar = asJavaCalendarUniversalZoneMidnight();
		calendar.add(Calendar.MONTH, increment);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		return CalendarMonth.from(year, month);
	}
	
	/**
	 * このインスタンスが表現する年月の前月を返す。
	 * 
	 * @return 前月
	 * @since 1.0
	 */
	public CalendarMonth previousMonth() {
		return plusMonths(-1);
	}
	
	/**
	 * この日付の文字列表現を取得する。
	 * 
	 * <p>{@link SimpleDateFormat}の使用に基づく {@code "yyyy-MM"}のパターンで整形する。</p>
	 * 
	 * @see java.lang.Object#toString()
	 * @since 1.0
	 */
	@Override
	public String toString() {
		return toString("yyyy-MM"); //default for console
	}
	
	/**
	 * この日付を、指定したパターンで整形し、その文字列表現を取得する。
	 * 
	 * @param pattern パターン
	 * @return 文字列表現
	 * @since 1.0
	 */
	public String toString(String pattern) {
		// Any timezone works, as long as the same one is used throughout.
		TimeZone arbitraryZone = TimeZone.getTimeZone("Universal");
		TimePoint point = asTimePoint(arbitraryZone);
		return point.toString(pattern, arbitraryZone);
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
