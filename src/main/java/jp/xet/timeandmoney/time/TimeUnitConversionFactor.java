/*
 * Copyright 2010 TRICREO, Inc. (http://tricreo.jp/)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 * ----
 * Copyright (c) 2004 Domain Language, Inc. (http://domainlanguage.com) This
 * free software is distributed under the "MIT" licence.
 * For more information, see http://timeandmoney.sourceforge.net.
 */
package jp.xet.timeandmoney.time;

enum TimeUnitConversionFactor {
	
	identical(1),

	millisecondsPerSecond(1000),

	millisecondsPerMinute(60 * millisecondsPerSecond.value),

	millisecondsPerHour(60 * millisecondsPerMinute.value),

	millisecondsPerDay(24 * millisecondsPerHour.value),

	millisecondsPerWeek(7 * millisecondsPerDay.value),

	monthsPerQuarter(3),

	monthsPerYear(12);
	
	final int value;
	

	TimeUnitConversionFactor(int factor) {
		value = factor;
	}
}
