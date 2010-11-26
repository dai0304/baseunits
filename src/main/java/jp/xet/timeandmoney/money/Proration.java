/*
 * Copyright 2010 Daisuke Miyamoto.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 * ----
 * Copyright (c) 2004 Domain Language, Inc. (http://domainlanguage.com) This
 * free software is distributed under the "MIT" licence. See file licence.txt.
 * For more information, see http://timeandmoney.sourceforge.net.
 */
package jp.xet.timeandmoney.money;

import java.math.BigDecimal;

import jp.xet.timeandmoney.util.Ratio;
import jp.xet.timeandmoney.util.Rounding;

import org.apache.commons.lang.Validate;

/**
 * 比例配分の為のユーティリティ。
 */
public final class Proration {
	
	/**
	 * 指定した金額を{@code n}等分した金額の配列を返す。
	 * 
	 * <p>但し、割り切れなかった分（余り）は、最小単位金額に分割し、配列の頭から順に上乗せする。</p>
	 * 
	 * <p>例えば、53円を5人で等分した場合は、<code>{11, 11, 11, 10, 10}</code>となる。</p>
	 * 
	 * @param total 合計金額
	 * @param n 分割数
	 * @return 分割結果
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 */
	public static Money[] dividedEvenlyIntoParts(Money total, int n) {
		Validate.notNull(total);
		Money lowResult = total.dividedBy(BigDecimal.valueOf(n), Rounding.DOWN);
		Money[] lowResults = new Money[n];
		for (int i = 0; i < n; i++) {
			lowResults[i] = lowResult;
		}
		Money remainder = total.minus(sum(lowResults));
		return distributeRemainderOver(lowResults, remainder);
	}
	
	/**
	 * {@code total}のうち、{@code portion / whole}の割合の金額を返す。割り切れない場合は切り捨てる。
	 * 
	 * @param total 合計額
	 * @param portion 部分量をあらわす値
	 * @param whole 全体量をあらわす値
	 * @return 部分の金額
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @throws ArithmeticException 引数{@code whole}が0だった場合
	 */
	public static Money partOfWhole(Money total, long portion, long whole) {
		Validate.notNull(total);
		return partOfWhole(total, Ratio.of(portion, whole));
	}
	
	/**
	 * {@code total}のうち、{@code ratio}の割合の金額を返す。割り切れない場合は切り捨てる。
	 * 
	 * @param total 合計額
	 * @param ratio 割合
	 * @return 指定した割合の金額
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 */
	public static Money partOfWhole(Money total, Ratio ratio) {
		Validate.notNull(total);
		Validate.notNull(ratio);
		int scale = defaultScaleForIntermediateCalculations(total);
		BigDecimal multiplier = ratio.decimalValue(scale, Rounding.DOWN);
		return total.times(multiplier, Rounding.DOWN);
	}
	
	/**
	 * 指定した金額を{@code proportions}であらわす割合で分割した金額の配列を返す。
	 * 
	 * <p>但し、割り切れなかった分（余り）は、最小単位金額に分割し、配列の頭から順に上乗せする。</p>
	 * 
	 * <p>例えば、52円を1:3:1で等分した場合は、<code>{11, 31, 10}</code>となる。</p>
	 * 
	 * @param total 合計金額
	 * @param proportions 比数の配列
	 * @return 分割結果
	 * @throws IllegalArgumentException 引数{@code total}に{@code null}を与えた場合
	 * @throws IllegalArgumentException 引数{@code proportions}またはその要素に{@code null}を与えた場合
	 */
	public static Money[] proratedOver(Money total, BigDecimal[] proportions) {
		Validate.notNull(total);
		Validate.noNullElements(proportions);
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
	
	/**
	 * 指定した金額を{@code proportions}であらわす割合で分割した金額の配列を返す。
	 * 
	 * <p>但し、割り切れなかった分（余り）は、最小単位金額に分割し、配列の頭から順に上乗せする。</p>
	 * 
	 * <p>例えば、52円を1:3:1で等分した場合は、<code>{11, 31, 10}</code>となる。</p>
	 * 
	 * @param total 合計金額
	 * @param longProportions 比数の配列
	 * @return 分割結果
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 */
	public static Money[] proratedOver(Money total, long[] longProportions) {
		Validate.notNull(total);
		Validate.notNull(longProportions);
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
	 * 比数の配列を割合の配列に変換する。
	 * 
	 * @param proportions 比の配列
	 * @return 割合の配列
	 * @throws IllegalArgumentException 引数{@code elements}またはその要素に{@code null}を与えた場合
	 */
	static Ratio[] ratios(BigDecimal[] proportions) {
		Validate.noNullElements(proportions);
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
	
	/**
	 * {@code elements}の要素の和を返す。
	 * 
	 * @param elements 配列
	 * @return 和
	 * @throws IllegalArgumentException 引数{@code elements}またはその要素に{@code null}を与えた場合
	 * @throws IllegalArgumentException 引数{@code elements}の要素数が0の場合
	 */
	static Money sum(Money[] elements) {
		Validate.noNullElements(elements);
		Validate.isTrue(elements.length > 0);
		Money sum = Money.valueOf(0, elements[0].breachEncapsulationOfCurrency());
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
		return total.breachEncapsulationOfAmount().precision() + 2;
	}
	
	private Proration() {
	}
	
}
