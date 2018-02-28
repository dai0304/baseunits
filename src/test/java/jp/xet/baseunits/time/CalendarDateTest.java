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
package jp.xet.baseunits.time;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.Calendar;
import java.util.TimeZone;

import jp.xet.baseunits.tests.SerializationTester;

import org.junit.Test;

/**
 * {@link CalendarDate}のテストクラス。
 */
public class CalendarDateTest {
	
	private CalendarDate feb17 = CalendarDate.from(2003, 2, 17);
	
	private CalendarDate mar13 = CalendarDate.from(2003, 3, 13);
	
	private CalendarDate may1 = CalendarDate.from(2004, 5, 1);
	
	private CalendarDate may20 = CalendarDate.from(2004, 5, 20);
	
	private static final TimeZone UTC = TimeZone.getTimeZone("Universal");
	
	private static final TimeZone CT = TimeZone.getTimeZone("America/Chicago");
	
	
	/**
	 * {@link CalendarDate}のインスタンスがシリアライズできるかどうか検証する。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test01_Serialization() throws Exception {
		SerializationTester.assertCanBeSerialized(feb17);
	}
	
	/**
	 * {@link CalendarDate#isBefore(CalendarDate)} と {@link CalendarDate#isAfter(CalendarDate)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test02_Comparison() throws Exception {
		assertThat(feb17.isBefore(mar13), is(true));
		assertThat(mar13.isBefore(feb17), is(false));
		assertThat(feb17.isBefore(feb17), is(false));
		assertThat(feb17.isAfter(mar13), is(false));
		assertThat(mar13.isAfter(feb17), is(true));
		assertThat(feb17.isAfter(feb17), is(false));
	}
	
	/**
	 * {@link CalendarDate#startAsTimePoint(TimeZone)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test03_StartAsTimePoint() throws Exception {
		TimePoint feb17StartAsCt = feb17.startAsTimePoint(CT);
		TimePoint feb17Hour0Ct = TimePoint.atMidnight(2003, 2, 17, CT);
		assertThat(feb17StartAsCt, is(feb17Hour0Ct));
	}
	
	/**
	 * {@link CalendarDate#asTimePointInterval(TimeZone)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test04_AsTimeInterval() throws Exception {
		TimePointInterval feb17AsCt = feb17.asTimePointInterval(CT);
		TimePoint feb17Hour0Ct = TimePoint.atMidnight(2003, 2, 17, CT);
		TimePoint feb18Hour0Ct = TimePoint.atMidnight(2003, 2, 18, CT);
		assertThat("start", feb17AsCt.start(), is(feb17Hour0Ct));
		assertThat("end", feb17AsCt.end(), is(feb18Hour0Ct));
	}
	
	/**
	 * {@link CalendarDate#toString(String)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test05_FormattedString() throws Exception {
		assertThat(feb17.toString("M/d/yyyy"), is("2/17/2003"));
		//Now a nonsense pattern, to make sure it isn't succeeding by accident.
		assertThat(feb17.toString("#d-yy/MM yyyy"), is("#17-03/02 2003"));
	}
	
	/**
	 * {@link CalendarDate#parse(String, String)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test06_FromFormattedString() throws Exception {
		assertThat(CalendarDate.parse("2/17/2003", "M/d/yyyy"), is(feb17));
		//Now a nonsense pattern, to make sure it isn't succeeding by accident.
		assertThat(CalendarDate.parse("#17-03/02 2003", "#d-yy/MM yyyy"), is(feb17));
		assertThat(CalendarDate.parse("foo-bar_2003-02-17_16.log", "'foo-bar_'yyyy-MM-dd"), is(feb17));
	}
	
	/**
	 * {@link CalendarDate#from(TimePoint, TimeZone)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test07_FromTimePoint() throws Exception {
		TimePoint feb18Hour0Ct = TimePoint.atMidnight(2003, 2, 18, UTC);
		CalendarDate mapped = CalendarDate.from(feb18Hour0Ct, CT);
		assertThat(mapped, is(CalendarDate.from(2003, 2, 17)));
	}
	
	/**
	 * {@link CalendarDate#equals(Object)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test08_Equals() throws Exception {
		assertThat(feb17.equals(feb17), is(true));
		assertThat(feb17.equals(mar13), is(false));
		assertThat(feb17.equals(null), is(false));
		assertThat(feb17.equals(new Object()), is(false));
	}
	
	/**
	 * {@link CalendarDate#dayOfWeek()}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test09_DayOfWeek() throws Exception {
		CalendarDate date = CalendarDate.from(2004, 11, 6);
		assertThat(date.dayOfWeek(), is(DayOfWeek.SATURDAY));
		date = CalendarDate.from(2007, 1, 1);
		assertThat(date.dayOfWeek(), is(DayOfWeek.MONDAY));
	}
	
	/**
	 * {@link CalendarDate#nextDay()}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test10_NextDay() throws Exception {
		CalendarDate feb28_2004 = CalendarDate.from(2004, 2, 28);
		assertThat(feb28_2004.nextDay(), is(CalendarDate.from(2004, 2, 29)));
		assertThat(feb28_2004.nextDay().nextDay(), is(CalendarDate.from(2004, 3, 1)));
	}
	
	/**
	 * {@link CalendarDate#previousDay()}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test11_PreviousDay() throws Exception {
		CalendarDate mar1_2004 = CalendarDate.from(2004, 3, 1);
		assertThat(mar1_2004.previousDay(), is(CalendarDate.from(2004, 2, 29)));
		assertThat(mar1_2004.previousDay().previousDay(), is(CalendarDate.from(2004, 2, 28)));
	}
	
	/**
	 * {@link CalendarDate#asMonthInterval()}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test12_Month() throws Exception {
		CalendarDate nov6_2004 = CalendarDate.from(2004, 11, 6);
		CalendarInterval nov2004 = CalendarInterval.inclusive(2004, 11, 1, 2004, 11, 30);
		assertThat(nov6_2004.asMonthInterval(), is(nov2004));
		
		CalendarDate dec6_2004 = CalendarDate.from(2004, 12, 6);
		CalendarInterval dec2004 = CalendarInterval.inclusive(2004, 12, 1, 2004, 12, 31);
		assertThat(dec6_2004.asMonthInterval(), is(dec2004));
		
		CalendarDate feb9_2004 = CalendarDate.from(2004, 2, 9);
		CalendarInterval feb2004 = CalendarInterval.inclusive(2004, 2, 1, 2004, 2, 29);
		assertThat(feb9_2004.asMonthInterval(), is(feb2004));
		
		CalendarDate feb9_2003 = CalendarDate.from(2003, 2, 9);
		CalendarInterval feb2003 = CalendarInterval.inclusive(2003, 2, 1, 2003, 2, 28);
		assertThat(feb9_2003.asMonthInterval(), is(feb2003));
		
	}
	
	/**
	 * {@link CalendarDate#toString()}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test13_ToString() throws Exception {
		CalendarDate date = CalendarDate.from(2004, 5, 28);
		assertThat(date.toString(), is("2004-05-28"));
	}
	
	/**
	 * {@link CalendarDate#asJavaCalendarUniversalZoneMidnight()}のテスト。（内部API）
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test14_ConversionToJavaUtil() throws Exception {
		Calendar expected = Calendar.getInstance(UTC);
		expected.setMinimalDaysInFirstWeek(4);
		expected.setFirstDayOfWeek(Calendar.MONDAY);
		expected.set(Calendar.YEAR, 1969);
		expected.set(Calendar.MONTH, Calendar.JULY);
		expected.set(Calendar.DATE, 20);
		expected.set(Calendar.HOUR, 0);
		expected.set(Calendar.AM_PM, Calendar.AM);
		expected.set(Calendar.MINUTE, 0);
		expected.set(Calendar.SECOND, 0);
		expected.set(Calendar.MILLISECOND, 0);
		
		CalendarDate date = CalendarDate.from(1969, 7, 20);
		Calendar actual = date.asJavaCalendarUniversalZoneMidnight();
		assertThat(actual.get(Calendar.HOUR), is(expected.get(Calendar.HOUR)));
		assertThat(actual.get(Calendar.AM_PM), is(expected.get(Calendar.AM_PM)));
		assertThat(actual.get(Calendar.HOUR_OF_DAY), is(expected.get(Calendar.HOUR_OF_DAY)));
		assertThat(actual, is(expected));
	}
	
	/**
	 * {@link CalendarDate#plusDays(int)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test15_DaysAdd() throws Exception {
		assertThat(may1.plusDays(19), is(may20));
	}
	
	/**
	 * {@link CalendarDate#from(java.util.Date, TimeZone)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test16_from() throws Exception {
		assertThat(CalendarDate.from(new java.util.Date(0L), UTC), is(CalendarDate.EPOCH_DATE));
	}
	
	/**
	 * {@link CalendarDate#asCalendarWeek()}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test17_asCalendarWeek() throws Exception {
		assertThat(CalendarDate.EPOCH_DATE.asCalendarWeek(), is(CalendarWeek.from(1970, 1)));
		assertThat(CalendarDate.from(1978, 3, 4).asCalendarWeek(), is(CalendarWeek.from(1978, 9)));
	}
	
	/**
	 * {@link CalendarDate#asJavaUtilDate(TimeZone)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test18_asJavaUtilDate() throws Exception {
		assertThat(CalendarDate.EPOCH_DATE.asJavaUtilDate(UTC), is(new java.util.Date(0L)));
	}
	
	/**
	 * {@link CalendarDate#asYearInterval()}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test19_asYearInterval() throws Exception {
		assertThat(CalendarDate.EPOCH_DATE.asYearInterval(), is(CalendarInterval.year(1970)));
		assertThat(CalendarDate.from(1978, 3, 4).asYearInterval(), is(CalendarInterval.year(1978)));
	}
	
	/**
	 * {@link CalendarDate#compareTo(CalendarDate)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test20_compareTo() throws Exception {
		CalendarDate daisukeBirth = CalendarDate.from(1978, 3, 4);
		CalendarDate yukariBirth = CalendarDate.from(1980, 2, 26);
		
		assertThat(daisukeBirth.compareTo(yukariBirth), is(lessThan(0)));
		assertThat(yukariBirth.compareTo(daisukeBirth), is(greaterThan(0)));
		assertThat(daisukeBirth.compareTo(daisukeBirth), is(0));
		
		try {
			daisukeBirth.compareTo(null);
			fail();
		} catch (NullPointerException e) {
			// success
		}
	}
	
	/**
	 * {@link CalendarDate#plus(Duration)}, {@link CalendarDate#minus(Duration)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test21_plus_minus() throws Exception {
		CalendarDate daisukeBirth = CalendarDate.from(1978, 3, 4);
		CalendarDate daisukeBirthEve = CalendarDate.from(1978, 3, 3);
		CalendarDate daisukeBirthNext = CalendarDate.from(1978, 3, 5);
		assertThat(daisukeBirth.plus(Duration.hours(15)), is(daisukeBirth));
		assertThat(daisukeBirth.plus(Duration.hours(29)), is(daisukeBirth));
		assertThat(daisukeBirthEve.plus(Duration.days(2)), is(daisukeBirthNext));
		assertThat(daisukeBirth.minus(Duration.hours(15)), is(daisukeBirth));
		assertThat(daisukeBirth.minus(Duration.hours(29)), is(daisukeBirth));
		assertThat(daisukeBirthNext.minus(Duration.days(2)), is(daisukeBirthEve));
	}
	
	/**
	 * {@link CalendarDate#getYear()}, {@link CalendarDate#getMonthOfYear()}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test22_() throws Exception {
		assertThat(CalendarDate.from(1978, 3, 4).getYear(), is(1978));
		assertThat(CalendarDate.from(1978, 3, 4).getMonthOfYear(), is(MonthOfYear.MAR));
	}
	
	/**
	 * {@link CalendarDate#getAge(CalendarDate)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test23_getAge() throws Exception {
		assertThat(CalendarDate.from(1978, 3, 4).getAge(CalendarDate.from(2011, 12, 31)), is(33L));
		assertThat(CalendarDate.from(1978, 3, 4).getAge(CalendarDate.from(2012, 1, 1)), is(33L));
		assertThat(CalendarDate.from(1978, 3, 4).getAge(CalendarDate.from(2012, 2, 28)), is(33L));
		assertThat(CalendarDate.from(1978, 3, 4).getAge(CalendarDate.from(2012, 3, 1)), is(33L));
		assertThat(CalendarDate.from(1978, 3, 4).getAge(CalendarDate.from(2012, 3, 2)), is(33L));
		assertThat(CalendarDate.from(1978, 3, 4).getAge(CalendarDate.from(2012, 3, 3)), is(33L));
		assertThat(CalendarDate.from(1978, 3, 4).getAge(CalendarDate.from(2012, 3, 4)), is(34L));
		assertThat(CalendarDate.from(1978, 3, 4).getAge(CalendarDate.from(2012, 3, 5)), is(34L));
		assertThat(CalendarDate.from(1978, 3, 4).getAge(CalendarDate.from(2012, 7, 19)), is(34L));
		
		assertThat(CalendarDate.from(2012, 7, 19).getAge(CalendarDate.from(1978, 3, 4)), is(34L));
	}
}
