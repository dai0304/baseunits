/*
 * Copyright 2011-2013 Daisuke Miyamoto. (http://d.hatena.ne.jp/daisuke-m)
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
 * Copyright (c) 200X Domain Language, Inc. (http://domainlanguage.com) This
 * free software is distributed under the "MIT" licence.
 * For more information, see http://timeandmoney.sourceforge.net.
 */
package jp.xet.baseunits.money;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

import com.google.common.base.Preconditions;

/**
 * {@link MoneyFan}の集合。
 * 
 * @param <T> 割り当ての対象
 * @author daisuke
 * @since 1.0
 */
public class FanTally<T> implements Iterable<MoneyFan<T>> {
	
	final Collection<MoneyFan<T>> fans;
	
	
	/**
	* インスタンスを生成する。
	* 
	* @param fans {@link MoneyFan}の集合
	* @throws NullPointerException 引数またはその要素に{@code null}を与えた場合
	 * @since 1.0
	*/
	public FanTally(Collection<MoneyFan<T>> fans) {
		Preconditions.checkNotNull(fans);
		for (MoneyFan<T> fan : fans) {
			Preconditions.checkNotNull(fan);
		}
		this.fans = fans;
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param fan {@link MoneyFan}の要素（単一）
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	@SuppressWarnings("unchecked")
	public FanTally(MoneyFan<T> fan) {
		this(Arrays.asList(fan));
	}
	
	@Override
	public Iterator<MoneyFan<T>> iterator() {
		return fans.iterator();
	}
	
	/**
	 * 要素の {@link MoneyFan}を全てマージしたものを返す。
	 * 
	 * @return {@link MoneyFan}
	 * @since 1.0
	 */
	public MoneyFan<T> net() {
		MoneyFan<T> sum = new MoneyFan<T>();
		for (MoneyFan<T> fan : fans) {
			sum = sum.plus(fan);
		}
		return sum;
	}
	
	@Override
	public String toString() {
		return fans.toString();
	}
	
	/**
	 * 要素の {@link MoneyFan}が含む {@link Allotment}の合計額を返す。
	 * 
	 * @return 合計額
	 * @since 1.0
	 */
	public Money total() {
		return net().total();
	}
}
