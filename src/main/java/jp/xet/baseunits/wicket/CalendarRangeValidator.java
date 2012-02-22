/*
 * Copyright 2011 Daisuke Miyamoto. (http://d.hatena.ne.jp/daisuke-m)
 * Created on 2012/02/10
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

import jp.xet.baseunits.time.CalendarDate;

import org.apache.commons.lang.Validate;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.validation.AbstractFormValidator;

/**
 * 日付期間の開始と終了を表す2つの {@link FormComponent} について、開始日が終了日より未来でないことを検証するバリデータ。
 * 
 * @author daisuke
 * @since 2.0
 */
@SuppressWarnings("serial")
public class CalendarRangeValidator extends AbstractFormValidator {
	
	private final FormComponent<CalendarDate> from;
	
	private final FormComponent<CalendarDate> to;
	
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param from 自
	 * @param to 至
	 */
	public CalendarRangeValidator(FormComponent<CalendarDate> from, FormComponent<CalendarDate> to) {
		Validate.notNull(from);
		Validate.notNull(to);
		this.from = from;
		this.to = to;
	}
	
	@Override
	public FormComponent<?>[] getDependentFormComponents() {
		return new FormComponent[] {
			from,
			to
		};
	}
	
	@Override
	public void validate(Form<?> form) {
		CalendarDate fromDate = from.getConvertedInput();
		CalendarDate toDate = to.getConvertedInput();
		if (fromDate.isAfter(toDate)) {
			error(from);
		}
	}
}
