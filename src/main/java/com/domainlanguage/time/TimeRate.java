/**
 * Copyright (c) 2005 Domain Language, Inc. (http://domainlanguage.com) This
 * free software is distributed under the "MIT" licence. See file licence.txt.
 * For more information, see http://timeandmoney.sourceforge.net.
 */
package com.domainlanguage.time;

import java.math.BigDecimal;

import com.domainlanguage.base.Rounding;

import org.apache.commons.lang.Validate;

/**
 * 時間割合。単位時間あたりの、何かの量を表すクラス。
 * 
 * <p>例えば、時給・時速など。</p>
 * 
 * @version $Id$
 * @author daisuke
 */
public class TimeRate {
	
	private BigDecimal quantity;
	
	private Duration unit;
	

	/**
	 * インスタンスを生成する。
	 * 
	 * @param quantity 単位時間あたりの量
	 * @param unit 単位時間
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 */
	public TimeRate(BigDecimal quantity, Duration unit) {
		Validate.notNull(quantity);
		Validate.notNull(unit);
		this.quantity = quantity;
		this.unit = unit;
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param quantity 単位時間あたりの量
	 * @param unit 単位時間
	 * @throws IllegalArgumentException 引数unitに{@code null}を与えた場合
	 * @throws NumberFormatException see {@link BigDecimal#BigDecimal(double)}
	 */
	public TimeRate(double quantity, Duration unit) {
		this(new BigDecimal(quantity), unit);
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param quantity 単位時間あたりの量をあらわす文字列
	 * @param unit 単位時間
	 * @throws NullPointerException 引数quantityに{@code null}を与えた場合
	 * @throws IllegalArgumentException 引数unitに{@code null}を与えた場合
	 * @throws NumberFormatException see {@link BigDecimal#BigDecimal(String)}
	 */
	public TimeRate(String quantity, Duration unit) {
		this(new BigDecimal(quantity), unit);
	}
	
	/**
	 * Only for use by persistence mapping frameworks
	 * <rant>These methods break encapsulation and we put them in here begrudgingly</rant>
	 */
	TimeRate() {
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
		TimeRate other = (TimeRate) obj;
		if (quantity == null) {
			if (other.quantity != null) {
				return false;
			}
		} else if (quantity.equals(other.quantity) == false) {
			return false;
		}
		if (unit == null) {
			if (other.unit != null) {
				return false;
			}
		} else if (!unit.equals(other.unit)) {
			return false;
		}
		return true;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((quantity == null) ? 0 : quantity.hashCode());
		result = prime * result + ((unit == null) ? 0 : unit.hashCode());
		return result;
	}
	
	/**
	 * 指定した時間量にこの時間割合を適用した場合の絶対量を取得する。
	 * 
	 * <p>レート計算における数字の丸めは行わない。</p>
	 * 
	 * <p>例えば、3時間に対して時給1000円を適用すると、3000円となる。</p>
	 * 
	 * @param duration 時間量
	 * @return 絶対量
	 * @throws IllegalArgumentException 引数durationの単位を、このオブジェクトの単位時間の単位に変換できない場合
	 * @throws ArithmeticException 引数{@code duration}の時間量が単位時間で割り切れない場合
	 */
	public BigDecimal over(Duration duration) {
		return over(duration, Rounding.UNNECESSARY);
	}
	
	/**
	 * 指定した時間量にこの時間割合を適用した場合の絶対量を取得する。
	 * 
	 * @param duration 時間量
	 * @param scale 
	 * @param roundRule 丸めルール
	 * @return 絶対量
	 * @throws IllegalArgumentException 引数durationの単位を、このオブジェクトの単位時間の単位に変換できない場合
	 * @throws ArithmeticException 引数 {@code roundRule} に {@link Rounding#UNNECESSARY} を指定したにもかかわらず、
	 * 			引数{@code duration}の時間量が単位時間で割り切れない場合
	 */
	public BigDecimal over(Duration duration, int scale, Rounding roundRule) {
		return duration.dividedBy(unit).times(quantity).decimalValue(scale, roundRule);
	}
	
	/**
	 * 指定した時間量にこの時間割合を適用した場合の絶対量を取得する。
	 * 
	 * @param duration 時間量
	 * @param roundRule 丸めルール
	 * @return 絶対量
	 * @throws IllegalArgumentException 引数durationの単位を、このオブジェクトの単位時間の単位に変換できない場合
	 * @throws ArithmeticException 引数 {@code roundRule} に {@link Rounding#UNNECESSARY} を指定したにもかかわらず、
	 * 			引数{@code duration}の時間量が単位時間で割り切れない場合
	 */
	public BigDecimal over(Duration duration, Rounding roundRule) {
		return over(duration, scale(), roundRule);
	}
	
	public int scale() {
		return quantity.scale();
	}
	
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(quantity);
		buffer.append(" per ");
		buffer.append(unit);
		return buffer.toString();
	}
	
	/**
	 * Only for use by persistence mapping frameworks
	 * <rant>These methods break encapsulation and we put them in here begrudgingly</rant>
	 * @return {@link #quantity}
	 */
	@SuppressWarnings("unused")
	private BigDecimal getForPersistentMapping_Quantity() { // CHECKSTYLE IGNORE THIS LINE
		return quantity;
	}
	
	/**
	 * Only for use by persistence mapping frameworks
	 * <rant>These methods break encapsulation and we put them in here begrudgingly</rant>
	 * @return {@link #unit}
	 */
	@SuppressWarnings("unused")
	private Duration getForPersistentMapping_Unit() { // CHECKSTYLE IGNORE THIS LINE
		return unit;
	}
	
	/**
	 * Only for use by persistence mapping frameworks
	 * <rant>These methods break encapsulation and we put them in here begrudgingly</rant>
	 * @param quantity {@link #quantity}
	 */
	@SuppressWarnings("unused")
	private void setForPersistentMapping_Quantity(BigDecimal quantity) { // CHECKSTYLE IGNORE THIS LINE
		this.quantity = quantity;
	}
	
	/**
	 * Only for use by persistence mapping frameworks
	 * <rant>These methods break encapsulation and we put them in here begrudgingly</rant>
	 * @param unit {@link #unit}
	 */
	@SuppressWarnings("unused")
	private void setForPersistentMapping_Unit(Duration unit) { // CHECKSTYLE IGNORE THIS LINE
		this.unit = unit;
	}
}
