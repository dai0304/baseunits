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

import jp.xet.baseunits.time.Duration;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.convert.IConverter;

/**
 * TODO for daisuke
 * 
 * @author daisuke
 * @since 2.6
 */
@SuppressWarnings("serial")
public class HourDurationTextField extends TextField<Duration> {
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id The non-null id of this component
	 * @throws WicketRuntimeException if the component has been given a null id.
	 */
	public HourDurationTextField(String id) {
		super(id, Duration.class);
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id The non-null id of this component
	 * @param model The component's model
	 * @throws WicketRuntimeException if the component has been given a null id.
	 */
	public HourDurationTextField(String id, IModel<Duration> model) {
		super(id, model, Duration.class);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <C>IConverter<C> getConverter(final Class<C> type) {
		if (Duration.class.isAssignableFrom(type)) {
			return (IConverter<C>) new HourDurationValueConverter();
		}
		return super.getConverter(type);
	}
}
