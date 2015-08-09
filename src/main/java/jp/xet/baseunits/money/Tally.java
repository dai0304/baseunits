/*
 * Copyright 2010-2015 Miyamoto Daisuke.
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
package jp.xet.baseunits.money;

import java.util.Arrays;
import java.util.Collection;
import java.util.Currency;
import java.util.Iterator;

import com.google.common.base.Preconditions;

/**
 * 同じ通貨単位の金額の集合をあらわすクラス。
 * 
 * @author daisuke
 * @since 1.0
 */
public class Tally implements Iterable<Money> {
	
	Collection<Money> monies;
	
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param monies 金額の集合
	 * @throws IllegalArgumentException 引数の要素数が{@code 0}の場合
	 * @throws IllegalArgumentException 引数の要素に通貨単位が異なる金額を含む場合
	 * @throws NullPointerException 引数またはその要素に{@code null}を与えた場合
	 * @since 1.0
	 */
	public Tally(Collection<Money> monies) {
		Preconditions.checkNotNull(monies);
		Preconditions.checkArgument(monies.size() > 0);
		for (Money money : monies) {
			Preconditions.checkNotNull(money);
		}
		
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
	 * @throws IllegalArgumentException 引数の要素に通貨単位が異なる金額を含む場合
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	public Tally(Money... moneies) {
		this(Arrays.asList(moneies));
	}
	
	/**
	 * 通貨単位を返す。
	 * 
	 * @return 通貨単位
	 * @since 1.0
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
	 * @since 1.0
	 */
	public Money net() {
		return Money.sum(monies);
	}
	
	@Override
	public String toString() {
		return monies.toString();
	}
}
