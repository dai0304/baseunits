/*
 * Copyright 2010-2018 Miyamoto Daisuke.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package jp.xet.baseunits.time;

import java.util.Calendar;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.TimeZone;

import jp.xet.baseunits.intervals.Interval;
import jp.xet.baseunits.util.ImmutableIterator;

import com.google.common.base.Preconditions;

/**
 * 暦日期間（暦日の区間）を表すクラス。
 * 
 * <p>限界の表現には {@link CalendarDate}を利用する。</p>
 * 
 * @author daisuke
 * @since 1.0
 */
@SuppressWarnings("serial")
public class CalendarInterval extends Interval<CalendarDate> {
	
	/**
	 * 空期間を生成する。
	 * 
	 * @return 空期間
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 */
	public static CalendarInterval empty() {
		return new CalendarInterval(CalendarDate.EPOCH_DATE, false, CalendarDate.EPOCH_DATE, false);
	}
	
	/**
	 * 開始暦日より、下側限界のみを持つ期間を生成する。
	 * 
	 * <p>開始暦日は期間に含む（閉じている）区間である。</p>
	 * 
	 * @param startDate 開始暦日（下側限界値）. {@code null}の場合は、限界がないことを表す
	 * @return 期間
	 * @since 1.0
	 */
	public static CalendarInterval everFrom(CalendarDate startDate) {
		return inclusive(startDate, null);
	}
	
	/**
	 * 終了暦日より、上側限界のみを持つ期間を生成する。
	 * 
	 * <p>終了暦日は期間に含む（閉じている）区間である。</p>
	 * 
	 * @param endDate 終了暦日（上側限界値）. {@code null}の場合は、限界がないことを表す
	 * @return 期間
	 * @since 1.0
	 */
	public static CalendarInterval everPreceding(CalendarDate endDate) {
		return inclusive(null, endDate);
	}
	
	/**
	 * 開始暦日と終了暦日より、期間を生成する。
	 * 
	 * <p>生成する期間の開始暦日と終了暦日は期間に含む（閉じている）開区間を生成する。</p>
	 * 
	 * @param start 開始暦日
	 * @param end 終了暦日
	 * @return 期間
	 * @throws IllegalArgumentException 下限値が上限値より大きい（未来である）場合
	 * @since 1.0
	 */
	public static CalendarInterval inclusive(CalendarDate start, CalendarDate end) {
		return new CalendarInterval(start, end);
	}
	
	/**
	 * 開始暦日と終了暦日より、期間を生成する。
	 * 
	 * <p>生成する期間の開始暦日と終了暦日は期間に含む（閉じている）開区間を生成する。</p>
	 * 
	 * @param startYear 開始暦日の年
	 * @param startMonth 開始暦日の月（1〜12）
	 * @param startDay 開始暦日の日
	 * @param endYear 終了暦日の年
	 * @param endMonth 終了暦日の月（1〜12）
	 * @param endDay 終了暦日の日
	 * @return 期間
	 * @throws IllegalArgumentException 下限値が上限値より大きい（未来である）場合
	 * @since 1.0
	 */
	public static CalendarInterval inclusive(int startYear, int startMonth, int startDay, int endYear, int endMonth,
			int endDay) {
		CalendarDate startDate = CalendarDate.from(startYear, startMonth, startDay);
		CalendarDate endDate = CalendarDate.from(endYear, endMonth, endDay);
		return new CalendarInterval(startDate, endDate);
	}
	
	/**
	 * 指定した暦月の初日からその月末日までの、期間を生成する。
	 * 
	 * <p>生成する期間の開始暦日と終了暦日は期間に含む（閉じている）開区間を生成する。</p>
	 * 
	 * @param month 暦月
	 * @return 期間
	 * @since 1.0
	 */
	public static CalendarInterval month(CalendarMonth month) {
		CalendarDate startDate = CalendarDate.from(month, DayOfMonth.valueOf(1));
		CalendarDate endDate = startDate.plusMonths(1).plusDays(-1);
		return inclusive(startDate, endDate);
	}
	
