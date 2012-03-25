/*
 * Copyright 2011-2012 Daisuke Miyamoto. (http://d.hatena.ne.jp/daisuke-m)
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
 * Copyright (c) 2004 Domain Language, Inc. (http://domainlanguage.com) This
 * free software is distributed under the "MIT" licence.
 * For more information, see http://timeandmoney.sourceforge.net.
 */
package jp.xet.baseunits.time;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.TimeZone;

import jp.xet.baseunits.intervals.Interval;
import jp.xet.baseunits.tests.SerializationTester;

import org.junit.Test;

/**
 * {@link CalendarInterval}のテストクラス。
 */
public class CalendarIntervalTest {
	
	private CalendarDate may1 = CalendarDate.from(2004, 5, 1);
	
	private CalendarDate may2 = CalendarDate.from(2004, 5, 2);
	
	private CalendarDate may3 = CalendarDate.from(2004, 5, 3);
	
	private CalendarDate may14 = CalendarDate.from(2004, 5, 14);
	
	private CalendarDate may20 = CalendarDate.from(2004, 5, 20);
	
	private CalendarDate may31 = CalendarDate.from(2004, 5, 31);
	
	private CalendarDate apr15 = CalendarDate.from(2004, 4, 15);
	
	private CalendarDate jun1 = CalendarDate.from(2004, 6, 1);
	
	private CalendarInterval may = CalendarInterval.inclusive(2004, 5, 1, 2004, 5, 31);
	
