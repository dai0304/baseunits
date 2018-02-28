/*
 * Copyright 2010-2018 Miyamoto Daisuke.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package jp.xet.baseunits.time.formatter;

import java.io.Serializable;
import java.util.Locale;

import jp.xet.baseunits.time.Duration;

import com.ibm.icu.text.TimeUnitFormat;
import com.ibm.icu.util.TimeUnit;
import com.ibm.icu.util.TimeUnitAmount;
import com.ibm.icu.util.ULocale;

import com.google.common.base.Preconditions;

/**
 * ICU4Jを利用した {@link DurationFormatter} 実装クラス。
 * 
 * @author daisuke
 * @since 2.0
 */
@SuppressWarnings("serial")
public class Icu4jDurationFormatter extends AbstractDurationFormatter implements Serializable {
	
	@Override
	public String format(Duration target, Locale locale) {
		Preconditions.checkNotNull(target);
		Preconditions.checkNotNull(locale);
		
		TimeUnitAmount source = convert(target);
		TimeUnitFormat format = new TimeUnitFormat(ULocale.forLocale(locale));
		String formatted = format.format(source);
		return formatted;
	}
	
	private TimeUnitAmount convert(Duration target) {
		TimeUnit unit;
		long number = target.breachEncapsulationOfQuantity();
		
		switch (target.breachEncapsulationOfUnit()) {
			case millisecond:
				unit = TimeUnit.SECOND;
				number /= 1000; // CHECKSTYLE IGNORE THIS LINE
				break;
			
			case second:
				unit = TimeUnit.SECOND;
				break;
			
			case minute:
				unit = TimeUnit.MINUTE;
				break;
			
			case hour:
				unit = TimeUnit.HOUR;
				break;
			
			case day:
				unit = TimeUnit.DAY;
				break;
			
			case week:
				unit = TimeUnit.WEEK;
				break;
			
			case month:
				unit = TimeUnit.MONTH;
				break;
			
			case quarter:
				unit = TimeUnit.MONTH;
				number *= 3;
				break;
			
			case year:
				unit = TimeUnit.YEAR;
				break;
			
			default:
				throw new AssertionError();
		}
		return new TimeUnitAmount(number, unit);
	}
}
