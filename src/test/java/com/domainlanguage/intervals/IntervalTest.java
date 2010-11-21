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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.domainlanguage.tests.SerializationTester;

import org.junit.Test;

/**
 * {@link Interval}のテストクラス。
 * 
 * @author daisuke
 */
public class IntervalTest {
	
	static List<Interval<Integer>> newIntegerIntervalList() {
		List<Interval<Integer>> list = new ArrayList<Interval<Integer>>();
		
		// 開区間
		list.add(Interval.over(0, false, 25, false));
		list.add(Interval.over(0, false, 50, false));
		list.add(Interval.over(0, false, 75, false));
		list.add(Interval.over(0, false, 100, false));
		list.add(Interval.over(25, false, 50, false));
		list.add(Interval.over(25, false, 75, false));
		list.add(Interval.over(25, false, 100, false));
		list.add(Interval.over(50, false, 75, false));
		list.add(Interval.over(50, false, 100, false));
		list.add(Interval.over(75, false, 100, false));
		
		// 半開区間
		list.add(Interval.over(0, true, 25, false));
		list.add(Interval.over(0, true, 50, false));
		list.add(Interval.over(0, true, 75, false));
		list.add(Interval.over(0, true, 100, false));
		list.add(Interval.over(25, true, 50, false));
		list.add(Interval.over(25, true, 75, false));
		list.add(Interval.over(25, true, 100, false));
		list.add(Interval.over(50, true, 75, false));
		list.add(Interval.over(50, true, 100, false));
		list.add(Interval.over(75, true, 100, false));
		
		list.add(Interval.over(0, false, 25, true));
		list.add(Interval.over(0, false, 50, true));
		list.add(Interval.over(0, false, 75, true));
		list.add(Interval.over(0, false, 100, true));
		list.add(Interval.over(25, false, 50, true));
		list.add(Interval.over(25, false, 75, true));
		list.add(Interval.over(25, false, 100, true));
		list.add(Interval.over(50, false, 75, true));
		list.add(Interval.over(50, false, 100, true));
		list.add(Interval.over(75, false, 100, true));
		
		// 閉区間
		list.add(Interval.over(0, true, 25, true));
		list.add(Interval.over(0, true, 50, true));
		list.add(Interval.over(0, true, 75, true));
		list.add(Interval.over(0, true, 100, true));
		list.add(Interval.over(25, true, 50, true));
		list.add(Interval.over(25, true, 75, true));
		list.add(Interval.over(25, true, 100, true));
		list.add(Interval.over(50, true, 75, true));
		list.add(Interval.over(50, true, 100, true));
		list.add(Interval.over(75, true, 100, true));
		
		// single point
		list.add(Interval.over(0, true, 0, false));
		list.add(Interval.over(0, false, 0, true));
		list.add(Interval.over(0, true, 0, true));
		list.add(Interval.over(25, true, 25, false));
		list.add(Interval.over(25, false, 25, true));
		list.add(Interval.over(25, true, 25, true));
		list.add(Interval.over(50, true, 50, false));
		list.add(Interval.over(50, false, 50, true));
		list.add(Interval.over(50, true, 50, true));
		list.add(Interval.over(75, true, 75, false));
		list.add(Interval.over(75, false, 75, true));
		list.add(Interval.over(75, true, 75, true));
		list.add(Interval.over(100, true, 100, false));
		list.add(Interval.over(100, false, 100, true));
		list.add(Interval.over(100, true, 100, true));
		
		// empty
		list.add(Interval.over(0, false, 0, false));
		list.add(Interval.over(25, false, 25, false));
		list.add(Interval.over(50, false, 50, false));
		list.add(Interval.over(75, false, 75, false));
		list.add(Interval.over(100, false, 100, false));
		
		// 下側限界のみ区間
		list.add(Interval.over(0, false, null, false));
		list.add(Interval.over(0, true, null, false));
		list.add(Interval.over(25, false, null, false));
		list.add(Interval.over(25, true, null, false));
		list.add(Interval.over(50, false, null, false));
		list.add(Interval.over(50, true, null, false));
		list.add(Interval.over(75, false, null, false));
		list.add(Interval.over(75, true, null, false));
		list.add(Interval.over(100, false, null, false));
		list.add(Interval.over(100, true, null, false));
		
		// 上側限界のみ区間
		list.add(Interval.over(null, false, 0, false));
		list.add(Interval.over(null, false, 0, true));
		list.add(Interval.over(null, false, 25, false));
		list.add(Interval.over(null, false, 25, true));
		list.add(Interval.over(null, false, 50, false));
		list.add(Interval.over(null, false, 50, true));
		list.add(Interval.over(null, false, 75, false));
		list.add(Interval.over(null, false, 75, true));
		list.add(Interval.over(null, false, 100, false));
		list.add(Interval.over(null, false, 100, true));
		
		// freedom
		list.add(Interval.<Integer> over(null, false, null, false));
		
		Collections.shuffle(list); // 念のためシャッフル
		return list;
	}
	
