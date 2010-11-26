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

import java.util.TimeZone;

import org.apache.commons.lang.Validate;

/**
 * 1日の中の特定の「時分」を表すクラス。
 * 
 * <p>{@link java.util.Date}と異なり、日付の概念を持っていない。またタイムゾーンの概念もない。</p>
 */
public class TimeOfDay {
	
	/**
	 * 指定した時分を表す、{@link TimeOfDay}のインスタンスを生成する。
	 * 
	 * @param hour 時をあらわす正数（0〜23）
	 * @param minute 分をあらわす正数（0〜59）
	 * @return {@link TimeOfDay}
	 * @throws IllegalArgumentException 引数{@code hour}が0〜23の範囲ではない場合
	 * @throws IllegalArgumentException 引数{@code minute}が0〜59の範囲ではない場合
	 */
	public static TimeOfDay hourAndMinute(int hour, int minute) {
		return new TimeOfDay(hour, minute);
	}
	

	final HourOfDay hour;
	
	final MinuteOfHour minute;
	

	/**
	 * インスタンスを生成する。
	 * 
	 * @param hour 時をあらわす正数（0〜23）
	 * @param minute 分をあらわす正数（0〜59）
	 * @throws IllegalArgumentException 引数{@code hour}の値が0〜23の範囲ではない場合
	 * @throws IllegalArgumentException 引数{@code minute}の値が0〜59の範囲ではない場合
	 */
	private TimeOfDay(int hour, int minute) {
		this.hour = HourOfDay.of(hour);
		this.minute = MinuteOfHour.of(minute);
	}
	
	/**
	 * 指定した年月日とタイムゾーンにおける、このインスタンスがあらわす時分の0秒0ミリ秒の瞬間について {@link TimePoint} 型のインスタンスを返す。
	 * 
	 * @param date 年月日
	 * @param timeZone タイムゾーン
	 * @return 瞬間
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 */
	public TimePoint asTimePointGiven(CalendarDate date, TimeZone timeZone) {
		Validate.notNull(date);
		Validate.notNull(timeZone);
		CalendarMinute timeOfDayOnDate = on(date);
		return timeOfDayOnDate.asTimePoint(timeZone);
	}
	
	@Override
	public boolean equals(Object obj) {
		assert hour != null;
		assert minute != null;
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		TimeOfDay other = (TimeOfDay) obj;
		if (hour.equals(other.hour) == false) {
			return false;
		}
		if (minute.equals(other.minute) == false) {
			return false;
		}
		return true;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + hour.hashCode();
		result = prime * result + minute.hashCode();
		return result;
	}
	
	/**
	 * このインスタンスがあらわす時分が、指定した時分よりも未来であるかどうか調べる。
	 * 
	 * <p>等価の場合は{@code false}を返す。</p>
	 * 
	 * @param another 基準時分
	 * @return 未来である場合は{@code true}、そうでない場合は{@code false}
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 */
	public boolean isAfter(TimeOfDay another) {
		Validate.notNull(another);
		return hour.isAfter(another.hour) || (hour.equals(another.hour) && minute.isAfter(another.minute));
	}
	
	/**
	 * このインスタンスがあらわす時分が、指定した時分よりも過去であるかどうか調べる。
	 * 
	 * <p>等価の場合は{@code false}を返す。</p>
	 * 
	 * @param another 基準時分
	 * @return 過去である場合は{@code true}、そうでない場合は{@code false}
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 */
	public boolean isBefore(TimeOfDay another) {
		Validate.notNull(another);
		return hour.isBefore(another.hour) || (hour.equals(another.hour) && minute.isBefore(another.minute));
	}
	
	/**
	 * 指定した年月日における、このインスタンスがあらわす時分について {@link CalendarMinute} 型のインスタンスを返す。
	 * 
	 * @param date 年月日
	 * @return {@link CalendarMinute}
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 */
	public CalendarMinute on(CalendarDate date) {
		Validate.notNull(date);
		return CalendarMinute.dateAndTimeOfDay(date, this);
	}
	
	@Override
	public String toString() {
		return hour.toString() + ":" + minute.toString();
	}
	
	int breachEncapsulationOfHour() {
		return hour.breachEncapsulationOfValue();
	}
	
	int breachEncapsulationOfMinute() {
		return minute.breachEncapsulationOfValue();
	}
	
	HourOfDay hour() {
		return hour;
	}
	
	MinuteOfHour minute() {
		return minute;
	}
}
