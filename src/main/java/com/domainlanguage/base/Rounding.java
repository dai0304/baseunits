package com.domainlanguage.base;

import java.math.BigDecimal;

/**
 * 丸めルールを表す列挙型。
 * 
 * @author daisuke
 */
public enum Rounding {
	
	/**
	 * 切り上げルール CEILING。
	 * 
	 * <p>正の無限大に向けて切り上げを行う丸めルールを表す。</p>
	 * 
	 * @see BigDecimal#ROUND_CEILING
	 */
	CEILING(BigDecimal.ROUND_CEILING),

	/**
	 * 切り上げルール UP。
	 * 
	 * <p>{@code 0}から遠ざかる方向に向けた丸めルールを表す。</p>
	 * 
	 * @see BigDecimal#ROUND_UP
	 */
	UP(BigDecimal.ROUND_UP),

	/**
	 * 切り捨てルール DOWN。
	 * 
	 * <p>{@code 0}に近づく方向に向けた丸めルールを表す。</p>
	 * 
	 * @see BigDecimal#ROUND_DOWN
	 */
	DOWN(BigDecimal.ROUND_DOWN),

	/**
	 * 切り捨てルール FLOOR。
	 * 
	 * <p>負の無限大に向けて切り上げを行う丸めルールを表す。</p>
	 * 
	 * @see BigDecimal#ROUND_FLOOR
	 */
	FLOOR(BigDecimal.ROUND_FLOOR),

	/**
	 * 丸めルール HALF_UP。
	 * 
	 * <p>両隣のうち、近い方に向けて切り上げを行う丸めルールを表す。両者の距離が同一である場合は、
	 * {@link #UP}ルールを適用する。</p>
	 * 
	 * @see BigDecimal#ROUND_HALF_UP
	 */
	HALF_UP(BigDecimal.ROUND_HALF_UP),

	/**
	 * 丸めルール HALF_DOWN。
	 * 
	 * <p>両隣のうち、近い方に向けて切り上げを行う丸めルールを表す。両者の距離が同一である場合は、
	 * {@link #DOWN}ルールを適用する。</p>
	 * 
	 * @see BigDecimal#ROUND_HALF_DOWN
	 */
	HALF_DOWN(BigDecimal.ROUND_HALF_DOWN),

	/**
	 * 丸めルール HALF_EVEN。
	 * 
	 * <p>両隣のうち、近い方に向けて切り上げを行う丸めルールを表す。両者の距離が同一である場合は、
	 * 偶数側に丸める。</p>
	 * 
	 * @see BigDecimal#ROUND_HALF_DOWN
	 */
	HALF_EVEN(BigDecimal.ROUND_HALF_EVEN),

	/**
	 * 丸めが不要であることを明示的に表すルール。
	 * 
	 * <p>このルールを明示的に指定したが割り切れない場合は、そのメソッドは {@link ArithmeticException}を
	 * スローする。</p>
	 * 
	 * @see BigDecimal#ROUND_UNNECESSARY
	 */
	UNNECESSARY(BigDecimal.ROUND_UNNECESSARY);
	
	/**
	 * {@link BigDecimal}クラスにおける int型 による定義値。
	 */
	public final int value;
	

	Rounding(int value) {
		this.value = value;
	}
}
