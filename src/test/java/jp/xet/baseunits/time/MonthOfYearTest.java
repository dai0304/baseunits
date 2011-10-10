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
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.Calendar;

import jp.xet.baseunits.time.CalendarMonth;
import jp.xet.baseunits.time.DayOfMonth;
import jp.xet.baseunits.time.MonthOfYear;

import org.junit.Test;

/**
 * {@link MonthOfYear}のテストクラス。
 */
public class MonthOfYearTest {
	
	/**
	 * 基本的な機能テスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test01_basics() throws Exception {
		assertThat(MonthOfYear.JAN.breachEncapsulationOfValue(), is(1));
		assertThat(MonthOfYear.FEB.breachEncapsulationOfValue(), is(2));
		assertThat(MonthOfYear.DEC.breachEncapsulationOfValue(), is(12));
		assertThat(MonthOfYear.JAN.breachEncapsulationOfCalendarValue(), is(Calendar.JANUARY));
		assertThat(MonthOfYear.FEB.breachEncapsulationOfCalendarValue(), is(Calendar.FEBRUARY));
		assertThat(MonthOfYear.DEC.breachEncapsulationOfCalendarValue(), is(Calendar.DECEMBER));
	}
	
	/**
	 * {@link MonthOfYear#calendarValueOf(int)}及び{@link MonthOfYear#valueOf(int)}によるインスタンス生成テスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test02_valueOf() throws Exception {
		assertThat(MonthOfYear.calendarValueOf(Calendar.MARCH), is(MonthOfYear.MAR));
		assertThat(MonthOfYear.calendarValueOf(Calendar.DECEMBER), is(MonthOfYear.DEC));
		assertThat(MonthOfYear.calendarValueOf(-999), is(nullValue()));
		
		assertThat(MonthOfYear.valueOf(3), is(MonthOfYear.MAR));
		assertThat(MonthOfYear.valueOf(12), is(MonthOfYear.DEC));
		assertThat(MonthOfYear.valueOf(-999), is(nullValue()));
	}
	
	/**
	 * {@link MonthOfYear#isAfter(MonthOfYear)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test03_isAfter() throws Exception {
		assertThat(MonthOfYear.JUL.isAfter(MonthOfYear.JAN), is(true));
		assertThat(MonthOfYear.JUL.isAfter(MonthOfYear.SEP), is(false));
		assertThat(MonthOfYear.JUL.isAfter(MonthOfYear.JUL), is(false));
		assertThat(MonthOfYear.JUL.isAfter(null), is(false));
	}
	
	/**
	 * {@link MonthOfYear#isBefore(MonthOfYear)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test04_isBefore() throws Exception {
		assertThat(MonthOfYear.JUL.isBefore(MonthOfYear.JAN), is(false));
		assertThat(MonthOfYear.JUL.isBefore(MonthOfYear.SEP), is(true));
		assertThat(MonthOfYear.JUL.isBefore(MonthOfYear.JUL), is(false));
		assertThat(MonthOfYear.JUL.isBefore(null), is(false));
	}
	
	/**
	 * {@link MonthOfYear#on(int)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test05_on() throws Exception {
		assertThat(MonthOfYear.MAR.on(1978), is(CalendarMonth.from(1978, 3)));
	}
	
	/**
	 * {@link MonthOfYear#getLastDayOfThisMonth(int)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test06_getLastDayOfThisMonth() throws Exception {
		assertThat(MonthOfYear.JAN.getLastDayOfThisMonth(2010), is(DayOfMonth.valueOf(31)));
		
		assertThat(MonthOfYear.FEB.getLastDayOfThisMonth(2010), is(DayOfMonth.valueOf(28)));
		assertThat(MonthOfYear.FEB.getLastDayOfThisMonth(2000), is(DayOfMonth.valueOf(29)));
		assertThat(MonthOfYear.FEB.getLastDayOfThisMonth(2004), is(DayOfMonth.valueOf(29)));
		assertThat(MonthOfYear.FEB.getLastDayOfThisMonth(1900), is(DayOfMonth.valueOf(28)));
		
		assertThat(MonthOfYear.NOV.getLastDayOfThisMonth(2010), is(DayOfMonth.valueOf(30)));
	}
}
