/*
 * Copyright 2011-2012 Daisuke Miyamoto. (http://d.hatena.ne.jp/daisuke-m)
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
 * Copyright (c) 2004 Domain Language, Inc. (http://domainlanguage.com) This
 * free software is distributed under the "MIT" licence.
 * For more information, see http://timeandmoney.sourceforge.net.
 */
package jp.xet.baseunits.time.spec;

import java.io.Serializable;

import jp.xet.baseunits.time.CalendarDate;
import jp.xet.baseunits.time.DayOfMonth;
import jp.xet.baseunits.time.MonthOfYear;

import com.google.common.base.Preconditions;

/**
 * 毎年特定の月日にマッチする暦日仕様実装クラス。
 * 
 * @author daisuke
 * @since 1.0
 */
@SuppressWarnings("serial")
public final class AnnualFixedDateSpecification extends AbstractAnnualDateSpecification implements Serializable {
	
	final MonthOfYear month;
	
	final DayOfMonth day;
	
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param month 月
	 * @param day 日
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 */
	AnnualFixedDateSpecification(MonthOfYear month, DayOfMonth day) {
		Preconditions.checkNotNull(month);
		Preconditions.checkNotNull(day);
		this.month = month;
		this.day = day;
	}
	
	/**
	 * この仕様を満たす条件としての日を返す。
	 * 
	 * @return この仕様を満たす条件としての日
	 * @since 2.0
	 */
	public DayOfMonth getDay() {
		return day;
	}
	
	/**
	 * この仕様を満たす条件としての月を返す。
	 * 
	 * @return この仕様を満たす条件としての月
	 * @since 2.0
	 */
	public MonthOfYear getMonth() {
		return month;
	}
	
	@Override
	public boolean isSatisfiedBy(CalendarDate date) {
		Preconditions.checkNotNull(date);
		return day.equals(date.getDayOfMonth())
				&& month == date.asCalendarMonth().getMonthOfYear();
	}
	
	@Override
	public CalendarDate ofYear(int year) {
		return CalendarDate.from(year, month, day);
	}
	
	@Override
	public String toString() {
		return day.toString() + " " + month.toString();
	}
}
