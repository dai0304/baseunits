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
package jp.xet.baseunits.aws.dynamodb;

import java.text.ParseException;

import jp.xet.baseunits.time.CalendarMonth;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * {@link DynamoDBMarshaller} implementation for {@link CalendarMonth}.
 * 
 * @since #version#
 * @author daisuke
 */
public class CalendarMonthMarshaller implements DynamoDBMarshaller<CalendarMonth> {
	
	private static Logger logger = LoggerFactory.getLogger(CalendarMonthMarshaller.class);
	
	private static final String NULL = "null";
	
	
	@Override
	public String marshall(CalendarMonth obj) {
		return obj == null ? NULL : obj.toString();
	}
	
	@Override
	public CalendarMonth unmarshall(Class<CalendarMonth> clazz, String obj) {
		if (obj == null || obj.equals(NULL)) {
			return null;
		}
		try {
			return CalendarMonth.parse(obj);
		} catch (ParseException e) {
			logger.warn("", e);
		}
		return null;
	}
}
