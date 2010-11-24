/*
 * Copyright 2010 Daisuke Miyamoto.
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
package jp.xet.timeandmoney.time;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

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
			DayOfMonth.of(i);
		}
		
		try {
			DayOfMonth.of(0);
			fail();
		} catch (IllegalArgumentException e) {
			// success
		}
		try {
			DayOfMonth.of(-1);
			fail();
		} catch (IllegalArgumentException e) {
			// success
		}
		try {
			DayOfMonth.of(32);
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
		for (int i = 1; i <= 30; i++) {
			DayOfMonth.of(2010, MonthOfYear.APR, i);
		}
		try {
			DayOfMonth.of(2010, MonthOfYear.APR, 0);
			fail();
		} catch (IllegalArgumentException e) {
			// success
		}
		try {
			DayOfMonth.of(2010, MonthOfYear.APR, -1);
			fail();
		} catch (IllegalArgumentException e) {
			// success
		}
		try {
			DayOfMonth.of(2010, MonthOfYear.APR, 31);
			fail();
		} catch (IllegalArgumentException e) {
			// success
		}
		try {
			DayOfMonth.of(2010, MonthOfYear.APR, 32);
			fail();
		} catch (IllegalArgumentException e) {
			// success
		}
	}
	
	/**
	 * {@link DayOfMonth#isAfter(DayOfMonth)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test03_isAfter() throws Exception {
		assertThat(DayOfMonth.of(1).isAfter(DayOfMonth.of(1)), is(false));
		assertThat(DayOfMonth.of(1).isAfter(DayOfMonth.of(2)), is(false));
		
		assertThat(DayOfMonth.of(2).isAfter(DayOfMonth.of(1)), is(true));
		assertThat(DayOfMonth.of(2).isAfter(DayOfMonth.of(2)), is(false));
		assertThat(DayOfMonth.of(2).isAfter(DayOfMonth.of(3)), is(false));
		
		assertThat(DayOfMonth.of(3).isAfter(DayOfMonth.of(2)), is(true));
		assertThat(DayOfMonth.of(3).isAfter(DayOfMonth.of(3)), is(false));
		assertThat(DayOfMonth.of(3).isAfter(DayOfMonth.of(4)), is(false));
		
		assertThat(DayOfMonth.of(12).isAfter(DayOfMonth.of(11)), is(true));
		assertThat(DayOfMonth.of(12).isAfter(DayOfMonth.of(12)), is(false));
	}
	
	/**
	 * {@link DayOfMonth#isBefore(DayOfMonth)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test03_isBefore() throws Exception {
		assertThat(DayOfMonth.of(1).isBefore(DayOfMonth.of(1)), is(false));
		assertThat(DayOfMonth.of(1).isBefore(DayOfMonth.of(2)), is(true));
		
		assertThat(DayOfMonth.of(2).isBefore(DayOfMonth.of(1)), is(false));
		assertThat(DayOfMonth.of(2).isBefore(DayOfMonth.of(2)), is(false));
		assertThat(DayOfMonth.of(2).isBefore(DayOfMonth.of(3)), is(true));
		
		assertThat(DayOfMonth.of(3).isBefore(DayOfMonth.of(2)), is(false));
		assertThat(DayOfMonth.of(3).isBefore(DayOfMonth.of(3)), is(false));
		assertThat(DayOfMonth.of(3).isBefore(DayOfMonth.of(4)), is(true));
		
		assertThat(DayOfMonth.of(12).isBefore(DayOfMonth.of(11)), is(false));
		assertThat(DayOfMonth.of(12).isBefore(DayOfMonth.of(12)), is(false));
	}
}
