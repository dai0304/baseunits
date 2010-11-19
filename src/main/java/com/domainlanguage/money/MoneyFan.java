package com.domainlanguage.money;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.Validate;

/**
 * 割り当ての集合。
 * 
 * @param <T> 割り当て対象
 * @version $Id$
 * @author daisuke
 */
public class MoneyFan<T> {
	
	private Set<Allotment<T>> allotments;
	

	/**
	 * インスタンスを生成する。
	 * 
	 * @param allotments 割り当ての集合。 {@link Set}の仕様と同様、重複は排除される。
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
	 * @param allotments 割り当ての{@link Set}
	 * @throws IllegalArgumentException 引数またはその要素に{@code null}を与えた場合
	 */
	public MoneyFan(Set<Allotment<T>> allotments) {
		Validate.noNullElements(allotments);
		this.allotments = allotments;
	}
	
	/**
	 * {@link MoneyFan}が保持する {@link Allotment}のうち、割り当て対象が {@code anEntity}であるものを返す。
	 * 
	 * @param anEntity 割り当て対象
	 * @return {@link Allotment}。見つからなかった場合は{@code null}
	 */
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
		if (allotments.equals(other.allotments) == false) {
			return false;
		}
		return true;
	}
	
	@Override
	public int hashCode() {
		return allotments.hashCode();
	}
	
	/**
	 * この{@link MoneyFan}から{@code subtracted}を引いた差を返す。
	 * 
	 * @param subtracted {@link MoneyFan}
	 * @return {@link MoneyFan}
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 */
	public MoneyFan<T> minus(MoneyFan<T> subtracted) {
		Validate.notNull(subtracted);
		return plus(subtracted.negated());
	}
	
	/**
	 * この {@link MoneyFan}の {@link Allotment}を {@link Allotment#negated()}した {@link Set}で構成される
	 * 新しい {@link MoneyFan}を返す。
	 * 
	 * @return {@link MoneyFan}
	 */
	public MoneyFan<T> negated() {
		Set<Allotment<T>> negatedAllotments = new HashSet<Allotment<T>>();
		for (Allotment<T> allotment : allotments) {
			negatedAllotments.add(allotment.negated());
		}
		return new MoneyFan<T>(negatedAllotments);
	}
	
	/**
	 * この{@link MoneyFan}に{@code added}を足した和を返す。
	 * 
	 * <p>同じ割り当て対象に対する割当額は、マージする。また、割当額が0の {@link Allotment} は取り除く。</p>
	 * 
	 * @param added {@link MoneyFan}
	 * @return {@link MoneyFan}
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 */
	public MoneyFan<T> plus(MoneyFan<T> added) {
		Validate.notNull(added);
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
	
	/**
	 * 全ての割り当ての合計額を返す。
	 * 
	 * @return 合計額
	 */
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
	
	/**
	 * このインスタンスが保持する {@link Allotment} のうち、割り当て金額が{@code 0}であるものを取り除いた
	 * 新しい {@link MoneyFan}を返す。
	 * 
	 * @return {@link MoneyFan}
	 */
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
