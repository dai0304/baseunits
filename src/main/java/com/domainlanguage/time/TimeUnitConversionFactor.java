/**
 * Copyright (c) 2004 Domain Language, Inc. (http://domainlanguage.com) This
 * free software is distributed under the "MIT" licence. See file licence.txt.
 * For more information, see http://timeandmoney.sourceforge.net.
 */

package com.domainlanguage.time;

enum TimeUnitConversionFactor {
	
	/** 1 */
	one(1),

	/** 1000 */
	millisecondsPerSecond(1000),

	millisecondsPerMinute(60 * TimeUnitConversionFactor.millisecondsPerSecond.value),

	millisecondsPerHour(60 * TimeUnitConversionFactor.millisecondsPerMinute.value),

	millisecondsPerDay(24 * TimeUnitConversionFactor.millisecondsPerHour.value),

	millisecondsPerWeek(7 * TimeUnitConversionFactor.millisecondsPerDay.value),

	/** 3 */
	monthsPerQuarter(3),

	/** 12 */
	monthsPerYear(12);
	
	public final int value;
	

	TimeUnitConversionFactor(int value) {
		this.value = value;
	}
}
