/**
 * Copyright (c) 2004 Domain Language, Inc. (http://domainlanguage.com) This
 * free software is distributed under the "MIT" licence. See file licence.txt.
 * For more information, see http://timeandmoney.sourceforge.net.
 */

package com.domainlanguage.time;

import java.util.TimeZone;

class ConcreteCalendarInterval extends CalendarInterval {
	
	/** TODO for daisuke */
	private static final long serialVersionUID = 5791375180384875227L;
	

	static ConcreteCalendarInterval from(CalendarDate start, CalendarDate end) {
		
		return new ConcreteCalendarInterval(start, end);
	}
	
	private static void assertStartIsBeforeEnd(CalendarDate start, CalendarDate end) {
		if (start != null && end != null && start.compareTo(end) > 0) {
			throw new IllegalArgumentException(start + " is not before or equal to " + end);
		}
	}
	

	private CalendarDate start;
	
	private CalendarDate end;
	

	//Only for use by persistence mapping frameworks
	//<rant>These methods break encapsulation and we put them in here begrudgingly</rant>
	ConcreteCalendarInterval() {
	}
	
	ConcreteCalendarInterval(CalendarDate start, CalendarDate end) {
		assertStartIsBeforeEnd(start, end);
		this.start = start;
		this.end = end;
	}
	
	@Override
	public TimeInterval asTimeInterval(TimeZone zone) {
		TimePoint startPoint = start.asTimeInterval(zone).start();
		TimePoint endPoint = end.asTimeInterval(zone).end();
		return TimeInterval.over(startPoint, endPoint);
	}
	
	@Override
	public Comparable lowerLimit() {
		return start;
	}
	
	@Override
	public Comparable upperLimit() {
		return end;
	}
	
	//Only for use by persistence mapping frameworks
	//<rant>These methods break encapsulation and we put them in here begrudgingly</rant>
	private CalendarDate getForPersistentMapping_End() {
		return end;
	}
	
	//Only for use by persistence mapping frameworks
	//<rant>These methods break encapsulation and we put them in here begrudgingly</rant>
	private CalendarDate getForPersistentMapping_Start() {
		return start;
	}
	
	//Only for use by persistence mapping frameworks
	//<rant>These methods break encapsulation and we put them in here begrudgingly</rant>
	private void setForPersistentMapping_End(CalendarDate end) {
		this.end = end;
	}
	
	//Only for use by persistence mapping frameworks
	//<rant>These methods break encapsulation and we put them in here begrudgingly</rant>
	private void setForPersistentMapping_Start(CalendarDate start) {
		this.start = start;
	}
	
}
