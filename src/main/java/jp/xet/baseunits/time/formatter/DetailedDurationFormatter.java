/*
 * Copyright 2011-2012 Daisuke Miyamoto. (http://d.hatena.ne.jp/daisuke-m)
 * Created on 2011/11/22
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
 */
package jp.xet.baseunits.time.formatter;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import jp.xet.baseunits.time.Duration;
import jp.xet.baseunits.time.TimeUnit;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ibm.icu.text.TimeUnitFormat;
import com.ibm.icu.util.TimeUnitAmount;

import org.apache.commons.lang.Validate;

/**
 * TODO for daisuke
 * 
 * @author daisuke
 * @since 2.5
 */
@SuppressWarnings("serial")
public class DetailedDurationFormatter extends AbstractDurationFormatter implements Serializable {
	
	private static final Map<TimeUnit, com.ibm.icu.util.TimeUnit> TIME_UNIT_MAP;
	
	static {
		Map<TimeUnit, com.ibm.icu.util.TimeUnit> map = Maps.newHashMap();
		map.put(TimeUnit.year, com.ibm.icu.util.TimeUnit.YEAR);
		map.put(TimeUnit.month, com.ibm.icu.util.TimeUnit.MONTH);
		map.put(TimeUnit.week, com.ibm.icu.util.TimeUnit.WEEK);
		map.put(TimeUnit.day, com.ibm.icu.util.TimeUnit.DAY);
		map.put(TimeUnit.hour, com.ibm.icu.util.TimeUnit.HOUR);
		map.put(TimeUnit.minute, com.ibm.icu.util.TimeUnit.MINUTE);
		map.put(TimeUnit.second, com.ibm.icu.util.TimeUnit.SECOND);
		TIME_UNIT_MAP = Collections.unmodifiableMap(map);
	}
	
	private final boolean allowZero;
	
	private final TimeUnit[] timeUnits;
	
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param allowZero 
	 * @param timeUnits 
	 * @since 2.5
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 */
	public DetailedDurationFormatter(boolean allowZero, TimeUnit... timeUnits) {
		Validate.noNullElements(timeUnits);
		this.allowZero = allowZero;
		this.timeUnits = timeUnits.clone();
	}
	
	@Override
	public String format(Duration target, Locale locale) {
		Validate.notNull(target);
		Validate.notNull(locale);
		
		TimeUnitFormat format = new TimeUnitFormat(locale);
		
		List<TimeUnitAmount> amounts = divide(target);
		List<String> sections = Lists.newArrayListWithCapacity(amounts.size());
		for (TimeUnitAmount amount : amounts) {
			sections.add(format.format(amount));
		}
		
		return Joiner.on(' ').join(sections);
	}
	
	private List<TimeUnitAmount> divide(Duration target) {
		Validate.notNull(target);
		
		List<TimeUnitAmount> result = Lists.newArrayListWithCapacity(timeUnits.length);
		for (TimeUnit timeUnit : timeUnits) {
			long value = target.to(timeUnit);
			if (value != 0 || allowZero) {
				if (TIME_UNIT_MAP.containsKey(timeUnit)) {
					target = target.minus(new Duration(value, timeUnit));
					result.add(new TimeUnitAmount(value, TIME_UNIT_MAP.get(timeUnit)));
				}
			}
		}
		
		return result;
	}
}
