/**
 * Copyright (c) 2005 Domain Language, Inc. (http://domainlanguage.com) This
 * free software is distributed under the "MIT" licence. See file licence.txt.
 * For more information, see http://timeandmoney.sourceforge.net.
 */
package com.domainlanguage.money;

import java.util.Currency;

import com.domainlanguage.base.Rounding;
import com.domainlanguage.time.Duration;
import com.domainlanguage.time.TimeRate;

import org.apache.commons.lang.Validate;

public class MoneyTimeRate {
	
	private TimeRate rate;
	
	private Currency currency;
	

	/**
	 * インスタンスを生成する。
	 * 
	 * @param money
	 * @param duration
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 */
	public MoneyTimeRate(Money money, Duration duration) {
		Validate.notNull(money);
		Validate.notNull(duration);
		rate = new TimeRate(money.getAmount(), duration);
		currency = money.getCurrency();
	}
	
	/**
	 * Only for use by persistence mapping frameworks
	 * <rant>These methods break encapsulation and we put them in here begrudgingly</rant>
	 */
	MoneyTimeRate() {
	}
	
	public boolean equals(MoneyTimeRate another) {
		return another != null && rate.equals(another.rate) && currency.equals(another.currency);
	}
	
	@Override
	public boolean equals(Object other) {
		try {
			return equals((MoneyTimeRate) other);
		} catch (ClassCastException ex) {
			return false;
		}
	}
	
	public Money over(Duration duration) {
		return over(duration, Rounding.UNNECESSARY);
	}
	
	public Money over(Duration duration, int scale, Rounding roundRule) {
		return Money.valueOf(rate.over(duration, scale, roundRule), currency);
	}
	
	public Money over(Duration duration, Rounding roundRule) {
		return over(duration, rate.scale(), roundRule);
	}
	
	@Override
	public String toString() {
		return rate.toString();
	}
	
	/**
	 * Only for use by persistence mapping frameworks
	 * <rant>These methods break encapsulation and we put them in here begrudgingly</rant>
	 * @return {@link #currency}
	 */
	@SuppressWarnings("unused")
	private Currency getForPersistentMapping_Currency() { // CHECKSTYLE IGNORE THIS LINE
		return currency;
	}
	
	/**
	 * Only for use by persistence mapping frameworks
	 * <rant>These methods break encapsulation and we put them in here begrudgingly</rant>
	 * @return {@link #rate}
	 */
	@SuppressWarnings("unused")
	private TimeRate getForPersistentMapping_Rate() { // CHECKSTYLE IGNORE THIS LINE
		return rate;
	}
	
	/**
	 * Only for use by persistence mapping frameworks
	 * <rant>These methods break encapsulation and we put them in here begrudgingly</rant>
	 * @param currency {@link #currency}
	 */
	@SuppressWarnings("unused")
	private void setForPersistentMapping_Currency(Currency currency) { // CHECKSTYLE IGNORE THIS LINE
		this.currency = currency;
	}
	
	/**
	 * Only for use by persistence mapping frameworks
	 * <rant>These methods break encapsulation and we put them in here begrudgingly</rant>
	 * @param rate {@link #rate}
	 */
	@SuppressWarnings("unused")
	private void setForPersistentMapping_Rate(TimeRate rate) { // CHECKSTYLE IGNORE THIS LINE
		this.rate = rate;
	}
}
