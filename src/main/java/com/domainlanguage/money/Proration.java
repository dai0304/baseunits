/**
 * Copyright (c) 2004 Domain Language, Inc. (http://domainlanguage.com) This
 * free software is distributed under the "MIT" licence. See file licence.txt.
 * For more information, see http://timeandmoney.sourceforge.net.
 */

package com.domainlanguage.money;

import java.math.BigDecimal;

import com.domainlanguage.base.Ratio;
import com.domainlanguage.base.Rounding;

import org.apache.commons.lang.Validate;

/**
 * 比例配分の為のユーティリティ。
 * 
 * @version $Id$
 * @author daisuke
 */
public final class Proration {
	
	public static Money[] dividedEvenlyIntoParts(Money total, int n) {
		Money lowResult = total.dividedBy(BigDecimal.valueOf(n), Rounding.DOWN);
		Money[] lowResults = new Money[n];
		for (int i = 0; i < n; i++) {
			lowResults[i] = lowResult;
		}
		Money remainder = total.minus(sum(lowResults));
		return distributeRemainderOver(lowResults, remainder);
	}
	
	public static Money partOfWhole(Money total, long portion, long whole) {
		return partOfWhole(total, Ratio.of(portion, whole));
	}
	
	public static Money partOfWhole(Money total, Ratio ratio) {
		int scale = defaultScaleForIntermediateCalculations(total);
		BigDecimal multiplier = ratio.decimalValue(scale, Rounding.DOWN);
		return total.times(multiplier, Rounding.DOWN);
	}
	
	public static Money[] proratedOver(Money total, BigDecimal[] proportions) {
		Money[] simpleResult = new Money[proportions.length];
		int scale = defaultScaleForIntermediateCalculations(total);
		Ratio[] ratios = ratios(proportions);
		for (int i = 0; i < ratios.length; i++) {
			BigDecimal multiplier = ratios[i].decimalValue(scale, Rounding.DOWN);
			simpleResult[i] = total.times(multiplier, Rounding.DOWN);
		}
		Money remainder = total.minus(sum(simpleResult));
		return distributeRemainderOver(simpleResult, remainder);
	}
	
	public static Money[] proratedOver(Money total, long[] longProportions) {
		BigDecimal[] proportions = new BigDecimal[longProportions.length];
		for (int i = 0; i < longProportions.length; i++) {
			proportions[i] = BigDecimal.valueOf(longProportions[i]);
		}
		return proratedOver(total, proportions);
	}
	
	static Money[] distributeRemainderOver(Money[] amounts, Money remainder) {
		int increments =
				remainder.dividedBy(remainder.minimumIncrement()).decimalValue(0, Rounding.UNNECESSARY).intValue();
		assertAmountsLengthLessThanOrEqualTo(amounts, increments);
		
		Money[] results = new Money[amounts.length];
		for (int i = 0; i < increments; i++) {
			results[i] = amounts[i].incremented();
		}
		for (int i = increments; i < amounts.length; i++) {
			results[i] = amounts[i];
		}
		return results;
	}
	
	/**
	 * TODO for daisuke
	 * 
	 * @param proportions
	 * @return
	 * @throws IllegalArgumentException 引数{@code elements}またはその要素に{@code null}を与えた場合
	 */
	static Ratio[] ratios(BigDecimal[] proportions) {
		BigDecimal total = sum(proportions);
		Ratio[] ratios = new Ratio[proportions.length];
		for (int i = 0; i < ratios.length; i++) {
			ratios[i] = Ratio.of(proportions[i], total);
		}
		return ratios;
	}
	
	/**
	 * {@code elements}の要素の和を返す。
	 * 
	 * @param elements 配列
	 * @return 和
	 * @throws IllegalArgumentException 引数{@code elements}またはその要素に{@code null}を与えた場合
	 */
	static BigDecimal sum(BigDecimal[] elements) {
		Validate.noNullElements(elements);
		BigDecimal sum = BigDecimal.ZERO;
		for (BigDecimal element : elements) {
			sum = sum.add(element);
		}
		return sum;
	}
	
	static Money sum(Money[] elements) {
		Money sum = Money.valueOf(0, elements[0].getCurrency());
		for (Money element : elements) {
			sum = sum.plus(element);
		}
		return sum;
	}
	
	private static void assertAmountsLengthLessThanOrEqualTo(Money[] amounts, int increments) {
		if (increments > amounts.length) {
			throw new IllegalArgumentException();
		}
	}
	
	private static int defaultScaleForIntermediateCalculations(Money total) {
		return total.getCurrency().getDefaultFractionDigits() + 1;
	}
	
	private Proration() {
	}
	
}
