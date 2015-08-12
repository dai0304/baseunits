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

import java.util.TimeZone;

import jp.xet.baseunits.time.CalendarDate;
import jp.xet.baseunits.timeutil.Clock;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.lang.Args;

/**
 * {@link TimeZone}のモデルより、「昨日」を表現するモデル。
 */
@SuppressWarnings("serial")
public class YesterdayModel extends LoadableDetachableModel<CalendarDate> {
	
	private final IModel<TimeZone> timeZoneModel;
	
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param timeZoneModel タイムゾーン
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 */
	public YesterdayModel(IModel<TimeZone> timeZoneModel) {
		Args.notNull(timeZoneModel, "timeZoneModel");
		this.timeZoneModel = timeZoneModel;
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param timeZone タイムゾーン
	 */
	public YesterdayModel(TimeZone timeZone) {
		this(Model.of(timeZone));
	}
	
	@Override
	public void detach() {
		timeZoneModel.detach();
		super.detach();
	}
	
	@Override
	protected CalendarDate load() {
		TimeZone timeZone = timeZoneModel.getObject();
		if (timeZone == null) {
			timeZone = TimeZone.getTimeZone("Universal");
		}
		return Clock.today(timeZone).previousDay();
	}
}
