/*
 * Copyright 2010-2015 Miyamoto Daisuke.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package jp.xet.baseunits.time.formatter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;

import java.util.Locale;

import jp.xet.baseunits.time.Duration;
import jp.xet.baseunits.time.TimeUnit;

import org.junit.Test;

/**
 * {@link DetailedDurationFormatter}のテストクラス。
 */
@SuppressWarnings("javadoc")
public class DetailedDurationFormatterTest {
	
	static Duration DUR_10h_10m = Duration.hours(10).plus(Duration.minutes(10));
	
	static Duration DUR_10h_10s = Duration.hours(10).plus(Duration.seconds(10));
	
	
	@Test
	public void test1() {
		DurationFormatter f1 = new DetailedDurationFormatter(true, TimeUnit.hour, TimeUnit.minute);
		DurationFormatter f2 = new DetailedDurationFormatter(false, TimeUnit.hour, TimeUnit.minute);
		DurationFormatter f3 = new DetailedDurationFormatter(true, TimeUnit.day, TimeUnit.hour, TimeUnit.minute);
		DurationFormatter f4 = new DetailedDurationFormatter(false, TimeUnit.day, TimeUnit.hour, TimeUnit.minute);
		DurationFormatter f5 = new DetailedDurationFormatter(true, TimeUnit.hour, TimeUnit.minute, TimeUnit.second);
		DurationFormatter f6 = new DetailedDurationFormatter(false, TimeUnit.hour, TimeUnit.minute, TimeUnit.second);
		assertThat(f1.format(DUR_10h_10m, Locale.JAPAN), is("10時間 10分"));
		assertThat(f2.format(DUR_10h_10m, Locale.JAPAN), is("10時間 10分"));
		assertThat(f3.format(DUR_10h_10m, Locale.JAPAN), is("0日 10時間 10分"));
		assertThat(f4.format(DUR_10h_10m, Locale.JAPAN), is("10時間 10分"));
		assertThat(f5.format(DUR_10h_10m, Locale.JAPAN), is("10時間 10分 0秒"));
		assertThat(f6.format(DUR_10h_10m, Locale.JAPAN), is("10時間 10分"));
	}
	
	@Test
	public void test2() {
		DurationFormatter f1 = new DetailedDurationFormatter(true, TimeUnit.hour, TimeUnit.minute);
		DurationFormatter f2 = new DetailedDurationFormatter(false, TimeUnit.hour, TimeUnit.minute);
		DurationFormatter f3 = new DetailedDurationFormatter(true, TimeUnit.day, TimeUnit.hour, TimeUnit.minute);
		DurationFormatter f4 = new DetailedDurationFormatter(false, TimeUnit.day, TimeUnit.hour, TimeUnit.minute);
		DurationFormatter f5 = new DetailedDurationFormatter(true, TimeUnit.hour, TimeUnit.minute, TimeUnit.second);
		DurationFormatter f6 = new DetailedDurationFormatter(false, TimeUnit.hour, TimeUnit.minute, TimeUnit.second);
		assertThat(f1.format(DUR_10h_10s, Locale.JAPAN), is("10時間 0分"));
		assertThat(f2.format(DUR_10h_10s, Locale.JAPAN), is("10時間"));
		assertThat(f3.format(DUR_10h_10s, Locale.JAPAN), is("0日 10時間 0分"));
		assertThat(f4.format(DUR_10h_10s, Locale.JAPAN), is("10時間"));
		assertThat(f5.format(DUR_10h_10s, Locale.JAPAN), is("10時間 0分 10秒"));
		assertThat(f6.format(DUR_10h_10s, Locale.JAPAN), is("10時間 10秒"));
	}
	
	@Test
	@SuppressWarnings("unchecked")
	public void test3() {
		DurationFormatter f1 = new DetailedDurationFormatter(true, TimeUnit.hour, TimeUnit.minute);
		DurationFormatter f2 = new DetailedDurationFormatter(false, TimeUnit.hour, TimeUnit.minute);
		assertThat(f1.format(Duration.NONE), anyOf(is("0 hours 0 minutes"), is("0時間 0分")));
		assertThat(f2.format(Duration.NONE), anyOf(is("0 minutes"), is("0分")));
	}
}
