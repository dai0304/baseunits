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
package jp.xet.baseunits.mirage;

import jp.sf.amateras.mirage.type.ValueType;

/**
 * Baseunits内の型を扱う {@link ValueType}の骨格実装クラス。
 * 
 * @param <T> baseunitsの値オブジェクト型
 * @author daisuke
 * @since 2.0
 */
public abstract class AbstractBaseunitsValueType<T> implements ValueType<T> {
	
	@Override
	public T getDefaultValue() {
		return null;
	}
}
