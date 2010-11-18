/**
 * Copyright (c) 2004 Domain Language, Inc. (http://domainlanguage.com) This
 * free software is distributed under the "MIT" licence. See file licence.txt.
 * For more information, see http://timeandmoney.sourceforge.net.
 */

package com.domainlanguage.time;

enum TimeUnitConversionFactor {
	
	identical(1),

	millisecondsPerSecond(1000), millisecondsPerMinute(60 * millisecondsPerSecond.value), millisecondsPerHour(
			60 * millisecondsPerMinute.value), millisecondsPerDay(24 * millisecondsPerHour.value), millisecondsPerWeek(
			7 * millisecondsPerDay.value), monthsPerQuarter(3), monthsPerYear(12);
	
	final int value;
	

	TimeUnitConversionFactor(int factor) {
		this.value = factor;
	}
}
