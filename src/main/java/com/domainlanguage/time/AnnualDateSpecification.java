/**
 * Copyright (c) 2004 Domain Language, Inc. (http://domainlanguage.com) This
 * free software is distributed under the "MIT" licence. See file licence.txt.
 * For more information, see http://timeandmoney.sourceforge.net.
 */

package com.domainlanguage.time;

import java.util.Iterator;

import com.domainlanguage.util.ImmutableIterator;

public abstract class AnnualDateSpecification extends DateSpecification {
	
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
	public Iterator iterateOver(final CalendarInterval interval) {
		final AnnualDateSpecification spec = this;
		return new ImmutableIterator() {
			
			CalendarDate next = firstOccurrenceIn(interval);
			
			int year = next.breachEncapsulationOf_year();
			

			@Override
			public boolean hasNext() {
				return next != null;
			}
			
			@Override
			public Object next() {
				if (next == null) {
					return null;
				}
				Object current = next;
				year += 1;
				next = spec.ofYear(year);
				if (!interval.includes(next)) {
					next = null;
				}
				return current;
			}
		};
	}
	
	public abstract CalendarDate ofYear(int year);
}
