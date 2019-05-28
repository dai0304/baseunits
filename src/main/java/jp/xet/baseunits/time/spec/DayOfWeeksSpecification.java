/*
 * Copyright 2010-2019 Miyamoto Daisuke.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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

import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;

/**
 * ある特定の曜日を表す暦日仕様実装クラス。
 * 
 * @author daisuke
 * @since 2.0
 */
@SuppressWarnings("serial")
public final class DayOfWeeksSpecification extends AbstractDateSpecification implements Serializable {
	
	final Set<DayOfWeek> dayOfWeeks;
	
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param dayOfWeeks 曜日の集合
	 * @throws NullPointerException 引数またはその要素に{@code null}を与えた場合
	 */
	public DayOfWeeksSpecification(Collection<DayOfWeek> dayOfWeeks) {
		Preconditions.checkNotNull(dayOfWeeks);
		for (DayOfWeek dayOfWeek : dayOfWeeks) {
			Preconditions.checkNotNull(dayOfWeek);
		}
		this.dayOfWeeks = Sets.newHashSet(dayOfWeeks);
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param dayOfWeek 曜日
	 * @param dayOfWeeks 曜日(optional)
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 */
	DayOfWeeksSpecification(DayOfWeek dayOfWeek, DayOfWeek... dayOfWeeks) {
		Preconditions.checkNotNull(dayOfWeek);
		Preconditions.checkNotNull(dayOfWeeks);
		for (DayOfWeek d : dayOfWeeks) {
			Preconditions.checkNotNull(d);
		}
		this.dayOfWeeks = Sets.newHashSet(dayOfWeeks);
		this.dayOfWeeks.add(dayOfWeek);
	}
	
	@Override
	public CalendarDate firstOccurrenceIn(CalendarInterval interval) {
		Preconditions.checkNotNull(interval);
		Preconditions.checkArgument(interval.hasLowerLimit());
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
		Preconditions.checkNotNull(date);
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
