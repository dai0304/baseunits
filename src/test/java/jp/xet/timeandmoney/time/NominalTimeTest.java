/**
 * Copyright (c) 2005 Domain Language, Inc. (http://domainlanguage.com) This
 * free software is distributed under the "MIT" licence. See file licence.txt.
 * For more information, see http://timeandmoney.sourceforge.net.
 */
package jp.xet.timeandmoney.time;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.TimeZone;

import org.junit.Test;

/**
 * 時間記述のなんとなくテスト。
 * 
 * @version $Id$
 * @author daisuke
 */
public class NominalTimeTest {
	
	private static final TimeZone HONOLULU_TIME = TimeZone.getTimeZone("Pacific/Honolulu");
	

	/**
	 * {@link CalendarDate}と{@link TimeOfDay}を結合するテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test01_CombineNominalTimes() throws Exception {
		TimeOfDay fiveFifteenPM = TimeOfDay.hourAndMinute(17, 15);
		CalendarDate april19_2006 = CalendarDate.from(2006, 4, 19);
		CalendarMinute expectedCombination = CalendarMinute.dateHourAndMinute(2006, 4, 19, 17, 15);
		assertThat(fiveFifteenPM.on(april19_2006), is(expectedCombination));
		assertThat(april19_2006.at(fiveFifteenPM), is(expectedCombination));
	}
	
	/**
	 * {@link CalendarMinute#asTimePoint(TimeZone)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test02_ConvertNominalTimeToTimePoint() throws Exception {
		CalendarMinute calendarMinute = CalendarMinute.dateHourAndMinute(2006, 4, 19, 17, 15);
		TimePoint expectedTimePoint = TimePoint.at(2006, 4, 19, 17, 15, 0, 0, HONOLULU_TIME);
		assertThat(calendarMinute.asTimePoint(HONOLULU_TIME), is(expectedTimePoint));
	}
}
