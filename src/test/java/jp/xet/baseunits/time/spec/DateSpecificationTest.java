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
package jp.xet.baseunits.time.spec;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.Iterator;
import java.util.NoSuchElementException;

import jp.xet.baseunits.time.CalendarDate;
import jp.xet.baseunits.time.CalendarInterval;
import jp.xet.baseunits.time.CalendarMonth;
import jp.xet.baseunits.time.DayOfMonth;
import jp.xet.baseunits.time.DayOfWeek;
import jp.xet.baseunits.time.MonthOfYear;

import org.junit.Test;

/**
 * {@link DateSpecifications}のテストクラス。
 */
public class DateSpecificationTest {
	
	/**
	 * {@link AnnualFixedDateSpecification}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test01_FixedDate() throws Exception {
		CalendarInterval y2004 = CalendarInterval.year(2004);
		DateSpecification independenceDay = DateSpecifications.fixed(7, 4);
		assertThat(((AnnualDateSpecification) independenceDay).ofYear(2004), is(CalendarDate.from(2004, 7, 4)));
		assertThat(independenceDay.firstOccurrenceIn(y2004), is(CalendarDate.from(2004, 7, 4)));
		assertThat(independenceDay.isSatisfiedBy(CalendarDate.from(2004, 7, 4)), is(true));
		assertThat(independenceDay.isSatisfiedBy(CalendarDate.from(2004, 7, 3)), is(false));
		assertThat(independenceDay.isSatisfiedBy(CalendarDate.from(1970, 7, 4)), is(true));
	}
	
	/**
	 * {@link AnnualFloatingDateSpecification}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test02_NthWeekdayInMonth() throws Exception {
		DateSpecification thanksgiving =
				DateSpecifications.nthOccuranceOfWeekdayInMonth(MonthOfYear.NOV, DayOfWeek.THURSDAY, 4);
		assertThat(((AnnualDateSpecification) thanksgiving).ofYear(2004), is(CalendarDate.from(2004, 11, 25)));
		
		CalendarInterval y2004 = CalendarInterval.year(2004);
		assertThat(thanksgiving.firstOccurrenceIn(y2004), is(CalendarDate.from(2004, 11, 25)));
		assertThat(thanksgiving.isSatisfiedBy(CalendarDate.from(2004, 11, 25)), is(true));
		assertThat(thanksgiving.isSatisfiedBy(CalendarDate.from(2002, 11, 25)), is(false));
		CalendarInterval y2002 = CalendarInterval.year(2002);
		assertThat(thanksgiving.firstOccurrenceIn(y2002), is(CalendarDate.from(2002, 11, 28)));
		assertThat(thanksgiving.isSatisfiedBy(CalendarDate.from(2002, 11, 28)), is(true));
		
		// Calculate all the Thanksgivings over a three year interval.
		CalendarInterval y2002_2004 = CalendarInterval.inclusive(2002, 1, 1, 2004, 12, 31);
		assertThat(thanksgiving.firstOccurrenceIn(y2002_2004), is(CalendarDate.from(2002, 11, 28)));
		Iterator<CalendarDate> iterator = thanksgiving.iterateOver(y2002_2004);
		assertThat(iterator.hasNext(), is(true));
		assertThat(iterator.next(), is(CalendarDate.from(2002, 11, 28)));
		assertThat(iterator.hasNext(), is(true));
		assertThat(iterator.next(), is(CalendarDate.from(2003, 11, 27)));
		assertThat(iterator.hasNext(), is(true));
		assertThat(iterator.next(), is(CalendarDate.from(2004, 11, 25)));
		assertThat(iterator.hasNext(), is(false));
	}
	
	/**
	 * {@link MonthlyFixedDateSpecification}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test03_FixedDate() throws Exception {
		CalendarInterval y2004 = CalendarInterval.year(2004);
		DateSpecification thirdEveryMonth = DateSpecifications.fixed(3);
		assertThat(((MonthlyDateSpecification) thirdEveryMonth).ofYearMonth(CalendarMonth.from(2010, 11)),
				is(CalendarDate.from(2010, 11, 3)));
		assertThat(thirdEveryMonth.firstOccurrenceIn(y2004), is(CalendarDate.from(2004, 1, 3)));
		assertThat(thirdEveryMonth.isSatisfiedBy(CalendarDate.from(2011, 2, 3)), is(true));
		assertThat(thirdEveryMonth.isSatisfiedBy(CalendarDate.from(2004, 7, 10)), is(false));
		assertThat(thirdEveryMonth.isSatisfiedBy(CalendarDate.from(1970, 1, 3)), is(true));
	}
	
	/**
	 * {@link MonthlyFloatingDateSpecification}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test04_NthWeekdayInEveryMonth() throws Exception {
		DateSpecification tsubasaHoliday = DateSpecifications.nthOccuranceOfWeekdayInEveryMonth(DayOfWeek.SATURDAY, 2);
		assertThat(((MonthlyFloatingDateSpecification) tsubasaHoliday).ofYearMonth(CalendarMonth.from(2010,
				MonthOfYear.AUG)), is(CalendarDate.from(2010, MonthOfYear.AUG, DayOfMonth.valueOf(14))));
		
		CalendarInterval apr2009 = CalendarInterval.month(CalendarMonth.from(2009, MonthOfYear.APR));
		assertThat(tsubasaHoliday.firstOccurrenceIn(apr2009), is(CalendarDate.from(2009, 4, 11)));
		assertThat(tsubasaHoliday.isSatisfiedBy(CalendarDate.from(2004, 11, 13)), is(true));
		assertThat(tsubasaHoliday.isSatisfiedBy(CalendarDate.from(2002, 11, 20)), is(false));
		CalendarInterval y2002 = CalendarInterval.year(2002);
		assertThat(tsubasaHoliday.firstOccurrenceIn(y2002), is(CalendarDate.from(2002, 1, 12)));
		assertThat(tsubasaHoliday.isSatisfiedBy(CalendarDate.from(2002, 1, 12)), is(true));
		assertThat(tsubasaHoliday.isSatisfiedBy(CalendarDate.from(2002, 1, 13)), is(false));
		
		// Calculate all the Tsubasa Holiday over a three month interval.
		CalendarInterval y2002_2004 = CalendarInterval.inclusive(2002, 4, 1, 2002, 6, 30);
		assertThat(tsubasaHoliday.firstOccurrenceIn(y2002_2004), is(CalendarDate.from(2002, 4, 13)));
		Iterator<CalendarDate> iterator = tsubasaHoliday.iterateOver(y2002_2004);
		assertThat(iterator.hasNext(), is(true));
		assertThat(iterator.next(), is(CalendarDate.from(2002, 4, 13)));
		assertThat(iterator.hasNext(), is(true));
		assertThat(iterator.next(), is(CalendarDate.from(2002, 5, 11)));
		assertThat(iterator.hasNext(), is(true));
		assertThat(iterator.next(), is(CalendarDate.from(2002, 6, 8)));
		assertThat(iterator.hasNext(), is(false));
	}
	
	/**
	 * {@link DateSpecifications#never()}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test05_never() throws Exception {
		DateSpecification never = DateSpecifications.never();
		assertThat(never, is(notNullValue()));
		
		assertThat(never.firstOccurrenceIn(CalendarInterval.year(2010)), is(nullValue()));
		Iterator<CalendarDate> it = CalendarInterval.year(2009).daysIterator();
		while (it.hasNext()) {
			assertThat(never.isSatisfiedBy(it.next()), is(false));
		}
		
		assertThat(never.iterateOver(CalendarInterval.year(2008)).hasNext(), is(false));
	}
	
	/**
	 * {@link DateSpecification#firstOccurrenceIn(CalendarInterval)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test06_SelectFirstFromInterval() throws Exception {
		CalendarInterval y2002_2004 = CalendarInterval.inclusive(2002, 1, 1, 2004, 12, 31);
		CalendarInterval ylate2002_2004 = CalendarInterval.inclusive(2002, 8, 1, 2004, 12, 31);
		CalendarInterval ylate2002 = CalendarInterval.inclusive(2002, 8, 1, 2002, 12, 31);
		CalendarInterval ylate2002_early2003 = CalendarInterval.inclusive(2002, 8, 1, 2003, 6, 30);
		DateSpecification independenceDay = DateSpecifications.fixed(7, 4);
		assertThat(independenceDay.firstOccurrenceIn(y2002_2004), is(CalendarDate.from(2002, 7, 4)));
		assertThat(independenceDay.firstOccurrenceIn(ylate2002_2004), is(CalendarDate.from(2003, 7, 4)));
		assertThat(independenceDay.firstOccurrenceIn(ylate2002), is(nullValue()));
		assertThat(independenceDay.firstOccurrenceIn(ylate2002_early2003), is(nullValue()));
	}
	
	/**
	 * {@link DateSpecification#iterateOver(CalendarInterval)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test07_IterateThroughInterval() throws Exception {
		DateSpecification independenceDay = DateSpecifications.fixed(7, 4);
		CalendarInterval ylate2002_early2005 = CalendarInterval.inclusive(2002, 8, 1, 2005, 6, 30);
		Iterator<CalendarDate> it = independenceDay.iterateOver(ylate2002_early2005);
		assertThat(it.hasNext(), is(true));
		assertThat(it.next(), is(CalendarDate.from(2003, 7, 4)));
		assertThat(it.hasNext(), is(true));
		assertThat(it.next(), is(CalendarDate.from(2004, 7, 4)));
		assertThat(it.hasNext(), is(false));
		try {
			it.next();
			fail();
		} catch (NoSuchElementException e) {
			// success
		}
	}
}
