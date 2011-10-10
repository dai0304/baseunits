/*
 * Copyright 2011 Daisuke Miyamoto. (http://d.hatena.ne.jp/daisuke-m)
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
 * Copyright (c) 2004 Domain Language, Inc. (http://domainlanguage.com) This
 * free software is distributed under the "MIT" licence.
 * For more information, see http://timeandmoney.sourceforge.net.
 */
package jp.tricreo.baseunits.time;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;

import jp.tricreo.baseunits.util.Ratio;

import org.apache.commons.lang.Validate;

/**
 * 時間量（時間の長さ・期間の長さなど）を表すクラス。
 * 
 * <p>負の時間量は表現しない。</p>
 * 
 * @since 1.0
 */
@SuppressWarnings("serial")
public class Duration implements Comparable<Duration>, Serializable {
	
	/** 長さ {@code 0} の期間 */
	public static final Duration NONE = milliseconds(0);
	
	
	/**
	 * 長さが {@code howMany} 日の時間量を取得する。
	 * 
	 * @param howMany 時間の長さ（日）
	 * @return 時間量
	 * @since 1.0
	 */
	public static Duration days(long howMany) {
		return Duration.valueOf(howMany, TimeUnit.day);
	}
	
	/**
	 * 長さが {@code days}日 + {@code hours}時間 + {@code minute}分 + {@code seconds}秒
	 * + {@code milliseconds}ミリ秒 の時間量を取得する。
	 * 
	 * @param days 時間の長さ（日） 
	 * @param hours 時間の長さ（時間） 
	 * @param minutes 時間の長さ（分）
	 * @param seconds 時間の長さ（秒）
	 * @param milliseconds  時間の長さ（ミリ秒）
	 * @return 時間量
	 * @since 1.0
	 */
	public static Duration daysHoursMinutesSecondsMilliseconds(long days, long hours, long minutes, long seconds,
			long milliseconds) {
		Duration result = Duration.days(days);
		if (hours != 0) {
			result = result.plus(Duration.hours(hours));
		}
		if (minutes != 0) {
			result = result.plus(Duration.minutes(minutes));
		}
		if (seconds != 0) {
			result = result.plus(Duration.seconds(seconds));
		}
		if (milliseconds != 0) {
			result = result.plus(Duration.milliseconds(milliseconds));
		}
		return result;
	}
	
	/**
	 * 長さが {@code howMany} 時間の時間量を取得する。
	 * 
	 * @param howMany 時間の長さ（時間）
	 * @return 時間量
	 * @since 1.0
	 */
	public static Duration hours(long howMany) {
		return Duration.valueOf(howMany, TimeUnit.hour);
	}
	
	/**
	 * 長さが {@code howMany} ミリ秒の時間量を取得する。
	 * 
	 * @param howMany 時間の長さ（ミリ秒）
	 * @return 時間量
	 * @since 1.0
	 */
	public static Duration milliseconds(long howMany) {
		return Duration.valueOf(howMany, TimeUnit.millisecond);
	}
	
	/**
	 * 長さが {@code howMany} 分の時間量を取得する。
	 * 
	 * @param howMany 時間の長さ（分）
	 * @return 時間量
	 * @since 1.0
	 */
	public static Duration minutes(long howMany) {
		return Duration.valueOf(howMany, TimeUnit.minute);
	}
	
	/**
	 * 長さが {@code howMany} ヶ月の時間量を取得する。
	 * 
	 * @param howMany 時間の長さ（月）
	 * @return 時間量
	 * @since 1.0
	 */
	public static Duration months(long howMany) {
		return Duration.valueOf(howMany, TimeUnit.month);
	}
	
	/**
	 * 長さが {@code howMany} 四半期の時間量を取得する。
	 * 
	 * @param howMany 時間の長さ（四半期）
	 * @return 時間量
	 * @since 1.0
	 */
	public static Duration quarters(long howMany) {
		return Duration.valueOf(howMany, TimeUnit.quarter);
	}
	
