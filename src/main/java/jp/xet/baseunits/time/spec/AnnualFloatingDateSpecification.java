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

import jp.xet.baseunits.time.CalendarDate;
import jp.xet.baseunits.time.DayOfMonth;
import jp.xet.baseunits.time.DayOfWeek;
import jp.xet.baseunits.time.MonthOfYear;

import org.apache.commons.lang.Validate;

/**
 * 毎年X月の第Y◎曜日、を表す日付仕様。
 * 
 * @author daisuke
 * @since 1.0
 */
@SuppressWarnings("serial")
public final class AnnualFloatingDateSpecification extends AbstractAnnualDateSpecification implements Serializable {
	
	final MonthOfYear month;
	
	final DayOfWeek dayOfWeek;
	
	final int occurrence;
	
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param month 月を表す正数（1〜12）
	 * @param dayOfWeek 曜日
	 * @param occurrence 周回数（1〜5）
	 * @throws IllegalArgumentException 引数{@code month}が1〜12の範囲ではない場合
	 * @throws IllegalArgumentException 引数{@code dayOfWeek}に{@code null}を与えた場合
	 * @throws IllegalArgumentException 引数{@code occurrence}が1〜5の範囲ではない場合
	 */
	AnnualFloatingDateSpecification(MonthOfYear month, DayOfWeek dayOfWeek, int occurrence) {
		Validate.notNull(dayOfWeek);
		Validate.isTrue(1 <= occurrence && occurrence <= 5);
		this.month = month;
		this.dayOfWeek = dayOfWeek;
		this.occurrence = occurrence;
	}
	
	/**
	 * この仕様を満たす条件としての曜日（{@link DayOfWeek}）を返す。
	 * 
	 * @return この仕様を満たす条件としての曜日（{@link DayOfWeek}）
	 */
	public DayOfWeek getDayOfWeek() {
		return dayOfWeek;
	}
	
	/**
	 * この仕様を満たす条件としての月（{@link MonthOfYear}）を返す。
	 * 
	 * @return この仕様を満たす条件としての月（{@link MonthOfYear}）
	 */
	public MonthOfYear getMonth() {
		return month;
	}
	
	/**
	 * この仕様を満たす条件としての曜日回数（第3日曜日であれば{@code 3}）を返す。
	 * 
	 * @return この仕様を満たす条件としての曜日回数
	 */
	public int getOccurrence() {
		return occurrence;
	}
	
	@Override
	public boolean isSatisfiedBy(CalendarDate date) {
		Validate.notNull(date);
		return ofYear(date.asCalendarMonth().getYear()).equals(date);
	}
	
	@Override
	public CalendarDate ofYear(int year) {
		CalendarDate firstOfMonth = CalendarDate.from(year, month, DayOfMonth.valueOf(1));
		int dayOfWeekOffset =
				dayOfWeek.breachEncapsulationOfValue() - firstOfMonth.dayOfWeek().breachEncapsulationOfValue();
		int dateOfFirstOccurrenceOfDayOfWeek = dayOfWeekOffset + (dayOfWeekOffset < 0 ? 8 : 1);
		int date = ((occurrence - 1) * 7) + dateOfFirstOccurrenceOfDayOfWeek;
		return CalendarDate.from(year, month, DayOfMonth.valueOf(date));
	}
}
