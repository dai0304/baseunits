/**
 * Copyright (c) 2004 Domain Language, Inc. (http://domainlanguage.com) This
 * free software is distributed under the "MIT" licence. See file licence.txt.
 * For more information, see http://timeandmoney.sourceforge.net.
 */

package jp.xet.timeandmoney.time;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

import jp.xet.timeandmoney.util.ImmutableIterator;

import org.apache.commons.lang.Validate;

/**
 * 営業日カレンダー。
 * 
 * <p>営業日と非営業日を判定する責務を持つ。非営業日とは休日（祝日）及び週末（土日）を表し、営業日とは非営業日でない日を表す。
 * 週末は休日ではないが、週末かつ休日は休日である。</p>
 * 
 * @version $Id$
 * @author daisuke
 */
public class BusinessCalendar {
	
	/**
	 * Should be rewritten for each particular organization
	 *  
	 * @return 営業日の{@link Set} 
	 */
	static Set<CalendarDate> defaultHolidays() {
		return new HashSet<CalendarDate>();
	}
	

	private final Set<CalendarDate> holidays;
	

	/**
	 * インスタンスを生成する。
	 */
	public BusinessCalendar() {
		holidays = defaultHolidays();
	}
	
	/**
	 * 休日として取り扱う「日」を追加する。
	 * 
	 * @param days 休日として取り扱う「日」 
	 */
	public void addHolidays(Set<CalendarDate> days) {
		holidays.addAll(days);
	}
	
	/**
	 * {@link CalendarDate}の反復子を受け取り、その反復子が返す{@link CalendarDate}のうち、
	 * 営業日に当たる{@link CalendarDate}のみを返す反復子を返す。
	 * 
	 * <p>このメソッドは引数に与えた反復子の状態を変更する。また、このメソッドの戻り値の反復子を利用中は、
	 * 引数に与えた反復子の {@link Iterator#next()} を呼び出してはならない。</p>
	 * 
	 * @param calendarDays 元となる反復子
	 * @return 営業日のみを返す反復子
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 */
	public Iterator<CalendarDate> businessDaysOnly(final Iterator<CalendarDate> calendarDays) {
		Validate.notNull(calendarDays);
		return new ImmutableIterator<CalendarDate>() {
			
			CalendarDate lookAhead = null;
			
			/* instance initializer */{ // CHECKSTYLE IGNORE THIS LINE
				lookAhead = nextBusinessDate();
			}
			

			@Override
			public boolean hasNext() {
				return lookAhead != null;
			}
			
			@Override
			public CalendarDate next() {
				if (hasNext() == false) {
					throw new NoSuchElementException();
				}
				CalendarDate next = lookAhead;
				lookAhead = nextBusinessDate();
				return next;
			}
			
			private CalendarDate nextBusinessDate() {
				CalendarDate result = null;
				do {
					result = calendarDays.hasNext() ? (CalendarDate) calendarDays.next() : null;
				} while ((result == null || isBusinessDay(result)) == false);
				return result;
			}
		};
	}
	
	/**
	 * {@link CalendarInterval}で表す期間のうち、営業日の日数を返す。
	 * 
	 * @param interval 期間
	 * @return 営業日の日数
	 */
	public int getElapsedBusinessDays(CalendarInterval interval) {
		int tally = 0;
		Iterator<CalendarDate> iterator = businessDaysOnly(interval.daysIterator());
		while (iterator.hasNext()) {
			iterator.next();
			tally += 1;
		}
		return tally;
	}
	
	/**
	 * {@link CalendarDate}が営業日に当たるかどうか調べる。
	 * 
	 * @param day 日
	 * @return 営業日に当たる場合は{@code true}、そうでない場合は{@code false}
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 */
	public boolean isBusinessDay(CalendarDate day) {
		return isWeekend(day) == false && isHoliday(day) == false;
	}
	
	/**
	 * {@link CalendarDate}が休日に当たるかどうか調べる。
	 * 
	 * <p>休日とは、非営業日のうち週末以外のものである。週末を含まないことに注意すること。</p>
	 * 
	 * @param day 日
	 * @return 休日に当たる場合は{@code true}、そうでない場合は{@code false}
	 */
	public boolean isHoliday(CalendarDate day) {
		return holidays.contains(day);
	}
	
	/**
	 * {@link CalendarDate}が週末に当たるかどうか調べる。
	 * 
	 * <p>週末とは、土曜日と日曜日のことである。</p>
	 * 
	 * @param day 日
	 * @return 週末に当たる場合は{@code true}、そうでない場合は{@code false}
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 */
	public boolean isWeekend(CalendarDate day) {
		Validate.notNull(day);
		Calendar calday = day.asJavaCalendarUniversalZoneMidnight();
		return (calday.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)
				|| (calday.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY);
	}
	
	/**
	 * 開始日から数えて{@code numberOfDays}営業日前の日付を返す。
	 * 
	 * @param startDate 開始日
	 * @param numberOfDays 営業日数（現在は正数しかサポートしない）
	 * @return 日付
	 * @throws IllegalArgumentException 引数{@code numberOfDays}が負数の場合
	 * @throws IllegalArgumentException 引数{@code startDate}に{@code null}を与えた場合
	 */
	public CalendarDate minusBusinessDays(CalendarDate startDate, int numberOfDays) {
		Validate.notNull(startDate);
		if (numberOfDays < 0) {
			throw new IllegalArgumentException("Negative numberOfDays not supported");
		}
		Iterator<CalendarDate> iterator = CalendarInterval.everPreceding(startDate).daysInReverseIterator();
		return nextNumberOfBusinessDays(numberOfDays, iterator);
	}
	
