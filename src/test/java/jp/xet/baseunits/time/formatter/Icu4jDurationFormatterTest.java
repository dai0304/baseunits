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
 * {@link Icu4jDurationFormatter}のテストクラス。
 */
@SuppressWarnings("javadoc")
public class Icu4jDurationFormatterTest {
	
	Icu4jDurationFormatter formatter = new Icu4jDurationFormatter();
	
	
	@Test
	public void test01_basic() {
		assertThat(formatter.format(Duration.seconds(4), Locale.ENGLISH), is("4 seconds"));
		assertThat(formatter.format(Duration.seconds(4), Locale.JAPAN), is("4秒"));
		assertThat(formatter.format(Duration.minutes(1), Locale.ENGLISH), is("1 minute"));
		assertThat(formatter.format(Duration.hours(2), Locale.ENGLISH), is("2 hours"));
		assertThat(formatter.format(Duration.days(3), Locale.ENGLISH), is("3 days"));
		assertThat(formatter.format(Duration.weeks(5), Locale.ENGLISH), is("5 weeks"));
		assertThat(formatter.format(Duration.months(6), Locale.ENGLISH), is("6 months"));
		assertThat(formatter.format(Duration.quarters(3), Locale.ENGLISH), is("9 months"));
		assertThat(formatter.format(Duration.years(10), Locale.ENGLISH), is("10 years"));
		assertThat(formatter.format(Duration.milliseconds(1000), Locale.JAPAN), is("1秒"));
		assertThat(formatter.format(Duration.milliseconds(1), Locale.JAPAN), is("0秒"));
		
		assertThat(formatter.format(Duration.minutes(4).plus(Duration.hours(5)), Locale.JAPAN), is("18,240秒"));
	}
}
