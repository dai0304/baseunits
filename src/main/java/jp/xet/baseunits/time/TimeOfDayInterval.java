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

import java.util.Iterator;
import java.util.NoSuchElementException;

import jp.xet.baseunits.intervals.Interval;
import jp.xet.baseunits.util.ImmutableIterator;

import com.google.common.base.Preconditions;

/**
 * 日内期間（時間の区間）を表すクラス。
 * 
 * <p>限界の表現には {@link TimeOfDay}を利用する。また、無限限界は設定できない。</p>
 * 
 * @author daisuke
 * @since 2.0
 */
@SuppressWarnings("serial")
public class TimeOfDayInterval extends Interval<TimeOfDay> {
	
	/**
	 * 開始時刻と終了時刻より、閉期間を返す。
	 * 
	 * @param start 開始時刻（下側限界値）
	 * @param end 終了時刻（上側限界値）
	 * @return 期間
	 * @throws IllegalArgumentException 下限値が上限値より大きい（未来である）場合
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @since 2.0
	 */
	public static TimeOfDayInterval closed(TimeOfDay start, TimeOfDay end) {
		Preconditions.checkNotNull(start);
		Preconditions.checkNotNull(end);
		return over(start, true, end, true);
	}
	
	/**
	 * 開始時刻と終了時刻より、開期間を返す。
	 * 
	 * @param start 開始時刻（下側限界値）
	 * @param end 終了時刻（上側限界値）
	 * @return 期間
	 * @throws IllegalArgumentException 下限値が上限値より大きい（未来である）場合
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @since 2.0
	 */
	public static TimeOfDayInterval open(TimeOfDay start, TimeOfDay end) {
		Preconditions.checkNotNull(start);
		Preconditions.checkNotNull(end);
		return over(start, false, end, false);
	}
	
	/**
	 * 開始時刻と終了時刻より、期間を返す。
	 * 
	 * <p>主に、半開区間（上限下限のどちらか一方だけが開いている区間）の生成に用いる。</p>
	 * 
	 * @param start 開始時刻（下側限界値）
	 * @param startClosed 開始時刻を期間に含む（閉じた下側限界）場合は{@code true}を指定する
	 * @param end 終了時刻（上側限界値）
	 * @param endClosed 終了時刻を期間に含む（閉じた上側限界）場合は{@code true}を指定する
	 * @return 期間
	 * @throws IllegalArgumentException 下限値が上限値より大きい（未来である）場合
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @since 2.0
	 */
	public static TimeOfDayInterval over(TimeOfDay start, boolean startClosed, TimeOfDay end, boolean endClosed) {
		Preconditions.checkNotNull(start);
		Preconditions.checkNotNull(end);
		return new TimeOfDayInterval(start, startClosed, end, endClosed);
	}
	
	/**
	 * 開始時刻と終了時刻より、期間を返す。
	 * 
	 * <p>生成する期間の開始時刻は期間に含み（閉じている）、終了時刻は期間に含まない（開いている）半開区間を返す。</p>
	 * 
	 * @param start 開始時刻（下側限界値）
	 * @param end 終了時刻（上側限界値）
	 * @return 期間
	 * @throws IllegalArgumentException 開始時刻が終了時刻より大きい（未来である）場合
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @since 2.0
	 */
	public static TimeOfDayInterval over(TimeOfDay start, TimeOfDay end) {
		Preconditions.checkNotNull(start);
		Preconditions.checkNotNull(end);
		// Uses the common default for time intervals, [start, end).
		return over(start, true, end, false);
	}
	
	/**
	 * 終了時刻と期間の長さより、期間を返す。
	 * 
	 * @param end 終了時刻（上側限界値）
	 * @param startClosed 開始時刻を期間に含む（閉じた下側限界）場合は{@code true}を指定する
	 * @param length 期間の長さ
	 * @param endClosed 終了時刻を期間に含む（閉じた上側限界）場合は{@code true}を指定する
	 * @return 期間
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @since 2.0
	 */
	public static TimeOfDayInterval preceding(TimeOfDay end, boolean startClosed, Duration length, boolean endClosed) {
		Preconditions.checkNotNull(end);
		Preconditions.checkNotNull(length);
		TimeOfDay start = end.minus(length);
		return over(start, startClosed, end, endClosed);
	}
	
	/**
	 * 終了時刻と期間の長さより、期間を返す。
	 * 
	 * @param end 終了時刻（上側限界値）
	 * @param length 期間の長さ
	 * @return 期間
	 * @throws IllegalArgumentException 減算の結果が0時を超えた場合
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @since 2.0
	 */
	public static TimeOfDayInterval preceding(TimeOfDay end, Duration length) {
		Preconditions.checkNotNull(end);
		Preconditions.checkNotNull(length);
		if (length.isGreaterThan(end.toDuration())) {
			throw new IllegalArgumentException();
		}
		
		// Uses the common default for time intervals, [start, end).
		return preceding(end, true, length, false);
	}
	
