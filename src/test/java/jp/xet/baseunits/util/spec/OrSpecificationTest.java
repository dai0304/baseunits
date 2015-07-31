/*
 * Copyright 2011-2013 Daisuke Miyamoto. (http://d.hatena.ne.jp/daisuke-m)
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
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import jp.xet.baseunits.util.spec.OrSpecification;
import jp.xet.baseunits.util.spec.Specification;

import org.junit.Test;

/**
 * {@link OrSpecification}のテストクラス。
 */
public class OrSpecificationTest {
	
	/**
	 * {@code false} OR {@code false} が {@code false} となること。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test01_falsefalse_To_false() throws Exception {
		@SuppressWarnings("unchecked")
		Specification<Void> mock1 = mock(Specification.class);
		@SuppressWarnings("unchecked")
		Specification<Void> mock2 = mock(Specification.class);
		when(mock1.isSatisfiedBy(any(Void.class))).thenReturn(false);
		when(mock2.isSatisfiedBy(any(Void.class))).thenReturn(false);
		
		OrSpecification<Void> or = new OrSpecification<Void>(mock1, mock2);
		assertThat(or.isSatisfiedBy(null), is(false));
		
		verify(mock1).isSatisfiedBy(null);
		verify(mock2).isSatisfiedBy(null);
	}
	
	/**
	 * {@code false} OR {@code true} が {@code true} となること。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test02_falsetrue_To_true() throws Exception {
		@SuppressWarnings("unchecked")
		Specification<Void> mock1 = mock(Specification.class);
		@SuppressWarnings("unchecked")
		Specification<Void> mock2 = mock(Specification.class);
		when(mock1.isSatisfiedBy(any(Void.class))).thenReturn(false);
		when(mock2.isSatisfiedBy(any(Void.class))).thenReturn(true);
		
		OrSpecification<Void> or = new OrSpecification<Void>(mock1, mock2);
		assertThat(or.isSatisfiedBy(null), is(true));
		
		verify(mock1).isSatisfiedBy(null);
		verify(mock2).isSatisfiedBy(null);
	}
	
	/**
	 * {@code true} OR {@code false} が {@code true} となること。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test03_truefalse_To_true() throws Exception {
		@SuppressWarnings("unchecked")
		Specification<Void> mock1 = mock(Specification.class);
		@SuppressWarnings("unchecked")
		Specification<Void> mock2 = mock(Specification.class);
		when(mock1.isSatisfiedBy(any(Void.class))).thenReturn(true);
		when(mock2.isSatisfiedBy(any(Void.class))).thenReturn(false);
		
		OrSpecification<Void> or = new OrSpecification<Void>(mock1, mock2);
		assertThat(or.isSatisfiedBy(null), is(true));
		
		verify(mock1).isSatisfiedBy(null);
//		verify(mock2, never()).isSatisfiedBy(null);
	}
	
	/**
	 * {@code true} OR {@code true} が {@code true} となること。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test04_truetrue_To_true() throws Exception {
		@SuppressWarnings("unchecked")
		Specification<Void> mock1 = mock(Specification.class);
		@SuppressWarnings("unchecked")
		Specification<Void> mock2 = mock(Specification.class);
		when(mock1.isSatisfiedBy(any(Void.class))).thenReturn(true);
		when(mock2.isSatisfiedBy(any(Void.class))).thenReturn(true);
		
		OrSpecification<Void> or = new OrSpecification<Void>(mock1, mock2);
		assertThat(or.isSatisfiedBy(null), is(true));
		
		verify(mock1).isSatisfiedBy(null);
//		verify(mock2, never()).isSatisfiedBy(null);
	}
}
