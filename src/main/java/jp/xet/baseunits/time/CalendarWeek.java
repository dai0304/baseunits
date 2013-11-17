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
 * Copyright (c) 2005 Domain Language, Inc. (http://domainlanguage.com) This
 * free software is distributed under the "MIT" licence.
 * For more information, see http://timeandmoney.sourceforge.net.
 */
package jp.xet.baseunits.time;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

import com.google.common.base.Preconditions;

/**
 * 暦週を表すクラス。
 * 
 * <p>暦週とは、カレンダー上の特定の「週」（デフォルトでは月曜日〜日曜日）のことで、
 * 暦年における週数によって指定される。例えば「2012年の第30週」や「1978年の第3週」等のことである。</p>
 * 
 * <p>{@link java.util.Date}と異なり、週未満（日以下）の概念を持っていない。また、{@link TimePoint}と異なり、
 * その週全ての範囲を表すクラスであり、特定の瞬間のモデルではない。</p>
 * 
 * @author daisuke
 * @since 2.0
 * @see WeekOfYear
 */
@SuppressWarnings("serial")
public class CalendarWeek implements Comparable<CalendarWeek>, Serializable {
	
	private static final DayOfWeek START_DAY_OF_WEEK = DayOfWeek.MONDAY;
	
	private static final DayOfWeek END_DAY_OF_WEEK = DayOfWeek.SUNDAY;
	
	
	/**
	 * 指定した暦日を含む暦週を返す。
	 * 
	 * @param date 暦日
	 * @return 暦週
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @since 2.0
	 */
	public static CalendarWeek from(CalendarDate date) {
		Preconditions.checkNotNull(date);
		Calendar cal = date.asJavaCalendarUniversalZoneMidnight();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		int weekOfYear = cal.get(Calendar.WEEK_OF_YEAR);
		
		if (month == Calendar.JANUARY) {
			if (weekOfYear >= 52) { // CHECKSTYLE IGNORE THIS LINE
				--year;
			}
		} else {
			if (weekOfYear == 1) {
				++year;
			}
		}
		
		return from(year, weekOfYear);
	}
	
	/**
	 * 指定した西暦年及び週の序数で表される暦週を返す。
	 * 
	 * @param year 西暦年
	 * @param weekOfYear 週の序数（1〜53）
	 * @return {@link CalendarDate}
	 * @throws IllegalArgumentException 引数{@code weekOfYear}が1〜53の範囲ではない場合
	 * @since 2.0
	 */
	public static CalendarWeek from(int year, int weekOfYear) {
		return new CalendarWeek(year, WeekOfYear.valueOf(weekOfYear));
	}
	
	/**
	 * 指定した西暦年及び週で表される暦週を返す。
	 * 
	 * @param year 年
	 * @param week 週
	 * @return {@link CalendarWeek}
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @since 2.0
	 */
	public static CalendarWeek from(int year, WeekOfYear week) {
		return new CalendarWeek(year, week);
	}
	
	
	final int year;
	
	final WeekOfYear week;
	
	
	CalendarWeek(int year, WeekOfYear week) {
		Preconditions.checkNotNull(week);
		this.year = year;
		this.week = week;
	}
	
	/**
	 * この暦週の月曜日からその週末（日曜日）までの、期間を生成する。
	 * 
	 * <p>生成する期間の開始日と終了日は期間に含む（閉じている）開区間を生成する。</p>
	 * 
	 * @return この暦週の月曜日からその週末（日曜日）までの、期間
	 * @since 2.0
	 */
	public CalendarInterval asCalendarInterval() {
		return CalendarInterval.week(this);
	}
	
	/**
	 * この暦週の、指定したタイムゾーンにおける月曜日の深夜0時の {@link TimePoint} を返す。
	 * 
	 * @param timeZone タイムゾーン
	 * @return {@link TimePoint}
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @since 2.0
	 */
	public TimePoint asTimePoint(TimeZone timeZone) {
		Preconditions.checkNotNull(timeZone);
		CalendarDate date = asCalendarInterval().start();
		return TimePoint.from(date, TimeOfDay.MIDNIGHT, timeZone);
	}
	
	/**
	 * この暦週を含む暦年の、元旦からその大晦日までの暦日期間を生成する。
	 * 
	 * <p>生成する期間の開始日と終了日は期間に含む（閉じている）開区間を生成する。</p>
	 * 
	 * @return この暦週を含む年の、元旦からその大晦日までの期間
	 * @since 2.0
	 */
	public CalendarInterval asYearInterval() {
		return CalendarInterval.year(year);
	}
	
	/**
	 * この暦週に含まれる暦日の中で指定した曜日を返す。
	 * 
	 * @param dow 曜日
	 * @return 暦日
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @since 2.0
	 */
	public CalendarDate at(DayOfWeek dow) {
		Preconditions.checkNotNull(dow);
		CalendarDate start = asCalendarInterval().start();
		return start.plusDays(dow.ordinal());
	}
	
	/**
	 * この暦週の{@link #week}フィールド（週）を返す。
	 * 
	 * <p>CAUTION: このメソッドは、このオブジェクトがカプセル化する要素を外部に暴露する。取り扱いには充分注意のこと。</p>
	 * 
	 * @return 月
	 * @since 2.0
	 */
	public WeekOfYear breachEncapsulationOfMonth() {
		return week;
	}
	
	/**
	 * この暦週の{@link #year}フィールド（西暦年をあらわす数）を返す。
	 * 
	 * <p>CAUTION: このメソッドは、このオブジェクトがカプセル化する要素を外部に暴露する。取り扱いには充分注意のこと。</p>
	 * 
	 * @return 西暦年をあらわす数
	 * @since 2.0
	 */
	public int breachEncapsulationOfYear() {
		return year;
	}
	
	/**
	 * 暦週同士の比較を行う。
	 * 
	 * <p>相対的に過去である方を「小さい」と判断する。</p>
	 * 
	 * @param other 比較対象
	 * @return {@link Comparable#compareTo(Object)}に準じる
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @since 2.0
	 */
	@Override
	public int compareTo(CalendarWeek other) {
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
		CalendarWeek other = (CalendarWeek) obj;
		if (week.equals(other.week) == false) {
			return false;
		}
		if (year != other.year) {
			return false;
		}
		return true;
	}
	
	/**
	 * この暦週の週末（日曜日）に当たる暦日を取得する。
	 * 
	 * @return {@link DayOfMonth}
	 * @since 2.0
	 */
	public CalendarDate getLastDay() {
		return at(END_DAY_OF_WEEK);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + week.hashCode();
		result = prime * result + year;
		return result;
	}
	
	/**
	 * この暦週が {@code other} よりも未来であるかどうかを検証する。
	 * 
	 * <p>{@code other} が {@code null} である場合は {@code false} を返す。
	 * また、同一である場合は {@code false} を返す。</p>
	 * 
	 * @param other 比較対象暦週
	 * @return 未来である場合は{@code true}、そうでない場合は{@code false}
	 * @since 2.0
	 */
	public boolean isAfter(CalendarWeek other) {
		if (other == null) {
			return false;
		}
		return isBefore(other) == false && equals(other) == false;
	}
	
	/**
	 * この暦週が {@code other} よりも過去であるかどうかを検証する。
	 * 
	 * <p>{@code other} が {@code null} である場合は {@code false} を返す。
	 * また、同一である場合は {@code false} を返す。</p>
	 * 
	 * @param other 比較対象暦週
	 * @return 過去である場合は{@code true}、そうでない場合は{@code false}
	 * @since 2.0
	 */
	public boolean isBefore(CalendarWeek other) {
		if (other == null) {
			return false;
		}
		if (year < other.year) {
			return true;
		}
		if (year > other.year) {
			return false;
		}
		return week.isBefore(other.week);
	}
	
	/**
	 * この暦週の翌週に当たる暦週を返す。
	 * 
	 * @return 翌週
	 * @since 2.0
	 */
	public CalendarWeek nextWeek() {
		return plusWeeks(1);
	}
	
	/**
	 * この暦週の {@code increment}週後に当たる暦週を返す。
	 * 
	 * <p> {@code increment}に負数を与えてもよい。</p>
	 * 
	 * @param increment 加える週数
	 * @return {@code increment}週後に当たる暦週
	 * @since 2.0
	 */
	public CalendarWeek plusWeeks(int increment) {
		Calendar calendar = asJavaCalendarUniversalZoneMidnight();
		calendar.add(Calendar.WEEK_OF_YEAR, increment);
		CalendarDate calendarDate = CalendarDate.from(calendar);
		return CalendarWeek.from(calendarDate);
	}
	
	/**
	 * この暦週の前週に当たる暦週を返す。
	 * 
	 * @return 前週
	 * @since 2.0
	 */
	public CalendarWeek previousWeek() {
		return plusWeeks(-1);
	}
	
	/**
	 * この暦週の文字列表現を取得する。
	 * 
	 * <p>{@link SimpleDateFormat}の使用に基づく {@code "yyyy-w'th'"}のパターンで整形する。</p>
	 * 
	 * @see java.lang.Object#toString()
	 * @since 2.0
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(String.valueOf(year));
		String weekValueString = String.valueOf(week.value);
		sb.append("-").append(weekValueString);
		if (week.value == 11 || week.value == 12 || week.value == 13) {
			sb.append("th");
		} else if (weekValueString.endsWith("1")) {
			sb.append("st");
		} else if (weekValueString.endsWith("2")) {
			sb.append("nd");
		} else if (weekValueString.endsWith("3")) {
			sb.append("rd");
		} else {
			sb.append("th");
		}
		return sb.toString(); //default for console
	}
	
	/**
	 * この暦週を、指定したパターンで整形し、その文字列表現を取得する。
	 * 
	 * @param pattern パターン
	 * @return 文字列表現
	 * @since 2.0
	 * @deprecated この仕様を正確かつ妥当に実装する手段がない
	 */
	@Deprecated
	public String toString(String pattern) {
		// Any timezone works, as long as the same one is used throughout.
		TimeZone arbitraryZone = TimePoint.UTC;
		TimePoint point = asTimePoint(arbitraryZone);
		return point.toString(pattern, arbitraryZone);
	}
	
	Calendar asJavaCalendarUniversalZoneMidnight() {
		Calendar calendar = CalendarUtil.newCalendar();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.WEEK_OF_YEAR, week.value);
		calendar.set(Calendar.DAY_OF_WEEK, START_DAY_OF_WEEK.value);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar;
	}
}