	/**
	 * 指定した年月の初日からその月末までの、期間を生成する。
	 * 
	 * <p>生成する期間の開始暦日と終了暦日は期間に含む（閉じている）開区間を生成する。</p>
	 * 
	 * @param year 年
	 * @param month 月（1〜12）
	 * @return 期間
	 * @throws IllegalArgumentException 引数{@code month}が1〜12の範囲外である場合
	 * @since 1.0
	 */
	public static CalendarInterval month(int year, int month) {
		MonthOfYear monthOfYear = MonthOfYear.valueOf(month);
		Preconditions.checkNotNull(monthOfYear);
		return month(year, monthOfYear);
	}
	
	/**
	 * 指定した暦年及び月の初日からその月末までの、期間を生成する。
	 * 
	 * <p>生成する期間の開始暦日と終了暦日は期間に含む（閉じている）開区間を生成する。</p>
	 * 
	 * @param year 開始暦日の年
	 * @param month 開始暦日の月
	 * @return 期間
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	public static CalendarInterval month(int year, MonthOfYear month) {
		Preconditions.checkNotNull(month);
		CalendarDate startDate = CalendarDate.from(year, month, DayOfMonth.MIN);
		CalendarDate endDate = startDate.plusMonths(1).plusDays(-1);
		return inclusive(startDate, endDate);
	}
	
	/**
	 * 終了暦日と期間の長さより、期間を生成する。
	 * 
	 * @param end 終了暦日（上側限界値）
	 * @param length 期間の長さ
	 * @return 期間
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @since 2.0
	 */
	public static CalendarInterval preceding(CalendarDate end, Duration length) {
		Preconditions.checkNotNull(end);
		Preconditions.checkNotNull(length);
		// Uses the common default for calendar intervals, [start, end].
		if (length.unit.compareTo(TimeUnit.day) < 0) {
			return inclusive(end, end);
		}
		return inclusive(end.minus(length).plusDays(1), end);
	}
	
	/**
	 * 開始暦日と期間の長さより、期間を生成する。
	 * 
	 * <p>生成する期間の開始暦日と終了暦日は期間に含む（閉じている）開区間を生成する。</p>
	 * 
	 * <p>引数 {@code length} の期間の長さの単位が "日" 未満である場合は、開始暦日と終了暦日は同日となる。<p>
	 * 
	 * @param start 開始暦日（下側限界値）
	 * @param length 期間の長さ
	 * @return 期間
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	public static CalendarInterval startingFrom(CalendarDate start, Duration length) {
		Preconditions.checkNotNull(start);
		Preconditions.checkNotNull(length);
		// Uses the common default for calendar intervals, [start, end].
		if (length.unit.compareTo(TimeUnit.day) < 0) {
			return inclusive(start, start);
		}
		return inclusive(start, start.plus(length).plusDays(-1));
	}
	
	/**
	 * 指定した暦週の月曜日からその週末（日曜日）までの、期間を生成する。
	 * 
	 * <p>生成する期間の開始暦日と終了暦日は期間に含む（閉じている）開区間を生成する。</p>
	 * 
	 * @param week 開始週
	 * @return 期間
	 * @since 1.0
	 */
	public static CalendarInterval week(CalendarWeek week) {
		Calendar cal = week.asJavaCalendarUniversalZoneMidnight();
		CalendarDate startDate = CalendarDate.from(cal);
		CalendarDate endDate = startDate.plusDays(6);
		return inclusive(startDate, endDate);
		
	}
	
	/**
	 * 指定した週の月曜日からその週末（日曜日）までの、期間を生成する。
	 * 
	 * <p>生成する期間の開始暦日と終了暦日は期間に含む（閉じている）開区間を生成する。</p>
	 * 
	 * @param year 開始週の年
	 * @param week 開始週
	 * @return 期間
	 * @since 1.0
	 */
	public static CalendarInterval week(int year, WeekOfYear week) {
		return week(CalendarWeek.from(year, week));
	}
	
