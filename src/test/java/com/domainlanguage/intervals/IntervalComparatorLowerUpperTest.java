/*
 * Copyright 2010 Daisuke Miyamoto.
 * Created on 2010/11/20
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
 */
package com.domainlanguage.intervals;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.SystemUtils;
import org.junit.Test;

/**
 * TODO for daisuke
 * 
 * @since TODO for daisuke
 * @version $Id$
 * @author daisuke
 */
public class IntervalComparatorLowerUpperTest {
	
	/**
	 * {@link Interval#compareTo(Interval)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test01_compare() throws Exception {
		// 自然ソート順
		List<Interval<Integer>> list1 = IntervalTest.newIntegerIntervalList();
		Collections.sort(list1);
		
		StringBuilder sb1 = new StringBuilder();
		for (Interval<Integer> interval : list1) {
			sb1.append(interval.toStringGraphically()).append(SystemUtils.LINE_SEPARATOR);
		}
		
		// IntervalComparatorLowerUpperのソート順
		List<Interval<Integer>> list2 = IntervalTest.newIntegerIntervalList();
		Collections.sort(list2, new IntervalComparatorLowerUpper<Integer>());
		
		StringBuilder sb2 = new StringBuilder();
		for (Interval<Integer> interval : list2) {
			sb2.append(interval.toStringGraphically()).append(SystemUtils.LINE_SEPARATOR);
		}
		
		assertThat(sb2.toString(), is(not(sb1.toString())));
		System.out.println(sb2);
	}
	
}
