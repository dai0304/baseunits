/*
 * Copyright 2010 TRICREO, Inc. (http://tricreo.jp/)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 * ----
 * Copyright (c) 200X Domain Language, Inc. (http://domainlanguage.com) This
 * free software is distributed under the "MIT" licence.
 * For more information, see http://timeandmoney.sourceforge.net.
 */
package jp.tricreo.basicunits.intervals;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import jp.tricreo.basicunits.intervals.IntervalLimit;

import org.junit.Test;

/**
 * {@link IntervalLimit}のテストクラス。
 */
public class IntervalLimitTest {
	
	/**
	 * {@link IntervalLimit#equals(Object)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	@SuppressWarnings("serial")
	public void test01_Equals() throws Exception {
		assertThat(IntervalLimit.lower(false, 10).equals(IntervalLimit.lower(false, 10)), is(true));
		assertThat(IntervalLimit.lower(true, 10).equals(IntervalLimit.lower(false, 10)), is(false));
		assertThat(IntervalLimit.lower(false, 10).equals(IntervalLimit.lower(true, 10)), is(false));
		assertThat(IntervalLimit.lower(true, 10).equals(IntervalLimit.lower(true, 10)), is(true));
		
		assertThat(IntervalLimit.upper(false, 10).equals(IntervalLimit.upper(false, 10)), is(true));
		assertThat(IntervalLimit.upper(true, 10).equals(IntervalLimit.upper(false, 10)), is(false));
		assertThat(IntervalLimit.upper(false, 10).equals(IntervalLimit.upper(true, 10)), is(false));
		assertThat(IntervalLimit.upper(true, 10).equals(IntervalLimit.upper(true, 10)), is(true));
		
		assertThat(IntervalLimit.lower(false, 10).equals(IntervalLimit.upper(false, 10)), is(false));
		assertThat(IntervalLimit.lower(true, 10).equals(IntervalLimit.upper(false, 10)), is(false));
		assertThat(IntervalLimit.lower(false, 10).equals(IntervalLimit.upper(true, 10)), is(false));
		assertThat(IntervalLimit.lower(true, 10).equals(IntervalLimit.upper(true, 10)), is(false));
		
		assertThat(IntervalLimit.upper(false, 10).equals(IntervalLimit.lower(false, 10)), is(false));
		assertThat(IntervalLimit.upper(true, 10).equals(IntervalLimit.lower(false, 10)), is(false));
		assertThat(IntervalLimit.upper(false, 10).equals(IntervalLimit.lower(true, 10)), is(false));
		assertThat(IntervalLimit.upper(true, 10).equals(IntervalLimit.lower(true, 10)), is(false));
		
		IntervalLimit<Integer> lowerOpen10 = IntervalLimit.lower(false, 10);
		assertThat(lowerOpen10.equals(lowerOpen10), is(true));
		assertThat(lowerOpen10.equals(null), is(false));
		assertThat(new IntervalLimit<Integer>(false, true, 10).equals(lowerOpen10), is(true));
		assertThat(new IntervalLimit<Integer>(false, true, 10) {
		}.equals(lowerOpen10), is(false));
	}
	
	/**
	 * {@link IntervalLimit#compareTo(IntervalLimit)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test02_compareTo() throws Exception {
		IntervalLimit<Integer> lowerInf = IntervalLimit.<Integer> lower(false, null);
		IntervalLimit<Integer> upperInf = IntervalLimit.<Integer> upper(false, null);
		IntervalLimit<Integer> lowerOpen2 = IntervalLimit.lower(false, 2);
		IntervalLimit<Integer> lowerClose2 = IntervalLimit.lower(true, 2);
		IntervalLimit<Integer> lowerOpen3 = IntervalLimit.lower(false, 3);
		IntervalLimit<Integer> lowerClose3 = IntervalLimit.lower(true, 3);
		IntervalLimit<Integer> upperOpen5 = IntervalLimit.upper(false, 5);
		IntervalLimit<Integer> upperClose5 = IntervalLimit.upper(true, 5);
		IntervalLimit<Integer> upperOpen6 = IntervalLimit.upper(false, 6);
		IntervalLimit<Integer> upperClose6 = IntervalLimit.upper(true, 6);
		
		// 無限同士比較
		assertThat(lowerInf.compareTo(upperInf), is(lessThan(0)));
		assertThat(upperInf.compareTo(lowerInf), is(greaterThan(0)));
		
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
		assertThat(lowerClose2.compareTo(lowerOpen2), is(lessThan(0)));
		assertThat(lowerClose2.compareTo(lowerClose3), is(lessThan(0)));
		assertThat(lowerClose2.compareTo(lowerOpen3), is(lessThan(0)));
		assertThat(lowerClose2.compareTo(upperClose5), is(lessThan(0)));
		assertThat(lowerClose2.compareTo(upperOpen5), is(lessThan(0)));
		assertThat(lowerClose2.compareTo(upperClose6), is(lessThan(0)));
		assertThat(lowerClose2.compareTo(upperOpen6), is(lessThan(0)));
		
		assertThat(lowerOpen2.compareTo(lowerClose2), is(greaterThan(0)));
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
		assertThat(lowerClose3.compareTo(lowerOpen3), is(lessThan(0)));
		assertThat(lowerClose3.compareTo(upperClose5), is(lessThan(0)));
		assertThat(lowerClose3.compareTo(upperOpen5), is(lessThan(0)));
		assertThat(lowerClose3.compareTo(upperClose6), is(lessThan(0)));
		assertThat(lowerClose3.compareTo(upperOpen6), is(lessThan(0)));
		
		assertThat(lowerOpen3.compareTo(lowerClose2), is(greaterThan(0)));
		assertThat(lowerOpen3.compareTo(lowerOpen2), is(greaterThan(0)));
		assertThat(lowerOpen3.compareTo(lowerClose3), is(greaterThan(0)));
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
		assertThat(upperClose5.compareTo(upperOpen5), is(greaterThan(0)));
		assertThat(upperClose5.compareTo(upperClose6), is(lessThan(0)));
		assertThat(upperClose5.compareTo(upperOpen6), is(lessThan(0)));
		
		assertThat(upperOpen5.compareTo(lowerClose2), is(greaterThan(0)));
		assertThat(upperOpen5.compareTo(lowerOpen2), is(greaterThan(0)));
		assertThat(upperOpen5.compareTo(lowerClose3), is(greaterThan(0)));
		assertThat(upperOpen5.compareTo(lowerOpen3), is(greaterThan(0)));
		assertThat(upperOpen5.compareTo(upperClose5), is(lessThan(0)));
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
		assertThat(upperClose6.compareTo(upperOpen6), is(greaterThan(0)));
		
		assertThat(upperOpen6.compareTo(lowerClose2), is(greaterThan(0)));
		assertThat(upperOpen6.compareTo(lowerOpen2), is(greaterThan(0)));
		assertThat(upperOpen6.compareTo(lowerClose3), is(greaterThan(0)));
		assertThat(upperOpen6.compareTo(lowerOpen3), is(greaterThan(0)));
		assertThat(upperOpen6.compareTo(upperClose5), is(greaterThan(0)));
		assertThat(upperOpen6.compareTo(upperOpen5), is(greaterThan(0)));
		assertThat(upperOpen6.compareTo(upperClose6), is(lessThan(0)));
		assertThat(upperOpen6.compareTo(upperOpen6), is(0));
		
		try {
			lowerInf.compareTo(null);
			fail();
		} catch (NullPointerException e) {
			// success
		}
	}
	
	/**
	 * {@link IntervalLimit#compareTo(IntervalLimit)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test03_sort() throws Exception {
		List<IntervalLimit<Integer>> list = new ArrayList<IntervalLimit<Integer>>();
		list.add(IntervalLimit.<Integer> upper(false, null));
		list.add(IntervalLimit.<Integer> upper(true, null));
		list.add(IntervalLimit.<Integer> lower(false, null));
		list.add(IntervalLimit.<Integer> lower(true, null));
		list.add(IntervalLimit.lower(true, 1));
		list.add(IntervalLimit.lower(false, 1));
		list.add(IntervalLimit.lower(true, 5));
		list.add(IntervalLimit.lower(false, 5));
		list.add(IntervalLimit.upper(true, 1));
		list.add(IntervalLimit.upper(false, 1));
		list.add(IntervalLimit.upper(true, 5));
		list.add(IntervalLimit.upper(false, 5));
		
		Collections.shuffle(list);
		Collections.sort(list);
		
//		for (IntervalLimit<Integer> intervalLimit : list) {
//			System.out.println(intervalLimit);
//		}
		
		assertThat(list.get(0), is(IntervalLimit.<Integer> lower(false, null)));
		assertThat(list.get(1), is(IntervalLimit.<Integer> lower(false, null))); // 閉じた無限限界は開いた無限限界に変換される
		
		assertThat(list.get(2), is(IntervalLimit.lower(true, 1)));
		assertThat(list.get(3), is(IntervalLimit.lower(false, 1)));
		assertThat(list.get(4), is(IntervalLimit.upper(false, 1)));
		assertThat(list.get(5), is(IntervalLimit.upper(true, 1)));
		assertThat(list.get(6), is(IntervalLimit.lower(true, 5)));
		assertThat(list.get(7), is(IntervalLimit.lower(false, 5)));
		assertThat(list.get(8), is(IntervalLimit.upper(false, 5)));
		assertThat(list.get(9), is(IntervalLimit.upper(true, 5)));
		
		assertThat(list.get(10), is(IntervalLimit.<Integer> upper(false, null)));
		assertThat(list.get(11), is(IntervalLimit.<Integer> upper(false, null))); // 閉じた無限限界は開いた無限限界に変換される
	}
}
