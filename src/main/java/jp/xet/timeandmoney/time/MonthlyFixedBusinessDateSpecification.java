/*
 * Copyright 2010 TRICREO, Inc. (http://tricreo.jp/)
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
package jp.xet.timeandmoney.time;

import org.apache.commons.lang.Validate;

/**
 * 毎月Y日（ただし、非営業日の場合は、前営業日/翌営業日）、を表す日付仕様。
 */
public class MonthlyFixedBusinessDateSpecification extends MonthlyDateSpecification {
	
	final DayOfMonth day;
	
	final Shifter shifter;
	
	final BusinessCalendar cal;
	

	/**
	 * インスタンスを生成する。
	 * 
	 * @param day 日
	 * @param shifter シフト戦略
	 * @param cal 営業日カレンダー
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 */
	public MonthlyFixedBusinessDateSpecification(DayOfMonth day, Shifter shifter, BusinessCalendar cal) {
		Validate.notNull(day);
		Validate.notNull(shifter);
		Validate.notNull(cal);
		this.day = day;
		this.shifter = shifter;
		this.cal = cal;
	}
	
	@Override
	public boolean isSatisfiedBy(CalendarDate date) {
		Validate.notNull(date);
		if (cal.isBusinessDay(date)) {
			CalendarDate thisMonth = ofYearMonth(date.breachEncapsulationOfYearMonth());
			return thisMonth.equals(date);
		}
		return false;
	}
	
	@Override
	public CalendarDate ofYearMonth(CalendarMonth month) {
		Validate.notNull(month);
		return shifter.shift(
				CalendarDate.date(month.breachEncapsulationOfYear(), month.breachEncapsulationOfMonth(), day), cal);
	}
	

	/**
	 * 指定日が非営業日の場合のシフト戦略。
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
