/**
 * Copyright (c) 2004 Domain Language, Inc. (http://domainlanguage.com) This
 * free software is distributed under the "MIT" licence. See file licence.txt.
 * For more information, see http://timeandmoney.sourceforge.net.
 */

package jp.xet.timeandmoney.time;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.text.ParseException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.junit.Test;

/**
 * {@link BusinessCalendar}のテストクラス。
 * 
 * @author daisuke
 */
public class BusinessCalendarTest {
	
	/**
	 * {@link BusinessCalendar#getElapsedBusinessDays(CalendarInterval)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test01_ElapsedBusinessDays() throws Exception {
		CalendarDate nov1 = CalendarDate.from(2004, 11, 1);
		CalendarDate nov30 = CalendarDate.from(2004, 11, 30);
		CalendarInterval interval = CalendarInterval.inclusive(nov1, nov30);
		assertThat(interval.length(), is(Duration.days(30)));
		// 1 holiday (Thanksgiving on a Thursday) + 8 weekend days.
		assertThat(businessCalendar().getElapsedBusinessDays(interval), is(21));
	}
	
	/**
	 * {@link BusinessCalendar#isWeekend(CalendarDate)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test02_IsWeekend() throws Exception {
		CalendarDate saturday = CalendarDate.from(2004, 1, 10);
		assertThat(businessCalendar().isWeekend(saturday), is(true));
		
		CalendarDate sunday = saturday.nextDay();
		assertThat(businessCalendar().isWeekend(sunday), is(true));
		
		CalendarDate day = sunday;
		for (int i = 0; i < 5; i++) {
			day = day.nextDay();
			assertThat("it's a midweek day", businessCalendar().isWeekend(day), is(false));
		}
		day = day.nextDay();
		assertThat("finally, the weekend is here...", businessCalendar().isWeekend(day), is(true));
		
		CalendarDate newYearEve = CalendarDate.from(2004, 1, 1); // it's a
		assertThat("a holiday is not necessarily a weekend day", businessCalendar().isWeekend(newYearEve), is(false));
	}
	
	/**
	 * {@link BusinessCalendar#isHoliday(CalendarDate)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test03_IsHoliday() throws Exception {
		CalendarDate newYearEve = CalendarDate.from(2004, 1, 1); // it's a
		assertThat("New Years Eve is a holiday.", businessCalendar().isHoliday(newYearEve), is(true));
		assertThat("The day after New Years Eve is not a holiday.", businessCalendar().isHoliday(newYearEve.nextDay()),
				is(false));
	}
	
	/**
	 * {@link BusinessCalendar#isBusinessDay(CalendarDate)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test04_IsBusinessDay() throws Exception {
		CalendarDate day = CalendarDate.from(2004, 1, 12); // it's a Monday
		for (int i = 0; i < 5; i++) {
			assertThat("another working day", businessCalendar().isBusinessDay(day), is(true));
			day = day.nextDay();
		}
		assertThat("finally, saturday arrived ...", businessCalendar().isBusinessDay(day), is(false));
		assertThat("... then sunday", businessCalendar().isBusinessDay(day.nextDay()), is(false));
		
		CalendarDate newYearEve = CalendarDate.from(2004, 1, 1); // it's a
		assertThat("hey, it's a holiday", businessCalendar().isBusinessDay(newYearEve), is(false));
	}
	
	/**
	 * nearestBusinessDay(CalendarDate)のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test05_NearestBusinessDay() throws Exception {
		CalendarDate saturday = CalendarDate.from(2004, 1, 10);
		CalendarDate sunday = saturday.nextDay();
		CalendarDate monday = sunday.nextDay();
		assertThat(businessCalendar().nearestBusinessDay(saturday), is(monday));
		assertThat(businessCalendar().nearestBusinessDay(sunday), is(monday));
		assertThat(businessCalendar().nearestBusinessDay(monday), is(monday));
		
		CalendarDate newYearEve = CalendarDate.from(2004, 1, 1); // it's a
		assertThat("it's a holiday & a thursday; wait till friday", businessCalendar().nearestBusinessDay(newYearEve),
				is(newYearEve.nextDay()));
		
		CalendarDate christmas = CalendarDate.from(2004, 12, 24); // it's a
		assertThat("it's a holiday & a friday; wait till monday", businessCalendar().nearestBusinessDay(christmas),
				is(CalendarDate.from(2004, 12, 27)));
	}
	
	/**
	 * {@link BusinessCalendar#businessDaysOnly(Iterator)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test06_BusinessDaysIterator() throws Exception {
		CalendarDate start = CalendarDate.from(2004, 2, 5);
		CalendarDate end = CalendarDate.from(2004, 2, 8);
		CalendarInterval interval = CalendarInterval.inclusive(start, end);
		Iterator<CalendarDate> it = businessCalendar().businessDaysOnly(interval.daysIterator());
		assertThat(it.hasNext(), is(true));
		assertThat(it.next(), is(start));
		assertThat(it.hasNext(), is(true));
		assertThat(it.next(), is(CalendarDate.from(2004, 2, 6)));
		assertThat(it.hasNext(), is(false));
	}
	
	/**
	 * {@link BusinessCalendar#nextBusinessDay(CalendarDate)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test07_NextBusinessDayOverWeekend() throws Exception {
		CalendarDate friday = CalendarDate.from(2006, 06, 16);
		CalendarDate monday = CalendarDate.from(2006, 06, 19);
		CalendarDate actual = businessCalendar().nextBusinessDay(friday);
		assertThat(actual, is(monday));
	}
	
	/**
	 * {@link BusinessCalendar#nextBusinessDay(CalendarDate)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test08_NextBusinessDayOverWeekday() throws Exception {
		CalendarDate monday = CalendarDate.from(2006, 06, 19);
		CalendarDate tuesday = CalendarDate.from(2006, 06, 20);
		CalendarDate actual = businessCalendar().nextBusinessDay(monday);
		assertThat(actual, is(tuesday));
	}
	
	/**
	 * {@link BusinessCalendar#plusBusinessDays(CalendarDate, int)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test09_PlusBusinessDayZero() throws Exception {
		CalendarDate monday = CalendarDate.from(2006, 06, 19);
		CalendarDate actual = businessCalendar().plusBusinessDays(monday, 0);
		assertThat(actual, is(monday));
	}
	
	/**
	 * {@link BusinessCalendar#plusBusinessDays(CalendarDate, int)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test10_PlusNonBusinessDayZero() throws Exception {
		CalendarDate saturday = CalendarDate.from(2006, 06, 17);
		CalendarDate monday = CalendarDate.from(2006, 06, 19);
		CalendarDate actual = businessCalendar().plusBusinessDays(saturday, 0);
		assertThat(actual, is(monday));
		
	}
	
	/**
	 * {@link BusinessCalendar#minusBusinessDays(CalendarDate, int)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test11_MinusNonBusinessDayZero() throws Exception {
		CalendarDate saturday = CalendarDate.from(2006, 06, 17);
		CalendarDate friday = CalendarDate.from(2006, 06, 16);
		CalendarDate actual = businessCalendar().minusBusinessDays(saturday, 0);
		assertThat(actual, is(friday));
		
	}
	
	/**
	 * {@link BusinessCalendar#businessDaysOnly(Iterator)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test12_BusinessDayReverseIterator() throws Exception {
		CalendarDate friday = CalendarDate.from(2006, 06, 16);
		CalendarDate nextTuesday = CalendarDate.from(2006, 06, 20);
		CalendarInterval interval = CalendarInterval.inclusive(friday, nextTuesday);
		Iterator<CalendarDate> it = businessCalendar().businessDaysOnly(interval.daysInReverseIterator());
		assertThat(it.hasNext(), is(true));
		assertThat(it.next(), is(nextTuesday));
		assertThat(it.hasNext(), is(true));
		CalendarDate nextMonday = CalendarDate.from(2006, 06, 19);
		assertThat(it.next(), is(nextMonday));
		assertThat(it.hasNext(), is(true));
		assertThat(it.next(), is(friday));
		assertThat(it.hasNext(), is(false));
	}
	
	/**
	 * TODO for daisuke
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test13_JapaneseHoliday() throws Exception {
		BusinessCalendar calendar = new BusinessCalendar();
		
		// 祝日の登録
		calendar.addHolidaySpec(DateSpecification.fixed(1, 1)); // 元旦
		calendar.addHolidaySpec(DateSpecification.nthOccuranceOfWeekdayInMonth(1, DayOfWeek.MONDAY, 2)); // 成人の日
		calendar.addHolidaySpec(DateSpecification.fixed(2, 11)); // 建国記念日
		calendar.addHoliday(CalendarDate.date(2010, 3, 21)); // 春分の日
		calendar.addHolidaySpec(DateSpecification.fixed(4, 29)); // 昭和の日
		calendar.addHolidaySpec(DateSpecification.fixed(5, 3)); // 憲法記念日
		calendar.addHolidaySpec(DateSpecification.fixed(5, 4)); // みどりの日
		calendar.addHolidaySpec(DateSpecification.fixed(5, 5)); // こどもの日
		calendar.addHolidaySpec(DateSpecification.nthOccuranceOfWeekdayInMonth(7, DayOfWeek.MONDAY, 3)); // 海の日
		calendar.addHolidaySpec(DateSpecification.nthOccuranceOfWeekdayInMonth(9, DayOfWeek.MONDAY, 3)); // 敬老の日
		calendar.addHoliday(CalendarDate.date(2010, 9, 23)); // 春分の日
		calendar.addHolidaySpec(DateSpecification.nthOccuranceOfWeekdayInMonth(10, DayOfWeek.MONDAY, 2)); // 体育の日
		calendar.addHolidaySpec(DateSpecification.fixed(11, 3)); // 文化の日
		calendar.addHolidaySpec(DateSpecification.fixed(11, 23)); // 勤労感謝の日
		calendar.addHolidaySpec(DateSpecification.fixed(12, 23)); // 天皇誕生日
		
		// それぞれの日が「営業日」にあたるかどうかチェック。
		assertThat(calendar.isBusinessDay(CalendarDate.date(2010, 10, 8)), is(true)); // 金曜日
		assertThat(calendar.isBusinessDay(CalendarDate.date(2010, 10, 9)), is(false)); // 土曜日
		assertThat(calendar.isBusinessDay(CalendarDate.date(2010, 10, 10)), is(false)); // 日曜日
		assertThat(calendar.isBusinessDay(CalendarDate.date(2010, 10, 11)), is(false)); // 月曜日体育の日
		assertThat(calendar.isBusinessDay(CalendarDate.date(2010, 10, 12)), is(true)); // 火曜日平日
		
		assertThat(calendar.isBusinessDay(CalendarDate.date(2010, 11, 22)), is(true)); // 月曜日平日
		assertThat(calendar.isBusinessDay(CalendarDate.date(2010, 11, 23)), is(false)); // 火曜日祝日
		assertThat(calendar.isBusinessDay(CalendarDate.date(2010, 11, 24)), is(true)); // 水曜日平日
		
		// 振替休日（「国民の祝日」が日曜日にあたる場合、その直後の「国民の祝日」でない日を休日とする）とか、
		// 国民の休日（「国民の祝日」と次の「国民の祝日」の間隔が中1日しかなくその中日（なかび）が「国民の祝日」でない場合、その日を休日とする）
		// なんかには、まだ対応していないけど、DateSpecification実装すればどうにかならんかな、と思っている。
		
		Iterator<CalendarDate> itr =
				calendar.businessDaysOnly(CalendarInterval.inclusive(2010, 10, 1, 2010, 11, 30).daysIterator());
		StringBuilder sb = new StringBuilder();
		while (itr.hasNext()) {
			CalendarDate calendarDate = itr.next();
			sb.append(calendarDate).append(" ");
		}
		assertThat(sb.toString(), is("2010-10-01 " +
				"2010-10-04 2010-10-05 2010-10-06 2010-10-07 2010-10-08 " +
				"2010-10-12 2010-10-13 2010-10-14 2010-10-15 " +
				"2010-10-18 2010-10-19 2010-10-20 2010-10-21 2010-10-22 " +
				"2010-10-25 2010-10-26 2010-10-27 2010-10-28 2010-10-29 " +
				"2010-11-01 2010-11-02 2010-11-04 2010-11-05 " +
				"2010-11-08 2010-11-09 2010-11-10 2010-11-11 2010-11-12 " +
				"2010-11-15 2010-11-16 2010-11-17 2010-11-18 2010-11-19 " +
				"2010-11-22 2010-11-24 2010-11-25 2010-11-26 " +
				"2010-11-29 2010-11-30 "));
	}
	
	private BusinessCalendar businessCalendar() {
		BusinessCalendar cal = new BusinessCalendar();
		cal.addHolidays(_HolidayDates.defaultHolidays());
		return cal;
	}
	

	/**
	 * dates are taken from: http://www.opm.gov/fedhol/index.htm note: when a
	 * holiday falls on a non-workday -- Saturday or Sunday -- the holiday usually
	 * is observed on Monday (if the holiday falls on Sunday) or Friday (if the
	 * holiday falls on Saturday). a holiday falls on a nonworkday will be referred
	 * to as a "deferred" holiday.
	 */
	static class _HolidayDates {
		
