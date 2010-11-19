package com.domainlanguage.money;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

/**
 * {@link Allotment}のテストクラス。
 * 
 * @author daisuke
 */
public class AllotmentTest {
	
	/**
	 * 等価性検証。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test01_Equals() throws Exception {
		assertThat(new Allotment<String>("ABC", Money.dollars(1.23)).equals(new Allotment<String>("ABC", Money
			.dollars(1.23))), is(true));
		
		assertThat(new Allotment<String>("ABC", Money.dollars(1.23)).equals(new Allotment<String>("ABC", Money
			.euros(1.23))), is(false));
		
		assertThat(new Allotment<String>("ABC", Money.dollars(1.23)).equals(new Allotment<String>("XYZ", Money
			.dollars(1.23))), is(false));
		
		assertThat(new Allotment<String>("ABC", Money.dollars(1.23)).equals(new Allotment<String>("ABC", Money
			.dollars(1.24))), is(false));
	}
	
	/**
	 * 正負転換検証。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test02_Negated() throws Exception {
		assertThat(new Allotment<String>("ABC", Money.dollars(-1.23)).equals(new Allotment<String>("ABC", Money
			.dollars(1.23)).negated()), is(true));
	}
}
