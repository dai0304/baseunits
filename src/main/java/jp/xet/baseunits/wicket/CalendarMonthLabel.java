/*
 * Copyright 2010-2015 Miyamoto Daisuke.
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
package jp.xet.baseunits.wicket;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

import jp.xet.baseunits.time.CalendarMonth;
import jp.xet.baseunits.time.TimePoint;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.convert.IConverter;
import org.apache.wicket.util.lang.Args;

/**
 * {@link CalendarMonth}を表示するWicketのLabelコンポーネント実装クラス。
 * 
 * @author daisuke
 * @since 2.1
 */
@SuppressWarnings("serial")
public class CalendarMonthLabel extends GenericLabel<CalendarMonth> {
	
	private static final String DEFAULT_PATTERN = "yyyy/MM";
	
	private String datePattern; // TODO model
	
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id The non-null id of this component
	 * @param calendarMonth 表示する暦月
	 * @throws WicketRuntimeException if the component has been given a null id.
	 * @since 2.1
	 */
	public CalendarMonthLabel(String id, CalendarMonth calendarMonth) {
		this(id, Model.of(calendarMonth), DEFAULT_PATTERN);
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id The non-null id of this component
	 * @param calendarMonth 表示する暦月
	 * @param datePattern {@link SimpleDateFormat}に基づくパターン
	 * @throws WicketRuntimeException if the component has been given a null id.
	 * @since 2.1
	 */
	public CalendarMonthLabel(String id, CalendarMonth calendarMonth, String datePattern) {
		this(id, Model.of(calendarMonth), datePattern);
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id The non-null id of this component
	 * @param model 表示する暦月
	 * @throws WicketRuntimeException if the component has been given a null id.
	 * @since 2.1
	 */
	public CalendarMonthLabel(String id, IModel<CalendarMonth> model) {
		this(id, model, DEFAULT_PATTERN);
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id The non-null id of this component
	 * @param model 表示する歴月
	 * @param datePattern {@link SimpleDateFormat}に基づくパターン
	 * @throws WicketRuntimeException if the component has been given a null id.
	 * @throws IllegalArgumentException 引数{@code datePattern}に{@code null}を与えた場合
	 * @since 2.1
	 */
	public CalendarMonthLabel(String id, IModel<CalendarMonth> model, String datePattern) {
		super(id, model);
		Args.notNull(datePattern, "datePattern");
		this.datePattern = datePattern;
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id The non-null id of this component
	 * @param timePointModel 表示する歴月が属する{@link TimePoint}
	 * @param timeZoneModel タイムゾーンモデル
	 * @throws WicketRuntimeException if the component has been given a null id.
	 * @since 2.1
	 */
	public CalendarMonthLabel(String id, IModel<TimePoint> timePointModel, IModel<TimeZone> timeZoneModel) {
		this(id, new CalendarMonthModel(timePointModel, timeZoneModel));
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id The non-null id of this component
	 * @param timePoint 表示する歴月が属する{@link TimePoint}
	 * @param timeZone タイムゾーン
	 * @throws WicketRuntimeException if the component has been given a null id.
	 * @since 2.1
	 */
	public CalendarMonthLabel(String id, TimePoint timePoint, TimeZone timeZone) {
		this(id, new CalendarMonthModel(timePoint, timeZone));
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <C>IConverter<C> getConverter(Class<C> type) {
		if (type == CalendarMonth.class) {
			return (IConverter<C>) new CalendarMonthConverter(datePattern);
		}
		return super.getConverter(type);
	}
	
	/**
	 * {@link SimpleDateFormat}に基づくパターン文字列を返す。
	 * 
	 * @return {@link SimpleDateFormat}に基づくパターン
	 * @since 2.1
	 */
	public String getDatePattern() {
		return datePattern;
	}
	
	/**
	 * {@link SimpleDateFormat}に基づくパターンを設定する。
	 * 
	 * @param datePattern {@link SimpleDateFormat}に基づくパターン
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @since 2.1
	 */
	protected void setDatePattern(String datePattern) {
		Args.notNull(datePattern, "datePattern");
		this.datePattern = datePattern;
	}
}
