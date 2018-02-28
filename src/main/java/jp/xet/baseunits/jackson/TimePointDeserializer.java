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
package jp.xet.baseunits.jackson;

import java.io.IOException;
import java.text.ParseException;
import java.util.TimeZone;

import jp.xet.baseunits.time.TimePoint;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.ObjectCodec;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.deser.std.StdDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Jackson data deserializer implementation for {@link TimePoint}.
 * 
 * @author daisuke
 */
public class TimePointDeserializer extends StdDeserializer<TimePoint> {
	
	private static Logger logger = LoggerFactory.getLogger(TimePointDeserializer.class);
	
	private static final TimeZone UTC = TimeZone.getTimeZone("Universal");
	
	
	/**
	 * インスタンスを生成する。
	 */
	public TimePointDeserializer() {
		super(TimePoint.class);
	}
	
	@Override
	public TimePoint deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
		ObjectCodec oc = jp.getCodec();
		JsonNode node = oc.readTree(jp);
		String textValue = node.getTextValue();
		try {
			return TimePoint.parse(textValue, "yyyy-MM-dd'T'HH:mm:ssZZ", UTC);
		} catch (ParseException e) {
			logger.error("", e);
		}
		return null;
	}
}