	/**
	 * 長さが {@code howMany} ミリの時間量を取得する。
	 * 
	 * @param howMany 時間の長さ（ミリ）
	 * @return 時間量
	 * @since 1.0
	 */
	public static Duration seconds(long howMany) {
		return Duration.valueOf(howMany, TimeUnit.second);
	}
	
	/**
	 * 長さが {@code howMany} 週間の時間量を取得する。
	 * 
	 * @param howMany 時間の長さ（週）
	 * @return 時間量
	 * @since 1.0
	 */
	public static Duration weeks(int howMany) {
		return Duration.valueOf(howMany, TimeUnit.week);
	}
	
	/**
	 * 長さが {@code howMany} 年の時間量を取得する。
	 * 
	 * @param howMany 時間の長さ（年）
	 * @return 時間量
	 * @since 1.0
	 */
	public static Duration years(int howMany) {
		return Duration.valueOf(howMany, TimeUnit.year);
	}
	
	static Duration valueOf(long howMany, TimeUnit unit) {
		return new Duration(howMany, unit);
	}
	
	
	final long quantity;
	
	final TimeUnit unit;
	
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param quantity 量を表す数値
	 * @param unit 量の単位
	 * @throws IllegalArgumentException 引数quantityが0未満の場合
	 * @throws IllegalArgumentException 引数unitに{@code null}を与えた場合
	 * @since 1.0
	 */
	public Duration(long quantity, TimeUnit unit) {
		Validate.notNull(unit);
		Validate.isTrue(quantity >= 0, "Quantity: " + quantity + " must be zero or positive");
		this.quantity = quantity;
		this.unit = unit;
	}
	
	/**
	 * 指定した日付に、このオブジェクトが表現する長さの時間を加えた、未来の日付を取得する。
	 * 
	 * <p>このオブジェクトが表現する時間の長さの単位が 日 未満である場合は、元の日付をそのまま返す。<p>
	 * 
	 * @param day 元となる日付
	 * @return このオブジェクトが表現する長さの時間が経過した未来の日付
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	public CalendarDate addedTo(CalendarDate day) {
		Validate.notNull(day);
//		only valid for days and larger units
		if (unit.compareTo(TimeUnit.day) < 0) {
			return day;
		}
		Calendar calendar = day.asJavaCalendarUniversalZoneMidnight();
		if (unit.equals(TimeUnit.day)) {
			calendar.add(Calendar.DATE, (int) quantity);
		} else {
			addAmountToCalendar(inBaseUnits(), calendar);
		}
		return CalendarDate.from(calendar);
	}
	
	/**
	 * 指定した年月に、このオブジェクトが表現する長さの時間を加えた、未来の年月を取得する。
	 * 
	 * <p>このオブジェクトが表現する時間の長さの単位が 月 未満である場合は、元の年月をそのまま返す。<p>
	 * 
	 * @param month 元となる年月
	 * @return このオブジェクトが表現する長さの時間が経過した未来の年月
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	public CalendarMonth addedTo(CalendarMonth month) {
		Validate.notNull(month);
//		only valid for days and larger units
		if (unit.compareTo(TimeUnit.month) < 0) {
			return month;
		}
		Calendar calendar = month.asJavaCalendarUniversalZoneMidnight();
		if (unit.equals(TimeUnit.month)) {
			calendar.add(Calendar.MONTH, (int) quantity);
		} else {
			addAmountToCalendar(inBaseUnits(), calendar);
		}
		return CalendarMonth.from(calendar);
	}
	
	/**
	 * 指定した日時に、このオブジェクトが表現する長さの時間を加えた、未来の日時を取得する。
	 * 
	 * @param point 元となる日時
	 * @return このオブジェクトが表現する長さの時間が経過した未来の日時
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @see #addAmountToTimePoint(long, TimePoint)
	 * @since 1.0
	 */
	public TimePoint addedTo(TimePoint point) {
		Validate.notNull(point);
		return addAmountToTimePoint(inBaseUnits(), point);
	}
	
