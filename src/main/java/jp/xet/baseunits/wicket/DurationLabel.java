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

import jp.xet.baseunits.time.Duration;
import jp.xet.baseunits.time.TimeUnit;
import jp.xet.baseunits.time.formatter.DetailedDurationFormatter;
import jp.xet.baseunits.time.formatter.DurationFormatter;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.convert.IConverter;

/**
 * {@link Duration}を表示するWicketのLabelコンポーネント実装クラス。
 * 
 * @author daisuke
 * @since 2.0
 */
@SuppressWarnings("serial")
public class DurationLabel extends GenericLabel<Duration> {
	
	private static final DurationFormatter DEFAULT_FORMATTER = new DetailedDurationFormatter(false, TimeUnit.hour,
			TimeUnit.minute);
	
	private final IConverter<Duration> converter;
	
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id The non-null id of this component
	 * @throws WicketRuntimeException if the component has been given a null id.
	 * @since 2.3
	 */
	public DurationLabel(String id) {
		this(id, DEFAULT_FORMATTER);
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id The non-null id of this component
	 * @param duration 表示する時間量
	 * @throws WicketRuntimeException if the component has been given a null id.
	 * @since 2.0
	 */
	public DurationLabel(String id, Duration duration) {
		this(id, Model.of(duration), DEFAULT_FORMATTER);
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id The non-null id of this component
	 * @param durationFormatter {@link DurationFormatter}
	 * @throws WicketRuntimeException if the component has been given a null id.
	 * @since 2.4
	 */
	public DurationLabel(String id, DurationFormatter durationFormatter) {
		super(id);
		converter = new DurationConverter(durationFormatter);
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id The non-null id of this component
	 * @param model The component's model
	 * @throws WicketRuntimeException if the component has been given a null id.
	 * @since 2.0
	 */
	public DurationLabel(String id, IModel<Duration> model) {
		this(id, model, DEFAULT_FORMATTER);
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id The non-null id of this component
	 * @param model The component's model
	 * @param durationFormatter {@link DurationFormatter}
	 * @throws WicketRuntimeException if the component has been given a null id.
	 * @since 2.4
	 */
	public DurationLabel(String id, IModel<Duration> model, DurationFormatter durationFormatter) {
		super(id, model);
		converter = new DurationConverter(durationFormatter);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <C>IConverter<C> getConverter(Class<C> type) {
		if (type == Duration.class) {
			return (IConverter<C>) converter;
		}
		return super.getConverter(type);
	}
}
