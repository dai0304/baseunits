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

import jp.xet.baseunits.time.BusinessCalendar;
import jp.xet.baseunits.time.CalendarDate;
import jp.xet.baseunits.time.CalendarMonth;
import jp.xet.baseunits.time.DayOfMonth;

import com.google.common.base.Preconditions;

/**
 * 毎月Y日（ただし、非営業日の場合は、前営業日/翌営業日）にマッチする暦日仕様実装クラス。
 * 
 * @author daisuke
 * @since 1.0
 */
public final class MonthlyFixedBusinessDateSpecification extends AbstractMonthlyDateSpecification {
	
	final DayOfMonth day;
	
	final Shifter shifter;
	
	final BusinessCalendar cal;
	
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param day 日
	 * @param shifter シフト戦略
	 * @param cal 営業日カレンダー
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	MonthlyFixedBusinessDateSpecification(DayOfMonth day, Shifter shifter, BusinessCalendar cal) {
		Preconditions.checkNotNull(day);
		Preconditions.checkNotNull(shifter);
		Preconditions.checkNotNull(cal);
		this.day = day;
		this.shifter = shifter;
		this.cal = cal;
	}
	
	/**
	 * 営業日判定に利用する {@link BusinessCalendar} を返す。
	 * 
	 * @return 営業日判定に利用する {@link BusinessCalendar}
	 */
	public BusinessCalendar getCal() {
		return cal;
	}
	
	/**
	 * この仕様を満たす条件としての日を返す。
	 * 
	 * @return この仕様を満たす条件としての日
	 */
	public DayOfMonth getDay() {
		return day;
	}
	
	/**
	 * この仕様を満たす条件としての日が非営業日であった場合の日付調整戦略を返す。
	 * 
	 * @return 日付調整戦略
	 */
	public Shifter getShifter() {
		return shifter;
	}
	
	@Override
	public boolean isSatisfiedBy(CalendarDate date) {
		Preconditions.checkNotNull(date);
		if (cal.isBusinessDay(date)) {
			CalendarDate thisMonth = ofYearMonth(date.getYearMonth());
			return thisMonth.equals(date);
		}
		return false;
	}
	
	@Override
	public CalendarDate ofYearMonth(CalendarMonth month) {
		Preconditions.checkNotNull(month);
		return shifter.shift(
				CalendarDate.from(month.getYear(), month.getMonthOfYear(), day), cal);
	}
	
	
	/**
	 * 指定日が非営業日の場合のシフト戦略（日付調整戦略）。
	 * 
	 * @since 1.0
	 */
	public enum Shifter {
		
		/** 前営業日にシフトする */
		NEXT {
			
			@Override
			CalendarDate shift(CalendarDate date, BusinessCalendar cal) {
				return cal.nearestNextBusinessDay(date);
			}
		},
		
		/** 翌営業日にシフトする */
		PREV {
			
			@Override
			CalendarDate shift(CalendarDate date, BusinessCalendar cal) {
				return cal.nearestPrevBusinessDay(date);
			}
		};
		
		abstract CalendarDate shift(CalendarDate date, BusinessCalendar cal);
	}
}