	/**
	 * このオブジェクトの{@link #quantity}フィールド（量）を返す。
	 * 
	 * <p>CAUTION: このメソッドは、このオブジェクトがカプセル化する要素を外部に暴露する。取り扱いには充分注意のこと。</p>
	 * 
	 * @return 量
	 * @since 1.0
	 */
	public long breachEncapsulationOfQuantity() {
		return quantity;
	}
	
	/**
	 * このオブジェクトの{@link #unit}フィールド（単位）を返す。
	 * 
	 * <p>CAUTION: このメソッドは、このオブジェクトがカプセル化する要素を外部に暴露する。取り扱いには充分注意のこと。</p>
	 * 
	 * @return 単位
	 * @since 1.0
	 */
	public TimeUnit breachEncapsulationOfUnit() {
		return unit;
	}
	
	/**
	 * 時間量同士の比較を行う。
	 * 
	 * <p>基本単位(baseUnit)換算で比較し、時間量の少ない方を「小さい」と判断する。
	 * 同じ基本単位に変換できない場合は{@link ClassCastException}をスローする。</p>
	 * 
	 * <p>例えば「1ヶ月間」と「30日間」は、同じ基本単位に変換できないため、比較不能である。</p>
	 * 
	 * @param other 比較対照
	 * @return {@link Comparable#compareTo(Object)}に準じる
	 * @throws ClassCastException 引数{@code other}の単位を、このオブジェクトの単位に変換できない場合
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	@Override
	public int compareTo(Duration other) {
		if (other == null) {
			throw new NullPointerException();
		}
		if (other.unit.isConvertibleTo(unit) == false && quantity != 0 && other.quantity != 0) {
			throw new ClassCastException(other.toString() + " is not convertible to: " + toString());
		}
		long difference = inBaseUnits() - other.inBaseUnits();
		if (difference > 0) {
			return 1;
		}
		if (difference < 0) {
			return -1;
		}
		return 0;
	}
	
	/**
	 * この時間量を、指定した時間量 {@code other} で割った商（割合）を取得する。
	 * 
	 * @param divisor 割る数
	 * @return 割合
	 * @throws IllegalArgumentException 引数divisorの単位を、このオブジェクトの単位に変換できない場合
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @throws ArithmeticException 引数{@code divisor}が0だった場合
	 * @since 1.0
	 */
	public Ratio dividedBy(Duration divisor) {
		Validate.notNull(divisor);
		checkConvertible(divisor);
		return Ratio.of(inBaseUnits(), divisor.inBaseUnits());
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
		Duration other = (Duration) obj;
		if (isConvertibleTo(other) == false) {
			return false;
		}
		if (inBaseUnits() != other.inBaseUnits()) {
			return false;
		}
		return true;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (inBaseUnits() ^ (inBaseUnits() >>> 32)); // CHECKSTYLE IGNORE THIS LINE
		result = prime * result + unit.baseType.hashCode();
		return result;
	}
	
	/**
	 * このオブジェクトが表現する時間量と、引数 {@code other} に与えた時間量の差を返す。
	 * 
	 * @param other 期間
	 * @return 時間量の差
	 * @throws IllegalArgumentException 引数otherの単位を、このオブジェクトの単位に変換できず、かつ、どちらのquantityも0ではない場合
	 * @throws IllegalArgumentException 引数otherの長さが、このオブジェクトよりも長い場合
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	public Duration minus(Duration other) {
		Validate.notNull(other);
		checkConvertible(other);
		checkGreaterThanOrEqualTo(other);
		long newQuantity = inBaseUnits() - other.inBaseUnits();
		return new Duration(newQuantity, other.quantity == 0 ? unit.baseUnit() : other.unit.baseUnit());
	}
	
	/**
	 * この期間を1単位で表せる最大の時間単位を求める。
	 * 
	 * <p>例えば、1〜23時間, 25〜47時間は hours だが、24時間, 48時間は days である。</p>
	 * 
	 * @return 時間単位
	 * @since 1.0
	 */
	public TimeUnit normalizedUnit() {
		TimeUnit[] units = unit.descendingUnits();
		long baseAmount = inBaseUnits();
		for (TimeUnit aUnit : units) {
			long remainder = baseAmount % aUnit.getFactor();
			if (remainder == 0) {
				return aUnit;
			}
		}
		return null;
		
	}
	
