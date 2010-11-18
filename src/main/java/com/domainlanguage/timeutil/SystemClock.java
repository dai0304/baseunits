/**
 * Copyright (c) 2004 Domain Language, Inc. (http://domainlanguage.com) This
 * free software is distributed under the "MIT" licence. See file licence.txt.
 * For more information, see http://timeandmoney.sourceforge.net.
 */

package com.domainlanguage.timeutil;

import java.util.Date;

import com.domainlanguage.time.TimePoint;
import com.domainlanguage.time.TimeSource;

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