	/**
	 * 指定した暦年の元旦からその年の大晦日までの、期間を生成する。
	 * 
	 * <p>生成する期間の開始暦日と終了暦日は期間に含む（閉じている）開区間を生成する。</p>
	 * 
	 * @param year 開始暦日の年
	 * @return 期間
	 * @since 1.0
	 */
	public static CalendarInterval year(int year) {
		CalendarDate startDate = CalendarDate.from(year, 1, 1);
		CalendarDate endDate = CalendarDate.from(year + 1, 1, 1).plusDays(-1);
		return inclusive(startDate, endDate);
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * <p>生成する期間の開始暦日と終了暦日は期間に含む（閉じている）開区間を生成する。</p>
	 * 
	 * @param start 開始暦日
	 * @param end 終了暦日
	 * @since 1.0
	 */
	protected CalendarInterval(CalendarDate start, CalendarDate end) {
		super(start, true, end, true);
	}
	
	// for empty interval
	private CalendarInterval(CalendarDate lower, boolean isLowerClosed, CalendarDate upper, boolean isUpperClosed) {
		super(lower, isLowerClosed, upper, isUpperClosed);
	}
	
	/**
	 * この期間の開始暦日の午前0時を開始{@link TimePoint}、この期間の終了暦日の翌日午前0時を終了{@link TimePoint}とする時間の期間を生成する。
	 * 
	 * <p>生成する期間の開始{@link TimePoint}は期間に含み（閉じている）、終了{@link TimePoint}は期間に含まない（開いている）半開区間を生成する。</p>
	 * 
	 * @param zone タイムゾーン
	 * @return 時間の期間
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	public TimePointInterval asTimeInterval(TimeZone zone) {
		Preconditions.checkNotNull(zone);
		TimePoint startPoint = hasLowerLimit() ? lowerLimit().asTimePointInterval(zone).start() : null;
		TimePoint endPoint = hasUpperLimit() ? upperLimit().asTimePointInterval(zone).end() : null;
		return TimePointInterval.over(startPoint, endPoint);
	}
	
	/**
	 * この期間の終了暦日を起点として、前回の暦日の前日を
	 * この期間の開始暦日を超過しない範囲で順次取得する反復子を取得する。
	 * 
	 * <p>例えば [2009/01/01, 2009/01/04] で表される期間に対してこのメソッドを呼び出した場合、
	 * その戻り値の反復子からは、以下の要素が取得できる。</p>
	 * 
	 * <ol>
	 *   <li>2009/01/04</li>
	 *   <li>2009/01/03</li>
	 *   <li>2009/01/02</li>
	 *   <li>2009/01/01</li>
	 * </ol>
	 * 
	 * <p>返す反復子は {@link Iterator#remove()} をサポートしない。</p>
	 * 
	 * <p>この期間が開始暦日（下側限界）を持たない場合、 {@link Iterator#hasNext()}は常に
	 * {@code true}を返すので、無限ループに注意すること。</p>
	 * 
	 * @return 暦日の反復子
	 * @throws IllegalStateException この期間が終了暦日（上側限界）を持たない場合
	 * @since 1.0
	 */
	public Iterator<CalendarDate> daysInReverseIterator() {
		if (hasUpperLimit() == false) {
			throw new IllegalStateException("daysInReverseIterator reqires upper limit (end).");
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
	 * この期間の開始暦日を起点として、前回の暦日の翌日を
	 * この期間の終了暦日を超過しない範囲で順次取得する反復子を取得する。
	 * 
	 * <p>例えば [2009/01/01, 2009/01/04] で表される期間に対してこのメソッドを呼び出した場合、
	 * その戻り値の反復子からは、以下の要素が取得できる。</p>
	 * 
	 * <ol>
	 *   <li>2009/01/01</li>
	 *   <li>2009/01/02</li>
	 *   <li>2009/01/03</li>
	 *   <li>2009/01/04</li>
	 * </ol>
	 * 
	 * <p>返す反復子は {@link Iterator#remove()} をサポートしない。</p>
	 * 
	 * <p>この期間が終了暦日（上側限界）を持たない場合、 {@link Iterator#hasNext()}は常に
	 * {@code true}を返すので、無限ループに注意すること。</p>
	 * 
	 * @return 暦日の反復子
	 * @throws IllegalStateException この期間が開始暦日（下側限界）を持たない場合
	 * @since 1.0
	 */
	public Iterator<CalendarDate> daysIterator() {
		if (hasLowerLimit() == false) {
			throw new IllegalStateException("daysIterator reqires lower limit (start).");
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
	
	@Override
	public CalendarInterval emptyOfSameType() {
		return empty();
	}
	
	/**
	 * 終了暦日を取得する。
	 * 
	 * @return 終了暦日。終了暦日がない場合は{@code null}
	 * @since 1.0
	 * @see #start()
	 */
	public CalendarDate end() {
		return upperLimit();
	}
	
	@Override
	public CalendarInterval intersect(Interval<CalendarDate> other) {
		return (CalendarInterval) super.intersect(other);
	}
	
	/**
	 * この期間の日数としての長さを取得する。
	 * 
	 * @return 期間の長さ
	 * @see #length()
	 * @since 1.0
	 */
	public Duration length() {
		return Duration.days(lengthInDaysInt());
	}
	
	/**
	 * この期間が、日数にして何日の長さがあるかを取得する。
	 * 
	 * @return 日数
	 * @throws IllegalStateException この期間が開始暦日（下側限界）または終了暦日（下側限界）を持たない場合
	 * @since 1.0
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
	 * <p>開始暦日と終了暦日が同月であれば{@code 0}ヶ月となる。</p>
	 * 
	 * @return 期間の長さ
	 * @see #lengthInMonthsInt()
	 * @since 1.0
	 */
	public Duration lengthInMonths() {
		return Duration.months(lengthInMonthsInt());
	}
	
	/**
	 * 限界日の「日」要素を考慮せず、この期間が月数にして何ヶ月の長さがあるかを取得する。
	 * 
	 * <p>開始暦日と終了暦日が同月であれば{@code 0}となる。</p>
	 * 
	 * @return 月数
	 * @throws IllegalStateException この期間が開始暦日（下側限界）または終了暦日（下側限界）を持たない場合
	 * @since 1.0
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
	
	/**
	 * この期間の終了暦日を含む暦月を起点として、前回の前月を
	 * この期間の開始暦日を含む暦月を超過しない範囲で順次取得する反復子を取得する。
	 * 
	 * <p>例えば [2009/01/10, 2009/04/04] で表される期間に対してこのメソッドを呼び出した場合、
	 * その戻り値の反復子からは、以下の要素が取得できる。</p>
	 * 
	 * <ol>
	 *   <li>2009/04</li>
	 *   <li>2009/03</li>
	 *   <li>2009/02</li>
	 *   <li>2009/01</li>
	 * </ol>
	 * 
	 * <p>返す反復子は {@link Iterator#remove()} をサポートしない。</p>
	 * 
	 * <p>この期間が開始暦日（下側限界）を持たない場合、 {@link Iterator#hasNext()}は常に
	 * {@code true}を返すので、無限ループに注意すること。</p>
	 * 
	 * @return 暦月の反復子
	 * @throws IllegalStateException この期間が終了暦日（上側限界）を持たない場合
	 * @since 2.0
	 */
	public Iterator<CalendarMonth> monthsInReverseIterator() {
		if (hasUpperLimit() == false) {
			throw new IllegalStateException("weeksInReverseIterator reqires upper limit (end).");
		}
		final CalendarMonth start = upperLimit().asCalendarMonth();
		final CalendarMonth end;
		if (hasLowerLimit()) {
			end = lowerLimit().asCalendarMonth();
		} else {
			end = null;
		}
		return new ImmutableIterator<CalendarMonth>() {
			
			CalendarMonth next = start;
			
			
			@Override
			public boolean hasNext() {
				return next.isBefore(end) == false;
			}
			
			@Override
			public CalendarMonth next() {
				if (hasNext() == false) {
					throw new NoSuchElementException();
				}
				CalendarMonth current = next;
				next = next.previousMonth();
				return current;
			}
		};
	}
	
	/**
	 * 期間の開始暦日を含む暦月を起点として、前回の翌月を
	 * この期間の終了暦日を含む暦月を超過しない範囲で順次取得する反復子を取得する。
	 * 
	 * <p>例えば [2009/01/10, 2009/05/04] で表される期間に対してこのメソッドを呼び出した場合、
	 * その戻り値の反復子からは、以下の要素が取得できる。</p>
	 * 
	 * <ol>
	 *   <li>2009/01</li>
	 *   <li>2009/02</li>
	 *   <li>2009/03</li>
	 *   <li>2009/04</li>
	 *   <li>2009/05</li>
	 * </ol>
	 * 
	 * <p>返す反復子は {@link Iterator#remove()} をサポートしない。</p>
	 * 
	 * <p>この期間が終了暦日（上側限界）を持たない場合、 {@link Iterator#hasNext()}は常に
	 * {@code true}を返すので、無限ループに注意すること。</p>
	 * 
	 * @return 暦月の反復子
	 * @throws IllegalStateException この期間が開始暦日（下側限界）を持たない場合
	 * @since 2.0
	 */
	public Iterator<CalendarMonth> monthsIterator() {
		if (hasLowerLimit() == false) {
			throw new IllegalStateException("weeksIterator reqires lower limit (start).");
		}
		final CalendarMonth start = lowerLimit().asCalendarMonth();
		final CalendarMonth end;
		if (hasUpperLimit()) {
			end = upperLimit().asCalendarMonth();
		} else {
			end = null;
		}
		return new ImmutableIterator<CalendarMonth>() {
			
			CalendarMonth next = start;
			
			
			@Override
			public boolean hasNext() {
				return next.isAfter(end) == false;
			}
			
			@Override
			public CalendarMonth next() {
				if (hasNext() == false) {
					throw new NoSuchElementException();
				}
				CalendarMonth current = next;
				next = next.nextMonth();
				return current;
			}
		};
	}
	
	@Override
	public CalendarInterval newOfSameType(CalendarDate lower, boolean isLowerClosed, CalendarDate upper,
			boolean isUpperClosed) {
		// TODO 怪しげ。要バグチェック
		CalendarDate includedLower = isLowerClosed ? (CalendarDate) lower : lower.plusDays(1);
		CalendarDate includedUpper = isUpperClosed ? (CalendarDate) upper : upper.plusDays(-1);
		return inclusive(includedLower, includedUpper);
	}
	
	/**
	 * 開始暦日を取得する。
	 * 
	 * @return 開始暦日。開始暦日がない場合は{@code null}
	 * @since 1.0
	 * @see #end()
	 */
	public CalendarDate start() {
		return lowerLimit();
	}
	
	/**
	 * この期間の開始暦日を起点として、指定した時間の長さを持ち前回の終了暦日の翌日を開始暦日とする{@link CalendarInterval 期間}を
	 * この期間の終了暦日を超過しない範囲で順次取得する反復子を取得する。
	 * 
	 * <p>例えば [2009/01/01, 2009/01/11] で表される期間に対して、
	 * 2日間の {@code subintervalLength} を与えた場合、
	 * その戻り値の反復子からは、以下の要素が取得できる。</p>
	 * 
	 * <ol>
	 *   <li>[2009/01/01, 2009/01/02]</li>
	 *   <li>[2009/01/03, 2009/01/04]</li>
	 *   <li>[2009/01/05, 2009/01/06]</li>
	 *   <li>[2009/01/07, 2009/01/08]</li>
	 *   <li>[2009/01/09, 2009/01/10]</li>
	 * </ol>
	 * 
	 * <p>返す反復子は {@link Iterator#remove()} をサポートしない。</p>
	 * 
	 * <p>この期間が終了暦日（上側限界）を持たない場合、 {@link Iterator#hasNext()}は常に
	 * {@code true}を返すので、無限ループに注意すること。</p>
	 * 
	 * @param subintervalLength 反復子が返す期間の長さ
	 * @return 期間の反復子
	 * @throws IllegalStateException この期間が開始暦日（下側限界）を持たない場合
	 * @throws IllegalArgumentException 引数subintervalLengthの長さ単位が「日」未満の場合
	 * @since 1.0
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
	
	/**
	 * この期間の終了暦日を含む暦週を起点として、前回の前週を
	 * この期間の開始暦日を含む暦週を超過しない範囲で順次取得する反復子を取得する。
	 * 
	 * <p>返す反復子は {@link Iterator#remove()} をサポートしない。</p>
	 * 
	 * <p>この期間が開始暦日（下側限界）を持たない場合、 {@link Iterator#hasNext()}は常に
	 * {@code true}を返すので、無限ループに注意すること。</p>
	 * 
	 * @return 暦週の反復子
	 * @throws IllegalStateException この期間が終了暦日（上側限界）を持たない場合
	 * @since 2.0
	 */
	public Iterator<CalendarWeek> weeksInReverseIterator() {
		if (hasUpperLimit() == false) {
			throw new IllegalStateException("weeksInReverseIterator reqires upper limit (end).");
		}
		final CalendarWeek start = upperLimit().asCalendarWeek();
		final CalendarWeek end;
		if (hasLowerLimit()) {
			end = lowerLimit().asCalendarWeek();
		} else {
			end = null;
		}
		return new ImmutableIterator<CalendarWeek>() {
			
			CalendarWeek next = start;
			
			
			@Override
			public boolean hasNext() {
				return next.isBefore(end) == false;
			}
			
			@Override
			public CalendarWeek next() {
				if (hasNext() == false) {
					throw new NoSuchElementException();
				}
				CalendarWeek current = next;
				next = next.previousWeek();
				return current;
			}
		};
	}
	
	/**
	 * 期間の開始暦日を含む暦週を起点として、前回の暦週の翌週を
	 * この期間の終了暦日を含む暦週を超過しない範囲で順次取得する反復子を取得する。
	 * 
	 * <p>返す反復子は {@link Iterator#remove()} をサポートしない。</p>
	 * 
	 * <p>この期間が終了暦日（上側限界）を持たない場合、 {@link Iterator#hasNext()}は常に
	 * {@code true}を返すので、無限ループに注意すること。</p>
	 * 
	 * @return 暦週の反復子
	 * @throws IllegalStateException この期間が開始暦日（下側限界）を持たない場合
	 * @since 2.0
	 */
	public Iterator<CalendarWeek> weeksIterator() {
		if (hasLowerLimit() == false) {
			throw new IllegalStateException("weeksIterator reqires lower limit (start).");
		}
		final CalendarWeek start = lowerLimit().asCalendarWeek();
		final CalendarWeek end;
		if (hasUpperLimit()) {
			end = upperLimit().asCalendarWeek();
		} else {
			end = null;
		}
		return new ImmutableIterator<CalendarWeek>() {
			
			CalendarWeek next = start;
			
			
			@Override
			public boolean hasNext() {
				return next.isAfter(end) == false;
			}
			
			@Override
			public CalendarWeek next() {
				if (hasNext() == false) {
					throw new NoSuchElementException();
				}
				CalendarWeek current = next;
				next = next.nextWeek();
				return current;
			}
		};
	}
}
