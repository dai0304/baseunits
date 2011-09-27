/*
 * Copyright 2011 Daisuke Miyamoto. (http://d.hatena.ne.jp/daisuke-m)
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
package jp.tricreo.baseunits.time;

import java.util.Iterator;
import java.util.NoSuchElementException;

import jp.tricreo.baseunits.util.ImmutableIterator;

import org.apache.commons.lang.Validate;

/**
 * 毎月1度だけ仕様を満たす日付仕様。
 */
public abstract class MonthlyDateSpecification extends DateSpecification {
	
	@Override
	public CalendarDate firstOccurrenceIn(CalendarInterval interval) {
		Validate.notNull(interval);
		CalendarMonth month = interval.start().asCalendarMonth();
		
		CalendarDate firstTry = ofYearMonth(month);
		if (interval.includes(firstTry)) {
			return firstTry;
		}
		
		CalendarDate secondTry = ofYearMonth(month.nextMonth());
		if (interval.includes(secondTry)) {
			return secondTry;
		}
		return null;
	}
	
	@Override
	public Iterator<CalendarDate> iterateOver(final CalendarInterval interval) {
		Validate.notNull(interval);
		return new ImmutableIterator<CalendarDate>() {
			
			CalendarDate next = firstOccurrenceIn(interval);
			
			CalendarMonth month = next.asCalendarMonth();
			
			
			@Override
			public boolean hasNext() {
				return next != null;
			}
			
			@Override
			public CalendarDate next() {
				if (hasNext() == false) {
					throw new NoSuchElementException();
				}
				CalendarDate current = next;
				month = month.nextMonth();
				next = MonthlyDateSpecification.this.ofYearMonth(month);
				if (interval.includes(next) == false) {
					next = null;
				}
				return current;
			}
		};
	}
	
	/**
	 * 指定した年月においてこの日付仕様を満たす年月日を返す。
	 * 
	 * @param month 年月
	 * @return {@link CalendarDate}
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 */
	public abstract CalendarDate ofYearMonth(CalendarMonth month);
}
