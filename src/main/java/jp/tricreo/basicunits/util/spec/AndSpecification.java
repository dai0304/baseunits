/*
 * Copyright 2010 the Sisioh Project ant the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package jp.tricreo.basicunits.util.spec;

import org.apache.commons.lang.Validate;

/**
 * 論理積の仕様を表すモデル。
 * 
 * <p>2つの {@link Specification} の論理積をとる {@link Specification} 実装クラス。</p>
 * 
 * @param <T> {@link AndSpecification}の型
 */
public class AndSpecification<T> extends AbstractSpecification<T> {
	
	final Specification<T> spec1;
	
	final Specification<T> spec2;
	

	/**
	 * Create a new AND specification based on two other spec.
	 *
	 * @param spec1 Specification one.
	 * @param spec2 Specification two.
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 */
	public AndSpecification(Specification<T> spec1, Specification<T> spec2) {
		Validate.notNull(spec1);
		Validate.notNull(spec2);
		this.spec1 = spec1;
		this.spec2 = spec2;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isSatisfiedBy(T t) {
		return spec1.isSatisfiedBy(t) && spec2.isSatisfiedBy(t);
	}
}
