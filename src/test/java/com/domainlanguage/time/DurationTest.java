/**
 * Copyright (c) 2004 Domain Language, Inc. (http://domainlanguage.com) This
 * free software is distributed under the "MIT" licence. See file licence.txt.
 * For more information, see http://timeandmoney.sourceforge.net.
 */

package com.domainlanguage.time;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.math.BigDecimal;

import com.domainlanguage.base.Rounding;
import com.domainlanguage.tests.SerializationTester;

import org.junit.Test;

/**
 * {@link Duration}のテストクラス。
 * 
 * @author daisuke
 */
public class DurationTest {
	
	/**
	 * {@link Duration}のインスタンスがシリアライズできるかどうか検証する。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test01_Serialization() throws Exception {
		SerializationTester.assertCanBeSerialized(Duration.days(1));
	}
	
	/**
	 * {@link Duration#addedTo(TimePoint)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test02_AddMillisecondsToPoint() throws Exception {
		TimePoint dec20At1 = TimePoint.atGMT(2003, 12, 20, 01, 0, 0, 0);
		TimePoint dec22At1 = TimePoint.atGMT(2003, 12, 22, 01, 0, 0, 0);
		Duration twoDays = Duration.days(2);
		assertThat(twoDays.addedTo(dec20At1), is(dec22At1));
	}
	
	/**
	 * {@link Duration#addedTo(TimePoint)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test03_AddMonthsToPoint() throws Exception {
		TimePoint oct20At1 = TimePoint.atGMT(2003, 10, 20, 01, 0, 0, 0);
		TimePoint dec20At1 = TimePoint.atGMT(2003, 12, 20, 01, 0, 0, 0);
		Duration twoMonths = Duration.months(2);
		assertThat(twoMonths.addedTo(oct20At1), is(dec20At1));
	}
	
	/**
	 * {@link Duration#subtractedFrom(TimePoint)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test04_SubtractMillisecondsFromPoint() throws Exception {
		TimePoint dec20At1 = TimePoint.atGMT(2003, 12, 20, 01, 0, 0, 0);
		TimePoint dec18At1 = TimePoint.atGMT(2003, 12, 18, 01, 0, 0, 0);
		Duration twoDays = Duration.days(2);
		assertThat(twoDays.subtractedFrom(dec20At1), is(dec18At1));
	}
	
	/**
	 * {@link Duration#subtractedFrom(TimePoint)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test05_SubtractMonthsFromPoint() throws Exception {
		TimePoint oct20At1 = TimePoint.atGMT(2003, 10, 20, 01, 0, 0, 0);
		TimePoint dec20At1 = TimePoint.atGMT(2003, 12, 20, 01, 0, 0, 0);
		Duration twoMonths = Duration.months(2);
		assertThat(twoMonths.subtractedFrom(dec20At1), is(oct20At1));
		
		TimePoint dec20At1_2001 = TimePoint.atGMT(2001, 12, 20, 01, 0, 0, 0);
		Duration twoYears = Duration.years(2);
		assertThat(twoYears.subtractedFrom(dec20At1), is(dec20At1_2001));
	}
	
	/**
	 * {@link Duration#subtractedFrom(CalendarDate)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test06_SubtractFromCalendarDate() throws Exception {
		CalendarDate oct20 = CalendarDate.from(2003, 10, 20);
		CalendarDate dec20 = CalendarDate.from(2003, 12, 20);
		
		Duration twoMonths = Duration.months(2);
		assertThat(twoMonths.subtractedFrom(dec20), is(oct20));
		
		Duration sixtyoneDays = Duration.days(61);
		assertThat(sixtyoneDays.subtractedFrom(dec20), is(oct20));
		
		CalendarDate dec20_2001 = CalendarDate.from(2001, 12, 20);
		Duration twoYears = Duration.years(2);
		assertThat(twoYears.subtractedFrom(dec20), is(dec20_2001));
	}
	
	/**
	 * {@link Duration#addedTo(CalendarDate)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test07_AddToCalendarDate() throws Exception {
		CalendarDate oct20_2003 = CalendarDate.from(2003, 10, 20);
		CalendarDate dec20_2003 = CalendarDate.from(2003, 12, 20);
		
		Duration twoMonths = Duration.months(2);
		assertThat(twoMonths.addedTo(oct20_2003), is(dec20_2003));
		
		Duration sixtyoneDays = Duration.days(61);
		assertThat(sixtyoneDays.addedTo(oct20_2003), is(dec20_2003));
		
		CalendarDate dec20_2001 = CalendarDate.from(2001, 12, 20);
		Duration twoYears = Duration.years(2);
		assertThat(twoYears.addedTo(dec20_2001), is(dec20_2003));
	}
	
	/**
	 * {@link Duration#inBaseUnits()}のテスト。（内部API）
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test08_ConversionToBaseUnits() throws Exception {
		Duration twoSeconds = Duration.seconds(2);
		assertThat(twoSeconds.inBaseUnits(), is(2000L));
	}
	
	/**
	 * {@link Duration#equals(Object)}のテスト。
	 * 
	 * <p>単位が違っていても、baseUnit換算できちんと比較できること。
	 * baseUnitに互換性がなければ、必ず{@code false}となること。</p>
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test09_Equals() throws Exception {
		assertThat(Duration.hours(48), is(Duration.days(2)));
		assertThat(Duration.quarters(4), is(Duration.years(1)));
		assertThat(Duration.months(6), is(Duration.quarters(2)));
		
		assertThat(Duration.months(1), is(not(Duration.days(28))));
		assertThat(Duration.months(1), is(not(Duration.days(29))));
		assertThat(Duration.months(1), is(not(Duration.days(30))));
		assertThat(Duration.months(1), is(not(Duration.days(31))));
	}
	
	/**
	 * {@link Duration#plus(Duration)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test10_Add() throws Exception {
		assertThat(Duration.hours(24).plus(Duration.days(1)), is(Duration.days(2)));
		assertThat(Duration.months(1).plus(Duration.quarters(1)), is(Duration.months(4)));
		
		try {
			Duration.days(1).plus(Duration.months(1));
			fail();
		} catch (IllegalArgumentException e) {
			// success
		}
	}
	
	/**
	 * {@link Duration#minus(Duration)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test11_Subtract() throws Exception {
		assertThat(Duration.days(3).minus(Duration.hours(24)), is(Duration.days(2)));
		assertThat(Duration.quarters(1).minus(Duration.months(1)), is(Duration.months(2)));
		
		try {
			Duration.months(2).plus(Duration.days(3));
			fail();
		} catch (IllegalArgumentException e) {
			// success
		}
	}
	
	/**
	 * {@link Duration#dividedBy(Duration)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test12_Divide() throws Exception {
		assertThat(Duration.days(3).dividedBy(Duration.days(2)).decimalValue(1, Rounding.DOWN), is(new BigDecimal(1.5)));
	}
	
	/**
	 * {@link Duration#toNormalizedString()}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test13_ToNormalizedString() throws Exception {
		assertThat(Duration.minutes(0).toNormalizedString(), is(""));
		assertThat(Duration.days(0).toNormalizedString(), is(""));
		
		assertThat(Duration.days(1).toNormalizedString(), is("1 day"));
		assertThat(Duration.days(2).toNormalizedString(), is("2 days"));
		
		Duration complicatedDuration = Duration.daysHoursMinutesSecondsMilliseconds(5, 4, 3, 2, 1);
		assertThat(complicatedDuration.toNormalizedString(), is("5 days, 4 hours, 3 minutes, 2 seconds, 1 millisecond"));
		assertThat(Duration.days(365).toNormalizedString(), is("52 weeks, 1 day"));
		
		assertThat(Duration.quarters(3).toNormalizedString(), is("3 quarters"));
		assertThat(Duration.quarters(4).toNormalizedString(), is("1 year"));
		assertThat(Duration.quarters(8).toNormalizedString(), is("2 years"));
	}
	
	/**
	 * TODO for daisuke
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test14_ToNormalizedStringMonthBased() throws Exception {
		assertThat(Duration.months(2).toNormalizedString(), is("2 months"));
		assertThat(Duration.months(16).toNormalizedString(), is("1 year, 1 quarter, 1 month"));
	}
	
	/**
	 * TODO for daisuke
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test15_ToString() throws Exception {
		assertThat(Duration.weeks(3).toString(), is("21 days")); //Weeks are not conventional to read.
		assertThat(Duration.months(16).toString(), is("1 year, 4 months")); //Quarters are not conventionalto read.
	}
	
	// TODO: More edge cases and exceptions (like nonconvertable units).
	/**
	 * TODO for daisuke
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test16_Compare() throws Exception {
		Duration oneHour = Duration.hours(1);
		Duration twoHours = Duration.hours(2);
		Duration sixtyMinutes = Duration.minutes(60);
		assertThat(oneHour.compareTo(twoHours), is(lessThan(0)));
		assertThat(oneHour.compareTo(sixtyMinutes), is(0));
		assertThat(twoHours.compareTo(oneHour), is(greaterThan(0)));
	}
	
	/**
	 * TODO for daisuke
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test17_StartingFromTimePoint() throws Exception {
		TimePoint dec20At1 = TimePoint.atGMT(2003, 12, 20, 01, 0, 0, 0);
		TimePoint dec20At3 = TimePoint.atGMT(2003, 12, 20, 03, 0, 0, 0);
		TimeInterval dec20_1_3 = dec20At1.until(dec20At3);
		assertThat(Duration.hours(2).startingFrom(dec20At1), is(dec20_1_3));
	}
	
	/**
	 * TODO for daisuke
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test18_StartingFromCalendarDate() throws Exception {
		CalendarDate dec20 = CalendarDate.date(2004, 12, 20);
		CalendarDate dec26 = CalendarDate.date(2004, 12, 26);
		CalendarInterval dec20_26 = dec20.through(dec26);
		assertThat(Duration.days(7).startingFrom(dec20), is(dec20_26));
	}
	
	/**
	 * TODO for daisuke
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test19_NormalizedUnit() throws Exception {
		assertThat(Duration.seconds(30).normalizedUnit(), is(TimeUnit.second));
		assertThat(Duration.seconds(120).normalizedUnit(), is(TimeUnit.minute));
		assertThat(Duration.hours(24).normalizedUnit(), is(TimeUnit.day));
		assertThat(Duration.hours(25).normalizedUnit(), is(TimeUnit.hour));
	}
	
}
