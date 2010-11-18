/**
 * Copyright (c) 2004 Domain Language, Inc. (http://domainlanguage.com) This
 * free software is distributed under the "MIT" licence. See file licence.txt.
 * For more information, see http://timeandmoney.sourceforge.net.
 */

package com.domainlanguage.money;

import java.math.BigDecimal;

import com.domainlanguage.base.Ratio;
import com.domainlanguage.base.Rounding;

public class Proration {
	
	static Ratio[] ratios(BigDecimal[] proportions) {
		BigDecimal total = sum(proportions);
		Ratio[] ratios = new Ratio[proportions.length];
		for (int i = 0; i < ratios.length; i++) {
			ratios[i] = Ratio.of(proportions[i], total);
		}
		return ratios;
	}
	
	static BigDecimal sum(BigDecimal[] elements) {
		BigDecimal sum = new BigDecimal(0);
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
	
	private static int defaultScaleForIntermediateCalculations(Money total) {
		return total.getCurrency().getDefaultFractionDigits() + 1;
	}
	
	public Money[] dividedEvenlyIntoParts(Money total, int n) {
		Money lowResult = total.dividedBy(BigDecimal.valueOf(n), Rounding.DOWN.value);
		Money[] lowResults = new Money[n];
		for (int i = 0; i < n; i++) {
			lowResults[i] = lowResult;
		}
		Money remainder = total.minus(sum(lowResults));
		return distributeRemainderOver(lowResults, remainder);
	}
	
	public Money partOfWhole(Money total, long portion, long whole) {
		return partOfWhole(total, Ratio.of(portion, whole));
	}
	
	public Money partOfWhole(Money total, Ratio ratio) {
		int scale = defaultScaleForIntermediateCalculations(total);
		BigDecimal multiplier = ratio.decimalValue(scale, Rounding.DOWN);
		return total.times(multiplier, Rounding.DOWN);
	}
	
	public Money[] proratedOver(Money total, BigDecimal[] proportions) {
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
	
	public Money[] proratedOver(Money total, long[] longProportions) {
		BigDecimal[] proportions = new BigDecimal[longProportions.length];
		for (int i = 0; i < longProportions.length; i++) {
			proportions[i] = BigDecimal.valueOf(longProportions[i]);
		}
		return proratedOver(total, proportions);
	}
	
	Money[] distributeRemainderOver(Money[] amounts, Money remainder) {
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
	
	private void assertAmountsLengthLessThanOrEqualTo(Money[] amounts, int increments) {
		if (increments > amounts.length) {
			throw new IllegalArgumentException();
		}
	}
	
}
