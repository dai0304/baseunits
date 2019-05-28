/*
 * Copyright 2010-2019 Miyamoto Daisuke.
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
package jp.xet.baseunits.util.spec;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

/**
 * {@link Specifications}のテストクラス。
 */
public class SpecificationsTest {
	
	/**
	 * alwaysとneverのテスト。
	 */
	@Test
	public void test01_always_and_never() {
		Specification<Void> always = Specifications.always();
		Specification<Void> never = Specifications.never();
		assertThat(always.isSatisfiedBy(null), is(true));
		assertThat(never.isSatisfiedBy(null), is(false));
	}
}
