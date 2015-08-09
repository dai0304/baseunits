/*
 * Copyright 2010-2015 Miyamoto Daisuke.
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
import static org.junit.Assert.assertThat;
import jp.xet.baseunits.money.Money;
import jp.xet.baseunits.money.Proration;

import org.junit.Test;

/**
 * {@link Proration}のテストクラス。
 */
public class ProrationTest {
	
	/**
	 * {@link Proration#proratedOver(Money, long[])}
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
	 * {@link Proration#proratedOver(Money, long[])}
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
	 * {@link Proration#proratedOver(Money, long[])}
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
	 * {@link Proration#proratedOver(Money, long[])}
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
	 * {@link Proration#dividedEvenlyIntoParts(Money, int)}
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
	 * {@link Proration#dividedEvenlyIntoParts(Money, int)}
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
	 * {@link Proration#distributeRemainderOver(Money[], Money)}のテスト。（内部API）
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
	 * {@link Proration#sum(Money[])}
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
	 * {@link Proration#partOfWhole(Money, long, long)}
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
