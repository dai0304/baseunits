/**
 * Copyright (c) 2004 Domain Language, Inc. (http://domainlanguage.com) This
 * free software is distributed under the "MIT" licence. See file licence.txt.
 * For more information, see http://timeandmoney.sourceforge.net.
 */

package com.domainlanguage.time;

import java.util.Iterator;

import com.domainlanguage.intervals.Interval;
import com.domainlanguage.util.ImmutableIterator;

public class TimeInterval extends Interval {
	
	/** TODO for daisuke */
	private static final long serialVersionUID = -3401251022581709549L;
	

	public static TimeInterval closed(TimePoint start, TimePoint end) {
		return over(start, true, end, true);
	}
	
	public static TimeInterval everFrom(TimePoint start) {
		return over(start, null);
	}
	
	public static TimeInterval everPreceding(TimePoint end) {
		return over(null, end);
	}
	
	public static TimeInterval open(TimePoint start, TimePoint end) {
		return over(start, false, end, false);
	}
	
	public static TimeInterval over(TimePoint start, boolean closedStart, TimePoint end, boolean closedEnd) {
		return new TimeInterval(start, closedStart, end, closedEnd);
	}
	
	public static TimeInterval over(TimePoint start, TimePoint end) {
		//Uses the common default for time intervals, [start, end).
		return over(start, true, end, false);
	}
	
	public static TimeInterval preceding(TimePoint end, boolean startClosed, Duration length, boolean endClosed) {
		TimePoint start = end.minus(length);
		return over(start, startClosed, end, endClosed);
	}
	
	public static TimeInterval preceding(TimePoint end, Duration length) {
		//Uses the common default for time intervals, [start, end).
		return preceding(end, true, length, false);
	}
	
	public static TimeInterval startingFrom(TimePoint start, boolean startClosed, Duration length, boolean endClosed) {
		TimePoint end = start.plus(length);
		return over(start, startClosed, end, endClosed);
	}
	
	public static TimeInterval startingFrom(TimePoint start, Duration length) {
		//Uses the common default for time intervals, [start, end).
		return startingFrom(start, true, length, false);
	}
	
	public TimeInterval(TimePoint start, boolean startIncluded, TimePoint end, boolean endIncluded) {
		super(start, startIncluded, end, endIncluded);
	}
	
	//Only for use by persistence mapping frameworks
	//<rant>These methods break encapsulation and we put them in here begrudgingly</rant>
	TimeInterval() {
	}
	
	public Iterator daysIterator() {
		return new ImmutableIterator() {
			
			TimePoint next = start();
			

			@Override
			public boolean hasNext() {
				return end().isAfter(next);
			}
			
			@Override
			public Object next() {
				Object current = next;
				next = next.nextDay();
				return current;
			}
		};
	}
	
	public TimePoint end() {
		return (TimePoint) upperLimit();
	}
	
	public TimeInterval intersect(TimeInterval interval) {
		return (TimeInterval) intersect((Interval) interval);
	}
	
	public boolean isAfter(TimePoint point) {
		return isAbove(point);
	}
	
	public boolean isBefore(TimePoint point) {
		return isBelow(point);
	}
	
	public Duration length() {
		long difference = end().millisecondsFromEpoc - start().millisecondsFromEpoc;
		return Duration.milliseconds(difference);
	}
	
	@Override
	public Interval newOfSameType(Comparable start, boolean isStartClosed, Comparable end, boolean isEndClosed) {
		return new TimeInterval((TimePoint) start, isStartClosed, (TimePoint) end, isEndClosed);
	}
	
	public TimePoint start() {
		return (TimePoint) lowerLimit();
	}
	
	public Iterator subintervalIterator(Duration subintervalLength) {
		final Duration segmentLength = subintervalLength;
		final Interval totalInterval = this;
		return new ImmutableIterator() {
			
			TimeInterval next = segmentLength.startingFrom(start());
			

			@Override
			public boolean hasNext() {
				return totalInterval.covers(next);
			}
			
			@Override
			public Object next() {
				if (!hasNext()) {
					return null;
				}
				Object current = next;
				next = segmentLength.startingFrom(next.end());
				return current;
			}
		};
	}
}
