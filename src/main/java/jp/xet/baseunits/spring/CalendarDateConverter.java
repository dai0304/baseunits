/*
 * Copyright 2013 Daisuke Miyamoto. (http://d.hatena.ne.jp/daisuke-m)
 * Created on 2013/08/20
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
package jp.xet.baseunits.spring;

import java.text.ParseException;

import jp.xet.baseunits.time.CalendarDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;

/**
 * Spring {@link Converter} implementation for {@link CalendarDate}.
 * 
 * @author daisuke
 */
public class CalendarDateConverter implements Converter<String, CalendarDate> {
	
	private static Logger logger = LoggerFactory.getLogger(CalendarDateConverter.class);
	
	private static final String DEFAULT_PATTERN = "yyyy-MM-dd";
	
	private String pattern;
	
	
	/**
	 * インスタンスを生成する。
	 */
	public CalendarDateConverter() {
		this(DEFAULT_PATTERN);
	}
	
	/**
	 * インスタンスを生成する。
	 * @param pattern 
	 * 
	 */
	public CalendarDateConverter(String pattern) {
		this.pattern = pattern;
	}
	
	@Override
	public CalendarDate convert(String source) {
		try {
			return CalendarDate.parse(source, pattern);
		} catch (ParseException e) {
			logger.error("fail to parse", e);
		}
		return null;
	}
	
}
