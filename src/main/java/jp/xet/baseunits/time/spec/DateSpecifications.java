/*
 * Copyright 2011-2013 Daisuke Miyamoto. (http://d.hatena.ne.jp/daisuke-m)
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
package jp.xet.baseunits.time.spec;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

import jp.xet.baseunits.time.BusinessCalendar;
import jp.xet.baseunits.time.CalendarDate;
import jp.xet.baseunits.time.CalendarInterval;
import jp.xet.baseunits.time.DayOfMonth;
import jp.xet.baseunits.time.DayOfWeek;
import jp.xet.baseunits.time.MonthOfYear;
import jp.xet.baseunits.time.spec.MonthlyFixedBusinessDateSpecification.Shifter;

import com.google.common.base.Preconditions;

/**
 * Static utility methods pertaining to {@code DateSpecification} instances.
 * 
 * @author daisuke
 * @since 2.0
 */
public final class DateSpecifications {
	
	/**
	 * どの暦日にもマッチする暦日仕様を返す。
	 * 
	 * @return 暦日仕様
	 * @since 2.0
	 */
	public static DateSpecification always() {
		return AlwaysDateSpecification.INSTANCE;
	}
	
	/**
	 * 指定した期間にマッチする暦日仕様を返す。
	 * 
	 * @param interval 期間
	 * @return 暦日仕様
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @since 2.0
	 */
	public static DateSpecification calendarInterval(CalendarInterval interval) {
		return new CalendarIntervalSpecification(interval);
	}
	
	/**
	 * 指定した曜日にマッチする暦日仕様を返す。
	 * 
	 * @param dayOfWeeks 曜日
	 * @return 暦日仕様
	 * @throws NullPointerException 引数またはその要素に{@code null}を与えた場合
	 * @since 2.0
	 */
	public static DateSpecification dayOfWeek(Collection<DayOfWeek> dayOfWeeks) {
		return new DayOfWeeksSpecification(dayOfWeeks);
	}
	
	/**
	 * 指定した曜日にマッチする暦日仕様を返す。
	 * 
	 * @param dayOfWeek 曜日
	 * @param dayOfWeeks 曜日(optional)
	 * @return 暦日仕様
	 * @throws NullPointerException 引数またはその要素に{@code null}を与えた場合
	 * @since 2.0
	 */
	public static DateSpecification dayOfWeek(DayOfWeek dayOfWeek, DayOfWeek... dayOfWeeks) {
		return new DayOfWeeksSpecification(dayOfWeek, dayOfWeeks);
	}
	
	/**
	 * 唯一、指定した暦日だけにマッチする暦日仕様を返す。
	 * 
	 * <p>ただし、例えば2/29を指定した場合、閏年でない限り、その暦年中にヒットする暦日は無い。</p>
	 * 
	 * @param date マッチする暦日
	 * @return 暦日仕様
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	public static DateSpecification fixed(CalendarDate date) {
		return new FixedDateSpecification(date);
	}
	
	/**
	 * 毎月{@code day}日にマッチする暦日仕様を返す。
	 * 
	 * <p>ただし、例えば31日を指定した場合、該当暦月に31日が存在しなければ、その暦月内の暦日にはヒットしない。</p>
	 * 
	 * @param day 日
	 * @return 暦日仕様
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @since 2.0
	 */
	public static MonthlyDateSpecification fixed(DayOfMonth day) {
		return new MonthlyFixedDateSpecification(day);
	}
	
	/**
	 * 毎月{@code day}日にマッチする暦日仕様を返す。
	 * 
	 * <p>ただし、例えば31日を指定した場合、該当暦月に31日が存在しなければ、その暦月内の暦日にはヒットしない。</p>
	 * 
	 * @param day 日を表す正数（1〜31）
	 * @return 暦日仕様
	 * @throws IllegalArgumentException 引数{@code day}が1〜31の範囲ではない場合
	 * @since 1.0
	 */
	public static MonthlyDateSpecification fixed(int day) {
		return fixed(DayOfMonth.valueOf(day));
	}
	
	/**
	 * 毎年{@code month}月{@code day}日にマッチする暦日仕様を返す。
	 * 
	 * @param month 月を表す正数（1〜12）
	 * @param day 日を表す正数（1〜31）
	 * @return 暦日仕様
	 * @throws IllegalArgumentException 引数{@code month}が1〜12の範囲ではない場合
	 * @throws IllegalArgumentException 引数{@code day}が1〜31の範囲ではない場合
	 * @since 1.0
	 */
	public static AnnualDateSpecification fixed(int month, int day) {
		return fixed(MonthOfYear.valueOf(month), DayOfMonth.valueOf(day));
	}
	
