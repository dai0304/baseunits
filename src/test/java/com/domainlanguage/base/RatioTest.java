/**
 * Copyright (c) 2004 Domain Language, Inc. (http://domainlanguage.com) This
 * free software is distributed under the "MIT" licence. See file licence.txt.
 * For more information, see http://timeandmoney.sourceforge.net.
 */

package com.domainlanguage.base;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;

import com.domainlanguage.base.Ratio;
import com.domainlanguage.base.Rounding;

import org.junit.Test;

/**
 * {@link Ratio}のテストクラス。
 * 
 * @author daisuke
 */
public class RatioTest {
	
	/**
	 * TODO for daisuke
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test01_BigDecimalRatio() throws Exception {
		Ratio r3over2 = Ratio.of(new BigDecimal(3), new BigDecimal(2));
		BigDecimal result = r3over2.decimalValue(1, Rounding.UNNECESSARY);
		assertThat(result, is(new BigDecimal("1.5")));
		
		Ratio r10over3 = Ratio.of(new BigDecimal(10), new BigDecimal(3));
		result = r10over3.decimalValue(3, Rounding.DOWN);
		assertThat(result, is(new BigDecimal("3.333")));
		
		result = r10over3.decimalValue(3, Rounding.UP);
		assertThat(result, is(new BigDecimal("3.334")));
		
		Ratio rManyDigits = Ratio.of(new BigDecimal("9.001"), new BigDecimal(3));
		result = rManyDigits.decimalValue(6, Rounding.UP);
		assertThat(result, is(new BigDecimal("3.000334")));
		
		result = rManyDigits.decimalValue(7, Rounding.UP);
		assertThat(result, is(new BigDecimal("3.0003334")));
		
		result = rManyDigits.decimalValue(7, Rounding.HALF_UP);
		assertThat(result, is(new BigDecimal("3.0003333")));
	}
	
	/**
	 * TODO for daisuke
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test02_LongRatio() throws Exception {
		Ratio rManyDigits = Ratio.of(9001L, 3000L);
		BigDecimal result = rManyDigits.decimalValue(6, Rounding.UP);
		assertThat(result, is(new BigDecimal("3.000334")));
	}
	
	/**
	 * TODO for daisuke
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test03_Equals() throws Exception {
		assertThat(Ratio.of(100, 200).equals(Ratio.of(100, 200)), is(true));
		assertThat(Ratio.of(100, 200), is(Ratio.of(100, 200)));
		assertThat(Ratio.of(new BigDecimal("100"), new BigDecimal("200")), is(Ratio.of(100, 200)));
	}
	
	/**
	 * TODO for daisuke
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test04_MultiplyNumerator() throws Exception {
		Ratio rManyDigits = Ratio.of(9001, 3000);
		Ratio product = rManyDigits.times(new BigDecimal("1.1"));
		assertThat(product, is(Ratio.of(new BigDecimal("9901.1"), new BigDecimal(3000))));
	}
	
	/**
	 * TODO for daisuke
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test05_MultiplyByRatio() throws Exception {
		Ratio r1 = Ratio.of(9001, 3000);
		Ratio r2 = Ratio.of(3, 2);
		Ratio expectedProduct = Ratio.of(27003, 6000);
		assertThat(r1.times(r2), is(expectedProduct));
	}
}
