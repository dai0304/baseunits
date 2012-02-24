/*
 * Copyright 2011-2012 Daisuke Miyamoto. (http://d.hatena.ne.jp/daisuke-m)
 * Created on 2011/12/22
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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.fail;

import org.junit.Test;

/**
 * {@link CalendarWeek}のテストクラス。
 */
public class CalendarWeekTest {
	
	/**
	 * {@link CalendarWeek#equals(Object)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test01_equals() throws Exception {
		CalendarWeek thisWeek = CalendarWeek.from(CalendarDate.from(2012, 2, 24));
		assertThat(thisWeek.equals(thisWeek), is(true));
		assertThat(CalendarWeek.from(2012, 1).equals(CalendarWeek.from(2012, 1)), is(true));
		assertThat(CalendarWeek.from(2012, 1).equals(CalendarWeek.from(2012, 2)), is(false));
		assertThat(CalendarWeek.from(2012, 1).equals(CalendarWeek.from(2011, 1)), is(false));
		
		assertThat(CalendarWeek.from(2011, 53).equals(CalendarWeek.from(2012, 1)), is(false));
		assertThat(CalendarWeek.from(2012, 1).equals(null), is(false));
		assertThat(CalendarWeek.from(2012, 1).equals(new Object()), is(false));
	}
	
	/**
	 * {@link CalendarWeek#toString()}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test02_toString() throws Exception {
		assertThat(CalendarWeek.from(2011, 52).toString(), is("2011-52th"));
		assertThat(CalendarWeek.from(2011, 53).toString(), is("2012-1th"));
		assertThat(CalendarWeek.from(2012, 1).toString(), is("2012-1th"));
	}
	
	/**
	 * {@link CalendarWeek#from(CalendarDate)}
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test03_from() throws Exception {
		assertThat(CalendarWeek.from(CalendarDate.from(2011, 12, 25)), is(CalendarWeek.from(2011, 51)));
		assertThat(CalendarWeek.from(CalendarDate.from(2011, 12, 26)), is(CalendarWeek.from(2011, 52)));
		assertThat(CalendarWeek.from(CalendarDate.from(2011, 12, 27)), is(CalendarWeek.from(2011, 52)));
		assertThat(CalendarWeek.from(CalendarDate.from(2011, 12, 28)), is(CalendarWeek.from(2011, 52)));
		assertThat(CalendarWeek.from(CalendarDate.from(2011, 12, 29)), is(CalendarWeek.from(2011, 52)));
		assertThat(CalendarWeek.from(CalendarDate.from(2011, 12, 30)), is(CalendarWeek.from(2011, 52)));
		assertThat(CalendarWeek.from(CalendarDate.from(2011, 12, 31)), is(CalendarWeek.from(2011, 52)));
		assertThat(CalendarWeek.from(CalendarDate.from(2012, 1, 1)), is(CalendarWeek.from(2011, 52)));
		assertThat(CalendarWeek.from(CalendarDate.from(2012, 1, 2)), is(CalendarWeek.from(2012, 1)));
		assertThat(CalendarWeek.from(CalendarDate.from(2012, 1, 3)), is(CalendarWeek.from(2012, 1)));
		assertThat(CalendarWeek.from(CalendarDate.from(2012, 1, 4)), is(CalendarWeek.from(2012, 1)));
		assertThat(CalendarWeek.from(CalendarDate.from(2012, 1, 5)), is(CalendarWeek.from(2012, 1)));
		assertThat(CalendarWeek.from(CalendarDate.from(2012, 1, 6)), is(CalendarWeek.from(2012, 1)));
		assertThat(CalendarWeek.from(CalendarDate.from(2012, 1, 7)), is(CalendarWeek.from(2012, 1)));
		assertThat(CalendarWeek.from(CalendarDate.from(2012, 1, 8)), is(CalendarWeek.from(2012, 1)));
		assertThat(CalendarWeek.from(CalendarDate.from(2012, 1, 9)), is(CalendarWeek.from(2012, 2)));
		assertThat(CalendarWeek.from(CalendarDate.from(2012, 2, 24)), is(CalendarWeek.from(2012, 8)));
	}
	
	/**
	 * {@link CalendarWeek#asYearInterval()}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test04_asYearInterval() throws Exception {
		assertThat(CalendarWeek.from(2012, 8).asYearInterval(), is(CalendarInterval.year(2012)));
	}
	
	/**
	 * {@link CalendarWeek#at(DayOfWeek)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test05_at() throws Exception {
		assertThat(CalendarWeek.from(2012, 8).at(DayOfWeek.FRIDAY), is(CalendarDate.from(2012, 2, 24)));
	}
	
	/**
	 * {@link CalendarWeek#breachEncapsulationOfMonth()}, {@link CalendarWeek#breachEncapsulationOfYear()}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test06_breachEncapsulationOf() throws Exception {
		CalendarWeek week = CalendarWeek.from(2012, 8);
		assertThat(week.breachEncapsulationOfMonth(), is(WeekOfYear.valueOf(8)));
		assertThat(week.breachEncapsulationOfYear(), is(2012));
	}
	
	/**
	 * {@link CalendarWeek#compareTo(CalendarWeek)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test07_compareTo() throws Exception {
		assertThat(CalendarWeek.from(2012, 8).compareTo(CalendarWeek.from(2012, 8)), is(0));
		assertThat(CalendarWeek.from(2012, 9).compareTo(CalendarWeek.from(2012, 8)), is(greaterThan(0)));
		assertThat(CalendarWeek.from(2012, 8).compareTo(CalendarWeek.from(2012, 9)), is(lessThan(0)));
		
		try {
			CalendarWeek.from(2012, 8).compareTo(null);
			fail();
		} catch (NullPointerException e) {
			// success
		}
	}
}
