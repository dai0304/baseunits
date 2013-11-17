/*
 * Copyright 2011-2013 Daisuke Miyamoto. (http://d.hatena.ne.jp/daisuke-m)
 * Copyright 2010-2011 TRICREO, Inc. (http://tricreo.jp/)
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

/**
 * 仕様を表すモデル。
 * 
 * <p>DDD本の中で説明している Specification パターンを表現するインターフェイス。
 * {@link Specification}の実装は、 {@link AbstractSpecification}を基底クラスとして実装するとよい。
 * その場合、 {@link #isSatisfiedBy(Object)} を実装する必要しかない。</p>
 * 
 * @param <T> {@link Specification}の型
 * @author daisuke
 * @since 1.0
 */
public interface Specification<T> {
	
	/**
	 * Create a new specification that is the AND operation of {@code this} specification and another specification.
	 * 
	 * @param specification Specification to AND.
	 * @return A new specification.
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	Specification<T> and(Specification<T> specification);
	
	/**
	 * Check if {@code t} is satisfied by the specification.
	 *
	 * @param t Object to test.
	 * @return {@code true} if {@code t} satisfies the specification.
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	boolean isSatisfiedBy(T t);
	
	/**
	 * Create a new specification that is the NOT operation of {@code this} specification.
	 * 
	 * @return A new specification.
	 * @since 1.0
	 */
	Specification<T> not();
	
	/**
	 * Create a new specification that is the OR operation of {@code this} specification and another specification.
	 * 
	 * @param specification Specification to OR.
	 * @return A new specification.
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	Specification<T> or(Specification<T> specification);
}
