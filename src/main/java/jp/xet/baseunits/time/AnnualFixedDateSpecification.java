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

import org.apache.commons.lang.Validate;

/**
 * 毎年X月Y日、を表す日付仕様。
 */
class AnnualFixedDateSpecification extends AnnualDateSpecification {
	
	final MonthOfYear month;
	
	final DayOfMonth day;
	
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param month 月
	 * @param day 日
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 */
	AnnualFixedDateSpecification(MonthOfYear month, DayOfMonth day) {
		Validate.notNull(month);
		Validate.notNull(day);
		this.month = month;
		this.day = day;
	}
	
	@Override
	public boolean isSatisfiedBy(CalendarDate date) {
		Validate.notNull(date);
		return day.equals(date.breachEncapsulationOfDay())
				&& month == date.asCalendarMonth().breachEncapsulationOfMonth();
	}
	
	@Override
	public CalendarDate ofYear(int year) {
		return CalendarDate.from(year, month, day);
	}
	
	@Override
	public String toString() {
		return day.toString() + " " + month.toString();
	}
}