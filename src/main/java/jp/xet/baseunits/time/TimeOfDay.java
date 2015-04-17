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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import com.google.common.base.Preconditions;

/**
 * 1日の中の特定の瞬間を表すクラス。
 * 
 * <p>{@link java.util.Date}と異なり、暦日の概念を持っていない。またタイムゾーンの概念もない。</p>
 * 
 * @author daisuke
 * @since 1.0
 */
@SuppressWarnings("serial")
public final class TimeOfDay implements Comparable<TimeOfDay>, Serializable {
	
	/** 
	 * {@link TimeOfDay}の最小値
	 * 
	 * @since 2.0
	 */
	public static final TimeOfDay MIN = from(0, 0, 0, 0);
	
	/**
	 * {@link TimeOfDay}の最大値
	 * 
	 * @since 2.0
	 */
	public static final TimeOfDay MAX = from(23, 59, 59, 999);
	
	/**
	 * 深夜0時
	 * 
	 * @since 2.0
	 */
	public static final TimeOfDay MIDNIGHT = from(0, 0, 0, 0);
	
	/**
	 * 正午
	 * 
	 * @since 2.0
	 */
	public static final TimeOfDay NOON = from(12, 0, 0, 0);
	
	
	/**
	 * 指定した瞬間を表す、{@link TimeOfDay}を返す。
	 * 
	 * <p>秒及びミリ秒は{@code 0}として解釈する。</p>
	 * 
	 * @param hour 時
	 * @param minute 分
	 * @return {@link TimeOfDay}
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	public static TimeOfDay from(HourOfDay hour, MinuteOfHour minute) {
		return from(hour, minute, SecondOfMinute.MIN, MillisecOfSecond.MIN);
	}
	
	/**
	 * 指定した瞬間を表す、{@link TimeOfDay}を返す。
	 * 
	 * <p>ミリ秒は{@code 0}として解釈する。</p>
	 * 
	 * @param hour 時
	 * @param minute 分
	 * @param second 秒
	 * @return {@link TimeOfDay}
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	public static TimeOfDay from(HourOfDay hour, MinuteOfHour minute, SecondOfMinute second) {
		return from(hour, minute, second, MillisecOfSecond.MIN);
	}
	
	/**
	 * 指定した瞬間を表す、{@link TimeOfDay}を返す。
	 * 
	 * @param hour 時
	 * @param minute 分
	 * @param second 秒
	 * @param millisec ミリ秒
	 * @return {@link TimeOfDay}
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	public static TimeOfDay from(HourOfDay hour, MinuteOfHour minute, SecondOfMinute second, MillisecOfSecond millisec) {
		return new TimeOfDay(hour, minute, second, millisec);
	}
	
	/**
	 * 指定した瞬間を表す、{@link TimeOfDay}を返す。
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
	 * 指定した瞬間を表す、{@link TimeOfDay}を返す。
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
	 * 指定した瞬間を表す、{@link TimeOfDay}を返す。
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
	
	/**
	 * UTCの午前0時を基準としたミリ秒を表す、{@link TimeOfDay}を返す。
	 * 
	 * @param millisec UTCの午前0時を基準としたミリ秒
	 * @return {@link TimeOfDay}
	 */
	public static TimeOfDay from(long millisec) {
		Duration duration = Duration.milliseconds(millisec);
		
		long h = duration.to(TimeUnit.hour);
		Duration minDuration = duration.minus(Duration.hours(h));
		long m = minDuration.to(TimeUnit.minute);
		Duration secDuration = minDuration.minus(Duration.minutes(m));
		long s = secDuration.to(TimeUnit.second);
		Duration millisecDuration = secDuration.minus(Duration.seconds(s));
		long ms = millisecDuration.to(TimeUnit.millisecond);
		return TimeOfDay.from((int) h, (int) m, (int) s, (int) ms);
	}
	
	/**
	 * {@link TimePointOfDay}を{@link TimeOfDay}に変換して返す。
	 * 
	 * @param tpod {@link TimePointOfDay}
	 * @param zone タイムゾーン
	 * @return {@link TimeOfDay}
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 */
	public static TimeOfDay from(TimePointOfDay tpod, TimeZone zone) {
		Preconditions.checkNotNull(tpod);
		Preconditions.checkNotNull(zone);
		return tpod.asTimeOfDay(zone);
	}
	
