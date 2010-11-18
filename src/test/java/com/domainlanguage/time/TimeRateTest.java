/**
 * Copyright (c) 2005 Domain Language, Inc. (http://domainlanguage.com) This
 * free software is distributed under the "MIT" licence. See file licence.txt.
 * For more information, see http://timeandmoney.sourceforge.net.
 */
package com.domainlanguage.time;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.math.BigDecimal;

import com.domainlanguage.base.Rounding;

import org.junit.Test;

/**
 * {@link TimeRate}のテストクラス。
 * 
 * @author daisuke
 */
public class TimeRateTest {
	
	/**
	 * TODO for daisuke
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test01_SimpleRate() throws Exception {
		TimeRate rate = new TimeRate(100.00, Duration.minutes(1));
		assertThat(rate.over(Duration.hours(1)), is(new BigDecimal(6000.00)));
	}
	
	/**
	 * TODO for daisuke
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
		}
	}
	
	/**
	 * TODO for daisuke
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test03_RoundingRate() throws Exception {
		TimeRate rate = new TimeRate("100.00", Duration.minutes(3));
		assertThat(rate.over(Duration.minutes(1), Rounding.DOWN), is(new BigDecimal("33.33")));
	}
	
	/**
	 * TODO for daisuke
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test04_RoundingScalingRate() throws Exception {
		TimeRate rate = new TimeRate("100.00", Duration.minutes(3));
		assertThat(rate.over(Duration.minutes(1), 3, Rounding.DOWN), is(new BigDecimal("33.333")));
	}
	
	/**
	 * TODO for daisuke
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test05_Equals() throws Exception {
		TimeRate rate = new TimeRate(11, Duration.days(2));
		assertThat(rate, is(new TimeRate(11.00, Duration.days(2))));
	}
}
