/*
 * Copyright 2010-2019 Miyamoto Daisuke.
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

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

import jp.xet.baseunits.time.spec.DateSpecifications;
import jp.xet.baseunits.util.ImmutableIterator;
import jp.xet.baseunits.util.spec.Specification;

import com.google.common.base.Preconditions;

/**
 * 営業日カレンダー。
 * 
 * <p>営業日と非営業日を判定する責務を持つ。
 * 非営業日とは基本的に休日（主に祝日）及び週末（土日）にあたる暦日を表し、営業日とは非営業日でない暦日を表す。
 * ただし、オーバーライドによって営業日・非営業日の定義が変更される可能性があることに留意すること。</p>
 * 
 * <p>休日と週末は区別する。すなわち、週末は休日ではないが、週末かつ休日は休日である。</p>
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
	 * 休日として判定する暦日を追加する。
	 * 
	 * @param date 休日として取り扱う暦日 
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	public void addHoliday(CalendarDate date) {
		addHolidaySpec(DateSpecifications.fixed(date));
	}
	
	/**
	 * 休日として判定する暦日を追加する。
	 * 
	 * @param days 休日として取り扱う暦日の集合
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	public void addHolidays(Set<CalendarDate> days) {
		Preconditions.checkNotNull(days);
		for (CalendarDate date : days) {
			Preconditions.checkNotNull(date);
		}
		for (CalendarDate date : days) {
			addHolidaySpec(DateSpecifications.fixed(date));
		}
	}
	
	/**
	 * 休日として判定する暦日仕様を追加する。
	 * 
	 * @param specs 休日として取り扱う暦日仕様
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	public void addHolidaySpec(Specification<CalendarDate> specs) {
		Preconditions.checkNotNull(specs);
		holidaySpecs = holidaySpecs.or(specs);
	}
	
	/**
	 * 暦日の反復子を受け取り、その反復子が返す暦日のうち、営業日に当たる暦日のみを返す反復子を返す。
	 * 
	 * <p>このメソッドは引数に与えた反復子の状態を変更する。また、このメソッドの戻り値の反復子を利用中は、
	 * 引数に与えた反復子の {@link Iterator#next()} を呼び出してはならない。</p>
	 * 
	 * <p>戻り値の反復子は{@link Iterator#remove()}をサポートしない。</p>
	 * 
	 * @param calendarDays 元となる暦日反復子
	 * @return 営業日のみを返す反復子
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	public Iterator<CalendarDate> businessDaysOnly(final Iterator<CalendarDate> calendarDays) {
		Preconditions.checkNotNull(calendarDays);
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
	 * 指定した暦日が営業日に当たるかどうか調べる。
	 * 
	 * <p>デフォルトの実装として、週末でなく休日でない日を営業日とするが、
	 * 業態によってはこのメソッドをオーバーライドする可能性があるので注意すること。</p>
	 * 
	 * @param day 日
	 * @return 営業日に当たる場合は{@code true}、そうでない場合は{@code false}
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	public boolean isBusinessDay(CalendarDate day) {
		return isWeekend(day) == false && isHoliday(day) == false;
	}
	
	/**
	 * 指定した暦日が休日に当たるかどうか調べる。
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
	 * 指定した暦日が週末に当たるかどうか調べる。
	 * 
	 * <p>週末とは、土曜日と日曜日のことである。</p>
	 * 
	 * @param day 暦日
	 * @return 週末に当たる場合は{@code true}、そうでない場合は{@code false}
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	public final boolean isWeekend(CalendarDate day) {
		Preconditions.checkNotNull(day);
		DayOfWeek dow = day.dayOfWeek();
		return dow == DayOfWeek.SATURDAY || dow == DayOfWeek.SUNDAY;
	}
	
	/**
	 * 基準暦日から数えて{@code numberOfDays}営業日前の暦日を返す。
	 * 
	 * @param day 基準暦日
	 * @param numberOfDays 営業日数（現在は正数しかサポートしない）
	 * @return 暦日
	 * @throws IllegalArgumentException 引数{@code numberOfDays}が負数の場合
	 * @throws NullPointerException 引数{@code startDate}に{@code null}を与えた場合
	 * @since 1.0
	 */
	public CalendarDate minusBusinessDays(CalendarDate day, int numberOfDays) {
		Preconditions.checkNotNull(day);
		if (numberOfDays < 0) {
			throw new IllegalArgumentException("Negative numberOfDays not supported");
		}
		Iterator<CalendarDate> iterator = CalendarInterval.everPreceding(day).daysInReverseIterator();
		return nextNumberOfBusinessDays(numberOfDays, iterator);
	}
	
	/**
	 * 指定した暦日から最も早く到来する営業日を取得する。
	 * 
	 * <p>指定日が営業日であれば当日、そうでなければ翌営業日を返す。</p>
	 * 
	 * @param day 基準暦日
	 * @return 営業日
	 * @throws NullPointerException 引数に{@code null}を与えた場合
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
	 * 指定した暦日を含む過去の営業日のうち、最も西院の営業日を取得する。
	 * 
	 * <p>指定日が営業日であれば当日、そうでなければ前営業日を返す。</p>
	 * 
	 * @param day 基準暦日
	 * @return 営業日
	 * @throws NullPointerException 引数に{@code null}を与えた場合
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
	 * 指定した暦日の翌営業日を取得する。
	 * 
	 * @param day 基準暦日
	 * @return 翌営業日
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	public CalendarDate nextBusinessDay(CalendarDate day) {
		if (isBusinessDay(day)) {
			return plusBusinessDays(day, 1);
		} else {
			return plusBusinessDays(day, 0);
		}
	}
	
	/**
	 * 指定した暦日から数えて{@code numberOfDays}営業日目の暦日を返す。
	 * 
	 * @param day 基準暦日
	 * @param numberOfDays 営業日数（現在は正数しかサポートしない）. {@code 0}の場合、開始日を含む翌営業日を返す
	 * @return 暦日
	 * @throws IllegalArgumentException 引数{@code numberOfDays}が負数の場合
	 * @throws NullPointerException 引数{@code startDate}に{@code null}を与えた場合
	 * @since 1.0
	 */
	public CalendarDate plusBusinessDays(CalendarDate day, int numberOfDays) {
		Preconditions.checkNotNull(day);
		if (numberOfDays < 0) {
			throw new IllegalArgumentException("Negative numberOfDays not supported");
		}
		Iterator<CalendarDate> iterator = CalendarInterval.everFrom(day).daysIterator();
		return nextNumberOfBusinessDays(numberOfDays, iterator);
	}
	
	/**
	 * 指定した暦日の前営業日を取得する。
	 * 
	 * @param day 基準暦日
	 * @return 前営業日
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	public CalendarDate prevBusinessDay(CalendarDate day) {
		if (isBusinessDay(day)) {
			return minusBusinessDays(day, 1);
		} else {
			return minusBusinessDays(day, 0);
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
	 * 反復子の先頭から数えて{@code numberOfDays}営業日目の暦日を返す。
	 * 
	 * @param numberOfDays 営業日数. {@code 0}の場合、イテレータの先頭
	 * @param calendarDays 暦日反復子
	 * @return 営業日
	 * @throws NullPointerException 引数に{@code null}を与えた場合
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
