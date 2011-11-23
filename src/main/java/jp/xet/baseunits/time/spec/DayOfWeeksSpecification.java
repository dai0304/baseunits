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
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

import jp.xet.baseunits.time.CalendarDate;
import jp.xet.baseunits.time.CalendarInterval;
import jp.xet.baseunits.time.DayOfWeek;
import jp.xet.baseunits.util.ImmutableIterator;

import com.google.common.collect.Sets;

import org.apache.commons.lang.Validate;

/**
 * ある特定の曜日を表す日付仕様。
 * 
 * @since 2.0
 */
@SuppressWarnings("serial")
public final class DayOfWeeksSpecification extends AbstractDateSpecification implements Serializable {
	
	final Set<DayOfWeek> dayOfWeeks;
	
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param dayOfWeeks 曜日の集合
	 */
	public DayOfWeeksSpecification(Collection<DayOfWeek> dayOfWeeks) {
		Validate.noNullElements(dayOfWeeks);
		this.dayOfWeeks = Sets.newHashSet(dayOfWeeks);
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param dayOfWeek 曜日
	 * @param dayOfWeeks 曜日(optional)
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 */
	DayOfWeeksSpecification(DayOfWeek dayOfWeek, DayOfWeek... dayOfWeeks) {
		Validate.notNull(dayOfWeek);
		Validate.noNullElements(dayOfWeeks);
		this.dayOfWeeks = Sets.newHashSet(dayOfWeeks);
		this.dayOfWeeks.add(dayOfWeek);
	}
	
	@Override
	public CalendarDate firstOccurrenceIn(CalendarInterval interval) {
		Validate.notNull(interval);
		Validate.isTrue(interval.hasLowerLimit());
		Iterator<CalendarDate> itr = interval.daysIterator();
		int counter = 0;
		while (itr.hasNext()) {
			CalendarDate calendarDate = itr.next();
			if (dayOfWeeks.contains(calendarDate.dayOfWeek())) {
				return calendarDate;
			}
			counter++;
			assert counter < DayOfWeek.SIZE; // 7周以上しないはず
		}
		return null;
	}
	
	/**
	 * この仕様を満たす条件としての曜日集合を返す。
	 * 
	 * @return この仕様を満たす条件としての曜日集合
	 */
	public Set<DayOfWeek> getDayOfWeeks() {
		return Sets.newHashSet(dayOfWeeks);
	}
	
	@Override
	public boolean isSatisfiedBy(CalendarDate date) {
		Validate.notNull(date);
		return dayOfWeeks.contains(date.dayOfWeek());
	}
	
	@Override
	public Iterator<CalendarDate> iterateOver(final CalendarInterval interval) {
		return new ImmutableIterator<CalendarDate>() {
			
			CalendarDate next = firstOccurrenceIn(interval);
			
			
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
				
				do {
					next = next.nextDay();
				} while (isSatisfiedBy(next) == false);
				
				if (interval.includes(next) == false) {
					next = null;
				}
				return current;
			}
		};
	}
	
	@Override
	public String toString() {
		return dayOfWeeks.toString();
	}
}
