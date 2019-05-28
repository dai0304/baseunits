/*
 * Copyright 2010-2019 Miyamoto Daisuke.
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

/**
 * 区間における「限界」を表すクラス。
 * 
 * <p>このクラスを理解するにあたっては、「限界」と「限界値」の区別を正しく認識することが重要となる。
 * 限界とはこのクラス {@code this} で表される値であり、限界値とは、{@link #value}で表される値である。</p>
 * 
 * <p>限界が「閉じている」とは、限界値そのものを超過とみなさないことを表し、
 * 「開いている」とは、これを超過とみなすことを表す。</p>
 * 
 * <p>無限限界とは、限界を制限しないことであり、{@link #value}が{@code null}であることによってこの状態を表現する。
 * 無限限界は常に開いていると考える。
 * 逆に、無限限界ではない限界（{@link #value}が{@code null} ではないもの）を有限限界と呼ぶ。</p>
 * 
 * <p>下側限界とは、限界値以下（または未満）の値を超過とみなす限界を表し、
 * 上側限界とは、限界値以上（または超える）の値を超過とみなす限界を表す。</p>
 * 
 * @param <T> 限界の型
 * @author daisuke
 */
@SuppressWarnings("serial")
class IntervalLimit<T extends Comparable<T> & Serializable> implements Comparable<IntervalLimit<T>>, Serializable {
	
	/**
	 * 下側限界インスタンスを生成する。
	 * 
	 * @param <T> 限界値の型
	 * @param closed 閉じた限界を生成する場合は {@code true}を指定する
	 * @param value 限界値. {@code null}の場合は、限界がないことを表す
	 * @return 下側限界インスタンス
	 */
	static <T extends Comparable<T> & Serializable>IntervalLimit<T> lower(boolean closed, T value) {
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
	static <T extends Comparable<T> & Serializable>IntervalLimit<T> upper(boolean closed, T value) {
		return new IntervalLimit<T>(closed, false, value);
	}
	
	
	/** 限界が閉じている場合 {@code true} */
	private final boolean closed;
	
	/**
	 * 限界値
	 * 
	 * {@code null}の場合は、限界がないことを表す。
	 */
	private final T value;
	
	/** 下側限界を表す場合は {@code true}、上側限界を表す場合は {@code false} */
	private final boolean lower;
	
	
	/**
	 * インスタンスを生成する。
	 * 
	 * <p>無限限界（{@code value}ば{@code null}だった場合は、{@code closed}の指定にかかわらず
	 * 常に閉じた限界のインスタンスを生成する。</p>
	 * 
	 * @param closed 閉じた限界を生成する場合は {@code true}を指定する
	 * @param lower 下側限界を生成する場合は {@code true}、上側限界を生成する場合は {@code false}を指定する
	 * @param value 限界値. {@code null}の場合は、限界がないことを表す
	 */
	IntervalLimit(boolean closed, boolean lower, T value) {
		if (value == null) {
			closed = false;
		}
		this.closed = closed;
		this.lower = lower;
		this.value = value;
	}
	
	/**
	 * 限界同士の比較を行う。
	 * 
	 * <p>無限限界に関して。
	 * 下側の無限限界は他のいかなる限界よりも「小さい」と判断し、
	 * 上側の無限限界は他のいかなる限界よりも「大きい」と判断する。
	 * 同じ側の限界同士の比較では「同一」と判断する。</p>
	 * 
	 * <p>有限限界同士の比較に関して。
	 * この場合は、それぞれの限界の開閉や上下にかかわらず、限界値が小さい方を「小さい」と判断する。
	 * 限界値が同一である場合、下側限界同士の比較である場合は閉じている方を「小さい」と判断し、
	 * 上側限界同士の比較である場合は閉じている方を「大きい」と判断する。
	 * 限界値が同一で、上側限界と下側限界の比較の場合は、開閉にかかわらず下側を「小さい」と判断する。</p>
	 * 
	 * @param other 比較対象
	 * @return 同値であった場合は {@code 0}、このオブジェクトが比較対象よりも小さい場合は負数、大きい場合は正数
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	// CHECKSTYLE:OFF
	@Override
	// CHECKSTYLE:ON
	public int compareTo(IntervalLimit<T> other) {
		if (other == null) {
			throw new NullPointerException();
		}
		
		// 無限同士の比較
		if (value == null && other.value == null) {
			if (lower == other.lower) {
				return 0;
			}
			return lower ? -1 : 1;
		}
		
		// 有限と無限の比較（自分が無限の場合）
		if (value == null) {
			return lower ? -1 : 1;
		}
		// 有限と無限の比較（otherが無限の場合）
		if (other.value == null) {
			return other.lower ? 1 : -1;
		}
		
		// 同値の有限同士の比較
		if (other.value == value) {
			// 下側限界同士の比較
			if (lower && other.lower) {
				if (closed ^ other.closed) {
					return closed ? -1 : 1;
				}
				return 0;
			}
			// 上側限界同士の比較
			if (lower == false && other.lower == false) {
				if (closed ^ other.closed) {
					return closed ? 1 : -1;
				}
				return 0;
			}
			return lower ? -1 : 1;
		}
		
		// 異なる値の有限同士の比較
		return value.compareTo(other.value);
	}
	
	@Override
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
		try {
			@SuppressWarnings("unchecked")
			IntervalLimit<T> other = (IntervalLimit<T>) obj;
			return compareTo(other) == 0;
		} catch (ClassCastException e) {
			return false;
		}
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (closed ? 1231 : 1237); // CHECKSTYLE IGNORE THIS LINE
		result = prime * result + (lower ? 1231 : 1237); // CHECKSTYLE IGNORE THIS LINE
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}
	
	@Override
	public String toString() {
		String l = lower ? "lower " : "upper ";
		String c = value == null ? "" : (closed ? "closed " : "open ");
		String v = value == null ? "Infinity" : value.toString();
		return String.format("<%s%s%s>", l, c, v);
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
	 * この限界が無限限界であるかどうかを検証する。
	 * 
	 * @return 無限限界である場合は{@code true}、そうでない場合は{@code false}
	 */
	boolean isInfinity() {
		return value == null;
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
}
