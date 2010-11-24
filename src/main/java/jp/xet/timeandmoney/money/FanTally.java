/*
 * Copyright 2010 Daisuke Miyamoto.
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
 * free software is distributed under the "MIT" licence. See file licence.txt.
 * For more information, see http://timeandmoney.sourceforge.net.
 */
package jp.xet.timeandmoney.money;

import java.util.Arrays;
import java.util.Collection;

import org.apache.commons.lang.Validate;

/**
 * {@link MoneyFan}の集合。
 * 
 * @param <T> 割り当ての対象
 * @version $Id$
 * @author daisuke
 */
public class FanTally<T> {
	
	final Collection<MoneyFan<T>> fans;
	

	/**
	 * インスタンスを生成する。
	 * 
	 * @param fans {@link MoneyFan}の集合
	 * @throws IllegalArgumentException 引数またはその要素に{@code null}を与えた場合
	 */
	public FanTally(Collection<MoneyFan<T>> fans) {
		Validate.noNullElements(fans);
		this.fans = fans;
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param fan {@link MoneyFan}の要素（単一）
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 */
	@SuppressWarnings("unchecked")
	public FanTally(MoneyFan<T> fan) {
		this(Arrays.asList(fan));
	}
	
	/**
	 * 要素の {@link MoneyFan}を全てマージしたものを返す。
	 * 
	 * @return {@link MoneyFan}
	 */
	public MoneyFan<T> net() {
		MoneyFan<T> sum = new MoneyFan<T>();
		for (MoneyFan<T> fan : fans) {
			sum = sum.plus(fan);
		}
		return sum;
	}
	
	/**
	 * 要素の {@link MoneyFan}が含む {@link Allotment}の合計額を返す。
	 * 
	 * @return 合計額
	 */
	public Money total() {
		return net().total();
	}
}
