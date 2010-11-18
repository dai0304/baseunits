/**
 * Copyright (c) 2004 Domain Language, Inc. (http://domainlanguage.com) This
 * free software is distributed under the "MIT" licence. See file licence.txt.
 * For more information, see http://timeandmoney.sourceforge.net.
 */

package com.domainlanguage.money;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

/**
 * {@link Proration}のテストクラス。
 * 
 * @author daisuke
 */
public class ProrationTest {
	
	/**
	 * TODO for daisuke
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test01_Allocate1() throws Exception {
		long[] proportions = {
			1,
			1
		};
		Money[] result = Proration.proratedOver(Money.dollars(0.01), proportions);
		assertThat(result[0], is(Money.dollars(0.01)));
		assertThat(result[1], is(Money.dollars(0)));
	}
	
	/**
	 * TODO for daisuke
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test02_ProrateOver2() throws Exception {
		long[] proportions = {
			3,
			7
		};
		Money[] result = Proration.proratedOver(Money.dollars(0.05), proportions);
		assertThat(result[0], is(Money.dollars(0.02)));
		assertThat(result[1], is(Money.dollars(0.03)));
	}
	
	/**
	 * TODO for daisuke
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test03_ProrateOver10() throws Exception {
		long[] proportions = {
			17,
			2,
			1,
			35,
			35,
			10
		};
		Money[] result = Proration.proratedOver(Money.dollars(0.10), proportions);
		assertThat(result[0], is(Money.dollars(0.02)));
		assertThat(result[1], is(Money.dollars(0.01)));
		assertThat(result[2], is(Money.dollars(0.00)));
		assertThat(result[3], is(Money.dollars(0.03)));
		assertThat(result[4], is(Money.dollars(0.03)));
		assertThat(result[5], is(Money.dollars(0.01)));
		Money sum = Money.dollars(0.0);
		for (Money element : result) {
			sum = sum.plus(element);
		}
		assertThat(sum, is(Money.dollars(0.10)));
	}
	
	/**
	 * TODO for daisuke
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test04_ProrateZeroTotal() throws Exception {
		long[] proportions = {
			3,
			7
		};
		Money[] result = Proration.proratedOver(Money.dollars(0), proportions);
		assertThat(result[0], is(Money.dollars(0)));
		assertThat(result[1], is(Money.dollars(0)));
	}
	
	/**
	 * TODO for daisuke
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test05_ProrateTotalIndivisibleBy3() throws Exception {
		Money[] actual = Proration.dividedEvenlyIntoParts(Money.dollars(100), 3);
		Money[] expected = {
			Money.dollars(33.34),
			Money.dollars(33.33),
			Money.dollars(33.33)
		};
		for (int i = 0; i < expected.length; i++) {
			assertThat(actual[i], is(expected[i]));
		}
	}
	
	/**
	 * TODO for daisuke
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test06_ProrateOnlyOneShortOfEven() throws Exception {
		Money[] prorated = Proration.dividedEvenlyIntoParts(Money.dollars(1.09), 10);
		for (int i = 0; i < 9; i++) {
			assertThat(prorated[i], is(Money.dollars(0.11)));
		}
		assertThat(prorated[9], is(Money.dollars(0.10)));
	}
	
	/**
	 * TODO for daisuke
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test07_DistributeRemainder() throws Exception {
		Money[] startingValues = new Money[4];
		startingValues[0] = Money.dollars(1.00);
		startingValues[1] = Money.dollars(2.00);
		startingValues[2] = Money.dollars(3.00);
		startingValues[3] = Money.dollars(4.00);
		Money[] result = Proration.distributeRemainderOver(startingValues, Money.dollars(0.02));
		assertThat(result[0], is(Money.dollars(1.01)));
		assertThat(result[1], is(Money.dollars(2.01)));
		assertThat(result[2], is(Money.dollars(3.00)));
		assertThat(result[3], is(Money.dollars(4.00)));
	}
	
	/**
	 * TODO for daisuke
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test08_SumMoney() throws Exception {
		Money[] startingValues = new Money[4];
		startingValues[0] = Money.dollars(1.00);
		startingValues[1] = Money.dollars(2.00);
		startingValues[2] = Money.dollars(3.00);
		startingValues[3] = Money.dollars(4.00);
		assertThat(Proration.sum(startingValues), is(Money.dollars(10.00)));
	}
	
	/**
	 * TODO for daisuke
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test09_PartOfWhole() throws Exception {
		Money total = Money.dollars(10.00);
		long portion = 3L;
		long whole = 9L;
		assertThat(Proration.partOfWhole(total, portion, whole), is(Money.dollars(3.33)));
	}
	
}
