/**
 * Copyright (c) 2004 Domain Language, Inc. (http://domainlanguage.com) This
 * free software is distributed under the "MIT" licence. See file licence.txt.
 * For more information, see http://timeandmoney.sourceforge.net.
 */

package com.domainlanguage.intervals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * 区間列（複数の {@link Interval 区間} の列）を表すクラス。
 * 
 * @param <T> {@link Interval 区間}の型
 * @author daisuke
 */
public class IntervalSequence<T extends Comparable<T>> implements Iterable<Interval<T>> {
	
	List<Interval<T>> intervals;
	

	/**
	 * インスタンスを生成する。
	 */
	public IntervalSequence() {
		intervals = new ArrayList<Interval<T>>();
	}
	
	/**
	 * 区間列に{@link Interval 区間}を追加する。
	 * 
	 * @param interval 追加する{@link Interval 区間}
	 */
	public void add(Interval<T> interval) {
		intervals.add(interval);
		Collections.sort(intervals);
	}
	
	/**
	 * 全ての要素区間を内包する、最小の区間を返す。
	 * 
	 * @return 全ての要素区間を内包する、最小の区間. 要素がない場合は {@code null} 
	 */
	public Interval<T> extent() {
		if (intervals.isEmpty()) {
			return null;
		}
		//TODO: Add a creation method to Interval for empty(), if it can be
		// polymorphic.
		if (intervals.size() == 1) {
			return intervals.get(0);
		}
		Interval<T> left = intervals.get(0);
		Interval<T> right = intervals.get(intervals.size() - 1);
		return left.newOfSameType(left.lowerLimit(), left.includesLowerLimit(), right.upperLimit(),
				right.includesUpperLimit());
	}
	
	/**
	 * TODO 詳細定義
	 * 
	 * @return ギャップ区間の列
	 */
	public IntervalSequence<T> gaps() {
		IntervalSequence<T> gaps = new IntervalSequence<T>();
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
	 * TODO 詳細定義
	 * 
	 * @return 共通区間の列
	 */
	public IntervalSequence<T> intersections() {
		IntervalSequence<T> intersections = new IntervalSequence<T>();
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
	
	/**
	 * Only for use by persistence mapping frameworks
	 * <rant>These methods break encapsulation and we put them in here begrudgingly</rant>
	 * @return {@link #intervals}
	 */
	@SuppressWarnings("unused")
	private List<Interval<T>> getForPersistentMapping_Intervals() { // CHECKSTYLE IGNORE THIS LINE
		return intervals;
	}
	
	/**
	 * Only for use by persistence mapping frameworks
	 * <rant>These methods break encapsulation and we put them in here begrudgingly</rant>
	 * @param intervals {@link #intervals}
	 */
	@SuppressWarnings("unused")
	private void setForPersistentMapping_Intervals(List<Interval<T>> intervals) { // CHECKSTYLE IGNORE THIS LINE
		this.intervals = intervals;
	}
}
