/*
 * Copyright 2010 Daisuke Miyamoto.
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
 * free software is distributed under the "MIT" licence. See file licence.txt.
 * For more information, see http://timeandmoney.sourceforge.net.
 */
package jp.xet.timeandmoney.time;

import java.util.Iterator;
import java.util.NoSuchElementException;

import jp.xet.timeandmoney.intervals.Interval;
import jp.xet.timeandmoney.util.ImmutableIterator;

import org.apache.commons.lang.Validate;

/**
 * 期間（時間の区間）を表すクラス。
 * 
 * <p>限界の表現には {@link TimePoint}を利用する。</p>
 * 
 * @author daisuke
 */
@SuppressWarnings("serial")
public class TimeInterval extends Interval<TimePoint> {
	
	/**
	 * 開始日時と終了日時より、閉期間を返す。
	 * 
	 * @param start 開始日時（下側限界値）. {@code null}の場合は、限界がないことを表す
	 * @param end 終了日時（上側限界値）. {@code null}の場合は、限界がないことを表す
	 * @return 期間
	 * @throws IllegalArgumentException 下限値が上限値より大きい（未来である）場合
	 */
	public static TimeInterval closed(TimePoint start, TimePoint end) {
		return over(start, true, end, true);
	}
	
	/**
	 * 開始日時より、下側限界のみを持つ期間を返す。
	 * 
	 * <p>開始日時は期間に含む（閉じている）区間である。</p>
	 * 
	 * @param start 開始日時（下側限界値）. {@code null}の場合は、限界がないことを表す
	 * @return 期間
	 */
	public static TimeInterval everFrom(TimePoint start) {
		return over(start, null);
	}
	
	/**
	 * 終了日時より、上側限界のみを持つ期間を返す。
	 * 
	 * <p>終了日時は期間に含まない（開いている）区間である。</p>
	 * 
	 * @param end 終了日時（上側限界値）. {@code null}の場合は、限界がないことを表す
	 * @return 期間
	 */
	public static TimeInterval everPreceding(TimePoint end) {
		return over(null, end);
	}
	
	/**
	 * 開始日時と終了日時より、開期間を返す。
	 * 
	 * @param start 開始日時（下側限界値）. {@code null}の場合は、限界がないことを表す
	 * @param end 終了日時（上側限界値）. {@code null}の場合は、限界がないことを表す
	 * @return 期間
	 * @throws IllegalArgumentException 下限値が上限値より大きい（未来である）場合
	 */
	public static TimeInterval open(TimePoint start, TimePoint end) {
		return over(start, false, end, false);
	}
	
	/**
	 * 開始日時と終了日時より、期間を返す。
	 * 
	 * <p>主に、半開区間（上限下限のどちらか一方だけが開いている区間）の生成に用いる。</p>
	 * 
	 * @param start 開始日時（下側限界値）. {@code null}の場合は、限界がないことを表す
	 * @param startClosed 開始日時を期間に含む（閉じた下側限界）場合は{@code true}を指定する
	 * @param end 終了日時（上側限界値）. {@code null}の場合は、限界がないことを表す
	 * @param endClosed 終了日時を期間に含む（閉じた上側限界）場合は{@code true}を指定する
	 * @return 期間
	 * @throws IllegalArgumentException 下限値が上限値より大きい（未来である）場合
	 */
	public static TimeInterval over(TimePoint start, boolean startClosed, TimePoint end, boolean endClosed) {
		return new TimeInterval(start, startClosed, end, endClosed);
	}
	
	/**
	 * 開始日時と終了日時より、期間を返す。
	 * 
	 * <p>生成する期間の開始日時は期間に含み（閉じている）、終了日時は期間に含まない（開いている）半開区間を返す。</p>
	 * 
	 * @param start 開始日時（下側限界値）. {@code null}の場合は、限界がないことを表す
	 * @param end 終了日時（上側限界値）. {@code null}の場合は、限界がないことを表す
	 * @return 期間
	 * @throws IllegalArgumentException 開始日時が終了日時より大きい（未来である）場合
	 */
	public static TimeInterval over(TimePoint start, TimePoint end) {
		// Uses the common default for time intervals, [start, end).
		return over(start, true, end, false);
	}
	
