/*
 * Copyright 2011-2013 Daisuke Miyamoto. (http://d.hatena.ne.jp/daisuke-m)
 * Copyright 2010-2011 TRICREO, Inc. (http://tricreo.jp/)
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
 * Copyright (c) 2005 Domain Language, Inc. (http://domainlanguage.com) This
 * free software is distributed under the "MIT" licence.
 * For more information, see http://timeandmoney.sourceforge.net.
 */
package jp.xet.baseunits.time;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import jp.xet.baseunits.time.MinuteOfHour;

import org.junit.Test;

/**
 * {@link MinuteOfHour}のテストクラス。
 */
public class MinuteOfHourTest {
	
	/**
	 * {@link MinuteOfHour#valueOf(int)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test01_Simple() throws Exception {
		assertThat(MinuteOfHour.valueOf(11).breachEncapsulationOfValue(), is(11));
		assertThat(MinuteOfHour.valueOf(23), is(MinuteOfHour.valueOf(23)));
	}
	
	/**
	 * {@link MinuteOfHour#valueOf(int)}の不正引数テスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test02_IllegalLessThanZero() throws Exception {
		try {
			MinuteOfHour.valueOf(-1);
			fail();
		} catch (IllegalArgumentException ex) {
			// success
		}
	}
	
	/**
	 * {@link MinuteOfHour#valueOf(int)}の不正引数テスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test03_GreaterThan() throws Exception {
		try {
			MinuteOfHour.valueOf(60);
			fail();
		} catch (IllegalArgumentException ex) {
			// success
		}
	}
	
	/**
	 * {@link MinuteOfHour#isAfter(MinuteOfHour)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test04_LaterAfterEarlier() throws Exception {
		MinuteOfHour later = MinuteOfHour.valueOf(45);
		MinuteOfHour earlier = MinuteOfHour.valueOf(15);
		assertThat(later.isAfter(earlier), is(true));
	}
	
	/**
	 * {@link MinuteOfHour#isAfter(MinuteOfHour)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test05_EarlierAfterLater() throws Exception {
		MinuteOfHour earlier = MinuteOfHour.valueOf(15);
		MinuteOfHour later = MinuteOfHour.valueOf(45);
		assertThat(earlier.isAfter(later), is(false));
	}
	
	/**
	 * {@link MinuteOfHour#isAfter(MinuteOfHour)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test06_EqualAfterEqual() throws Exception {
		MinuteOfHour anMinute = MinuteOfHour.valueOf(45);
		MinuteOfHour anotherMinute = MinuteOfHour.valueOf(45);
		assertThat(anMinute.isAfter(anotherMinute), is(false));
	}
	
	/**
	 * {@link MinuteOfHour#isBefore(MinuteOfHour)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test07_LaterBeforeEarlier() throws Exception {
		MinuteOfHour later = MinuteOfHour.valueOf(45);
		MinuteOfHour earlier = MinuteOfHour.valueOf(15);
		assertThat(later.isBefore(earlier), is(false));
	}
	
	/**
	 * {@link MinuteOfHour#isBefore(MinuteOfHour)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test08_EarlierBeforeLater() throws Exception {
		MinuteOfHour earlier = MinuteOfHour.valueOf(15);
		MinuteOfHour later = MinuteOfHour.valueOf(45);
		assertThat(earlier.isBefore(later), is(true));
	}
	
	/**
	 * {@link MinuteOfHour#isBefore(MinuteOfHour)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test09_EqualBeforeEqual() throws Exception {
		MinuteOfHour anMinute = MinuteOfHour.valueOf(15);
		MinuteOfHour anotherMinute = MinuteOfHour.valueOf(15);
		assertThat(anMinute.isBefore(anotherMinute), is(false));
	}
	
	/**
	 * {@link MinuteOfHour#equals(Object)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	@SuppressWarnings("serial")
	public void test10_equals() throws Exception {
		MinuteOfHour m14 = MinuteOfHour.valueOf(14);
		assertThat(m14.equals(m14), is(true));
		assertThat(m14.equals(MinuteOfHour.valueOf(14)), is(true));
		assertThat(m14.equals(MinuteOfHour.valueOf(15)), is(false));
		assertThat(m14.equals(null), is(false));
		assertThat(m14.equals(new MinuteOfHour(14)), is(true));
		assertThat(m14.equals(new MinuteOfHour(14) {
		}), is(false));
	}
	
	/**
	 * {@link MinuteOfHour#toString()}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test11_toString() throws Exception {
		for (int i = 1; i < 10; i++) {
			MinuteOfHour m = MinuteOfHour.valueOf(i);
			assertThat(m.toString(), is(String.format("%02d", i)));
		}
		for (int i = 10; i < 60; i++) {
			MinuteOfHour m = MinuteOfHour.valueOf(i);
			assertThat(m.toString(), is(String.valueOf(i)));
		}
	}
}
