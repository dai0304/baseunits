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
 * 否定の仕様を表すモデル。
 * 
 * <p>ある {@link Specification} の否定をとる {@link Specification} 実装クラス。
 * デコレータではないので注意。</p>
 * 
 * @param <T> {@link NotSpecification}の型
 * @since 1.0
 */
public class NotSpecification<T> extends AbstractSpecification<T> {
	
	final Specification<T> spec1;
	
	
	/**
	 * Create a new NOT specification based on another spec.
	 *
	 * @param spec1 Specification instance to not.
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	public NotSpecification(final Specification<T> spec1) {
		Validate.notNull(spec1);
		this.spec1 = spec1;
	}
	
	@Override
	public boolean isSatisfiedBy(T t) {
		return spec1.isSatisfiedBy(t) == false;
	}
}
