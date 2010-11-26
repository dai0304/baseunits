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
 * Copyright (c) 2005 Domain Language, Inc. (http://domainlanguage.com) This
 * free software is distributed under the "MIT" licence. See file licence.txt.
 * For more information, see http://timeandmoney.sourceforge.net.
 */
package jp.xet.timeandmoney.time;

import java.io.Serializable;
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
	public static CalendarMinute dateAndTimeOfDay(CalendarDate aDate, TimeOfDay aTime) {
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
	public static CalendarMinute dateHourAndMinute(int year, int month, int day, int hour, int minute) {
		return new CalendarMinute(CalendarDate.from(year, month, day), TimeOfDay.hourAndMinute(hour, minute));
	}
	

	final CalendarDate date;
	
	final TimeOfDay time;
	

	private CalendarMinute(CalendarDate date, TimeOfDay time) {
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
	
	@Override
	public int compareTo(CalendarMinute arg0) {
		// TODO Auto-generated method stub
		return 0;
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
	
	@Override
	public String toString() {
		return date.toString() + " at " + time.toString();
	}
	
	CalendarDate breachEncapsulationOfDate() {
		return date;
	}
	
	TimeOfDay breachEncapsulationOfTime() {
		return time;
	}
}
