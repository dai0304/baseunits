/**
 * Copyright (c) 2004 Domain Language, Inc. (http://domainlanguage.com) This
 * free software is distributed under the "MIT" licence. See file licence.txt.
 * For more information, see http://timeandmoney.sourceforge.net.
 */

package com.domainlanguage.intervals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 * 区間列（複数の {@link Interval 区間} の列）を表すクラス。
 * 
 * @param <T> {@link Interval 区間}の型
 * @author daisuke
 */
public class IntervalSequence<T extends Comparable<T>> implements Iterable<Interval<T>> {
	
	final List<Interval<T>> intervals;
	
	final Comparator<Interval<T>> comparator;
	

	/**
	 * インスタンスを生成する。
	 */
	public IntervalSequence() {
		this(new IntervalComparatorUpperLower<T>());
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param comparator コンパレータ
	 */
	public IntervalSequence(Comparator<Interval<T>> comparator) {
		intervals = new ArrayList<Interval<T>>();
		this.comparator = comparator;
	}
	
	/**
	 * 区間列に{@link Interval 区間}を追加する。
	 * 
	 * @param interval 追加する{@link Interval 区間}
	 */
	public void add(Interval<T> interval) {
		intervals.add(interval);
		Collections.sort(intervals, comparator);
	}
	
	/**
	 * 全ての要素区間を内包する、最小の区間を返す。
	 * 
	 * @return 全ての要素区間を内包する、最小の区間
	 * @throws IllegalStateException 要素が1つもない場合
	 */
	public Interval<T> extent() {
		if (intervals.isEmpty()) {
			// TODO: Add a creation method to Interval for empty(), if it can be polymorphic.
			throw new IllegalStateException();
		}
		if (intervals.size() == 1) {
			return intervals.get(0);
		}
		Interval<T> left = intervals.get(0);
		Interval<T> right = intervals.get(intervals.size() - 1);
		
		for (Interval<T> interval : intervals) {
			if (interval.lowerLimit() == null) {
				left = interval;
			}
			if (interval.upperLimit() == null) {
				right = interval;
			}
		}
		
		return left.newOfSameType(left.lowerLimit(), left.includesLowerLimit(), right.upperLimit(),
				right.includesUpperLimit());
	}
	
	/**
	 * 要素区間の補区間をあらわす区間列を返す。但し、片側に限界のない区間は含まない。
	 * 
	 * @return ギャップ区間の列
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
	 * 隣あった要素区間同士が重なっている区間を区間列として返す。
	 * 
	 * <p>区間数が2つ未満の場合は、空の区間列を返す。</p>
	 * 
	 * @return 共通区間の列
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
	 */
	@Override
	public Iterator<Interval<T>> iterator() {
		return intervals.iterator();
	}
	
//	String toStringGraphically() {
//		StringBuilder sb = new StringBuilder();
//		for (Interval<T> interval : intervals) {
//			sb.append(interval.toStringGraphically()).append(SystemUtils.LINE_SEPARATOR);
//		}
//		return sb.toString();
//	}
}
