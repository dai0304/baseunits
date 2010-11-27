/*
 * Copyright 2010 TRICREO, Inc. (http://tricreo.jp/)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 * ----
 * Copyright (c) 2005 Domain Language, Inc. (http://domainlanguage.com) This
 * free software is distributed under the "MIT" licence.
 * For more information, see http://timeandmoney.sourceforge.net.
 */
package jp.tricreo.basicunits.time;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.math.BigDecimal;

import jp.tricreo.basicunits.time.Duration;
import jp.tricreo.basicunits.time.TimeRate;
import jp.tricreo.basicunits.util.Rounding;

import org.junit.Test;

/**
 * {@link TimeRate}のテストクラス。
 */
public class TimeRateTest {
	
	/**
	 * {@link TimeRate}のインスタンス生成テスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test01_SimpleRate() throws Exception {
		TimeRate rate = new TimeRate(100.00, Duration.minutes(1));
		assertThat(rate.over(Duration.hours(1)), is(new BigDecimal(6000.00)));
		
		assertThat(rate.toString(), is("100 per 1 minute"));
		assertThat(rate.breachEncapsulationOfQuantity(), is(BigDecimal.valueOf(100)));
		assertThat(rate.breachEncapsulationOfUnit(), is(Duration.minutes(1)));
	}
	
	/**
	 * {@link TimeRate#over(Duration)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test02_Rounding() throws Exception {
		TimeRate rate = new TimeRate(100.00, Duration.minutes(3));
		try {
			rate.over(Duration.minutes(1));
			fail("ArtithmeticException should have been thrown. This case requires rounding.");
		} catch (ArithmeticException expected) {
			// success
		}
	}
	
	/**
	 * {@link TimeRate#over(Duration, Rounding)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test03_RoundingRate() throws Exception {
		TimeRate rate = new TimeRate("100.00", Duration.minutes(3));
		assertThat(rate.over(Duration.minutes(1), Rounding.DOWN), is(new BigDecimal("33.33")));
	}
	
	/**
	 * {@link TimeRate#over(Duration, int, Rounding)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test04_RoundingScalingRate() throws Exception {
		TimeRate rate = new TimeRate("100.00", Duration.minutes(3));
		assertThat(rate.over(Duration.minutes(1), 3, Rounding.DOWN), is(new BigDecimal("33.333")));
	}
	
	/**
	 * {@link TimeRate#equals(Object)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test05_Equals() throws Exception {
		TimeRate rate = new TimeRate(11, Duration.days(2));
		assertThat(rate, is(new TimeRate(11.00, Duration.days(2))));
		assertThat(rate.equals(rate), is(true));
		assertThat(rate.equals(new TimeRate(11.00, Duration.days(2))), is(true));
		assertThat(rate.equals(new TimeRate(11.00, Duration.days(2)) {
		}), is(false));
		assertThat(rate.equals(null), is(false));
		assertThat(rate.equals(new TimeRate(11.00, Duration.days(1))), is(false));
		assertThat(rate.equals(new TimeRate(11.00, Duration.months(2))), is(false));
		assertThat(rate.equals(new TimeRate(11.01, Duration.days(2))), is(false));
	}
	
	/**
	 * {@link TimeRate#toString()}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test06_toString() throws Exception {
		TimeRate rate = new TimeRate(100.00, Duration.minutes(1));
		assertThat(rate.toString(), is("100 per 1 minute"));
	}
}
