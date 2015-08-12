/*
 * Copyright 2010-2015 Miyamoto Daisuke.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package jp.xet.baseunits.time.formatter;

import java.io.Serializable;
import java.util.Locale;
import java.util.TimeZone;

import jp.xet.baseunits.time.TimePoint;

/**
 * 常に指定した文字列を返すフォーマッタ。
 * 
 * <p>主に「たった今」や「ずっと昔」等のフォールバック値を表示することを想定する。</p>
 * 
 * @author daisuke
 * @since 2.0
 */
@SuppressWarnings("serial")
public class FixedTimePointFormatter extends AbstractTimePointFormatter implements Serializable {
	
	private final String fixed;
	
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param fixed 固定文字列
	 */
	public FixedTimePointFormatter(String fixed) {
		this.fixed = fixed;
	}
	
	@Override
	public String format(TimePoint target, Locale locale, TimeZone timeZone) {
		return fixed;
	}
}
