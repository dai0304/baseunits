package com.domainlanguage.money;

public class Allotment<T> {
	
	T entity;
	
	Money amount;
	

	public Allotment(T entity, Money amount) {
		this.entity = entity;
		this.amount = amount;
	}
	
	@Override
	public String toString() {
		return "" + entity + " --> " + amount;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((amount == null) ? 0 : amount.hashCode());
		result = prime * result + ((entity == null) ? 0 : entity.hashCode());
		return result;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
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
		if (amount == null) {
			if (other.amount != null) {
				return false;
			}
		} else if (!amount.equals(other.amount)) {
			return false;
		}
		if (entity == null) {
			if (other.entity != null) {
				return false;
			}
		} else if (!entity.equals(other.entity)) {
			return false;
		}
		return true;
	}
	
	public Allotment<T> negated() {
		return new Allotment<T>(entity, amount.negated());
	}
}
