/*
 * Copyright 2011 Daisuke Miyamoto. (http://d.hatena.ne.jp/daisuke-m)
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
 */
package jp.xet.baseunits.time;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import jp.xet.baseunits.time.HourOfDay.Meridian;

import org.apache.commons.lang.Validate;

/**
 * ミリ秒精度で、ある1日の特定の瞬間をあらわすクラス。
 * 
 * <p>{@link TimeOfDay}と異なり、タイムゾーン情報を含んでいる。</p>
 * 
 * @since 2.0
 */
@SuppressWarnings("serial")
public class TimePointOfDay implements Comparable<TimePointOfDay>, Serializable {
	
	private static final TimeZone GMT = TimeZone.getTimeZone("Universal");
	
	
	/**
	 * 指定したタイムゾーンにおける、指定した日時を表すインスタンスを取得する。
	 * 
	 * @param hour 時
	 * @param minute 分
	 * @param second 秒
	 * @param millisecond ミリ秒
	 * @param zone タイムゾーン
	 * @return {@link TimePointOfDay}
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @since 2.0
	 */
	public static TimePointOfDay at(int hour, int minute, int second, int millisecond,
			TimeZone zone) {
		Validate.notNull(zone);
		Calendar calendar = CalendarUtil.newCalendar(zone);
		calendar.set(Calendar.HOUR_OF_DAY, hour);
		calendar.set(Calendar.MINUTE, minute);
		calendar.set(Calendar.SECOND, second);
		calendar.set(Calendar.MILLISECOND, millisecond);
		return valueOf(calendar);
	}
	
	/**
	 * 指定したタイムゾーンにおける、指定した日時を表すインスタンスを取得する。
	 * 
	 * @param hour 時
	 * @param minute 分
	 * @param second 秒
	 * @param zone タイムゾーン
	 * @return {@link TimePointOfDay}
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @since 2.0
	 */
	public static TimePointOfDay at(int hour, int minute, int second, TimeZone zone) {
		Validate.notNull(zone);
		return at(hour, minute, second, 0, zone);
	}
	
	/**
	 * 指定したタイムゾーンにおける、指定した日時を表すインスタンスを取得する。
	 * 
	 * @param hour 時
	 * @param minute 分
	 * @param zone タイムゾーン
	 * @return {@link TimePointOfDay}
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @since 2.0
	 */
	public static TimePointOfDay at(int hour, int minute, TimeZone zone) {
		Validate.notNull(zone);
		return at(hour, minute, 0, 0, zone);
	}
	
	/**
	 * 世界標準時における、指定した日時を表すインスタンスを取得する。
	 * 
	 * @param hour 時
	 * @param meridian 午前/午後
	 * @param minute 分
	 * @param second 秒
	 * @param millisecond ミリ秒
	 * @return {@link TimePointOfDay}
	 * @throws IllegalArgumentException 引数{@code hour}の値が0〜11の範囲ではない場合
	 * @throws IllegalArgumentException 引数{@code meridian}の値が {@code "AM"} または {@code "PM"} ではない場合
	 * @since 2.0
	 */
	public static TimePointOfDay at12hr(int hour, Meridian meridian, int minute, int second, // CHECKSTYLE IGNORE THIS LINE
			int millisecond) {
		return at(HourOfDay.convertTo24hour(hour, meridian), minute, second, millisecond, GMT);
	}
	
	/**
	 * 指定したタイムゾーンにおける、指定した日時を表すインスタンスを取得する。
	 * 
	 * @param hour 時
	 * @param meridian 午前/午後
	 * @param minute 分
	 * @param second 秒
	 * @param millisecond ミリ秒
	 * @param zone タイムゾーン
	 * @return {@link TimePointOfDay}
	 * @throws IllegalArgumentException 引数{@code hour}の値が0〜11の範囲ではない場合
	 * @throws IllegalArgumentException 引数{@code zone}または{@code meridian}に{@code null}を与えた場合
	 * @since 2.0
	 */
	public static TimePointOfDay at12hr(int hour, Meridian meridian, int minute, int second, // CHECKSTYLE IGNORE THIS LINE
			int millisecond, TimeZone zone) {
		Validate.notNull(zone);
		return at(HourOfDay.convertTo24hour(hour, meridian), minute, second, millisecond, zone);
	}
	
