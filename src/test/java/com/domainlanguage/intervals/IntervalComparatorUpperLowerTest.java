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

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.SystemUtils;
import org.junit.Test;

/**
 * {@link IntervalComparatorUpperLower}のテストクラス。
 * 
 * @version $Id$
 * @author daisuke
 */
public class IntervalComparatorUpperLowerTest {
	
	/**
	 * {@link Interval#compareTo(Interval)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test01_compare() throws Exception {
		List<Interval<Integer>> list = IntervalTest.newIntegerIntervalList2();
		Collections.sort(list, new IntervalComparatorUpperLower<Integer>());
		
		StringBuilder sb = new StringBuilder();
		for (Interval<Integer> interval : list) {
			sb.append(interval.toStringGraphically()).append(SystemUtils.LINE_SEPARATOR);
		}
		
		System.out.println("{upper -> lower * -1}");
		System.out.println("           1         2         3");
		System.out.println(" 0123456789012345678901234567890");
		System.out.println(sb);
		
		// TODO assertion
	}
	
}
