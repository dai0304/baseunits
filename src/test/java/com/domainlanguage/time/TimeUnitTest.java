/**
 * Copyright (c) 2004 Domain Language, Inc. (http://domainlanguage.com) This
 * free software is distributed under the "MIT" licence. See file licence.txt.
 * For more information, see http://timeandmoney.sourceforge.net.
 */

package com.domainlanguage.time;

import java.util.Calendar;

import junit.framework.TestCase;

import com.domainlanguage.tests.SerializationTester;

public class TimeUnitTest extends TestCase {
	
	public static TimeUnit exampleForPersistentMappingTesting() {
		return TimeUnit.SECOND;
	}
	
	public static TimeUnit.Type exampleTypeForPersistentMappingTesting() {
		return TimeUnit.Type.hour;
	}
	
	public void testComparison() {
		assertEquals(0, TimeUnit.HOUR.compareTo(TimeUnit.HOUR));
		assertTrue(TimeUnit.HOUR.compareTo(TimeUnit.MILLISECOND) > 0);
		assertTrue(TimeUnit.MILLISECOND.compareTo(TimeUnit.HOUR) < 0);
		assertTrue(TimeUnit.DAY.compareTo(TimeUnit.HOUR) > 0);
		assertTrue(TimeUnit.HOUR.compareTo(TimeUnit.DAY) < 0);
		
		assertTrue(TimeUnit.MONTH.compareTo(TimeUnit.DAY) > 0);
		assertTrue(TimeUnit.DAY.compareTo(TimeUnit.MONTH) < 0);
		assertTrue(TimeUnit.QUARTER.compareTo(TimeUnit.HOUR) > 0);
		
		assertEquals(0, TimeUnit.MONTH.compareTo(TimeUnit.MONTH));
		assertTrue(TimeUnit.QUARTER.compareTo(TimeUnit.YEAR) < 0);
		assertTrue(TimeUnit.YEAR.compareTo(TimeUnit.QUARTER) > 0);
	}
	
	public void testConvertibleToMilliseconds() {
		assertTrue(TimeUnit.MILLISECOND.isConvertibleToMilliseconds());
		assertTrue(TimeUnit.HOUR.isConvertibleToMilliseconds());
		assertTrue(TimeUnit.DAY.isConvertibleToMilliseconds());
		assertTrue(TimeUnit.WEEK.isConvertibleToMilliseconds());
		assertFalse(TimeUnit.MONTH.isConvertibleToMilliseconds());
		assertFalse(TimeUnit.YEAR.isConvertibleToMilliseconds());
	}
	
	public void testIsConvertableTo() {
		assertTrue(TimeUnit.HOUR.isConvertibleTo(TimeUnit.MINUTE));
		assertTrue(TimeUnit.MINUTE.isConvertibleTo(TimeUnit.HOUR));
		assertTrue(TimeUnit.YEAR.isConvertibleTo(TimeUnit.MONTH));
		assertTrue(TimeUnit.MONTH.isConvertibleTo(TimeUnit.YEAR));
		assertFalse(TimeUnit.MONTH.isConvertibleTo(TimeUnit.HOUR));
		assertFalse(TimeUnit.HOUR.isConvertibleTo(TimeUnit.MONTH));
	}
	
	public void testJavaCalendarConstantForBaseType() {
		assertEquals(Calendar.MILLISECOND, TimeUnit.MILLISECOND.javaCalendarConstantForBaseType());
		assertEquals(Calendar.MILLISECOND, TimeUnit.HOUR.javaCalendarConstantForBaseType());
		assertEquals(Calendar.MILLISECOND, TimeUnit.DAY.javaCalendarConstantForBaseType());
		assertEquals(Calendar.MILLISECOND, TimeUnit.WEEK.javaCalendarConstantForBaseType());
		assertEquals(Calendar.MONTH, TimeUnit.MONTH.javaCalendarConstantForBaseType());
		assertEquals(Calendar.MONTH, TimeUnit.QUARTER.javaCalendarConstantForBaseType());
		assertEquals(Calendar.MONTH, TimeUnit.YEAR.javaCalendarConstantForBaseType());
	}
	
	public void testNextFinerUnit() {
		assertEquals(TimeUnit.MINUTE, TimeUnit.HOUR.nextFinerUnit());
		assertEquals(TimeUnit.MONTH, TimeUnit.QUARTER.nextFinerUnit());
	}
	
	public void testSerialization() {
		SerializationTester.assertCanBeSerialized(TimeUnit.MONTH);
	}
	
	public void testToString() {
		assertEquals("month", TimeUnit.MONTH.toString());
	}
}
