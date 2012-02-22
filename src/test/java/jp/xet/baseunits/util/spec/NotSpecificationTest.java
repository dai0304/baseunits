/*
 * Copyright 2011 Daisuke Miyamoto. (http://d.hatena.ne.jp/daisuke-m)
 * Copyright 2010-2011 TRICREO, Inc. (http://tricreo.jp/)
 * Created on 2010/11/26
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
package jp.xet.baseunits.util.spec;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;

/**
 * {@link NotSpecification}のテストクラス。
 */
public class NotSpecificationTest {
	
	/**
	 * NOT {@code false} が {@code true} となること。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test01_false_To_true() throws Exception {
		@SuppressWarnings("unchecked")
		Specification<Void> mock = mock(Specification.class);
		when(mock.isSatisfiedBy(any(Void.class))).thenReturn(false);
		
		NotSpecification<Void> not = new NotSpecification<Void>(mock);
		assertThat(not.isSatisfiedBy(null), is(true));
		
		verify(mock).isSatisfiedBy(null);
	}
	
	/**
	 * NOT {@code true} が {@code false} となること。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test01_true_To_false() throws Exception {
		@SuppressWarnings("unchecked")
		Specification<Void> mock = mock(Specification.class);
		when(mock.isSatisfiedBy(any(Void.class))).thenReturn(true);
		
		NotSpecification<Void> not = new NotSpecification<Void>(mock);
		assertThat(not.isSatisfiedBy(null), is(false));
		
		verify(mock).isSatisfiedBy(null);
	}
	
	/**
	 * 二重notのテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test03_always_to_always() throws Exception {
		Specification<Void> always = Specifications.always();
		Specification<Void> notnot = always.not().not();
		
		// 二度連続でnotする場合、wrappingを外して元のインスタンスに戻る
		assertThat(notnot, is(sameInstance(always)));
	}
}