	/**
	 * Parses text strings to produce instances of {@link TimeOfDay}
	 * 
	 * @param timeString the text string whose beginning should be parsed.
	 * @param pattern the pattern string
	 * @return the parsed result
	 * @throws ParseException if fail to parse
	 * @since 2.0
	 */
	public static TimeOfDay parse(String timeString, String pattern) throws ParseException {
		Preconditions.checkNotNull(timeString);
		Preconditions.checkNotNull(pattern);
		TimeZone arbitraryZone = TimeZone.getTimeZone("Universal");
		// Any timezone works, as long as the same one is used throughout.
		TimePoint point = TimePoint.parse(timeString, pattern, arbitraryZone);
		return point.asTimeOfDay(arbitraryZone);
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
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 */
	TimeOfDay(HourOfDay hour, MinuteOfHour minute, SecondOfMinute second, MillisecOfSecond millisec) {
		Preconditions.checkNotNull(hour);
		Preconditions.checkNotNull(minute);
		Preconditions.checkNotNull(second);
		Preconditions.checkNotNull(millisec);
		this.hour = hour;
		this.minute = minute;
		this.second = second;
		this.millisec = millisec;
	}
	
	/**
	 * 指定した暦日とタイムゾーンにおける、この時刻の {@link TimePoint} を返す。
	 * 
	 * @param date 暦日
	 * @param timeZone タイムゾーン
	 * @return 瞬間
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	public TimePoint asTimePointGiven(CalendarDate date, TimeZone timeZone) {
		Preconditions.checkNotNull(date);
		Preconditions.checkNotNull(timeZone);
		return TimePoint.from(date, this, timeZone);
	}
	
	/**
	 * {@link TimePointOfDay}に変換する。
	 * 
	 * @param timeZone タイムゾーン
	 * @return {@link TimePointOfDay}
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @since 2.0
	 */
	public TimePointOfDay asTimePointOfDay(TimeZone timeZone) {
		Preconditions.checkNotNull(timeZone);
		return TimePointOfDay.from(this, timeZone);
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
	 * この瞬間が、指定した瞬間よりも未来であるかどうか調べる。
	 * 
	 * <p>{@code other} が {@code null} である場合は {@code false} を返す。
	 * また、同一である場合は {@code false} を返す。</p>
	 * 
	 * @param other 基準瞬間
	 * @return 未来である場合は{@code true}、そうでない場合は{@code false}
	 * @since 1.0
	 */
	public boolean isAfter(TimeOfDay other) {
		if (other == null) {
			return false;
		}
		if (hour.isAfter(other.hour)) {
			return true;
		}
		if (hour.equals(other.hour)) {
			if (minute.isAfter(other.minute)) {
				return true;
			}
			if (minute.equals(other.minute)) {
				if (second.isAfter(other.second)) {
					return true;
				}
				if (second.equals(other.second)) {
					if (millisec.isAfter(other.millisec)) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * この瞬間が、指定した瞬間よりも過去であるかどうか調べる。
	 * 
	 * <p>{@code other} が {@code null} である場合は {@code false} を返す。
	 * また、同一である場合は {@code false} を返す。</p>
	 * 
	 * @param other 比較対象瞬間
	 * @return 過去である場合は{@code true}、そうでない場合は{@code false}
	 * @since 1.0
	 */
	public boolean isBefore(TimeOfDay other) {
		if (other == null) {
			return false;
		}
		return hour.isBefore(other.hour) || (hour.equals(other.hour) && minute.isBefore(other.minute));
	}
	
	/**
	 * この時刻から、指定した時間の長さ分過去の時刻を取得する。
	 * 
	 * @param duration 時間の長さ
	 * @return 過去の時刻
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @since 2.0
	 */
	public TimeOfDay minus(Duration duration) {
		Preconditions.checkNotNull(duration);
		Duration d = toDuration();
		while (d.isGreaterThan(duration) == false) {
			d = d.plus(Duration.days(1));
		}
		d = d.minus(duration);
		return TimeOfDay.MIN.plus(d);
	}
	
	/**
	 * この時刻から、指定した時間の長さ分未来の時刻を取得する。
	 * 
	 * @param duration 時間の長さ
	 * @return 未来の時刻
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @since 2.0
	 */
	public TimeOfDay plus(Duration duration) {
		Preconditions.checkNotNull(duration);
		Duration total = toDuration().plus(duration);
		while (total.isGreaterThanOrEqual(Duration.days(1))) {
			total = total.minus(Duration.days(1));
		}
		return from(total.to(TimeUnit.millisecond));
	}
	
	/**
	 * 深夜午前0時からこの瞬間までの時間量を返す。
	 * 
	 * @return 時間量
	 */
	public Duration toDuration() {
		Duration d = Duration.hours(hour.value);
		d = d.plus(Duration.minutes(minute.value));
		d = d.plus(Duration.seconds(second.value));
		d = d.plus(Duration.milliseconds(millisec.value));
		return d;
	}
	
	@Override
	public String toString() {
		return hour.toString() + ":" + minute.toString() + ":" + second.toString() + "," + millisec.toString();
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
		TimePoint point = asTimePointGiven(TimePoint.from(0).asCalendarDate(arbitraryZone), arbitraryZone);
		return point.toString(pattern, arbitraryZone);
	}
}
