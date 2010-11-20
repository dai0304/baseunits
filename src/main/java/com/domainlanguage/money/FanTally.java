package com.domainlanguage.money;

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