	/**
	 * 世界標準時における、指定した日時を表すインスタンスを取得する。
	 * 
	 * @param hour 時
	 * @param minute 分
	 * @return {@link TimePointOfDay}
	 * @since 2.0
	 */
	public static TimePointOfDay atGMT(int hour, int minute) {
		return atGMT(hour, minute, 0, 0);
	}
	
	/**
	 * 世界標準時における、指定した日時を表すインスタンスを取得する。
	 * 
	 * @param hour 時
	 * @param minute 分
	 * @param second 秒
	 * @return {@link TimePointOfDay}
	 * @since 2.0
	 */
	public static TimePointOfDay atGMT(int hour, int minute, int second) {
		return atGMT(hour, minute, second, 0);
	}
	
	/**
	 * 世界標準時における、指定した日時を表すインスタンスを取得する。
	 * 
	 * @param hour 時
	 * @param minute 分
	 * @param second 秒
	 * @param millisecond ミリ秒
	 * @return {@link TimePointOfDay}
	 * @since 2.0
	 */
	public static TimePointOfDay atGMT(int hour, int minute, int second, int millisecond) {
		return at(hour, minute, second, millisecond, GMT);
	}
	
	/**
	 * 指定したタイムゾーンにおける、指定した日時の午前0時（深夜）を表すインスタンスを取得する。
	 * 
	 * @param zone タイムゾーン
	 * @return {@link TimePointOfDay}
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @since 2.0
	 */
	public static TimePointOfDay atMidnight(TimeZone zone) {
		Validate.notNull(zone);
		return at(0, 0, 0, 0, zone);
	}
	
	/**
	 * 世界標準時における、指定した日付の午前0時（深夜）を表すインスタンスを取得する。
	 * 
	 * @return {@link TimePointOfDay}
	 * @since 2.0
	 */
	public static TimePointOfDay atMidnightGMT() {
		return atMidnight(GMT);
	}
	
	/**
	 * {@link Date}を{@link TimePointOfDay}に変換する。
	 * 
	 * @param javaDate 元となる日時情報を表す {@link Date}インスタンス
	 * @return {@link TimePointOfDay}
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @since 2.0
	 */
	public static TimePointOfDay from(Date javaDate) {
		Validate.notNull(javaDate);
		return from(javaDate.getTime());
	}
	
	/**
	 * GMTにおける深夜0時からの経過ミリ秒を {@link TimePointOfDay} に変換する。
	 * 
	 * @param milliseconds エポックからの経過ミリ秒
	 * @return {@link TimePointOfDay}
	 * @since 2.0
	 */
	public static TimePointOfDay from(long milliseconds) {
		TimePointOfDay result = new TimePointOfDay(milliseconds);
		//assert FAR_FUTURE == null || result.isBefore(FAR_FUTURE);
		//assert FAR_PAST == null || result.isAfter(FAR_PAST);
		return result;
	}
	
	/**
	 * 指定したタイムゾーンにおける、時刻を表すインスタンスを取得する。
	 * 
	 * @param time 時間
	 * @param zone タイムゾーン
	 * @return {@link TimePointOfDay}
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @since 2.0
	 */
	public static TimePointOfDay from(TimeOfDay time, TimeZone zone) {
		Validate.notNull(time);
		Validate.notNull(zone);
		TimePoint tp = CalendarDate.EPOCH_DATE.at(time, zone);
		return TimePointOfDay.from(tp.toEpochMillisec());
	}
	
