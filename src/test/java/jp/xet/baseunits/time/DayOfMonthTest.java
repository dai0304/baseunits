/*
 * Copyright 2011 Daisuke Miyamoto. (http://d.hatena.ne.jp/daisuke-m)
 * Copyright 2010-2011 TRICREO, Inc. (http://tricreo.jp/)
 * Created on 2010/11/25
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
package jp.xet.baseunits.time;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import jp.xet.baseunits.time.DayOfMonth;

import org.junit.Test;

/**
 * {@link DayOfMonth}のテストクラス。
 */
public class DayOfMonthTest {
	
	/**
	 * インスタンス生成テスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test01_create() throws Exception {
		for (int i = 1; i <= 31; i++) {
			DayOfMonth m = DayOfMonth.valueOf(i);
			assertThat(m.breachEncapsulationOfValue(), is(i));
		}
		
		try {
			DayOfMonth.valueOf(0);
			fail();
		} catch (IllegalArgumentException e) {
			// success
		}
		try {
			DayOfMonth.valueOf(-1);
			fail();
		} catch (IllegalArgumentException e) {
			// success
		}
		try {
			DayOfMonth.valueOf(32);
			fail();
		} catch (IllegalArgumentException e) {
			// success
		}
	}
	
	/**
	 * インスタンス生成テスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test02_create2() throws Exception {
		for (int i = 1; i <= 31; i++) {
			DayOfMonth.valueOf(i);
		}
		try {
			DayOfMonth.valueOf(0);
			fail();
		} catch (IllegalArgumentException e) {
			// success
		}
		try {
			DayOfMonth.valueOf(-1);
			fail();
		} catch (IllegalArgumentException e) {
			// success
		}
		try {
			DayOfMonth.valueOf(32);
			fail();
		} catch (IllegalArgumentException e) {
			// success
		}
	}
	
	/**
	 * {@link DayOfMonth#isBefore(DayOfMonth)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test03_isBefore() throws Exception {
		assertThat(DayOfMonth.valueOf(1).isBefore(DayOfMonth.valueOf(1)), is(false));
		assertThat(DayOfMonth.valueOf(1).isBefore(DayOfMonth.valueOf(2)), is(true));
		
		assertThat(DayOfMonth.valueOf(2).isBefore(DayOfMonth.valueOf(1)), is(false));
		assertThat(DayOfMonth.valueOf(2).isBefore(DayOfMonth.valueOf(2)), is(false));
		assertThat(DayOfMonth.valueOf(2).isBefore(DayOfMonth.valueOf(3)), is(true));
		
		assertThat(DayOfMonth.valueOf(3).isBefore(DayOfMonth.valueOf(2)), is(false));
		assertThat(DayOfMonth.valueOf(3).isBefore(DayOfMonth.valueOf(3)), is(false));
		assertThat(DayOfMonth.valueOf(3).isBefore(DayOfMonth.valueOf(4)), is(true));
		
		assertThat(DayOfMonth.valueOf(12).isBefore(DayOfMonth.valueOf(11)), is(false));
		assertThat(DayOfMonth.valueOf(12).isBefore(DayOfMonth.valueOf(12)), is(false));
		
		assertThat(DayOfMonth.valueOf(12).isBefore(null), is(false));
	}
	
	/**
	 * {@link DayOfMonth#isAfter(DayOfMonth)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test04_isAfter() throws Exception {
		assertThat(DayOfMonth.valueOf(1).isAfter(DayOfMonth.valueOf(1)), is(false));
		assertThat(DayOfMonth.valueOf(1).isAfter(DayOfMonth.valueOf(2)), is(false));
		
		assertThat(DayOfMonth.valueOf(2).isAfter(DayOfMonth.valueOf(1)), is(true));
		assertThat(DayOfMonth.valueOf(2).isAfter(DayOfMonth.valueOf(2)), is(false));
		assertThat(DayOfMonth.valueOf(2).isAfter(DayOfMonth.valueOf(3)), is(false));
		
		assertThat(DayOfMonth.valueOf(3).isAfter(DayOfMonth.valueOf(2)), is(true));
		assertThat(DayOfMonth.valueOf(3).isAfter(DayOfMonth.valueOf(3)), is(false));
		assertThat(DayOfMonth.valueOf(3).isAfter(DayOfMonth.valueOf(4)), is(false));
		
		assertThat(DayOfMonth.valueOf(12).isAfter(DayOfMonth.valueOf(11)), is(true));
		assertThat(DayOfMonth.valueOf(12).isAfter(DayOfMonth.valueOf(12)), is(false));
		
		assertThat(DayOfMonth.valueOf(12).isAfter(null), is(false));
	}
	
	/**
	 * {@link DayOfMonth#compareTo(DayOfMonth)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test05_compareTo() throws Exception {
		assertThat(DayOfMonth.valueOf(10).compareTo(DayOfMonth.valueOf(10)), is(0));
		assertThat(DayOfMonth.valueOf(10).compareTo(DayOfMonth.valueOf(11)), is(lessThan(0)));
		assertThat(DayOfMonth.valueOf(10).compareTo(DayOfMonth.valueOf(31)), is(lessThan(0)));
		assertThat(DayOfMonth.valueOf(10).compareTo(DayOfMonth.valueOf(1)), is(greaterThan(0)));
		assertThat(DayOfMonth.valueOf(10).compareTo(DayOfMonth.valueOf(2)), is(greaterThan(0)));
		assertThat(DayOfMonth.valueOf(10).compareTo(DayOfMonth.valueOf(5)), is(greaterThan(0)));
	}
	
	/**
	 * {@link DayOfMonth#toString()}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test06_toString() throws Exception {
		assertThat(DayOfMonth.valueOf(1).toString(), is("1"));
		assertThat(DayOfMonth.valueOf(10).toString(), is("10"));
		assertThat(DayOfMonth.valueOf(31).toString(), is("31"));
	}
	
	/**
	 * {@link DayOfMonth#equals(Object)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	@SuppressWarnings("serial")
	public void test07_equals() throws Exception {
		DayOfMonth dom1 = DayOfMonth.valueOf(1);
		assertThat(dom1.equals(DayOfMonth.valueOf(1)), is(true));
		assertThat(dom1.equals(dom1), is(true));
		assertThat(dom1.equals(DayOfMonth.valueOf(2)), is(false));
		assertThat(dom1.equals(null), is(false));
		assertThat(dom1.equals(new DayOfMonth(1) {
		}), is(false));
	}
}
