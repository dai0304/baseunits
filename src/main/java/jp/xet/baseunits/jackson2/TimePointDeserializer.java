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

import jp.xet.baseunits.time.TimePoint;
import jp.xet.baseunits.util.TimeZones;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

/**
 * {@link TimePoint}のデシリアライザ。
 * 
 * @since 2.13
 * @author daisuke
 */
public class TimePointDeserializer extends JsonDeserializer<TimePoint> {
	
	@Override
	public TimePoint deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
		try {
			return TimePoint.parse(jp.getText(), TimePoint.ISO8601_FORMAT_UNIVERSAL, TimeZones.UNIVERSAL);
		} catch (ParseException e) {
			return null;
		}
	}
}
