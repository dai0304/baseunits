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
package jp.xet.baseunits.jackson;

import java.io.IOException;

import jp.xet.baseunits.time.CalendarDate;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.ser.std.SerializerBase;

/**
 * Jackson data serializer implementation for {@link CalendarDate}.
 * 
 * @author daisuke
 */
public class CalendarDateSerializer extends SerializerBase<CalendarDate> {
	
	/**
	 * インスタンスを生成する。
	 */
	public CalendarDateSerializer() {
		super(CalendarDate.class);
	}
	
	@Override
	public void serialize(CalendarDate value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
		String string = (value == null) ? "" : value.toString("yyyy-MM-dd");
		jgen.writeString(string);
	}
}
