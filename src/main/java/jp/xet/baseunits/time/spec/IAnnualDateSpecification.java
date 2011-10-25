/*
 * Copyright 2011 Daisuke Miyamoto. (http://d.hatena.ne.jp/daisuke-m)
 * Created on 2011/10/25
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
 */
package jp.xet.baseunits.time.spec;

import jp.xet.baseunits.time.CalendarDate;

/**
 * TODO for daisuke
 * 
 * @since 2.0
 */
public interface IAnnualDateSpecification extends DateSpecification {
	
	/**
	 * 指定した年においてこの日付仕様を満たす年月日を返す。
	 * 
	 * @param year 西暦年をあらわす数
	 * @return {@link CalendarDate}
	 * @since 1.0
	 */
	CalendarDate ofYear(int year);
	
}
