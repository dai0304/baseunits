/*
 * Copyright 2010 Daisuke Miyamoto.
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
package jp.xet.timeandmoney.time;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import jp.xet.timeandmoney.tests.SerializationTester;

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
	 * TODO for daisuke
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test02_from() throws Exception {
		assertThat(CalendarMonth.from("2010-11", "yyyy-MM"), is(nov2010));
		assertThat(CalendarMonth.from(1978, MonthOfYear.MAR), is(mar1978));
	}
	
	/**
	 * TODO for daisuke
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
	 * TODO for daisuke
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
	 * TODO for daisuke
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
	@SuppressWarnings("serial")
	public void test05_equals() throws Exception {
		assertThat(feb2009.equals(feb2009), is(true));
		assertThat(feb2009.equals(dec2010), is(false));
		assertThat(feb2009.equals(null), is(false));
		assertThat(feb2009.equals(new CalendarMonth(2009, MonthOfYear.FEB)), is(true));
		assertThat(feb2009.equals(new CalendarMonth(2009, MonthOfYear.FEB) {
		}), is(false));
	}
}
