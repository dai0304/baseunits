/*
 * Copyright 2010 TRICREO, Inc. (http://tricreo.jp/)
 * Created on 2010/11/27
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
package jp.tricreo.basicunits.time;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import jp.tricreo.basicunits.tests.SerializationTester;

import org.junit.Test;

/**
 * {@link CalendarMonth}のテストクラス。
 */
public class CalendarMonthTest {
	
	CalendarMonth nov2010 = CalendarMonth.from(2010, 11);
	
	CalendarMonth dec2010 = CalendarMonth.from(2010, 12);
	
	CalendarMonth feb2009 = CalendarMonth.from(2009, 2);
	
	CalendarMonth mar1978 = CalendarMonth.from(1978, 3);
	

	/**
	 * {@link CalendarMonth}のインスタンスがシリアライズできるかどうか検証する。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test01_Serialization() throws Exception {
		SerializationTester.assertCanBeSerialized(nov2010);
	}
	
	/**
	 * {@link CalendarMonth#from}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test02_from() throws Exception {
		assertThat(CalendarMonth.parse("2010-11", "yyyy-MM"), is(nov2010));
		assertThat(CalendarMonth.from(1978, MonthOfYear.MAR), is(mar1978));
	}
	
	/**
	 * {@link CalendarMonth#asCalendarInterval()}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test03_asCalendarInterval() throws Exception {
		CalendarInterval calendarInterval = dec2010.asCalendarInterval();
		assertThat(calendarInterval.start(), is(CalendarDate.from(2010, 12, 1)));
		assertThat(calendarInterval.end(), is(CalendarDate.from(2010, 12, 31)));
	}
	
	/**
	 * {@link CalendarMonth#at(DayOfMonth)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test03_at() throws Exception {
		assertThat(mar1978.at(DayOfMonth.valueOf(4)), is(CalendarDate.from(1978, 3, 4)));
		
		try {
			feb2009.at(DayOfMonth.valueOf(30));
			fail();
		} catch (IllegalArgumentException e) {
			// success
		}
	}
	
	/**
	 * {@link CalendarMonth#getLastDay()}
	 * {@link CalendarMonth#getLastDayOfMonth()}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test04_getLastDay() throws Exception {
		assertThat(feb2009.getLastDay(), is(CalendarDate.from(2009, 2, 28)));
		assertThat(nov2010.getLastDay(), is(CalendarDate.from(2010, 11, 30)));
		assertThat(dec2010.getLastDay(), is(CalendarDate.from(2010, 12, 31)));
		
		assertThat(feb2009.getLastDayOfMonth(), is(DayOfMonth.valueOf(28)));
		assertThat(nov2010.getLastDayOfMonth(), is(DayOfMonth.valueOf(30)));
		assertThat(dec2010.getLastDayOfMonth(), is(DayOfMonth.valueOf(31)));
	}
	
	/**
	 * TODO for daisuke
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test06_compareTo() throws Exception {
		assertThat(feb2009.compareTo(feb2009), is(0));
		assertThat(feb2009.compareTo(dec2010), is(lessThan(0)));
		assertThat(dec2010.compareTo(feb2009), is(greaterThan(0)));
		try {
			feb2009.compareTo(null);
			fail();
		} catch (NullPointerException e) {
			// success
		}
	}
	
	/**
	 * TODO for daisuke
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test07_isBefore() throws Exception {
		assertThat(feb2009.isBefore(feb2009), is(false));
		assertThat(feb2009.isBefore(null), is(false));
		assertThat(feb2009.isBefore(dec2010), is(true));
		assertThat(dec2010.isBefore(feb2009), is(false));
	}
	
	/**
	 * TODO for daisuke
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test08_isAfter() throws Exception {
		assertThat(feb2009.isAfter(feb2009), is(false));
		assertThat(feb2009.isAfter(null), is(false));
		assertThat(feb2009.isAfter(dec2010), is(false));
		assertThat(dec2010.isAfter(feb2009), is(true));
	}
	
	/**
	 * {@link CalendarMonth#equals(Object)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	@SuppressWarnings("serial")
	public void test09_equals() throws Exception {
		assertThat(feb2009.equals(feb2009), is(true));
		assertThat(feb2009.equals(dec2010), is(false));
		assertThat(feb2009.equals(null), is(false));
		assertThat(feb2009.equals(new CalendarMonth(2009, MonthOfYear.FEB)), is(true));
		assertThat(feb2009.equals(new CalendarMonth(2009, MonthOfYear.FEB) {
		}), is(false));
	}
}
