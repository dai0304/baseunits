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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

import org.apache.commons.lang.Validate;

/**
 * カレンダー上の特定の「年第x週」を表すクラス。
 * 
 * <p>{@link java.util.Date}と異なり、週未満（日以下）の概念を持っていない。また、{@link TimePoint}と異なり、
 * その週全ての範囲を表すクラスであり、特定の瞬間をモデリングしたものではない。</p>
 * 
 * @author daisuke
 * @since 2.0
 */
@SuppressWarnings("serial")
public class CalendarWeek implements Comparable<CalendarWeek>, Serializable {
	
	/**
	 * 指定した日付を含む週を返す。
	 * 
	 * @param date 日付
	 * @return 週
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @since 2.0
	 */
	public static CalendarWeek from(CalendarDate date) {
		Validate.notNull(date);
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
	 * 指定した週を表す、{@link CalendarWeek}のインスタンスを生成する。
	 * 
	 * @param year 西暦年をあらわす数
	 * @param weekOfYear 週数をあらわす正数（1〜53）
	 * @return {@link CalendarDate}
	 * @throws IllegalArgumentException 引数{@code month}が1〜53の範囲ではない場合
	 * @since 2.0
	 */
	public static CalendarWeek from(int year, int weekOfYear) {
		return new CalendarWeek(year, WeekOfYear.valueOf(weekOfYear));
	}
	
	/**
	 * 指定した年月を表す、{@link CalendarWeek}のインスタンスを生成する。
	 * 
	 * @param year 年
	 * @param month 月
	 * @return {@link CalendarWeek}
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @since 2.0
	 */
	public static CalendarWeek from(int year, WeekOfYear month) {
		return new CalendarWeek(year, month);
	}
	
	
	final int year;
	
	final WeekOfYear week;
	
	
	CalendarWeek(int year, WeekOfYear week) {
		Validate.notNull(week);
		this.year = year;
		this.week = week;
	}
	
	/**
	 * このインスタンスが表現する週の月曜日からその週末（日曜日）までの、期間を生成する。
	 * 
	 * <p>生成する期間の開始日と終了日は期間に含む（閉じている）開区間を生成する。</p>
	 * 
	 * @return このインスタンスが表現する週の月曜日からその週末（日曜日）までの、期間
	 * @since 2.0
	 */
	public CalendarInterval asCalendarInterval() {
		return CalendarInterval.week(year, week);
	}
	
	/**
	 * 指定したタイムゾーンにおける、このインスタンスが表す週の月曜日の深夜0時の瞬間について {@link TimePoint} 型のインスタンスを返す。
	 * 
	 * @param timeZone タイムゾーン
	 * @return {@link TimePoint}
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @since 2.0
	 */
	public TimePoint asTimePoint(TimeZone timeZone) {
		Validate.notNull(timeZone);
		CalendarDate date = asCalendarInterval().start();
		return TimePoint.from(date, TimeOfDay.MIDNIGHT, timeZone);
	}
	
	/**
	 * このインスタンスが表現する週を含む年の元旦からその大晦日までの、期間を生成する。
	 * 
	 * <p>生成する期間の開始日と終了日は期間に含む（閉じている）開区間を生成する。</p>
	 * 
	 * @return このインスタンスが表現する年月の1日からその月末までを表現する期間
	 * @since 2.0
	 */
	public CalendarInterval asYearInterval() {
		return CalendarInterval.year(year);
	}
	
	/**
	 * このインスタンスが表す週で、引数{@code dow}で表す曜日を表す年月日を返す。
	 * 
	 * @param dow 曜日
	 * @return 年月日
	 * @since 2.0
	 */
	public CalendarDate at(DayOfWeek dow) {
		CalendarDate monday = asCalendarInterval().start();
		return monday.plusDays(dow.ordinal());
	}
	
	/**
	 * このオブジェクトの{@link #week}フィールド（週）を返す。
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
	 * このオブジェクトの{@link #year}フィールド（西暦年をあらわす数）を返す。
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
	 * 年月日同士の比較を行う。
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
		assert week != null;
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
	 * 月末の日付を取得する。
	 * 
	 * @return {@link DayOfMonth}
	 * @since 2.0
	 */
	public CalendarDate getLastDay() {
		return at(DayOfWeek.SUNDAY);
	}
	
	@Override
	public int hashCode() {
		assert week != null;
		final int prime = 31;
		int result = 1;
		result = prime * result + week.hashCode();
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
	 * @since 2.0
	 */
	public boolean isAfter(CalendarWeek other) {
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
	 * このインスタンスが表現する年月の翌月を返す。
	 * 
	 * @return 翌月
	 * @since 2.0
	 */
	public CalendarWeek nextWeek() {
		return plusWeeks(1);
	}
	
	/**
	 * このインスタンスが表現する年月の {@code increment} 週後を返す。
	 * 
	 * <p> {@code increment}に負数を与えてもよい。</p>
	 * 
	 * @param increment 加える月数
	 * @return 計算結果
	 * @since 2.0
	 */
	public CalendarWeek plusWeeks(int increment) {
		Calendar calendar = asJavaCalendarUniversalZoneMidnight();
		calendar.add(Calendar.WEEK_OF_YEAR, increment);
		int year = calendar.get(Calendar.YEAR);
		int week = calendar.get(Calendar.WEEK_OF_YEAR);
		return CalendarWeek.from(year, week);
	}
	
	/**
	 * このインスタンスが表現する週の前週を返す。
	 * 
	 * @return 前週
	 * @since 2.0
	 */
	public CalendarWeek previousWeek() {
		return plusWeeks(-1);
	}
	
	/**
	 * この日付の文字列表現を取得する。
	 * 
	 * <p>{@link SimpleDateFormat}の使用に基づく {@code "yyyy-w'th'"}のパターンで整形する。</p>
	 * 
	 * @see java.lang.Object#toString()
	 * @since 2.0
	 */
	@Override
	public String toString() {
		return toString("yyyy-w'th'"); //default for console
	}
	
	/**
	 * この週を、指定したパターンで整形し、その文字列表現を取得する。
	 * 
	 * @param pattern パターン
	 * @return 文字列表現
	 * @since 2.0
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
		calendar.set(Calendar.WEEK_OF_YEAR, week.value);
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar;
	}
}
