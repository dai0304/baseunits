/**
 * Copyright (c) 2005 Domain Language, Inc. (http://domainlanguage.com) This
 * free software is distributed under the "MIT" licence.
 * For more information, see http://timeandmoney.sourceforge.net.
 */
package jp.xet.timeandmoney.money;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.util.Currency;

import jp.xet.timeandmoney.time.Duration;
import jp.xet.timeandmoney.time.TimeRate;
import jp.xet.timeandmoney.util.Rounding;

import org.junit.Test;

/**
 * {@link MoneyTimeRate}のテストクラス。
 */
public class MoneyTimeRateTest {
	
	/**
	 * {@link MoneyTimeRate}のインスタンス生成テスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test01_SimpleRate() throws Exception {
		MoneyTimeRate rate = new MoneyTimeRate(Money.dollars(20.00), Duration.hours(1));
		assertThat(rate.over(Duration.hours(2)), is(Money.dollars(40.00)));
		
		assertThat(rate.breachEncapsulationOfRate(), is(new TimeRate(new BigDecimal("20.00"), Duration.hours(1))));
		assertThat(rate.breachEncapsulationOfCurrency(), is(Currency.getInstance("USD")));
	}
	
	/**
	 * {@link MoneyTimeRate#over(Duration)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test02_Rounding() throws Exception {
		MoneyTimeRate rate = new MoneyTimeRate(Money.dollars(100.00), Duration.minutes(3));
		try {
			rate.over(Duration.minutes(1));
			fail("ArtithmeticException should have been thrown. This case requires rounding.");
		} catch (ArithmeticException e) {
			// success
		}
	}
	
	/**
	 * {@link MoneyTimeRate#over(Duration, Rounding)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test03_RoundingRate() throws Exception {
		MoneyTimeRate rate = new MoneyTimeRate(Money.euros(100.00), Duration.minutes(3));
		assertThat(rate.over(Duration.minutes(1), Rounding.DOWN), is(Money.euros(new BigDecimal("33.33"))));
	}
	
	/**
	 * {@link MoneyTimeRate#over(Duration, int, Rounding)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test04_RoundingScalingRate() throws Exception {
		MoneyTimeRate rate = new MoneyTimeRate(Money.euros(new BigDecimal("100.00")), Duration.minutes(3));
		assertThat(rate.over(Duration.minutes(1), 2, Rounding.DOWN), is(Money.euros(new BigDecimal("33.33"))));
	}
	
	/**
	 * {@link MoneyTimeRate#equals(Object)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test05_Equals() throws Exception {
		Money amount = Money.euros(11.00);
		MoneyTimeRate rate = amount.per(Duration.days(2));
		assertThat(rate.equals(rate), is(true));
		assertThat(rate.equals(new MoneyTimeRate(Money.euros(11.00), Duration.days(2))), is(true));
		assertThat(rate.equals(new MoneyTimeRate(Money.euros(11.01), Duration.days(2))), is(false));
		assertThat(rate.equals(new MoneyTimeRate(Money.euros(11.00), Duration.days(1))), is(false));
		assertThat(rate.equals(new MoneyTimeRate(Money.yens(11.00), Duration.days(2))), is(false));
		assertThat(rate.equals(null), is(false));
		assertThat(rate.equals(new MoneyTimeRate(Money.euros(11.00), Duration.days(2)) {
		}), is(false));
		
		assertThat(rate.hashCode(), is(new MoneyTimeRate(Money.euros(11.00), Duration.days(2)).hashCode()));
		assertThat(rate.hashCode(), is(not(new MoneyTimeRate(Money.euros(11.01), Duration.days(2)).hashCode())));
		assertThat(rate.hashCode(), is(not(new MoneyTimeRate(Money.euros(11.00), Duration.days(1)).hashCode())));
		assertThat(rate.hashCode(), is(not(new MoneyTimeRate(Money.yens(11.00), Duration.days(2)).hashCode())));
	}
	
	/**
	 * {@link MoneyTimeRate#toString()}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test06_toString() throws Exception {
		Money amount = Money.euros(11.00);
		MoneyTimeRate rate = amount.per(Duration.days(2));
		assertThat(rate.toString(), is("EUR 11.00 per 2 days"));
	}
}
