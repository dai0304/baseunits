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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

import jp.xet.baseunits.time.TimePointOfDay;

import org.apache.wicket.util.convert.converter.AbstractConverter;
import org.apache.wicket.util.lang.Args;
import org.apache.wicket.util.string.Strings;

/**
 * Converts from Object to {@link TimePointOfDay}.
 * 
 * @author daisuke
 * @since 2.0
 */
@SuppressWarnings("serial")
public class TimePointOfDayConverter extends AbstractConverter<TimePointOfDay> {
	
	/** default time pattern */
	public static final String DEFAILT_PATTERN = "HH:mm";
	
	private final String timePattern;
	
	private final TimeZone timeZone;
	
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param timePattern {@link SimpleDateFormat}に基づくパターン
	 * @param timeZone タイムゾーン
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 */
	public TimePointOfDayConverter(String timePattern, TimeZone timeZone) {
		Args.notNull(timePattern, "timePattern");
		Args.notNull(timeZone, "timeZone");
		this.timePattern = timePattern;
		this.timeZone = timeZone;
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param timeZone タイムゾーン
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 */
	public TimePointOfDayConverter(TimeZone timeZone) {
		this(DEFAILT_PATTERN, timeZone);
	}
	
	@Override
	public TimePointOfDay convertToObject(String value, Locale locale) {
		if (Strings.isEmpty(value)) {
			return null;
		}
		
		try {
			return TimePointOfDay.parse(value, timePattern, timeZone);
		} catch (ParseException e) {
			throw newConversionException("Cannot convert '" + value + "' to TimePointOfDay", value, locale);
		}
	}
	
	@Override
	public String convertToString(TimePointOfDay value, Locale locale) {
		return value == null ? null : value.toString(timePattern, timeZone);
	}
	
	@Override
	protected Class<TimePointOfDay> getTargetType() {
		return TimePointOfDay.class;
	}
}
