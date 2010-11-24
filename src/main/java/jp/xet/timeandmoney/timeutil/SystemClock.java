/*
 * Copyright 2010 Daisuke Miyamoto.
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
 * ----
 * Copyright (c) 2004 Domain Language, Inc. (http://domainlanguage.com) This
 * free software is distributed under the "MIT" licence. See file licence.txt.
 * For more information, see http://timeandmoney.sourceforge.net.
 */
package jp.xet.timeandmoney.timeutil;

import java.util.Date;

import jp.xet.timeandmoney.time.TimePoint;
import jp.xet.timeandmoney.time.TimeSource;

/**
 * システム時計に基づき、現在の時刻を返すクラス。
 * 
 * @version $Id$
 * @author daisuke
 */
public final class SystemClock implements TimeSource {
	
	private static final SystemClock INSTANCE = new SystemClock();
	

	/**
	 * システム時間に基づき現在の時刻を返す {@link TimeSource} を返す。
	 * 
	 * @return システム時間に基づき現在の時刻を返す {@link TimeSource}
	 */
	public static TimeSource timeSource() {
		// THINK なぜ INSTANCE を直接返さないのか？ 謎だ。
		return new TimeSource() {
			
			@Override
			public TimePoint now() {
				return INSTANCE.now();
			}
		};
	}
	
	private SystemClock() {
	}
	
	@Override
	public TimePoint now() {
		return TimePoint.from(new Date());
	}
}