	/**
	 * 毎年{@code month}月{@code day}日にマッチする暦日仕様を返す。
	 * 
	 * @param month 月
	 * @param day 日
	 * @return 暦日仕様
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @since 2.0
	 */
	public static AnnualDateSpecification fixed(MonthOfYear month, DayOfMonth day) {
		return new AnnualFixedDateSpecification(month, day);
	}
	
	/**
	 * 毎月{@code day}日（ただし、非営業日の場合は、前営業日/翌営業日）にマッチする暦日仕様を返す。
	 * 
	 * @param day 日
	 * @param shifter シフト戦略
	 * @param cal 営業日カレンダー
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @return 暦日仕様
	 */
	public static MonthlyDateSpecification fixedBusiness(DayOfMonth day, Shifter shifter, BusinessCalendar cal) {
		return new MonthlyFixedBusinessDateSpecification(day, shifter, cal);
	}
	
	/**
	 * どの暦日にもマッチしない暦日仕様を返す。
	 * 
	 * @return 暦日仕様
	 * @since 1.0
	 */
	public static DateSpecification never() {
		return NeverDateSpecification.INSTANCE;
	}
	
	/**
	 * 毎月第{@code n}◎曜日にマッチする暦日仕様を返す。
	 * 
	 * @param dayOfWeek 曜日◎
	 * @param occurrence 序数（1〜5）
	 * @return 暦日仕様
	 * @throws IllegalArgumentException 引数{@code occurrence}が1〜5の範囲ではない場合
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	public static MonthlyDateSpecification nthOccuranceOfWeekdayInEveryMonth(DayOfWeek dayOfWeek, int occurrence) {
		return new MonthlyFloatingDateSpecification(dayOfWeek, occurrence);
	}
	
	/**
	 * {@code month}月の第{@code n}◎曜日にマッチする暦日仕様を返す。
	 * 
	 * @param month 月を表す正数（1〜12）
	 * @param dayOfWeek 曜日◎
	 * @param occurrence 序数（1〜5）
	 * @return 暦日仕様
	 * @throws IllegalArgumentException 引数{@code month}が1〜12の範囲ではない場合
	 * @throws IllegalArgumentException 引数{@code occurrence}が1〜5の範囲ではない場合
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	public static AnnualDateSpecification nthOccuranceOfWeekdayInMonth(int month, DayOfWeek dayOfWeek, int occurrence) {
		return nthOccuranceOfWeekdayInMonth(MonthOfYear.valueOf(month), dayOfWeek, occurrence);
	}
	
	/**
	 * {@code month}月の第{@code n}◎曜日にマッチする暦日仕様を返す。
	 * 
	 * @param month 月
	 * @param dayOfWeek 曜日◎
	 * @param occurrence 周回数（1〜5）
	 * @return 暦日仕様
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @since 2.0
	 */
	public static AnnualDateSpecification nthOccuranceOfWeekdayInMonth(MonthOfYear month, DayOfWeek dayOfWeek, int occurrence) {
		return new AnnualFloatingDateSpecification(month, dayOfWeek, occurrence);
	}
	
	private DateSpecifications() {
	}
	
	
	@SuppressWarnings("serial")
	private static final class AlwaysDateSpecification extends AbstractDateSpecification implements Serializable {
		
		static final AlwaysDateSpecification INSTANCE = new AlwaysDateSpecification();
		
		
		private AlwaysDateSpecification() {
		}
		
		@Override
		public CalendarDate firstOccurrenceIn(CalendarInterval interval) {
			return interval.start();
		}
		
		@Override
		public boolean isSatisfiedBy(CalendarDate date) {
			return true;
		}
		
		@Override
		public Iterator<CalendarDate> iterateOver(CalendarInterval interval) {
			Preconditions.checkNotNull(interval);
			Preconditions.checkArgument(interval.hasLowerLimit());
			return interval.daysIterator();
		}
	}
	
	@SuppressWarnings("serial")
	private static final class NeverDateSpecification extends AbstractDateSpecification implements Serializable {
		
		static final NeverDateSpecification INSTANCE = new NeverDateSpecification();
		
		
		private NeverDateSpecification() {
		}
		
		@Override
		public CalendarDate firstOccurrenceIn(CalendarInterval interval) {
			Preconditions.checkNotNull(interval);
			return null;
		}
		
		@Override
		public boolean isSatisfiedBy(CalendarDate date) {
			Preconditions.checkNotNull(date);
			return false;
		}
		
		@Override
		@SuppressWarnings("unchecked")
		public Iterator<CalendarDate> iterateOver(CalendarInterval interval) {
			return Collections.EMPTY_LIST.iterator();
		}
	}
}
