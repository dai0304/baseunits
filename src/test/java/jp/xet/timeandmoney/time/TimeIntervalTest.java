/*
 * Copyright 2010 TRICREO, Inc. (http://tricreo.jp/)
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
package jp.xet.timeandmoney.time;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.Iterator;
import java.util.NoSuchElementException;

import jp.xet.timeandmoney.intervals.Interval;
import jp.xet.timeandmoney.tests.SerializationTester;

import org.junit.Test;

/**
 * {@link TimeInterval}のテストクラス。
 */
public class TimeIntervalTest {
	
	private TimePoint dec19_2003 = TimePoint.atMidnightGMT(2003, 12, 19);
	
	private TimePoint dec20_2003 = TimePoint.atMidnightGMT(2003, 12, 20);
	
	private TimePoint dec21_2003 = TimePoint.atMidnightGMT(2003, 12, 21);
	
	private TimePoint dec22_2003 = TimePoint.atMidnightGMT(2003, 12, 22);
	
	private TimePoint dec23_2003 = TimePoint.atMidnightGMT(2003, 12, 23);
	

	/**
	 * {@link TimeInterval}のインスタンスがシリアライズできるかどうか検証する。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test01_Serialization() throws Exception {
		TimeInterval interval = TimeInterval.closed(dec20_2003, dec22_2003);
		SerializationTester.assertCanBeSerialized(interval);
	}
	
	/**
	 * {@link TimeInterval#isBefore(TimePoint)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test02_BeforeClosed() throws Exception {
		TimeInterval interval = TimeInterval.closed(dec20_2003, dec22_2003);
		// Only the upper end should matter for this test.
		assertThat(interval.isBefore(dec21_2003), is(false));
		assertThat(interval.isBefore(dec22_2003), is(false));
		assertThat(interval.isBefore(dec23_2003), is(true));
	}
	
	/**
	 * {@link TimeInterval#isAfter(TimePoint)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test03_AfterClosed() throws Exception {
		TimeInterval interval = TimeInterval.closed(dec20_2003, dec22_2003);
		// Only the lower end should matter for this test.
		assertThat(interval.isAfter(dec19_2003), is(true));
		assertThat(interval.isAfter(dec20_2003), is(false));
		assertThat(interval.isAfter(dec21_2003), is(false));
	}
	
	/**
	 * {@link TimeInterval#includes(TimePoint)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test04_IncludesClosed() throws Exception {
		TimeInterval interval = TimeInterval.closed(dec20_2003, dec22_2003);
		assertThat(interval.includes(dec19_2003), is(false));
		assertThat(interval.includes(dec20_2003), is(true));
		assertThat(interval.includes(dec21_2003), is(true));
		assertThat(interval.includes(dec22_2003), is(true));
		assertThat(interval.includes(dec23_2003), is(false));
	}
	
	/**
	 * {@link TimeInterval#isBefore(TimePoint)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test05_BeforeOpen() throws Exception {
		TimeInterval interval = TimeInterval.open(dec20_2003, dec22_2003);
		// Only the upper end should matter for this test.
		assertThat(interval.isBefore(dec21_2003), is(false));
		assertThat(interval.isBefore(dec22_2003), is(true));
		assertThat(interval.isBefore(dec23_2003), is(true));
	}
	
	/**
	 * {@link TimeInterval#isAfter(TimePoint)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test06_AfterOpen() throws Exception {
		TimeInterval interval = TimeInterval.open(dec20_2003, dec22_2003);
		// Only the lower end should matter for this test.
		assertThat(interval.isAfter(dec19_2003), is(true));
		assertThat(interval.isAfter(dec20_2003), is(true));
		assertThat(interval.isAfter(dec21_2003), is(false));
	}
	
	/**
	 * {@link TimeInterval#includes(TimePoint)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test07_IncludesOpen() throws Exception {
		TimeInterval interval = TimeInterval.open(dec20_2003, dec22_2003);
		assertThat(interval.includes(dec19_2003), is(false));
		assertThat(interval.includes(dec20_2003), is(false));
		assertThat(interval.includes(dec21_2003), is(true));
		assertThat(interval.includes(dec22_2003), is(false));
		assertThat(interval.includes(dec23_2003), is(false));
	}
	
	/**
	 * {@link TimeInterval#includes(TimePoint)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test08_IncludesHalfOpen() throws Exception {
		TimeInterval interval = TimeInterval.over(dec20_2003, true, dec22_2003, false);
		assertThat(interval.includes(dec19_2003), is(false));
		assertThat(interval.includes(dec20_2003), is(true));
		assertThat(interval.includes(dec21_2003), is(true));
		assertThat(interval.includes(dec22_2003), is(false));
		assertThat(interval.includes(dec23_2003), is(false));
	}
	
	/**
	 * {@link TimeInterval#startingFrom(TimePoint, Duration)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test09_CreateWithDurationFrom() throws Exception {
		Duration twoDays = Duration.days(2);
		TimeInterval following = TimeInterval.startingFrom(dec20_2003, true, twoDays, true);
		assertThat("[ dec20", following.start(), is(dec20_2003));
		assertThat("dec 22]", following.end(), is(dec22_2003));
		
	}
	
	/**
	 * {@link TimeInterval#preceding(TimePoint, Duration)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test10_CreateWithDurationUntil() throws Exception {
		Duration twoDays = Duration.days(2);
		TimeInterval preceding = TimeInterval.preceding(dec21_2003, true, twoDays, true);
		assertThat("[ dec19", preceding.start(), is(dec19_2003));
		assertThat("dec21 )", preceding.end(), is(dec21_2003));
	}
	
	/**
	 * {@link TimeInterval#over(TimePoint, TimePoint)}のテスト。
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
		TimeInterval interval = TimeInterval.over(dec20_2003, dec22_2003);
		assertThat(interval.includes(dec19_2003), is(false));
		assertThat(interval.includes(dec20_2003), is(true));
		assertThat(interval.includes(dec21_2003), is(true));
		assertThat(interval.includes(dec22_2003), is(false));
		assertThat(interval.includes(dec23_2003), is(false));
	}
	
	/**
	 * {@link TimeInterval#startingFrom(TimePoint, Duration)}のテスト。
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
		TimeInterval interval = TimeInterval.startingFrom(dec20_2003, Duration.hours(48));
		assertThat(interval.includes(dec19_2003), is(false));
		assertThat(interval.includes(dec20_2003), is(true));
		assertThat(interval.includes(dec21_2003), is(true));
		assertThat(interval.includes(dec22_2003), is(false));
		assertThat(interval.includes(dec23_2003), is(false));
	}
	
	/**
	 * {@link TimeInterval#everFrom(TimePoint)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test13_EverFrom() throws Exception {
		TimeInterval afterDec20 = TimeInterval.everFrom(dec20_2003);
		assertThat(afterDec20.includes(TimePoint.atMidnightGMT(2062, 3, 5)), is(true));
		assertThat(afterDec20.includes(TimePoint.atMidnightGMT(1776, 7, 4)), is(false));
		assertThat(afterDec20.includes(dec20_2003), is(true));
	}
	
	/**
	 * {@link TimeInterval#everPreceding(TimePoint)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test14_EverUntil() throws Exception {
		TimeInterval afterDec20 = TimeInterval.everPreceding(dec20_2003);
		assertThat(afterDec20.includes(TimePoint.atMidnightGMT(2062, 3, 5)), is(false));
		assertThat(afterDec20.includes(TimePoint.atMidnightGMT(1776, 7, 4)), is(true));
		assertThat(afterDec20.includes(dec20_2003), is(false));
	}
	
	/**
	 * {@link TimeInterval#open(TimePoint, TimePoint)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test15_Length() throws Exception {
		TimeInterval interval = TimeInterval.open(dec20_2003, dec22_2003);
		assertThat(interval.length(), is(Duration.days(2)));
		
		TimePoint first = TimePoint.atGMT(2004, 1, 1, 1, 1, 1, 1);
		TimePoint second = TimePoint.atGMT(2004, 1, 6, 5, 4, 3, 2);
		interval = TimeInterval.closed(first, second);
		Duration expectedLength = Duration.daysHoursMinutesSecondsMilliseconds(5, 4, 3, 2, 1);
		assertThat(interval.length(), is(expectedLength));
	}
	
	/**
	 * {@link TimeInterval#daysIterator()}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test16_DaysIterator() throws Exception {
		TimePoint start = TimePoint.atGMT(2004, 2, 5, 10, 0);
		TimePoint end = TimePoint.atGMT(2004, 2, 8, 2, 0);
		TimeInterval interval = TimeInterval.over(start, end);
		Iterator<TimePoint> it = interval.daysIterator();
		assertThat(it.hasNext(), is(true));
		assertThat(it.next(), is(start));
		assertThat(it.hasNext(), is(true));
		assertThat(it.next(), is(TimePoint.atGMT(2004, 2, 6, 10, 0)));
		assertThat(it.hasNext(), is(true));
		assertThat(it.next(), is(TimePoint.atGMT(2004, 2, 7, 10, 0)));
		assertThat(it.hasNext(), is(false));
		try {
			it.next();
			fail();
		} catch (NoSuchElementException e) {
			// success
		}
		
		TimeInterval interval2 = TimeInterval.everPreceding(end);
		try {
			interval2.daysIterator();
			fail();
		} catch (IllegalStateException e) {
			// success
		}
		
		TimeInterval interval3 = TimeInterval.everFrom(start);
		Iterator<TimePoint> it3 = interval3.daysIterator();
		for (int i = 0; i < 100; i++) {
			assertThat(it3.hasNext(), is(true));
		}
	}
	
	/**
	 * {@link TimeInterval#subintervalIterator(Duration)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test17_SubintervalIterator() throws Exception {
		TimePoint d4_h10 = TimePoint.atGMT(2004, 2, 4, 10, 0);
		TimePoint d6_h10 = TimePoint.atGMT(2004, 2, 6, 10, 0);
		TimePoint d8_h10 = TimePoint.atGMT(2004, 2, 8, 10, 0);
		TimePoint d9_h2 = TimePoint.atGMT(2004, 2, 9, 2, 0);
		
		TimeInterval interval = TimeInterval.over(d4_h10, d9_h2);
		Iterator<TimeInterval> iterator = interval.subintervalIterator(Duration.days(2));
		assertThat(iterator.hasNext(), is(true));
		assertThat(iterator.next(), is(TimeInterval.over(d4_h10, d6_h10)));
		assertThat(iterator.hasNext(), is(true));
		assertThat(iterator.next(), is(TimeInterval.over(d6_h10, d8_h10)));
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
		TimePoint h3_m30 = TimePoint.atGMT(2004, 2, 9, 3, 30);
		TimePoint h5 = TimePoint.atGMT(2004, 2, 9, 5, 0);
		TimePoint h6_m30 = TimePoint.atGMT(2004, 2, 9, 6, 30);
		TimePoint h8 = TimePoint.atGMT(2004, 2, 9, 8, 0);
		
		TimeInterval interval2 = TimeInterval.over(h2, h8);
		iterator = interval2.subintervalIterator(Duration.minutes(90));
		assertThat(iterator.hasNext(), is(true));
		assertThat(iterator.next(), is(TimeInterval.over(h2, h3_m30)));
		assertThat(iterator.hasNext(), is(true));
		assertThat(iterator.next(), is(TimeInterval.over(h3_m30, h5)));
		assertThat(iterator.hasNext(), is(true));
		assertThat(iterator.next(), is(TimeInterval.over(h5, h6_m30)));
		assertThat(iterator.hasNext(), is(true));
		assertThat(iterator.next(), is(TimeInterval.over(h6_m30, h8)));
		assertThat(iterator.hasNext(), is(false));
		
		try {
			iterator.next();
			fail();
		} catch (NoSuchElementException e) {
			// success
		}
	}
	
	/**
	 * {@link TimeInterval#intersects(Interval)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test18_Intersection() throws Exception {
		TimeInterval i19_22 = TimeInterval.over(dec19_2003, dec22_2003);
		TimeInterval i20_23 = TimeInterval.over(dec20_2003, dec23_2003);
		TimeInterval intersection = i19_22.intersect(i20_23);
		assertThat(intersection.start(), is(dec20_2003));
		assertThat(intersection.end(), is(dec22_2003));
		assertThat("intersects true", i19_22.intersects(i20_23), is(true));
		
		TimeInterval i19_21 = TimeInterval.over(dec19_2003, dec21_2003);
		TimeInterval i22_23 = TimeInterval.over(dec22_2003, dec23_2003);
		assertThat("intersects false", i19_21.intersects(i22_23), is(false));
	}
	
}
