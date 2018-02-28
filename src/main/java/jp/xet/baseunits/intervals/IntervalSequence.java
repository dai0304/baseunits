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
package jp.xet.baseunits.intervals;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import com.google.common.base.Preconditions;

/**
 * 区間列（複数の{@link Interval 区間}の列）を表すクラス。
 * 
 * @param <T> {@link Interval 区間}の型
 * @author daisuke
 * @since 1.0
 */
public class IntervalSequence<T extends Comparable<T> & Serializable> implements Iterable<Interval<T>> {
	
	final List<Interval<T>> intervals;
	
	final Comparator<Interval<T>> comparator;
	
	
	/**
	 * インスタンスを生成する。
	 * 
	 * <p>各区間のソート条件を決定する {@link Comparator} はデフォルト
	 * （{@link IntervalComparatorUpperLower}の下位結果反転）を利用する。</p>
	 * 
	 * @since 1.0
	 */
	public IntervalSequence() {
		this(new IntervalComparatorUpperLower<T>(true, false));
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param comparator 各区間のソート条件を決定する {@link Comparator}
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	public IntervalSequence(Comparator<Interval<T>> comparator) {
		Preconditions.checkNotNull(comparator);
		intervals = new ArrayList<Interval<T>>();
		this.comparator = comparator;
	}
	
	/**
	 * 区間列に{@link Interval 区間}を追加する。
	 * 
	 * @param interval 追加する{@link Interval 区間}
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	public void add(Interval<T> interval) {
		Preconditions.checkNotNull(interval);
		intervals.add(interval);
		Collections.sort(intervals, comparator);
	}
	
	/**
	 * この区間列が含む全ての要素を削除する。
	 * 
	 * @since 2.0
	 */
	public void clear() {
		intervals.clear();
	}
	
	/**
	 * 全ての要素区間を内包する、最小の区間を返す。
	 * 
	 * @return 全ての要素区間を内包する、最小の区間
	 * @throws IllegalStateException 要素が1つもない場合
	 * @since 1.0
	 */
	public Interval<T> extent() {
		if (intervals.isEmpty()) {
			// TODO: Add a creation method to Interval for empty(), if it can be polymorphic.
			throw new IllegalStateException();
		}
		
		if (intervals.size() == 1) {
			return intervals.get(0);
		}
		
		Interval<T> firstInterval = intervals.get(0);
		IntervalLimit<T> lower = firstInterval.lowerLimitObject;
		IntervalLimit<T> upper = firstInterval.upperLimitObject;
		for (int i = 1; i < intervals.size(); i++) {
			Interval<T> interval = intervals.get(i);
			if (lower.compareTo(interval.lowerLimitObject) > 0) {
				lower = interval.lowerLimitObject;
			}
			if (upper.compareTo(interval.upperLimitObject) < 0) {
				upper = interval.upperLimitObject;
			}
		}
		
		return firstInterval.newOfSameType(lower.getValue(), lower.isClosed(), upper.getValue(),
				upper.isClosed());
	}
	
	/**
	 * ソート済みの区間で、隣り合った区間同士に挟まれる区間を区間列として返す。
	 * 
	 * <p>結果の区間列の {@link Comparator} は、この区間列の {@link Comparator} を流用する。</p>
	 * 
	 * <p>区間数が2つ未満の場合は、空の区間列を返す。また、区間同士が重なっていたり接していた場合は、
	 * その区間は結果の要素に含まない。全てが重なっている場合は、空の区間列を返す。</p>
	 * 
	 * @return ギャップ区間列
	 * @since 1.0
	 */
	public IntervalSequence<T> gaps() {
		IntervalSequence<T> gaps = new IntervalSequence<T>(comparator);
		if (intervals.size() < 2) {
			return gaps;
		}
		for (int i = 1; i < intervals.size(); i++) {
			Interval<T> left = intervals.get(i - 1);
			Interval<T> right = intervals.get(i);
			Interval<T> gap = left.gap(right);
			if (gap.isEmpty() == false) {
				gaps.add(gap);
			}
		}
		return gaps;
	}
	
	/**
	 * ソート済みの区間で、隣り合った区間同士が重なっている区間を区間列として返す。
	 * 
	 * <p>結果の区間列の {@link Comparator} は、この区間列の {@link Comparator} を流用する。</p>
	 * 
	 * <p>区間数が2つ未満の場合は、空の区間列を返す。また、区間同士が重ならなかったり接していた場合は、
	 * その区間は結果の要素に含まない。全てが重ならない場合は、空の区間列を返す。</p>
	 * 
	 * @return 共通区間列
	 * @since 1.0
	 */
	public IntervalSequence<T> intersections() {
		IntervalSequence<T> intersections = new IntervalSequence<T>(comparator);
		if (intervals.size() < 2) {
			return intersections;
		}
		for (int i = 1; i < intervals.size(); i++) {
			Interval<T> left = intervals.get(i - 1);
			Interval<T> right = intervals.get(i);
			Interval<T> intersection = left.intersect(right);
			if (intersection.isEmpty() == false) {
				intersections.add(intersection);
			}
		}
		return intersections;
	}
	
	/**
	 * 区間列の要素が空であるかどうかを検証する。
	 * 
	 * @return 空である場合は{@code true}、そうでない場合は{@code false}
	 * @since 1.0
	 */
	public boolean isEmpty() {
		return intervals.isEmpty();
	}
	
	/**
	 * この区間列の要素をあらわすイテレータを取得する。
	 * 
	 * <p>各要素は、昇順にソートされている。</p>
	 * 
	 * @see java.lang.Iterable#iterator()
	 * @since 1.0
	 */
	@Override
	public Iterator<Interval<T>> iterator() {
		return intervals.iterator();
	}
	
	@Override
	public String toString() {
		return intervals.toString();
	}
	
//	String toStringGraphically() {
//		StringBuilder sb = new StringBuilder();
//		for (Interval<T> interval : intervals) {
//			sb.append(interval.toStringGraphically()).append(SystemUtils.LINE_SEPARATOR);
//		}
//		return sb.toString();
//	}
}
