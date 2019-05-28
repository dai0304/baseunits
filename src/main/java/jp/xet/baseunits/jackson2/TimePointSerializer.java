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

import jp.xet.baseunits.time.TimePoint;
import jp.xet.baseunits.util.TimeZones;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * {@link TimePoint}のシリアライザ。
 * 
 * @since 2.13
 * @author daisuke
 */
public class TimePointSerializer extends JsonSerializer<TimePoint> {
	
	@Override
	public Class<TimePoint> handledType() {
		return TimePoint.class;
	}
	
	@Override
	public void serialize(TimePoint value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
		String string = (value == null) ? "" : value.toString(TimeZones.UNIVERSAL);
		jgen.writeString(string);
	}
}
