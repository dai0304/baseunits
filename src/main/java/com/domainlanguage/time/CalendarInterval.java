/**
 * Copyright (c) 2004 Domain Language, Inc. (http://domainlanguage.com) This
 * free software is distributed under the "MIT" licence. See file licence.txt.
 * For more information, see http://timeandmoney.sourceforge.net.
 */

package com.domainlanguage.time;

import java.util.Calendar;
import java.util.Iterator;
import java.util.TimeZone;

import com.domainlanguage.intervals.Interval;
import com.domainlanguage.util.ImmutableIterator;

public abstract class CalendarInterval extends Interval {
	
	/** TODO for daisuke */
	private static final long serialVersionUID = 4323424254035516684L;
	

	public static CalendarInterval everFrom(CalendarDate startDate) {
		return inclusive(startDate, null);
	}
	
	public static CalendarInterval everPreceding(CalendarDate endDate) {
		return inclusive(null, endDate);
	}
	
	public static CalendarInterval inclusive(CalendarDate start, CalendarDate end) {
		return ConcreteCalendarInterval.from(start, end);
	}
	
	public static CalendarInterval inclusive(int startYear, int startMonth, int startDay, int endYear, int endMonth,
			int endDay) {
		CalendarDate startDate = CalendarDate.from(startYear, startMonth, startDay);
		CalendarDate endDate = CalendarDate.from(endYear, endMonth, endDay);
		return ConcreteCalendarInterval.from(startDate, endDate);
	}
	
	public static CalendarInterval month(int year, int month) {
		CalendarDate startDate = CalendarDate.date(year, month, 1);
		CalendarDate endDate = startDate.plusMonths(1).plusDays(-1);
		return inclusive(startDate, endDate);
	}
	
	public static CalendarInterval startingFrom(CalendarDate start, Duration length) {
		// Uses the common default for calendar intervals, [start, end].
		return inclusive(start, start.plus(length).plusDays(-1));
	}
	
	public static CalendarInterval year(int year) {
		CalendarDate startDate = CalendarDate.date(year, 1, 1);
		CalendarDate endDate = CalendarDate.date(year + 1, 1, 1).plusDays(-1);
		return inclusive(startDate, endDate);
	}
	
	public abstract TimeInterval asTimeInterval(TimeZone zone);
	
	public Iterator daysInReverseIterator() {
		final CalendarDate start = (CalendarDate) upperLimit();
		final CalendarDate end = (CalendarDate) lowerLimit();
		return new ImmutableIterator() {
			
			CalendarDate next = start;
			

			@Override
			public boolean hasNext() {
				return !next.isBefore(end);
			}
			
			@Override
			public Object next() {
				Object current = next;
				next = next.plusDays(-1);
				return current;
			}
		};
	}
	
	public Iterator daysIterator() {
		final CalendarDate start = (CalendarDate) lowerLimit();
		final CalendarDate end = (CalendarDate) upperLimit();
		return new ImmutableIterator() {
			
			CalendarDate next = start;
			

			@Override
			public boolean hasNext() {
				return !next.isAfter(end);
			}
			
			@Override
			public Object next() {
				Object current = next;
				next = next.plusDays(1);
				return current;
			}
		};
	}
	
	public CalendarDate end() {
		return (CalendarDate) upperLimit();
	}
	
	public boolean equals(CalendarInterval other) {
		return other != null && upperLimit().equals(other.upperLimit()) && lowerLimit().equals(other.lowerLimit());
	}
	
	@Override
	public boolean equals(Object object) {
		try {
			return equals((CalendarInterval) object);
		} catch (ClassCastException ex) {
			return false;
		}
	}
	
	@Override
	public int hashCode() {
		return lowerLimit().hashCode();
	}
	
	@Override
	public boolean includesLowerLimit() {
		return true;
	}
	
	@Override
	public boolean includesUpperLimit() {
		return true;
	}
	
	public Duration length() {
		return Duration.days(lengthInDaysInt());
	}
	
	public int lengthInDaysInt() {
		Calendar calStart = start().asJavaCalendarUniversalZoneMidnight();
		Calendar calEnd = end().plusDays(1).asJavaCalendarUniversalZoneMidnight();
		long diffMillis = calEnd.getTimeInMillis() - calStart.getTimeInMillis();
		return (int) (diffMillis / TimeUnitConversionFactor.millisecondsPerDay.value);
	}
	
	public Duration lengthInMonths() {
		return Duration.months(lengthInMonthsInt());
	}
	
	public int lengthInMonthsInt() {
		Calendar calStart = start().asJavaCalendarUniversalZoneMidnight();
		Calendar calEnd = end().plusDays(1).asJavaCalendarUniversalZoneMidnight();
		int yearDiff = calEnd.get(Calendar.YEAR) - calStart.get(Calendar.YEAR);
		int monthDiff = yearDiff * 12 + calEnd.get(Calendar.MONTH) - calStart.get(Calendar.MONTH);
		return monthDiff;
	}
	
	@Override
	public Interval newOfSameType(Comparable lower, boolean isLowerClosed, Comparable upper, boolean isUpperClosed) {
		CalendarDate includedLower = isLowerClosed ? (CalendarDate) lower : ((CalendarDate) lower).plusDays(1);
		CalendarDate includedUpper = isUpperClosed ? (CalendarDate) upper : ((CalendarDate) upper).plusDays(-1);
		return inclusive(includedLower, includedUpper);
	}
	
	public CalendarDate start() {
		return (CalendarDate) lowerLimit();
	}
	
	public Iterator subintervalIterator(Duration subintervalLength) {
		//assert TimeUnit.day.compareTo(subintervalLength.normalizedUnit()) <=
		// 0;
		if (TimeUnit.DAY.compareTo(subintervalLength.normalizedUnit()) > 0) {
			throw new IllegalArgumentException("CalendarIntervals must be a whole number of days or months.");
		}
		
		final Interval totalInterval = this;
		final Duration segmentLength = subintervalLength;
		return new ImmutableIterator() {
			
			CalendarInterval next = segmentLength.startingFrom(start());
			

			@Override
			public boolean hasNext() {
				return totalInterval.covers(next);
			}
			
			@Override
			public Object next() {
				if (!hasNext()) {
					return null;
				}
				CalendarInterval current = next;
				next = segmentLength.startingFrom(next.end().plusDays(1));
				return current;
			}
		};
	}
	
}
