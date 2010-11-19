/**
 * Copyright (c) 2005 Domain Language, Inc. (http://domainlanguage.com) This
 * free software is distributed under the "MIT" licence. See file licence.txt.
 * For more information, see http://timeandmoney.sourceforge.net.
 */
package com.domainlanguage.intervals;

import java.io.Serializable;

/**
 * 区間における「限界」を表すクラス。
 * 
 * <p>このクラスを理解するにあたっては、「限界」と「限界値」の区別を正しく認識することが重要となる。
 * 限界とはこのクラス {@code this} で表される値であり、限界値とは、 {@link #value}で表される値である。</p>
 * 
 * <p>無限限界とは、限界を制限しないことであり、 {@link #value} が {@code null} であることで
 * この状態を表現する。逆に、無限限界ではない限界（{@link #value} が {@code null} ではないもの）を
 * 有限限界と呼ぶ。</p>
 * 
 * <p>限界が「閉じている」とは、限界値そのものを超過とみなすことを表し、
 * 「開いている」とは、これを超過とみなすことを表す。</p>
 * 
 * <p>下側限界とは、限界値以下（または未満）の値を超過とみなす限界を表し、
 * 上側限界とは、限界値以上（または超える）の値を超過とみなす限界を表す。</p>
 * 
 * @param <T> 限界の型
 * @author daisuke
 */
@SuppressWarnings("serial")
class IntervalLimit<T extends Comparable<T>> implements Comparable<IntervalLimit<T>>, Serializable {
	
	/**
	 * 下側限界インスタンスを生成する。
	 * 
	 * @param <T> 限界値の型
	 * @param closed 閉じた限界を生成する場合は {@code true}を指定する
	 * @param value 限界値. {@code null}の場合は、限界がないことを表す
	 * @return 下側限界インスタンス
	 */
	static <T extends Comparable<T>>IntervalLimit<T> lower(boolean closed, T value) {
		return new IntervalLimit<T>(closed, true, value);
	}
	
	/**
	 * 上側限界インスタンスを生成する。
	 * 
	 * @param <T> 限界値の型
	 * @param closed 閉じた限界を生成する場合は {@code true}を指定する
	 * @param value 限界値. {@code null}の場合は、限界がないことを表す
	 * @return 上側限界インスタンス
	 */
	static <T extends Comparable<T>>IntervalLimit<T> upper(boolean closed, T value) {
		return new IntervalLimit<T>(closed, false, value);
	}
	

	/** 限界が閉じている場合 {@code true} */
	private boolean closed;
	
	/**
	 * 限界値
	 * 
	 * {@code null}の場合は、限界がないことを表す。
	 */
	private T value;
	
	/** 下側限界を表す場合は {@code true}、上側限界を表す場合は {@code false} */
	private boolean lower;
	