	private TimeZone ct = TimeZone.getTimeZone("America/Chicago");
	
	
	/**
	 * {@link CalendarInterval}のインスタンスがシリアライズできるかどうか検証する。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test01_Serialization() throws Exception {
		SerializationTester.assertCanBeSerialized(may);
	}
	
	/**
	 * {@link CalendarInterval#asTimeInterval(TimeZone)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test02_TranslationToTimeInterval() throws Exception {
		TimePointInterval tpi = may.asTimeInterval(ct);
		assertThat(tpi.start(), is(TimePoint.atMidnight(2004, 5, 1, ct)));
		assertThat(tpi.includesLowerLimit(), is(true));
		assertThat(tpi.end(), is(TimePoint.atMidnight(2004, 6, 1, ct)));
		assertThat(tpi.includesUpperLimit(), is(false));
		
		TimePointInterval tpi2 = CalendarInterval.everFrom(CalendarDate.EPOCH_DATE).asTimeInterval(ct);
		assertThat(tpi2.hasLowerLimit(), is(true));
		assertThat(tpi2.hasUpperLimit(), is(false));
		
		TimePointInterval tpi3 = CalendarInterval.everPreceding(CalendarDate.EPOCH_DATE).asTimeInterval(ct);
		assertThat(tpi3.hasLowerLimit(), is(false));
		assertThat(tpi3.hasUpperLimit(), is(true));
	}
	
	/**
	 * {@link CalendarInterval#equals(Interval)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test03_Equals() throws Exception {
		assertThat(may.equals(CalendarInterval.inclusive(may1, may31)), is(true));
		assertThat(may.equals(may1), is(false));
		assertThat(may.equals(CalendarInterval.inclusive(may1, may20)), is(false));
	}
	
	/**
	 * {@link CalendarInterval#includes(CalendarDate)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test04_Includes() throws Exception {
		assertThat("apr15", may.includes(apr15), is(false));
		assertThat("may1", may.includes(may1), is(true));
		assertThat("may20", may.includes(may20), is(true));
		assertThat("jun1", may.includes(jun1), is(false));
		assertThat("may", may.covers(may), is(true));
	}
	
	/**
	 * {@link CalendarInterval#daysIterator()}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test05_DaysIterator() throws Exception {
		Iterator<CalendarDate> iterator = CalendarInterval.inclusive(may1, may3).daysIterator();
		assertThat(iterator.hasNext(), is(true));
		assertThat(iterator.next(), is(may1));
		assertThat(iterator.hasNext(), is(true));
		assertThat(iterator.next(), is(may2));
		assertThat(iterator.hasNext(), is(true));
		assertThat(iterator.next(), is(may3));
		assertThat(iterator.hasNext(), is(false));
		try {
			iterator.next();
			fail();
		} catch (NoSuchElementException e) {
			// success
		}
		
		try {
			CalendarInterval.everPreceding(may1).daysIterator();
			fail();
		} catch (IllegalStateException e) {
			// success
		}
	}
	
	/**
	 * {@link CalendarInterval#subintervalIterator(Duration)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test06_SubintervalIterator() throws Exception {
		CalendarInterval may1_3 = CalendarInterval.inclusive(may1, may3);
		Iterator<CalendarInterval> iterator = may1_3.subintervalIterator(Duration.days(1));
		assertThat(iterator.hasNext(), is(true));
		assertThat(iterator.next().start(), is(may1));
		assertThat(iterator.hasNext(), is(true));
		assertThat(iterator.next().start(), is(may2));
		assertThat(iterator.hasNext(), is(true));
		assertThat(iterator.next().start(), is(may3));
		assertThat(iterator.hasNext(), is(false));
		
		iterator = may1_3.subintervalIterator(Duration.days(2));
		assertThat(iterator.hasNext(), is(true));
		assertThat(iterator.next(), is(may1.through(may2)));
		assertThat(iterator.hasNext(), is(false));
		
		try {
			iterator = may1_3.subintervalIterator(Duration.hours(25));
			fail("CalendarInterval should not accept subinterval length that is not a multiple of days.");
		} catch (IllegalArgumentException e) {
			assertThat(true, is(true));
		}
		
		iterator = may1_3.subintervalIterator(Duration.months(1));
		assertThat(iterator.hasNext(), is(false));
		try {
			iterator.next();
			fail();
		} catch (NoSuchElementException e) {
			// success
		}
		
		CalendarInterval apr15_jun1 = CalendarInterval.inclusive(apr15, jun1);
		iterator = apr15_jun1.subintervalIterator(Duration.months(1));
		assertThat(iterator.hasNext(), is(true));
		assertThat(iterator.next(), is(apr15.through(may14)));
		assertThat(iterator.hasNext(), is(false));
		
		try {
			CalendarInterval.everPreceding(may1).subintervalIterator(Duration.months(1));
			fail();
		} catch (IllegalStateException e) {
			// success
		}
	}
	
	/**
	 * {@link CalendarInterval#length()} {@link CalendarInterval#lengthInMonths()} のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test07_Length() throws Exception {
		assertThat(may1.through(may3).length(), is(Duration.days(3)));
		CalendarInterval may2002_july2004 = CalendarInterval.inclusive(2002, 5, 1, 2004, 7, 1);
		// (5/1/2002-4/30/2003) 365 days + (-4/30/2004) 366 + (5/1-7/31) 31+30+1 = 793 days
		assertThat(may2002_july2004.length(), is(Duration.days(793)));
		assertThat(may2002_july2004.lengthInMonths(), is(Duration.months(26)));
		assertThat(apr15.through(may14).lengthInMonths(), is(Duration.months(1)));
	}
	
	/**
	 * {@link CalendarInterval#complementRelativeTo(Interval)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test08_Complements() throws Exception {
		CalendarInterval may1Onward = CalendarInterval.inclusive(may1, null);
		CalendarInterval may2Onward = CalendarInterval.inclusive(may2, null);
		List<Interval<CalendarDate>> complementList = may2Onward.complementRelativeTo(may1Onward);
		assertThat(complementList.size(), is(1));
		
		CalendarInterval complement = (CalendarInterval) complementList.iterator().next();
		assertThat(complement.isClosed(), is(true));
		assertThat(complement.start(), is(may1));
		assertThat(complement.end(), is(may1));
	}
	
	/**
	 * {@link CalendarInterval#everFrom(CalendarDate)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test10_EverFromToString() throws Exception {
		CalendarDate x = CalendarDate.from(2007, 6, 5);
		CalendarInterval i = CalendarInterval.everFrom(x);
		assertThat(i.toString(), is("[2007-06-05, Infinity)"));
	}
	
	/**
	 * {@link CalendarInterval}のインスタンス生成テスト。
	 * 
	 * [ 1792849 ] ConcreteCalendarInterval allows misordered limits
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test11_BackwardCalendarIvalIntersection() throws Exception {
		try {
			CalendarInterval.inclusive(2001, 1, 1, 1776, 7, 4);
			fail();
		} catch (IllegalArgumentException error) {
			// success
		}
	}
	
	/**
	 * {@link CalendarInterval#startingFrom(CalendarDate, Duration)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test12_StartingFrom() throws Exception {
		CalendarInterval d1 = CalendarInterval.startingFrom(may1, Duration.days(2));
		CalendarInterval expected1 = CalendarInterval.inclusive(2004, 5, 1, 2004, 5, 2);
		assertThat(d1, is(expected1));
		
		CalendarInterval d2 = CalendarInterval.startingFrom(may1, Duration.minutes(2));
		CalendarInterval expected2 = CalendarInterval.inclusive(2004, 5, 1, 2004, 5, 1);
		assertThat(d2, is(expected2));
	}
	
	/**
	 * {@link CalendarInterval#monthsIterator()}
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test13_monthsIterator() throws Exception {
		CalendarInterval interval = CalendarInterval.inclusive(2010, 5, 10, 2011, 7, 1);
		Iterator<CalendarMonth> itr = interval.monthsIterator();
		
		CalendarMonth std = CalendarMonth.from(2010, 5);
		while (itr.hasNext()) {
			CalendarMonth target = itr.next();
			assertThat(target, is(std));
			std = std.nextMonth();
		}
		assertThat(std, is(CalendarMonth.from(2011, 8)));
		
		try {
			itr.next();
			fail();
		} catch (NoSuchElementException e) {
			// success
		}
		
		CalendarInterval.everFrom(CalendarDate.EPOCH_DATE).monthsIterator();
		// assert no exception
		
		try {
			CalendarInterval.everPreceding(CalendarDate.EPOCH_DATE).monthsIterator();
			fail();
		} catch (IllegalStateException e) {
			// success
		}
	}
	
	/**
	 * {@link CalendarInterval#month(int, int)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test14_month() throws Exception {
		CalendarInterval month = CalendarInterval.month(2012, 2);
		assertThat(month.start(), is(CalendarDate.from(2012, 2, 1)));
		assertThat(month.end(), is(CalendarDate.from(2012, 2, 29)));
	}
	
	/**
	 * {@link CalendarInterval#preceding(CalendarDate, Duration)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test15_preceding() throws Exception {
		CalendarInterval week = CalendarInterval.preceding(CalendarDate.from(2012, 2, 23), Duration.weeks(1));
		assertThat(week.start(), is(CalendarDate.from(2012, 2, 17)));
		assertThat(week.end(), is(CalendarDate.from(2012, 2, 23)));
		assertThat(week.toString(), is("[2012-02-17, 2012-02-23]"));
		
		CalendarInterval p = CalendarInterval.preceding(CalendarDate.from(2012, 2, 23), Duration.minutes(1));
		assertThat(p.start(), is(CalendarDate.from(2012, 2, 23)));
		assertThat(p.end(), is(CalendarDate.from(2012, 2, 23)));
	}
	
	/**
	 * {@link CalendarInterval#daysInReverseIterator()}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test16_daysInReverseIterator() throws Exception {
		CalendarInterval week = CalendarInterval.preceding(CalendarDate.from(2012, 2, 23), Duration.weeks(1));
		Iterator<CalendarDate> itr = week.daysInReverseIterator();
		assertThat(itr.hasNext(), is(true));
		assertThat(itr.next(), is(CalendarDate.from(2012, 2, 23)));
		assertThat(itr.hasNext(), is(true));
		assertThat(itr.next(), is(CalendarDate.from(2012, 2, 22)));
		assertThat(itr.hasNext(), is(true));
		assertThat(itr.next(), is(CalendarDate.from(2012, 2, 21)));
		assertThat(itr.hasNext(), is(true));
		assertThat(itr.next(), is(CalendarDate.from(2012, 2, 20)));
		assertThat(itr.hasNext(), is(true));
		assertThat(itr.next(), is(CalendarDate.from(2012, 2, 19)));
		assertThat(itr.hasNext(), is(true));
		assertThat(itr.next(), is(CalendarDate.from(2012, 2, 18)));
		assertThat(itr.hasNext(), is(true));
		assertThat(itr.next(), is(CalendarDate.from(2012, 2, 17)));
		assertThat(itr.hasNext(), is(false));
		try {
			itr.next();
			fail();
		} catch (NoSuchElementException e) {
			// success
		}
		
		try {
			CalendarInterval.everFrom(CalendarDate.EPOCH_DATE).daysInReverseIterator();
			fail();
		} catch (IllegalStateException e) {
			// success
		}
	}
	
	/**
	 * {@link CalendarInterval#lengthInDaysInt()}, {@link CalendarInterval#lengthInMonths()},
	 * {@link CalendarInterval#lengthInMonthsInt()}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test17_lengthIn() throws Exception {
		CalendarInterval week = CalendarInterval.preceding(CalendarDate.from(2012, 2, 23), Duration.quarters(3));
		assertThat(week.lengthInDaysInt(), is(276));
		assertThat(week.lengthInMonthsInt(), is(9));
		assertThat(week.lengthInMonths(), is(Duration.months(9)));
		
		try {
			CalendarInterval.everFrom(CalendarDate.EPOCH_DATE).lengthInDaysInt();
			fail();
		} catch (IllegalStateException e) {
			// success
		}
		
		try {
			CalendarInterval.everFrom(CalendarDate.EPOCH_DATE).lengthInMonthsInt();
			fail();
		} catch (IllegalStateException e) {
			// success
		}
		
		try {
			CalendarInterval.everFrom(CalendarDate.EPOCH_DATE).lengthInMonths();
			fail();
		} catch (IllegalStateException e) {
			// success
		}
	}
	
	/**
	 * {@link CalendarInterval#monthsInReverseIterator()}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test18_monthsInReverseIterator() throws Exception {
		CalendarInterval interval = CalendarInterval.year(2012);
		Iterator<CalendarMonth> itr = interval.monthsInReverseIterator();
		
		assertThat(itr.hasNext(), is(true));
		assertThat(itr.next(), is(CalendarMonth.from(2012, 12)));
		assertThat(itr.hasNext(), is(true));
		assertThat(itr.next(), is(CalendarMonth.from(2012, 11)));
		assertThat(itr.hasNext(), is(true));
		assertThat(itr.next(), is(CalendarMonth.from(2012, 10)));
		assertThat(itr.hasNext(), is(true));
		assertThat(itr.next(), is(CalendarMonth.from(2012, 9)));
		assertThat(itr.hasNext(), is(true));
		assertThat(itr.next(), is(CalendarMonth.from(2012, 8)));
		assertThat(itr.hasNext(), is(true));
		assertThat(itr.next(), is(CalendarMonth.from(2012, 7)));
		assertThat(itr.hasNext(), is(true));
		assertThat(itr.next(), is(CalendarMonth.from(2012, 6)));
		assertThat(itr.hasNext(), is(true));
		assertThat(itr.next(), is(CalendarMonth.from(2012, 5)));
		assertThat(itr.hasNext(), is(true));
		assertThat(itr.next(), is(CalendarMonth.from(2012, 4)));
		assertThat(itr.hasNext(), is(true));
		assertThat(itr.next(), is(CalendarMonth.from(2012, 3)));
		assertThat(itr.hasNext(), is(true));
		assertThat(itr.next(), is(CalendarMonth.from(2012, 2)));
		assertThat(itr.hasNext(), is(true));
		assertThat(itr.next(), is(CalendarMonth.from(2012, 1)));
		assertThat(itr.hasNext(), is(false));
		
		try {
			itr.next();
			fail();
		} catch (NoSuchElementException e) {
			// success
		}
		
		try {
			CalendarInterval.everFrom(CalendarDate.EPOCH_DATE).monthsInReverseIterator();
			fail();
		} catch (IllegalStateException e) {
			// success
		}
		
		CalendarInterval.everPreceding(CalendarDate.EPOCH_DATE).monthsInReverseIterator();
		// assert no exception
	}
	
	/**
	 * {@link CalendarInterval#weeksIterator()}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test19_weeksIterator() throws Exception {
		CalendarInterval interval = CalendarInterval.preceding(CalendarDate.from(2012, 2, 24), Duration.months(2));
		Iterator<CalendarWeek> itr = interval.weeksIterator();
		
		assertThat(itr.hasNext(), is(true));
		assertThat(itr.next(), is(CalendarWeek.from(2011, 51)));
		assertThat(itr.hasNext(), is(true));
		assertThat(itr.next(), is(CalendarWeek.from(2011, 52)));
		assertThat(itr.hasNext(), is(true));
		assertThat(itr.next(), is(CalendarWeek.from(2012, 1)));
		assertThat(itr.hasNext(), is(true));
		assertThat(itr.next(), is(CalendarWeek.from(2012, 2)));
		assertThat(itr.hasNext(), is(true));
		assertThat(itr.next(), is(CalendarWeek.from(2012, 3)));
		assertThat(itr.hasNext(), is(true));
		assertThat(itr.next(), is(CalendarWeek.from(2012, 4)));
		assertThat(itr.hasNext(), is(true));
		assertThat(itr.next(), is(CalendarWeek.from(2012, 5)));
		assertThat(itr.hasNext(), is(true));
		assertThat(itr.next(), is(CalendarWeek.from(2012, 6)));
		assertThat(itr.hasNext(), is(true));
		assertThat(itr.next(), is(CalendarWeek.from(2012, 7)));
		assertThat(itr.hasNext(), is(true));
		assertThat(itr.next(), is(CalendarWeek.from(2012, 8)));
		assertThat(itr.hasNext(), is(false));
		
		try {
			itr.next();
			fail();
		} catch (NoSuchElementException e) {
			// success
		}
		
		CalendarInterval.everFrom(CalendarDate.EPOCH_DATE).weeksIterator();
		// assert no exception
		
		try {
			CalendarInterval.everPreceding(CalendarDate.EPOCH_DATE).weeksIterator();
			fail();
		} catch (IllegalStateException e) {
			// success
		}
	}
	
	/**
	 * {@link CalendarInterval#weeksInReverseIterator()}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test20_weeksInReverseIterator() throws Exception {
		CalendarInterval interval = CalendarInterval.preceding(CalendarDate.from(2012, 2, 24), Duration.months(2));
		Iterator<CalendarWeek> itr = interval.weeksInReverseIterator();
		
		assertThat(itr.hasNext(), is(true));
		assertThat(itr.next(), is(CalendarWeek.from(2012, 8)));
		assertThat(itr.hasNext(), is(true));
		assertThat(itr.next(), is(CalendarWeek.from(2012, 7)));
		assertThat(itr.hasNext(), is(true));
		assertThat(itr.next(), is(CalendarWeek.from(2012, 6)));
		assertThat(itr.hasNext(), is(true));
		assertThat(itr.next(), is(CalendarWeek.from(2012, 5)));
		assertThat(itr.hasNext(), is(true));
		assertThat(itr.next(), is(CalendarWeek.from(2012, 4)));
		assertThat(itr.hasNext(), is(true));
		assertThat(itr.next(), is(CalendarWeek.from(2012, 3)));
		assertThat(itr.hasNext(), is(true));
		assertThat(itr.next(), is(CalendarWeek.from(2012, 2)));
		assertThat(itr.hasNext(), is(true));
		assertThat(itr.next(), is(CalendarWeek.from(2012, 1)));
		assertThat(itr.hasNext(), is(true));
		assertThat(itr.next(), is(CalendarWeek.from(2011, 52)));
		assertThat(itr.hasNext(), is(true));
		assertThat(itr.next(), is(CalendarWeek.from(2011, 51)));
		assertThat(itr.hasNext(), is(false));
		
		try {
			itr.next();
			fail();
		} catch (NoSuchElementException e) {
			// success
		}
		
		try {
			CalendarInterval.everFrom(CalendarDate.EPOCH_DATE).weeksInReverseIterator();
			fail();
		} catch (IllegalStateException e) {
			// success
		}
		
		CalendarInterval.everPreceding(CalendarDate.EPOCH_DATE).weeksInReverseIterator();
		// assert no exception
	}
	
	/**
	 * @throws Exception 例外が発生した場合
	 * @see <a href="http://dragon.xet.jp/jira/browse/BU-4">BU-4</a>
	 */
	@Test
	public void test21_intersect() throws Exception {
		CalendarInterval calint1 = CalendarInterval.inclusive(2012, 2, 26, 2012, 3, 23);
		CalendarInterval calint2 = CalendarInterval.inclusive(2012, 3, 4, 2012, 3, 31);
		
		CalendarInterval intersect = calint1.intersect(calint2);
		assertThat(intersect.hasLowerLimit(), is(true));
		assertThat(intersect.hasUpperLimit(), is(true));
		assertThat(intersect.lowerLimit(), is(CalendarDate.from(2012, 3, 4)));
		assertThat(intersect.upperLimit(), is(CalendarDate.from(2012, 3, 23)));
	}
}
