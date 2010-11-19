/**
 * Copyright (c) 2006 Domain Language, Inc. (http://domainlanguage.com) This
 * free software is distributed under the "MIT" licence. See file licence.txt.
 * For more information, see http://timeandmoney.sourceforge.net.
 */
package com.domainlanguage.time;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.TimeZone;

import org.junit.Test;

/**
 * {@link TimeOfDay}のテストクラス。
 *
 * @author davem
 */
public class TimeOfDayTest {
	
	private static final TimeZone CST = TimeZone.getTimeZone("CST");
	
	private CalendarDate feb17 = CalendarDate.from(2006, 2, 17);
	
	private TimeOfDay midnight = TimeOfDay.hourAndMinute(0, 0);
	
	private TimeOfDay morning = TimeOfDay.hourAndMinute(10, 20);
	
	private TimeOfDay noon = TimeOfDay.hourAndMinute(12, 0);
	
	private TimeOfDay afternoon = TimeOfDay.hourAndMinute(15, 40);
	
	private TimeOfDay twoMinutesBeforeMidnight = TimeOfDay.hourAndMinute(23, 58);
	

	/**
	 * {@link TimeOfDay#on(CalendarDate)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test01_OnStartOfDay() throws Exception {
		CalendarMinute feb17AtStartOfDay = CalendarMinute.dateHourAndMinute(2006, 2, 17, 0, 0);
		assertThat(midnight.on(feb17), is(feb17AtStartOfDay));
	}
	
	/**
	 * {@link TimeOfDay#on(CalendarDate)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test02_OnMiddleOfDay() throws Exception {
		CalendarMinute feb17AtMiddleOfDay = CalendarMinute.dateHourAndMinute(2006, 2, 17, 12, 0);
		assertThat(noon.on(feb17), is(feb17AtMiddleOfDay));
	}
	
	/**
	 * {@link TimeOfDay#on(CalendarDate)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test03_OnEndOfDay() throws Exception {
		CalendarMinute feb17AtEndOfDay = CalendarMinute.dateHourAndMinute(2006, 2, 17, 23, 58);
		assertThat(twoMinutesBeforeMidnight.on(feb17), is(feb17AtEndOfDay));
	}
	
	/**
	 * {@link TimeOfDay#equals(Object)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test04_Equals() throws Exception {
		assertThat(midnight, is(TimeOfDay.hourAndMinute(0, 0)));
		assertThat(morning, is(TimeOfDay.hourAndMinute(10, 20)));
		assertThat(noon, is(TimeOfDay.hourAndMinute(12, 0)));
		assertThat(afternoon, is(TimeOfDay.hourAndMinute(15, 40)));
		assertThat(twoMinutesBeforeMidnight, is(TimeOfDay.hourAndMinute(23, 58)));
	}
	
	/**
	 * {@link TimeOfDay#hashCode()}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test05_HashCode() throws Exception {
		assertThat(midnight.hashCode(), is(TimeOfDay.hourAndMinute(0, 0).hashCode()));
		assertThat(morning.hashCode(), is(TimeOfDay.hourAndMinute(10, 20).hashCode()));
		assertThat(noon.hashCode(), is(TimeOfDay.hourAndMinute(12, 0).hashCode()));
		assertThat(afternoon.hashCode(), is(TimeOfDay.hourAndMinute(15, 40).hashCode()));
		assertThat(twoMinutesBeforeMidnight.hashCode(), is(TimeOfDay.hourAndMinute(23, 58).hashCode()));
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
		assertThat(midnight.breachEncapsulationOfHour(), is(0));
		assertThat(morning.breachEncapsulationOfHour(), is(10));
		assertThat(noon.breachEncapsulationOfHour(), is(12));
		assertThat(afternoon.breachEncapsulationOfHour(), is(15));
		assertThat(twoMinutesBeforeMidnight.breachEncapsulationOfHour(), is(23));
	}
	
	/**
	 * {@link TimeOfDay#breachEncapsulationOfMinute()}のテスト。（内部API）
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test13_GetMinute() throws Exception {
		assertThat(midnight.breachEncapsulationOfMinute(), is(0));
		assertThat(morning.breachEncapsulationOfMinute(), is(20));
		assertThat(noon.breachEncapsulationOfMinute(), is(0));
		assertThat(afternoon.breachEncapsulationOfMinute(), is(40));
		assertThat(twoMinutesBeforeMidnight.breachEncapsulationOfMinute(), is(58));
	}
	
	/**
	 * {@link TimeOfDay#asTimePointGiven(CalendarDate, TimeZone)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test14_AsTimePoint() throws Exception {
		TimeOfDay fiveFifteen = TimeOfDay.hourAndMinute(17, 15);
		CalendarDate mayEleventh = CalendarDate.date(2006, 5, 11);
		TimePoint mayEleventhAtFiveFifteen = fiveFifteen.asTimePointGiven(mayEleventh, CST);
		assertThat(mayEleventhAtFiveFifteen, is(TimePoint.at(2006, 5, 11, 17, 15, 0, 0, CST)));
	}
}
