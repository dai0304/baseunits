/*
 * Copyright 2010 Daisuke Miyamoto.
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
 * free software is distributed under the "MIT" licence. See file licence.txt.
 * For more information, see http://timeandmoney.sourceforge.net.
 */
package jp.xet.timeandmoney.time;

import org.apache.commons.lang.Validate;

/**
 * 毎月Y日、を表す日付仕様。
 */
class MonthlyFixedDateSpecification extends MonthlyDateSpecification {
	
	private final int day;
	

	/**
	 * インスタンスを生成する。
	 * 
	 * @param day 日を表す正数（1〜31）
	 * @throws IllegalArgumentException 引数{@code day}が1〜31の範囲ではない場合
	 */
	MonthlyFixedDateSpecification(int day) {
		Validate.isTrue(1 <= day && day <= 31); // CHECKSTYLE IGNORE THIS LINE
		this.day = day;
	}
	
	@Override
	public boolean isSatisfiedBy(CalendarDate date) {
		Validate.notNull(date);
		return day == date.breachEncapsulationOfDay();
	}
	
	@Override
	public CalendarDate ofYearMonth(CalendarMonth month) {
		Validate.notNull(month);
		return CalendarDate.date(month.breachEncapsulationOfYear(), month.breachEncapsulationOfMonth(), day);
	}
	
}
