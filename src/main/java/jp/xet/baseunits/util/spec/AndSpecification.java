/*
 * Copyright 2011 Daisuke Miyamoto. (http://d.hatena.ne.jp/daisuke-m)
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
package jp.xet.baseunits.util.spec;

import org.apache.commons.lang.Validate;

/**
 * 論理積の仕様を表すモデル。
 * 
 * <p>2つの {@link Specification} の論理積をとる {@link Specification} 実装クラス。</p>
 * 
 * @param <T> {@link AndSpecification}の型
 * @since 1.0
 */
public class AndSpecification<T> extends AbstractSpecification<T> {
	
	final Specification<T> left;
	
	final Specification<T> right;
	
	
	/**
	 * Create a new AND specification based on two other spec.
	 *
	 * @param left left side Specification.
	 * @param right right side Specification.
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	public AndSpecification(Specification<T> left, Specification<T> right) {
		Validate.notNull(left);
		Validate.notNull(right);
		this.left = left;
		this.right = right;
	}
	
	@Override
	public boolean isSatisfiedBy(T t) {
		return left.isSatisfiedBy(t) && right.isSatisfiedBy(t);
	}
}
