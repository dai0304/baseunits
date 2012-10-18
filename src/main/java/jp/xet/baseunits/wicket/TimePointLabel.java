/*
 * Copyright 2011-2012 Daisuke Miyamoto. (http://d.hatena.ne.jp/daisuke-m)
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
import java.util.TimeZone;

import jp.xet.baseunits.time.TimePoint;

import com.google.common.base.Preconditions;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.convert.IConverter;

/**
 * {@link TimePoint}を表示するWicketのLabelコンポーネント実装クラス。
 * 
 * @author daisuke
 * @since 2.0
 */
@SuppressWarnings("serial")
public class TimePointLabel extends GenericLabel<TimePoint> {
	
	private static final String DEFAULT_PATTERN = "yyyy/MM/dd HH:mm:ss";
	
	private String datePattern;
	
	private final IModel<TimeZone> timeZoneModel;
	
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id The non-null id of this component
	 * @param model The component's model
	 * @param timeZoneModel time zone
	 * @throws WicketRuntimeException if the component has been given a null id.
	 */
	public TimePointLabel(String id, IModel<TimePoint> model, IModel<TimeZone> timeZoneModel) {
		this(id, model, DEFAULT_PATTERN, timeZoneModel);
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id The non-null id of this component
	 * @param model The component's model
	 * @param datePattern {@link SimpleDateFormat}に基づくパターン
	 * @param timeZoneModel タイムゾーン
	 * @throws WicketRuntimeException if the component has been given a null id.
	 */
	public TimePointLabel(String id, IModel<TimePoint> model, String datePattern, IModel<TimeZone> timeZoneModel) {
		super(id, model);
		Preconditions.checkNotNull(datePattern);
		Preconditions.checkNotNull(timeZoneModel);
		this.datePattern = datePattern;
		this.timeZoneModel = timeZoneModel;
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id The non-null id of this component
	 * @param model The component's model
	 * @param datePattern {@link SimpleDateFormat}に基づくパターン
	 * @param timeZone タイムゾーン
	 * @throws WicketRuntimeException if the component has been given a null id.
	 */
	public TimePointLabel(String id, IModel<TimePoint> model, String datePattern, TimeZone timeZone) {
		this(id, model, datePattern, Model.of(timeZone));
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id The non-null id of this component
	 * @param model The component's model
	 * @param timeZone time zone
	 * @throws WicketRuntimeException if the component has been given a null id.
	 */
	public TimePointLabel(String id, IModel<TimePoint> model, TimeZone timeZone) {
		this(id, model, DEFAULT_PATTERN, Model.of(timeZone));
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id The non-null id of this component
	 * @param timePoint 表示する日付
	 * @param timeZoneModel time zone
	 * @throws WicketRuntimeException if the component has been given a null id.
	 */
	public TimePointLabel(String id, TimePoint timePoint, IModel<TimeZone> timeZoneModel) {
		this(id, Model.of(timePoint), DEFAULT_PATTERN, timeZoneModel);
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id The non-null id of this component
	 * @param timePoint 表示する日付
	 * @param datePattern {@link SimpleDateFormat}に基づくパターン
	 * @param timeZoneModel time zone
	 * @throws WicketRuntimeException if the component has been given a null id.
	 */
	public TimePointLabel(String id, TimePoint timePoint, String datePattern, IModel<TimeZone> timeZoneModel) {
		this(id, Model.of(timePoint), datePattern, timeZoneModel);
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id The non-null id of this component
	 * @param timePoint 表示する日付
	 * @param datePattern {@link SimpleDateFormat}に基づくパターン
	 * @param timeZone time zone
	 * @throws WicketRuntimeException if the component has been given a null id.
	 */
	public TimePointLabel(String id, TimePoint timePoint, String datePattern, TimeZone timeZone) {
		this(id, Model.of(timePoint), datePattern, Model.of(timeZone));
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id The non-null id of this component
	 * @param timePoint 表示する日付
	 * @param timeZone time zone
	 * @throws WicketRuntimeException if the component has been given a null id.
	 */
	public TimePointLabel(String id, TimePoint timePoint, TimeZone timeZone) {
		this(id, Model.of(timePoint), DEFAULT_PATTERN, Model.of(timeZone));
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <C>IConverter<C> getConverter(Class<C> type) {
		if (type == TimePoint.class) {
			TimeZone timeZone = timeZoneModel.getObject();
			return (IConverter<C>) new TimePointConverter(datePattern, timeZone);
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
	
	@Override
	protected void onDetach() {
		if (timeZoneModel != null) {
			timeZoneModel.detach();
		}
		super.onDetach();
	}
	
	/**
	 * {@link SimpleDateFormat}に基づくパターンを設定する。
	 * 
	 * @param datePattern {@link SimpleDateFormat}に基づくパターン
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 */
	protected void setDatePattern(String datePattern) {
		Preconditions.checkNotNull(datePattern);
		this.datePattern = datePattern;
	}
}
