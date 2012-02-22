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

import jp.xet.baseunits.time.TimeOfDay;

import org.apache.commons.lang.Validate;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.convert.IConverter;

/**
 * {@link TimeOfDay}を表示するWicketのLabelコンポーネント実装クラス。
 * 
 * @since 2.0
 * @version $Id$
 * @author daisuke
 */
@SuppressWarnings("serial")
public class TimeOfDayLabel extends GenericLabel<TimeOfDay> {
	
	private static final String DEFAULT_PATTERN = "HH:mm";
	
	private final String timePattern;
	
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id The non-null id of this component
	 * @param model The component's model
	 */
	public TimeOfDayLabel(String id, IModel<TimeOfDay> model) {
		this(id, model, DEFAULT_PATTERN);
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id The non-null id of this component
	 * @param model The component's model
	 * @param timePattern {@link SimpleDateFormat}に基づくパターン
	 */
	public TimeOfDayLabel(String id, IModel<TimeOfDay> model, String timePattern) {
		super(id, model);
		Validate.notNull(timePattern);
		this.timePattern = timePattern;
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id The non-null id of this component
	 * @param timeOfDay 表示する時刻
	 */
	public TimeOfDayLabel(String id, TimeOfDay timeOfDay) {
		this(id, Model.of(timeOfDay), DEFAULT_PATTERN);
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id The non-null id of this component
	 * @param timeOfDay 表示する時刻
	 * @param timePattern {@link SimpleDateFormat}に基づくパターン
	 */
	public TimeOfDayLabel(String id, TimeOfDay timeOfDay, String timePattern) {
		this(id, Model.of(timeOfDay), timePattern);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <C>IConverter<C> getConverter(Class<C> type) {
		if (type == TimeOfDay.class) {
			return (IConverter<C>) new TimeOfDayConverter(timePattern);
		}
		return super.getConverter(type);
	}
}
