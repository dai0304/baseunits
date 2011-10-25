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
import java.util.TimeZone;

import org.apache.commons.lang.Validate;

/**
 * 1日の中の特定の瞬間を表すクラス。
 * 
 * <p>{@link java.util.Date}と異なり、日付の概念を持っていない。またタイムゾーンの概念もない。</p>
 * 
 * @since 1.0
 */
@SuppressWarnings("serial")
public final class TimeOfDay implements Comparable<TimeOfDay>, Serializable {
	
	/**
	 * 指定した瞬間を表す、{@link TimeOfDay}のインスタンスを生成する。
	 * 
	 * <p>秒及びミリ秒は{@code 0}として解釈する。</p>
	 * 
	 * @param hour 時
	 * @param minute 分
	 * @return {@link TimeOfDay}
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	public static TimeOfDay from(HourOfDay hour, MinuteOfHour minute) {
		return from(hour, minute, SecondOfMinute.valueOf(0), MillisecOfSecond.valueOf(0));
	}
	
	/**
	 * 指定した瞬間を表す、{@link TimeOfDay}のインスタンスを生成する。
	 * 
	 * <p>ミリ秒は{@code 0}として解釈する。</p>
	 * 
	 * @param hour 時
	 * @param minute 分
	 * @param second 秒
	 * @return {@link TimeOfDay}
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	public static TimeOfDay from(HourOfDay hour, MinuteOfHour minute, SecondOfMinute second) {
		return from(hour, minute, second, MillisecOfSecond.valueOf(0));
	}
	
	/**
	 * 指定した瞬間を表す、{@link TimeOfDay}のインスタンスを生成する。
	 * 
	 * @param hour 時
	 * @param minute 分
	 * @param second 秒
	 * @param millisec ミリ秒
	 * @return {@link TimeOfDay}
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	public static TimeOfDay from(HourOfDay hour, MinuteOfHour minute, SecondOfMinute second, MillisecOfSecond millisec) {
		return new TimeOfDay(hour, minute, second, millisec);
	}
	
	/**
	 * 指定した瞬間を表す、{@link TimeOfDay}のインスタンスを生成する。
	 * 
	 * <p>秒及びミリ秒は{@code 0}として解釈する。</p>
	 * 
	 * @param hour 時をあらわす正数（0〜23）
	 * @param minute 分をあらわす正数（0〜59）
	 * @return {@link TimeOfDay}
	 * @throws IllegalArgumentException 引数{@code hour}が0〜23の範囲ではない場合
	 * @throws IllegalArgumentException 引数{@code minute}が0〜59の範囲ではない場合
	 * @since 1.0
	 */
	public static TimeOfDay from(int hour, int minute) {
		return from(hour, minute, 0, 0);
	}
	
	/**
	 * 指定した瞬間を表す、{@link TimeOfDay}のインスタンスを生成する。
	 * 
	 * <p>ミリ秒は{@code 0}として解釈する。</p>
	 * 
	 * @param hour 時をあらわす正数（0〜23）
	 * @param minute 分をあらわす正数（0〜59）
	 * @param second 秒をあらわす正数（0〜59）
	 * @return {@link TimeOfDay}
	 * @throws IllegalArgumentException 引数{@code hour}が0〜23の範囲ではない場合
	 * @throws IllegalArgumentException 引数{@code minute}, {@code second}が0〜59の範囲ではない場合
	 * @since 1.0
	 */
	public static TimeOfDay from(int hour, int minute, int second) {
		return from(hour, minute, second, 0);
	}
	
	/**
	 * 指定した瞬間を表す、{@link TimeOfDay}のインスタンスを生成する。
	 * 
	 * @param hour 時をあらわす正数（0〜23）
	 * @param minute 分をあらわす正数（0〜59）
	 * @param second 秒をあらわす正数（0〜59）
	 * @param millisecond ミリ秒をあらわす正数（0〜999）
	 * @return {@link TimeOfDay}
	 * @throws IllegalArgumentException 引数{@code hour}が0〜23の範囲ではない場合
	 * @throws IllegalArgumentException 引数{@code minute}, {@code second}が0〜59の範囲ではない場合
	 * @throws IllegalArgumentException 引数{@code millisecond}が0〜999の範囲ではない場合
	 * @since 1.0
	 */
	public static TimeOfDay from(int hour, int minute, int second, int millisecond) {
		return new TimeOfDay(HourOfDay.valueOf(hour), MinuteOfHour.valueOf(minute), SecondOfMinute.valueOf(second),
				MillisecOfSecond.valueOf(millisecond));
	}
	
	
	/** 時 */
	final HourOfDay hour;
	
	/** 分 */
	final MinuteOfHour minute;
	
