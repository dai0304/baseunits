/*
 * Copyright 2011-2012 Daisuke Miyamoto. (http://d.hatena.ne.jp/daisuke-m)
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
 * Copyright (c) 2004 Domain Language, Inc. (http://domainlanguage.com) This
 * free software is distributed under the "MIT" licence.
 * For more information, see http://timeandmoney.sourceforge.net.
 */
package jp.xet.baseunits.time;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;

import jp.xet.baseunits.util.Ratio;

import com.google.common.base.Preconditions;

/**
 * 時間量（時間の長さ・期間の長さなど）を表すクラス。
 * 
 * <p>負の時間量は表現しない。</p>
 * 
 * @author daisuke
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
	 * @throws IllegalArgumentException 引数{@code howMany}に負数を与えた場合
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
	 * @throws IllegalArgumentException 引数に負数を与えた場合
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
	 * 2つの{@link TimePoint}の差を表す時間量を返す。
	 * 
	 * @param tp1 {@link TimePoint}
	 * @param tp2 {@link TimePoint}
	 * @return 時間量
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @since 2.0
	 */
	public static Duration diff(TimePoint tp1, TimePoint tp2) {
		Preconditions.checkNotNull(tp1);
		Preconditions.checkNotNull(tp2);
		return Duration.milliseconds(Math.abs(tp1.toEpochMillisec() - tp2.toEpochMillisec()));
	}
	
	/**
	 * 長さが {@code howMany} 時間の時間量を取得する。
	 * 
	 * @param howMany 時間の長さ（時間）
	 * @return 時間量
	 * @throws IllegalArgumentException 引数{@code howMany}に負数を与えた場合
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
	 * @throws IllegalArgumentException 引数{@code howMany}に負数を与えた場合
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
	 * @throws IllegalArgumentException 引数{@code howMany}に負数を与えた場合
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
	 * @throws IllegalArgumentException 引数{@code howMany}に負数を与えた場合
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
	 * @throws IllegalArgumentException 引数{@code howMany}に負数を与えた場合
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
	 * @throws IllegalArgumentException 引数{@code howMany}に負数を与えた場合
	 * @since 1.0
	 */
	public static Duration seconds(long howMany) {
		return Duration.valueOf(howMany, TimeUnit.second);
	}
	
	/**
	 * 複数の {@link Duration} の総和を返す。
	 * 
	 * @param values 複数{@link Duration}
	 * @return 総和
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @since 2.0
	 */
	public static Duration sum(Iterable<Duration> values) {
		Preconditions.checkNotNull(values);
		Duration result = Duration.NONE;
		for (Duration v : values) {
			if (v != null) {
				result = result.plus(v);
			}
		}
		return result;
	}
	
	/**
	 * 長さが {@code howMany} 週間の時間量を取得する。
	 * 
	 * @param howMany 時間の長さ（週）
	 * @return 時間量
	 * @throws IllegalArgumentException 引数{@code howMany}に負数を与えた場合
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
	 * @throws IllegalArgumentException 引数{@code howMany}に負数を与えた場合
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
	 * @throws IllegalArgumentException 引数{@code quantity}に負数を与えた場合
	 * @throws NullPointerException 引数{@code unit}に{@code null}を与えた場合
	 * @since 1.0
	 */
	public Duration(long quantity, TimeUnit unit) {
		Preconditions.checkNotNull(unit);
		Preconditions.checkArgument(quantity >= 0, "Quantity: " + quantity + " must be zero or positive");
		this.quantity = quantity;
		this.unit = unit;
	}
	
	/**
	 * 指定した暦日に、この長さの時間を加えた、未来の暦日を取得する。
	 * 
	 * <p>この長さの単位が 日 未満である場合は、元の暦日をそのまま返す。<p>
	 * 
	 * @param day 元となる暦日
	 * @return この長さの時間が経過した未来の暦日
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @see #subtractedFrom(CalendarDate)
	 * @since 1.0
	 */
	public CalendarDate addedTo(CalendarDate day) {
		Preconditions.checkNotNull(day);
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
	 * この長さの時間を、指定した暦月に加えた、未来の暦月を取得する。
	 * 
	 * <p>この長さの単位が 月 未満である場合は、元の暦月をそのまま返す。<p>
	 * 
	 * @param month 元となる暦月
	 * @return この長さの時間が経過した未来の暦月
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	public CalendarMonth addedTo(CalendarMonth month) {
		Preconditions.checkNotNull(month);
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
	 * 指定した{@link TimePoint}に、この長さの時間を加えた、未来の{@link TimePoint}を取得する。
	 * 
	 * @param point 元となる{@link TimePoint}
	 * @return この長さの時間が経過した未来の{@link TimePoint}
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @see #addAmountToTimePoint(long, TimePoint)
	 * @see #subtractedFrom(TimePoint)
	 * @since 1.0
	 */
	public TimePoint addedTo(TimePoint point) {
		Preconditions.checkNotNull(point);
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
		long thisValue = inBaseUnits();
		long anotherValue = other.inBaseUnits();
		return thisValue < anotherValue ? -1 : ((thisValue == anotherValue) ? 0 : 1);
	}
	
	/**
	 * この時間量を、指定した時間量 {@code other} で割った商（割合）を取得する。
	 * 
	 * @param divisor 割る数
	 * @return 割合
	 * @throws IllegalArgumentException 引数divisorの単位を、このオブジェクトの単位に変換できない場合
	 * @throws ArithmeticException 引数{@code divisor}が0だった場合
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	public Ratio dividedBy(Duration divisor) {
		Preconditions.checkNotNull(divisor);
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
	 * この時間量が、引数に指定した時間量{@code other}よりも大きいかどうかを調べる。
	 * 
	 * @param other 比較対照
	 * @return {@code other}よりも大きい場合は{@code true}、そうでない場合は{@code false}
	 * @throws ClassCastException 引数{@code other}の単位を、このオブジェクトの単位に変換できない場合
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @see #compareTo(Duration)
	 */
	public boolean isGreaterThan(Duration other) {
		return compareTo(other) > 0;
	}
	
	/**
	 * この時間量が、引数に指定した時間量{@code other}よりも大きいかまたは等しいどうかを調べる。
	 * 
	 * @param other 比較対照
	 * @return {@code other}よりも大きいまたは等しい場合は{@code true}、そうでない場合は{@code false}
	 * @throws ClassCastException 引数{@code other}の単位を、このオブジェクトの単位に変換できない場合
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @see #compareTo(Duration)
	 */
	public boolean isGreaterThanOrEqual(Duration other) {
		return compareTo(other) >= 0;
	}
	
	/**
	 * この時間量が、引数に指定した時間量{@code other}よりも小さいかどうかを調べる。
	 * 
	 * @param other 比較対照
	 * @return {@code other}よりも小さい場合は{@code true}、そうでない場合は{@code false}
	 * @throws ClassCastException 引数{@code other}の単位を、このオブジェクトの単位に変換できない場合
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @see #compareTo(Duration)
	 */
	public boolean isLessThan(Duration other) {
		return compareTo(other) < 0;
	}
	
	/**
	 * この時間量が、引数に指定した時間量{@code other}よりも小さいかまたは等しいどうかを調べる。
	 * 
	 * @param other 比較対照
	 * @return {@code other}よりも小さいまたは等しい場合は{@code true}、そうでない場合は{@code false}
	 * @throws ClassCastException 引数{@code other}の単位を、このオブジェクトの単位に変換できない場合
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @see #compareTo(Duration)
	 */
	public boolean isLessThanOrEqual(Duration other) {
		return compareTo(other) <= 0;
	}
	
	/**
	 * この時間量と{@code other}の差を返す。
	 * 
	 * @param other 期間
	 * @return 時間量の差
	 * @throws IllegalArgumentException 引数{@code other}の単位を、このオブジェクトの単位に変換できず、かつ、どちらのquantityも0ではない場合
	 * @throws IllegalArgumentException 引数{@code other}の長さが、このオブジェクトよりも長い場合
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	public Duration minus(Duration other) {
		Preconditions.checkNotNull(other);
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
		throw new AssertionError("must not to be occured");
	}
	
	/**
	 * この時間量と、引数 {@code other} に与えた時間量の和を返す。
	 * 
	 * @param other 期間
	 * @return 時間量の和
	 * @throws IllegalArgumentException 引数otherの単位を、このオブジェクトの単位に変換できず、かつ、どちらのquantityも0ではない場合
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	public Duration plus(Duration other) {
		Preconditions.checkNotNull(other);
		checkConvertible(other);
		long newQuantity = inBaseUnits() + other.inBaseUnits();
		return new Duration(newQuantity, other.quantity == 0 ? unit.baseUnit() : other.unit.baseUnit());
	}
	
	/**
	 * 指定した暦日を終了暦日とする、この長さを持つ期間を生成する。
	 * 
	 * @param end 終了暦日（上側限界値）
	 * @return 期間
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @since 2.0
	 */
	public CalendarInterval preceding(CalendarDate end) {
		Preconditions.checkNotNull(end);
		return CalendarInterval.preceding(end, this);
	}
	
	/**
	 * 指定した時刻を終了時刻とする、この長さを持つ期間を生成する。
	 * 
	 * <p>生成する期間の開始時刻は区間に含み（閉じている）、終了時刻は区間に含まない（開いている）半開期間を生成する。</p>
	 * 
	 * @param end 終了時刻（上側限界値）
	 * @return 期間
	 * @throws IllegalArgumentException 減算の結果が0時を超えた場合
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @since 2.0
	 */
	public TimeOfDayInterval preceding(TimeOfDay end) {
		Preconditions.checkNotNull(end);
		if (isGreaterThan(end.toDuration())) {
			throw new IllegalArgumentException();
		}
		return TimeOfDayInterval.preceding(end, this);
	}
	
	/**
	 * 指定した瞬間を終了{@link TimePoint}とする、この長さを持つ期間を生成する。
	 * 
	 * <p>生成する期間の開始{@link TimePoint}は区間に含み（閉じている）、終了{@link TimePoint}は区間に含まない（開いている）半開期間を生成する。</p>
	 * 
	 * @param end 終了{@link TimePoint}（上側限界値）
	 * @return 期間
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	public TimePointInterval preceding(TimePoint end) {
		Preconditions.checkNotNull(end);
		return TimePointInterval.preceding(end, this);
	}
	
	/**
	 * 指定した暦日を開始暦日とする、この長さを持つ期間を生成する。
	 * 
	 * <p>生成する期間の開始日と終了日は期間に含む（閉じている）開区間を生成する。</p>
	 * 
	 * <p>この時間量の単位が "日" 未満である場合は、開始日と終了日は同日となる。<p>
	 * 
	 * @param start 開始日（下側限界値）
	 * @return 期間
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	public CalendarInterval startingFrom(CalendarDate start) {
		Preconditions.checkNotNull(start);
		return CalendarInterval.startingFrom(start, this);
	}
	
	/**
	 * 指定した時刻を開始時刻とする、この長さを持つ期間を生成する。
	 * 
	 * <p>生成する期間の開始{@link TimePoint}は区間に含み（閉じている）、終了{@link TimePoint}は区間に含まない（開いている）半開期間を生成する。</p>
	 * 
	 * @param start 開始{@link TimePoint}（下側限界値）. {@code null}の場合は、限界がないことを表す
	 * @return 期間
	 * @throws IllegalArgumentException 加算の結果が24時を超えた場合
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	public TimeOfDayInterval startingFrom(TimeOfDay start) {
		Preconditions.checkNotNull(start);
		return TimeOfDayInterval.startingFrom(start, this);
	}
	
	/**
	 * 指定した瞬間を開始{@link TimePoint}とする、この長さを持つ期間を生成する。
	 * 
	 * <p>生成する期間の開始{@link TimePoint}は区間に含み（閉じている）、終了{@link TimePoint}は区間に含まない（開いている）半開期間を生成する。</p>
	 * 
	 * @param start 開始{@link TimePoint}（下側限界値）
	 * @return 期間
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	public TimePointInterval startingFrom(TimePoint start) {
		Preconditions.checkNotNull(start);
		return TimePointInterval.startingFrom(start, this);
	}
	
	/**
	 * 指定した暦日に、この長さの時間を引いた、過去の暦日を取得する。
	 * 
	 * <p>この長さの単位が 日 未満である場合は、元の暦日をそのまま返す。<p>
	 * 
	 * @param day 元となる暦日
	 * @return この長さのを引いた、過去の暦日
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @see #addedTo(CalendarDate)
	 * @since 1.0
	 */
	public CalendarDate subtractedFrom(CalendarDate day) {
		Preconditions.checkNotNull(day);
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
	 * 指定した{@link TimePoint}に、この長さの時間を引いた、過去の{@link TimePoint}を取得する。
	 * 
	 * @param point 元となる{@link TimePoint}
	 * @return この長さのを引いた、過去の{@link TimePoint}
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @see #addAmountToTimePoint(long, TimePoint)
	 * @see #addedTo(TimePoint)
	 * @since 1.0
	 */
	public TimePoint subtractedFrom(TimePoint point) {
		Preconditions.checkNotNull(point);
		return addAmountToTimePoint(-1 * inBaseUnits(), point);
	}
	
	/**
	 * この時間量を、指定した単位で表現した場合の整数値に変換する。
	 * 
	 * <p>序算で発生した余りは切り捨てる。</p>
	 * 
	 * <code><pre>
	 * Duration d1 = Duration.days(2);
	 * assertThat(d1.to(TimeUnit.day), is(2L));
	 * assertThat(d1.to(TimeUnit.hour), is(48L));
	 * 
	 * Duration d2 = Duration.hours(49);
	 * assertThat(d2.to(TimeUnit.day), is(2L));
	 * </pre></code>
	 * 
	 * @param unit 単位
	 * @return 値
	 * @throws IllegalArgumentException この時間量を引数に指定した単位に変換できない場合
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 */
	public long to(TimeUnit unit) {
		Preconditions.checkNotNull(unit);
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
