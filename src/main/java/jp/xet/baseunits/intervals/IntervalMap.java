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
 * Copyright (c) 2004 Domain Language, Inc. (http://domainlanguage.com) This
 * free software is distributed under the "MIT" licence.
 * For more information, see http://timeandmoney.sourceforge.net.
 */
package jp.xet.baseunits.intervals;

import java.io.Serializable;

/**
 * 区間に対して値をマッピングするクラス。
 * 
 * @param <K> キーとなる区間の型
 * @param <V> 値の型
 * @author daisuke
 * @since 1.0
 */
public interface IntervalMap<K extends Comparable<K> & Serializable, V> {
	
	/**
	 * 指定した区間と共通部分を持つ区間に対するマッピングがマップに含まれている場合に {@code true} を返す。
	 * 
	 * @param interval 区間
	 * @return 指定した区間と共通部分を持つ区間に対するマッピングがマップに含まれている場合は{@code true}、そうでない場合は{@code false}
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	boolean containsIntersectingKey(Interval<K> interval);
	
	/**
	 * 指定されたキーを含む区間に対するマッピングがマップに含まれている場合に {@code true} を返す。
	 * 
	 * @param key キー
	 * @return 指定されたキーを含む区間に対するマッピングがマップに含まれている場合は{@code true}、そうでない場合は{@code false}
	 * @since 1.0
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
	 * @since 1.0
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
	 * @since 1.0
	 */
	void put(Interval<K> keyInterval, V value);
	
	/**
	 * このオブジェクトのマッピングのうち、 指定した区間の各要素に対するマッピングを削除する。
	 * 
	 * @param keyInterval 削除する区間
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	void remove(Interval<K> keyInterval);
}
