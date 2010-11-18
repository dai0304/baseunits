package com.domainlanguage.money;

import java.util.Arrays;
import java.util.Currency;
import java.util.List;

public class Tally {
	
	List<Money> monies;
	

	public Tally(List<Money> monies) {
		this.monies = monies;
	}
	
	public Tally(Money... moneies) {
		this(Arrays.asList(moneies));
	}
	
	public Currency currency() {
		return monies.get(0).breachEncapsulationOfCurrency();
	}
	
	public Money net() {
		Money sum = Money.zero(currency());
		for (Money money : monies) {
			sum = sum.plus(money);
		}
		return sum;
	}
	
}