	/**
	 * 日時を表す文字列を、指定したパターンで指定したタイムゾーンとして解析し、その日時を表す {@link TimePointOfDay}を返す。
	 * 
	 * @param dateTimeString 日時を表す文字列
	 * @param pattern 解析パターン（{@link SimpleDateFormat}参照）
	 * @param zone タイムゾーン
	 * @return {@link TimePointOfDay}
	 * @throws ParseException 文字列の解析に失敗した場合
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @since 2.0
	 */
	public static TimePointOfDay parse(String dateTimeString, String pattern, TimeZone zone) throws ParseException {
		Validate.notNull(dateTimeString);
		Validate.notNull(pattern);
		Validate.notNull(zone);
		SimpleDateFormat format = CalendarUtil.newSimpleDateFormat(pattern, zone);
		Date date = format.parse(dateTimeString);
		return from(date);
	}
	
	/**
	 * 日時を表す文字列を、指定したパターンで世界標準時として解析し、その日時を表す {@link TimePointOfDay}を返す。
	 * 
	 * @param dateString 日時を表す文字列
	 * @param pattern 解析パターン（{@link SimpleDateFormat}参照）
	 * @return {@link TimePointOfDay}
	 * @throws ParseException 文字列の解析に失敗した場合
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @since 2.0
	 */
	public static TimePointOfDay parseGMTFrom(String dateString, String pattern) throws ParseException {
		Validate.notNull(dateString);
		Validate.notNull(pattern);
		return parse(dateString, pattern, GMT);
	}
	
	/**
	 * {@link Calendar}を{@link TimePointOfDay}に変換する。
	 * 
	 * @param calendar 元となる日時情報を表す {@link Calendar}インスタンス
	 * @return {@link TimePointOfDay}
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @since 2.0
	 */
	public static TimePointOfDay valueOf(Calendar calendar) {
		Validate.notNull(calendar);
		return from(calendar.getTime());
	}
	
	
	/** GMTの午前0時からの経過ミリ秒 */
	final long millisecondsFromGMTMidnight;
	
	
	private TimePointOfDay(long millisecondsFromGMTMidnight) {
		this.millisecondsFromGMTMidnight = millisecondsFromGMTMidnight;
	}
	
