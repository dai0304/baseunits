/**
 * Copyright (c) 2004 Domain Language, Inc. (http://domainlanguage.com) This
 * free software is distributed under the "MIT" licence. See file licence.txt.
 * For more information, see http://timeandmoney.sourceforge.net.
 */

package com.domainlanguage.money;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Currency;
import java.util.Iterator;
import java.util.Locale;

import com.domainlanguage.base.Ratio;
import com.domainlanguage.base.Rounding;
import com.domainlanguage.time.Duration;

import org.apache.commons.lang.Validate;

/**
 * 金額を表すクラス。
 * 
 * <p>ある一定の「量」と「通貨単位」から成るクラスである。</p>
 * 
 * @author daisuke
 */
@SuppressWarnings("serial")
public class Money implements Comparable<Money>, Serializable {
	
	private static final Currency USD = Currency.getInstance("USD");
	
	private static final Currency EUR = Currency.getInstance("EUR");
	
	private static final Currency JPY = Currency.getInstance("JPY");
	
	private static final Rounding DEFAULT_ROUNDING_MODE = Rounding.HALF_EVEN;
	

	/**
	 * {@code amount}で表す量のドルを表すインスタンスを返す。
	 * 
	 * <p>This creation method is safe to use. It will adjust scale, but will not
	 * round off the amount.</p>
	 * 
	 * @param amount 量
	 * @return {@code amount}で表す量のドルを表すインスタンス
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 */
	public static Money dollars(BigDecimal amount) {
		Validate.notNull(amount);
		return Money.valueOf(amount, USD);
	}
	
	/**
	 * {@code amount}で表す量のドルを表すインスタンスを返す。
	 * 
	 * <p>WARNING: Because of the indefinite precision of double, this method must
	 * round off the value.</p>
	 * 
	 * @param amount 量
	 * @return {@code amount}で表す量のドルを表すインスタンス
	 */
	public static Money dollars(double amount) {
		return Money.valueOf(amount, USD);
	}
	
	/**
	 * This creation method is safe to use. It will adjust scale, but will not
	 * round off the amount.
	 * @param amount 量
	 * @return {@code amount}で表す量のユーロを表すインスタンス
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 */
	public static Money euros(BigDecimal amount) {
		Validate.notNull(amount);
		return Money.valueOf(amount, EUR);
	}
	
	/**
	 * WARNING: Because of the indefinite precision of double, this method must
	 * round off the value.
	 * @param amount 量
	 * @return {@code amount}で表す量のユーロを表すインスタンス
	 */
	public static Money euros(double amount) {
		return Money.valueOf(amount, EUR);
	}
	
	/**
	 * TODO for daisuke
	 * 
	 * @param monies
	 * @return
	 * @throws ClassCastException 引数の通貨単位の中に通貨単位が異なるものを含む場合
	 */
	public static Money sum(Collection<Money> monies) {
		//TODO Return Default Currency
		if (monies.isEmpty()) {
			return Money.dollars(0.00);
		}
		Iterator<Money> iterator = monies.iterator();
		Money sum = iterator.next();
		while (iterator.hasNext()) {
			Money each = iterator.next();
			sum = sum.plus(each);
		}
		return sum;
	}
	
	/**
	 * This creation method is safe to use. It will adjust scale, but will not
	 * round off the amount.
	 * 
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 */
	public static Money valueOf(BigDecimal amount, Currency currency) {
		Validate.notNull(amount);
		Validate.notNull(currency);
		return Money.valueOf(amount, currency, Rounding.UNNECESSARY);
	}
	
	/**
	 * For convenience, an amount can be rounded to create a Money.
	 * 
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 */
	public static Money valueOf(BigDecimal rawAmount, Currency currency, Rounding roundingMode) {
		Validate.notNull(rawAmount);
		Validate.notNull(currency);
		Validate.notNull(rawAmount);
		BigDecimal amount = rawAmount.setScale(currency.getDefaultFractionDigits(), roundingMode.value);
		return new Money(amount, currency);
	}
	
	/**
	 * WARNING: Because of the indefinite precision of double, this method must
	 * round off the value.
	 * 
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 */
	public static Money valueOf(double dblAmount, Currency currency) {
		Validate.notNull(currency);
		return Money.valueOf(dblAmount, currency, DEFAULT_ROUNDING_MODE);
	}
	
	/**
	 * Because of the indefinite precision of double, this method must round off
	 * the value. This method gives the client control of the rounding mode.
	 * 
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 */
	public static Money valueOf(double dblAmount, Currency currency, Rounding roundingMode) {
		Validate.notNull(currency);
		Validate.notNull(roundingMode);
		BigDecimal rawAmount = new BigDecimal(dblAmount);
		return Money.valueOf(rawAmount, currency, roundingMode);
	}
	
