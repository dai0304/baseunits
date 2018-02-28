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
package jp.xet.baseunits.jackson2;

import java.io.IOException;

import jp.xet.baseunits.time.CalendarMonth;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * {@link CalendarMonth}のシリアライザ。
 * 
 * @since 1.13
 * @author daisuke
 */
public class CalendarMonthSerializer extends JsonSerializer<CalendarMonth> {
	
	private final String format;
	
	
	/**
	 * インスタンスを生成する。
	 */
	public CalendarMonthSerializer() {
		this("yyyy-MM");
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param format 書式
	 */
	public CalendarMonthSerializer(String format) {
		this.format = format;
	}
	
	@Override
	public Class<CalendarMonth> handledType() {
		return CalendarMonth.class;
	}
	
	@Override
	public void serialize(CalendarMonth value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
		String string = (value == null) ? "" : value.toString(format);
		jgen.writeString(string);
	}
}
