/*
 * Copyright 2010 TRICREO, Inc. (http://tricreo.jp/)
 * Created on 2010/11/27
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
package jp.tricreo.basicunits.util.spec;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThat;
import jp.tricreo.basicunits.util.spec.AbstractSpecification;
import jp.tricreo.basicunits.util.spec.AndSpecification;
import jp.tricreo.basicunits.util.spec.NotSpecification;
import jp.tricreo.basicunits.util.spec.OrSpecification;
import jp.tricreo.basicunits.util.spec.Specification;

import org.junit.Test;

/**
 * {@link AbstractSpecification}のテストクラス。
 */
public class AbstractSpecificationTest {
	
	/**
	 * {@link AbstractSpecification#and(Specification)}
	 * {@link AbstractSpecification#or(Specification)}
	 * {@link AbstractSpecification#not()}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test01_and_or_not() throws Exception {
		AbstractSpecification<Void> spec = new AbstractSpecification<Void>() {
			
			@Override
			public boolean isSatisfiedBy(Void t) {
				return false;
			}
		};
		assertThat(spec.and(spec), is(instanceOf(AndSpecification.class)));
		assertThat(spec.or(spec), is(instanceOf(OrSpecification.class)));
		assertThat(spec.not(), is(instanceOf(NotSpecification.class)));
	}
	
}
