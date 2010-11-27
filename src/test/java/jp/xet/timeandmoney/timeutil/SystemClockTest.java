/**
 * Copyright (c) 2004 Domain Language, Inc. (http://domainlanguage.com) This
 * free software is distributed under the "MIT" licence.
 * For more information, see http://timeandmoney.sourceforge.net.
 */

package jp.xet.timeandmoney.timeutil;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.closeTo;
import static org.junit.Assert.assertThat;

import java.util.Date;

import jp.xet.timeandmoney.time.TimePoint;
import jp.xet.timeandmoney.time.TimeSource;

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
		long expectedNow = TimePoint.from(new Date()).breachEncapsulationOfMillisecondsFromEpoc();
		long now = source.now().breachEncapsulationOfMillisecondsFromEpoc();
		
		// タイミングによって成功しない、微妙なテスト…。
		assertThat((double) now, is(closeTo(expectedNow, 50)));
	}
	
}
