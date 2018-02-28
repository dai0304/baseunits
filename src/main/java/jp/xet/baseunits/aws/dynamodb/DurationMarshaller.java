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
package jp.xet.baseunits.aws.dynamodb;

import jp.xet.baseunits.time.Duration;
import jp.xet.baseunits.time.TimeUnit;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * {@link DynamoDBMarshaller} implementation for {@link Duration}.
 * 
 * @since #version#
 * @author daisuke
 */
public class DurationMarshaller implements DynamoDBMarshaller<Duration> {
	
	private static Logger logger = LoggerFactory.getLogger(DurationMarshaller.class);
	
	private static final String NULL = "null";
	
	private final TimeUnit timeUnit;
	
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @since #version#
	 */
	public DurationMarshaller() {
		this(TimeUnit.millisecond);
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param timeUnit 単位
	 * @since #version#
	 */
	public DurationMarshaller(TimeUnit timeUnit) {
		this.timeUnit = timeUnit;
	}
	
	@Override
	public String marshall(Duration obj) {
		return obj == null ? NULL : String.valueOf(obj.to(timeUnit));
	}
	
	@Override
	public Duration unmarshall(Class<Duration> clazz, String obj) {
		if (obj == null || obj.equals(NULL)) {
			return null;
		}
		try {
			return Duration.valueOf(Long.parseLong(obj), timeUnit);
		} catch (NumberFormatException e) {
			logger.warn("", e);
		}
		return null;
	}
}