		static String[] COMMON_US_HOLIDAYS = new String[] {
			
			// 2004
			"2004/01/01", /* New Year's Day */
			"2004/01/19", /* Birthday of Martin Luther King */
			"2004/02/16", /* Washington's Birthday */
			"2004/05/31", /* Memorial Day */
			"2004/07/05", /* United States of America's Independence Day, July 4 *///revisit:defered
			"2004/09/06", /* Labor Day */
			"2004/11/25", /* Thanksgiving Day */
			"2004/12/24", /*
							 * Christmas Day, December 25 - Friday - deferred from
							 * Saturday
							 */
			"2004/12/31", /*
							 * New Year's Day for January 1, 2005 - Friday -
							 * deferred from Saturday
							 */
			
			// 2005
			"2005/01/17", /* Birthday of Martin Luther King */
			"2005/02/21", /* Washington's Birthday */
			"2005/05/30", /* Memorial Day */
			"2005/07/04", /* United States of America's Independence Day, July 4 */
			"2005/09/05", /* Labor Day */
			"2005/11/24", /* Thanksgiving Day */
			"2005/12/26", /*
							 * Christmas Day, December 25 - Monday - deferred from
							 * Sunday
							 */
			
			// 2006
			"2006/01/02", /* New Year's Day, January 1 */
			"2006/01/16", /* Birthday of Martin Luther King */
			"2006/02/20", /* Washington's Birthday */
			"2006/05/29", /* Memorial Day */
			"2006/07/04", /* United States of America's Independence Day, July 4 */
			"2006/09/04", /* Labor Day */
			"2006/11/23", /* Thanksgiving Day */
			"2006/12/25", /* Christmas Day, December 25 */
		};
		

		static Set<CalendarDate> defaultHolidays() {
			Set<CalendarDate> dates = new HashSet<CalendarDate>();
			String[] strings = COMMON_US_HOLIDAYS;
			for (String string : strings) {
				try {
					dates.add(CalendarDate.from(string, "yyyy/MM/dd"));
				} catch (ParseException e) {
					throw new Error(e);
				}
			}
			return dates;
		}
		
	}
	
}
