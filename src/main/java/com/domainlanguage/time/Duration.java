/**
 * Copyright (c) 2004 Domain Language, Inc. (http://domainlanguage.com) This
 * free software is distributed under the "MIT" licence. See file licence.txt.
 * For more information, see http://timeandmoney.sourceforge.net.
 */

package com.domainlanguage.time;

import java.io.Serializable;
import java.util.Calendar;

import com.domainlanguage.base.Ratio;

import org.apache.commons.lang.Validate;

/**
 * 時間量（時間の長さ・期間の長さなど）を表すクラス。
 * 
 * <p>負の時間量は表現しない。</p>
 * 
 * @author daisuke
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
	 */
	public static Duration days(int howMany) {
		return Duration.of(howMany, TimeUnit.day);
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
	 */
	public static Duration daysHoursMinutesSecondsMilliseconds(int days, int hours, int minutes, int seconds,
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
	 */
	public static Duration hours(int howMany) {
		return Duration.of(howMany, TimeUnit.hour);
	}
	
	/**
	 * 長さが {@code howMany} ミリ秒の時間量を取得する。
	 * 
	 * @param howMany 時間の長さ（ミリ秒）
	 * @return 時間量
	 */
	public static Duration milliseconds(long howMany) {
		return Duration.of(howMany, TimeUnit.millisecond);
	}
	
	/**
	 * 長さが {@code howMany} 分の時間量を取得する。
	 * 
	 * @param howMany 時間の長さ（分）
	 * @return 時間量
	 */
	public static Duration minutes(int howMany) {
		return Duration.of(howMany, TimeUnit.minute);
	}
	
	/**
	 * 長さが {@code howMany} ヶ月の時間量を取得する。
	 * 
	 * @param howMany 時間の長さ（月）
	 * @return 時間量
	 */
	public static Duration months(int howMany) {
		return Duration.of(howMany, TimeUnit.month);
	}
	
	/**
	 * 長さが {@code howMany} 四半期の時間量を取得する。
	 * 
	 * @param howMany 時間の長さ（四半期）
	 * @return 時間量
	 */
	public static Duration quarters(int howMany) {
		return Duration.of(howMany, TimeUnit.quarter);
	}
	
	/**
	 * 長さが {@code howMany} ミリの時間量を取得する。
	 * 
	 * @param howMany 時間の長さ（ミリ）
	 * @return 時間量
	 */
	public static Duration seconds(int howMany) {
		return Duration.of(howMany, TimeUnit.second);
	}
	
	/**
	 * 長さが {@code howMany} 週間の時間量を取得する。
	 * 
	 * @param howMany 時間の長さ（週）
	 * @return 時間量
	 */
	public static Duration weeks(int howMany) {
		return Duration.of(howMany, TimeUnit.week);
	}
	
	/**
	 * 長さが {@code howMany} 年の時間量を取得する。
	 * 
	 * @param howMany 時間の長さ（年）
	 * @return 時間量
	 */
	public static Duration years(int howMany) {
		return Duration.of(howMany, TimeUnit.year);
	}
	
	private static Duration of(long howMany, TimeUnit unit) {
		return new Duration(howMany, unit);
	}
	

	private long quantity;
	
	TimeUnit unit;
	

	/**
	 * インスタンスを生成する。
	 * 
	 * @param quantity 量を表す数値
	 * @param unit 量の単位
	 * @throws IllegalArgumentException 引数quantityが0未満の場合
	 * @throws IllegalArgumentException 引数unitに{@code null}を与えた場合
	 */
	public Duration(long quantity, TimeUnit unit) {
		Validate.notNull(unit);
		assertQuantityPositiveOrZero(quantity);
		this.quantity = quantity;
		this.unit = unit;
	}
	
	/**
	 * Only for use by persistence mapping frameworks
	 * <rant>These methods break encapsulation and we put them in here begrudgingly</rant>
	 */
	Duration() {
	}
	
	/**
	 * 指定した日付に、このオブジェクトが表現する長さの時間を加えた、未来の日付を取得する。
	 * 
	 * <p>このオブジェクトが表現する時間の長さの単位が 日 未満である場合は、元の日付をそのまま返す。<p>
	 * 
	 * @param day 元となる日付
	 * @return このオブジェクトが表現する長さの時間が経過した未来の日付
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
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
		return CalendarDate._from(calendar);
	}
	
	/**
	 * 指定した日時に、このオブジェクトが表現する長さの時間を加えた、未来の日時を取得する。
	 * 
	 * @param point 元となる日時
	 * @return このオブジェクトが表現する長さの時間が経過した未来の日時
	 * @see #addAmountToTimePoint(long, TimePoint)
	 */
	public TimePoint addedTo(TimePoint point) {
		return addAmountToTimePoint(inBaseUnits(), point);
	}
	
	/**
	 * TODO 詳細定義
	 * 
	 * @param other 比較対照
	 * @return {@link Comparable#compareTo(Object)}に準じる
	 * @throws IllegalArgumentException 引数otherの単位を、このオブジェクトの単位に変換できない場合.
	 * 	例えば「1ヶ月間」と「30日間」は比較不能
	 */
	@Override
	public int compareTo(Duration other) {
		if (other == null) {
			return -1;
		}
		assertConvertible(other);
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
	 */
	public Ratio dividedBy(Duration divisor) {
		Validate.notNull(divisor);
		assertConvertible(divisor);
		return Ratio.of(inBaseUnits(), divisor.inBaseUnits());
	}
	
	@Override
	public boolean equals(Object object) {
		if (object instanceof Duration == false) {
			return false;
		}
		Duration other = (Duration) object;
		if (isConvertibleTo(other) == false) {
			return false;
		}
		return inBaseUnits() == other.inBaseUnits();
	}
	
	@Override
	public int hashCode() {
		return (int) quantity;
	}
	
	/**
	 * このオブジェクトが表現する時間量と、引数 {@code other} に与えた時間量の差を返す。
	 * 
	 * <p>ただし、返す結果の単位は、 TODO</p>
	 * 
	 * @param other 期間
	 * @return 時間量の差
	 * @throws IllegalArgumentException 引数otherの単位を、このオブジェクトの単位に変換できない場合
	 * @throws IllegalArgumentException 引数otherの長さが、このオブジェクトよりも長い場合
	 */
	public Duration minus(Duration other) {
		assertConvertible(other);
		assertGreaterThanOrEqualTo(other);
		long newQuantity = inBaseUnits() - other.inBaseUnits();
		return new Duration(newQuantity, unit.baseUnit());
	}
	
	/**
	 * この期間を1単位で表せる最大の時間単位を求める。
	 * 
	 * <p>例えば、1〜23時間, 25〜47時間は hours だが、24時間, 48時間は days である。</p>
	 * 
	 * @return 時間単位
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
	 * <p>ただし、返す結果の単位は、 TODO</p>
	 * 
	 * @param other 期間
	 * @return 時間量の和
	 * @throws IllegalArgumentException 引数otherの単位を、このオブジェクトの単位に変換できない場合
	 */
	public Duration plus(Duration other) {
		assertConvertible(other);
		long newQuantity = inBaseUnits() + other.inBaseUnits();
		return new Duration(newQuantity, unit.baseUnit());
	}
	
	/**
	 * 終了日時とこのオブジェクトが表現する時間量より、期間を生成する。
	 * 
	 * @param end 終了日時（上側限界値）
	 * @return 期間
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 */
	public TimeInterval preceding(TimePoint end) {
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
	 */
	public CalendarInterval startingFrom(CalendarDate start) {
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
	 */
	public TimeInterval startingFrom(TimePoint start) {
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
		return CalendarDate._from(calendar);
	}
	
	/**
	 * 指定した日時に、このオブジェクトが表現する長さの時間を引いた、過去の日時を取得する。
	 * 
	 * @param point 元となる日時
	 * @return このオブジェクトが表現する長さのを引いた、過去の日時
	 * @see #addAmountToTimePoint(long, TimePoint)
	 */
	public TimePoint subtractedFrom(TimePoint point) {
		return addAmountToTimePoint(-1 * inBaseUnits(), point);
	}
	
	/**
	 * この時間量の文字列表現を返す。
	 * 
	 * @return 時間量の文字列表現
	 */
	public String toNormalizedString() {
		return toNormalizedString(unit.descendingUnits());
	}
	
	/**
	 * この時間量の文字列表現を返す。
	 * 
	 * @return 時間量の文字列表現
	 * @see #toNormalizedString()
	 */
	@Override
	public String toString() {
		return toNormalizedString(unit.descendingUnitsForDisplay());
	}
	
	void addAmountToCalendar(long amount, Calendar calendar) {
		if (unit.isConvertibleToMilliseconds()) {
			calendar.setTimeInMillis(calendar.getTimeInMillis() + amount);
		} else {
			assertAmountValid(amount);
			calendar.add(unit.javaCalendarConstantForBaseType(), (int) amount);
		}
	}
	
	TimePoint addAmountToTimePoint(long amount, TimePoint point) {
		if (unit.isConvertibleToMilliseconds()) {
			return TimePoint.from(amount + point.millisecondsFromEpoc);
		} else {
			Calendar calendar = point.asJavaCalendar();
			addAmountToCalendar(amount, calendar);
			return TimePoint.from(calendar);
		}
	}
	
	long inBaseUnits() {
		return quantity * unit.getFactor();
	}
	
	void subtractAmountFromCalendar(long amount, Calendar calendar) {
		addAmountToCalendar(-1 * amount, calendar);
	}
	
	private void assertAmountValid(long amount) {
		if (!(amount >= Integer.MIN_VALUE && amount <= Integer.MAX_VALUE)) {
			throw new IllegalArgumentException(amount + " is not valid");
		}
	}
	
	private void assertConvertible(Duration other) {
		if (other.unit.isConvertibleTo(unit) == false) {
			throw new IllegalArgumentException(other.toString() + " is not convertible to: " + toString());
		}
	}
	
	private void assertGreaterThanOrEqualTo(Duration other) {
		if (compareTo(other) < 0) {
			throw new IllegalArgumentException(this + " is before " + other);
		}
	}
	
	private void assertQuantityPositiveOrZero(long quantity) {
		if (quantity < 0) {
			throw new IllegalArgumentException("Quantity: " + quantity + " must be zero or positive");
		}
	}
	
	/**
	 * Only for use by persistence mapping frameworks
	 * <rant>These methods break encapsulation and we put them in here begrudgingly</rant>
	 * @return {@link #quantity}
	 */
	@SuppressWarnings("unused")
	private long getForPersistentMapping_Quantity() { // CHECKSTYLE IGNORE THIS LINE
		return quantity;
	}
	
	/**
	 * Only for use by persistence mapping frameworks
	 * <rant>These methods break encapsulation and we put them in here begrudgingly</rant>
	 * @return {@link #unit}
	 */
	@SuppressWarnings("unused")
	private TimeUnit getForPersistentMapping_Unit() { // CHECKSTYLE IGNORE THIS LINE
		return unit;
	}
	
	private boolean isConvertibleTo(Duration other) {
		return unit.isConvertibleTo(other.unit);
	}
	
	/**
	 * Only for use by persistence mapping frameworks
	 * <rant>These methods break encapsulation and we put them in here begrudgingly</rant>
	 * @param quantity {@link #quantity}
	 */
	@SuppressWarnings("unused")
	private void setForPersistentMapping_Quantity(long quantity) { // CHECKSTYLE IGNORE THIS LINE
		this.quantity = quantity;
	}
	
	/**
	 * Only for use by persistence mapping frameworks
	 * <rant>These methods break encapsulation and we put them in here begrudgingly</rant>
	 * @param unit {@link #unit}
	 */
	@SuppressWarnings("unused")
	private void setForPersistentMapping_Unit(TimeUnit unit) { // CHECKSTYLE IGNORE THIS LINE
		this.unit = unit;
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
