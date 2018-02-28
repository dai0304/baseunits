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
package jp.xet.baseunits.spring;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import jp.xet.baseunits.time.CalendarMonth;

import com.google.common.base.Preconditions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;

/**
 * Spring {@link Converter} implementation for {@link CalendarMonth}.
 * 
 * @deprecated use {@link StringToCalendarMonthConverter}
 */
@Deprecated
public class CalendarMonthConverter implements Converter<String, CalendarMonth> {
	
	private static Logger logger = LoggerFactory.getLogger(CalendarMonthConverter.class);
	
	private static final String DEFAULT_PATTERN = "yyyy-MM";
	
	private String pattern;
	
	
	/**
	 * インスタンスを生成する。
	 * 
	 */
	public CalendarMonthConverter() {
		this(DEFAULT_PATTERN);
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param pattern 解析パターン文字列（{@link SimpleDateFormat}参照）
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 */
	public CalendarMonthConverter(String pattern) {
		Preconditions.checkNotNull(pattern);
		this.pattern = pattern;
	}
	
	@Override
	public CalendarMonth convert(String source) {
		try {
			return CalendarMonth.parse(source, pattern);
		} catch (ParseException e) {
			logger.error("fail to parse", e);
		}
		return null;
	}
}
