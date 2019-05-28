/*
 * Copyright 2010-2019 Miyamoto Daisuke.
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
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import jp.xet.baseunits.time.Duration;
import jp.xet.baseunits.time.TimeUnit;

import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ibm.icu.text.TimeUnitFormat;
import com.ibm.icu.util.TimeUnitAmount;

/**
 * 時間量を文字列に整形するフォーマッタ実装クラス。
 * 
 * @author daisuke
 * @since 2.5
 */
@SuppressWarnings("serial")
public class DetailedDurationFormatter extends AbstractDurationFormatter implements Serializable {
	
	private static final String DEFAULT_SEPARATOR = " ";
	
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
	
	private final String separator;
	
	private final TimeUnit[] timeUnits;
	
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param allowZero 値が{@code 0}となるセクションを表示する場合は{@code true}、そうでない場合は{@code false}
	 * @param separator セクション区切り文字列
	 * @param timeUnit 1番目のセクションの単位
	 * @param timeUnits 2番目以降のセクションの単位
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @since 2.9
	 */
	public DetailedDurationFormatter(boolean allowZero, String separator, TimeUnit timeUnit, TimeUnit... timeUnits) {
		Preconditions.checkNotNull(separator);
		Preconditions.checkNotNull(timeUnit);
		Preconditions.checkNotNull(timeUnits);
		for (TimeUnit u : timeUnits) {
			Preconditions.checkNotNull(u);
		}
		this.allowZero = allowZero;
		this.separator = separator;
		this.timeUnits = new TimeUnit[timeUnits.length + 1];
		this.timeUnits[0] = timeUnit;
		System.arraycopy(timeUnits, 0, this.timeUnits, 1, timeUnits.length);
		
		for (TimeUnit u : this.timeUnits) {
			Preconditions.checkArgument(TIME_UNIT_MAP.containsKey(u));
		}
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param allowZero 値が{@code 0}となるセクションを表示する場合は{@code true}、そうでない場合は{@code false}
	 * @param timeUnit 1番目のセクションの単位
	 * @param timeUnits 2番目以降のセクションの単位
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @since 2.5
	 */
	public DetailedDurationFormatter(boolean allowZero, TimeUnit timeUnit, TimeUnit... timeUnits) {
		this(allowZero, DEFAULT_SEPARATOR, timeUnit, timeUnits);
	}
	
	@Override
	public String format(Duration target, Locale locale) {
		Preconditions.checkNotNull(target);
		Preconditions.checkNotNull(locale);
		
		TimeUnitFormat format = new TimeUnitFormat(locale);
		
		List<TimeUnitAmount> amounts = divide(target);
		if (amounts.isEmpty()) {
			TimeUnit lastUnit = timeUnits[timeUnits.length - 1];
			return format.format(new TimeUnitAmount(0, TIME_UNIT_MAP.get(lastUnit)));
		}
		List<String> sections = Lists.newArrayListWithCapacity(amounts.size());
		for (TimeUnitAmount amount : amounts) {
			sections.add(format.format(amount));
		}
		
		return Joiner.on(separator).join(sections);
	}
	
	private List<TimeUnitAmount> divide(Duration target) {
		Preconditions.checkNotNull(target);
		
		List<TimeUnitAmount> result = Lists.newArrayListWithCapacity(timeUnits.length);
		for (TimeUnit timeUnit : timeUnits) {
			long value = target.to(timeUnit);
			if (value != 0 || allowZero) {
				target = target.minus(new Duration(value, timeUnit));
				result.add(new TimeUnitAmount(value, TIME_UNIT_MAP.get(timeUnit)));
			}
		}
		
		return result;
	}
}
