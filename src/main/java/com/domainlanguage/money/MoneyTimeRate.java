/**
 * Copyright (c) 2005 Domain Language, Inc. (http://domainlanguage.com) This
 * free software is distributed under the "MIT" licence. See file licence.txt.
 * For more information, see http://timeandmoney.sourceforge.net.
 */
package com.domainlanguage.money;

import java.util.Currency;

import com.domainlanguage.base.Rounding;
import com.domainlanguage.time.Duration;
import com.domainlanguage.time.TimeRate;

import org.apache.commons.lang.Validate;

/**
 * 単位時間あたりの金額（時間量に対する金額の割合）をあらわすクラス。
 * 
 * <p>例えば時給、人月単価など。</p>
 * 
 * @version $Id$
 * @author daisuke
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
		if (currency == null) {
			if (other.currency != null) {
				return false;
			}
		} else if (!currency.equals(other.currency)) {
			return false;
		}
		if (rate == null) {
			if (other.rate != null) {
				return false;
			}
		} else if (!rate.equals(other.rate)) {
			return false;
		}
		return true;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((currency == null) ? 0 : currency.hashCode());
		result = prime * result + ((rate == null) ? 0 : rate.hashCode());
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
