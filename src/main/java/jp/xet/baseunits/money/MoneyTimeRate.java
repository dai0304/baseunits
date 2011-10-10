/*
 * Copyright 2011 Daisuke Miyamoto. (http://d.hatena.ne.jp/daisuke-m)
 * Copyright 2010-2011 TRICREO, Inc. (http://tricreo.jp/)
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
 * Copyright (c) 2005 Domain Language, Inc. (http://domainlanguage.com) This
 * free software is distributed under the "MIT" licence.
 * For more information, see http://timeandmoney.sourceforge.net.
 */
package jp.xet.baseunits.money;

import java.math.RoundingMode;
import java.util.Currency;

import jp.xet.baseunits.time.Duration;
import jp.xet.baseunits.time.TimeRate;

import org.apache.commons.lang.Validate;

/**
 * 単位時間あたりの金額（時間量に対する金額の割合）をあらわすクラス。
 * 
 * <p>例えば時給、人月単価など。</p>
 * 
 * @since 1.0
 */
public class MoneyTimeRate {
	
	/** 単位時間あたりの数量 */
	private final TimeRate rate;
	
	/** 通貨単位 */
	private final Currency currency;
	
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param money 金額
	 * @param duration 時間量
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	public MoneyTimeRate(Money money, Duration duration) {
		Validate.notNull(money);
		Validate.notNull(duration);
		rate = new TimeRate(money.breachEncapsulationOfAmount(), duration);
		currency = money.breachEncapsulationOfCurrency();
	}
	
	/**
	 * このオブジェクトの{@link #currency}フィールド（通貨単位）を返す。
	 * 
	 * <p>CAUTION: このメソッドは、このオブジェクトがカプセル化する要素を外部に暴露する。取り扱いには充分注意のこと。</p>
	 * 
	 * @return 通貨単位
	 * @since 1.0
	 */
	public Currency breachEncapsulationOfCurrency() {
		return currency;
	}
	
	/**
	 * このオブジェクトの{@link #rate}フィールド（単位時間当たりの数量）を返す。
	 * 
	 * <p>CAUTION: このメソッドは、このオブジェクトがカプセル化する要素を外部に暴露する。取り扱いには充分注意のこと。</p>
	 * 
	 * @return 単位時間あたりの数量
	 * @since 1.0
	 */
	public TimeRate breachEncapsulationOfRate() {
		return rate;
	}
	
	@Override
	public boolean equals(Object obj) {
		assert rate != null;
		assert currency != null;
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		MoneyTimeRate other = (MoneyTimeRate) obj;
		if (currency.equals(other.currency) == false) {
			return false;
		}
		if (rate.equals(other.rate) == false) {
			return false;
		}
		return true;
	}
	
	@Override
	public int hashCode() {
		assert rate != null;
		assert currency != null;
		final int prime = 31;
		int result = 1;
		result = prime * result + currency.hashCode();
		result = prime * result + rate.hashCode();
		return result;
	}
	
	/**
	 * 時間量に対してこの割合を適用した場合の金額を返す。
	 * 
	 * @param duration 時間量
	 * @return 金額
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	public Money over(Duration duration) {
		Validate.notNull(duration);
		return over(duration, RoundingMode.UNNECESSARY);
	}
	
	/**
	 * 時間量に対してこの割合を適用した場合の金額を返す。
	 * 
	 * @param duration 時間量
	 * @param scale スケール
	 * @param roundMode 丸めモード
	 * @return 金額
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @since 1.0
	 * @deprecated 次期メジャーバージョンアップ（v2.0）以降、この機能はサポートされません。
	 * 			use {@link #over(Duration, int, RoundingMode)}
	 */
	@Deprecated
	public Money over(Duration duration, int scale, jp.xet.baseunits.util.Rounding roundMode) {
		Validate.notNull(duration);
		Validate.notNull(roundMode);
		return Money.valueOf(rate.over(duration, scale, roundMode), currency);
	}
	
	/**
	 * 時間量に対してこの割合を適用した場合の金額を返す。
	 * 
	 * @param duration 時間量
	 * @param scale スケール
	 * @param roundMode 丸めモード
	 * @return 金額
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @since 1.1
	 */
	public Money over(Duration duration, int scale, RoundingMode roundMode) {
		Validate.notNull(duration);
		Validate.notNull(roundMode);
		return Money.valueOf(rate.over(duration, scale, roundMode), currency);
	}
	
	/**
	 * 時間量に対してこの割合を適用した場合の金額を返す。
	 * 
	 * @param duration 時間量
	 * @param roundMode 丸めモード
	 * @return 金額
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @since 1.0
	 * @deprecated 次期メジャーバージョンアップ（v2.0）以降、この機能はサポートされません。
	 * 			use {@link #over(Duration, RoundingMode)}
	 */
	@Deprecated
	public Money over(Duration duration, jp.xet.baseunits.util.Rounding roundMode) {
		Validate.notNull(duration);
		Validate.notNull(roundMode);
		return over(duration, rate.scale(), roundMode);
	}
	
	/**
	 * 時間量に対してこの割合を適用した場合の金額を返す。
	 * 
	 * @param duration 時間量
	 * @param roundMode 丸めモード
	 * @return 金額
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @since 1.1
	 */
	public Money over(Duration duration, RoundingMode roundMode) {
		Validate.notNull(duration);
		Validate.notNull(roundMode);
		return over(duration, rate.scale(), roundMode);
	}
	
	@Override
	public String toString() {
		return currency.getSymbol() + " " + rate.toString();
	}
}
