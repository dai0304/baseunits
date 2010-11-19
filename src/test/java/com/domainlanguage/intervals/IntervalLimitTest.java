package com.domainlanguage.intervals;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.assertThat;

import org.junit.Test;

/**
 * {@link IntervalLimit}のテストクラス。
 * 
 * @author daisuke
 */
public class IntervalLimitTest {
	
	/**
	 * {@link IntervalLimit#compareTo(IntervalLimit)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test01_Unlimited() throws Exception {
		IntervalLimit<Integer> lowerInf = IntervalLimit.lower(false, (Integer) null);
		IntervalLimit<Integer> upperInf = IntervalLimit.upper(false, (Integer) null);
		IntervalLimit<Integer> lowerOpen2 = IntervalLimit.lower(false, 2);
		IntervalLimit<Integer> lowerClose2 = IntervalLimit.lower(true, 2);
		IntervalLimit<Integer> lowerOpen3 = IntervalLimit.lower(false, 3);
		IntervalLimit<Integer> lowerClose3 = IntervalLimit.lower(true, 3);
		IntervalLimit<Integer> upperOpen5 = IntervalLimit.upper(false, 5);
		IntervalLimit<Integer> upperClose5 = IntervalLimit.upper(true, 5);
		IntervalLimit<Integer> upperOpen6 = IntervalLimit.upper(false, 6);
		IntervalLimit<Integer> upperClose6 = IntervalLimit.upper(true, 6);
		
		// 無限同士比較
		assertThat(lowerInf.compareTo(upperInf), is(0));
		assertThat(upperInf.compareTo(lowerInf), is(0));
		
		// 無限有限比較
		assertThat(lowerInf.compareTo(lowerOpen3), is(lessThan(0)));
		assertThat(lowerInf.compareTo(lowerClose3), is(lessThan(0)));
		assertThat(lowerInf.compareTo(upperOpen5), is(lessThan(0)));
		assertThat(lowerInf.compareTo(upperClose5), is(lessThan(0)));
		assertThat(upperInf.compareTo(lowerOpen3), is(greaterThan(0)));
		assertThat(upperInf.compareTo(lowerClose3), is(greaterThan(0)));
		assertThat(upperInf.compareTo(upperOpen5), is(greaterThan(0)));
		assertThat(upperInf.compareTo(upperClose5), is(greaterThan(0)));
		
		// 有限無限比較（上記セクションとの対象性検証）
		assertThat(lowerOpen3.compareTo(lowerInf), is(greaterThan(0)));
		assertThat(lowerClose3.compareTo(lowerInf), is(greaterThan(0)));
		assertThat(upperOpen5.compareTo(lowerInf), is(greaterThan(0)));
		assertThat(upperClose5.compareTo(lowerInf), is(greaterThan(0)));
		assertThat(lowerOpen3.compareTo(upperInf), is(lessThan(0)));
		assertThat(lowerClose3.compareTo(upperInf), is(lessThan(0)));
		assertThat(upperOpen5.compareTo(upperInf), is(lessThan(0)));
		assertThat(upperClose5.compareTo(upperInf), is(lessThan(0)));
		
		// 有限比較
		assertThat(lowerClose2.compareTo(lowerClose2), is(0));
		assertThat(lowerClose2.compareTo(lowerOpen2), is(0));
		assertThat(lowerClose2.compareTo(lowerClose3), is(lessThan(0)));
		assertThat(lowerClose2.compareTo(lowerOpen3), is(lessThan(0)));
		assertThat(lowerClose2.compareTo(upperClose5), is(lessThan(0)));
		assertThat(lowerClose2.compareTo(upperOpen5), is(lessThan(0)));
		assertThat(lowerClose2.compareTo(upperClose6), is(lessThan(0)));
		assertThat(lowerClose2.compareTo(upperOpen6), is(lessThan(0)));
		
		assertThat(lowerOpen2.compareTo(lowerClose2), is(0));
		assertThat(lowerOpen2.compareTo(lowerOpen2), is(0));
		assertThat(lowerOpen2.compareTo(lowerClose3), is(lessThan(0)));
		assertThat(lowerOpen2.compareTo(lowerOpen3), is(lessThan(0)));
		assertThat(lowerOpen2.compareTo(upperClose5), is(lessThan(0)));
		assertThat(lowerOpen2.compareTo(upperOpen5), is(lessThan(0)));
		assertThat(lowerOpen2.compareTo(upperClose6), is(lessThan(0)));
		assertThat(lowerOpen2.compareTo(upperOpen6), is(lessThan(0)));
		
		assertThat(lowerClose3.compareTo(lowerClose2), is(greaterThan(0)));
		assertThat(lowerClose3.compareTo(lowerOpen2), is(greaterThan(0)));
		assertThat(lowerClose3.compareTo(lowerClose3), is(0));
		assertThat(lowerClose3.compareTo(lowerOpen3), is(0));
		assertThat(lowerClose3.compareTo(upperClose5), is(lessThan(0)));
		assertThat(lowerClose3.compareTo(upperOpen5), is(lessThan(0)));
		assertThat(lowerClose3.compareTo(upperClose6), is(lessThan(0)));
		assertThat(lowerClose3.compareTo(upperOpen6), is(lessThan(0)));
		
		assertThat(lowerOpen3.compareTo(lowerClose2), is(greaterThan(0)));
		assertThat(lowerOpen3.compareTo(lowerOpen2), is(greaterThan(0)));
		assertThat(lowerOpen3.compareTo(lowerClose3), is(0));
		assertThat(lowerOpen3.compareTo(lowerOpen3), is(0));
		assertThat(lowerOpen3.compareTo(upperClose5), is(lessThan(0)));
		assertThat(lowerOpen3.compareTo(upperOpen5), is(lessThan(0)));
		assertThat(lowerOpen3.compareTo(upperClose6), is(lessThan(0)));
		assertThat(lowerOpen3.compareTo(upperOpen6), is(lessThan(0)));
		
		assertThat(upperClose5.compareTo(lowerClose2), is(greaterThan(0)));
		assertThat(upperClose5.compareTo(lowerOpen2), is(greaterThan(0)));
		assertThat(upperClose5.compareTo(lowerClose3), is(greaterThan(0)));
		assertThat(upperClose5.compareTo(lowerOpen3), is(greaterThan(0)));
		assertThat(upperClose5.compareTo(upperClose5), is(0));
		assertThat(upperClose5.compareTo(upperOpen5), is(0));
		assertThat(upperClose5.compareTo(upperClose6), is(lessThan(0)));
		assertThat(upperClose5.compareTo(upperOpen6), is(lessThan(0)));
		
		assertThat(upperOpen5.compareTo(lowerClose2), is(greaterThan(0)));
		assertThat(upperOpen5.compareTo(lowerOpen2), is(greaterThan(0)));
		assertThat(upperOpen5.compareTo(lowerClose3), is(greaterThan(0)));
		assertThat(upperOpen5.compareTo(lowerOpen3), is(greaterThan(0)));
		assertThat(upperOpen5.compareTo(upperClose5), is(0));
		assertThat(upperOpen5.compareTo(upperOpen5), is(0));
		assertThat(upperOpen5.compareTo(upperClose6), is(lessThan(0)));
		assertThat(upperOpen5.compareTo(upperOpen6), is(lessThan(0)));
		
		assertThat(upperClose6.compareTo(lowerClose2), is(greaterThan(0)));
		assertThat(upperClose6.compareTo(lowerOpen2), is(greaterThan(0)));
		assertThat(upperClose6.compareTo(lowerClose3), is(greaterThan(0)));
		assertThat(upperClose6.compareTo(lowerOpen3), is(greaterThan(0)));
		assertThat(upperClose6.compareTo(upperClose5), is(greaterThan(0)));
		assertThat(upperClose6.compareTo(upperOpen5), is(greaterThan(0)));
		assertThat(upperClose6.compareTo(upperClose6), is(0));
		assertThat(upperClose6.compareTo(upperOpen6), is(0));
		
		assertThat(upperOpen6.compareTo(lowerClose2), is(greaterThan(0)));
		assertThat(upperOpen6.compareTo(lowerOpen2), is(greaterThan(0)));
		assertThat(upperOpen6.compareTo(lowerClose3), is(greaterThan(0)));
		assertThat(upperOpen6.compareTo(lowerOpen3), is(greaterThan(0)));
		assertThat(upperOpen6.compareTo(upperClose5), is(greaterThan(0)));
		assertThat(upperOpen6.compareTo(upperOpen5), is(greaterThan(0)));
		assertThat(upperOpen6.compareTo(upperClose6), is(0));
		assertThat(upperOpen6.compareTo(upperOpen6), is(0));
	}
}
