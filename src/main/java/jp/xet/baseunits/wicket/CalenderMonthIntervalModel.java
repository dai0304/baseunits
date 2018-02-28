/*
 * Copyright 2010-2018 Miyamoto Daisuke.
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

import jp.xet.baseunits.time.CalendarInterval;
import jp.xet.baseunits.time.CalendarMonth;

import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;

/**
 * {@link CalendarMonth}のモデルを {@link CalendarInterval}のモデルに変換するアダプタモデル。
 * 
 * @author daisuke
 * @since 2.6
 */
@SuppressWarnings("serial")
public class CalenderMonthIntervalModel extends AbstractReadOnlyModel<CalendarInterval> {
	
	private final IModel<CalendarMonth> monthModel;
	
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param monthModel {@link CalendarMonth}のモデル
	 */
	public CalenderMonthIntervalModel(IModel<CalendarMonth> monthModel) {
		this.monthModel = monthModel;
	}
	
	@Override
	public void detach() {
		if (monthModel != null) {
			monthModel.detach();
		}
		super.detach();
	}
	
	@Override
	public CalendarInterval getObject() {
		if (monthModel == null) {
			return null;
		}
		CalendarMonth month = monthModel.getObject();
		if (month == null) {
			return null;
		}
		return month.asCalendarInterval();
	}
}
