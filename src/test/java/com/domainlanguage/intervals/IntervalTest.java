/**
 * Copyright (c) 2004 Domain Language, Inc. (http://domainlanguage.com) This
 * free software is distributed under the "MIT" licence. See file licence.txt.
 * For more information, see http://timeandmoney.sourceforge.net.
 */

package com.domainlanguage.intervals;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.util.List;

import com.domainlanguage.intervals.Interval;
import com.domainlanguage.intervals.IntervalLimit;
import com.domainlanguage.tests.SerializationTester;

import org.junit.Test;

/**
 * {@link Interval}のテストクラス。
 * 
 * @author daisuke
 */
public class IntervalTest {
	
	public static IntervalLimit<Integer> exampleLimitForPersistentMappingTesting() {
		return IntervalLimit.upper(true, 78);
	}
	

//    private Interval o1_10o = Interval.open(new BigDecimal(1), new BigDecimal(10));
//    private Interval o10_12o = Interval.open(new BigDecimal(10), new BigDecimal(12));
	private Interval<BigDecimal> empty = Interval.open(new BigDecimal(1), new BigDecimal(1));
	
	private Interval<BigDecimal> c5_10c = Interval.closed(new BigDecimal(5), new BigDecimal(10));
	
	private Interval<BigDecimal> c1_10c = Interval.closed(new BigDecimal(1), new BigDecimal(10));
	
	private Interval<BigDecimal> c4_6c = Interval.closed(new BigDecimal(4), new BigDecimal(6));
	
	private Interval<BigDecimal> c5_15c = Interval.closed(new BigDecimal(5), new BigDecimal(15));
	
	private Interval<BigDecimal> c12_16c = Interval.closed(new BigDecimal(12), new BigDecimal(16));
	
	private Interval<BigDecimal> o10_12c = Interval.over(new BigDecimal(10), false, new BigDecimal(12), true);
	
	private Interval<BigDecimal> o1_1c = Interval.over(new BigDecimal(1), false, new BigDecimal(1), true);
	
	private Interval<BigDecimal> c1_1o = Interval.over(new BigDecimal(1), true, new BigDecimal(1), false);
	
	private Interval<BigDecimal> c1_1c = Interval.over(new BigDecimal(1), true, new BigDecimal(1), true);
	
	private Interval<BigDecimal> o1_1o = Interval.over(new BigDecimal(1), false, new BigDecimal(1), false);
	

