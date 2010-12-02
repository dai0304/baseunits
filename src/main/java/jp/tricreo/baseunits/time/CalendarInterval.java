/*
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

import java.util.Calendar;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.TimeZone;

import jp.tricreo.baseunits.intervals.Interval;
import jp.tricreo.baseunits.util.ImmutableIterator;

import org.apache.commons.lang.Validate;

/**
 * 期間（日付の区間）を表すクラス。
 * 
 * <p>限界の表現には {@link CalendarDate}を利用する。</p>
 */
@SuppressWarnings("serial")
public class CalendarInterval extends Interval<CalendarDate> {
	
	/**
	 * 開始日より、下側限界のみを持つ期間を生成する。
	 * 
	 * <p>開始日は期間に含む（閉じている）区間である。</p>
	 * 
	 * @param startDate 開始日（下側限界値）. {@code null}の場合は、限界がないことを表す
	 * @return 期間
	 */
	public static CalendarInterval everFrom(CalendarDate startDate) {
		return inclusive(startDate, null);
	}
	
	/**
	 * 終了日より、上側限界のみを持つ期間を生成する。
	 * 
	 * <p>終了日は期間に含む（閉じている）区間である。</p>
	 * 
	 * @param endDate 終了日（上側限界値）. {@code null}の場合は、限界がないことを表す
	 * @return 期間
	 */
	public static CalendarInterval everPreceding(CalendarDate endDate) {
		return inclusive(null, endDate);
	}
	
	/**
	 * 開始日と終了日より、期間を生成する。
	 * 
	 * <p>生成する期間の開始日と終了日は期間に含む（閉じている）開区間を生成する。</p>
	 * 
	 * @param start 開始日
	 * @param end 終了日
	 * @return 期間
	 * @throws IllegalArgumentException 下限値が上限値より大きい（未来である）場合
	 */
	public static CalendarInterval inclusive(CalendarDate start, CalendarDate end) {
		return new CalendarInterval(start, end);
	}
	
	/**
	 * 開始日と終了日より、期間を生成する。
	 * 
	 * <p>生成する期間の開始日と終了日は期間に含む（閉じている）開区間を生成する。</p>
	 * 
	 * @param startYear 開始日の年
	 * @param startMonth 開始日の月（1〜12）
	 * @param startDay 開始日の日
	 * @param endYear 終了日の年
	 * @param endMonth 終了日の月（1〜12）
	 * @param endDay 終了日の日
	 * @return 期間
	 * @throws IllegalArgumentException 下限値が上限値より大きい（未来である）場合
	 */
	public static CalendarInterval inclusive(int startYear, int startMonth, int startDay, int endYear, int endMonth,
			int endDay) {
		CalendarDate startDate = CalendarDate.from(startYear, startMonth, startDay);
		CalendarDate endDate = CalendarDate.from(endYear, endMonth, endDay);
		return new CalendarInterval(startDate, endDate);
	}
	
	/**
	 * 指定した年月の1日からその月末までの、期間を生成する。
	 * 
	 * <p>生成する期間の開始日と終了日は期間に含む（閉じている）開区間を生成する。</p>
	 * 
	 * @param month 開始日の年月
	 * @return 期間
	 */
	public static CalendarInterval month(CalendarMonth month) {
		CalendarDate startDate = CalendarDate.from(month, DayOfMonth.valueOf(1));
		CalendarDate endDate = startDate.plusMonths(1).plusDays(-1);
		return inclusive(startDate, endDate);
	}
	
	/**
	 * 指定した年月の1日からその月末までの、期間を生成する。
	 * 
	 * <p>生成する期間の開始日と終了日は期間に含む（閉じている）開区間を生成する。</p>
	 * 
	 * @param year 開始日の年
	 * @param month 開始日の月（1〜12）
	 * @return 期間
	 */
	public static CalendarInterval month(int year, int month) {
		return month(year, MonthOfYear.valueOf(month));
	}
	
