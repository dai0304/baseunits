/**
 * Copyright (c) 2005 Domain Language, Inc. (http://domainlanguage.com) This
 * free software is distributed under the "MIT" licence. See file licence.txt.
 * For more information, see http://timeandmoney.sourceforge.net.
 */
package com.domainlanguage.time;

import java.util.Date;
import java.util.TimeZone;

/**
 * 特定の「時分」を表すクラス。
 * 
 * <p>{@link Date}と異なり、日付の概念を持っていない。</p>
 * 
 * @author daisuke
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
	

	private HourOfDay hour;
	
	private MinuteOfHour minute;
	

	/**
	 * Only for use by persistence mapping frameworks
	 * <rant>These methods break encapsulation and we put them in here begrudgingly</rant>
	 */
	TimeOfDay() {
	}
	
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
	 */
	public TimePoint asTimePointGiven(CalendarDate date, TimeZone timeZone) {
		CalendarMinute timeOfDayOnDate = on(date);
		return timeOfDayOnDate.asTimePoint(timeZone);
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
		TimeOfDay other = (TimeOfDay) obj;
		if (hour == null) {
			if (other.hour != null) {
				return false;
			}
		} else if (hour.equals(other.hour) == false) {
			return false;
		}
		if (minute == null) {
			if (other.minute != null) {
				return false;
			}
		} else if (minute.equals(other.minute) == false) {
			return false;
		}
		return true;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((hour == null) ? 0 : hour.hashCode());
		result = prime * result + ((minute == null) ? 0 : minute.hashCode());
		return result;
	}
	
	/**
	 * このインスタンスがあらわす時分が、指定した時分よりも未来であるかどうか調べる。
	 * 
	 * <p>等価の場合は{@code false}を返す。</p>
	 * 
	 * @param another 基準時分
	 * @return 未来である場合は{@code true}、そうでない場合は{@code false}
	 */
	public boolean isAfter(TimeOfDay another) {
		return hour.isAfter(another.hour) || (hour.equals(another.hour) && minute.isAfter(another.minute));
	}
	
	/**
	 * このインスタンスがあらわす時分が、指定した時分よりも過去であるかどうか調べる。
	 * 
	 * <p>等価の場合は{@code false}を返す。</p>
	 * 
	 * @param another 基準時分
	 * @return 過去である場合は{@code true}、そうでない場合は{@code false}
	 */
	public boolean isBefore(TimeOfDay another) {
		return hour.isBefore(another.hour) || (hour.equals(another.hour) && minute.isBefore(another.minute));
	}
	
	/**
	 * 指定した年月日における、このインスタンスがあらわす時分について {@link CalendarMinute} 型のインスタンスを返す。
	 * 
	 * @param date 年月日
	 * @return {@link CalendarMinute}
	 */
	public CalendarMinute on(CalendarDate date) {
		return CalendarMinute.dateAndTimeOfDay(date, this);
	}
	
	@Override
	public String toString() {
		return hour.toString() + ":" + minute.toString();
	}
	
	int breachEncapsulationOf_hour() { // CHECKSTYLE IGNORE THIS LINE
		return hour.value();
	}
	
	int breachEncapsulationOf_minute() { // CHECKSTYLE IGNORE THIS LINE
		return minute.value();
	}
	
	HourOfDay hour() {
		return hour;
	}
	
	MinuteOfHour minute() {
		return minute;
	}
	
	/**
	 * Only for use by persistence mapping frameworks
	 * <rant>These methods break encapsulation and we put them in here begrudgingly</rant>
	 * @return value of hour
	 */
	@SuppressWarnings("unused")
	private int getForPersistentMapping_Hour() { // CHECKSTYLE IGNORE THIS LINE
		return hour.value();
	}
	
	/**
	 * Only for use by persistence mapping frameworks
	 * <rant>These methods break encapsulation and we put them in here begrudgingly</rant>
	 * @return value of minute
	 */
	@SuppressWarnings("unused")
	private int getForPersistentMapping_Minute() { // CHECKSTYLE IGNORE THIS LINE
		return minute.value();
	}
	
	/**
	 * Only for use by persistence mapping frameworks
	 * <rant>These methods break encapsulation and we put them in here begrudgingly</rant>
	 * @param hour value of hour
	 */
	@SuppressWarnings("unused")
	private void setForPersistentMapping_Hour(int hour) { // CHECKSTYLE IGNORE THIS LINE
		this.hour = HourOfDay.of(hour);
	}
	
	/**
	 * Only for use by persistence mapping frameworks
	 * <rant>These methods break encapsulation and we put them in here begrudgingly</rant>
	 * @param minute value of minute
	 */
	@SuppressWarnings("unused")
	private void setForPersistentMapping_Minute(int minute) { // CHECKSTYLE IGNORE THIS LINE
		this.minute = MinuteOfHour.of(minute);
	}
}
