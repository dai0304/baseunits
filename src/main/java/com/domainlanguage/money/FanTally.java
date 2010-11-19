package com.domainlanguage.money;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.Validate;

public class FanTally<T> {
	
	List<MoneyFan<T>> fans;
	

	/**
	 * インスタンスを生成する。
	 * 
	 * @param fans
	 * @throws IllegalArgumentException 引数またはその要素に{@code null}を与えた場合
	 */
	public FanTally(List<MoneyFan<T>> fans) {
		Validate.noNullElements(fans);
		this.fans = fans;
	}
	
	public FanTally(MoneyFan<T>... fans) {
		this(Arrays.asList(fans));
	}
	
	public MoneyFan<T> net() {
		MoneyFan<T> sum = fans.get(0);
		for (int i = 1; i < fans.size(); i++) {
			MoneyFan<T> fan = fans.get(i);
			sum = sum.plus(fan);
		}
		return sum;
	}
	
	public Money total() {
		return net().total();
	}
}
