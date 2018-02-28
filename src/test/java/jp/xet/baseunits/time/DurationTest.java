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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.TimeZone;

import jp.xet.baseunits.tests.SerializationTester;

import com.google.common.collect.Lists;

import org.junit.Test;

/**
 * {@link Duration}のテストクラス。
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
		TimePoint dec20At1 = TimePoint.atUTC(2003, 12, 20, 01, 0, 0, 0);
		TimePoint dec22At1 = TimePoint.atUTC(2003, 12, 22, 01, 0, 0, 0);
		Duration twoDays = Duration.days(2);
		assertThat(twoDays.addedTo(dec20At1), is(dec22At1));
		
		Duration fourtyEightHours = Duration.hours(48);
		assertThat(fourtyEightHours.addedTo(dec20At1), is(dec22At1));
	}
	
	/**
	 * {@link Duration#addedTo(TimePoint)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test03_AddMonthsToPoint() throws Exception {
		TimePoint oct20At1 = TimePoint.atUTC(2003, 10, 20, 01, 0, 0, 0);
		TimePoint dec20At1 = TimePoint.atUTC(2003, 12, 20, 01, 0, 0, 0);
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
		TimePoint dec20At1 = TimePoint.atUTC(2003, 12, 20, 01, 0, 0, 0);
		TimePoint dec18At1 = TimePoint.atUTC(2003, 12, 18, 01, 0, 0, 0);
		Duration twoDays = Duration.days(2);
		assertThat(twoDays.subtractedFrom(dec20At1), is(dec18At1));
		
		Duration fourtyEightHours = Duration.hours(48);
		assertThat(fourtyEightHours.subtractedFrom(dec20At1), is(dec18At1));
	}
	
	/**
	 * {@link Duration#subtractedFrom(TimePoint)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test05_SubtractMonthsFromPoint() throws Exception {
		TimePoint oct20At1 = TimePoint.atUTC(2003, 10, 20, 01, 0, 0, 0);
		TimePoint dec20At1 = TimePoint.atUTC(2003, 12, 20, 01, 0, 0, 0);
		Duration twoMonths = Duration.months(2);
		assertThat(twoMonths.subtractedFrom(dec20At1), is(oct20At1));
		
		TimePoint dec20At1_2001 = TimePoint.atUTC(2001, 12, 20, 01, 0, 0, 0);
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
		
		// 単位が日未満の時は日付を変えない。
		Duration threeHours = Duration.milliseconds(30);
		assertThat(threeHours.addedTo(CalendarDate.from(2010, 11, 27)), is(CalendarDate.from(2010, 11, 27)));
		
		try {
			Duration.months(Long.MAX_VALUE).addedTo(TimePoint.EPOCH);
			fail();
		} catch (IllegalArgumentException e) {
			// success
		}
		
		assertThat(Duration.weeks(3).addedTo(dec20_2001), is(CalendarDate.from(2002, 1, 10)));
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
		assertThat(Duration.hours(48).equals(Duration.days(2)), is(true));
		assertThat(Duration.quarters(4).equals(Duration.years(1)), is(true));
		assertThat(Duration.months(6).equals(Duration.quarters(2)), is(true));
		
		assertThat(Duration.months(1).equals(Duration.days(28)), is(false));
		assertThat(Duration.months(1).equals(Duration.days(29)), is(false));
		assertThat(Duration.months(1).equals(Duration.days(30)), is(false));
		assertThat(Duration.months(1).equals(Duration.days(31)), is(false));
		
		Duration twoMonths = Duration.months(2);
		assertThat(twoMonths.equals(twoMonths), is(true));
		assertThat(twoMonths.equals(new Object()), is(false));
		assertThat(twoMonths.equals(null), is(false));
	}
	
	/**
	 * {@link Duration#plus(Duration)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test10_Add() throws Exception {
		assertThat(Duration.hours(24).plus(Duration.days(1)), is(Duration.days(2)));
		assertThat(Duration.hours(24).plus(Duration.days(1)), is(Duration.hours(48)));
		assertThat(Duration.hours(23).plus(Duration.days(1)), is(Duration.hours(47)));
		assertThat(Duration.months(1).plus(Duration.quarters(1)), is(Duration.months(4)));
		
		try {
			Duration.days(1).plus(Duration.months(1));
			fail();
		} catch (IllegalArgumentException e) {
			// success
		}
		
		assertThat(Duration.NONE.plus(Duration.NONE), is(Duration.NONE));
	}
	
	/**
	 * {@link Duration#minus(Duration)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test11_Subtract() throws Exception {
		assertThat(Duration.days(3).minus(Duration.hours(24)), is(Duration.days(2)));
		assertThat(Duration.days(3).minus(Duration.hours(24)), is(Duration.hours(48)));
		assertThat(Duration.days(3).minus(Duration.hours(23)), is(Duration.hours(49)));
		assertThat(Duration.quarters(1).minus(Duration.months(1)), is(Duration.months(2)));
		
		try {
			Duration.months(2).minus(Duration.days(3));
			fail();
		} catch (IllegalArgumentException e) {
			// success
		}
		
		try {
			Duration.days(3).minus(Duration.days(10));
			fail();
		} catch (IllegalArgumentException e) {
			// success
		}
		
		assertThat(Duration.NONE.minus(Duration.NONE), is(Duration.NONE));
	}
	
	/**
	 * {@link Duration#dividedBy(Duration)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test12_Divide() throws Exception {
		assertThat(Duration.days(3).dividedBy(Duration.days(2)).decimalValue(1, RoundingMode.DOWN), is(new BigDecimal(
				1.5)));
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
	}
	
	/**
	 * {@link Duration#toNormalizedString()}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test14_ToNormalizedStringMonthBased() throws Exception {
		assertThat(Duration.months(2).toNormalizedString(), is("2 months"));
		assertThat(Duration.months(16).toNormalizedString(), is("1 year, 1 quarter, 1 month"));
		
		assertThat(Duration.quarters(3).toNormalizedString(), is("3 quarters"));
		assertThat(Duration.quarters(4).toNormalizedString(), is("1 year"));
		assertThat(Duration.quarters(8).toNormalizedString(), is("2 years"));
	}
	
	/**
	 * {@link Duration#toString()}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test15_ToString() throws Exception {
		assertThat(Duration.weeks(3).toString(), is("21 days")); //Weeks are not conventional to read.
		assertThat(Duration.months(2).toNormalizedString(), is("2 months"));
		assertThat(Duration.months(16).toString(), is("1 year, 4 months")); //Quarters are not conventionalto read.
		assertThat(Duration.quarters(3).toString(), is("9 months"));
		assertThat(Duration.quarters(4).toString(), is("1 year"));
		assertThat(Duration.quarters(5).toString(), is("1 year, 3 months"));
		assertThat(Duration.quarters(8).toString(), is("2 years"));
	}
	
	/**
	 * {@link Duration#compareTo(Duration)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test16_Compare() throws Exception {
		// convertable units
		Duration oneHour = Duration.hours(1);
		Duration twoHours = Duration.hours(2);
		Duration sixtyMinutes = Duration.minutes(60);
		Duration sixtyMinutesAndOneMillisec = Duration.daysHoursMinutesSecondsMilliseconds(0, 0, 60, 0, 1);
		assertThat(oneHour.compareTo(twoHours), is(lessThan(0)));
		assertThat(oneHour.compareTo(sixtyMinutes), is(0));
		assertThat(twoHours.compareTo(oneHour), is(greaterThan(0)));
		assertThat(oneHour.compareTo(sixtyMinutesAndOneMillisec), is(lessThan(0)));
		
		// nonconvertable units
		Duration twoQuarters = Duration.quarters(2);
		try {
			oneHour.compareTo(twoQuarters);
			fail();
		} catch (ClassCastException e) {
			// success
		}
		
		Duration threeHundredsAndSixtyFiveDays = Duration.days(365);
		Duration anYear = Duration.years(1);
		try {
			threeHundredsAndSixtyFiveDays.compareTo(anYear);
			fail();
		} catch (ClassCastException e) {
			// success
		}
		
		// nonconvertable units but zero
		Duration zeroMonths = Duration.months(0);
		Duration zeroMillisecs = Duration.milliseconds(0);
		assertThat(twoHours.compareTo(zeroMonths), is(greaterThan(0)));
		assertThat(zeroMonths.compareTo(zeroMillisecs), is(0));
		
		try {
			twoHours.compareTo(null);
			fail();
		} catch (NullPointerException e) {
		}
	}
	
	/**
	 * {@link Duration#startingFrom(TimePoint)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test17_StartingFromTimePoint() throws Exception {
		TimePoint dec20At1 = TimePoint.atUTC(2003, 12, 20, 01, 0, 0, 0);
		TimePoint dec20At3 = TimePoint.atUTC(2003, 12, 20, 03, 0, 0, 0);
		TimePointInterval dec20_1_3 = dec20At1.until(dec20At3);
		assertThat(Duration.hours(2).startingFrom(dec20At1), is(dec20_1_3));
	}
	
	/**
	 * {@link Duration#startingFrom(CalendarDate)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test18_StartingFromCalendarDate() throws Exception {
		CalendarDate dec20 = CalendarDate.from(2004, 12, 20);
		CalendarDate dec26 = CalendarDate.from(2004, 12, 26);
		CalendarInterval dec20_26 = dec20.through(dec26);
		assertThat(Duration.days(7).startingFrom(dec20), is(dec20_26));
	}
	
	/**
	 * {@link Duration#normalizedUnit()}のテスト。
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
	
	/**
	 * {@link Duration#addedTo(CalendarMonth)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test20_AddToCalendarMonth() throws Exception {
		CalendarMonth oct2003 = CalendarMonth.from(2003, 10);
		CalendarMonth dec2003 = CalendarMonth.from(2003, 12);
		
		Duration twoMonths = Duration.months(2);
		assertThat(twoMonths.addedTo(oct2003), is(dec2003));
		
		Duration sixtyoneDays = Duration.days(61);
		assertThat(sixtyoneDays.addedTo(oct2003), is(oct2003));
		
		CalendarMonth dec2001 = CalendarMonth.from(2001, 12);
		Duration twoYears = Duration.years(2);
		assertThat(twoYears.addedTo(dec2001), is(dec2003));
		
		// 単位が日未満の時は日付を変えない。
		Duration threeHours = Duration.days(30);
		assertThat(threeHours.addedTo(CalendarMonth.from(2010, 11)), is(CalendarMonth.from(2010, 11)));
	}
	
	/**
	 * {@link Duration#to(TimeUnit)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test21_To() throws Exception {
		Duration d1 = Duration.days(2);
		assertThat(d1.to(TimeUnit.day), is(2L));
		assertThat(d1.to(TimeUnit.hour), is(48L));
		
		Duration d2 = Duration.hours(49);
		assertThat(d2.to(TimeUnit.day), is(2L));
		
		Duration d3 = Duration.months(1);
		try {
			d3.to(TimeUnit.day);
			fail();
		} catch (IllegalArgumentException e) {
			// success
		}
	}
	
	/**
	 * {@link Duration#diff(TimePoint, TimePoint)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test22_diff() throws Exception {
		TimePoint a = TimePoint.atUTC(1978, 3, 4, 7, 6, 0);
		TimePoint b = TimePoint.atUTC(1978, 3, 6, 7, 7, 0);
		assertThat(Duration.diff(a, a), is(Duration.NONE));
		assertThat(Duration.diff(a, b), is(Duration.days(2).plus(Duration.minutes(1))));
		assertThat(Duration.diff(b, a), is(Duration.days(2).plus(Duration.minutes(1))));
	}
	
	/**
	 * {@link Duration#sum(Iterable)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test23_sum() throws Exception {
		List<Duration> durations = Lists.newArrayList();
		durations.add(Duration.hours(23));
		durations.add(Duration.minutes(50));
		durations.add(Duration.minutes(9));
		durations.add(null);
		durations.add(Duration.seconds(61));
		
		assertThat(Duration.sum(durations), is(Duration.days(1).plus(Duration.seconds(1))));
	}
	
	/**
	 * {@link Duration#daysHoursMinutesSecondsMilliseconds(long, long, long, long, long)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test24_daysHoursMinutesSecondsMilliseconds() throws Exception {
		assertThat(Duration.daysHoursMinutesSecondsMilliseconds(0, 0, 0, 0, 0), is(Duration.NONE));
		assertThat(Duration.daysHoursMinutesSecondsMilliseconds(1, 0, 0, 0, 0), is(Duration.days(1)));
		assertThat(Duration.daysHoursMinutesSecondsMilliseconds(0, 2, 0, 0, 0), is(Duration.hours(2)));
		assertThat(Duration.daysHoursMinutesSecondsMilliseconds(0, 0, 3, 0, 0), is(Duration.minutes(3)));
		assertThat(Duration.daysHoursMinutesSecondsMilliseconds(0, 0, 0, 4, 0), is(Duration.seconds(4)));
		assertThat(Duration.daysHoursMinutesSecondsMilliseconds(0, 0, 0, 0, 5), is(Duration.milliseconds(5)));
	}
	
	/**
	 * {@link Duration#isLessThan(Duration)}, {@link Duration#isLessThanOrEqual(Duration)} のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test25_isLessThan() throws Exception {
		assertThat(Duration.NONE.isLessThan(Duration.milliseconds(1)), is(true));
		assertThat(Duration.NONE.isLessThan(Duration.NONE), is(false));
		assertThat(Duration.milliseconds(1).isLessThan(Duration.NONE), is(false));
		
		assertThat(Duration.NONE.isLessThanOrEqual(Duration.milliseconds(1)), is(true));
		assertThat(Duration.NONE.isLessThanOrEqual(Duration.NONE), is(true));
		assertThat(Duration.milliseconds(1).isLessThanOrEqual(Duration.NONE), is(false));
	}
	
	/**
	 * {@link Duration#preceding(CalendarDate)}, {@link Duration#preceding(TimeOfDay)},
	 * {@link Duration#preceding(TimePoint)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test26_precedingCalendarDate() throws Exception {
		assertThat(Duration.days(2).preceding(CalendarDate.from(2012, 2, 24)),
				is(CalendarInterval.inclusive(CalendarDate.from(2012, 2, 23), CalendarDate.from(2012, 2, 24))));
		assertThat(Duration.hours(3).preceding(TimeOfDay.NOON),
				is(TimeOfDayInterval.over(TimeOfDay.from(9, 0), TimeOfDay.NOON)));
		assertThat(Duration.weeks(3).preceding(TimePoint.at(2012, 2, 24, 3, 53, TimeZone.getTimeZone("Japan"))),
				is(TimePointInterval.over(TimePoint.at(2012, 2, 3, 3, 53, TimeZone.getTimeZone("Japan")),
						TimePoint.at(2012, 2, 24, 3, 53, TimeZone.getTimeZone("Japan")))));
	}
}