	/**
	 * この瞬間を「時分」として返す。
	 * 
	 * @param zone タイムゾーン
	 * @return 時分
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @since 2.0
	 */
	public TimeOfDay asTimeOfDay(TimeZone zone) {
		Validate.notNull(zone);
		Calendar calendar = asJavaCalendar(zone);
		return TimeOfDay.from(calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE),
				calendar.get(Calendar.SECOND), calendar.get(Calendar.MILLISECOND));
	}
	
	/**
	 * 瞬間同士の比較を行う。
	 * 
	 * <p>相対的に過去である方を「小さい」と判断する。</p>
	 * 
	 * @param otherPoint 比較対象
	 * @return {@link Comparable#compareTo(Object)}に準じる
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @since 2.0
	 */
	@Override
	public int compareTo(TimePointOfDay otherPoint) {
		if (otherPoint == null) {
			throw new NullPointerException();
		}
		if (isBefore(otherPoint)) {
			return -1;
		}
		if (isAfter(otherPoint)) {
			return 1;
		}
		return 0;
	}
	
	/**
	 * このオブジェクトと、与えたオブジェクト {@code other}の同一性を検証する。
	 * 
	 * <p>与えたオブジェクトが {@code null} ではなく、かつ {@link TimePointOfDay}型であった場合、
	 * 同じ日時を指している場合は{@code true}、そうでない場合は{@code false}を返す。</p>
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 * @since 2.0
	 */
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
		TimePointOfDay other = (TimePointOfDay) obj;
		if (millisecondsFromGMTMidnight != other.millisecondsFromGMTMidnight) {
			return false;
		}
		return true;
	}
	
	@Override
	public int hashCode() {
		return (int) (millisecondsFromGMTMidnight ^ (millisecondsFromGMTMidnight >>> 32)); // CHECKSTYLE IGNORE THIS LINE;
	}
	
	/**
	 * 指定した瞬間 {@code other} が、このオブジェクトが表現する日時よりも未来であるかどうかを検証する。
	 * 
	 * <p>同一日時である場合は {@code false} を返す。</p>
	 * 
	 * @param other 対象日時
	 * @return 未来である場合は{@code true}、そうでない場合は{@code false}
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @since 2.0
	 */
	public boolean isAfter(TimePointOfDay other) {
		Validate.notNull(other);
		return millisecondsFromGMTMidnight > other.millisecondsFromGMTMidnight;
	}
	
	/**
	 * 指定した瞬間 {@code other} が、このオブジェクトが表現する日時よりも過去であるかどうかを検証する。
	 * 
	 * <p>同一日時である場合は {@code false} を返す。</p>
	 * 
	 * @param other 対象日時
	 * @return 過去である場合は{@code true}、そうでない場合は{@code false}
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @since 2.0
	 */
	public boolean isBefore(TimePointOfDay other) {
		Validate.notNull(other);
		return millisecondsFromGMTMidnight < other.millisecondsFromGMTMidnight;
	}
	
	/**
	 * この日時の、指定した時間の長さ分過去の日時を取得する。
	 * 
	 * @param duration 時間の長さ
	 * @return 過去の日時
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @since 2.0
	 */
	public TimePointOfDay minus(Duration duration) {
		Validate.notNull(duration);
		return TimePointOfDay.from(millisecondsFromGMTMidnight - duration.to(TimeUnit.millisecond));
	}
	
	/**
	 * この瞬間から、指定した時間の長さ分未来の瞬間を取得する。
	 * 
	 * @param duration 時間の長さ
	 * @return 未来の日時
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @since 2.0
	 */
	public TimePointOfDay plus(Duration duration) {
		Validate.notNull(duration);
		return TimePointOfDay.from(millisecondsFromGMTMidnight + duration.to(TimeUnit.millisecond));
	}
	
	/**
	 * GMTの午前0時を基準としたミリ秒を返す。
	 * 
	 * @return エポックミリ秒
	 * @since 2.0
	 */
	public long toGMTMidnightMillisec() {
		return millisecondsFromGMTMidnight;
	}
	
	/**
	 * この瞬間をエポック秒に変換して返す。
	 * 
	 * @return エポック秒
	 * @since 2.0
	 */
	public long toGMTMidnightSec() {
		return millisecondsFromGMTMidnight / TimeUnitConversionFactor.millisecondsPerSecond.value;
	}
	
	/**
	 * この瞬間の文字列表現を取得する。
	 * 
	 * @see java.lang.Object#toString()
	 * @since 2.0
	 */
	@Override
	public String toString() {
		return asJavaUtilDate().toString();
	}
	
	/**
	 * この瞬間を、指定したパターンで整形し、その文字列表現を取得する。
	 * 
	 * @param pattern {@link SimpleDateFormat}に基づくパターン
	 * @param zone タイムゾーン
	 * @return 整形済み時間文字列
	 * @since 2.0
	 */
	public String toString(String pattern, TimeZone zone) {
		SimpleDateFormat df = CalendarUtil.newSimpleDateFormat(pattern, zone);
		return df.format(asJavaUtilDate());
	}
	
	/**
	 * このオブジェクトが表現する瞬間をGMTとして扱い、{@link Calendar}型として取得する。
	 * 
	 * @return {@link Calendar}
	 * @since 2.0
	 */
	Calendar asJavaCalendar() {
		return asJavaCalendar(GMT);
	}
	
	/**
	 * このオブジェクトが表現する瞬間を指定したタイムゾーンとして扱い、{@link Calendar}型として取得する。
	 * 
	 * @param zone タイムゾーン
	 * @return {@link Calendar}
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @since 2.0
	 */
	Calendar asJavaCalendar(TimeZone zone) {
		Validate.notNull(zone);
		Calendar calendar = CalendarUtil.newCalendar(zone);
		calendar.setTimeInMillis(millisecondsFromGMTMidnight);
		return calendar;
	}
	
	/**
	 * このオブジェクトが表現する瞬間を、{@link Date}型として取得する。
	 * 
	 * @return {@link Date}
	 * @since 2.0
	 */
	Date asJavaUtilDate() {
		return new Date(millisecondsFromGMTMidnight);
	}
}
