/**
 * Copyright (c) 2004 Domain Language, Inc. (http://domainlanguage.com) This
 * free software is distributed under the "MIT" licence.
 * For more information, see http://timeandmoney.sourceforge.net.
 */

package jp.xet.timeandmoney.time;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Calendar;
import java.util.TimeZone;

import jp.xet.timeandmoney.tests.SerializationTester;

import org.junit.Test;

/**
 * {@link CalendarDate}のテストクラス。
 */
public class CalendarDateTest {
	
	private CalendarDate feb17 = CalendarDate.from(2003, 2, 17);
	
	private CalendarDate mar13 = CalendarDate.from(2003, 3, 13);
	
	private CalendarDate may1 = CalendarDate.date(2004, 5, 1);
	
	private CalendarDate may20 = CalendarDate.date(2004, 5, 20);
	
	private TimeZone gmt = TimeZone.getTimeZone("Universal");
	
	private TimeZone ct = TimeZone.getTimeZone("America/Chicago");
	

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
		TimePoint feb17StartAsCt = feb17.startAsTimePoint(ct);
		TimePoint feb17Hour0Ct = TimePoint.atMidnight(2003, 2, 17, ct);
		assertThat(feb17StartAsCt, is(feb17Hour0Ct));
	}
	
	/**
	 * {@link CalendarDate#asTimeInterval(TimeZone)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test04_AsTimeInterval() throws Exception {
		TimeInterval feb17AsCt = feb17.asTimeInterval(ct);
		TimePoint feb17Hour0Ct = TimePoint.atMidnight(2003, 2, 17, ct);
		TimePoint feb18Hour0Ct = TimePoint.atMidnight(2003, 2, 18, ct);
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
	 * {@link CalendarDate#from(String, String)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test06_FromFormattedString() throws Exception {
		assertThat(CalendarDate.from("2/17/2003", "M/d/yyyy"), is(feb17));
		//Now a nonsense pattern, to make sure it isn't succeeding by accident.
		assertThat(CalendarDate.from("#17-03/02 2003", "#d-yy/MM yyyy"), is(feb17));
	}
	
	/**
	 * {@link CalendarDate#from(TimePoint, TimeZone)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test07_FromTimePoint() throws Exception {
		TimePoint feb18Hour0Ct = TimePoint.atMidnight(2003, 2, 18, gmt);
		CalendarDate mapped = CalendarDate.from(feb18Hour0Ct, ct);
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
	}
	
	/**
	 * {@link CalendarDate#dayOfWeek()}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test09_DayOfWeek() throws Exception {
		CalendarDate date = CalendarDate.date(2004, 11, 6);
		assertThat(date.dayOfWeek(), is(DayOfWeek.SATURDAY));
		date = CalendarDate.date(2007, 1, 1);
		assertThat(date.dayOfWeek(), is(DayOfWeek.MONDAY));
	}
	
	/**
	 * {@link CalendarDate#nextDay()}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test10_NextDay() throws Exception {
		CalendarDate feb28_2004 = CalendarDate.date(2004, 2, 28);
		assertThat(feb28_2004.nextDay(), is(CalendarDate.date(2004, 2, 29)));
		assertThat(feb28_2004.nextDay().nextDay(), is(CalendarDate.date(2004, 3, 1)));
	}
	
	/**
	 * {@link CalendarDate#previousDay()}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test11_PreviousDay() throws Exception {
		CalendarDate mar1_2004 = CalendarDate.date(2004, 3, 1);
		assertThat(mar1_2004.previousDay(), is(CalendarDate.date(2004, 2, 29)));
		assertThat(mar1_2004.previousDay().previousDay(), is(CalendarDate.date(2004, 2, 28)));
	}
	
	/**
	 * {@link CalendarDate#monthCalendarInterval()}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test12_Month() throws Exception {
		CalendarDate nov6_2004 = CalendarDate.date(2004, 11, 6);
		CalendarInterval nov2004 = CalendarInterval.inclusive(2004, 11, 1, 2004, 11, 30);
		assertThat(nov6_2004.monthCalendarInterval(), is(nov2004));
		
		CalendarDate dec6_2004 = CalendarDate.date(2004, 12, 6);
		CalendarInterval dec2004 = CalendarInterval.inclusive(2004, 12, 1, 2004, 12, 31);
		assertThat(dec6_2004.monthCalendarInterval(), is(dec2004));
		
		CalendarDate feb9_2004 = CalendarDate.date(2004, 2, 9);
		CalendarInterval feb2004 = CalendarInterval.inclusive(2004, 2, 1, 2004, 2, 29);
		assertThat(feb9_2004.monthCalendarInterval(), is(feb2004));
		
		CalendarDate feb9_2003 = CalendarDate.date(2003, 2, 9);
		CalendarInterval feb2003 = CalendarInterval.inclusive(2003, 2, 1, 2003, 2, 28);
		assertThat(feb9_2003.monthCalendarInterval(), is(feb2003));
		
	}
	
	/**
	 * {@link CalendarDate#toString()}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test13_ToString() throws Exception {
		CalendarDate date = CalendarDate.date(2004, 5, 28);
		assertThat(date.toString(), is("2004-05-28"));
	}
	
	/**
	 * {@link CalendarDate#asJavaCalendarUniversalZoneMidnight()}のテスト。（内部API）
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test14_ConversionToJavaUtil() throws Exception {
		Calendar expected = Calendar.getInstance(gmt);
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
	
}