	/**
	 * This creation method is safe to use. It will adjust scale, but will not
	 * round off the amount.
	 * 
	 * @param amount 量
	 * @return {@code amount}で表す量の円を表すインスタンス
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 */
	public static Money yens(BigDecimal amount) {
		Validate.notNull(amount);
		return Money.valueOf(amount, JPY);
	}
	
	/**
	 * WARNING: Because of the indefinite precision of double, this method must
	 * round off the value.
	 * 
	 * @param amount 量
	 * @return {@code amount}で表す量の円を表すインスタンス
	 */
	public static Money yens(double amount) {
		return Money.valueOf(amount, JPY);
	}
	
	/**
	 * TODO for daisuke
	 * 
	 * @param currency
	 * @return
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 */
	public static Money zero(Currency currency) {
		Validate.notNull(currency);
		return Money.valueOf(0.0, currency);
	}
	

	private BigDecimal amount;
	
	private Currency currency;
	

	/**
	 * The constructor does not complex computations and requires simple, inputs
	 * consistent with the class invariant. Other creation methods are available
	 * for convenience.
	 * 
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 */
	public Money(BigDecimal amount, Currency currency) {
		Validate.notNull(amount);
		Validate.notNull(currency);
		if (amount.scale() != currency.getDefaultFractionDigits()) {
			throw new IllegalArgumentException("Scale of amount does not match currency"); // TODO @throws
		}
		this.currency = currency;
		this.amount = amount;
	}
	
	/**
	 * Only for use by persistence mapping frameworks
	 * <rant>These methods break encapsulation and we put them in here begrudgingly</rant>
	 */
	Money() {
	}
	
	/**
	 * Returns a {@link Money} whose amount is the absolute amount of this {@link Money}, and whose scale is this.scale().
	 * 
	 * @return
	 */
	public Money abs() {
		return Money.valueOf(amount.abs(), currency);
	}
	
	/**
	 * TODO for daisuke
	 * 
	 * @param ratio
	 * @param scale
	 * @param roundingRule
	 * @return
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 */
	public Money applying(Ratio ratio, int scale, Rounding roundingRule) {
		Validate.notNull(ratio);
		Validate.notNull(roundingRule);
		BigDecimal newAmount = ratio.times(amount).decimalValue(scale, roundingRule);
		return Money.valueOf(newAmount, currency);
	}
	
	/**
	 * TODO for daisuke
	 * 
	 * @param ratio
	 * @param roundingRule
	 * @return
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 */
	public Money applying(Ratio ratio, Rounding roundingRule) {
		return applying(ratio, currency.getDefaultFractionDigits(), roundingRule);
	}
	
	@Override
	public int compareTo(Money other) {
		if (other == null) {
			return -1;
		}
		if (hasSameCurrencyAs(other) == false) {
			throw new ClassCastException("Compare is not defined between different currencies");
		}
		return amount.compareTo(other.amount);
	}
	
	/**
	 * TODO for daisuke
	 * 
	 * @param divisor
	 * @param roundingMode
	 * @return
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 */
	public Money dividedBy(BigDecimal divisor, Rounding roundingMode) {
		Validate.notNull(divisor);
		Validate.notNull(roundingMode);
		BigDecimal newAmount = amount.divide(divisor, roundingMode.value);
		return Money.valueOf(newAmount, currency);
	}
	
	public Money dividedBy(double divisor) {
		return dividedBy(divisor, DEFAULT_ROUNDING_MODE);
	}
	
	/**
	 * TODO for daisuke
	 * 
	 * @param divisor
	 * @param roundingMode
	 * @return
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 */
	public Money dividedBy(double divisor, Rounding roundingMode) {
		return dividedBy(new BigDecimal(divisor), roundingMode);
	}
	
	/**
	 * TODO for daisuke
	 * 
	 * @param divisor
	 * @return
	 * @throws ClassCastException 引数の通貨単位がこのインスタンスの通貨単位と異なる場合
	 */
	public Ratio dividedBy(Money divisor) {
		assertHasSameCurrencyAs(divisor);
		return Ratio.of(amount, divisor.amount);
	}
	
	public boolean equals(Money other) {
		return other != null && hasSameCurrencyAs(other) && amount.equals(other.amount);
	}
	
	@Override
	public boolean equals(Object other) {
		try {
			return equals((Money) other);
		} catch (ClassCastException ex) {
			return false;
		}
	}
	
	@Override
	public int hashCode() {
		return amount.hashCode();
	}
	
	/**
	 * TODO for daisuke
	 * 
	 * @param other
	 * @return
	 * @throws ClassCastException 引数の通貨単位がこのインスタンスの通貨単位と異なる場合
	 */
	public boolean isGreaterThan(Money other) {
		return compareTo(other) > 0;
	}
	
	/**
	 * TODO for daisuke
	 * 
	 * @param other
	 * @return
	 * @throws ClassCastException 引数の通貨単位がこのインスタンスの通貨単位と異なる場合
	 */
	public boolean isLessThan(Money other) {
		return compareTo(other) < 0;
	}
	
