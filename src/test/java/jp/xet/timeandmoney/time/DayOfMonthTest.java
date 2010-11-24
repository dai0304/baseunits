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
	}
}
