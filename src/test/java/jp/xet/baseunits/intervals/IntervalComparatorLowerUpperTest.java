/*
 * Copyright 2010-2018 Miyamoto Daisuke.
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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.fail;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.junit.Test;

/**
 * {@link IntervalComparatorLowerUpper}のテストクラス。
 */
public class IntervalComparatorLowerUpperTest {
	
	/**
	 * {@link IntervalComparatorLowerUpper#compare(Interval, Interval)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test01_compare() throws Exception {
		List<Interval<Integer>> list = IntervalTest.newIntegerIntervalList2();
		Collections.sort(list, new IntervalComparatorLowerUpper<Integer>(false, true));
		
		assertThat(list.get(0), is(Interval.<Integer> over(null, false, null, false)));
		assertThat(list.get(1), is(Interval.over(null, false, 20, true)));
		assertThat(list.get(2), is(Interval.over(null, false, 20, false)));
		// ...
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void test02_NPE() {
		Comparator<Interval<Integer>> c = new IntervalComparatorLowerUpper<Integer>(false, true);
		try {
			c.compare(null, Interval.singleElement(10));
			fail();
		} catch (NullPointerException e) {
			// success
		}
	}
}
