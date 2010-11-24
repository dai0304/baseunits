/*
 * Copyright 2010 Daisuke Miyamoto.
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
 * free software is distributed under the "MIT" licence. See file licence.txt.
 * For more information, see http://timeandmoney.sourceforge.net.
 */
package jp.xet.timeandmoney.time;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.apache.commons.lang.Validate;

/**
 * カレンダー上の特定の「年月日」を表すクラス。
 * 
 * <p>{@link Date}と異なり、時間の概念を持っていない。また、{@link TimePoint}と異なり、
 * その日1日間全ての範囲を表すクラスであり、特定の瞬間をモデリングしたものではない。</p>
 * 
 * @author daisuke
 */
@SuppressWarnings("serial")
public class CalendarDate implements Comparable<CalendarDate>, Serializable {
	
	/**
	 * 指定した年月日を表す、{@link CalendarDate}のインスタンスを生成する。
	 * 
	 * @param year 西暦年をあらわす数
	 * @param month 月をあらわす正数（1〜12）
	 * @param day 日をあらわす正数（1〜31）
	 * @return {@link CalendarDate}
	 * @throws IllegalArgumentException 引数{@code month}が1〜12の範囲ではない場合
	 * @throws IllegalArgumentException 引数{@code day}が1〜31の範囲ではない場合
	 */
	public static CalendarDate date(int year, int month, int day) {
		return from(year, month, day);
	}
	
	/**
	 * 指定した年月日を表す、{@link CalendarDate}のインスタンスを生成する。
	 * 
	 * @param year 西暦年をあらわす数
	 * @param month 月をあらわす正数（1〜12）
	 * @param day 日をあらわす正数（1〜31）
	 * @return {@link CalendarDate}
	 * @throws IllegalArgumentException 引数{@code month}が1〜12の範囲ではない場合
	 * @throws IllegalArgumentException 引数{@code day}が1〜31の範囲ではない場合
	 */
	public static CalendarDate from(int year, int month, int day) {
		return new CalendarDate(year, month, day);
	}
	
	/**
	 * 指定した年月日を表す、{@link CalendarDate}のインスタンスを生成する。
	 * 
	 * @param dateString 年月日を表す文字列 
	 * @param pattern 解析パターン文字列
	 * @return {@link CalendarDate}
	 * @throws ParseException 文字列の解析に失敗した場合 
	 */
	public static CalendarDate from(String dateString, String pattern) throws ParseException {
		TimeZone arbitraryZone = TimeZone.getTimeZone("Universal");
		//Any timezone works, as long as the same one is used throughout.
		TimePoint point = TimePoint.parseFrom(dateString, pattern, arbitraryZone);
		return CalendarDate.from(point, arbitraryZone);
	}
	
	/**
	 * 指定したタイムゾーン上で指定した瞬間が属する日付を元に、{@link CalendarDate}のインスタンスを生成する。
	 * 
	 * @param timePoint 瞬間
	 * @param zone タイムゾーン
	 * @return {@link CalendarDate}
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 */
	public static CalendarDate from(TimePoint timePoint, TimeZone zone) {
		Validate.notNull(timePoint);
		Validate.notNull(zone);
		Calendar calendar = timePoint.asJavaCalendar();
		calendar.setTimeZone(zone);
		return CalendarDate._from(calendar);
	}
	
	static CalendarDate _from(Calendar calendar) { // CHECKSTYLE IGNORE THIS LINE
		// Use timezone already set in calendar.
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1; // T&M Lib counts January as 1
		int date = calendar.get(Calendar.DATE);
		return CalendarDate.from(year, month, date);
	}
	

	private final int year;
	
	/** 1 based: January = 1, February = 2, ... */
	private final int month;
	
	private final int day;
	

	CalendarDate(int year, int month, int day) {
		Validate.isTrue(0 < month && month <= 12);
		Validate.isTrue(0 < day && day <= 31); // CHECKSTYLE IGNORE THIS LINE
		
		this.year = year;
		this.month = month;
		this.day = day;
	}
	
	/**
	 * このインスタンスが表現する日の午前0時から丸一日を期間として取得する。
	 * 
	 * <p>生成する期間の開始日時は区間に含み（閉じている）、終了日時は区間に含まない（開いている）半開区間を生成する。</p>
	 * 
	 * @param zone タイムゾーン
	 * @return このインスタンスが表現する日の午前0時から丸一日を表現する期間
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 */
	public TimeInterval asTimeInterval(TimeZone zone) {
		Validate.notNull(zone);
		return TimeInterval.startingFrom(startAsTimePoint(zone), true, Duration.days(1), false);
	}
	
	/**
	 * このインスタンスが表す日付で、引数{@code timeOfDay}で表す時を表す日時を返す。
	 * 
	 * @param timeOfDay 時
	 * @return 日時
	 */
	public CalendarMinute at(TimeOfDay timeOfDay) {
		return CalendarMinute.dateAndTimeOfDay(this, timeOfDay);
	}
	
	/**
	 * 年月日同士の比較を行う。
	 * 
	 * <p>相対的に過去である方を「小さい」と判断する。</p>
	 * 
	 * @param other 比較対象
	 * @return {@link Comparable#compareTo(Object)}に準じる
	 * @throws NullPointerException 引数に{@code null}を与えた場合
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
	 * この日付の曜日を返す。
	 * 
	 * @return 曜日
	 */
	public DayOfWeek dayOfWeek() {
		Calendar calendar = asJavaCalendarUniversalZoneMidnight();
		return DayOfWeek.valueOf(calendar.get(Calendar.DAY_OF_WEEK));
	}
	
