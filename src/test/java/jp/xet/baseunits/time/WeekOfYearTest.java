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
package jp.xet.baseunits.time;

import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

/**
 * {@link WeekOfYear}のテストクラス。
 */
public class WeekOfYearTest {
	
	/**
	 * {@link WeekOfYear#equals(Object)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test01_equals() throws Exception {
		assertThat(WeekOfYear.MIN.equals(WeekOfYear.MIN), is(true));
		assertThat(WeekOfYear.MIN.equals(WeekOfYear.MAX), is(false));
		assertThat(WeekOfYear.valueOf(3).equals(WeekOfYear.valueOf(3)), is(true));
		assertThat(WeekOfYear.valueOf(3).equals(null), is(false));
		assertThat(WeekOfYear.valueOf(3).equals(new Object()), is(false));
	}
	
	/**
	 * {@link WeekOfYear#toString()}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test02_toString() throws Exception {
		assertThat(WeekOfYear.valueOf(3), hasToString("3"));
	}
	
	/**
	 * {@link WeekOfYear#isBefore(WeekOfYear)}, {@link WeekOfYear#isAfter(WeekOfYear)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test03_isAfter_isBefore() throws Exception {
		assertThat(WeekOfYear.valueOf(3).isAfter(WeekOfYear.MIN), is(true));
		assertThat(WeekOfYear.valueOf(3).isAfter(WeekOfYear.valueOf(3)), is(false));
		assertThat(WeekOfYear.valueOf(3).isAfter(WeekOfYear.valueOf(23)), is(false));
		assertThat(WeekOfYear.valueOf(3).isAfter(null), is(false));
		assertThat(WeekOfYear.valueOf(3).isBefore(WeekOfYear.MIN), is(false));
		assertThat(WeekOfYear.valueOf(3).isBefore(WeekOfYear.valueOf(3)), is(false));
		assertThat(WeekOfYear.valueOf(3).isBefore(WeekOfYear.valueOf(23)), is(true));
		assertThat(WeekOfYear.valueOf(3).isBefore(null), is(false));
	}
}
