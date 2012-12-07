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

import jp.xet.baseunits.time.TimePointOfDay;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.convert.IConverter;
import org.apache.wicket.util.lang.Args;

/**
 * {@link TimePointOfDay}を表示するWicketのLabelコンポーネント実装クラス。
 * 
 * @author daisuke
 * @since 2.0
 */
@SuppressWarnings("serial")
public class TimePointOfDayLabel extends GenericLabel<TimePointOfDay> {
	
	private static final String DEFAULT_PATTERN = "HH:mm";
	
	private final String timePattern;
	
	private IModel<TimeZone> timeZoneModel;
	
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id The non-null id of this component
	 * @param model The component's model
	 * @param timeZoneModel {@link TimeZone} to interpret calendar of {@link TimePointOfDay}
	 * @throws WicketRuntimeException if the component has been given a null id.
	 * @throws IllegalArgumentException 引数{@code timeZoneModel}に{@code null}を与えた場合
	 */
	public TimePointOfDayLabel(String id, IModel<TimePointOfDay> model, IModel<TimeZone> timeZoneModel) {
		this(id, model, DEFAULT_PATTERN, timeZoneModel);
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id The non-null id of this component
	 * @param model The component's model
	 * @param timePattern {@link SimpleDateFormat}に基づくパターン
	 * @param timeZoneModel {@link TimeZone} to interpret calendar of {@link TimePointOfDay}
	 * @throws WicketRuntimeException if the component has been given a null id.
	 * @throws IllegalArgumentException 引数{@code timePattern}または{@code timeZoneModel}に{@code null}を与えた場合
	 */
	public TimePointOfDayLabel(String id, IModel<TimePointOfDay> model, String timePattern,
			IModel<TimeZone> timeZoneModel) {
		super(id, model);
		Args.notNull(timePattern, "timePattern");
		Args.notNull(timeZoneModel, "timeZoneModel");
		this.timePattern = timePattern;
		this.timeZoneModel = timeZoneModel;
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id The non-null id of this component
	 * @param model The component's model
	 * @param timePattern {@link SimpleDateFormat}に基づくパターン
	 * @param timeZone {@link TimeZone} to interpret calendar of {@link TimePointOfDay}
	 * @throws WicketRuntimeException if the component has been given a null id.
	 * @throws IllegalArgumentException 引数{@code timePattern}または{@code timeZone}に{@code null}を与えた場合
	 */
	public TimePointOfDayLabel(String id, IModel<TimePointOfDay> model, String timePattern, TimeZone timeZone) {
		this(id, model, timePattern, Model.of(timeZone));
		Args.notNull(timeZone, "timeZone");
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id The non-null id of this component
	 * @param model The component's model
	 * @param timeZone {@link TimeZone} to interpret calendar of {@link TimePointOfDay}
	 * @throws WicketRuntimeException if the component has been given a null id.
	 * @throws IllegalArgumentException 引数{@code timeZone}に{@code null}を与えた場合
	 */
	public TimePointOfDayLabel(String id, IModel<TimePointOfDay> model, TimeZone timeZone) {
		this(id, model, DEFAULT_PATTERN, Model.of(timeZone));
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id The non-null id of this component
	 * @param timeZoneModel {@link TimeZone} to interpret calendar of {@link TimePointOfDay}
	 * @throws WicketRuntimeException if the component has been given a null id.
	 * @throws IllegalArgumentException 引数{@code timeZoneModel}に{@code null}を与えた場合
	 */
	public TimePointOfDayLabel(String id, IModel<TimeZone> timeZoneModel) {
		this(id, DEFAULT_PATTERN, timeZoneModel);
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id The non-null id of this component
	 * @param timePattern {@link SimpleDateFormat}に基づくパターン
	 * @param timeZoneModel {@link TimeZone} to interpret calendar of {@link TimePointOfDay}
	 * @throws WicketRuntimeException if the component has been given a null id.
	 * @throws IllegalArgumentException 引数{@code timePattern}または{@code timeZoneModel}に{@code null}を与えた場合
	 */
	public TimePointOfDayLabel(String id, String timePattern, IModel<TimeZone> timeZoneModel) {
		super(id);
		Args.notNull(timePattern, "timePattern");
		Args.notNull(timeZoneModel, "timeZoneModel");
		this.timePattern = timePattern;
		this.timeZoneModel = timeZoneModel;
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id The non-null id of this component
	 * @param timePattern {@link SimpleDateFormat}に基づくパターン
	 * @param timeZone {@link TimeZone} to interpret calendar of {@link TimePointOfDay}
	 * @throws WicketRuntimeException if the component has been given a null id.
	 * @throws IllegalArgumentException 引数{@code timePattern}または{@code timeZone}に{@code null}を与えた場合
	 */
	public TimePointOfDayLabel(String id, String timePattern, TimeZone timeZone) {
		this(id, timePattern, Model.of(timeZone));
		Args.notNull(timeZone, "timeZone");
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id The non-null id of this component
	 * @param timePointOfDay 表示する時刻
	 * @param timeZoneModel {@link TimeZone} to interpret calendar of {@link TimePointOfDay}
	 * @throws WicketRuntimeException if the component has been given a null id.
	 */
	public TimePointOfDayLabel(String id, TimePointOfDay timePointOfDay, IModel<TimeZone> timeZoneModel) {
		this(id, Model.of(timePointOfDay), DEFAULT_PATTERN, timeZoneModel);
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id The non-null id of this component
	 * @param timeOfDay 表示する時刻
	 * @param timePattern {@link SimpleDateFormat}に基づくパターン
	 * @param timeZoneModel {@link TimeZone} to interpret calendar of {@link TimePointOfDay}
	 * @throws WicketRuntimeException if the component has been given a null id.
	 */
	public TimePointOfDayLabel(String id, TimePointOfDay timeOfDay, String timePattern, IModel<TimeZone> timeZoneModel) {
		this(id, Model.of(timeOfDay), timePattern, timeZoneModel);
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id The non-null id of this component
	 * @param timeOfDay 表示する時刻
	 * @param timePattern {@link SimpleDateFormat}に基づくパターン
	 * @param timeZone {@link TimeZone} to interpret calendar of {@link TimePointOfDay}
	 * @throws WicketRuntimeException if the component has been given a null id.
	 */
	public TimePointOfDayLabel(String id, TimePointOfDay timeOfDay, String timePattern, TimeZone timeZone) {
		this(id, Model.of(timeOfDay), timePattern, Model.of(timeZone));
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id The non-null id of this component
	 * @param timePointOfDay 表示する時刻
	 * @param timeZone {@link TimeZone} to interpret calendar of {@link TimePointOfDay}
	 * @throws WicketRuntimeException if the component has been given a null id.
	 */
	public TimePointOfDayLabel(String id, TimePointOfDay timePointOfDay, TimeZone timeZone) {
		this(id, Model.of(timePointOfDay), DEFAULT_PATTERN, Model.of(timeZone));
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id The non-null id of this component
	 * @param timeZone {@link TimeZone} to interpret calendar of {@link TimePointOfDay}
	 * @throws WicketRuntimeException if the component has been given a null id.
	 */
	public TimePointOfDayLabel(String id, TimeZone timeZone) {
		this(id, DEFAULT_PATTERN, Model.of(timeZone));
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <C>IConverter<C> getConverter(Class<C> type) {
		if (type == TimePointOfDay.class) {
			TimeZone timeZone = timeZoneModel.getObject();
			if (timeZone == null) {
				timeZone = TimeZone.getTimeZone("Universal");
			}
			return (IConverter<C>) new TimePointOfDayConverter(timePattern, timeZone);
		}
		return super.getConverter(type);
	}
	
	@Override
	protected void onDetach() {
		if (timeZoneModel != null) {
			timeZoneModel.detach();
		}
		super.onDetach();
	}
}
