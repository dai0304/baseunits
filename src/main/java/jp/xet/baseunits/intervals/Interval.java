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
 * Copyright (c) 2004 Domain Language, Inc. (http://domainlanguage.com)
 * This free software is distributed under the "MIT" licence.
 * For more information, see http://timeandmoney.sourceforge.net.
 */
package jp.xet.baseunits.intervals;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.Validate;

/**
 * 「区間」を表すクラス。
 * 
 * <p>閉区間とは、{@code lower <= x <= upper}であらわされる区間であり、
 * 開区間とは、{@code lower < x < upper}であらわされる区間である。
 * どちらか一方のみが {@code <=} で、他方が {@code <} である場合は、半開区間と言う。</p>
 * 
 * The rules of this class are consistent with the common mathematical
 * definition of "interval". For a simple explanation, see
 * http://en.wikipedia.org/wiki/Interval_(mathematics)
 * 
 * Interval (and its "ConcreteInterval" subclass) can be used for any objects
 * that have a natural ordering reflected by implementing the Comparable
 * interface. For example, Integer implements Comparable, so if you want to
 * check if an Integer is within a range, make an Interval. Any class of yours
 * which implements Comparable can have intervals defined this way.
 * 
 * @param <T> 区間要素の型
 * @author daisuke
 * @since 1.0
 */
@SuppressWarnings("serial")
public class Interval<T extends Comparable<T> & Serializable> implements Serializable {
	
	/**
	 * 下側限界のみを持つ区間を生成する。
	 * 
	 * <p>下側限界値は区間に含む（閉じている）区間である。</p>
	 * 
	 * @param <T> 限界値の型
	 * @param lower 下側限界値. {@code null}の場合は、限界がないことを表す
	 * @return 区間
	 * @since 1.0
	 */
	public static <T extends Comparable<T> & Serializable>Interval<T> andMore(T lower) {
		return closed(lower, null);
	}
	
	/**
	 * 閉区間を生成する。
	 * 
	 * @param <T> 限界値の型
	 * @param lower 下側限界値. {@code null}の場合は、限界がないことを表す
	 * @param upper 上側限界値. {@code null}の場合は、限界がないことを表す
	 * @return 閉区間
	 * @throws IllegalArgumentException 下限値が上限値より大きい場合
	 * @since 1.0
	 */
	public static <T extends Comparable<T> & Serializable>Interval<T> closed(T lower, T upper) {
		return new Interval<T>(lower, true, upper, true);
	}
	
	/**
	 * 空区間を生成する。
	 * 
	 * @param someValue {@code null}ではない何らかの値。なんでもよい。
	 * @return 空区間
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 */
	public static <T extends Comparable<T> & Serializable>Interval<T> empty(T someValue) {
		Validate.notNull(someValue);
		return new Interval<T>(someValue, false, someValue, false);
	}
	
	/**
	 * 下側限界のみを持つ区間を生成する。
	 * 
	 * <p>下側限界値は区間に含まない（開いている）区間である。</p>
	 * 
	 * @param <T> 限界値の型
	 * @param lower 下側限界値. {@code null}の場合は、限界がないことを表す
	 * @return 区間
	 * @since 1.0
	 */
	public static <T extends Comparable<T> & Serializable>Interval<T> moreThan(T lower) {
		return open(lower, null);
	}
	
	/**
	 * 開区間を生成する。
	 * 
	 * @param <T> 限界値の型
	 * @param lower 下側限界値. {@code null}の場合は、限界がないことを表す
	 * @param upper 上側限界値. {@code null}の場合は、限界がないことを表す
	 * @return 開区間
	 * @throws IllegalArgumentException 下限値が上限値より大きい場合
	 * @since 1.0
	 */
	public static <T extends Comparable<T> & Serializable>Interval<T> open(T lower, T upper) {
		return new Interval<T>(lower, false, upper, false);
	}
	
	/**
	 * 区間を生成する。
	 * 
	 * <p>主に、半開区間（上限下限のどちらか一方だけが開いている区間）の生成に用いる。</p>
	 * 
	 * @param <T> 限界値の型
	 * @param lower 下側限界値. {@code null}の場合は、限界がないことを表す
	 * @param lowerIncluded 下限値を区間に含む（閉じた下側限界）場合は{@code true}を指定する
	 * @param upper 上側限界値. {@code null}の場合は、限界がないことを表す
	 * @param upperIncluded 上限値を区間に含む（閉じた上側限界）場合は{@code true}を指定する
	 * @return 区間
	 * @throws IllegalArgumentException 下限値が上限値より大きい場合
	 * @since 1.0
	 */
	public static <T extends Comparable<T> & Serializable>Interval<T> over(T lower, boolean lowerIncluded, T upper,
			boolean upperIncluded) {
		return new Interval<T>(lower, lowerIncluded, upper, upperIncluded);
	}
	
	/**
	 * 単一要素区間を生成する。
	 * 
	 * @param element 単一要素となる値
	 * @param <T> 限界値の型
	 * @return 区間
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	public static <T extends Comparable<T> & Serializable>Interval<T> singleElement(T element) {
		Validate.notNull(element);
		return closed(element, element);
	}
	
	/**
	 * 上側限界のみを持つ区間を生成する。
	 * 
	 * <p>上側限界値は区間に含まない（開いている）区間である。</p>
	 * 
	 * @param <T> 限界値の型
	 * @param upper 上側限界値. {@code null}の場合は、限界がないことを表す
	 * @return 区間
	 * @since 1.0
	 */
	public static <T extends Comparable<T> & Serializable>Interval<T> under(T upper) {
		return open(null, upper);
	}
	
	/**
	 * 上側限界のみを持つ区間を生成する。
	 * 
	 * <p>上側限界値は区間に含む（閉じている）区間である。</p>
	 * 
	 * @param <T> 限界値の型
	 * @param upper 上側限界値. {@code null}の場合は、限界がないことを表す
	 * @return 区間
	 * @since 1.0
	 */
	public static <T extends Comparable<T> & Serializable>Interval<T> upTo(T upper) {
		return closed(null, upper);
	}
	
	
	/** 下側限界 */
	final IntervalLimit<T> lowerLimitObject;
	
	/** 上側限界 */
	final IntervalLimit<T> upperLimitObject;
	
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param lower 下側限界値. {@code null}の場合は、限界がないことを表す
	 * @param isLowerClosed 下限値が閉区間である場合は {@code true}を指定する
	 * @param upper 上側限界値. {@code null}の場合は、限界がないことを表す
	 * @param isUpperClosed 上限値が閉区間である場合は {@code true}を指定する
	 * @throws IllegalArgumentException 下限値が上限値より大きい場合
	 * @since 1.0
	 */
	protected Interval(T lower, boolean isLowerClosed, T upper, boolean isUpperClosed) {
		this(IntervalLimit.lower(isLowerClosed, lower), IntervalLimit.upper(isUpperClosed, upper));
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param lower 下側限界
	 * @param upper 上側限界
	 * @throws IllegalArgumentException {@code lower.isLower() == false}または {@code uppser.isUpper() == false} の場合
	 * @throws IllegalArgumentException 下限値が上限値より大きい場合
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 */
	Interval(IntervalLimit<T> lower, IntervalLimit<T> upper) {
		Validate.notNull(lower);
		Validate.notNull(upper);
		checkLowerIsLessThanOrEqualUpper(lower, upper);
		
		// 単一要素区間であり、かつ、どちらか片方が開いている場合、両者を開く。
		// [5, 5) や (5, 5] を [5, 5] にする。(5, 5)は空区間だから除外。
		if (upper.isInfinity() == false && lower.isInfinity() == false
				&& upper.getValue().equals(lower.getValue())
				&& (lower.isOpen() ^ upper.isOpen())) {
			if (lower.isOpen()) {
				lower = IntervalLimit.lower(true, lower.getValue());
			}
			if (upper.isOpen()) {
				upper = IntervalLimit.upper(true, upper.getValue());
			}
		}
		this.lowerLimitObject = lower;
		this.upperLimitObject = upper;
	}
	
	/**
	 * この区間の<b>補</b>区間と与えた区間 {@code other} の共通部分を返す。
	 * 
	 * <p>この区間と与えた区間に共通部分がない場合は、 {@code other} を要素とする要素数1の区間列を返す。
	 * 与えた区間が、この区間を完全に内包する場合は、2つの区間に分かれるため、要素数が2の区間列を返す。
	 * 逆にこの区間が、与えた区間を完全に内包する場合は、要素数0の区間列を返す。
	 * 上記以外の場合、この区間の補区間と与えた区間の共通部分を要素とする要素数1の区間列を返す。</p>
	 * 
	 * @param other 対照となる区間
	 * @return 補区間と対照区間の共通部分のリスト
	 * @see <a href="http://en.wikipedia.org/wiki/Set_theoretic_complement">complement (wikipedia)</a>
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	public List<Interval<T>> complementRelativeTo(Interval<T> other) {
		Validate.notNull(other);
		List<Interval<T>> intervalSequence = new ArrayList<Interval<T>>();
		if (this.intersects(other) == false) {
			intervalSequence.add(other);
			return intervalSequence;
		}
		Interval<T> left = leftComplementRelativeTo(other);
		if (left != null) {
			intervalSequence.add(left);
		}
		Interval<T> right = rightComplementRelativeTo(other);
		if (right != null) {
			intervalSequence.add(right);
		}
		return intervalSequence;
	}
	
	/**
	 * この区間が、指定した区間 {@code other}を完全に内包するかどうかを検証する。
	 * 
	 * @param other 区間
	 * @return 完全に内包する場合は{@code true}、そうでない場合は{@code false}
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	public boolean covers(Interval<T> other) {
		Validate.notNull(other);
		
		int lowerComparison = lowerLimit().compareTo(other.lowerLimit());
		boolean lowerPass = this.includes(other.lowerLimit())
				|| (lowerComparison == 0 && other.includesLowerLimit() == false);
		
		int upperComparison = upperLimit().compareTo(other.upperLimit());
		boolean upperPass = this.includes(other.upperLimit())
				|| (upperComparison == 0 && other.includesUpperLimit() == false);
		
		return lowerPass && upperPass;
	}
	
	/**
	 * この区間と同じ限界値を持つ、新しい開区間を生成する。
	 * 
	 * @return 新しい開区間
	 * @since 1.0
	 */
	public Interval<T> emptyOfSameType() {
		return empty(lowerLimit());
	}
	
	/**
	 * この区間と、与えた区間 {@code other}の同一性を検証する。
	 * 
	 * <p>両者が共に空の区間であった場合は{@code true}、どちらか一方のみが空の区間であった場合は{@code false}を返す。
	 * 両者とも単一要素区間であった場合は、単一要素となる限界値同士を比較し、一致した場合は{@code true}を返す。
	 * また、どちらか一方のみが単一要素区間であった場合は{@code false}を返す。</p>
	 * 
	 * <p>{@code other}が{@code null}であった場合は、必ず{@code false}を返す。</p>
	 * 
	 * @param other 比較対象の区間
	 * @return 同一である場合は{@code true}、そうでない場合は{@code false}
	 * @since 1.0
	 */
	public boolean equals(Interval<T> other) {
		if (other == null) {
			return false;
		}
		
		boolean thisEmpty = this.isEmpty();
		boolean otherEmpty = other.isEmpty();
		if (thisEmpty & otherEmpty) {
			return true;
		}
		if (thisEmpty ^ otherEmpty) {
			return false;
		}
		
		boolean thisSingle = this.isSingleElement();
		boolean otherSingle = other.isSingleElement();
		if (thisSingle & otherSingle) {
			return this.lowerLimit().equals(other.lowerLimit());
		}
		if (thisSingle ^ otherSingle) {
			return false;
		}
		
		return upperLimitObject.compareTo(other.upperLimitObject) == 0
				&& lowerLimitObject.compareTo(other.lowerLimitObject) == 0;
	}
	
	/**
	 * このオブジェクトと、与えたオブジェクト {@code other}の同一性を検証する。
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 * @since 1.0
	 */
	@Override
	@SuppressWarnings("unchecked")
	public boolean equals(Object other) {
		try {
			return equals((Interval<T>) other);
		} catch (ClassCastException ex) {
			return false;
		}
	}
	
	/**
	 * この区間と与えた区間 {@code other} の間にある区間を取得する。
	 * 
	 * <p>例えば、[3, 5) と [10, 20) の gap は、[5, 19) である。
	 * 2つの区間が共通部分を持つ場合は、空の区間を返す。</p>
	 * 
	 * @param other 比較対象の区間
	 * @return ギャップ区間
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	public Interval<T> gap(Interval<T> other) {
		Validate.notNull(other);
		if (this.intersects(other)) {
			return this.emptyOfSameType();
		}
		
		return newOfSameType(lesserOfUpperLimits(other), lesserOfUpperIncludedInUnion(other) == false,
				greaterOfLowerLimits(other), greaterOfLowerIncludedInUnion(other) == false);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((lowerLimitObject == null) ? 0 : lowerLimitObject.hashCode());
		result = prime * result + ((upperLimitObject == null) ? 0 : upperLimitObject.hashCode());
		return result;
	}
	
	/**
	 * 下側限界があるかどうかを取得する。
	 * 
	 * <p>Warning: This method should generally be used for display
	 * purposes and interactions with closely coupled classes.
	 * Look for (or add) other methods to do computations.</p>
	 * 
	 * <p>警告：このメソッドは一般的に、この値の表示目的および、このクラスと結合度の高いクラスとの
	 * インタラクションに使用する。不用意なこのメソッドの使用は、このクラスとクライアント側のクラスの
	 * 結合度をいたずらに高めてしまうことを表す。この値を計算に使用したい場合は、他の適切なメソッドを探すか、
	 * このクラスに新しいメソッドを追加することを検討すること。</p>
	 * 
	 * @return 下側限界がある場合は{@code true}、そうでない場合は{@code false}
	 * @since 1.0
	 */
	public boolean hasLowerLimit() {
		return lowerLimit() != null;
	}
	
	/**
	 * 上側限界があるかどうかを取得する。
	 * 
	 * <p>Warning: This method should generally be used for display
	 * purposes and interactions with closely coupled classes.
	 * Look for (or add) other methods to do computations.</p>
	 * 
	 * <p>警告：このメソッドは一般的に、この値の表示目的および、このクラスと結合度の高いクラスとの
	 * インタラクションに使用する。不用意なこのメソッドの使用は、このクラスとクライアント側のクラスの
	 * 結合度をいたずらに高めてしまうことを表す。この値を計算に使用したい場合は、他の適切なメソッドを探すか、
	 * このクラスに新しいメソッドを追加することを検討すること。</p>
	 * 
	 * @return 上側限界がある場合は{@code true}、そうでない場合は{@code false}
	 * @since 1.0
	 */
	public boolean hasUpperLimit() {
		return upperLimit() != null;
	}
	
	/**
	 * 指定した値 {@code value} が、この区間に含まれるかどうかを検証する。
	 * 
	 * @param value 値
	 * @return 含まれる場合は{@code true}、そうでない場合は{@code false}
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	public boolean includes(T value) {
		Validate.notNull(value);
		return isBelow(value) == false && isAbove(value) == false;
	}
	
	/**
	 * 下側限界が閉じているかどうかを取得する。
	 * 
	 * <p>Warning: This method should generally be used for display
	 * purposes and interactions with closely coupled classes.
	 * Look for (or add) other methods to do computations.</p>
	 * 
	 * <p>警告：このメソッドは一般的に、この値の表示目的および、このクラスと結合度の高いクラスとの
	 * インタラクションに使用する。不用意なこのメソッドの使用は、このクラスとクライアント側のクラスの
	 * 結合度をいたずらに高めてしまうことを表す。この値を計算に使用したい場合は、他の適切なメソッドを探すか、
	 * このクラスに新しいメソッドを追加することを検討すること。</p>
	 * 
	 * @return 下側限界が閉じている場合は{@code true}、そうでない場合は{@code false}
	 * @since 1.0
	 */
	public boolean includesLowerLimit() {
		return lowerLimitObject.isClosed();
	}
	
	/**
	 * 上側限界が閉じているかどうかを取得する。
	 * 
	 * <p>Warning: This method should generally be used for display
	 * purposes and interactions with closely coupled classes.
	 * Look for (or add) other methods to do computations.</p>
	 * 
	 * <p>警告：このメソッドは一般的に、この値の表示目的および、このクラスと結合度の高いクラスとの
	 * インタラクションに使用する。不用意なこのメソッドの使用は、このクラスとクライアント側のクラスの
	 * 結合度をいたずらに高めてしまうことを表す。この値を計算に使用したい場合は、他の適切なメソッドを探すか、
	 * このクラスに新しいメソッドを追加することを検討すること。</p>
	 * 
	 * @return 上側限界値が閉じている場合は{@code true}、そうでない場合は{@code false}
	 * @since 1.0
	 */
	public boolean includesUpperLimit() {
		return upperLimitObject.isClosed();
	}
	
	/**
	 * この区間と与えた区間 {@code other} の積集合（共通部分）を返す。
	 * 
	 * <p>共通部分が存在しない場合は、空の区間を返す。</p>
	 * 
	 * @param other 比較対象の区間
	 * @return 積集合（共通部分）
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	public Interval<T> intersect(Interval<T> other) {
		Validate.notNull(other);
		T intersectLowerBound = greaterOfLowerLimits(other);
		T intersectUpperBound = lesserOfUpperLimits(other);
		if (intersectLowerBound.compareTo(intersectUpperBound) > 0) {
			return emptyOfSameType();
		}
		return newOfSameType(intersectLowerBound, greaterOfLowerIncludedInIntersection(other), intersectUpperBound,
				lesserOfUpperIncludedInIntersection(other));
	}
	
	/**
	 * この区間と、与えた区間{@code other}の間に共通部分が存在するかどうか検証する。
	 * 
	 * @param other 対象区間
	 * @return 共通部分が存在する場合は{@code true}、そうでない場合は{@code false}
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	public boolean intersects(Interval<T> other) {
		Validate.notNull(other);
		if (upperLimit() == null && other.upperLimit() == null) {
			return true;
		}
		if (lowerLimit() == null && other.lowerLimit() == null) {
			return true;
		}
		int comparison = greaterOfLowerLimits(other).compareTo(lesserOfUpperLimits(other));
		if (comparison < 0) {
			return true;
		}
		if (comparison > 0) {
			return false;
		}
		return greaterOfLowerIncludedInIntersection(other) && lesserOfUpperIncludedInIntersection(other);
	}
	
	/**
	 * 指定した値 {@code value} が、この区間の下側限界を超過していないかどうかを検証する。
	 * 
	 * @param value 値
	 * @return 超過していない場合は{@code true}、そうでない場合は{@code false}
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	public boolean isAbove(T value) {
		Validate.notNull(value);
		if (hasLowerLimit() == false) {
			return false;
		}
		int comparison = lowerLimit().compareTo(value);
		return comparison > 0 || (comparison == 0 && includesLowerLimit() == false);
	}
	
	/**
	 * 指定した値 {@code value} が、この区間の上側限界を超過していないかどうかを検証する。
	 * 
	 * @param value 値
	 * @return 超過していない場合は{@code true}、そうでない場合は{@code false}
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	public boolean isBelow(T value) {
		Validate.notNull(value);
		if (hasUpperLimit() == false) {
			return false;
		}
		int comparison = upperLimit().compareTo(value);
		return comparison < 0 || (comparison == 0 && includesUpperLimit() == false);
	}
	
	/**
	 * この区間が閉区間であるかどうかを検証する。
	 * 
	 * @return 閉区間である場合は{@code true}、そうでない場合（半開区間を含む）は{@code false}
	 * @since 1.0
	 */
	public boolean isClosed() {
		return includesLowerLimit() && includesUpperLimit();
	}
	
	/**
	 * この区間が空であるかどうかを検証する。
	 * 
	 * <p>区間が空であるとは、上側限界値と下側限界値が同値であり、かつ、開区間であることを示す。
	 * 例えば {@code 3 < x < 3}のような状態である。</p>
	 * 
	 * @return 空である場合は{@code true}、そうでない場合は{@code false}
	 * @since 1.0
	 */
	public boolean isEmpty() {
		// TODO: Consider explicit empty interval
		// A 'degenerate' interval is an empty set, {}.
		if (upperLimit() == null || lowerLimit() == null) {
			return false;
		}
		return isOpen() && upperLimit().equals(lowerLimit());
	}
	
	/**
	 * この区間が開区間であるかどうかを検証する。
	 * 
	 * @return 開区間である場合は{@code true}、そうでない場合（半開区間を含む）は{@code false}
	 * @since 1.0
	 */
	public boolean isOpen() {
		return includesLowerLimit() == false && includesUpperLimit() == false;
	}
	
	/**
	 * この区間が単一要素区間であるかどうかを検証する。
	 * 
	 * <p>単一要素区間は、上側下側の両限界を持ち、さらにそれらの限界値が同値であり、かつ、開区間ではないことを示す。
	 * 例えば {@code 3 <= x < 3}, {@code 3 < x <= 3}, {@code 3 <= x <= 3}のような状態である。</p>
	 * 
	 * @return 単一要素区間である場合は{@code true}、そうでない場合は{@code false}
	 * @since 1.0
	 */
	public boolean isSingleElement() {
		if (hasUpperLimit() == false) {
			return false;
		}
		if (hasLowerLimit() == false) {
			return false;
		}
		//An interval containing a single element, {a}.
		return upperLimit().equals(lowerLimit()) && isEmpty() == false;
	}
	
	/**
	 * 下側限界値を取得する。
	 * 
	 * <p>Warning: This method should generally be used for display
	 * purposes and interactions with closely coupled classes.
	 * Look for (or add) other methods to do computations.</p>
	 * 
	 * <p>警告：このメソッドは一般的に、この値の表示目的および、このクラスと結合度の高いクラスとの
	 * インタラクションに使用する。不用意なこのメソッドの使用は、このクラスとクライアント側のクラスの
	 * 結合度をいたずらに高めてしまうことを表す。この値を計算に使用したい場合は、他の適切なメソッドを探すか、
	 * このクラスに新しいメソッドを追加することを検討すること。</p>
	 * 
	 * @return 下側限界値. 限界がない場合は、{@code null}
	 * @since 1.0
	 */
	public T lowerLimit() {
		return lowerLimitObject.getValue();
	}
	
	/**
	 * この区間と同じ型{@code T}を持つ、新しい区間を生成する。
	 * 
	 * @param lower 下側限界値. 限界値がない場合は、{@code null}
	 * @param isLowerClosed 下限値を区間に含む（閉じた下側限界）場合は{@code true}を指定する
	 * @param upper 上側限界値. 限界値がない場合は、{@code null}
	 * @param isUpperClosed 上限値を区間に含む（閉じた上側限界）場合は{@code true}を指定する
	 * @return 新しい区間
	 * @since 1.0
	 */
	public Interval<T> newOfSameType(T lower, boolean isLowerClosed, T upper, boolean isUpperClosed) {
		return new Interval<T>(lower, isLowerClosed, upper, isUpperClosed);
	}
	
	/**
	 * 区間の文字列表現を取得する。
	 * 
	 * <p>空の区間である場合は "&#123;&#125;", 単一要素区間である場合は "&#123;x&#125;"を返す。
	 * また、例えば 3〜5 の開区間である場合は "(3, 5)"、閉区間である場合は "[3, 5]"、
	 * 半開区間であれば "[3, 5)" または "(3, 5]" の様に、開いた限界を "()"、
	 * 閉じた限界を "[]" で表現する。</p>
	 * 
	 * @see java.lang.Object#toString()
	 * @since 1.0
	 */
	@Override
	public String toString() {
		if (isEmpty()) {
			return "{}";
		}
		if (isSingleElement()) {
			return "{" + lowerLimit().toString() + "}";
		}
		return toStringDetail();
	}
	
	/**
	 * 上側限界値を取得する。
	 * 
	 * <p>Warning: This method should generally be used for display
	 * purposes and interactions with closely coupled classes.
	 * Look for (or add) other methods to do computations.</p>
	 * 
	 * <p>警告：このメソッドは一般的に、この値の表示目的および、このクラスと結合度の高いクラスとの
	 * インタラクションに使用する。不用意なこのメソッドの使用は、このクラスとクライアント側のクラスの
	 * 結合度をいたずらに高めてしまうことを表す。この値を計算に使用したい場合は、他の適切なメソッドを探すか、
	 * このクラスに新しいメソッドを追加することを検討すること。</p>
	 * 
	 * @return 上側限界値. 限界がない場合は、{@code null}
	 * @since 1.0
	 */
	public T upperLimit() {
		return upperLimitObject.getValue();
	}
	
	/**
	 * この区間と与えた区間 {@code other} の下側限界値のうち、より大きい（限界の狭い、制約の大きい）限界値を返す。
	 * 
	 * @param other 比較対象の限界値
	 * @return より大きい限界値
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 */
	T greaterOfLowerLimits(Interval<T> other) {
		Validate.notNull(other);
		if (lowerLimit() == null) {
			return other.lowerLimit();
		}
		if (other.lowerLimit() == null) {
			return lowerLimit();
		}
		int lowerComparison = lowerLimit().compareTo(other.lowerLimit());
		if (lowerComparison >= 0) {
			return this.lowerLimit();
		}
		return other.lowerLimit();
	}
	
	/**
	 * この区間と与えた区間 {@code other} の上側限界値のうち、より小さい（限界の狭い、制約の大きい）限界値を返す。
	 * 
	 * @param other 比較対象の限界値
	 * @return より小さい限界値
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 */
	T lesserOfUpperLimits(Interval<T> other) {
		Validate.notNull(other);
		if (upperLimit() == null) {
			return other.upperLimit();
		}
		if (other.upperLimit() == null) {
			return upperLimit();
		}
		int upperComparison = upperLimit().compareTo(other.upperLimit());
		if (upperComparison <= 0) {
			return this.upperLimit();
		}
		return other.upperLimit();
	}
	
	String toStringDetail() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(includesLowerLimit() ? "[" : "(");
		buffer.append(hasLowerLimit() ? lowerLimit().toString() : "Infinity");
		buffer.append(", ");
		buffer.append(hasUpperLimit() ? upperLimit().toString() : "Infinity");
		buffer.append(includesUpperLimit() ? "]" : ")");
		return buffer.toString();
	}
	
//	/**
//	 * 区間をグラフィカルに確認するためのデバッグ用メソッド。
//	 * 
//	 * <p>単一要素区間はキャラクタ{@code @}で表示する。
//	 * 下側限界がない場合はキャラクタ{@code <}で表示し、上側限界がない場合はキャラクタ{@code >}で表示する。
//	 * 下側限界が開区間である場合はキャラクタ{@code (}、閉区間である場合はキャラクタ{@code [}で表示する。
//	 * 上側限界が開区間である場合はキャラクタ{@code )}、閉区間である場合はキャラクタ{@code ]}で表示する。
//	 * 区間内の要素はキャラクタ{@code -}で表示する。</p>
//	 * 
//	 * @return 文字列
//	 */
//	String toStringGraphically() {
//		StringBuilder sb = new StringBuilder();
//		
//		sb.append(" ");
//		
//		if (isEmpty()) {
//			sb.append("EMPTY");
//		} else if (isSingleElement()) {
//			for (int i = 0; i < (Integer) lowerLimit(); i++) {
//				sb.append(" ");
//			}
//			sb.append("@");
//		} else {
//			if (lowerLimit() == null) {
//				sb.deleteCharAt(0);
//				sb.append("<");
//			} else {
//				for (int i = 0; i < (Integer) lowerLimit(); i++) {
//					sb.append(" ");
//				}
//				sb.append(includesLowerLimit() ? "[" : "(");
//			}
//			
//			if (upperLimit() == null) {
//				sb.append(">");
//			} else {
//				int l = lowerLimit() == null ? -1 : (Integer) lowerLimit();
//				int u = upperLimit() == null ? 100 : (Integer) upperLimit(); // CHECKSTYLE IGNORE THIS LINE
//				for (int i = 0; i < u - l - 1; i++) {
//					sb.append("-");
//				}
//				sb.append(includesUpperLimit() ? "]" : ")");
//			}
//		}
//		
//		return sb.toString();
//	}
	
	private void checkLowerIsLessThanOrEqualUpper(IntervalLimit<T> lower, IntervalLimit<T> upper) {
		if ((lower.isLower() && upper.isUpper() && lower.compareTo(upper) <= 0) == false) {
			throw new IllegalArgumentException(lower + " is not before or equal to " + upper);
		}
	}
	
	private boolean greaterOfLowerIncludedInIntersection(Interval<T> other) {
		T limit = greaterOfLowerLimits(other);
		return this.includes(limit) && other.includes(limit);
	}
	
	private boolean greaterOfLowerIncludedInUnion(Interval<T> other) {
		T limit = greaterOfLowerLimits(other);
		return this.includes(limit) || other.includes(limit);
	}
	
	/**
	 * この区間の下側<b>補</b>区間と与えた区間 {@code other} の共通部分を返す。
	 *
	 * @param other 比較対象の区間
	 * @return この区間の下側の補区間と、与えた区間の共通部分。存在しない場合は {@code null}
	 */
	private Interval<T> leftComplementRelativeTo(Interval<T> other) {
		// この区間の下側限界値の方が小さいか等しい場合、下側の補区間に共通部分は無い
		if (this.lowerLimitObject.compareTo(other.lowerLimitObject) <= 0) {
			return null;
		}
		return newOfSameType(other.lowerLimit(), other.includesLowerLimit(), this.lowerLimit(),
				this.includesLowerLimit() == false);
	}
	
	private boolean lesserOfUpperIncludedInIntersection(Interval<T> other) {
		T limit = lesserOfUpperLimits(other);
		return this.includes(limit) && other.includes(limit);
	}
	
	private boolean lesserOfUpperIncludedInUnion(Interval<T> other) {
		T limit = lesserOfUpperLimits(other);
		return this.includes(limit) || other.includes(limit);
	}
	
	/**
	 * この区間の上側<b>補</b>区間と与えた区間 {@code other} の共通部分を返す。
	 *
	 * @param other 比較対象の区間
	 * @return この区間の上側の補区間と、与えた区間の共通部分。存在しない場合は {@code null}
	 */
	private Interval<T> rightComplementRelativeTo(Interval<T> other) {
		// この区間の上側限界値の方が大きいか等しい場合、上側の補区間に共通部分は無い
		if (this.upperLimitObject.compareTo(other.upperLimitObject) >= 0) {
			return null;
		}
		return newOfSameType(this.upperLimit(), this.includesUpperLimit() == false, other.upperLimit(),
				other.includesUpperLimit());
	}
}
