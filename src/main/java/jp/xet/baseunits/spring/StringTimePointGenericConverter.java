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
import java.util.TimeZone;

import jp.xet.baseunits.time.TimePoint;

import com.google.common.base.Preconditions;

import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.GenericConverter;

/**
 * {@link GenericConverter} implementation from {@link String} to {@link TimePoint}.
 * 
 * @author daisuke
 * @since 2.10
 */
public class StringTimePointGenericConverter implements GenericConverter {
	
	private static final String DEFAULT_FORMAT = "EEE MMM dd HH:mm:ss zzz yyyy";
	
	private String format;
	
	private TimeZone timeZone;
	
	
	/**
	 * インスタンスを生成する。
	 */
	public StringTimePointGenericConverter() {
		this(DEFAULT_FORMAT, TimeZone.getDefault());
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param format パターン文字列
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 */
	public StringTimePointGenericConverter(String format) {
		this(format, TimeZone.getDefault());
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param format パターン文字列
	 * @param timeZone タイムゾーン
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 */
	public StringTimePointGenericConverter(String format, TimeZone timeZone) {
		Preconditions.checkNotNull(format);
		Preconditions.checkNotNull(timeZone);
		this.format = format;
		this.timeZone = timeZone;
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param timeZone タイムゾーン
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 */
	public StringTimePointGenericConverter(TimeZone timeZone) {
		this(DEFAULT_FORMAT, timeZone);
	}
	
	@Override
	public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
		try {
			return source == null ? null : TimePoint.parse((String) source, format, timeZone);
		} catch (ParseException e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	@Override
	public Set<ConvertiblePair> getConvertibleTypes() {
		Set<ConvertiblePair> convertibleTypes = new HashSet<ConvertiblePair>();
		convertibleTypes.add(new ConvertiblePair(String.class, TimePoint.class));
		return convertibleTypes;
	}
}