	/**
	 * 終了日時と期間の長さより、期間を返す。
	 * 
	 * @param end 終了日時（上側限界値）. {@code null}の場合は、限界がないことを表す
	 * @param startClosed 開始日時を期間に含む（閉じた下側限界）場合は{@code true}を指定する
	 * @param length 期間の長さ
	 * @param endClosed 終了日時を期間に含む（閉じた上側限界）場合は{@code true}を指定する
	 * @return 期間
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 */
	public static TimeInterval preceding(TimePoint end, boolean startClosed, Duration length, boolean endClosed) {
		Validate.notNull(end);
		Validate.notNull(length);
		TimePoint start = end.minus(length);
		return over(start, startClosed, end, endClosed);
	}
	
	/**
	 * 終了日時と期間の長さより、期間を返す。
	 * 
	 * @param end 終了日時（上側限界値）. {@code null}の場合は、限界がないことを表す
	 * @param length 期間の長さ
	 * @return 期間
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 */
	public static TimeInterval preceding(TimePoint end, Duration length) {
		Validate.notNull(end);
		Validate.notNull(length);
		// Uses the common default for time intervals, [start, end).
		return preceding(end, true, length, false);
	}
	
	/**
	 * 開始日時と期間の長さより、期間を返す。
	 * 
	 * @param start 開始日時（下側限界値）
	 * @param startClosed 開始日時を期間に含む（閉じた下側限界）場合は{@code true}を指定する
	 * @param length 期間の長さ
	 * @param endClosed 終了日時を期間に含む（閉じた上側限界）場合は{@code true}を指定する
	 * @return 期間
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 */
	public static TimeInterval startingFrom(TimePoint start, boolean startClosed, Duration length, boolean endClosed) {
		Validate.notNull(start);
		Validate.notNull(length);
		TimePoint end = start.plus(length);
		return over(start, startClosed, end, endClosed);
	}
	
	/**
	 * 開始日時と期間の長さより、期間を返す。
	 * 
	 * <p>生成する期間の開始日時は期間に含み（閉じている）、終了日時は期間に含まない（開いている）半開区間を返す。</p>
	 * 
	 * @param start 開始日時（下側限界値）
	 * @param length 期間の長さ
	 * @return 期間
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 */
	public static TimeInterval startingFrom(TimePoint start, Duration length) {
		Validate.notNull(start);
		Validate.notNull(length);
		// Uses the common default for time intervals, [start, end).
		return startingFrom(start, true, length, false);
	}
	
	/**
	 * インスタンスを返す。
	 * 
	 * @param start 開始日時（下側限界値）. {@code null}の場合は、限界がないことを表す
	 * @param startIncluded 開始日時を期間に含む（閉じた下側限界）場合は{@code true}を指定する
	 * @param end 終了日時（上側限界値）. {@code null}の場合は、限界がないことを表す
	 * @param endIncluded 終了日時を期間に含む（閉じた上側限界）場合は{@code true}を指定する
	 * @throws IllegalArgumentException 下限値が上限値より大きい（未来である）場合
	 */
	public TimeInterval(TimePoint start, boolean startIncluded, TimePoint end, boolean endIncluded) {
		super(start, startIncluded, end, endIncluded);
	}
	
	/**
	 * この期間の開始日時を起点として、前回の日時の1日後の日時を
	 * この期間の終了日時を超過しない範囲で順次取得する反復子を取得する。
	 * 
	 * <p>例えば [2009/01/01 13:00, 2009/01/04 05:00) で表される期間に対してこのメソッドを呼び出した場合、
	 * その戻り値の反復子からは、以下の要素が取得できる。
	 * <ol>
	 *   <li>2009/01/01 13:00</li>
	 *   <li>2009/01/02 13:00</li>
	 *   <li>2009/01/03 13:00</li>
	 * </ol>
	 * </p>
	 * 
	 * <p>返す反復子は {@link Iterator#remove()} をサポートしない。</p>
	 * 
	 * <p>この期間が終了日時（上側限界）を持たない場合、 {@link Iterator#hasNext()}は常に
	 * {@code true}を返すので、無限ループに注意すること。</p>
	 * 
	 * @return 日時の反復子
	 * @throws IllegalStateException この期間が開始日時（下側限界）を持たない場合
	 */
	public Iterator<TimePoint> daysIterator() {
		if (hasLowerLimit() == false) {
			throw new IllegalStateException();
		}
		return new ImmutableIterator<TimePoint>() {
			
			TimePoint next = start();
			

			@Override
			public boolean hasNext() {
				return end().isAfter(next);
			}
			
			@Override
			public TimePoint next() {
				if (hasNext() == false) {
					throw new NoSuchElementException();
				}
				TimePoint current = next;
				next = next.nextDay();
				return current;
			}
		};
	}
	
