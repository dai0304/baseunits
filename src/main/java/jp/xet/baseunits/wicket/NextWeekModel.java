/*
 * Copyright 2011 Daisuke Miyamoto. (http://d.hatena.ne.jp/daisuke-m)
 * Created on 2012/02/27
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

import java.util.TimeZone;

import jp.xet.baseunits.time.CalendarWeek;
import jp.xet.baseunits.timeutil.Clock;

import org.apache.commons.lang.Validate;
import org.apache.wicket.model.AbstractReadOnlyModel;

/**
 * TODO for daisuke
 */
@SuppressWarnings("serial")
public class NextWeekModel extends AbstractReadOnlyModel<CalendarWeek> {
	
	private final TimeZone timeZone;
	
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param timeZone タイムゾーン
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 */
	public NextWeekModel(TimeZone timeZone) {
		Validate.notNull(timeZone);
		this.timeZone = timeZone;
	}
	
	@Override
	public CalendarWeek getObject() {
		return Clock.today(timeZone).asCalendarWeek().nextWeek();
	}
}
