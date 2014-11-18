/*
 * Copyright 2013-2014 Daisuke Miyamoto. (http://d.hatena.ne.jp/daisuke-m)
 * Created on 2014/11/18
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
package jp.xet.baseunits.jackson2;

import java.io.IOException;

import jp.xet.baseunits.time.CalendarMonth;
import jp.xet.baseunits.time.Duration;
import jp.xet.baseunits.time.TimeUnit;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

/**
 * {@link CalendarMonth}のシリアライザ。
 * 
 * @since 1.13
 * @author daisuke
 */
public class DurationDeserializer extends JsonDeserializer<Duration> {
	
	private final TimeUnit timeUnit;
	
	
	/**
	 * インスタンスを生成する。
	 */
	public DurationDeserializer() {
		this(TimeUnit.millisecond);
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param timeUnit 単位
	 */
	public DurationDeserializer(TimeUnit timeUnit) {
		this.timeUnit = timeUnit;
	}
	
	@Override
	public Duration deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
		return Duration.valueOf(jp.getNumberValue().longValue(), timeUnit);
	}
	
	@Override
	public Class<Duration> handledType() {
		return Duration.class;
	}
}
