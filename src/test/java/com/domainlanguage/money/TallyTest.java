package com.domainlanguage.money;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

/**
 * {@link Tally}のテストクラス。
 * 
 * @author daisuke
 */
public class TallyTest {
	
	/**
	 * {@link Tally#net()}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test01_Net() throws Exception {
		Tally tally = new Tally(Money.dollars(55.34), Money.dollars(12.22), Money.dollars(-3.07));
		assertThat(tally.net(), is(Money.dollars(64.49)));
	}
}
