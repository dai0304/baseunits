/*
 * Copyright 2011-2012 Daisuke Miyamoto. (http://d.hatena.ne.jp/daisuke-m)
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
 * ----
 * Copyright (c) 2004 Domain Language, Inc. (http://domainlanguage.com) This
 * free software is distributed under the "MIT" licence.
 * For more information, see http://timeandmoney.sourceforge.net.
 */
package jp.xet.baseunits.util.spec;

import java.io.Serializable;

/**
 * 日付の仕様を表現するオブジェクト。
 * 
 * @author daisuke
 * @since 2.0
 */
public final class Specifications {
	
	/**
	 * どの日付にもマッチする日付仕様を返す。
	 * 
	 * @return 日付仕様
	 * @since 2.0
	 */
	@SuppressWarnings("unchecked")
	public static <T>Specification<T> always() {
		return AlwaysSpecification.INSTANCE;
	}
	
	/**
	 * どの日付にもマッチしない日付仕様を返す。
	 * 
	 * @return 日付仕様
	 * @since 1.0
	 */
	@SuppressWarnings("unchecked")
	public static <T>Specification<T> never() {
		return NeverSpecification.INSTANCE;
	}
	
	private Specifications() {
	}
	
	
	@SuppressWarnings("serial")
	private static final class AlwaysSpecification<T> extends AbstractSpecification<T> implements Serializable {
		
		@SuppressWarnings("rawtypes")
		static final AlwaysSpecification INSTANCE = new AlwaysSpecification();
		
		
		private AlwaysSpecification() {
		}
		
		@Override
		public boolean isSatisfiedBy(T t) {
			return true;
		}
	}
	
	@SuppressWarnings("serial")
	private static final class NeverSpecification<T> extends AbstractSpecification<T> implements Serializable {
		
		@SuppressWarnings("rawtypes")
		static final NeverSpecification INSTANCE = new NeverSpecification();
		
		
		private NeverSpecification() {
		}
		
		@Override
		public boolean isSatisfiedBy(T t) {
			return false;
		}
		
	}
}
