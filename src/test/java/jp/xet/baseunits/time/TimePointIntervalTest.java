/*
 * Copyright 2010-2019 Miyamoto Daisuke.
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
package jp.xet.baseunits.time;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.Iterator;
import java.util.NoSuchElementException;

import jp.xet.baseunits.intervals.Interval;
import jp.xet.baseunits.tests.SerializationTester;

import org.junit.Test;

/**
 * {@link TimePointInterval}のテストクラス。
 */
public class TimePointIntervalTest {
	
	private TimePoint dec19_2003 = TimePoint.atMidnightUTC(2003, 12, 19);
	
	private TimePoint dec20_2003 = TimePoint.atMidnightUTC(2003, 12, 20);
	
	private TimePoint dec21_2003 = TimePoint.atMidnightUTC(2003, 12, 21);
	
	private TimePoint dec22_2003 = TimePoint.atMidnightUTC(2003, 12, 22);
	
	private TimePoint dec23_2003 = TimePoint.atMidnightUTC(2003, 12, 23);
	
	
	/**
	 * {@link TimePointInterval}のインスタンスがシリアライズできるかどうか検証する。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test01_Serialization() throws Exception {
		TimePointInterval interval = TimePointInterval.closed(dec20_2003, dec22_2003);
		SerializationTester.assertCanBeSerialized(interval);
	}
	
	/**
	 * {@link TimePointInterval#isBefore(TimePoint)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test02_BeforeClosed() throws Exception {
		TimePointInterval interval = TimePointInterval.closed(dec20_2003, dec22_2003);
		// Only the upper end should matter for this test.
		assertThat(interval.isBefore(dec21_2003), is(false));
		assertThat(interval.isBefore(dec22_2003), is(false));
		assertThat(interval.isBefore(dec23_2003), is(true));
	}
	
	/**
	 * {@link TimePointInterval#isAfter(TimePoint)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test03_AfterClosed() throws Exception {
		TimePointInterval interval = TimePointInterval.closed(dec20_2003, dec22_2003);
		// Only the lower end should matter for this test.
		assertThat(interval.isAfter(dec19_2003), is(true));
		assertThat(interval.isAfter(dec20_2003), is(false));
		assertThat(interval.isAfter(dec21_2003), is(false));
	}
	
	/**
	 * {@link TimePointInterval#includes(TimePoint)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test04_IncludesClosed() throws Exception {
		TimePointInterval interval = TimePointInterval.closed(dec20_2003, dec22_2003);
		assertThat(interval.includes(dec19_2003), is(false));
		assertThat(interval.includes(dec20_2003), is(true));
		assertThat(interval.includes(dec21_2003), is(true));
		assertThat(interval.includes(dec22_2003), is(true));
		assertThat(interval.includes(dec23_2003), is(false));
	}
	
	/**
	 * {@link TimePointInterval#isBefore(TimePoint)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test05_BeforeOpen() throws Exception {
		TimePointInterval interval = TimePointInterval.open(dec20_2003, dec22_2003);
		// Only the upper end should matter for this test.
		assertThat(interval.isBefore(dec21_2003), is(false));
		assertThat(interval.isBefore(dec22_2003), is(true));
		assertThat(interval.isBefore(dec23_2003), is(true));
	}
	
	/**
	 * {@link TimePointInterval#isAfter(TimePoint)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test06_AfterOpen() throws Exception {
		TimePointInterval interval = TimePointInterval.open(dec20_2003, dec22_2003);
		// Only the lower end should matter for this test.
		assertThat(interval.isAfter(dec19_2003), is(true));
		assertThat(interval.isAfter(dec20_2003), is(true));
		assertThat(interval.isAfter(dec21_2003), is(false));
	}
	
	/**
	 * {@link TimePointInterval#includes(TimePoint)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test07_IncludesOpen() throws Exception {
		TimePointInterval interval = TimePointInterval.open(dec20_2003, dec22_2003);
		assertThat(interval.includes(dec19_2003), is(false));
		assertThat(interval.includes(dec20_2003), is(false));
		assertThat(interval.includes(dec21_2003), is(true));
		assertThat(interval.includes(dec22_2003), is(false));
		assertThat(interval.includes(dec23_2003), is(false));
	}
	
	/**
	 * {@link TimePointInterval#includes(TimePoint)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test08_IncludesHalfOpen() throws Exception {
		TimePointInterval interval = TimePointInterval.over(dec20_2003, true, dec22_2003, false);
		assertThat(interval.includes(dec19_2003), is(false));
		assertThat(interval.includes(dec20_2003), is(true));
		assertThat(interval.includes(dec21_2003), is(true));
		assertThat(interval.includes(dec22_2003), is(false));
		assertThat(interval.includes(dec23_2003), is(false));
	}
	
	/**
	 * {@link TimePointInterval#startingFrom(TimePoint, Duration)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test09_CreateWithDurationFrom() throws Exception {
		Duration twoDays = Duration.days(2);
		TimePointInterval following = TimePointInterval.startingFrom(dec20_2003, true, twoDays, true);
		assertThat("[ dec20", following.start(), is(dec20_2003));
		assertThat("dec 22]", following.end(), is(dec22_2003));
		
	}
	
	/**
	 * {@link TimePointInterval#preceding(TimePoint, Duration)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test10_CreateWithDurationUntil() throws Exception {
		Duration twoDays = Duration.days(2);
		TimePointInterval preceding = TimePointInterval.preceding(dec21_2003, true, twoDays, true);
		assertThat("[ dec19", preceding.start(), is(dec19_2003));
		assertThat("dec21 )", preceding.end(), is(dec21_2003));
	}
	
	/**
	 * {@link TimePointInterval#over(TimePoint, TimePoint)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test11_DefaultFromPoints() throws Exception {
		/*       Default is closed start, open end [start, end)
		         which is the most common convention. For example,
		         Days include 12:00am at their start, but do not
		         include the 12:00am that end them.
		*/
		TimePointInterval interval = TimePointInterval.over(dec20_2003, dec22_2003);
		assertThat(interval.includes(dec19_2003), is(false));
		assertThat(interval.includes(dec20_2003), is(true));
		assertThat(interval.includes(dec21_2003), is(true));
		assertThat(interval.includes(dec22_2003), is(false));
		assertThat(interval.includes(dec23_2003), is(false));
	}
	
	/**
	 * {@link TimePointInterval#startingFrom(TimePoint, Duration)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test12_DefaultFromDuration() throws Exception {
		/*       Default is closed start, open end [start, end)
		         which is the most common convention. For example,
		         Days include 12:00am at their start, but do not
		         include the 12:00am that end them.
		*/
		TimePointInterval interval = TimePointInterval.startingFrom(dec20_2003, Duration.hours(48));
		assertThat(interval.includes(dec19_2003), is(false));
		assertThat(interval.includes(dec20_2003), is(true));
		assertThat(interval.includes(dec21_2003), is(true));
		assertThat(interval.includes(dec22_2003), is(false));
		assertThat(interval.includes(dec23_2003), is(false));
	}
	
	/**
	 * {@link TimePointInterval#everFrom(TimePoint)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test13_EverFrom() throws Exception {
		TimePointInterval afterDec20 = TimePointInterval.everFrom(dec20_2003);
		assertThat(afterDec20.includes(TimePoint.atMidnightUTC(2062, 3, 5)), is(true));
		assertThat(afterDec20.includes(TimePoint.atMidnightUTC(1776, 7, 4)), is(false));
		assertThat(afterDec20.includes(dec20_2003), is(true));
	}
	
	/**
	 * {@link TimePointInterval#everPreceding(TimePoint)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test14_EverUntil() throws Exception {
		TimePointInterval afterDec20 = TimePointInterval.everPreceding(dec20_2003);
		assertThat(afterDec20.includes(TimePoint.atMidnightUTC(2062, 3, 5)), is(false));
		assertThat(afterDec20.includes(TimePoint.atMidnightUTC(1776, 7, 4)), is(true));
		assertThat(afterDec20.includes(dec20_2003), is(false));
	}
	
	/**
	 * {@link TimePointInterval#open(TimePoint, TimePoint)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test15_Length() throws Exception {
		TimePointInterval interval = TimePointInterval.open(dec20_2003, dec22_2003);
		assertThat(interval.length(), is(Duration.days(2)));
		
		TimePoint first = TimePoint.atUTC(2004, 1, 1, 1, 1, 1, 1);
		TimePoint second = TimePoint.atUTC(2004, 1, 6, 5, 4, 3, 2);
		interval = TimePointInterval.closed(first, second);
		Duration expectedLength = Duration.daysHoursMinutesSecondsMilliseconds(5, 4, 3, 2, 1);
		assertThat(interval.length(), is(expectedLength));
		
		try {
			TimePointInterval.everFrom(TimePoint.EPOCH).length();
			fail();
		} catch (IllegalStateException e) {
			// success
		}
		
		try {
			TimePointInterval.everPreceding(TimePoint.EPOCH).length();
			fail();
		} catch (IllegalStateException e) {
			// success
		}
	}
	
	/**
	 * {@link TimePointInterval#daysIterator()}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test16_DaysIterator() throws Exception {
		TimePoint start = TimePoint.atUTC(2004, 2, 5, 10, 0);
		TimePoint end = TimePoint.atUTC(2004, 2, 8, 2, 0);
		TimePointInterval interval = TimePointInterval.over(start, end);
		Iterator<TimePoint> it = interval.daysIterator();
		assertThat(it.hasNext(), is(true));
		assertThat(it.next(), is(start));
		assertThat(it.hasNext(), is(true));
		assertThat(it.next(), is(TimePoint.atUTC(2004, 2, 6, 10, 0)));
		assertThat(it.hasNext(), is(true));
		assertThat(it.next(), is(TimePoint.atUTC(2004, 2, 7, 10, 0)));
		assertThat(it.hasNext(), is(false));
		try {
			it.next();
			fail();
		} catch (NoSuchElementException e) {
			// success
		}
		
		TimePointInterval interval2 = TimePointInterval.everPreceding(end);
		try {
			interval2.daysIterator();
			fail();
		} catch (IllegalStateException e) {
			// success
		}
		
		TimePointInterval interval3 = TimePointInterval.everFrom(start);
		Iterator<TimePoint> it3 = interval3.daysIterator();
		for (int i = 0; i < 100; i++) {
			assertThat(it3.hasNext(), is(true));
		}
	}
	
	/**
	 * {@link TimePointInterval#subintervalIterator(Duration)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test17_SubintervalIterator() throws Exception {
		try {
			TimePointInterval.everPreceding(dec19_2003).subintervalIterator(Duration.days(1));
			fail();
		} catch (IllegalStateException e) {
			// success
		}
		
		TimePoint d4_h10 = TimePoint.atUTC(2004, 2, 4, 10, 0);
		TimePoint d6_h10 = TimePoint.atUTC(2004, 2, 6, 10, 0);
		TimePoint d8_h10 = TimePoint.atUTC(2004, 2, 8, 10, 0);
		TimePoint d9_h2 = TimePoint.atUTC(2004, 2, 9, 2, 0);
		
		TimePointInterval interval = TimePointInterval.over(d4_h10, d9_h2);
		Iterator<TimePointInterval> iterator = interval.subintervalIterator(Duration.days(2));
		assertThat(iterator.hasNext(), is(true));
		assertThat(iterator.next(), is(TimePointInterval.over(d4_h10, d6_h10)));
		assertThat(iterator.hasNext(), is(true));
		assertThat(iterator.next(), is(TimePointInterval.over(d6_h10, d8_h10)));
		assertThat(iterator.hasNext(), is(false));
		try {
			iterator.next();
			fail();
		} catch (NoSuchElementException e) {
			// success
		}
		
		iterator = interval.subintervalIterator(Duration.weeks(1));
		assertThat(iterator.hasNext(), is(false));
		
		TimePoint h2 = d9_h2;
		TimePoint h3_m30 = TimePoint.atUTC(2004, 2, 9, 3, 30);
		TimePoint h5 = TimePoint.atUTC(2004, 2, 9, 5, 0);
		TimePoint h6_m30 = TimePoint.atUTC(2004, 2, 9, 6, 30);
		TimePoint h8 = TimePoint.atUTC(2004, 2, 9, 8, 0);
		
		TimePointInterval interval2 = TimePointInterval.over(h2, h8);
		iterator = interval2.subintervalIterator(Duration.minutes(90));
		assertThat(iterator.hasNext(), is(true));
		assertThat(iterator.next(), is(TimePointInterval.over(h2, h3_m30)));
		assertThat(iterator.hasNext(), is(true));
		assertThat(iterator.next(), is(TimePointInterval.over(h3_m30, h5)));
		assertThat(iterator.hasNext(), is(true));
		assertThat(iterator.next(), is(TimePointInterval.over(h5, h6_m30)));
		assertThat(iterator.hasNext(), is(true));
		assertThat(iterator.next(), is(TimePointInterval.over(h6_m30, h8)));
		assertThat(iterator.hasNext(), is(false));
		
		try {
			iterator.next();
			fail();
		} catch (NoSuchElementException e) {
			// success
		}
	}
	
	/**
	 * {@link TimePointInterval#intersects(Interval)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test18_Intersection() throws Exception {
		TimePointInterval i19_22 = TimePointInterval.over(dec19_2003, dec22_2003);
		TimePointInterval i20_23 = TimePointInterval.over(dec20_2003, dec23_2003);
		TimePointInterval intersection = i19_22.intersect(i20_23);
		assertThat(intersection.start(), is(dec20_2003));
		assertThat(intersection.end(), is(dec22_2003));
		assertThat("intersects true", i19_22.intersects(i20_23), is(true));
		
		TimePointInterval i19_21 = TimePointInterval.over(dec19_2003, dec21_2003);
		TimePointInterval i22_23 = TimePointInterval.over(dec22_2003, dec23_2003);
		assertThat("intersects false", i19_21.intersects(i22_23), is(false));
	}
	
	/**
	 * {@link TimePointInterval#preceding(TimePoint, Duration)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test19_preceding() throws Exception {
		assertThat(TimePointInterval.preceding(dec23_2003, Duration.days(3)),
				is(TimePointInterval.over(dec20_2003, true, dec23_2003, false)));
	}
}
