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
 * 毎年X月Y日、を表す日付仕様。
 */
class AnnualFixedDateSpecification extends AnnualDateSpecification {
	
	private final int month;
	
	private final int day;
	

	/**
	 * インスタンスを生成する。
	 * 
	 * @param month 月を表す正数（1〜12）
	 * @param day 日を表す正数（1〜31）
	 * @throws IllegalArgumentException 引数{@code month}が1〜12の範囲ではない場合
	 * @throws IllegalArgumentException 引数{@code day}が1〜31の範囲ではない場合
	 */
	AnnualFixedDateSpecification(int month, int day) {
		Validate.isTrue(1 <= month && month <= 12);
		Validate.isTrue(1 <= day && day <= 31); // CHECKSTYLE IGNORE THIS LINE
		this.month = month;
		this.day = day;
	}
	
	@Override
	public boolean isSatisfiedBy(CalendarDate date) {
		Validate.notNull(date);
		return day == date.breachEncapsulationOfDay() && month == date.breachEncapsulationOfMonth();
	}
	
	@Override
	public CalendarDate ofYear(int year) {
		return CalendarDate.date(year, month, day);
	}
	
}
