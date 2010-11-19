package com.domainlanguage.money;

import java.util.Arrays;
import java.util.Collection;

import org.apache.commons.lang.Validate;

public class FanTally<T> {
	
	Collection<MoneyFan<T>> fans;
	

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
	
	public MoneyFan<T> net() {
		MoneyFan<T> sum = new MoneyFan<T>();
		for (MoneyFan<T> fan : fans) {
			sum = sum.plus(fan);
		}
		return sum;
	}
	
	public Money total() {
		return net().total();
	}
}
