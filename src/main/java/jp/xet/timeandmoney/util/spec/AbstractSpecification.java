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
package jp.xet.timeandmoney.util.spec;

/**
 * {@link Specification}の抽象実装クラス。
 * 
 * <p>{@link #and(Specification)}, {@link #or(Specification)}, {@link #not(Specification)}を
 * 実装済みである。</p>
 * 
 * @param <T> {@link AbstractSpecification}の型
 */
public abstract class AbstractSpecification<T> implements Specification<T> {
	
	@Override
	public Specification<T> and(final Specification<T> specification) {
		return new AndSpecification<T>(this, specification);
	}
	
	@Override
	public Specification<T> not(final Specification<T> specification) {
		return new NotSpecification<T>(specification);
	}
	
	@Override
	public Specification<T> or(final Specification<T> specification) {
		return new OrSpecification<T>(this, specification);
	}
}
