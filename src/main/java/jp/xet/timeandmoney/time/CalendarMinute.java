/**
 * Copyright (c) 2005 Domain Language, Inc. (http://domainlanguage.com) This
 * free software is distributed under the "MIT" licence. See file licence.txt.
 * For more information, see http://timeandmoney.sourceforge.net.
 */
package jp.xet.timeandmoney.time;

import java.util.Date;
import java.util.TimeZone;

import org.apache.commons.lang.Validate;

/**
 * カレンダー上の特定の「年月日時分」を表すクラス。
 * 
 * <p>{@link Date}と異なり、分未満（秒以下）の概念を持っていない。また、{@link TimePoint}と異なり、
 * その分1分間全ての範囲を表すクラスであり、特定の瞬間をモデリングしたものではない。</p>
 * 
 * @author daisuke
 */
public class CalendarMinute {
	
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
	 */
	public static CalendarMinute dateHourAndMinute(int year, int month, int day, int hour, int minute) {
		return new CalendarMinute(CalendarDate.from(year, month, day), TimeOfDay.hourAndMinute(hour, minute));
	}
	

	private final CalendarDate date;
	
	private final TimeOfDay time;
	

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
		return TimePoint.at(date.breachEncapsulationOfYear(), date.breachEncapsulationOfMonth(),
				date.breachEncapsulationOfDay(), time.breachEncapsulationOfHour(), time.breachEncapsulationOfMinute(),
				0, 0, timeZone);
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
		CalendarMinute other = (CalendarMinute) obj;
		if (date == null) {
			if (other.date != null) {
				return false;
			}
		} else if (date.equals(other.date) == false) {
			return false;
		}
		if (time == null) {
			if (other.time != null) {
				return false;
			}
		} else if (time.equals(other.time) == false) {
			return false;
		}
		return true;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((time == null) ? 0 : time.hashCode());
		return result;
	}
	
	@Override
	public String toString() {
		return date.toString() + " at " + time.toString();
	}
}
