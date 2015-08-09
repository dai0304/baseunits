/*
 * Copyright 2010-2015 Miyamoto Daisuke.
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
package jp.xet.baseunits.money;

import com.google.common.base.Preconditions;

/**
 * 何かに対するお金の割り当てをあらわす。
 * 
 * @param <T> 割り当て対象
 * @author daisuke
 * @since 1.0
 */
public class Allotment<T> {
	
	/** 割り当て対象 */
	final T entity;
	
	/** 金額 */
	final Money amount;
	
	
	/**
	* インスタンスを生成する。
	* 
	* @param entity 割り当て対象
	* @param amount 割り当て量
	* @throws NullPointerException 引数に{@code null}を与えた場合
	 * @since 1.0
	*/
	public Allotment(T entity, Money amount) {
		Preconditions.checkNotNull(entity);
		Preconditions.checkNotNull(amount);
		this.entity = entity;
		this.amount = amount;
	}
	
	/**
	 * このオブジェクトの{@link #amount}フィールド（金額）を返す。
	 * 
	 * <p>CAUTION: このメソッドは、このオブジェクトがカプセル化する要素を外部に暴露する。取り扱いには充分注意のこと。</p>
	 * 
	 * @return 金額
	 * @since 1.0
	 */
	public Money breachEncapsulationOfAmount() {
		return amount;
	}
	
	/**
	 * このオブジェクトの{@link #entity}フィールド（割り当て対象）を返す。
	 * 
	 * <p>CAUTION: このメソッドは、このオブジェクトがカプセル化する要素を外部に暴露する。取り扱いには充分注意のこと。</p>
	 * 
	 * @return 割り当て対象
	 * @since 1.0
	 */
	public T breachEncapsulationOfEntity() {
		return entity;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Allotment<?> other = (Allotment<?>) obj;
		if (amount.equals(other.amount) == false) {
			return false;
		}
		if (entity.equals(other.entity) == false) {
			return false;
		}
		return true;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + amount.hashCode();
		result = prime * result + entity.hashCode();
		return result;
	}
	
	/**
	 * 割り当て量の正負を反転させた新しい割り当てを返す。
	 * 
	 * @return 割り当て
	 * @since 1.0
	 */
	public Allotment<T> negated() {
		return new Allotment<T>(entity, amount.negated());
	}
	
	@Override
	public String toString() {
		return "" + entity + " --> " + amount;
	}
}
