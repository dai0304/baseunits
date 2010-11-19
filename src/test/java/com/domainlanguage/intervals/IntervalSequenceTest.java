/**
 * Copyright (c) 2004 Domain Language, Inc. (http://domainlanguage.com) This
 * free software is distributed under the "MIT" licence. See file licence.txt.
 * For more information, see http://timeandmoney.sourceforge.net.
 */

package com.domainlanguage.intervals;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.Test;

/**
 * {@link IntervalSequence}のテストクラス。
 * 
 * @author daisuke
 */
public class IntervalSequenceTest {
	
	private Interval<Integer> c5_10c = Interval.closed(5, 10);
	
	private Interval<Integer> o10_12c = Interval.over(10, false, 12, true);
	
	private Interval<Integer> o11_20c = Interval.over(11, false, 20, true);
	
	private Interval<Integer> o12_20o = Interval.open(12, 20);
	
	private Interval<Integer> c20_25c = Interval.closed(20, 25);
	
	private Interval<Integer> o25_30c = Interval.over(25, false, 30, true);
	
	private Interval<Integer> o30_35o = Interval.open(30, 35);
	
	private Interval<Integer> o11_12c = Interval.over(11, false, 12, true);
	
	private Interval<Integer> c20_20c = Interval.closed(20, 20);
	

	/**
	 * {@link IntervalSequence#iterator()}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test01_Iterate() throws Exception {
		IntervalSequence<Integer> intervalSequence = new IntervalSequence<Integer>();
		assertThat(intervalSequence.isEmpty(), is(true));
		intervalSequence.add(c5_10c);
		intervalSequence.add(o10_12c);
		Iterator<Interval<Integer>> it = intervalSequence.iterator();
		assertThat(it.hasNext(), is(true));
		assertThat(it.next(), is(c5_10c));
		assertThat(it.hasNext(), is(true));
		assertThat(it.next(), is(o10_12c));
		assertThat(it.hasNext(), is(false));
		try {
			it.next();
			fail("Should throw NoSuchElementException");
		} catch (NoSuchElementException e) {
			// success
		}
	}
	
	/**
	 * {@link IntervalSequence#add(Interval)}が順不同で行われた場合の{@link IntervalSequence}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test02_InsertedOutOfOrder() throws Exception {
		IntervalSequence<Integer> intervalSequence = new IntervalSequence<Integer>();
		intervalSequence.add(o10_12c);
		intervalSequence.add(c5_10c);
		//Iterator behavior should be the same regardless of order of insertion.
		Iterator<Interval<Integer>> it = intervalSequence.iterator();
		assertThat(it.hasNext(), is(true));
		assertThat(it.next(), is(c5_10c));
		assertThat(it.hasNext(), is(true));
		assertThat(it.next(), is(o10_12c));
		assertThat(it.hasNext(), is(false));
		try {
			it.next();
			fail("Should throw NoSuchElementException");
		} catch (NoSuchElementException e) {
			// success
		}
	}
	
	/**
	 * {@link IntervalSequence#gaps()}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test03_Gaps() throws Exception {
		IntervalSequence<Integer> intervalSequence = new IntervalSequence<Integer>();
		intervalSequence.add(c5_10c);
		intervalSequence.add(o10_12c);
		intervalSequence.add(c20_25c);
		intervalSequence.add(o30_35o);
		IntervalSequence<Integer> gaps = intervalSequence.gaps();
		Iterator<Interval<Integer>> it = gaps.iterator();
		assertThat(it.hasNext(), is(true));
		assertThat(it.next(), is(o12_20o));
		assertThat(it.hasNext(), is(true));
		assertThat(it.next(), is(o25_30c));
		assertThat(it.hasNext(), is(false));
		try {
			it.next();
			fail("Should throw NoSuchElementException");
		} catch (NoSuchElementException e) {
			// success
		}
	}
	
	/**
	 * 重なる区間を含んだ{@link IntervalSequence}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test04_Overlapping() throws Exception {
		IntervalSequence<Integer> intervalSequence = new IntervalSequence<Integer>();
		intervalSequence.add(o10_12c);
		intervalSequence.add(o11_20c);
		Iterator<Interval<Integer>> it = intervalSequence.iterator();
		assertThat(it.hasNext(), is(true));
		assertThat(it.next(), is(o10_12c));
		assertThat(it.hasNext(), is(true));
		assertThat(it.next(), is(o11_20c));
		assertThat(it.hasNext(), is(false));
		try {
			it.next();
			fail("Should throw NoSuchElementException");
		} catch (NoSuchElementException e) {
			// success
		}
	}
	
	/**
	 * {@link IntervalSequence#intersections()}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test05_Intersections() throws Exception {
		IntervalSequence<Integer> intervalSequence = new IntervalSequence<Integer>();
		intervalSequence.add(o10_12c);
		intervalSequence.add(o11_20c);
		intervalSequence.add(c20_25c);
		
		Iterator<Interval<Integer>> it = intervalSequence.intersections().iterator();
		assertThat(it.hasNext(), is(true));
		assertThat(it.next(), is(o11_12c));
		assertThat(it.hasNext(), is(true));
		assertThat(it.next(), is(c20_20c));
		assertThat(it.hasNext(), is(false));
		try {
			it.next();
			fail("Should throw NoSuchElementException");
		} catch (NoSuchElementException e) {
			// success
		}
	}
	
	/**
	 * {@link IntervalSequence#extent()}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test06_Extent() throws Exception {
		IntervalSequence<Integer> intervalSequence = new IntervalSequence<Integer>();
		intervalSequence.add(c5_10c);
		intervalSequence.add(o10_12c);
		intervalSequence.add(c20_25c);
		assertThat(intervalSequence.extent(), is(Interval.closed(5, 25)));
	}
}
