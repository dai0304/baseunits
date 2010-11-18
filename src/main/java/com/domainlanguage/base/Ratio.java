/**
 * Copyright (c) 2004 Domain Language, Inc. (http://domainlanguage.com) This
 * free software is distributed under the "MIT" licence. See file licence.txt.
 * For more information, see http://timeandmoney.sourceforge.net.
 * 
 */

package com.domainlanguage.base;

import java.math.BigDecimal;

import org.apache.commons.lang.Validate;

/**
 * {@link Ratio}は、2つ同じ単位を持つの量の商（比率）であり、単位のない値である。
 * 
 * このクラスの利点は、比率の計算を遅延評価できることにある。
 * 
 * Ratio represents the unitless division of two quantities of the same type.
 * The key to its usefulness is that it defers the calculation of a decimal
 * value for the ratio. An object which has responsibility for the two values in
 * the ratio and understands their quantities can create the ratio, which can
 * then be used by any client in a unitless form, so that the client is not
 * required to understand the units of the quantity. At the same time, this
 * gives control of the precision and rounding rules to the client, when the
 * time comes to compute a decimal value for the ratio. The client typically has
 * the responsibilities that enable an appropriate choice of these parameters.
 *  
 * @author  Eric Evans
 */
public class Ratio {
	
	/**
	 * 分子
	 * 
	 * {@link Ratio} は immutableオブジェクトであるが、永続化用の変更器のために、finalとしていない。
	 */
	private BigDecimal numerator;
	
	/**
	 * 分母
	 * 
	 * {@link Ratio} は immutableオブジェクトであるが、永続化用の変更器のために、finalとしていない。
	 */
	private BigDecimal denominator;
	

	/**
	 * インスタンスを生成する。
	 * 
	 * @param numerator 分子
	 * @param denominator 分母
	 * @return 引数に与えた分子、分母からなる比
	 */
	public static Ratio of(BigDecimal numerator, BigDecimal denominator) {
		return new Ratio(numerator, denominator);
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param numerator 分子
	 * @param denominator 分母
	 * @return 引数に与えた分子、分母からなる比率
	 */
	public static Ratio of(long numerator, long denominator) {
		return new Ratio(BigDecimal.valueOf(numerator), BigDecimal.valueOf(denominator));
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param fractional 分数
	 * @return 与えた分数であらわされる比率
	 */
	public static Ratio of(BigDecimal fractional) {
		return new Ratio(fractional, BigDecimal.valueOf(1));
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param numerator 分子
	 * @param denominator 分母
	 */
	public Ratio(BigDecimal numerator, BigDecimal denominator) {
		this.numerator = numerator;
		this.denominator = denominator;
	}
	
	/**
	 * 比率を {@link BigDecimal}型で取得する。
	 *  
	 * @param scale 小数点以下の有効数字
	 * @param roundingRule 丸めルール
	 * @return この比率の {@link BigDecimal} 型の表現
	 */
	public BigDecimal decimalValue(int scale, Rounding roundingRule) {
		return numerator.divide(denominator, scale, roundingRule.value);
	}
	
	/**
	 * このオブジェクトと、与えたオブジェクトの同一性を検証する。
	 * 
	 * <p>与えたオブジェクト {@code anObject} が {@code null}である場合、または{@link Ratio}型や
	 * そのサブクラスではない場合、{@code false}を返す。
	 * 与えたオブジェクトの、分母と分子が共に一致する場合、{@code true}を返す。</p>
	 * 
	 * <p>{@code 2/3} と {@code 4/6} は、評価結果としては同一であるが、分母同士、分子同士が
	 * 異なるため、このメソッドでは {@code true} と判断されず、 {@code false} となる。
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object anObject) {
		try {
			return equals((Ratio) anObject);
		} catch (ClassCastException ex) {
			return false;
		}
	}
	
	/**
	 * このオブジェクトと、与えたオブジェクトの同一性を検証する。
	 * 
	 * @param other 比較対象オブジェクト
	 * @return 同一の場合は{@code true}、そうでない場合は{@code false}
	 * @see #equals(Object)
	 */
	public boolean equals(Ratio other) {
		return other != null
				&& numerator.equals(other.numerator)
				&& denominator.equals(other.denominator);
	}
	
	/**
	 * このオブジェクトのハッシュ値を取得する。
	 * 
	 * TODO equalsとの整合性がとれていない？
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return numerator.hashCode();
	}
	
	/**
	 * この比率と {@code multiplier} の積からなる比率。
	 * 
	 * <p>計算結果は、分母は変化せず、分子は分子と {@code multiplyer} の積からなる比率となる。</p>
	 * 
	 * @param multiplier 乗数
	 * @return 積
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 */
	public Ratio times(BigDecimal multiplier) {
		Validate.notNull(multiplier);
		return Ratio.of(numerator.multiply(multiplier), denominator);
	}
	
	/**
	 * この比率と {@code multiplier} の積からなる比率。
	 * 
	 * <p>計算結果は、分子同士・分母同士の積からなる比率となる。</p>
	 * 
	 * @param multiplier 乗数比率
	 * @return 積
	 */
	public Ratio times(Ratio multiplier) {
		return Ratio.of(numerator.multiply(multiplier.numerator), denominator.multiply(multiplier.denominator));
	}
	
	/** この比率の文字列表現を取得する。
	 * 
	 * <p>"分子/分母"という表記となる。</p>
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return numerator.toString() + "/" + denominator;
	}
	
	/**
	 * Only for use by persistence mapping frameworks
	 * <rant>These methods break encapsulation and we put them in here begrudgingly</rant>
	 */
	Ratio() {
	}
	
	/**
	 * Only for use by persistence mapping frameworks
	 * <rant>These methods break encapsulation and we put them in here begrudgingly</rant>
	 * @return {@link #denominator}
	 */
	@SuppressWarnings("unused")
	private BigDecimal getForPersistentMapping_Denominator() { // CHECKSTYLE IGNORE THIS LINE
		return denominator;
	}
	
	/**
	 * Only for use by persistence mapping frameworks
	 * <rant>These methods break encapsulation and we put them in here begrudgingly</rant>
	 * @param denominator {@link #denominator}
	 */
	@SuppressWarnings("unused")
	private void setForPersistentMapping_Denominator(BigDecimal denominator) { // CHECKSTYLE IGNORE THIS LINE
		this.denominator = denominator;
	}
	
	/**
	 * Only for use by persistence mapping frameworks
	 * <rant>These methods break encapsulation and we put them in here begrudgingly</rant>
	 * @return {@link #numerator}
	 */
	@SuppressWarnings("unused")
	private BigDecimal getForPersistentMapping_Numerator() { // CHECKSTYLE IGNORE THIS LINE
		return numerator;
	}
	
	/**
	 * Only for use by persistence mapping frameworks
	 * <rant>These methods break encapsulation and we put them in here begrudgingly</rant>
	 * @param numerator {@link #numerator}
	 */
	@SuppressWarnings("unused")
	private void setForPersistentMapping_Numerator(BigDecimal numerator) { // CHECKSTYLE IGNORE THIS LINE
		this.numerator = numerator;
	}
	
}
