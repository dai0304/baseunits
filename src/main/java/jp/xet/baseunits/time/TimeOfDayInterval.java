/*
 * Copyright 2011 Daisuke Miyamoto. (http://d.hatena.ne.jp/daisuke-m)
 * Created on 2011/10/25
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

import java.util.Iterator;
import java.util.NoSuchElementException;

import jp.xet.baseunits.intervals.Interval;
import jp.xet.baseunits.util.ImmutableIterator;

import org.apache.commons.lang.Validate;

/**
 * 日内期間（時間の区間）を表すクラス。
 * 
 * <p>限界の表現には {@link TimeOfDay}を利用する。</p>
 * 
 * @since 2.0
 */
@SuppressWarnings("serial")
public class TimeOfDayInterval extends Interval<TimeOfDay> {
	
	/**
	 * 開始日時と終了日時より、閉期間を返す。
	 * 
	 * @param start 開始日時（下側限界値）. {@code null}の場合は、限界がないことを表す
	 * @param end 終了日時（上側限界値）. {@code null}の場合は、限界がないことを表す
	 * @return 期間
	 * @throws IllegalArgumentException 下限値が上限値より大きい（未来である）場合
	 * @since 1.0
	 */
	public static TimeOfDayInterval closed(TimeOfDay start, TimeOfDay end) {
		return over(start, true, end, true);
	}
	
	/**
	 * 開始日時より、下側限界のみを持つ期間を返す。
	 * 
	 * <p>開始日時は期間に含む（閉じている）区間である。</p>
	 * 
	 * @param start 開始日時（下側限界値）. {@code null}の場合は、限界がないことを表す
	 * @return 期間
	 * @since 1.0
	 */
	public static TimeOfDayInterval everFrom(TimeOfDay start) {
		return over(start, null);
	}
	
	/**
	 * 終了日時より、上側限界のみを持つ期間を返す。
	 * 
	 * <p>終了日時は期間に含まない（開いている）区間である。</p>
	 * 
	 * @param end 終了日時（上側限界値）. {@code null}の場合は、限界がないことを表す
	 * @return 期間
	 * @since 1.0
	 */
	public static TimeOfDayInterval everPreceding(TimeOfDay end) {
		return over(null, end);
	}
	
