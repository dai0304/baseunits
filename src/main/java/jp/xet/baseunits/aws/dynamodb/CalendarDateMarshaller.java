/*
 * Copyright 2011-2012 Daisuke Miyamoto. (http://d.hatena.ne.jp/daisuke-m)
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
package jp.xet.baseunits.aws.dynamodb;

import java.text.ParseException;

import jp.xet.baseunits.time.CalendarDate;

import com.amazonaws.services.dynamodb.datamodeling.DynamoDBMarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * {@link DynamoDBMarshaller} implementation for {@link CalendarDate}.
 * 
 * @since 2.10
 * @author daisuke
 */
public class CalendarDateMarshaller implements DynamoDBMarshaller<CalendarDate> {
	
	private static Logger logger = LoggerFactory.getLogger(CalendarDateMarshaller.class);
	
	private static final String NULL = "null";
	
	
	@Override
	public String marshall(CalendarDate obj) {
		return obj == null ? NULL : obj.toString();
	}
	
	@Override
	public CalendarDate unmarshall(Class<CalendarDate> clazz, String obj) {
		if (obj == null || obj.equals(NULL)) {
			return null;
		}
		try {
			return CalendarDate.parse(obj);
		} catch (ParseException e) {
			logger.warn("", e);
		}
		return null;
	}
}
