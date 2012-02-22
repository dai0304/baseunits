/*
 * Copyright 2011-2012 Daisuke Miyamoto. (http://d.hatena.ne.jp/daisuke-m)
 * Copyright 2010-2011 TRICREO, Inc. (http://tricreo.jp/)
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
 * free software is distributed under the "MIT" licence.
 * For more information, see http://timeandmoney.sourceforge.net.
 */
package jp.xet.baseunits.timeutil;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.closeTo;
import static org.junit.Assert.assertThat;

import java.util.Date;

import jp.xet.baseunits.time.TimePoint;
import jp.xet.baseunits.time.TimeSource;
import jp.xet.baseunits.timeutil.SystemClock;

import org.junit.Test;

/**
 * {@link SystemClock}のテストクラス。
 */
public class SystemClockTest {
	
	/**
	 * {@link SystemClock#timeSource()}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test01_SystemClockTimeSource() throws Exception {
		// The following calls allow polymorphic substitution of TimeSources
		// either in applications or, more often, in testing.
		TimeSource source = SystemClock.timeSource();
		long expectedNow = TimePoint.from(new Date()).toEpochMillisec();
		long now = source.now().toEpochMillisec();
		
		// タイミングによって成功しない、微妙なテスト…。
		assertThat((double) now, is(closeTo(expectedNow, 50)));
	}
	
}
