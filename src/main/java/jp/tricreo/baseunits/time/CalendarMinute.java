/*
 * Copyright 2010 TRICREO, Inc. (http://tricreo.jp/)
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
package jp.tricreo.baseunits.time;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import org.apache.commons.lang.Validate;

/**
 * カレンダー上の特定の「年月日時分」を表すクラス。
 * 
 * <p>{@link java.util.Date}と異なり、分未満（秒以下）の概念を持っていない。また、{@link TimePoint}と異なり、
 * その分1分間全ての範囲を表すクラスであり、特定の瞬間をモデリングしたものではない。</p>
 */
@SuppressWarnings("serial")
public class CalendarMinute implements Comparable<CalendarMinute>, Serializable {
	
	/**
	 * 指定した年月日を時分表す、{@link CalendarMinute}のインスタンスを生成する。
	 * 
	 * @param aDate 年月日
	 * @param aTime 時分
	 * @return {@link CalendarMinute}
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 */
	public static CalendarMinute from(CalendarDate aDate, TimeOfDay aTime) {
		Validate.notNull(aDate);
		Validate.notNull(aTime);
		return new CalendarMinute(aDate, aTime);
	}
	
	/**
	 * 指定した年月日を時分表す、{@link CalendarMinute}のインスタンスを生成する。
	 * 
	 * @param year 西暦年をあらわす数
	 * @param month 月をあらわす正数（1〜12）
	 * @param day 日をあらわす正数（1〜31）
	 * @param hour 時をあらわす正数（0〜23）
	 * @param minute 分をあらわす正数（0〜59）
	 * @return {@link CalendarMinute}
	 * @throws IllegalArgumentException 引数{@code month}が1〜12の範囲ではない場合
	 * @throws IllegalArgumentException 引数{@code day}が1〜31の範囲ではない場合
	 * @throws IllegalArgumentException 引数{@code hour}が0〜23の範囲ではない場合
	 * @throws IllegalArgumentException 引数{@code minute}が0〜59の範囲ではない場合
	 * @throws IllegalArgumentException 引数{@code day}が{@code yearMonth}の月に存在しない場合
	 */
	public static CalendarMinute from(int year, int month, int day, int hour, int minute) {
		return new CalendarMinute(CalendarDate.from(year, month, day), TimeOfDay.from(hour, minute));
	}
	
	/**
	 * 指定した年月日時分を表す、{@link CalendarDate}のインスタンスを生成する。
	 * 
	 * @param dateTimeString 年月日時分を表す文字列 
	 * @param pattern 解析パターン文字列
	 * @return {@link CalendarMinute}
	 * @throws ParseException 文字列の解析に失敗した場合 
	 */
	public static CalendarMinute parse(String dateTimeString, String pattern) throws ParseException {
		TimeZone arbitraryZone = TimeZone.getTimeZone("Universal");
		//Any timezone works, as long as the same one is used throughout.
		TimePoint point = TimePoint.parse(dateTimeString, pattern, arbitraryZone);
		return CalendarMinute.from(point.calendarDate(arbitraryZone), point.asTimeOfDay(arbitraryZone));
	}
	

	/** 年月日 */
	final CalendarDate date;
	
	/** 時分 */
	final TimeOfDay time;
	

	CalendarMinute(CalendarDate date, TimeOfDay time) {
		Validate.notNull(date);
		Validate.notNull(time);
		this.date = date;
		this.time = time;
	}
	
	/**
	* 指定したタイムゾーンにおける、このインスタンスが表す「年月日時分」の0秒0ミリ秒の瞬間について {@link TimePoint} 型のインスタンスを返す。
	* 
	* @param timeZone タイムゾーン
	* @return {@link TimePoint}
	* @throws IllegalArgumentException 引数に{@code null}を与えた場合
	*/
	public TimePoint asTimePoint(TimeZone timeZone) {
		Validate.notNull(timeZone);
		return TimePoint.from(date, time, timeZone);
	}
	
	/**
	 * このオブジェクトの{@link #date}フィールド（年月日）を返す。
	 * 
	 * <p>CAUTION: このメソッドは、このオブジェクトがカプセル化する要素を外部に暴露する。取り扱いには充分注意のこと。</p>
	 * 
	 * @return 年月日
	 */
	public CalendarDate breachEncapsulationOfDate() {
		return date;
	}
	
	/**
	 * このオブジェクトの{@link #time}フィールド（時分）を返す。
	 * 
	 * <p>CAUTION: このメソッドは、このオブジェクトがカプセル化する要素を外部に暴露する。取り扱いには充分注意のこと。</p>
	 * 
	 * @return 時分
	 */
	public TimeOfDay breachEncapsulationOfTime() {
		return time;
	}
	
	@Override
	public int compareTo(CalendarMinute other) {
		int dateComparance = date.compareTo(other.date);
		if (dateComparance != 0) {
			return dateComparance;
		}
		return time.compareTo(other.time);
	}
	
	@Override
	public boolean equals(Object obj) {
		assert date != null;
		assert time != null;
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		CalendarMinute other = (CalendarMinute) obj;
		if (date.equals(other.date) == false) {
			return false;
		}
		if (time.equals(other.time) == false) {
			return false;
		}
		return true;
	}
	
	@Override
	public int hashCode() {
		assert date != null;
		assert time != null;
		final int prime = 31;
		int result = 1;
		result = prime * result + date.hashCode();
		result = prime * result + time.hashCode();
		return result;
	}
	
	/**
	 * 指定した年月日時分 {@code other} が、このオブジェクトが表現する年月日時分よりも過去であるかどうかを検証する。
	 * 
	 * <p>{@code other} が {@code null} である場合と、お互いが同一日時である場合は {@code false} を返す。</p>
	 * 
	 * @param other 対象年月日時分
	 * @return 過去である場合は{@code true}、そうでない場合は{@code false}
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 */
	public boolean isAfter(CalendarMinute other) {
		Validate.notNull(other);
		return isBefore(other) == false && equals(other) == false;
	}
	
	/**
	 * 指定した年月日時分 {@code other} が、このオブジェクトが表現する年月日時分よりも未来であるかどうかを検証する。
	 * 
	 * <p>{@code other} が {@code null} である場合と、お互いが同一日時である場合は {@code false} を返す。</p>
	 * 
	 * @param other 対象年月日時分
	 * @return 未来である場合は{@code true}、そうでない場合は{@code false}
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 */
	public boolean isBefore(CalendarMinute other) {
		Validate.notNull(other);
		if (date.isBefore(other.date)) {
			return true;
		}
		if (date.isAfter(other.date)) {
			return false;
		}
		return time.isBefore(other.time);
	}
	
	@Override
	public String toString() {
		return date.toString() + " at " + time.toString();
	}
	
	/**
	 * この年月日時分を、指定したパターンで整形し、その文字列表現を取得する。
	 * 
	 * @param pattern {@link SimpleDateFormat}に基づくパターン
	 * @param zone タイムゾーン
	 * @return 整形済み時間文字列
	 */
	public String toString(String pattern, TimeZone zone) {
		TimePoint point = asTimePoint(zone);
		return point.toString(pattern, zone);
	}
}
