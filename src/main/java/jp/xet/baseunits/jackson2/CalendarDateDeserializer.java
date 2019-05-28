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
package jp.xet.baseunits.jackson2;

import java.io.IOException;
import java.text.ParseException;

import jp.xet.baseunits.time.CalendarDate;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

/**
 * {@link CalendarDate}のデシリアライザ。
 * 
 * @since 1.13
 * @author daisuke
 */
public class CalendarDateDeserializer extends JsonDeserializer<CalendarDate> {
	
	private final String format;
	
	
	/**
	 * インスタンスを生成する。
	 */
	public CalendarDateDeserializer() {
		this("yyyy-MM-dd");
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param format 書式
	 */
	public CalendarDateDeserializer(String format) {
		this.format = format;
	}
	
	@Override
	public CalendarDate deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
		try {
			return CalendarDate.parse(jp.getText(), format);
		} catch (ParseException e) {
			return null;
		}
	}
	
	@Override
	public Class<CalendarDate> handledType() {
		return CalendarDate.class;
	}
}
