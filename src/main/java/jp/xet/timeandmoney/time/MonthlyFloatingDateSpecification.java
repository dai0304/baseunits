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
 * 毎月の第Y◎曜日、を表す日付仕様。
 */
class MonthlyFloatingDateSpecification extends MonthlyDateSpecification {
	
	private final DayOfWeek dayOfWeek;
	
	private final int occurrence;
	

	/**
	 * インスタンスを生成する。
	 * 
	 * @param dayOfWeek 曜日
	 * @param occurrence 周回数（1〜5）
	 * @throws IllegalArgumentException 引数{@code dayOfWeek}に{@code null}を与えた場合
	 * @throws IllegalArgumentException 引数{@code occurrence}が1〜5の範囲ではない場合
	 */
	MonthlyFloatingDateSpecification(DayOfWeek dayOfWeek, int occurrence) {
		Validate.notNull(dayOfWeek);
		Validate.isTrue(1 <= occurrence && occurrence <= 5);
		this.dayOfWeek = dayOfWeek;
		this.occurrence = occurrence;
	}
	
	@Override
	public boolean isSatisfiedBy(CalendarDate date) {
		Validate.notNull(date);
		return ofYearMonth(date.breachEncapsulationOfYearMonth()).equals(date);
	}
	
	@Override
	public CalendarDate ofYearMonth(CalendarMonth month) {
		CalendarDate firstOfMonth = CalendarDate.date(month, DayOfMonth.valueOf(1));
		int dayOfWeekOffset = dayOfWeek.value - firstOfMonth.dayOfWeek().value;
		int dateOfFirstOccurrenceOfDayOfWeek = dayOfWeekOffset + (dayOfWeekOffset < 0 ? 8 : 1);
		int date = ((occurrence - 1) * 7) + dateOfFirstOccurrenceOfDayOfWeek;
		return CalendarDate.date(month, DayOfMonth.of(date, month));
	}
}
