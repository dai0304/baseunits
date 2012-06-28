/*
 * Copyright 2011 Daisuke Miyamoto. (http://d.hatena.ne.jp/daisuke-m)
 * Created on 2012/06/27
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
package jp.xet.baseunits.time.formatter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.Locale;

import jp.xet.baseunits.time.Duration;
import jp.xet.baseunits.time.TimeUnit;

import org.junit.Test;

/**
 * TODO for daisuke
 */
@SuppressWarnings("javadoc")
public class DetailedDurationFormatterTest {
	
	static Duration H10_M10 = Duration.hours(10).plus(Duration.minutes(10));
	
	static Duration H10_S10 = Duration.hours(10).plus(Duration.seconds(10));
	
	
	@Test
	public void test1() {
		DurationFormatter f1 = new DetailedDurationFormatter(true, TimeUnit.hour, TimeUnit.minute);
		DurationFormatter f2 = new DetailedDurationFormatter(false, TimeUnit.hour, TimeUnit.minute);
		DurationFormatter f3 = new DetailedDurationFormatter(true, TimeUnit.day, TimeUnit.hour, TimeUnit.minute);
		DurationFormatter f4 = new DetailedDurationFormatter(false, TimeUnit.day, TimeUnit.hour, TimeUnit.minute);
		DurationFormatter f5 = new DetailedDurationFormatter(true, TimeUnit.hour, TimeUnit.minute, TimeUnit.second);
		DurationFormatter f6 = new DetailedDurationFormatter(false, TimeUnit.hour, TimeUnit.minute, TimeUnit.second);
		assertThat(f1.format(H10_M10, Locale.JAPAN), is("10時間 10分"));
		assertThat(f2.format(H10_M10, Locale.JAPAN), is("10時間 10分"));
		assertThat(f3.format(H10_M10, Locale.JAPAN), is("0日 10時間 10分"));
		assertThat(f4.format(H10_M10, Locale.JAPAN), is("10時間 10分"));
		assertThat(f5.format(H10_M10, Locale.JAPAN), is("10時間 10分 0秒"));
		assertThat(f6.format(H10_M10, Locale.JAPAN), is("10時間 10分"));
	}
	
	@Test
	public void test2() {
		DurationFormatter f1 = new DetailedDurationFormatter(true, TimeUnit.hour, TimeUnit.minute);
		DurationFormatter f2 = new DetailedDurationFormatter(false, TimeUnit.hour, TimeUnit.minute);
		DurationFormatter f3 = new DetailedDurationFormatter(true, TimeUnit.day, TimeUnit.hour, TimeUnit.minute);
		DurationFormatter f4 = new DetailedDurationFormatter(false, TimeUnit.day, TimeUnit.hour, TimeUnit.minute);
		DurationFormatter f5 = new DetailedDurationFormatter(true, TimeUnit.hour, TimeUnit.minute, TimeUnit.second);
		DurationFormatter f6 = new DetailedDurationFormatter(false, TimeUnit.hour, TimeUnit.minute, TimeUnit.second);
		assertThat(f1.format(H10_S10, Locale.JAPAN), is("10時間 0分"));
		assertThat(f2.format(H10_S10, Locale.JAPAN), is("10時間"));
		assertThat(f3.format(H10_S10, Locale.JAPAN), is("0日 10時間 0分"));
		assertThat(f4.format(H10_S10, Locale.JAPAN), is("10時間"));
		assertThat(f5.format(H10_S10, Locale.JAPAN), is("10時間 0分 10秒"));
		assertThat(f6.format(H10_S10, Locale.JAPAN), is("10時間 10秒"));
	}
	
	@Test
	public void test3() throws Exception {
		DurationFormatter f1 = new DetailedDurationFormatter(true, TimeUnit.hour, TimeUnit.minute);
		DurationFormatter f2 = new DetailedDurationFormatter(false, TimeUnit.hour, TimeUnit.minute);
		assertThat(f1.format(Duration.NONE), is("0時間 0分"));
		assertThat(f2.format(Duration.NONE), is("0分"));
	}
}
