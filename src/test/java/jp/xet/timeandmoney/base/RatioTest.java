/**
 * Copyright (c) 2004 Domain Language, Inc. (http://domainlanguage.com) This
 * free software is distributed under the "MIT" licence. See file licence.txt.
 * For more information, see http://timeandmoney.sourceforge.net.
 */

package jp.xet.timeandmoney.base;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.math.BigDecimal;

import org.junit.Test;

/**
 * {@link Ratio}のテストクラス。
 * 
 * @author daisuke
 */
public class RatioTest {
	
	/**
	 * {@link BigDecimal}で構成する{@link Ratio}の挙動テスト。
	 * 
	 * <ul>
	 *   <li>{@code 3/2}であらわす割合について、小数点第1位までで丸めなかった場合は1.5である。</li>
	 *   <li>{@code 10/3}であらわす割合について、小数点第3位までで切り捨てた場合は3.333である。</li>
	 *   <li>{@code 10/3}であらわす割合について、小数点第3位までで切り上げた場合は3.334である。</li>
	 *   <li>{@code 9.001/3}であらわす割合（3.00033…）について、小数点第6位までで切り上げた場合は3.000334である。</li>
	 *   <li>{@code 9.001/3}であらわす割合（3.00033…）について、小数点第7位までで切り上げた場合は3.0003334である。</li>
	 *   <li>{@code 9.001/3}であらわす割合（3.00033…）について、小数点第7位までで四捨五入した場合は3.0003333である。</li>
	 * </ul>
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
		
		try {
			Ratio.of(0, 0);
			fail();
		} catch (ArithmeticException e) {
			// success
		}
		try {
			Ratio.of(10, 0);
			fail();
		} catch (ArithmeticException e) {
			// success
		}
	}
	
	/**
	 * {@code long}で構成する{@link Ratio}の挙動テスト。
	 * 
	 * <p>{@code 9001/3000}であらわす割合（3.00033…）について、小数点第6位までで切り上げた場合は3.000334である。</p>
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
	 * {@link Ratio#equals(Object)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test03_Equals() throws Exception {
		assertThat(Ratio.of(100, 200).equals(Ratio.of(100, 200)), is(true));
		assertThat(Ratio.of(100, 200), is(Ratio.of(100, 200)));
		assertThat(Ratio.of(new BigDecimal("100"), new BigDecimal("200")), is(Ratio.of(100, 200)));
		
		// THINK 等価なんだけどな。
		assertThat(Ratio.of(100, 200), is(not(Ratio.of(10, 20))));
	}
	
	/**
	 * {@link Ratio#times(BigDecimal)}のテスト。
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
	 * {@link Ratio#times(Ratio)}のテスト。
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
