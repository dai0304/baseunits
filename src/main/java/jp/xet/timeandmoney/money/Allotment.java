package jp.xet.timeandmoney.money;

import org.apache.commons.lang.Validate;

/**
 * 何かに対するお金の割り当てをあらわす。
 * 
 * @param <T> 割り当て対象
 * @version $Id$
 * @author daisuke
 */
public class Allotment<T> {
	
	final T entity;
	
	final Money amount;
	

	/**
	 * インスタンスを生成する。
	 * 
	 * @param entity 割り当て対象
	 * @param amount 割り当て量
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 */
	public Allotment(T entity, Money amount) {
		Validate.notNull(entity);
		Validate.notNull(amount);
		this.entity = entity;
		this.amount = amount;
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
		result = prime * result + ((amount == null) ? 0 : amount.hashCode());
		result = prime * result + ((entity == null) ? 0 : entity.hashCode());
		return result;
	}
	
	/**
	 * 割り当て量の正負を反転させた新しい割り当てを返す。
	 * 
	 * @return 割り当て
	 */
	public Allotment<T> negated() {
		return new Allotment<T>(entity, amount.negated());
	}
	
	@Override
	public String toString() {
		return "" + entity + " --> " + amount;
	}
}