	/** 秒 */
	final SecondOfMinute second;
	
	/** ミリ秒 */
	final MillisecOfSecond millisec;
	
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param hour 時
	 * @param minute 分
	 * @param second 秒
	 * @param millisec ミリ秒
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 */
	TimeOfDay(HourOfDay hour, MinuteOfHour minute, SecondOfMinute second, MillisecOfSecond millisec) {
		Validate.notNull(hour);
		Validate.notNull(minute);
		Validate.notNull(second);
		Validate.notNull(millisec);
		this.hour = hour;
		this.minute = minute;
		this.second = second;
		this.millisec = millisec;
	}
	
	/**
	 * 指定した年月日とタイムゾーンにおける、このインスタンスがあらわす瞬間について {@link TimePoint} 型のインスタンスを返す。
	 * 
	 * @param date 年月日
	 * @param timeZone タイムゾーン
	 * @return 瞬間
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	public TimePoint asTimePointGiven(CalendarDate date, TimeZone timeZone) {
		Validate.notNull(date);
		Validate.notNull(timeZone);
		return TimePoint.from(date, this, timeZone);
	}
	
	/**
	 * このオブジェクトの{@link #hour}フィールド（時）を返す。
	 * 
	 * <p>CAUTION: このメソッドは、このオブジェクトがカプセル化する要素を外部に暴露する。取り扱いには充分注意のこと。</p>
	 * 
	 * @return 時
	 * @since 1.0
	 */
	public HourOfDay breachEncapsulationOfHour() {
		return hour;
	}
	
	/**
	 * このオブジェクトの{@link #minute}フィールド（分）を返す。
	 * 
	 * <p>CAUTION: このメソッドは、このオブジェクトがカプセル化する要素を外部に暴露する。取り扱いには充分注意のこと。</p>
	 * 
	 * @return 分
	 * @since 1.0
	 */
	public MinuteOfHour breachEncapsulationOfMinute() {
		return minute;
	}
	
	@Override
	public int compareTo(TimeOfDay other) {
		int hourComparance = hour.compareTo(other.hour);
		if (hourComparance != 0) {
			return hourComparance;
		}
		return minute.compareTo(other.minute);
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
	 * このインスタンスがあらわす瞬間が、指定した瞬間よりも未来であるかどうか調べる。
	 * 
	 * <p>等価の場合は{@code false}を返す。</p>
	 * 
	 * @param another 基準瞬間
	 * @return 未来である場合は{@code true}、そうでない場合は{@code false}
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	public boolean isAfter(TimeOfDay another) {
		Validate.notNull(another);
		if (hour.isAfter(another.hour)) {
			return true;
		}
		if (hour.equals(another.hour)) {
			if (minute.isAfter(another.minute)) {
				return true;
			}
			if (minute.equals(another.minute)) {
				if (second.isAfter(another.second)) {
					return true;
				}
				if (second.equals(another.second)) {
					if (millisec.isAfter(another.millisec)) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * このインスタンスがあらわす瞬間が、指定した瞬間よりも過去であるかどうか調べる。
	 * 
	 * <p>等価の場合は{@code false}を返す。</p>
	 * 
	 * @param another 基準瞬間
	 * @return 過去である場合は{@code true}、そうでない場合は{@code false}
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	public boolean isBefore(TimeOfDay another) {
		Validate.notNull(another);
		return hour.isBefore(another.hour) || (hour.equals(another.hour) && minute.isBefore(another.minute));
	}
	
	/**
	 * 深夜午前0時からこの瞬間までの時間量を返す。
	 * 
	 * @return 時間量
	 */
	public Duration toDuration() {
		Duration d = Duration.hours(hour.breachEncapsulationOfValue());
		d = d.plus(Duration.minutes(minute.breachEncapsulationOfValue()));
		d = d.plus(Duration.seconds(second.breachEncapsulationOfValue()));
		d = d.plus(Duration.milliseconds(millisec.breachEncapsulationOfValue()));
		return d;
	}
	
	@Override
	public String toString() {
		return hour.toString() + ":" + minute.toString() + ":" + second.toString() + "." + millisec.toString();
	}
	
	/**
	 * この瞬間を、指定したパターンで整形し、その文字列表現を取得する。
	 * 
	 * @param pattern {@link SimpleDateFormat}に基づくパターン
	 * @return 整形済み瞬間文字列
	 * @since 1.0
	 */
	public String toString(String pattern) {
		// Any timezone works, as long as the same one is used throughout.
		TimeZone arbitraryZone = TimeZone.getTimeZone("Universal");
		TimePoint point = asTimePointGiven(TimePoint.from(0).calendarDate(arbitraryZone), arbitraryZone);
		return point.toString(pattern, arbitraryZone);
	}
}
