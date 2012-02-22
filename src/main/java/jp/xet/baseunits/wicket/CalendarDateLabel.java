/*
 * Copyright 2011 Daisuke Miyamoto. (http://d.hatena.ne.jp/daisuke-m)
 * Created on 2011/11/22
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
package jp.xet.baseunits.wicket;

import java.text.SimpleDateFormat;

import jp.xet.baseunits.time.CalendarDate;

import org.apache.commons.lang.Validate;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.convert.IConverter;

/**
 * {@link CalendarDate}を表示するWicketのLabelコンポーネント実装クラス。
 * 
 * @author daisuke
 * @since 2.0
 */
@SuppressWarnings("serial")
public class CalendarDateLabel extends GenericLabel<CalendarDate> {
	
	private static final String DEFAULT_PATTERN = "yyyy/MM/dd";
	
	private String datePattern;
	
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id The non-null id of this component
	 * @param calendarDate 表示する日付
	 */
	public CalendarDateLabel(String id, CalendarDate calendarDate) {
		this(id, Model.of(calendarDate), DEFAULT_PATTERN);
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id The non-null id of this component
	 * @param calendarDate 表示する日付
	 * @param datePattern {@link SimpleDateFormat}に基づくパターン
	 */
	public CalendarDateLabel(String id, CalendarDate calendarDate, String datePattern) {
		this(id, Model.of(calendarDate), datePattern);
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id The non-null id of this component
	 * @param model The component's model
	 */
	public CalendarDateLabel(String id, IModel<CalendarDate> model) {
		this(id, model, DEFAULT_PATTERN);
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id The non-null id of this component
	 * @param model The component's model
	 * @param datePattern {@link SimpleDateFormat}に基づくパターン
	 */
	public CalendarDateLabel(String id, IModel<CalendarDate> model, String datePattern) {
		super(id, model);
		Validate.notNull(datePattern);
		this.datePattern = datePattern;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <C>IConverter<C> getConverter(Class<C> type) {
		if (type == CalendarDate.class) {
			return (IConverter<C>) new CalendarDateConverter(datePattern);
		}
		return super.getConverter(type);
	}
	
	/**
	 * {@link SimpleDateFormat}に基づくパターン文字列を返す。
	 * 
	 * @return {@link SimpleDateFormat}に基づくパターン
	 */
	public String getDatePattern() {
		return datePattern;
	}
	
	/**
	 * {@link SimpleDateFormat}に基づくパターンを設定する。
	 * 
	 * @param datePattern {@link SimpleDateFormat}に基づくパターン
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 */
	protected void setDatePattern(String datePattern) {
		Validate.notNull(datePattern);
		this.datePattern = datePattern;
	}
}
