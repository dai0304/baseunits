/*
 * Copyright 2013 Classmethod, Inc.
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
package jp.xet.baseunits.spring;

import java.text.ParseException;
import java.util.HashSet;
import java.util.Set;

import jp.xet.baseunits.time.CalendarDate;
import jp.xet.baseunits.time.TimePoint;

import com.google.common.base.Preconditions;

import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.ConditionalGenericConverter;
import org.springframework.core.convert.converter.GenericConverter;

/**
 * {@link GenericConverter} implementation from {@link String} to {@link TimePoint}.
 * 
 * @author daisuke
 * @since 2.10
 */
public class StringCalendarDateGenericConverter implements ConditionalGenericConverter {
	
	private static final String DEFAULT_FORMAT = "yyyy-MM-dd";
	
	private String format;
	
	
	/**
	 * インスタンスを生成する。
	 */
	public StringCalendarDateGenericConverter() {
		this(DEFAULT_FORMAT);
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param format
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 */
	public StringCalendarDateGenericConverter(String format) {
		Preconditions.checkNotNull(format);
		this.format = format;
	}
	
	@Override
	public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
		String format = this.format;
		try {
			return source == null ? null : CalendarDate.parse((String) source, format);
		} catch (ParseException e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	@Override
	public Set<ConvertiblePair> getConvertibleTypes() {
		Set<ConvertiblePair> convertibleTypes = new HashSet<ConvertiblePair>();
		convertibleTypes.add(new ConvertiblePair(String.class, CalendarDate.class));
		return convertibleTypes;
	}
	
	@Override
	public boolean matches(TypeDescriptor sourceType, TypeDescriptor targetType) {
		// TODO Auto-generated method stub
		return false;
	}
}