	/**
	 * この期間の終了日時を取得する。
	 * 
	 * @return この期間の終了日時. 上側限界がない場合は {@code null}
	 */
	public TimePoint end() {
		return upperLimit();
	}
	
	/**
	 * この期間と与えた期間 {@code interval} の積集合（共通部分）を返す。
	 * 
	 * <p>共通部分が存在しない場合は、空の区間を返す。</p>
	 * 
	 * @param interval 比較対象の期間
	 * @return 積集合（共通部分）
	 */
	public TimeInterval intersect(TimeInterval interval) {
		return (TimeInterval) super.intersect(interval);
	}
	
	/**
	 * 指定した日時が、この期間の開始日時以前でないかどうかを検証する。
	 * 
	 * @param point 日時
	 * @return 開始日時以前でない場合は{@code true}、そうでない場合は{@code false}
	 * @see #isAbove(TimePoint)
	 */
	public boolean isAfter(TimePoint point) {
		return isAbove(point);
	}
	
	/**
	 * 指定した日時が、この期間の終了日時を以後でないかどうかを検証する。
	 * 
	 * @param point 日時
	 * @return 終了日時以後でない場合は{@code true}、そうでない場合は{@code false}
	 * @see #isBelow(TimePoint)
	 */
	public boolean isBefore(TimePoint point) {
		return isBelow(point);
	}
	
	/**
	 * この期間の長さを取得する。
	 * 
	 * @return 長さ
	 */
	public Duration length() {
		long difference = end().millisecondsFromEpoc - start().millisecondsFromEpoc;
		return Duration.milliseconds(difference);
	}
	
	/**
	 * この期間と同じ型を持つ、新しい期間を返す。
	 * 
	 * @param start 下側限界値. 限界値がない場合は、{@code null}
	 * @param isStartClosed 下限値を期間に含む（閉じた下側限界）場合は{@code true}を指定する
	 * @param end 上側限界値. 限界値がない場合は、{@code null}
	 * @param isEndClosed 上限値を期間に含む（閉じた上側限界）場合は{@code true}を指定する
	 * @return 新しい期間
	 */
	@Override
	public Interval<TimePoint> newOfSameType(TimePoint start, boolean isStartClosed, TimePoint end, boolean isEndClosed) {
		return new TimeInterval(start, isStartClosed, end, isEndClosed);
	}
	
	/**
	 * この期間の開始日時を取得する。
	 * 
	 * @return この期間の開始日時. 下側限界がない場合は {@code null}
	 */
	public TimePoint start() {
		return lowerLimit();
	}
	
	/**
	 * この期間の開始日時を起点として、指定した時間の長さを持ち前回の終了日時を開始日時とする期間 {@link TimeInterval} を
	 * この期間の終了日時を超過しない範囲で順次取得する反復子を取得する。
	 * 
	 * <p>例えば [2009/01/01 02:00, 2009/01/10 15:00) で表される期間に対して、
	 * 2日間の {@code subintervalLength} を与えた場合、
	 * その戻り値の反復子からは、以下の要素が取得できる。
	 * <ol>
	 *   <li>[2009/01/01 02:00, 2009/01/03 02:00)</li>
	 *   <li>[2009/01/03 02:00, 2009/01/05 02:00)</li>
	 *   <li>[2009/01/05 02:00, 2009/01/07 02:00)</li>
	 *   <li>[2009/01/07 02:00, 2009/01/09 02:00)</li>
	 * </ol>
	 * </p>
	 * 
	 * <p>返す反復子は {@link Iterator#remove()} をサポートしない。</p>
	 * 
	 * <p>この期間が終了日時（上側限界）を持たない場合、 {@link Iterator#hasNext()}は常に
	 * {@code true}を返すので、無限ループに注意すること。</p>
	 * 
	 * @param subintervalLength 反復子が返す期間の長さ
	 * @return 期間の反復子
	 * @throws IllegalStateException この期間が開始日時（下側限界）を持たない場合
	 */
	public Iterator<TimeInterval> subintervalIterator(Duration subintervalLength) {
		if (hasLowerLimit() == false) {
			throw new IllegalStateException();
		}
		final Duration segmentLength = subintervalLength;
		return new ImmutableIterator<TimeInterval>() {
			
			TimeInterval next = segmentLength.startingFrom(start());
			

			@Override
			public boolean hasNext() {
				return TimeInterval.this.covers(next);
			}
			
			@Override
			public TimeInterval next() {
				if (hasNext() == false) {
					throw new NoSuchElementException();
				}
				TimeInterval current = next;
				next = segmentLength.startingFrom(next.end());
				return current;
			}
		};
	}
}