	/**
	 * 指定した年月の1日からその月末までの、期間を生成する。
	 * 
	 * <p>生成する期間の開始日と終了日は期間に含む（閉じている）開区間を生成する。</p>
	 * 
	 * @param year 開始日の年
	 * @param month 開始日の月
	 * @return 期間
	 */
	public static CalendarInterval month(int year, MonthOfYear month) {
		CalendarDate startDate = CalendarDate.from(year, month, DayOfMonth.valueOf(1));
		CalendarDate endDate = startDate.plusMonths(1).plusDays(-1);
		return inclusive(startDate, endDate);
	}
	
	/**
	 * 開始日と期間の長さより、期間を生成する。
	 * 
	 * <p>生成する期間の開始日と終了日は期間に含む（閉じている）開区間を生成する。</p>
	 * 
	 * <p>引数 {@code length} の期間の長さの単位が "日" 未満である場合は、開始日と終了日は同日となる。<p>
	 * 
	 * @param start 開始日（下側限界値）
	 * @param length 期間の長さ
	 * @return 期間
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 */
	public static CalendarInterval startingFrom(CalendarDate start, Duration length) {
		Validate.notNull(start);
		Validate.notNull(length);
		// Uses the common default for calendar intervals, [start, end].
		if (length.unit.compareTo(TimeUnit.day) < 0) {
			return inclusive(start, start);
		}
		return inclusive(start, start.plus(length).plusDays(-1));
	}
	
