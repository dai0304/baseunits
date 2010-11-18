/**
 * Copyright (c) 2005 Domain Language, Inc. (http://domainlanguage.com) This
 * free software is distributed under the "MIT" licence. See file licence.txt.
 * For more information, see http://timeandmoney.sourceforge.net.
 */
package com.domainlanguage.intervals;

import java.io.Serializable;

class IntervalLimit implements Comparable, Serializable {
	
	/** TODO for daisuke */
	private static final long serialVersionUID = -2633134782034372937L;
	

	static IntervalLimit lower(boolean closed, Comparable value) {
		return new IntervalLimit(closed, true, value);
	}
	
	static IntervalLimit upper(boolean closed, Comparable value) {
		return new IntervalLimit(closed, false, value);
	}
	

	private boolean closed;
	
	private Comparable value;
	
	private boolean lower;
	

	//Only for use by persistence mapping frameworks
	//<rant>These methods break encapsulation and we put them in here begrudgingly</rant>
	IntervalLimit() {
	}
	
	IntervalLimit(boolean closed, boolean lower, Comparable value) {
		super();
		this.closed = closed;
		this.lower = lower;
		this.value = value;
	}
	
	@Override
	public int compareTo(Object another) {
		IntervalLimit other = ((IntervalLimit) another);
		Comparable otherValue = other.value;
		if (otherValue == value) {
			return 0;
		}
		if (value == null) {
			return lower ? -1 : 1;
		}
		if (otherValue == null) {
			return other.lower ? 1 : -1;
		}
		return value.compareTo(otherValue);
	}
	
	//Only for use by persistence mapping frameworks
	//<rant>These methods break encapsulation and we put them in here begrudgingly</rant>
	Comparable getForPersistentMapping_Value() {
		return value;
	}
	
	Comparable getValue() {
		return value;
	}
	
	boolean isClosed() {
		return closed;
	}
	
	//Only for use by persistence mapping frameworks
	//<rant>These methods break encapsulation and we put them in here begrudgingly</rant>
	boolean isForPersistentMapping_Closed() {
		return closed;
	}
	
	//Only for use by persistence mapping frameworks
	//<rant>These methods break encapsulation and we put them in here begrudgingly</rant>
	boolean isForPersistentMapping_Lower() {
		return lower;
	}
	
	boolean isLower() {
		return lower;
	}
	
	boolean isOpen() {
		return !closed;
	}
	
	boolean isUpper() {
		return !lower;
	}
	
	//Only for use by persistence mapping frameworks
	//<rant>These methods break encapsulation and we put them in here begrudgingly</rant>
	void setForPersistentMapping_Closed(boolean closed) {
		this.closed = closed;
	}
	
	//Only for use by persistence mapping frameworks
	//<rant>These methods break encapsulation and we put them in here begrudgingly</rant>
	void setForPersistentMapping_Lower(boolean lower) {
		this.lower = lower;
	}
	
	//Only for use by persistence mapping frameworks
	//<rant>These methods break encapsulation and we put them in here begrudgingly</rant>
	void setForPersistentMapping_Value(Comparable value) {
		this.value = value;
	}
}
