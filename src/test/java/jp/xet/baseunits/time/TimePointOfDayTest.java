/*
 * Copyright 2011 Daisuke Miyamoto. (http://d.hatena.ne.jp/daisuke-m)
 * Created on 2012/01/11
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
import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.Matchers.is;

import java.util.TimeZone;

import org.junit.Test;

/**
 * {@link TimePointOfDay}のテストクラス。
 */
@SuppressWarnings("javadoc")
public class TimePointOfDayTest {
	
	@Test
	public void test01_toString() {
		TimePointOfDay jstNoon = TimePointOfDay.from(TimeOfDay.NOON, TimeZone.getTimeZone("JST"));
		
		assertThat(jstNoon, hasToString("03:00:00,000 UTC")); // JST 12:00 = UTC 03:00
		assertThat(jstNoon.toString("HH:mm zzz", TimeZone.getTimeZone("JST")), is("12:00 JST"));
		assertThat(jstNoon.toString("HH:mm zzz", TimeZone.getTimeZone("UTC")), is("03:00 UTC"));
	}
}
