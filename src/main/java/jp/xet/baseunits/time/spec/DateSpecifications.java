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

import org.apache.commons.lang.Validate;

/**
 * 日付の仕様を表現するオブジェクト。
 * 
 * @author daisuke
 * @since 2.0
 */
public final class DateSpecifications {
	
	/**
	 * どの日付にもマッチする日付仕様を返す。
	 * 
	 * @return 日付仕様
	 * @since 2.0
	 */
	public static DateSpecification always() {
		return AlwaysDateSpecification.INSTANCE;
	}
	
	/**
	 * 指定した期間にマッチする日付仕様を返す。
	 * 
	 * @param interval 期間
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @return 日付仕様
	 * @since 2.0
	 */
	public static DateSpecification calendarInterval(CalendarInterval interval) {
		return new CalendarIntervalSpecification(interval);
	}
	
	/**
	 * 指定した曜日にマッチする日付仕様を返す。
	 * 
	 * @param dayOfWeeks 曜日
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @return 日付仕様
	 * @since 2.0
	 */
	public static DateSpecification dayOfWeek(Collection<DayOfWeek> dayOfWeeks) {
		return new DayOfWeeksSpecification(dayOfWeeks);
	}
	
	/**
	 * 指定した曜日にマッチする日付仕様を返す。
	 * 
	 * @param dayOfWeek 曜日
	 * @param dayOfWeeks 曜日(optional)
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @return 日付仕様
	 * @since 2.0
	 */
	public static DateSpecification dayOfWeek(DayOfWeek dayOfWeek, DayOfWeek... dayOfWeeks) {
		return new DayOfWeeksSpecification(dayOfWeek, dayOfWeeks);
	}
	
	/**
	 * 特定のある1日だけにマッチする日付仕様のインスタンスを返す。
	 * 
	 * <p>毎月31日を指定した場合、該当月に31日が存在しなければ、その月にはヒットしない。</p>
	 * 
	 * @param date マッチする日
	 * @return 日付仕様
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	public static DateSpecification fixed(CalendarDate date) {
		return new FixedDateSpecification(date);
	}
	
	/**
	 * 日付仕様「毎月{@code day}日」のインスタンスを生成する。
	 * 
	 * <p>毎月31日を指定した場合、該当月に31日が存在しなければ、その月にはヒットしない。</p>
	 * 
	 * @param day 日
	 * @throws IllegalArgumentException 引数{@code day}が1〜31の範囲ではない場合
	 * @return 日付仕様
	 * @since 2.0
	 */
	public static MonthlyDateSpecification fixed(DayOfMonth day) {
		return new MonthlyFixedDateSpecification(day);
	}
	
	/**
	 * 日付仕様「毎月{@code day}日」のインスタンスを生成する。
	 * 
	 * <p>毎月31日を指定した場合、該当月に31日が存在しなければ、その月にはヒットしない。</p>
	 * 
	 * @param day 日を表す正数（1〜31）
	 * @throws IllegalArgumentException 引数{@code day}が1〜31の範囲ではない場合
	 * @return 日付仕様
	 * @since 1.0
	 */
	public static MonthlyDateSpecification fixed(int day) {
		return fixed(DayOfMonth.valueOf(day));
	}
	
	/**
	 * 日付仕様「毎年{@code month}月{@code day}日」のインスタンスを生成する。
	 * 
	 * @param month 月を表す正数（1〜12）
	 * @param day 日を表す正数（1〜31）
	 * @throws IllegalArgumentException 引数{@code month}が1〜12の範囲ではない場合
	 * @throws IllegalArgumentException 引数{@code day}が1〜31の範囲ではない場合
	 * @return 日付仕様
	 * @since 1.0
	 */
	public static AnnualDateSpecification fixed(int month, int day) {
		return fixed(MonthOfYear.valueOf(month), DayOfMonth.valueOf(day));
	}
	
	/**
	 * 日付仕様「毎年{@code month}月{@code day}日」のインスタンスを生成する。
	 * 
	 * @param month 月を表す正数（1〜12）
	 * @param day 日を表す正数（1〜31）
	 * @throws IllegalArgumentException 引数{@code month}が1〜12の範囲ではない場合
	 * @throws IllegalArgumentException 引数{@code day}が1〜31の範囲ではない場合
	 * @return 日付仕様
	 * @since 2.0
	 */
	public static AnnualDateSpecification fixed(MonthOfYear month, DayOfMonth day) {
		return new AnnualFixedDateSpecification(month, day);
	}
	
	/**
	 * 日付仕様「毎月{@code day}日（ただし、非営業日の場合は、前営業日/翌営業日）」のインスタンスを生成する。
	 * 
	 * @param day 日
	 * @param shifter シフト戦略
	 * @param cal 営業日カレンダー
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @return 日付仕様
	 */
	public static MonthlyDateSpecification fixedBusiness(DayOfMonth day, Shifter shifter, BusinessCalendar cal) {
		return new MonthlyFixedBusinessDateSpecification(day, shifter, cal);
	}
	
	/**
	 * どの日付にもマッチしない日付仕様を返す。
	 * 
	 * @return 日付仕様
	 * @since 1.0
	 */
	public static DateSpecification never() {
		return NeverDateSpecification.INSTANCE;
	}
	
	/**
	 * 日付仕様「毎月第{@code n}◎曜日仕様」のインスタンスを生成する。
	 * 
	 * @param dayOfWeek 曜日◎
	 * @param n 周回数（1〜5）
	 * @return 日付仕様
	 * @throws IllegalArgumentException 引数dayOfWeekに{@code null}を与えた場合
	 * @since 1.0
	 */
	public static MonthlyDateSpecification nthOccuranceOfWeekdayInEveryMonth(DayOfWeek dayOfWeek, int n) {
		return new MonthlyFloatingDateSpecification(dayOfWeek, n);
	}
	
	/**
	 * 日付仕様「{@code month}月の第{@code n}◎曜日仕様のインスタンスを生成する。
	 * 
	 * @param month 月を表す正数（1〜12）
	 * @param dayOfWeek 曜日◎
	 * @param n 周回数（1〜5）
	 * @return 日付仕様
	 * @throws IllegalArgumentException 引数dayOfWeekに{@code null}を与えた場合
	 * @since 1.0
	 */
	public static AnnualDateSpecification nthOccuranceOfWeekdayInMonth(int month, DayOfWeek dayOfWeek, int n) {
		return nthOccuranceOfWeekdayInMonth(MonthOfYear.valueOf(month), dayOfWeek, n);
	}
	
	/**
	 * 日付仕様「{@code month}月の第{@code n}◎曜日仕様のインスタンスを生成する。
	 * 
	 * @param month 月
	 * @param dayOfWeek 曜日◎
	 * @param n 周回数（1〜5）
	 * @return 日付仕様
	 * @throws IllegalArgumentException 引数dayOfWeekに{@code null}を与えた場合
	 * @since 2.0
	 */
	public static AnnualDateSpecification nthOccuranceOfWeekdayInMonth(MonthOfYear month, DayOfWeek dayOfWeek, int n) {
		return new AnnualFloatingDateSpecification(month, dayOfWeek, n);
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
			Validate.notNull(interval);
			Validate.isTrue(interval.hasLowerLimit());
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
			Validate.notNull(interval);
			return null;
		}
		
		@Override
		public boolean isSatisfiedBy(CalendarDate date) {
			Validate.notNull(date);
			return false;
		}
		
		@Override
		@SuppressWarnings("unchecked")
		public Iterator<CalendarDate> iterateOver(CalendarInterval interval) {
			return Collections.EMPTY_LIST.iterator();
		}
	}
}
