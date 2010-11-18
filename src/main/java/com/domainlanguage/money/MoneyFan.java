package com.domainlanguage.money;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.Validate;

public class MoneyFan<T> {
	
	private Set<Allotment<T>> allotments;
	

	/**
	 * インスタンスを生成する。
	 * 
	 * @param allotments
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 */
	public MoneyFan(Allotment<T>... allotments) {
		Validate.noNullElements(allotments);
		Set<Allotment<T>> setOfAllotments = new HashSet<Allotment<T>>();
		setOfAllotments.addAll(Arrays.asList(allotments));
		this.allotments = setOfAllotments;
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param allotments
	 * @throws IllegalArgumentException 引数またはその要素に{@code null}を与えた場合
	 */
	public MoneyFan(Set<Allotment<T>> allotments) {
		Validate.noNullElements(allotments);
		this.allotments = allotments;
	}
	
	public Allotment<T> allotment(T anEntity) {
		for (Allotment<T> allotment : allotments) {
			if (allotment.entity.equals(anEntity)) {
				return allotment;
			}
		}
		return null;
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
		final MoneyFan<?> other = (MoneyFan<?>) obj;
		if (allotments == null) {
			if (other.allotments != null) {
				return false;
			}
		} else if (!allotments.equals(other.allotments)) {
			return false;
		}
		return true;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((allotments == null) ? 0 : allotments.hashCode());
		return result;
	}
	
	/**
	 * TODO for daisuke
	 * 
	 * @param subtracted
	 * @return
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 */
	public MoneyFan<T> minus(MoneyFan<T> subtracted) {
		Validate.notNull(subtracted);
		return plus(subtracted.negated());
	}
	
	public MoneyFan<T> negated() {
		Set<Allotment<T>> negatedAllotments = new HashSet<Allotment<T>>();
		for (Allotment<T> allotment : allotments) {
			negatedAllotments.add(allotment.negated());
		}
		return new MoneyFan<T>(negatedAllotments);
	}
	
	public MoneyFan<T> plus(MoneyFan<T> added) {
		Set<T> allEntities = new HashSet<T>();
		for (Allotment<T> allotment : allotments) {
			allEntities.add(allotment.entity);
		}
		for (Allotment<T> allotment : added.allotments) {
			allEntities.add(allotment.entity);
		}
		Set<Allotment<T>> summedAllotments = new HashSet<Allotment<T>>();
		for (T entity : allEntities) {
			if (this.allotment(entity) == null) {
				summedAllotments.add(added.allotment(entity));
			} else if (added.allotment(entity) == null) {
				summedAllotments.add(this.allotment(entity));
			} else {
				Money sum = this.allotment(entity).amount.plus(added.allotment(entity).amount);
				summedAllotments.add(new Allotment<T>(entity, sum));
			}
		}
		return new MoneyFan<T>(summedAllotments).withoutZeros();
	}
	
	@Override
	public String toString() {
		return "" + allotments;
	}
	
	public Money total() {
		return asTally().net();
	}
	
	private Tally asTally() {
		List<Money> moneies = new ArrayList<Money>();
		for (Allotment<T> allotment : allotments) {
			moneies.add(allotment.amount);
		}
		return new Tally(moneies);
	}
	
	private MoneyFan<T> withoutZeros() {
		Set<Allotment<T>> nonZeroAllotments = new HashSet<Allotment<T>>();
		for (Allotment<T> allotment : allotments) {
			if (!allotment.amount.isZero()) {
				nonZeroAllotments.add(allotment);
			}
		}
		return new MoneyFan<T>(nonZeroAllotments);
	}
	
}
