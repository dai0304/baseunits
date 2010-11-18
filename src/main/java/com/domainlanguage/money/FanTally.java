package com.domainlanguage.money;

import java.util.Arrays;
import java.util.List;

public class FanTally<T> {
	
	List<MoneyFan<T>> fans;
	

	public FanTally(List<MoneyFan<T>> fans) {
		super();
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
