/*
 * Copyright 2011-2012 Daisuke Miyamoto. (http://d.hatena.ne.jp/daisuke-m)
 * Created on 2011/12/22
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
package jp.xet.baseunits.time;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

/**
 * {@link CalendarWeek}のテストクラス。
 */
@SuppressWarnings("javadoc")
public class CalendarWeekTest {
	
	@Test
	public void testToString() {
		assertThat(CalendarWeek.from(2011, 52).toString(), is("2011-52th"));
		assertThat(CalendarWeek.from(2011, 53).toString(), is("2012-1th"));
		assertThat(CalendarWeek.from(2012, 1).toString(), is("2012-1th"));
	}
}
