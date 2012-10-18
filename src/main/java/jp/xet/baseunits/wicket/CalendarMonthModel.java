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

import jp.xet.baseunits.time.CalendarMonth;
import jp.xet.baseunits.time.TimePoint;

import com.google.common.base.Preconditions;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

/**
 * {@link TimePoint}及び{@link TimeZone}のモデルより、{@link CalendarMonth}を表現するモデル。
 * 
 * @since 2.1
 */
@SuppressWarnings("serial")
public class CalendarMonthModel extends AbstractReadOnlyModel<CalendarMonth> {
	
	private final IModel<TimePoint> timePointModel;
	
	private final IModel<TimeZone> timeZoneModel;
	
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param timePointModel 基準時刻モデル
	 * @param timeZoneModel タイムゾーンモデル
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @since 2.1
	 */
	public CalendarMonthModel(IModel<TimePoint> timePointModel, IModel<TimeZone> timeZoneModel) {
		Preconditions.checkNotNull(timePointModel);
		Preconditions.checkNotNull(timeZoneModel);
		this.timePointModel = timePointModel;
		this.timeZoneModel = timeZoneModel;
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param timePointModel 基準時刻モデル
	 * @param timeZone タイムゾーン
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @since 2.1
	 */
	public CalendarMonthModel(IModel<TimePoint> timePointModel, TimeZone timeZone) {
		this(timePointModel, Model.of(timeZone));
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param timePoint 基準時刻
	 * @param timeZoneModel タイムゾーンモデル
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @since 2.1
	 */
	public CalendarMonthModel(TimePoint timePoint, IModel<TimeZone> timeZoneModel) {
		this(Model.of(timePoint), timeZoneModel);
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param timePoint 基準時刻
	 * @param timeZone タイムゾーン
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @since 2.1
	 */
	public CalendarMonthModel(TimePoint timePoint, TimeZone timeZone) {
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
	public CalendarMonth getObject() {
		TimePoint timePoint = timePointModel.getObject();
		if (timePoint == null) {
			return null;
		}
		TimeZone timeZone = timeZoneModel.getObject();
		if (timeZone == null) {
			timeZone = TimeZone.getTimeZone("Universal");
		}
		return timePoint.asCalendarDate(timeZone).asCalendarMonth();
	}
}
