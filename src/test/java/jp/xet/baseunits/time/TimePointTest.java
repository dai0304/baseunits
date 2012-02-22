/*
 * Copyright 2011 Daisuke Miyamoto. (http://d.hatena.ne.jp/daisuke-m)
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
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.assertThat;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import jp.xet.baseunits.tests.SerializationTester;
import jp.xet.baseunits.time.HourOfDay.Meridian;

import org.junit.Test;

/**
 * {@link TimePoint}のテストクラス。
 */
public class TimePointTest {
	
	/** UTC */
	private static final TimeZone UTC = TimeZone.getTimeZone("Universal");
	
	/** UTC+0 */
	private static final TimeZone GMT = TimeZone.getTimeZone("GMT");
	
	/** UTC-8 */
	private static final TimeZone PT = TimeZone.getTimeZone("America/Los_Angeles");
	
	/** UTC-6 */
	private static final TimeZone CT = TimeZone.getTimeZone("America/Chicago");
	
	private static final TimePoint DEC19_2003 = TimePoint.atMidnightUTC(2003, 12, 19);
	
	private static final TimePoint DEC20_2003 = TimePoint.atMidnightUTC(2003, 12, 20);
	
	private static final TimePoint DEC21_2003 = TimePoint.atMidnightUTC(2003, 12, 21);
	
	private static final TimePoint DEC22_2003 = TimePoint.atMidnightUTC(2003, 12, 22);
	
	
	/**
	 * {@link TimePoint}のインスタンスがシリアライズできるかどうか検証する。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test01_Serialization() throws Exception {
		SerializationTester.assertCanBeSerialized(DEC19_2003);
	}
	
	/**
	 * UTCにおける {@link TimePoint}インスタンス生成テスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test02_CreationWithDefaultTimeZone() throws Exception {
		TimePoint expected = TimePoint.atUTC(2004, 1, 1, 0, 0, 0, 0);
		assertThat("at midnight", TimePoint.atMidnightUTC(2004, 1, 1), is(expected));
		assertThat("hours in 24hr clock", TimePoint.atUTC(2004, 1, 1, 0, 0), is(expected));
		assertThat("hours in 12hr clock", TimePoint.at12hr(2004, 1, 1, 12, Meridian.AM, 0, 0, 0, UTC), is(expected));
		assertThat("date from formatted String", TimePoint.parseUTCFrom("2004/1/1", "yyyy/MM/dd"), is(expected));
		assertThat("pm hours in 12hr clock", TimePoint.at12hr(2004, 1, 1, 12, Meridian.PM, 0, 0, 0, UTC),
				is(TimePoint.atUTC(2004, 1, 1, 12, 0)));
	}
	
	/**
	 * {@link TimePoint}インスタンス生成テスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test03_CreationWithTimeZone() throws Exception {
		/*
		 * TimePoints are based on miliseconds from the Epoc. They do not have a
		 * "timezone". When that basic value needs to be converted to or from a
		 * date or hours and minutes, then a Timezone must be specified or
		 * assumed. The default is always GMT. So creation operations which
		 * don't pass any Timezone assume the date, hours and minutes are GMT.
		 * The TimeLibrary does not use the default TimeZone operation in Java,
		 * the selection of the appropriate Timezone is left to the application.
		 */
		TimePoint utc10Hour = TimePoint.at(2004, 3, 5, 10, 10, 0, 0, GMT); // 2004-03-05T10:10Z
		TimePoint default10Hour = TimePoint.atUTC(2004, 3, 5, 10, 10, 0, 0); // 2004-03-05T10:10Z
		TimePoint pt2Hour = TimePoint.at(2004, 3, 5, 2, 10, 0, 0, PT); // 2004-03-05T10:10Z = 2004-03-05T02:10-08
		assertThat(default10Hour, is(utc10Hour));
		assertThat(pt2Hour, is(utc10Hour));
		
