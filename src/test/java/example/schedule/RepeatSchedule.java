/*
 * Copyright 2011 Daisuke Miyamoto. (http://d.hatena.ne.jp/daisuke-m)
 * Created on 2011/10/25
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
package example.schedule;

import java.util.TimeZone;

import jp.xet.baseunits.time.TimeInterval;
import jp.xet.baseunits.time.TimePoint;
import jp.xet.baseunits.time.spec.DateSpecification;

import org.apache.commons.lang.Validate;

/**
 * TODO for daisuke
 */
public class RepeatSchedule {
	
	private DateSpecification dateSpec;
	
	private TimeInterval interval;
	
	private TimeZone timeZone;
	
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param dateSpec 日付仕様
	 * @param interval 時間の期間
	 * @param timeZone タイムゾーン
	 */
	public RepeatSchedule(DateSpecification dateSpec, TimeInterval interval, TimeZone timeZone) {
		Validate.notNull(dateSpec);
		Validate.notNull(interval);
		Validate.notNull(timeZone);
		this.dateSpec = dateSpec;
		this.interval = interval;
		this.timeZone = timeZone;
	}
	
	public boolean isInScheduledTime(TimePoint timePoint) {
		if (dateSpec.isSatisfiedBy(timePoint.calendarDate(timeZone)) == false) {
			return false;
		}
		return interval.includes(timePoint);
	}
}
