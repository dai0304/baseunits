/*
 * Copyright 2011 Daisuke Miyamoto. (http://d.hatena.ne.jp/daisuke-m)
 * Created on 2012/03/06
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

import jp.xet.baseunits.time.CalendarDate;
import jp.xet.baseunits.time.TimePoint;

import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.lang.Args;

/**
 * {@link TimePoint}及びタイムゾーンによって、暦日を表現するモデル実装クラス。
 * 
 * @since 2.0
 */
@SuppressWarnings("serial")
public class CalendarDateModel extends AbstractReadOnlyModel<CalendarDate> {
	
	private final IModel<TimePoint> timePointModel;
	
	private final IModel<TimeZone> timeZoneModel;
	
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param timePointModel {@link TimePoint}
	 * @param timeZoneModel タイムゾーンモデル
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @since 2.0
	 */
	public CalendarDateModel(IModel<TimePoint> timePointModel, IModel<TimeZone> timeZoneModel) {
		Args.notNull(timePointModel, "timePointModel");
		Args.notNull(timeZoneModel, "timeZoneModel");
		this.timePointModel = timePointModel;
		this.timeZoneModel = timeZoneModel;
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param timePointModel {@link TimePoint}
	 * @param timeZone タイムゾーン
	 * @throws IllegalArgumentException 引数{@code timePointModel}に{@code null}を与えた場合
	 * @since 2.0
	 */
	public CalendarDateModel(IModel<TimePoint> timePointModel, TimeZone timeZone) {
		this(timePointModel, Model.of(timeZone));
		Args.notNull(timePointModel, "timePointModel");
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param timePoint {@link TimePoint}
	 * @param timeZoneModel タイムゾーンモデル
	 * @throws IllegalArgumentException 引数{@code timeZoneModel}に{@code null}を与えた場合
	 * @since 2.0
	 */
	public CalendarDateModel(TimePoint timePoint, IModel<TimeZone> timeZoneModel) {
		this(Model.of(timePoint), timeZoneModel);
		Args.notNull(timeZoneModel, "timeZoneModel");
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param timePoint {@link TimePoint}
	 * @param timeZone タイムゾーン
	 * @since 2.0
	 */
	public CalendarDateModel(TimePoint timePoint, TimeZone timeZone) {
		this(Model.of(timePoint), Model.of(timeZone));
	}
	
	@Override
	public void detach() {
		super.detach();
		if (timePointModel != null) {
			timePointModel.detach();
		}
		if (timeZoneModel != null) {
			timeZoneModel.detach();
		}
	}
	
	@Override
	public CalendarDate getObject() {
		TimePoint timePoint = timePointModel.getObject();
		if (timePoint == null) {
			return null;
		}
		TimeZone timeZone = timeZoneModel.getObject();
		if (timeZone == null) {
			timeZone = TimeZone.getTimeZone("Universal");
		}
		return timePoint.asCalendarDate(timeZone);
	}
}
