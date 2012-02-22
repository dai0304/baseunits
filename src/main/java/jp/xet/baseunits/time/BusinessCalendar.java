/*
 * Copyright 2011 Daisuke Miyamoto. (http://d.hatena.ne.jp/daisuke-m)
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
 * Copyright (c) 2004 Domain Language, Inc. (http://domainlanguage.com) This
 * free software is distributed under the "MIT" licence.
 * For more information, see http://timeandmoney.sourceforge.net.
 */
package jp.xet.baseunits.time;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

import jp.xet.baseunits.time.spec.DateSpecifications;
import jp.xet.baseunits.util.ImmutableIterator;
import jp.xet.baseunits.util.spec.Specification;

import org.apache.commons.lang.Validate;

/**
 * 営業日カレンダー。
 * 
 * <p>営業日と非営業日を判定する責務を持つ。非営業日とは休日（祝日）及び週末（土日）を表し、営業日とは非営業日でない日を表す。
 * 週末は休日ではないが、週末かつ休日は休日である。</p>
 * 
 * @author daisuke
 * @since 1.0
 */
public class BusinessCalendar {
	
	Specification<CalendarDate> holidaySpecs;
	
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @since 1.0
	 */
	public BusinessCalendar() {
		holidaySpecs = defaultHolidaySpecs();
	}
	
	/**
	 * 休日として取り扱う「日」を追加する。
	 * 
	 * @param date 休日として取り扱う「日」 
	 * @since 1.0
	 */
	public void addHoliday(CalendarDate date) {
		addHolidaySpec(DateSpecifications.fixed(date));
	}
	
	/**
	 * 休日として取り扱う「日」を追加する。
	 * 
	 * @param days 休日として取り扱う「日」 
	 * @since 1.0
	 */
	public void addHolidays(Set<CalendarDate> days) {
		for (CalendarDate date : days) {
			addHolidaySpec(DateSpecifications.fixed(date));
		}
	}
	
	/**
	 * 休日として取り扱う「日付仕様」を追加する。
	 * 
	 * @param specs 休日として取り扱う「日付仕様」 
	 * @since 1.0
	 */
	public void addHolidaySpec(Specification<CalendarDate> specs) {
		holidaySpecs = holidaySpecs.or(specs);
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
	 * @since 1.0
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
	 * @since 1.0
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
	 * <p>デフォルトの実装として、週末でなく休日でない日を営業日とするが、
	 * 業態によってはオーバーライドの可能性があるので注意すること。</p>
	 * 
	 * @param day 日
	 * @return 営業日に当たる場合は{@code true}、そうでない場合は{@code false}
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @since 1.0
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
	 * @since 1.0
	 */
	public boolean isHoliday(CalendarDate day) {
		return holidaySpecs.isSatisfiedBy(day);
	}
	
	/**
	 * {@link CalendarDate}が週末に当たるかどうか調べる。
	 * 
	 * <p>週末とは、土曜日と日曜日のことである。</p>
	 * 
	 * @param day 日
	 * @return 週末に当たる場合は{@code true}、そうでない場合は{@code false}
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	public boolean isWeekend(CalendarDate day) {
		Validate.notNull(day);
		DayOfWeek dow = day.dayOfWeek();
		return dow == DayOfWeek.SATURDAY || dow == DayOfWeek.SUNDAY;
	}
	
	/**
	 * 開始日から数えて{@code numberOfDays}営業日前の日付を返す。
	 * 
	 * @param startDate 開始日
	 * @param numberOfDays 営業日数（現在は正数しかサポートしない）
	 * @return 日付
	 * @throws IllegalArgumentException 引数{@code numberOfDays}が負数の場合
	 * @throws IllegalArgumentException 引数{@code startDate}に{@code null}を与えた場合
	 * @since 1.0
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
	 * 指定した日の直近営業日を取得する。
	 * 
	 * <p>指定日が営業日であれば当日、そうでなければ翌営業日を返す。</p>
	 * 
	 * @param day 基準日
	 * @return 営業日
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	public CalendarDate nearestNextBusinessDay(CalendarDate day) {
		if (isBusinessDay(day)) {
			return day;
		} else {
			return nextBusinessDay(day);
		}
	}
	
	/**
	 * 指定した日の直近過去営業日を取得する。
	 * 
	 * <p>指定日が営業日であれば当日、そうでなければ前営業日を返す。</p>
	 * 
	 * @param day 基準日
	 * @return 営業日
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	public CalendarDate nearestPrevBusinessDay(CalendarDate day) {
		if (isBusinessDay(day)) {
			return day;
		} else {
			return prevBusinessDay(day);
		}
	}
	
	/**
	 * 指定した日の翌営業日を取得する。
	 * 
	 * @param startDate 基準日
	 * @return 翌営業日
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @since 1.0
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
	 * @param numberOfDays 営業日数（現在は正数しかサポートしない）. {@code 0}の場合、開始日を含む翌営業日を返す
	 * @return 日付
	 * @throws IllegalArgumentException 引数{@code numberOfDays}が負数の場合
	 * @throws IllegalArgumentException 引数{@code startDate}に{@code null}を与えた場合
	 * @since 1.0
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
	 * 指定した日の前営業日を取得する。
	 * 
	 * @param startDate 基準日
	 * @return 前営業日
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	public CalendarDate prevBusinessDay(CalendarDate startDate) {
		if (isBusinessDay(startDate)) {
			return minusBusinessDays(startDate, 1);
		} else {
			return minusBusinessDays(startDate, 0);
		}
	}
	
	/**
	 * Should be overriden for each particular organization.
	 *  
	 * @return 営業日の{@link Set} 
	 * @since 1.0
	 */
	protected Specification<CalendarDate> defaultHolidaySpecs() {
		return DateSpecifications.never();
	}
	
	/**
	 * {@code calendarDays}の先頭から数えて{@code numberOfDays}営業日目の日付を返す。
	 * 
	 * @param numberOfDays 営業日数. {@code 0}の場合、イテレータの先頭
	 * @param calendarDays 日付イテレータ
	 * @return 営業日
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @since 1.0
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
