/**
 * Copyright (c) 2004 Domain Language, Inc. (http://domainlanguage.com) This
 * free software is distributed under the "MIT" licence. See file licence.txt.
 * For more information, see http://timeandmoney.sourceforge.net.
 */

package com.domainlanguage.intervals;

/**
 * 区間に対して値をマッピングするクラス。
 * 
 * 
 * 
 * @param <K> キーとなる区間の型
 * @param <V> 値の型
 * @author daisuke
 */
public interface IntervalMap<K extends Comparable<K>, V> {
	
	/**
	 * 指定した区間と共通部分を持つ区間に対するマッピングがマップに含まれている場合に {@code true} を返す。
	 * 
	 * @param interval 区間
	 * @return 指定した区間と共通部分を持つ区間に対するマッピングがマップに含まれている場合は{@code true}、そうでない場合は{@code false}
	 */
	boolean containsIntersectingKey(Interval<K> interval);
	
	/**
	 * 指定されたキーを含む区間に対するマッピングがマップに含まれている場合に {@code true} を返す。
	 * 
	 * @param key キー
	 * @return 指定されたキーを含む区間に対するマッピングがマップに含まれている場合は{@code true}、そうでない場合は{@code false}
	 */
	boolean containsKey(K key);
	
	/**
	 * マップが指定されたキーをマップする値を返す。
	 * 
	 * <p>マップがこのキーのマッピングを保持していない場合は {@code null} を返す。
	 * 戻り値の {@code null} は、マップがキーのマッピングを保持していないことを示すとは限らない。
	 * つまり、マップが明示的にキーを {@code null}l にマップすることもある。
	 * {@link #containsKey(Comparable)} オペレーションを使うと、こうした 2 つの場合を見分けることができる。</p> 
	 * 
	 * @param key キーとなる値
	 * @return 値
	 */
	V get(K key);
	
	/**
	 * 指定された値と指定された区間の各要素をこのマップに関連付ける。
	 * 
	 * <p>マップにすでに区間で示された要素に対するマッピングがある場合、古い値は指定された値に置き換えられる。</p>
	 * 
	 * <p>例えば、[3, 5] に X をマッピング済みの時、[4, 6] に対して Y をマッピングした場合、
	 * [3, 4) には X 、[4, 6] には Y がマッピングされた状態となる。</p>
	 * 
	 * @param keyInterval キーとなる区間
	 * @param value 値
	 * @throws IllegalArgumentException 引数keyIntervalに{@code null}を与えた場合
	 */
	void put(Interval<K> keyInterval, V value);
	
	/**
	 * このオブジェクトのマッピングのうち、 指定した区間の各要素に対するマッピングを削除する。
	 * 
	 * @param keyInterval 削除する区間
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 */
	void remove(Interval<K> keyInterval);
}