	public boolean isNegative() {
		return amount.compareTo(BigDecimal.ZERO) < 0;
	}
	
	public boolean isPositive() {
		return amount.compareTo(BigDecimal.ZERO) > 0;
	}
	
	public boolean isZero() {
		return this.equals(Money.valueOf(0.0, currency));
	}
	
	/**
	 * TODO for daisuke
	 * 
	 * @param other
	 * @return
	 * @throws ClassCastException 引数の通貨単位がこのインスタンスの通貨単位と異なる場合
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 */
	public Money minus(Money other) {
		Validate.notNull(other);
		return plus(other.negated());
	}
	
	/**
	 * Returns a {@link Money} whose amount is (-amount), and whose scale is this.scale().
	 * 
	 * @return
	 */
	public Money negated() {
		return Money.valueOf(amount.negate(), currency);
	}
	
	/**
	 * TODO for daisuke
	 * 
	 * @param duration
	 * @return
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 */
	public MoneyTimeRate per(Duration duration) {
		Validate.notNull(duration);
		return new MoneyTimeRate(this, duration);
	}
	
	/**
	 * TODO
	 * 
	 * @param other
	 * @return
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @throws ClassCastException 引数の通貨単位がこのインスタンスの通貨単位と異なる場合
	 */
	public Money plus(Money other) {
		Validate.notNull(other);
		assertHasSameCurrencyAs(other);
		return Money.valueOf(amount.add(other.amount), currency);
	}
	
	/**
	 * TODO: Many apps require carrying extra precision in intermediate
	 * calculations. The use of Ratio is a beginning, but need a comprehensive
	 * solution. Currently, an invariant of Money is that the scale is the
	 * currencies standard scale, but this will probably have to be suspended or
	 * elaborated in intermediate calcs, or handled with defered calculations
	 * like Ratio.
	 */
	public Money times(BigDecimal factor) {
		return times(factor, DEFAULT_ROUNDING_MODE);
	}
	
	/**
	 * TODO: BigDecimal.multiply() scale is sum of scales of two multiplied
	 * numbers. So what is scale of times?
	 */
	public Money times(BigDecimal factor, Rounding roundingMode) {
		return Money.valueOf(amount.multiply(factor), currency, roundingMode);
	}
	
	public Money times(double amount) {
		return times(new BigDecimal(amount));
	}
	
	public Money times(double amount, Rounding roundingMode) {
		return times(new BigDecimal(amount), roundingMode);
	}
	
	public Money times(int i) {
		return times(new BigDecimal(i));
	}
	
	@Override
	public String toString() {
		return currency.getSymbol() + " " + amount;
	}
	
	public String toString(Locale locale) {
		return currency.getSymbol(locale) + " " + amount;
	}
	
	/**
	 * How best to handle access to the internals? It is needed for
	 * database mapping, UI presentation, and perhaps a few other
	 * uses. Yet giving public access invites people to do the
	 * real work of the Money object elsewhere.
	 * Here is an experimental approach, giving access with a 
	 * warning label of sorts. Let us know how you like it.
	 */
	BigDecimal breachEncapsulationOfAmount() {
		return amount;
	}
	
	Currency breachEncapsulationOfCurrency() {
		return currency;
	}
	
	BigDecimal getAmount() {
		return amount;
	}
	
//  TODO: Provide some currency-dependent formatting. Java 1.4 Currency doesn't
//  do it.
//  public String formatString() {
//      return currency.formatString(amount());
//  }
//  public String localString() {
//      return currency.getFormat().format(amount());
//  }
	
	Currency getCurrency() {
		return currency;
	}
	
	boolean hasSameCurrencyAs(Money arg) {
		return currency.equals(arg.currency);
	}
	
	Money incremented() {
		return plus(minimumIncrement());
	}
	
	/**
	 * This probably should be Currency responsibility. Even then, it may need
	 * to be customized for specialty apps because there are other cases, where
	 * the smallest increment is not the smallest unit.
	 */
	Money minimumIncrement() {
		BigDecimal increment = BigDecimal.ONE.movePointLeft(currency.getDefaultFractionDigits());
		return Money.valueOf(increment, currency);
	}
	
	private void assertHasSameCurrencyAs(Money aMoney) {
		if (hasSameCurrencyAs(aMoney) == false) {
			throw new ClassCastException(aMoney.toString() + " is not same currency as " + this.toString());
		}
	}
	
	/**
	 * Only for use by persistence mapping frameworks
	 * <rant>These methods break encapsulation and we put them in here begrudgingly</rant>
	 * @return {@link #amount}
	 */
	@SuppressWarnings("unused")
	private BigDecimal getForPersistentMapping_Amount() { // CHECKSTYLE IGNORE THIS LINE
		return amount;
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
	 * @param amount {@link #amount}
	 */
	@SuppressWarnings("unused")
	private void setForPersistentMapping_Amount(BigDecimal amount) { // CHECKSTYLE IGNORE THIS LINE
		this.amount = amount;
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
	
}