	/**
	 * 開始時刻と期間の長さより、期間を返す。
	 * 
	 * @param start 開始時刻（下側限界値）
	 * @param startClosed 開始時刻を期間に含む（閉じた下側限界）場合は{@code true}を指定する
	 * @param length 期間の長さ
	 * @param endClosed 終了時刻を期間に含む（閉じた上側限界）場合は{@code true}を指定する
	 * @return 期間
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @since 2.0
	 */
	public static TimeOfDayInterval startingFrom(TimeOfDay start, boolean startClosed, Duration length,
			boolean endClosed) {
		Preconditions.checkNotNull(start);
		Preconditions.checkNotNull(length);
		TimeOfDay end = start.plus(length);
		return over(start, startClosed, end, endClosed);
	}
	
	/**
	 * 開始時刻と期間の長さより、期間を返す。
	 * 
	 * <p>生成する期間の開始時刻は期間に含み（閉じている）、終了時刻は期間に含まない（開いている）半開区間を返す。</p>
	 * 
	 * @param start 開始時刻（下側限界値）
	 * @param length 期間の長さ
	 * @return 期間
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @since 2.0
	 */
	public static TimeOfDayInterval startingFrom(TimeOfDay start, Duration length) {
		Preconditions.checkNotNull(start);
		Preconditions.checkNotNull(length);
		// Uses the common default for time intervals, [start, end).
		return startingFrom(start, true, length, false);
	}
	
	/**
	 * インスタンスを返す。
	 * 
	 * @param start 開始時刻（下側限界値）
	 * @param startIncluded 開始時刻を期間に含む（閉じた下側限界）場合は{@code true}を指定する
	 * @param end 終了時刻（上側限界値）
	 * @param endIncluded 終了時刻を期間に含む（閉じた上側限界）場合は{@code true}を指定する
	 * @throws IllegalArgumentException 下限値が上限値より大きい（未来である）場合
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @since 2.0
	 */
	public TimeOfDayInterval(TimeOfDay start, boolean startIncluded, TimeOfDay end, boolean endIncluded) {
		super(start, startIncluded, end, endIncluded);
		Preconditions.checkNotNull(start);
		Preconditions.checkNotNull(end);
	}
	
	/**
	 * この期間の終了時刻を取得する。
	 * 
	 * @return この期間の終了時刻
	 * @since 2.0
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
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @since 2.0
	 */
	public TimeOfDayInterval intersect(TimeOfDayInterval interval) {
		Preconditions.checkNotNull(interval);
		return (TimeOfDayInterval) super.intersect(interval);
	}
	
	/**
	 * 指定した時刻が、この期間の開始時刻以前でないかどうかを検証する。
	 * 
	 * @param point 比較対象時刻
	 * @return 開始時刻以前でない場合は{@code true}、そうでない場合は{@code false}
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @see Interval#isAbove(Comparable)
	 * @since 2.0
	 */
	public boolean isAfter(TimeOfDay point) {
		Preconditions.checkNotNull(point);
		return isAbove(point);
	}
	
	/**
	 * 指定した時刻が、この期間の終了時刻を以後でないかどうかを検証する。
	 * 
	 * @param point 比較対象時刻
	 * @return 終了時刻以後でない場合は{@code true}、そうでない場合は{@code false}
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @see Interval#isBelow(Comparable)
	 * @since 2.0
	 */
	public boolean isBefore(TimeOfDay point) {
		Preconditions.checkNotNull(point);
		return isBelow(point);
	}
	
	/**
	 * この期間の長さを取得する。
	 * 
	 * @return 長さ
	 * @since 2.0
	 */
	public Duration length() {
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
	 * @since 2.0
	 */
	@Override
	public Interval<TimeOfDay> newOfSameType(TimeOfDay start, boolean isStartClosed, TimeOfDay end, boolean isEndClosed) {
		return new TimeOfDayInterval(start, isStartClosed, end, isEndClosed);
	}
	
	/**
	 * この期間の開始時刻を取得する。
	 * 
	 * @return この期間の開始時刻. 下側限界がない場合は {@code null}
	 * @since 2.0
	 */
	public TimeOfDay start() {
		return lowerLimit();
	}
	
	/**
	 * この期間の開始時刻を起点として、指定した時間の長さを持ち前回の終了時刻を開始時刻とする期間 {@link TimeOfDayInterval} を
	 * この期間の終了時刻を超過しない範囲で順次取得する反復子を取得する。
	 * 
	 * <p>例えば [2009/01/01 02:00, 2009/01/10 15:00) で表される期間に対して、
	 * 2日間の {@code subintervalLength} を与えた場合、
	 * その戻り値の反復子からは、以下の要素が取得できる。</p>
	 * 
	 * <ol>
	 *   <li>[2009/01/01 02:00, 2009/01/03 02:00)</li>
	 *   <li>[2009/01/03 02:00, 2009/01/05 02:00)</li>
	 *   <li>[2009/01/05 02:00, 2009/01/07 02:00)</li>
	 *   <li>[2009/01/07 02:00, 2009/01/09 02:00)</li>
	 * </ol>
	 * 
	 * <p>返す反復子は {@link Iterator#remove()} をサポートしない。</p>
	 * 
	 * <p>この期間が終了時刻（上側限界）を持たない場合、 {@link Iterator#hasNext()}は常に
	 * {@code true}を返すので、無限ループに注意すること。</p>
	 * 
	 * @param subintervalLength 反復子が返す期間の長さ
	 * @return 期間の反復子
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @since 2.0
	 */
	public Iterator<TimeOfDayInterval> subintervalIterator(Duration subintervalLength) {
		Preconditions.checkNotNull(subintervalLength);
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
