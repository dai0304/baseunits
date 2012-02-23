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
 * Copyright (c) 2006 Domain Language, Inc. (http://domainlanguage.com) This
 * free software is distributed under the "MIT" licence.
 * For more information, see http://timeandmoney.sourceforge.net.
 */
package jp.xet.baseunits.time;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.TimeZone;

import org.junit.Test;

/**
 * {@link TimeOfDay}のテストクラス。
 */
public class TimeOfDayTest {
	
	private static final TimeZone CST = TimeZone.getTimeZone("CST");
	
	private static final TimeZone GMT = TimeZone.getTimeZone("GMT");
	
	private CalendarDate feb17 = CalendarDate.from(2006, 2, 17);
	
	private TimeOfDay midnight = TimeOfDay.from(0, 0, 0, 0);
	
	private TimeOfDay morning = TimeOfDay.from(10, 20, 0, 0);
	
	private TimeOfDay noon = TimeOfDay.from(12, 0, 0, 0);
	
	private TimeOfDay afternoon = TimeOfDay.from(15, 40, 0, 0);
	
	private TimeOfDay twoMinutesBeforeMidnight = TimeOfDay.from(23, 58, 0, 0);
	
	private TimeOfDay tenMinutesBeforeMidnight = TimeOfDay.from(23, 50, 0, 0);
	
	
	/**
	 * {@link TimeOfDay#asTimePointGiven(CalendarDate, TimeZone)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test01_OnStartOfDay() throws Exception {
		TimePoint feb17AtStartOfDay = TimePoint.atUTC(2006, 2, 17, 0, 0);
		assertThat(midnight.asTimePointGiven(feb17, GMT), is(feb17AtStartOfDay));
	}
	
	/**
	 * {@link TimeOfDay#asTimePointGiven(CalendarDate, TimeZone)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test02_OnMiddleOfDay() throws Exception {
		TimePoint feb17AtMiddleOfDay = TimePoint.atUTC(2006, 2, 17, 12, 0);
		assertThat(noon.asTimePointGiven(feb17, GMT), is(feb17AtMiddleOfDay));
	}
	
	/**
	 * {@link TimeOfDay#asTimePointGiven(CalendarDate, TimeZone)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test03_OnEndOfDay() throws Exception {
		TimePoint feb17AtEndOfDay = TimePoint.atUTC(2006, 2, 17, 23, 58);
		assertThat(twoMinutesBeforeMidnight.asTimePointGiven(feb17, GMT), is(feb17AtEndOfDay));
	}
	
	/**
	 * {@link TimeOfDay#equals(Object)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test04_Equals() throws Exception {
		assertThat(midnight.equals(TimeOfDay.from(0, 0, 0, 0)), is(true));
		assertThat(morning.equals(TimeOfDay.from(10, 20, 0, 0)), is(true));
		assertThat(noon.equals(TimeOfDay.from(12, 0, 0, 0)), is(true));
		assertThat(afternoon.equals(TimeOfDay.from(15, 40, 0, 0)), is(true));
		assertThat(twoMinutesBeforeMidnight.equals(TimeOfDay.from(23, 58, 0, 0)), is(true));
		
		assertThat(midnight.equals(morning), is(false));
		assertThat(midnight.equals(midnight), is(true));
		assertThat(morning.equals(null), is(false));
		assertThat(morning.equals(new Object()), is(false));
		assertThat(tenMinutesBeforeMidnight.equals(twoMinutesBeforeMidnight), is(false));
		assertThat(noon.equals(TimeOfDay.from(HourOfDay.valueOf(12), MinuteOfHour.valueOf(0))), is(true));
	}
	
	/**
	 * {@link TimeOfDay#hashCode()}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test05_HashCode() throws Exception {
		assertThat(midnight.hashCode(), is(TimeOfDay.from(0, 0, 0, 0).hashCode()));
		assertThat(morning.hashCode(), is(TimeOfDay.from(10, 20, 0, 0).hashCode()));
		assertThat(noon.hashCode(), is(TimeOfDay.from(12, 0, 0, 0).hashCode()));
		assertThat(afternoon.hashCode(), is(TimeOfDay.from(15, 40, 0, 0).hashCode()));
		assertThat(twoMinutesBeforeMidnight.hashCode(), is(TimeOfDay.from(23, 58, 0, 0).hashCode()));
	}
	
	/**
	 * {@link TimeOfDay#isAfter(TimeOfDay)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test06_AfterWithEarlierTimeOfDay() throws Exception {
		assertThat("expected twoMinutesBeforeMidnight to be after midnight",
				twoMinutesBeforeMidnight.isAfter(midnight), is(true));
		assertThat("expected afternoon to be after morning", afternoon.isAfter(morning), is(true));
		assertThat("expected noon to be after midnight", noon.isAfter(midnight), is(true));
		
		assertThat(TimeOfDay.from(1, 2, 3, 4).isAfter(TimeOfDay.from(1, 2, 3, 4)), is(false));
		assertThat(TimeOfDay.from(1, 2, 3, 4).isAfter(TimeOfDay.from(1, 2, 3, 5)), is(false));
		assertThat(TimeOfDay.from(1, 2, 3, 4).isAfter(TimeOfDay.from(1, 2, 3, 3)), is(true));
		assertThat(TimeOfDay.from(1, 2, 3, 4).isAfter(TimeOfDay.from(1, 2, 2, 4)), is(true));
		assertThat(TimeOfDay.from(1, 2, 3, 4).isAfter(TimeOfDay.from(1, 1, 3, 4)), is(true));
	}
	
	/**
	 * {@link TimeOfDay#isAfter(TimeOfDay)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test07_AfterWithLaterTimeOfDay() throws Exception {
		assertThat("expected midnight not after twoMinutesBeforeMidnight", midnight.isAfter(twoMinutesBeforeMidnight),
				is(false));
		assertThat("expected morning not after afternoon", morning.isAfter(afternoon), is(false));
		assertThat("expected noon not after twoMinutesBeforeMidnight", noon.isAfter(twoMinutesBeforeMidnight),
				is(false));
	}
	
	/**
	 * {@link TimeOfDay#isAfter(TimeOfDay)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test08_AfterWithSameTimeOfDay() throws Exception {
		assertThat("expected midnight not after midnight", midnight.isAfter(midnight), is(false));
		assertThat("expected morning not after morning", morning.isAfter(morning), is(false));
		assertThat("expected afternoon not after afternoon", afternoon.isAfter(afternoon), is(false));
		assertThat("expected noon not after noon", noon.isAfter(noon), is(false));
	}
	
	/**
	 * {@link TimeOfDay#isBefore(TimeOfDay)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test09_BeforeWithEarlierTimeOfDay() throws Exception {
		assertThat("expected twoMinutesBeforeMidnight not after midnight", twoMinutesBeforeMidnight.isBefore(midnight),
				is(false));
		assertThat("expected afternoon not after morning", afternoon.isBefore(morning), is(false));
		assertThat("expected noon not after midnight", noon.isBefore(midnight), is(false));
	}
	
	/**
	 * {@link TimeOfDay#isBefore(TimeOfDay)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test10_BeforeWithLaterTimeOfDay() throws Exception {
		assertThat("expected midnight not after twoMinutesBeforeMidnight", midnight.isBefore(twoMinutesBeforeMidnight),
				is(true));
		assertThat("expected morning not after afternoon", morning.isBefore(afternoon), is(true));
		assertThat("expected noon not after twoMinutesBeforeMidnight", noon.isBefore(twoMinutesBeforeMidnight),
				is(true));
	}
	
	/**
	 * {@link TimeOfDay#isBefore(TimeOfDay)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test11_BeforeWithSameTimeOfDay() throws Exception {
		assertThat("expected midnight not after midnight", midnight.isBefore(midnight), is(false));
		assertThat("expected morning not after morning", morning.isBefore(morning), is(false));
		assertThat("expected afternoon not after afternoon", afternoon.isBefore(afternoon), is(false));
		assertThat("expected noon not after noon", noon.isBefore(noon), is(false));
	}
	
	/**
	 * {@link TimeOfDay#breachEncapsulationOfHour()}のテスト。（内部API）
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test12_GetHour() throws Exception {
		assertThat(midnight.breachEncapsulationOfHour(), is(HourOfDay.valueOf(0)));
		assertThat(morning.breachEncapsulationOfHour(), is(HourOfDay.valueOf(10)));
		assertThat(noon.breachEncapsulationOfHour(), is(HourOfDay.valueOf(12)));
		assertThat(afternoon.breachEncapsulationOfHour(), is(HourOfDay.valueOf(15)));
		assertThat(twoMinutesBeforeMidnight.breachEncapsulationOfHour(), is(HourOfDay.valueOf(23)));
	}
	
	/**
	 * {@link TimeOfDay#breachEncapsulationOfMinute()}のテスト。（内部API）
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test13_GetMinute() throws Exception {
		assertThat(midnight.breachEncapsulationOfMinute(), is(MinuteOfHour.valueOf(0)));
		assertThat(morning.breachEncapsulationOfMinute(), is(MinuteOfHour.valueOf(20)));
		assertThat(noon.breachEncapsulationOfMinute(), is(MinuteOfHour.valueOf(0)));
		assertThat(afternoon.breachEncapsulationOfMinute(), is(MinuteOfHour.valueOf(40)));
		assertThat(twoMinutesBeforeMidnight.breachEncapsulationOfMinute(), is(MinuteOfHour.valueOf(58)));
	}
	
	/**
	 * {@link TimeOfDay#asTimePointGiven(CalendarDate, TimeZone)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test14_AsTimePoint() throws Exception {
		TimeOfDay fiveFifteen = TimeOfDay.from(17, 15, 0, 0);
		CalendarDate mayEleventh = CalendarDate.from(2006, 5, 11);
		TimePoint mayEleventhAtFiveFifteen = fiveFifteen.asTimePointGiven(mayEleventh, CST);
		assertThat(mayEleventhAtFiveFifteen, is(TimePoint.at(2006, 5, 11, 17, 15, 0, 0, CST)));
	}
	
	/**
	 * {@link TimeOfDay#toString()} 及び {@link TimeOfDay#toString(String)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test15_toString() throws Exception {
		assertThat(midnight.toString(), is("00:00:00,000"));
		assertThat(morning.toString(), is("10:20:00,000"));
		assertThat(noon.toString(), is("12:00:00,000"));
		assertThat(afternoon.toString(), is("15:40:00,000"));
		
		assertThat(midnight.toString("HH-mm"), is("00-00"));
		assertThat(morning.toString("HH-mm"), is("10-20"));
		assertThat(noon.toString("HH-mm"), is("12-00"));
		assertThat(afternoon.toString("HH-mm"), is("15-40"));
	}
	
	/**
	 * {@link TimeOfDay#plus(Duration)}及び{@link TimeOfDay#minus(Duration)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test16_plus_minus() throws Exception {
		assertThat(TimeOfDay.NOON.plus(Duration.hours(1)), is(TimeOfDay.from(13, 0)));
		assertThat(TimeOfDay.from(4, 52, 3, 55).plus(Duration.minutes(12)), is(TimeOfDay.from(5, 4, 3, 55)));
		assertThat(TimeOfDay.NOON.plus(Duration.hours(13)), is(TimeOfDay.from(1, 0)));
		
		assertThat(TimeOfDay.from(13, 0).minus(Duration.hours(2)), is(TimeOfDay.from(11, 0)));
		assertThat(TimeOfDay.from(5, 4, 3, 55).minus(Duration.minutes(12)), is(TimeOfDay.from(4, 52, 3, 55)));
		assertThat(TimeOfDay.NOON.minus(Duration.hours(13)), is(TimeOfDay.from(23, 0)));
	}
	
	/**
	 * {@link TimeOfDay#parse(String, String)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test17_parse() throws Exception {
		assertThat(TimeOfDay.parse("15:20", "HH:mm"), is(TimeOfDay.from(15, 20)));
		assertThat(TimeOfDay.parse("15:20:30", "HH:mm"), is(TimeOfDay.from(15, 20)));
		assertThat(TimeOfDay.parse("15時から20分過ぎ", "HH時からmm分過ぎ"), is(TimeOfDay.from(15, 20)));
	}
}
