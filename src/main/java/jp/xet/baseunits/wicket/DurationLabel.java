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

import jp.xet.baseunits.time.Duration;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.convert.IConverter;

/**
 * TODO for daisuke
 * 
 * @since 1.0.0
 * @version $Id$
 * @author daisuke
 */
@SuppressWarnings("serial")
public class DurationLabel extends GenericLabel<Duration> {
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id The non-null id of this component
	 * @param duration 表示する時間量
	 */
	public DurationLabel(String id, Duration duration) {
		this(id, Model.of(duration));
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id The non-null id of this component
	 * @param model The component's model
	 */
	public DurationLabel(String id, IModel<Duration> model) {
		super(id, model);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <C>IConverter<C> getConverter(Class<C> type) {
		if (type == Duration.class) {
			return (IConverter<C>) new HourAndMinuteDurationConverter();
		}
		return super.getConverter(type);
	}
}