		TimePoint utc6Hour = TimePoint.at(2004, 3, 5, 6, 0, 0, 0, GMT);
		TimePoint ct0Hour = TimePoint.at(2004, 3, 5, 0, 0, 0, 0, CT);
		TimePoint ctMidnight = TimePoint.atMidnight(2004, 3, 5, CT);
		assertThat(ct0Hour, is(utc6Hour));
		assertThat(ctMidnight, is(utc6Hour));
	}
	
	/**
	 * {@link TimePoint#toString(String, TimeZone)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test04_StringFormat() throws Exception {
		TimePoint point = TimePoint.at(2004, 3, 12, 5, 3, 14, 0, PT);
		// Try stupid date/time format, so that it couldn't work by accident.
		assertThat(point.toString("M-yy-d m:h:s", PT), is("3-04-12 3:5:14"));
		assertThat(point.toString("M-yy-d", PT), is("3-04-12"));
	}
	
	/**
	 * {@link TimePoint#asJavaUtilDate()}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test05_AsJavaUtilDate() throws Exception {
		TimePoint dec20_2003 = TimePoint.atMidnightUTC(2003, 12, 20);
		assertThat(dec20_2003.asJavaUtilDate(), is(javaUtilDateDec20_2003()));
	}
	
	/**
	 * {@link TimePoint#backToMidnight(TimeZone)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test06_BackToMidnight() throws Exception {
		TimePoint threeOClock = TimePoint.atUTC(2004, 11, 22, 3, 0);
		assertThat(threeOClock.backToMidnight(UTC), is(TimePoint.atMidnightUTC(2004, 11, 22)));
		TimePoint thirteenOClock = TimePoint.atUTC(2004, 11, 22, 13, 0);
		assertThat(thirteenOClock.backToMidnight(UTC), is(TimePoint.atMidnightUTC(2004, 11, 22)));
	}
	
	/**
	 * {@link TimePoint#parseUTCFrom(String, String)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test07_FromString() throws Exception {
		TimePoint expected = TimePoint.atUTC(2004, 3, 29, 2, 44, 58, 0);
		String source = "2004-03-29 02:44:58";
		String pattern = "yyyy-MM-dd hh:mm:ss";
		assertThat(TimePoint.parseUTCFrom(source, pattern), is(expected));
	}
	
	/**
	 * {@link TimePoint#equals(Object)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test08_Equals() throws Exception {
		TimePoint createdFromJavaDate = TimePoint.from(javaUtilDateDec20_2003());
		TimePoint dec5_2003 = TimePoint.atMidnightUTC(2003, 12, 5);
		TimePoint dec20_2003 = TimePoint.atMidnightUTC(2003, 12, 20);
		assertThat(dec20_2003, is(createdFromJavaDate));
		assertThat(createdFromJavaDate.equals(dec20_2003), is(true));
		assertThat(createdFromJavaDate.equals(dec5_2003), is(false));
		assertThat(createdFromJavaDate.equals(new Object()), is(false));
		assertThat(createdFromJavaDate.equals(null), is(false));
	}
	
	/**
	 * {@link TimePoint#isSameDayAs(TimePoint, TimeZone)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test09_EqualsOverYearMonthDay() throws Exception {
		TimePoint thePoint = TimePoint.atUTC(2000, 1, 1, 8, 0);
		
		assertThat("exactly the same", TimePoint.atUTC(2000, 1, 1, 8, 0).isSameDayAs(thePoint, UTC), is(true));
		assertThat("same second", TimePoint.atUTC(2000, 1, 1, 8, 0, 0, 500).isSameDayAs(thePoint, UTC), is(true));
		assertThat("same minute", TimePoint.atUTC(2000, 1, 1, 8, 0, 30, 0).isSameDayAs(thePoint, UTC), is(true));
		assertThat("same hour", TimePoint.atUTC(2000, 1, 1, 8, 30, 0, 0).isSameDayAs(thePoint, UTC), is(true));
		assertThat("same day", TimePoint.atUTC(2000, 1, 1, 20, 0).isSameDayAs(thePoint, UTC), is(true));
		assertThat("midnight (in the moring), start of same day",
				TimePoint.atMidnightUTC(2000, 1, 1).isSameDayAs(thePoint, UTC), is(true));
		
		assertThat("midnight (night), start of next day", TimePoint.atMidnightUTC(2000, 1, 2)
			.isSameDayAs(thePoint, UTC), is(false));
		assertThat("next day", TimePoint.atUTC(2000, 1, 2, 8, 0).isSameDayAs(thePoint, UTC), is(false));
		assertThat("next month", TimePoint.atUTC(2000, 2, 1, 8, 0).isSameDayAs(thePoint, UTC), is(false));
		assertThat("next year", TimePoint.atUTC(2001, 1, 1, 8, 0).isSameDayAs(thePoint, UTC), is(false));
	}
	
	/**
	 * {@link TimePoint#isBefore(TimePoint)}, {@link TimePoint#isAfter(TimePoint)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test10_BeforeAfter() throws Exception {
		TimePoint dec5_2003 = TimePoint.atMidnightUTC(2003, 12, 5);
		TimePoint dec20_2003 = TimePoint.atMidnightUTC(2003, 12, 20);
		assertThat(dec5_2003.isBefore(dec20_2003), is(true));
		assertThat(dec20_2003.isBefore(dec20_2003), is(false));
		assertThat(dec20_2003.isBefore(dec5_2003), is(false));
		assertThat(dec5_2003.isAfter(dec20_2003), is(false));
		assertThat(dec20_2003.isAfter(dec20_2003), is(false));
		assertThat(dec20_2003.isAfter(dec5_2003), is(true));
		
		TimePoint oneSecondLater = TimePoint.atUTC(2003, 12, 20, 0, 0, 1, 0);
		assertThat(dec20_2003.isBefore(oneSecondLater), is(true));
		assertThat(dec20_2003.isAfter(oneSecondLater), is(false));
	}
	
	/**
	 * {@link TimePoint#plus(Duration)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test11_IncrementDuration() throws Exception {
		Duration twoDays = Duration.days(2);
		assertThat(DEC20_2003.plus(twoDays), is(DEC22_2003));
	}
	
	/**
	 * {@link TimePoint#minus(Duration)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test12_DecrementDuration() throws Exception {
		Duration twoDays = Duration.days(2);
		assertThat(DEC21_2003.minus(twoDays), is(DEC19_2003));
	}
	
	/**
	 * {@link TimePoint#isBefore(TimePointInterval)}, {@link TimePoint#isAfter(TimePointInterval)}のテスト。
	 * 
	 * This is only an integration test. The primary responsibility is in {@link TimePointInterval}.
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test13_BeforeAfterPeriod() throws Exception {
		TimePointInterval period = TimePointInterval.closed(DEC20_2003, DEC22_2003);
		assertThat(DEC19_2003.isBefore(period), is(true));
		assertThat(DEC19_2003.isAfter(period), is(false));
		assertThat(DEC20_2003.isBefore(period), is(false));
		assertThat(DEC20_2003.isAfter(period), is(false));
		assertThat(DEC21_2003.isBefore(period), is(false));
		assertThat(DEC21_2003.isAfter(period), is(false));
	}
	
	/**
	 * {@link TimePoint#nextDay()}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test14_NextDay() throws Exception {
		assertThat(DEC19_2003.nextDay(), is(DEC20_2003));
	}
	
	/**
	 * {@link TimePoint#compareTo(TimePoint)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test15_Compare() throws Exception {
		assertThat(DEC19_2003.compareTo(DEC20_2003), is(lessThan(0)));
		assertThat(DEC20_2003.compareTo(DEC19_2003), is(greaterThan(0)));
		assertThat(DEC20_2003.compareTo(DEC20_2003), is(0));
	}
	
	// 
	// 
	// 
	// 
	/**
	 * This test verifies bug #1336072 fix
	 * The problem is Duration.days(25) overflowed and became negative
	 * on a conversion from a long to int in the bowels of the model.
	 * We made the conversion unnecessary
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test16_PotentialProblemDueToOldUsageOf_Duration_toBaseUnitsUsage() throws Exception {
		TimePoint start = TimePoint.atUTC(2005, 10, 1, 0, 0);
		TimePoint end1 = start.plus(Duration.days(24));
		TimePoint end2 = start.plus(Duration.days(25));
		assertThat("Start timepoint is before end1", start.isBefore(end1), is(true));
		assertThat("and should of course be before end2", start.isBefore(end2), is(true));
	}
	
	/**
	 * TimePoint.at() ignores the minute parameter.
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test17_NotIgnoringMinuteParameter() throws Exception {
		TimePoint point = TimePoint.at(2006, 03, 22, 13, 45, 59, 499, UTC);
		assertThat(point.toString("yyyy-MM-dd HH:mm:ss:SSS", UTC), is("2006-03-22 13:45:59:499"));
		TimePoint pointNoMilli = TimePoint.at(2006, 03, 22, 13, 45, 59, UTC);
		assertThat(pointNoMilli.toString("yyyy-MM-dd HH:mm:ss:SSS", UTC), is("2006-03-22 13:45:59:000"));
	}
	
	/**
	 * {@link TimePoint#at(int, int, int, int, int, int, TimeZone)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test18_AtWithTimeZone() throws Exception {
		TimePoint someTime = TimePoint.at(2006, 6, 8, 16, 45, 33, TimeZone.getDefault());
		Calendar someTimeAsJavaCalendar = someTime.asJavaCalendar(TimeZone.getDefault());
		
		assertThat(someTimeAsJavaCalendar.get(Calendar.YEAR), is(2006));
		assertThat(someTimeAsJavaCalendar.get(Calendar.MONTH), is(Calendar.JUNE));
		assertThat(someTimeAsJavaCalendar.get(Calendar.DAY_OF_MONTH), is(8));
		assertThat(someTimeAsJavaCalendar.get(Calendar.HOUR_OF_DAY), is(16));
		assertThat(someTimeAsJavaCalendar.get(Calendar.MINUTE), is(45));
		assertThat(someTimeAsJavaCalendar.get(Calendar.SECOND), is(33));
	}
	
	/**
	 * {@link TimePoint#asTimeOfDay(TimeZone)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test19_() throws Exception {
		TimePoint am = TimePoint.atUTC(2005, 10, 1, 8, 20);
		TimeOfDay amTod = am.asTimeOfDay(UTC);
		assertThat(amTod, is(TimeOfDay.from(8, 20)));
		
		TimePoint pm = TimePoint.atUTC(2006, 6, 8, 16, 45, 33);
		TimeOfDay pmTod = pm.asTimeOfDay(UTC);
		assertThat(pmTod, is(TimeOfDay.from(16, 45, 33)));
	}
	
	private Date javaUtilDateDec20_2003() {
		Calendar calendar = Calendar.getInstance(UTC);
		calendar.clear(); // non-deterministic without this!!!
		calendar.set(Calendar.YEAR, 2003);
		calendar.set(Calendar.MONTH, Calendar.DECEMBER);
		calendar.set(Calendar.DATE, 20);
		return calendar.getTime();
	}
	
}
