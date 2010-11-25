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
 * Copyright (c) 2005 Domain Language, Inc. (http://domainlanguage.com) This
 * free software is distributed under the "MIT" licence. See file licence.txt.
 * For more information, see http://timeandmoney.sourceforge.net.
 */
package jp.xet.timeandmoney.money;

import java.util.Currency;

import jp.xet.timeandmoney.time.Duration;
import jp.xet.timeandmoney.time.TimeRate;
import jp.xet.timeandmoney.util.Rounding;

import org.apache.commons.lang.Validate;

/**
 * 単位時間あたりの金額（時間量に対する金額の割合）をあらわすクラス。
 * 
 * <p>例えば時給、人月単価など。</p>
 */
public class MoneyTimeRate {
	
	private final TimeRate rate;
	
	private final Currency currency;
	

	/**
	 * インスタンスを生成する。
	 * 
	 * @param money 金額
	 * @param duration 時間量
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 */
	public MoneyTimeRate(Money money, Duration duration) {
		Validate.notNull(money);
		Validate.notNull(duration);
		rate = new TimeRate(money.getAmount(), duration);
		currency = money.getCurrency();
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
	 */
	public Money over(Duration duration) {
		Validate.notNull(duration);
		return over(duration, Rounding.UNNECESSARY);
	}
	
	/**
	 * 時間量に対してこの割合を適用した場合の金額を返す。
	 * 
	 * @param duration 時間量
	 * @param scale スケール
	 * @param roundRule 丸めルール
	 * @return 金額
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 */
	public Money over(Duration duration, int scale, Rounding roundRule) {
		Validate.notNull(duration);
		Validate.notNull(roundRule);
		return Money.valueOf(rate.over(duration, scale, roundRule), currency);
	}
	
	/**
	 * 時間量に対してこの割合を適用した場合の金額を返す。
	 * 
	 * @param duration 時間量
	 * @param roundRule 丸めルール
	 * @return 金額
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 */
	public Money over(Duration duration, Rounding roundRule) {
		Validate.notNull(duration);
		Validate.notNull(roundRule);
		return over(duration, rate.scale(), roundRule);
	}
	
	@Override
	public String toString() {
		return rate.toString();
	}
}