	/**
	 * 開始日時と終了日時より、開期間を返す。
	 * 
	 * @param start 開始日時（下側限界値）. {@code null}の場合は、限界がないことを表す
	 * @param end 終了日時（上側限界値）. {@code null}の場合は、限界がないことを表す
	 * @return 期間
	 * @throws IllegalArgumentException 下限値が上限値より大きい（未来である）場合
	 * @since 1.0
	 */
	public static TimeOfDayInterval open(TimeOfDay start, TimeOfDay end) {
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
	 * @since 1.0
	 */
	public static TimeOfDayInterval over(TimeOfDay start, boolean startClosed, TimeOfDay end, boolean endClosed) {
		return new TimeOfDayInterval(start, startClosed, end, endClosed);
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
	 * @since 1.0
	 */
	public static TimeOfDayInterval over(TimeOfDay start, TimeOfDay end) {
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
	 * @since 1.0
	 */
	public static TimeOfDayInterval preceding(TimeOfDay end, boolean startClosed, Duration length, boolean endClosed) {
		Validate.notNull(end);
		Validate.notNull(length);
		TimeOfDay start = end.minus(length);
		return over(start, startClosed, end, endClosed);
	}
	
	/**
	 * 終了日時と期間の長さより、期間を返す。
	 * 
	 * @param end 終了日時（上側限界値）. {@code null}の場合は、限界がないことを表す
	 * @param length 期間の長さ
	 * @return 期間
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	public static TimeOfDayInterval preceding(TimeOfDay end, Duration length) {
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
	 * @since 1.0
	 */
	public static TimeOfDayInterval startingFrom(TimeOfDay start, boolean startClosed, Duration length,
			boolean endClosed) {
		Validate.notNull(start);
		Validate.notNull(length);
		TimeOfDay end = start.plus(length);
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
	 * @since 1.0
	 */
	public static TimeOfDayInterval startingFrom(TimeOfDay start, Duration length) {
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
	 * @since 1.0
	 */
	public TimeOfDayInterval(TimeOfDay start, boolean startIncluded, TimeOfDay end, boolean endIncluded) {
		super(start, startIncluded, end, endIncluded);
	}
	
	/**
	 * この期間の終了日時を取得する。
	 * 
	 * @return この期間の終了日時. 上側限界がない場合は {@code null}
	 * @since 1.0
	 */
	public TimeOfDay end() {
		return upperLimit();
	}
	
	/**
	 * この期間と与えた期間 {@code interval} の積集合（共通部分）を返す。
	 * 
	 * <p>共通部分が存在しない場合は、空の区間を返す。</p>
	 * 
	 * @param interval 比較対象の期間
	 * @return 積集合（共通部分）
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	public TimeOfDayInterval intersect(TimeOfDayInterval interval) {
		Validate.notNull(interval);
		return (TimeOfDayInterval) super.intersect(interval);
	}
	
	/**
	 * 指定した日時が、この期間の開始日時以前でないかどうかを検証する。
	 * 
	 * @param point 日時
	 * @return 開始日時以前でない場合は{@code true}、そうでない場合は{@code false}
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @see Interval#isAbove(Comparable)
	 * @since 1.0
	 */
	public boolean isAfter(TimeOfDay point) {
		Validate.notNull(point);
		return isAbove(point);
	}
	
	/**
	 * 指定した日時が、この期間の終了日時を以後でないかどうかを検証する。
	 * 
	 * @param point 日時
	 * @return 終了日時以後でない場合は{@code true}、そうでない場合は{@code false}
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @see Interval#isBelow(Comparable)
	 * @since 1.0
	 */
	public boolean isBefore(TimeOfDay point) {
		Validate.notNull(point);
		return isBelow(point);
	}
	
	/**
	 * この期間の長さを取得する。
	 * 
	 * @return 長さ. もし開始日時または終了日時が存在しない（無限）場合は{@code null}を返す。
	 * @throws IllegalStateException この期間が開始日時（下側限界）または終了日時（下側限界）を持たない場合
	 * @since 1.0
	 */
	public Duration length() {
		if (hasLowerLimit() == false || hasUpperLimit() == false) {
			throw new IllegalStateException();
		}
		long e = end().toDuration().to(TimeUnit.millisecond);
		long s = start().toDuration().to(TimeUnit.millisecond);
		
		return Duration.milliseconds(e - s);
	}
	
	/**
	 * この期間と同じ型を持つ、新しい期間を返す。
	 * 
	 * @param start 下側限界値. 限界値がない場合は、{@code null}
	 * @param isStartClosed 下限値を期間に含む（閉じた下側限界）場合は{@code true}を指定する
	 * @param end 上側限界値. 限界値がない場合は、{@code null}
	 * @param isEndClosed 上限値を期間に含む（閉じた上側限界）場合は{@code true}を指定する
	 * @return 新しい期間
	 * @since 1.0
	 */
	@Override
	public Interval<TimeOfDay> newOfSameType(TimeOfDay start, boolean isStartClosed, TimeOfDay end, boolean isEndClosed) {
		return new TimeOfDayInterval(start, isStartClosed, end, isEndClosed);
	}
	
	/**
	 * この期間の開始日時を取得する。
	 * 
	 * @return この期間の開始日時. 下側限界がない場合は {@code null}
	 * @since 1.0
	 */
	public TimeOfDay start() {
		return lowerLimit();
	}
	
	/**
	 * この期間の開始日時を起点として、指定した時間の長さを持ち前回の終了日時を開始日時とする期間 {@link TimeOfDayInterval} を
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
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	public Iterator<TimeOfDayInterval> subintervalIterator(Duration subintervalLength) {
		Validate.notNull(subintervalLength);
		if (hasLowerLimit() == false) {
			throw new IllegalStateException();
		}
		final Duration segmentLength = subintervalLength;
		return new ImmutableIterator<TimeOfDayInterval>() {
			
			TimeOfDayInterval next = segmentLength.startingFrom(start());
			
			
			@Override
			public boolean hasNext() {
				return TimeOfDayInterval.this.covers(next);
			}
			
			@Override
			public TimeOfDayInterval next() {
				if (hasNext() == false) {
					throw new NoSuchElementException();
				}
				TimeOfDayInterval current = next;
				next = segmentLength.startingFrom(next.end());
				return current;
			}
		};
	}
	
}