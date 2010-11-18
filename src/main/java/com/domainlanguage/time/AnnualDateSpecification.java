/**
 * Copyright (c) 2004 Domain Language, Inc. (http://domainlanguage.com) This
 * free software is distributed under the "MIT" licence. See file licence.txt.
 * For more information, see http://timeandmoney.sourceforge.net.
 */

package com.domainlanguage.time;

import java.util.Iterator;
import java.util.NoSuchElementException;

import com.domainlanguage.util.ImmutableIterator;


public abstract class AnnualDateSpecification extends DateSpecification {
	
	public abstract CalendarDate ofYear(int year);
	
	@Override
	public CalendarDate firstOccurrenceIn(CalendarInterval interval) {
		CalendarDate firstTry = ofYear(interval.start().breachEncapsulationOf_year());
		if (interval.includes(firstTry)) {
			return firstTry;
		}
		CalendarDate secondTry = ofYear(interval.start().breachEncapsulationOf_year() + 1);
		if (interval.includes(secondTry)) {
			return secondTry;
		}
		return null;
	}
	
	@Override
	public Iterator<CalendarDate> iterateOver(final CalendarInterval interval) {
		return new ImmutableIterator<CalendarDate>() {
			
			CalendarDate next = firstOccurrenceIn(interval);
			
			int year = next.breachEncapsulationOf_year();
			

			public boolean hasNext() {
				return next != null;
			}
			
			public CalendarDate next() {
				if (hasNext() == false) {
					throw new NoSuchElementException();
				}
				CalendarDate current = next;
				year += 1;
				next = AnnualDateSpecification.this.ofYear(year);
				if (interval.includes(next) == false) {
					next = null;
				}
				return current;
			}
		};
	}
}