	/**
	 * Only for use by persistence mapping frameworks
	 * <rant>These methods break encapsulation and we put them in here begrudgingly</rant>
	 */
	IntervalLimit() {
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param closed 閉じた限界を生成する場合は {@code true}を指定する
	 * @param lower 下側限界を生成する場合は {@code true}、上側限界を生成する場合は {@code false}を指定する
	 * @param value 限界値. {@code null}の場合は、限界がないことを表す
	 */
	IntervalLimit(boolean closed, boolean lower, T value) {
		this.closed = closed;
		this.lower = lower;
		this.value = value;
	}
	
	/**
	 * 限界同士の比較を行う。
	 * 
	 * <p>限界は、限界値が一致していた場合は、限界の開閉や上下にかかわらず同値と判断する。
	 * また、両者が無限限界であった場合も同様に限界の開閉や上下にかかわらず同値と判断する。</p>
	 * 
	 * <p>有限限界と無限限界の比較に関して。
	 * このオブジェクトが下側の無限限界である場合は常に「小さい」と判断し、
	 * 逆にこのオブジェクトが上側の無限限界である場合は常に「大きい」と判断する。
	 * 比較対象限界が無権限界である場合は、 {@link #compareTo(IntervalLimit)} の対称性の観点から結果を返す。</p>
	 * 
	 * <p>有限限界同士の比較に関して。
	 * この場合は、それぞれの限界の開閉や上下にかかわらず、限界値のが小さい方を「小さい」と判断する。</p>
	 * 
	 * @param other 比較対象
	 * @return 同値であった場合は {@code 0}、このオブジェクトが比較対象よりも小さい場合は負数、大きい場合は正数
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(IntervalLimit<T> other) {
		if (other == null) {
			return -1;
		}
		
		T otherValue = other.value;
		if (otherValue == value) {
			return 0;
		}
		if (value == null) {
			return lower ? -1 : 1;
		}
		if (otherValue == null) {
			return other.lower ? 1 : -1;
		}
		return value.compareTo(otherValue);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		IntervalLimit<?> other = (IntervalLimit<?>) obj;
		if (other.value == value) {
			return true;
		}
		try {
			return value.compareTo((T) other.value) == 0;
		} catch (ClassCastException e) {
			return false;
		}
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}
	
	@Override
	public String toString() {
		return String.format("<%s %s %s>", lower ? "lower" : "upper", closed ? "closed" : "open", value);
	}
	
	/**
	 * Only for use by persistence mapping frameworks
	 * <rant>These methods break encapsulation and we put them in here begrudgingly</rant>
	 * @return {@link #value}
	 */
	T getForPersistentMapping_Value() { // CHECKSTYLE IGNORE THIS LINE
		return value;
	}
	
	/**
	 * 限界値を取得する。
	 * 
	 * @return 限界値. {@code null}の場合は、限界がないことを表す
	 */
	T getValue() {
		return value;
	}
	
	/**
	 * この限界が閉じているかどうかを検証する。
	 * 
	 * @return 閉じている場合は{@code true}、そうでない場合は{@code false}
	 */
	boolean isClosed() {
		return closed;
	}
	
	/**
	 * Only for use by persistence mapping frameworks
	 * <rant>These methods break encapsulation and we put them in here begrudgingly</rant>
	 * @return {@link #closed}
	 */
	boolean isForPersistentMapping_Closed() { // CHECKSTYLE IGNORE THIS LINE
		return closed;
	}
	
	/**
	 * Only for use by persistence mapping frameworks
	 * <rant>These methods break encapsulation and we put them in here begrudgingly</rant>
	 * @return {@link #lower}
	 */
	boolean isForPersistentMapping_Lower() { // CHECKSTYLE IGNORE THIS LINE
		return lower;
	}
	
	/**
	 * この限界が下側限界であるかどうかを検証する。
	 * 
	 * @return 下側限界値の場合は{@code true}、そうでない場合は{@code false}
	 */
	boolean isLower() {
		return lower;
	}
	
	/**
	 * この限界が開いているかどうかを検証する。
	 * 
	 * @return 開いている場合は{@code true}、そうでない場合は{@code false}
	 */
	boolean isOpen() {
		return closed == false;
	}
	
	/**
	 * この限界が上側限界であるかどうかを検証する。
	 * 
	 * @return 上限値の場合は{@code true}、そうでない場合は{@code false}
	 */
	boolean isUpper() {
		return lower == false;
	}
	
	/**
	 * Only for use by persistence mapping frameworks
	 * <rant>These methods break encapsulation and we put them in here begrudgingly</rant>
	 * @param closed {@link #closed}
	 */
	void setForPersistentMapping_Closed(boolean closed) { // CHECKSTYLE IGNORE THIS LINE
		this.closed = closed;
	}
	
	/**
	 * Only for use by persistence mapping frameworks
	 * <rant>These methods break encapsulation and we put them in here begrudgingly</rant>
	 * @param lower {@link #lower}
	 */
	void setForPersistentMapping_Lower(boolean lower) { // CHECKSTYLE IGNORE THIS LINE
		this.lower = lower;
	}
	
	/**
	 * Only for use by persistence mapping frameworks
	 * <rant>These methods break encapsulation and we put them in here begrudgingly</rant>
	 * @param value {@link #value}
	 */
	void setForPersistentMapping_Value(T value) { // CHECKSTYLE IGNORE THIS LINE
		this.value = value;
	}
}
