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
package jp.xet.baseunits.wicket;

import java.io.Serializable;
import java.util.TimeZone;

import jp.xet.baseunits.time.TimePoint;
import jp.xet.baseunits.time.formatter.Icu4jRelativeTimePointFormatter;
import jp.xet.baseunits.time.formatter.RelativeTimePointFormatter;
import jp.xet.baseunits.time.formatter.RelativeTimePointFormatter.FallbackConfig;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.convert.IConverter;
import org.apache.wicket.util.lang.Args;

/**
 * 「5分前」「10時間後」など、現在時刻からの相対時間を表示するラベルコンポーネント実装クラス。
 * 
 * @since 2.2
 * @version $Id$
 * @author daisuke
 */
@SuppressWarnings("serial")
public class RelativeTimeLabel extends GenericLabel<TimePoint> {
	
	private final RelativeTimePointFormatter formatter;
	
	private final IModel<FallbackConfig> configModel;
	
	private final IModel<TimeZone> timeZoneModel;
	
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id The non-null id of this component
	 * @param config {@link FallbackConfig}
	 * @param timeZone {@link TimeZone}
	 * @throws WicketRuntimeException if the component has been given a null id.
	 * @since 2.2
	 * @deprecated on 2.9 use {@link #RelativeTimeLabel(String, IModel, IModel)}
	 */
	@Deprecated
	public RelativeTimeLabel(String id, FallbackConfig config, TimeZone timeZone) {
		this(id, Model.of(config), Model.of(timeZone));
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id The non-null id of this component
	 * @param configModel {@link FallbackConfig}
	 * @param timeZoneModel {@link TimeZone}
	 * @throws WicketRuntimeException if the component has been given a null id.
	 * @since 2.9
	 */
	public RelativeTimeLabel(String id, IModel<FallbackConfig> configModel, IModel<TimeZone> timeZoneModel) {
		super(id);
		formatter = null;
		this.configModel = configModel;
		this.timeZoneModel = timeZoneModel;
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id The non-null id of this component
	 * @param model The component's model
	 * @param config {@link FallbackConfig}
	 * @param timeZone {@link TimeZone}
	 * @throws WicketRuntimeException if the component has been given a null id.
	 * @since 2.2
	 * @deprecated on 2.9 use {@link #RelativeTimeLabel(String, IModel, IModel, IModel)}
	 */
	@Deprecated
	public RelativeTimeLabel(String id, IModel<TimePoint> model, FallbackConfig config, TimeZone timeZone) {
		this(id, model, Model.of(config), Model.of(timeZone));
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id The non-null id of this component
	 * @param model The component's model
	 * @param configModel {@link FallbackConfig}
	 * @param timeZoneModel {@link TimeZone}
	 * @throws WicketRuntimeException if the component has been given a null id.
	 * @since 2.9
	 */
	public RelativeTimeLabel(String id, IModel<TimePoint> model, IModel<FallbackConfig> configModel,
			IModel<TimeZone> timeZoneModel) {
		super(id, model);
		formatter = null;
		this.configModel = configModel;
		this.timeZoneModel = timeZoneModel;
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id The non-null id of this component
	 * @param model The component's model
	 * @param formatter {@link RelativeTimePointFormatter}
	 * @throws WicketRuntimeException if the component has been given a null id.
	 * @since 2.2
	 */
	public RelativeTimeLabel(String id, IModel<TimePoint> model, RelativeTimePointFormatter formatter) {
		super(id, model);
		Args.isTrue(formatter == null || formatter instanceof Serializable, "formatter must be Serializable");
		this.formatter = formatter;
		configModel = null;
		timeZoneModel = null;
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id The non-null id of this component
	 * @param formatter {@link RelativeTimePointFormatter}
	 * @throws WicketRuntimeException if the component has been given a null id.
	 * @since 2.2
	 */
	public RelativeTimeLabel(String id, RelativeTimePointFormatter formatter) {
		super(id);
		Args.isTrue(formatter == null || formatter instanceof Serializable, "formatter must be Serializable");
		this.formatter = formatter;
		configModel = null;
		timeZoneModel = null;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <C>IConverter<C> getConverter(Class<C> type) {
		if (type == TimePoint.class) {
			return (IConverter<C>) new RelativeTimePointConverter(formatter != null ?
					formatter : new Icu4jRelativeTimePointFormatter(configModel.getObject(), timeZoneModel.getObject()));
		}
		return super.getConverter(type);
	}
}