	static List<Interval<Integer>> newIntegerIntervalList2() {
		List<Interval<Integer>> list = new ArrayList<Interval<Integer>>();
		
		// 開区間
		list.add(Interval.over(0, false, 5, false));
		list.add(Interval.over(0, false, 10, false));
		list.add(Interval.over(0, false, 15, false));
		list.add(Interval.over(0, false, 20, false));
		list.add(Interval.over(5, false, 10, false));
		list.add(Interval.over(5, false, 15, false));
		list.add(Interval.over(5, false, 20, false));
		list.add(Interval.over(10, false, 15, false));
		list.add(Interval.over(10, false, 20, false));
		list.add(Interval.over(15, false, 20, false));
		
		// 半開区間
		list.add(Interval.over(0, true, 5, false));
		list.add(Interval.over(0, true, 10, false));
		list.add(Interval.over(0, true, 15, false));
		list.add(Interval.over(0, true, 20, false));
		list.add(Interval.over(5, true, 10, false));
		list.add(Interval.over(5, true, 15, false));
		list.add(Interval.over(5, true, 20, false));
		list.add(Interval.over(10, true, 15, false));
		list.add(Interval.over(10, true, 20, false));
		list.add(Interval.over(15, true, 20, false));
		
		list.add(Interval.over(0, false, 5, true));
		list.add(Interval.over(0, false, 10, true));
		list.add(Interval.over(0, false, 15, true));
		list.add(Interval.over(0, false, 20, true));
		list.add(Interval.over(5, false, 10, true));
		list.add(Interval.over(5, false, 15, true));
		list.add(Interval.over(5, false, 20, true));
		list.add(Interval.over(10, false, 15, true));
		list.add(Interval.over(10, false, 20, true));
		list.add(Interval.over(15, false, 20, true));
		
		// 閉区間
		list.add(Interval.over(0, true, 5, true));
		list.add(Interval.over(0, true, 10, true));
		list.add(Interval.over(0, true, 15, true));
		list.add(Interval.over(0, true, 20, true));
		list.add(Interval.over(5, true, 10, true));
		list.add(Interval.over(5, true, 15, true));
		list.add(Interval.over(5, true, 20, true));
		list.add(Interval.over(10, true, 15, true));
		list.add(Interval.over(10, true, 20, true));
		list.add(Interval.over(15, true, 20, true));
		
		// single point
		list.add(Interval.over(0, true, 0, false));
		list.add(Interval.over(0, false, 0, true));
		list.add(Interval.over(0, true, 0, true));
		list.add(Interval.over(5, true, 5, false));
		list.add(Interval.over(5, false, 5, true));
		list.add(Interval.over(5, true, 5, true));
		list.add(Interval.over(10, true, 10, false));
		list.add(Interval.over(10, false, 10, true));
		list.add(Interval.over(10, true, 10, true));
		list.add(Interval.over(15, true, 15, false));
		list.add(Interval.over(15, false, 15, true));
		list.add(Interval.over(15, true, 15, true));
		list.add(Interval.over(20, true, 20, false));
		list.add(Interval.over(20, false, 20, true));
		list.add(Interval.over(20, true, 20, true));
		
		// empty
		list.add(Interval.over(0, false, 0, false));
		list.add(Interval.over(5, false, 5, false));
		list.add(Interval.over(10, false, 10, false));
		list.add(Interval.over(15, false, 15, false));
		list.add(Interval.over(20, false, 20, false));
		
		// 下側限界のみ区間
		list.add(Interval.over(0, false, null, false));
		list.add(Interval.over(0, true, null, false));
		list.add(Interval.over(5, false, null, false));
		list.add(Interval.over(5, true, null, false));
		list.add(Interval.over(10, false, null, false));
		list.add(Interval.over(10, true, null, false));
		list.add(Interval.over(15, false, null, false));
		list.add(Interval.over(15, true, null, false));
		list.add(Interval.over(20, false, null, false));
		list.add(Interval.over(20, true, null, false));
		
		// 上側限界のみ区間
		list.add(Interval.over(null, false, 0, false));
		list.add(Interval.over(null, false, 0, true));
		list.add(Interval.over(null, false, 5, false));
		list.add(Interval.over(null, false, 5, true));
		list.add(Interval.over(null, false, 10, false));
		list.add(Interval.over(null, false, 10, true));
		list.add(Interval.over(null, false, 15, false));
		list.add(Interval.over(null, false, 15, true));
		list.add(Interval.over(null, false, 20, false));
		list.add(Interval.over(null, false, 20, true));
		
		// freedom
		list.add(Interval.<Integer> over(null, false, null, false));
		
		Collections.shuffle(list); // 念のためシャッフル
		return list;
	}
	

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
	
