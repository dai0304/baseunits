/*
 * Copyright 2010-2015 Miyamoto Daisuke.
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

import java.util.Iterator;
import java.util.NoSuchElementException;

import jp.xet.baseunits.time.CalendarDate;
import jp.xet.baseunits.time.CalendarInterval;
import jp.xet.baseunits.util.ImmutableIterator;

import com.google.common.base.Preconditions;

/**
 * 1年間に1度だけマッチする暦日仕様の骨格実装。
 * 
 * @author daisuke
 * @since 1.0
 */
public abstract class AbstractAnnualDateSpecification extends AbstractDateSpecification implements
		AnnualDateSpecification {
	
	@Override
	public CalendarDate firstOccurrenceIn(CalendarInterval interval) {
		Preconditions.checkNotNull(interval);
		Preconditions.checkArgument(interval.hasLowerLimit());
		CalendarDate firstTry = ofYear(interval.start().asCalendarMonth().getYear());
		if (interval.includes(firstTry)) {
			return firstTry;
		}
		CalendarDate secondTry = ofYear(interval.start().asCalendarMonth().getYear() + 1);
		if (interval.includes(secondTry)) {
			return secondTry;
		}
		return null;
	}
	
	@Override
	public Iterator<CalendarDate> iterateOver(final CalendarInterval interval) {
		Preconditions.checkNotNull(interval);
		return new ImmutableIterator<CalendarDate>() {
			
			CalendarDate next = firstOccurrenceIn(interval);
			
			int year = next.asCalendarMonth().getYear();
			
			
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
				year += 1;
				next = AbstractAnnualDateSpecification.this.ofYear(year);
				if (interval.includes(next) == false) {
					next = null;
				}
				return current;
			}
		};
	}
}
