/**
 * Copyright (c) 2004 Domain Language, Inc. (http://domainlanguage.com) This
 * free software is distributed under the "MIT" licence. See file licence.txt.
 * For more information, see http://timeandmoney.sourceforge.net.
 */

package com.domainlanguage.intervals;

public interface IntervalMap {
	
	boolean containsIntersectingKey(Interval interval);
	
	boolean containsKey(Comparable key);
	
	Object get(Comparable key);
	
	void put(Interval keyInterval, Object value);
	
	void remove(Interval keyInterval);
}