	/**
	 * TODO for daisuke
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test01_Assertions() throws Exception {
		//Redundant, maybe, but with all the compiler default
		//confusion at the moment, I decided to throw this in.
		try {
			Interval.closed(new BigDecimal(2.0), new BigDecimal(1.0));
			fail("Lower bound mustn't be above upper bound.");
		} catch (IllegalArgumentException e) {
			//Correct. Do nothing.
		}
	}
	
	/**
	 * TODO for daisuke
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test02_UpTo() throws Exception {
		Interval<Double> range = Interval.upTo(new Double(5.5));
		assertThat(range.includes(new Double(5.5)), is(true));
		assertThat(range.includes(new Double(-5.5)), is(true));
		assertThat(range.includes(Double.NEGATIVE_INFINITY), is(true));
		assertThat(!range.includes(new Double(5.5001)), is(true));
	}
	
	/**
	 * TODO for daisuke
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test03_AndMore() throws Exception {
		Interval<Double> range = Interval.andMore(5.5);
		assertThat(range.includes(5.5), is(true));
		assertThat(range.includes(5.4999), is(false));
		assertThat(range.includes(-5.5), is(false));
		assertThat(range.includes(Double.POSITIVE_INFINITY), is(true));
		assertThat(range.includes(5.5001), is(true));
	}
	
	/**
	 * TODO for daisuke
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test04_AbstractCreation() throws Exception {
		Interval<Integer> concrete = new Interval<Integer>(1, true, 3, true);
		Interval<Integer> newInterval = concrete.newOfSameType(1, false, 4, false);
		
		Interval<Integer> expected = new Interval<Integer>(1, false, 4, false);
		assertThat(newInterval, is(expected));
	}
	
	/**
	 * TODO for daisuke
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test05_Serialization() throws Exception {
		SerializationTester.assertCanBeSerialized(c5_10c);
	}
	
	/**
	 * TODO for daisuke
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test06_ToString() throws Exception {
		assertThat(c1_10c.toString(), is("[1, 10]"));
		assertThat(o10_12c.toString(), is("(10, 12]"));
		assertThat(empty.toString(), is("{}"));
		assertThat(Interval.closed(10, 10).toString(), is("{10}"));
	}
	
	/**
	 * TODO for daisuke
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test07_IsBelow() throws Exception {
		Interval<BigDecimal> range = Interval.closed(new BigDecimal(-5.5), new BigDecimal(6.6));
		assertThat(range.isBelow(new BigDecimal(5.0)), is(false));
		assertThat(range.isBelow(new BigDecimal(-5.5)), is(false));
		assertThat(range.isBelow(new BigDecimal(-5.4999)), is(false));
		assertThat(range.isBelow(new BigDecimal(6.6)), is(false));
		assertThat(range.isBelow(new BigDecimal(6.601)), is(true));
		assertThat(range.isBelow(new BigDecimal(-5.501)), is(false));
	}
	
	/**
	 * TODO for daisuke
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test08_Includes() throws Exception {
		Interval<BigDecimal> range = Interval.closed(new BigDecimal(-5.5), new BigDecimal(6.6));
		assertThat(range.includes(new BigDecimal(5.0)), is(true));
		assertThat(range.includes(new BigDecimal(-5.5)), is(true));
		assertThat(range.includes(new BigDecimal(-5.4999)), is(true));
		assertThat(range.includes(new BigDecimal(6.6)), is(true));
		assertThat(range.includes(new BigDecimal(6.601)), is(false));
		assertThat(range.includes(new BigDecimal(-5.501)), is(false));
	}
	
	/**
	 * TODO for daisuke
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test09_OpenInterval() throws Exception {
		Interval<BigDecimal> exRange = Interval.over(new BigDecimal(-5.5), false, new BigDecimal(6.6), true);
		assertThat(exRange.includes(new BigDecimal(5.0)), is(true));
		assertThat(exRange.includes(new BigDecimal(-5.5)), is(false));
		assertThat(exRange.includes(new BigDecimal(-5.4999)), is(true));
		assertThat(exRange.includes(new BigDecimal(6.6)), is(true));
		assertThat(exRange.includes(new BigDecimal(6.601)), is(false));
		assertThat(exRange.includes(new BigDecimal(-5.501)), is(false));
	}
	
	/**
	 * TODO for daisuke
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test10_IsEmpty() throws Exception {
		assertThat(Interval.closed(5, 6).isEmpty(), is(false));
		assertThat(Interval.closed(6, 6).isEmpty(), is(false));
		assertThat(Interval.open(6, 6).isEmpty(), is(true));
		assertThat(c1_10c.emptyOfSameType().isEmpty(), is(true));
	}
	
	/**
	 * TODO for daisuke
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test11_Intersects() throws Exception {
		assertThat("c5_10c.intersects(c1_10c)", c5_10c.intersects(c1_10c), is(true));
		assertThat("c1_10c.intersects(c5_10c)", c1_10c.intersects(c5_10c), is(true));
		assertThat("c4_6c.intersects(c1_10c)", c4_6c.intersects(c1_10c), is(true));
		assertThat("c1_10c.intersects(c4_6c)", c1_10c.intersects(c4_6c), is(true));
		assertThat("c5_10c.intersects(c5_15c)", c5_10c.intersects(c5_15c), is(true));
		assertThat("c5_15c.intersects(c1_10c)", c5_15c.intersects(c1_10c), is(true));
		assertThat("c1_10c.intersects(c5_15c)", c1_10c.intersects(c5_15c), is(true));
		assertThat("c1_10c.intersects(c12_16c)", c1_10c.intersects(c12_16c), is(false));
		assertThat("c12_16c.intersects(c1_10c)", c12_16c.intersects(c1_10c), is(false));
		assertThat("c5_10c.intersects(c5_10c)", c5_10c.intersects(c5_10c), is(true));
		assertThat("c1_10c.intersects(o10_12c)", c1_10c.intersects(o10_12c), is(false));
		assertThat("o10_12c.intersects(c1_10c)", o10_12c.intersects(c1_10c), is(false));
	}
	
	/**
	 * TODO for daisuke
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test12_Intersection() throws Exception {
		assertThat(c5_10c.intersect(c1_10c), is(c5_10c));
		assertThat(c1_10c.intersect(c5_10c), is(c5_10c));
		assertThat(c4_6c.intersect(c1_10c), is(c4_6c));
		assertThat(c1_10c.intersect(c4_6c), is(c4_6c));
		assertThat(c5_10c.intersect(c5_15c), is(c5_10c));
		assertThat(c5_15c.intersect(c1_10c), is(c5_10c));
		assertThat(c1_10c.intersect(c5_15c), is(c5_10c));
		assertThat(c1_10c.intersect(c12_16c).isEmpty(), is(true));
		assertThat(c1_10c.intersect(c12_16c), is(empty));
		assertThat(c12_16c.intersect(c1_10c), is(empty));
		assertThat(c5_10c.intersect(c5_10c), is(c5_10c));
		assertThat(c1_10c.intersect(o10_12c), is(empty));
		assertThat(o10_12c.intersect(c1_10c), is(empty));
	}
	
	/**
	 * TODO for daisuke
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test13_GreaterOfLowerLimits() throws Exception {
		assertThat(c5_10c.greaterOfLowerLimits(c1_10c), is(new BigDecimal(5)));
		assertThat(c1_10c.greaterOfLowerLimits(c5_10c), is(new BigDecimal(5)));
		assertThat(c1_10c.greaterOfLowerLimits(c12_16c), is(new BigDecimal(12)));
		assertThat(c12_16c.greaterOfLowerLimits(c1_10c), is(new BigDecimal(12)));
	}
	
	/**
	 * TODO for daisuke
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test14_LesserOfUpperLimits() throws Exception {
		assertThat(c5_10c.lesserOfUpperLimits(c1_10c), is(new BigDecimal(10)));
		assertThat(c1_10c.lesserOfUpperLimits(c5_10c), is(new BigDecimal(10)));
		assertThat(c4_6c.lesserOfUpperLimits(c12_16c), is(new BigDecimal(6)));
		assertThat(c12_16c.lesserOfUpperLimits(c4_6c), is(new BigDecimal(6)));
	}
	
	/**
	 * TODO for daisuke
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test15_CoversInterval() throws Exception {
		assertThat(c5_10c.covers(c1_10c), is(false));
		assertThat(c1_10c.covers(c5_10c), is(true));
		assertThat(c4_6c.covers(c1_10c), is(false));
		assertThat(c1_10c.covers(c4_6c), is(true));
		assertThat(c5_10c.covers(c5_10c), is(true));
		Interval<BigDecimal> halfOpen5_10 = Interval.over(new BigDecimal(5), false, new BigDecimal(10), true);
		assertThat("closed incl left-open", c5_10c.covers(halfOpen5_10), is(true));
		assertThat("left-open incl left-open", halfOpen5_10.covers(halfOpen5_10), is(true));
		assertThat("left-open doesn't include closed", halfOpen5_10.covers(c5_10c), is(false));
		//TODO: Need to test other half-open case and full-open case.
	}
	
	/**
	 * TODO for daisuke
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test16_Gap() throws Exception {
		Interval<Integer> c1_3c = Interval.closed(1, 3);
		Interval<Integer> c5_7c = Interval.closed(5, 7);
		Interval<Integer> o3_5o = Interval.open(3, 5);
		Interval<Integer> c2_3o = Interval.over(2, true, 3, false);
		
		assertThat(c1_3c.gap(c5_7c), is(o3_5o));
		assertThat(c1_3c.gap(o3_5o).isEmpty(), is(true));
		assertThat(c1_3c.gap(c2_3o).isEmpty(), is(true));
		assertThat(c2_3o.gap(o3_5o).isSingleElement(), is(true));
	}
	
	/**
	 * TODO for daisuke
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test17_RelativeComplementDisjoint() throws Exception {
		Interval<Integer> c1_3c = Interval.closed(1, 3);
		Interval<Integer> c5_7c = Interval.closed(5, 7);
		List<Interval<Integer>> complement = c1_3c.complementRelativeTo(c5_7c);
		assertThat(complement.size(), is(1));
		assertThat(complement.get(0), is(c5_7c));
	}
	
	/**
	 * TODO for daisuke
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test18_RelativeComplementDisjointAdjacentOpen() throws Exception {
		Interval<Integer> c1_3o = Interval.over(1, true, 3, false);
		Interval<Integer> c3_7c = Interval.closed(3, 7);
		List<Interval<Integer>> complement = c1_3o.complementRelativeTo(c3_7c);
		assertThat(complement.size(), is(1));
		assertThat(complement.get(0), is(c3_7c));
	}
	
	/**
	 * TODO for daisuke
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test19_RelativeComplementOverlapLeft() throws Exception {
		Interval<Integer> c1_5c = Interval.closed(1, 5);
		Interval<Integer> c3_7c = Interval.closed(3, 7);
		List<Interval<Integer>> complement = c3_7c.complementRelativeTo(c1_5c);
		Interval<Integer> c1_3o = Interval.over(1, true, 3, false);
		assertThat(complement.size(), is(1));
		assertThat(complement.get(0), is(c1_3o));
	}
	
	/**
	 * TODO for daisuke
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test20_RelativeComplementOverlapRight() throws Exception {
		Interval<Integer> c1_5c = Interval.closed(1, 5);
		Interval<Integer> c3_7c = Interval.closed(3, 7);
		List<Interval<Integer>> complement = c1_5c.complementRelativeTo(c3_7c);
		Interval<Integer> o5_7c = Interval.over(5, false, 7, true);
		assertThat(complement.size(), is(1));
		assertThat(complement.get(0), is(o5_7c));
	}
	
	/**
	 * TODO for daisuke
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test21_RelativeComplementAdjacentClosed() throws Exception {
		Interval<Integer> c1_3c = Interval.closed(1, 3);
		Interval<Integer> c5_7c = Interval.closed(5, 7);
		List<Interval<Integer>> complement = c1_3c.complementRelativeTo(c5_7c);
		assertThat(complement.size(), is(1));
		assertThat(complement.get(0), is(c5_7c));
	}
	
	/**
	 * TODO for daisuke
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test22_RelativeComplementEnclosing() throws Exception {
		Interval<Integer> c3_5c = Interval.closed(3, 5);
		Interval<Integer> c1_7c = Interval.closed(1, 7);
		List<Interval<Integer>> complement = c1_7c.complementRelativeTo(c3_5c);
		assertThat(complement.size(), is(0));
	}
	
	/**
	 * TODO for daisuke
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test23_RelativeComplementEqual() throws Exception {
		Interval<Integer> c1_7c = Interval.closed(1, 7);
		List<Interval<Integer>> complement = c1_7c.complementRelativeTo(c1_7c);
		assertThat(complement.size(), is(0));
	}
	
	/**
	 * TODO for daisuke
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test24_RelativeComplementEnclosed() throws Exception {
		Interval<Integer> c3_5c = Interval.closed(3, 5);
		Interval<Integer> c1_7c = Interval.closed(1, 7);
		Interval<Integer> c1_3o = Interval.over(1, true, 3, false);
		Interval<Integer> o5_7c = Interval.over(5, false, 7, true);
		List<Interval<Integer>> complement = c3_5c.complementRelativeTo(c1_7c);
		assertThat(complement.size(), is(2));
		assertThat(complement.get(0), is(c1_3o));
		assertThat(complement.get(1), is(o5_7c));
	}
	
	/**
	 * TODO for daisuke
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test25_RelativeComplementEnclosedEndPoint() throws Exception {
		Interval<Integer> o3_5o = Interval.open(3, 5);
		Interval<Integer> c3_5c = Interval.closed(3, 5);
		List<Interval<Integer>> complement = o3_5o.complementRelativeTo(c3_5c);
		assertThat(complement.size(), is(2));
		assertThat(complement.get(0).includes(3), is(true));
	}
	
	/**
	 * TODO for daisuke
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test26_IsSingleElement() throws Exception {
		assertThat(o1_1c.isSingleElement(), is(true));
		assertThat(c1_1c.isSingleElement(), is(true));
		assertThat(c1_1o.isSingleElement(), is(true));
		assertThat(c1_10c.isSingleElement(), is(false));
		assertThat(o1_1o.isSingleElement(), is(false));
	}
	
	/**
	 * TODO for daisuke
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test27_EqualsForOnePointIntervals() throws Exception {
		assertThat(c1_1o, is(o1_1c));
		assertThat(c1_1c, is(o1_1c));
		assertThat(c1_1c, is(c1_1o));
		assertThat(o1_1c.equals(o1_1o), is(false));
	}
	
	/**
	 * TODO for daisuke
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test28_EqualsForEmptyIntervals() throws Exception {
		assertThat(c4_6c.emptyOfSameType(), is(c1_10c.emptyOfSameType()));
	}
	
	/**
	 * TODO for daisuke
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test29_RelativeComplementEnclosedOpen() throws Exception {
		Interval<Integer> o3_5o = Interval.open(3, 5);
		Interval<Integer> c1_7c = Interval.closed(1, 7);
		Interval<Integer> c1_3c = Interval.closed(1, 3);
		Interval<Integer> c5_7c = Interval.closed(5, 7);
		List<Interval<Integer>> complement = o3_5o.complementRelativeTo(c1_7c);
		assertThat(complement.size(), is(2));
		assertThat(complement.get(0), is(c1_3c));
		assertThat(complement.get(1), is(c5_7c));
	}
	
}
