/**
 * Copyright (c) 2004 Domain Language, Inc. (http://domainlanguage.com) This
 * free software is distributed under the "MIT" licence. See file licence.txt.
 * For more information, see http://timeandmoney.sourceforge.net.
 */

package com.domainlanguage.time;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.Iterator;
import java.util.NoSuchElementException;

import com.domainlanguage.time.AnnualDateSpecification;
import com.domainlanguage.time.CalendarDate;
import com.domainlanguage.time.CalendarInterval;
import com.domainlanguage.time.DateSpecification;
import com.domainlanguage.time.DayOfWeek;

import org.junit.Test;

/**
 * {@link DateSpecification}のテストクラス。
 * 
 * @author daisuke
 */
public class DateSpecificationTest {
	
	/**
	 * TODO for daisuke
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test01_FixedDate() throws Exception {
		CalendarInterval y2004 = CalendarInterval.year(2004);
		DateSpecification independenceDay = DateSpecification.fixed(7, 4);
		assertThat(((AnnualDateSpecification) independenceDay).ofYear(2004), is(CalendarDate.date(2004, 7, 4)));
		assertThat(independenceDay.firstOccurrenceIn(y2004), is(CalendarDate.date(2004, 7, 4)));
		assertThat(independenceDay.isSatisfiedBy(CalendarDate.date(2004, 7, 4)), is(true));
		assertThat(independenceDay.isSatisfiedBy(CalendarDate.date(2004, 7, 3)), is(false));
		assertThat(independenceDay.isSatisfiedBy(CalendarDate.date(1970, 7, 4)), is(true));
	}
	
	/**
	 * TODO for daisuke
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test02_NthWeekdayInMonth() throws Exception {
		DateSpecification thanksgiving = DateSpecification.nthOccuranceOfWeekdayInMonth(11, DayOfWeek.THURSDAY, 4);
		assertThat(((AnnualDateSpecification) thanksgiving).ofYear(2004), is(CalendarDate.date(2004, 11, 25)));
		
		CalendarInterval y2004 = CalendarInterval.year(2004);
		assertThat(thanksgiving.firstOccurrenceIn(y2004), is(CalendarDate.date(2004, 11, 25)));
		assertThat(thanksgiving.isSatisfiedBy(CalendarDate.date(2004, 11, 25)), is(true));
		assertThat(thanksgiving.isSatisfiedBy(CalendarDate.date(2002, 11, 25)), is(false));
		CalendarInterval y2002 = CalendarInterval.year(2002);
		assertThat(thanksgiving.firstOccurrenceIn(y2002), is(CalendarDate.date(2002, 11, 28)));
		assertThat(thanksgiving.isSatisfiedBy(CalendarDate.date(2002, 11, 28)), is(true));
		
		// Calculate all the Thanksgivings over a three year interval.
		CalendarInterval y2002_2004 = CalendarInterval.inclusive(2002, 1, 1, 2004, 12, 31);
		assertThat(thanksgiving.firstOccurrenceIn(y2002_2004), is(CalendarDate.date(2002, 11, 28)));
		Iterator<CalendarDate> iterator = thanksgiving.iterateOver(y2002_2004);
		assertThat(iterator.hasNext(), is(true));
		assertThat(iterator.next(), is(CalendarDate.date(2002, 11, 28)));
		assertThat(iterator.hasNext(), is(true));
		assertThat(iterator.next(), is(CalendarDate.date(2003, 11, 27)));
		assertThat(iterator.hasNext(), is(true));
		assertThat(iterator.next(), is(CalendarDate.date(2004, 11, 25)));
		assertThat(iterator.hasNext(), is(false));
	}
	
	/**
	 * TODO for daisuke
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test03_SelectFirstFromInterval() throws Exception {
		CalendarInterval y2002_2004 = CalendarInterval.inclusive(2002, 1, 1, 2004, 12, 31);
		CalendarInterval ylate2002_2004 = CalendarInterval.inclusive(2002, 8, 1, 2004, 12, 31);
		CalendarInterval ylate2002 = CalendarInterval.inclusive(2002, 8, 1, 2002, 12, 31);
		CalendarInterval ylate2002_early2003 = CalendarInterval.inclusive(2002, 8, 1, 2003, 6, 30);
		DateSpecification independenceDay = DateSpecification.fixed(7, 4);
		assertThat(independenceDay.firstOccurrenceIn(y2002_2004), is(CalendarDate.date(2002, 7, 4)));
		assertThat(independenceDay.firstOccurrenceIn(ylate2002_2004), is(CalendarDate.date(2003, 7, 4)));
		assertThat(independenceDay.firstOccurrenceIn(ylate2002), is(nullValue()));
		assertThat(independenceDay.firstOccurrenceIn(ylate2002_early2003), is(nullValue()));
	}
	
	/**
	 * TODO for daisuke
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test04_IterateThroughInterval() throws Exception {
		DateSpecification independenceDay = DateSpecification.fixed(7, 4);
		CalendarInterval ylate2002_early2005 = CalendarInterval.inclusive(2002, 8, 1, 2005, 6, 31);
		Iterator<CalendarDate> it = independenceDay.iterateOver(ylate2002_early2005);
		assertThat(it.hasNext(), is(true));
		assertThat(it.next(), is(CalendarDate.date(2003, 7, 4)));
		assertThat(it.hasNext(), is(true));
		assertThat(it.next(), is(CalendarDate.date(2004, 7, 4)));
		assertThat(it.hasNext(), is(false));
		try {
			it.next();
			fail();
		} catch (NoSuchElementException e) {
			// success
		}
	}
}
