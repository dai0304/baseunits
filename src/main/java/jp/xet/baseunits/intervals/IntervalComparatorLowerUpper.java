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
import java.util.Comparator;

/**
 * 区間同士の比較を行うコンパレータ。下側限界による比較を優先し、同じであったら上側限界による比較を採用する。
 * 
 * @param <T> 区間要素の型
 * @author daisuke
 * @since 1.0
 */
public class IntervalComparatorLowerUpper<T extends Comparable<T> & Serializable> implements Comparator<Interval<T>> {
	
	private final int lowerFactor;
	
	private final int upperFactor;
	
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param inverseLower 下側限界の比較結果の符号を反転する場合は{@code true}、そうでない場合は{@code false}
	 * @param inverseUpper 上側限界の比較結果の符号を反転する場合は{@code true}、そうでない場合は{@code false}
	 * @since 1.0
	 */
	public IntervalComparatorLowerUpper(boolean inverseLower, boolean inverseUpper) {
		this.lowerFactor = inverseLower ? -1 : 1;
		this.upperFactor = inverseUpper ? -1 : 1;
	}
	
	/**
	 * 区間同士の比較を行う。
	 * 
	 * <p>下限限界優先ロジック：
	 * 下限限界値がより小さい方を「小さい」と判断する。同一の場合は、上限限界値がより<strong>大きい</strong>方を「小さい」とする。
	 * ただし、空区間は他のどんな区間よりも大きい。</p>
	 * 
	 * @param e1 比較対象1
	 * @param e2 比較対象2
	 * @return 同値であった場合は {@code 0}、この{@code e1}が比較対象よりも小さい場合は負数、大きい場合は正数
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	@Override
	public int compare(Interval<T> e1, Interval<T> e2) {
		if (e1 == null || e2 == null) {
			throw new NullPointerException();
		}
		
		// 下限限界優先ロジック
		if (e1.isEmpty() && e2.isEmpty()) {
			return 0;
		} else if (e1.isEmpty()) {
			return 1;
		} else if (e2.isEmpty()) {
			return -1;
		}
		
		int upperComparance = e1.upperLimitObject.compareTo(e2.upperLimitObject);
		int lowerComparance = e1.lowerLimitObject.compareTo(e2.lowerLimitObject);
		return lowerComparance != 0 ? (lowerComparance * lowerFactor) : (upperComparance * upperFactor);
	}
}
