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
package jp.xet.baseunits.spring;

import java.text.SimpleDateFormat;

import jp.xet.baseunits.time.CalendarDate;
import jp.xet.baseunits.time.CalendarMonth;

import com.google.common.base.Preconditions;

import org.springframework.core.convert.converter.Converter;

/**
 * {@link String}から{@link CalendarMonth}に変換する {@link Converter}実装クラス。
 * 
 * @since #version#
 * @version $Id$
 * @author daisuke
 */
public class CalendarDateToStringConverter implements Converter<CalendarDate, String> {
	
	private static final String DEFAULT_PATTERN = "yyyy-MM-dd";
	
	private String pattern;
	
	
	/**
	 * インスタンスを生成する。
	 * 
	 */
	public CalendarDateToStringConverter() {
		this(DEFAULT_PATTERN);
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param pattern 解析パターン文字列（{@link SimpleDateFormat}参照）
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 */
	public CalendarDateToStringConverter(String pattern) {
		Preconditions.checkNotNull(pattern);
		this.pattern = pattern;
	}
	
	@Override
	public String convert(CalendarDate source) {
		return source == null ? null : source.toString(pattern);
	}
}
