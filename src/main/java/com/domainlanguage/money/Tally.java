package com.domainlanguage.money;

import java.util.Arrays;
import java.util.Collection;
import java.util.Currency;

import org.apache.commons.lang.Validate;

/**
 * 同じ通貨単位の金額の集合をあらわすクラス。
 * 
 * @version $Id$
 * @author daisuke
 */
public class Tally {
	
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
