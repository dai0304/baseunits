/**
 * Copyright (c) 2004 Domain Language, Inc. (http://domainlanguage.com) This
 * free software is distributed under the "MIT" licence. See file licence.txt.
 * For more information, see http://timeandmoney.sourceforge.net.
 */

package jp.xet.timeandmoney.time;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;

import jp.xet.timeandmoney.intervals.Interval;
import jp.xet.timeandmoney.tests.SerializationTester;

import org.junit.Test;

/**
 * {@link CalendarInterval}のテストクラス。
 */
public class CalendarIntervalTest {
	
	private CalendarDate may1 = CalendarDate.date(2004, 5, 1);
	
	private CalendarDate may2 = CalendarDate.date(2004, 5, 2);
	
	private CalendarDate may3 = CalendarDate.date(2004, 5, 3);
	
	private CalendarDate may14 = CalendarDate.date(2004, 5, 14);
	
	private CalendarDate may20 = CalendarDate.date(2004, 5, 20);
	
	private CalendarDate may31 = CalendarDate.date(2004, 5, 31);
	
	private CalendarDate apr15 = CalendarDate.date(2004, 4, 15);
	
	private CalendarDate jun1 = CalendarDate.date(2004, 6, 1);
	
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
		TimeInterval day = may20.asTimeInterval(ct);
		assertThat("May20Ct", day.start(), is(TimePoint.atMidnight(2004, 5, 20, ct)));
	}
	
	/**
	 * {@link CalendarInterval#includes(CalendarDate)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test03_Includes() throws Exception {
		assertThat("apr15", may.includes(apr15), is(false));
		assertThat("may1", may.includes(may1), is(true));
		assertThat("may20", may.includes(may20), is(true));
		assertThat("jun1", may.includes(jun1), is(false));
		assertThat("may", may.covers(may), is(true));
	}
	
	/**
	 * {@link CalendarInterval#equals(Interval)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test04_Equals() throws Exception {
		assertThat(may.equals(CalendarInterval.inclusive(may1, may31)), is(true));
		assertThat(may.equals(may1), is(false));
		assertThat(may.equals(CalendarInterval.inclusive(may1, may20)), is(false));
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
		
		CalendarInterval apr15_jun1 = CalendarInterval.inclusive(apr15, jun1);
		iterator = apr15_jun1.subintervalIterator(Duration.months(1));
		assertThat(iterator.hasNext(), is(true));
		assertThat(iterator.next(), is(apr15.through(may14)));
		assertThat(iterator.hasNext(), is(false));
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
		assertThat(i.toString(), is("[2007-06-05, Infinity]"));
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
}