	/**
	 * 指定した日に最も近い営業日を取得する。
	 * 
	 * @param day 基準日
	 * @return 営業日
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 */
	public CalendarDate nearestBusinessDay(CalendarDate day) {
		if (isBusinessDay(day)) {
			return day;
		} else {
			return nextBusinessDay(day);
		}
	}
	
	/**
	 * 指定した日の翌営業日を取得する。
	 * 
	 * @param startDate 基準日
	 * @return 翌営業日
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 */
	public CalendarDate nextBusinessDay(CalendarDate startDate) {
		if (isBusinessDay(startDate)) {
			return plusBusinessDays(startDate, 1);
		} else {
			return plusBusinessDays(startDate, 0);
		}
	}
	
	/**
	 * 開始日から数えて{@code numberOfDays}営業日目の日付を返す。
	 * 
	 * @param startDate 開始日
	 * @param numberOfDays 営業日数（現在は正数しかサポートしない）
	 * @return 日付
	 * @throws IllegalArgumentException 引数{@code numberOfDays}が負数の場合
	 * @throws IllegalArgumentException 引数{@code startDate}に{@code null}を与えた場合
	 */
	public CalendarDate plusBusinessDays(CalendarDate startDate, int numberOfDays) {
		Validate.notNull(startDate);
		if (numberOfDays < 0) {
			throw new IllegalArgumentException("Negative numberOfDays not supported");
		}
		Iterator<CalendarDate> iterator = CalendarInterval.everFrom(startDate).daysIterator();
		return nextNumberOfBusinessDays(numberOfDays, iterator);
	}
	
	/**
	 * {@code calendarDays}の先頭から数えて{@code numberOfDays}営業日目の日付を返す。
	 * 
	 * @param numberOfDays 営業日数
	 * @param calendarDays 日付イテレータ
	 * @return 営業日
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 */
	private CalendarDate nextNumberOfBusinessDays(int numberOfDays, Iterator<CalendarDate> calendarDays) {
		Iterator<CalendarDate> businessDays = businessDaysOnly(calendarDays);
		CalendarDate result = null;
		for (int i = 0; i <= numberOfDays; i++) {
			result = businessDays.next();
		}
		return result;
	}
	
	/*
	 * boolean isBusinessHours(TimePoint now) { Calendar date =
	 * now.asJavaCalendar(); int theHour = date.get(Calendar.HOUR_OF_DAY); int
	 * theMinute = date.get(Calendar.MINUTE); int timeAsMinutes = (theHour * 60) +
	 * theMinute; return timeAsMinutes >= openForBusiness && timeAsMinutes <=
	 * closeForBusiness; }
	 * 
	 * boolean isInBusiness(TimePoint point) { return isBusinessDay(point) &&
	 * isBusinessHours(point); }
	 * 
	 * Returns true if <now> is a holiday. An alternative to using
	 * <Holidays.ALL>
	 * 
	 * It makes no effort to recognize "half-day holidays", such as the
	 * Wednesday before Thanksgiving. Currently, it only recognizes these
	 * holidays: New Year's Day MLK Day President's Day Memorial Day
	 * Independence Day Labor Day Thanksgiving Christmas
	 * 
	 * 
	 * boolean isFederalHoliday(TimePoint point) { Calendar javaCal =
	 * point.asJavaCalendar(); int[] month_date = { Calendar.JANUARY, 1,
	 * Calendar.JULY, 4, Calendar.DECEMBER, 25, }; int[] month_weekday_monthweek = {
	 * Calendar.JANUARY, Calendar.MONDAY, 3, // MLK Day, 3rd monday in Jan
	 * Calendar.FEBRUARY, Calendar.MONDAY, 3, // President's day
	 * Calendar.SEPTEMBER, Calendar.MONDAY, 1, // Labor day Calendar.NOVEMBER,
	 * Calendar.THURSDAY, 4, // Thanksgiving }; // Columbus Day is a federal
	 * holiday. // it is the second Monday in October int mm =
	 * javaCal.get(Calendar.MONTH); int dd = javaCal.get(Calendar.DAY_OF_MONTH);
	 * int dw = javaCal.get(Calendar.DAY_OF_WEEK); int wm =
	 * javaCal.get(Calendar.WEEK_OF_MONTH); // go over the month/day-of-month
	 * entries, return true on full match for (int i = 0; i < month_date.length;
	 * i += 2) { if ((mm == month_date[i + 0]) && (dd == month_date[i + 1]))
	 * return true; } // go over month/weekday/week-of-month entries, return
	 * true on full match for (int i = 0; i < month_weekday_monthweek.length; i +=
	 * 3) { if ((mm == month_weekday_monthweek[i + 0]) && (dw ==
	 * month_weekday_monthweek[i + 1]) && (wm == month_weekday_monthweek[i +
	 * 2])) return true; }
	 * 
	 * if ((mm == Calendar.MAY) && (dw == Calendar.MONDAY) && (wm ==
	 * javaCal.getMaximum(Calendar.WEEK_OF_MONTH))) // last week in May return
	 * true;
	 * 
	 * return false; }
	 */
}
