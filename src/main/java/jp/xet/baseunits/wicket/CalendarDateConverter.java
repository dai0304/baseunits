/*
 * Copyright 2010-2015 Miyamoto Daisuke.
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

import jp.xet.baseunits.time.CalendarDate;

import org.apache.wicket.util.convert.converter.AbstractConverter;
import org.apache.wicket.util.lang.Args;
import org.apache.wicket.util.string.Strings;

/**
 * Converts from Object to {@link CalendarDate}.
 * 
 * @author daisuke
 * @since 2.0
 */
@SuppressWarnings("serial")
public class CalendarDateConverter extends AbstractConverter<CalendarDate> {
	
	/** default date pattern for {@link SimpleDateFormat} */
	public static final String DEFAILT_PATTERN_JAVA = "yyyy/MM/dd";

	
	/** default date pattern for JQuery */
	public static final String DEFAILT_PATTERN_JQ = "yy/mm/dd";
	
	private final String datePattern;
	
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @since 1.0
	 */
	public CalendarDateConverter() {
		this(DEFAILT_PATTERN_JAVA);
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param datePattern {@link SimpleDateFormat}に基づくパターン
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	public CalendarDateConverter(String datePattern) {
		Args.notNull(datePattern, "datePattern");
		this.datePattern = datePattern;
	}
	
	@Override
	public CalendarDate convertToObject(String value, Locale locale) {
		if (Strings.isEmpty(value)) {
			return null;
		}
		
		try {
			return CalendarDate.parse(value, datePattern);
		} catch (ParseException e) {
			throw newConversionException("Cannot convert '" + value + "' to CalendarDate", value, locale);
		}
	}
	
	@Override
	public String convertToString(CalendarDate value, Locale locale) {
		return value == null ? null : value.toString(datePattern, locale);
	}
	
	@Override
	protected Class<CalendarDate> getTargetType() {
		return CalendarDate.class;
	}
}
