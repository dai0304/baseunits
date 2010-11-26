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
import java.util.Currency;
import java.util.Iterator;

import org.apache.commons.lang.Validate;

/**
 * 同じ通貨単位の金額の集合をあらわすクラス。
 */
public class Tally implements Iterable<Money> {
	
	Collection<Money> monies;
	

	/**
	 * インスタンスを生成する。
	 * 
	 * @param monies 金額の集合
	 * @throws IllegalArgumentException 引数またはその要素に{@code null}を与えた場合
	 * @throws IllegalArgumentException 引数の要素数が{@code 0}の場合
	 * @throws IllegalArgumentException 引数の要素に通貨単位が異なる金額を含む場合
	 */
	public Tally(Collection<Money> monies) {
		Validate.noNullElements(monies);
		Validate.isTrue(monies.size() > 0);
		this.monies = monies;
		Currency currency = currency();
		for (Money money : monies) {
			if (money.breachEncapsulationOfCurrency().equals(currency) == false) {
				throw new IllegalArgumentException();
			}
		}
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param moneies 金額
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @throws IllegalArgumentException 引数の要素に通貨単位が異なる金額を含む場合
	 */
	public Tally(Money... moneies) {
		this(Arrays.asList(moneies));
	}
	
	/**
	 * 通貨単位を返す。
	 * 
	 * @return 通貨単位
	 */
	public Currency currency() {
		return monies.iterator().next().breachEncapsulationOfCurrency();
	}
	
	@Override
	public Iterator<Money> iterator() {
		return monies.iterator();
	}
	
	/**
	 * 合計金額を返す。
	 * 
	 * @return 合計
	 */
	public Money net() {
		Money sum = Money.zero(currency());
		for (Money money : monies) {
			sum = sum.plus(money);
		}
		return sum;
	}
	
}
