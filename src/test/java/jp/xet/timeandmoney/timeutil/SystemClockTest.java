/**
 * Copyright (c) 2004 Domain Language, Inc. (http://domainlanguage.com) This
 * free software is distributed under the "MIT" licence. See file licence.txt.
 * For more information, see http://timeandmoney.sourceforge.net.
 */

package jp.xet.timeandmoney.timeutil;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.assertThat;

import java.util.Date;

import jp.xet.timeandmoney.time.Duration;
import jp.xet.timeandmoney.time.TimePoint;
import jp.xet.timeandmoney.time.TimeSource;

import org.junit.Ignore;
import org.junit.Test;

/**
 * {@link SystemClock}のテストクラス。
 * 
 * @author daisuke
 */
public class SystemClockTest {
	
	/**
	 * {@link SystemClock#timeSource()}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	@Ignore
	public void test01_SystemClockTimeSource() throws Exception {
		// The following calls allow polymorphic substitution of TimeSources
		// either in applications or, more often, in testing.
		TimeSource source = SystemClock.timeSource();
		TimePoint expectedNow = TimePoint.from(new Date());
		TimePoint now = source.now();
		
		// タイミングによって成功しない、微妙なテスト…。
		assertThat(now.until(expectedNow).length().compareTo(Duration.milliseconds(50)), is(lessThan(0)));
	}
	
}
