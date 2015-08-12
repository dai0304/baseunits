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

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Locale;

import jp.xet.baseunits.time.Duration;

import org.junit.Test;

/**
 * {@link Icu4jHourAndMinuteDurationFormatter}のテストクラス。
 */
@SuppressWarnings({
	"javadoc",
	"deprecation"
})
public class Icu4jHourAndMinuteDurationFormatterTest {
	
	@Test
	public void test01_basic() {
		Icu4jHourAndMinuteDurationFormatter f = new Icu4jHourAndMinuteDurationFormatter(true);
		assertThat(f.format(Duration.seconds(4), Locale.ENGLISH), is("0 minutes"));
		assertThat(f.format(Duration.seconds(4), Locale.JAPAN), is("0分"));
		assertThat(f.format(Duration.minutes(1), Locale.ENGLISH), is("1 minute"));
		assertThat(f.format(Duration.minutes(61), Locale.ENGLISH), is("1 hour 1 minute"));
		assertThat(f.format(Duration.minutes(63), Locale.ENGLISH), is("1 hour 3 minutes"));
		assertThat(f.format(Duration.minutes(121), Locale.ENGLISH), is("2 hours 1 minute"));
		assertThat(f.format(Duration.minutes(123), Locale.ENGLISH), is("2 hours 3 minutes"));
		assertThat(f.format(Duration.hours(2), Locale.ENGLISH), is("2 hours 0 minutes"));
		assertThat(f.format(Duration.days(3), Locale.ENGLISH), is("72 hours 0 minutes"));
		
		assertThat(f.format(Duration.minutes(4).plus(Duration.hours(5)), Locale.JAPAN), is("5時間 4分"));
	}
	
	@Test
	public void test02_basic() {
		Icu4jHourAndMinuteDurationFormatter f = new Icu4jHourAndMinuteDurationFormatter(false);
		assertThat(f.format(Duration.seconds(4), Locale.ENGLISH), is("0 minutes"));
		assertThat(f.format(Duration.seconds(4), Locale.JAPAN), is("0分"));
		assertThat(f.format(Duration.minutes(1), Locale.ENGLISH), is("1 minute"));
		assertThat(f.format(Duration.minutes(61), Locale.ENGLISH), is("1 hour 1 minute"));
		assertThat(f.format(Duration.minutes(63), Locale.ENGLISH), is("1 hour 3 minutes"));
		assertThat(f.format(Duration.minutes(121), Locale.ENGLISH), is("2 hours 1 minute"));
		assertThat(f.format(Duration.minutes(123), Locale.ENGLISH), is("2 hours 3 minutes"));
		assertThat(f.format(Duration.hours(2), Locale.ENGLISH), is("2 hours"));
		assertThat(f.format(Duration.days(3), Locale.ENGLISH), is("72 hours"));
		
		assertThat(f.format(Duration.minutes(4).plus(Duration.hours(5)), Locale.JAPAN), is("5時間 4分"));
	}
}
