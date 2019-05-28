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
package jp.xet.baseunits.wicket;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import jp.xet.baseunits.time.TimeOfDay;

import org.apache.wicket.util.convert.converter.AbstractConverter;
import org.apache.wicket.util.lang.Args;
import org.apache.wicket.util.string.Strings;

/**
 * Converts from Object to {@link TimeOfDay}.
 * 
 * @author daisuke
 * @since 2.0
 */
@SuppressWarnings("serial")
public class TimeOfDayConverter extends AbstractConverter<TimeOfDay> {
	
	/** default time pattern */
	public static final String DEFAILT_PATTERN = "HH:mm";
	
	private final String timePattern;
	
	
	/**
	 * インスタンスを生成する。
	 */
	public TimeOfDayConverter() {
		this(DEFAILT_PATTERN);
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param timePattern {@link SimpleDateFormat}に基づくパターン
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 */
	public TimeOfDayConverter(String timePattern) {
		Args.notNull(timePattern, "timePattern");
		this.timePattern = timePattern;
	}
	
	@Override
	public TimeOfDay convertToObject(String value, Locale locale) {
		if (Strings.isEmpty(value)) {
			return null;
		}
		
		try {
			return TimeOfDay.parse(value, timePattern);
		} catch (ParseException e) {
			throw newConversionException("Cannot convert '" + value + "' to TimeOfDay", value, locale);
		}
	}
	
	@Override
	public String convertToString(TimeOfDay value, Locale locale) {
		return value == null ? null : value.toString(timePattern);
	}
	
	@Override
	protected Class<TimeOfDay> getTargetType() {
		return TimeOfDay.class;
	}
}