	/**
	 * このオブジェクトが表現する時間量と、引数 {@code other} に与えた時間量の和を返す。
	 * 
	 * @param other 期間
	 * @return 時間量の和
	 * @throws IllegalArgumentException 引数otherの単位を、このオブジェクトの単位に変換できず、かつ、どちらのquantityも0ではない場合
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	public Duration plus(Duration other) {
		Validate.notNull(other);
		checkConvertible(other);
		long newQuantity = inBaseUnits() + other.inBaseUnits();
		return new Duration(newQuantity, other.quantity == 0 ? unit.baseUnit() : other.unit.baseUnit());
	}
	
	/**
	 * 終了日時とこのオブジェクトが表現する時間量より、期間を生成する。
	 * 
	 * @param end 終了日時（上側限界値）
	 * @return 期間
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	public TimeInterval preceding(TimePoint end) {
		Validate.notNull(end);
		return TimeInterval.preceding(end, this);
	}
	
	/**
	 * 指定した日付を開始日とする、このオブジェクトが表現する長さを持つ期間を生成する。
	 * 
	 * <p>生成する期間の開始日と終了日は期間に含む（閉じている）開区間を生成する。</p>
	 * 
	 * <p>この時間量の単位が "日" 未満である場合は、開始日と終了日は同日となる。<p>
	 * 
	 * @param start 開始日時（下側限界値）. {@code null}の場合は、限界がないことを表す
	 * @return 期間
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	public CalendarInterval startingFrom(CalendarDate start) {
		Validate.notNull(start);
		return CalendarInterval.startingFrom(start, this);
	}
	
	/**
	 * 指定した日時を開始日時とする、このオブジェクトが表現する長さを持つ期間を生成する。
	 * 
	 * <p>生成する期間の開始日時は区間に含み（閉じている）、終了日時は区間に含まない（開いている）半開期間を生成する。</p>
	 * 
	 * @param start 開始日時（下側限界値）. {@code null}の場合は、限界がないことを表す
	 * @return 期間
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	public TimeInterval startingFrom(TimePoint start) {
		Validate.notNull(start);
		return TimeInterval.startingFrom(start, this);
	}
	
	/**
	 * 指定した日付に、このオブジェクトが表現する長さの時間を引いた、過去の日付を取得する。
	 * 
	 * <p>このオブジェクトが表現する時間の長さの単位が 日 未満である場合は、元の日付をそのまま返す。<p>
	 * 
	 * @param day 元となる日付
	 * @return このオブジェクトが表現する長さのを引いた、過去の日付
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	public CalendarDate subtractedFrom(CalendarDate day) {
		Validate.notNull(day);
//		only valid for days and larger units
		if (unit.compareTo(TimeUnit.day) < 0) {
			return day;
		}
		Calendar calendar = day.asJavaCalendarUniversalZoneMidnight();
		if (unit.equals(TimeUnit.day)) {
			calendar.add(Calendar.DATE, -1 * (int) quantity);
		} else {
			subtractAmountFromCalendar(inBaseUnits(), calendar);
		}
		return CalendarDate.from(calendar);
	}
	
	/**
	 * 指定した日時に、このオブジェクトが表現する長さの時間を引いた、過去の日時を取得する。
	 * 
	 * @param point 元となる日時
	 * @return このオブジェクトが表現する長さのを引いた、過去の日時
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @see #addAmountToTimePoint(long, TimePoint)
	 * @since 1.0
	 */
	public TimePoint subtractedFrom(TimePoint point) {
		Validate.notNull(point);
		return addAmountToTimePoint(-1 * inBaseUnits(), point);
	}
	