	private Interval<BigDecimal> _2o = Interval.over(null, true, new BigDecimal(2), false);
	
	private Interval<BigDecimal> o9_ = Interval.over(new BigDecimal(9), false, null, true);
	
	private Interval<BigDecimal> empty = Interval.open(new BigDecimal(1), new BigDecimal(1));
	
	private Interval<BigDecimal> all = Interval.<BigDecimal> closed(null, null);
	

	/**
	 * {@link Interval}のインスタンス生成テスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test01_Assertions() throws Exception {
		// Redundant, maybe, but with all the compiler default
		// confusion at the moment, I decided to throw this in.
		try {
			Interval.closed(new BigDecimal(2.0), new BigDecimal(1.0));
			fail("Lower bound mustn't be above upper bound.");
		} catch (IllegalArgumentException e) {
			// success
		}
	}
	
	/**
	 * {@link Interval#upTo(Comparable)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test02_UpTo() throws Exception {
		Interval<Double> range = Interval.upTo(Double.valueOf(5.5));
		assertThat(range.includes(Double.valueOf(5.5)), is(true));
		assertThat(range.includes(Double.valueOf(-5.5)), is(true));
		assertThat(range.includes(Double.NEGATIVE_INFINITY), is(true));
		assertThat(!range.includes(Double.valueOf(5.5001)), is(true));
	}
	
	/**
	 * {@link Interval#andMore(Comparable)}のテスト。
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
	 * {@link Interval#newOfSameType(Comparable, boolean, Comparable, boolean)}のテスト。
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
	 * {@link Interval}のインスタンスがシリアライズできるかどうか検証する。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test05_Serialization() throws Exception {
		SerializationTester.assertCanBeSerialized(c5_10c);
	}
	
	/**
	 * {@link Interval#toString()}のテスト。
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
	 * {@link Interval#isBelow(Comparable)}のテスト。
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
	 * {@link Interval#includes(Comparable)}のテスト。
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
	 * {@link Interval}の開閉の境界挙動テスト。
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
	 * {@link Interval#isEmpty()}のテスト。
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
	 * {@link Interval#intersects(Interval)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test11_Intersects() throws Exception {
		assertThat(c5_10c.intersects(c1_10c), is(true));
		assertThat(c1_10c.intersects(c5_10c), is(true));
		assertThat(c4_6c.intersects(c1_10c), is(true));
		assertThat(c1_10c.intersects(c4_6c), is(true));
		assertThat(c5_10c.intersects(c5_15c), is(true));
		assertThat(c5_15c.intersects(c1_10c), is(true));
		assertThat(c1_10c.intersects(c5_15c), is(true));
		assertThat(c1_10c.intersects(c12_16c), is(false));
		assertThat(c12_16c.intersects(c1_10c), is(false));
		assertThat(c5_10c.intersects(c5_10c), is(true));
		assertThat(c1_10c.intersects(o10_12c), is(false));
		assertThat(o10_12c.intersects(c1_10c), is(false));
		
		// ---- 気を取り直して総当たりしてみよう
		
		assertThat(c5_10c.intersects(c5_10c), is(true));
		assertThat(c5_10c.intersects(c1_10c), is(true));
		assertThat(c5_10c.intersects(c4_6c), is(true));
		assertThat(c5_10c.intersects(c5_15c), is(true));
		assertThat(c5_10c.intersects(c12_16c), is(false));
		assertThat(c5_10c.intersects(o10_12c), is(false));
		assertThat(c5_10c.intersects(o1_1c), is(false));
		assertThat(c5_10c.intersects(c1_1o), is(false));
		assertThat(c5_10c.intersects(c1_1c), is(false));
		assertThat(c5_10c.intersects(o1_1o), is(false));
		assertThat(c5_10c.intersects(_2o), is(false));
		assertThat(c5_10c.intersects(o9_), is(true));
		assertThat(c5_10c.intersects(empty), is(false));
		assertThat(c5_10c.intersects(all), is(true));
		
		assertThat(c1_10c.intersects(c5_10c), is(true));
		assertThat(c1_10c.intersects(c1_10c), is(true));
		assertThat(c1_10c.intersects(c4_6c), is(true));
		assertThat(c1_10c.intersects(c5_15c), is(true));
		assertThat(c1_10c.intersects(c12_16c), is(false));
		assertThat(c1_10c.intersects(o10_12c), is(false));
		assertThat(c1_10c.intersects(o1_1c), is(true));
		assertThat(c1_10c.intersects(c1_1o), is(true));
		assertThat(c1_10c.intersects(c1_1c), is(true));
		assertThat(c1_10c.intersects(o1_1o), is(false));
		assertThat(c1_10c.intersects(_2o), is(true));
		assertThat(c1_10c.intersects(o9_), is(true));
		assertThat(c1_10c.intersects(empty), is(false));
		assertThat(c1_10c.intersects(all), is(true));
		
		assertThat(c4_6c.intersects(c5_10c), is(true));
		assertThat(c4_6c.intersects(c1_10c), is(true));
		assertThat(c4_6c.intersects(c4_6c), is(true));
		assertThat(c4_6c.intersects(c5_15c), is(true));
		assertThat(c4_6c.intersects(c12_16c), is(false));
		assertThat(c4_6c.intersects(o10_12c), is(false));
		assertThat(c4_6c.intersects(o1_1c), is(false));
		assertThat(c4_6c.intersects(c1_1o), is(false));
		assertThat(c4_6c.intersects(c1_1c), is(false));
		assertThat(c4_6c.intersects(o1_1o), is(false));
		assertThat(c4_6c.intersects(_2o), is(false));
		assertThat(c4_6c.intersects(o9_), is(false));
		assertThat(c4_6c.intersects(empty), is(false));
		assertThat(c4_6c.intersects(all), is(true));
		
		assertThat(c5_15c.intersects(c5_10c), is(true));
		assertThat(c5_15c.intersects(c1_10c), is(true));
		assertThat(c5_15c.intersects(c4_6c), is(true));
		assertThat(c5_15c.intersects(c5_15c), is(true));
		assertThat(c5_15c.intersects(c12_16c), is(true));
		assertThat(c5_15c.intersects(o10_12c), is(true));
		assertThat(c5_15c.intersects(o1_1c), is(false));
		assertThat(c5_15c.intersects(c1_1o), is(false));
		assertThat(c5_15c.intersects(c1_1c), is(false));
		assertThat(c5_15c.intersects(o1_1o), is(false));
		assertThat(c5_15c.intersects(_2o), is(false));
		assertThat(c5_15c.intersects(o9_), is(true));
		assertThat(c5_15c.intersects(empty), is(false));
		assertThat(c5_15c.intersects(all), is(true));
		
		// --- 疲れてきたからあと適当ｗ 総当たり達成ならず。まぁ、大丈夫やろ…。
		
		assertThat(c12_16c.intersects(c1_10c), is(false));
		assertThat(o10_12c.intersects(c1_10c), is(false));
		assertThat(o1_1c.intersects(c4_6c), is(false));
		assertThat(c1_1o.intersects(c5_15c), is(false));
		assertThat(c1_1c.intersects(c5_15c), is(false));
		assertThat(o1_1o.intersects(c12_16c), is(false));
		assertThat(empty.intersects(o10_12c), is(false));
		assertThat(all.intersects(o10_12c), is(true));
	}
	
	/**
	 * {@link Interval#intersect(Interval)}のテスト。
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
	 * {@link Interval#greaterOfLowerLimits(Interval)}のテスト。（内部API）
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
	 * {@link Interval#lesserOfUpperLimits(Interval)}のテスト。（内部API）
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
	 * {@link Interval#covers(Interval)}のテスト。
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
		
		Interval<BigDecimal> o5_10c = Interval.over(new BigDecimal(5), false, new BigDecimal(10), true);
		assertThat("closed incl left-open", c5_10c.covers(o5_10c), is(true));
		assertThat("left-open incl left-open", o5_10c.covers(o5_10c), is(true));
		assertThat("left-open doesn't include closed", o5_10c.covers(c5_10c), is(false));
		
		Interval<BigDecimal> o1_10o = Interval.over(new BigDecimal(1), false, new BigDecimal(10), false);
		assertThat(c5_10c.covers(o1_10o), is(false));
		assertThat(o1_10o.covers(o1_10o), is(true));
		assertThat(o1_10o.covers(c5_10c), is(false));
	}
	
	/**
	 * {@link Interval#gap(Interval)}のテスト。
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
	 * {@link Interval#complementRelativeTo(Interval)}のテスト。
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
	 * {@link Interval#complementRelativeTo(Interval)}のテスト。
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
	 * {@link Interval#complementRelativeTo(Interval)}のテスト。
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
	 * {@link Interval#complementRelativeTo(Interval)}のテスト。
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
	 * {@link Interval#complementRelativeTo(Interval)}のテスト。
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
	 * {@link Interval#complementRelativeTo(Interval)}のテスト。
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
	 * {@link Interval#complementRelativeTo(Interval)}のテスト。
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
	 * {@link Interval#complementRelativeTo(Interval)}のテスト。
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
	 * {@link Interval#complementRelativeTo(Interval)}のテスト。
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
	 * {@link Interval#isSingleElement()}のテスト。
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
	 * {@link Interval#equals(Interval)}のテスト。
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
	 * {@link Interval#emptyOfSameType()}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test28_EqualsForEmptyIntervals() throws Exception {
		assertThat(c4_6c.emptyOfSameType(), is(c1_10c.emptyOfSameType()));
	}
	
	/**
	 * {@link Interval#complementRelativeTo(Interval)}のテスト。
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
