/**
 * Copyright (c) 2004 Domain Language, Inc. (http://domainlanguage.com) This
 * free software is distributed under the "MIT" licence.
 * For more information, see http://timeandmoney.sourceforge.net.
 */

package jp.xet.timeandmoney.time;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.assertThat;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import jp.xet.timeandmoney.tests.SerializationTester;

import org.junit.Test;

/**
 * {@link TimePoint}のテストクラス。
 */
public class TimePointTest {
	
	private static final String AM = "AM";
	
	private static final String PM = "PM";
	
	private TimeZone gmt = TimeZone.getTimeZone("Universal");
	
	private TimeZone pt = TimeZone.getTimeZone("America/Los_Angeles");
	
	private TimeZone ct = TimeZone.getTimeZone("America/Chicago");
	
	private TimePoint dec19_2003 = TimePoint.atMidnightGMT(2003, 12, 19);
	
	private TimePoint dec20_2003 = TimePoint.atMidnightGMT(2003, 12, 20);
	
	private TimePoint dec21_2003 = TimePoint.atMidnightGMT(2003, 12, 21);
	
	private TimePoint dec22_2003 = TimePoint.atMidnightGMT(2003, 12, 22);
	

	/**
	 * {@link TimePoint}のインスタンスがシリアライズできるかどうか検証する。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test01_Serialization() throws Exception {
		SerializationTester.assertCanBeSerialized(dec19_2003);
	}
	
	/**
	 * GMTにおける {@link TimePoint}インスタンス生成テスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test02_CreationWithDefaultTimeZone() throws Exception {
		TimePoint expected = TimePoint.atGMT(2004, 1, 1, 0, 0, 0, 0);
		assertThat("at midnight", TimePoint.atMidnightGMT(2004, 1, 1), is(expected));
		assertThat("hours in 24hr clock", TimePoint.atGMT(2004, 1, 1, 0, 0), is(expected));
		assertThat("hours in 12hr clock", TimePoint.at12hr(2004, 1, 1, 12, AM, 0, 0, 0, gmt), is(expected));
		assertThat("date from formatted String", TimePoint.parseGMTFrom("2004/1/1", "yyyy/MM/dd"), is(expected));
		assertThat("pm hours in 12hr clock", TimePoint.at12hr(2004, 1, 1, 12, PM, 0, 0, 0, gmt),
				is(TimePoint.atGMT(2004, 1, 1, 12, 0)));
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
		TimePoint gmt10Hour = TimePoint.at(2004, 3, 5, 10, 10, 0, 0, gmt);
		TimePoint default10Hour = TimePoint.atGMT(2004, 3, 5, 10, 10, 0, 0);
		TimePoint pt2Hour = TimePoint.at(2004, 3, 5, 2, 10, 0, 0, pt);
		assertThat(default10Hour, is(gmt10Hour));
		assertThat(pt2Hour, is(gmt10Hour));
		
		TimePoint gmt6Hour = TimePoint.at(2004, 3, 5, 6, 0, 0, 0, gmt);
		TimePoint ct0Hour = TimePoint.at(2004, 3, 5, 0, 0, 0, 0, ct);
		TimePoint ctMidnight = TimePoint.atMidnight(2004, 3, 5, ct);
		assertThat(ct0Hour, is(gmt6Hour));
		assertThat(ctMidnight, is(gmt6Hour));
	}
	
	/**
	 * {@link TimePoint#toString(String, TimeZone)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test04_StringFormat() throws Exception {
		TimePoint point = TimePoint.at(2004, 3, 12, 5, 3, 14, 0, pt);
		// Try stupid date/time format, so that it couldn't work by accident.
		assertThat(point.toString("M-yy-d m:h:s", pt), is("3-04-12 3:5:14"));
		assertThat(point.toString("M-yy-d", pt), is("3-04-12"));
	}
	
	/**
	 * {@link TimePoint#asJavaUtilDate()}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test05_AsJavaUtilDate() throws Exception {
		TimePoint dec20_2003 = TimePoint.atMidnightGMT(2003, 12, 20);
		assertThat(dec20_2003.asJavaUtilDate(), is(javaUtilDateDec20_2003()));
	}
	
	/**
	 * {@link TimePoint#backToMidnight(TimeZone)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test06_BackToMidnight() throws Exception {
		TimePoint threeOClock = TimePoint.atGMT(2004, 11, 22, 3, 0);
		assertThat(threeOClock.backToMidnight(gmt), is(TimePoint.atMidnightGMT(2004, 11, 22)));
		TimePoint thirteenOClock = TimePoint.atGMT(2004, 11, 22, 13, 0);
		assertThat(thirteenOClock.backToMidnight(gmt), is(TimePoint.atMidnightGMT(2004, 11, 22)));
	}
	
	/**
	 * {@link TimePoint#parseGMTFrom(String, String)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test07_FromString() throws Exception {
		TimePoint expected = TimePoint.atGMT(2004, 3, 29, 2, 44, 58, 0);
		String source = "2004-03-29 02:44:58";
		String pattern = "yyyy-MM-dd hh:mm:ss";
		assertThat(TimePoint.parseGMTFrom(source, pattern), is(expected));
	}
	
	/**
	 * {@link TimePoint#equals(Object)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test08_Equals() throws Exception {
		TimePoint createdFromJavaDate = TimePoint.from(javaUtilDateDec20_2003());
		TimePoint dec5_2003 = TimePoint.atMidnightGMT(2003, 12, 5);
		TimePoint dec20_2003 = TimePoint.atMidnightGMT(2003, 12, 20);
		assertThat(dec20_2003, is(createdFromJavaDate));
		assertThat(createdFromJavaDate.equals(dec20_2003), is(true));
		assertThat(createdFromJavaDate.equals(dec5_2003), is(false));
	}
	
	/**
	 * {@link TimePoint#isSameDayAs(TimePoint, TimeZone)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test09_EqualsOverYearMonthDay() throws Exception {
		TimePoint thePoint = TimePoint.atGMT(2000, 1, 1, 8, 0);
		TimeZone gmt = TimeZone.getTimeZone("Universal");
		
		assertThat("exactly the same", TimePoint.atGMT(2000, 1, 1, 8, 0).isSameDayAs(thePoint, gmt), is(true));
		assertThat("same second", TimePoint.atGMT(2000, 1, 1, 8, 0, 0, 500).isSameDayAs(thePoint, gmt), is(true));
		assertThat("same minute", TimePoint.atGMT(2000, 1, 1, 8, 0, 30, 0).isSameDayAs(thePoint, gmt), is(true));
		assertThat("same hour", TimePoint.atGMT(2000, 1, 1, 8, 30, 0, 0).isSameDayAs(thePoint, gmt), is(true));
		assertThat("same day", TimePoint.atGMT(2000, 1, 1, 20, 0).isSameDayAs(thePoint, gmt), is(true));
		assertThat("midnight (in the moring), start of same day",
				TimePoint.atMidnightGMT(2000, 1, 1).isSameDayAs(thePoint, gmt), is(true));
		
		assertThat("midnight (night), start of next day", TimePoint.atMidnightGMT(2000, 1, 2)
			.isSameDayAs(thePoint, gmt), is(false));
		assertThat("next day", TimePoint.atGMT(2000, 1, 2, 8, 0).isSameDayAs(thePoint, gmt), is(false));
		assertThat("next month", TimePoint.atGMT(2000, 2, 1, 8, 0).isSameDayAs(thePoint, gmt), is(false));
		assertThat("next year", TimePoint.atGMT(2001, 1, 1, 8, 0).isSameDayAs(thePoint, gmt), is(false));
	}
	
	/**
	 * {@link TimePoint#isBefore(TimePoint)}, {@link TimePoint#isAfter(TimePoint)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test10_BeforeAfter() throws Exception {
		TimePoint dec5_2003 = TimePoint.atMidnightGMT(2003, 12, 5);
		TimePoint dec20_2003 = TimePoint.atMidnightGMT(2003, 12, 20);
		assertThat(dec5_2003.isBefore(dec20_2003), is(true));
		assertThat(dec20_2003.isBefore(dec20_2003), is(false));
		assertThat(dec20_2003.isBefore(dec5_2003), is(false));
		assertThat(dec5_2003.isAfter(dec20_2003), is(false));
		assertThat(dec20_2003.isAfter(dec20_2003), is(false));
		assertThat(dec20_2003.isAfter(dec5_2003), is(true));
		
		TimePoint oneSecondLater = TimePoint.atGMT(2003, 12, 20, 0, 0, 1, 0);
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
		assertThat(dec20_2003.plus(twoDays), is(dec22_2003));
	}
	
	/**
	 * {@link TimePoint#minus(Duration)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test12_DecrementDuration() throws Exception {
		Duration twoDays = Duration.days(2);
		assertThat(dec21_2003.minus(twoDays), is(dec19_2003));
	}
	
	/**
	 * {@link TimePoint#isBefore(TimeInterval)}, {@link TimePoint#isAfter(TimeInterval)}のテスト。
	 * 
	 * This is only an integration test. The primary responsibility is in {@link TimeInterval}.
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test13_BeforeAfterPeriod() throws Exception {
		TimeInterval period = TimeInterval.closed(dec20_2003, dec22_2003);
		assertThat(dec19_2003.isBefore(period), is(true));
		assertThat(dec19_2003.isAfter(period), is(false));
		assertThat(dec20_2003.isBefore(period), is(false));
		assertThat(dec20_2003.isAfter(period), is(false));
		assertThat(dec21_2003.isBefore(period), is(false));
		assertThat(dec21_2003.isAfter(period), is(false));
	}
	
	/**
	 * {@link TimePoint#nextDay()}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test14_NextDay() throws Exception {
		assertThat(dec19_2003.nextDay(), is(dec20_2003));
	}
	
	/**
	 * {@link TimePoint#compareTo(TimePoint)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test15_Compare() throws Exception {
		assertThat(dec19_2003.compareTo(dec20_2003), is(lessThan(0)));
		assertThat(dec20_2003.compareTo(dec19_2003), is(greaterThan(0)));
		assertThat(dec20_2003.compareTo(dec20_2003), is(0));
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
		TimePoint start = TimePoint.atGMT(2005, 10, 1, 0, 0);
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
		TimePoint point = TimePoint.at(2006, 03, 22, 13, 45, 59, 499, gmt);
		assertThat(point.toString("yyyy-MM-dd HH:mm:ss:SSS", gmt), is("2006-03-22 13:45:59:499"));
		TimePoint pointNoMilli = TimePoint.at(2006, 03, 22, 13, 45, 59, gmt);
		assertThat(pointNoMilli.toString("yyyy-MM-dd HH:mm:ss:SSS", gmt), is("2006-03-22 13:45:59:000"));
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
	
	private Date javaUtilDateDec20_2003() {
		Calendar calendar = Calendar.getInstance(gmt);
		calendar.clear(); // non-deterministic without this!!!
		calendar.set(Calendar.YEAR, 2003);
		calendar.set(Calendar.MONTH, Calendar.DECEMBER);
		calendar.set(Calendar.DATE, 20);
		return calendar.getTime();
	}
	
}
