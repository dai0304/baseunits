/*
 * Copyright 2011 Daisuke Miyamoto. (http://d.hatena.ne.jp/daisuke-m)
 * Created on 2011/11/23
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
package jp.xet.baseunits.timeutil;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import jp.xet.baseunits.time.TimePoint;

import org.junit.Test;

/**
 * {@link FixedTimeSource}のテストクラス。
 */
public class FixedTimeSourceTest {
	
	static final TimePoint TP = TimePoint.atUTC(1978, 3, 4, 6, 56, 0, 0);
	
	
	@Test
	@SuppressWarnings("javadoc")
	public void test() throws InterruptedException {
		FixedTimeSource source = new FixedTimeSource(TP);
		assertThat(source.now(), is(TP));
		Thread.sleep(1000L);
		assertThat(source.now(), is(TP));
	}
	
}
