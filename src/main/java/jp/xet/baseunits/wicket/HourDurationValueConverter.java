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
package jp.xet.baseunits.wicket;

import java.util.Locale;

import jp.xet.baseunits.time.Duration;
import jp.xet.baseunits.time.TimeUnit;

import org.apache.wicket.util.convert.converter.AbstractConverter;

/**
 * {@link TimeUnit#hour}時間量値と {@link Duration} を相互変換するコンバータ実装クラス。
 * 
 * @author daisuke
 * @since 2.6
 */
@SuppressWarnings("serial")
public class HourDurationValueConverter extends AbstractConverter<Duration> {
	
	@Override
	public Duration convertToObject(String value, Locale locale) {
		long hours;
		try {
			hours = Long.parseLong(value);
		} catch (NumberFormatException e) {
			throw newConversionException("Cannot convert '" + value + "' to Long", value, locale);
		}
		if (hours < 0) {
			throw newConversionException("Value '" + value + "' must be zero or positive", value, locale);
		}
		return Duration.hours(hours);
	}
	
	@Override
	public String convertToString(Duration value, Locale locale) {
		return value == null ? null : String.valueOf(value.to(TimeUnit.hour));
	}
	
	@Override
	protected Class<Duration> getTargetType() {
		return Duration.class;
	}
}
