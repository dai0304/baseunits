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
package jp.xet.baseunits.timeutil;

import jp.xet.baseunits.time.TimePoint;
import jp.xet.baseunits.time.TimeSource;

import com.google.common.base.Preconditions;

/**
 * 常に指定した時間を返す {@link TimeSource} 実装クラス。
 * 
 * <p>主にデバッグ用途を意図している。</p>
 * 
 * @author daisuke
 * @since 2.0
 */
public final class FixedTimeSource implements TimeSource {
	
	final TimePoint fixed;
	
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param fixed 固定する時間
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 */
	public FixedTimeSource(TimePoint fixed) {
		Preconditions.checkNotNull(fixed);
		this.fixed = fixed;
	}
	
	@Override
	public TimePoint now() {
		return fixed;
	}
}