	// comment-out by daisuke
//	public CalendarDate start() {
//		return this;
//	}
//
//	public CalendarDate end() {
//		return this;
//	}
	
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
		if (day != other.day) {
			return false;
		}
		if (month != other.month) {
			return false;
		}
		if (year != other.year) {
			return false;
		}
		return true;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + day;
		result = prime * result + month;
		result = prime * result + year;
		return result;
	}
	
	/**
	 * 指定した日 {@code other} が、このオブジェクトが表現する日よりも過去であるかどうかを検証する。
	 * 
	 * <p>{@code other} が {@code null} である場合と、お互いが同一日時である場合は {@code false} を返す。</p>
	 * 
	 * @param other 対象日時
	 * @return 過去である場合は{@code true}、そうでない場合は{@code false}
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 */
	public boolean isAfter(CalendarDate other) {
		if (other == null) {
			return false;
		}
		return isBefore(other) == false && equals(other) == false;
	}
	
	/**
	 * 指定した日 {@code other} が、このオブジェクトが表現する日よりも未来であるかどうかを検証する。
	 * 
	 * <p>{@code other} が {@code null} である場合と、お互いが同一日時である場合は {@code false} を返す。</p>
	 * 
	 * @param other 対象日時
	 * @return 未来である場合は{@code true}、そうでない場合は{@code false}
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 */
	public boolean isBefore(CalendarDate other) {
		if (other == null) {
			return false;
		}
		if (year < other.year) {
			return true;
		}
		if (year > other.year) {
			return false;
		}
		if (month < other.month) {
			return true;
		}
		if (month > other.month) {
			return false;
		}
		return day < other.day;
	}
	
	/**
	 * このインスタンスが表現する日を含む年月を表す期間を取得する。
	 * 
	 * @return このインスタンスが表現する日を含む年月を表す期間
	 */
	public CalendarInterval month() {
		return CalendarInterval.month(year, month);
	}
	
	/**
	 * このインスタンスが表現する日の翌日を返す。
	 * 
	 * @return 翌日
	 */
	public CalendarDate nextDay() {
		return plusDays(1);
	}
	
	/**
	 * このオブジェクトが表現する日付に、指定した長さの時間を加えた、未来の日付を取得する。
	 * 
	 * <p>引数の長さの単位が "日" 未満である場合は、元の日付をそのまま返す。<p>
	 * 
	 * @param length 時間の長さ
	 * @return 未来の日付
	 */
	public CalendarDate plus(Duration length) {
		return length.addedTo(this);
	}
	
	/**
	 * このインスタンスが表現する日の {@code increment} 日後を返す。
	 * 
	 * <p> {@code increment}に負数を与えてもよい。</p>
	 * 
	 * @param increment 加える日数
	 * @return 計算結果
	 */
	public CalendarDate plusDays(int increment) {
		Calendar calendar = asJavaCalendarUniversalZoneMidnight();
		calendar.add(Calendar.DATE, increment);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DATE);
		return CalendarDate.from(year, month, day);
	}
	
	/**
	 * このインスタンスが表現する日の {@code increment} ヶ月後を返す。
	 * 
	 * <p> {@code increment}に負数を与えてもよい。</p>
	 * 
	 * @param increment 加える月数
	 * @return 計算結果
	 */
	public CalendarDate plusMonths(int increment) {
		Calendar calendar = asJavaCalendarUniversalZoneMidnight();
		calendar.add(Calendar.MONTH, increment);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DATE);
		return CalendarDate.from(year, month, day);
	}
	
	/**
	 * このインスタンスが表現する日の前日を返す。
	 * 
	 * @return 前日
	 */
	public CalendarDate previousDay() {
		return plusDays(-1);
	}
	
	/**
	 * このインスタンスが表現する日付の午前0時を、日時として取得する。 
	 * 
	 * @param zone タイムゾーン
	 * @return このインスタンスが表現する日の午前0時を表現する日時
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 */
	public TimePoint startAsTimePoint(TimeZone zone) {
		Validate.notNull(zone);
		return TimePoint.atMidnight(year, month, day, zone);
	}
	
	/**
	 * このインスタンスが表現する日付を開始日とし、指定した日付 {@code otherDate} を終了日とする期間を取得する。
	 * 
	 * @param otherDate 終了日
	 * @return 期間
	 */
	public CalendarInterval through(CalendarDate otherDate) {
		return CalendarInterval.inclusive(this, otherDate);
	}
	
	/**
	 * この日付の文字列表現を取得する。
	 * 
	 * <p>{@link SimpleDateFormat}の使用に基づく {@code "yyyy-MM-dd"}のパターンで整形する。</p>
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return toString("yyyy-MM-dd"); //default for console
	}
	
	/**
	 * この日付を、指定したパターンで整形し、その文字列表現を取得する。
	 * 
	 * @param pattern パターン
	 * @return 文字列表現
	 */
	public String toString(String pattern) {
		// Any timezone works, as long as the same one is used throughout.
		TimeZone arbitraryZone = TimeZone.getTimeZone("Universal");
		TimePoint point = startAsTimePoint(arbitraryZone);
		return point.toString(pattern, arbitraryZone);
	}
	
	/**
	 * このインスタンスが表現する日を含む年を表す期間を取得する。
	 * 
	 * @return このインスタンスが表現する日を含む年を表す期間
	 */
	public CalendarInterval year() {
		return CalendarInterval.year(year);
	}
	
	Calendar asJavaCalendarUniversalZoneMidnight() {
		TimeZone zone = TimeZone.getTimeZone("Universal");
		Calendar calendar = Calendar.getInstance(zone);
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month - 1);
		calendar.set(Calendar.DATE, day);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar;
	}
	
	int breachEncapsulationOfDay() {
		return day;
	}
	
	int breachEncapsulationOfMonth() {
		return month;
	}
	
	int breachEncapsulationOfYear() {
		return year;
	}
}