	/**
	 * 指定した年の元旦からその年の大晦日までの、期間を生成する。
	 * 
	 * <p>生成する期間の開始日と終了日は期間に含む（閉じている）開区間を生成する。</p>
	 * 
	 * @param year 開始日の年
	 * @return 期間
	 */
	public static CalendarInterval year(int year) {
		CalendarDate startDate = CalendarDate.from(year, 1, 1);
		CalendarDate endDate = CalendarDate.from(year + 1, 1, 1).plusDays(-1);
		return inclusive(startDate, endDate);
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * <p>生成する期間の開始日と終了日は期間に含む（閉じている）開区間を生成する。</p>
	 * 
	 * @param start 開始日
	 * @param end 終了日
	 */
	protected CalendarInterval(CalendarDate start, CalendarDate end) {
		super(start, true, end, true);
	}
	
	/**
	 * この期間の開始日の午前0時を開始日時、この期間の終了日の翌日午前0時を終了日時とする時間の期間を生成する。
	 * 
	 * <p>生成する期間の開始日時は期間に含み（閉じている）、終了日時は期間に含まない（開いている）半開区間を生成する。</p>
	 * 
	 * @param zone タイムゾーン
	 * @return 時間の期間
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 */
	public TimeInterval asTimeInterval(TimeZone zone) {
		Validate.notNull(zone);
		TimePoint startPoint = lowerLimit().asTimeInterval(zone).start();
		TimePoint endPoint = upperLimit().asTimeInterval(zone).end();
		return TimeInterval.over(startPoint, endPoint);
	}
	
	/**
	 * この期間の終了日を起点として、前回の日付の前日を
	 * この期間の開始日を超過しない範囲で順次取得する反復子を取得する。
	 * 
	 * <p>例えば [2009/01/01, 2009/01/04] で表される期間に対してこのメソッドを呼び出した場合、
	 * その戻り値の反復子からは、以下の要素が取得できる。
	 * <ol>
	 *   <li>2009/01/04</li>
	 *   <li>2009/01/03</li>
	 *   <li>2009/01/02</li>
	 *   <li>2009/01/01</li>
	 * </ol>
	 * </p>
	 * 
	 * <p>返す反復子は {@link Iterator#remove()} をサポートしない。</p>
	 * 
	 * <p>この期間が開始日（下側限界）を持たない場合、 {@link Iterator#hasNext()}は常に
	 * {@code true}を返すので、無限ループに注意すること。</p>
	 * 
	 * @return 日付の反復子
	 * @throws IllegalStateException この期間が終了日（上側限界）を持たない場合
	 */
	public Iterator<CalendarDate> daysInReverseIterator() {
		if (hasUpperLimit() == false) {
			throw new IllegalStateException();
		}
		final CalendarDate start = upperLimit();
		final CalendarDate end = lowerLimit();
		return new ImmutableIterator<CalendarDate>() {
			
			CalendarDate next = start;
			

			@Override
			public boolean hasNext() {
				return next.isBefore(end) == false;
			}
			
			@Override
			public CalendarDate next() {
				if (hasNext() == false) {
					throw new NoSuchElementException();
				}
				CalendarDate current = next;
				next = next.plusDays(-1);
				return current;
			}
		};
	}
	
	/**
	 * この期間の開始日を起点として、前回の日付の翌日を
	 * この期間の終了日を超過しない範囲で順次取得する反復子を取得する。
	 * 
	 * <p>例えば [2009/01/01, 2009/01/04] で表される期間に対してこのメソッドを呼び出した場合、
	 * その戻り値の反復子からは、以下の要素が取得できる。
	 * <ol>
	 *   <li>2009/01/01</li>
	 *   <li>2009/01/02</li>
	 *   <li>2009/01/03</li>
	 *   <li>2009/01/04</li>
	 * </ol>
	 * </p>
	 * 
	 * <p>返す反復子は {@link Iterator#remove()} をサポートしない。</p>
	 * 
	 * <p>この期間が終了日（上側限界）を持たない場合、 {@link Iterator#hasNext()}は常に
	 * {@code true}を返すので、無限ループに注意すること。</p>
	 * 
	 * @return 日付の反復子
	 * @throws IllegalStateException この期間が開始日（下側限界）を持たない場合
	 */
	public Iterator<CalendarDate> daysIterator() {
		if (hasLowerLimit() == false) {
			throw new IllegalStateException();
		}
		final CalendarDate start = lowerLimit();
		final CalendarDate end = upperLimit();
		return new ImmutableIterator<CalendarDate>() {
			
			CalendarDate next = start;
			

			@Override
			public boolean hasNext() {
				return next.isAfter(end) == false;
			}
			
			@Override
			public CalendarDate next() {
				if (hasNext() == false) {
					throw new NoSuchElementException();
				}
				CalendarDate current = next;
				next = next.plusDays(1);
				return current;
			}
		};
	}
	
	/**
	 * 終了日を取得する。
	 * 
	 * @return 終了日. 開始日がない場合は{@code null}
	 */
	public CalendarDate end() {
		return upperLimit();
	}
	
	/**
	 * この期間の日数としての長さを取得する。
	 * 
	 * @return 期間の長さ
	 * @see #length()
	 */
	public Duration length() {
		return Duration.days(lengthInDaysInt());
	}
	
	/**
	 * この期間が、日数にして何日の長さがあるかを取得する。
	 * 
	 * @return 日数
	 * @throws IllegalStateException この期間が開始日（下側限界）または終了日（下側限界）を持たない場合
	 */
	public int lengthInDaysInt() {
		if (hasLowerLimit() == false || hasUpperLimit() == false) {
			throw new IllegalStateException();
		}
		Calendar calStart = start().asJavaCalendarUniversalZoneMidnight();
		Calendar calEnd = end().plusDays(1).asJavaCalendarUniversalZoneMidnight();
		long diffMillis = calEnd.getTimeInMillis() - calStart.getTimeInMillis();
		return (int) (diffMillis / TimeUnitConversionFactor.millisecondsPerDay.value);
	}
	
	/**
	 * この期間の月数としての長さを取得する。
	 * 
	 * <p>開始日と終了日が同月であれば{@code 0}ヶ月となる。</p>
	 * 
	 * @return 期間の長さ
	 * @see #lengthInMonthsInt()
	 */
	public Duration lengthInMonths() {
		return Duration.months(lengthInMonthsInt());
	}
	
	/**
	 * 限界日の「日」要素を考慮せず、この期間が月数にして何ヶ月の長さがあるかを取得する。
	 * 
	 * <p>開始日と終了日が同月であれば{@code 0}となる。</p>
	 * 
	 * @return 月数
	 * @throws IllegalStateException この期間が開始日（下側限界）または終了日（下側限界）を持たない場合
	 */
	public int lengthInMonthsInt() {
		if (hasLowerLimit() == false || hasUpperLimit() == false) {
			throw new IllegalStateException();
		}
		Calendar calStart = start().asJavaCalendarUniversalZoneMidnight();
		Calendar calEnd = end().plusDays(1).asJavaCalendarUniversalZoneMidnight();
		int yearDiff = calEnd.get(Calendar.YEAR) - calStart.get(Calendar.YEAR);
		int monthDiff = yearDiff * 12 + calEnd.get(Calendar.MONTH) - calStart.get(Calendar.MONTH);
		return monthDiff;
	}
	
	@Override
	public Interval<CalendarDate> newOfSameType(CalendarDate lower, boolean isLowerClosed, CalendarDate upper,
			boolean isUpperClosed) {
		CalendarDate includedLower = isLowerClosed ? (CalendarDate) lower : lower.plusDays(1);
		CalendarDate includedUpper = isUpperClosed ? (CalendarDate) upper : upper.plusDays(-1);
		return inclusive(includedLower, includedUpper);
	}
	
	/**
	 * 開始日を取得する。
	 * 
	 * @return 開始日. 開始日がない場合は{@code null}
	 */
	public CalendarDate start() {
		return lowerLimit();
	}
	
	/**
	 * この期間の開始日を起点として、指定した時間の長さを持ち前回の終了日の翌日を開始日とする期間 {@link CalendarInterval} を
	 * この期間の終了日を超過しない範囲で順次取得する反復子を取得する。
	 * 
	 * <p>例えば [2009/01/01, 2009/01/11] で表される期間に対して、
	 * 2日間の {@code subintervalLength} を与えた場合、
	 * その戻り値の反復子からは、以下の要素が取得できる。
	 * <ol>
	 *   <li>[2009/01/01, 2009/01/02]</li>
	 *   <li>[2009/01/03, 2009/01/04]</li>
	 *   <li>[2009/01/05, 2009/01/06]</li>
	 *   <li>[2009/01/07, 2009/01/08]</li>
	 *   <li>[2009/01/09, 2009/01/10]</li>
	 * </ol>
	 * </p>
	 * 
	 * <p>返す反復子は {@link Iterator#remove()} をサポートしない。</p>
	 * 
	 * <p>この期間が終了日（上側限界）を持たない場合、 {@link Iterator#hasNext()}は常に
	 * {@code true}を返すので、無限ループに注意すること。</p>
	 * 
	 * @param subintervalLength 反復子が返す期間の長さ
	 * @return 期間の反復子
	 * @throws IllegalStateException この期間が開始日（下側限界）を持たない場合
	 * @throws IllegalArgumentException 引数subintervalLengthの長さ単位が「日」未満の場合
	 */
	public Iterator<CalendarInterval> subintervalIterator(Duration subintervalLength) {
		if (hasLowerLimit() == false) {
			throw new IllegalStateException();
		}
		if (TimeUnit.day.compareTo(subintervalLength.normalizedUnit()) > 0) {
			throw new IllegalArgumentException("CalendarIntervals must be a whole number of days or months.");
		}
		
		final Duration segmentLength = subintervalLength;
		return new ImmutableIterator<CalendarInterval>() {
			
			CalendarInterval next = segmentLength.startingFrom(start());
			

			@Override
			public boolean hasNext() {
				return CalendarInterval.this.covers(next);
			}
			
			@Override
			public CalendarInterval next() {
				if (hasNext() == false) {
					throw new NoSuchElementException();
				}
				CalendarInterval current = next;
				next = segmentLength.startingFrom(next.end().plusDays(1));
				return current;
			}
		};
	}
	
}
