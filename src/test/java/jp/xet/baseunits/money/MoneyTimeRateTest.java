/*
 * Copyright 2010-2019 Miyamoto Daisuke.
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

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;

import jp.xet.baseunits.money.Money;
import jp.xet.baseunits.money.MoneyTimeRate;
import jp.xet.baseunits.time.Duration;
import jp.xet.baseunits.time.TimeRate;

import org.junit.Test;

/**
 * {@link MoneyTimeRate}のテストクラス。
 */
public class MoneyTimeRateTest {
	
	/**
	 * {@link MoneyTimeRate#equals(Object)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test01_Equals() throws Exception {
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
	public void test04_Rounding() throws Exception {
		MoneyTimeRate rate = new MoneyTimeRate(Money.dollars(100.00), Duration.minutes(3));
		try {
			rate.over(Duration.minutes(1));
			fail("ArtithmeticException should have been thrown. This case requires rounding.");
		} catch (ArithmeticException e) {
			// success
		}
	}
	
	/**
	 * {@link MoneyTimeRate#over(Duration, RoundingMode)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test05_RoundingRate() throws Exception {
		MoneyTimeRate rate = new MoneyTimeRate(Money.euros(100.00), Duration.minutes(3));
		assertThat(rate.over(Duration.minutes(1), RoundingMode.DOWN), is(Money.euros(new BigDecimal("33.33"))));
	}
	
	/**
	 * {@link MoneyTimeRate#over(Duration, int, RoundingMode)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test06_RoundingScalingRate() throws Exception {
		MoneyTimeRate rate = new MoneyTimeRate(Money.euros(new BigDecimal("100.00")), Duration.minutes(3));
		assertThat(rate.over(Duration.minutes(1), 2, RoundingMode.DOWN), is(Money.euros(new BigDecimal("33.33"))));
	}
	
	/**
	 * {@link MoneyTimeRate#toString()}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test07_toString() throws Exception {
		Money amount = Money.euros(11.00);
		MoneyTimeRate rate = amount.per(Duration.days(2));
		assertThat(rate.toString(), is("EUR 11.00 per 2 days"));
	}
}