	/**
	 * この時間量を、指定した単位で表現した場合の整数値に変換する。
	 * 
	 * <p>序算で発生した余りは切り捨てる。</p>
	 * 
	 * <code>
	 * Duration d1 = Duration.days(2);
	 * assertThat(d1.to(TimeUnit.day), is(2L));
	 * assertThat(d1.to(TimeUnit.hour), is(48L));
	 * 
	 * Duration d2 = Duration.hours(49);
	 * assertThat(d2.to(TimeUnit.day), is(2L));
	 * </code>
	 * 
	 * @param unit 単位
	 * @return 値
	 * @throws IllegalArgumentException この時間量を引数に指定した単位に変換できない場合
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 */
	public long to(TimeUnit unit) {
		Validate.notNull(unit);
		if (this.unit == unit) {
			return quantity;
		}
		Duration unitDuration = Duration.valueOf(1, unit);
		Ratio ratio = dividedBy(unitDuration);
		BigDecimal decimal = ratio.decimalValue(0, RoundingMode.DOWN);
		return decimal.longValue();
	}
	
	/**
	 * この時間量の文字列表現を返す。
	 * 
	 * @return 時間量の文字列表現
	 * @since 1.0
	 */
	public String toNormalizedString() {
		return toNormalizedString(unit.descendingUnits());
	}
	
	/**
	 * この時間量の文字列表現を返す。
	 * 
	 * @return 時間量の文字列表現
	 * @see #toNormalizedString()
	 * @since 1.0
	 */
	@Override
	public String toString() {
		return toNormalizedString(unit.descendingUnitsForDisplay());
	}
	
	void addAmountToCalendar(long amount, Calendar calendar) {
		if (unit.isConvertibleToMilliseconds()) {
			calendar.setTimeInMillis(calendar.getTimeInMillis() + amount);
		} else {
			checkAmountValid(amount);
			calendar.add(unit.javaCalendarConstantForBaseType(), (int) amount);
		}
	}
	
	TimePoint addAmountToTimePoint(long amount, TimePoint point) {
		if (unit.isConvertibleToMilliseconds()) {
			return TimePoint.from(amount + point.millisecondsFromEpoch);
		} else {
			Calendar calendar = point.asJavaCalendar();
			addAmountToCalendar(amount, calendar);
			return TimePoint.valueOf(calendar);
		}
	}
	
	long inBaseUnits() {
		return quantity * unit.getFactor();
	}
	
	void subtractAmountFromCalendar(long amount, Calendar calendar) {
		addAmountToCalendar(-1 * amount, calendar);
	}
	
	private void checkAmountValid(long amount) {
		if ((amount >= Integer.MIN_VALUE && amount <= Integer.MAX_VALUE) == false) {
			throw new IllegalArgumentException(amount + " is not valid");
		}
	}
	
	private void checkConvertible(Duration other) {
		if (other.unit.isConvertibleTo(unit) == false && quantity != 0 && other.quantity != 0) {
			throw new IllegalArgumentException(other.toString() + " is not convertible to: " + toString());
		}
	}
	
	private void checkGreaterThanOrEqualTo(Duration other) {
		if (compareTo(other) < 0) {
			throw new IllegalArgumentException(this + " is before " + other);
		}
	}
	
	private boolean isConvertibleTo(Duration other) {
		return unit.isConvertibleTo(other.unit);
	}
	
	private String toNormalizedString(TimeUnit[] units) {
		StringBuffer buffer = new StringBuffer();
		long remainder = inBaseUnits();
		boolean first = true;
		for (TimeUnit aUnit : units) {
			long portion = remainder / aUnit.getFactor();
			if (portion > 0) {
				if (first == false) {
					buffer.append(", ");
				} else {
					first = false;
				}
				buffer.append(aUnit.toString(portion));
			}
			remainder = remainder % aUnit.getFactor();
		}
		return buffer.toString();
	}
}
