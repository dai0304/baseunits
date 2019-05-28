/*
 * Copyright 2010-2019 Miyamoto Daisuke.
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
package jp.xet.baseunits.intervals;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.Test;

/**
 * {@link IntervalSequence}のテストクラス。
 */
public class IntervalSequenceTest {
	
	private Interval<Integer> c5_10c = Interval.closed(5, 10);
	
	private Interval<Integer> o10_12c = Interval.over(10, false, 12, true);
	
	private Interval<Integer> o11_20c = Interval.over(11, false, 20, true);
	
	private Interval<Integer> o12_20o = Interval.open(12, 20);
	
	@SuppressWarnings("unused")
	private Interval<Integer> c18_25c = Interval.closed(18, 25);
	
	private Interval<Integer> c20_25c = Interval.closed(20, 25);
	
	private Interval<Integer> o25_30c = Interval.over(25, false, 30, true);
	
	private Interval<Integer> o30_35o = Interval.open(30, 35);
	
	private Interval<Integer> o11_12c = Interval.over(11, false, 12, true);
	
	private Interval<Integer> c20_20c = Interval.closed(20, 20);
	
	private Interval<Integer> _o18 = Interval.under(18);
	
	private Interval<Integer> empty = Interval.closed(0, 0);
	
	private Interval<Integer> all = Interval.open(null, null);
	
	
	/**
	 * {@link IntervalSequence#iterator()}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test01_iterate() throws Exception {
		IntervalSequence<Integer> intervalSequence = new IntervalSequence<Integer>();
		assertThat(intervalSequence.isEmpty(), is(true));
		intervalSequence.add(empty);
		intervalSequence.add(c5_10c);
		intervalSequence.add(o10_12c);
		Iterator<Interval<Integer>> it = intervalSequence.iterator();
		assertThat(it.hasNext(), is(true));
		assertThat(it.next(), is(empty));
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
	public void test02_insertedOutOfOrder() throws Exception {
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
	 * 重なる区間を含んだ{@link IntervalSequence}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test03_overlapping() throws Exception {
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
	public void test04_intersections() throws Exception {
		IntervalSequence<Integer> intervalSequence = new IntervalSequence<Integer>();
		intervalSequence.add(o10_12c);
		
		assertThat(intervalSequence.intersections().isEmpty(), is(true));
		
		intervalSequence.add(c20_25c);
		
		assertThat(intervalSequence.intersections().isEmpty(), is(true));
		
		intervalSequence.add(o11_20c);
		
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
	 * {@link IntervalSequence#gaps()}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test05_gaps() throws Exception {
		IntervalSequence<Integer> intervalSequence = new IntervalSequence<Integer>();
		intervalSequence.add(c5_10c);
		
		assertThat(intervalSequence.gaps().isEmpty(), is(true));
		
		intervalSequence.add(o10_12c);
		intervalSequence.add(c20_25c);
		intervalSequence.add(o30_35o);
		
		Iterator<Interval<Integer>> it = intervalSequence.gaps().iterator();
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
	 * {@link IntervalSequence#extent()}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test06_extent() throws Exception {
		IntervalSequence<Integer> intervalSequence = new IntervalSequence<Integer>();
		intervalSequence.add(c5_10c);
		
		assertThat(intervalSequence.extent(), is(c5_10c));
		
		intervalSequence.add(o10_12c);
		intervalSequence.add(c20_25c);
		assertThat(intervalSequence.extent(), is(Interval.closed(5, 25)));
		
		intervalSequence.add(_o18);
		assertThat(intervalSequence.extent(), is(Interval.closed(null, 25)));
		
		intervalSequence.add(all);
		assertThat(intervalSequence.extent(), is(all));
		
		for (IntervalSequence<Integer> seq : variousSequences()) {
			seq.add(c5_10c);
			seq.add(o10_12c);
			seq.add(c20_25c);
			assertThat(seq.extent(), is(Interval.closed(5, 25)));
			
			seq.add(_o18);
			assertThat(seq.extent(), is(Interval.closed(null, 25)));
			
			seq.add(all);
			assertThat(seq.extent(), is(all));
		}
		
		intervalSequence.clear();
		try {
			intervalSequence.extent();
			fail();
		} catch (IllegalStateException e) {
			// success
		}
	}
	
	/**
	 * {@link IntervalSequence#toString()}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test07_toString() throws Exception {
		IntervalSequence<Integer> intervalSequence = new IntervalSequence<Integer>();
		intervalSequence.add(o10_12c);
		intervalSequence.add(c5_10c);
		
		assertThat(intervalSequence.toString(), is("[[5, 10], (10, 12]]"));
	}
	
	@SuppressWarnings("unchecked")
	private Iterable<IntervalSequence<Integer>> variousSequences() {
		return Arrays.asList(
				new IntervalSequence<Integer>(new IntervalComparatorUpperLower<Integer>(false, false)),
				new IntervalSequence<Integer>(new IntervalComparatorUpperLower<Integer>(false, true)),
				new IntervalSequence<Integer>(new IntervalComparatorUpperLower<Integer>(true, false)),
				new IntervalSequence<Integer>(new IntervalComparatorUpperLower<Integer>(true, true)),
				new IntervalSequence<Integer>(new IntervalComparatorLowerUpper<Integer>(false, false)),
				new IntervalSequence<Integer>(new IntervalComparatorLowerUpper<Integer>(false, true)),
				new IntervalSequence<Integer>(new IntervalComparatorLowerUpper<Integer>(true, false)),
				new IntervalSequence<Integer>(new IntervalComparatorLowerUpper<Integer>(true, true)),
				new IntervalSequence<Integer>()
			);
	}
}
