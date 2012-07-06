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

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;

/**
 * A {@link Label} with typesafe getters and setters for the model and its underlying object
 * 
 * @param <T> the type of the label's model object
 * @author daisuke
 * @since 2.0
 */
@SuppressWarnings("serial")
public class GenericLabel<T> extends Label {
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id The non-null id of this component
	 * @throws WicketRuntimeException if the component has been given a null id.
	 */
	public GenericLabel(String id) {
		super(id);
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id The non-null id of this component
	 * @param model The component's model
	 * @throws WicketRuntimeException if the component has been given a null id.
	 */
	public GenericLabel(String id, IModel<T> model) {
		super(id, model);
	}
	
	/**
	 * Typesafe getter for the page's model
	 * 
	 * @return the model
	 */
	@SuppressWarnings("unchecked")
	public final IModel<T> getModel() {
		return (IModel<T>) getDefaultModel();
	}
	
	/**
	 * Typesafe getter for the model's object
	 * 
	 * @return the model object
	 */
	@SuppressWarnings("unchecked")
	public final T getModelObject() {
		return (T) getDefaultModelObject();
	}
	
	/**
	 * Typesafe setter for the model
	 * 
	 * @param model
	 *            the new model
	 */
	public final void setModel(final IModel<T> model) {
		setDefaultModel(model);
	}
	
	/**
	 * Typesafe setter for the model object
	 * 
	 * @param modelObject
	 *            the new model object
	 */
	public final void setModelObject(final T modelObject) {
		setDefaultModelObject(modelObject);
	}
}
